/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.security.PrivateKey;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.util.com.google.common.base.Charsets;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.util.com.mojang.authlib.properties.Property;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ import net.minecraft.util.org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.ProtocolInjector;
/*     */ 
/*     */ public class LoginListener
/*     */   implements PacketLoginInListener {
/*  21 */   private static final AtomicInteger b = new AtomicInteger(0);
/*  22 */   private static final Logger c = LogManager.getLogger();
/*  23 */   private static final Random random = new Random();
/*  24 */   private final byte[] e = new byte[4];
/*     */   private final MinecraftServer server;
/*     */   public final NetworkManager networkManager;
/*     */   private EnumProtocolState g;
/*     */   private int h;
/*     */   private GameProfile i;
/*     */   private String j;
/*     */   private SecretKey loginKey;
/*  32 */   public String hostname = "";
/*     */   
/*     */   public LoginListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
/*  35 */     this.g = EnumProtocolState.HELLO;
/*  36 */     this.j = "";
/*  37 */     this.server = minecraftserver;
/*  38 */     this.networkManager = networkmanager;
/*  39 */     random.nextBytes(this.e);
/*     */   }
/*     */   
/*     */   public void a() {
/*  43 */     if (this.g == EnumProtocolState.READY_TO_ACCEPT) {
/*  44 */       c();
/*     */     }
/*     */     
/*  47 */     if (this.h++ == 600) {
/*  48 */       disconnect("Took too long to log in");
/*     */     }
/*     */   }
/*     */   
/*     */   public void disconnect(String s) {
/*     */     try {
/*  54 */       c.info("Disconnecting " + getName() + ": " + s);
/*  55 */       ChatComponentText chatcomponenttext = new ChatComponentText(s);
/*     */       
/*  57 */       this.networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext), new GenericFutureListener[0]);
/*  58 */       this.networkManager.close(chatcomponenttext);
/*  59 */     } catch (Exception exception) {
/*  60 */       c.error("Error whilst disconnecting player", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initUUID() {
/*     */     UUID uuid;
/*  68 */     if (this.networkManager.spoofedUUID != null) {
/*     */       
/*  70 */       uuid = this.networkManager.spoofedUUID;
/*     */     } else {
/*     */       
/*  73 */       uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.i.getName()).getBytes(Charsets.UTF_8));
/*     */     } 
/*     */     
/*  76 */     this.i = new GameProfile(uuid, this.i.getName());
/*     */     
/*  78 */     if (this.networkManager.spoofedProfile != null)
/*     */     {
/*  80 */       for (Property property : this.networkManager.spoofedProfile)
/*     */       {
/*  82 */         this.i.getProperties().put(property.getName(), property);
/*     */       }
/*     */     }
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
/*     */   public void c() {
/*  98 */     EntityPlayer s = this.server.getPlayerList().attemptLogin(this, this.i, this.hostname);
/*     */     
/* 100 */     if (s != null) {
/*     */ 
/*     */ 
/*     */       
/* 104 */       this.g = EnumProtocolState.e;
/*     */       
/* 106 */       if (this.networkManager.getVersion() >= 27)
/*     */       {
/* 108 */         this.networkManager.handle((Packet)new ProtocolInjector.PacketLoginCompression(256), new GenericFutureListener[] { new GenericFutureListener()
/*     */               {
/*     */                 
/*     */                 public void operationComplete(Future future) throws Exception
/*     */                 {
/* 113 */                   LoginListener.this.networkManager.enableCompression();
/*     */                 }
/*     */               } });
/*     */       }
/*     */       
/* 118 */       this.networkManager.handle(new PacketLoginOutSuccess(this.i), new GenericFutureListener[0]);
/* 119 */       this.server.getPlayerList().a(this.networkManager, this.server.getPlayerList().processLogin(this.i, s));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(IChatBaseComponent ichatbasecomponent) {
/* 124 */     c.info(getName() + " lost connection: " + ichatbasecomponent.c());
/*     */   }
/*     */   
/*     */   public String getName() {
/* 128 */     return (this.i != null) ? (this.i.toString() + " (" + this.networkManager.getSocketAddress().toString() + ")") : String.valueOf(this.networkManager.getSocketAddress());
/*     */   }
/*     */   
/*     */   public void a(EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
/* 132 */     Validate.validState((this.g == EnumProtocolState.e || this.g == EnumProtocolState.HELLO), "Unexpected change in protocol", new Object[0]);
/* 133 */     Validate.validState((enumprotocol1 == EnumProtocol.PLAY || enumprotocol1 == EnumProtocol.LOGIN), "Unexpected protocol " + enumprotocol1, new Object[0]);
/*     */   }
/*     */   
/*     */   public void a(PacketLoginInStart packetlogininstart) {
/* 137 */     Validate.validState((this.g == EnumProtocolState.HELLO), "Unexpected hello packet", new Object[0]);
/* 138 */     this.i = packetlogininstart.c();
/* 139 */     if (this.server.getOnlineMode() && !this.networkManager.c()) {
/* 140 */       this.g = EnumProtocolState.KEY;
/* 141 */       this.networkManager.handle(new PacketLoginOutEncryptionBegin(this.j, this.server.K().getPublic(), this.e), new GenericFutureListener[0]);
/*     */     } else {
/* 143 */       (new ThreadPlayerLookupUUID(this, "User Authenticator #" + b.incrementAndGet())).start();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(PacketLoginInEncryptionBegin packetlogininencryptionbegin) {
/* 148 */     Validate.validState((this.g == EnumProtocolState.KEY), "Unexpected key packet", new Object[0]);
/* 149 */     PrivateKey privatekey = this.server.K().getPrivate();
/*     */     
/* 151 */     if (!Arrays.equals(this.e, packetlogininencryptionbegin.b(privatekey))) {
/* 152 */       throw new IllegalStateException("Invalid nonce!");
/*     */     }
/* 154 */     this.loginKey = packetlogininencryptionbegin.a(privatekey);
/* 155 */     this.g = EnumProtocolState.AUTHENTICATING;
/* 156 */     this.networkManager.a(this.loginKey);
/* 157 */     (new ThreadPlayerLookupUUID(this, "User Authenticator #" + b.incrementAndGet())).start();
/*     */   }
/*     */ 
/*     */   
/*     */   protected GameProfile a(GameProfile gameprofile) {
/* 162 */     UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameprofile.getName()).getBytes(Charsets.UTF_8));
/*     */     
/* 164 */     return new GameProfile(uuid, gameprofile.getName());
/*     */   }
/*     */   
/*     */   static GameProfile a(LoginListener loginlistener) {
/* 168 */     return loginlistener.i;
/*     */   }
/*     */   
/*     */   static String b(LoginListener loginlistener) {
/* 172 */     return loginlistener.j;
/*     */   }
/*     */   
/*     */   static MinecraftServer c(LoginListener loginlistener) {
/* 176 */     return loginlistener.server;
/*     */   }
/*     */   
/*     */   static SecretKey d(LoginListener loginlistener) {
/* 180 */     return loginlistener.loginKey;
/*     */   }
/*     */   
/*     */   static GameProfile a(LoginListener loginlistener, GameProfile gameprofile) {
/* 184 */     return loginlistener.i = gameprofile;
/*     */   }
/*     */   
/*     */   static Logger e() {
/* 188 */     return c;
/*     */   }
/*     */   
/*     */   static EnumProtocolState a(LoginListener loginlistener, EnumProtocolState enumprotocolstate) {
/* 192 */     return loginlistener.g = enumprotocolstate;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\LoginListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */