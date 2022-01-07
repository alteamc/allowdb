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
                    sender.sendMessage(String.format(AllowDB.getInstance().getMessages().getPlayerUuidRemoved(), args[0]));
                } catch (NoSuchProfileException e) {
                    sender.sendMessage(String.format(AllowDB.getInstance().getMessages().getErrorMessageFormat(), String.format(AllowDB.getInstance().getMessages().getPlayerUuidNotOnList(), args[0])));
                }
            } catch (IllegalArgumentException e) {
                try {
                    AllowDB.getInstance().getList().remove(args[0]);
                    sender.sendMessage(String.format(AllowDB.getInstance().getMessages().getPlayerNickRemoved(), args[0]));
                } catch (NoSuchProfileException ex) {
                    sender.sendMessage(String.format(AllowDB.getInstance().getMessages().getErrorMessageFormat(), String.format(AllowDB.getInstance().getMessages().getPlayerNickNotOnList(), args[0])));
                }
            }

        } catch (StorageException e) {
            sender.sendMessage(String.format(AllowDB.getInstance().getMessages().getErrorMessageFormat(), String.format(AllowDB.getInstance().getMessages().getInternalStorageError(), args[0])));
        }
    }

    @Override
    public @NotNull String getUsage() {
        return AllowDB.getInstance().getMessages().getSubcommandRemoveUsage();
    }
}
