/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import com.jcraft.jzlib.Deflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.compression.CompressionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SpdyHeaderBlockJZlibEncoder
/*     */   extends SpdyHeaderBlockRawEncoder
/*     */ {
/*  29 */   private final Deflater z = new Deflater();
/*     */   
/*     */   private boolean finished;
/*     */ 
/*     */   
/*     */   public SpdyHeaderBlockJZlibEncoder(int version, int compressionLevel, int windowBits, int memLevel) {
/*  35 */     super(version);
/*  36 */     if (compressionLevel < 0 || compressionLevel > 9) {
/*  37 */       throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
/*     */     }
/*     */     
/*  40 */     if (windowBits < 9 || windowBits > 15) {
/*  41 */       throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
/*     */     }
/*     */     
/*  44 */     if (memLevel < 1 || memLevel > 9) {
/*  45 */       throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
/*     */     }
/*     */ 
/*     */     
/*  49 */     int resultCode = this.z.deflateInit(compressionLevel, windowBits, memLevel, JZlib.W_ZLIB);
/*     */     
/*  51 */     if (resultCode != 0) {
/*  52 */       throw new CompressionException("failed to initialize an SPDY header block deflater: " + resultCode);
/*     */     }
/*     */     
/*  55 */     if (version < 3) {
/*  56 */       resultCode = this.z.deflateSetDictionary(SpdyCodecUtil.SPDY2_DICT, SpdyCodecUtil.SPDY2_DICT.length);
/*     */     } else {
/*  58 */       resultCode = this.z.deflateSetDictionary(SpdyCodecUtil.SPDY_DICT, SpdyCodecUtil.SPDY_DICT.length);
/*     */     } 
/*  60 */     if (resultCode != 0) {
/*  61 */       throw new CompressionException("failed to set the SPDY dictionary: " + resultCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setInput(ByteBuf decompressed) {
/*  68 */     byte[] in = new byte[decompressed.readableBytes()];
/*  69 */     decompressed.readBytes(in);
/*  70 */     this.z.next_in = in;
/*  71 */     this.z.next_in_index = 0;
/*  72 */     this.z.avail_in = in.length;
/*     */   }
/*     */   
/*     */   private void encode(ByteBuf compressed) {
/*     */     try {
/*  77 */       byte[] out = new byte[(int)Math.ceil(this.z.next_in.length * 1.001D) + 12];
/*  78 */       this.z.next_out = out;
/*  79 */       this.z.next_out_index = 0;
/*  80 */       this.z.avail_out = out.length;
/*     */       
/*  82 */       int resultCode = this.z.deflate(2);
/*  83 */       if (resultCode != 0) {
/*  84 */         throw new CompressionException("compression failure: " + resultCode);
/*     */       }
/*     */       
/*  87 */       if (this.z.next_out_index != 0) {
/*  88 */         compressed.writeBytes(out, 0, this.z.next_out_index);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/*  95 */       this.z.next_in = null;
/*  96 */       this.z.next_out = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf encode(ChannelHandlerContext ctx, SpdyHeadersFrame frame) throws Exception {
/* 102 */     if (frame == null) {
/* 103 */       throw new IllegalArgumentException("frame");
/*     */     }
/*     */     
/* 106 */     if (this.finished) {
/* 107 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 110 */     ByteBuf decompressed = super.encode(ctx, frame);
/* 111 */     if (decompressed.readableBytes() == 0) {
/* 112 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 115 */     ByteBuf compressed = ctx.alloc().buffer();
/* 116 */     setInput(decompressed);
/* 117 */     encode(compressed);
/* 118 */     return compressed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/* 123 */     if (this.finished) {
/*     */       return;
/*     */     }
/* 126 */     this.finished = true;
/* 127 */     this.z.deflateEnd();
/* 128 */     this.z.next_in = null;
/* 129 */     this.z.next_out = null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyHeaderBlockJZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */