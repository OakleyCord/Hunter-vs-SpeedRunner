package com.OakleyPlugins.huntervsspeed.Events;

import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class OnEntityDeath implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (Huntervsspeed.inGame) {
            if (Huntervsspeed.offlineEntity.containsKey(e.getEntity())) {
                UUID i = Huntervsspeed.offlineEntity.get(e.getEntity());
                OfflinePlayer k = Bukkit.getOfflinePlayer(i);
                ItemStack[] inv = Huntervsspeed.offlineInv.get(i);
                e.getDrops().clear();
                for (ItemStack l : inv) {
                    e.getDrops().add(l);
                }
                e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + k.getName() + ChatColor.RED + " Has Been Killed by " + ChatColor.GOLD + e.getEntity().getKiller().getDisplayName());
                if (Huntervsspeed.speed.contains(i)) {
                    Huntervsspeed.speed.remove(i);
                    if (Huntervsspeed.speed.size() == 0) {
                        for (Player everyone : Bukkit.getServer().getOnlinePlayers()) {
                            everyone.sendTitle(ChatColor.GREEN + "Hunter", ChatColor.GOLD + "Wins", 10, 70, 20);
                            everyone.playSound(everyone.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
                            everyone.playSound(everyone.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
                            Huntervsspeed.inGame = false;
                            Bukkit.getScheduler().runTaskLater(Huntervsspeed.plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart"), 160);
                        }
                    }
                }
                Huntervsspeed.offline.remove(i);
                Huntervsspeed.offlineInv.remove(i);
                Huntervsspeed.offlineEntity.remove(e.getEntity());
                Huntervsspeed.InvClear.add(i);
            }
            if (e.getEntity() instanceof EnderDragon) {
                for (Player everyone : Bukkit.getServer().getOnlinePlayers()) {
                    Huntervsspeed.inGame = false;
                    everyone.sendTitle(ChatColor.GREEN + "SpeedRunner", ChatColor.GOLD + "Wins", 10, 70, 20);
                    Bukkit.getScheduler().runTaskLater((Plugin) this, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart"), 160);
                }
            }
        }
    }
}
