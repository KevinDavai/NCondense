package com.nours.ncondense.recipes;

import com.nours.ncondense.NCondense;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.logging.Logger;

public class RecipeModel {

    private final NCondense plugin;

    private final String id;

    private final Material inputMaterial;

    private final String inputName;

    private final int inputNumber;

    private final int outputNumber;

    private final Material outputMaterial;

    private final String outputName;

    private final String permission;

    private final boolean isAutoCondensable;

    public RecipeModel(NCondense plugin, String id, Material inputMaterial, String inputName, int inputNumber, int outputNumber, Material outputMaterial, String outputName, String permission, boolean isAutoCondensable) {
        this.plugin = plugin;
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
     * @param inventory - the inventory to craft the recipe in
     */
    public void craft(Inventory inventory) {
        int validRecipeItemCount = getValidRecipeItemCount(inventory);
        int maxCraft = validRecipeItemCount / inputNumber;
        int itemsToRemove = maxCraft * inputNumber;

        if(maxCraft == 0) {
            return;
        }

        HashMap<Integer, ItemStack> remaining = inventory.addItem(getOutputItemStack(maxCraft * outputNumber));
        inventory.removeItem(getInputItemStack(itemsToRemove));

        // Drop the remaining items on the ground
        if(!remaining.isEmpty()) {
            remaining.forEach((index, item) -> inventory.getLocation().getWorld().dropItem(inventory.getLocation(), item));
        }
    }

    /**
     * Check if the inventory has the minimum number of items needed to craft the recipe
     * @param inventory - the inventory to check
     * @return true if the inventory has the minimum number of items needed
     */
    public boolean hasMinItems(Inventory inventory) {
        int minRequired = this.plugin.getConfigsManager().getMinItemsToCondense();

        if(minRequired == 0) {
            return true;
        }

        int number = this.getValidRecipeItemCount(inventory);

        return number >= minRequired;
    }

    private ItemStack getInputItemStack(int amount) {
        ItemStack item = new ItemStack(inputMaterial, amount);
        ItemMeta meta = item.getItemMeta();
        if(inputName != null) {
            meta.setDisplayName(inputName);
        }
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getOutputItemStack(int amount) {
        ItemStack item = new ItemStack(outputMaterial, amount);
        ItemMeta meta = item.getItemMeta();
        if(outputName != null) {
            meta.setDisplayName(outputName);
        }
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Get the number of valid recipe items in the inventory
     * @param inventory - the inventory to check
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
     * @param item - the item to check
     * @return true if the item matches the recipe criteria
     */
    private boolean isValidRecipeItem(ItemStack item) {
        if (item == null) {
            return false; // Item is null, invalid
        }

        // Check if the item matches the material
        if (item.getType() != inputMaterial) {
            return false; // Material does not match
        }

        // Check the item meta (display name, etc.)
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            // Check if the item has a display name, and if so, compare with inputName
            if (meta.hasDisplayName()) {

                if (inputName != null && !meta.getDisplayName().equals(inputName)) {
                    return false; // Display name exists and doesn't match
                }
            } else if (inputName != null) {
                // If the inputName is provided but the item doesn't have a display name
                return false;
            }

            // TODO: Enchant check and custom model data check

            // Additional checks for lore, custom models, etc., can go here if needed
        } else if (inputName != null) {
            // If inputName is provided but the item has no ItemMeta (so no display name)
            return false;
        }

        if(inputName == null && item.getItemMeta().hasDisplayName()) {
            return false;
        }

        return true; // The item matches the recipe criteria
    }
}
