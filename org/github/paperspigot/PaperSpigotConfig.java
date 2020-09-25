/*     */ package org.github.paperspigot;
/*     */ 
/*     */ import com.google.common.base.Throwables;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PaperSpigotConfig
/*     */ {
/*  25 */   private static final File CONFIG_FILE = new File("paper.yml");
/*     */   
/*     */   private static final String HEADER = "This is the main configuration file for PaperSpigot.\nAs you can see, there's tons to configure. Some options may impact gameplay, so use\nwith caution, and make sure you know what each option does before configuring.\n\nIf you need help with the configuration or have any questions related to PaperSpigot,\njoin us at the IRC.\n\nIRC: #paperspigot @ irc.spi.gt ( http://irc.spi.gt/iris/?channels=PaperSpigot )\n";
/*     */   
/*     */   static YamlConfiguration config;
/*     */   
/*     */   static int version;
/*     */   
/*     */   static Map<String, Command> commands;
/*     */   
/*     */   public static double babyZombieMovementSpeed;
/*     */   
/*     */   public static boolean asyncCatcherFeature;
/*     */   
/*     */   static {
/*  40 */     init();
/*     */   }
/*     */   public static boolean interactLimitEnabled; public static double strengthEffectModifier; public static double weaknessEffectModifier; public static int maxPacketsPerPlayer; public static boolean stackableLavaBuckets; public static boolean stackableWaterBuckets; public static boolean stackableMilkBuckets;
/*     */   public static void init() {
/*  44 */     config = new YamlConfiguration();
/*     */     
/*     */     try {
/*  47 */       config.load(CONFIG_FILE);
/*  48 */     } catch (IOException ex) {
/*     */     
/*  50 */     } catch (InvalidConfigurationException ex) {
/*     */       
/*  52 */       Bukkit.getLogger().log(Level.SEVERE, "Could not load paper.yml, please correct your syntax errors", (Throwable)ex);
/*  53 */       throw Throwables.propagate(ex);
/*     */     } 
/*  55 */     config.options().header("This is the main configuration file for PaperSpigot.\nAs you can see, there's tons to configure. Some options may impact gameplay, so use\nwith caution, and make sure you know what each option does before configuring.\n\nIf you need help with the configuration or have any questions related to PaperSpigot,\njoin us at the IRC.\n\nIRC: #paperspigot @ irc.spi.gt ( http://irc.spi.gt/iris/?channels=PaperSpigot )\n");
/*  56 */     config.options().copyDefaults(true);
/*     */     
/*  58 */     commands = new HashMap<String, Command>();
/*     */     
/*  60 */     version = getInt("config-version", 6);
/*  61 */     set("config-version", Integer.valueOf(6));
/*  62 */     readConfig(PaperSpigotConfig.class, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerCommands() {
/*  67 */     for (Map.Entry<String, Command> entry : commands.entrySet())
/*     */     {
/*  69 */       (MinecraftServer.getServer()).server.getCommandMap().register(entry.getKey(), "PaperSpigot", entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void readConfig(Class<?> clazz, Object instance) {
/*  75 */     for (Method method : clazz.getDeclaredMethods()) {
/*     */       
/*  77 */       if (Modifier.isPrivate(method.getModifiers()))
/*     */       {
/*  79 */         if ((method.getParameterTypes()).length == 0 && method.getReturnType() == void.class) {
/*     */           
/*     */           try {
/*     */             
/*  83 */             method.setAccessible(true);
/*  84 */             method.invoke(instance, new Object[0]);
/*  85 */           } catch (InvocationTargetException ex) {
/*     */             
/*  87 */             throw Throwables.propagate(ex.getCause());
/*  88 */           } catch (Exception ex) {
/*     */             
/*  90 */             Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + method, ex);
/*     */           } 
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  98 */       config.save(CONFIG_FILE);
/*  99 */     } catch (IOException ex) {
/*     */       
/* 101 */       Bukkit.getLogger().log(Level.SEVERE, "Could not save " + CONFIG_FILE, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void set(String path, Object val) {
/* 107 */     config.set(path, val);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean getBoolean(String path, boolean def) {
/* 112 */     config.addDefault(path, Boolean.valueOf(def));
/* 113 */     return config.getBoolean(path, config.getBoolean(path));
/*     */   }
/*     */ 
/*     */   
/*     */   private static double getDouble(String path, double def) {
/* 118 */     config.addDefault(path, Double.valueOf(def));
/* 119 */     return config.getDouble(path, config.getDouble(path));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static float getFloat(String path, float def) {
/* 125 */     return (float)getDouble(path, def);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getInt(String path, int def) {
/* 130 */     config.addDefault(path, Integer.valueOf(def));
/* 131 */     return config.getInt(path, config.getInt(path));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> List getList(String path, T def) {
/* 136 */     config.addDefault(path, def);
/* 137 */     return config.getList(path, config.getList(path));
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getString(String path, String def) {
/* 142 */     config.addDefault(path, def);
/* 143 */     return config.getString(path, config.getString(path));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void babyZombieMovementSpeed() {
/* 149 */     babyZombieMovementSpeed = getDouble("settings.baby-zombie-movement-speed", 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void asyncCatcherFeature() {
/* 155 */     asyncCatcherFeature = getBoolean("settings.async-plugin-bad-magic-catcher", true);
/* 156 */     if (!asyncCatcherFeature) {
/* 157 */       Bukkit.getLogger().log(Level.INFO, "Disabling async plugin bad ju-ju catcher, this may be bad depending on your plugins");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void interactLimitEnabled() {
/* 164 */     interactLimitEnabled = getBoolean("settings.limit-player-interactions", true);
/* 165 */     if (!interactLimitEnabled) {
/* 166 */       Bukkit.getLogger().log(Level.INFO, "Disabling player interaction limiter, your server may be more vulnerable to malicious users");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void effectModifiers() {
/* 174 */     strengthEffectModifier = getDouble("effect-modifiers.strength", 1.3D);
/* 175 */     weaknessEffectModifier = getDouble("effect-modifiers.weakness", -0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void maxPacketsPerPlayer() {
/* 181 */     maxPacketsPerPlayer = getInt("max-packets-per-player", 1000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void stackableBuckets() {
/*     */     Field maxStack;
/* 189 */     stackableLavaBuckets = getBoolean("stackable-buckets.lava", false);
/* 190 */     stackableWaterBuckets = getBoolean("stackable-buckets.water", false);
/* 191 */     stackableMilkBuckets = getBoolean("stackable-buckets.milk", false);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 196 */       maxStack = Material.class.getDeclaredField("maxStack");
/* 197 */       maxStack.setAccessible(true);
/*     */       
/* 199 */       Field modifiers = Field.class.getDeclaredField("modifiers");
/* 200 */       modifiers.setAccessible(true);
/* 201 */       modifiers.setInt(maxStack, maxStack.getModifiers() & 0xFFFFFFEF);
/* 202 */     } catch (Exception e) {
/* 203 */       e.printStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 208 */       if (stackableLavaBuckets) {
/* 209 */         maxStack.set(Material.LAVA_BUCKET, Integer.valueOf(Material.BUCKET.getMaxStackSize()));
/*     */       }
/*     */       
/* 212 */       if (stackableWaterBuckets) {
/* 213 */         maxStack.set(Material.WATER_BUCKET, Integer.valueOf(Material.BUCKET.getMaxStackSize()));
/*     */       }
/*     */       
/* 216 */       if (stackableMilkBuckets) {
/* 217 */         maxStack.set(Material.MILK_BUCKET, Integer.valueOf(Material.BUCKET.getMaxStackSize()));
/*     */       }
/* 219 */     } catch (Exception e) {
/* 220 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\github\paperspigot\PaperSpigotConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */