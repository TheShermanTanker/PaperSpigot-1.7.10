/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerPing
/*    */ {
/*    */   private IChatBaseComponent a;
/*    */   private ServerPingPlayerSample b;
/*    */   private ServerPingServerData c;
/*    */   private String d;
/*    */   
/*    */   public IChatBaseComponent a() {
/* 21 */     return this.a;
/*    */   }
/*    */   
/*    */   public void setMOTD(IChatBaseComponent paramIChatBaseComponent) {
/* 25 */     this.a = paramIChatBaseComponent;
/*    */   }
/*    */   
/*    */   public ServerPingPlayerSample b() {
/* 29 */     return this.b;
/*    */   }
/*    */   
/*    */   public void setPlayerSample(ServerPingPlayerSample paramServerPingPlayerSample) {
/* 33 */     this.b = paramServerPingPlayerSample;
/*    */   }
/*    */   
/*    */   public ServerPingServerData c() {
/* 37 */     return this.c;
/*    */   }
/*    */   
/*    */   public void setServerInfo(ServerPingServerData paramServerPingServerData) {
/* 41 */     this.c = paramServerPingServerData;
/*    */   }
/*    */   
/*    */   public void setFavicon(String paramString) {
/* 45 */     this.d = paramString;
/*    */   }
/*    */   
/*    */   public String d() {
/* 49 */     return this.d;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerPing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */