/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLStreamHandler;
/*     */ import java.net.URLStreamHandlerFactory;
/*     */ import sun.net.www.protocol.http.Handler;
/*     */ import sun.net.www.protocol.https.Handler;
/*     */ 
/*     */ public class CachedStreamHandlerFactory
/*     */   implements URLStreamHandlerFactory {
/*     */   public static boolean isSet = false;
/*     */   
/*     */   public URLStreamHandler createURLStreamHandler(String protocol) {
/*  19 */     if (protocol.equals("http") || protocol.equals("https"))
/*     */     {
/*  21 */       return new CachedStreamHandler(protocol);
/*     */     }
/*  23 */     return null;
/*     */   }
/*     */   
/*     */   public class CachedStreamHandler
/*     */     extends URLStreamHandler
/*     */   {
/*     */     private final String protocol;
/*     */     private final URLStreamHandler handler;
/*     */     private final Method openCon;
/*     */     private final Method openConProxy;
/*     */     
/*     */     public CachedStreamHandler(String protocol) {
/*  35 */       this.protocol = protocol;
/*  36 */       if (protocol.equals("http")) {
/*     */         
/*  38 */         this.handler = new Handler();
/*     */       } else {
/*     */         
/*  41 */         this.handler = new Handler();
/*     */       } 
/*     */       
/*     */       try {
/*  45 */         this.openCon = this.handler.getClass().getDeclaredMethod("openConnection", new Class[] { URL.class });
/*  46 */         this.openCon.setAccessible(true);
/*  47 */         this.openConProxy = this.handler.getClass().getDeclaredMethod("openConnection", new Class[] { URL.class, Proxy.class });
/*  48 */         this.openConProxy.setAccessible(true);
/*  49 */       } catch (NoSuchMethodException e) {
/*     */         
/*  51 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected URLConnection openConnection(URL u) throws IOException {
/*  58 */       if (u.getHost().equals("api.mojang.com") || u.getPath().startsWith("/profiles/minecraft"))
/*     */       {
/*     */         
/*  61 */         return cachedConnection(u);
/*     */       }
/*  63 */       return getDefaultConnection(u);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected URLConnection openConnection(URL u, Proxy p) throws IOException {
/*  69 */       if (u.getHost().equals("api.mojang.com") || u.getPath().startsWith("/profiles/minecraft"))
/*     */       {
/*     */         
/*  72 */         return cachedConnection(u, p);
/*     */       }
/*  74 */       return getDefaultConnection(u, p);
/*     */     }
/*     */ 
/*     */     
/*     */     private URLConnection cachedConnection(URL u) {
/*  79 */       return cachedConnection(u, null);
/*     */     }
/*     */ 
/*     */     
/*     */     private URLConnection cachedConnection(URL u, Proxy p) {
/*  84 */       return new CachedMojangAPIConnection(this, u, p);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public URLConnection getDefaultConnection(URL u) {
/*     */       try {
/*  91 */         return (URLConnection)this.openCon.invoke(this.handler, new Object[] { u });
/*  92 */       } catch (IllegalAccessException e) {
/*     */         
/*  94 */         e.printStackTrace();
/*  95 */       } catch (InvocationTargetException e) {
/*     */         
/*  97 */         e.printStackTrace();
/*     */       } 
/*  99 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public URLConnection getDefaultConnection(URL u, Proxy p) {
/*     */       try {
/* 106 */         return (URLConnection)this.openConProxy.invoke(this.handler, new Object[] { u, p });
/* 107 */       } catch (IllegalAccessException e) {
/*     */         
/* 109 */         e.printStackTrace();
/* 110 */       } catch (InvocationTargetException e) {
/*     */         
/* 112 */         e.printStackTrace();
/*     */       } 
/* 114 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\CachedStreamHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */