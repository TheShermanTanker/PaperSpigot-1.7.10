/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.security.PrivateKey;
/*    */ import javax.crypto.SecretKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketLoginInEncryptionBegin
/*    */   extends Packet
/*    */ {
/* 13 */   private byte[] a = new byte[0];
/* 14 */   private byte[] b = new byte[0];
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
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 27 */     this.a = a(paramPacketDataSerializer);
/* 28 */     this.b = a(paramPacketDataSerializer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 33 */     a(paramPacketDataSerializer, this.a);
/* 34 */     a(paramPacketDataSerializer, this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketLoginInListener paramPacketLoginInListener) {
/* 39 */     paramPacketLoginInListener.a(this);
/*    */   }
/*    */   
/*    */   public SecretKey a(PrivateKey paramPrivateKey) {
/* 43 */     return MinecraftEncryption.a(paramPrivateKey, this.a);
/*    */   }
/*    */   
/*    */   public byte[] b(PrivateKey paramPrivateKey) {
/* 47 */     if (paramPrivateKey == null) {
/* 48 */       return this.b;
/*    */     }
/* 50 */     return MinecraftEncryption.b(paramPrivateKey, this.b);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketLoginInEncryptionBegin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */