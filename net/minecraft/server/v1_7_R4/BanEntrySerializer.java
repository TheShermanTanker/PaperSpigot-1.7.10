/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.lang.reflect.Type;
/*    */ import java.text.ParseException;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.google.gson.JsonDeserializationContext;
/*    */ import net.minecraft.util.com.google.gson.JsonDeserializer;
/*    */ import net.minecraft.util.com.google.gson.JsonElement;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ import net.minecraft.util.com.google.gson.JsonSerializationContext;
/*    */ import net.minecraft.util.com.google.gson.JsonSerializer;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ class BanEntrySerializer
/*    */   implements JsonDeserializer, JsonSerializer
/*    */ {
/*    */   final UserCache a;
/*    */   
/*    */   private BanEntrySerializer(UserCache usercache) {
/* 21 */     this.a = usercache;
/*    */   }
/*    */   
/*    */   public JsonElement a(UserCacheEntry usercacheentry, Type type, JsonSerializationContext jsonserializationcontext) {
/* 25 */     JsonObject jsonobject = new JsonObject();
/*    */     
/* 27 */     jsonobject.addProperty("name", usercacheentry.a().getName());
/* 28 */     UUID uuid = usercacheentry.a().getId();
/*    */     
/* 30 */     jsonobject.addProperty("uuid", (uuid == null) ? "" : uuid.toString());
/* 31 */     jsonobject.addProperty("expiresOn", UserCache.a.format(usercacheentry.b()));
/* 32 */     return (JsonElement)jsonobject;
/*    */   }
/*    */   
/*    */   public UserCacheEntry a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) {
/* 36 */     if (jsonelement.isJsonObject()) {
/* 37 */       JsonObject jsonobject = jsonelement.getAsJsonObject();
/* 38 */       JsonElement jsonelement1 = jsonobject.get("name");
/* 39 */       JsonElement jsonelement2 = jsonobject.get("uuid");
/* 40 */       JsonElement jsonelement3 = jsonobject.get("expiresOn");
/*    */       
/* 42 */       if (jsonelement1 != null && jsonelement2 != null) {
/* 43 */         String s = jsonelement2.getAsString();
/* 44 */         String s1 = jsonelement1.getAsString();
/* 45 */         Date date = null;
/*    */         
/* 47 */         if (jsonelement3 != null) {
/*    */           try {
/* 49 */             date = UserCache.a.parse(jsonelement3.getAsString());
/* 50 */           } catch (ParseException parseexception) {
/* 51 */             date = null;
/*    */           } 
/*    */         }
/*    */         
/* 55 */         if (s1 != null && s != null) {
/*    */           UUID uuid;
/*    */           
/*    */           try {
/* 59 */             uuid = UUID.fromString(s);
/* 60 */           } catch (Throwable throwable) {
/* 61 */             return null;
/*    */           } 
/*    */           
/* 64 */           UserCacheEntry usercacheentry = new UserCacheEntry(this.a, new GameProfile(uuid, s1), date, (GameProfileLookup)null);
/*    */           
/* 66 */           return usercacheentry;
/*    */         } 
/* 68 */         return null;
/*    */       } 
/*    */       
/* 71 */       return null;
/*    */     } 
/*    */     
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
/* 79 */     return a((UserCacheEntry)object, type, jsonserializationcontext);
/*    */   }
/*    */   
/*    */   public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) {
/* 83 */     return a(jsonelement, type, jsondeserializationcontext);
/*    */   }
/*    */   
/*    */   BanEntrySerializer(UserCache usercache, GameProfileLookup gameprofilelookup) {
/* 87 */     this(usercache);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BanEntrySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */