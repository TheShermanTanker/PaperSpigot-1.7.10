/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.OptionalDataException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.AbstractServer;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationFactory;
/*     */ import org.apache.logging.log4j.core.config.XMLConfiguration;
/*     */ import org.apache.logging.log4j.core.config.XMLConfigurationFactory;
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
/*     */ 
/*     */ public class UDPSocketServer
/*     */   extends AbstractServer
/*     */   implements Runnable
/*     */ {
/*     */   private final Logger logger;
/*     */   private static final int MAX_PORT = 65534;
/*     */   private volatile boolean isActive = true;
/*     */   private final DatagramSocket server;
/*  58 */   private final int maxBufferSize = 67584;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UDPSocketServer(int port) throws IOException {
/*  69 */     this.server = new DatagramSocket(port);
/*  70 */     this.logger = LogManager.getLogger(getClass().getName() + '.' + port);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*     */     String line;
/*  82 */     if (args.length < 1 || args.length > 2) {
/*  83 */       System.err.println("Incorrect number of arguments");
/*  84 */       printUsage();
/*     */       return;
/*     */     } 
/*  87 */     int port = Integer.parseInt(args[0]);
/*  88 */     if (port <= 0 || port >= 65534) {
/*  89 */       System.err.println("Invalid port number");
/*  90 */       printUsage();
/*     */       return;
/*     */     } 
/*  93 */     if (args.length == 2 && args[1].length() > 0) {
/*  94 */       ConfigurationFactory.setConfigurationFactory((ConfigurationFactory)new ServerConfigurationFactory(args[1]));
/*     */     }
/*  96 */     UDPSocketServer sserver = new UDPSocketServer(port);
/*  97 */     Thread server = new Thread(sserver);
/*  98 */     server.start();
/*  99 */     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
/*     */     do {
/* 101 */       line = reader.readLine();
/* 102 */     } while (line != null && !line.equalsIgnoreCase("Quit") && !line.equalsIgnoreCase("Stop") && !line.equalsIgnoreCase("Exit"));
/* 103 */     sserver.shutdown();
/* 104 */     server.join();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void printUsage() {
/* 111 */     System.out.println("Usage: ServerSocket port configFilePath");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 118 */     this.isActive = false;
/* 119 */     Thread.currentThread().interrupt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 127 */     while (this.isActive) {
/*     */       try {
/* 129 */         byte[] buf = new byte[67584];
/* 130 */         DatagramPacket packet = new DatagramPacket(buf, buf.length);
/* 131 */         this.server.receive(packet);
/* 132 */         ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength()));
/* 133 */         LogEvent event = (LogEvent)ois.readObject();
/* 134 */         if (event != null) {
/* 135 */           log(event);
/*     */         }
/* 137 */       } catch (OptionalDataException opt) {
/* 138 */         this.logger.error("OptionalDataException eof=" + opt.eof + " length=" + opt.length, opt);
/* 139 */       } catch (ClassNotFoundException cnfe) {
/* 140 */         this.logger.error("Unable to locate LogEvent class", cnfe);
/* 141 */       } catch (EOFException eofe) {
/* 142 */         this.logger.info("EOF encountered");
/* 143 */       } catch (IOException ioe) {
/* 144 */         this.logger.error("Exception encountered on accept. Ignoring. Stack Trace :", ioe);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ServerConfigurationFactory
/*     */     extends XMLConfigurationFactory
/*     */   {
/*     */     private final String path;
/*     */ 
/*     */     
/*     */     public ServerConfigurationFactory(String path) {
/* 157 */       this.path = path;
/*     */     }
/*     */ 
/*     */     
/*     */     public Configuration getConfiguration(String name, URI configLocation) {
/* 162 */       if (this.path != null && this.path.length() > 0) {
/* 163 */         File file = null;
/* 164 */         ConfigurationFactory.ConfigurationSource source = null;
/*     */         try {
/* 166 */           file = new File(this.path);
/* 167 */           FileInputStream is = new FileInputStream(file);
/* 168 */           source = new ConfigurationFactory.ConfigurationSource(is, file);
/* 169 */         } catch (FileNotFoundException ex) {}
/*     */ 
/*     */         
/* 172 */         if (source == null) {
/*     */           try {
/* 174 */             URL url = new URL(this.path);
/* 175 */             source = new ConfigurationFactory.ConfigurationSource(url.openStream(), this.path);
/* 176 */           } catch (MalformedURLException mue) {
/*     */           
/* 178 */           } catch (IOException ioe) {}
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 184 */           if (source != null) {
/* 185 */             return (Configuration)new XMLConfiguration(source);
/*     */           }
/* 187 */         } catch (Exception ex) {}
/*     */ 
/*     */         
/* 190 */         System.err.println("Unable to process configuration at " + this.path + ", using default.");
/*     */       } 
/* 192 */       return super.getConfiguration(name, configLocation);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\UDPSocketServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */