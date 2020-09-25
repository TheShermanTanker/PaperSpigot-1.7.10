/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.event.block.NotePlayEvent;
/*    */ 
/*    */ public class TileEntityNote extends TileEntity {
/*    */   public byte note;
/*    */   public boolean i;
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound) {
/* 11 */     super.b(nbttagcompound);
/* 12 */     nbttagcompound.setByte("note", this.note);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 16 */     super.a(nbttagcompound);
/* 17 */     this.note = nbttagcompound.getByte("note");
/* 18 */     if (this.note < 0) {
/* 19 */       this.note = 0;
/*    */     }
/*    */     
/* 22 */     if (this.note > 24) {
/* 23 */       this.note = 24;
/*    */     }
/*    */   }
/*    */   
/*    */   public void a() {
/* 28 */     this.note = (byte)((this.note + 1) % 25);
/* 29 */     update();
/*    */   }
/*    */   
/*    */   public void play(World world, int i, int j, int k) {
/* 33 */     if (world.getType(i, j + 1, k).getMaterial() == Material.AIR) {
/* 34 */       Material material = world.getType(i, j - 1, k).getMaterial();
/* 35 */       byte b0 = 0;
/*    */       
/* 37 */       if (material == Material.STONE) {
/* 38 */         b0 = 1;
/*    */       }
/*    */       
/* 41 */       if (material == Material.SAND) {
/* 42 */         b0 = 2;
/*    */       }
/*    */       
/* 45 */       if (material == Material.SHATTERABLE) {
/* 46 */         b0 = 3;
/*    */       }
/*    */       
/* 49 */       if (material == Material.WOOD) {
/* 50 */         b0 = 4;
/*    */       }
/*    */ 
/*    */       
/* 54 */       NotePlayEvent event = CraftEventFactory.callNotePlayEvent(this.world, i, j, k, b0, this.note);
/* 55 */       if (!event.isCancelled())
/* 56 */         this.world.playBlockAction(i, j, k, Blocks.NOTE_BLOCK, event.getInstrument().getType(), event.getNote().getId()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityNote.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */