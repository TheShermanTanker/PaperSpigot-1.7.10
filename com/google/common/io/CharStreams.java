/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Closeable;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.Writer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class CharStreams
/*     */ {
/*     */   private static final int BUF_SIZE = 2048;
/*     */   
/*     */   public static InputSupplier<StringReader> newReaderSupplier(final String value) {
/*  67 */     Preconditions.checkNotNull(value);
/*  68 */     return new InputSupplier<StringReader>()
/*     */       {
/*     */         public StringReader getInput() {
/*  71 */           return new StringReader(value);
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
/*     */   public static InputSupplier<InputStreamReader> newReaderSupplier(final InputSupplier<? extends InputStream> in, final Charset charset) {
/*  86 */     Preconditions.checkNotNull(in);
/*  87 */     Preconditions.checkNotNull(charset);
/*  88 */     return new InputSupplier<InputStreamReader>()
/*     */       {
/*     */         public InputStreamReader getInput() throws IOException {
/*  91 */           return new InputStreamReader(in.getInput(), charset);
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
/*     */   public static OutputSupplier<OutputStreamWriter> newWriterSupplier(final OutputSupplier<? extends OutputStream> out, final Charset charset) {
/* 106 */     Preconditions.checkNotNull(out);
/* 107 */     Preconditions.checkNotNull(charset);
/* 108 */     return new OutputSupplier<OutputStreamWriter>()
/*     */       {
/*     */         public OutputStreamWriter getOutput() throws IOException {
/* 111 */           return new OutputStreamWriter(out.getOutput(), charset);
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
/*     */   public static <W extends Appendable & Closeable> void write(CharSequence from, OutputSupplier<W> to) throws IOException {
/* 126 */     Preconditions.checkNotNull(from);
/* 127 */     boolean threw = true;
/* 128 */     Appendable appendable = (Appendable)to.getOutput();
/*     */     try {
/* 130 */       appendable.append(from);
/* 131 */       threw = false;
/*     */     } finally {
/* 133 */       Closeables.close((Closeable)appendable, threw);
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
/*     */   public static <R extends Readable & Closeable, W extends Appendable & Closeable> long copy(InputSupplier<R> from, OutputSupplier<W> to) throws IOException {
/* 150 */     boolean threw = true;
/* 151 */     Readable readable = (Readable)from.getInput();
/*     */     try {
/* 153 */       Appendable appendable = (Appendable)to.getOutput();
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */       
/* 162 */       Closeables.close((Closeable)readable, threw);
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
/*     */   public static <R extends Readable & Closeable> long copy(InputSupplier<R> from, Appendable to) throws IOException {
/* 178 */     boolean threw = true;
/* 179 */     Readable readable = (Readable)from.getInput();
/*     */     try {
/* 181 */       long count = copy(readable, to);
/* 182 */       threw = false;
/* 183 */       return count;
/*     */     } finally {
/* 185 */       Closeables.close((Closeable)readable, threw);
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
/*     */   public static long copy(Readable from, Appendable to) throws IOException {
/* 199 */     CharBuffer buf = CharBuffer.allocate(2048);
/* 200 */     long total = 0L;
/*     */     while (true) {
/* 202 */       int r = from.read(buf);
/* 203 */       if (r == -1) {
/*     */         break;
/*     */       }
/* 206 */       buf.flip();
/* 207 */       to.append(buf, 0, r);
/* 208 */       total += r;
/*     */     } 
/* 210 */     return total;
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
/*     */   public static String toString(Readable r) throws IOException {
/* 222 */     return toStringBuilder(r).toString();
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
/*     */   public static <R extends Readable & Closeable> String toString(InputSupplier<R> supplier) throws IOException {
/* 235 */     return toStringBuilder(supplier).toString();
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
/*     */   private static StringBuilder toStringBuilder(Readable r) throws IOException {
/* 247 */     StringBuilder sb = new StringBuilder();
/* 248 */     copy(r, sb);
/* 249 */     return sb;
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
/*     */   private static <R extends Readable & Closeable> StringBuilder toStringBuilder(InputSupplier<R> supplier) throws IOException {
/* 261 */     boolean threw = true;
/* 262 */     Readable readable = (Readable)supplier.getInput();
/*     */     try {
/* 264 */       StringBuilder result = toStringBuilder(readable);
/* 265 */       threw = false;
/* 266 */       return result;
/*     */     } finally {
/* 268 */       Closeables.close((Closeable)readable, threw);
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
/*     */   public static <R extends Readable & Closeable> String readFirstLine(InputSupplier<R> supplier) throws IOException {
/* 283 */     boolean threw = true;
/* 284 */     Readable readable = (Readable)supplier.getInput();
/*     */     try {
/* 286 */       String line = (new LineReader(readable)).readLine();
/* 287 */       threw = false;
/* 288 */       return line;
/*     */     } finally {
/* 290 */       Closeables.close((Closeable)readable, threw);
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
/*     */   public static <R extends Readable & Closeable> List<String> readLines(InputSupplier<R> supplier) throws IOException {
/* 305 */     boolean threw = true;
/* 306 */     Readable readable = (Readable)supplier.getInput();
/*     */     try {
/* 308 */       List<String> result = readLines(readable);
/* 309 */       threw = false;
/* 310 */       return result;
/*     */     } finally {
/* 312 */       Closeables.close((Closeable)readable, threw);
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
/*     */   public static List<String> readLines(Readable r) throws IOException {
/* 330 */     List<String> result = new ArrayList<String>();
/* 331 */     LineReader lineReader = new LineReader(r);
/*     */     String line;
/* 333 */     while ((line = lineReader.readLine()) != null) {
/* 334 */       result.add(line);
/*     */     }
/* 336 */     return result;
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
/*     */   public static <R extends Readable & Closeable, T> T readLines(InputSupplier<R> supplier, LineProcessor<T> callback) throws IOException {
/* 351 */     boolean threw = true;
/* 352 */     Readable readable = (Readable)supplier.getInput();
/*     */     try {
/* 354 */       LineReader lineReader = new LineReader(readable); String line; do {
/*     */       
/* 356 */       } while ((line = lineReader.readLine()) != null && 
/* 357 */         callback.processLine(line));
/*     */ 
/*     */ 
/*     */       
/* 361 */       threw = false;
/*     */     } finally {
/* 363 */       Closeables.close((Closeable)readable, threw);
/*     */     } 
/* 365 */     return callback.getResult();
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
/*     */   public static InputSupplier<Reader> join(final Iterable<? extends InputSupplier<? extends Reader>> suppliers) {
/* 385 */     return new InputSupplier<Reader>() {
/*     */         public Reader getInput() throws IOException {
/* 387 */           return new MultiReader(suppliers.iterator());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputSupplier<Reader> join(InputSupplier<? extends Reader>... suppliers) {
/* 395 */     return join(Arrays.asList(suppliers));
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
/*     */   public static void skipFully(Reader reader, long n) throws IOException {
/* 410 */     while (n > 0L) {
/* 411 */       long amt = reader.skip(n);
/* 412 */       if (amt == 0L) {
/*     */         
/* 414 */         if (reader.read() == -1) {
/* 415 */           throw new EOFException();
/*     */         }
/* 417 */         n--; continue;
/*     */       } 
/* 419 */       n -= amt;
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
/*     */   public static Writer asWriter(Appendable target) {
/* 435 */     if (target instanceof Writer) {
/* 436 */       return (Writer)target;
/*     */     }
/* 438 */     return new AppendableWriter(target);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\CharStreams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */