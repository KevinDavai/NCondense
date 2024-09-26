package com.nours.ncondense.recipes;

import com.nours.ncondense.NCondense;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * recipes:
 *     recipe1:
 *       input-item:
 *         material: WHEAT
 *         name: '&4My Custom Wheat [Tier 1]'
 *         input-number: 9
 *       output-item:
 *         material: WHEAT
 *         name: '&4My Custom Wheat [Tier 2]'
 *         output-number: 1
 *       permission: 'autocondense.recipe1'
 *       is-auto-condensable: true
 *     recipe2:
 *       input-item:
 *         material: IRON_NUGGET
 *         input-number: 9
 *       output-item:
 *         material: IRON_INGOT
 *         output-number: 1
 *       permission: 'autocondense.recipe2'
 *       is-auto-condensable: false
 *
 */

public class RecipeModel {

    private String id;

    private Material inputMaterial;

    private String inputName;

    private int inputNumber;

    private int outputNumber;

    private Material outputMaterial;

    private String outputName;

    private String permission;

    private boolean isAutoCondensable;

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

    public boolean canCraft(Inventory inventory) {
        int count = 0;
        // TODO: Check name / etc
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) != null && inventory.getItem(i).getType() == inputMaterial) {
                count += inventory.getItem(i).getAmount();
            }
        }
        return count >= inputNumber;
    }

    public void craft(Inventory inventory) {
        int maxCrafts = 0;
        int totalInputCount = 0;

        for(int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if(item != null && item.getType() == inputMaterial) {
                totalInputCount += item.getAmount();
            }
        }

        maxCrafts = totalInputCount / inputNumber;

        if(maxCrafts == 0) {
            return;
        }

        int itemsToRemove = maxCrafts * inputNumber;


        for(int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if(item != null && item.getType() == inputMaterial) {

                if(itemsToRemove > 0) {
                    int amountToRemove = Math.min(item.getAmount(), itemsToRemove);
                    item.setAmount(item.getAmount() - amountToRemove);
                    itemsToRemove -= amountToRemove;
                }

                if(item.getAmount() == 0) {
                    inventory.setItem(i, null);
                }
            }

            if(itemsToRemove <= 0) {
                break;
            }
        }

        int totalOutput = maxCrafts * outputNumber;
        ItemStack outputItem = new ItemStack(outputMaterial, totalOutput);
        inventory.addItem(outputItem);
    }
}
