/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemRecord
/*    */   extends Item
/*    */ {
/* 14 */   private static final Map b = new HashMap<Object, Object>();
/*    */   
/*    */   public final String a;
/*    */   
/*    */   protected ItemRecord(String paramString) {
/* 19 */     this.a = paramString;
/* 20 */     this.maxStackSize = 1;
/* 21 */     a(CreativeModeTab.f);
/*    */     
/* 23 */     b.put(paramString, this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 33 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == Blocks.JUKEBOX && paramWorld.getData(paramInt1, paramInt2, paramInt3) == 0) {
/* 34 */       if (paramWorld.isStatic) return true;
/*    */       
/* 36 */       ((BlockJukeBox)Blocks.JUKEBOX).b(paramWorld, paramInt1, paramInt2, paramInt3, paramItemStack);
/* 37 */       paramWorld.a((EntityHuman)null, 1005, paramInt1, paramInt2, paramInt3, Item.getId(this));
/* 38 */       paramItemStack.count--;
/* 39 */       return true;
/*    */     } 
/* 41 */     return false;
/*    */   }
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
/*    */   public EnumItemRarity f(ItemStack paramItemStack) {
/* 55 */     return EnumItemRarity.RARE;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */