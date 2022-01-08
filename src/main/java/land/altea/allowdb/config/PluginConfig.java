package land.altea.allowdb.config;

import land.altea.allowdb.config.exception.BadConfigException;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class PluginConfig {
    private final @NotNull String locale;
    private final @NotNull String storageUrl;
    private final @NotNull String messageNotListed;

    public PluginConfig(@NotNull FileConfiguration conf) throws BadConfigException {
        try {
            this.locale = Objects.requireNonNull(conf.getString("locale"));
            this.storageUrl = Objects.requireNonNull(conf.getString("storage.url"));
            this.messageNotListed = Objects.requireNonNull(conf.getString("messages.not-listed"));
            Config.init(this);
        } catch (NullPointerException e) {
            throw new BadConfigException(e);
        }
    }

    public @NotNull String getLocale() {
        return locale;
    }

    public @NotNull String getStorageUrl() {
        return storageUrl;
    }

    public @NotNull String getMessageNotListed() {
        return messageNotListed;
    }
}
