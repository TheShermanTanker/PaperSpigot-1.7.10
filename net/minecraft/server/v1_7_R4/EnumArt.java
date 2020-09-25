/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumArt
/*    */ {
/*    */   public static final int A;
/*    */   public final String B;
/*    */   public final int C;
/* 15 */   KEBAB("Kebab", 16, 16, 0, 0),
/* 16 */   AZTEC("Aztec", 16, 16, 16, 0),
/* 17 */   ALBAN("Alban", 16, 16, 32, 0),
/* 18 */   AZTEC2("Aztec2", 16, 16, 48, 0),
/* 19 */   BOMB("Bomb", 16, 16, 64, 0),
/* 20 */   PLANT("Plant", 16, 16, 80, 0),
/* 21 */   WASTELAND("Wasteland", 16, 16, 96, 0),
/*    */   
/* 23 */   POOL("Pool", 32, 16, 0, 32),
/* 24 */   COURBET("Courbet", 32, 16, 32, 32),
/* 25 */   SEA("Sea", 32, 16, 64, 32),
/* 26 */   SUNSET("Sunset", 32, 16, 96, 32),
/* 27 */   CREEBET("Creebet", 32, 16, 128, 32),
/*    */   
/* 29 */   WANDERER("Wanderer", 16, 32, 0, 64),
/* 30 */   GRAHAM("Graham", 16, 32, 16, 64),
/*    */   
/* 32 */   MATCH("Match", 32, 32, 0, 128),
/* 33 */   BUST("Bust", 32, 32, 32, 128),
/* 34 */   STAGE("Stage", 32, 32, 64, 128),
/* 35 */   VOID("Void", 32, 32, 96, 128),
/* 36 */   SKULL_AND_ROSES("SkullAndRoses", 32, 32, 128, 128),
/* 37 */   WITHER("Wither", 32, 32, 160, 128),
/* 38 */   FIGHTERS("Fighters", 64, 32, 0, 96),
/*    */   
/* 40 */   POINTER("Pointer", 64, 64, 0, 192),
/* 41 */   PIGSCENE("Pigscene", 64, 64, 64, 192),
/* 42 */   BURNINGSKULL("BurningSkull", 64, 64, 128, 192),
/*    */   
/* 44 */   SKELETON("Skeleton", 64, 48, 192, 64),
/* 45 */   DONKEYKONG("DonkeyKong", 64, 48, 192, 112);
/*    */   static {
/* 47 */     A = "SkullAndRoses".length();
/*    */   }
/*    */   public final int D;
/*    */   public final int E;
/*    */   public final int F;
/*    */   
/*    */   EnumArt(String paramString1, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 54 */     this.B = paramString1;
/* 55 */     this.C = paramInt1;
/* 56 */     this.D = paramInt2;
/* 57 */     this.E = paramInt3;
/* 58 */     this.F = paramInt4;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumArt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */