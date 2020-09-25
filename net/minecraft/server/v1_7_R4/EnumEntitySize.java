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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum EnumEntitySize
/*     */ {
/* 121 */   SIZE_1,
/* 122 */   SIZE_2,
/* 123 */   SIZE_3,
/* 124 */   SIZE_4,
/* 125 */   SIZE_5,
/* 126 */   SIZE_6;
/*     */   
/*     */   public int a(double paramDouble) {
/* 129 */     double d = paramDouble - MathHelper.floor(paramDouble) + 0.5D;
/*     */     
/* 131 */     switch (EntitySizes.a[ordinal()]) {
/*     */       case 1:
/* 133 */         if ((d < 0.0D) ? (d < -0.3125D) : (d < 0.3125D)) {
/* 134 */           return MathHelper.f(paramDouble * 32.0D);
/*     */         }
/*     */         
/* 137 */         return MathHelper.floor(paramDouble * 32.0D);
/*     */       case 2:
/* 139 */         if ((d < 0.0D) ? (d < -0.3125D) : (d < 0.3125D)) {
/* 140 */           return MathHelper.floor(paramDouble * 32.0D);
/*     */         }
/*     */         
/* 143 */         return MathHelper.f(paramDouble * 32.0D);
/*     */       case 3:
/* 145 */         if (d > 0.0D) {
/* 146 */           return MathHelper.floor(paramDouble * 32.0D);
/*     */         }
/*     */         
/* 149 */         return MathHelper.f(paramDouble * 32.0D);
/*     */       case 4:
/* 151 */         if ((d < 0.0D) ? (d < -0.1875D) : (d < 0.1875D)) {
/* 152 */           return MathHelper.f(paramDouble * 32.0D);
/*     */         }
/*     */         
/* 155 */         return MathHelper.floor(paramDouble * 32.0D);
/*     */       case 5:
/* 157 */         if ((d < 0.0D) ? (d < -0.1875D) : (d < 0.1875D)) {
/* 158 */           return MathHelper.floor(paramDouble * 32.0D);
/*     */         }
/*     */         
/* 161 */         return MathHelper.f(paramDouble * 32.0D);
/*     */     } 
/*     */     
/* 164 */     if (d > 0.0D) {
/* 165 */       return MathHelper.f(paramDouble * 32.0D);
/*     */     }
/*     */     
/* 168 */     return MathHelper.floor(paramDouble * 32.0D);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumEntitySize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */