package com.OakleyPlugins.huntervsspeed.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import static com.OakleyPlugins.huntervsspeed.Huntervsspeed.portalLocation;
import static com.OakleyPlugins.huntervsspeed.Huntervsspeed.speed;

public class OnPortal implements Listener {
    @EventHandler
    public void onPortalTeleport(PlayerTeleportEvent e){
        Player p = e.getPlayer();
        if(speed.contains(p.getUniqueId())){
            if(e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL || e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL){
                portalLocation.put(p.getUniqueId(),p.getLocation());
            }
        }
    }
}
