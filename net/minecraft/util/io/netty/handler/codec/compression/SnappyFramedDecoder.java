/*     */ package net.minecraft.util.io.netty.handler.codec.compression;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufUtil;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.ByteToMessageDecoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnappyFramedDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   enum ChunkType
/*     */   {
/*  40 */     STREAM_IDENTIFIER,
/*  41 */     COMPRESSED_DATA,
/*  42 */     UNCOMPRESSED_DATA,
/*  43 */     RESERVED_UNSKIPPABLE,
/*  44 */     RESERVED_SKIPPABLE;
/*     */   }
/*     */   
/*  47 */   private static final byte[] SNAPPY = new byte[] { 115, 78, 97, 80, 112, 89 };
/*     */   
/*  49 */   private final Snappy snappy = new Snappy();
/*     */ 
/*     */   
/*     */   private final boolean validateChecksums;
/*     */ 
/*     */   
/*     */   private boolean started;
/*     */   
/*     */   private boolean corrupted;
/*     */ 
/*     */   
/*     */   public SnappyFramedDecoder() {
/*  61 */     this(false);
/*     */   }
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
/*     */   public SnappyFramedDecoder(boolean validateChecksums) {
/*  74 */     this.validateChecksums = validateChecksums;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  79 */     if (this.corrupted) {
/*  80 */       in.skipBytes(in.readableBytes()); return;
/*     */     }  try {
/*     */       byte[] identifier;
/*     */       int checksum;
/*     */       ByteBuf uncompressed;
/*  85 */       int idx = in.readerIndex();
/*  86 */       int inSize = in.writerIndex() - idx;
/*  87 */       if (inSize < 4) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  93 */       int chunkTypeVal = in.getUnsignedByte(idx);
/*  94 */       ChunkType chunkType = mapChunkType((byte)chunkTypeVal);
/*  95 */       int chunkLength = ByteBufUtil.swapMedium(in.getUnsignedMedium(idx + 1));
/*     */       
/*  97 */       switch (chunkType) {
/*     */         case STREAM_IDENTIFIER:
/*  99 */           if (chunkLength != SNAPPY.length) {
/* 100 */             throw new DecompressionException("Unexpected length of stream identifier: " + chunkLength);
/*     */           }
/*     */           
/* 103 */           if (inSize < 4 + SNAPPY.length) {
/*     */             break;
/*     */           }
/*     */           
/* 107 */           identifier = new byte[chunkLength];
/* 108 */           in.skipBytes(4).readBytes(identifier);
/*     */           
/* 110 */           if (!Arrays.equals(identifier, SNAPPY)) {
/* 111 */             throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
/*     */           }
/*     */ 
/*     */           
/* 115 */           this.started = true;
/*     */           break;
/*     */         case RESERVED_SKIPPABLE:
/* 118 */           if (!this.started) {
/* 119 */             throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
/*     */           }
/*     */           
/* 122 */           if (inSize < 4 + chunkLength) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 127 */           in.skipBytes(4 + chunkLength);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case RESERVED_UNSKIPPABLE:
/* 133 */           throw new DecompressionException("Found reserved unskippable chunk type: 0x" + Integer.toHexString(chunkTypeVal));
/*     */         
/*     */         case UNCOMPRESSED_DATA:
/* 136 */           if (!this.started) {
/* 137 */             throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
/*     */           }
/* 139 */           if (chunkLength > 65540) {
/* 140 */             throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
/*     */           }
/*     */           
/* 143 */           if (inSize < 4 + chunkLength) {
/*     */             return;
/*     */           }
/*     */           
/* 147 */           in.skipBytes(4);
/* 148 */           if (this.validateChecksums) {
/* 149 */             int i = ByteBufUtil.swapInt(in.readInt());
/* 150 */             Snappy.validateChecksum(i, in, in.readerIndex(), chunkLength - 4);
/*     */           } else {
/* 152 */             in.skipBytes(4);
/*     */           } 
/* 154 */           out.add(in.readSlice(chunkLength - 4).retain());
/*     */           break;
/*     */         case COMPRESSED_DATA:
/* 157 */           if (!this.started) {
/* 158 */             throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
/*     */           }
/*     */           
/* 161 */           if (inSize < 4 + chunkLength) {
/*     */             return;
/*     */           }
/*     */           
/* 165 */           in.skipBytes(4);
/* 166 */           checksum = ByteBufUtil.swapInt(in.readInt());
/* 167 */           uncompressed = ctx.alloc().buffer(0);
/* 168 */           if (this.validateChecksums) {
/* 169 */             int oldWriterIndex = in.writerIndex();
/*     */             try {
/* 171 */               in.writerIndex(in.readerIndex() + chunkLength - 4);
/* 172 */               this.snappy.decode(in, uncompressed);
/*     */             } finally {
/* 174 */               in.writerIndex(oldWriterIndex);
/*     */             } 
/* 176 */             Snappy.validateChecksum(checksum, uncompressed, 0, uncompressed.writerIndex());
/*     */           } else {
/* 178 */             this.snappy.decode(in.readSlice(chunkLength - 4), uncompressed);
/*     */           } 
/* 180 */           out.add(uncompressed);
/* 181 */           this.snappy.reset();
/*     */           break;
/*     */       } 
/* 184 */     } catch (Exception e) {
/* 185 */       this.corrupted = true;
/* 186 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ChunkType mapChunkType(byte type) {
/* 197 */     if (type == 0)
/* 198 */       return ChunkType.COMPRESSED_DATA; 
/* 199 */     if (type == 1)
/* 200 */       return ChunkType.UNCOMPRESSED_DATA; 
/* 201 */     if (type == Byte.MIN_VALUE)
/* 202 */       return ChunkType.STREAM_IDENTIFIER; 
/* 203 */     if ((type & 0x80) == 128) {
/* 204 */       return ChunkType.RESERVED_SKIPPABLE;
/*     */     }
/* 206 */     return ChunkType.RESERVED_UNSKIPPABLE;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\compression\SnappyFramedDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */