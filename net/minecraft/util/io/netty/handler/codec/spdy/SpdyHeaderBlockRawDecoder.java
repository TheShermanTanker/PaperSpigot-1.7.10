/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
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
/*     */ 
/*     */ 
/*     */ public class SpdyHeaderBlockRawDecoder
/*     */   extends SpdyHeaderBlockDecoder
/*     */ {
/*     */   private final int version;
/*     */   private final int maxHeaderSize;
/*     */   private final int lengthFieldSize;
/*     */   private int headerSize;
/*     */   private int numHeaders;
/*     */   
/*     */   public SpdyHeaderBlockRawDecoder(int version, int maxHeaderSize) {
/*  33 */     if (version < 2 || version > 3) {
/*  34 */       throw new IllegalArgumentException("unsupported version: " + version);
/*     */     }
/*     */ 
/*     */     
/*  38 */     this.version = version;
/*  39 */     this.maxHeaderSize = maxHeaderSize;
/*  40 */     this.lengthFieldSize = (version < 3) ? 2 : 4;
/*  41 */     reset();
/*     */   }
/*     */   
/*     */   private int readLengthField(ByteBuf buffer) {
/*     */     int length;
/*  46 */     if (this.version < 3) {
/*  47 */       length = SpdyCodecUtil.getUnsignedShort(buffer, buffer.readerIndex());
/*  48 */       buffer.skipBytes(2);
/*     */     } else {
/*  50 */       length = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
/*  51 */       buffer.skipBytes(4);
/*     */     } 
/*  53 */     return length;
/*     */   }
/*     */ 
/*     */   
/*     */   void decode(ByteBuf encoded, SpdyHeadersFrame frame) throws Exception {
/*  58 */     if (encoded == null) {
/*  59 */       throw new NullPointerException("encoded");
/*     */     }
/*  61 */     if (frame == null) {
/*  62 */       throw new NullPointerException("frame");
/*     */     }
/*     */     
/*  65 */     if (this.numHeaders == -1) {
/*     */       
/*  67 */       if (encoded.readableBytes() < this.lengthFieldSize) {
/*     */         return;
/*     */       }
/*  70 */       this.numHeaders = readLengthField(encoded);
/*  71 */       if (this.numHeaders < 0) {
/*  72 */         frame.setInvalid();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  77 */     while (this.numHeaders > 0) {
/*  78 */       int headerSize = this.headerSize;
/*  79 */       encoded.markReaderIndex();
/*     */ 
/*     */       
/*  82 */       if (encoded.readableBytes() < this.lengthFieldSize) {
/*  83 */         encoded.resetReaderIndex();
/*     */         return;
/*     */       } 
/*  86 */       int nameLength = readLengthField(encoded);
/*     */ 
/*     */       
/*  89 */       if (nameLength <= 0) {
/*  90 */         frame.setInvalid();
/*     */         return;
/*     */       } 
/*  93 */       headerSize += nameLength;
/*  94 */       if (headerSize > this.maxHeaderSize) {
/*  95 */         frame.setTruncated();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 100 */       if (encoded.readableBytes() < nameLength) {
/* 101 */         encoded.resetReaderIndex();
/*     */         return;
/*     */       } 
/* 104 */       byte[] nameBytes = new byte[nameLength];
/* 105 */       encoded.readBytes(nameBytes);
/* 106 */       String name = new String(nameBytes, "UTF-8");
/*     */ 
/*     */       
/* 109 */       if (frame.headers().contains(name)) {
/* 110 */         frame.setInvalid();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 115 */       if (encoded.readableBytes() < this.lengthFieldSize) {
/* 116 */         encoded.resetReaderIndex();
/*     */         return;
/*     */       } 
/* 119 */       int valueLength = readLengthField(encoded);
/*     */ 
/*     */       
/* 122 */       if (valueLength < 0) {
/* 123 */         frame.setInvalid();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 128 */       if (valueLength == 0) {
/* 129 */         if (this.version < 3) {
/* 130 */           frame.setInvalid();
/*     */           return;
/*     */         } 
/* 133 */         frame.headers().add(name, "");
/* 134 */         this.numHeaders--;
/* 135 */         this.headerSize = headerSize;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 140 */       headerSize += valueLength;
/* 141 */       if (headerSize > this.maxHeaderSize) {
/* 142 */         frame.setTruncated();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 147 */       if (encoded.readableBytes() < valueLength) {
/* 148 */         encoded.resetReaderIndex();
/*     */         return;
/*     */       } 
/* 151 */       byte[] valueBytes = new byte[valueLength];
/* 152 */       encoded.readBytes(valueBytes);
/*     */ 
/*     */       
/* 155 */       int index = 0;
/* 156 */       int offset = 0;
/* 157 */       while (index < valueLength) {
/* 158 */         while (index < valueBytes.length && valueBytes[index] != 0) {
/* 159 */           index++;
/*     */         }
/* 161 */         if (index < valueBytes.length && valueBytes[index + 1] == 0) {
/*     */ 
/*     */           
/* 164 */           frame.setInvalid();
/*     */           return;
/*     */         } 
/* 167 */         String value = new String(valueBytes, offset, index - offset, "UTF-8");
/*     */         
/*     */         try {
/* 170 */           frame.headers().add(name, value);
/* 171 */         } catch (IllegalArgumentException e) {
/*     */           
/* 173 */           frame.setInvalid();
/*     */           
/*     */           return;
/*     */         } 
/* 177 */         offset = ++index;
/*     */       } 
/* 179 */       this.numHeaders--;
/* 180 */       this.headerSize = headerSize;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void reset() {
/* 187 */     this.headerSize = 0;
/* 188 */     this.numHeaders = -1;
/*     */   }
/*     */   
/*     */   void end() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyHeaderBlockRawDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */