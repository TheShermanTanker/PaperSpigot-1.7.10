/*    */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*    */ 
/*    */ import net.minecraft.util.io.netty.channel.ChannelInboundHandler;
/*    */ import net.minecraft.util.io.netty.channel.ChannelOutboundHandler;
/*    */ import net.minecraft.util.io.netty.channel.CombinedChannelDuplexHandler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SpdyFrameCodec
/*    */   extends CombinedChannelDuplexHandler<SpdyFrameDecoder, SpdyFrameEncoder>
/*    */ {
/*    */   public SpdyFrameCodec(int version) {
/* 32 */     this(version, 8192, 16384, 6, 15, 8);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpdyFrameCodec(int version, int maxChunkSize, int maxHeaderSize, int compressionLevel, int windowBits, int memLevel) {
/* 41 */     super((ChannelInboundHandler)new SpdyFrameDecoder(version, maxChunkSize, maxHeaderSize), (ChannelOutboundHandler)new SpdyFrameEncoder(version, compressionLevel, windowBits, memLevel));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyFrameCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */