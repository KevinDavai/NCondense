package com.nours.ncondense.config;

import com.nours.ncondense.NCondense;
import com.nours.ncondense.recipes.RecipeModel;
import com.nours.ncondense.utils.ConfigUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;


import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ConfigsManagerImpl {
    private final NCondense plugin;
    private CommentedConfiguration config;

    private static final String[] IGNORED_SECTIONS = new String[] {
            "recipes"
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
    }

    public void updateValue(String key, Object value) {
        // Update the value of the key in the config
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
            String inputName = inputItemSection.getString("name");
            int inputNumber = inputItemSection.getInt("input-number");

            Material outputMaterial = Material.getMaterial(outputItemSection.getString("material"));
            String outputName = outputItemSection.getString("name");
            int outputNumber = outputItemSection.getInt("output-number");

            String permission = recipeSection.getString("permission");
            boolean isAutoCondensable = recipeSection.getBoolean("is-auto-condensable");

            RecipeModel recipe = new RecipeModel(recipeKey, inputMaterial, inputName, inputNumber, outputNumber, outputMaterial, outputName, permission, isAutoCondensable);
            recipes.add(recipe);
        }

        return recipes;
    }

}
