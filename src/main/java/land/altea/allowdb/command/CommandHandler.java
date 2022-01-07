package land.altea.allowdb.command;

import land.altea.allowdb.command.exception.InsufficientPermissionsException;
import land.altea.allowdb.command.exception.InvalidUsageException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CommandHandler {
    void invoke(@NotNull CommandSender sender, @NotNull String[] args) throws InsufficientPermissionsException, InvalidUsageException;

    default @Nullable List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }

    default @Nullable String getUsage() {
        return null;
    }
}
