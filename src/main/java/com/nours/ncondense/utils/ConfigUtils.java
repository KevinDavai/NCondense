package com.nours.ncondense.utils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class ConfigUtils {
    public static boolean isRecipeValid(ConfigurationSection recipeSection) {
        if(!recipeSection.isConfigurationSection("input-item") || !recipeSection.isConfigurationSection("output-item")) {
            return false; // Missing input-item or output-item section
        }

        ConfigurationSection inputItemSection = recipeSection.getConfigurationSection("input-item");
        ConfigurationSection outputItemSection = recipeSection.getConfigurationSection("output-item");

        if(!inputItemSection.contains("material") || !inputItemSection.contains("input-number")) {
            return false; // Missing material or input-number in input-item section
        }

        if(!outputItemSection.contains("material") || !outputItemSection.contains("output-number")) {
            return false; // Missing material or output-number in output-item section
        }

        if(!isValidMaterial(inputItemSection.getString("material")) || !isValidMaterial(outputItemSection.getString("material"))) {
            return false; // Invalid material name
        }


        if(inputItemSection.getInt("input-number") < 1 || outputItemSection.getInt("output-number") < 1) {
            return false; // Invalid input-number or output-number
        }

        return true;
    }

    public static boolean isCondenseBlockValid(ConfigurationSection blockSection, ConfigurationSection globalRecipesSection) {
        String materialStr = blockSection.getString("material");
        if(materialStr == null || !isValidMaterial(materialStr)) {
            return false; // Missing material
        }

        if(blockSection.contains("hologram")) {
            ConfigurationSection hologramSection = blockSection.getConfigurationSection("hologram");

            if(!hologramSection.contains("enabled") && !hologramSection.isBoolean("enabled")) {
                return false; // Missing enabled or text in hologram section
            }

            if(!hologramSection.contains("lines") || !hologramSection.isList("lines")) {
                return false; // Missing lines or invalid lines in hologram section
            }

            List<String> lines = hologramSection.getStringList("lines");
            if(lines.isEmpty()) {
                return false; // Empty lines
            }
        }

        // Check if the recipes section exists and contains the listed recipes
        if (blockSection.contains("recipes")) {
            List<String> blockRecipes = blockSection.getStringList("recipes");

            // Check if the global recipes section exists and contains the listed recipes
            for (String recipeKey : blockRecipes) {
                if (globalRecipesSection == null || !globalRecipesSection.contains(recipeKey)) {
                    return false; // Invalid recipe key in the condenser block
                }
            }
        }

        // all necessary checks passed, return true
        return true;
    }

    public static boolean isValidMaterial(String materialName) {
        try {
            Material.valueOf(materialName.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false; // Invalid material name
        }
    }

}
