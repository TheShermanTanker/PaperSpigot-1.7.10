/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import javax.crypto.Cipher;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder;
/*    */ 
/*    */ public class PacketEncrypter
/*    */   extends MessageToByteEncoder {
/*    */   private final PacketEncryptionHandler a;
/*    */   
/*    */   public PacketEncrypter(Cipher paramCipher) {
/* 13 */     this.a = new PacketEncryptionHandler(paramCipher);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) {
/* 18 */     this.a.a(paramByteBuf1, paramByteBuf2);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketEncrypter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */