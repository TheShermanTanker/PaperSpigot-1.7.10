/*    */ package org.bukkit.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.FileChannel;
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
/*    */ public class FileUtil
/*    */ {
/*    */   public static boolean copy(File inFile, File outFile) {
/* 22 */     if (!inFile.exists()) {
/* 23 */       return false;
/*    */     }
/*    */     
/* 26 */     FileChannel in = null;
/* 27 */     FileChannel out = null;
/*    */     
/*    */     try {
/* 30 */       in = (new FileInputStream(inFile)).getChannel();
/* 31 */       out = (new FileOutputStream(outFile)).getChannel();
/*    */       
/* 33 */       long pos = 0L;
/* 34 */       long size = in.size();
/*    */       
/* 36 */       while (pos < size) {
/* 37 */         pos += in.transferTo(pos, 10485760L, out);
/*    */       }
/* 39 */     } catch (IOException ioe) {
/* 40 */       return false;
/*    */     } finally {
/*    */       try {
/* 43 */         if (in != null) {
/* 44 */           in.close();
/*    */         }
/* 46 */         if (out != null) {
/* 47 */           out.close();
/*    */         }
/* 49 */       } catch (IOException ioe) {
/* 50 */         return false;
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\FileUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */