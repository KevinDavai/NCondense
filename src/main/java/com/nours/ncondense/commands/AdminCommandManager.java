package com.nours.ncondense.commands;

import com.nours.ncondense.NCondense;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;

public class AdminCommandManager extends BaseCommandManager {

    public AdminCommandManager(NCondense plugin) {
        super(1);

        // Register admin-related subcommands
        subcommands.put("reload", new AdminReloadCommand(plugin));
        subcommands.put("showrecipes", new AdminShowRecipesCommand(plugin));

    }

    @Override
    public void showHelp(CommandSender sender) {
        // Header with alternating dashes
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &8- &7- &8- &7- &8- &7- &8- [&e NCondense Admin &7] &8- &7- &8- &7- &8- &7- &8-"));

        // Available commands header
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Available commands:"));

        // Command listings with alternating dash colors
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 * " + ChatColor.YELLOW + "/ncondense admin reload " + ChatColor.translateAlternateColorCodes('&', "&7") + ": reload NCondense configuration."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 * " + ChatColor.YELLOW + "/ncondense admin showrecipes " + ChatColor.translateAlternateColorCodes('&', "&7") + ": List all recipes in the console."));

    }

    @Override
    public @Nullable String getPermission() {
        return "ncondense.admin";
    }
}