package com.nours.ncondense.commands;

import com.nours.ncondense.NCondense;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AutoCondenseCommand implements CommandHandler {
    private final NCondense plugin;

    public AutoCondenseCommand(NCondense plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return;
        }

        // Store the player in a local variable to avoid repeated casting

        // If no arguments, toggle the auto condense
        if (args.length == 1) {
            plugin.getNPlayerManager().toggleAutoCondense(player);
            sender.sendMessage(ChatColor.YELLOW + "Auto condense is now " + (plugin.getNPlayerManager().isAutoCondenseEnabled(player) ? "enabled" : "disabled") + ".");
            return;
        }

        // If there's a second argument, check for "on" or "off"
        if (args.length == 2) {
            plugin.getLogger().info(args[1].toLowerCase());
            switch (args[1].toLowerCase()) {
                case "on":
                    plugin.getNPlayerManager().setAutoCondense(player, true);
                    sender.sendMessage(ChatColor.YELLOW + "Auto condense is now " + (plugin.getNPlayerManager().isAutoCondenseEnabled(player) ? "enabled" : "disabled") + ".");
                    break;
                case "off":
                    plugin.getNPlayerManager().setAutoCondense(player, false);
                    sender.sendMessage(ChatColor.YELLOW + "Auto condense is now " + (plugin.getNPlayerManager().isAutoCondenseEnabled(player) ? "enabled" : "disabled") + ".");
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Invalid argument. Use 'on' or 'off'.");
                    break;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /command autocondense <on/off>");
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        tabComplete.add("on");
        tabComplete.add("off");
        return tabComplete;
    }

    @Override
    public @Nullable String getPermission() {
        return "ncondense.autocondense";
    }
}
