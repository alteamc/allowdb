package land.altea.allowdb.command;

import land.altea.allowdb.AllowDB;
import land.altea.allowdb.command.exception.InsufficientPermissionsException;
import land.altea.allowdb.command.exception.InvalidUsageException;
import land.altea.allowdb.command.util.CommandUtil;
import land.altea.allowdb.util.UuidUtil;
import land.altea.allowdb.storage.exception.NoSuchProfileException;
import land.altea.allowdb.storage.exception.StorageException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class SubcommandRemove implements CommandHandler {
    @Override
    public void invoke(@NotNull CommandSender sender, @NotNull String[] args) throws InsufficientPermissionsException, InvalidUsageException {
        CommandUtil.requirePerm(sender, "allowdb.command.remove");
        CommandUtil.requireNArgs(args, 1);

        try {
            try {
                UUID uuid = UuidUtil.parseLeniently(args[0]);

                try {
                    AllowDB.getInstance().getList().remove(uuid);
                    sender.sendMessage("Player with UUID " + args[0] + " is no longer allowed in.");
                } catch (NoSuchProfileException e) {
                    sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "player with UUID " + args[0] + " is not on the allowlist.");
                }
            } catch (IllegalArgumentException e) {
                try {
                    AllowDB.getInstance().getList().remove(args[0]);
                    sender.sendMessage("Player " + args[0] + " is no longer allowed in.");
                } catch (NoSuchProfileException ex) {
                    sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "player " + args[0] + " is not on the allowlist.");
                }
            }

        } catch (StorageException e) {
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "internal storage failure.");
        }
    }

    @Override
    public @NotNull String getUsage() {
        return "<username or UUID>";
    }
}
