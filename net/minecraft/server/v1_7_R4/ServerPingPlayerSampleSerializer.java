/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.com.google.gson.JsonArray;
/*     */ import net.minecraft.util.com.google.gson.JsonDeserializationContext;
/*     */ import net.minecraft.util.com.google.gson.JsonDeserializer;
/*     */ import net.minecraft.util.com.google.gson.JsonElement;
/*     */ import net.minecraft.util.com.google.gson.JsonObject;
/*     */ import net.minecraft.util.com.google.gson.JsonSerializationContext;
/*     */ import net.minecraft.util.com.google.gson.JsonSerializer;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerPingPlayerSampleSerializer
/*     */   implements JsonDeserializer, JsonSerializer
/*     */ {
/*     */   public ServerPingPlayerSample a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) {
/*  81 */     JsonObject jsonObject = ChatDeserializer.l(paramJsonElement, "players");
/*  82 */     ServerPingPlayerSample serverPingPlayerSample = new ServerPingPlayerSample(ChatDeserializer.m(jsonObject, "max"), ChatDeserializer.m(jsonObject, "online"));
/*     */     
/*  84 */     if (ChatDeserializer.d(jsonObject, "sample")) {
/*  85 */       JsonArray jsonArray = ChatDeserializer.t(jsonObject, "sample");
/*  86 */       if (jsonArray.size() > 0) {
/*  87 */         GameProfile[] arrayOfGameProfile = new GameProfile[jsonArray.size()];
/*  88 */         for (byte b = 0; b < arrayOfGameProfile.length; b++) {
/*  89 */           JsonObject jsonObject1 = ChatDeserializer.l(jsonArray.get(b), "player[" + b + "]");
/*  90 */           String str = ChatDeserializer.h(jsonObject1, "id");
/*  91 */           arrayOfGameProfile[b] = new GameProfile(UUID.fromString(str), ChatDeserializer.h(jsonObject1, "name"));
/*     */         } 
/*  93 */         serverPingPlayerSample.a(arrayOfGameProfile);
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return serverPingPlayerSample;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement a(ServerPingPlayerSample paramServerPingPlayerSample, Type paramType, JsonSerializationContext paramJsonSerializationContext) {
/* 102 */     JsonObject jsonObject = new JsonObject();
/*     */     
/* 104 */     jsonObject.addProperty("max", Integer.valueOf(paramServerPingPlayerSample.a()));
/* 105 */     jsonObject.addProperty("online", Integer.valueOf(paramServerPingPlayerSample.b()));
/*     */     
/* 107 */     if (paramServerPingPlayerSample.c() != null && (paramServerPingPlayerSample.c()).length > 0) {
/* 108 */       JsonArray jsonArray = new JsonArray();
/*     */       
/* 110 */       for (byte b = 0; b < (paramServerPingPlayerSample.c()).length; b++) {
/* 111 */         JsonObject jsonObject1 = new JsonObject();
/* 112 */         UUID uUID = paramServerPingPlayerSample.c()[b].getId();
/* 113 */         jsonObject1.addProperty("id", (uUID == null) ? "" : uUID.toString());
/* 114 */         jsonObject1.addProperty("name", paramServerPingPlayerSample.c()[b].getName());
/* 115 */         jsonArray.add((JsonElement)jsonObject1);
/*     */       } 
/*     */       
/* 118 */       jsonObject.add("sample", (JsonElement)jsonArray);
/*     */     } 
/*     */     
/* 121 */     return (JsonElement)jsonObject;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerPingPlayerSampleSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */