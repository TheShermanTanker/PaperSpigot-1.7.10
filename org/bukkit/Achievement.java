/*    */ package org.bukkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Achievement
/*    */ {
/*  7 */   OPEN_INVENTORY,
/*  8 */   MINE_WOOD(OPEN_INVENTORY),
/*  9 */   BUILD_WORKBENCH(MINE_WOOD),
/* 10 */   BUILD_PICKAXE(BUILD_WORKBENCH),
/* 11 */   BUILD_FURNACE(BUILD_PICKAXE),
/* 12 */   ACQUIRE_IRON(BUILD_FURNACE),
/* 13 */   BUILD_HOE(BUILD_WORKBENCH),
/* 14 */   MAKE_BREAD(BUILD_HOE),
/* 15 */   BAKE_CAKE(BUILD_HOE),
/* 16 */   BUILD_BETTER_PICKAXE(BUILD_PICKAXE),
/* 17 */   COOK_FISH(BUILD_FURNACE),
/* 18 */   ON_A_RAIL(ACQUIRE_IRON),
/* 19 */   BUILD_SWORD(BUILD_WORKBENCH),
/* 20 */   KILL_ENEMY(BUILD_SWORD),
/* 21 */   KILL_COW(BUILD_SWORD),
/* 22 */   FLY_PIG(KILL_COW),
/* 23 */   SNIPE_SKELETON(KILL_ENEMY),
/* 24 */   GET_DIAMONDS(ACQUIRE_IRON),
/* 25 */   NETHER_PORTAL(GET_DIAMONDS),
/* 26 */   GHAST_RETURN(NETHER_PORTAL),
/* 27 */   GET_BLAZE_ROD(NETHER_PORTAL),
/* 28 */   BREW_POTION(GET_BLAZE_ROD),
/* 29 */   END_PORTAL(GET_BLAZE_ROD),
/* 30 */   THE_END(END_PORTAL),
/* 31 */   ENCHANTMENTS(GET_DIAMONDS),
/* 32 */   OVERKILL(ENCHANTMENTS),
/* 33 */   BOOKCASE(ENCHANTMENTS),
/* 34 */   EXPLORE_ALL_BIOMES(END_PORTAL),
/* 35 */   SPAWN_WITHER(THE_END),
/* 36 */   KILL_WITHER(SPAWN_WITHER),
/* 37 */   FULL_BEACON(KILL_WITHER),
/* 38 */   BREED_COW(KILL_COW),
/* 39 */   DIAMONDS_TO_YOU(GET_DIAMONDS);
/*    */   
/*    */   private final Achievement parent;
/*    */ 
/*    */   
/*    */   Achievement() {
/* 45 */     this.parent = null;
/*    */   }
/*    */   
/*    */   Achievement(Achievement parent) {
/* 49 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasParent() {
/* 58 */     return (this.parent != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Achievement getParent() {
/* 67 */     return this.parent;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Achievement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */