package land.altea.allowdb.config;

import org.jetbrains.annotations.NotNull;

public final class Messages {
    private static MessagesConfig messagesConfig;

    private Messages() {

    }

    public static void init(@NotNull MessagesConfig _messagesConfig) {
        messagesConfig = _messagesConfig;
    }

    @NotNull
    public static String getErrorMessageFormat() {
        return messagesConfig.getErrorMessageFormat();
    }

    @NotNull
    public static String getAccountLookupFailure() {
        return messagesConfig.getAccountLookupFailure();
    }

    @NotNull
    public static String getPlayerUuidAdded() {
        return messagesConfig.getPlayerUuidAdded();
    }

    @NotNull
    public static String getPlayerUuidRemoved() {
        return messagesConfig.getPlayerUuidRemoved();
    }

    @NotNull
    public static String getPlayerUuidNotOnList() {
        return messagesConfig.getPlayerUuidNotOnList();
    }

    @NotNull
    public static String getPlayerUuidNonexistent() {
        return messagesConfig.getPlayerUuidNonexistent();
    }

    @NotNull
    public static String getPlayerUuidAlreadyAdded() {
        return messagesConfig.getPlayerUuidAlreadyAdded();
    }

    @NotNull
    public static String getPlayerNickAdded() {
        return messagesConfig.getPlayerNickAdded();
    }

    @NotNull
    public static String getPlayerNickRemoved() {
        return messagesConfig.getPlayerNickRemoved();
    }

    @NotNull
    public static String getPlayerNickNotOnList() {
        return messagesConfig.getPlayerNickNotOnList();
    }

    @NotNull
    public static String getPlayerNickNonexistent() {
        return messagesConfig.getPlayerNickNonexistent();
    }

    @NotNull
    public static String getPlayerNickAlreadyAdded() {
        return messagesConfig.getPlayerNickAlreadyAdded();
    }

    @NotNull
    public static String getInternalStorageError() {
        return messagesConfig.getInternalStorageError();
    }

    @NotNull
    public static String getSubcommandAddUsage() {
        return messagesConfig.getSubcommandAddUsage();
    }

    @NotNull
    public static String getSubcommandRemoveUsage() {
        return messagesConfig.getSubcommandRemoveUsage();
    }

    @NotNull
    public static String getCommandUsageArgs() {
        return messagesConfig.getCommandUsageArgs();
    }

    @NotNull
    public static String getCommandUsageNoArgs() {
        return messagesConfig.getCommandUsageNoArgs();
    }

    @NotNull
    public static String getAvailableCommands() {
        return messagesConfig.getAvailableCommands();
    }

    @NotNull
    public static String getConfigDbReloaded() {
        return messagesConfig.getConfigDbReloaded();
    }

    @NotNull
    public static String getReloadFailed() {
        return messagesConfig.getReloadFailed();
    }

    @NotNull
    public static String getInsufficientPermissions() {
        return messagesConfig.getInsufficientPermissions();
    }

    @NotNull
    public static String getCommandUsageParent() {
        return messagesConfig.getCommandUsageParent();
    }

    @NotNull
    public static String getCommandUsageParentHint() {
        return messagesConfig.getCommandUsageParentHint();
    }

    @NotNull
    public static String getAllowlistUpdatePending() {
        return messagesConfig.getAllowlistUpdatePending();
    }
}
