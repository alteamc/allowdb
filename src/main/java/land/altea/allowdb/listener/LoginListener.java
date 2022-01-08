package land.altea.allowdb.listener;

import land.altea.allowdb.AllowDB;
import land.altea.allowdb.AllowDbPlugin;
import land.altea.allowdb.config.Config;
import land.altea.allowdb.config.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

public final class LoginListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    void onPlayerLogin(@NotNull PlayerLoginEvent e) {
        try {
            if (!AllowDB.isAllowed(e.getPlayer()).get(3, TimeUnit.SECONDS)) {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Config.getMessageNotListed());
            }
        } catch (InterruptedException ex) {
            AllowDbPlugin.getInstance().getLogger().log(Level.SEVERE, "Account lookup interrupted.", e);
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Messages.getAccountLookupFailure());
        } catch (ExecutionException ex) {
            AllowDbPlugin.getInstance().getLogger().log(Level.SEVERE, "Failed to lookup account in the allowlist.", e);
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Messages.getAccountLookupFailure());
        } catch (TimeoutException ex) {
            AllowDbPlugin.getInstance().getLogger().log(Level.SEVERE, "Took too long to lookup account in the allowlist.", e);
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Messages.getAccountLookupFailure());
        }
    }
}
