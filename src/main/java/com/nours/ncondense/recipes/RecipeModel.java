package com.nours.ncondense.recipes;

import com.nours.ncondense.NCondense;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class RecipeModel {

    private final String id;

    private final Material inputMaterial;

    private final String inputName;

    private final int inputNumber;

    private final int outputNumber;

    private final Material outputMaterial;

    private final String outputName;

    private final String permission;

    private final boolean isAutoCondensable;

    public RecipeModel(String id, Material inputMaterial, String inputName, int inputNumber, int outputNumber, Material outputMaterial, String outputName, String permission, boolean isAutoCondensable) {
        this.id = id;
        this.inputMaterial = inputMaterial;
        this.inputName = inputName;
        this.inputNumber = inputNumber;
        this.outputNumber = outputNumber;
        this.outputMaterial = outputMaterial;
        this.outputName = outputName;
        this.permission = permission;
        this.isAutoCondensable = isAutoCondensable;
    }

    public String getId() {
        return id;
    }

    public Material getInputMaterial() {
        return inputMaterial;
    }

    public String getInputName() {
        return inputName;
    }

    public int getInputNumber() {
        return inputNumber;
    }

    public int getOutputNumber() {
        return outputNumber;
    }

    public Material getOutputMaterial() {
        return outputMaterial;
    }

    public String getOutputName() {
        return outputName;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isAutoCondensable() {
        return isAutoCondensable;
    }

    public void printRecipe(NCondense plugin) {
        plugin.getLogger().info("Recipe ID: " + id);
        plugin.getLogger().info("Input Material: " + inputMaterial);
        plugin.getLogger().info("Input Name: " + inputName);
        plugin.getLogger().info("Input Number: " + inputNumber);
        plugin.getLogger().info("Output Material: " + outputMaterial);
        plugin.getLogger().info("Output Name: " + outputName);
        plugin.getLogger().info("Output Number: " + outputNumber);
        plugin.getLogger().info("Permission: " + permission);
        plugin.getLogger().info("Is Auto Condensable: " + isAutoCondensable);
    }


    /**
     * Craft the recipe
     * @param inventory
     */
    public void craft(Inventory inventory) {
        int validRecipeItemCount = getValidRecipeItemCount(inventory);
        int maxCraft = validRecipeItemCount / inputNumber;
        int itemsToRemove = maxCraft * inputNumber;

        if(maxCraft == 0) {
            return;
        }

        HashMap<Integer, ItemStack> remaining = inventory.addItem(new ItemStack(outputMaterial, maxCraft * outputNumber));
        inventory.removeItem(new ItemStack(inputMaterial, itemsToRemove));

        // Drop the remaining items on the ground
        if(!remaining.isEmpty()) {
            remaining.forEach((index, item) -> {
                inventory.getLocation().getWorld().dropItem(inventory.getLocation(), item);
            });
        }
    }

    /**
     * Get the number of valid recipe items in the inventory
     * @param inventory
     * @return the number of valid recipe items
     */
    private int getValidRecipeItemCount(Inventory inventory) {
        int validItems = 0;

        for(int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if(isValidRecipeItem(item)) {
                validItems += item.getAmount();
            }
        }

        return validItems;
    }


    /**
     * Check if the item is a valid recipe item
     * @param item
     * @return true if the item is a valid recipe item
     */
    private boolean isValidRecipeItem(ItemStack item) {
        return item != null && item.getType() == inputMaterial;
    }
}
