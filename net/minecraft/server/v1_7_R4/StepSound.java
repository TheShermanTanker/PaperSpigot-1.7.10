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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StepSound
/*     */ {
/*     */   public final String a;
/*     */   public final float b;
/*     */   public final float c;
/*     */   
/*     */   public StepSound(String paramString, float paramFloat1, float paramFloat2) {
/* 120 */     this.a = paramString;
/* 121 */     this.b = paramFloat1;
/* 122 */     this.c = paramFloat2;
/*     */   }
/*     */   
/*     */   public float getVolume1() {
/* 126 */     return this.b;
/*     */   }
/*     */   
/*     */   public float getVolume2() {
/* 130 */     return this.c;
/*     */   }
/*     */   
/*     */   public String getBreakSound() {
/* 134 */     return "dig." + this.a;
/*     */   }
/*     */   
/*     */   public String getStepSound() {
/* 138 */     return "step." + this.a;
/*     */   }
/*     */   
/*     */   public String getPlaceSound() {
/* 142 */     return getBreakSound();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\StepSound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */