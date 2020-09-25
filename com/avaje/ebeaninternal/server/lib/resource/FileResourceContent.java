/*    */ package com.avaje.ebeaninternal.server.lib.resource;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Date;
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
/*    */ 
/*    */ public class FileResourceContent
/*    */   implements ResourceContent
/*    */ {
/*    */   File file;
/*    */   String entryName;
/*    */   
/*    */   public FileResourceContent(File file, String entryName) {
/* 42 */     this.file = file;
/* 43 */     this.entryName = entryName;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuffer sb = new StringBuffer();
/* 48 */     sb.append("[").append(getName());
/* 49 */     sb.append("] size[").append(size());
/* 50 */     sb.append("] lastModified[").append(new Date(lastModified()));
/* 51 */     sb.append("]");
/* 52 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 63 */     return this.entryName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long lastModified() {
/* 70 */     return this.file.lastModified();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long size() {
/* 77 */     return this.file.length();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getInputStream() throws IOException {
/* 85 */     FileInputStream is = new FileInputStream(this.file);
/* 86 */     return is;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\resource\FileResourceContent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */