/*    */ package com.avaje.ebeaninternal.server.lib.resource;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UrlResourceContent
/*    */   implements ResourceContent
/*    */ {
/*    */   String entryName;
/*    */   URLConnection con;
/*    */   
/*    */   public UrlResourceContent(URL url, String entryName) {
/* 45 */     this.entryName = entryName;
/*    */     try {
/* 47 */       this.con = url.openConnection();
/* 48 */     } catch (IOException ex) {
/* 49 */       throw new RuntimeException(ex);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     StringBuffer sb = new StringBuffer();
/* 55 */     sb.append("[").append(getName());
/* 56 */     sb.append("] size[").append(size());
/* 57 */     sb.append("] lastModified[").append(new Date(lastModified()));
/* 58 */     sb.append("]");
/* 59 */     return sb.toString();
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
/* 70 */     return this.entryName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long lastModified() {
/* 77 */     return this.con.getLastModified();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long size() {
/* 84 */     return this.con.getContentLength();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getInputStream() throws IOException {
/* 92 */     return this.con.getInputStream();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\resource\UrlResourceContent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */