/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutKickDisconnect
/*    */   extends Packet
/*    */ {
/*    */   private IChatBaseComponent a;
/*    */   
/*    */   public PacketPlayOutKickDisconnect() {}
/*    */   
/*    */   public PacketPlayOutKickDisconnect(IChatBaseComponent paramIChatBaseComponent) {
/* 17 */     this.a = paramIChatBaseComponent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 22 */     this.a = ChatSerializer.a(paramPacketDataSerializer.c(32767));
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 27 */     paramPacketDataSerializer.a(ChatSerializer.a(this.a));
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 32 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutKickDisconnect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */