/*     */ package net.minecraft.util.com.mojang.authlib.yggdrasil;
/*     */ import java.net.URL;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.com.google.common.collect.Iterables;
/*     */ import net.minecraft.util.com.google.common.collect.Multimap;
/*     */ import net.minecraft.util.com.google.gson.Gson;
/*     */ import net.minecraft.util.com.google.gson.GsonBuilder;
/*     */ import net.minecraft.util.com.google.gson.JsonParseException;
/*     */ import net.minecraft.util.com.mojang.authlib.AuthenticationService;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.util.com.mojang.authlib.HttpAuthenticationService;
/*     */ import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import net.minecraft.util.com.mojang.authlib.minecraft.InsecureTextureException;
/*     */ import net.minecraft.util.com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import net.minecraft.util.com.mojang.authlib.properties.Property;
/*     */ import net.minecraft.util.com.mojang.authlib.yggdrasil.request.JoinMinecraftServerRequest;
/*     */ import net.minecraft.util.com.mojang.authlib.yggdrasil.response.HasJoinedMinecraftServerResponse;
/*     */ import net.minecraft.util.com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;
/*     */ import net.minecraft.util.com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
/*     */ import net.minecraft.util.com.mojang.authlib.yggdrasil.response.Response;
/*     */ import net.minecraft.util.com.mojang.util.UUIDTypeAdapter;
/*     */ import net.minecraft.util.org.apache.commons.codec.Charsets;
/*     */ import net.minecraft.util.org.apache.commons.codec.binary.Base64;
/*     */ import net.minecraft.util.org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class YggdrasilMinecraftSessionService extends HttpMinecraftSessionService {
/*  34 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";
/*  36 */   private static final URL JOIN_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/join");
/*  37 */   private static final URL CHECK_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/hasJoined");
/*     */   
/*     */   private final PublicKey publicKey;
/*  40 */   private final Gson gson = (new GsonBuilder()).registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
/*     */   
/*     */   protected YggdrasilMinecraftSessionService(YggdrasilAuthenticationService authenticationService) {
/*  43 */     super(authenticationService);
/*     */     
/*     */     try {
/*  46 */       X509EncodedKeySpec spec = new X509EncodedKeySpec(IOUtils.toByteArray(YggdrasilMinecraftSessionService.class.getResourceAsStream("/yggdrasil_session_pubkey.der")));
/*  47 */       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
/*  48 */       this.publicKey = keyFactory.generatePublic(spec);
/*  49 */     } catch (Exception e) {
/*  50 */       throw new Error("Missing/invalid yggdrasil public key!");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void joinServer(GameProfile profile, String authenticationToken, String serverId) throws AuthenticationException {
/*  56 */     JoinMinecraftServerRequest request = new JoinMinecraftServerRequest();
/*  57 */     request.accessToken = authenticationToken;
/*  58 */     request.selectedProfile = profile.getId();
/*  59 */     request.serverId = serverId;
/*     */     
/*  61 */     getAuthenticationService().makeRequest(JOIN_URL, request, Response.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile hasJoinedServer(GameProfile user, String serverId) throws AuthenticationUnavailableException {
/*  66 */     Map<String, Object> arguments = new HashMap<String, Object>();
/*     */     
/*  68 */     arguments.put("username", user.getName());
/*  69 */     arguments.put("serverId", serverId);
/*     */     
/*  71 */     URL url = HttpAuthenticationService.concatenateURL(CHECK_URL, HttpAuthenticationService.buildQuery(arguments));
/*     */     
/*     */     try {
/*  74 */       HasJoinedMinecraftServerResponse response = getAuthenticationService().<HasJoinedMinecraftServerResponse>makeRequest(url, null, HasJoinedMinecraftServerResponse.class);
/*     */       
/*  76 */       if (response != null && response.getId() != null) {
/*  77 */         GameProfile result = new GameProfile(response.getId(), user.getName());
/*     */         
/*  79 */         if (response.getProperties() != null) {
/*  80 */           result.getProperties().putAll((Multimap)response.getProperties());
/*     */         }
/*     */         
/*  83 */         return result;
/*     */       } 
/*  85 */       return null;
/*     */     }
/*  87 */     catch (AuthenticationUnavailableException e) {
/*  88 */       throw e;
/*  89 */     } catch (AuthenticationException e) {
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile profile, boolean requireSecure) {
/*     */     MinecraftTexturesPayload result;
/*  96 */     Property textureProperty = (Property)Iterables.getFirst(profile.getProperties().get("textures"), null);
/*     */     
/*  98 */     if (textureProperty == null) {
/*  99 */       return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
/*     */     }
/*     */     
/* 102 */     if (requireSecure) {
/* 103 */       if (!textureProperty.hasSignature()) {
/* 104 */         LOGGER.error("Signature is missing from textures payload");
/* 105 */         throw new InsecureTextureException("Signature is missing from textures payload");
/*     */       } 
/*     */       
/* 108 */       if (!textureProperty.isSignatureValid(this.publicKey)) {
/* 109 */         LOGGER.error("Textures payload has been tampered with (signature invalid)");
/* 110 */         throw new InsecureTextureException("Textures payload has been tampered with (signature invalid)");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 116 */       String json = new String(Base64.decodeBase64(textureProperty.getValue()), Charsets.UTF_8);
/* 117 */       result = (MinecraftTexturesPayload)this.gson.fromJson(json, MinecraftTexturesPayload.class);
/* 118 */     } catch (JsonParseException e) {
/* 119 */       LOGGER.error("Could not decode textures payload", (Throwable)e);
/* 120 */       return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
/*     */     } 
/*     */     
/* 123 */     return (result.getTextures() == null) ? new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>() : result.getTextures();
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile fillProfileProperties(GameProfile profile, boolean requireSecure) {
/* 128 */     if (profile.getId() == null) {
/* 129 */       return profile;
/*     */     }
/*     */     
/*     */     try {
/* 133 */       URL url = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/profile/" + UUIDTypeAdapter.fromUUID(profile.getId()));
/* 134 */       url = HttpAuthenticationService.concatenateURL(url, "unsigned=" + (!requireSecure ? 1 : 0));
/* 135 */       MinecraftProfilePropertiesResponse response = getAuthenticationService().<MinecraftProfilePropertiesResponse>makeRequest(url, null, MinecraftProfilePropertiesResponse.class);
/*     */       
/* 137 */       if (response == null) {
/* 138 */         LOGGER.debug("Couldn't fetch profile properties for " + profile + " as the profile does not exist");
/* 139 */         return profile;
/*     */       } 
/* 141 */       GameProfile result = new GameProfile(response.getId(), response.getName());
/* 142 */       result.getProperties().putAll((Multimap)response.getProperties());
/* 143 */       profile.getProperties().putAll((Multimap)response.getProperties());
/* 144 */       LOGGER.debug("Successfully fetched profile properties for " + profile);
/* 145 */       return result;
/*     */     }
/* 147 */     catch (AuthenticationException e) {
/* 148 */       LOGGER.warn("Couldn't look up profile properties for " + profile, (Throwable)e);
/* 149 */       return profile;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public YggdrasilAuthenticationService getAuthenticationService() {
/* 155 */     return (YggdrasilAuthenticationService)super.getAuthenticationService();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\yggdrasil\YggdrasilMinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */