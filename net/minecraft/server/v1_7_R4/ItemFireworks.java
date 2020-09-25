/*    */ package net.minecraft.server.v1_7_R4;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemFireworks
/*    */   extends Item
/*    */ {
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 35 */     if (!paramWorld.isStatic) {
/*    */ 
/*    */       
/* 38 */       EntityFireworks entityFireworks = new EntityFireworks(paramWorld, (paramInt1 + paramFloat1), (paramInt2 + paramFloat2), (paramInt3 + paramFloat3), paramItemStack);
/* 39 */       paramWorld.addEntity(entityFireworks);
/*    */       
/* 41 */       if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 42 */         paramItemStack.count--;
/*    */       }
/* 44 */       return true;
/*    */     } 
/*    */     
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemFireworks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */