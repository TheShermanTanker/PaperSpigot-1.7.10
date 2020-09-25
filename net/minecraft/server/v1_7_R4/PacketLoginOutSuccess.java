/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class PacketLoginOutSuccess
/*    */   extends Packet
/*    */ {
/*    */   private GameProfile a;
/*    */   
/*    */   public PacketLoginOutSuccess() {}
/*    */   
/*    */   public PacketLoginOutSuccess(GameProfile gameprofile) {
/* 15 */     this.a = gameprofile;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 19 */     String s = packetdataserializer.c(36);
/* 20 */     String s1 = packetdataserializer.c(16);
/* 21 */     UUID uuid = UUID.fromString(s);
/*    */     
/* 23 */     this.a = new GameProfile(uuid, s1);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 27 */     UUID uuid = this.a.getId();
/*    */     
/* 29 */     packetdataserializer.a((uuid == null) ? "" : ((packetdataserializer.version >= 5) ? uuid.toString() : uuid.toString().replaceAll("-", "")));
/* 30 */     packetdataserializer.a(this.a.getName());
/*    */   }
/*    */   
/*    */   public void a(PacketLoginOutListener packetloginoutlistener) {
/* 34 */     packetloginoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 38 */     return true;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 42 */     a((PacketLoginOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketLoginOutSuccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */