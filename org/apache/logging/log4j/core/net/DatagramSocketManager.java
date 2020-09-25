/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
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
/*     */ public class DatagramSocketManager
/*     */   extends AbstractSocketManager
/*     */ {
/*  35 */   private static final DatagramSocketManagerFactory FACTORY = new DatagramSocketManagerFactory();
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
/*     */   protected DatagramSocketManager(String name, OutputStream os, InetAddress address, String host, int port, Layout<? extends Serializable> layout) {
/*  48 */     super(name, os, address, host, port, layout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DatagramSocketManager getSocketManager(String host, int port, Layout<? extends Serializable> layout) {
/*  59 */     if (Strings.isEmpty(host)) {
/*  60 */       throw new IllegalArgumentException("A host name is required");
/*     */     }
/*  62 */     if (port <= 0) {
/*  63 */       throw new IllegalArgumentException("A port value is required");
/*     */     }
/*  65 */     return (DatagramSocketManager)getManager("UDP:" + host + ":" + port, new FactoryData(host, port, layout), FACTORY);
/*     */   }
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
/*     */   public Map<String, String> getContentFormat() {
/*  78 */     Map<String, String> result = new HashMap<String, String>(super.getContentFormat());
/*  79 */     result.put("protocol", "udp");
/*  80 */     result.put("direction", "out");
/*     */     
/*  82 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String host;
/*     */     
/*     */     private final int port;
/*     */     private final Layout<? extends Serializable> layout;
/*     */     
/*     */     public FactoryData(String host, int port, Layout<? extends Serializable> layout) {
/*  94 */       this.host = host;
/*  95 */       this.port = port;
/*  96 */       this.layout = layout;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class DatagramSocketManagerFactory
/*     */     implements ManagerFactory<DatagramSocketManager, FactoryData>
/*     */   {
/*     */     private DatagramSocketManagerFactory() {}
/*     */     
/*     */     public DatagramSocketManager createManager(String name, DatagramSocketManager.FactoryData data) {
/*     */       InetAddress address;
/* 108 */       OutputStream os = new DatagramOutputStream(data.host, data.port, data.layout.getHeader(), data.layout.getFooter());
/*     */       
/*     */       try {
/* 111 */         address = InetAddress.getByName(data.host);
/* 112 */       } catch (UnknownHostException ex) {
/* 113 */         DatagramSocketManager.LOGGER.error("Could not find address of " + data.host, ex);
/* 114 */         return null;
/*     */       } 
/* 116 */       return new DatagramSocketManager(name, os, address, data.host, data.port, data.layout);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\DatagramSocketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */