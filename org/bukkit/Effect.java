/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.material.MaterialData;
/*     */ import org.bukkit.potion.Potion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Effect
/*     */ {
/*  18 */   CLICK2(1000, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  22 */   CLICK1(1001, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  26 */   BOW_FIRE(1002, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  30 */   DOOR_TOGGLE(1003, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  34 */   EXTINGUISH(1004, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  38 */   RECORD_PLAY(1005, Type.SOUND, Material.class),
/*     */ 
/*     */ 
/*     */   
/*  42 */   GHAST_SHRIEK(1007, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  46 */   GHAST_SHOOT(1008, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  50 */   BLAZE_SHOOT(1009, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  54 */   ZOMBIE_CHEW_WOODEN_DOOR(1010, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  58 */   ZOMBIE_CHEW_IRON_DOOR(1011, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  62 */   ZOMBIE_DESTROY_DOOR(1012, Type.SOUND),
/*     */ 
/*     */ 
/*     */   
/*  66 */   SMOKE(2000, Type.VISUAL, BlockFace.class),
/*     */ 
/*     */ 
/*     */   
/*  70 */   STEP_SOUND(2001, Type.SOUND, Material.class),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   POTION_BREAK(2002, Type.VISUAL, Potion.class),
/*     */ 
/*     */ 
/*     */   
/*  79 */   ENDER_SIGNAL(2003, Type.VISUAL),
/*     */ 
/*     */ 
/*     */   
/*  83 */   MOBSPAWNER_FLAMES(2004, Type.VISUAL),
/*     */ 
/*     */ 
/*     */   
/*  87 */   FIREWORKS_SPARK("fireworksSpark", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/*  91 */   CRIT("crit", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/*  95 */   MAGIC_CRIT("magicCrit", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/*  99 */   POTION_SWIRL("mobSpell", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 103 */   POTION_SWIRL_TRANSPARENT("mobSpellAmbient", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 107 */   SPELL("spell", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 111 */   INSTANT_SPELL("instantSpell", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 115 */   WITCH_MAGIC("witchMagic", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 119 */   NOTE("note", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 123 */   PORTAL("portal", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 127 */   FLYING_GLYPH("enchantmenttable", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 131 */   FLAME("flame", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 135 */   LAVA_POP("lava", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 139 */   FOOTSTEP("footstep", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 143 */   SPLASH("splash", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 147 */   PARTICLE_SMOKE("smoke", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 151 */   EXPLOSION_HUGE("hugeexplosion", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 155 */   EXPLOSION_LARGE("largeexplode", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 159 */   EXPLOSION("explode", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 163 */   VOID_FOG("depthsuspend", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 167 */   SMALL_SMOKE("townaura", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 171 */   CLOUD("cloud", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 175 */   COLOURED_DUST("reddust", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 179 */   SNOWBALL_BREAK("snowballpoof", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 183 */   WATERDRIP("dripWater", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 187 */   LAVADRIP("dripLava", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 191 */   SNOW_SHOVEL("snowshovel", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 195 */   SLIME("slime", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 199 */   HEART("heart", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 203 */   VILLAGER_THUNDERCLOUD("angryVillager", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */   
/* 207 */   HAPPY_VILLAGER("happyVillager", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   LARGE_SMOKE("largesmoke", Type.PARTICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   ITEM_BREAK("iconcrack", Type.PARTICLE, Material.class),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   TILE_BREAK("blockcrack", Type.PARTICLE, MaterialData.class),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   TILE_DUST("blockdust", Type.PARTICLE, MaterialData.class);
/*     */   private final int id;
/*     */   private final Type type;
/*     */   private final Class<?> data;
/*     */   private static final Map<Integer, Effect> BY_ID;
/* 232 */   private static final Map<String, Effect> BY_NAME; private final String particleName; Effect(int id, Type type, Class<?> data) { this.id = id; this.type = type; this.data = data; this.particleName = null; } Effect(String particleName, Type type, Class<?> data) { this.particleName = particleName; this.type = type; this.id = 0; this.data = data; } Effect(String particleName, Type type) { this.particleName = particleName; this.type = type; this.id = 0; this.data = null; } @Deprecated public int getId() { return this.id; } public String getName() { return this.particleName; } static { BY_ID = Maps.newHashMap();
/* 233 */     BY_NAME = Maps.newHashMap();
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
/* 308 */     for (Effect effect : values()) {
/* 309 */       if (effect.type != Type.PARTICLE) {
/* 310 */         BY_ID.put(Integer.valueOf(effect.id), effect);
/*     */       }
/*     */     } 
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
/* 326 */     for (Effect effect : values()) {
/* 327 */       if (effect.type == Type.PARTICLE)
/* 328 */         BY_NAME.put(effect.particleName, effect); 
/*     */     }  } public Type getType() { return this.type; } public Class<?> getData() { return this.data; } @Deprecated
/*     */   public static Effect getById(int id) {
/*     */     return BY_ID.get(Integer.valueOf(id));
/*     */   } public static Effect getByName(String name) {
/*     */     return BY_NAME.get(name);
/*     */   } public enum Type
/*     */   {
/* 336 */     SOUND, VISUAL, PARTICLE;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Effect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */