package com.nours.ncondense.commands;

import com.nours.ncondense.NCondense;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;

public class NCondenseCommandManager extends BaseCommandManager implements TabExecutor {

    private final NCondense plugin;

    public NCondenseCommandManager(NCondense plugin) {
        super(0);  // 0 means the subcommand comes after "/ncondense"

        this.plugin = plugin;

        // Register subcommands
        subcommands.put("admin", new AdminCommandManager(plugin));

        // Register condense subcommand
        subcommands.put("condense", new CondenseCommand(plugin));
    }



    @Override
    public void showHelp(CommandSender sender) {
        // Header with alternating dashes
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &8- &7- &8- &7- &8- &7- &8- [&e NCondense &7] &8- &7- &8- &7- &8- &7- &8-"));

        // Available commands header
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Available commands:"));

        // Command listings with alternating dash colors
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 * " + ChatColor.YELLOW + "/ncondense condense " + ChatColor.translateAlternateColorCodes('&', "&7") + ": condense your items."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 * " + ChatColor.YELLOW + "/ncondense admin reload " + ChatColor.translateAlternateColorCodes('&', "&7") + ": reload NCondense configuration."));
    }

    @Override
    public @Nullable String getPermission() {
        return null;  // General permission for /ncondense if needed
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        super.handle(sender, args);
        return true;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return super.tabComplete(sender, args);
    }

    public void loadCommand() {
        // Register the command
        this.plugin.getCommand("ncondense").setExecutor(this);
        this.plugin.getCommand("ncondense").setTabCompleter(this);
    }

}