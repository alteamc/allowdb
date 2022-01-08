package land.altea.allowdb.command;

import land.altea.allowdb.AllowDB;
import land.altea.allowdb.AllowDbPlugin;
import land.altea.allowdb.command.exception.InsufficientPermissionsException;
import land.altea.allowdb.command.exception.InvalidUsageException;
import land.altea.allowdb.command.util.CommandUtil;
import land.altea.allowdb.config.Messages;
import land.altea.allowdb.storage.exception.NoSuchProfileException;
import land.altea.allowdb.storage.exception.NotOnListException;
import land.altea.allowdb.storage.exception.StorageException;
import land.altea.allowdb.util.UuidUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class SubcommandRemove implements CommandHandler {
    @Override
    public void invoke(@NotNull CommandSender sender, @NotNull String[] args) throws InsufficientPermissionsException, InvalidUsageException {
        CommandUtil.requirePerm(sender, "allowdb.command.remove");
        CommandUtil.requireNArgs(args, 1);

        try {
            UUID uuid = UuidUtil.parseLeniently(args[0]);
            AllowDB.remove(uuid).whenComplete((unused, e) -> AllowDbPlugin.getInstance().getServer().getScheduler().runTask(AllowDbPlugin.getInstance(), () -> {
                if (e == null) {
                    sender.sendMessage(String.format(Messages.getPlayerUuidRemoved(), args[0]));
                } else {
                    if (e.getCause() instanceof StorageException) {
                        sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getInternalStorageError(), args[0])));
                    } else if (e.getCause() instanceof NoSuchProfileException) {
                        sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getPlayerUuidNonexistent(), args[0])));
                    } else if (e.getCause() instanceof NotOnListException) {
                        sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getPlayerUuidNotOnList(), args[0])));
                    }
                }
            }));
        } catch (IllegalArgumentException ignored) {
            AllowDB.remove(args[0]).whenComplete((unused, e) -> AllowDbPlugin.getInstance().getServer().getScheduler().runTask(AllowDbPlugin.getInstance(), () -> {
                if (e == null) {
                    sender.sendMessage(String.format(Messages.getPlayerNickRemoved(), args[0]));
                } else {
                    if (e.getCause() instanceof StorageException) {
                        sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getInternalStorageError(), args[0])));
                    } else if (e.getCause() instanceof NoSuchProfileException) {
                        sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getPlayerNickNonexistent(), args[0])));
                    } else if (e.getCause() instanceof NotOnListException) {
                        sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getPlayerNickNotOnList(), args[0])));
                    }
                }
            }));
        }

        sender.sendMessage(Messages.getAllowlistUpdatePending());
    }

    @Override
    public @NotNull String getUsage() {
        return Messages.getSubcommandRemoveUsage();
    }
}
