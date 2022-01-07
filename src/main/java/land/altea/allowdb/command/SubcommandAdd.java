package land.altea.allowdb.command;

import land.altea.allowdb.AllowDbPlugin;
import land.altea.allowdb.command.exception.InsufficientPermissionsException;
import land.altea.allowdb.command.exception.InvalidUsageException;
import land.altea.allowdb.command.util.CommandUtil;
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
                    AllowDbPlugin.getInstance().getList().add(uuid);
                    sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getPlayerUuidAdded(), args[0]));
                } catch (NoSuchProfileException e) {
                    sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getErrorMessageFormat(), String.format(AllowDbPlugin.getInstance().getMessages().getPlayerUuidNonexistent(), args[0])));
                } catch (AlreadyAllowedException e) {
                    sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getErrorMessageFormat(), String.format(AllowDbPlugin.getInstance().getMessages().getPlayerUuidAlreadyAdded(), args[0])));
                }
            } catch (IllegalArgumentException e) {
                try {
                    AllowDbPlugin.getInstance().getList().add(args[0]);
                    sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getPlayerNickAdded(), args[0]));
                } catch (NoSuchProfileException ex) {
                    sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getErrorMessageFormat(), String.format(AllowDbPlugin.getInstance().getMessages().getPlayerNickNonexistent(), args[0])));
                } catch (AlreadyAllowedException ex) {
                    sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getErrorMessageFormat(), String.format(AllowDbPlugin.getInstance().getMessages().getPlayerNickAlreadyAdded(), args[0])));
                }
            }

        } catch (StorageException e) {
            sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getErrorMessageFormat(), String.format(AllowDbPlugin.getInstance().getMessages().getInternalStorageError(), args[0])));
        }
    }

    @Override
    public @NotNull String getUsage() {
        return AllowDbPlugin.getInstance().getMessages().getSubcommandAddUsage();
    }
}
