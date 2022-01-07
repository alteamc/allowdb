package land.altea.allowdb.storage;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import land.altea.allowdb.AllowDB;
import land.altea.allowdb.util.MojangAPI;
import land.altea.allowdb.storage.exception.AlreadyAllowedException;
import land.altea.allowdb.storage.exception.NoSuchProfileException;
import land.altea.allowdb.storage.exception.StorageException;
import land.altea.allowdb.storage.model.AllowRecord;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;
import java.util.logging.Level;

public final class AllowList {
    private final @NotNull ConnectionSource conn;
    private final @NotNull Dao<AllowRecord, UUID> dao;

    public AllowList() {
        JdbcPooledConnectionSource conn;
        Dao<AllowRecord, UUID> dao;

        try {
            conn = new JdbcPooledConnectionSource(AllowDB.getInstance().getPConfig().getStorageUrl());
        } catch (SQLException e) {
            AllowDB a = AllowDB.getInstance();
            a.getLogger().log(Level.SEVERE, "Failed to open connection source.", e);
            a.getServer().getPluginManager().disablePlugin(a);

            //noinspection ConstantConditions
            this.conn = null;
            //noinspection ConstantConditions
            this.dao = null;

            return;
        }

        try {
            dao = DaoManager.createDao(conn, AllowRecord.class);
        } catch (SQLException e) {
            AllowDB a = AllowDB.getInstance();
            a.getLogger().log(Level.SEVERE, "Failed to prepare DAO.", e);
            a.getServer().getPluginManager().disablePlugin(a);

            //noinspection ConstantConditions
            this.conn = null;
            //noinspection ConstantConditions
            this.dao = null;

            return;
        }

        try {
            if (!dao.isTableExists()) {
                TableUtils.createTableIfNotExists(conn, AllowRecord.class);
            }
        } catch (SQLException e) {
            AllowDB a = AllowDB.getInstance();
            a.getLogger().log(Level.SEVERE, "Failed to create database table.", e);
            a.getServer().getPluginManager().disablePlugin(a);
        }

        this.conn = conn;
        this.dao = dao;
    }

    public void close() {
        //noinspection ConstantConditions
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                AllowDB.getInstance().getLogger().log(Level.SEVERE, "Failed to close connection source.", e);
            }
        }
    }

    public void add(@NotNull UUID uuid) throws StorageException, NoSuchProfileException, AlreadyAllowedException {
        String nickname = MojangAPI.getNickname(uuid);
        if (nickname == null) {
            throw new NoSuchProfileException();
        }

        try {
            createRecord(uuid, nickname);
        } catch (StorageException e) {
            if (e.getCause() != null && e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new AlreadyAllowedException();
            }
            throw new StorageException("Failed to create allow record.", e);
        }
    }

    public void add(@NotNull String nickname) throws StorageException, NoSuchProfileException, AlreadyAllowedException {
        UUID uuid = MojangAPI.getUuid(nickname);
        if (uuid == null) {
            throw new NoSuchProfileException();
        }

        try {
            createRecord(uuid, nickname);
        } catch (StorageException e) {
            throw new StorageException("Failed to add player to allowlist.", e);
        }
    }

    private void createRecord(@NotNull UUID uuid, @NotNull String nickname) throws StorageException, AlreadyAllowedException {
        try {
            updateConflictingNickname(nickname);
        } catch (SQLException e) {
            throw new StorageException("Failed to update possibly conflicting nicknames.", e);
        }

        try {
            dao.create(new AllowRecord(uuid, nickname));
        } catch (SQLException e) {
            if (e.getCause() != null && e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new AlreadyAllowedException();
            }

            throw new StorageException("Failed to create allow record.", e);
        }
    }

    public boolean isAllowed(@NotNull Player player) throws StorageException {
        try {
            return dao.idExists(player.getUniqueId());
        } catch (SQLException e) {
            throw new StorageException("Failed to query allow entry.", e);
        }
    }

    public void remove(@NotNull UUID uuid) throws StorageException, NoSuchProfileException {
        try {
            if (dao.deleteById(uuid) == 0) {
                throw new NoSuchProfileException();
            }
        } catch (SQLException e) {
            throw new StorageException("Failed to remove allow entry.", e);
        }
    }

    public void remove(@NotNull String nickname) throws StorageException, NoSuchProfileException {
        Player player = AllowDB.getInstance().getServer().getOnlinePlayers().stream()
                .filter(p -> p.getName().equalsIgnoreCase(nickname)).findFirst().orElse(null);

        UUID uuid;
        if (player == null) {
            uuid = MojangAPI.getUuid(nickname);
            if (uuid == null) {
                throw new NoSuchProfileException();
            }

        } else {
            uuid = player.getUniqueId();
        }

        remove(uuid);
    }

    private void updateConflictingNickname(@NotNull String nickname) throws SQLException {
        AllowRecord record = dao.queryBuilder().where().eq("username", nickname).queryForFirst();
        if (record != null) {
            record.setUsername(MojangAPI.getNickname(record.getUuid()));
            dao.update(record);
        }
    }
}
