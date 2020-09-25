/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.ServerSocket;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ public abstract class RemoteConnectionThread implements Runnable {
/*  11 */   private static final AtomicInteger h = new AtomicInteger(0);
/*     */   protected boolean running;
/*     */   protected IMinecraftServer server;
/*     */   protected final String c;
/*     */   protected Thread thread;
/*  16 */   protected int e = 5;
/*  17 */   protected List f = new ArrayList();
/*  18 */   protected List g = new ArrayList();
/*     */   
/*     */   protected RemoteConnectionThread(IMinecraftServer paramIMinecraftServer, String paramString) {
/*  21 */     this.server = paramIMinecraftServer;
/*  22 */     this.c = paramString;
/*  23 */     if (this.server.isDebugging()) {
/*  24 */       warning("Debugging is enabled, performance maybe reduced!");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void a() {
/*  32 */     this.thread = new Thread(this, this.c + " #" + h.incrementAndGet());
/*  33 */     this.thread.start();
/*  34 */     this.running = true;
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
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  73 */     return this.running;
/*     */   }
/*     */   
/*     */   protected void debug(String paramString) {
/*  77 */     this.server.i(paramString);
/*     */   }
/*     */   
/*     */   protected void info(String paramString) {
/*  81 */     this.server.info(paramString);
/*     */   }
/*     */   
/*     */   protected void warning(String paramString) {
/*  85 */     this.server.warning(paramString);
/*     */   }
/*     */   
/*     */   protected void error(String paramString) {
/*  89 */     this.server.h(paramString);
/*     */   }
/*     */   
/*     */   protected int d() {
/*  93 */     return this.server.C();
/*     */   }
/*     */   
/*     */   protected void a(DatagramSocket paramDatagramSocket) {
/*  97 */     debug("registerSocket: " + paramDatagramSocket);
/*  98 */     this.f.add(paramDatagramSocket);
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
/*     */   protected boolean a(DatagramSocket paramDatagramSocket, boolean paramBoolean) {
/* 111 */     debug("closeSocket: " + paramDatagramSocket);
/* 112 */     if (null == paramDatagramSocket) {
/* 113 */       return false;
/*     */     }
/*     */     
/* 116 */     boolean bool = false;
/* 117 */     if (!paramDatagramSocket.isClosed()) {
/* 118 */       paramDatagramSocket.close();
/* 119 */       bool = true;
/*     */     } 
/*     */     
/* 122 */     if (paramBoolean) {
/* 123 */       this.f.remove(paramDatagramSocket);
/*     */     }
/*     */     
/* 126 */     return bool;
/*     */   }
/*     */   
/*     */   protected boolean b(ServerSocket paramServerSocket) {
/* 130 */     return a(paramServerSocket, true);
/*     */   }
/*     */   
/*     */   protected boolean a(ServerSocket paramServerSocket, boolean paramBoolean) {
/* 134 */     debug("closeSocket: " + paramServerSocket);
/* 135 */     if (null == paramServerSocket) {
/* 136 */       return false;
/*     */     }
/*     */     
/* 139 */     boolean bool = false;
/*     */     try {
/* 141 */       if (!paramServerSocket.isClosed()) {
/* 142 */         paramServerSocket.close();
/* 143 */         bool = true;
/*     */       } 
/* 145 */     } catch (IOException iOException) {
/* 146 */       warning("IO: " + iOException.getMessage());
/*     */     } 
/*     */     
/* 149 */     if (paramBoolean) {
/* 150 */       this.g.remove(paramServerSocket);
/*     */     }
/*     */     
/* 153 */     return bool;
/*     */   }
/*     */   
/*     */   protected void e() {
/* 157 */     a(false);
/*     */   }
/*     */   
/*     */   protected void a(boolean paramBoolean) {
/* 161 */     byte b = 0;
/* 162 */     for (DatagramSocket datagramSocket : this.f) {
/* 163 */       if (a(datagramSocket, false)) {
/* 164 */         b++;
/*     */       }
/*     */     } 
/* 167 */     this.f.clear();
/*     */     
/* 169 */     for (ServerSocket serverSocket : this.g) {
/* 170 */       if (a(serverSocket, false)) {
/* 171 */         b++;
/*     */       }
/*     */     } 
/* 174 */     this.g.clear();
/*     */     
/* 176 */     if (paramBoolean && 0 < b)
/* 177 */       warning("Force closed " + b + " sockets"); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RemoteConnectionThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */