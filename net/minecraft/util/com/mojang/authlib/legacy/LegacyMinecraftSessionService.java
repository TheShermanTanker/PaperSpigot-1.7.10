/*    */ package net.minecraft.util.com.mojang.authlib.legacy;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.com.mojang.authlib.AuthenticationService;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.util.com.mojang.authlib.HttpAuthenticationService;
/*    */ import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationException;
/*    */ import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*    */ import net.minecraft.util.com.mojang.authlib.minecraft.HttpMinecraftSessionService;
/*    */ import net.minecraft.util.com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*    */ 
/*    */ public class LegacyMinecraftSessionService
/*    */   extends HttpMinecraftSessionService {
/*    */   private static final String BASE_URL = "http://session.minecraft.net/game/";
/* 18 */   private static final URL JOIN_URL = HttpAuthenticationService.constantURL("http://session.minecraft.net/game/joinserver.jsp");
/* 19 */   private static final URL CHECK_URL = HttpAuthenticationService.constantURL("http://session.minecraft.net/game/checkserver.jsp");
/*    */   
/*    */   protected LegacyMinecraftSessionService(LegacyAuthenticationService authenticationService) {
/* 22 */     super(authenticationService);
/*    */   }
/*    */ 
/*    */   
/*    */   public void joinServer(GameProfile profile, String authenticationToken, String serverId) throws AuthenticationException {
/* 27 */     Map<String, Object> arguments = new HashMap<String, Object>();
/*    */     
/* 29 */     arguments.put("user", profile.getName());
/* 30 */     arguments.put("sessionId", authenticationToken);
/* 31 */     arguments.put("serverId", serverId);
/*    */     
/* 33 */     URL url = HttpAuthenticationService.concatenateURL(JOIN_URL, HttpAuthenticationService.buildQuery(arguments));
/*    */     
/*    */     try {
/* 36 */       String response = getAuthenticationService().performGetRequest(url);
/*    */       
/* 38 */       if (!response.equals("OK")) {
/* 39 */         throw new AuthenticationException(response);
/*    */       }
/* 41 */     } catch (IOException e) {
/* 42 */       throw new AuthenticationUnavailableException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfile hasJoinedServer(GameProfile user, String serverId) throws AuthenticationUnavailableException {
/* 48 */     Map<String, Object> arguments = new HashMap<String, Object>();
/*    */     
/* 50 */     arguments.put("user", user.getName());
/* 51 */     arguments.put("serverId", serverId);
/*    */     
/* 53 */     URL url = HttpAuthenticationService.concatenateURL(CHECK_URL, HttpAuthenticationService.buildQuery(arguments));
/*    */     
/*    */     try {
/* 56 */       String response = getAuthenticationService().performGetRequest(url);
/*    */       
/* 58 */       return response.equals("YES") ? user : null;
/* 59 */     } catch (IOException e) {
/* 60 */       throw new AuthenticationUnavailableException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile profile, boolean requireSecure) {
/* 66 */     return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfile fillProfileProperties(GameProfile profile, boolean requireSecure) {
/* 71 */     return profile;
/*    */   }
/*    */ 
/*    */   
/*    */   public LegacyAuthenticationService getAuthenticationService() {
/* 76 */     return (LegacyAuthenticationService)super.getAuthenticationService();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\legacy\LegacyMinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */