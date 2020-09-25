/*     */ package net.minecraft.util.io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.jcraft.jzlib.Inflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import java.util.List;
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
/*     */ public class JZlibDecoder
/*     */   extends ZlibDecoder
/*     */ {
/*  28 */   private final Inflater z = new Inflater();
/*     */ 
/*     */   
/*     */   private byte[] dictionary;
/*     */ 
/*     */   
/*     */   private volatile boolean finished;
/*     */ 
/*     */   
/*     */   public JZlibDecoder() {
/*  38 */     this(ZlibWrapper.ZLIB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JZlibDecoder(ZlibWrapper wrapper) {
/*  47 */     if (wrapper == null) {
/*  48 */       throw new NullPointerException("wrapper");
/*     */     }
/*     */     
/*  51 */     int resultCode = this.z.init(ZlibUtil.convertWrapperType(wrapper));
/*  52 */     if (resultCode != 0) {
/*  53 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JZlibDecoder(byte[] dictionary) {
/*  65 */     if (dictionary == null) {
/*  66 */       throw new NullPointerException("dictionary");
/*     */     }
/*  68 */     this.dictionary = dictionary;
/*     */ 
/*     */     
/*  71 */     int resultCode = this.z.inflateInit(JZlib.W_ZLIB);
/*  72 */     if (resultCode != 0) {
/*  73 */       ZlibUtil.fail(this.z, "initialization failure", resultCode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/*  83 */     return this.finished;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  89 */     if (!in.isReadable()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  95 */       int inputLength = in.readableBytes();
/*  96 */       this.z.avail_in = inputLength;
/*  97 */       if (in.hasArray()) {
/*  98 */         this.z.next_in = in.array();
/*  99 */         this.z.next_in_index = in.arrayOffset() + in.readerIndex();
/*     */       } else {
/* 101 */         byte[] array = new byte[inputLength];
/* 102 */         in.getBytes(in.readerIndex(), array);
/* 103 */         this.z.next_in = array;
/* 104 */         this.z.next_in_index = 0;
/*     */       } 
/* 106 */       int oldNextInIndex = this.z.next_in_index;
/*     */ 
/*     */       
/* 109 */       int maxOutputLength = inputLength << 1;
/* 110 */       ByteBuf decompressed = ctx.alloc().heapBuffer(maxOutputLength);
/*     */       
/*     */       try {
/*     */         while (true) {
/* 114 */           this.z.avail_out = maxOutputLength;
/* 115 */           decompressed.ensureWritable(maxOutputLength);
/* 116 */           this.z.next_out = decompressed.array();
/* 117 */           this.z.next_out_index = decompressed.arrayOffset() + decompressed.writerIndex();
/* 118 */           int oldNextOutIndex = this.z.next_out_index;
/*     */ 
/*     */           
/* 121 */           int resultCode = this.z.inflate(2);
/* 122 */           int outputLength = this.z.next_out_index - oldNextOutIndex;
/* 123 */           if (outputLength > 0) {
/* 124 */             decompressed.writerIndex(decompressed.writerIndex() + outputLength);
/*     */           }
/*     */           
/* 127 */           switch (resultCode) {
/*     */             case 2:
/* 129 */               if (this.dictionary == null) {
/* 130 */                 ZlibUtil.fail(this.z, "decompression failure", resultCode); continue;
/*     */               } 
/* 132 */               resultCode = this.z.inflateSetDictionary(this.dictionary, this.dictionary.length);
/* 133 */               if (resultCode != 0) {
/* 134 */                 ZlibUtil.fail(this.z, "failed to set the dictionary", resultCode);
/*     */               }
/*     */               continue;
/*     */             
/*     */             case 1:
/* 139 */               this.finished = true;
/* 140 */               this.z.inflateEnd();
/*     */               break;
/*     */             case 0:
/*     */               continue;
/*     */             case -5:
/* 145 */               if (this.z.avail_in <= 0) {
/*     */                 break;
/*     */               }
/*     */               continue;
/*     */           } 
/* 150 */           ZlibUtil.fail(this.z, "decompression failure", resultCode);
/*     */         } 
/*     */       } finally {
/*     */         
/* 154 */         in.skipBytes(this.z.next_in_index - oldNextInIndex);
/* 155 */         if (decompressed.isReadable()) {
/* 156 */           out.add(decompressed);
/*     */         } else {
/* 158 */           decompressed.release();
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 166 */       this.z.next_in = null;
/* 167 */       this.z.next_out = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\compression\JZlibDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */