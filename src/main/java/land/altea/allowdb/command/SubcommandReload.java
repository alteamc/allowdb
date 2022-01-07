package land.altea.allowdb.command;

import land.altea.allowdb.AllowDB;
import land.altea.allowdb.command.exception.InsufficientPermissionsException;
import land.altea.allowdb.command.util.CommandUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public final class SubcommandReload implements CommandHandler {
    @Override
    public void invoke(@NotNull CommandSender sender, @NotNull String[] args) throws InsufficientPermissionsException {
        CommandUtil.requirePerm(sender, "allowdb.command.reload");
        AllowDB.getInstance().reload();
        // todo: this is a hack, need to make something fancier than this
        if (!AllowDB.getInstance().isEnabled()) {
            sender.sendMessage(AllowDB.getInstance().getMessages().getReloadFailed());
        } else {
            sender.sendMessage(AllowDB.getInstance().getMessages().getConfigDbReloaded());
        }
    }

    @Override
    public @NotNull List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
