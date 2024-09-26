package com.nours.ncondense.commands;

import com.nours.ncondense.NCondense;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.util.List;

public class AdminReloadCommand implements CommandHandler {

    private final NCondense plugin;

    public AdminReloadCommand(NCondense plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.GRAY + "Reloading NCondense configuration...");
        plugin.reloadPlugin();
        sender.sendMessage(ChatColor.GREEN + "NCondense configuration successfully reloaded.");
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