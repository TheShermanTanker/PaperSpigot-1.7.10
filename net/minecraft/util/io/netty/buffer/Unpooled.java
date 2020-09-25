/*     */ package net.minecraft.util.io.netty.buffer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
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
/*     */ public final class Unpooled
/*     */ {
/*  79 */   private static final ByteBufAllocator ALLOC = UnpooledByteBufAllocator.DEFAULT;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static final ByteBuf EMPTY_BUFFER = ALLOC.buffer(0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf buffer() {
/* 101 */     return ALLOC.heapBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf directBuffer() {
/* 109 */     return ALLOC.directBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf buffer(int initialCapacity) {
/* 118 */     return ALLOC.heapBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf directBuffer(int initialCapacity) {
/* 127 */     return ALLOC.directBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf buffer(int initialCapacity, int maxCapacity) {
/* 136 */     return ALLOC.heapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
/* 145 */     return ALLOC.directBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(byte[] array) {
/* 154 */     if (array.length == 0) {
/* 155 */       return EMPTY_BUFFER;
/*     */     }
/* 157 */     return new UnpooledHeapByteBuf(ALLOC, array, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(byte[] array, int offset, int length) {
/* 166 */     if (length == 0) {
/* 167 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 170 */     if (offset == 0 && length == array.length) {
/* 171 */       return wrappedBuffer(array);
/*     */     }
/*     */     
/* 174 */     return wrappedBuffer(array).slice(offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuffer buffer) {
/* 183 */     if (!buffer.hasRemaining()) {
/* 184 */       return EMPTY_BUFFER;
/*     */     }
/* 186 */     if (buffer.hasArray()) {
/* 187 */       return wrappedBuffer(buffer.array(), buffer.arrayOffset() + buffer.position(), buffer.remaining()).order(buffer.order());
/*     */     }
/*     */ 
/*     */     
/* 191 */     if (PlatformDependent.hasUnsafe()) {
/* 192 */       if (buffer.isReadOnly()) {
/* 193 */         if (buffer.isDirect()) {
/* 194 */           return new ReadOnlyUnsafeDirectByteBuf(ALLOC, buffer);
/*     */         }
/* 196 */         return new ReadOnlyByteBufferBuf(ALLOC, buffer);
/*     */       } 
/*     */       
/* 199 */       return new UnpooledUnsafeDirectByteBuf(ALLOC, buffer, buffer.remaining());
/*     */     } 
/*     */     
/* 202 */     if (buffer.isReadOnly()) {
/* 203 */       return new ReadOnlyByteBufferBuf(ALLOC, buffer);
/*     */     }
/* 205 */     return new UnpooledDirectByteBuf(ALLOC, buffer, buffer.remaining());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuf buffer) {
/* 216 */     if (buffer.isReadable()) {
/* 217 */       return buffer.slice();
/*     */     }
/* 219 */     return EMPTY_BUFFER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(byte[]... arrays) {
/* 229 */     return wrappedBuffer(16, arrays);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuf... buffers) {
/* 238 */     return wrappedBuffer(16, buffers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(ByteBuffer... buffers) {
/* 247 */     return wrappedBuffer(16, buffers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf wrappedBuffer(int maxNumComponents, byte[]... arrays) {
/* 256 */     switch (arrays.length) {
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
/*     */       case 0:
/* 281 */         return EMPTY_BUFFER;
/*     */       case 1:
/*     */         if ((arrays[0]).length != 0)
/*     */           return wrappedBuffer(arrays[0]); 
/*     */     }  List<ByteBuf> components = new ArrayList<ByteBuf>(arrays.length); for (byte[] a : arrays) {
/*     */       if (a == null)
/*     */         break;  if (a.length > 0)
/*     */         components.add(wrappedBuffer(a)); 
/*     */     }  if (!components.isEmpty())
/* 290 */       return new CompositeByteBuf(ALLOC, false, maxNumComponents, components);  } public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuf... buffers) { switch (buffers.length) {
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
/*     */       case 0:
/* 305 */         return EMPTY_BUFFER;
/*     */       case 1:
/*     */         if (buffers[0].isReadable())
/*     */           return wrappedBuffer(buffers[0].order(BIG_ENDIAN)); 
/*     */     } 
/*     */     for (ByteBuf b : buffers) {
/*     */       if (b.isReadable())
/*     */         return new CompositeByteBuf(ALLOC, false, maxNumComponents, buffers); 
/*     */     }  }
/* 314 */   public static ByteBuf wrappedBuffer(int maxNumComponents, ByteBuffer... buffers) { switch (buffers.length)
/*     */     
/*     */     { 
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
/*     */       case 0:
/* 339 */         return EMPTY_BUFFER;
/*     */       case 1:
/*     */         if (buffers[0].hasRemaining())
/*     */           return wrappedBuffer(buffers[0].order(BIG_ENDIAN));  }  List<ByteBuf> components = new ArrayList<ByteBuf>(buffers.length); for (ByteBuffer b : buffers) { if (b == null)
/*     */         break;  if (b.remaining() > 0)
/*     */         components.add(wrappedBuffer(b.order(BIG_ENDIAN)));  }
/*     */      if (!components.isEmpty())
/* 346 */       return new CompositeByteBuf(ALLOC, false, maxNumComponents, components);  } public static CompositeByteBuf compositeBuffer() { return compositeBuffer(16); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompositeByteBuf compositeBuffer(int maxNumComponents) {
/* 353 */     return new CompositeByteBuf(ALLOC, false, maxNumComponents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(byte[] array) {
/* 362 */     if (array.length == 0) {
/* 363 */       return EMPTY_BUFFER;
/*     */     }
/* 365 */     return wrappedBuffer((byte[])array.clone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(byte[] array, int offset, int length) {
/* 375 */     if (length == 0) {
/* 376 */       return EMPTY_BUFFER;
/*     */     }
/* 378 */     byte[] copy = new byte[length];
/* 379 */     System.arraycopy(array, offset, copy, 0, length);
/* 380 */     return wrappedBuffer(copy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(ByteBuffer buffer) {
/* 390 */     int length = buffer.remaining();
/* 391 */     if (length == 0) {
/* 392 */       return EMPTY_BUFFER;
/*     */     }
/* 394 */     byte[] copy = new byte[length];
/* 395 */     int position = buffer.position();
/*     */     try {
/* 397 */       buffer.get(copy);
/*     */     } finally {
/* 399 */       buffer.position(position);
/*     */     } 
/* 401 */     return wrappedBuffer(copy).order(buffer.order());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(ByteBuf buffer) {
/* 411 */     if (buffer.isReadable()) {
/* 412 */       return buffer.copy();
/*     */     }
/* 414 */     return EMPTY_BUFFER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(byte[]... arrays) {
/* 425 */     switch (arrays.length) {
/*     */       case 0:
/* 427 */         return EMPTY_BUFFER;
/*     */       case 1:
/* 429 */         if ((arrays[0]).length == 0) {
/* 430 */           return EMPTY_BUFFER;
/*     */         }
/* 432 */         return copiedBuffer(arrays[0]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 437 */     int length = 0;
/* 438 */     for (byte[] a : arrays) {
/* 439 */       if (Integer.MAX_VALUE - length < a.length) {
/* 440 */         throw new IllegalArgumentException("The total length of the specified arrays is too big.");
/*     */       }
/*     */       
/* 443 */       length += a.length;
/*     */     } 
/*     */     
/* 446 */     if (length == 0) {
/* 447 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 450 */     byte[] mergedArray = new byte[length];
/* 451 */     for (int i = 0, j = 0; i < arrays.length; i++) {
/* 452 */       byte[] a = arrays[i];
/* 453 */       System.arraycopy(a, 0, mergedArray, j, a.length);
/* 454 */       j += a.length;
/*     */     } 
/*     */     
/* 457 */     return wrappedBuffer(mergedArray);
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
/*     */   public static ByteBuf copiedBuffer(ByteBuf... buffers) {
/* 471 */     switch (buffers.length) {
/*     */       case 0:
/* 473 */         return EMPTY_BUFFER;
/*     */       case 1:
/* 475 */         return copiedBuffer(buffers[0]);
/*     */     } 
/*     */ 
/*     */     
/* 479 */     ByteOrder order = null;
/* 480 */     int length = 0;
/* 481 */     for (ByteBuf b : buffers) {
/* 482 */       int bLen = b.readableBytes();
/* 483 */       if (bLen > 0) {
/*     */ 
/*     */         
/* 486 */         if (Integer.MAX_VALUE - length < bLen) {
/* 487 */           throw new IllegalArgumentException("The total length of the specified buffers is too big.");
/*     */         }
/*     */         
/* 490 */         length += bLen;
/* 491 */         if (order != null) {
/* 492 */           if (!order.equals(b.order())) {
/* 493 */             throw new IllegalArgumentException("inconsistent byte order");
/*     */           }
/*     */         } else {
/* 496 */           order = b.order();
/*     */         } 
/*     */       } 
/*     */     } 
/* 500 */     if (length == 0) {
/* 501 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 504 */     byte[] mergedArray = new byte[length];
/* 505 */     for (int i = 0, j = 0; i < buffers.length; i++) {
/* 506 */       ByteBuf b = buffers[i];
/* 507 */       int bLen = b.readableBytes();
/* 508 */       b.getBytes(b.readerIndex(), mergedArray, j, bLen);
/* 509 */       j += bLen;
/*     */     } 
/*     */     
/* 512 */     return wrappedBuffer(mergedArray).order(order);
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
/*     */   public static ByteBuf copiedBuffer(ByteBuffer... buffers) {
/* 526 */     switch (buffers.length) {
/*     */       case 0:
/* 528 */         return EMPTY_BUFFER;
/*     */       case 1:
/* 530 */         return copiedBuffer(buffers[0]);
/*     */     } 
/*     */ 
/*     */     
/* 534 */     ByteOrder order = null;
/* 535 */     int length = 0;
/* 536 */     for (ByteBuffer b : buffers) {
/* 537 */       int bLen = b.remaining();
/* 538 */       if (bLen > 0) {
/*     */ 
/*     */         
/* 541 */         if (Integer.MAX_VALUE - length < bLen) {
/* 542 */           throw new IllegalArgumentException("The total length of the specified buffers is too big.");
/*     */         }
/*     */         
/* 545 */         length += bLen;
/* 546 */         if (order != null) {
/* 547 */           if (!order.equals(b.order())) {
/* 548 */             throw new IllegalArgumentException("inconsistent byte order");
/*     */           }
/*     */         } else {
/* 551 */           order = b.order();
/*     */         } 
/*     */       } 
/*     */     } 
/* 555 */     if (length == 0) {
/* 556 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 559 */     byte[] mergedArray = new byte[length];
/* 560 */     for (int i = 0, j = 0; i < buffers.length; i++) {
/* 561 */       ByteBuffer b = buffers[i];
/* 562 */       int bLen = b.remaining();
/* 563 */       int oldPos = b.position();
/* 564 */       b.get(mergedArray, j, bLen);
/* 565 */       b.position(oldPos);
/* 566 */       j += bLen;
/*     */     } 
/*     */     
/* 569 */     return wrappedBuffer(mergedArray).order(order);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(CharSequence string, Charset charset) {
/* 579 */     if (string == null) {
/* 580 */       throw new NullPointerException("string");
/*     */     }
/*     */     
/* 583 */     if (string instanceof CharBuffer) {
/* 584 */       return copiedBuffer((CharBuffer)string, charset);
/*     */     }
/*     */     
/* 587 */     return copiedBuffer(CharBuffer.wrap(string), charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(CharSequence string, int offset, int length, Charset charset) {
/* 598 */     if (string == null) {
/* 599 */       throw new NullPointerException("string");
/*     */     }
/* 601 */     if (length == 0) {
/* 602 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 605 */     if (string instanceof CharBuffer) {
/* 606 */       CharBuffer buf = (CharBuffer)string;
/* 607 */       if (buf.hasArray()) {
/* 608 */         return copiedBuffer(buf.array(), buf.arrayOffset() + buf.position() + offset, length, charset);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 614 */       buf = buf.slice();
/* 615 */       buf.limit(length);
/* 616 */       buf.position(offset);
/* 617 */       return copiedBuffer(buf, charset);
/*     */     } 
/*     */     
/* 620 */     return copiedBuffer(CharBuffer.wrap(string, offset, offset + length), charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(char[] array, Charset charset) {
/* 630 */     return copiedBuffer(array, 0, array.length, charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copiedBuffer(char[] array, int offset, int length, Charset charset) {
/* 640 */     if (array == null) {
/* 641 */       throw new NullPointerException("array");
/*     */     }
/* 643 */     if (length == 0) {
/* 644 */       return EMPTY_BUFFER;
/*     */     }
/* 646 */     return copiedBuffer(CharBuffer.wrap(array, offset, length), charset);
/*     */   }
/*     */   
/*     */   private static ByteBuf copiedBuffer(CharBuffer buffer, Charset charset) {
/* 650 */     ByteBuffer dst = ByteBufUtil.encodeString(buffer, charset);
/* 651 */     ByteBuf result = wrappedBuffer(dst.array());
/* 652 */     result.writerIndex(dst.remaining());
/* 653 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf unmodifiableBuffer(ByteBuf buffer) {
/* 663 */     ByteOrder endianness = buffer.order();
/* 664 */     if (endianness == BIG_ENDIAN) {
/* 665 */       return new ReadOnlyByteBuf(buffer);
/*     */     }
/*     */     
/* 668 */     return (new ReadOnlyByteBuf(buffer.order(BIG_ENDIAN))).order(LITTLE_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyInt(int value) {
/* 675 */     ByteBuf buf = buffer(4);
/* 676 */     buf.writeInt(value);
/* 677 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyInt(int... values) {
/* 684 */     if (values == null || values.length == 0) {
/* 685 */       return EMPTY_BUFFER;
/*     */     }
/* 687 */     ByteBuf buffer = buffer(values.length * 4);
/* 688 */     for (int v : values) {
/* 689 */       buffer.writeInt(v);
/*     */     }
/* 691 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyShort(int value) {
/* 698 */     ByteBuf buf = buffer(2);
/* 699 */     buf.writeShort(value);
/* 700 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyShort(short... values) {
/* 707 */     if (values == null || values.length == 0) {
/* 708 */       return EMPTY_BUFFER;
/*     */     }
/* 710 */     ByteBuf buffer = buffer(values.length * 2);
/* 711 */     for (int v : values) {
/* 712 */       buffer.writeShort(v);
/*     */     }
/* 714 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyShort(int... values) {
/* 721 */     if (values == null || values.length == 0) {
/* 722 */       return EMPTY_BUFFER;
/*     */     }
/* 724 */     ByteBuf buffer = buffer(values.length * 2);
/* 725 */     for (int v : values) {
/* 726 */       buffer.writeShort(v);
/*     */     }
/* 728 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyMedium(int value) {
/* 735 */     ByteBuf buf = buffer(3);
/* 736 */     buf.writeMedium(value);
/* 737 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyMedium(int... values) {
/* 744 */     if (values == null || values.length == 0) {
/* 745 */       return EMPTY_BUFFER;
/*     */     }
/* 747 */     ByteBuf buffer = buffer(values.length * 3);
/* 748 */     for (int v : values) {
/* 749 */       buffer.writeMedium(v);
/*     */     }
/* 751 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyLong(long value) {
/* 758 */     ByteBuf buf = buffer(8);
/* 759 */     buf.writeLong(value);
/* 760 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyLong(long... values) {
/* 767 */     if (values == null || values.length == 0) {
/* 768 */       return EMPTY_BUFFER;
/*     */     }
/* 770 */     ByteBuf buffer = buffer(values.length * 8);
/* 771 */     for (long v : values) {
/* 772 */       buffer.writeLong(v);
/*     */     }
/* 774 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyBoolean(boolean value) {
/* 781 */     ByteBuf buf = buffer(1);
/* 782 */     buf.writeBoolean(value);
/* 783 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyBoolean(boolean... values) {
/* 790 */     if (values == null || values.length == 0) {
/* 791 */       return EMPTY_BUFFER;
/*     */     }
/* 793 */     ByteBuf buffer = buffer(values.length);
/* 794 */     for (boolean v : values) {
/* 795 */       buffer.writeBoolean(v);
/*     */     }
/* 797 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyFloat(float value) {
/* 804 */     ByteBuf buf = buffer(4);
/* 805 */     buf.writeFloat(value);
/* 806 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyFloat(float... values) {
/* 813 */     if (values == null || values.length == 0) {
/* 814 */       return EMPTY_BUFFER;
/*     */     }
/* 816 */     ByteBuf buffer = buffer(values.length * 4);
/* 817 */     for (float v : values) {
/* 818 */       buffer.writeFloat(v);
/*     */     }
/* 820 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyDouble(double value) {
/* 827 */     ByteBuf buf = buffer(8);
/* 828 */     buf.writeDouble(value);
/* 829 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf copyDouble(double... values) {
/* 836 */     if (values == null || values.length == 0) {
/* 837 */       return EMPTY_BUFFER;
/*     */     }
/* 839 */     ByteBuf buffer = buffer(values.length * 8);
/* 840 */     for (double v : values) {
/* 841 */       buffer.writeDouble(v);
/*     */     }
/* 843 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuf unreleasableBuffer(ByteBuf buf) {
/* 850 */     return new UnreleasableByteBuf(buf);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\Unpooled.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */