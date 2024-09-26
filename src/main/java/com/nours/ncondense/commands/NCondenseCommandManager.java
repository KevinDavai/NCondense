package com.nours.ncondense.commands;

import com.nours.ncondense.NCondense;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

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
        subcommands.put("autocondense", new AutoCondenseCommand(plugin));
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
        return null;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        super.handle(sender, args);
        return true;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        return super.tabComplete(sender, args);
    }

    public void loadCommand() {
        // Register the command
        this.plugin.getCommand("ncondense").setExecutor(this);
        this.plugin.getCommand("ncondense").setTabCompleter(this);
    }

}