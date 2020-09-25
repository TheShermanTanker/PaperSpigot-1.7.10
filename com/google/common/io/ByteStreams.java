/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.Arrays;
/*     */ import java.util.zip.Checksum;
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
/*     */ @Beta
/*     */ public final class ByteStreams
/*     */ {
/*     */   private static final int BUF_SIZE = 4096;
/*     */   
/*     */   public static InputSupplier<ByteArrayInputStream> newInputStreamSupplier(byte[] b) {
/*  62 */     return newInputStreamSupplier(b, 0, b.length);
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
/*     */   public static InputSupplier<ByteArrayInputStream> newInputStreamSupplier(final byte[] b, final int off, final int len) {
/*  76 */     return new InputSupplier<ByteArrayInputStream>()
/*     */       {
/*     */         public ByteArrayInputStream getInput() {
/*  79 */           return new ByteArrayInputStream(b, off, len);
/*     */         }
/*     */       };
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
/*     */   public static void write(byte[] from, OutputSupplier<? extends OutputStream> to) throws IOException {
/*  93 */     Preconditions.checkNotNull(from);
/*  94 */     boolean threw = true;
/*  95 */     OutputStream out = to.getOutput();
/*     */     try {
/*  97 */       out.write(from);
/*  98 */       threw = false;
/*     */     } finally {
/* 100 */       Closeables.close(out, threw);
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
/*     */   public static long copy(InputSupplier<? extends InputStream> from, OutputSupplier<? extends OutputStream> to) throws IOException {
/* 115 */     boolean threw = true;
/* 116 */     InputStream in = from.getInput();
/*     */     try {
/* 118 */       OutputStream out = to.getOutput();
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */       
/* 127 */       Closeables.close(in, threw);
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
/*     */   public static long copy(InputSupplier<? extends InputStream> from, OutputStream to) throws IOException {
/* 143 */     boolean threw = true;
/* 144 */     InputStream in = from.getInput();
/*     */     try {
/* 146 */       long count = copy(in, to);
/* 147 */       threw = false;
/* 148 */       return count;
/*     */     } finally {
/* 150 */       Closeables.close(in, threw);
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
/*     */   public static long copy(InputStream from, OutputSupplier<? extends OutputStream> to) throws IOException {
/* 167 */     boolean threw = true;
/* 168 */     OutputStream out = to.getOutput();
/*     */     try {
/* 170 */       long count = copy(from, out);
/* 171 */       threw = false;
/* 172 */       return count;
/*     */     } finally {
/* 174 */       Closeables.close(out, threw);
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
/*     */   public static long copy(InputStream from, OutputStream to) throws IOException {
/* 189 */     byte[] buf = new byte[4096];
/* 190 */     long total = 0L;
/*     */     while (true) {
/* 192 */       int r = from.read(buf);
/* 193 */       if (r == -1) {
/*     */         break;
/*     */       }
/* 196 */       to.write(buf, 0, r);
/* 197 */       total += r;
/*     */     } 
/* 199 */     return total;
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
/*     */   public static long copy(ReadableByteChannel from, WritableByteChannel to) throws IOException {
/* 213 */     ByteBuffer buf = ByteBuffer.allocate(4096);
/* 214 */     long total = 0L;
/* 215 */     while (from.read(buf) != -1) {
/* 216 */       buf.flip();
/* 217 */       while (buf.hasRemaining()) {
/* 218 */         total += to.write(buf);
/*     */       }
/* 220 */       buf.clear();
/*     */     } 
/* 222 */     return total;
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
/*     */   public static byte[] toByteArray(InputStream in) throws IOException {
/* 234 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 235 */     copy(in, out);
/* 236 */     return out.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] toByteArray(InputSupplier<? extends InputStream> supplier) throws IOException {
/* 247 */     boolean threw = true;
/* 248 */     InputStream in = supplier.getInput();
/*     */     try {
/* 250 */       byte[] result = toByteArray(in);
/* 251 */       threw = false;
/* 252 */       return result;
/*     */     } finally {
/* 254 */       Closeables.close(in, threw);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteArrayDataInput newDataInput(byte[] bytes) {
/* 263 */     return new ByteArrayDataInputStream(bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteArrayDataInput newDataInput(byte[] bytes, int start) {
/* 274 */     Preconditions.checkPositionIndex(start, bytes.length);
/* 275 */     return new ByteArrayDataInputStream(bytes, start);
/*     */   }
/*     */   
/*     */   private static class ByteArrayDataInputStream implements ByteArrayDataInput {
/*     */     final DataInput input;
/*     */     
/*     */     ByteArrayDataInputStream(byte[] bytes) {
/* 282 */       this.input = new DataInputStream(new ByteArrayInputStream(bytes));
/*     */     }
/*     */     
/*     */     ByteArrayDataInputStream(byte[] bytes, int start) {
/* 286 */       this.input = new DataInputStream(new ByteArrayInputStream(bytes, start, bytes.length - start));
/*     */     }
/*     */ 
/*     */     
/*     */     public void readFully(byte[] b) {
/*     */       try {
/* 292 */         this.input.readFully(b);
/* 293 */       } catch (IOException e) {
/* 294 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void readFully(byte[] b, int off, int len) {
/*     */       try {
/* 300 */         this.input.readFully(b, off, len);
/* 301 */       } catch (IOException e) {
/* 302 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int skipBytes(int n) {
/*     */       try {
/* 308 */         return this.input.skipBytes(n);
/* 309 */       } catch (IOException e) {
/* 310 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean readBoolean() {
/*     */       try {
/* 316 */         return this.input.readBoolean();
/* 317 */       } catch (IOException e) {
/* 318 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte readByte() {
/*     */       try {
/* 324 */         return this.input.readByte();
/* 325 */       } catch (EOFException e) {
/* 326 */         throw new IllegalStateException(e);
/* 327 */       } catch (IOException impossible) {
/* 328 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int readUnsignedByte() {
/*     */       try {
/* 334 */         return this.input.readUnsignedByte();
/* 335 */       } catch (IOException e) {
/* 336 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public short readShort() {
/*     */       try {
/* 342 */         return this.input.readShort();
/* 343 */       } catch (IOException e) {
/* 344 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int readUnsignedShort() {
/*     */       try {
/* 350 */         return this.input.readUnsignedShort();
/* 351 */       } catch (IOException e) {
/* 352 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public char readChar() {
/*     */       try {
/* 358 */         return this.input.readChar();
/* 359 */       } catch (IOException e) {
/* 360 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int readInt() {
/*     */       try {
/* 366 */         return this.input.readInt();
/* 367 */       } catch (IOException e) {
/* 368 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long readLong() {
/*     */       try {
/* 374 */         return this.input.readLong();
/* 375 */       } catch (IOException e) {
/* 376 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public float readFloat() {
/*     */       try {
/* 382 */         return this.input.readFloat();
/* 383 */       } catch (IOException e) {
/* 384 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double readDouble() {
/*     */       try {
/* 390 */         return this.input.readDouble();
/* 391 */       } catch (IOException e) {
/* 392 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String readLine() {
/*     */       try {
/* 398 */         return this.input.readLine();
/* 399 */       } catch (IOException e) {
/* 400 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String readUTF() {
/*     */       try {
/* 406 */         return this.input.readUTF();
/* 407 */       } catch (IOException e) {
/* 408 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteArrayDataOutput newDataOutput() {
/* 417 */     return new ByteArrayDataOutputStream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteArrayDataOutput newDataOutput(int size) {
/* 427 */     Preconditions.checkArgument((size >= 0), "Invalid size: %s", new Object[] { Integer.valueOf(size) });
/* 428 */     return new ByteArrayDataOutputStream(size);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ByteArrayDataOutputStream
/*     */     implements ByteArrayDataOutput
/*     */   {
/*     */     final DataOutput output;
/*     */     final ByteArrayOutputStream byteArrayOutputSteam;
/*     */     
/*     */     ByteArrayDataOutputStream() {
/* 439 */       this(new ByteArrayOutputStream());
/*     */     }
/*     */     
/*     */     ByteArrayDataOutputStream(int size) {
/* 443 */       this(new ByteArrayOutputStream(size));
/*     */     }
/*     */     
/*     */     ByteArrayDataOutputStream(ByteArrayOutputStream byteArrayOutputSteam) {
/* 447 */       this.byteArrayOutputSteam = byteArrayOutputSteam;
/* 448 */       this.output = new DataOutputStream(byteArrayOutputSteam);
/*     */     }
/*     */     
/*     */     public void write(int b) {
/*     */       try {
/* 453 */         this.output.write(b);
/* 454 */       } catch (IOException impossible) {
/* 455 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void write(byte[] b) {
/*     */       try {
/* 461 */         this.output.write(b);
/* 462 */       } catch (IOException impossible) {
/* 463 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void write(byte[] b, int off, int len) {
/*     */       try {
/* 469 */         this.output.write(b, off, len);
/* 470 */       } catch (IOException impossible) {
/* 471 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeBoolean(boolean v) {
/*     */       try {
/* 477 */         this.output.writeBoolean(v);
/* 478 */       } catch (IOException impossible) {
/* 479 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeByte(int v) {
/*     */       try {
/* 485 */         this.output.writeByte(v);
/* 486 */       } catch (IOException impossible) {
/* 487 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeBytes(String s) {
/*     */       try {
/* 493 */         this.output.writeBytes(s);
/* 494 */       } catch (IOException impossible) {
/* 495 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeChar(int v) {
/*     */       try {
/* 501 */         this.output.writeChar(v);
/* 502 */       } catch (IOException impossible) {
/* 503 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeChars(String s) {
/*     */       try {
/* 509 */         this.output.writeChars(s);
/* 510 */       } catch (IOException impossible) {
/* 511 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeDouble(double v) {
/*     */       try {
/* 517 */         this.output.writeDouble(v);
/* 518 */       } catch (IOException impossible) {
/* 519 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeFloat(float v) {
/*     */       try {
/* 525 */         this.output.writeFloat(v);
/* 526 */       } catch (IOException impossible) {
/* 527 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeInt(int v) {
/*     */       try {
/* 533 */         this.output.writeInt(v);
/* 534 */       } catch (IOException impossible) {
/* 535 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeLong(long v) {
/*     */       try {
/* 541 */         this.output.writeLong(v);
/* 542 */       } catch (IOException impossible) {
/* 543 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeShort(int v) {
/*     */       try {
/* 549 */         this.output.writeShort(v);
/* 550 */       } catch (IOException impossible) {
/* 551 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void writeUTF(String s) {
/*     */       try {
/* 557 */         this.output.writeUTF(s);
/* 558 */       } catch (IOException impossible) {
/* 559 */         throw new AssertionError(impossible);
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte[] toByteArray() {
/* 564 */       return this.byteArrayOutputSteam.toByteArray();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long length(InputSupplier<? extends InputStream> supplier) throws IOException {
/* 573 */     long count = 0L;
/* 574 */     boolean threw = true;
/* 575 */     InputStream in = supplier.getInput();
/*     */     
/*     */     try {
/*     */       while (true) {
/* 579 */         long amt = in.skip(2147483647L);
/* 580 */         if (amt == 0L) {
/* 581 */           if (in.read() == -1) {
/* 582 */             threw = false;
/* 583 */             return count;
/*     */           } 
/* 585 */           count++; continue;
/*     */         } 
/* 587 */         count += amt;
/*     */       } 
/*     */     } finally {
/*     */       
/* 591 */       Closeables.close(in, threw);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equal(InputSupplier<? extends InputStream> supplier1, InputSupplier<? extends InputStream> supplier2) throws IOException {
/* 602 */     byte[] buf1 = new byte[4096];
/* 603 */     byte[] buf2 = new byte[4096];
/*     */     
/* 605 */     boolean threw = true;
/* 606 */     InputStream in1 = supplier1.getInput();
/*     */     try {
/* 608 */       InputStream in2 = supplier2.getInput();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 625 */       Closeables.close(in1, threw);
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
/*     */   public static void readFully(InputStream in, byte[] b) throws IOException {
/* 641 */     readFully(in, b, 0, b.length);
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
/*     */ 
/*     */   
/*     */   public static void readFully(InputStream in, byte[] b, int off, int len) throws IOException {
/* 660 */     if (read(in, b, off, len) != len) {
/* 661 */       throw new EOFException();
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
/*     */   public static void skipFully(InputStream in, long n) throws IOException {
/* 678 */     while (n > 0L) {
/* 679 */       long amt = in.skip(n);
/* 680 */       if (amt == 0L) {
/*     */         
/* 682 */         if (in.read() == -1) {
/* 683 */           throw new EOFException();
/*     */         }
/* 685 */         n--; continue;
/*     */       } 
/* 687 */       n -= amt;
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
/*     */   public static <T> T readBytes(InputSupplier<? extends InputStream> supplier, ByteProcessor<T> processor) throws IOException {
/* 702 */     byte[] buf = new byte[4096];
/* 703 */     boolean threw = true;
/* 704 */     InputStream in = supplier.getInput();
/*     */     try {
/*     */       int amt;
/*     */       do {
/* 708 */         amt = in.read(buf);
/* 709 */         if (amt == -1) {
/* 710 */           threw = false;
/*     */           break;
/*     */         } 
/* 713 */       } while (processor.processBytes(buf, 0, amt));
/* 714 */       return processor.getResult();
/*     */     } finally {
/* 716 */       Closeables.close(in, threw);
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
/*     */   public static long getChecksum(InputSupplier<? extends InputStream> supplier, final Checksum checksum) throws IOException {
/* 732 */     return ((Long)readBytes(supplier, new ByteProcessor<Long>()
/*     */         {
/*     */           public boolean processBytes(byte[] buf, int off, int len) {
/* 735 */             checksum.update(buf, off, len);
/* 736 */             return true;
/*     */           }
/*     */ 
/*     */           
/*     */           public Long getResult() {
/* 741 */             long result = checksum.getValue();
/* 742 */             checksum.reset();
/* 743 */             return Long.valueOf(result);
/*     */           }
/*     */         })).longValue();
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
/*     */   public static byte[] getDigest(InputSupplier<? extends InputStream> supplier, final MessageDigest md) throws IOException {
/* 760 */     return readBytes(supplier, (ByteProcessor)new ByteProcessor<byte[]>()
/*     */         {
/*     */           public boolean processBytes(byte[] buf, int off, int len) {
/* 763 */             md.update(buf, off, len);
/* 764 */             return true;
/*     */           }
/*     */ 
/*     */           
/*     */           public byte[] getResult() {
/* 769 */             return md.digest();
/*     */           }
/*     */         });
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
/*     */   public static int read(InputStream in, byte[] b, int off, int len) throws IOException {
/* 800 */     if (len < 0) {
/* 801 */       throw new IndexOutOfBoundsException("len is negative");
/*     */     }
/* 803 */     int total = 0;
/* 804 */     while (total < len) {
/* 805 */       int result = in.read(b, off + total, len - total);
/* 806 */       if (result == -1) {
/*     */         break;
/*     */       }
/* 809 */       total += result;
/*     */     } 
/* 811 */     return total;
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
/*     */   
/*     */   public static InputSupplier<InputStream> slice(final InputSupplier<? extends InputStream> supplier, final long offset, final long length) {
/* 829 */     Preconditions.checkNotNull(supplier);
/* 830 */     Preconditions.checkArgument((offset >= 0L), "offset is negative");
/* 831 */     Preconditions.checkArgument((length >= 0L), "length is negative");
/* 832 */     return new InputSupplier<InputStream>() {
/*     */         public InputStream getInput() throws IOException {
/* 834 */           InputStream in = supplier.getInput();
/* 835 */           if (offset > 0L) {
/*     */             try {
/* 837 */               ByteStreams.skipFully(in, offset);
/* 838 */             } catch (IOException e) {
/* 839 */               Closeables.closeQuietly(in);
/* 840 */               throw e;
/*     */             } 
/*     */           }
/* 843 */           return new LimitInputStream(in, length);
/*     */         }
/*     */       };
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputSupplier<InputStream> join(final Iterable<? extends InputSupplier<? extends InputStream>> suppliers) {
/* 865 */     return new InputSupplier<InputStream>() {
/*     */         public InputStream getInput() throws IOException {
/* 867 */           return new MultiInputStream(suppliers.iterator());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputSupplier<InputStream> join(InputSupplier<? extends InputStream>... suppliers) {
/* 875 */     return join(Arrays.asList(suppliers));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\ByteStreams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */