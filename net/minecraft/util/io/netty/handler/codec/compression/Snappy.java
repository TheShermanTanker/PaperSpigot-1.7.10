/*     */ package net.minecraft.util.io.netty.handler.codec.compression;
/*     */ 
/*     */ import java.util.zip.CRC32;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufUtil;
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
/*     */ 
/*     */ 
/*     */ public class Snappy
/*     */ {
/*     */   private static final int MAX_HT_SIZE = 16384;
/*     */   private static final int MIN_COMPRESSIBLE_BYTES = 15;
/*     */   private static final int PREAMBLE_NOT_FULL = -1;
/*     */   private static final int NOT_ENOUGH_INPUT = -1;
/*     */   private static final int LITERAL = 0;
/*     */   private static final int COPY_1_BYTE_OFFSET = 1;
/*     */   private static final int COPY_2_BYTE_OFFSET = 2;
/*     */   private static final int COPY_4_BYTE_OFFSET = 3;
/*  44 */   private State state = State.READY;
/*     */   private byte tag;
/*     */   private int written;
/*     */   
/*     */   private enum State {
/*  49 */     READY,
/*  50 */     READING_PREAMBLE,
/*  51 */     READING_TAG,
/*  52 */     READING_LITERAL,
/*  53 */     READING_COPY;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  57 */     this.state = State.READY;
/*  58 */     this.tag = 0;
/*  59 */     this.written = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encode(ByteBuf in, ByteBuf out, int length) {
/*  64 */     for (int i = 0;; i++) {
/*  65 */       int b = length >>> i * 7;
/*  66 */       if ((b & 0xFFFFFF80) != 0) {
/*  67 */         out.writeByte(b & 0x7F | 0x80);
/*     */       } else {
/*  69 */         out.writeByte(b);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  74 */     int inIndex = in.readerIndex();
/*  75 */     int baseIndex = in.readerIndex();
/*  76 */     int maxIndex = length;
/*     */     
/*  78 */     short[] table = getHashTable(maxIndex);
/*  79 */     int shift = 32 - (int)Math.floor(Math.log(table.length) / Math.log(2.0D));
/*     */     
/*  81 */     int nextEmit = inIndex;
/*     */     
/*  83 */     if (maxIndex - inIndex >= 15) {
/*  84 */       int nextHash = hash(in, ++inIndex, shift);
/*     */       while (true) {
/*  86 */         int skip = 32;
/*     */ 
/*     */         
/*  89 */         int nextIndex = inIndex;
/*     */         while (true) {
/*  91 */           inIndex = nextIndex;
/*  92 */           int hash = nextHash;
/*  93 */           int bytesBetweenHashLookups = skip++ >> 5;
/*  94 */           nextIndex = inIndex + bytesBetweenHashLookups;
/*     */ 
/*     */           
/*  97 */           if (nextIndex > maxIndex - 4) {
/*     */             break;
/*     */           }
/*     */           
/* 101 */           nextHash = hash(in, nextIndex, shift);
/*     */           
/* 103 */           int candidate = baseIndex + table[hash];
/*     */           
/* 105 */           table[hash] = (short)(inIndex - baseIndex);
/*     */           
/* 107 */           if (in.getInt(inIndex) == in.getInt(candidate)) {
/*     */             
/* 109 */             encodeLiteral(in, out, inIndex - nextEmit);
/*     */ 
/*     */             
/*     */             while (true) {
/* 113 */               int base = inIndex;
/* 114 */               int matched = 4 + findMatchingLength(in, candidate + 4, inIndex + 4, maxIndex);
/* 115 */               inIndex += matched;
/* 116 */               int offset = base - candidate;
/* 117 */               encodeCopy(out, offset, matched);
/* 118 */               in.readerIndex(in.readerIndex() + matched);
/* 119 */               int insertTail = inIndex - 1;
/* 120 */               nextEmit = inIndex;
/* 121 */               if (inIndex >= maxIndex - 4) {
/*     */                 break;
/*     */               }
/*     */               
/* 125 */               int prevHash = hash(in, insertTail, shift);
/* 126 */               table[prevHash] = (short)(inIndex - baseIndex - 1);
/* 127 */               int currentHash = hash(in, insertTail + 1, shift);
/* 128 */               candidate = baseIndex + table[currentHash];
/* 129 */               table[currentHash] = (short)(inIndex - baseIndex);
/*     */               
/* 131 */               if (in.getInt(insertTail + 1) != in.getInt(candidate))
/*     */               
/* 133 */               { nextHash = hash(in, insertTail + 2, shift);
/* 134 */                 inIndex++; } 
/*     */             }  break;
/*     */           } 
/*     */         }  break;
/*     */       } 
/* 139 */     }  if (nextEmit < maxIndex) {
/* 140 */       encodeLiteral(in, out, maxIndex - nextEmit);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hash(ByteBuf in, int index, int shift) {
/* 155 */     return in.getInt(index) + 506832829 >>> shift;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static short[] getHashTable(int inputSize) {
/*     */     short[] table;
/* 165 */     int htSize = 256;
/* 166 */     while (htSize < 16384 && htSize < inputSize) {
/* 167 */       htSize <<= 1;
/*     */     }
/*     */ 
/*     */     
/* 171 */     if (htSize <= 256) {
/* 172 */       table = new short[256];
/*     */     } else {
/* 174 */       table = new short[16384];
/*     */     } 
/*     */     
/* 177 */     return table;
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
/*     */ 
/*     */   
/*     */   private static int findMatchingLength(ByteBuf in, int minIndex, int inIndex, int maxIndex) {
/* 192 */     int matched = 0;
/*     */     
/* 194 */     while (inIndex <= maxIndex - 4 && in.getInt(inIndex) == in.getInt(minIndex + matched)) {
/*     */       
/* 196 */       inIndex += 4;
/* 197 */       matched += 4;
/*     */     } 
/*     */     
/* 200 */     while (inIndex < maxIndex && in.getByte(minIndex + matched) == in.getByte(inIndex)) {
/* 201 */       inIndex++;
/* 202 */       matched++;
/*     */     } 
/*     */     
/* 205 */     return matched;
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
/*     */   private static int bitsToEncode(int value) {
/* 217 */     int highestOneBit = Integer.highestOneBit(value);
/* 218 */     int bitLength = 0;
/* 219 */     while ((highestOneBit >>= 1) != 0) {
/* 220 */       bitLength++;
/*     */     }
/*     */     
/* 223 */     return bitLength;
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
/*     */   private static void encodeLiteral(ByteBuf in, ByteBuf out, int length) {
/* 236 */     if (length < 61) {
/* 237 */       out.writeByte(length - 1 << 2);
/*     */     } else {
/* 239 */       int bitLength = bitsToEncode(length - 1);
/* 240 */       int bytesToEncode = 1 + bitLength / 8;
/* 241 */       out.writeByte(59 + bytesToEncode << 2);
/* 242 */       for (int i = 0; i < bytesToEncode; i++) {
/* 243 */         out.writeByte(length - 1 >> i * 8 & 0xFF);
/*     */       }
/*     */     } 
/*     */     
/* 247 */     out.writeBytes(in, length);
/*     */   }
/*     */   
/*     */   private static void encodeCopyWithOffset(ByteBuf out, int offset, int length) {
/* 251 */     if (length < 12 && offset < 2048) {
/* 252 */       out.writeByte(0x1 | length - 4 << 2 | offset >> 8 << 5);
/* 253 */       out.writeByte(offset & 0xFF);
/*     */     } else {
/* 255 */       out.writeByte(0x2 | length - 1 << 2);
/* 256 */       out.writeByte(offset & 0xFF);
/* 257 */       out.writeByte(offset >> 8 & 0xFF);
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
/*     */   private static void encodeCopy(ByteBuf out, int offset, int length) {
/* 269 */     while (length >= 68) {
/* 270 */       encodeCopyWithOffset(out, offset, 64);
/* 271 */       length -= 64;
/*     */     } 
/*     */     
/* 274 */     if (length > 64) {
/* 275 */       encodeCopyWithOffset(out, offset, 60);
/* 276 */       length -= 60;
/*     */     } 
/*     */     
/* 279 */     encodeCopyWithOffset(out, offset, length);
/*     */   }
/*     */   
/*     */   public void decode(ByteBuf in, ByteBuf out) {
/* 283 */     while (in.isReadable()) {
/* 284 */       int uncompressedLength; int literalWritten; int decodeWritten; switch (this.state) {
/*     */         case READY:
/* 286 */           this.state = State.READING_PREAMBLE;
/*     */         case READING_PREAMBLE:
/* 288 */           uncompressedLength = readPreamble(in);
/* 289 */           if (uncompressedLength == -1) {
/*     */             return;
/*     */           }
/*     */           
/* 293 */           if (uncompressedLength == 0) {
/*     */             
/* 295 */             this.state = State.READY;
/*     */             return;
/*     */           } 
/* 298 */           out.ensureWritable(uncompressedLength);
/* 299 */           this.state = State.READING_TAG;
/*     */         case READING_TAG:
/* 301 */           if (!in.isReadable()) {
/*     */             return;
/*     */           }
/* 304 */           this.tag = in.readByte();
/* 305 */           switch (this.tag & 0x3) {
/*     */             case 0:
/* 307 */               this.state = State.READING_LITERAL;
/*     */             
/*     */             case 1:
/*     */             case 2:
/*     */             case 3:
/* 312 */               this.state = State.READING_COPY;
/*     */           } 
/*     */         
/*     */         
/*     */         case READING_LITERAL:
/* 317 */           literalWritten = decodeLiteral(this.tag, in, out);
/* 318 */           if (literalWritten != -1) {
/* 319 */             this.state = State.READING_TAG;
/* 320 */             this.written += literalWritten;
/*     */             continue;
/*     */           } 
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case READING_COPY:
/* 328 */           switch (this.tag & 0x3) {
/*     */             case 1:
/* 330 */               decodeWritten = decodeCopyWith1ByteOffset(this.tag, in, out, this.written);
/* 331 */               if (decodeWritten != -1) {
/* 332 */                 this.state = State.READING_TAG;
/* 333 */                 this.written += decodeWritten;
/*     */                 continue;
/*     */               } 
/*     */               return;
/*     */ 
/*     */             
/*     */             case 2:
/* 340 */               decodeWritten = decodeCopyWith2ByteOffset(this.tag, in, out, this.written);
/* 341 */               if (decodeWritten != -1) {
/* 342 */                 this.state = State.READING_TAG;
/* 343 */                 this.written += decodeWritten;
/*     */                 continue;
/*     */               } 
/*     */               return;
/*     */ 
/*     */             
/*     */             case 3:
/* 350 */               decodeWritten = decodeCopyWith4ByteOffset(this.tag, in, out, this.written);
/* 351 */               if (decodeWritten != -1) {
/* 352 */                 this.state = State.READING_TAG;
/* 353 */                 this.written += decodeWritten;
/*     */                 continue;
/*     */               } 
/*     */               return;
/*     */           } 
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int readPreamble(ByteBuf in) {
/* 374 */     int length = 0;
/* 375 */     int byteIndex = 0;
/* 376 */     while (in.isReadable()) {
/* 377 */       int current = in.readUnsignedByte();
/* 378 */       length |= (current & 0x7F) << byteIndex++ * 7;
/* 379 */       if ((current & 0x80) == 0) {
/* 380 */         return length;
/*     */       }
/*     */       
/* 383 */       if (byteIndex >= 4) {
/* 384 */         throw new DecompressionException("Preamble is greater than 4 bytes");
/*     */       }
/*     */     } 
/*     */     
/* 388 */     return 0;
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
/*     */   
/*     */   private static int decodeLiteral(byte tag, ByteBuf in, ByteBuf out) {
/*     */     int length;
/* 403 */     in.markReaderIndex();
/*     */     
/* 405 */     switch (tag >> 2 & 0x3F) {
/*     */       case 60:
/* 407 */         if (!in.isReadable()) {
/* 408 */           return -1;
/*     */         }
/* 410 */         length = in.readUnsignedByte();
/*     */         break;
/*     */       case 61:
/* 413 */         if (in.readableBytes() < 2) {
/* 414 */           return -1;
/*     */         }
/* 416 */         length = ByteBufUtil.swapShort(in.readShort());
/*     */         break;
/*     */       case 62:
/* 419 */         if (in.readableBytes() < 3) {
/* 420 */           return -1;
/*     */         }
/* 422 */         length = ByteBufUtil.swapMedium(in.readUnsignedMedium());
/*     */         break;
/*     */       case 64:
/* 425 */         if (in.readableBytes() < 4) {
/* 426 */           return -1;
/*     */         }
/* 428 */         length = ByteBufUtil.swapInt(in.readInt());
/*     */         break;
/*     */       default:
/* 431 */         length = tag >> 2 & 0x3F; break;
/*     */     } 
/* 433 */     length++;
/*     */     
/* 435 */     if (in.readableBytes() < length) {
/* 436 */       in.resetReaderIndex();
/* 437 */       return -1;
/*     */     } 
/*     */     
/* 440 */     out.writeBytes(in, length);
/* 441 */     return length;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int decodeCopyWith1ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
/* 458 */     if (!in.isReadable()) {
/* 459 */       return -1;
/*     */     }
/*     */     
/* 462 */     int initialIndex = out.writerIndex();
/* 463 */     int length = 4 + ((tag & 0x1C) >> 2);
/* 464 */     int offset = (tag & 0xE0) << 8 >> 5 | in.readUnsignedByte();
/*     */     
/* 466 */     validateOffset(offset, writtenSoFar);
/*     */     
/* 468 */     out.markReaderIndex();
/* 469 */     if (offset < length) {
/* 470 */       int copies = length / offset;
/* 471 */       for (; copies > 0; copies--) {
/* 472 */         out.readerIndex(initialIndex - offset);
/* 473 */         out.readBytes(out, offset);
/*     */       } 
/* 475 */       if (length % offset != 0) {
/* 476 */         out.readerIndex(initialIndex - offset);
/* 477 */         out.readBytes(out, length % offset);
/*     */       } 
/*     */     } else {
/* 480 */       out.readerIndex(initialIndex - offset);
/* 481 */       out.readBytes(out, length);
/*     */     } 
/* 483 */     out.resetReaderIndex();
/*     */     
/* 485 */     return length;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int decodeCopyWith2ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
/* 502 */     if (in.readableBytes() < 2) {
/* 503 */       return -1;
/*     */     }
/*     */     
/* 506 */     int initialIndex = out.writerIndex();
/* 507 */     int length = 1 + (tag >> 2 & 0x3F);
/* 508 */     int offset = ByteBufUtil.swapShort(in.readShort());
/*     */     
/* 510 */     validateOffset(offset, writtenSoFar);
/*     */     
/* 512 */     out.markReaderIndex();
/* 513 */     if (offset < length) {
/* 514 */       int copies = length / offset;
/* 515 */       for (; copies > 0; copies--) {
/* 516 */         out.readerIndex(initialIndex - offset);
/* 517 */         out.readBytes(out, offset);
/*     */       } 
/* 519 */       if (length % offset != 0) {
/* 520 */         out.readerIndex(initialIndex - offset);
/* 521 */         out.readBytes(out, length % offset);
/*     */       } 
/*     */     } else {
/* 524 */       out.readerIndex(initialIndex - offset);
/* 525 */       out.readBytes(out, length);
/*     */     } 
/* 527 */     out.resetReaderIndex();
/*     */     
/* 529 */     return length;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int decodeCopyWith4ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
/* 546 */     if (in.readableBytes() < 4) {
/* 547 */       return -1;
/*     */     }
/*     */     
/* 550 */     int initialIndex = out.writerIndex();
/* 551 */     int length = 1 + (tag >> 2 & 0x3F);
/* 552 */     int offset = ByteBufUtil.swapInt(in.readInt());
/*     */     
/* 554 */     validateOffset(offset, writtenSoFar);
/*     */     
/* 556 */     out.markReaderIndex();
/* 557 */     if (offset < length) {
/* 558 */       int copies = length / offset;
/* 559 */       for (; copies > 0; copies--) {
/* 560 */         out.readerIndex(initialIndex - offset);
/* 561 */         out.readBytes(out, offset);
/*     */       } 
/* 563 */       if (length % offset != 0) {
/* 564 */         out.readerIndex(initialIndex - offset);
/* 565 */         out.readBytes(out, length % offset);
/*     */       } 
/*     */     } else {
/* 568 */       out.readerIndex(initialIndex - offset);
/* 569 */       out.readBytes(out, length);
/*     */     } 
/* 571 */     out.resetReaderIndex();
/*     */     
/* 573 */     return length;
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
/*     */   private static void validateOffset(int offset, int chunkSizeSoFar) {
/* 586 */     if (offset > 32767) {
/* 587 */       throw new DecompressionException("Offset exceeds maximum permissible value");
/*     */     }
/*     */     
/* 590 */     if (offset <= 0) {
/* 591 */       throw new DecompressionException("Offset is less than minimum permissible value");
/*     */     }
/*     */     
/* 594 */     if (offset > chunkSizeSoFar) {
/* 595 */       throw new DecompressionException("Offset exceeds size of chunk");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateChecksum(ByteBuf data) {
/* 606 */     return calculateChecksum(data, data.readerIndex(), data.readableBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateChecksum(ByteBuf data, int offset, int length) {
/* 616 */     CRC32 crc32 = new CRC32();
/*     */     try {
/* 618 */       if (data.hasArray()) {
/* 619 */         crc32.update(data.array(), data.arrayOffset() + offset, length);
/*     */       } else {
/* 621 */         byte[] array = new byte[length];
/* 622 */         data.getBytes(offset, array);
/* 623 */         crc32.update(array);
/*     */       } 
/*     */       
/* 626 */       return maskChecksum((int)crc32.getValue());
/*     */     } finally {
/* 628 */       crc32.reset();
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
/*     */ 
/*     */   
/*     */   static void validateChecksum(int expectedChecksum, ByteBuf data) {
/* 642 */     validateChecksum(expectedChecksum, data, data.readerIndex(), data.readableBytes());
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
/*     */   static void validateChecksum(int expectedChecksum, ByteBuf data, int offset, int length) {
/* 655 */     int actualChecksum = calculateChecksum(data, offset, length);
/* 656 */     if (actualChecksum != expectedChecksum) {
/* 657 */       throw new DecompressionException("mismatching checksum: " + Integer.toHexString(actualChecksum) + " (expected: " + Integer.toHexString(expectedChecksum) + ')');
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int maskChecksum(int checksum) {
/* 675 */     return (checksum >> 15 | checksum << 17) + -1568478504;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\compression\Snappy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */