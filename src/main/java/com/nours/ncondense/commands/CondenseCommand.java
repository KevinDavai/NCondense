package com.nours.ncondense.commands;

import com.nours.ncondense.NCondense;
import com.nours.ncondense.recipes.RecipeModel;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.Recipe;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CondenseCommand implements CommandHandler {

    private final NCondense plugin;

    public CondenseCommand(NCondense plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return;
        }
        Player player = (Player) sender;

        Inventory inventory = player.getInventory();

        // Get the recipes from the config
        List<RecipeModel> recipes = plugin.getRecipeManager().getRecipes();

        // Loop through the recipes
        for (RecipeModel recipe : recipes) {
            // Check if the recipe can be crafted
            if (recipe.canCraft(inventory)) {
                // Craft the recipe


                recipe.craft(inventory);
                return;
            }
        }

    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return TabHelper.EMPTY_LIST;  // No tab completion needed for condense
    }

    @Override
    public @Nullable String getPermission() {
        return "ncodense.condense";
    }
}