package com.nours.ncondense.commands;

import com.nours.ncondense.NCondense;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.util.List;

public class AdminShowRecipesCommand implements CommandHandler {
    private final NCondense plugin;

    public AdminShowRecipesCommand(NCondense plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        plugin.getRecipeManager().getRecipes().forEach(recipe -> {
            recipe.printRecipe(plugin);
        });
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return TabHelper.EMPTY_LIST;  // No tab completion needed for reload
    }

    @Override
    public @Nullable String getPermission() {
        return "ncondense.admin";
    }
}
