/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URI;
/*    */ import java.net.URL;
/*    */ import java.net.URLDecoder;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FileUtils
/*    */ {
/*    */   private static final String PROTOCOL_FILE = "file";
/*    */   private static final String JBOSS_FILE = "vfsfile";
/* 40 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static File fileFromURI(URI uri) {
/* 53 */     if (uri == null || (uri.getScheme() != null && !"file".equals(uri.getScheme()) && !"vfsfile".equals(uri.getScheme())))
/*    */     {
/* 55 */       return null;
/*    */     }
/* 57 */     if (uri.getScheme() == null) {
/*    */       try {
/* 59 */         uri = (new File(uri.getPath())).toURI();
/* 60 */       } catch (Exception ex) {
/* 61 */         LOGGER.warn("Invalid URI " + uri);
/* 62 */         return null;
/*    */       } 
/*    */     }
/*    */     try {
/* 66 */       return new File(URLDecoder.decode(uri.toURL().getFile(), "UTF8"));
/* 67 */     } catch (MalformedURLException ex) {
/* 68 */       LOGGER.warn("Invalid URL " + uri, ex);
/* 69 */     } catch (UnsupportedEncodingException uee) {
/* 70 */       LOGGER.warn("Invalid encoding: UTF8", uee);
/*    */     } 
/* 72 */     return null;
/*    */   }
/*    */   
/*    */   public static boolean isFile(URL url) {
/* 76 */     return (url != null && (url.getProtocol().equals("file") || url.getProtocol().equals("vfsfile")));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void mkdir(File dir, boolean createDirectoryIfNotExisting) throws IOException {
/* 87 */     if (!dir.exists()) {
/* 88 */       if (!createDirectoryIfNotExisting) {
/* 89 */         throw new IOException("The directory " + dir.getAbsolutePath() + " does not exist.");
/*    */       }
/* 91 */       if (!dir.mkdirs()) {
/* 92 */         throw new IOException("Could not create directory " + dir.getAbsolutePath());
/*    */       }
/*    */     } 
/* 95 */     if (!dir.isDirectory())
/* 96 */       throw new IOException("File " + dir + " exists and is not a directory. Unable to create directory."); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */