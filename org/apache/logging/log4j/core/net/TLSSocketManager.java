/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.core.net.ssl.SSLConfiguration;
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
/*     */ public class TLSSocketManager
/*     */   extends TCPSocketManager
/*     */ {
/*     */   public static final int DEFAULT_PORT = 6514;
/*  38 */   private static final TLSSocketManagerFactory FACTORY = new TLSSocketManagerFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SSLConfiguration sslConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TLSSocketManager(String name, OutputStream os, Socket sock, SSLConfiguration sslConfig, InetAddress addr, String host, int port, int delay, boolean immediateFail, Layout<? extends Serializable> layout) {
/*  56 */     super(name, os, sock, addr, host, port, delay, immediateFail, layout);
/*  57 */     this.sslConfig = sslConfig;
/*     */   }
/*     */   
/*     */   private static class TLSFactoryData
/*     */   {
/*     */     protected SSLConfiguration sslConfig;
/*     */     private final String host;
/*     */     private final int port;
/*     */     private final int delay;
/*     */     private final boolean immediateFail;
/*     */     private final Layout layout;
/*     */     
/*     */     public TLSFactoryData(SSLConfiguration sslConfig, String host, int port, int delay, boolean immediateFail, Layout layout) {
/*  70 */       this.host = host;
/*  71 */       this.port = port;
/*  72 */       this.delay = delay;
/*  73 */       this.immediateFail = immediateFail;
/*  74 */       this.layout = layout;
/*  75 */       this.sslConfig = sslConfig;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static TLSSocketManager getSocketManager(SSLConfiguration sslConfig, String host, int port, int delay, boolean immediateFail, Layout layout) {
/*  81 */     if (Strings.isEmpty(host)) {
/*  82 */       throw new IllegalArgumentException("A host name is required");
/*     */     }
/*  84 */     if (port <= 0) {
/*  85 */       port = 6514;
/*     */     }
/*  87 */     if (delay == 0) {
/*  88 */       delay = 30000;
/*     */     }
/*  90 */     return (TLSSocketManager)getManager("TLS:" + host + ":" + port, new TLSFactoryData(sslConfig, host, port, delay, immediateFail, layout), FACTORY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Socket createSocket(String host, int port) throws IOException {
/*  96 */     SSLSocketFactory socketFactory = createSSLSocketFactory(this.sslConfig);
/*  97 */     return socketFactory.createSocket(host, port);
/*     */   }
/*     */ 
/*     */   
/*     */   private static SSLSocketFactory createSSLSocketFactory(SSLConfiguration sslConf) {
/*     */     SSLSocketFactory socketFactory;
/* 103 */     if (sslConf != null) {
/* 104 */       socketFactory = sslConf.getSSLSocketFactory();
/*     */     } else {
/* 106 */       socketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
/*     */     } 
/* 108 */     return socketFactory;
/*     */   }
/*     */   
/*     */   private static class TLSSocketManagerFactory implements ManagerFactory<TLSSocketManager, TLSFactoryData> {
/*     */     private TLSSocketManagerFactory() {}
/*     */     
/*     */     private class TLSSocketManagerFactoryException extends Exception {
/*     */       private TLSSocketManagerFactoryException() {}
/*     */     }
/*     */     
/*     */     public TLSSocketManager createManager(String name, TLSSocketManager.TLSFactoryData data) {
/* 119 */       InetAddress address = null;
/* 120 */       OutputStream os = null;
/* 121 */       Socket socket = null;
/*     */       
/*     */       try {
/* 124 */         address = resolveAddress(data.host);
/* 125 */         socket = createSocket(data);
/* 126 */         os = socket.getOutputStream();
/* 127 */         checkDelay(data.delay, os);
/*     */       }
/* 129 */       catch (IOException e) {
/* 130 */         TLSSocketManager.LOGGER.error("TLSSocketManager (" + name + ") " + e);
/* 131 */         os = new ByteArrayOutputStream();
/*     */       }
/* 133 */       catch (TLSSocketManagerFactoryException e) {
/* 134 */         return null;
/*     */       } 
/* 136 */       return createManager(name, os, socket, data.sslConfig, address, data.host, data.port, data.delay, data.immediateFail, data.layout);
/*     */     }
/*     */ 
/*     */     
/*     */     private InetAddress resolveAddress(String hostName) throws TLSSocketManagerFactoryException {
/*     */       InetAddress address;
/*     */       try {
/* 143 */         address = InetAddress.getByName(hostName);
/* 144 */       } catch (UnknownHostException ex) {
/* 145 */         TLSSocketManager.LOGGER.error("Could not find address of " + hostName, ex);
/* 146 */         throw new TLSSocketManagerFactoryException();
/*     */       } 
/*     */       
/* 149 */       return address;
/*     */     }
/*     */     
/*     */     private void checkDelay(int delay, OutputStream os) throws TLSSocketManagerFactoryException {
/* 153 */       if (delay == 0 && os == null) {
/* 154 */         throw new TLSSocketManagerFactoryException();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Socket createSocket(TLSSocketManager.TLSFactoryData data) throws IOException {
/* 161 */       SSLSocketFactory socketFactory = TLSSocketManager.createSSLSocketFactory(data.sslConfig);
/* 162 */       SSLSocket socket = (SSLSocket)socketFactory.createSocket(data.host, data.port);
/* 163 */       return socket;
/*     */     }
/*     */     
/*     */     private TLSSocketManager createManager(String name, OutputStream os, Socket socket, SSLConfiguration sslConfig, InetAddress address, String host, int port, int delay, boolean immediateFail, Layout layout) {
/* 167 */       return new TLSSocketManager(name, os, socket, sslConfig, address, host, port, delay, immediateFail, layout);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\TLSSocketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */