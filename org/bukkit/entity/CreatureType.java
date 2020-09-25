/*     */ package org.bukkit.entity;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public enum CreatureType
/*     */ {
/*  15 */   CREEPER("Creeper", (Class)Creeper.class, 50),
/*  16 */   SKELETON("Skeleton", (Class)Skeleton.class, 51),
/*  17 */   SPIDER("Spider", (Class)Spider.class, 52),
/*  18 */   GIANT("Giant", (Class)Giant.class, 53),
/*  19 */   ZOMBIE("Zombie", (Class)Zombie.class, 54),
/*  20 */   SLIME("Slime", (Class)Slime.class, 55),
/*  21 */   GHAST("Ghast", (Class)Ghast.class, 56),
/*  22 */   PIG_ZOMBIE("PigZombie", (Class)PigZombie.class, 57),
/*  23 */   ENDERMAN("Enderman", (Class)Enderman.class, 58),
/*  24 */   CAVE_SPIDER("CaveSpider", (Class)CaveSpider.class, 59),
/*  25 */   SILVERFISH("Silverfish", (Class)Silverfish.class, 60),
/*  26 */   BLAZE("Blaze", (Class)Blaze.class, 61),
/*  27 */   MAGMA_CUBE("LavaSlime", (Class)MagmaCube.class, 62),
/*  28 */   ENDER_DRAGON("EnderDragon", (Class)EnderDragon.class, 63),
/*  29 */   PIG("Pig", (Class)Pig.class, 90),
/*  30 */   SHEEP("Sheep", (Class)Sheep.class, 91),
/*  31 */   COW("Cow", (Class)Cow.class, 92),
/*  32 */   CHICKEN("Chicken", (Class)Chicken.class, 93),
/*  33 */   SQUID("Squid", (Class)Squid.class, 94),
/*  34 */   WOLF("Wolf", (Class)Wolf.class, 95),
/*  35 */   MUSHROOM_COW("MushroomCow", (Class)MushroomCow.class, 96),
/*  36 */   SNOWMAN("SnowMan", (Class)Snowman.class, 97),
/*  37 */   VILLAGER("Villager", (Class)Villager.class, 120);
/*     */   
/*     */   private String name;
/*     */   private Class<? extends Entity> clazz;
/*     */   
/*     */   static {
/*  43 */     NAME_MAP = new HashMap<String, CreatureType>();
/*  44 */     ID_MAP = new HashMap<Short, CreatureType>();
/*     */ 
/*     */     
/*  47 */     for (CreatureType type : EnumSet.<CreatureType>allOf(CreatureType.class)) {
/*  48 */       NAME_MAP.put(type.name, type);
/*  49 */       if (type.typeId != 0)
/*  50 */         ID_MAP.put(Short.valueOf(type.typeId), type); 
/*     */     } 
/*     */   }
/*     */   private short typeId; private static final Map<String, CreatureType> NAME_MAP; private static final Map<Short, CreatureType> ID_MAP;
/*     */   
/*     */   CreatureType(String name, Class<? extends Entity> clazz, int typeId) {
/*  56 */     this.name = name;
/*  57 */     this.clazz = clazz;
/*  58 */     this.typeId = (short)typeId;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  62 */     return this.name;
/*     */   }
/*     */   
/*     */   public Class<? extends Entity> getEntityClass() {
/*  66 */     return this.clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public short getTypeId() {
/*  75 */     return this.typeId;
/*     */   }
/*     */   
/*     */   public static CreatureType fromName(String name) {
/*  79 */     return NAME_MAP.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static CreatureType fromId(int id) {
/*  88 */     if (id > 32767) {
/*  89 */       return null;
/*     */     }
/*  91 */     return ID_MAP.get(Short.valueOf((short)id));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public EntityType toEntityType() {
/*  96 */     return EntityType.fromName(getName());
/*     */   }
/*     */   
/*     */   public static CreatureType fromEntityType(EntityType creatureType) {
/* 100 */     return fromName(creatureType.getName());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\CreatureType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */