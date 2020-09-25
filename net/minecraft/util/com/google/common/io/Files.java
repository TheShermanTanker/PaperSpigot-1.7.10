/*      */ package net.minecraft.util.com.google.common.io;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.RandomAccessFile;
/*      */ import java.nio.MappedByteBuffer;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import net.minecraft.util.com.google.common.annotations.Beta;
/*      */ import net.minecraft.util.com.google.common.base.Joiner;
/*      */ import net.minecraft.util.com.google.common.base.Preconditions;
/*      */ import net.minecraft.util.com.google.common.base.Predicate;
/*      */ import net.minecraft.util.com.google.common.base.Splitter;
/*      */ import net.minecraft.util.com.google.common.collect.ImmutableSet;
/*      */ import net.minecraft.util.com.google.common.collect.Lists;
/*      */ import net.minecraft.util.com.google.common.collect.TreeTraverser;
/*      */ import net.minecraft.util.com.google.common.hash.HashCode;
/*      */ import net.minecraft.util.com.google.common.hash.HashFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Beta
/*      */ public final class Files
/*      */ {
/*      */   private static final int TEMP_DIR_ATTEMPTS = 10000;
/*      */   
/*      */   public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {
/*   84 */     Preconditions.checkNotNull(file);
/*   85 */     Preconditions.checkNotNull(charset);
/*   86 */     return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {
/*  101 */     Preconditions.checkNotNull(file);
/*  102 */     Preconditions.checkNotNull(charset);
/*  103 */     return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteSource asByteSource(File file) {
/*  113 */     return new FileByteSource(file);
/*      */   }
/*      */   
/*      */   private static final class FileByteSource
/*      */     extends ByteSource {
/*      */     private final File file;
/*      */     
/*      */     private FileByteSource(File file) {
/*  121 */       this.file = (File)Preconditions.checkNotNull(file);
/*      */     }
/*      */ 
/*      */     
/*      */     public FileInputStream openStream() throws IOException {
/*  126 */       return new FileInputStream(this.file);
/*      */     }
/*      */ 
/*      */     
/*      */     public long size() throws IOException {
/*  131 */       if (!this.file.isFile()) {
/*  132 */         throw new FileNotFoundException(this.file.toString());
/*      */       }
/*  134 */       return this.file.length();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] read() throws IOException {
/*  139 */       Closer closer = Closer.create();
/*      */       try {
/*  141 */         FileInputStream in = closer.<FileInputStream>register(openStream());
/*  142 */         return Files.readFile(in, in.getChannel().size());
/*  143 */       } catch (Throwable e) {
/*  144 */         throw closer.rethrow(e);
/*      */       } finally {
/*  146 */         closer.close();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  152 */       return "Files.asByteSource(" + this.file + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static byte[] readFile(InputStream in, long expectedSize) throws IOException {
/*  164 */     if (expectedSize > 2147483647L) {
/*  165 */       throw new OutOfMemoryError("file is too large to fit in a byte array: " + expectedSize + " bytes");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  171 */     return (expectedSize == 0L) ? ByteStreams.toByteArray(in) : ByteStreams.toByteArray(in, (int)expectedSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteSink asByteSink(File file, FileWriteMode... modes) {
/*  186 */     return new FileByteSink(file, modes);
/*      */   }
/*      */   
/*      */   private static final class FileByteSink
/*      */     extends ByteSink {
/*      */     private final File file;
/*      */     private final ImmutableSet<FileWriteMode> modes;
/*      */     
/*      */     private FileByteSink(File file, FileWriteMode... modes) {
/*  195 */       this.file = (File)Preconditions.checkNotNull(file);
/*  196 */       this.modes = ImmutableSet.copyOf((Object[])modes);
/*      */     }
/*      */ 
/*      */     
/*      */     public FileOutputStream openStream() throws IOException {
/*  201 */       return new FileOutputStream(this.file, this.modes.contains(FileWriteMode.APPEND));
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  206 */       return "Files.asByteSink(" + this.file + ", " + this.modes + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharSource asCharSource(File file, Charset charset) {
/*  217 */     return asByteSource(file).asCharSource(charset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharSink asCharSink(File file, Charset charset, FileWriteMode... modes) {
/*  232 */     return asByteSink(file, modes).asCharSink(charset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static InputSupplier<FileInputStream> newInputStreamSupplier(File file) {
/*  247 */     return ByteStreams.asInputSupplier(asByteSource(file));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static OutputSupplier<FileOutputStream> newOutputStreamSupplier(File file) {
/*  262 */     return newOutputStreamSupplier(file, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static OutputSupplier<FileOutputStream> newOutputStreamSupplier(File file, boolean append) {
/*  280 */     return ByteStreams.asOutputSupplier(asByteSink(file, modes(append)));
/*      */   }
/*      */   
/*      */   private static FileWriteMode[] modes(boolean append) {
/*  284 */     (new FileWriteMode[1])[0] = FileWriteMode.APPEND; return append ? new FileWriteMode[1] : new FileWriteMode[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static InputSupplier<InputStreamReader> newReaderSupplier(File file, Charset charset) {
/*  303 */     return CharStreams.asInputSupplier(asCharSource(file, charset));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static OutputSupplier<OutputStreamWriter> newWriterSupplier(File file, Charset charset) {
/*  320 */     return newWriterSupplier(file, charset, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static OutputSupplier<OutputStreamWriter> newWriterSupplier(File file, Charset charset, boolean append) {
/*  340 */     return CharStreams.asOutputSupplier(asCharSink(file, charset, modes(append)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] toByteArray(File file) throws IOException {
/*  353 */     return asByteSource(file).read();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(File file, Charset charset) throws IOException {
/*  367 */     return asCharSource(file, charset).read();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void copy(InputSupplier<? extends InputStream> from, File to) throws IOException {
/*  384 */     ByteStreams.asByteSource(from).copyTo(asByteSink(to, new FileWriteMode[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void write(byte[] from, File to) throws IOException {
/*  395 */     asByteSink(to, new FileWriteMode[0]).write(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void copy(File from, OutputSupplier<? extends OutputStream> to) throws IOException {
/*  412 */     asByteSource(from).copyTo(ByteStreams.asByteSink(to));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(File from, OutputStream to) throws IOException {
/*  423 */     asByteSource(from).copyTo(to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(File from, File to) throws IOException {
/*  440 */     Preconditions.checkArgument(!from.equals(to), "Source %s and destination %s must be different", new Object[] { from, to });
/*      */     
/*  442 */     asByteSource(from).copyTo(asByteSink(to, new FileWriteMode[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <R extends Readable & java.io.Closeable> void copy(InputSupplier<R> from, File to, Charset charset) throws IOException {
/*  462 */     CharStreams.asCharSource(from).copyTo(asCharSink(to, charset, new FileWriteMode[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void write(CharSequence from, File to, Charset charset) throws IOException {
/*  477 */     asCharSink(to, charset, new FileWriteMode[0]).write(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void append(CharSequence from, File to, Charset charset) throws IOException {
/*  492 */     write(from, to, charset, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void write(CharSequence from, File to, Charset charset, boolean append) throws IOException {
/*  508 */     asCharSink(to, charset, modes(append)).write(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <W extends Appendable & java.io.Closeable> void copy(File from, Charset charset, OutputSupplier<W> to) throws IOException {
/*  528 */     asCharSource(from, charset).copyTo(CharStreams.asCharSink(to));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(File from, Charset charset, Appendable to) throws IOException {
/*  543 */     asCharSource(from, charset).copyTo(to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equal(File file1, File file2) throws IOException {
/*  552 */     Preconditions.checkNotNull(file1);
/*  553 */     Preconditions.checkNotNull(file2);
/*  554 */     if (file1 == file2 || file1.equals(file2)) {
/*  555 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  563 */     long len1 = file1.length();
/*  564 */     long len2 = file2.length();
/*  565 */     if (len1 != 0L && len2 != 0L && len1 != len2) {
/*  566 */       return false;
/*      */     }
/*  568 */     return asByteSource(file1).contentEquals(asByteSource(file2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static File createTempDir() {
/*  591 */     File baseDir = new File(System.getProperty("java.io.tmpdir"));
/*  592 */     String baseName = System.currentTimeMillis() + "-";
/*      */     
/*  594 */     for (int counter = 0; counter < 10000; counter++) {
/*  595 */       File tempDir = new File(baseDir, baseName + counter);
/*  596 */       if (tempDir.mkdir()) {
/*  597 */         return tempDir;
/*      */       }
/*      */     } 
/*  600 */     throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + baseName + "0 to " + baseName + '✏' + ')');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void touch(File file) throws IOException {
/*  613 */     Preconditions.checkNotNull(file);
/*  614 */     if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis()))
/*      */     {
/*  616 */       throw new IOException("Unable to update modification time of " + file);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void createParentDirs(File file) throws IOException {
/*  631 */     Preconditions.checkNotNull(file);
/*  632 */     File parent = file.getCanonicalFile().getParentFile();
/*  633 */     if (parent == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  643 */     parent.mkdirs();
/*  644 */     if (!parent.isDirectory()) {
/*  645 */       throw new IOException("Unable to create parent directories of " + file);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void move(File from, File to) throws IOException {
/*  659 */     Preconditions.checkNotNull(from);
/*  660 */     Preconditions.checkNotNull(to);
/*  661 */     Preconditions.checkArgument(!from.equals(to), "Source %s and destination %s must be different", new Object[] { from, to });
/*      */ 
/*      */     
/*  664 */     if (!from.renameTo(to)) {
/*  665 */       copy(from, to);
/*  666 */       if (!from.delete()) {
/*  667 */         if (!to.delete()) {
/*  668 */           throw new IOException("Unable to delete " + to);
/*      */         }
/*  670 */         throw new IOException("Unable to delete " + from);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String readFirstLine(File file, Charset charset) throws IOException {
/*  688 */     return asCharSource(file, charset).readFirstLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<String> readLines(File file, Charset charset) throws IOException {
/*  710 */     return readLines(file, charset, new LineProcessor<List<String>>() {
/*  711 */           final List<String> result = Lists.newArrayList();
/*      */ 
/*      */           
/*      */           public boolean processLine(String line) {
/*  715 */             this.result.add(line);
/*  716 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public List<String> getResult() {
/*  721 */             return this.result;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T readLines(File file, Charset charset, LineProcessor<T> callback) throws IOException {
/*  739 */     return CharStreams.readLines(newReaderSupplier(file, charset), callback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> T readBytes(File file, ByteProcessor<T> processor) throws IOException {
/*  755 */     return ByteStreams.readBytes((InputSupplier)newInputStreamSupplier(file), processor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static HashCode hash(File file, HashFunction hashFunction) throws IOException {
/*  769 */     return asByteSource(file).hash(hashFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MappedByteBuffer map(File file) throws IOException {
/*  789 */     Preconditions.checkNotNull(file);
/*  790 */     return map(file, FileChannel.MapMode.READ_ONLY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MappedByteBuffer map(File file, FileChannel.MapMode mode) throws IOException {
/*  813 */     Preconditions.checkNotNull(file);
/*  814 */     Preconditions.checkNotNull(mode);
/*  815 */     if (!file.exists()) {
/*  816 */       throw new FileNotFoundException(file.toString());
/*      */     }
/*  818 */     return map(file, mode, file.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MappedByteBuffer map(File file, FileChannel.MapMode mode, long size) throws FileNotFoundException, IOException {
/*  844 */     Preconditions.checkNotNull(file);
/*  845 */     Preconditions.checkNotNull(mode);
/*      */     
/*  847 */     Closer closer = Closer.create();
/*      */     try {
/*  849 */       RandomAccessFile raf = closer.<RandomAccessFile>register(new RandomAccessFile(file, (mode == FileChannel.MapMode.READ_ONLY) ? "r" : "rw"));
/*      */       
/*  851 */       return map(raf, mode, size);
/*  852 */     } catch (Throwable e) {
/*  853 */       throw closer.rethrow(e);
/*      */     } finally {
/*  855 */       closer.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static MappedByteBuffer map(RandomAccessFile raf, FileChannel.MapMode mode, long size) throws IOException {
/*  861 */     Closer closer = Closer.create();
/*      */     try {
/*  863 */       FileChannel channel = closer.<FileChannel>register(raf.getChannel());
/*  864 */       return channel.map(mode, 0L, size);
/*  865 */     } catch (Throwable e) {
/*  866 */       throw closer.rethrow(e);
/*      */     } finally {
/*  868 */       closer.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String simplifyPath(String pathname) {
/*  894 */     Preconditions.checkNotNull(pathname);
/*  895 */     if (pathname.length() == 0) {
/*  896 */       return ".";
/*      */     }
/*      */ 
/*      */     
/*  900 */     Iterable<String> components = Splitter.on('/').omitEmptyStrings().split(pathname);
/*      */     
/*  902 */     List<String> path = new ArrayList<String>();
/*      */ 
/*      */     
/*  905 */     for (String component : components) {
/*  906 */       if (component.equals("."))
/*      */         continue; 
/*  908 */       if (component.equals("..")) {
/*  909 */         if (path.size() > 0 && !((String)path.get(path.size() - 1)).equals("..")) {
/*  910 */           path.remove(path.size() - 1); continue;
/*      */         } 
/*  912 */         path.add("..");
/*      */         continue;
/*      */       } 
/*  915 */       path.add(component);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  920 */     String result = Joiner.on('/').join(path);
/*  921 */     if (pathname.charAt(0) == '/') {
/*  922 */       result = "/" + result;
/*      */     }
/*      */     
/*  925 */     while (result.startsWith("/../")) {
/*  926 */       result = result.substring(3);
/*      */     }
/*  928 */     if (result.equals("/..")) {
/*  929 */       result = "/";
/*  930 */     } else if ("".equals(result)) {
/*  931 */       result = ".";
/*      */     } 
/*      */     
/*  934 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFileExtension(String fullName) {
/*  945 */     Preconditions.checkNotNull(fullName);
/*  946 */     String fileName = (new File(fullName)).getName();
/*  947 */     int dotIndex = fileName.lastIndexOf('.');
/*  948 */     return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getNameWithoutExtension(String file) {
/*  962 */     Preconditions.checkNotNull(file);
/*  963 */     String fileName = (new File(file)).getName();
/*  964 */     int dotIndex = fileName.lastIndexOf('.');
/*  965 */     return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TreeTraverser<File> fileTreeTraverser() {
/*  979 */     return FILE_TREE_TRAVERSER;
/*      */   }
/*      */   
/*  982 */   private static final TreeTraverser<File> FILE_TREE_TRAVERSER = new TreeTraverser<File>()
/*      */     {
/*      */       public Iterable<File> children(File file)
/*      */       {
/*  986 */         if (file.isDirectory()) {
/*  987 */           File[] files = file.listFiles();
/*  988 */           if (files != null) {
/*  989 */             return Collections.unmodifiableList(Arrays.asList(files));
/*      */           }
/*      */         } 
/*      */         
/*  993 */         return Collections.emptyList();
/*      */       }
/*      */ 
/*      */       
/*      */       public String toString() {
/*  998 */         return "Files.fileTreeTraverser()";
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Predicate<File> isDirectory() {
/* 1008 */     return FilePredicate.IS_DIRECTORY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Predicate<File> isFile() {
/* 1017 */     return FilePredicate.IS_FILE;
/*      */   }
/*      */   
/*      */   private enum FilePredicate implements Predicate<File> {
/* 1021 */     IS_DIRECTORY
/*      */     {
/*      */       public boolean apply(File file) {
/* 1024 */         return file.isDirectory();
/*      */       }
/*      */ 
/*      */       
/*      */       public String toString() {
/* 1029 */         return "Files.isDirectory()";
/*      */       }
/*      */     },
/*      */     
/* 1033 */     IS_FILE
/*      */     {
/*      */       public boolean apply(File file) {
/* 1036 */         return file.isFile();
/*      */       }
/*      */ 
/*      */       
/*      */       public String toString() {
/* 1041 */         return "Files.isFile()";
/*      */       }
/*      */     };
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\io\Files.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */