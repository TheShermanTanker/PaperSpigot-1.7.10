/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketLoginOutDisconnect
/*    */   extends Packet
/*    */ {
/*    */   private IChatBaseComponent a;
/*    */   
/*    */   public PacketLoginOutDisconnect() {}
/*    */   
/*    */   public PacketLoginOutDisconnect(IChatBaseComponent paramIChatBaseComponent) {
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
/*    */   public void a(PacketLoginOutListener paramPacketLoginOutListener) {
/* 32 */     paramPacketLoginOutListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketLoginOutDisconnect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */