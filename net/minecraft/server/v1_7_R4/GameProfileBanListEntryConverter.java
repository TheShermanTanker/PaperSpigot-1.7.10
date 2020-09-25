/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.util.com.mojang.authlib.ProfileLookupCallback;
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
/*     */ final class GameProfileBanListEntryConverter
/*     */   implements ProfileLookupCallback
/*     */ {
/*     */   GameProfileBanListEntryConverter(MinecraftServer paramMinecraftServer, Map paramMap, GameProfileBanList paramGameProfileBanList) {}
/*     */   
/*     */   public void onProfileLookupSucceeded(GameProfile paramGameProfile) {
/*  85 */     this.a.getUserCache().a(paramGameProfile);
/*  86 */     String[] arrayOfString = (String[])this.b.get(paramGameProfile.getName().toLowerCase(Locale.ROOT));
/*  87 */     if (arrayOfString == null) {
/*  88 */       NameReferencingFileConverter.a().warn("Could not convert user banlist entry for " + paramGameProfile.getName());
/*  89 */       throw new FileConversionException("Profile not in the conversionlist", null);
/*     */     } 
/*     */     
/*  92 */     Date date1 = (arrayOfString.length > 1) ? NameReferencingFileConverter.a(arrayOfString[1], (Date)null) : null;
/*  93 */     String str1 = (arrayOfString.length > 2) ? arrayOfString[2] : null;
/*  94 */     Date date2 = (arrayOfString.length > 3) ? NameReferencingFileConverter.a(arrayOfString[3], (Date)null) : null;
/*  95 */     String str2 = (arrayOfString.length > 4) ? arrayOfString[4] : null;
/*  96 */     this.c.add(new GameProfileBanEntry(paramGameProfile, date1, str1, date2, str2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProfileLookupFailed(GameProfile paramGameProfile, Exception paramException) {
/* 101 */     NameReferencingFileConverter.a().warn("Could not lookup user banlist entry for " + paramGameProfile.getName(), paramException);
/* 102 */     if (!(paramException instanceof net.minecraft.util.com.mojang.authlib.yggdrasil.ProfileNotFoundException))
/* 103 */       throw new FileConversionException("Could not request user " + paramGameProfile.getName() + " from backend systems", paramException, null); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GameProfileBanListEntryConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */