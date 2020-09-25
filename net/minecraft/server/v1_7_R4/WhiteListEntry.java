/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class WhiteListEntry
/*    */   extends JsonListEntry
/*    */ {
/*    */   public WhiteListEntry(GameProfile paramGameProfile) {
/* 11 */     super(paramGameProfile);
/*    */   }
/*    */   
/*    */   public WhiteListEntry(JsonObject paramJsonObject) {
/* 15 */     super(b(paramJsonObject), paramJsonObject);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(JsonObject paramJsonObject) {
/* 20 */     if (getKey() == null) {
/*    */       return;
/*    */     }
/* 23 */     paramJsonObject.addProperty("uuid", (((GameProfile)getKey()).getId() == null) ? "" : ((GameProfile)getKey()).getId().toString());
/* 24 */     paramJsonObject.addProperty("name", ((GameProfile)getKey()).getName());
/* 25 */     super.a(paramJsonObject);
/*    */   }
/*    */   private static GameProfile b(JsonObject paramJsonObject) {
/*    */     UUID uUID;
/* 29 */     if (!paramJsonObject.has("uuid") || !paramJsonObject.has("name")) {
/* 30 */       return null;
/*    */     }
/* 32 */     String str = paramJsonObject.get("uuid").getAsString();
/*    */     
/*    */     try {
/* 35 */       uUID = UUID.fromString(str);
/* 36 */     } catch (Throwable throwable) {
/* 37 */       return null;
/*    */     } 
/* 39 */     return new GameProfile(uUID, paramJsonObject.get("name").getAsString());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WhiteListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */