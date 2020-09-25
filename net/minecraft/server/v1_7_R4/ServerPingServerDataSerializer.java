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
/*     */ public class ServerPingServerDataSerializer
/*     */   implements JsonDeserializer, JsonSerializer
/*     */ {
/*     */   public ServerPingServerData a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) {
/* 146 */     JsonObject jsonObject = ChatDeserializer.l(paramJsonElement, "version");
/* 147 */     return new ServerPingServerData(ChatDeserializer.h(jsonObject, "name"), ChatDeserializer.m(jsonObject, "protocol"));
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement a(ServerPingServerData paramServerPingServerData, Type paramType, JsonSerializationContext paramJsonSerializationContext) {
/* 152 */     JsonObject jsonObject = new JsonObject();
/* 153 */     jsonObject.addProperty("name", paramServerPingServerData.a());
/* 154 */     jsonObject.addProperty("protocol", Integer.valueOf(paramServerPingServerData.b()));
/* 155 */     return (JsonElement)jsonObject;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerPingServerDataSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */