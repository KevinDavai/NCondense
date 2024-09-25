package com.nours.ncondense.config;

import com.nours.ncondense.NCondense;

import java.io.File;
import java.util.logging.Level;

public class ConfigsManagerImpl {
    private final NCondense plugin;
    private CommentedConfiguration config;

    public ConfigsManagerImpl(NCondense plugin) {
        this.plugin = plugin;
    }

    public void loadData() {
        File file = new File(plugin.getDataFolder(), "config.yml");

        if(!file.exists()) {
            plugin.saveResource("config.yml", false);
        }

        CommentedConfiguration config = CommentedConfiguration.loadConfiguration(file);
        this.config = config;

        try {
            config.syncWithConfig(file, plugin.getResource("config.yml"));
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error while syncing the config file", e);
        }
    }

    public void updateValue(String key, Object value) {
        // Update the value of the key in the config
    }

    public CommentedConfiguration getConfig() {
        return config;
    }

    public String getTestDev() {
        return config.getString("test-dev", "test");
    }
}
