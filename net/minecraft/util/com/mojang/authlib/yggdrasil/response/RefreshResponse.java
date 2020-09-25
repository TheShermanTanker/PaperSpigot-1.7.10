/*    */ package net.minecraft.util.com.mojang.authlib.yggdrasil.response;
/*    */ 
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class RefreshResponse extends Response {
/*    */   private String accessToken;
/*    */   private String clientToken;
/*    */   private GameProfile selectedProfile;
/*    */   private GameProfile[] availableProfiles;
/*    */   private User user;
/*    */   
/*    */   public String getAccessToken() {
/* 13 */     return this.accessToken;
/*    */   }
/*    */   
/*    */   public String getClientToken() {
/* 17 */     return this.clientToken;
/*    */   }
/*    */   
/*    */   public GameProfile[] getAvailableProfiles() {
/* 21 */     return this.availableProfiles;
/*    */   }
/*    */   
/*    */   public GameProfile getSelectedProfile() {
/* 25 */     return this.selectedProfile;
/*    */   }
/*    */   
/*    */   public User getUser() {
/* 29 */     return this.user;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\yggdrasil\response\RefreshResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */