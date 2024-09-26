package com.nours.ncondense;

import com.nours.ncondense.commands.NCondenseCommandManager;
import com.nours.ncondense.config.ConfigsManagerImpl;
import com.nours.ncondense.recipes.RecipeManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class NCondense extends JavaPlugin {

    private final ConfigsManagerImpl configsManager = new ConfigsManagerImpl(this);
    private final NCondenseCommandManager commandManager = new NCondenseCommandManager(this);
    private final RecipeManager recipeManager = new RecipeManager(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Mitsuki le fdp");

        configsManager.loadData();
        commandManager.loadCommand();

        recipeManager.loadRecipes();
    }


    public void reloadPlugin() {
        configsManager.loadData();
        recipeManager.loadRecipes();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ConfigsManagerImpl getConfigsManager() {
        return configsManager;
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }
}
