package land.altea.allowdb.command;

import land.altea.allowdb.AllowDbPlugin;
import land.altea.allowdb.command.exception.InsufficientPermissionsException;
import land.altea.allowdb.command.exception.InvalidUsageException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public final class CommandAllowDb implements TabExecutor {
    private final @NotNull Map<String, CommandHandler> subcommandHandlers = new HashMap<>();
    private final @NotNull Set<String> subcommands = new HashSet<>();

    public CommandAllowDb() {
        addSubcommand(Arrays.asList("h", "help"), new SubcommandHelp(this));
        addSubcommand(Arrays.asList("rl", "rel", "reload"), new SubcommandReload());
        addSubcommand(Arrays.asList("a", "add"), new SubcommandAdd());
        addSubcommand(Arrays.asList("r", "rm", "rem", "remove"), new SubcommandRemove());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            CommandHandler handler = subcommandHandlers.get(args[0].toLowerCase(Locale.ENGLISH));
            if (handler != null) {
                try {
                    handler.invoke(sender, Arrays.copyOfRange(args, 1, args.length));
                } catch (InsufficientPermissionsException e) {
                    sender.sendMessage(AllowDbPlugin.getInstance().getMessages().getInsufficientPermissions());
                } catch (InvalidUsageException e) {
                    String usage = handler.getUsage();
                    if (usage != null) {
                        sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getCommandUsageArgs(), label, args[0], usage));
                    } else {
                        sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getCommandUsageNoArgs(), label, args[0]));
                    }
                }

                return true;
            }
        }

        sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getCommandUsageParent(), label));
        sender.sendMessage(String.format(AllowDbPlugin.getInstance().getMessages().getCommandUsageParentHint(), label));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return subcommands.stream()
                    .filter(s -> s.toLowerCase(Locale.ENGLISH).startsWith(args[0].toLowerCase(Locale.ENGLISH)))
                    .collect(Collectors.toList());
        }

        CommandHandler handler = subcommandHandlers.get(args[0].toLowerCase(Locale.ENGLISH));
        if (handler != null) {
            return handler.complete(sender, args);
        }

        return null;
    }

    private void addSubcommand(@NotNull List<String> names, @NotNull CommandHandler handler) {
        for (String name : names) {
            subcommandHandlers.put(name, handler);
        }

        String name = names.stream().max(Comparator.comparingInt(String::length)).orElse(null);
        subcommands.add(name);
    }

    public @NotNull Set<String> getSubcommands() {
        return subcommands;
    }
}
