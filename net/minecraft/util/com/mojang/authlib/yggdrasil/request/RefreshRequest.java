/*    */ package net.minecraft.util.com.mojang.authlib.yggdrasil.request;
/*    */ 
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.util.com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*    */ 
/*    */ public class RefreshRequest {
/*    */   private String clientToken;
/*    */   private String accessToken;
/*    */   private GameProfile selectedProfile;
/*    */   private boolean requestUser = true;
/*    */   
/*    */   public RefreshRequest(YggdrasilUserAuthentication authenticationService) {
/* 13 */     this(authenticationService, null);
/*    */   }
/*    */   
/*    */   public RefreshRequest(YggdrasilUserAuthentication authenticationService, GameProfile profile) {
/* 17 */     this.clientToken = authenticationService.getAuthenticationService().getClientToken();
/* 18 */     this.accessToken = authenticationService.getAuthenticatedToken();
/* 19 */     this.selectedProfile = profile;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\yggdrasil\request\RefreshRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */