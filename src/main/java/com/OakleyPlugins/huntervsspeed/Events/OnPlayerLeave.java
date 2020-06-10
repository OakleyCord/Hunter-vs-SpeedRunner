package com.OakleyPlugins.huntervsspeed.Events;

import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@SuppressWarnings("ConstantConditions")
public class OnPlayerLeave implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if (Huntervsspeed.inGame) {
            Player p = e.getPlayer();
            if (!(Huntervsspeed.IsSpectating.contains(p))) {
                World w = p.getWorld();
                LivingEntity zombie = (LivingEntity) w.spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                Zombie zombieAdult = (Zombie) zombie;
                if (zombieAdult.isBaby()) {
                    zombieAdult.setBaby(false);
                }
                zombie.setHealth(p.getHealth());
                zombie.setAI(false);
                zombie.setRemoveWhenFarAway(false);
                zombie.getEquipment().setArmorContents(p.getInventory().getArmorContents());
                if (p.getInventory().getHelmet() == null) {
                    ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                    headMeta.setOwningPlayer(p);
                    head.setItemMeta(headMeta);
                    zombie.getEquipment().setHelmet(head);
                }
                zombie.setCustomName(ChatColor.GOLD + p.getDisplayName() + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "OFFLINE" + ChatColor.DARK_GRAY + "]");
                zombie.getEquipment().setItemInMainHand(p.getInventory().getItemInMainHand());
                zombie.getEquipment().setItemInOffHand(p.getInventory().getItemInOffHand());
                Huntervsspeed.offlineInv.put(p.getUniqueId(), p.getInventory().getContents());
                Huntervsspeed.offline.put(p.getUniqueId(), zombie);
                Huntervsspeed.offlineEntity.put(zombie, p.getUniqueId());
            }
        }
    }
}
