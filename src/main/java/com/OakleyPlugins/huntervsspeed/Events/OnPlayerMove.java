package com.OakleyPlugins.huntervsspeed.Events;

import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.UUID;

public class OnPlayerMove implements Listener {
    ArrayList<UUID> isFrozen = Huntervsspeed.isFrozen;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (isFrozen.contains(p.getUniqueId())) {
            p.teleport(p);
        }
    }
}
