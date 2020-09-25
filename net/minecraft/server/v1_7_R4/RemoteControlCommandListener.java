/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class RemoteControlCommandListener
/*    */   implements ICommandListener {
/*  5 */   public static final RemoteControlCommandListener instance = new RemoteControlCommandListener();
/*  6 */   private StringBuffer b = new StringBuffer();
/*    */ 
/*    */ 
/*    */   
/*    */   public void e() {
/* 11 */     this.b.setLength(0);
/*    */   }
/*    */   
/*    */   public String f() {
/* 15 */     return this.b.toString();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 19 */     return "Rcon";
/*    */   }
/*    */   
/*    */   public IChatBaseComponent getScoreboardDisplayName() {
/* 23 */     return new ChatComponentText(getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String message) {
/* 28 */     this.b.append(message);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(IChatBaseComponent ichatbasecomponent) {
/* 33 */     this.b.append(ichatbasecomponent.c());
/*    */   }
/*    */   
/*    */   public boolean a(int i, String s) {
/* 37 */     return true;
/*    */   }
/*    */   
/*    */   public ChunkCoordinates getChunkCoordinates() {
/* 41 */     return new ChunkCoordinates(0, 0, 0);
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 45 */     return MinecraftServer.getServer().getWorld();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RemoteControlCommandListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */