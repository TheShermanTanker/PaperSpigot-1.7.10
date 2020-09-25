/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
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
/*     */ @Beta
/*     */ public final class Resources
/*     */ {
/*     */   public static InputSupplier<InputStream> newInputStreamSupplier(final URL url) {
/*  56 */     Preconditions.checkNotNull(url);
/*  57 */     return new InputSupplier<InputStream>()
/*     */       {
/*     */         public InputStream getInput() throws IOException {
/*  60 */           return url.openStream();
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
/*     */   public static InputSupplier<InputStreamReader> newReaderSupplier(URL url, Charset charset) {
/*  75 */     return CharStreams.newReaderSupplier(newInputStreamSupplier(url), charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] toByteArray(URL url) throws IOException {
/*  86 */     return ByteStreams.toByteArray(newInputStreamSupplier(url));
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
/*     */   public static String toString(URL url, Charset charset) throws IOException {
/*  99 */     return CharStreams.toString(newReaderSupplier(url, charset));
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
/*     */   public static <T> T readLines(URL url, Charset charset, LineProcessor<T> callback) throws IOException {
/* 114 */     return CharStreams.readLines(newReaderSupplier(url, charset), callback);
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
/*     */   public static List<String> readLines(URL url, Charset charset) throws IOException {
/* 129 */     return CharStreams.readLines(newReaderSupplier(url, charset));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copy(URL from, OutputStream to) throws IOException {
/* 140 */     ByteStreams.copy(newInputStreamSupplier(from), to);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL getResource(String resourceName) {
/* 151 */     URL url = Resources.class.getClassLoader().getResource(resourceName);
/* 152 */     Preconditions.checkArgument((url != null), "resource %s not found.", new Object[] { resourceName });
/* 153 */     return url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL getResource(Class<?> contextClass, String resourceName) {
/* 163 */     URL url = contextClass.getResource(resourceName);
/* 164 */     Preconditions.checkArgument((url != null), "resource %s relative to %s not found.", new Object[] { resourceName, contextClass.getName() });
/*     */     
/* 166 */     return url;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\Resources.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */