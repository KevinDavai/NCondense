package com.nours.ncondense;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PlayerPickUpListener implements Listener {

    private final NCondense plugin;
    private int itemPickupCount = 0; // Variable to count item pickups

    public PlayerPickUpListener(NCondense plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        itemPickupCount += event.getItem().getItemStack().getAmount(); // Increment the pickup count
        plugin.getLogger().info("Total items picked up: " + itemPickupCount); // Log the count
    }
}