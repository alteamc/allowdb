package land.altea.allowdb.storage;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import land.altea.allowdb.AllowDbPlugin;
import land.altea.allowdb.config.Config;
import land.altea.allowdb.storage.exception.AlreadyAllowedException;
import land.altea.allowdb.storage.exception.NoSuchProfileException;
import land.altea.allowdb.storage.exception.NotOnListException;
import land.altea.allowdb.storage.exception.StorageException;
import land.altea.allowdb.storage.model.AllowRecord;
import land.altea.allowdb.util.MojangAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public final class AllowList {
    private final @NotNull ConnectionSource conn;
    private final @NotNull Dao<AllowRecord, UUID> dao;
    private final @NotNull ExecutorService executor = Executors.newSingleThreadExecutor();

    public AllowList() {
        JdbcPooledConnectionSource conn;
        Dao<AllowRecord, UUID> dao;

        try {
            conn = new JdbcPooledConnectionSource(Config.getStorageUrl());
        } catch (SQLException e) {
            AllowDbPlugin a = AllowDbPlugin.getInstance();
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
            AllowDbPlugin a = AllowDbPlugin.getInstance();
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
            AllowDbPlugin a = AllowDbPlugin.getInstance();
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
                AllowDbPlugin.getInstance().getLogger().log(Level.SEVERE, "Failed to close connection source.", e);
            }
        }
    }

    public CompletableFuture<Void> add(@NotNull UUID uuid) {
        return CompletableFuture.runAsync(() -> {
            String nickname = MojangAPI.getNickname(uuid);
            if (nickname == null) {
                throw new CompletionException(new NoSuchProfileException());
            }

            try {
                createRecord(uuid, nickname);
            } catch (StorageException | AlreadyAllowedException e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    public CompletableFuture<Void> add(@NotNull String nickname) {
        return CompletableFuture.runAsync(() -> {
            UUID uuid = MojangAPI.getUuid(nickname);
            if (uuid == null) {
                throw new CompletionException(new NoSuchProfileException());
            }

            try {
                createRecord(uuid, nickname);
            } catch (StorageException | AlreadyAllowedException e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    private void createRecord(@NotNull UUID uuid, @NotNull String nickname) throws StorageException, AlreadyAllowedException {
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

    public CompletableFuture<Void> remove(@NotNull UUID uuid) {
        return CompletableFuture.runAsync(() -> {
            try {
                if (dao.deleteById(uuid) == 0) {
                    throw new CompletionException(new NotOnListException());
                }
            } catch (SQLException e) {
                throw new CompletionException(new StorageException("Failed to remove allow entry.", e));
            }
        }, executor);
    }

    public CompletableFuture<Void> remove(@NotNull String nickname) {
        Player player = AllowDbPlugin.getInstance().getServer().getOnlinePlayers().stream()
                .filter(p -> p.getName().equalsIgnoreCase(nickname)).findFirst().orElse(null);

        return CompletableFuture.runAsync(() -> {
            UUID uuid;
            if (player == null) {
                uuid = MojangAPI.getUuid(nickname);
                if (uuid == null) {
                    throw new CompletionException(new NoSuchProfileException());
                }
            } else {
                uuid = player.getUniqueId();
            }

            remove(uuid).join();
        });
    }
}
