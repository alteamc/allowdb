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
        sender.sendMessage("AllowDB configuration and database reloaded.");
    }

    @Override
    public @NotNull List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
