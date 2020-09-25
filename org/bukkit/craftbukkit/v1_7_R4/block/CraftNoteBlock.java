/*    */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.TileEntityNote;
/*    */ import net.minecraft.server.v1_7_R4.World;
/*    */ import org.bukkit.Instrument;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Note;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.NoteBlock;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*    */ 
/*    */ public class CraftNoteBlock extends CraftBlockState implements NoteBlock {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityNote note;
/*    */   
/*    */   public CraftNoteBlock(Block block) {
/* 18 */     super(block);
/*    */     
/* 20 */     this.world = (CraftWorld)block.getWorld();
/* 21 */     this.note = (TileEntityNote)this.world.getTileEntityAt(getX(), getY(), getZ());
/*    */   }
/*    */   
/*    */   public Note getNote() {
/* 25 */     return new Note(this.note.note);
/*    */   }
/*    */   
/*    */   public byte getRawNote() {
/* 29 */     return this.note.note;
/*    */   }
/*    */   
/*    */   public void setNote(Note n) {
/* 33 */     this.note.note = n.getId();
/*    */   }
/*    */   
/*    */   public void setRawNote(byte n) {
/* 37 */     this.note.note = n;
/*    */   }
/*    */   
/*    */   public boolean play() {
/* 41 */     Block block = getBlock();
/*    */     
/* 43 */     if (block.getType() == Material.NOTE_BLOCK) {
/* 44 */       this.note.play((World)this.world.getHandle(), getX(), getY(), getZ());
/* 45 */       return true;
/*    */     } 
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean play(byte instrument, byte note) {
/* 53 */     Block block = getBlock();
/*    */     
/* 55 */     if (block.getType() == Material.NOTE_BLOCK) {
/* 56 */       this.world.getHandle().playBlockAction(getX(), getY(), getZ(), CraftMagicNumbers.getBlock(block), instrument, note);
/* 57 */       return true;
/*    */     } 
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean play(Instrument instrument, Note note) {
/* 65 */     Block block = getBlock();
/*    */     
/* 67 */     if (block.getType() == Material.NOTE_BLOCK) {
/* 68 */       this.world.getHandle().playBlockAction(getX(), getY(), getZ(), CraftMagicNumbers.getBlock(block), instrument.getType(), note.getId());
/* 69 */       return true;
/*    */     } 
/* 71 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftNoteBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */