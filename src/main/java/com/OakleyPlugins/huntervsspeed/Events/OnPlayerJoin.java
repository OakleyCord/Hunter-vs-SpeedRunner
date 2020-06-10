package com.OakleyPlugins.huntervsspeed.Events;


import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OnPlayerJoin implements Listener {
    FileConfiguration getConfig = Huntervsspeed.plugin.getConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        boolean bS = getConfig.getBoolean("spectateBlind");
        boolean kod = getConfig.getBoolean("KickOnDie");
        Player p = e.getPlayer();
        if (Huntervsspeed.inGame) {
            if (Huntervsspeed.hunt.contains(p.getUniqueId()) || Huntervsspeed.speed.contains(p.getUniqueId())) {
                if (Huntervsspeed.offline.containsKey(p.getUniqueId())) {
                    if(Huntervsspeed.hunt.contains(p.getUniqueId())){
                        if(p.getGameMode() == GameMode.SPECTATOR){
                            p.setGameMode(GameMode.SURVIVAL);
                            p.teleport(Huntervsspeed.spawn);
                            p.removePotionEffect(PotionEffectType.BLINDNESS);
                        }
                    }
                    LivingEntity le = Huntervsspeed.offline.get(p.getUniqueId());
                    if(!(le.getHealth() <= 0)) {
                        p.setHealth(le.getHealth());
                    }
                    le.remove();
                    Huntervsspeed.offlineEntity.remove(le);
                    Huntervsspeed.offlineInv.remove(p.getUniqueId());
                    Huntervsspeed.offline.remove(p.getUniqueId());
                } else if (Huntervsspeed.InvClear.contains(p.getUniqueId())) {
                    p.teleport(Huntervsspeed.spawn);
                    Huntervsspeed.InvClear.remove(p.getUniqueId());
                    p.getInventory().clear();
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
                    p.sendTitle(ChatColor.RED + "YOU DIED!", "", 10, 20, 10);
                }
                return;
            }else{
                if (bS) {
                    Huntervsspeed.isFrozen.add(p.getUniqueId());
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
                }
            }
            if (Huntervsspeed.InvClear.contains(p.getUniqueId())) {
                Huntervsspeed.InvClear.remove(p.getUniqueId());
                p.setGameMode(GameMode.SPECTATOR);
                p.getInventory().clear();
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
                p.sendTitle(ChatColor.RED + "YOU DIED!", "", 10, 20, 10);
                if (kod) {
                    p.kickPlayer("");
                }
            }
            Huntervsspeed.IsSpectating.add(p);
            p.sendMessage(ChatColor.GREEN + "Do /TpTo to tp to a player");
        }
        if (Huntervsspeed.spawn == null) {
            Huntervsspeed.spawn = p.getLocation();
        }
        p.setGameMode(GameMode.SPECTATOR);
    }
}
