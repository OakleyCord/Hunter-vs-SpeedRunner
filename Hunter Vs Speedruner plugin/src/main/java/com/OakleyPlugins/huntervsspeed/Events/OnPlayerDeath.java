package com.OakleyPlugins.huntervsspeed.Events;

import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import static com.OakleyPlugins.huntervsspeed.Huntervsspeed.*;

@SuppressWarnings("ConstantConditions")
public class OnPlayerDeath implements Listener {
    FileConfiguration getConfig = Huntervsspeed.plugin.getConfig();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        System.out.println("Player has died");
        Player p = e.getEntity();
        boolean kc = getConfig.getBoolean("KeepCompass");
        boolean ce = getConfig.getBoolean("CompassEnabled");
        if (inGame) {
            p.getWorld().strikeLightningEffect(p.getLocation());
            if (kc & ce) {
                if (hunt.contains(p.getPlayer().getUniqueId())) {
                    if (p.getInventory().contains(Material.COMPASS)) {
                        ItemStack Compass = new ItemStack(Material.COMPASS);
                        e.getDrops().remove(Compass);
                        hasCompass.add(p.getUniqueId());
                    } else if (p.getInventory().contains(Material.COMPASS)) {
                        hasCompass.remove(p.getUniqueId());
                    }
                }
            }
        }
    }
}