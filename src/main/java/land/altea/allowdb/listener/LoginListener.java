package land.altea.allowdb.listener;

import land.altea.allowdb.AllowDbPlugin;
import land.altea.allowdb.storage.exception.StorageException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public final class LoginListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    void onPlayerLogin(@NotNull PlayerLoginEvent e) {
        try {
            if (!AllowDbPlugin.getInstance().getList().isAllowed(e.getPlayer())) {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, AllowDbPlugin.getInstance().getPluginConfig().getMessageNotListed());
            }
        } catch (StorageException ex) {
            AllowDbPlugin.getInstance().getLogger().log(Level.SEVERE, "Failed to lookup account in the allowlist.", e);
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, AllowDbPlugin.getInstance().getMessages().getAccountLookupFailure());
        }
    }
}
