package land.altea.allowdb.command;

import land.altea.allowdb.AllowDB;
import land.altea.allowdb.AllowDbPlugin;
import land.altea.allowdb.command.exception.InsufficientPermissionsException;
import land.altea.allowdb.command.exception.InvalidUsageException;
import land.altea.allowdb.command.util.CommandUtil;
import land.altea.allowdb.config.Messages;
import land.altea.allowdb.storage.AllowList;
import land.altea.allowdb.storage.exception.AlreadyAllowedException;
import land.altea.allowdb.storage.exception.NoSuchProfileException;
import land.altea.allowdb.storage.exception.StorageException;
import land.altea.allowdb.util.UuidUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class SubcommandAdd implements CommandHandler {
    @Override
    public void invoke(@NotNull CommandSender sender, @NotNull String[] args) throws InsufficientPermissionsException, InvalidUsageException {
        CommandUtil.requirePerm(sender, "allowdb.command.add");
        CommandUtil.requireNArgs(args, 1);

        try {
            try {
                UUID uuid = UuidUtil.parseLeniently(args[0]);

                try {
                    AllowDB.add(uuid);
                    sender.sendMessage(String.format(Messages.getPlayerUuidAdded(), args[0]));
                } catch (NoSuchProfileException e) {
                    sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getPlayerUuidNonexistent(), args[0])));
                } catch (AlreadyAllowedException e) {
                    sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getPlayerUuidAlreadyAdded(), args[0])));
                }
            } catch (IllegalArgumentException e) {
                try {
                    AllowDB.add(args[0]);
                    sender.sendMessage(String.format(Messages.getPlayerNickAdded(), args[0]));
                } catch (NoSuchProfileException ex) {
                    sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getPlayerNickNonexistent(), args[0])));
                } catch (AlreadyAllowedException ex) {
                    sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getPlayerNickAlreadyAdded(), args[0])));
                }
            }

        } catch (StorageException e) {
            sender.sendMessage(String.format(Messages.getErrorMessageFormat(), String.format(Messages.getInternalStorageError(), args[0])));
        }
    }

    @Override
    public @NotNull String getUsage() {
        return Messages.getSubcommandAddUsage();
    }
}
