/*    */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*    */ 
/*    */ import java.util.zip.DataFormatException;
/*    */ import java.util.zip.Inflater;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.Unpooled;
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
/*    */ class SpdyHeaderBlockZlibDecoder
/*    */   extends SpdyHeaderBlockRawDecoder
/*    */ {
/*    */   private final int version;
/* 29 */   private final byte[] out = new byte[8192];
/* 30 */   private final Inflater decompressor = new Inflater();
/*    */   
/*    */   private ByteBuf decompressed;
/*    */   
/*    */   public SpdyHeaderBlockZlibDecoder(int version, int maxHeaderSize) {
/* 35 */     super(version, maxHeaderSize);
/* 36 */     this.version = version;
/*    */   }
/*    */   
/*    */   void decode(ByteBuf encoded, SpdyHeadersFrame frame) throws Exception {
/*    */     int numBytes;
/* 41 */     setInput(encoded);
/*    */ 
/*    */     
/*    */     do {
/* 45 */       numBytes = decompress(frame);
/* 46 */     } while (!this.decompressed.isReadable() && numBytes > 0);
/*    */   }
/*    */   
/*    */   private void setInput(ByteBuf compressed) {
/* 50 */     byte[] in = new byte[compressed.readableBytes()];
/* 51 */     compressed.readBytes(in);
/* 52 */     this.decompressor.setInput(in);
/*    */   }
/*    */   
/*    */   private int decompress(SpdyHeadersFrame frame) throws Exception {
/* 56 */     if (this.decompressed == null) {
/* 57 */       this.decompressed = Unpooled.buffer(8192);
/*    */     }
/*    */     try {
/* 60 */       int numBytes = this.decompressor.inflate(this.out);
/* 61 */       if (numBytes == 0 && this.decompressor.needsDictionary()) {
/* 62 */         if (this.version < 3) {
/* 63 */           this.decompressor.setDictionary(SpdyCodecUtil.SPDY2_DICT);
/*    */         } else {
/* 65 */           this.decompressor.setDictionary(SpdyCodecUtil.SPDY_DICT);
/*    */         } 
/* 67 */         numBytes = this.decompressor.inflate(this.out);
/*    */       } 
/* 69 */       if (frame != null) {
/* 70 */         this.decompressed.writeBytes(this.out, 0, numBytes);
/* 71 */         super.decode(this.decompressed, frame);
/* 72 */         this.decompressed.discardReadBytes();
/*    */       } 
/* 74 */       return numBytes;
/* 75 */     } catch (DataFormatException e) {
/* 76 */       throw new SpdyProtocolException("Received invalid header block", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 83 */     this.decompressed = null;
/* 84 */     super.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void end() {
/* 89 */     this.decompressed = null;
/* 90 */     this.decompressor.end();
/* 91 */     super.end();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyHeaderBlockZlibDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */