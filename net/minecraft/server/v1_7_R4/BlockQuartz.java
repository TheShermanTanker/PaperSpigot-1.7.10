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
/*     */ public class BlockQuartz
/*     */   extends Block
/*     */ {
/*  19 */   public static final String[] a = new String[] { "default", "chiseled", "lines" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   private static final String[] b = new String[] { "side", "chiseled", "lines", null, null };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockQuartz() {
/*  38 */     super(Material.STONE);
/*  39 */     a(CreativeModeTab.b);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5) {
/*  71 */     if (paramInt5 == 2) {
/*  72 */       switch (paramInt4) {
/*     */         case 2:
/*     */         case 3:
/*  75 */           paramInt5 = 4;
/*     */           break;
/*     */         case 4:
/*     */         case 5:
/*  79 */           paramInt5 = 3;
/*     */           break;
/*     */         case 0:
/*     */         case 1:
/*  83 */           paramInt5 = 2;
/*     */           break;
/*     */       } 
/*     */     
/*     */     }
/*  88 */     return paramInt5;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDropData(int paramInt) {
/*  93 */     if (paramInt == 3 || paramInt == 4) return 2;
/*     */     
/*  95 */     return paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack j(int paramInt) {
/* 100 */     if (paramInt == 3 || paramInt == 4) return new ItemStack(Item.getItemOf(this), 1, 2); 
/* 101 */     return super.j(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/* 106 */     return 39;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialMapColor f(int paramInt) {
/* 136 */     return MaterialMapColor.p;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockQuartz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */