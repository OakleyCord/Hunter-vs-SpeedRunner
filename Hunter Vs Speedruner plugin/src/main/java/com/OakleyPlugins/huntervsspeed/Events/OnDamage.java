package com.OakleyPlugins.huntervsspeed.Events;

import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnDamage implements Listener {
    FileConfiguration getConfig = Huntervsspeed.plugin.getConfig();

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (Huntervsspeed.inGame) {
            if (e.getEntity() instanceof Player) {
                if (((Player) e.getEntity()).getHealth() - e.getFinalDamage() <= 0) {
                    Player p = (Player) e.getEntity();
                    if(e.getCause() == EntityDamageEvent.DamageCause.CRAMMING){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " was squished too much");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.MAGIC){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " was killed using magic");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.STARVATION){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has starved to death");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.SUICIDE){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has committed suicide");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.LAVA){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " tried to swim in lava");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has discovered the floor is lava");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has been struck by lightning");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has suffocated in a wall");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has been shot to death");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.FIRE){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has been burned in flames");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has been burned in flames");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has been blown up");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has been killed by a dragon using magic");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.DROWNING){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has drowned");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.CONTACT){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has been poked to death");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has fell from a high place");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has been killed by a falling block");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.VOID){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has fell in the void");
                    }

                    if(e.getCause() == EntityDamageEvent.DamageCause.WITHER){
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " has withered away");
                    }
                    World w = p.getPlayer().getWorld();
                    if (Huntervsspeed.speed.contains(p.getPlayer().getUniqueId())) {
                        Huntervsspeed.UpdateScore();
                        boolean kod = getConfig.getBoolean("KickOnDie");
                        Huntervsspeed.speed.remove(p.getPlayer().getUniqueId());
                        Huntervsspeed.IsSpectating.add(p.getPlayer());
                        p.setGameMode(GameMode.SPECTATOR);
                        e.setCancelled(true);
                        w.strikeLightningEffect(p.getLocation());
                        if (Huntervsspeed.speed.size() == 0) {
                            for (Player everyone : Bukkit.getServer().getOnlinePlayers()) {
                                everyone.sendTitle(ChatColor.GREEN + "Hunter", ChatColor.GOLD + "Wins", 10, 70, 20);
                                everyone.playSound(everyone.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
                                everyone.playSound(everyone.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
                                Huntervsspeed.inGame = false;
                                Bukkit.getScheduler().runTaskLater(Huntervsspeed.plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart"), 160);
                            }
                        } else {
                            if (kod) {
                                p.kickPlayer("");
                            }
                            p.sendMessage(ChatColor.GREEN + "Do /TpTo to tp to a player");
                        }
                    }
                }
            }
        }
    }
}
