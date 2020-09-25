/*     */ package net.minecraft.util.io.netty.util.internal;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Formatter;
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
/*     */ public final class StringUtil
/*     */ {
/*     */   public static final String NEWLINE;
/*     */   private static final String EMPTY_STRING = "";
/*     */   
/*     */   static {
/*     */     String str;
/*     */     try {
/*  37 */       str = (new Formatter()).format("%n", new Object[0]).toString();
/*  38 */     } catch (Exception e) {
/*  39 */       str = "\n";
/*     */     } 
/*     */     
/*  42 */     NEWLINE = str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] split(String value, char delim) {
/*  52 */     int end = value.length();
/*  53 */     List<String> res = new ArrayList<String>();
/*     */     
/*  55 */     int start = 0; int i;
/*  56 */     for (i = 0; i < end; i++) {
/*  57 */       if (value.charAt(i) == delim) {
/*  58 */         if (start == i) {
/*  59 */           res.add("");
/*     */         } else {
/*  61 */           res.add(value.substring(start, i));
/*     */         } 
/*  63 */         start = i + 1;
/*     */       } 
/*     */     } 
/*     */     
/*  67 */     if (start == 0) {
/*  68 */       res.add(value);
/*     */     }
/*  70 */     else if (start != end) {
/*     */       
/*  72 */       res.add(value.substring(start, end));
/*     */     } else {
/*     */       
/*  75 */       for (i = res.size() - 1; i >= 0 && (
/*  76 */         (String)res.get(i)).isEmpty(); i--) {
/*  77 */         res.remove(i);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     return res.<String>toArray(new String[res.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String simpleClassName(Object o) {
/*  92 */     return simpleClassName(o.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String simpleClassName(Class<?> clazz) {
/* 100 */     Package pkg = clazz.getPackage();
/* 101 */     if (pkg != null) {
/* 102 */       return clazz.getName().substring(pkg.getName().length() + 1);
/*     */     }
/* 104 */     return clazz.getName();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\StringUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */