package com.OakleyPlugins.huntervsspeed;

import com.OakleyPlugins.huntervsspeed.Events.*;
import com.oakleyplugins.randommobspawnhvsp.RandomMobSpawnHvsP;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;


@SuppressWarnings("ConstantConditions")
public final class Huntervsspeed extends JavaPlugin implements Listener {
    public static ArrayList<UUID> speed = new ArrayList<>();
    public static ArrayList<UUID> hunt = new ArrayList<>();
    public static ArrayList<UUID> isFrozen = new ArrayList<>();
    public static ArrayList<UUID> hasCompass = new ArrayList<>();
    public static ArrayList<Player> IsSpectating = new ArrayList<>();
    public static ArrayList<String> cl = new ArrayList<>();
    public static ArrayList<String> timeLore = new ArrayList<>();
    public static Map<UUID, LivingEntity> offline = new HashMap<>();
    public static Map<LivingEntity, UUID> offlineEntity = new HashMap<>();
    public static Map<UUID, ItemStack[]> offlineInv = new HashMap<>();
    public static Map<UUID,Integer> pointingTo = new HashMap<>();
    public static ArrayList<UUID> InvClear = new ArrayList<>();
    public static Location spawn;
    public static Player waitingForInputP;
    public static boolean waitingForInput;
    public static boolean inGame = false;
    public static Huntervsspeed plugin;
    public static Scoreboard s;
    public static Objective obj;
    public static int time;
    public static int speedOrgSize;
    public static boolean ce;
    public static int timeSecs;
    public static Map<UUID,Location> portalLocation = new HashMap<>();
    public static boolean RandomSpawnFound = false;
    public static boolean startCDown = false;
    public static ArrayList<Inventory> RemoveRunner = new ArrayList<>();
    public static ArrayList<Inventory> AddRunner = new ArrayList<>();
    public static ArrayList<Inventory> TpMenu = new ArrayList<>();
    private final List<Supplier<Listener>> list = Arrays.asList(OnPlayerRespawn::new, OnAsyncPlayerChat::new, OnBlockBreak::new, OnEntityDeath::new, OnInventoryClick::new, OnPlayerDeath::new, OnPlayerJoin::new, OnPlayerInteract::new, OnPlayerMove::new, OnDamage::new, OnPlayerLeave::new, OnEntityDamageByEntity::new);

