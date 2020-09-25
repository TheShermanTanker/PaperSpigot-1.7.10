/*    */ package com.avaje.ebeaninternal.server.lib.resource;
/*    */ 
/*    */ import java.io.File;
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
/*    */ 
/*    */ 
/*    */ public class FileResourceSource
/*    */   extends AbstractResourceSource
/*    */   implements ResourceSource
/*    */ {
/*    */   String directory;
/*    */   String baseDir;
/*    */   
/*    */   public FileResourceSource(String directory) {
/* 38 */     this.directory = directory;
/* 39 */     this.baseDir = directory + File.separator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FileResourceSource(File dir) {
/* 46 */     this(dir.getPath());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRealPath() {
/* 51 */     return this.directory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceContent getContent(String entry) {
/* 59 */     String fullPath = this.baseDir + entry;
/*    */     
/* 61 */     File f = new File(fullPath);
/* 62 */     if (f.exists()) {
/* 63 */       FileResourceContent content = new FileResourceContent(f, entry);
/* 64 */       return content;
/*    */     } 
/* 66 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\resource\FileResourceSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */