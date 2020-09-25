/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockWeb
/*    */   extends Block
/*    */ {
/*    */   public BlockWeb() {
/* 13 */     super(Material.WEB);
/* 14 */     a(CreativeModeTab.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity) {
/* 19 */     paramEntity.as();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean c() {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 29 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b() {
/* 34 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 49 */     return Items.STRING;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean E() {
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockWeb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */