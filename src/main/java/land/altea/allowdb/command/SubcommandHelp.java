package land.altea.allowdb.command;

import land.altea.allowdb.command.exception.InsufficientPermissionsException;
import land.altea.allowdb.command.util.CommandUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class SubcommandHelp implements CommandHandler {
    private final @NotNull Set<String> subcommands;

    public SubcommandHelp(@NotNull CommandAllowDb parent) {
        this.subcommands = parent.getSubcommands();
    }

    @Override
    public void invoke(@NotNull CommandSender sender, @NotNull String[] args) throws InsufficientPermissionsException {
        CommandUtil.requirePerm(sender, "allowdb.command.help");
        sender.sendMessage("Available commands: " + String.join(", ", subcommands) + ".");
    }
}
