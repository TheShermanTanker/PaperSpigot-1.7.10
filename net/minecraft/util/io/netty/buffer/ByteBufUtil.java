/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import net.minecraft.util.io.netty.util.CharsetUtil;
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
/*     */ public final class ByteBufUtil
/*     */ {
/*  34 */   private static final char[] HEXDUMP_TABLE = new char[1024];
/*     */   
/*     */   static {
/*  37 */     char[] DIGITS = "0123456789abcdef".toCharArray();
/*  38 */     for (int i = 0; i < 256; i++) {
/*  39 */       HEXDUMP_TABLE[i << 1] = DIGITS[i >>> 4 & 0xF];
/*  40 */       HEXDUMP_TABLE[(i << 1) + 1] = DIGITS[i & 0xF];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String hexDump(ByteBuf buffer) {
/*  49 */     return hexDump(buffer, buffer.readerIndex(), buffer.readableBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String hexDump(ByteBuf buffer, int fromIndex, int length) {
/*  57 */     if (length < 0) {
/*  58 */       throw new IllegalArgumentException("length: " + length);
/*     */     }
/*  60 */     if (length == 0) {
/*  61 */       return "";
/*     */     }
/*     */     
/*  64 */     int endIndex = fromIndex + length;
/*  65 */     char[] buf = new char[length << 1];
/*     */     
/*  67 */     int srcIdx = fromIndex;
/*  68 */     int dstIdx = 0;
/*  69 */     for (; srcIdx < endIndex; srcIdx++, dstIdx += 2) {
/*  70 */       System.arraycopy(HEXDUMP_TABLE, buffer.getUnsignedByte(srcIdx) << 1, buf, dstIdx, 2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  75 */     return new String(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hashCode(ByteBuf buffer) {
/*  83 */     int aLen = buffer.readableBytes();
/*  84 */     int intCount = aLen >>> 2;
/*  85 */     int byteCount = aLen & 0x3;
/*     */     
/*  87 */     int hashCode = 1;
/*  88 */     int arrayIndex = buffer.readerIndex();
/*  89 */     if (buffer.order() == ByteOrder.BIG_ENDIAN) {
/*  90 */       for (int j = intCount; j > 0; j--) {
/*  91 */         hashCode = 31 * hashCode + buffer.getInt(arrayIndex);
/*  92 */         arrayIndex += 4;
/*     */       } 
/*     */     } else {
/*  95 */       for (int j = intCount; j > 0; j--) {
/*  96 */         hashCode = 31 * hashCode + swapInt(buffer.getInt(arrayIndex));
/*  97 */         arrayIndex += 4;
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     for (int i = byteCount; i > 0; i--) {
/* 102 */       hashCode = 31 * hashCode + buffer.getByte(arrayIndex++);
/*     */     }
/*     */     
/* 105 */     if (hashCode == 0) {
/* 106 */       hashCode = 1;
/*     */     }
/*     */     
/* 109 */     return hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(ByteBuf bufferA, ByteBuf bufferB) {
/* 118 */     int aLen = bufferA.readableBytes();
/* 119 */     if (aLen != bufferB.readableBytes()) {
/* 120 */       return false;
/*     */     }
/*     */     
/* 123 */     int longCount = aLen >>> 3;
/* 124 */     int byteCount = aLen & 0x7;
/*     */     
/* 126 */     int aIndex = bufferA.readerIndex();
/* 127 */     int bIndex = bufferB.readerIndex();
/*     */     
/* 129 */     if (bufferA.order() == bufferB.order()) {
/* 130 */       for (int j = longCount; j > 0; j--) {
/* 131 */         if (bufferA.getLong(aIndex) != bufferB.getLong(bIndex)) {
/* 132 */           return false;
/*     */         }
/* 134 */         aIndex += 8;
/* 135 */         bIndex += 8;
/*     */       } 
/*     */     } else {
/* 138 */       for (int j = longCount; j > 0; j--) {
/* 139 */         if (bufferA.getLong(aIndex) != swapLong(bufferB.getLong(bIndex))) {
/* 140 */           return false;
/*     */         }
/* 142 */         aIndex += 8;
/* 143 */         bIndex += 8;
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     for (int i = byteCount; i > 0; i--) {
/* 148 */       if (bufferA.getByte(aIndex) != bufferB.getByte(bIndex)) {
/* 149 */         return false;
/*     */       }
/* 151 */       aIndex++;
/* 152 */       bIndex++;
/*     */     } 
/*     */     
/* 155 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int compare(ByteBuf bufferA, ByteBuf bufferB) {
/* 163 */     int aLen = bufferA.readableBytes();
/* 164 */     int bLen = bufferB.readableBytes();
/* 165 */     int minLength = Math.min(aLen, bLen);
/* 166 */     int uintCount = minLength >>> 2;
/* 167 */     int byteCount = minLength & 0x3;
/*     */     
/* 169 */     int aIndex = bufferA.readerIndex();
/* 170 */     int bIndex = bufferB.readerIndex();
/*     */     
/* 172 */     if (bufferA.order() == bufferB.order()) {
/* 173 */       for (int j = uintCount; j > 0; j--) {
/* 174 */         long va = bufferA.getUnsignedInt(aIndex);
/* 175 */         long vb = bufferB.getUnsignedInt(bIndex);
/* 176 */         if (va > vb) {
/* 177 */           return 1;
/*     */         }
/* 179 */         if (va < vb) {
/* 180 */           return -1;
/*     */         }
/* 182 */         aIndex += 4;
/* 183 */         bIndex += 4;
/*     */       } 
/*     */     } else {
/* 186 */       for (int j = uintCount; j > 0; j--) {
/* 187 */         long va = bufferA.getUnsignedInt(aIndex);
/* 188 */         long vb = swapInt(bufferB.getInt(bIndex)) & 0xFFFFFFFFL;
/* 189 */         if (va > vb) {
/* 190 */           return 1;
/*     */         }
/* 192 */         if (va < vb) {
/* 193 */           return -1;
/*     */         }
/* 195 */         aIndex += 4;
/* 196 */         bIndex += 4;
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     for (int i = byteCount; i > 0; i--) {
/* 201 */       short va = bufferA.getUnsignedByte(aIndex);
/* 202 */       short vb = bufferB.getUnsignedByte(bIndex);
/* 203 */       if (va > vb) {
/* 204 */         return 1;
/*     */       }
/* 206 */       if (va < vb) {
/* 207 */         return -1;
/*     */       }
/* 209 */       aIndex++;
/* 210 */       bIndex++;
/*     */     } 
/*     */     
/* 213 */     return aLen - bLen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int indexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
/* 221 */     if (fromIndex <= toIndex) {
/* 222 */       return firstIndexOf(buffer, fromIndex, toIndex, value);
/*     */     }
/* 224 */     return lastIndexOf(buffer, fromIndex, toIndex, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static short swapShort(short value) {
/* 232 */     return Short.reverseBytes(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int swapMedium(int value) {
/* 239 */     int swapped = value << 16 & 0xFF0000 | value & 0xFF00 | value >>> 16 & 0xFF;
/* 240 */     if ((swapped & 0x800000) != 0) {
/* 241 */       swapped |= 0xFF000000;
/*     */     }
/* 243 */     return swapped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int swapInt(int value) {
/* 250 */     return Integer.reverseBytes(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long swapLong(long value) {
/* 257 */     return Long.reverseBytes(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf readBytes(ByteBufAllocator alloc, ByteBuf buffer, int length) {
/* 264 */     boolean release = true;
/* 265 */     ByteBuf dst = alloc.buffer(length);
/*     */     try {
/* 267 */       buffer.readBytes(dst);
/* 268 */       release = false;
/* 269 */       return dst;
/*     */     } finally {
/* 271 */       if (release) {
/* 272 */         dst.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int firstIndexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
/* 278 */     fromIndex = Math.max(fromIndex, 0);
/* 279 */     if (fromIndex >= toIndex || buffer.capacity() == 0) {
/* 280 */       return -1;
/*     */     }
/*     */     
/* 283 */     for (int i = fromIndex; i < toIndex; i++) {
/* 284 */       if (buffer.getByte(i) == value) {
/* 285 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 289 */     return -1;
/*     */   }
/*     */   
/*     */   private static int lastIndexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
/* 293 */     fromIndex = Math.min(fromIndex, buffer.capacity());
/* 294 */     if (fromIndex < 0 || buffer.capacity() == 0) {
/* 295 */       return -1;
/*     */     }
/*     */     
/* 298 */     for (int i = fromIndex - 1; i >= toIndex; i--) {
/* 299 */       if (buffer.getByte(i) == value) {
/* 300 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 304 */     return -1;
/*     */   }
/*     */   
/*     */   static ByteBuffer encodeString(CharBuffer src, Charset charset) {
/* 308 */     CharsetEncoder encoder = CharsetUtil.getEncoder(charset);
/* 309 */     ByteBuffer dst = ByteBuffer.allocate((int)(src.remaining() * encoder.maxBytesPerChar()));
/*     */     
/*     */     try {
/* 312 */       CoderResult cr = encoder.encode(src, dst, true);
/* 313 */       if (!cr.isUnderflow()) {
/* 314 */         cr.throwException();
/*     */       }
/* 316 */       cr = encoder.flush(dst);
/* 317 */       if (!cr.isUnderflow()) {
/* 318 */         cr.throwException();
/*     */       }
/* 320 */     } catch (CharacterCodingException x) {
/* 321 */       throw new IllegalStateException(x);
/*     */     } 
/* 323 */     dst.flip();
/* 324 */     return dst;
/*     */   }
/*     */   
/*     */   static String decodeString(ByteBuffer src, Charset charset) {
/* 328 */     CharsetDecoder decoder = CharsetUtil.getDecoder(charset);
/* 329 */     CharBuffer dst = CharBuffer.allocate((int)(src.remaining() * decoder.maxCharsPerByte()));
/*     */     
/*     */     try {
/* 332 */       CoderResult cr = decoder.decode(src, dst, true);
/* 333 */       if (!cr.isUnderflow()) {
/* 334 */         cr.throwException();
/*     */       }
/* 336 */       cr = decoder.flush(dst);
/* 337 */       if (!cr.isUnderflow()) {
/* 338 */         cr.throwException();
/*     */       }
/* 340 */     } catch (CharacterCodingException x) {
/* 341 */       throw new IllegalStateException(x);
/*     */     } 
/* 343 */     return dst.flip().toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\ByteBufUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */