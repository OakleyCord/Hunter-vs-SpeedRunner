package com.OakleyPlugins.huntervsspeed.Events;

import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


import java.util.UUID;

import static com.OakleyPlugins.huntervsspeed.Huntervsspeed.*;

public class OnPlayerInteract implements Listener {
    FileConfiguration getConfig = Huntervsspeed.plugin.getConfig();


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (inGame) {
            if (hunt.contains(p.getUniqueId())) {
                boolean ce = getConfig.getBoolean("CompassEnabled");
                boolean trackPort = getConfig.getBoolean("trackNetherPortal");
                if (ce) {
                    if (e.getMaterial() == Material.COMPASS) {
                        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                            pointingTo.putIfAbsent(p.getUniqueId(), 0);
                            int i = pointingTo.get(p.getUniqueId());
                            if (i == speed.size() - 1) {
                                i = 0;
                            } else {
                                i++;
                            }
                            pointingTo.replace(p.getUniqueId(), i);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Compass has now changed to point to " + ChatColor.GOLD + Bukkit.getOfflinePlayer(speed.get(i)).getName()));
                        }
                        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
                            pointingTo.putIfAbsent(p.getUniqueId(), 0);
                            int i = pointingTo.get(p.getUniqueId());
                            UUID k = speed.get(i);
                            Player closestp = Bukkit.getPlayer(k);
                            boolean ShowY = getConfig.getBoolean("ShowYCompass");
                            if (closestp != null) {
                                Location l = closestp.getLocation();
                                int y = l.getBlockY();
                                if (closestp.getWorld().getEnvironment() == World.Environment.NORMAL) {
                                    if (p.getWorld().getEnvironment() == World.Environment.NORMAL) {
                                        if (ShowY) {
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Compass now is pointing at " + ChatColor.GOLD + closestp.getName() + ChatColor.GOLD + " with y level: " + ChatColor.GOLD + y));
                                        } else {
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Compass now is pointing at " + ChatColor.GOLD + closestp.getName()));
                                        }
                                        p.setCompassTarget(closestp.getLocation());
                                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                                    } else {
                                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "You must be in the over world to find " + ChatColor.GOLD + closestp.getName()));
                                    }
                                } else if (closestp.getWorld().getEnvironment() == World.Environment.NETHER) {
                                    if (p.getWorld().getEnvironment() == World.Environment.NETHER) {
                                        p.setCompassTarget(closestp.getLocation());
                                        if (ShowY) {
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Compass now is pointing at " + ChatColor.GOLD + closestp.getName() + ChatColor.GOLD + " with y level: " + ChatColor.GOLD + y));
                                        } else {
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Compass now is pointing at " + ChatColor.GOLD + closestp.getName()));
                                        }
                                    } else {
                                        if (closestp.getWorld().getEnvironment() == World.Environment.NORMAL) {
                                            if (trackPort) {
                                                p.setCompassTarget(portalLocation.get(closestp.getUniqueId()));
                                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Compass now is pointing to " + ChatColor.GOLD + closestp.getName() + ChatColor.GREEN + "'s nether portal"));
                                            } else {
                                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + closestp.getName() + " is in the nether"));
                                            }
                                        } else {
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "You Must be in the OverWorld to find the end portal"));
                                        }
                                    }
                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                                } else if (closestp.getWorld().getEnvironment() == World.Environment.THE_END) {
                                    if (p.getWorld().getEnvironment() == World.Environment.THE_END) {
                                        p.setCompassTarget(closestp.getLocation());
                                        if (ShowY) {
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Compass now is pointing at " + ChatColor.GOLD + closestp.getName() + ChatColor.GOLD + " with y level: " + ChatColor.GOLD + y));
                                        } else {
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Compass now is pointing at " + ChatColor.GOLD + closestp.getName()));
                                        }
                                    } else {
                                        if (closestp.getWorld().getEnvironment() == World.Environment.NORMAL) {
                                            if (trackPort) {
                                                p.setCompassTarget(portalLocation.get(closestp.getUniqueId()));
                                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Compass now is pointing to the end portal"));
                                            } else {
                                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + closestp.getName() + " is in the end"));
                                            }
                                        } else {
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "You Must be in the OverWorld to find the end portal"));
                                        }
                                    }
                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                                }
                            } else {
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "SpeedRunner is offline"));
                            }
                        }
                    }

                }
            }
        }
    }
}
