package com.nours.ncondense.condenseblocks;

import org.bukkit.Material;

import java.util.List;

public class CondenseBlockModel {
    private String key;
    private Material material;
    private String displayName; // Optional
    private List<String> lore; // Optional
    private int customModelData; // Optional
    private String permission; // Optional
    private List<String> recipes; // Optional
    private boolean hologramEnabled; // Optional
    private List<String> hologramLines; // Optional

    public CondenseBlockModel(String key, Material material) {
        this.key = key;
        this.material = material;
    }

    public CondenseBlockModel(String key, Material material, String displayName, List<String> lore, int customModelData, String permission, List<String> recipes, boolean hologramEnabled, List<String> hologramLines) {
        this.key = key;
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
        this.customModelData = customModelData;
        this.permission = permission;
        this.recipes = recipes;
        this.hologramEnabled = hologramEnabled;
        this.hologramLines = hologramLines;
    }

    public String getKey() {
        return key;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getRecipes() {
        return recipes;
    }

    public boolean isHologramEnabled() {
        return hologramEnabled;
    }

    public List<String> getHologramLines() {
        return hologramLines;
    }
}
