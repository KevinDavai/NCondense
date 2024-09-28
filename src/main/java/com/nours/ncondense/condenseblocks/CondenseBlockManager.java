package com.nours.ncondense.condenseblocks;

import com.nours.ncondense.NCondense;
import com.nours.ncondense.recipes.RecipeModel;

import java.util.List;

public class CondenseBlockManager {
    private final NCondense plugin;

    private List<CondenseBlockModel> condenseBlocks;

    public CondenseBlockManager(NCondense plugin) {
        this.plugin = plugin;
    }

    public void loadCondenseBlocks() {
        this.condenseBlocks = plugin.getConfigsManager().getCondenseBlocks();
    }

    public List<CondenseBlockModel> getCondenseBlocks() {
        return condenseBlocks;
    }
}
