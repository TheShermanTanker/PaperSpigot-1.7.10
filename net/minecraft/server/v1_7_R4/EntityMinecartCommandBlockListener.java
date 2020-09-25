/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class EntityMinecartCommandBlockListener extends CommandBlockListenerAbstract {
/*    */   final EntityMinecartCommandBlock a;
/*    */   
/*    */   EntityMinecartCommandBlockListener(EntityMinecartCommandBlock entityminecartcommandblock) {
/*  9 */     this.a = entityminecartcommandblock;
/* 10 */     this.sender = (CommandSender)entityminecartcommandblock.getBukkitEntity();
/*    */   }
/*    */   
/*    */   public void e() {
/* 14 */     this.a.getDataWatcher().watch(23, getCommand());
/* 15 */     this.a.getDataWatcher().watch(24, ChatSerializer.a(h()));
/*    */   }
/*    */   
/*    */   public ChunkCoordinates getChunkCoordinates() {
/* 19 */     return new ChunkCoordinates(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY + 0.5D), MathHelper.floor(this.a.locZ));
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 23 */     return this.a.world;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMinecartCommandBlockListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */