/*    */ package net.minecraft.server.v1_7_R4;public enum EnumDifficulty {
/*    */   private static final EnumDifficulty[] e;
/*    */   private final int f;
/*  4 */   PEACEFUL(0, "options.difficulty.peaceful"),
/*  5 */   EASY(1, "options.difficulty.easy"),
/*  6 */   NORMAL(2, "options.difficulty.normal"),
/*  7 */   HARD(3, "options.difficulty.hard"); private final String g;
/*    */   static {
/*  9 */     e = new EnumDifficulty[(values()).length];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 27 */     for (EnumDifficulty enumDifficulty : values())
/* 28 */       e[enumDifficulty.f] = enumDifficulty; 
/*    */   } EnumDifficulty(int paramInt1, String paramString1) {
/*    */     this.f = paramInt1;
/*    */     this.g = paramString1;
/*    */   } public String b() {
/* 33 */     return this.g;
/*    */   }
/*    */   
/*    */   public int a() {
/*    */     return this.f;
/*    */   }
/*    */   
/*    */   public static EnumDifficulty getById(int paramInt) {
/*    */     return e[paramInt % e.length];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumDifficulty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */