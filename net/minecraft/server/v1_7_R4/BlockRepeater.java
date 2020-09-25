/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
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
/*     */ public class BlockRepeater
/*     */   extends BlockDiodeAbstract
/*     */ {
/*  16 */   public static final double[] b = new double[] { -0.0625D, 0.0625D, 0.1875D, 0.3125D };
/*     */ 
/*     */ 
/*     */   
/*  20 */   private static final int[] M = new int[] { 1, 2, 3, 4 };
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockRepeater(boolean paramBoolean) {
/*  25 */     super(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  30 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  31 */     int j = (i & 0xC) >> 2;
/*  32 */     j = j + 1 << 2 & 0xC;
/*     */     
/*  34 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, j | i & 0x3, 3);
/*  35 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int b(int paramInt) {
/*  40 */     return M[(paramInt & 0xC) >> 2] * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockDiodeAbstract e() {
/*  45 */     return Blocks.DIODE_ON;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockDiodeAbstract i() {
/*  50 */     return Blocks.DIODE_OFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/*  55 */     return Items.DIODE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int b() {
/*  65 */     return 15;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean g(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  70 */     return (h(paramIBlockAccess, paramInt1, paramInt2, paramInt3, paramInt4) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(Block paramBlock) {
/*  75 */     return d(paramBlock);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 131 */     super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
/* 132 */     e(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockRepeater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */