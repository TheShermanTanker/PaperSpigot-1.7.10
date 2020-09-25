/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockPlant
/*    */   extends Block
/*    */ {
/*    */   protected BlockPlant(Material paramMaterial) {
/* 12 */     super(paramMaterial);
/* 13 */     a(true);
/* 14 */     float f = 0.2F;
/* 15 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
/* 16 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   protected BlockPlant() {
/* 20 */     this(Material.PLANT);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 25 */     return (super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3) && a(paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3)));
/*    */   }
/*    */   
/*    */   protected boolean a(Block paramBlock) {
/* 29 */     return (paramBlock == Blocks.GRASS || paramBlock == Blocks.DIRT || paramBlock == Blocks.SOIL);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/* 34 */     super.doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock);
/* 35 */     e(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/* 40 */     e(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   
/*    */   protected void e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 44 */     if (!j(paramWorld, paramInt1, paramInt2, paramInt3)) {
/* 45 */       b(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3), 0);
/* 46 */       paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, getById(0), 0, 2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean j(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 52 */     return a(paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3));
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean c() {
/* 66 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 71 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b() {
/* 76 */     return 1;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */