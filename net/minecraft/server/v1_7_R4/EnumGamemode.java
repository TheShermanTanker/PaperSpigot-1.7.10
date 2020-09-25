/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumGamemode
/*    */ {
/*  9 */   NONE(-1, ""), SURVIVAL(0, "survival"), CREATIVE(1, "creative"), ADVENTURE(2, "adventure");
/*    */   
/*    */   String f;
/*    */   int e;
/*    */   
/*    */   EnumGamemode(int paramInt1, String paramString1) {
/* 15 */     this.e = paramInt1;
/* 16 */     this.f = paramString1;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 20 */     return this.e;
/*    */   }
/*    */   
/*    */   public String b() {
/* 24 */     return this.f;
/*    */   }
/*    */   
/*    */   public void a(PlayerAbilities paramPlayerAbilities) {
/* 28 */     if (this == CREATIVE) {
/* 29 */       paramPlayerAbilities.canFly = true;
/* 30 */       paramPlayerAbilities.canInstantlyBuild = true;
/* 31 */       paramPlayerAbilities.isInvulnerable = true;
/*    */     } else {
/* 33 */       paramPlayerAbilities.canFly = false;
/* 34 */       paramPlayerAbilities.canInstantlyBuild = false;
/* 35 */       paramPlayerAbilities.isInvulnerable = false;
/* 36 */       paramPlayerAbilities.isFlying = false;
/*    */     } 
/* 38 */     paramPlayerAbilities.mayBuild = !isAdventure();
/*    */   }
/*    */   
/*    */   public boolean isAdventure() {
/* 42 */     return (this == ADVENTURE);
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 46 */     return (this == CREATIVE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static EnumGamemode getById(int paramInt) {
/* 54 */     for (EnumGamemode enumGamemode : values()) {
/* 55 */       if (enumGamemode.e == paramInt) {
/* 56 */         return enumGamemode;
/*    */       }
/*    */     } 
/* 59 */     return SURVIVAL;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumGamemode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */