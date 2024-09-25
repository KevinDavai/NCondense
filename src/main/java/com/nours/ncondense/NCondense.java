package com.nours.ncondense;

import com.nours.ncondense.config.ConfigsManagerImpl;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class NCondense extends JavaPlugin {

    private final ConfigsManagerImpl configsManager = new ConfigsManagerImpl(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Mitsuki le fdp");


        configsManager.loadData();


        startRepeatingTask();
    }


    public void reloadPlugin() {
        configsManager.loadData();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void startRepeatingTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                getLogger().info(configsManager.getTestDev());
            }
        }.runTaskTimer(this, 0L, 20L);
    }
}
