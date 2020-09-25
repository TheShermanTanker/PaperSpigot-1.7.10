/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class WorldManager
/*    */   implements IWorldAccess {
/*    */   private MinecraftServer server;
/*    */   public WorldServer world;
/*    */   
/*    */   public WorldManager(MinecraftServer minecraftserver, WorldServer worldserver) {
/* 11 */     this.server = minecraftserver;
/* 12 */     this.world = worldserver;
/*    */   }
/*    */   
/*    */   public void a(String s, double d0, double d1, double d2, double d3, double d4, double d5) {}
/*    */   
/*    */   public void a(Entity entity) {
/* 18 */     this.world.getTracker().track(entity);
/*    */   }
/*    */   
/*    */   public void b(Entity entity) {
/* 22 */     this.world.getTracker().untrackEntity(entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(String s, double d0, double d1, double d2, float f, float f1) {
/* 27 */     this.server.getPlayerList().sendPacketNearby(d0, d1, d2, (f > 1.0F) ? (16.0F * f) : 16.0D, this.world.dimension, new PacketPlayOutNamedSoundEffect(s, d0, d1, d2, f, f1));
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(EntityHuman entityhuman, String s, double d0, double d1, double d2, float f, float f1) {
/* 32 */     this.server.getPlayerList().sendPacketNearby(entityhuman, d0, d1, d2, (f > 1.0F) ? (16.0F * f) : 16.0D, this.world.dimension, new PacketPlayOutNamedSoundEffect(s, d0, d1, d2, f, f1));
/*    */   }
/*    */   
/*    */   public void a(int i, int j, int k, int l, int i1, int j1) {}
/*    */   
/*    */   public void a(int i, int j, int k) {
/* 38 */     this.world.getPlayerChunkMap().flagDirty(i, j, k);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(int i, int j, int k) {}
/*    */   
/*    */   public void a(String s, int i, int j, int k) {}
/*    */   
/*    */   public void a(EntityHuman entityhuman, int i, int j, int k, int l, int i1) {
/* 47 */     this.server.getPlayerList().sendPacketNearby(entityhuman, j, k, l, 64.0D, this.world.dimension, new PacketPlayOutWorldEvent(i, j, k, l, i1, false));
/*    */   }
/*    */   
/*    */   public void a(int i, int j, int k, int l, int i1) {
/* 51 */     this.server.getPlayerList().sendAll(new PacketPlayOutWorldEvent(i, j, k, l, i1, true));
/*    */   }
/*    */   
/*    */   public void b(int i, int j, int k, int l, int i1) {
/* 55 */     Iterator<EntityPlayer> iterator = (this.server.getPlayerList()).players.iterator();
/*    */     
/* 57 */     while (iterator.hasNext()) {
/* 58 */       EntityPlayer entityplayer = iterator.next();
/*    */       
/* 60 */       if (entityplayer != null && entityplayer.world == this.world && entityplayer.getId() != i) {
/* 61 */         double d0 = j - entityplayer.locX;
/* 62 */         double d1 = k - entityplayer.locY;
/* 63 */         double d2 = l - entityplayer.locZ;
/*    */         
/* 65 */         if (d0 * d0 + d1 * d1 + d2 * d2 < 1024.0D)
/* 66 */           entityplayer.playerConnection.sendPacket(new PacketPlayOutBlockBreakAnimation(i, j, k, l, i1)); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b() {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */