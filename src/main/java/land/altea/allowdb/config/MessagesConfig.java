package land.altea.allowdb.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class MessagesConfig {
    private final @NotNull String errorMessageFormat;
    private final @NotNull String accountLookupFailure;
    private final @NotNull String playerUuidAdded;
    private final @NotNull String playerUuidRemoved;
    private final @NotNull String playerUuidNotOnList;
    private final @NotNull String playerUuidNonexistent;
    private final @NotNull String playerUuidAlreadyAdded;
    private final @NotNull String playerNickAdded;
    private final @NotNull String playerNickRemoved;
    private final @NotNull String playerNickNotOnList;
    private final @NotNull String playerNickNonexistent;
    private final @NotNull String playerNickAlreadyAdded;
    private final @NotNull String internalStorageError;
    private final @NotNull String subcommandAddUsage;
    private final @NotNull String subcommandRemoveUsage;
    private final @NotNull String availableCommands;
    private final @NotNull String configDbReloaded;
    private final @NotNull String reloadFailed;
    private final @NotNull String insufficientPermissions;
    private final @NotNull String commandUsageArgs;
    private final @NotNull String commandUsageNoArgs;
    private final @NotNull String commandUsageParent;
    private final @NotNull String commandUsageParentHint;

    public MessagesConfig(@NotNull FileConfiguration conf) {
        this.errorMessageFormat = Objects.requireNonNull(conf.getString("error-message-format"));
        this.accountLookupFailure = Objects.requireNonNull(conf.getString("account-lookup-failure"));
        this.playerUuidAdded = Objects.requireNonNull(conf.getString("player-uuid-added"));
        this.playerUuidRemoved = Objects.requireNonNull(conf.getString("player-uuid-removed"));
        this.playerUuidNotOnList = Objects.requireNonNull(conf.getString("player-uuid-not-on-list"));
        this.playerUuidNonexistent = Objects.requireNonNull(conf.getString("player-uuid-nonexistent"));
        this.playerUuidAlreadyAdded = Objects.requireNonNull(conf.getString("player-uuid-already-added"));
        this.playerNickAdded = Objects.requireNonNull(conf.getString("player-nick-added"));
        this.playerNickRemoved = Objects.requireNonNull(conf.getString("player-nick-removed"));
        this.playerNickNotOnList = Objects.requireNonNull(conf.getString("player-nick-not-on-list"));
        this.playerNickNonexistent = Objects.requireNonNull(conf.getString("player-nick-nonexistent"));
        this.playerNickAlreadyAdded = Objects.requireNonNull(conf.getString("player-nick-already-added"));
        this.internalStorageError = Objects.requireNonNull(conf.getString("internal-storage-error"));
        this.subcommandAddUsage = Objects.requireNonNull(conf.getString("subcommand-add-usage"));
        this.subcommandRemoveUsage = Objects.requireNonNull(conf.getString("subcommand-remove-usage"));
        this.availableCommands = Objects.requireNonNull(conf.getString("available-commands"));
        this.configDbReloaded = Objects.requireNonNull(conf.getString("config-db-reloaded"));
        this.reloadFailed = Objects.requireNonNull(conf.getString("reload-failed"));
        this.insufficientPermissions = Objects.requireNonNull(conf.getString("insufficient-permissions"));
        this.commandUsageArgs = Objects.requireNonNull(conf.getString("command-usage-args"));
        this.commandUsageNoArgs = Objects.requireNonNull(conf.getString("command-usage-no-args"));
        this.commandUsageParent = Objects.requireNonNull(conf.getString("command-usage-parent"));
        this.commandUsageParentHint = Objects.requireNonNull(conf.getString("command-usage-parent-hint"));
    }

    public @NotNull String getErrorMessageFormat() {
        return errorMessageFormat;
    }

    public @NotNull String getAccountLookupFailure() {
        return accountLookupFailure;
    }

    public @NotNull String getPlayerUuidAdded() {
        return playerUuidAdded;
    }

    public @NotNull String getPlayerUuidRemoved() {
        return playerUuidRemoved;
    }

    public @NotNull String getPlayerUuidNotOnList() {
        return playerUuidNotOnList;
    }

    public @NotNull String getPlayerUuidNonexistent() {
        return playerUuidNonexistent;
    }

    public @NotNull String getPlayerUuidAlreadyAdded() {
        return playerUuidAlreadyAdded;
    }

    public @NotNull String getPlayerNickAdded() {
        return playerNickAdded;
    }

    public @NotNull String getPlayerNickRemoved() {
        return playerNickRemoved;
    }

    public @NotNull String getPlayerNickNotOnList() {
        return playerNickNotOnList;
    }

    public @NotNull String getPlayerNickNonexistent() {
        return playerNickNonexistent;
    }

    public @NotNull String getPlayerNickAlreadyAdded() {
        return playerNickAlreadyAdded;
    }

    public @NotNull String getInternalStorageError() {
        return internalStorageError;
    }

    public @NotNull String getSubcommandAddUsage() {
        return subcommandAddUsage;
    }

    public @NotNull String getSubcommandRemoveUsage() {
        return subcommandRemoveUsage;
    }

    public @NotNull String getCommandUsageArgs() {
        return commandUsageArgs;
    }

    public @NotNull String getCommandUsageNoArgs() {
        return commandUsageNoArgs;
    }

    public @NotNull String getAvailableCommands() {
        return availableCommands;
    }

    public @NotNull String getConfigDbReloaded() {
        return configDbReloaded;
    }

    public @NotNull String getReloadFailed() {
        return reloadFailed;
    }

    public @NotNull String getInsufficientPermissions() {
        return insufficientPermissions;
    }

    public @NotNull String getCommandUsageParent() {
        return commandUsageParent;
    }

    public @NotNull String getCommandUsageParentHint() {
        return commandUsageParentHint;
    }
}
