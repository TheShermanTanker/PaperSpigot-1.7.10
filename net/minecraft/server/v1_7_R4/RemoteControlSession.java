/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RemoteControlSession
/*     */   extends RemoteConnectionThread
/*     */ {
/*  20 */   private static final Logger h = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private boolean i;
/*     */ 
/*     */   
/*     */   private Socket j;
/*     */   
/*  28 */   private byte[] k = new byte[1460];
/*     */   private String l;
/*     */   
/*     */   RemoteControlSession(IMinecraftServer paramIMinecraftServer, Socket paramSocket) {
/*  32 */     super(paramIMinecraftServer, "RCON Client");
/*  33 */     this.j = paramSocket;
/*     */     
/*     */     try {
/*  36 */       this.j.setSoTimeout(0);
/*  37 */     } catch (Exception exception) {
/*  38 */       this.running = false;
/*     */     } 
/*     */     
/*  41 */     this.l = paramIMinecraftServer.a("rcon.password", "");
/*  42 */     info("Rcon connection from: " + paramSocket.getInetAddress());
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  48 */       while (this.running) {
/*  49 */         String str; BufferedInputStream bufferedInputStream = new BufferedInputStream(this.j.getInputStream());
/*  50 */         int i = bufferedInputStream.read(this.k, 0, 1460);
/*     */         
/*  52 */         if (10 > i) {
/*     */           return;
/*     */         }
/*     */         
/*  56 */         int j = 0;
/*  57 */         int k = StatusChallengeUtils.b(this.k, 0, i);
/*  58 */         if (k != i - 4) {
/*     */           return;
/*     */         }
/*     */         
/*  62 */         j += true;
/*  63 */         int m = StatusChallengeUtils.b(this.k, j, i);
/*  64 */         j += true;
/*     */         
/*  66 */         int n = StatusChallengeUtils.b(this.k, j);
/*  67 */         j += true;
/*  68 */         switch (n) {
/*     */           case 3:
/*  70 */             str = StatusChallengeUtils.a(this.k, j, i);
/*  71 */             j += str.length();
/*  72 */             if (0 != str.length() && str.equals(this.l)) {
/*  73 */               this.i = true;
/*  74 */               a(m, 2, ""); continue;
/*     */             } 
/*  76 */             this.i = false;
/*  77 */             f();
/*     */             continue;
/*     */           
/*     */           case 2:
/*  81 */             if (this.i) {
/*  82 */               String str1 = StatusChallengeUtils.a(this.k, j, i);
/*     */               try {
/*  84 */                 a(m, this.server.g(str1));
/*  85 */               } catch (Exception exception) {
/*  86 */                 a(m, "Error executing: " + str1 + " (" + exception.getMessage() + ")");
/*     */               }  continue;
/*     */             } 
/*  89 */             f();
/*     */             continue;
/*     */         } 
/*     */         
/*  93 */         a(m, String.format("Unknown request %s", new Object[] { Integer.toHexString(n) }));
/*     */       }
/*     */     
/*  96 */     } catch (SocketTimeoutException socketTimeoutException) {
/*     */     
/*  98 */     } catch (IOException iOException) {
/*     */     
/* 100 */     } catch (Exception exception) {
/* 101 */       h.error("Exception whilst parsing RCON input", exception);
/*     */     } finally {
/* 103 */       g();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void a(int paramInt1, int paramInt2, String paramString) {
/* 110 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1248);
/* 111 */     DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
/* 112 */     byte[] arrayOfByte = paramString.getBytes("UTF-8");
/* 113 */     dataOutputStream.writeInt(Integer.reverseBytes(arrayOfByte.length + 10));
/* 114 */     dataOutputStream.writeInt(Integer.reverseBytes(paramInt1));
/* 115 */     dataOutputStream.writeInt(Integer.reverseBytes(paramInt2));
/* 116 */     dataOutputStream.write(arrayOfByte);
/* 117 */     dataOutputStream.write(0);
/* 118 */     dataOutputStream.write(0);
/* 119 */     this.j.getOutputStream().write(byteArrayOutputStream.toByteArray());
/*     */   }
/*     */   
/*     */   private void f() {
/* 123 */     a(-1, 2, "");
/*     */   }
/*     */   
/*     */   private void a(int paramInt, String paramString) {
/* 127 */     int i = paramString.length();
/*     */     
/*     */     do {
/* 130 */       boolean bool = (4096 <= i) ? true : i;
/* 131 */       a(paramInt, 0, paramString.substring(0, bool));
/* 132 */       paramString = paramString.substring(bool);
/* 133 */       i = paramString.length();
/* 134 */     } while (0 != i);
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
/*     */   private void g() {
/* 147 */     if (null == this.j) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 152 */       this.j.close();
/* 153 */     } catch (IOException iOException) {
/* 154 */       warning("IO: " + iOException.getMessage());
/*     */     } 
/* 156 */     this.j = null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RemoteControlSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */