/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.com.google.common.collect.Sets;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemPickaxe
/*    */   extends ItemTool
/*    */ {
/* 11 */   private static final Set c = Sets.newHashSet((Object[])new Block[] { Blocks.COBBLESTONE, Blocks.DOUBLE_STEP, Blocks.STEP, Blocks.STONE, Blocks.SANDSTONE, Blocks.MOSSY_COBBLESTONE, Blocks.IRON_ORE, Blocks.IRON_BLOCK, Blocks.COAL_ORE, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.DIAMOND_ORE, Blocks.DIAMOND_BLOCK, Blocks.ICE, Blocks.NETHERRACK, Blocks.LAPIS_ORE, Blocks.LAPIS_BLOCK, Blocks.REDSTONE_ORE, Blocks.GLOWING_REDSTONE_ORE, Blocks.RAILS, Blocks.DETECTOR_RAIL, Blocks.GOLDEN_RAIL, Blocks.ACTIVATOR_RAIL });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ItemPickaxe(EnumToolMaterial paramEnumToolMaterial) {
/* 17 */     super(2.0F, paramEnumToolMaterial, c);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canDestroySpecialBlock(Block paramBlock) {
/* 22 */     if (paramBlock == Blocks.OBSIDIAN) return (this.b.d() == 3); 
/* 23 */     if (paramBlock == Blocks.DIAMOND_BLOCK || paramBlock == Blocks.DIAMOND_ORE) return (this.b.d() >= 2); 
/* 24 */     if (paramBlock == Blocks.EMERALD_ORE || paramBlock == Blocks.EMERALD_BLOCK) return (this.b.d() >= 2); 
/* 25 */     if (paramBlock == Blocks.GOLD_BLOCK || paramBlock == Blocks.GOLD_ORE) return (this.b.d() >= 2); 
/* 26 */     if (paramBlock == Blocks.IRON_BLOCK || paramBlock == Blocks.IRON_ORE) return (this.b.d() >= 1); 
/* 27 */     if (paramBlock == Blocks.LAPIS_BLOCK || paramBlock == Blocks.LAPIS_ORE) return (this.b.d() >= 1); 
/* 28 */     if (paramBlock == Blocks.REDSTONE_ORE || paramBlock == Blocks.GLOWING_REDSTONE_ORE) return (this.b.d() >= 2); 
/* 29 */     if (paramBlock.getMaterial() == Material.STONE) return true; 
/* 30 */     if (paramBlock.getMaterial() == Material.ORE) return true; 
/* 31 */     if (paramBlock.getMaterial() == Material.HEAVY) return true; 
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock) {
/* 37 */     if (paramBlock.getMaterial() == Material.ORE || paramBlock.getMaterial() == Material.HEAVY || paramBlock.getMaterial() == Material.STONE) {
/* 38 */       return this.a;
/*    */     }
/* 40 */     return super.getDestroySpeed(paramItemStack, paramBlock);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemPickaxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */