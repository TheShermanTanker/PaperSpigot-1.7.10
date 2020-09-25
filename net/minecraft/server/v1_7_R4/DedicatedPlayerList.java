/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class DedicatedPlayerList
/*     */   extends PlayerList
/*     */ {
/*  11 */   private static final Logger g = LogManager.getLogger();
/*     */   
/*     */   public DedicatedPlayerList(DedicatedServer paramDedicatedServer) {
/*  14 */     super(paramDedicatedServer);
/*     */     
/*  16 */     a(paramDedicatedServer.a("view-distance", 10));
/*  17 */     this.maxPlayers = paramDedicatedServer.a("max-players", 20);
/*  18 */     setHasWhitelist(paramDedicatedServer.a("white-list", false));
/*     */     
/*  20 */     if (!paramDedicatedServer.N()) {
/*  21 */       getProfileBans().a(true);
/*  22 */       getIPBans().a(true);
/*     */     } 
/*     */     
/*  25 */     y();
/*  26 */     w();
/*  27 */     x();
/*  28 */     v();
/*  29 */     z();
/*  30 */     B();
/*  31 */     A();
/*  32 */     if (!getWhitelist().c().exists()) {
/*  33 */       C();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHasWhitelist(boolean paramBoolean) {
/*  39 */     super.setHasWhitelist(paramBoolean);
/*  40 */     getServer().a("white-list", Boolean.valueOf(paramBoolean));
/*  41 */     getServer().a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOp(GameProfile paramGameProfile) {
/*  46 */     super.addOp(paramGameProfile);
/*  47 */     A();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeOp(GameProfile paramGameProfile) {
/*  52 */     super.removeOp(paramGameProfile);
/*  53 */     A();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeWhitelist(GameProfile paramGameProfile) {
/*  58 */     super.removeWhitelist(paramGameProfile);
/*  59 */     C();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWhitelist(GameProfile paramGameProfile) {
/*  64 */     super.addWhitelist(paramGameProfile);
/*  65 */     C();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadWhitelist() {
/*  70 */     B();
/*     */   }
/*     */   
/*     */   private void v() {
/*     */     try {
/*  75 */       getIPBans().save();
/*  76 */     } catch (IOException iOException) {
/*  77 */       g.warn("Failed to save ip banlist: ", iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void w() {
/*     */     try {
/*  83 */       getProfileBans().save();
/*  84 */     } catch (IOException iOException) {
/*  85 */       g.warn("Failed to save user banlist: ", iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void x() {
/*     */     try {
/*  91 */       getIPBans().load();
/*  92 */     } catch (IOException iOException) {
/*  93 */       g.warn("Failed to load ip banlist: ", iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void y() {
/*     */     try {
/*  99 */       getProfileBans().load();
/* 100 */     } catch (IOException iOException) {
/* 101 */       g.warn("Failed to load user banlist: ", iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void z() {
/*     */     try {
/* 107 */       getOPs().load();
/* 108 */     } catch (Exception exception) {
/* 109 */       g.warn("Failed to load operators list: ", exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void A() {
/*     */     try {
/* 115 */       getOPs().save();
/* 116 */     } catch (Exception exception) {
/* 117 */       g.warn("Failed to save operators list: ", exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void B() {
/*     */     try {
/* 123 */       getWhitelist().load();
/* 124 */     } catch (Exception exception) {
/* 125 */       g.warn("Failed to load white-list: ", exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void C() {
/*     */     try {
/* 131 */       getWhitelist().save();
/* 132 */     } catch (Exception exception) {
/* 133 */       g.warn("Failed to save white-list: ", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWhitelisted(GameProfile paramGameProfile) {
/* 139 */     return (!getHasWhitelist() || isOp(paramGameProfile) || getWhitelist().isWhitelisted(paramGameProfile));
/*     */   }
/*     */ 
/*     */   
/*     */   public DedicatedServer getServer() {
/* 144 */     return (DedicatedServer)super.getServer();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DedicatedPlayerList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */