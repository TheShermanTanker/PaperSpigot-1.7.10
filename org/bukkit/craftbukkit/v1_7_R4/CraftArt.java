/*    */ package org.bukkit.craftbukkit.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.EnumArt;
/*    */ import org.bukkit.Art;
/*    */ 
/*    */ 
/*    */ public class CraftArt
/*    */ {
/*    */   public static Art NotchToBukkit(EnumArt art) {
/* 10 */     switch (art) { case KEBAB:
/* 11 */         return Art.KEBAB;
/* 12 */       case AZTEC: return Art.AZTEC;
/* 13 */       case ALBAN: return Art.ALBAN;
/* 14 */       case AZTEC2: return Art.AZTEC2;
/* 15 */       case BOMB: return Art.BOMB;
/* 16 */       case PLANT: return Art.PLANT;
/* 17 */       case WASTELAND: return Art.WASTELAND;
/* 18 */       case POOL: return Art.POOL;
/* 19 */       case COURBET: return Art.COURBET;
/* 20 */       case SEA: return Art.SEA;
/* 21 */       case SUNSET: return Art.SUNSET;
/* 22 */       case CREEBET: return Art.CREEBET;
/* 23 */       case WANDERER: return Art.WANDERER;
/* 24 */       case GRAHAM: return Art.GRAHAM;
/* 25 */       case MATCH: return Art.MATCH;
/* 26 */       case BUST: return Art.BUST;
/* 27 */       case STAGE: return Art.STAGE;
/* 28 */       case VOID: return Art.VOID;
/* 29 */       case SKULL_AND_ROSES: return Art.SKULL_AND_ROSES;
/* 30 */       case FIGHTERS: return Art.FIGHTERS;
/* 31 */       case POINTER: return Art.POINTER;
/* 32 */       case PIGSCENE: return Art.PIGSCENE;
/* 33 */       case BURNINGSKULL: return Art.BURNINGSKULL;
/* 34 */       case SKELETON: return Art.SKELETON;
/* 35 */       case DONKEYKONG: return Art.DONKEYKONG;
/* 36 */       case WITHER: return Art.WITHER; }
/*    */     
/* 38 */     throw new AssertionError(art);
/*    */   }
/*    */ 
/*    */   
/*    */   public static EnumArt BukkitToNotch(Art art) {
/* 43 */     switch (art) { case KEBAB:
/* 44 */         return EnumArt.KEBAB;
/* 45 */       case AZTEC: return EnumArt.AZTEC;
/* 46 */       case ALBAN: return EnumArt.ALBAN;
/* 47 */       case AZTEC2: return EnumArt.AZTEC2;
/* 48 */       case BOMB: return EnumArt.BOMB;
/* 49 */       case PLANT: return EnumArt.PLANT;
/* 50 */       case WASTELAND: return EnumArt.WASTELAND;
/* 51 */       case POOL: return EnumArt.POOL;
/* 52 */       case COURBET: return EnumArt.COURBET;
/* 53 */       case SEA: return EnumArt.SEA;
/* 54 */       case SUNSET: return EnumArt.SUNSET;
/* 55 */       case CREEBET: return EnumArt.CREEBET;
/* 56 */       case WANDERER: return EnumArt.WANDERER;
/* 57 */       case GRAHAM: return EnumArt.GRAHAM;
/* 58 */       case MATCH: return EnumArt.MATCH;
/* 59 */       case BUST: return EnumArt.BUST;
/* 60 */       case STAGE: return EnumArt.STAGE;
/* 61 */       case VOID: return EnumArt.VOID;
/* 62 */       case SKULL_AND_ROSES: return EnumArt.SKULL_AND_ROSES;
/* 63 */       case FIGHTERS: return EnumArt.FIGHTERS;
/* 64 */       case POINTER: return EnumArt.POINTER;
/* 65 */       case PIGSCENE: return EnumArt.PIGSCENE;
/* 66 */       case BURNINGSKULL: return EnumArt.BURNINGSKULL;
/* 67 */       case SKELETON: return EnumArt.SKELETON;
/* 68 */       case DONKEYKONG: return EnumArt.DONKEYKONG;
/* 69 */       case WITHER: return EnumArt.WITHER; }
/*    */     
/* 71 */     throw new AssertionError(art);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftArt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */