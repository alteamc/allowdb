package land.altea.allowdb;

import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.logger.LoggerFactory;
import land.altea.allowdb.command.CommandAllowDb;
import land.altea.allowdb.config.Config;
import land.altea.allowdb.listener.LoginListener;
import land.altea.allowdb.storage.AllowList;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class AllowDB extends JavaPlugin {
    @SuppressWarnings("NotNullFieldNotInitialized")
    private static @NotNull AllowDB I;

    @SuppressWarnings("NotNullFieldNotInitialized")
    private @NotNull Config config;
    @SuppressWarnings("NotNullFieldNotInitialized")
    private @NotNull AllowList list;

    @Override
    public void onEnable() {
        I = this;

        reloadConfig();
        LoggerFactory.setLogBackendFactory(LogBackendType.NULL);
        list = new AllowList();

        PluginCommand command = Objects.requireNonNull(getCommand("allowdb"));
        CommandAllowDb executor = new CommandAllowDb();
        command.setExecutor(executor);
        command.setTabCompleter(executor);

        getServer().getPluginManager().registerEvents(new LoginListener(), this);
    }

    @Override
    public void onDisable() {
        //noinspection ConstantConditions
        if (list != null) {
            list.close();
        }
    }

    @Override
    public void reloadConfig() {
        saveDefaultConfig();
        super.reloadConfig();
        config = new Config(getConfig());
    }

    public void reload() {
        list.close();
        reloadConfig();
        list = new AllowList();
    }

    public static @NotNull AllowDB getInstance() {
        return I;
    }

    public @NotNull Config getPConfig() {
        return config;
    }

    public @NotNull AllowList getList() {
        return list;
    }
}
