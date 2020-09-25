/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class RemoteControlListener
/*     */   extends RemoteConnectionThread
/*     */ {
/*     */   private int h;
/*     */   private int i;
/*     */   private String j;
/*     */   private ServerSocket k;
/*     */   private String l;
/*     */   private Map m;
/*     */   
/*     */   public RemoteControlListener(IMinecraftServer paramIMinecraftServer) {
/*  24 */     super(paramIMinecraftServer, "RCON Listener");
/*  25 */     this.h = paramIMinecraftServer.a("rcon.port", 0);
/*  26 */     this.l = paramIMinecraftServer.a("rcon.password", "");
/*  27 */     this.j = paramIMinecraftServer.y();
/*  28 */     this.i = paramIMinecraftServer.z();
/*  29 */     if (0 == this.h) {
/*     */       
/*  31 */       this.h = this.i + 10;
/*  32 */       info("Setting default rcon port to " + this.h);
/*  33 */       paramIMinecraftServer.a("rcon.port", Integer.valueOf(this.h));
/*  34 */       if (0 == this.l.length()) {
/*  35 */         paramIMinecraftServer.a("rcon.password", "");
/*     */       }
/*  37 */       paramIMinecraftServer.a();
/*     */     } 
/*     */     
/*  40 */     if (0 == this.j.length()) {
/*  41 */       this.j = "0.0.0.0";
/*     */     }
/*     */     
/*  44 */     f();
/*  45 */     this.k = null;
/*     */   }
/*     */   
/*     */   private void f() {
/*  49 */     this.m = new HashMap<Object, Object>();
/*     */   }
/*     */   
/*     */   private void g() {
/*  53 */     Iterator<Map.Entry> iterator = this.m.entrySet().iterator();
/*  54 */     while (iterator.hasNext()) {
/*  55 */       Map.Entry entry = iterator.next();
/*  56 */       if (!((RemoteControlSession)entry.getValue()).c()) {
/*  57 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  64 */     info("RCON running on " + this.j + ":" + this.h);
/*     */     try {
/*  66 */       while (this.running) {
/*     */         
/*     */         try {
/*  69 */           Socket socket = this.k.accept();
/*  70 */           socket.setSoTimeout(500);
/*  71 */           RemoteControlSession remoteControlSession = new RemoteControlSession(this.server, socket);
/*  72 */           remoteControlSession.a();
/*  73 */           this.m.put(socket.getRemoteSocketAddress(), remoteControlSession);
/*     */ 
/*     */           
/*  76 */           g();
/*  77 */         } catch (SocketTimeoutException socketTimeoutException) {
/*     */           
/*  79 */           g();
/*  80 */         } catch (IOException iOException) {
/*  81 */           if (this.running) {
/*  82 */             info("IO: " + iOException.getMessage());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*  87 */       b(this.k);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a() {
/*  93 */     if (0 == this.l.length()) {
/*  94 */       warning("No rcon password set in '" + this.server.b() + "', rcon disabled!");
/*     */       
/*     */       return;
/*     */     } 
/*  98 */     if (0 >= this.h || 65535 < this.h) {
/*  99 */       warning("Invalid rcon port " + this.h + " found in '" + this.server.b() + "', rcon disabled!");
/*     */       
/*     */       return;
/*     */     } 
/* 103 */     if (this.running) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 108 */       this.k = new ServerSocket(this.h, 0, InetAddress.getByName(this.j));
/* 109 */       this.k.setSoTimeout(500);
/* 110 */       super.a();
/* 111 */     } catch (IOException iOException) {
/* 112 */       warning("Unable to initialise rcon on " + this.j + ":" + this.h + " : " + iOException.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RemoteControlListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */