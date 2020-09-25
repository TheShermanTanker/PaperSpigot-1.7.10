/*     */ package net.minecraft.server.v1_7_R4;
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
/*     */ class GameRuleValue
/*     */ {
/*     */   private String a;
/*     */   private boolean b;
/*     */   private int c;
/*     */   private double d;
/*     */   
/*     */   public GameRuleValue(String paramString) {
/* 117 */     a(paramString);
/*     */   }
/*     */   
/*     */   public void a(String paramString) {
/* 121 */     this.a = paramString;
/* 122 */     this.b = Boolean.parseBoolean(paramString);
/*     */     try {
/* 124 */       this.c = Integer.parseInt(paramString);
/* 125 */     } catch (NumberFormatException numberFormatException) {}
/*     */     
/*     */     try {
/* 128 */       this.d = Double.parseDouble(paramString);
/* 129 */     } catch (NumberFormatException numberFormatException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public String a() {
/* 134 */     return this.a;
/*     */   }
/*     */   
/*     */   public boolean b() {
/* 138 */     return this.b;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GameRuleValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */