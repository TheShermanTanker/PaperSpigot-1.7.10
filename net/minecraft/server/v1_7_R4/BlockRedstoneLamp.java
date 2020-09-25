/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockRedstoneLamp
/*    */   extends Block
/*    */ {
/*    */   private final boolean a;
/*    */   
/*    */   public BlockRedstoneLamp(boolean flag) {
/* 12 */     super(Material.BUILDABLE_GLASS);
/* 13 */     this.a = flag;
/* 14 */     if (flag) {
/* 15 */       a(1.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public void onPlace(World world, int i, int j, int k) {
/* 20 */     if (!world.isStatic) {
/* 21 */       if (this.a && !world.isBlockIndirectlyPowered(i, j, k)) {
/* 22 */         world.a(i, j, k, this, 4);
/* 23 */       } else if (!this.a && world.isBlockIndirectlyPowered(i, j, k)) {
/*    */         
/* 25 */         if (CraftEventFactory.callRedstoneChange(world, i, j, k, 0, 15).getNewCurrent() != 15) {
/*    */           return;
/*    */         }
/*    */ 
/*    */         
/* 30 */         world.setTypeAndData(i, j, k, Blocks.REDSTONE_LAMP_ON, 0, 2);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 36 */     if (!world.isStatic) {
/* 37 */       if (this.a && !world.isBlockIndirectlyPowered(i, j, k)) {
/* 38 */         world.a(i, j, k, this, 4);
/* 39 */       } else if (!this.a && world.isBlockIndirectlyPowered(i, j, k)) {
/*    */         
/* 41 */         if (CraftEventFactory.callRedstoneChange(world, i, j, k, 0, 15).getNewCurrent() != 15) {
/*    */           return;
/*    */         }
/*    */ 
/*    */         
/* 46 */         world.setTypeAndData(i, j, k, Blocks.REDSTONE_LAMP_ON, 0, 2);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 52 */     if (!world.isStatic && this.a && !world.isBlockIndirectlyPowered(i, j, k)) {
/*    */       
/* 54 */       if (CraftEventFactory.callRedstoneChange(world, i, j, k, 15, 0).getNewCurrent() != 0) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 59 */       world.setTypeAndData(i, j, k, Blocks.REDSTONE_LAMP_OFF, 0, 2);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Item getDropType(int i, Random random, int j) {
/* 64 */     return Item.getItemOf(Blocks.REDSTONE_LAMP_OFF);
/*    */   }
/*    */   
/*    */   protected ItemStack j(int i) {
/* 68 */     return new ItemStack(Blocks.REDSTONE_LAMP_OFF);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockRedstoneLamp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */