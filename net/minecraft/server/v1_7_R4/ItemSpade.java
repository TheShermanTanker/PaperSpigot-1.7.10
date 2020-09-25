/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.com.google.common.collect.Sets;
/*    */ 
/*    */ 
/*    */ public class ItemSpade
/*    */   extends ItemTool
/*    */ {
/* 10 */   private static final Set c = Sets.newHashSet((Object[])new Block[] { Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW, Blocks.SNOW_BLOCK, Blocks.CLAY, Blocks.SOIL, Blocks.SOUL_SAND, Blocks.MYCEL });
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemSpade(EnumToolMaterial paramEnumToolMaterial) {
/* 15 */     super(1.0F, paramEnumToolMaterial, c);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canDestroySpecialBlock(Block paramBlock) {
/* 20 */     if (paramBlock == Blocks.SNOW) return true; 
/* 21 */     if (paramBlock == Blocks.SNOW_BLOCK) return true; 
/* 22 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemSpade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */