package com.OakleyPlugins.huntervsspeed.Events;

import com.OakleyPlugins.huntervsspeed.Huntervsspeed;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.OakleyPlugins.huntervsspeed.Huntervsspeed.*;

@SuppressWarnings("ConstantConditions")
public class OnInventoryClick implements Listener {
    FileConfiguration getConfig = Huntervsspeed.plugin.getConfig();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() != null) {
            if (e.getCurrentItem().getItemMeta() != null) {
                if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Teleport to a player")) {
                    if (e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                        e.setCancelled(true);
                        Player t = Bukkit.getPlayerExact(e.getCurrentItem().getItemMeta().getDisplayName());
                        if (t != null) {
                            p.teleport(t);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                            p.closeInventory();
                            p.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.GOLD + t.getDisplayName());
                        }
                    }
                    if(e.getCurrentItem().getType() == Material.BARRIER){
                        p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_HAT,1f,1f);
                        p.closeInventory();
                    }
                    if(e.getCurrentItem().getType() == Material.ARROW){
                        int nextPage = TpMenu.indexOf(e.getClickedInventory()) + 1;
                        if(nextPage > (TpMenu.size() - 1) ){
                            p.playSound(p.getLocation(),Sound.ENTITY_ITEM_BREAK,1f,1f);
                            p.sendMessage(ChatColor.RED + "You are on the last page!");
                        }else{
                            p.openInventory(TpMenu.get(nextPage));
                            p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_HAT,1f,1f);
                        }
                    }
                    if(e.getCurrentItem().getType() == Material.BLAZE_ROD){
                        int nextPage = TpMenu.indexOf(e.getClickedInventory()) - 1;
                        if(nextPage < 0){
                            p.playSound(p.getLocation(),Sound.ENTITY_ITEM_BREAK,1f,1f);
                            p.sendMessage(ChatColor.RED + "You are on the first page!");
                        }else{
                            p.openInventory(TpMenu.get(nextPage));
                            p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_HAT,1f,1f);
                        }
                    }
                }


                if (AddRunner.contains(e.getClickedInventory())) {
                    if (e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                        ItemStack s = e.getCurrentItem();
                        e.setCancelled(true);
                        Player t = Bukkit.getPlayerExact(s.getItemMeta().getDisplayName());
                        if (t != null) {
                            Huntervsspeed.speed.add(t.getUniqueId());
                            p.sendMessage(ChatColor.GOLD + t.getDisplayName() + ChatColor.GREEN + " Added To SpeedRunner");
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                        }
                        int page = AddRunner.indexOf(e.getClickedInventory());
                        p.closeInventory();
                        Huntervsspeed.OpenAddRunner(p);
                        p.openInventory(AddRunner.get(page));
                    }
                    if(e.getCurrentItem().getType() == Material.BARRIER){
                        p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_HAT,1f,1f);
                        p.closeInventory();
                    }
                    if(e.getCurrentItem().getType() == Material.ARROW){
                        int nextPage = AddRunner.indexOf(e.getClickedInventory()) + 1;
                        if(nextPage > (AddRunner.size() - 1) ){
                            p.playSound(p.getLocation(),Sound.ENTITY_ITEM_BREAK,1f,1f);
                            p.sendMessage(ChatColor.RED + "You are on the last page!");
                        }else{
                            p.openInventory(AddRunner.get(nextPage));
                            p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_HAT,1f,1f);
                        }
                    }
                    if(e.getCurrentItem().getType() == Material.BLAZE_ROD){
                        int nextPage = AddRunner.indexOf(e.getClickedInventory()) - 1;
                        if(nextPage < 0){
                            p.playSound(p.getLocation(),Sound.ENTITY_ITEM_BREAK,1f,1f);
                            p.sendMessage(ChatColor.RED + "You are on the first page!");
                        }else{
                            p.openInventory(AddRunner.get(nextPage));
                            p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_HAT,1f,1f);
                        }
                    }
                }


                if (RemoveRunner.contains(e.getClickedInventory())) {
                    if(e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                        e.setCancelled(true);
                        ItemStack s = e.getCurrentItem();
                        Player t = Bukkit.getPlayerExact(s.getItemMeta().getDisplayName());
                        if (t != null) {
                            p.sendMessage(ChatColor.GOLD + t.getDisplayName() + ChatColor.RED + " Removed To SpeedRunner");
                            Huntervsspeed.speed.remove(t.getUniqueId());
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                        }
                        int page = RemoveRunner.indexOf(e.getClickedInventory());
                        p.closeInventory();
                        Huntervsspeed.OpenRemoveRunner(p);
                        p.openInventory(RemoveRunner.get(page));
                    }
                    if(e.getCurrentItem().getType() == Material.BARRIER){
                        p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_HAT,1f,1f);
                        p.closeInventory();
                    }
                    if(e.getCurrentItem().getType() == Material.ARROW){
                        int nextPage = RemoveRunner.indexOf(e.getClickedInventory()) + 1;
                        if(nextPage > (RemoveRunner.size() - 1) ){
                            p.playSound(p.getLocation(),Sound.ENTITY_ITEM_BREAK,1f,1f);
                            p.sendMessage(ChatColor.RED + "You are on the last page!");
                        }else{
                            p.openInventory(RemoveRunner.get(nextPage));
                            p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_HAT,1f,1f);
                        }
                    }
                    if(e.getCurrentItem().getType() == Material.BLAZE_ROD){
                        int nextPage = RemoveRunner.indexOf(e.getClickedInventory()) - 1;
                        if(nextPage < 0){
                            p.playSound(p.getLocation(),Sound.ENTITY_ITEM_BREAK,1f,1f);
                            p.sendMessage(ChatColor.RED + "You are on the first page!");
                        }else{
                            p.openInventory(RemoveRunner.get(nextPage));
                            p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_HAT,1f,1f);
                        }
                    }
                }
            }

            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Compass Settings")) {
                e.setCancelled(true);
                boolean playHat = false;
                switch (e.getCurrentItem().getType()) {
                    case RED_WOOL:
                        e.getCurrentItem().setType(Material.GREEN_WOOL);
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Keep Compass")) {
                            getConfig.set("KeepCompass", true);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Start with compass")) {
                            getConfig.set("StartWithCompass", true);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Show Y level")) {
                            getConfig.set("ShowYCompass", true);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        break;
                    case GREEN_WOOL:
                        e.getCurrentItem().setType(Material.RED_WOOL);
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Start with compass")) {
                            getConfig.set("StartWithCompass", false);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Keep Compass")) {
                            getConfig.set("KeepCompass", false);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Show Y level")) {
                            getConfig.set("ShowYCompass", false);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        break;
                    case PAPER:
                        playHat = true;
                        Huntervsspeed.OpenSettings(p);
                }
                if (playHat) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                } else {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                }
            }


            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Game Settings")) {
                e.setCancelled(true);
                boolean playHat = false;
                switch (e.getCurrentItem().getType()) {
                    case RED_WOOL:
                        e.getCurrentItem().setType(Material.GREEN_WOOL);
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Kick SpeedRunner on death")) {
                            getConfig.set("KickOnDie", true);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Blind on join")) {
                            getConfig.set("spectateBlind", true);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Delete world on plugin start")) {
                            getConfig.set("delete", true);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        break;
                    case GREEN_WOOL:
                        e.getCurrentItem().setType(Material.RED_WOOL);
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Kick SpeedRunner on death")) {
                            getConfig.set("KickOnDie", false);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Blind on join")) {
                            getConfig.set("spectateBlind", false);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Delete world on plugin start")) {
                            getConfig.set("delete", false);
                            Huntervsspeed.plugin.saveConfig();
                        }
                        break;
                    case CLOCK:
                        if (e.getAction() == InventoryAction.DROP_ONE_SLOT) {
                            playHat = true;
                            Huntervsspeed.waitingForInput = true;
                            Huntervsspeed.waitingForInputP = (Player) e.getWhoClicked();
                            p.closeInventory();
                            p.sendMessage(ChatColor.GOLD + "Type the time in seconds" + ChatColor.RED + " Type (STOP) to stop this");
                        }
                        break;
                    case PAPER:
                        playHat = true;
                        Huntervsspeed.OpenSettings(p);
                }
                if (playHat) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                } else {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                }
            }
            if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Settings")) {
                e.setCancelled(true);
                ItemStack b = new ItemStack(Material.PAPER);
                ItemStack t = new ItemStack(Material.GREEN_WOOL);
                ItemStack f = new ItemStack(Material.RED_WOOL);
                ItemMeta tm = t.getItemMeta();
                ItemMeta fm = f.getItemMeta();
                ItemMeta bm = b.getItemMeta();
                bm.setDisplayName(ChatColor.GOLD + "Back");
                b.setItemMeta(bm);
                switch (e.getCurrentItem().getType()) {
                    case COMPASS:
                        boolean ce = getConfig.getBoolean("CompassEnabled");
                        if (e.getAction() == InventoryAction.DROP_ONE_SLOT) {
                            ItemMeta cm = e.getCurrentItem().getItemMeta();
                            if (ce) {
                                Huntervsspeed.cl.set(1, ChatColor.RED + "Disabled");
                                cm.setLore(Huntervsspeed.cl);
                                getConfig.set("CompassEnabled", false);
                            } else {
                                Huntervsspeed.cl.set(1, ChatColor.GREEN + "Enabled");
                                cm.setLore(Huntervsspeed.cl);
                                getConfig.set("CompassEnabled", true);
                            }
                            e.getCurrentItem().setItemMeta(cm);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                            Huntervsspeed.plugin.saveConfig();
                        } else {
                            if (ce) {
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                                Inventory CS = Bukkit.createInventory(p, 27, ChatColor.GOLD + "Compass Settings");
                                boolean swc = getConfig.getBoolean("StartWithCompass");
                                boolean syc = getConfig.getBoolean("ShowYCompass");
                                boolean kc = getConfig.getBoolean("KeepCompass");
                                if (kc) {
                                    tm.setDisplayName(ChatColor.GOLD + "Keep Compass");
                                    t.setItemMeta(tm);
                                    CS.setItem(15, t);
                                } else {
                                    fm.setDisplayName(ChatColor.GOLD + "Keep Compass");
                                    f.setItemMeta(fm);
                                    CS.setItem(15, f);
                                }
                                if (swc) {
                                    tm.setDisplayName(ChatColor.GOLD + "Start with compass");
                                    t.setItemMeta(tm);
                                    CS.setItem(11, t);
                                } else {
                                    fm.setDisplayName(ChatColor.GOLD + "Start with compass");
                                    f.setItemMeta(fm);
                                    CS.setItem(11, f);
                                }
                                if (syc) {
                                    tm.setDisplayName(ChatColor.GOLD + "Show Y level");
                                    t.setItemMeta(tm);
                                    CS.setItem(13, t);
                                } else {
                                    fm.setDisplayName(ChatColor.GOLD + "Show Y level");
                                    f.setItemMeta(fm);
                                    CS.setItem(13, f);
                                }
                                CS.setItem(26, b);
                                p.openInventory(CS);
                            }
                        }
                        break;
                    case IRON_SWORD:
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                        Inventory SS = Bukkit.createInventory(p, 27, ChatColor.GOLD + "Game Settings");
                        boolean kod = getConfig.getBoolean("KickOnDie");
                        boolean sS = getConfig.getBoolean("spectateBlind");
                        boolean d = getConfig.getBoolean("delete");

                        int time = getConfig.getInt("seconds");
                        Huntervsspeed.timeLore.set(0, ChatColor.GRAY + "Press q to set time to start game");
                        Huntervsspeed.timeLore.set(1, ChatColor.AQUA + "Time to start is " + ChatColor.GOLD + time + " Seconds");
                        ItemStack T = new ItemStack(Material.CLOCK);
                        ItemMeta TM = T.getItemMeta();
                        TM.setLore(Huntervsspeed.timeLore);
                        TM.setDisplayName(ChatColor.AQUA + "Set Timer");
                        T.setItemMeta(TM);
                        SS.setItem(10, T);

                        if (kod) {
                            tm.setDisplayName(ChatColor.GOLD + "Kick SpeedRunner on death");
                            t.setItemMeta(tm);
                            SS.setItem(12, t);
                        } else {
                            fm.setDisplayName(ChatColor.GOLD + "Kick SpeedRunner on death");
                            f.setItemMeta(fm);
                            SS.setItem(12, f);
                        }

                        if (sS) {
                            tm.setDisplayName(ChatColor.GOLD + "Blind on join");
                            t.setItemMeta(tm);
                            SS.setItem(14, t);
                        } else {
                            fm.setDisplayName(ChatColor.GOLD + "Blind on join");
                            f.setItemMeta(fm);
                            SS.setItem(14, f);
                        }

                        if (d) {
                            tm.setDisplayName(ChatColor.GOLD + "Delete world on plugin start");
                            t.setItemMeta(tm);
                            SS.setItem(16, t);
                        } else {
                            fm.setDisplayName(ChatColor.GOLD + "Delete world on plugin start");
                            f.setItemMeta(fm);
                            SS.setItem(16, f);
                        }


                        SS.setItem(26, b);
                        p.openInventory(SS);
                        break;
                }

            }
        }

    }
}
