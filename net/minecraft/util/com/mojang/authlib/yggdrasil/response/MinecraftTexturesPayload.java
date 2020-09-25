/*    */ package net.minecraft.util.com.mojang.authlib.yggdrasil.response;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*    */ 
/*    */ public class MinecraftTexturesPayload
/*    */ {
/*    */   private long timestamp;
/*    */   private UUID profileId;
/*    */   private String profileName;
/*    */   private boolean isPublic;
/*    */   private Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textures;
/*    */   
/*    */   public long getTimestamp() {
/* 16 */     return this.timestamp;
/*    */   }
/*    */   
/*    */   public UUID getProfileId() {
/* 20 */     return this.profileId;
/*    */   }
/*    */   
/*    */   public String getProfileName() {
/* 24 */     return this.profileName;
/*    */   }
/*    */   
/*    */   public boolean isPublic() {
/* 28 */     return this.isPublic;
/*    */   }
/*    */   
/*    */   public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures() {
/* 32 */     return this.textures;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\yggdrasil\response\MinecraftTexturesPayload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */