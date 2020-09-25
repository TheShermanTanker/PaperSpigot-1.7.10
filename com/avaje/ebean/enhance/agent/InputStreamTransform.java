/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.instrument.IllegalClassFormatException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputStreamTransform
/*     */ {
/*     */   final Transformer transformer;
/*     */   final ClassLoader classLoader;
/*     */   
/*     */   public InputStreamTransform(Transformer transformer, ClassLoader classLoader) {
/*  26 */     this.transformer = transformer;
/*  27 */     this.classLoader = classLoader;
/*     */   }
/*     */   
/*     */   public void log(int level, String msg) {
/*  31 */     this.transformer.log(level, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] transform(String className, File file) throws IOException, IllegalClassFormatException {
/*     */     try {
/*  39 */       return transform(className, new FileInputStream(file));
/*     */     }
/*  41 */     catch (FileNotFoundException e) {
/*  42 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] transform(String className, InputStream is) throws IOException, IllegalClassFormatException {
/*     */     try {
/*  53 */       byte[] classBytes = readBytes(is);
/*     */       
/*  55 */       return this.transformer.transform(this.classLoader, className, null, null, classBytes);
/*     */     } finally {
/*     */       
/*  58 */       if (is != null) {
/*  59 */         is.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeBytes(byte[] bytes, File file) throws IOException {
/*  68 */     writeBytes(bytes, new FileOutputStream(file));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeBytes(byte[] bytes, OutputStream os) throws IOException {
/*  76 */     BufferedOutputStream bos = new BufferedOutputStream(os);
/*     */     
/*  78 */     ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
/*     */     
/*  80 */     byte[] buf = new byte[1028];
/*     */     
/*  82 */     int len = 0;
/*  83 */     while ((len = bis.read(buf, 0, buf.length)) > -1) {
/*  84 */       bos.write(buf, 0, len);
/*     */     }
/*     */     
/*  87 */     bos.flush();
/*  88 */     bos.close();
/*     */     
/*  90 */     bis.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] readBytes(InputStream is) throws IOException {
/*  96 */     BufferedInputStream bis = new BufferedInputStream(is);
/*     */     
/*  98 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
/*     */     
/* 100 */     byte[] buf = new byte[1028];
/*     */     
/* 102 */     int len = 0;
/* 103 */     while ((len = bis.read(buf, 0, buf.length)) > -1) {
/* 104 */       baos.write(buf, 0, len);
/*     */     }
/* 106 */     baos.flush();
/* 107 */     baos.close();
/* 108 */     return baos.toByteArray();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\InputStreamTransform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */