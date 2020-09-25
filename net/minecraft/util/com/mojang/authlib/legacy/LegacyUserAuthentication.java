/*     */ package net.minecraft.util.com.mojang.authlib.legacy;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.com.mojang.authlib.AuthenticationService;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.util.com.mojang.authlib.HttpAuthenticationService;
/*     */ import net.minecraft.util.com.mojang.authlib.HttpUserAuthentication;
/*     */ import net.minecraft.util.com.mojang.authlib.UserType;
/*     */ import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import net.minecraft.util.com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import net.minecraft.util.com.mojang.util.UUIDTypeAdapter;
/*     */ import net.minecraft.util.org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class LegacyUserAuthentication extends HttpUserAuthentication {
/*  18 */   private static final URL AUTHENTICATION_URL = HttpAuthenticationService.constantURL("https://login.minecraft.net");
/*     */   
/*     */   private static final int AUTHENTICATION_VERSION = 14;
/*     */   
/*     */   private static final int RESPONSE_PART_PROFILE_NAME = 2;
/*     */   
/*     */   private static final int RESPONSE_PART_SESSION_TOKEN = 3;
/*     */   
/*     */   private static final int RESPONSE_PART_PROFILE_ID = 4;
/*     */   private String sessionToken;
/*     */   
/*     */   protected LegacyUserAuthentication(LegacyAuthenticationService authenticationService) {
/*  30 */     super(authenticationService);
/*     */   }
/*     */   
/*     */   public void logIn() throws AuthenticationException {
/*     */     String response;
/*  35 */     if (StringUtils.isBlank(getUsername())) {
/*  36 */       throw new InvalidCredentialsException("Invalid username");
/*     */     }
/*  38 */     if (StringUtils.isBlank(getPassword())) {
/*  39 */       throw new InvalidCredentialsException("Invalid password");
/*     */     }
/*     */     
/*  42 */     Map<String, Object> args = new HashMap<String, Object>();
/*  43 */     args.put("user", getUsername());
/*  44 */     args.put("password", getPassword());
/*  45 */     args.put("version", Integer.valueOf(14));
/*     */ 
/*     */     
/*     */     try {
/*  49 */       response = getAuthenticationService().performPostRequest(AUTHENTICATION_URL, HttpAuthenticationService.buildQuery(args), "application/x-www-form-urlencoded").trim();
/*  50 */     } catch (IOException e) {
/*  51 */       throw new AuthenticationException("Authentication server is not responding", e);
/*     */     } 
/*     */     
/*  54 */     String[] split = response.split(":");
/*     */     
/*  56 */     if (split.length == 5) {
/*  57 */       String profileId = split[4];
/*  58 */       String profileName = split[2];
/*  59 */       String sessionToken = split[3];
/*     */       
/*  61 */       if (StringUtils.isBlank(profileId) || StringUtils.isBlank(profileName) || StringUtils.isBlank(sessionToken)) {
/*  62 */         throw new AuthenticationException("Unknown response from authentication server: " + response);
/*     */       }
/*     */       
/*  65 */       setSelectedProfile(new GameProfile(UUIDTypeAdapter.fromString(profileId), profileName));
/*  66 */       this.sessionToken = sessionToken;
/*  67 */       setUserType(UserType.LEGACY);
/*     */     } else {
/*  69 */       throw new InvalidCredentialsException(response);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void logOut() {
/*  75 */     super.logOut();
/*  76 */     this.sessionToken = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlayOnline() {
/*  81 */     return (isLoggedIn() && getSelectedProfile() != null && getAuthenticatedToken() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile[] getAvailableProfiles() {
/*  86 */     if (getSelectedProfile() != null) {
/*  87 */       return new GameProfile[] { getSelectedProfile() };
/*     */     }
/*  89 */     return new GameProfile[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectGameProfile(GameProfile profile) throws AuthenticationException {
/* 100 */     throw new UnsupportedOperationException("Game profiles cannot be changed in the legacy authentication service");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAuthenticatedToken() {
/* 105 */     return this.sessionToken;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUserID() {
/* 110 */     return getUsername();
/*     */   }
/*     */ 
/*     */   
/*     */   public LegacyAuthenticationService getAuthenticationService() {
/* 115 */     return (LegacyAuthenticationService)super.getAuthenticationService();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\legacy\LegacyUserAuthentication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */