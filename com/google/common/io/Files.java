/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.MappedByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public final class Files
/*     */ {
/*     */   private static final int TEMP_DIR_ATTEMPTS = 10000;
/*     */   
/*     */   public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {
/*  69 */     return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
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
/*     */   public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {
/*  83 */     return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
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
/*     */   public static InputSupplier<FileInputStream> newInputStreamSupplier(final File file) {
/*  96 */     Preconditions.checkNotNull(file);
/*  97 */     return new InputSupplier<FileInputStream>()
/*     */       {
/*     */         public FileInputStream getInput() throws IOException {
/* 100 */           return new FileInputStream(file);
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
/*     */   public static OutputSupplier<FileOutputStream> newOutputStreamSupplier(File file) {
/* 114 */     return newOutputStreamSupplier(file, false);
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
/*     */   public static OutputSupplier<FileOutputStream> newOutputStreamSupplier(final File file, final boolean append) {
/* 128 */     Preconditions.checkNotNull(file);
/* 129 */     return new OutputSupplier<FileOutputStream>()
/*     */       {
/*     */         public FileOutputStream getOutput() throws IOException {
/* 132 */           return new FileOutputStream(file, append);
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
/*     */   public static InputSupplier<InputStreamReader> newReaderSupplier(File file, Charset charset) {
/* 147 */     return CharStreams.newReaderSupplier((InputSupplier)newInputStreamSupplier(file), charset);
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
/*     */   public static OutputSupplier<OutputStreamWriter> newWriterSupplier(File file, Charset charset) {
/* 160 */     return newWriterSupplier(file, charset, false);
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
/*     */   public static OutputSupplier<OutputStreamWriter> newWriterSupplier(File file, Charset charset, boolean append) {
/* 175 */     return CharStreams.newWriterSupplier((OutputSupplier)newOutputStreamSupplier(file, append), charset);
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
/*     */   public static byte[] toByteArray(File file) throws IOException {
/* 189 */     Preconditions.checkArgument((file.length() <= 2147483647L));
/* 190 */     if (file.length() == 0L)
/*     */     {
/* 192 */       return ByteStreams.toByteArray((InputSupplier)newInputStreamSupplier(file));
/*     */     }
/*     */     
/* 195 */     byte[] b = new byte[(int)file.length()];
/* 196 */     boolean threw = true;
/* 197 */     InputStream in = new FileInputStream(file);
/*     */     try {
/* 199 */       ByteStreams.readFully(in, b);
/* 200 */       threw = false;
/*     */     } finally {
/* 202 */       Closeables.close(in, threw);
/*     */     } 
/* 204 */     return b;
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
/*     */   public static String toString(File file, Charset charset) throws IOException {
/* 218 */     return new String(toByteArray(file), charset.name());
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
/*     */   public static void copy(InputSupplier<? extends InputStream> from, File to) throws IOException {
/* 231 */     ByteStreams.copy(from, (OutputSupplier)newOutputStreamSupplier(to));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(byte[] from, File to) throws IOException {
/* 242 */     ByteStreams.write(from, (OutputSupplier)newOutputStreamSupplier(to));
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
/*     */   public static void copy(File from, OutputSupplier<? extends OutputStream> to) throws IOException {
/* 255 */     ByteStreams.copy((InputSupplier)newInputStreamSupplier(from), to);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copy(File from, OutputStream to) throws IOException {
/* 266 */     ByteStreams.copy((InputSupplier)newInputStreamSupplier(from), to);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copy(File from, File to) throws IOException {
/* 277 */     copy((InputSupplier)newInputStreamSupplier(from), to);
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
/*     */   public static <R extends Readable & java.io.Closeable> void copy(InputSupplier<R> from, File to, Charset charset) throws IOException {
/* 292 */     CharStreams.copy(from, newWriterSupplier(to, charset));
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
/*     */   public static void write(CharSequence from, File to, Charset charset) throws IOException {
/* 306 */     write(from, to, charset, false);
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
/*     */   public static void append(CharSequence from, File to, Charset charset) throws IOException {
/* 320 */     write(from, to, charset, true);
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
/*     */   private static void write(CharSequence from, File to, Charset charset, boolean append) throws IOException {
/* 335 */     CharStreams.write(from, newWriterSupplier(to, charset, append));
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
/*     */   public static <W extends Appendable & java.io.Closeable> void copy(File from, Charset charset, OutputSupplier<W> to) throws IOException {
/* 350 */     CharStreams.copy(newReaderSupplier(from, charset), to);
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
/*     */   public static void copy(File from, Charset charset, Appendable to) throws IOException {
/* 364 */     CharStreams.copy(newReaderSupplier(from, charset), to);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equal(File file1, File file2) throws IOException {
/* 373 */     if (file1 == file2 || file1.equals(file2)) {
/* 374 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 382 */     long len1 = file1.length();
/* 383 */     long len2 = file2.length();
/* 384 */     if (len1 != 0L && len2 != 0L && len1 != len2) {
/* 385 */       return false;
/*     */     }
/* 387 */     return ByteStreams.equal((InputSupplier)newInputStreamSupplier(file1), (InputSupplier)newInputStreamSupplier(file2));
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
/*     */   public static File createTempDir() {
/* 411 */     File baseDir = new File(System.getProperty("java.io.tmpdir"));
/* 412 */     String baseName = System.currentTimeMillis() + "-";
/*     */     
/* 414 */     for (int counter = 0; counter < 10000; counter++) {
/* 415 */       File tempDir = new File(baseDir, baseName + counter);
/* 416 */       if (tempDir.mkdir()) {
/* 417 */         return tempDir;
/*     */       }
/*     */     } 
/* 420 */     throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + baseName + "0 to " + baseName + '✏' + ')');
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
/*     */   public static void touch(File file) throws IOException {
/* 433 */     if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis()))
/*     */     {
/* 435 */       throw new IOException("Unable to update modification time of " + file);
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
/*     */   public static void createParentDirs(File file) throws IOException {
/* 450 */     File parent = file.getCanonicalFile().getParentFile();
/* 451 */     if (parent == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 461 */     parent.mkdirs();
/* 462 */     if (!parent.isDirectory()) {
/* 463 */       throw new IOException("Unable to create parent directories of " + file);
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
/*     */   public static void move(File from, File to) throws IOException {
/* 476 */     Preconditions.checkNotNull(to);
/* 477 */     Preconditions.checkArgument(!from.equals(to), "Source %s and destination %s must be different", new Object[] { from, to });
/*     */ 
/*     */     
/* 480 */     if (!from.renameTo(to)) {
/* 481 */       copy(from, to);
/* 482 */       if (!from.delete()) {
/* 483 */         if (!to.delete()) {
/* 484 */           throw new IOException("Unable to delete " + to);
/*     */         }
/* 486 */         throw new IOException("Unable to delete " + from);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void deleteDirectoryContents(File directory) throws IOException {
/* 512 */     Preconditions.checkArgument(directory.isDirectory(), "Not a directory: %s", new Object[] { directory });
/*     */ 
/*     */     
/* 515 */     if (!directory.getCanonicalPath().equals(directory.getAbsolutePath())) {
/*     */       return;
/*     */     }
/* 518 */     File[] files = directory.listFiles();
/* 519 */     if (files == null) {
/* 520 */       throw new IOException("Error listing files for " + directory);
/*     */     }
/* 522 */     for (File file : files) {
/* 523 */       deleteRecursively(file);
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
/*     */   
/*     */   @Deprecated
/*     */   public static void deleteRecursively(File file) throws IOException {
/* 545 */     if (file.isDirectory()) {
/* 546 */       deleteDirectoryContents(file);
/*     */     }
/* 548 */     if (!file.delete()) {
/* 549 */       throw new IOException("Failed to delete " + file);
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
/*     */   public static String readFirstLine(File file, Charset charset) throws IOException {
/* 565 */     return CharStreams.readFirstLine(newReaderSupplier(file, charset));
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
/*     */   public static List<String> readLines(File file, Charset charset) throws IOException {
/* 580 */     return CharStreams.readLines(newReaderSupplier(file, charset));
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
/*     */   public static <T> T readLines(File file, Charset charset, LineProcessor<T> callback) throws IOException {
/* 595 */     return CharStreams.readLines(newReaderSupplier(file, charset), callback);
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
/*     */   public static <T> T readBytes(File file, ByteProcessor<T> processor) throws IOException {
/* 612 */     return ByteStreams.readBytes((InputSupplier)newInputStreamSupplier(file), processor);
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
/*     */   public static long getChecksum(File file, Checksum checksum) throws IOException {
/* 627 */     return ByteStreams.getChecksum((InputSupplier)newInputStreamSupplier(file), checksum);
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
/*     */   public static byte[] getDigest(File file, MessageDigest md) throws IOException {
/* 642 */     return ByteStreams.getDigest((InputSupplier)newInputStreamSupplier(file), md);
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
/*     */   public static MappedByteBuffer map(File file) throws IOException {
/* 662 */     return map(file, FileChannel.MapMode.READ_ONLY);
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
/*     */   public static MappedByteBuffer map(File file, FileChannel.MapMode mode) throws IOException {
/* 685 */     if (!file.exists()) {
/* 686 */       throw new FileNotFoundException(file.toString());
/*     */     }
/* 688 */     return map(file, mode, file.length());
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
/*     */   public static MappedByteBuffer map(File file, FileChannel.MapMode mode, long size) throws FileNotFoundException, IOException {
/* 714 */     RandomAccessFile raf = new RandomAccessFile(file, (mode == FileChannel.MapMode.READ_ONLY) ? "r" : "rw");
/*     */ 
/*     */     
/* 717 */     boolean threw = true;
/*     */     try {
/* 719 */       MappedByteBuffer mbb = map(raf, mode, size);
/* 720 */       threw = false;
/* 721 */       return mbb;
/*     */     } finally {
/* 723 */       Closeables.close(raf, threw);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static MappedByteBuffer map(RandomAccessFile raf, FileChannel.MapMode mode, long size) throws IOException {
/* 729 */     FileChannel channel = raf.getChannel();
/*     */     
/* 731 */     boolean threw = true;
/*     */     try {
/* 733 */       MappedByteBuffer mbb = channel.map(mode, 0L, size);
/* 734 */       threw = false;
/* 735 */       return mbb;
/*     */     } finally {
/* 737 */       Closeables.close(channel, threw);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean sep(char[] a, int pos) {
/* 742 */     return (pos >= a.length || a[pos] == '/');
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\Files.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */