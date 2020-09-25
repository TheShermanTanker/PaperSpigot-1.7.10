/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.util.com.google.gson.Gson;
/*    */ import net.minecraft.util.com.google.gson.GsonBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketStatusOutServerInfo
/*    */   extends Packet
/*    */ {
/* 13 */   private static final Gson a = (new GsonBuilder()).registerTypeAdapter(ServerPingServerData.class, new ServerPingServerDataSerializer()).registerTypeAdapter(ServerPingPlayerSample.class, new ServerPingPlayerSampleSerializer()).registerTypeAdapter(ServerPing.class, new ServerPingSerializer()).registerTypeHierarchyAdapter(IChatBaseComponent.class, new ChatSerializer()).registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifierSerializer()).registerTypeAdapterFactory(new ChatTypeAdapterFactory()).create();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ServerPing b;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PacketStatusOutServerInfo() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PacketStatusOutServerInfo(ServerPing paramServerPing) {
/* 29 */     this.b = paramServerPing;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 34 */     this.b = (ServerPing)a.fromJson(paramPacketDataSerializer.c(32767), ServerPing.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 39 */     paramPacketDataSerializer.a(a.toJson(this.b));
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketStatusOutListener paramPacketStatusOutListener) {
/* 44 */     paramPacketStatusOutListener.a(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketStatusOutServerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */