/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.crypto.Cipher;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageDecoder;
/*    */ 
/*    */ public class PacketDecrypter
/*    */   extends MessageToMessageDecoder {
/*    */   private final PacketEncryptionHandler a;
/*    */   
/*    */   public PacketDecrypter(Cipher paramCipher) {
/* 14 */     this.a = new PacketEncryptionHandler(paramCipher);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<ByteBuf> paramList) {
/* 19 */     paramList.add(this.a.a(paramChannelHandlerContext, paramByteBuf));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketDecrypter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */