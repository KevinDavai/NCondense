package com.nours.ncondense.recipes;

import com.nours.ncondense.NCondense;

import java.util.List;

public class RecipeManager {
    private final NCondense plugin;

    private List<RecipeModel> recipes;

    public RecipeManager(NCondense plugin) {
        this.plugin = plugin;
    }

    public void loadRecipes() {
        // Load the recipes from the config
        List<RecipeModel> recipes = plugin.getConfigsManager().getRecipes();
        this.recipes = recipes;
    }

    public List<RecipeModel> getRecipes() {
        return recipes;
    }
}
