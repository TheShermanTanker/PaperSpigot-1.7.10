/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockLadder
/*     */   extends Block
/*     */ {
/*     */   protected BlockLadder() {
/*  12 */     super(Material.ORIENTABLE);
/*  13 */     a(CreativeModeTab.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  18 */     updateShape(paramWorld, paramInt1, paramInt2, paramInt3);
/*  19 */     return super.a(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  30 */     b(paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3));
/*     */   }
/*     */   
/*     */   public void b(int paramInt) {
/*  34 */     int i = paramInt;
/*  35 */     float f = 0.125F;
/*     */     
/*  37 */     if (i == 2) a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F); 
/*  38 */     if (i == 3) a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f); 
/*  39 */     if (i == 4) a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F); 
/*  40 */     if (i == 5) a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/*  59 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  64 */     if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3).r())
/*  65 */       return true; 
/*  66 */     if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3).r())
/*  67 */       return true; 
/*  68 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1).r())
/*  69 */       return true; 
/*  70 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1).r()) {
/*  71 */       return true;
/*     */     }
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5) {
/*  78 */     int i = paramInt5;
/*     */     
/*  80 */     if ((i == 0 || paramInt4 == 2) && paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1).r()) i = 2; 
/*  81 */     if ((i == 0 || paramInt4 == 3) && paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1).r()) i = 3; 
/*  82 */     if ((i == 0 || paramInt4 == 4) && paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3).r()) i = 4; 
/*  83 */     if ((i == 0 || paramInt4 == 5) && paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3).r()) i = 5;
/*     */     
/*  85 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/*  90 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  91 */     boolean bool = false;
/*     */     
/*  93 */     if (i == 2 && paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1).r()) bool = true; 
/*  94 */     if (i == 3 && paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1).r()) bool = true; 
/*  95 */     if (i == 4 && paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3).r()) bool = true; 
/*  96 */     if (i == 5 && paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3).r()) bool = true; 
/*  97 */     if (!bool) {
/*  98 */       b(paramWorld, paramInt1, paramInt2, paramInt3, i, 0);
/*  99 */       paramWorld.setAir(paramInt1, paramInt2, paramInt3);
/*     */     } 
/*     */     
/* 102 */     super.doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public int a(Random paramRandom) {
/* 107 */     return 1;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockLadder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */