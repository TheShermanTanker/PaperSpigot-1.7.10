/*     */ package net.minecraft.util.com.google.common.io;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableList;
/*     */ import net.minecraft.util.com.google.common.hash.Funnels;
/*     */ import net.minecraft.util.com.google.common.hash.HashCode;
/*     */ import net.minecraft.util.com.google.common.hash.HashFunction;
/*     */ import net.minecraft.util.com.google.common.hash.Hasher;
/*     */ import net.minecraft.util.com.google.common.hash.PrimitiveSink;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ByteSource
/*     */   implements InputSupplier<InputStream>
/*     */ {
/*     */   private static final int BUF_SIZE = 4096;
/*     */   
/*     */   public CharSource asCharSource(Charset charset) {
/*  72 */     return new AsCharSource(charset);
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
/*     */   @Deprecated
/*     */   public final InputStream getInput() throws IOException {
/*  97 */     return openStream();
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
/*     */   public InputStream openBufferedStream() throws IOException {
/* 113 */     InputStream in = openStream();
/* 114 */     return (in instanceof BufferedInputStream) ? in : new BufferedInputStream(in);
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
/*     */   public ByteSource slice(long offset, long length) {
/* 126 */     return new SlicedByteSource(offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() throws IOException {
/* 137 */     Closer closer = Closer.create();
/*     */     try {
/* 139 */       InputStream in = closer.<InputStream>register(openStream());
/* 140 */       return (in.read() == -1);
/* 141 */     } catch (Throwable e) {
/* 142 */       throw closer.rethrow(e);
/*     */     } finally {
/* 144 */       closer.close();
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
/*     */ 
/*     */   
/*     */   public long size() throws IOException {
/* 164 */     Closer closer = Closer.create();
/*     */     try {
/* 166 */       InputStream in = closer.<InputStream>register(openStream());
/* 167 */       return countBySkipping(in);
/* 168 */     } catch (IOException e) {
/*     */     
/*     */     } finally {
/* 171 */       closer.close();
/*     */     } 
/*     */     
/* 174 */     closer = Closer.create();
/*     */     try {
/* 176 */       InputStream in = closer.<InputStream>register(openStream());
/* 177 */       return countByReading(in);
/* 178 */     } catch (Throwable e) {
/* 179 */       throw closer.rethrow(e);
/*     */     } finally {
/* 181 */       closer.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long countBySkipping(InputStream in) throws IOException {
/* 190 */     long count = 0L;
/*     */ 
/*     */     
/*     */     while (true) {
/* 194 */       long skipped = in.skip(Math.min(in.available(), 2147483647));
/* 195 */       if (skipped <= 0L) {
/* 196 */         if (in.read() == -1)
/* 197 */           return count; 
/* 198 */         if (count == 0L && in.available() == 0)
/*     */         {
/*     */           
/* 201 */           throw new IOException();
/*     */         }
/* 203 */         count++; continue;
/*     */       } 
/* 205 */       count += skipped;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 210 */   private static final byte[] countBuffer = new byte[4096];
/*     */   
/*     */   private long countByReading(InputStream in) throws IOException {
/* 213 */     long count = 0L;
/*     */     long read;
/* 215 */     while ((read = in.read(countBuffer)) != -1L) {
/* 216 */       count += read;
/*     */     }
/* 218 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long copyTo(OutputStream output) throws IOException {
/* 229 */     Preconditions.checkNotNull(output);
/*     */     
/* 231 */     Closer closer = Closer.create();
/*     */     try {
/* 233 */       InputStream in = closer.<InputStream>register(openStream());
/* 234 */       return ByteStreams.copy(in, output);
/* 235 */     } catch (Throwable e) {
/* 236 */       throw closer.rethrow(e);
/*     */     } finally {
/* 238 */       closer.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long copyTo(ByteSink sink) throws IOException {
/* 249 */     Preconditions.checkNotNull(sink);
/*     */     
/* 251 */     Closer closer = Closer.create();
/*     */     try {
/* 253 */       InputStream in = closer.<InputStream>register(openStream());
/* 254 */       OutputStream out = closer.<OutputStream>register(sink.openStream());
/* 255 */       return ByteStreams.copy(in, out);
/* 256 */     } catch (Throwable e) {
/* 257 */       throw closer.rethrow(e);
/*     */     } finally {
/* 259 */       closer.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] read() throws IOException {
/* 269 */     Closer closer = Closer.create();
/*     */     try {
/* 271 */       InputStream in = closer.<InputStream>register(openStream());
/* 272 */       return ByteStreams.toByteArray(in);
/* 273 */     } catch (Throwable e) {
/* 274 */       throw closer.rethrow(e);
/*     */     } finally {
/* 276 */       closer.close();
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
/*     */   @Beta
/*     */   public <T> T read(ByteProcessor<T> processor) throws IOException {
/* 291 */     Preconditions.checkNotNull(processor);
/*     */     
/* 293 */     Closer closer = Closer.create();
/*     */     try {
/* 295 */       InputStream in = closer.<InputStream>register(openStream());
/* 296 */       return (T)ByteStreams.readBytes(in, (ByteProcessor)processor);
/* 297 */     } catch (Throwable e) {
/* 298 */       throw closer.rethrow(e);
/*     */     } finally {
/* 300 */       closer.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashCode hash(HashFunction hashFunction) throws IOException {
/* 310 */     Hasher hasher = hashFunction.newHasher();
/* 311 */     copyTo(Funnels.asOutputStream((PrimitiveSink)hasher));
/* 312 */     return hasher.hash();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contentEquals(ByteSource other) throws IOException {
/* 323 */     Preconditions.checkNotNull(other);
/*     */     
/* 325 */     byte[] buf1 = new byte[4096];
/* 326 */     byte[] buf2 = new byte[4096];
/*     */     
/* 328 */     Closer closer = Closer.create();
/*     */     try {
/* 330 */       InputStream in1 = closer.<InputStream>register(openStream());
/* 331 */       InputStream in2 = closer.<InputStream>register(other.openStream());
/*     */       while (true) {
/* 333 */         int read1 = ByteStreams.read(in1, buf1, 0, 4096);
/* 334 */         int read2 = ByteStreams.read(in2, buf2, 0, 4096);
/* 335 */         if (read1 != read2 || !Arrays.equals(buf1, buf2))
/* 336 */           return false; 
/* 337 */         if (read1 != 4096) {
/* 338 */           return true;
/*     */         }
/*     */       } 
/* 341 */     } catch (Throwable e) {
/* 342 */       throw closer.rethrow(e);
/*     */     } finally {
/* 344 */       closer.close();
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
/*     */   public static ByteSource concat(Iterable<? extends ByteSource> sources) {
/* 360 */     return new ConcatenatedByteSource(sources);
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
/*     */   public static ByteSource concat(Iterator<? extends ByteSource> sources) {
/* 382 */     return concat((Iterable<? extends ByteSource>)ImmutableList.copyOf(sources));
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
/*     */   public static ByteSource concat(ByteSource... sources) {
/* 398 */     return concat((Iterable<? extends ByteSource>)ImmutableList.copyOf((Object[])sources));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteSource wrap(byte[] b) {
/* 408 */     return new ByteArrayByteSource(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteSource empty() {
/* 417 */     return EmptyByteSource.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract InputStream openStream() throws IOException;
/*     */   
/*     */   private final class AsCharSource
/*     */     extends CharSource
/*     */   {
/*     */     private final Charset charset;
/*     */     
/*     */     private AsCharSource(Charset charset) {
/* 429 */       this.charset = (Charset)Preconditions.checkNotNull(charset);
/*     */     }
/*     */ 
/*     */     
/*     */     public Reader openStream() throws IOException {
/* 434 */       return new InputStreamReader(ByteSource.this.openStream(), this.charset);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 439 */       return ByteSource.this.toString() + ".asCharSource(" + this.charset + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class SlicedByteSource
/*     */     extends ByteSource
/*     */   {
/*     */     private final long offset;
/*     */     
/*     */     private final long length;
/*     */     
/*     */     private SlicedByteSource(long offset, long length) {
/* 452 */       Preconditions.checkArgument((offset >= 0L), "offset (%s) may not be negative", new Object[] { Long.valueOf(offset) });
/* 453 */       Preconditions.checkArgument((length >= 0L), "length (%s) may not be negative", new Object[] { Long.valueOf(length) });
/* 454 */       this.offset = offset;
/* 455 */       this.length = length;
/*     */     }
/*     */ 
/*     */     
/*     */     public InputStream openStream() throws IOException {
/* 460 */       return sliceStream(ByteSource.this.openStream());
/*     */     }
/*     */ 
/*     */     
/*     */     public InputStream openBufferedStream() throws IOException {
/* 465 */       return sliceStream(ByteSource.this.openBufferedStream());
/*     */     }
/*     */     
/*     */     private InputStream sliceStream(InputStream in) throws IOException {
/* 469 */       if (this.offset > 0L) {
/*     */         try {
/* 471 */           ByteStreams.skipFully(in, this.offset);
/* 472 */         } catch (Throwable e) {
/* 473 */           Closer closer = Closer.create();
/* 474 */           closer.register(in);
/*     */           try {
/* 476 */             throw closer.rethrow(e);
/*     */           } finally {
/* 478 */             closer.close();
/*     */           } 
/*     */         } 
/*     */       }
/* 482 */       return ByteStreams.limit(in, this.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSource slice(long offset, long length) {
/* 487 */       Preconditions.checkArgument((offset >= 0L), "offset (%s) may not be negative", new Object[] { Long.valueOf(offset) });
/* 488 */       Preconditions.checkArgument((length >= 0L), "length (%s) may not be negative", new Object[] { Long.valueOf(length) });
/* 489 */       long maxLength = this.length - offset;
/* 490 */       return ByteSource.this.slice(this.offset + offset, Math.min(length, maxLength));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() throws IOException {
/* 495 */       return (this.length == 0L || super.isEmpty());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 500 */       return ByteSource.this.toString() + ".slice(" + this.offset + ", " + this.length + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ByteArrayByteSource
/*     */     extends ByteSource {
/*     */     protected final byte[] bytes;
/*     */     
/*     */     protected ByteArrayByteSource(byte[] bytes) {
/* 509 */       this.bytes = (byte[])Preconditions.checkNotNull(bytes);
/*     */     }
/*     */ 
/*     */     
/*     */     public InputStream openStream() {
/* 514 */       return new ByteArrayInputStream(this.bytes);
/*     */     }
/*     */ 
/*     */     
/*     */     public InputStream openBufferedStream() throws IOException {
/* 519 */       return openStream();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 524 */       return (this.bytes.length == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public long size() {
/* 529 */       return this.bytes.length;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] read() {
/* 534 */       return (byte[])this.bytes.clone();
/*     */     }
/*     */ 
/*     */     
/*     */     public long copyTo(OutputStream output) throws IOException {
/* 539 */       output.write(this.bytes);
/* 540 */       return this.bytes.length;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T read(ByteProcessor<T> processor) throws IOException {
/* 545 */       processor.processBytes(this.bytes, 0, this.bytes.length);
/* 546 */       return processor.getResult();
/*     */     }
/*     */ 
/*     */     
/*     */     public HashCode hash(HashFunction hashFunction) throws IOException {
/* 551 */       return hashFunction.hashBytes(this.bytes);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 558 */       return "ByteSource.wrap(" + BaseEncoding.base16().encode(this.bytes) + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class EmptyByteSource
/*     */     extends ByteArrayByteSource {
/* 564 */     private static final EmptyByteSource INSTANCE = new EmptyByteSource();
/*     */     
/*     */     private EmptyByteSource() {
/* 567 */       super(new byte[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSource asCharSource(Charset charset) {
/* 572 */       Preconditions.checkNotNull(charset);
/* 573 */       return CharSource.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] read() {
/* 578 */       return this.bytes;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 583 */       return "ByteSource.empty()";
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ConcatenatedByteSource
/*     */     extends ByteSource {
/*     */     private final Iterable<? extends ByteSource> sources;
/*     */     
/*     */     ConcatenatedByteSource(Iterable<? extends ByteSource> sources) {
/* 592 */       this.sources = (Iterable<? extends ByteSource>)Preconditions.checkNotNull(sources);
/*     */     }
/*     */ 
/*     */     
/*     */     public InputStream openStream() throws IOException {
/* 597 */       return new MultiInputStream(this.sources.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() throws IOException {
/* 602 */       for (ByteSource source : this.sources) {
/* 603 */         if (!source.isEmpty()) {
/* 604 */           return false;
/*     */         }
/*     */       } 
/* 607 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public long size() throws IOException {
/* 612 */       long result = 0L;
/* 613 */       for (ByteSource source : this.sources) {
/* 614 */         result += source.size();
/*     */       }
/* 616 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 621 */       return "ByteSource.concat(" + this.sources + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\io\ByteSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */