/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BlockStepAbstract
/*     */   extends Block
/*     */ {
/*     */   protected final boolean a;
/*     */   
/*     */   public BlockStepAbstract(boolean paramBoolean, Material paramMaterial) {
/*  20 */     super(paramMaterial);
/*  21 */     this.a = paramBoolean;
/*     */     
/*  23 */     if (paramBoolean) {
/*  24 */       this.q = true;
/*     */     } else {
/*  26 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } 
/*  28 */     g(255);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  33 */     if (this.a) {
/*  34 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } else {
/*  36 */       boolean bool = ((paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3) & 0x8) != 0) ? true : false;
/*  37 */       if (bool) {
/*  38 */         a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       } else {
/*  40 */         a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void g() {
/*  47 */     if (this.a) {
/*  48 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } else {
/*  50 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity) {
/*  56 */     updateShape(paramWorld, paramInt1, paramInt2, paramInt3);
/*  57 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  62 */     return this.a;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5) {
/*  67 */     if (this.a) return paramInt5;
/*     */     
/*  69 */     if (paramInt4 == 0 || (paramInt4 != 1 && paramFloat2 > 0.5D)) {
/*  70 */       return paramInt5 | 0x8;
/*     */     }
/*  72 */     return paramInt5;
/*     */   }
/*     */ 
/*     */   
/*     */   public int a(Random paramRandom) {
/*  77 */     if (this.a) {
/*  78 */       return 2;
/*     */     }
/*  80 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDropData(int paramInt) {
/*  85 */     return paramInt & 0x7;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  90 */     return this.a;
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
/*     */   public abstract String b(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDropData(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 126 */     return super.getDropData(paramWorld, paramInt1, paramInt2, paramInt3) & 0x7;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockStepAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */