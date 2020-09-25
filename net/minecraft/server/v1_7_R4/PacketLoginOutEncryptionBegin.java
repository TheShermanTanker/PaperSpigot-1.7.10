/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.security.PublicKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketLoginOutEncryptionBegin
/*    */   extends Packet
/*    */ {
/*    */   private String a;
/*    */   private PublicKey b;
/*    */   private byte[] c;
/*    */   
/*    */   public PacketLoginOutEncryptionBegin() {}
/*    */   
/*    */   public PacketLoginOutEncryptionBegin(String paramString, PublicKey paramPublicKey, byte[] paramArrayOfbyte) {
/* 20 */     this.a = paramString;
/* 21 */     this.b = paramPublicKey;
/* 22 */     this.c = paramArrayOfbyte;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 27 */     this.a = paramPacketDataSerializer.c(20);
/* 28 */     this.b = MinecraftEncryption.a(a(paramPacketDataSerializer));
/* 29 */     this.c = a(paramPacketDataSerializer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 34 */     paramPacketDataSerializer.a(this.a);
/* 35 */     a(paramPacketDataSerializer, this.b.getEncoded());
/* 36 */     a(paramPacketDataSerializer, this.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketLoginOutListener paramPacketLoginOutListener) {
/* 41 */     paramPacketLoginOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketLoginOutEncryptionBegin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */