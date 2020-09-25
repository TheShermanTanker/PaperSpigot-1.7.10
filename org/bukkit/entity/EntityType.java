/*     */ package org.bukkit.entity;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.entity.minecart.CommandMinecart;
/*     */ import org.bukkit.entity.minecart.ExplosiveMinecart;
/*     */ import org.bukkit.entity.minecart.HopperMinecart;
/*     */ import org.bukkit.entity.minecart.PoweredMinecart;
/*     */ import org.bukkit.entity.minecart.RideableMinecart;
/*     */ import org.bukkit.entity.minecart.SpawnerMinecart;
/*     */ import org.bukkit.entity.minecart.StorageMinecart;
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
/*     */ public enum EntityType
/*     */ {
/*  26 */   DROPPED_ITEM("Item", (Class)Item.class, 1, false),
/*     */ 
/*     */ 
/*     */   
/*  30 */   EXPERIENCE_ORB("XPOrb", (Class)ExperienceOrb.class, 2),
/*     */ 
/*     */ 
/*     */   
/*  34 */   LEASH_HITCH("LeashKnot", (Class)LeashHitch.class, 8),
/*     */ 
/*     */ 
/*     */   
/*  38 */   PAINTING("Painting", (Class)Painting.class, 9),
/*     */ 
/*     */ 
/*     */   
/*  42 */   ARROW("Arrow", (Class)Arrow.class, 10),
/*     */ 
/*     */ 
/*     */   
/*  46 */   SNOWBALL("Snowball", (Class)Snowball.class, 11),
/*     */ 
/*     */ 
/*     */   
/*  50 */   FIREBALL("Fireball", (Class)LargeFireball.class, 12),
/*     */ 
/*     */ 
/*     */   
/*  54 */   SMALL_FIREBALL("SmallFireball", (Class)SmallFireball.class, 13),
/*     */ 
/*     */ 
/*     */   
/*  58 */   ENDER_PEARL("ThrownEnderpearl", (Class)EnderPearl.class, 14),
/*     */ 
/*     */ 
/*     */   
/*  62 */   ENDER_SIGNAL("EyeOfEnderSignal", (Class)EnderSignal.class, 15),
/*     */ 
/*     */ 
/*     */   
/*  66 */   THROWN_EXP_BOTTLE("ThrownExpBottle", (Class)ThrownExpBottle.class, 17),
/*     */ 
/*     */ 
/*     */   
/*  70 */   ITEM_FRAME("ItemFrame", (Class)ItemFrame.class, 18),
/*     */ 
/*     */ 
/*     */   
/*  74 */   WITHER_SKULL("WitherSkull", (Class)WitherSkull.class, 19),
/*     */ 
/*     */ 
/*     */   
/*  78 */   PRIMED_TNT("PrimedTnt", (Class)TNTPrimed.class, 20),
/*     */ 
/*     */ 
/*     */   
/*  82 */   FALLING_BLOCK("FallingSand", (Class)FallingBlock.class, 21, false),
/*  83 */   FIREWORK("FireworksRocketEntity", (Class)Firework.class, 22, false),
/*     */ 
/*     */ 
/*     */   
/*  87 */   MINECART_COMMAND("MinecartCommandBlock", (Class)CommandMinecart.class, 40),
/*     */ 
/*     */ 
/*     */   
/*  91 */   BOAT("Boat", (Class)Boat.class, 41),
/*     */ 
/*     */ 
/*     */   
/*  95 */   MINECART("MinecartRideable", (Class)RideableMinecart.class, 42),
/*     */ 
/*     */ 
/*     */   
/*  99 */   MINECART_CHEST("MinecartChest", (Class)StorageMinecart.class, 43),
/*     */ 
/*     */ 
/*     */   
/* 103 */   MINECART_FURNACE("MinecartFurnace", (Class)PoweredMinecart.class, 44),
/*     */ 
/*     */ 
/*     */   
/* 107 */   MINECART_TNT("MinecartTNT", (Class)ExplosiveMinecart.class, 45),
/*     */ 
/*     */ 
/*     */   
/* 111 */   MINECART_HOPPER("MinecartHopper", (Class)HopperMinecart.class, 46),
/*     */ 
/*     */ 
/*     */   
/* 115 */   MINECART_MOB_SPAWNER("MinecartMobSpawner", (Class)SpawnerMinecart.class, 47),
/* 116 */   CREEPER("Creeper", (Class)Creeper.class, 50),
/* 117 */   SKELETON("Skeleton", (Class)Skeleton.class, 51),
/* 118 */   SPIDER("Spider", (Class)Spider.class, 52),
/* 119 */   GIANT("Giant", (Class)Giant.class, 53),
/* 120 */   ZOMBIE("Zombie", (Class)Zombie.class, 54),
/* 121 */   SLIME("Slime", (Class)Slime.class, 55),
/* 122 */   GHAST("Ghast", (Class)Ghast.class, 56),
/* 123 */   PIG_ZOMBIE("PigZombie", (Class)PigZombie.class, 57),
/* 124 */   ENDERMAN("Enderman", (Class)Enderman.class, 58),
/* 125 */   CAVE_SPIDER("CaveSpider", (Class)CaveSpider.class, 59),
/* 126 */   SILVERFISH("Silverfish", (Class)Silverfish.class, 60),
/* 127 */   BLAZE("Blaze", (Class)Blaze.class, 61),
/* 128 */   MAGMA_CUBE("LavaSlime", (Class)MagmaCube.class, 62),
/* 129 */   ENDER_DRAGON("EnderDragon", (Class)EnderDragon.class, 63),
/* 130 */   WITHER("WitherBoss", (Class)Wither.class, 64),
/* 131 */   BAT("Bat", (Class)Bat.class, 65),
/* 132 */   WITCH("Witch", (Class)Witch.class, 66),
/* 133 */   PIG("Pig", (Class)Pig.class, 90),
/* 134 */   SHEEP("Sheep", (Class)Sheep.class, 91),
/* 135 */   COW("Cow", (Class)Cow.class, 92),
/* 136 */   CHICKEN("Chicken", (Class)Chicken.class, 93),
/* 137 */   SQUID("Squid", (Class)Squid.class, 94),
/* 138 */   WOLF("Wolf", (Class)Wolf.class, 95),
/* 139 */   MUSHROOM_COW("MushroomCow", (Class)MushroomCow.class, 96),
/* 140 */   SNOWMAN("SnowMan", (Class)Snowman.class, 97),
/* 141 */   OCELOT("Ozelot", (Class)Ocelot.class, 98),
/* 142 */   IRON_GOLEM("VillagerGolem", (Class)IronGolem.class, 99),
/* 143 */   HORSE("EntityHorse", (Class)Horse.class, 100),
/* 144 */   VILLAGER("Villager", (Class)Villager.class, 120),
/* 145 */   ENDER_CRYSTAL("EnderCrystal", (Class)EnderCrystal.class, 200),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   SPLASH_POTION(null, (Class)ThrownPotion.class, -1, false),
/*     */ 
/*     */ 
/*     */   
/* 154 */   EGG(null, (Class)Egg.class, -1, false),
/*     */ 
/*     */ 
/*     */   
/* 158 */   FISHING_HOOK(null, (Class)Fish.class, -1, false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   LIGHTNING(null, (Class)LightningStrike.class, -1, false),
/* 165 */   WEATHER(null, (Class)Weather.class, -1, false),
/* 166 */   PLAYER(null, (Class)Player.class, -1, false),
/* 167 */   COMPLEX_PART(null, (Class)ComplexEntityPart.class, -1, false),
/*     */ 
/*     */ 
/*     */   
/* 171 */   UNKNOWN(null, null, -1, false);
/*     */   
/*     */   private String name;
/*     */   private Class<? extends Entity> clazz;
/*     */   private short typeId;
/*     */   
/*     */   static {
/* 178 */     NAME_MAP = new HashMap<String, EntityType>();
/* 179 */     ID_MAP = new HashMap<Short, EntityType>();
/*     */ 
/*     */     
/* 182 */     for (EntityType type : values()) {
/* 183 */       if (type.name != null) {
/* 184 */         NAME_MAP.put(type.name.toLowerCase(), type);
/*     */       }
/* 186 */       if (type.typeId > 0)
/* 187 */         ID_MAP.put(Short.valueOf(type.typeId), type); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean independent;
/*     */   private boolean living;
/*     */   private static final Map<String, EntityType> NAME_MAP;
/*     */   private static final Map<Short, EntityType> ID_MAP;
/*     */   
/*     */   EntityType(String name, Class<? extends Entity> clazz, int typeId, boolean independent) {
/* 197 */     this.name = name;
/* 198 */     this.clazz = clazz;
/* 199 */     this.typeId = (short)typeId;
/* 200 */     this.independent = independent;
/* 201 */     if (clazz != null) {
/* 202 */       this.living = LivingEntity.class.isAssignableFrom(clazz);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getName() {
/* 212 */     return this.name;
/*     */   }
/*     */   
/*     */   public Class<? extends Entity> getEntityClass() {
/* 216 */     return this.clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public short getTypeId() {
/* 225 */     return this.typeId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static EntityType fromName(String name) {
/* 234 */     if (name == null) {
/* 235 */       return null;
/*     */     }
/* 237 */     return NAME_MAP.get(name.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static EntityType fromId(int id) {
/* 246 */     if (id > 32767) {
/* 247 */       return null;
/*     */     }
/* 249 */     return ID_MAP.get(Short.valueOf((short)id));
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
/*     */   public boolean isSpawnable() {
/* 261 */     return this.independent;
/*     */   }
/*     */   
/*     */   public boolean isAlive() {
/* 265 */     return this.living;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\EntityType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */