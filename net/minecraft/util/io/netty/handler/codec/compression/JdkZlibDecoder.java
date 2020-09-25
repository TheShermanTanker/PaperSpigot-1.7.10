/*     */ package net.minecraft.util.io.netty.handler.codec.compression;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Inflater;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JdkZlibDecoder
/*     */   extends ZlibDecoder
/*     */ {
/*     */   private static final int FHCRC = 2;
/*     */   private static final int FEXTRA = 4;
/*     */   private static final int FNAME = 8;
/*     */   private static final int FCOMMENT = 16;
/*     */   private static final int FRESERVED = 224;
/*     */   private final Inflater inflater;
/*     */   private final byte[] dictionary;
/*     */   private final CRC32 crc;
/*     */   
/*     */   private enum GzipState
/*     */   {
/*  46 */     HEADER_START,
/*  47 */     HEADER_END,
/*  48 */     FLG_READ,
/*  49 */     XLEN_READ,
/*  50 */     SKIP_FNAME,
/*  51 */     SKIP_COMMENT,
/*  52 */     PROCESS_FHCRC,
/*  53 */     FOOTER_START;
/*     */   }
/*     */   
/*  56 */   private GzipState gzipState = GzipState.HEADER_START;
/*  57 */   private int flags = -1;
/*  58 */   private int xlen = -1;
/*     */ 
/*     */   
/*     */   private volatile boolean finished;
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibDecoder() {
/*  66 */     this(ZlibWrapper.ZLIB, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibDecoder(byte[] dictionary) {
/*  75 */     this(ZlibWrapper.ZLIB, dictionary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkZlibDecoder(ZlibWrapper wrapper) {
/*  84 */     this(wrapper, null);
/*     */   }
/*     */   
/*     */   private JdkZlibDecoder(ZlibWrapper wrapper, byte[] dictionary) {
/*  88 */     if (wrapper == null) {
/*  89 */       throw new NullPointerException("wrapper");
/*     */     }
/*  91 */     switch (wrapper) {
/*     */       case FOOTER_START:
/*  93 */         this.inflater = new Inflater(true);
/*  94 */         this.crc = new CRC32();
/*     */         break;
/*     */       case HEADER_START:
/*  97 */         this.inflater = new Inflater(true);
/*  98 */         this.crc = null;
/*     */         break;
/*     */       case FLG_READ:
/* 101 */         this.inflater = new Inflater();
/* 102 */         this.crc = null;
/*     */         break;
/*     */       default:
/* 105 */         throw new IllegalArgumentException("Only GZIP or ZLIB is supported, but you used " + wrapper);
/*     */     } 
/* 107 */     this.dictionary = dictionary;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 112 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 117 */     if (!in.isReadable() && this.finished) {
/*     */       return;
/*     */     }
/*     */     
/* 121 */     if (this.crc != null) {
/* 122 */       switch (this.gzipState) {
/*     */         case FOOTER_START:
/* 124 */           if (readGZIPFooter(in)) {
/* 125 */             this.finished = true;
/*     */           }
/*     */           return;
/*     */       } 
/* 129 */       if (this.gzipState != GzipState.HEADER_END && 
/* 130 */         !readGZIPHeader(in)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 137 */     int readableBytes = in.readableBytes();
/* 138 */     if (in.hasArray()) {
/* 139 */       this.inflater.setInput(in.array(), in.arrayOffset() + in.readerIndex(), in.readableBytes());
/*     */     } else {
/* 141 */       byte[] array = new byte[in.readableBytes()];
/* 142 */       in.getBytes(in.readerIndex(), array);
/* 143 */       this.inflater.setInput(array);
/*     */     } 
/*     */     
/* 146 */     int maxOutputLength = this.inflater.getRemaining() << 1;
/* 147 */     ByteBuf decompressed = ctx.alloc().heapBuffer(maxOutputLength);
/*     */     try {
/* 149 */       boolean readFooter = false;
/* 150 */       while (!this.inflater.needsInput()) {
/* 151 */         byte[] outArray = decompressed.array();
/* 152 */         int outIndex = decompressed.arrayOffset() + decompressed.writerIndex();
/* 153 */         int length = outArray.length - outIndex;
/*     */         
/* 155 */         int outputLength = this.inflater.inflate(outArray, outIndex, length);
/*     */         
/* 157 */         if (outputLength > 0) {
/* 158 */           decompressed.writerIndex(decompressed.writerIndex() + outputLength);
/* 159 */           if (this.crc != null) {
/* 160 */             this.crc.update(outArray, outIndex, outputLength);
/*     */           }
/*     */         }
/* 163 */         else if (this.inflater.needsDictionary()) {
/* 164 */           if (this.dictionary == null) {
/* 165 */             throw new DecompressionException("decompression failure, unable to set dictionary as non was specified");
/*     */           }
/*     */           
/* 168 */           this.inflater.setDictionary(this.dictionary);
/*     */         } 
/*     */ 
/*     */         
/* 172 */         if (this.inflater.finished()) {
/* 173 */           if (this.crc == null) {
/* 174 */             this.finished = true; break;
/*     */           } 
/* 176 */           readFooter = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 182 */       in.skipBytes(readableBytes - this.inflater.getRemaining());
/*     */       
/* 184 */       if (readFooter) {
/* 185 */         this.gzipState = GzipState.FOOTER_START;
/* 186 */         if (readGZIPFooter(in)) {
/* 187 */           this.finished = true;
/*     */         }
/*     */       } 
/* 190 */     } catch (DataFormatException e) {
/* 191 */       throw new DecompressionException("decompression failure", e);
/*     */     } finally {
/*     */       
/* 194 */       if (decompressed.isReadable()) {
/* 195 */         out.add(decompressed);
/*     */       } else {
/* 197 */         decompressed.release();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/* 204 */     super.handlerRemoved0(ctx);
/* 205 */     this.inflater.end(); } private boolean readGZIPHeader(ByteBuf in) {
/*     */     int magic0;
/*     */     int magic1;
/*     */     int method;
/* 209 */     switch (this.gzipState) {
/*     */       case HEADER_START:
/* 211 */         if (in.readableBytes() < 10) {
/* 212 */           return false;
/*     */         }
/*     */         
/* 215 */         magic0 = in.readByte();
/* 216 */         magic1 = in.readByte();
/*     */         
/* 218 */         if (magic0 != 31) {
/* 219 */           throw new CompressionException("Input is not in the GZIP format");
/*     */         }
/* 221 */         this.crc.update(magic0);
/* 222 */         this.crc.update(magic1);
/*     */         
/* 224 */         method = in.readUnsignedByte();
/* 225 */         if (method != 8) {
/* 226 */           throw new CompressionException("Unsupported compression method " + method + " in the GZIP header");
/*     */         }
/*     */         
/* 229 */         this.crc.update(method);
/*     */         
/* 231 */         this.flags = in.readUnsignedByte();
/* 232 */         this.crc.update(this.flags);
/*     */         
/* 234 */         if ((this.flags & 0xE0) != 0) {
/* 235 */           throw new CompressionException("Reserved flags are set in the GZIP header");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 240 */         this.crc.update(in.readByte());
/* 241 */         this.crc.update(in.readByte());
/* 242 */         this.crc.update(in.readByte());
/* 243 */         this.crc.update(in.readByte());
/*     */         
/* 245 */         this.crc.update(in.readUnsignedByte());
/* 246 */         this.crc.update(in.readUnsignedByte());
/*     */         
/* 248 */         this.gzipState = GzipState.FLG_READ;
/*     */       case FLG_READ:
/* 250 */         if ((this.flags & 0x4) != 0) {
/* 251 */           if (in.readableBytes() < 2) {
/* 252 */             return false;
/*     */           }
/* 254 */           int xlen1 = in.readUnsignedByte();
/* 255 */           int xlen2 = in.readUnsignedByte();
/* 256 */           this.crc.update(xlen1);
/* 257 */           this.crc.update(xlen2);
/*     */           
/* 259 */           this.xlen |= xlen1 << 8 | xlen2;
/*     */         } 
/* 261 */         this.gzipState = GzipState.XLEN_READ;
/*     */       case XLEN_READ:
/* 263 */         if (this.xlen != -1) {
/* 264 */           if (in.readableBytes() < this.xlen) {
/* 265 */             return false;
/*     */           }
/* 267 */           byte[] xtra = new byte[this.xlen];
/* 268 */           in.readBytes(xtra);
/* 269 */           this.crc.update(xtra);
/*     */         } 
/* 271 */         this.gzipState = GzipState.SKIP_FNAME;
/*     */       case SKIP_FNAME:
/* 273 */         if ((this.flags & 0x8) != 0) {
/* 274 */           if (!in.isReadable()) {
/* 275 */             return false;
/*     */           }
/*     */           do {
/* 278 */             int b = in.readUnsignedByte();
/* 279 */             this.crc.update(b);
/* 280 */             if (b == 0) {
/*     */               break;
/*     */             }
/* 283 */           } while (in.isReadable());
/*     */         } 
/* 285 */         this.gzipState = GzipState.SKIP_COMMENT;
/*     */       case SKIP_COMMENT:
/* 287 */         if ((this.flags & 0x10) != 0) {
/* 288 */           if (!in.isReadable()) {
/* 289 */             return false;
/*     */           }
/*     */           do {
/* 292 */             int b = in.readUnsignedByte();
/* 293 */             this.crc.update(b);
/* 294 */             if (b == 0) {
/*     */               break;
/*     */             }
/* 297 */           } while (in.isReadable());
/*     */         } 
/* 299 */         this.gzipState = GzipState.PROCESS_FHCRC;
/*     */       case PROCESS_FHCRC:
/* 301 */         if ((this.flags & 0x2) != 0) {
/* 302 */           if (in.readableBytes() < 4) {
/* 303 */             return false;
/*     */           }
/* 305 */           verifyCrc(in);
/*     */         } 
/* 307 */         this.crc.reset();
/* 308 */         this.gzipState = GzipState.HEADER_END;
/*     */       case HEADER_END:
/* 310 */         return true;
/*     */     } 
/* 312 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean readGZIPFooter(ByteBuf buf) {
/* 317 */     if (buf.readableBytes() < 8) {
/* 318 */       return false;
/*     */     }
/*     */     
/* 321 */     verifyCrc(buf);
/*     */ 
/*     */     
/* 324 */     int dataLength = 0;
/* 325 */     for (int i = 0; i < 4; i++) {
/* 326 */       dataLength |= buf.readUnsignedByte() << i * 8;
/*     */     }
/* 328 */     int readLength = this.inflater.getTotalOut();
/* 329 */     if (dataLength != readLength) {
/* 330 */       throw new CompressionException("Number of bytes missmatch. Expected: " + dataLength + ", Got: " + readLength);
/*     */     }
/*     */     
/* 333 */     return true;
/*     */   }
/*     */   
/*     */   private void verifyCrc(ByteBuf in) {
/* 337 */     long crcValue = 0L;
/* 338 */     for (int i = 0; i < 4; i++) {
/* 339 */       crcValue |= in.readUnsignedByte() << i * 8;
/*     */     }
/* 341 */     long readCrc = this.crc.getValue();
/* 342 */     if (crcValue != readCrc)
/* 343 */       throw new CompressionException("CRC value missmatch. Expected: " + crcValue + ", Got: " + readCrc); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\compression\JdkZlibDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */