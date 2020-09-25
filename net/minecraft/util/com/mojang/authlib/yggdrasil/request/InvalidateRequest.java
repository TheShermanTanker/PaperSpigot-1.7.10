/*    */ package net.minecraft.util.com.mojang.authlib.yggdrasil.request;
/*    */ 
/*    */ import net.minecraft.util.com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*    */ 
/*    */ public class InvalidateRequest {
/*    */   private String accessToken;
/*    */   private String clientToken;
/*    */   
/*    */   public InvalidateRequest(YggdrasilUserAuthentication authenticationService) {
/* 10 */     this.accessToken = authenticationService.getAuthenticatedToken();
/* 11 */     this.clientToken = authenticationService.getAuthenticationService().getClientToken();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\yggdrasil\request\InvalidateRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */