/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockLongGrass
/*     */   extends BlockPlant
/*     */   implements IBlockFragilePlantElement
/*     */ {
/*  13 */   private static final String[] a = new String[] { "deadbush", "tallgrass", "fern" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockLongGrass() {
/*  24 */     super(Material.REPLACEABLE_PLANT);
/*  25 */     float f = 0.4F;
/*  26 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
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
/*     */   public boolean j(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  45 */     return a(paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3));
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
/*     */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/*  65 */     if (paramRandom.nextInt(8) == 0) {
/*  66 */       return Items.SEEDS;
/*     */     }
/*     */     
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDropCount(int paramInt, Random paramRandom) {
/*  74 */     return 1 + paramRandom.nextInt(paramInt * 2 + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, EntityHuman paramEntityHuman, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  79 */     if (!paramWorld.isStatic && paramEntityHuman.bF() != null && paramEntityHuman.bF().getItem() == Items.SHEARS) {
/*  80 */       paramEntityHuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
/*     */ 
/*     */       
/*  83 */       a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Blocks.LONG_GRASS, 1, paramInt4));
/*     */     } else {
/*  85 */       super.a(paramWorld, paramEntityHuman, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDropData(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  91 */     return paramWorld.getData(paramInt1, paramInt2, paramInt3);
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
/*     */   public boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 112 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 113 */     if (i == 0) {
/* 114 */       return false;
/*     */     }
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 126 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 127 */     byte b = 2;
/* 128 */     if (i == 2) {
/* 129 */       b = 3;
/*     */     }
/* 131 */     if (Blocks.DOUBLE_PLANT.canPlace(paramWorld, paramInt1, paramInt2, paramInt3))
/* 132 */       Blocks.DOUBLE_PLANT.c(paramWorld, paramInt1, paramInt2, paramInt3, b, 2); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockLongGrass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */