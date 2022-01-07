package land.altea.allowdb.util;

import land.altea.allowdb.AllowDbPlugin;
import me.kbrewster.exceptions.APIException;
import me.kbrewster.exceptions.InvalidPlayerException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public final class MojangAPI {
    private MojangAPI() {

    }

    public static @Nullable UUID getUuid(@NotNull String username) {
        try {
            return me.kbrewster.mojangapi.MojangAPI.getUUID(username);
        } catch (InvalidPlayerException e) {
            return null;
        } catch (IOException | APIException e) {
            AllowDbPlugin.getInstance().getLogger().log(Level.SEVERE, "Failed to request UUID from Mojang API.", e);
            return null;
        }
    }

    public static @Nullable String getNickname(@NotNull UUID uuid) {
        try {
            return me.kbrewster.mojangapi.MojangAPI.getUsername(uuid);
        } catch (InvalidPlayerException e) {
            return null;
        } catch (IOException | APIException e) {
            AllowDbPlugin.getInstance().getLogger().log(Level.SEVERE, "Failed to request username from Mojang API.", e);
            return null;
        }
    }
}