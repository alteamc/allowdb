package land.altea.allowdb;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class AllowDB extends JavaPlugin {
    @SuppressWarnings("NotNullFieldNotInitialized")
    private static @NotNull AllowDB I;

    @Override
    public void onEnable() {
        I = this;
    }

    @Override
    public void onDisable() {
        //noinspection ConstantConditions
        I = null;
    }

    public static @NotNull AllowDB getInstance() {
        return I;
    }
}
