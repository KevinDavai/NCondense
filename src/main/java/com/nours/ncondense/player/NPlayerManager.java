package com.nours.ncondense.player;

import com.nours.ncondense.NCondense;
import com.nours.ncondense.recipes.RecipeModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
        // Schedule the task to run every X seconds (20 ticks = 1 second)
        int seconds = 1;
        int ticks = seconds * 20;

        new BukkitRunnable() {
            @Override
            public void run() {
                takeInventorySnapshots();
            }
        }.runTaskTimer(plugin, 0, ticks);
    }

    private void takeInventorySnapshots() {
        for (Player player : autoCondensePlayers.keySet()) {
            // Take a snapshot of the player's inventory contents
            ItemStack[] currentContents = player.getInventory().getContents();

            // Pass the snapshot to an async task for comparison
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                List<RecipeModel> recipes = plugin.getRecipeManager().getRecipes();
                for (RecipeModel recipe : recipes) {
                    plugin.getLogger().info("Checking recipe: " + recipe.getId());
                    if (player.hasPermission(recipe.getPermission())) {
                        recipe.craft(player.getInventory());
                    }
                }
            });

        }
    }



}
