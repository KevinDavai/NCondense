package com.nours.ncondense;

import com.nours.ncondense.commands.NCondenseCommandManager;
import com.nours.ncondense.config.ConfigsManagerImpl;
import com.nours.ncondense.player.NPlayerManager;
import com.nours.ncondense.recipes.RecipeManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NCondense extends JavaPlugin {

    private final ConfigsManagerImpl configsManager = new ConfigsManagerImpl(this);
    private final NCondenseCommandManager commandManager = new NCondenseCommandManager(this);
    private final RecipeManager recipeManager = new RecipeManager(this);
    private final NPlayerManager nPlayerManager = new NPlayerManager(this);

    @Override
    public void onEnable() {
        this.configsManager.loadData();
        this.commandManager.loadCommand();

        this.recipeManager.loadRecipes();

        this.nPlayerManager.startAutoCondenseTask();
    }

    public void reloadPlugin() {
        this.nPlayerManager.stopAutoCondenseTask();

        this.configsManager.loadData();
        this.recipeManager.loadRecipes();

        this.nPlayerManager.startAutoCondenseTask();
    }

    @Override
    public void onDisable() {
        this.nPlayerManager.stopAutoCondenseTask();
    }

    public ConfigsManagerImpl getConfigsManager() {
        return this.configsManager;
    }

    public RecipeManager getRecipeManager() {
        return this.recipeManager;
    }

    public NPlayerManager getNPlayerManager() {
        return this.nPlayerManager;
    }


}
