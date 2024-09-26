package com.nours.ncondense.utils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

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

    public static boolean isValidMaterial(String materialName) {
        try {
            Material material = Material.valueOf(materialName.toUpperCase());
            return material != null;
        } catch (IllegalArgumentException e) {
            return false; // Invalid material name
        }
    }
}
