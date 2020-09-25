/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.util.com.google.gson.JsonDeserializationContext;
/*     */ import net.minecraft.util.com.google.gson.JsonDeserializer;
/*     */ import net.minecraft.util.com.google.gson.JsonElement;
/*     */ import net.minecraft.util.com.google.gson.JsonObject;
/*     */ import net.minecraft.util.com.google.gson.JsonSerializationContext;
/*     */ import net.minecraft.util.com.google.gson.JsonSerializer;
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
/*     */ public class ServerPingSerializer
/*     */   implements JsonDeserializer, JsonSerializer
/*     */ {
/*     */   public ServerPing a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) {
/* 163 */     JsonObject jsonObject = ChatDeserializer.l(paramJsonElement, "status");
/* 164 */     ServerPing serverPing = new ServerPing();
/*     */     
/* 166 */     if (jsonObject.has("description")) {
/* 167 */       serverPing.setMOTD((IChatBaseComponent)paramJsonDeserializationContext.deserialize(jsonObject.get("description"), IChatBaseComponent.class));
/*     */     }
/*     */     
/* 170 */     if (jsonObject.has("players")) {
/* 171 */       serverPing.setPlayerSample((ServerPingPlayerSample)paramJsonDeserializationContext.deserialize(jsonObject.get("players"), ServerPingPlayerSample.class));
/*     */     }
/*     */     
/* 174 */     if (jsonObject.has("version")) {
/* 175 */       serverPing.setServerInfo((ServerPingServerData)paramJsonDeserializationContext.deserialize(jsonObject.get("version"), ServerPingServerData.class));
/*     */     }
/*     */     
/* 178 */     if (jsonObject.has("favicon")) {
/* 179 */       serverPing.setFavicon(ChatDeserializer.h(jsonObject, "favicon"));
/*     */     }
/*     */     
/* 182 */     return serverPing;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement a(ServerPing paramServerPing, Type paramType, JsonSerializationContext paramJsonSerializationContext) {
/* 187 */     JsonObject jsonObject = new JsonObject();
/*     */     
/* 189 */     if (paramServerPing.a() != null) {
/* 190 */       jsonObject.add("description", paramJsonSerializationContext.serialize(paramServerPing.a()));
/*     */     }
/*     */     
/* 193 */     if (paramServerPing.b() != null) {
/* 194 */       jsonObject.add("players", paramJsonSerializationContext.serialize(paramServerPing.b()));
/*     */     }
/*     */     
/* 197 */     if (paramServerPing.c() != null) {
/* 198 */       jsonObject.add("version", paramJsonSerializationContext.serialize(paramServerPing.c()));
/*     */     }
/*     */     
/* 201 */     if (paramServerPing.d() != null) {
/* 202 */       jsonObject.addProperty("favicon", paramServerPing.d());
/*     */     }
/*     */     
/* 205 */     return (JsonElement)jsonObject;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerPingSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */