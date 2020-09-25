/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.util.org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class PacketPlayInTabComplete
/*    */   extends Packet
/*    */ {
/*    */   private String a;
/*    */   
/*    */   public PacketPlayInTabComplete() {}
/*    */   
/*    */   public PacketPlayInTabComplete(String s) {
/* 14 */     this.a = s;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 18 */     this.a = packetdataserializer.c(32767);
/*    */     
/* 20 */     if (packetdataserializer.version >= 37)
/*    */     {
/* 22 */       if (packetdataserializer.readBoolean()) {
/* 23 */         long position = packetdataserializer.readLong();
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 30 */     packetdataserializer.a(StringUtils.substring(this.a, 0, 32767));
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 34 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public String c() {
/* 38 */     return this.a;
/*    */   }
/*    */   
/*    */   public String b() {
/* 42 */     return String.format("message='%s'", new Object[] { this.a });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 46 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInTabComplete.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */