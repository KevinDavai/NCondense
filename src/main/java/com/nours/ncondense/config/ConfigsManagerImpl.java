package com.nours.ncondense.config;

import com.nours.ncondense.NCondense;
import com.nours.ncondense.condenseblocks.CondenseBlockModel;
import com.nours.ncondense.recipes.RecipeModel;
import com.nours.ncondense.utils.ConfigUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ConfigsManagerImpl {
    private final NCondense plugin;
    private CommentedConfiguration config;

    private boolean autoCondenseEnabled;
    private int autoCondenseInterval;
    private int minItemsToCondense;
    private List<String> blacklistedWorlds;

    private static final String[] IGNORED_SECTIONS = new String[] {
            "recipes",
            "condenser-block"
    };

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
            config.syncWithConfig(file, plugin.getResource("config.yml"), IGNORED_SECTIONS);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error while syncing the config file", e);
        }

        loadConfigValues();
    }

    private void loadConfigValues() {
        autoCondenseEnabled = config.getBoolean("auto-condense.enabled", true);
        autoCondenseInterval = config.getInt("auto-condense.interval", 1);
        minItemsToCondense = config.getInt("auto-condense.min-items", 0);
        blacklistedWorlds = config.getStringList("auto-condense.blacklisted-worlds");
    }

    public CommentedConfiguration getConfig() {
        return config;
    }

    public List<RecipeModel> getRecipes() {
        List<RecipeModel> recipes = new ArrayList<>();

        ConfigurationSection recipesSection = config.getConfigurationSection("recipes");

        if(recipesSection == null) {
            plugin.getLogger().warning("No recipes found in the config");
            return recipes;
        }

        for(String recipeKey : recipesSection.getKeys(false)) {

            ConfigurationSection recipeSection = config.getConfigurationSection("recipes." + recipeKey);

            if(!ConfigUtils.isRecipeValid(recipeSection)) {
                plugin.getLogger().warning("Invalid recipe, please refer to the documentation for the correct format: " + recipeKey);
                continue;
            }

            ConfigurationSection inputItemSection = recipeSection.getConfigurationSection("input-item");
            ConfigurationSection outputItemSection = recipeSection.getConfigurationSection("output-item");

            Material inputMaterial =  Material.getMaterial(inputItemSection.getString("material"));
            String inputName = inputItemSection.getString("display-name");
            int inputNumber = inputItemSection.getInt("input-number");

            Material outputMaterial = Material.getMaterial(outputItemSection.getString("material"));
            String outputName = outputItemSection.getString("display-name");
            int outputNumber = outputItemSection.getInt("output-number");

            String permission = recipeSection.getString("permission");
            boolean isAutoCondensable = recipeSection.getBoolean("is-auto-condensable");

            RecipeModel recipe = new RecipeModel(plugin, recipeKey, inputMaterial, inputName, inputNumber, outputNumber, outputMaterial, outputName, permission, isAutoCondensable);
            recipes.add(recipe);
        }

        return recipes;
    }

    public List<CondenseBlockModel> getCondenseBlocks() {
        List<CondenseBlockModel> condenseBlocks = new ArrayList<>();

        ConfigurationSection condenseBlocksSection = config.getConfigurationSection("condenser-block");
        ConfigurationSection globalRecipesSection = config.getConfigurationSection("recipes");

        if(condenseBlocksSection == null) {
            plugin.getLogger().warning("No condenser blocks found in the config");
            return condenseBlocks;
        }

        for(String blockKey : condenseBlocksSection.getKeys(false)) {
            ConfigurationSection blockSection = config.getConfigurationSection("condenser-block." + blockKey);

            if(!ConfigUtils.isCondenseBlockValid(blockSection, globalRecipesSection)) {
                plugin.getLogger().warning("Invalid condenser block, please refer to the documentation for the correct format: " + blockKey);
                continue;
            }

            Material material = Material.getMaterial(blockSection.getString("material"));

            // display name is optional
            String displayName = blockSection.getString("display-name", null);

            // lore is optional, default to an empty list
            List<String> lore = blockSection.getStringList("lore");

            // custom model data is optional, default to 0
            int customModelData = blockSection.getInt("custom-model-data", 0);

            // permission is optional
            String permission = blockSection.getString("permission", null);

            // recipes are optional, default to an empty list
            List<String> recipes = blockSection.getStringList("recipes");

            // Hologram but guaranteed valid if present
            boolean hologramEnabled = blockSection.getBoolean("hologram.enabled");
            List<String> hologramLines = new ArrayList<>();

            if(hologramEnabled) {
                hologramLines = blockSection.getStringList("hologram.lines");
            }

            CondenseBlockModel condenseBlock = new CondenseBlockModel(
                    blockKey,
                    material,
                    displayName,
                    lore,
                    customModelData,
                    permission,
                    recipes,
                    hologramEnabled,
                    hologramLines
            );

            condenseBlocks.add(condenseBlock);
        }

        return condenseBlocks;
    }

    public boolean isAutoCondenseEnabled() {
        return autoCondenseEnabled;
    }

    public int getAutoCondenseInterval() {
        return autoCondenseInterval;
    }

    public int getMinItemsToCondense() {
        return minItemsToCondense;
    }

    public List<String> getBlacklistedWorlds() {
        return blacklistedWorlds;
    }
}
