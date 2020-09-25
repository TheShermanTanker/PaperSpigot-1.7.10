/*      */ package org.bukkit.craftbukkit.v1_7_R4;
/*      */ import com.avaje.ebean.config.DataSourceConfig;
/*      */ import com.avaje.ebean.config.ServerConfig;
/*      */ import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.MapMaker;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FilenameFilter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.md_5.bungee.api.chat.BaseComponent;
/*      */ import net.minecraft.server.v1_7_R4.ChunkCoordinates;
/*      */ import net.minecraft.server.v1_7_R4.CommandAbstract;
/*      */ import net.minecraft.server.v1_7_R4.CommandBan;
/*      */ import net.minecraft.server.v1_7_R4.CommandBanIp;
/*      */ import net.minecraft.server.v1_7_R4.CommandBanList;
/*      */ import net.minecraft.server.v1_7_R4.CommandClear;
/*      */ import net.minecraft.server.v1_7_R4.CommandDeop;
/*      */ import net.minecraft.server.v1_7_R4.CommandEffect;
/*      */ import net.minecraft.server.v1_7_R4.CommandGamemodeDefault;
/*      */ import net.minecraft.server.v1_7_R4.CommandGamerule;
/*      */ import net.minecraft.server.v1_7_R4.CommandGive;
/*      */ import net.minecraft.server.v1_7_R4.CommandHelp;
/*      */ import net.minecraft.server.v1_7_R4.CommandIdleTimeout;
/*      */ import net.minecraft.server.v1_7_R4.CommandKick;
/*      */ import net.minecraft.server.v1_7_R4.CommandKill;
/*      */ import net.minecraft.server.v1_7_R4.CommandMe;
/*      */ import net.minecraft.server.v1_7_R4.CommandNetstat;
/*      */ import net.minecraft.server.v1_7_R4.CommandOp;
/*      */ import net.minecraft.server.v1_7_R4.CommandPardon;
/*      */ import net.minecraft.server.v1_7_R4.CommandPardonIP;
/*      */ import net.minecraft.server.v1_7_R4.CommandPlaySound;
/*      */ import net.minecraft.server.v1_7_R4.CommandSay;
/*      */ import net.minecraft.server.v1_7_R4.CommandScoreboard;
/*      */ import net.minecraft.server.v1_7_R4.CommandSetBlock;
/*      */ import net.minecraft.server.v1_7_R4.CommandSetWorldSpawn;
/*      */ import net.minecraft.server.v1_7_R4.CommandSpawnpoint;
/*      */ import net.minecraft.server.v1_7_R4.CommandTell;
/*      */ import net.minecraft.server.v1_7_R4.CommandTellRaw;
/*      */ import net.minecraft.server.v1_7_R4.CommandTestFor;
/*      */ import net.minecraft.server.v1_7_R4.CommandToggleDownfall;
/*      */ import net.minecraft.server.v1_7_R4.CommandTp;
/*      */ import net.minecraft.server.v1_7_R4.CommandWeather;
/*      */ import net.minecraft.server.v1_7_R4.CommandWhitelist;
/*      */ import net.minecraft.server.v1_7_R4.ConvertProgressUpdater;
/*      */ import net.minecraft.server.v1_7_R4.CraftingManager;
/*      */ import net.minecraft.server.v1_7_R4.DedicatedPlayerList;
/*      */ import net.minecraft.server.v1_7_R4.DedicatedServer;
/*      */ import net.minecraft.server.v1_7_R4.Enchantment;
/*      */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*      */ import net.minecraft.server.v1_7_R4.EntityTracker;
/*      */ import net.minecraft.server.v1_7_R4.EnumDifficulty;
/*      */ import net.minecraft.server.v1_7_R4.EnumGamemode;
/*      */ import net.minecraft.server.v1_7_R4.ExceptionWorldConflict;
/*      */ import net.minecraft.server.v1_7_R4.ICommandListener;
/*      */ import net.minecraft.server.v1_7_R4.IProgressUpdate;
/*      */ import net.minecraft.server.v1_7_R4.IWorldAccess;
/*      */ import net.minecraft.server.v1_7_R4.Item;
/*      */ import net.minecraft.server.v1_7_R4.ItemStack;
/*      */ import net.minecraft.server.v1_7_R4.Items;
/*      */ import net.minecraft.server.v1_7_R4.JsonListEntry;
/*      */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*      */ import net.minecraft.server.v1_7_R4.MobEffectList;
/*      */ import net.minecraft.server.v1_7_R4.PersistentCollection;
/*      */ import net.minecraft.server.v1_7_R4.PlayerList;
/*      */ import net.minecraft.server.v1_7_R4.PropertyManager;
/*      */ import net.minecraft.server.v1_7_R4.RecipesFurnace;
/*      */ import net.minecraft.server.v1_7_R4.RegionFile;
/*      */ import net.minecraft.server.v1_7_R4.RegionFileCache;
/*      */ import net.minecraft.server.v1_7_R4.ServerCommand;
/*      */ import net.minecraft.server.v1_7_R4.ServerNBTManager;
/*      */ import net.minecraft.server.v1_7_R4.World;
/*      */ import net.minecraft.server.v1_7_R4.WorldLoaderServer;
/*      */ import net.minecraft.server.v1_7_R4.WorldManager;
/*      */ import net.minecraft.server.v1_7_R4.WorldMap;
/*      */ import net.minecraft.server.v1_7_R4.WorldNBTStorage;
/*      */ import net.minecraft.server.v1_7_R4.WorldServer;
/*      */ import net.minecraft.server.v1_7_R4.WorldSettings;
/*      */ import net.minecraft.server.v1_7_R4.WorldType;
/*      */ import net.minecraft.util.com.google.common.base.Charsets;
/*      */ import net.minecraft.util.com.google.common.base.Function;
/*      */ import net.minecraft.util.com.google.common.collect.Lists;
/*      */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*      */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*      */ import net.minecraft.util.io.netty.buffer.ByteBufOutputStream;
/*      */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*      */ import net.minecraft.util.io.netty.handler.codec.base64.Base64;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.bukkit.BanList;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.GameMode;
/*      */ import org.bukkit.OfflinePlayer;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.UnsafeValues;
/*      */ import org.bukkit.Warning;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.WorldCreator;
/*      */ import org.bukkit.command.Command;
/*      */ import org.bukkit.command.CommandException;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.command.ConsoleCommandSender;
/*      */ import org.bukkit.command.PluginCommand;
/*      */ import org.bukkit.command.SimpleCommandMap;
/*      */ import org.bukkit.configuration.Configuration;
/*      */ import org.bukkit.configuration.ConfigurationSection;
/*      */ import org.bukkit.configuration.file.YamlConfiguration;
/*      */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
/*      */ import org.bukkit.conversations.Conversable;
/*      */ import org.bukkit.craftbukkit.Main;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.command.VanillaCommandWrapper;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.help.SimpleHelpMap;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftFurnaceRecipe;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryCustom;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemFactory;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftRecipe;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftShapedRecipe;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftShapelessRecipe;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.inventory.RecipeIterator;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.map.CraftMapView;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.metadata.EntityMetadataStore;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.metadata.PlayerMetadataStore;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.metadata.WorldMetadataStore;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.potion.CraftPotionBrewer;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.scheduler.CraftScheduler;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboardManager;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.updater.AutoUpdater;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftIconCache;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.util.DatFileFilter;
/*      */ import org.bukkit.enchantments.Enchantment;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.Event;
/*      */ import org.bukkit.event.inventory.InventoryType;
/*      */ import org.bukkit.event.player.PlayerChatTabCompleteEvent;
/*      */ import org.bukkit.event.world.WorldInitEvent;
/*      */ import org.bukkit.event.world.WorldLoadEvent;
/*      */ import org.bukkit.event.world.WorldSaveEvent;
/*      */ import org.bukkit.event.world.WorldUnloadEvent;
/*      */ import org.bukkit.generator.ChunkGenerator;
/*      */ import org.bukkit.help.HelpMap;
/*      */ import org.bukkit.inventory.FurnaceRecipe;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.InventoryHolder;
/*      */ import org.bukkit.inventory.ItemFactory;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.Recipe;
/*      */ import org.bukkit.inventory.ShapedRecipe;
/*      */ import org.bukkit.inventory.ShapelessRecipe;
/*      */ import org.bukkit.map.MapView;
/*      */ import org.bukkit.permissions.Permissible;
/*      */ import org.bukkit.permissions.Permission;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.plugin.PluginLoadOrder;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.plugin.ServicesManager;
/*      */ import org.bukkit.plugin.SimplePluginManager;
/*      */ import org.bukkit.plugin.messaging.Messenger;
/*      */ import org.bukkit.plugin.messaging.StandardMessenger;
/*      */ import org.bukkit.potion.Potion;
/*      */ import org.bukkit.potion.PotionBrewer;
/*      */ import org.bukkit.scheduler.BukkitScheduler;
/*      */ import org.bukkit.scheduler.BukkitWorker;
/*      */ import org.bukkit.scoreboard.ScoreboardManager;
/*      */ import org.bukkit.util.CachedServerIcon;
/*      */ import org.bukkit.util.StringUtil;
/*      */ import org.bukkit.util.permissions.DefaultPermissions;
/*      */ import org.github.paperspigot.PaperSpigotConfig;
/*      */ import org.spigotmc.SpigotConfig;
/*      */ import org.yaml.snakeyaml.Yaml;
/*      */ import org.yaml.snakeyaml.constructor.BaseConstructor;
/*      */ import org.yaml.snakeyaml.constructor.SafeConstructor;
/*      */ import org.yaml.snakeyaml.error.MarkedYAMLException;
/*      */ 
/*      */ public final class CraftServer implements Server {
/*  198 */   private static final Player[] EMPTY_PLAYER_ARRAY = new Player[0];
/*  199 */   private final String serverName = "CraftBukkit";
/*      */   private final String serverVersion;
/*  201 */   private final String bukkitVersion = Versioning.getBukkitVersion();
/*  202 */   private final Logger logger = Logger.getLogger("Minecraft");
/*  203 */   private final ServicesManager servicesManager = (ServicesManager)new SimpleServicesManager();
/*  204 */   private final CraftScheduler scheduler = new CraftScheduler();
/*  205 */   private final SimpleCommandMap commandMap = new SimpleCommandMap(this);
/*  206 */   private final SimpleHelpMap helpMap = new SimpleHelpMap(this);
/*  207 */   private final StandardMessenger messenger = new StandardMessenger();
/*  208 */   private final PluginManager pluginManager = (PluginManager)new SimplePluginManager(this, this.commandMap);
/*      */   protected final MinecraftServer console;
/*      */   protected final DedicatedPlayerList playerList;
/*  211 */   private final Map<String, World> worlds = new LinkedHashMap<String, World>();
/*      */   private YamlConfiguration configuration;
/*      */   private YamlConfiguration commandsConfiguration;
/*  214 */   private final Yaml yaml = new Yaml((BaseConstructor)new SafeConstructor());
/*  215 */   private final Map<UUID, OfflinePlayer> offlinePlayers = (new MapMaker()).softValues().makeMap();
/*      */   private final AutoUpdater updater;
/*  217 */   private final EntityMetadataStore entityMetadata = new EntityMetadataStore();
/*  218 */   private final PlayerMetadataStore playerMetadata = new PlayerMetadataStore();
/*  219 */   private final WorldMetadataStore worldMetadata = new WorldMetadataStore();
/*  220 */   private int monsterSpawn = -1;
/*  221 */   private int animalSpawn = -1;
/*  222 */   private int waterAnimalSpawn = -1;
/*  223 */   private int ambientSpawn = -1;
/*  224 */   public int chunkGCPeriod = -1;
/*  225 */   public int chunkGCLoadThresh = 0;
/*      */   private File container;
/*  227 */   private Warning.WarningState warningState = Warning.WarningState.DEFAULT;
/*  228 */   private final BooleanWrapper online = new BooleanWrapper();
/*      */   public CraftScoreboardManager scoreboardManager;
/*      */   public boolean playerCommandState;
/*      */   private boolean printSaveWarning;
/*      */   private CraftIconCache icon;
/*      */   private boolean overrideAllCommandBlockCommands = false;
/*  234 */   private final Pattern validUserPattern = Pattern.compile("^[a-zA-Z0-9_]{2,16}$");
/*  235 */   private final UUID invalidUserUUID = UUID.nameUUIDFromBytes("InvalidUsername".getBytes(Charsets.UTF_8)); private final List<CraftPlayer> playerView; private final Server.Spigot spigot;
/*      */   
/*      */   private final class BooleanWrapper {
/*      */     private BooleanWrapper() {}
/*      */     
/*      */     private boolean value = true; }
/*      */   
/*      */   static {
/*  243 */     ConfigurationSerialization.registerClass(CraftOfflinePlayer.class);
/*  244 */     CraftItemFactory.instance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCommandBlockOverride(String command) {
/*  336 */     return (this.overrideAllCommandBlockCommands || this.commandsConfiguration.getStringList("command-block-overrides").contains(command));
/*      */   }
/*      */   
/*      */   private File getConfigFile() {
/*  340 */     return (File)this.console.options.valueOf("bukkit-settings");
/*      */   }
/*      */   
/*      */   private File getCommandsConfigFile() {
/*  344 */     return (File)this.console.options.valueOf("commands-settings");
/*      */   }
/*      */   
/*      */   private void saveConfig() {
/*      */     try {
/*  349 */       this.configuration.save(getConfigFile());
/*  350 */     } catch (IOException ex) {
/*  351 */       Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + getConfigFile(), ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void saveCommandsConfig() {
/*      */     try {
/*  357 */       this.commandsConfiguration.save(getCommandsConfigFile());
/*  358 */     } catch (IOException ex) {
/*  359 */       Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + getCommandsConfigFile(), ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void loadPlugins() {
/*  364 */     this.pluginManager.registerInterface(JavaPluginLoader.class);
/*      */     
/*  366 */     File pluginFolder = (File)this.console.options.valueOf("plugins");
/*      */     
/*  368 */     if (pluginFolder.exists()) {
/*  369 */       Plugin[] plugins = this.pluginManager.loadPlugins(pluginFolder);
/*  370 */       for (Plugin plugin : plugins) {
/*      */         try {
/*  372 */           String message = String.format("Loading %s", new Object[] { plugin.getDescription().getFullName() });
/*  373 */           plugin.getLogger().info(message);
/*  374 */           plugin.onLoad();
/*  375 */         } catch (Throwable ex) {
/*  376 */           Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*      */         } 
/*      */       } 
/*      */     } else {
/*  380 */       pluginFolder.mkdir();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void enablePlugins(PluginLoadOrder type) {
/*  385 */     if (type == PluginLoadOrder.STARTUP) {
/*  386 */       this.helpMap.clear();
/*  387 */       this.helpMap.initializeGeneralTopics();
/*      */     } 
/*      */     
/*  390 */     Plugin[] plugins = this.pluginManager.getPlugins();
/*      */     
/*  392 */     for (Plugin plugin : plugins) {
/*  393 */       if (!plugin.isEnabled() && plugin.getDescription().getLoad() == type) {
/*  394 */         loadPlugin(plugin);
/*      */       }
/*      */     } 
/*      */     
/*  398 */     if (type == PluginLoadOrder.POSTWORLD) {
/*      */       
/*  400 */       setVanillaCommands(true);
/*  401 */       this.commandMap.setFallbackCommands();
/*  402 */       setVanillaCommands(false);
/*      */       
/*  404 */       this.commandMap.registerServerAliases();
/*  405 */       loadCustomPermissions();
/*  406 */       DefaultPermissions.registerCorePermissions();
/*  407 */       this.helpMap.initializeCommands();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void disablePlugins() {
/*  412 */     this.pluginManager.disablePlugins();
/*      */   }
/*      */ 
/*      */   
/*      */   private void tryRegister(VanillaCommandWrapper commandWrapper, boolean first) {
/*  417 */     if (SpigotConfig.replaceCommands.contains(commandWrapper.getName())) {
/*  418 */       if (first) {
/*  419 */         this.commandMap.register("minecraft", (Command)commandWrapper);
/*      */       }
/*  421 */     } else if (!first) {
/*  422 */       this.commandMap.register("minecraft", (Command)commandWrapper);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setVanillaCommands(boolean first) {
/*  428 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandAchievement(), "/achievement give <stat_name> [player]"), first);
/*  429 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandBan(), "/ban <playername> [reason]"), first);
/*  430 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandBanIp(), "/ban-ip <ip-address|playername>"), first);
/*  431 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandBanList(), "/banlist [ips]"), first);
/*  432 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandClear(), "/clear <playername> [item] [metadata]"), first);
/*  433 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandGamemodeDefault(), "/defaultgamemode <mode>"), first);
/*  434 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandDeop(), "/deop <playername>"), first);
/*  435 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandDifficulty(), "/difficulty <new difficulty>"), first);
/*  436 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandEffect(), "/effect <player> <effect|clear> [seconds] [amplifier]"), first);
/*  437 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandEnchant(), "/enchant <playername> <enchantment ID> [enchantment level]"), first);
/*  438 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandGamemode(), "/gamemode <mode> [player]"), first);
/*  439 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandGamerule(), "/gamerule <rulename> [true|false]"), first);
/*  440 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandGive(), "/give <playername> <item> [amount] [metadata] [dataTag]"), first);
/*  441 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandHelp(), "/help [page|commandname]"), first);
/*  442 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandIdleTimeout(), "/setidletimeout <Minutes until kick>"), first);
/*  443 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandKick(), "/kick <playername> [reason]"), first);
/*  444 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandKill(), "/kill [playername]"), first);
/*  445 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandList(), "/list"), first);
/*  446 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandMe(), "/me <actiontext>"), first);
/*  447 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandOp(), "/op <playername>"), first);
/*  448 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandPardon(), "/pardon <playername>"), first);
/*  449 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandPardonIP(), "/pardon-ip <ip-address>"), first);
/*  450 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandPlaySound(), "/playsound <sound> <playername> [x] [y] [z] [volume] [pitch] [minimumVolume]"), first);
/*  451 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandSay(), "/say <message>"), first);
/*  452 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandScoreboard(), "/scoreboard"), first);
/*  453 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandSeed(), "/seed"), first);
/*  454 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandSetBlock(), "/setblock <x> <y> <z> <tilename> [datavalue] [oldblockHandling] [dataTag]"), first);
/*  455 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandSetWorldSpawn(), "/setworldspawn [x] [y] [z]"), first);
/*  456 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandSpawnpoint(), "/spawnpoint <playername> [x] [y] [z]"), first);
/*  457 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandSpreadPlayers(), "/spreadplayers <x> <z> [spreadDistance] [maxRange] [respectTeams] <playernames>"), first);
/*  458 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandSummon(), "/summon <EntityName> [x] [y] [z] [dataTag]"), first);
/*  459 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandTp(), "/tp [player] <target>\n/tp [player] <x> <y> <z>"), first);
/*  460 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandTell(), "/tell <playername> <message>"), first);
/*  461 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandTellRaw(), "/tellraw <playername> <raw message>"), first);
/*  462 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandTestFor(), "/testfor <playername | selector> [dataTag]"), first);
/*  463 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandTestForBlock(), "/testforblock <x> <y> <z> <tilename> [datavalue] [dataTag]"), first);
/*  464 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandTime(), "/time set <value>\n/time add <value>"), first);
/*  465 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandToggleDownfall(), "/toggledownfall"), first);
/*  466 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandWeather(), "/weather <clear/rain/thunder> [duration in seconds]"), first);
/*  467 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandWhitelist(), "/whitelist (add|remove) <player>\n/whitelist (on|off|list|reload)"), first);
/*  468 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandXp(), "/xp <amount> [player]\n/xp <amount>L [player]"), first);
/*      */     
/*  470 */     tryRegister(new VanillaCommandWrapper((CommandAbstract)new CommandNetstat(), "/list"), first);
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadPlugin(Plugin plugin) {
/*      */     try {
/*  476 */       this.pluginManager.enablePlugin(plugin);
/*      */       
/*  478 */       List<Permission> perms = plugin.getDescription().getPermissions();
/*      */       
/*  480 */       for (Permission perm : perms) {
/*      */         try {
/*  482 */           this.pluginManager.addPermission(perm);
/*  483 */         } catch (IllegalArgumentException ex) {
/*  484 */           getLogger().log(Level.WARNING, "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + perm.getName() + "' but it's already registered", ex);
/*      */         } 
/*      */       } 
/*  487 */     } catch (Throwable ex) {
/*  488 */       Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getName() {
/*  494 */     return "CraftBukkit";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVersion() {
/*  499 */     return this.serverVersion + " (MC: " + this.console.getVersion() + ")";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getBukkitVersion() {
/*  504 */     return this.bukkitVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Player[] getOnlinePlayers() {
/*  511 */     return getOnlinePlayers().<Player>toArray(EMPTY_PLAYER_ARRAY);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<CraftPlayer> getOnlinePlayers() {
/*  516 */     return this.playerView;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Player getPlayer(String name) {
/*  522 */     Validate.notNull(name, "Name cannot be null");
/*      */ 
/*      */     
/*  525 */     Player found = getPlayerExact(name);
/*  526 */     if (found != null) {
/*  527 */       return found;
/*      */     }
/*      */     
/*  530 */     String lowerName = name.toLowerCase();
/*  531 */     int delta = Integer.MAX_VALUE;
/*  532 */     for (Player player : getOnlinePlayers()) {
/*  533 */       if (player.getName().toLowerCase().startsWith(lowerName)) {
/*  534 */         int curDelta = player.getName().length() - lowerName.length();
/*  535 */         if (curDelta < delta) {
/*  536 */           found = player;
/*  537 */           delta = curDelta;
/*      */         } 
/*  539 */         if (curDelta == 0)
/*      */           break; 
/*      */       } 
/*  542 */     }  return found;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Player getPlayerExact(String name) {
/*  549 */     EntityPlayer player = (EntityPlayer)this.playerList.playerMap.get(name);
/*  550 */     return (player != null) ? (Player)player.getBukkitEntity() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Player getPlayer(UUID id) {
/*  557 */     for (Player player : getOnlinePlayers()) {
/*  558 */       if (player.getUniqueId().equals(id)) {
/*  559 */         return player;
/*      */       }
/*      */     } 
/*      */     
/*  563 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public int broadcastMessage(String message) {
/*  568 */     return broadcast(message, "bukkit.broadcast.user");
/*      */   }
/*      */   
/*      */   public Player getPlayer(EntityPlayer entity) {
/*  572 */     return (Player)entity.playerConnection.getPlayer();
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public List<Player> matchPlayer(String partialName) {
/*  578 */     Validate.notNull(partialName, "PartialName cannot be null");
/*      */     
/*  580 */     List<Player> matchedPlayers = new ArrayList<Player>();
/*      */     
/*  582 */     for (Player iterPlayer : getOnlinePlayers()) {
/*  583 */       String iterPlayerName = iterPlayer.getName();
/*      */       
/*  585 */       if (partialName.equalsIgnoreCase(iterPlayerName)) {
/*      */         
/*  587 */         matchedPlayers.clear();
/*  588 */         matchedPlayers.add(iterPlayer);
/*      */         break;
/*      */       } 
/*  591 */       if (iterPlayerName.toLowerCase().contains(partialName.toLowerCase()))
/*      */       {
/*  593 */         matchedPlayers.add(iterPlayer);
/*      */       }
/*      */     } 
/*      */     
/*  597 */     return matchedPlayers;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/*  602 */     return this.playerList.getMaxPlayers();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPort() {
/*  609 */     return getConfigInt("server-port", 25565);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getViewDistance() {
/*  614 */     return getConfigInt("view-distance", 10);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getIp() {
/*  619 */     return getConfigString("server-ip", "");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerName() {
/*  624 */     return getConfigString("server-name", "Unknown Server");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerId() {
/*  629 */     return getConfigString("server-id", "unnamed");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getWorldType() {
/*  634 */     return getConfigString("level-type", "DEFAULT");
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getGenerateStructures() {
/*  639 */     return getConfigBoolean("generate-structures", true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAllowEnd() {
/*  644 */     return this.configuration.getBoolean("settings.allow-end");
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAllowNether() {
/*  649 */     return getConfigBoolean("allow-nether", true);
/*      */   }
/*      */   
/*      */   public boolean getWarnOnOverload() {
/*  653 */     return this.configuration.getBoolean("settings.warn-on-overload");
/*      */   }
/*      */   
/*      */   public boolean getQueryPlugins() {
/*  657 */     return this.configuration.getBoolean("settings.query-plugins");
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasWhitelist() {
/*  662 */     return getConfigBoolean("white-list", false);
/*      */   }
/*      */ 
/*      */   
/*      */   private String getConfigString(String variable, String defaultValue) {
/*  667 */     return this.console.getPropertyManager().getString(variable, defaultValue);
/*      */   }
/*      */   
/*      */   private int getConfigInt(String variable, int defaultValue) {
/*  671 */     return this.console.getPropertyManager().getInt(variable, defaultValue);
/*      */   }
/*      */   
/*      */   private boolean getConfigBoolean(String variable, boolean defaultValue) {
/*  675 */     return this.console.getPropertyManager().getBoolean(variable, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUpdateFolder() {
/*  682 */     return this.configuration.getString("settings.update-folder", "update");
/*      */   }
/*      */ 
/*      */   
/*      */   public File getUpdateFolderFile() {
/*  687 */     return new File((File)this.console.options.valueOf("plugins"), this.configuration.getString("settings.update-folder", "update"));
/*      */   }
/*      */   
/*      */   public int getPingPacketLimit() {
/*  691 */     return this.configuration.getInt("settings.ping-packet-limit", 100);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getConnectionThrottle() {
/*  697 */     if (SpigotConfig.bungee) {
/*  698 */       return -1L;
/*      */     }
/*  700 */     return this.configuration.getInt("settings.connection-throttle");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTicksPerAnimalSpawns() {
/*  707 */     return this.configuration.getInt("ticks-per.animal-spawns");
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTicksPerMonsterSpawns() {
/*  712 */     return this.configuration.getInt("ticks-per.monster-spawns");
/*      */   }
/*      */ 
/*      */   
/*      */   public PluginManager getPluginManager() {
/*  717 */     return this.pluginManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public CraftScheduler getScheduler() {
/*  722 */     return this.scheduler;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServicesManager getServicesManager() {
/*  727 */     return this.servicesManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<World> getWorlds() {
/*  732 */     return new ArrayList<World>(this.worlds.values());
/*      */   }
/*      */   
/*      */   public DedicatedPlayerList getHandle() {
/*  736 */     return this.playerList;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean dispatchServerCommand(CommandSender sender, ServerCommand serverCommand) {
/*  741 */     if (sender instanceof Conversable) {
/*  742 */       Conversable conversable = (Conversable)sender;
/*      */       
/*  744 */       if (conversable.isConversing()) {
/*  745 */         conversable.acceptConversationInput(serverCommand.command);
/*  746 */         return true;
/*      */       } 
/*      */     } 
/*      */     try {
/*  750 */       this.playerCommandState = true;
/*  751 */       return dispatchCommand(sender, serverCommand.command);
/*  752 */     } catch (Exception ex) {
/*  753 */       getLogger().log(Level.WARNING, "Unexpected exception while parsing console command \"" + serverCommand.command + '"', ex);
/*  754 */       return false;
/*      */     } finally {
/*  756 */       this.playerCommandState = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean dispatchCommand(CommandSender sender, String commandLine) {
/*  762 */     Validate.notNull(sender, "Sender cannot be null");
/*  763 */     Validate.notNull(commandLine, "CommandLine cannot be null");
/*      */     
/*  765 */     if (this.commandMap.dispatch(sender, commandLine)) {
/*  766 */       return true;
/*      */     }
/*      */     
/*  769 */     sender.sendMessage(SpigotConfig.unknownCommandMessage);
/*      */     
/*  771 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void reload() {
/*  776 */     this.configuration = YamlConfiguration.loadConfiguration(getConfigFile());
/*  777 */     this.commandsConfiguration = YamlConfiguration.loadConfiguration(getCommandsConfigFile());
/*  778 */     PropertyManager config = new PropertyManager(this.console.options);
/*      */     
/*  780 */     ((DedicatedServer)this.console).propertyManager = config;
/*      */     
/*  782 */     boolean animals = config.getBoolean("spawn-animals", this.console.getSpawnAnimals());
/*  783 */     boolean monsters = config.getBoolean("spawn-monsters", (((WorldServer)this.console.worlds.get(0)).difficulty != EnumDifficulty.PEACEFUL));
/*  784 */     EnumDifficulty difficulty = EnumDifficulty.getById(config.getInt("difficulty", ((WorldServer)this.console.worlds.get(0)).difficulty.ordinal()));
/*      */     
/*  786 */     this.online.value = config.getBoolean("online-mode", this.console.getOnlineMode());
/*  787 */     this.console.setSpawnAnimals(config.getBoolean("spawn-animals", this.console.getSpawnAnimals()));
/*  788 */     this.console.setPvP(config.getBoolean("pvp", this.console.getPvP()));
/*  789 */     this.console.setAllowFlight(config.getBoolean("allow-flight", this.console.getAllowFlight()));
/*  790 */     this.console.setMotd(config.getString("motd", this.console.getMotd()));
/*  791 */     this.monsterSpawn = this.configuration.getInt("spawn-limits.monsters");
/*  792 */     this.animalSpawn = this.configuration.getInt("spawn-limits.animals");
/*  793 */     this.waterAnimalSpawn = this.configuration.getInt("spawn-limits.water-animals");
/*  794 */     this.ambientSpawn = this.configuration.getInt("spawn-limits.ambient");
/*  795 */     this.warningState = Warning.WarningState.value(this.configuration.getString("settings.deprecated-verbose"));
/*  796 */     this.printSaveWarning = false;
/*  797 */     this.console.autosavePeriod = this.configuration.getInt("ticks-per.autosave");
/*  798 */     this.chunkGCPeriod = this.configuration.getInt("chunk-gc.period-in-ticks");
/*  799 */     this.chunkGCLoadThresh = this.configuration.getInt("chunk-gc.load-threshold");
/*  800 */     loadIcon();
/*      */     
/*      */     try {
/*  803 */       this.playerList.getIPBans().load();
/*  804 */     } catch (IOException ex) {
/*  805 */       this.logger.log(Level.WARNING, "Failed to load banned-ips.json, " + ex.getMessage());
/*      */     } 
/*      */     try {
/*  808 */       this.playerList.getProfileBans().load();
/*  809 */     } catch (IOException ex) {
/*  810 */       this.logger.log(Level.WARNING, "Failed to load banned-players.json, " + ex.getMessage());
/*      */     } 
/*      */     
/*  813 */     SpigotConfig.init();
/*  814 */     PaperSpigotConfig.init();
/*  815 */     for (WorldServer world : this.console.worlds) {
/*  816 */       world.difficulty = difficulty;
/*  817 */       world.setSpawnFlags(monsters, animals);
/*  818 */       if (getTicksPerAnimalSpawns() < 0) {
/*  819 */         world.ticksPerAnimalSpawns = 400L;
/*      */       } else {
/*  821 */         world.ticksPerAnimalSpawns = getTicksPerAnimalSpawns();
/*      */       } 
/*      */       
/*  824 */       if (getTicksPerMonsterSpawns() < 0) {
/*  825 */         world.ticksPerMonsterSpawns = 1L;
/*      */       } else {
/*  827 */         world.ticksPerMonsterSpawns = getTicksPerMonsterSpawns();
/*      */       } 
/*  829 */       world.spigotConfig.init();
/*  830 */       world.paperSpigotConfig.init();
/*      */     } 
/*      */     
/*  833 */     this.pluginManager.clearPlugins();
/*  834 */     this.commandMap.clearCommands();
/*  835 */     resetRecipes();
/*  836 */     SpigotConfig.registerCommands();
/*  837 */     PaperSpigotConfig.registerCommands();
/*      */     
/*  839 */     this.overrideAllCommandBlockCommands = this.commandsConfiguration.getStringList("command-block-overrides").contains("*");
/*      */     
/*  841 */     int pollCount = 0;
/*      */ 
/*      */     
/*  844 */     while (pollCount < 50 && getScheduler().getActiveWorkers().size() > 0) {
/*      */       try {
/*  846 */         Thread.sleep(50L);
/*  847 */       } catch (InterruptedException e) {}
/*  848 */       pollCount++;
/*      */     } 
/*      */     
/*  851 */     List<BukkitWorker> overdueWorkers = getScheduler().getActiveWorkers();
/*  852 */     for (BukkitWorker worker : overdueWorkers) {
/*  853 */       Plugin plugin = worker.getOwner();
/*  854 */       String author = "<NoAuthorGiven>";
/*  855 */       if (plugin.getDescription().getAuthors().size() > 0) {
/*  856 */         author = plugin.getDescription().getAuthors().get(0);
/*      */       }
/*  858 */       getLogger().log(Level.SEVERE, String.format("Nag author: '%s' of '%s' about the following: %s", new Object[] { author, plugin.getDescription().getName(), "This plugin is not properly shutting down its async tasks when it is being reloaded.  This may cause conflicts with the newly loaded version of the plugin" }));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  865 */     loadPlugins();
/*  866 */     enablePlugins(PluginLoadOrder.STARTUP);
/*  867 */     enablePlugins(PluginLoadOrder.POSTWORLD);
/*      */   }
/*      */   
/*      */   private void loadIcon() {
/*  871 */     this.icon = new CraftIconCache(null);
/*      */     try {
/*  873 */       File file = new File(new File("."), "server-icon.png");
/*  874 */       if (file.isFile()) {
/*  875 */         this.icon = loadServerIcon0(file);
/*      */       }
/*  877 */     } catch (Exception ex) {
/*  878 */       getLogger().log(Level.WARNING, "Couldn't load server icon", ex);
/*      */     } 
/*      */   }
/*      */   private void loadCustomPermissions() {
/*      */     FileInputStream stream;
/*      */     Map<String, Map<String, Object>> perms;
/*  884 */     File file = new File(this.configuration.getString("settings.permissions-file"));
/*      */ 
/*      */     
/*      */     try {
/*  888 */       stream = new FileInputStream(file);
/*  889 */     } catch (FileNotFoundException ex) {
/*      */       try {
/*      */         return;
/*      */       } finally {
/*  893 */         Exception exception = null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  900 */       perms = (Map<String, Map<String, Object>>)this.yaml.load(stream);
/*  901 */     } catch (MarkedYAMLException ex) {
/*  902 */       getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + ex.toString());
/*      */       return;
/*  904 */     } catch (Throwable ex) {
/*  905 */       getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", ex);
/*      */       return;
/*      */     } finally {
/*      */       try {
/*  909 */         stream.close();
/*  910 */       } catch (IOException ex) {}
/*      */     } 
/*      */     
/*  913 */     if (perms == null) {
/*  914 */       getLogger().log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
/*      */       
/*      */       return;
/*      */     } 
/*  918 */     List<Permission> permsList = Permission.loadPermissions(perms, "Permission node '%s' in " + file + " is invalid", Permission.DEFAULT_PERMISSION);
/*      */     
/*  920 */     for (Permission perm : permsList) {
/*      */       try {
/*  922 */         this.pluginManager.addPermission(perm);
/*  923 */       } catch (IllegalArgumentException ex) {
/*  924 */         getLogger().log(Level.SEVERE, "Permission in " + file + " was already defined", ex);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  931 */     return "CraftServer{serverName=CraftBukkit,serverVersion=" + this.serverVersion + ",minecraftVersion=" + this.console.getVersion() + '}';
/*      */   }
/*      */   
/*      */   public World createWorld(String name, World.Environment environment) {
/*  935 */     return WorldCreator.name(name).environment(environment).createWorld();
/*      */   }
/*      */   
/*      */   public World createWorld(String name, World.Environment environment, long seed) {
/*  939 */     return WorldCreator.name(name).environment(environment).seed(seed).createWorld();
/*      */   }
/*      */   
/*      */   public World createWorld(String name, World.Environment environment, ChunkGenerator generator) {
/*  943 */     return WorldCreator.name(name).environment(environment).generator(generator).createWorld();
/*      */   }
/*      */   
/*      */   public World createWorld(String name, World.Environment environment, long seed, ChunkGenerator generator) {
/*  947 */     return WorldCreator.name(name).environment(environment).seed(seed).generator(generator).createWorld();
/*      */   }
/*      */ 
/*      */   
/*      */   public World createWorld(WorldCreator creator) {
/*  952 */     Validate.notNull(creator, "Creator may not be null");
/*      */     
/*  954 */     String name = creator.name();
/*  955 */     ChunkGenerator generator = creator.generator();
/*  956 */     File folder = new File(getWorldContainer(), name);
/*  957 */     World world = getWorld(name);
/*  958 */     WorldType type = WorldType.getType(creator.type().getName());
/*  959 */     boolean generateStructures = creator.generateStructures();
/*      */     
/*  961 */     if (world != null) {
/*  962 */       return world;
/*      */     }
/*      */     
/*  965 */     if (folder.exists() && !folder.isDirectory()) {
/*  966 */       throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
/*      */     }
/*      */     
/*  969 */     if (generator == null) {
/*  970 */       generator = getGenerator(name);
/*      */     }
/*      */     
/*  973 */     WorldLoaderServer worldLoaderServer = new WorldLoaderServer(getWorldContainer());
/*  974 */     if (worldLoaderServer.isConvertable(name)) {
/*  975 */       getLogger().info("Converting world '" + name + "'");
/*  976 */       worldLoaderServer.convert(name, (IProgressUpdate)new ConvertProgressUpdater(this.console));
/*      */     } 
/*      */     
/*  979 */     int dimension = 10 + this.console.worlds.size();
/*  980 */     boolean used = false;
/*      */     while (true) {
/*  982 */       for (WorldServer server : this.console.worlds) {
/*  983 */         used = (server.dimension == dimension);
/*  984 */         if (used) {
/*  985 */           dimension++;
/*      */           break;
/*      */         } 
/*      */       } 
/*  989 */       if (!used) {
/*  990 */         boolean hardcore = false;
/*      */         
/*  992 */         WorldServer internal = new WorldServer(this.console, (IDataManager)new ServerNBTManager(getWorldContainer(), name, true), name, dimension, new WorldSettings(creator.seed(), EnumGamemode.getById(getDefaultGameMode().getValue()), generateStructures, hardcore, type), this.console.methodProfiler, creator.environment(), generator);
/*      */         
/*  994 */         if (!this.worlds.containsKey(name.toLowerCase())) {
/*  995 */           return null;
/*      */         }
/*      */         
/*  998 */         internal.scoreboard = getScoreboardManager().getMainScoreboard().getHandle();
/*      */         
/* 1000 */         internal.tracker = new EntityTracker(internal);
/* 1001 */         internal.addIWorldAccess((IWorldAccess)new WorldManager(this.console, internal));
/* 1002 */         internal.difficulty = EnumDifficulty.EASY;
/* 1003 */         internal.setSpawnFlags(true, true);
/* 1004 */         this.console.worlds.add(internal);
/*      */         
/* 1006 */         if (generator != null) {
/* 1007 */           internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
/*      */         }
/*      */         
/* 1010 */         this.pluginManager.callEvent((Event)new WorldInitEvent(internal.getWorld()));
/* 1011 */         System.out.print("Preparing start region for level " + (this.console.worlds.size() - 1) + " (Seed: " + internal.getSeed() + ")");
/*      */         
/* 1013 */         if (internal.getWorld().getKeepSpawnInMemory()) {
/* 1014 */           short short1 = 196;
/* 1015 */           long i = System.currentTimeMillis();
/* 1016 */           for (int j = -short1; j <= short1; j += 16) {
/* 1017 */             for (int k = -short1; k <= short1; k += 16) {
/* 1018 */               long l = System.currentTimeMillis();
/*      */               
/* 1020 */               if (l < i) {
/* 1021 */                 i = l;
/*      */               }
/*      */               
/* 1024 */               if (l > i + 1000L) {
/* 1025 */                 int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
/* 1026 */                 int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;
/*      */                 
/* 1028 */                 System.out.println("Preparing spawn area for " + name + ", " + (j1 * 100 / i1) + "%");
/* 1029 */                 i = l;
/*      */               } 
/*      */               
/* 1032 */               ChunkCoordinates chunkcoordinates = internal.getSpawn();
/* 1033 */               internal.chunkProviderServer.getChunkAt(chunkcoordinates.x + j >> 4, chunkcoordinates.z + k >> 4);
/*      */             } 
/*      */           } 
/*      */         } 
/* 1037 */         this.pluginManager.callEvent((Event)new WorldLoadEvent(internal.getWorld()));
/* 1038 */         return internal.getWorld();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public boolean unloadWorld(String name, boolean save) {
/* 1043 */     return unloadWorld(getWorld(name), save);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean unloadWorld(World world, boolean save) {
/* 1048 */     if (world == null) {
/* 1049 */       return false;
/*      */     }
/*      */     
/* 1052 */     WorldServer handle = ((CraftWorld)world).getHandle();
/*      */     
/* 1054 */     if (!this.console.worlds.contains(handle)) {
/* 1055 */       return false;
/*      */     }
/*      */     
/* 1058 */     if (handle.dimension <= 1) {
/* 1059 */       return false;
/*      */     }
/*      */     
/* 1062 */     if (handle.players.size() > 0) {
/* 1063 */       return false;
/*      */     }
/*      */     
/* 1066 */     WorldUnloadEvent e = new WorldUnloadEvent(handle.getWorld());
/* 1067 */     this.pluginManager.callEvent((Event)e);
/*      */     
/* 1069 */     if (e.isCancelled()) {
/* 1070 */       return false;
/*      */     }
/*      */     
/* 1073 */     if (save) {
/*      */       try {
/* 1075 */         handle.save(true, null);
/* 1076 */         handle.saveLevel();
/* 1077 */         WorldSaveEvent event = new WorldSaveEvent(handle.getWorld());
/* 1078 */         getPluginManager().callEvent((Event)event);
/* 1079 */       } catch (ExceptionWorldConflict ex) {
/* 1080 */         getLogger().log(Level.SEVERE, (String)null, (Throwable)ex);
/*      */       } 
/*      */     }
/*      */     
/* 1084 */     this.worlds.remove(world.getName().toLowerCase());
/* 1085 */     this.console.worlds.remove(this.console.worlds.indexOf(handle));
/*      */     
/* 1087 */     File parentFolder = world.getWorldFolder().getAbsoluteFile();
/*      */ 
/*      */     
/* 1090 */     synchronized (RegionFileCache.class) {
/*      */       
/* 1092 */       Iterator<Map.Entry<File, RegionFile>> i = RegionFileCache.a.entrySet().iterator();
/* 1093 */       while (i.hasNext()) {
/* 1094 */         Map.Entry<File, RegionFile> entry = i.next();
/* 1095 */         File child = ((File)entry.getKey()).getAbsoluteFile();
/* 1096 */         while (child != null) {
/* 1097 */           if (child.equals(parentFolder)) {
/* 1098 */             i.remove();
/*      */             try {
/* 1100 */               ((RegionFile)entry.getValue()).c();
/* 1101 */             } catch (IOException ex) {
/* 1102 */               getLogger().log(Level.SEVERE, (String)null, ex);
/*      */             } 
/*      */             break;
/*      */           } 
/* 1106 */           child = child.getParentFile();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1111 */     return true;
/*      */   }
/*      */   
/*      */   public MinecraftServer getServer() {
/* 1115 */     return this.console;
/*      */   }
/*      */ 
/*      */   
/*      */   public World getWorld(String name) {
/* 1120 */     Validate.notNull(name, "Name cannot be null");
/*      */     
/* 1122 */     return this.worlds.get(name.toLowerCase());
/*      */   }
/*      */ 
/*      */   
/*      */   public World getWorld(UUID uid) {
/* 1127 */     for (World world : this.worlds.values()) {
/* 1128 */       if (world.getUID().equals(uid)) {
/* 1129 */         return world;
/*      */       }
/*      */     } 
/* 1132 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addWorld(World world) {
/* 1137 */     if (getWorld(world.getUID()) != null) {
/* 1138 */       System.out.println("World " + world.getName() + " is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from " + world.getName() + "'s world directory if you want to be able to load the duplicate world.");
/*      */       return;
/*      */     } 
/* 1141 */     this.worlds.put(world.getName().toLowerCase(), world);
/*      */   }
/*      */ 
/*      */   
/*      */   public Logger getLogger() {
/* 1146 */     return this.logger;
/*      */   }
/*      */   
/*      */   public ConsoleReader getReader() {
/* 1150 */     return this.console.reader;
/*      */   }
/*      */ 
/*      */   
/*      */   public PluginCommand getPluginCommand(String name) {
/* 1155 */     Command command = this.commandMap.getCommand(name);
/*      */     
/* 1157 */     if (command instanceof PluginCommand) {
/* 1158 */       return (PluginCommand)command;
/*      */     }
/* 1160 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void savePlayers() {
/* 1166 */     checkSaveState();
/* 1167 */     this.playerList.savePlayers();
/*      */   }
/*      */ 
/*      */   
/*      */   public void configureDbConfig(ServerConfig config) {
/* 1172 */     Validate.notNull(config, "Config cannot be null");
/*      */     
/* 1174 */     DataSourceConfig ds = new DataSourceConfig();
/* 1175 */     ds.setDriver(this.configuration.getString("database.driver"));
/* 1176 */     ds.setUrl(this.configuration.getString("database.url"));
/* 1177 */     ds.setUsername(this.configuration.getString("database.username"));
/* 1178 */     ds.setPassword(this.configuration.getString("database.password"));
/* 1179 */     ds.setIsolationLevel(TransactionIsolation.getLevel(this.configuration.getString("database.isolation")));
/*      */     
/* 1181 */     if (ds.getDriver().contains("sqlite")) {
/* 1182 */       config.setDatabasePlatform((DatabasePlatform)new SQLitePlatform());
/* 1183 */       config.getDatabasePlatform().getDbDdlSyntax().setIdentity("");
/*      */     } 
/*      */     
/* 1186 */     config.setDataSourceConfig(ds);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addRecipe(Recipe recipe) {
/*      */     CraftFurnaceRecipe craftFurnaceRecipe;
/* 1192 */     if (recipe instanceof CraftRecipe) {
/* 1193 */       CraftRecipe toAdd = (CraftRecipe)recipe;
/*      */     }
/* 1195 */     else if (recipe instanceof ShapedRecipe) {
/* 1196 */       CraftShapedRecipe craftShapedRecipe = CraftShapedRecipe.fromBukkitRecipe((ShapedRecipe)recipe);
/* 1197 */     } else if (recipe instanceof ShapelessRecipe) {
/* 1198 */       CraftShapelessRecipe craftShapelessRecipe = CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe)recipe);
/* 1199 */     } else if (recipe instanceof FurnaceRecipe) {
/* 1200 */       craftFurnaceRecipe = CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe)recipe);
/*      */     } else {
/* 1202 */       return false;
/*      */     } 
/*      */     
/* 1205 */     craftFurnaceRecipe.addToCraftingManager();
/* 1206 */     CraftingManager.getInstance().sort();
/* 1207 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Recipe> getRecipesFor(ItemStack result) {
/* 1212 */     Validate.notNull(result, "Result cannot be null");
/*      */     
/* 1214 */     List<Recipe> results = new ArrayList<Recipe>();
/* 1215 */     Iterator<Recipe> iter = recipeIterator();
/* 1216 */     while (iter.hasNext()) {
/* 1217 */       Recipe recipe = iter.next();
/* 1218 */       ItemStack stack = recipe.getResult();
/* 1219 */       if (stack.getType() != result.getType()) {
/*      */         continue;
/*      */       }
/* 1222 */       if (result.getDurability() == -1 || result.getDurability() == stack.getDurability()) {
/* 1223 */         results.add(recipe);
/*      */       }
/*      */     } 
/* 1226 */     return results;
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator<Recipe> recipeIterator() {
/* 1231 */     return (Iterator<Recipe>)new RecipeIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearRecipes() {
/* 1236 */     (CraftingManager.getInstance()).recipes.clear();
/* 1237 */     (RecipesFurnace.getInstance()).recipes.clear();
/* 1238 */     (RecipesFurnace.getInstance()).customRecipes.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetRecipes() {
/* 1243 */     (CraftingManager.getInstance()).recipes = (new CraftingManager()).recipes;
/* 1244 */     (RecipesFurnace.getInstance()).recipes = (new RecipesFurnace()).recipes;
/* 1245 */     (RecipesFurnace.getInstance()).customRecipes.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<String, String[]> getCommandAliases() {
/* 1250 */     ConfigurationSection section = this.commandsConfiguration.getConfigurationSection("aliases");
/* 1251 */     Map<String, String[]> result = (Map)new LinkedHashMap<String, String>();
/*      */     
/* 1253 */     if (section != null) {
/* 1254 */       for (String key : section.getKeys(false)) {
/*      */         ImmutableList immutableList;
/*      */         
/* 1257 */         if (section.isList(key)) {
/* 1258 */           List<String> commands = section.getStringList(key);
/*      */         } else {
/* 1260 */           immutableList = ImmutableList.of(section.getString(key));
/*      */         } 
/*      */         
/* 1263 */         result.put(key, (String[])immutableList.toArray((Object[])new String[immutableList.size()]));
/*      */       } 
/*      */     }
/*      */     
/* 1267 */     return result;
/*      */   }
/*      */   
/*      */   public void removeBukkitSpawnRadius() {
/* 1271 */     this.configuration.set("settings.spawn-radius", null);
/* 1272 */     saveConfig();
/*      */   }
/*      */   
/*      */   public int getBukkitSpawnRadius() {
/* 1276 */     return this.configuration.getInt("settings.spawn-radius", -1);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getShutdownMessage() {
/* 1281 */     return this.configuration.getString("settings.shutdown-message");
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSpawnRadius() {
/* 1286 */     return ((DedicatedServer)this.console).propertyManager.getInt("spawn-protection", 16);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpawnRadius(int value) {
/* 1291 */     this.configuration.set("settings.spawn-radius", Integer.valueOf(value));
/* 1292 */     saveConfig();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getOnlineMode() {
/* 1297 */     return this.online.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAllowFlight() {
/* 1302 */     return this.console.getAllowFlight();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHardcore() {
/* 1307 */     return this.console.isHardcore();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean useExactLoginLocation() {
/* 1312 */     return this.configuration.getBoolean("settings.use-exact-login-location");
/*      */   }
/*      */   
/*      */   public ChunkGenerator getGenerator(String world) {
/* 1316 */     ConfigurationSection section = this.configuration.getConfigurationSection("worlds");
/* 1317 */     ChunkGenerator result = null;
/*      */     
/* 1319 */     if (section != null) {
/* 1320 */       section = section.getConfigurationSection(world);
/*      */       
/* 1322 */       if (section != null) {
/* 1323 */         String name = section.getString("generator");
/*      */         
/* 1325 */         if (name != null && !name.equals("")) {
/* 1326 */           String[] split = name.split(":", 2);
/* 1327 */           String id = (split.length > 1) ? split[1] : null;
/* 1328 */           Plugin plugin = this.pluginManager.getPlugin(split[0]);
/*      */           
/* 1330 */           if (plugin == null) {
/* 1331 */             getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
/* 1332 */           } else if (!plugin.isEnabled()) {
/* 1333 */             getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' is not enabled yet (is it load:STARTUP?)");
/*      */           } else {
/*      */             try {
/* 1336 */               result = plugin.getDefaultWorldGenerator(world, id);
/* 1337 */               if (result == null) {
/* 1338 */                 getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' lacks a default world generator");
/*      */               }
/* 1340 */             } catch (Throwable t) {
/* 1341 */               plugin.getLogger().log(Level.SEVERE, "Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName(), t);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1348 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public CraftMapView getMap(short id) {
/* 1354 */     PersistentCollection collection = ((WorldServer)this.console.worlds.get(0)).worldMaps;
/* 1355 */     WorldMap worldmap = (WorldMap)collection.get(WorldMap.class, "map_" + id);
/* 1356 */     if (worldmap == null) {
/* 1357 */       return null;
/*      */     }
/* 1359 */     return worldmap.mapView;
/*      */   }
/*      */ 
/*      */   
/*      */   public CraftMapView createMap(World world) {
/* 1364 */     Validate.notNull(world, "World cannot be null");
/*      */     
/* 1366 */     ItemStack stack = new ItemStack((Item)Items.MAP, 1, -1);
/* 1367 */     WorldMap worldmap = Items.MAP.getSavedMap(stack, (World)((CraftWorld)world).getHandle());
/* 1368 */     return worldmap.mapView;
/*      */   }
/*      */ 
/*      */   
/*      */   public void shutdown() {
/* 1373 */     this.console.safeShutdown();
/*      */   }
/*      */ 
/*      */   
/*      */   public int broadcast(String message, String permission) {
/* 1378 */     int count = 0;
/* 1379 */     Set<Permissible> permissibles = getPluginManager().getPermissionSubscriptions(permission);
/*      */     
/* 1381 */     for (Permissible permissible : permissibles) {
/* 1382 */       if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
/* 1383 */         CommandSender user = (CommandSender)permissible;
/* 1384 */         user.sendMessage(message);
/* 1385 */         count++;
/*      */       } 
/*      */     } 
/*      */     
/* 1389 */     return count;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public OfflinePlayer getOfflinePlayer(String name) {
/*      */     OfflinePlayer offlinePlayer;
/* 1395 */     Validate.notNull(name, "Name cannot be null");
/* 1396 */     Preconditions.checkArgument(!StringUtils.isBlank(name), "Name cannot be blank");
/*      */     
/* 1398 */     Player player = getPlayerExact(name);
/* 1399 */     if (player == null) {
/*      */       
/* 1401 */       GameProfile profile = null;
/*      */       
/* 1403 */       if (MinecraftServer.getServer().getOnlineMode() || SpigotConfig.bungee)
/*      */       {
/* 1405 */         profile = MinecraftServer.getServer().getUserCache().getProfile(name);
/*      */       }
/*      */       
/* 1408 */       if (profile == null) {
/*      */         
/* 1410 */         offlinePlayer = getOfflinePlayer(new GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8)), name));
/*      */       } else {
/*      */         
/* 1413 */         offlinePlayer = getOfflinePlayer(profile);
/*      */       } 
/*      */     } else {
/* 1416 */       this.offlinePlayers.remove(offlinePlayer.getUniqueId());
/*      */     } 
/*      */     
/* 1419 */     return offlinePlayer;
/*      */   }
/*      */   
/*      */   public OfflinePlayer getOfflinePlayer(UUID id) {
/*      */     OfflinePlayer offlinePlayer;
/* 1424 */     Validate.notNull(id, "UUID cannot be null");
/*      */     
/* 1426 */     Player player = getPlayer(id);
/* 1427 */     if (player == null) {
/* 1428 */       offlinePlayer = this.offlinePlayers.get(id);
/* 1429 */       if (offlinePlayer == null) {
/* 1430 */         offlinePlayer = new CraftOfflinePlayer(this, new GameProfile(id, null));
/* 1431 */         this.offlinePlayers.put(id, offlinePlayer);
/*      */       } 
/*      */     } else {
/* 1434 */       this.offlinePlayers.remove(id);
/*      */     } 
/*      */     
/* 1437 */     return offlinePlayer;
/*      */   }
/*      */   
/*      */   public OfflinePlayer getOfflinePlayer(GameProfile profile) {
/* 1441 */     OfflinePlayer player = new CraftOfflinePlayer(this, profile);
/* 1442 */     this.offlinePlayers.put(profile.getId(), player);
/* 1443 */     return player;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getIPBans() {
/* 1449 */     return new HashSet<String>(Arrays.asList(this.playerList.getIPBans().getEntries()));
/*      */   }
/*      */ 
/*      */   
/*      */   public void banIP(String address) {
/* 1454 */     Validate.notNull(address, "Address cannot be null.");
/*      */     
/* 1456 */     getBanList(BanList.Type.IP).addBan(address, null, null, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void unbanIP(String address) {
/* 1461 */     Validate.notNull(address, "Address cannot be null.");
/*      */     
/* 1463 */     getBanList(BanList.Type.IP).pardon(address);
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<OfflinePlayer> getBannedPlayers() {
/* 1468 */     Set<OfflinePlayer> result = new HashSet<OfflinePlayer>();
/*      */     
/* 1470 */     for (JsonListEntry entry : this.playerList.getProfileBans().getValues()) {
/* 1471 */       result.add(getOfflinePlayer((GameProfile)entry.getKey()));
/*      */     }
/*      */     
/* 1474 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public BanList getBanList(BanList.Type type) {
/* 1479 */     Validate.notNull(type, "Type cannot be null");
/*      */     
/* 1481 */     switch (type) {
/*      */       case IP:
/* 1483 */         return new CraftIpBanList(this.playerList.getIPBans());
/*      */     } 
/*      */     
/* 1486 */     return new CraftProfileBanList(this.playerList.getProfileBans());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWhitelist(boolean value) {
/* 1492 */     this.playerList.setHasWhitelist(value);
/* 1493 */     this.console.getPropertyManager().setProperty("white-list", Boolean.valueOf(value));
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<OfflinePlayer> getWhitelistedPlayers() {
/* 1498 */     Set<OfflinePlayer> result = new LinkedHashSet<OfflinePlayer>();
/*      */     
/* 1500 */     for (JsonListEntry entry : this.playerList.getWhitelist().getValues()) {
/* 1501 */       result.add(getOfflinePlayer((GameProfile)entry.getKey()));
/*      */     }
/*      */     
/* 1504 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<OfflinePlayer> getOperators() {
/* 1509 */     Set<OfflinePlayer> result = new HashSet<OfflinePlayer>();
/*      */     
/* 1511 */     for (JsonListEntry entry : this.playerList.getOPs().getValues()) {
/* 1512 */       result.add(getOfflinePlayer((GameProfile)entry.getKey()));
/*      */     }
/*      */     
/* 1515 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void reloadWhitelist() {
/* 1520 */     this.playerList.reloadWhitelist();
/*      */   }
/*      */ 
/*      */   
/*      */   public GameMode getDefaultGameMode() {
/* 1525 */     return GameMode.getByValue(((WorldServer)this.console.worlds.get(0)).getWorldData().getGameType().getId());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDefaultGameMode(GameMode mode) {
/* 1530 */     Validate.notNull(mode, "Mode cannot be null");
/*      */     
/* 1532 */     for (World world : getWorlds()) {
/* 1533 */       (((CraftWorld)world).getHandle()).worldData.setGameType(EnumGamemode.getById(mode.getValue()));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ConsoleCommandSender getConsoleSender() {
/* 1539 */     return this.console.console;
/*      */   }
/*      */   
/*      */   public EntityMetadataStore getEntityMetadata() {
/* 1543 */     return this.entityMetadata;
/*      */   }
/*      */   
/*      */   public PlayerMetadataStore getPlayerMetadata() {
/* 1547 */     return this.playerMetadata;
/*      */   }
/*      */   
/*      */   public WorldMetadataStore getWorldMetadata() {
/* 1551 */     return this.worldMetadata;
/*      */   }
/*      */ 
/*      */   
/*      */   public void detectListNameConflict(EntityPlayer entityPlayer) {
/* 1556 */     for (int i = 0; i < (getHandle()).players.size(); i++) {
/* 1557 */       EntityPlayer testEntityPlayer = (getHandle()).players.get(i);
/*      */ 
/*      */       
/* 1560 */       if (testEntityPlayer != entityPlayer && testEntityPlayer.listName.equals(entityPlayer.listName)) {
/* 1561 */         String oldName = entityPlayer.listName;
/* 1562 */         int spaceLeft = 16 - oldName.length();
/*      */         
/* 1564 */         if (spaceLeft <= 1) {
/* 1565 */           entityPlayer.listName = oldName.subSequence(0, oldName.length() - 2 - spaceLeft) + String.valueOf(System.currentTimeMillis() % 99L);
/*      */         } else {
/* 1567 */           entityPlayer.listName = oldName + String.valueOf(System.currentTimeMillis() % 99L);
/*      */         } 
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public File getWorldContainer() {
/* 1577 */     if ((getServer()).universe != null) {
/* 1578 */       return (getServer()).universe;
/*      */     }
/*      */     
/* 1581 */     if (this.container == null) {
/* 1582 */       this.container = new File(this.configuration.getString("settings.world-container", "."));
/*      */     }
/*      */     
/* 1585 */     return this.container;
/*      */   }
/*      */ 
/*      */   
/*      */   public OfflinePlayer[] getOfflinePlayers() {
/* 1590 */     WorldNBTStorage storage = (WorldNBTStorage)((WorldServer)this.console.worlds.get(0)).getDataManager();
/* 1591 */     String[] files = storage.getPlayerDir().list((FilenameFilter)new DatFileFilter());
/* 1592 */     Set<OfflinePlayer> players = new HashSet<OfflinePlayer>();
/*      */     
/* 1594 */     for (String file : files) {
/*      */       try {
/* 1596 */         players.add(getOfflinePlayer(UUID.fromString(file.substring(0, file.length() - 4))));
/* 1597 */       } catch (IllegalArgumentException ex) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1602 */     players.addAll(getOnlinePlayers());
/*      */     
/* 1604 */     return players.<OfflinePlayer>toArray(new OfflinePlayer[players.size()]);
/*      */   }
/*      */ 
/*      */   
/*      */   public Messenger getMessenger() {
/* 1609 */     return (Messenger)this.messenger;
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPluginMessage(Plugin source, String channel, byte[] message) {
/* 1614 */     StandardMessenger.validatePluginMessage(getMessenger(), source, channel, message);
/*      */     
/* 1616 */     for (Player player : getOnlinePlayers()) {
/* 1617 */       player.sendPluginMessage(source, channel, message);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<String> getListeningPluginChannels() {
/* 1623 */     Set<String> result = new HashSet<String>();
/*      */     
/* 1625 */     for (Player player : getOnlinePlayers()) {
/* 1626 */       result.addAll(player.getListeningPluginChannels());
/*      */     }
/*      */     
/* 1629 */     return result;
/*      */   }
/*      */   
/*      */   public void onPlayerJoin(Player player) {
/* 1633 */     if (this.updater.isEnabled() && this.updater.getCurrent() != null && player.hasPermission("bukkit.broadcast.admin")) {
/* 1634 */       if (this.updater.getCurrent().isBroken() && this.updater.getOnBroken().contains("warn-ops")) {
/* 1635 */         player.sendMessage(ChatColor.DARK_RED + "The version of CraftBukkit that this server is running is known to be broken. Please consider updating to the latest version at dl.bukkit.org.");
/* 1636 */       } else if (this.updater.isUpdateAvailable() && this.updater.getOnUpdate().contains("warn-ops")) {
/* 1637 */         player.sendMessage(ChatColor.DARK_PURPLE + "The version of CraftBukkit that this server is running is out of date. Please consider updating to the latest version at dl.bukkit.org.");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Inventory createInventory(InventoryHolder owner, InventoryType type) {
/* 1645 */     return (Inventory)new CraftInventoryCustom(owner, type);
/*      */   }
/*      */ 
/*      */   
/*      */   public Inventory createInventory(InventoryHolder owner, InventoryType type, String title) {
/* 1650 */     return (Inventory)new CraftInventoryCustom(owner, type, title);
/*      */   }
/*      */ 
/*      */   
/*      */   public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException {
/* 1655 */     Validate.isTrue((size % 9 == 0), "Chests must have a size that is a multiple of 9!");
/* 1656 */     return (Inventory)new CraftInventoryCustom(owner, size);
/*      */   }
/*      */ 
/*      */   
/*      */   public Inventory createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException {
/* 1661 */     Validate.isTrue((size % 9 == 0), "Chests must have a size that is a multiple of 9!");
/* 1662 */     return (Inventory)new CraftInventoryCustom(owner, size, title);
/*      */   }
/*      */ 
/*      */   
/*      */   public HelpMap getHelpMap() {
/* 1667 */     return (HelpMap)this.helpMap;
/*      */   }
/*      */   
/*      */   public SimpleCommandMap getCommandMap() {
/* 1671 */     return this.commandMap;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMonsterSpawnLimit() {
/* 1676 */     return this.monsterSpawn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAnimalSpawnLimit() {
/* 1681 */     return this.animalSpawn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getWaterAnimalSpawnLimit() {
/* 1686 */     return this.waterAnimalSpawn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAmbientSpawnLimit() {
/* 1691 */     return this.ambientSpawn;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPrimaryThread() {
/* 1696 */     return Thread.currentThread().equals(this.console.primaryThread);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getMotd() {
/* 1701 */     return this.console.getMotd();
/*      */   }
/*      */ 
/*      */   
/*      */   public Warning.WarningState getWarningState() {
/* 1706 */     return this.warningState;
/*      */   }
/*      */   
/*      */   public List<String> tabComplete(ICommandListener sender, String message) {
/* 1710 */     if (!(sender instanceof EntityPlayer)) {
/* 1711 */       return (List<String>)ImmutableList.of();
/*      */     }
/*      */     
/* 1714 */     CraftPlayer craftPlayer = ((EntityPlayer)sender).getBukkitEntity();
/* 1715 */     if (message.startsWith("/")) {
/* 1716 */       return tabCompleteCommand((Player)craftPlayer, message);
/*      */     }
/* 1718 */     return tabCompleteChat((Player)craftPlayer, message);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> tabCompleteCommand(Player player, String message) {
/* 1724 */     if ((SpigotConfig.tabComplete < 0 || message.length() <= SpigotConfig.tabComplete) && !message.contains(" "))
/*      */     {
/* 1726 */       return (List<String>)ImmutableList.of();
/*      */     }
/*      */ 
/*      */     
/* 1730 */     List<String> completions = null;
/*      */     try {
/* 1732 */       completions = getCommandMap().tabComplete((CommandSender)player, message.substring(1));
/* 1733 */     } catch (CommandException ex) {
/* 1734 */       player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to tab-complete this command");
/* 1735 */       getLogger().log(Level.SEVERE, "Exception when " + player.getName() + " attempted to tab complete " + message, (Throwable)ex);
/*      */     } 
/*      */     
/* 1738 */     return (completions == null) ? (List<String>)ImmutableList.of() : completions;
/*      */   }
/*      */   
/*      */   public List<String> tabCompleteChat(Player player, String message) {
/* 1742 */     List<String> completions = new ArrayList<String>();
/* 1743 */     PlayerChatTabCompleteEvent event = new PlayerChatTabCompleteEvent(player, message, completions);
/* 1744 */     String token = event.getLastToken();
/* 1745 */     for (Player p : getOnlinePlayers()) {
/* 1746 */       if (player.canSee(p) && StringUtil.startsWithIgnoreCase(p.getName(), token)) {
/* 1747 */         completions.add(p.getName());
/*      */       }
/*      */     } 
/* 1750 */     this.pluginManager.callEvent((Event)event);
/*      */     
/* 1752 */     Iterator<?> it = completions.iterator();
/* 1753 */     while (it.hasNext()) {
/* 1754 */       Object current = it.next();
/* 1755 */       if (!(current instanceof String))
/*      */       {
/* 1757 */         it.remove();
/*      */       }
/*      */     } 
/* 1760 */     Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
/* 1761 */     return completions;
/*      */   }
/*      */ 
/*      */   
/*      */   public CraftItemFactory getItemFactory() {
/* 1766 */     return CraftItemFactory.instance();
/*      */   }
/*      */ 
/*      */   
/*      */   public CraftScoreboardManager getScoreboardManager() {
/* 1771 */     return this.scoreboardManager;
/*      */   }
/*      */   
/*      */   public void checkSaveState() {
/* 1775 */     if (this.playerCommandState || this.printSaveWarning || this.console.autosavePeriod <= 0) {
/*      */       return;
/*      */     }
/* 1778 */     this.printSaveWarning = true;
/* 1779 */     getLogger().log(Level.WARNING, "A manual (plugin-induced) save has been detected while server is configured to auto-save. This may affect performance.", (this.warningState == Warning.WarningState.ON) ? new Throwable() : null);
/*      */   }
/*      */ 
/*      */   
/*      */   public CraftIconCache getServerIcon() {
/* 1784 */     return this.icon;
/*      */   }
/*      */ 
/*      */   
/*      */   public CraftIconCache loadServerIcon(File file) throws Exception {
/* 1789 */     Validate.notNull(file, "File cannot be null");
/* 1790 */     if (!file.isFile()) {
/* 1791 */       throw new IllegalArgumentException(file + " is not a file");
/*      */     }
/* 1793 */     return loadServerIcon0(file);
/*      */   }
/*      */   
/*      */   static CraftIconCache loadServerIcon0(File file) throws Exception {
/* 1797 */     return loadServerIcon0(ImageIO.read(file));
/*      */   }
/*      */ 
/*      */   
/*      */   public CraftIconCache loadServerIcon(BufferedImage image) throws Exception {
/* 1802 */     Validate.notNull(image, "Image cannot be null");
/* 1803 */     return loadServerIcon0(image);
/*      */   }
/*      */   
/*      */   static CraftIconCache loadServerIcon0(BufferedImage image) throws Exception {
/* 1807 */     ByteBuf bytebuf = Unpooled.buffer();
/*      */     
/* 1809 */     Validate.isTrue((image.getWidth() == 64), "Must be 64 pixels wide");
/* 1810 */     Validate.isTrue((image.getHeight() == 64), "Must be 64 pixels high");
/* 1811 */     ImageIO.write(image, "PNG", (OutputStream)new ByteBufOutputStream(bytebuf));
/* 1812 */     ByteBuf bytebuf1 = Base64.encode(bytebuf);
/*      */     
/* 1814 */     return new CraftIconCache("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setIdleTimeout(int threshold) {
/* 1819 */     this.console.setIdleTimeout(threshold);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getIdleTimeout() {
/* 1824 */     return this.console.getIdleTimeout();
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public UnsafeValues getUnsafe() {
/* 1830 */     return CraftMagicNumbers.INSTANCE;
/*      */   }
/*      */   
/* 1833 */   public CraftServer(MinecraftServer console, PlayerList playerList) { this.spigot = new Server.Spigot()
/*      */       {
/*      */ 
/*      */         
/*      */         public YamlConfiguration getConfig()
/*      */         {
/* 1839 */           return SpigotConfig.config;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public double[] getTPS() {
/* 1845 */           return new double[] { (MinecraftServer.getServer()).tps1.getAverage(), (MinecraftServer.getServer()).tps5.getAverage(), (MinecraftServer.getServer()).tps15.getAverage() };
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void broadcast(BaseComponent component) {
/* 1861 */           for (Player player : CraftServer.this.getOnlinePlayers())
/*      */           {
/* 1863 */             player.spigot().sendMessage(component);
/*      */           }
/*      */         }
/*      */         
/*      */         public CraftPlayer apply(EntityPlayer player) {
/*      */           return player.getBukkitEntity();
/*      */         }
/*      */       };
/*      */     this.console = console;
/*      */     this.playerList = (DedicatedPlayerList)playerList;
/*      */     this.playerView = Collections.unmodifiableList(Lists.transform(playerList.players, new Function<EntityPlayer, CraftPlayer>()
/*      */           {
/*      */             public void broadcast(BaseComponent... components) {
/* 1876 */               for (Player player : CraftServer.this.getOnlinePlayers())
/*      */               {
/* 1878 */                 player.spigot().sendMessage(components); }  } })); this.serverVersion = CraftServer.class.getPackage().getImplementationVersion(); this.online.value = console.getPropertyManager().getBoolean("online-mode", true); Bukkit.setServer(this); Enchantment.DAMAGE_ALL.getClass(); Enchantment.stopAcceptingRegistrations(); Potion.setPotionBrewer((PotionBrewer)new CraftPotionBrewer()); MobEffectList.BLINDNESS.getClass(); PotionEffectType.stopAcceptingRegistrations(); if (!Main.useConsole)
/*      */       getLogger().info("Console input is disabled due to --noconsole command argument");  this.configuration = YamlConfiguration.loadConfiguration(getConfigFile()); this.configuration.options().copyDefaults(true); this.configuration.setDefaults((Configuration)YamlConfiguration.loadConfiguration(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("configurations/bukkit.yml"), Charsets.UTF_8))); ConfigurationSection legacyAlias = null; if (!this.configuration.isString("aliases")) { legacyAlias = this.configuration.getConfigurationSection("aliases"); this.configuration.set("aliases", "now-in-commands.yml"); }  saveConfig(); if (getCommandsConfigFile().isFile())
/*      */       legacyAlias = null;  this.commandsConfiguration = YamlConfiguration.loadConfiguration(getCommandsConfigFile()); this.commandsConfiguration.options().copyDefaults(true); this.commandsConfiguration.setDefaults((Configuration)YamlConfiguration.loadConfiguration(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("configurations/commands.yml"), Charsets.UTF_8))); saveCommandsConfig(); if (legacyAlias != null) { ConfigurationSection aliases = this.commandsConfiguration.createSection("aliases"); for (String key : legacyAlias.getKeys(false)) { ArrayList<String> commands = new ArrayList<String>(); if (legacyAlias.isList(key)) { for (String command : legacyAlias.getStringList(key))
/*      */             commands.add(command + " $1-");  }
/*      */         else { commands.add(legacyAlias.getString(key) + " $1-"); }
/*      */          aliases.set(key, commands); }
/*      */        }
/* 1885 */      saveCommandsConfig(); this.overrideAllCommandBlockCommands = this.commandsConfiguration.getStringList("command-block-overrides").contains("*"); ((SimplePluginManager)this.pluginManager).useTimings(this.configuration.getBoolean("settings.plugin-profiling")); this.monsterSpawn = this.configuration.getInt("spawn-limits.monsters"); this.animalSpawn = this.configuration.getInt("spawn-limits.animals"); this.waterAnimalSpawn = this.configuration.getInt("spawn-limits.water-animals"); this.ambientSpawn = this.configuration.getInt("spawn-limits.ambient"); console.autosavePeriod = this.configuration.getInt("ticks-per.autosave"); this.warningState = Warning.WarningState.value(this.configuration.getString("settings.deprecated-verbose")); this.chunkGCPeriod = this.configuration.getInt("chunk-gc.period-in-ticks"); this.chunkGCLoadThresh = this.configuration.getInt("chunk-gc.load-threshold"); loadIcon(); this.updater = new AutoUpdater(new BukkitDLUpdaterService(this.configuration.getString("auto-updater.host")), getLogger(), this.configuration.getString("auto-updater.preferred-channel")); this.updater.setEnabled(false); this.updater.setSuggestChannels(this.configuration.getBoolean("auto-updater.suggest-channels")); this.updater.getOnBroken().addAll(this.configuration.getStringList("auto-updater.on-broken")); this.updater.getOnUpdate().addAll(this.configuration.getStringList("auto-updater.on-update")); this.updater.check(this.serverVersion); } public Server.Spigot spigot() { return this.spigot; }
/*      */ 
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */