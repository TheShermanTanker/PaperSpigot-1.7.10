/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class OpListEntry
/*    */   extends JsonListEntry
/*    */ {
/*    */   private final int a;
/*    */   
/*    */   public OpListEntry(GameProfile paramGameProfile, int paramInt) {
/* 13 */     super(paramGameProfile);
/* 14 */     this.a = paramInt;
/*    */   }
/*    */   
/*    */   public OpListEntry(JsonObject paramJsonObject) {
/* 18 */     super(b(paramJsonObject), paramJsonObject);
/* 19 */     this.a = paramJsonObject.has("level") ? paramJsonObject.get("level").getAsInt() : 0;
/*    */   }
/*    */   
/*    */   public int a() {
/* 23 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(JsonObject paramJsonObject) {
/* 28 */     if (getKey() == null) {
/*    */       return;
/*    */     }
/* 31 */     paramJsonObject.addProperty("uuid", (((GameProfile)getKey()).getId() == null) ? "" : ((GameProfile)getKey()).getId().toString());
/* 32 */     paramJsonObject.addProperty("name", ((GameProfile)getKey()).getName());
/* 33 */     super.a(paramJsonObject);
/* 34 */     paramJsonObject.addProperty("level", Integer.valueOf(this.a));
/*    */   }
/*    */   private static GameProfile b(JsonObject paramJsonObject) {
/*    */     UUID uUID;
/* 38 */     if (!paramJsonObject.has("uuid") || !paramJsonObject.has("name")) {
/* 39 */       return null;
/*    */     }
/* 41 */     String str = paramJsonObject.get("uuid").getAsString();
/*    */     
/*    */     try {
/* 44 */       uUID = UUID.fromString(str);
/* 45 */     } catch (Throwable throwable) {
/* 46 */       return null;
/*    */     } 
/* 48 */     return new GameProfile(uUID, paramJsonObject.get("name").getAsString());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\OpListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */