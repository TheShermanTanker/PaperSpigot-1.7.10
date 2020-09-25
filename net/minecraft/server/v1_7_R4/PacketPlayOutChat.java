/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.chat.ComponentSerializer;
/*    */ 
/*    */ public class PacketPlayOutChat
/*    */   extends Packet {
/*    */   private IChatBaseComponent a;
/*    */   public BaseComponent[] components;
/*    */   
/*    */   public PacketPlayOutChat() {
/* 13 */     this.b = true;
/*    */   }
/*    */   private boolean b; private int pos;
/*    */   public PacketPlayOutChat(IChatBaseComponent ichatbasecomponent) {
/* 17 */     this(ichatbasecomponent, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public PacketPlayOutChat(IChatBaseComponent ichatbasecomponent, int pos) {
/* 22 */     this(ichatbasecomponent, pos, true);
/*    */   }
/*    */   
/*    */   public PacketPlayOutChat(IChatBaseComponent ichatbasecomponent, boolean flag) {
/* 26 */     this(ichatbasecomponent, 0, flag);
/*    */   }
/*    */   
/*    */   public PacketPlayOutChat(IChatBaseComponent ichatbasecomponent, int pos, boolean flag) {
/* 30 */     this.b = true;
/* 31 */     this.a = ichatbasecomponent;
/* 32 */     this.b = flag;
/* 33 */     this.pos = pos;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 38 */     this.a = ChatSerializer.a(packetdataserializer.c(32767));
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 43 */     if (this.components != null) {
/*    */       
/* 45 */       packetdataserializer.a(ComponentSerializer.toString(this.components));
/*    */     } else {
/*    */       
/* 48 */       packetdataserializer.a(ChatSerializer.a(this.a));
/*    */     } 
/*    */ 
/*    */     
/* 52 */     if (packetdataserializer.version >= 16)
/*    */     {
/* 54 */       packetdataserializer.writeByte(this.pos);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 60 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 64 */     return String.format("message='%s'", new Object[] { this.a });
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 68 */     return this.b;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 72 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutChat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */