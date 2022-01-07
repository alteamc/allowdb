package land.altea.allowdb.config;

import land.altea.allowdb.AllowDB;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public final class PluginConfig {
    private final @NotNull String locale;
    private final @NotNull String storageUrl;
    private final @NotNull String messageNotListed;

    public PluginConfig(@NotNull FileConfiguration conf) {
        AllowDB a = AllowDB.getInstance();

        String locale = conf.getString("locale");
        if (locale == null) {
            a.getLogger().severe("locale is not set!");
            this.locale = "en";
        } else if (locale.isBlank()) {
            a.getLogger().severe("locale is blank.");
            this.locale = "en";
        } else {
            this.locale = locale;
        }

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

    public String getLocale() {
        return locale;
    }

    public @NotNull String getStorageUrl() {
        return storageUrl;
    }

    public @NotNull String getMessageNotListed() {
        return messageNotListed;
    }
}
