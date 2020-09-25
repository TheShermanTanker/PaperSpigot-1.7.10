/*    */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class SpdyHeaderBlockEncoder
/*    */ {
/*    */   static SpdyHeaderBlockEncoder newInstance(int version, int compressionLevel, int windowBits, int memLevel) {
/* 27 */     if (PlatformDependent.javaVersion() >= 7) {
/* 28 */       return new SpdyHeaderBlockZlibEncoder(version, compressionLevel);
/*    */     }
/*    */     
/* 31 */     return new SpdyHeaderBlockJZlibEncoder(version, compressionLevel, windowBits, memLevel);
/*    */   }
/*    */   
/*    */   abstract ByteBuf encode(ChannelHandlerContext paramChannelHandlerContext, SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception;
/*    */   
/*    */   abstract void end();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyHeaderBlockEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */