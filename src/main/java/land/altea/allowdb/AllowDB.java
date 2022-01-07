package land.altea.allowdb;

import land.altea.allowdb.config.MessagesConfig;
import land.altea.allowdb.storage.AllowList;
import land.altea.allowdb.storage.exception.AlreadyAllowedException;
import land.altea.allowdb.storage.exception.NoSuchProfileException;
import land.altea.allowdb.storage.exception.StorageException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class AllowDB {
    private static AllowList allowList;

    private AllowDB() {

    }

    void init(@NotNull AllowList _allowList) {
        allowList = _allowList;
    }

    public static void add(@NotNull UUID uuid) throws StorageException, NoSuchProfileException, AlreadyAllowedException {
        allowList.add(uuid);
    }

    public static void add(@NotNull String nickname) throws StorageException, NoSuchProfileException, AlreadyAllowedException {
        allowList.add(nickname);
    }

    public static boolean isAllowed(@NotNull Player player) throws StorageException {
        return allowList.isAllowed(player);
    }

    public static void remove(@NotNull UUID uuid) throws StorageException, NoSuchProfileException {
        allowList.remove(uuid);
    }

    public static void remove(@NotNull String nickname) throws StorageException, NoSuchProfileException {
        allowList.remove(nickname);
    }
}
