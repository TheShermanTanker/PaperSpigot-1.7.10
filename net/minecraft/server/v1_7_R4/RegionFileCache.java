/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RegionFileCache
/*    */ {
/* 13 */   public static final Map a = new HashMap<Object, Object>();
/*    */   
/*    */   public static synchronized RegionFile a(File file1, int i, int j) {
/* 16 */     File file2 = new File(file1, "region");
/* 17 */     File file3 = new File(file2, "r." + (i >> 5) + "." + (j >> 5) + ".mca");
/* 18 */     RegionFile regionfile = (RegionFile)a.get(file3);
/*    */     
/* 20 */     if (regionfile != null) {
/* 21 */       return regionfile;
/*    */     }
/* 23 */     if (!file2.exists()) {
/* 24 */       file2.mkdirs();
/*    */     }
/*    */     
/* 27 */     if (a.size() >= 256) {
/* 28 */       a();
/*    */     }
/*    */     
/* 31 */     RegionFile regionfile1 = new RegionFile(file3);
/*    */     
/* 33 */     a.put(file3, regionfile1);
/* 34 */     return regionfile1;
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized void a() {
/* 39 */     Iterator<RegionFile> iterator = a.values().iterator();
/*    */     
/* 41 */     while (iterator.hasNext()) {
/* 42 */       RegionFile regionfile = iterator.next();
/*    */       
/*    */       try {
/* 45 */         if (regionfile != null) {
/* 46 */           regionfile.c();
/*    */         }
/* 48 */       } catch (IOException ioexception) {
/* 49 */         ioexception.printStackTrace();
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     a.clear();
/*    */   }
/*    */   
/*    */   public static DataInputStream c(File file1, int i, int j) {
/* 57 */     RegionFile regionfile = a(file1, i, j);
/*    */     
/* 59 */     return regionfile.a(i & 0x1F, j & 0x1F);
/*    */   }
/*    */   
/*    */   public static DataOutputStream d(File file1, int i, int j) {
/* 63 */     RegionFile regionfile = a(file1, i, j);
/*    */     
/* 65 */     return regionfile.b(i & 0x1F, j & 0x1F);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RegionFileCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */