/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.com.google.common.collect.Sets;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemAxe
/*    */   extends ItemTool
/*    */ {
/* 11 */   private static final Set c = Sets.newHashSet((Object[])new Block[] { Blocks.WOOD, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.JACK_O_LANTERN });
/*    */ 
/*    */ 
/*    */   
/*    */   protected ItemAxe(EnumToolMaterial paramEnumToolMaterial) {
/* 16 */     super(3.0F, paramEnumToolMaterial, c);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock) {
/* 21 */     if (paramBlock.getMaterial() == Material.WOOD || paramBlock.getMaterial() == Material.PLANT || paramBlock.getMaterial() == Material.REPLACEABLE_PLANT) {
/* 22 */       return this.a;
/*    */     }
/* 24 */     return super.getDestroySpeed(paramItemStack, paramBlock);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemAxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */