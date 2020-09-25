/*    */ package net.minecraft.util.com.mojang.authlib.minecraft;
/*    */ import net.minecraft.util.com.mojang.authlib.AuthenticationService;
/*    */ import net.minecraft.util.com.mojang.authlib.HttpAuthenticationService;
/*    */ 
/*    */ public abstract class HttpMinecraftSessionService extends BaseMinecraftSessionService {
/*    */   protected HttpMinecraftSessionService(HttpAuthenticationService authenticationService) {
/*  7 */     super((AuthenticationService)authenticationService);
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpAuthenticationService getAuthenticationService() {
/* 12 */     return (HttpAuthenticationService)super.getAuthenticationService();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\minecraft\HttpMinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */