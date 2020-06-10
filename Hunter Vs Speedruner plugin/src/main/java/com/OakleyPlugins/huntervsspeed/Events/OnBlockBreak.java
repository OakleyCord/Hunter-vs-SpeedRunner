package com.OakleyPlugins.huntervsspeed.Events;


import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.UUID;


public class OnBlockBreak implements Listener {
    ArrayList<UUID> isFrozen = Huntervsspeed.isFrozen;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (isFrozen.contains(p.getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
