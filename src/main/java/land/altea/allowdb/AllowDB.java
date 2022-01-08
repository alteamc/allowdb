package land.altea.allowdb;

import land.altea.allowdb.storage.AllowList;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class AllowDB {
    private static AllowList allowList;

    private AllowDB() {

    }

    static void init(@NotNull AllowList _allowList) {
        allowList = _allowList;
    }

    public static CompletableFuture<Void> add(@NotNull UUID uuid) {
        return allowList.add(uuid);
    }

    public static CompletableFuture<Void> add(@NotNull String nickname) {
        return allowList.add(nickname);
    }

    public static CompletableFuture<Boolean> isAllowed(@NotNull Player player) {
        return allowList.isAllowed(player);
    }

    public static CompletableFuture<Void> remove(@NotNull UUID uuid) {
        return allowList.remove(uuid);
    }

    public static CompletableFuture<Void> remove(@NotNull String nickname) {
        return allowList.remove(nickname);
    }
}
