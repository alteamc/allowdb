package land.altea.allowdb.listener;

import land.altea.allowdb.AllowDB;
import land.altea.allowdb.storage.exception.StorageException;
import org.bukkit.ChatColor;
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
            if (!AllowDB.getInstance().getList().isAllowed(e.getPlayer())) {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, AllowDB.getInstance().getPConfig().getMessageNotListed());
            }
        } catch (StorageException ex) {
            AllowDB.getInstance().getLogger().log(Level.SEVERE, "Failed to lookup account in the allowlist.", e);
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, ChatColor.RED + "Couldn't lookup your account in the allowlist. Contact server administrator.");
        }
    }
}
