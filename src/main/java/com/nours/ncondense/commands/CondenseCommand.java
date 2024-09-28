package com.nours.ncondense.commands;

import com.nours.ncondense.NCondense;
import com.nours.ncondense.recipes.RecipeModel;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nullable;
import java.util.List;

public class CondenseCommand implements CommandHandler {

    private final NCondense plugin;

    public CondenseCommand(NCondense plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return;
        }

        Inventory inventory = player.getInventory();

        // Get the recipes from the recipe manager
        List<RecipeModel> recipes = plugin.getRecipeManager().getRecipes();

        // Loop through the recipes and craft them
        for (RecipeModel recipe : recipes) {
            // Check if the player has permission to use the recipe
            // If they don't, skip to the next recipe
            if(!player.hasPermission(recipe.getPermission())) continue;

            recipe.craft(inventory);
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return TabHelper.EMPTY_LIST;  // No tab completion needed for condense
    }

    @Override
    public @Nullable String getPermission() {
        return "ncondense.condense";
    }
}