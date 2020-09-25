/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.spigotmc.SpigotConfig;
/*    */ 
/*    */ public class IntCache {
/*  8 */   private static int a = 256;
/*  9 */   private static List b = new ArrayList();
/* 10 */   private static List c = new ArrayList();
/* 11 */   private static List d = new ArrayList();
/* 12 */   private static List e = new ArrayList();
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized int[] a(int i) {
/* 17 */     if (i <= 256) {
/* 18 */       if (b.isEmpty()) {
/* 19 */         int[] arrayOfInt1 = new int[256];
/* 20 */         if (c.size() < SpigotConfig.intCacheLimit) c.add(arrayOfInt1); 
/* 21 */         return arrayOfInt1;
/*    */       } 
/* 23 */       int[] arrayOfInt = b.remove(b.size() - 1);
/* 24 */       if (c.size() < SpigotConfig.intCacheLimit) c.add(arrayOfInt); 
/* 25 */       return arrayOfInt;
/*    */     } 
/* 27 */     if (i > a) {
/* 28 */       a = i;
/* 29 */       d.clear();
/* 30 */       e.clear();
/* 31 */       int[] arrayOfInt = new int[a];
/* 32 */       if (e.size() < SpigotConfig.intCacheLimit) e.add(arrayOfInt); 
/* 33 */       return arrayOfInt;
/* 34 */     }  if (d.isEmpty()) {
/* 35 */       int[] arrayOfInt = new int[a];
/* 36 */       if (e.size() < SpigotConfig.intCacheLimit) e.add(arrayOfInt); 
/* 37 */       return arrayOfInt;
/*    */     } 
/* 39 */     int[] aint = d.remove(d.size() - 1);
/* 40 */     if (e.size() < SpigotConfig.intCacheLimit) e.add(aint); 
/* 41 */     return aint;
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized void a() {
/* 46 */     if (!d.isEmpty()) {
/* 47 */       d.remove(d.size() - 1);
/*    */     }
/*    */     
/* 50 */     if (!b.isEmpty()) {
/* 51 */       b.remove(b.size() - 1);
/*    */     }
/*    */     
/* 54 */     d.addAll(e);
/* 55 */     b.addAll(c);
/* 56 */     e.clear();
/* 57 */     c.clear();
/*    */   }
/*    */   
/*    */   public static synchronized String b() {
/* 61 */     return "cache: " + d.size() + ", tcache: " + b.size() + ", allocated: " + e.size() + ", tallocated: " + c.size();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IntCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */