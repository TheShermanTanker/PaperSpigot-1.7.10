/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BlockLogAbstract
/*    */   extends BlockRotatable
/*    */ {
/*    */   public BlockLogAbstract() {
/* 19 */     super(Material.WOOD);
/* 20 */     a(CreativeModeTab.b);
/* 21 */     c(2.0F);
/* 22 */     a(f);
/*    */   }
/*    */   
/*    */   public static int c(int paramInt) {
/* 26 */     return paramInt & 0x3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(Random paramRandom) {
/* 31 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 36 */     return Item.getItemOf(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 41 */     byte b = 4;
/* 42 */     int i = b + 1;
/*    */     
/* 44 */     if (paramWorld.b(paramInt1 - i, paramInt2 - i, paramInt3 - i, paramInt1 + i, paramInt2 + i, paramInt3 + i))
/* 45 */       for (byte b1 = -b; b1 <= b; b1++) {
/* 46 */         for (byte b2 = -b; b2 <= b; b2++) {
/* 47 */           for (byte b3 = -b; b3 <= b; b3++) {
/* 48 */             if (paramWorld.getType(paramInt1 + b1, paramInt2 + b2, paramInt3 + b3).getMaterial() == Material.LEAVES) {
/* 49 */               int j = paramWorld.getData(paramInt1 + b1, paramInt2 + b2, paramInt3 + b3);
/* 50 */               if ((j & 0x8) == 0)
/* 51 */                 paramWorld.setData(paramInt1 + b1, paramInt2 + b2, paramInt3 + b3, j | 0x8, 4); 
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockLogAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */