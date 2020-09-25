/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.BlockJukeBox;
/*    */ import net.minecraft.server.v1_7_R4.Blocks;
/*    */ import net.minecraft.server.v1_7_R4.ItemStack;
/*    */ import net.minecraft.server.v1_7_R4.TileEntityRecordPlayer;
/*    */ import net.minecraft.server.v1_7_R4.World;
/*    */ import org.bukkit.Effect;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Jukebox;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*    */ 
/*    */ public class CraftJukebox extends CraftBlockState implements Jukebox {
/*    */   private final CraftWorld world;
/*    */   
/*    */   public CraftJukebox(Block block) {
/* 19 */     super(block);
/*    */     
/* 21 */     this.world = (CraftWorld)block.getWorld();
/* 22 */     this.jukebox = (TileEntityRecordPlayer)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   private final TileEntityRecordPlayer jukebox;
/*    */   
/*    */   public Material getPlaying() {
/* 27 */     ItemStack record = this.jukebox.getRecord();
/* 28 */     if (record == null) {
/* 29 */       return Material.AIR;
/*    */     }
/* 31 */     return CraftMagicNumbers.getMaterial(record.getItem());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPlaying(Material record) {
/* 36 */     if (record == null || CraftMagicNumbers.getItem(record) == null) {
/* 37 */       record = Material.AIR;
/* 38 */       this.jukebox.setRecord(null);
/*    */     } else {
/* 40 */       this.jukebox.setRecord(new ItemStack(CraftMagicNumbers.getItem(record), 1));
/*    */     } 
/* 42 */     this.jukebox.update();
/* 43 */     if (record == Material.AIR) {
/* 44 */       this.world.getHandle().setData(getX(), getY(), getZ(), 0, 3);
/*    */     } else {
/* 46 */       this.world.getHandle().setData(getX(), getY(), getZ(), 1, 3);
/*    */     } 
/* 48 */     this.world.playEffect(getLocation(), Effect.RECORD_PLAY, record.getId());
/*    */   }
/*    */   
/*    */   public boolean isPlaying() {
/* 52 */     return (getRawData() == 1);
/*    */   }
/*    */   
/*    */   public boolean eject() {
/* 56 */     boolean result = isPlaying();
/* 57 */     ((BlockJukeBox)Blocks.JUKEBOX).dropRecord((World)this.world.getHandle(), getX(), getY(), getZ());
/* 58 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftJukebox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */