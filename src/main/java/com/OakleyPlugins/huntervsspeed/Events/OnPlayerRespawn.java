package com.OakleyPlugins.huntervsspeed.Events;

import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;


import static com.OakleyPlugins.huntervsspeed.Huntervsspeed.hasCompass;

public class OnPlayerRespawn implements Listener {
    FileConfiguration getConfig = Huntervsspeed.plugin.getConfig();

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        boolean ce = getConfig.getBoolean("CompassEnabled");
        boolean kc = getConfig.getBoolean("KeepCompass");
        if (kc & ce) {
            Player p = e.getPlayer();
            if (hasCompass.contains(p.getUniqueId())) {
                p.getInventory().addItem(new ItemStack(Material.COMPASS));
            }
        }
    }
}
