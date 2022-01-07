package land.altea.allowdb.config;

import org.jetbrains.annotations.NotNull;

public final class Config {
    private static PluginConfig pluginConfig;

    private Config() {

    }

    public static void init(@NotNull PluginConfig _pluginConfig) {
        pluginConfig = _pluginConfig;
    }

    @NotNull
    public static String getLocale() {
        return pluginConfig.getLocale();
    }

    @NotNull
    public static String getStorageUrl() {
        return pluginConfig.getStorageUrl();
    }

    @NotNull
    public static String getMessageNotListed() {
        return pluginConfig.getMessageNotListed();
    }
}