    @Override
    public void onEnable() {
        Huntervsspeed.timeLore.add(null);
        Huntervsspeed.timeLore.add(null);
        cl.add(null);
        cl.add(null);
        if (getServer().getPluginManager().getPlugin("RandomMobSpawnHvsP")!=null){
            RandomSpawnFound = true;
        }
        ce = getConfig().getBoolean("CompassEnabled");
        plugin = this;
        getServer().getPluginManager().addPermission(new Permission("HvsP.Settings"));
        getServer().getPluginManager().addPermission(new Permission("HvsP.Setup"));
        list.stream().map(Supplier::get).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        boolean d = getConfig().getBoolean("delete");
        if (d) {
            try {
                FileUtils.deleteDirectory(new File("world"));
                FileUtils.deleteDirectory(new File("world_nether"));
                FileUtils.deleteDirectory(new File("world_the_end"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String getSpeed(){
        return ChatColor.RESET + "" + speed.size() + " / " + ChatColor.GOLD + speedOrgSize;
    }
    public void timer(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    if (startCDown){
                        timeSecs++;
                }else{
                        timeSecs--;
                    }
            UpdateScore();
        },20,20);
    }

    public static void OpenSettings(Player p){
        Inventory s = Bukkit.createInventory(p, 27, ChatColor.GOLD + "Settings");
        if (ce) {
            cl.set(1, ChatColor.GREEN + "Enabled");
        } else {
            cl.set(1, ChatColor.RED + "Disabled");
        }
        cl.set(0, ChatColor.GRAY + "Press q to enable/disable");
        ItemStack SS = new ItemStack(Material.IRON_SWORD);
        ItemStack C = new ItemStack(Material.COMPASS);
        ItemMeta SSM = SS.getItemMeta();
        ItemMeta CM = C.getItemMeta();
        CM.setLore(cl);
        SSM.setDisplayName(ChatColor.AQUA + "Game Settings");
        CM.setDisplayName(ChatColor.AQUA + "Compass Settings");
        SS.setItemMeta(SSM);
        C.setItemMeta(CM);
        s.setItem(12, C);
        s.setItem(14, SS);
        p.openInventory(s);
    }

    public static void OpenTpMenu(Player p){
        TpMenu.clear();
        ArrayList<ItemStack> heads = new ArrayList<>();
        ItemStack S = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta f = (SkullMeta) S.getItemMeta();
        for (Player all : Bukkit.getOnlinePlayers()) {
            if(all != p) {
                f.setOwningPlayer(all);
                f.setDisplayName(all.getDisplayName());
                S.setItemMeta(f);
                heads.add(S);
            }
        }

        int inventoriesNeeded = ((int)Math.ceil(((double)heads.size()) / 27));
        for(int i = 0; i < inventoriesNeeded;i++){
            int page = i + 1;
            Inventory inv = Bukkit.createInventory(null, 36,ChatColor.RED + "Teleport To Player " + ChatColor.GOLD + "Page:" + page);
            TpMenu.add(inv);
            for (int k = 0; k < 27; k++) {
                try {
                    ItemStack head;
                    if(i != 0){
                        int o = 27*i;
                        int item = o + k;
                        head = heads.get(item);
                    }else {
                        head = heads.get(k);
                    }
                    TpMenu.get(i).addItem(head);
                }catch (IndexOutOfBoundsException e){
                    //p
                }
            }
        }
        for (Inventory inv : RemoveRunner) {

            ItemStack previous = new ItemStack(Material.BLAZE_ROD);
            ItemMeta previousMeta = previous.getItemMeta();
            previousMeta.setDisplayName(ChatColor.GOLD + "<< Previous Page");
            previous.setItemMeta(previousMeta);

            ItemStack close = new ItemStack(Material.BARRIER);
            ItemMeta closeMeta = close.getItemMeta();
            closeMeta.setDisplayName(ChatColor.RED + "Close Menu");
            close.setItemMeta(closeMeta);

            ItemStack next = new ItemStack(Material.ARROW);
            ItemMeta nextMeta = next.getItemMeta();
            nextMeta.setDisplayName(ChatColor.GOLD + "Next Page >>");
            next.setItemMeta(nextMeta);

            ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta placeholderMeta = placeholder.getItemMeta();
            placeholderMeta.setDisplayName("||");
            placeholder.setItemMeta(placeholderMeta);
            inv.setItem(27,placeholder);
            inv.setItem(28,placeholder);
            inv.setItem(29,placeholder);
            inv.setItem(30, previous);
            inv.setItem(31, close);
            inv.setItem(32, next);
            inv.setItem(33,placeholder);
            inv.setItem(34,placeholder);
            inv.setItem(35,placeholder);
        }
        p.openInventory(TpMenu.get(0));
    }

    public static void OpenRemoveRunner(Player p){
        RemoveRunner.clear();
        ArrayList<ItemStack> heads = new ArrayList<>();
        for (UUID t : speed) {
            ItemStack S = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta f = (SkullMeta) S.getItemMeta();
            Player all = Bukkit.getPlayer(t);
                f.setOwningPlayer(all);
                f.setDisplayName(all.getDisplayName());
                S.setItemMeta(f);
                heads.add(S);
        }

        int inventoriesNeeded = ((int)Math.ceil(((double)heads.size()) / 27));
        for(int i = 0; i < inventoriesNeeded;i++){
            int page = i + 1;
            Inventory inv = Bukkit.createInventory(null, 36,ChatColor.RED + "RemoveRunners " + ChatColor.GOLD + "Page:" + page);
            RemoveRunner.add(inv);
            for (int k = 0; k < 27; k++) {
                try {
                    ItemStack head;
                    if(i != 0){
                        int o = 27*i;
                        int item = o + k;
                        head = heads.get(item);
                    }else {
                        head = heads.get(k);
                    }
                    RemoveRunner.get(i).setItem(k,head);
                }catch (IndexOutOfBoundsException e){
                    //p
                }
            }
        }
        for (Inventory inv : RemoveRunner) {

            ItemStack previous = new ItemStack(Material.BLAZE_ROD);
            ItemMeta previousMeta = previous.getItemMeta();
            previousMeta.setDisplayName(ChatColor.GOLD + "<< Previous Page");
            previous.setItemMeta(previousMeta);

            ItemStack close = new ItemStack(Material.BARRIER);
            ItemMeta closeMeta = close.getItemMeta();
            closeMeta.setDisplayName(ChatColor.RED + "Close Menu");
            close.setItemMeta(closeMeta);

            ItemStack next = new ItemStack(Material.ARROW);
            ItemMeta nextMeta = next.getItemMeta();
            nextMeta.setDisplayName(ChatColor.GOLD + "Next Page >>");
            next.setItemMeta(nextMeta);

            ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta placeholderMeta = placeholder.getItemMeta();
            placeholderMeta.setDisplayName("||");
            placeholder.setItemMeta(placeholderMeta);
            inv.setItem(27,placeholder);
            inv.setItem(28,placeholder);
            inv.setItem(29,placeholder);
            inv.setItem(30, previous);
            inv.setItem(31, close);
            inv.setItem(32, next);
            inv.setItem(33,placeholder);
            inv.setItem(34,placeholder);
            inv.setItem(35,placeholder);
        }
        p.openInventory(RemoveRunner.get(0));
    }

    public static void OpenAddRunner(Player p){
        AddRunner.clear();
        ArrayList<ItemStack> heads = new ArrayList<>();
        ItemStack S = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta f = (SkullMeta) S.getItemMeta();
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (!(speed.contains(all.getUniqueId()))) {
               f.setOwningPlayer(all);
               f.setDisplayName(all.getDisplayName());
               S.setItemMeta(f);
               heads.add(S);
            }
        }

        int inventoriesNeeded = ((int)Math.ceil(((double)heads.size()) / 27));
        for(int i = 0; i < inventoriesNeeded;i++){
            int page = i + 1;
            Inventory inv = Bukkit.createInventory(null, 36,ChatColor.GREEN + "AddRunners " + ChatColor.GOLD + "Page:" + page);
            AddRunner.add(inv);
            for (int k = 0; k < 27; k++) {
                try {
                    ItemStack head;
                    if(i != 0){
                        int o = 27*i;
                        int item = o + k;
                        head = heads.get(item);
                    }else {
                        head = heads.get(k);
                    }
                    AddRunner.get(i).setItem(k,head);
                }catch (IndexOutOfBoundsException e){
                    //i am a comment
                }
            }
        }
        for (Inventory inv : AddRunner) {

            ItemStack previous = new ItemStack(Material.BLAZE_ROD);
            ItemMeta previousMeta = previous.getItemMeta();
            previousMeta.setDisplayName(ChatColor.GOLD + "<< Previous Page");
            previous.setItemMeta(previousMeta);

            ItemStack close = new ItemStack(Material.BARRIER);
            ItemMeta closeMeta = close.getItemMeta();
            closeMeta.setDisplayName(ChatColor.RED + "Close Menu");
            close.setItemMeta(closeMeta);

            ItemStack next = new ItemStack(Material.ARROW);
            ItemMeta nextMeta = next.getItemMeta();
            nextMeta.setDisplayName(ChatColor.GOLD + "Next Page >>");
            next.setItemMeta(nextMeta);

            ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta placeholderMeta = placeholder.getItemMeta();
            placeholderMeta.setDisplayName("||");
            placeholder.setItemMeta(placeholderMeta);
            inv.setItem(27,placeholder);
            inv.setItem(28,placeholder);
            inv.setItem(29,placeholder);
            inv.setItem(30, previous);
            inv.setItem(31, close);
            inv.setItem(32, next);
            inv.setItem(33,placeholder);
            inv.setItem(34,placeholder);
            inv.setItem(35,placeholder);
        }
        p.openInventory(AddRunner.get(0));
    }

    public static void UpdateScore(){
        s = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = s.registerNewObjective("Board","Dummy",ChatColor.GOLD + "Hunter vs SpeedRunner");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score b = obj.getScore("");
        Score b1 = obj.getScore(" ");
        Score b2 = obj.getScore("  ");
        Score RunnersLeft = obj.getScore("SpeedRunners Left: " + getSpeed());
        Score Hunters = obj.getScore("Hunter" +
                "s: " + ChatColor.GOLD + hunt.size());
        Score TimeElapsed;
        Score UntilSpawn;
        int timeMin = timeSecs / 60;
        int timeHour = timeMin / 60;
        if(timeMin > 0){
            int timeSecM = timeSecs % 60;
            String timeSecStr = "" + timeSecM;
            if(timeSecM < 10){
                timeSecStr = "0" + timeSecM;
            }
            if(timeHour > 0){
                int timeMinH = timeMin % 60;
                String timeMinStr = "" + timeMinH;
                if(timeMinH < 10){
                    timeMinStr = "0" + timeMinH;
                }
                if(startCDown) {
                    TimeElapsed = obj.getScore("Game time: " + ChatColor.GOLD + timeHour + ":" + timeMinStr + ":" + timeSecStr);
                }else {
                    TimeElapsed = obj.getScore("Starting in: " + ChatColor.GOLD + timeHour + ":" + timeMinStr + ":" + timeSecStr);
                }
            }else{
                if(startCDown) {
                    TimeElapsed = obj.getScore("Game time: " + ChatColor.GOLD + timeMin + ":" + timeSecStr);
                }else {
                    TimeElapsed = obj.getScore("Starting in: " + ChatColor.GOLD + timeMin + ":" + timeSecStr);
                }
            }
        }else{
            if(startCDown) {
                TimeElapsed = obj.getScore("Game time: " + ChatColor.GOLD + timeSecs);
            }else {
                TimeElapsed = obj.getScore("Starting in: " + ChatColor.GOLD + timeSecs);
            }
        }
        if(RandomSpawnFound){
            if(startCDown) {
                if (RandomMobSpawnHvsP.enabled) {
                    int timeMinS = RandomMobSpawnHvsP.timeCount / 60;
                    int timeHourS = timeMinS / 60;
                    if (timeMinS > 0) {
                        int timeSecM = RandomMobSpawnHvsP.timeCount % 60;
                        String timeSecStr = "" + timeSecM;
                        if (timeSecM < 10) {
                            timeSecStr = "0" + timeSecM;
                        }
                        if (timeHourS > 0) {
                            int timeMinH = timeMinS % 60;
                            String timeMinStr = "" + timeMinH;
                            if (timeMinH < 10) {
                                timeMinStr = "0" + timeMinH;
                            }
                            UntilSpawn = obj.getScore("Next Spawn: " + ChatColor.GOLD + timeHour + ":" + timeMinStr + ":" + timeSecStr);
                        } else {
                            UntilSpawn = obj.getScore("Next Spawn: " + ChatColor.GOLD + timeMin + ":" + timeSecStr);
                        }
                    } else {
                        UntilSpawn = obj.getScore("Next Spawn: " + ChatColor.GOLD + RandomMobSpawnHvsP.timeCount);
                    }
                    UntilSpawn.setScore(1);
                }
            }
        }
        b.setScore(7);
        RunnersLeft.setScore(4);
        b1.setScore(5);
        Hunters.setScore(2);
        b2.setScore(3);
        TimeElapsed.setScore(6);
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setScoreboard(s);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("AddRunner")) {
            if (inGame) {
                sender.sendMessage(ChatColor.RED + "Game has already started!");
                return true;
            } else {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (p.hasPermission("HvsP.Setup")) {
                        OpenAddRunner(p);
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have permission to access this command");
                    }
                } else {
                    System.out.println("You must be a player to access this command");
                }
            }
        }
        if (command.getName().equalsIgnoreCase("RemoveRunner")) {
            if (inGame) {
                sender.sendMessage(ChatColor.RED + "Game has already started!");
                return true;
            } else {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (p.hasPermission("HvsP.Setup")) {
                        OpenRemoveRunner(p);
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have permission to access this command");
                    }
                } else {
                    System.out.println("You must be a player to access this command");
                }
            }
        }
        if (command.getName().equalsIgnoreCase("TpTo")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (IsSpectating.contains(p)) {
                    if (args.length == 0) {
                        OpenTpMenu(p);
                    } else {
                        Player t = Bukkit.getPlayerExact(args[0]);
                        p.teleport(t);
                        p.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.GOLD + t.getDisplayName());
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "You must be a spectator to access this command");
                }
            } else {
                System.out.println("You must be a player to access this command");
            }
        }
        if (command.getName().equalsIgnoreCase("settings")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("HvsP.Settings")) {
                    OpenSettings(p);
                } else {
                    p.sendMessage(ChatColor.RED + "You don't have permission to access this command");
                }
            } else {
                System.out.println("You must be a player to access this command");
            }
        }
        if (command.getName().equalsIgnoreCase("StartGame")) {
            if (sender.hasPermission("HvsP.Setup")) {
                if (!inGame) {
                    if (speed.size() > 0) {
                        if (Bukkit.getOnlinePlayers().size() > 1) {
                            ArrayList<UUID> online = new ArrayList<>();
                            for (Player d : Bukkit.getOnlinePlayers()) {
                                online.add(d.getUniqueId());
                            }
                            if (!(speed.containsAll(online))) {
                                inGame = true;
                                time = getConfig().getInt("seconds");
                                int ticks = time * 20;
                                boolean swc = getConfig().getBoolean("StartWithCompass");
                                boolean ce = getConfig().getBoolean("CompassEnabled");
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    int timeM = time / 60;
                                    int timeS = time % 60;
                                    if (timeM == 0) {
                                        all.sendTitle("", ChatColor.GREEN + "Starts in " + ChatColor.GOLD + timeS + " Seconds", 10, 40, 10);
                                    } else {
                                        if (timeS < 10) {
                                            all.sendTitle("", ChatColor.GREEN + "Starts in " + ChatColor.GOLD + timeM + ":0" + timeS + " Minutes", 10, 40, 10);
                                        } else {
                                            all.sendTitle("", ChatColor.GREEN + "Starts in " + ChatColor.GOLD + timeM + ":" + timeS + " Minutes", 10, 40, 10);
                                        }
                                    }
                                    hunt.add(all.getUniqueId());
                                    isFrozen.add(all.getUniqueId());
                                    all.setGameMode(GameMode.SURVIVAL);
                                    all.setGameMode(GameMode.SPECTATOR);
                                    for (UUID j : speed) {
                                        Player SpeedRunner = Bukkit.getPlayer(j);
                                        SpeedRunner.setGameMode(GameMode.SURVIVAL);
                                        SpeedRunner.teleport(spawn);
                                        hunt.remove(SpeedRunner.getUniqueId());
                                        isFrozen.remove(SpeedRunner.getUniqueId());
                                        SpeedRunner.removePotionEffect(PotionEffectType.BLINDNESS);
                                        for (UUID k : hunt) {
                                            Player Hunter = Bukkit.getPlayer(k);
                                            if (!(speed.contains(Hunter.getUniqueId()))) {
                                                if (swc & ce) {
                                                    Hunter.getInventory().clear();
                                                    Hunter.getInventory().addItem(new ItemStack(Material.COMPASS));
                                                }
                                                Hunter.teleport(Hunter.getLocation().add(1, 256, 1));
                                                Hunter.hidePlayer(plugin, SpeedRunner);
                                                Hunter.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
                                                Bukkit.getScheduler().runTaskLater(this, () -> Hunter.showPlayer(plugin, SpeedRunner), ticks);
                                                Bukkit.getScheduler().runTaskLater(this, () -> Hunter.teleport(spawn), ticks);
                                                Bukkit.getScheduler().runTaskLater(this, () -> Hunter.setGameMode(GameMode.SURVIVAL), ticks);
                                                Bukkit.getScheduler().runTaskLater(this, () -> Hunter.removePotionEffect(PotionEffectType.BLINDNESS), ticks);
                                                Bukkit.getScheduler().runTaskLater(this, () -> isFrozen.remove(Hunter.getUniqueId()), ticks);
                                            }
                                        }
                                    }
                                    all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                                    Bukkit.getScheduler().runTaskLater(this, () -> all.sendTitle(ChatColor.GOLD + "Go!", "", 10, 40, 10), ticks);
                                    Bukkit.getScheduler().runTaskLater(this, () -> all.playSound(all.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f), ticks);
                                }
                                for (int i = 0; i < ticks; i += 20) {
                                    int s = time - i / 20;
                                    if (s < 11) {
                                        Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.broadcastMessage(ChatColor.GREEN + "Starting in " + ChatColor.GOLD + s), i);
                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                            Bukkit.getScheduler().runTaskLater(this, () -> all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f), i);
                                        }
                                    }
                                }
                                if(RandomSpawnFound){
                                    Bukkit.getScheduler().runTaskLater(this, RandomMobSpawnHvsP::RandomMobSpawn,ticks);
                                }
                                Bukkit.getScheduler().runTaskLater(this, () -> timeSecs = 0 ,ticks);
                                Bukkit.getScheduler().runTaskLater(this, () -> startCDown = true ,ticks);
                                speedOrgSize = speed.size();
                                timeSecs = time;
                                UpdateScore();
                                timer();
                            } else {
                                sender.sendMessage(ChatColor.RED + "Not all players can be SpeedRunner Remove a runner with /RemoveRunner");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "There is not enough players to start");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "You must add speed runners with command (/AddRunner)");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Game has already started");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have permission to access this command");
            }
        }
        return false;
    }
}