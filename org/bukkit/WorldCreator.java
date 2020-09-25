/*     */ package org.bukkit;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ public class WorldCreator
/*     */ {
/*     */   private final String name;
/*     */   private long seed;
/*  14 */   private World.Environment environment = World.Environment.NORMAL;
/*  15 */   private ChunkGenerator generator = null;
/*  16 */   private WorldType type = WorldType.NORMAL;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean generateStructures = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCreator(String name) {
/*  25 */     if (name == null) {
/*  26 */       throw new IllegalArgumentException("World name cannot be null");
/*     */     }
/*     */     
/*  29 */     this.name = name;
/*  30 */     this.seed = (new Random()).nextLong();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCreator copy(World world) {
/*  40 */     if (world == null) {
/*  41 */       throw new IllegalArgumentException("World cannot be null");
/*     */     }
/*     */     
/*  44 */     this.seed = world.getSeed();
/*  45 */     this.environment = world.getEnvironment();
/*  46 */     this.generator = world.getGenerator();
/*     */     
/*  48 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCreator copy(WorldCreator creator) {
/*  58 */     if (creator == null) {
/*  59 */       throw new IllegalArgumentException("Creator cannot be null");
/*     */     }
/*     */     
/*  62 */     this.seed = creator.seed();
/*  63 */     this.environment = creator.environment();
/*  64 */     this.generator = creator.generator();
/*     */     
/*  66 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  75 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long seed() {
/*  84 */     return this.seed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCreator seed(long seed) {
/*  94 */     this.seed = seed;
/*     */     
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World.Environment environment() {
/* 105 */     return this.environment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCreator environment(World.Environment env) {
/* 115 */     this.environment = env;
/*     */     
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldType type() {
/* 126 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCreator type(WorldType type) {
/* 136 */     this.type = type;
/*     */     
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkGenerator generator() {
/* 150 */     return this.generator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCreator generator(ChunkGenerator generator) {
/* 163 */     this.generator = generator;
/*     */     
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCreator generator(String generator) {
/* 182 */     this.generator = getGeneratorForName(this.name, generator, (CommandSender)Bukkit.getConsoleSender());
/*     */     
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCreator generator(String generator, CommandSender output) {
/* 203 */     this.generator = getGeneratorForName(this.name, generator, output);
/*     */     
/* 205 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCreator generateStructures(boolean generate) {
/* 216 */     this.generateStructures = generate;
/*     */     
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generateStructures() {
/* 227 */     return this.generateStructures;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World createWorld() {
/* 239 */     return Bukkit.createWorld(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WorldCreator name(String name) {
/* 249 */     return new WorldCreator(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChunkGenerator getGeneratorForName(String world, String name, CommandSender output) {
/*     */     ConsoleCommandSender consoleCommandSender;
/* 269 */     ChunkGenerator result = null;
/*     */     
/* 271 */     if (world == null) {
/* 272 */       throw new IllegalArgumentException("World name must be specified");
/*     */     }
/*     */     
/* 275 */     if (output == null) {
/* 276 */       consoleCommandSender = Bukkit.getConsoleSender();
/*     */     }
/*     */     
/* 279 */     if (name != null) {
/* 280 */       String[] split = name.split(":", 2);
/* 281 */       String id = (split.length > 1) ? split[1] : null;
/* 282 */       Plugin plugin = Bukkit.getPluginManager().getPlugin(split[0]);
/*     */       
/* 284 */       if (plugin == null) {
/* 285 */         consoleCommandSender.sendMessage("Could not set generator for world '" + world + "': Plugin '" + split[0] + "' does not exist");
/* 286 */       } else if (!plugin.isEnabled()) {
/* 287 */         consoleCommandSender.sendMessage("Could not set generator for world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' is not enabled");
/*     */       } else {
/* 289 */         result = plugin.getDefaultWorldGenerator(world, id);
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\WorldCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */