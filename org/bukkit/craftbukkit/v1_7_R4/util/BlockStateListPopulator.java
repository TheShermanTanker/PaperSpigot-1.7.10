/*    */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.server.v1_7_R4.Block;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.BlockState;
/*    */ 
/*    */ 
/*    */ public class BlockStateListPopulator
/*    */ {
/*    */   private final World world;
/*    */   private final List<BlockState> list;
/*    */   
/*    */   public BlockStateListPopulator(World world) {
/* 16 */     this(world, new ArrayList<BlockState>());
/*    */   }
/*    */   
/*    */   public BlockStateListPopulator(World world, List<BlockState> list) {
/* 20 */     this.world = world;
/* 21 */     this.list = list;
/*    */   }
/*    */   
/*    */   public void setTypeAndData(int x, int y, int z, Block block, int data, int light) {
/* 25 */     BlockState state = this.world.getBlockAt(x, y, z).getState();
/* 26 */     state.setTypeId(Block.getId(block));
/* 27 */     state.setRawData((byte)data);
/* 28 */     this.list.add(state);
/*    */   }
/*    */   public void setTypeId(int x, int y, int z, int type) {
/* 31 */     BlockState state = this.world.getBlockAt(x, y, z).getState();
/* 32 */     state.setTypeId(type);
/* 33 */     this.list.add(state);
/*    */   }
/*    */   
/*    */   public void setTypeUpdate(int x, int y, int z, Block block) {
/* 37 */     setType(x, y, z, block);
/*    */   }
/*    */   
/*    */   public void setType(int x, int y, int z, Block block) {
/* 41 */     BlockState state = this.world.getBlockAt(x, y, z).getState();
/* 42 */     state.setTypeId(Block.getId(block));
/* 43 */     this.list.add(state);
/*    */   }
/*    */   
/*    */   public void updateList() {
/* 47 */     for (BlockState state : this.list) {
/* 48 */       state.update(true);
/*    */     }
/*    */   }
/*    */   
/*    */   public List<BlockState> getList() {
/* 53 */     return this.list;
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 57 */     return this.world;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\BlockStateListPopulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */