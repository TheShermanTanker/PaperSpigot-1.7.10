/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import java.net.URLClassLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalClassLoader
/*    */   extends URLClassLoader
/*    */ {
/*    */   public LocalClassLoader(URL[] urls, ClassLoader loader) {
/* 17 */     super(urls, loader);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
/* 23 */     if (name.startsWith("java."))
/*    */     {
/* 25 */       return super.loadClass(name, resolve);
/*    */     }
/* 27 */     Class<?> c = findLoadedClass(name);
/* 28 */     if (c != null) {
/* 29 */       return c;
/*    */     }
/* 31 */     String resource = name.replace('.', '/') + ".class";
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 36 */       URL url = getResource(resource);
/* 37 */       if (url == null) {
/* 38 */         throw new ClassNotFoundException(name);
/*    */       }
/*    */       
/* 41 */       File f = new File("build/bin/" + resource);
/* 42 */       System.out.println("FileLen:" + f.length() + "  " + f.getName());
/*    */       
/* 44 */       InputStream is = url.openStream();
/*    */       try {
/* 46 */         ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 47 */         byte[] b = new byte[2048];
/*    */         int count;
/* 49 */         while ((count = is.read(b, 0, 2048)) != -1) {
/* 50 */           os.write(b, 0, count);
/*    */         }
/* 52 */         byte[] bytes = os.toByteArray();
/* 53 */         System.err.println("bytes: " + bytes.length + " " + resource);
/* 54 */         return defineClass(name, bytes, 0, bytes.length);
/*    */       } finally {
/* 56 */         if (is != null) {
/* 57 */           is.close();
/*    */         }
/*    */       } 
/* 60 */     } catch (SecurityException e) {
/* 61 */       return super.loadClass(name, resolve);
/* 62 */     } catch (IOException e) {
/* 63 */       throw new ClassNotFoundException(name, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\LocalClassLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */