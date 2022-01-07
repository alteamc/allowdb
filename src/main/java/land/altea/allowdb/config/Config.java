package land.altea.allowdb.config;

import land.altea.allowdb.AllowDB;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public final class Config {
    private final @NotNull String storageUrl;
    private final @NotNull String messageNotListed;

    public Config(@NotNull FileConfiguration conf) {
        AllowDB a = AllowDB.getInstance();

        String storageUrl = conf.getString("storage.url");
        if (storageUrl == null) {
            a.getLogger().severe("storage.url is not set!");
            this.storageUrl = "";
        } else if (storageUrl.isBlank()) {
            a.getLogger().severe("storage.url is blank.");
            this.storageUrl = "";
        } else {
            this.storageUrl = storageUrl;
        }

        String messageNotListed = conf.getString("messages.not-listed");
        if (messageNotListed == null) {
            a.getLogger().severe("messages.not-listed is not set.");
            this.messageNotListed = "";
        } else if (messageNotListed.isBlank()) {
            a.getLogger().severe("messages.not-listed is blank.");
            this.messageNotListed = "";
        } else {
            this.messageNotListed = messageNotListed;
        }
    }

    public @NotNull String getStorageUrl() {
        return storageUrl;
    }

    public @NotNull String getMessageNotListed() {
        return messageNotListed;
    }
}
