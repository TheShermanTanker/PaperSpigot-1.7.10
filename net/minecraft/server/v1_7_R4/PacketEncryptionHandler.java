/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import javax.crypto.Cipher;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ 
/*    */ 
/*    */ public class PacketEncryptionHandler
/*    */ {
/*    */   private final Cipher a;
/* 11 */   private byte[] b = new byte[0];
/* 12 */   private byte[] c = new byte[0];
/*    */   
/*    */   protected PacketEncryptionHandler(Cipher paramCipher) {
/* 15 */     this.a = paramCipher;
/*    */   }
/*    */   
/*    */   private byte[] a(ByteBuf paramByteBuf) {
/* 19 */     int i = paramByteBuf.readableBytes();
/* 20 */     if (this.b.length < i) {
/* 21 */       this.b = new byte[i];
/*    */     }
/* 23 */     paramByteBuf.readBytes(this.b, 0, i);
/* 24 */     return this.b;
/*    */   }
/*    */   
/*    */   protected ByteBuf a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) {
/* 28 */     int i = paramByteBuf.readableBytes();
/* 29 */     byte[] arrayOfByte = a(paramByteBuf);
/*    */     
/* 31 */     ByteBuf byteBuf = paramChannelHandlerContext.alloc().heapBuffer(this.a.getOutputSize(i));
/* 32 */     byteBuf.writerIndex(this.a.update(arrayOfByte, 0, i, byteBuf.array(), byteBuf.arrayOffset()));
/*    */     
/* 34 */     return byteBuf;
/*    */   }
/*    */   
/*    */   protected void a(ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) {
/* 38 */     int i = paramByteBuf1.readableBytes();
/* 39 */     byte[] arrayOfByte = a(paramByteBuf1);
/*    */     
/* 41 */     int j = this.a.getOutputSize(i);
/* 42 */     if (this.c.length < j) {
/* 43 */       this.c = new byte[j];
/*    */     }
/* 45 */     paramByteBuf2.writeBytes(this.c, 0, this.a.update(arrayOfByte, 0, i, this.c));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketEncryptionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */