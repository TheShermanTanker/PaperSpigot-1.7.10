/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.command.CraftBlockCommandSender;
/*    */ 
/*    */ public class TileEntityCommandListener
/*    */   extends CommandBlockListenerAbstract {
/*    */   TileEntityCommandListener(TileEntityCommand tileentitycommand) {
/*  9 */     this.a = tileentitycommand;
/* 10 */     this.sender = (CommandSender)new CraftBlockCommandSender(this);
/*    */   }
/*    */   final TileEntityCommand a;
/*    */   public ChunkCoordinates getChunkCoordinates() {
/* 14 */     return new ChunkCoordinates(this.a.x, this.a.y, this.a.z);
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 18 */     return this.a.getWorld();
/*    */   }
/*    */   
/*    */   public void setCommand(String s) {
/* 22 */     super.setCommand(s);
/* 23 */     this.a.update();
/*    */   }
/*    */   
/*    */   public void e() {
/* 27 */     this.a.getWorld().notify(this.a.x, this.a.y, this.a.z);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityCommandListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */