/*    */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*    */ 
/*    */ import java.util.zip.Deflater;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
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
/*    */ class SpdyHeaderBlockZlibEncoder
/*    */   extends SpdyHeaderBlockRawEncoder
/*    */ {
/* 28 */   private final byte[] out = new byte[8192];
/*    */   
/*    */   private final Deflater compressor;
/*    */   private boolean finished;
/*    */   
/*    */   public SpdyHeaderBlockZlibEncoder(int version, int compressionLevel) {
/* 34 */     super(version);
/* 35 */     if (compressionLevel < 0 || compressionLevel > 9) {
/* 36 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*    */     }
/*    */     
/* 39 */     this.compressor = new Deflater(compressionLevel);
/* 40 */     if (version < 3) {
/* 41 */       this.compressor.setDictionary(SpdyCodecUtil.SPDY2_DICT);
/*    */     } else {
/* 43 */       this.compressor.setDictionary(SpdyCodecUtil.SPDY_DICT);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void setInput(ByteBuf decompressed) {
/* 48 */     byte[] in = new byte[decompressed.readableBytes()];
/* 49 */     decompressed.readBytes(in);
/* 50 */     this.compressor.setInput(in);
/*    */   }
/*    */   
/*    */   private void encode(ByteBuf compressed) {
/* 54 */     int numBytes = this.out.length;
/* 55 */     while (numBytes == this.out.length) {
/* 56 */       numBytes = this.compressor.deflate(this.out, 0, this.out.length, 2);
/* 57 */       compressed.writeBytes(this.out, 0, numBytes);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf encode(ChannelHandlerContext ctx, SpdyHeadersFrame frame) throws Exception {
/* 63 */     if (frame == null) {
/* 64 */       throw new IllegalArgumentException("frame");
/*    */     }
/*    */     
/* 67 */     if (this.finished) {
/* 68 */       return Unpooled.EMPTY_BUFFER;
/*    */     }
/*    */     
/* 71 */     ByteBuf decompressed = super.encode(ctx, frame);
/* 72 */     if (decompressed.readableBytes() == 0) {
/* 73 */       return Unpooled.EMPTY_BUFFER;
/*    */     }
/*    */     
/* 76 */     ByteBuf compressed = ctx.alloc().buffer();
/* 77 */     setInput(decompressed);
/* 78 */     encode(compressed);
/* 79 */     return compressed;
/*    */   }
/*    */ 
/*    */   
/*    */   public void end() {
/* 84 */     if (this.finished) {
/*    */       return;
/*    */     }
/* 87 */     this.finished = true;
/* 88 */     this.compressor.end();
/* 89 */     super.end();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyHeaderBlockZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */