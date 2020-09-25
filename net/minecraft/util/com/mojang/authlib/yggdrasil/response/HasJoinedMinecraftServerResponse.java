/*    */ package net.minecraft.util.com.mojang.authlib.yggdrasil.response;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.mojang.authlib.properties.PropertyMap;
/*    */ 
/*    */ public class HasJoinedMinecraftServerResponse
/*    */   extends Response {
/*    */   private UUID id;
/*    */   private PropertyMap properties;
/*    */   
/*    */   public UUID getId() {
/* 12 */     return this.id;
/*    */   }
/*    */   
/*    */   public PropertyMap getProperties() {
/* 16 */     return this.properties;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\yggdrasil\response\HasJoinedMinecraftServerResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */