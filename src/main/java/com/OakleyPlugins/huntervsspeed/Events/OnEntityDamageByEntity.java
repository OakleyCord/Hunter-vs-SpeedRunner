package com.OakleyPlugins.huntervsspeed.Events;

        import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
        import org.bukkit.Bukkit;
        import org.bukkit.ChatColor;
        import org.bukkit.entity.*;
        import org.bukkit.event.EventHandler;
        import org.bukkit.event.Listener;
        import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class OnEntityDamageByEntity implements Listener {
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity damageTaker = e.getEntity();
        if (damageTaker instanceof Player) {
            Player taker = (Player) damageTaker;
            if (damager instanceof Player) {
                Player damagerPlayer = (Player) damager;
                    if (((Player) e.getEntity()).getHealth() - e.getFinalDamage() <= 0) {
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + taker.getDisplayName() + ChatColor.RED + " Has Been Killed by " + ChatColor.GOLD + damagerPlayer.getDisplayName());
                    }
            }else{
                if (((Player) e.getEntity()).getHealth() - e.getFinalDamage() <= 0) {
                    if(!(damager instanceof Arrow))
                    Bukkit.getServer().broadcastMessage(ChatColor.GOLD + taker.getDisplayName() + ChatColor.RED + " Has Been Killed by a " + ChatColor.GOLD + damager.getName());
                }
            }
        }else{
            if(e.getEntity() instanceof LivingEntity)  {
                LivingEntity entityDamaged = (LivingEntity) e.getEntity();
                if (Huntervsspeed.offlineEntity.containsKey(entityDamaged)) {
                    if (damager instanceof Mob) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
