/*    */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.server.v1_7_R4.Block;
/*    */ import net.minecraft.server.v1_7_R4.Blocks;
/*    */ import net.minecraft.server.v1_7_R4.World;
/*    */ import org.bukkit.BlockChangeDelegate;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ public class StructureGrowDelegate
/*    */   implements BlockChangeDelegate
/*    */ {
/*    */   private final CraftWorld world;
/* 17 */   private final List<BlockState> blocks = new ArrayList<BlockState>();
/*    */   
/*    */   public StructureGrowDelegate(World world) {
/* 20 */     this.world = world.getWorld();
/*    */   }
/*    */   
/*    */   public boolean setRawTypeId(int x, int y, int z, int type) {
/* 24 */     return setRawTypeIdAndData(x, y, z, type, 0);
/*    */   }
/*    */   
/*    */   public boolean setRawTypeIdAndData(int x, int y, int z, int type, int data) {
/* 28 */     BlockState state = this.world.getBlockAt(x, y, z).getState();
/* 29 */     state.setTypeId(type);
/* 30 */     state.setData(new MaterialData(type, (byte)data));
/* 31 */     this.blocks.add(state);
/* 32 */     return true;
/*    */   }
/*    */   
/*    */   public boolean setTypeId(int x, int y, int z, int typeId) {
/* 36 */     return setRawTypeId(x, y, z, typeId);
/*    */   }
/*    */   
/*    */   public boolean setTypeIdAndData(int x, int y, int z, int typeId, int data) {
/* 40 */     return setRawTypeIdAndData(x, y, z, typeId, data);
/*    */   }
/*    */   
/*    */   public int getTypeId(int x, int y, int z) {
/* 44 */     for (BlockState state : this.blocks) {
/* 45 */       if (state.getX() == x && state.getY() == y && state.getZ() == z) {
/* 46 */         return state.getTypeId();
/*    */       }
/*    */     } 
/*    */     
/* 50 */     return this.world.getBlockTypeIdAt(x, y, z);
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 54 */     return this.world.getMaxHeight();
/*    */   }
/*    */   
/*    */   public List<BlockState> getBlocks() {
/* 58 */     return this.blocks;
/*    */   }
/*    */   
/*    */   public boolean isEmpty(int x, int y, int z) {
/* 62 */     for (BlockState state : this.blocks) {
/* 63 */       if (state.getX() == x && state.getY() == y && state.getZ() == z) {
/* 64 */         return (Block.getById(state.getTypeId()) == Blocks.AIR);
/*    */       }
/*    */     } 
/*    */     
/* 68 */     return this.world.getBlockAt(x, y, z).isEmpty();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\StructureGrowDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */