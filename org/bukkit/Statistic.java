/*     */ package org.bukkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Statistic
/*     */ {
/*   7 */   DAMAGE_DEALT,
/*   8 */   DAMAGE_TAKEN,
/*   9 */   DEATHS,
/*  10 */   MOB_KILLS,
/*  11 */   PLAYER_KILLS,
/*  12 */   FISH_CAUGHT,
/*  13 */   ANIMALS_BRED,
/*  14 */   TREASURE_FISHED,
/*  15 */   JUNK_FISHED,
/*  16 */   LEAVE_GAME,
/*  17 */   JUMP,
/*  18 */   DROP,
/*  19 */   PLAY_ONE_TICK,
/*  20 */   WALK_ONE_CM,
/*  21 */   SWIM_ONE_CM,
/*  22 */   FALL_ONE_CM,
/*  23 */   CLIMB_ONE_CM,
/*  24 */   FLY_ONE_CM,
/*  25 */   DIVE_ONE_CM,
/*  26 */   MINECART_ONE_CM,
/*  27 */   BOAT_ONE_CM,
/*  28 */   PIG_ONE_CM,
/*  29 */   HORSE_ONE_CM,
/*  30 */   MINE_BLOCK(Type.BLOCK),
/*  31 */   USE_ITEM(Type.ITEM),
/*  32 */   BREAK_ITEM(Type.ITEM),
/*  33 */   CRAFT_ITEM(Type.ITEM),
/*  34 */   KILL_ENTITY(Type.ENTITY),
/*  35 */   ENTITY_KILLED_BY(Type.ENTITY);
/*     */ 
/*     */ 
/*     */   
/*     */   private final Type type;
/*     */ 
/*     */ 
/*     */   
/*     */   Statistic(Type type) {
/*  44 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType() {
/*  53 */     return this.type;
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
/*     */   public boolean isSubstatistic() {
/*  68 */     return (this.type != Type.UNTYPED);
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
/*     */   public boolean isBlock() {
/*  80 */     return (this.type == Type.BLOCK);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Type
/*     */   {
/*  91 */     UNTYPED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     ITEM,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     BLOCK,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     ENTITY;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Statistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */