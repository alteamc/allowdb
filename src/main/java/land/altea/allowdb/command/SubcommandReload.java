package land.altea.allowdb.command;

import land.altea.allowdb.AllowDbPlugin;
import land.altea.allowdb.command.exception.InsufficientPermissionsException;
import land.altea.allowdb.command.util.CommandUtil;
import land.altea.allowdb.config.Messages;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public final class SubcommandReload implements CommandHandler {
    @Override
    public void invoke(@NotNull CommandSender sender, @NotNull String[] args) throws InsufficientPermissionsException {
        CommandUtil.requirePerm(sender, "allowdb.command.reload");
        AllowDbPlugin.getInstance().reload();
        // todo: this is a hack, need to make something fancier than this
        if (!AllowDbPlugin.getInstance().isEnabled()) {
            sender.sendMessage(Messages.getReloadFailed());
        } else {
            sender.sendMessage(Messages.getConfigDbReloaded());
        }
    }

    @Override
    public @NotNull List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
