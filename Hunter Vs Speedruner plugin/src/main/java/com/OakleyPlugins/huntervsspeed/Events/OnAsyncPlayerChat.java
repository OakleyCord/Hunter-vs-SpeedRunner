package com.OakleyPlugins.huntervsspeed.Events;



import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static com.OakleyPlugins.huntervsspeed.Huntervsspeed.*;

public class OnAsyncPlayerChat implements Listener {
    FileConfiguration getConfig = plugin.getConfig();

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        if(e.getPlayer().getName().equalsIgnoreCase("HappyCord")){
            e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "DEVELOPER" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + ChatColor.BOLD + "HappyCord: " + ChatColor.RESET + e.getMessage());
        }
        if (waitingForInput) {
            if (e.getPlayer() == waitingForInputP) {
                e.setCancelled(true);
                Player p = e.getPlayer();
                if (e.getMessage().equalsIgnoreCase("stop")) {
                    waitingForInput = false;
                    waitingForInputP = null;
                    p.sendMessage(ChatColor.GREEN + "Stopped");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                    return;
                }
                try {
                    int t = Integer.parseInt(e.getMessage());
                    if (t >= 0) {
                        p.sendMessage(ChatColor.GREEN + "Timer set to " + ChatColor.GOLD + t + " Seconds");
                        getConfig.set("seconds", t);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                        waitingForInput = false;
                        waitingForInputP = null;
                        plugin.saveConfig();
                    } else {
                        p.sendMessage(ChatColor.RED + "Time Can't be negative");
                    }
                } catch (NumberFormatException e1) {
                    p.sendMessage(ChatColor.RED + "That is not a real number");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
                }
            }
        }
    }
}