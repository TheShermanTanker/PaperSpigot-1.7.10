/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.OptionalDataException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ public class SocketServer
/*     */   extends AbstractServer
/*     */   implements Runnable
/*     */ {
/*     */   private final Logger logger;
/*     */   private static final int MAX_PORT = 65534;
/*     */   private volatile boolean isActive = true;
/*     */   private final ServerSocket server;
/*  60 */   private final ConcurrentMap<Long, SocketHandler> handlers = new ConcurrentHashMap<Long, SocketHandler>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketServer(int port) throws IOException {
/*  68 */     this.server = new ServerSocket(port);
/*  69 */     this.logger = LogManager.getLogger(getClass().getName() + '.' + port);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*     */     String line;
/*  77 */     if (args.length < 1 || args.length > 2) {
/*  78 */       System.err.println("Incorrect number of arguments");
/*  79 */       printUsage();
/*     */       return;
/*     */     } 
/*  82 */     int port = Integer.parseInt(args[0]);
/*  83 */     if (port <= 0 || port >= 65534) {
/*  84 */       System.err.println("Invalid port number");
/*  85 */       printUsage();
/*     */       return;
/*     */     } 
/*  88 */     if (args.length == 2 && args[1].length() > 0) {
/*  89 */       ConfigurationFactory.setConfigurationFactory((ConfigurationFactory)new ServerConfigurationFactory(args[1]));
/*     */     }
/*  91 */     SocketServer sserver = new SocketServer(port);
/*  92 */     Thread server = new Thread(sserver);
/*  93 */     server.start();
/*  94 */     Charset enc = Charset.defaultCharset();
/*  95 */     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, enc));
/*     */     do {
/*  97 */       line = reader.readLine();
/*  98 */     } while (line != null && !line.equalsIgnoreCase("Quit") && !line.equalsIgnoreCase("Stop") && !line.equalsIgnoreCase("Exit"));
/*  99 */     sserver.shutdown();
/* 100 */     server.join();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void printUsage() {
/* 107 */     System.out.println("Usage: ServerSocket port configFilePath");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 114 */     this.isActive = false;
/* 115 */     Thread.currentThread().interrupt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 123 */     while (this.isActive) {
/*     */       
/*     */       try {
/* 126 */         Socket clientSocket = this.server.accept();
/* 127 */         clientSocket.setSoLinger(true, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 133 */         SocketHandler handler = new SocketHandler(clientSocket);
/* 134 */         this.handlers.put(Long.valueOf(handler.getId()), handler);
/* 135 */         handler.start();
/* 136 */       } catch (IOException ioe) {
/* 137 */         System.out.println("Exception encountered on accept. Ignoring. Stack Trace :");
/* 138 */         ioe.printStackTrace();
/*     */       } 
/*     */     } 
/* 141 */     for (Map.Entry<Long, SocketHandler> entry : this.handlers.entrySet()) {
/* 142 */       SocketHandler handler = entry.getValue();
/* 143 */       handler.shutdown();
/*     */       try {
/* 145 */         handler.join();
/* 146 */       } catch (InterruptedException ie) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class SocketHandler
/*     */     extends Thread
/*     */   {
/*     */     private final ObjectInputStream ois;
/*     */ 
/*     */     
/*     */     private boolean shutdown = false;
/*     */ 
/*     */     
/*     */     public SocketHandler(Socket socket) throws IOException {
/* 162 */       this.ois = new ObjectInputStream(socket.getInputStream());
/*     */     }
/*     */     
/*     */     public void shutdown() {
/* 166 */       this.shutdown = true;
/* 167 */       interrupt();
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 172 */       boolean closed = false; try {
/*     */         while (true) {
/*     */           try {
/* 175 */             if (!this.shutdown) {
/* 176 */               LogEvent event = (LogEvent)this.ois.readObject();
/* 177 */               if (event != null)
/* 178 */                 SocketServer.this.log(event); 
/*     */               continue;
/*     */             } 
/* 181 */           } catch (EOFException eof) {
/* 182 */             closed = true;
/* 183 */           } catch (OptionalDataException opt) {
/* 184 */             SocketServer.this.logger.error("OptionalDataException eof=" + opt.eof + " length=" + opt.length, opt);
/* 185 */           } catch (ClassNotFoundException cnfe) {
/* 186 */             SocketServer.this.logger.error("Unable to locate LogEvent class", cnfe);
/* 187 */           } catch (IOException ioe) {
/* 188 */             SocketServer.this.logger.error("IOException encountered while reading from socket", ioe);
/*     */           }  break;
/* 190 */         }  if (!closed) {
/*     */           try {
/* 192 */             this.ois.close();
/* 193 */           } catch (Exception ex) {}
/*     */         }
/*     */       }
/*     */       finally {
/*     */         
/* 198 */         SocketServer.this.handlers.remove(Long.valueOf(getId()));
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
/* 211 */       this.path = path;
/*     */     }
/*     */ 
/*     */     
/*     */     public Configuration getConfiguration(String name, URI configLocation) {
/* 216 */       if (this.path != null && this.path.length() > 0) {
/* 217 */         File file = null;
/* 218 */         ConfigurationFactory.ConfigurationSource source = null;
/*     */         try {
/* 220 */           file = new File(this.path);
/* 221 */           FileInputStream is = new FileInputStream(file);
/* 222 */           source = new ConfigurationFactory.ConfigurationSource(is, file);
/* 223 */         } catch (FileNotFoundException ex) {}
/*     */ 
/*     */         
/* 226 */         if (source == null) {
/*     */           try {
/* 228 */             URL url = new URL(this.path);
/* 229 */             source = new ConfigurationFactory.ConfigurationSource(url.openStream(), this.path);
/* 230 */           } catch (MalformedURLException mue) {
/*     */           
/* 232 */           } catch (IOException ioe) {}
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 238 */           if (source != null) {
/* 239 */             return (Configuration)new XMLConfiguration(source);
/*     */           }
/* 241 */         } catch (Exception ex) {}
/*     */ 
/*     */         
/* 244 */         System.err.println("Unable to process configuration at " + this.path + ", using default.");
/*     */       } 
/* 246 */       return super.getConfiguration(name, configLocation);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\SocketServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */