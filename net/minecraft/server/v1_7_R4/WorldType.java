/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class WorldType
/*    */ {
/*  5 */   public static final WorldType[] types = new WorldType[16];
/*    */   
/*  7 */   public static final WorldType NORMAL = (new WorldType(0, "default", 1)).i();
/*  8 */   public static final WorldType FLAT = new WorldType(1, "flat");
/*  9 */   public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
/* 10 */   public static final WorldType AMPLIFIED = (new WorldType(3, "amplified")).j();
/*    */   
/* 12 */   public static final WorldType NORMAL_1_1 = (new WorldType(8, "default_1_1", 0)).a(false);
/*    */   
/*    */   private final int g;
/*    */   private final String name;
/*    */   private final int version;
/*    */   private boolean j;
/*    */   private boolean k;
/*    */   private boolean l;
/*    */   
/*    */   private WorldType(int paramInt, String paramString) {
/* 22 */     this(paramInt, paramString, 0);
/*    */   }
/*    */   
/*    */   private WorldType(int paramInt1, String paramString, int paramInt2) {
/* 26 */     this.name = paramString;
/* 27 */     this.version = paramInt2;
/* 28 */     this.j = true;
/* 29 */     this.g = paramInt1;
/* 30 */     types[paramInt1] = this;
/*    */   }
/*    */   
/*    */   public String name() {
/* 34 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getVersion() {
/* 46 */     return this.version;
/*    */   }
/*    */   
/*    */   public WorldType a(int paramInt) {
/* 50 */     if (this == NORMAL && paramInt == 0) {
/* 51 */       return NORMAL_1_1;
/*    */     }
/* 53 */     return this;
/*    */   }
/*    */   
/*    */   private WorldType a(boolean paramBoolean) {
/* 57 */     this.j = paramBoolean;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private WorldType i() {
/* 66 */     this.k = true;
/* 67 */     return this;
/*    */   }
/*    */   
/*    */   public boolean f() {
/* 71 */     return this.k;
/*    */   }
/*    */   
/*    */   public static WorldType getType(String paramString) {
/* 75 */     for (byte b = 0; b < types.length; b++) {
/* 76 */       if (types[b] != null && (types[b]).name.equalsIgnoreCase(paramString)) {
/* 77 */         return types[b];
/*    */       }
/*    */     } 
/* 80 */     return null;
/*    */   }
/*    */   
/*    */   public int g() {
/* 84 */     return this.g;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private WorldType j() {
/* 92 */     this.l = true;
/* 93 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */