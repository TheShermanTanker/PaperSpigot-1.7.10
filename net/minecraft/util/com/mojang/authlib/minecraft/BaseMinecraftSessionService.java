/*    */ package net.minecraft.util.com.mojang.authlib.minecraft;
/*    */ 
/*    */ import net.minecraft.util.com.mojang.authlib.AuthenticationService;
/*    */ 
/*    */ public abstract class BaseMinecraftSessionService implements MinecraftSessionService {
/*    */   private final AuthenticationService authenticationService;
/*    */   
/*    */   protected BaseMinecraftSessionService(AuthenticationService authenticationService) {
/*  9 */     this.authenticationService = authenticationService;
/*    */   }
/*    */   
/*    */   public AuthenticationService getAuthenticationService() {
/* 13 */     return this.authenticationService;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\minecraft\BaseMinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */