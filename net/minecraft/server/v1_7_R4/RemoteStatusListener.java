/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.PortUnreachableException;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RemoteStatusListener extends RemoteConnectionThread {
/*     */   private long clearedTime;
/*     */   private int bindPort;
/*     */   private int serverPort;
/*     */   private int maxPlayers;
/*     */   private String localAddress;
/*     */   private String worldName;
/*     */   private DatagramSocket socket;
/*  25 */   private byte[] o = new byte[1460];
/*     */   
/*     */   private DatagramPacket p;
/*     */   
/*     */   private Map q;
/*     */   private String hostname;
/*     */   private String motd;
/*     */   private Map challenges;
/*     */   private long u;
/*     */   private RemoteStatusReply cachedReply;
/*     */   private long cacheTime;
/*     */   
/*     */   public RemoteStatusListener(IMinecraftServer paramIMinecraftServer) {
/*  38 */     super(paramIMinecraftServer, "Query Listener");
/*     */     
/*  40 */     this.bindPort = paramIMinecraftServer.a("query.port", 0);
/*  41 */     this.motd = paramIMinecraftServer.y();
/*  42 */     this.serverPort = paramIMinecraftServer.z();
/*  43 */     this.localAddress = paramIMinecraftServer.A();
/*  44 */     this.maxPlayers = paramIMinecraftServer.D();
/*  45 */     this.worldName = paramIMinecraftServer.O();
/*     */ 
/*     */     
/*  48 */     this.cacheTime = 0L;
/*     */     
/*  50 */     this.hostname = "0.0.0.0";
/*     */ 
/*     */     
/*  53 */     if (0 == this.motd.length() || this.hostname.equals(this.motd)) {
/*     */       
/*  55 */       this.motd = "0.0.0.0";
/*     */       try {
/*  57 */         InetAddress inetAddress = InetAddress.getLocalHost();
/*  58 */         this.hostname = inetAddress.getHostAddress();
/*  59 */       } catch (UnknownHostException unknownHostException) {
/*  60 */         warning("Unable to determine local host IP, please set server-ip in '" + paramIMinecraftServer.b() + "' : " + unknownHostException.getMessage());
/*     */       } 
/*     */     } else {
/*  63 */       this.hostname = this.motd;
/*     */     } 
/*     */ 
/*     */     
/*  67 */     if (0 == this.bindPort) {
/*     */       
/*  69 */       this.bindPort = this.serverPort;
/*  70 */       info("Setting default query port to " + this.bindPort);
/*  71 */       paramIMinecraftServer.a("query.port", Integer.valueOf(this.bindPort));
/*  72 */       paramIMinecraftServer.a("debug", Boolean.valueOf(false));
/*  73 */       paramIMinecraftServer.a();
/*     */     } 
/*     */     
/*  76 */     this.q = new HashMap<Object, Object>();
/*  77 */     this.cachedReply = new RemoteStatusReply(1460);
/*  78 */     this.challenges = new HashMap<Object, Object>();
/*  79 */     this.u = (new Date()).getTime();
/*     */   }
/*     */   
/*     */   private void send(byte[] paramArrayOfbyte, DatagramPacket paramDatagramPacket) {
/*  83 */     this.socket.send(new DatagramPacket(paramArrayOfbyte, paramArrayOfbyte.length, paramDatagramPacket.getSocketAddress()));
/*     */   }
/*     */   private boolean parsePacket(DatagramPacket paramDatagramPacket) {
/*     */     RemoteStatusReply remoteStatusReply;
/*  87 */     byte[] arrayOfByte = paramDatagramPacket.getData();
/*  88 */     int i = paramDatagramPacket.getLength();
/*  89 */     SocketAddress socketAddress = paramDatagramPacket.getSocketAddress();
/*  90 */     debug("Packet len " + i + " [" + socketAddress + "]");
/*  91 */     if (3 > i || -2 != arrayOfByte[0] || -3 != arrayOfByte[1]) {
/*     */       
/*  93 */       debug("Invalid packet [" + socketAddress + "]");
/*  94 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  98 */     debug("Packet '" + StatusChallengeUtils.a(arrayOfByte[2]) + "' [" + socketAddress + "]");
/*  99 */     switch (arrayOfByte[2]) {
/*     */       
/*     */       case 9:
/* 102 */         createChallenge(paramDatagramPacket);
/* 103 */         debug("Challenge [" + socketAddress + "]");
/* 104 */         return true;
/*     */ 
/*     */       
/*     */       case 0:
/* 108 */         if (!hasChallenged(paramDatagramPacket).booleanValue()) {
/* 109 */           debug("Invalid challenge [" + socketAddress + "]");
/* 110 */           return false;
/*     */         } 
/*     */         
/* 113 */         if (15 == i) {
/*     */           
/* 115 */           send(getFullReply(paramDatagramPacket), paramDatagramPacket);
/* 116 */           debug("Rules [" + socketAddress + "]");
/*     */           break;
/*     */         } 
/* 119 */         remoteStatusReply = new RemoteStatusReply(1460);
/* 120 */         remoteStatusReply.write(0);
/* 121 */         remoteStatusReply.write(getIdentityToken(paramDatagramPacket.getSocketAddress()));
/* 122 */         remoteStatusReply.write(this.localAddress);
/* 123 */         remoteStatusReply.write("SMP");
/* 124 */         remoteStatusReply.write(this.worldName);
/* 125 */         remoteStatusReply.write(Integer.toString(d()));
/* 126 */         remoteStatusReply.write(Integer.toString(this.maxPlayers));
/* 127 */         remoteStatusReply.write((short)this.serverPort);
/* 128 */         remoteStatusReply.write(this.hostname);
/*     */         
/* 130 */         send(remoteStatusReply.getBytes(), paramDatagramPacket);
/* 131 */         debug("Status [" + socketAddress + "]");
/*     */         break;
/*     */     } 
/*     */     
/* 135 */     return true;
/*     */   }
/*     */   
/*     */   private byte[] getFullReply(DatagramPacket paramDatagramPacket) {
/* 139 */     long l = MinecraftServer.ar();
/* 140 */     if (l < this.cacheTime + 5000L) {
/*     */       
/* 142 */       byte[] arrayOfByte1 = this.cachedReply.getBytes();
/* 143 */       byte[] arrayOfByte2 = getIdentityToken(paramDatagramPacket.getSocketAddress());
/* 144 */       arrayOfByte1[1] = arrayOfByte2[0];
/* 145 */       arrayOfByte1[2] = arrayOfByte2[1];
/* 146 */       arrayOfByte1[3] = arrayOfByte2[2];
/* 147 */       arrayOfByte1[4] = arrayOfByte2[3];
/*     */       
/* 149 */       return arrayOfByte1;
/*     */     } 
/*     */     
/* 152 */     this.cacheTime = l;
/*     */     
/* 154 */     this.cachedReply.reset();
/* 155 */     this.cachedReply.write(0);
/* 156 */     this.cachedReply.write(getIdentityToken(paramDatagramPacket.getSocketAddress()));
/* 157 */     this.cachedReply.write("splitnum");
/* 158 */     this.cachedReply.write(128);
/* 159 */     this.cachedReply.write(0);
/*     */ 
/*     */     
/* 162 */     this.cachedReply.write("hostname");
/* 163 */     this.cachedReply.write(this.localAddress);
/* 164 */     this.cachedReply.write("gametype");
/* 165 */     this.cachedReply.write("SMP");
/* 166 */     this.cachedReply.write("game_id");
/* 167 */     this.cachedReply.write("MINECRAFT");
/* 168 */     this.cachedReply.write("version");
/* 169 */     this.cachedReply.write(this.server.getVersion());
/* 170 */     this.cachedReply.write("plugins");
/* 171 */     this.cachedReply.write(this.server.getPlugins());
/* 172 */     this.cachedReply.write("map");
/* 173 */     this.cachedReply.write(this.worldName);
/* 174 */     this.cachedReply.write("numplayers");
/* 175 */     this.cachedReply.write("" + d());
/* 176 */     this.cachedReply.write("maxplayers");
/* 177 */     this.cachedReply.write("" + this.maxPlayers);
/* 178 */     this.cachedReply.write("hostport");
/* 179 */     this.cachedReply.write("" + this.serverPort);
/* 180 */     this.cachedReply.write("hostip");
/* 181 */     this.cachedReply.write(this.hostname);
/* 182 */     this.cachedReply.write(0);
/* 183 */     this.cachedReply.write(1);
/*     */ 
/*     */ 
/*     */     
/* 187 */     this.cachedReply.write("player_");
/* 188 */     this.cachedReply.write(0);
/*     */     
/* 190 */     String[] arrayOfString = this.server.getPlayers();
/* 191 */     for (String str : arrayOfString) {
/* 192 */       this.cachedReply.write(str);
/*     */     }
/* 194 */     this.cachedReply.write(0);
/*     */     
/* 196 */     return this.cachedReply.getBytes();
/*     */   }
/*     */   
/*     */   private byte[] getIdentityToken(SocketAddress paramSocketAddress) {
/* 200 */     return ((RemoteStatusChallenge)this.challenges.get(paramSocketAddress)).getIdentityToken();
/*     */   }
/*     */   
/*     */   private Boolean hasChallenged(DatagramPacket paramDatagramPacket) {
/* 204 */     SocketAddress socketAddress = paramDatagramPacket.getSocketAddress();
/* 205 */     if (!this.challenges.containsKey(socketAddress))
/*     */     {
/* 207 */       return Boolean.valueOf(false);
/*     */     }
/*     */     
/* 210 */     byte[] arrayOfByte = paramDatagramPacket.getData();
/* 211 */     if (((RemoteStatusChallenge)this.challenges.get(socketAddress)).getToken() != StatusChallengeUtils.c(arrayOfByte, 7, paramDatagramPacket.getLength()))
/*     */     {
/* 213 */       return Boolean.valueOf(false);
/*     */     }
/*     */ 
/*     */     
/* 217 */     return Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   private void createChallenge(DatagramPacket paramDatagramPacket) {
/* 221 */     RemoteStatusChallenge remoteStatusChallenge = new RemoteStatusChallenge(this, paramDatagramPacket);
/* 222 */     this.challenges.put(paramDatagramPacket.getSocketAddress(), remoteStatusChallenge);
/*     */     
/* 224 */     send(remoteStatusChallenge.getChallengeResponse(), paramDatagramPacket);
/*     */   }
/*     */   
/*     */   private void cleanChallenges() {
/* 228 */     if (!this.running) {
/*     */       return;
/*     */     }
/*     */     
/* 232 */     long l = MinecraftServer.ar();
/* 233 */     if (l < this.clearedTime + 30000L) {
/*     */       return;
/*     */     }
/* 236 */     this.clearedTime = l;
/*     */     
/* 238 */     Iterator<Map.Entry> iterator = this.challenges.entrySet().iterator();
/* 239 */     while (iterator.hasNext()) {
/* 240 */       Map.Entry entry = iterator.next();
/* 241 */       if (((RemoteStatusChallenge)entry.getValue()).isExpired(l).booleanValue()) {
/* 242 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 249 */     info("Query running on " + this.motd + ":" + this.bindPort);
/* 250 */     this.clearedTime = MinecraftServer.ar();
/* 251 */     this.p = new DatagramPacket(this.o, this.o.length);
/*     */     
/*     */     try {
/* 254 */       while (this.running) {
/*     */         try {
/* 256 */           this.socket.receive(this.p);
/*     */ 
/*     */           
/* 259 */           cleanChallenges();
/*     */ 
/*     */           
/* 262 */           parsePacket(this.p);
/* 263 */         } catch (SocketTimeoutException socketTimeoutException) {
/*     */           
/* 265 */           cleanChallenges();
/* 266 */         } catch (PortUnreachableException portUnreachableException) {
/*     */         
/* 268 */         } catch (IOException iOException) {
/*     */           
/* 270 */           a(iOException);
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 274 */       e();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a() {
/* 280 */     if (this.running) {
/*     */       return;
/*     */     }
/*     */     
/* 284 */     if (0 >= this.bindPort || 65535 < this.bindPort) {
/* 285 */       warning("Invalid query port " + this.bindPort + " found in '" + this.server.b() + "' (queries disabled)");
/*     */       
/*     */       return;
/*     */     } 
/* 289 */     if (g()) {
/* 290 */       super.a();
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(Exception paramException) {
/* 295 */     if (!this.running) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 300 */     warning("Unexpected exception, buggy JRE? (" + paramException.toString() + ")");
/*     */ 
/*     */     
/* 303 */     if (!g()) {
/* 304 */       error("Failed to recover from buggy JRE, shutting down!");
/* 305 */       this.running = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean g() {
/*     */     try {
/* 311 */       this.socket = new DatagramSocket(this.bindPort, InetAddress.getByName(this.motd));
/* 312 */       a(this.socket);
/* 313 */       this.socket.setSoTimeout(500);
/* 314 */       return true;
/* 315 */     } catch (SocketException socketException) {
/* 316 */       warning("Unable to initialise query system on " + this.motd + ":" + this.bindPort + " (Socket): " + socketException.getMessage());
/* 317 */     } catch (UnknownHostException unknownHostException) {
/* 318 */       warning("Unable to initialise query system on " + this.motd + ":" + this.bindPort + " (Unknown Host): " + unknownHostException.getMessage());
/* 319 */     } catch (Exception exception) {
/* 320 */       warning("Unable to initialise query system on " + this.motd + ":" + this.bindPort + " (E): " + exception.getMessage());
/*     */     } 
/*     */     
/* 323 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RemoteStatusListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */