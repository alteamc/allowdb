package land.altea.allowdb;

import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.logger.LoggerFactory;
import land.altea.allowdb.command.CommandAllowDb;
import land.altea.allowdb.config.Config;
import land.altea.allowdb.config.Messages;
import land.altea.allowdb.config.MessagesConfig;
import land.altea.allowdb.config.PluginConfig;
import land.altea.allowdb.config.exception.BadConfigException;
import land.altea.allowdb.listener.LoginListener;
import land.altea.allowdb.storage.AllowList;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Level;

public final class AllowDbPlugin extends JavaPlugin {
    @SuppressWarnings("NotNullFieldNotInitialized")
    private static @NotNull AllowDbPlugin I;

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

        AllowDB.init(list);
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

        try {
            Config.init(new PluginConfig(getConfig()));
            try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getResource("messages_" + Config.getLocale() + ".yml")))) {
                Messages.init(new MessagesConfig(YamlConfiguration.loadConfiguration(reader)));
            } catch (IOException e) {
                throw new BadConfigException("Failed to load messages file.", e);
            }
        } catch (BadConfigException e) {
            getLogger().log(Level.SEVERE, "Failed to load configuration files.", e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void reload() {
        list.close();
        reloadConfig();
        list = new AllowList();

        AllowDB.init(list);
    }

    public static @NotNull AllowDbPlugin getInstance() {
        return I;
    }
}
