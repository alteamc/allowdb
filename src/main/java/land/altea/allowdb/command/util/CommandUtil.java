package land.altea.allowdb.command.util;

import land.altea.allowdb.command.exception.InsufficientPermissionsException;
import land.altea.allowdb.command.exception.InvalidUsageException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class CommandUtil {
    private CommandUtil() {

    }

    public static void requirePerm(@NotNull CommandSender sender, @NotNull String node) throws InsufficientPermissionsException {
        if (!sender.hasPermission(node)) {
            throw new InsufficientPermissionsException();
        }
    }

    public static void requireNArgs(@NotNull String[] args, int min) throws InvalidUsageException {
        if (args.length < min) {
            throw new InvalidUsageException();
        }
    }
}
