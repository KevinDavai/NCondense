package com.nours.ncondense.player;

import com.nours.ncondense.NCondense;
import com.nours.ncondense.recipes.RecipeModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NPlayerManager {
    private final ConcurrentHashMap<Player, Boolean> autoCondensePlayers = new ConcurrentHashMap<>();
    private final NCondense plugin;

    public NPlayerManager(NCondense plugin) {
        this.plugin = plugin;
    }


    public boolean isAutoCondenseEnabled(Player player) {
        return autoCondensePlayers.containsKey(player);
    }

    public void toggleAutoCondense(Player player) {
        if(isAutoCondenseEnabled(player)) {
            autoCondensePlayers.remove(player);
            return;
        }

        autoCondensePlayers.put(player, true);
    }

    public void setAutoCondense(Player sender, boolean b) {
        if(b) {
            autoCondensePlayers.put(sender, true);
            return;
        }

        autoCondensePlayers.remove(sender);
    }

    public void startAutoCondenseTask() {
        if(!plugin.getConfigsManager().isAutoCondenseEnabled()) return;

        // Schedule the task to run every X seconds (20 ticks = 1 second)
        int seconds = plugin.getConfigsManager().getAutoCondenseInterval();
        int ticks = seconds * 20;

        new BukkitRunnable() {
            @Override
            public void run() {
                takeInventorySnapshots();
            }
        }.runTaskTimer(plugin, 0, ticks);
    }

    public void stopAutoCondenseTask() {
        Bukkit.getScheduler().cancelTasks(plugin);
    }

    private void takeInventorySnapshots() {
        for (Player player : autoCondensePlayers.keySet()) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                List<RecipeModel> recipes = plugin.getRecipeManager().getRecipes();
                for (RecipeModel recipe : recipes) {
                    if(!recipe.isAutoCondensable()) continue;

                    // Check if the player has the permission to craft the recipe
                    if (!player.hasPermission(recipe.getPermission())) continue;

                    // Check if the player is in a blacklisted world
                    if(plugin.getConfigsManager().getBlacklistedWorlds().contains(player.getWorld().getName())) continue;

                    Inventory playerInventory = player.getInventory();

                    // Check if the player has the minimum number of items needed to craft the recipe
                    if(!recipe.hasMinItems(playerInventory)) continue;

                    // Craft the recipe
                    recipe.craft(playerInventory);
                }
            });
        }
    }



}
