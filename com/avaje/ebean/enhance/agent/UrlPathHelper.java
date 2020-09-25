/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UrlPathHelper
/*    */ {
/*    */   private static final String PROTOCAL_PREFIX = "file:";
/*    */   
/*    */   public static URL[] convertToUrl(String[] paths) {
/* 20 */     ArrayList<URL> list = new ArrayList<URL>();
/* 21 */     for (int i = 0; i < paths.length; i++) {
/* 22 */       URL url = convertToUrl(paths[i]);
/* 23 */       if (url != null) {
/* 24 */         list.add(url);
/*    */       }
/*    */     } 
/* 27 */     return list.<URL>toArray(new URL[list.size()]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static URL convertToUrl(String path) {
/* 34 */     if (isEmpty(path)) {
/* 35 */       return null;
/*    */     }
/*    */     try {
/* 38 */       return new URL("file:" + convertUrlString(path));
/* 39 */     } catch (MalformedURLException e) {
/* 40 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String convertUrlString(String classpath) {
/* 49 */     if (isEmpty(classpath)) {
/* 50 */       return "";
/*    */     }
/*    */     
/* 53 */     classpath = classpath.trim();
/* 54 */     if (classpath.length() < 2) {
/* 55 */       return "";
/*    */     }
/* 57 */     if (classpath.charAt(0) != '/' && classpath.charAt(1) == ':')
/*    */     {
/*    */       
/* 60 */       classpath = "/" + classpath;
/*    */     }
/* 62 */     if (!classpath.endsWith("/")) {
/* 63 */       File file = new File(classpath);
/* 64 */       if (file.exists() && file.isDirectory()) {
/* 65 */         classpath = classpath.concat("/");
/*    */       }
/*    */     } 
/* 68 */     return classpath;
/*    */   }
/*    */   
/*    */   private static boolean isEmpty(String s) {
/* 72 */     return (s == null || s.trim().length() == 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\UrlPathHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */