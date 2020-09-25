/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.util.com.google.gson.JsonDeserializationContext;
/*     */ import net.minecraft.util.com.google.gson.JsonDeserializer;
/*     */ import net.minecraft.util.com.google.gson.JsonElement;
/*     */ import net.minecraft.util.com.google.gson.JsonObject;
/*     */ import net.minecraft.util.com.google.gson.JsonPrimitive;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatModifierSerializer
/*     */   implements JsonDeserializer, JsonSerializer
/*     */ {
/*     */   public ChatModifier a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) {
/* 250 */     if (paramJsonElement.isJsonObject()) {
/* 251 */       ChatModifier chatModifier = new ChatModifier();
/* 252 */       JsonObject jsonObject = paramJsonElement.getAsJsonObject();
/* 253 */       if (jsonObject == null) {
/* 254 */         return null;
/*     */       }
/*     */       
/* 257 */       if (jsonObject.has("bold")) ChatModifier.a(chatModifier, Boolean.valueOf(jsonObject.get("bold").getAsBoolean())); 
/* 258 */       if (jsonObject.has("italic")) ChatModifier.b(chatModifier, Boolean.valueOf(jsonObject.get("italic").getAsBoolean())); 
/* 259 */       if (jsonObject.has("underlined")) ChatModifier.c(chatModifier, Boolean.valueOf(jsonObject.get("underlined").getAsBoolean())); 
/* 260 */       if (jsonObject.has("strikethrough")) ChatModifier.d(chatModifier, Boolean.valueOf(jsonObject.get("strikethrough").getAsBoolean())); 
/* 261 */       if (jsonObject.has("obfuscated")) ChatModifier.e(chatModifier, Boolean.valueOf(jsonObject.get("obfuscated").getAsBoolean())); 
/* 262 */       if (jsonObject.has("color")) ChatModifier.a(chatModifier, (EnumChatFormat)paramJsonDeserializationContext.deserialize(jsonObject.get("color"), EnumChatFormat.class));
/*     */       
/* 264 */       if (jsonObject.has("clickEvent")) {
/* 265 */         JsonObject jsonObject1 = jsonObject.getAsJsonObject("clickEvent");
/* 266 */         if (jsonObject1 != null) {
/* 267 */           JsonPrimitive jsonPrimitive1 = jsonObject1.getAsJsonPrimitive("action");
/* 268 */           EnumClickAction enumClickAction = (jsonPrimitive1 == null) ? null : EnumClickAction.a(jsonPrimitive1.getAsString());
/*     */           
/* 270 */           JsonPrimitive jsonPrimitive2 = jsonObject1.getAsJsonPrimitive("value");
/* 271 */           String str = (jsonPrimitive2 == null) ? null : jsonPrimitive2.getAsString();
/*     */           
/* 273 */           if (enumClickAction != null && str != null && enumClickAction.a()) {
/* 274 */             ChatModifier.a(chatModifier, new ChatClickable(enumClickAction, str));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 279 */       if (jsonObject.has("hoverEvent")) {
/* 280 */         JsonObject jsonObject1 = jsonObject.getAsJsonObject("hoverEvent");
/* 281 */         if (jsonObject1 != null) {
/* 282 */           JsonPrimitive jsonPrimitive = jsonObject1.getAsJsonPrimitive("action");
/* 283 */           EnumHoverAction enumHoverAction = (jsonPrimitive == null) ? null : EnumHoverAction.a(jsonPrimitive.getAsString());
/*     */           
/* 285 */           IChatBaseComponent iChatBaseComponent = (IChatBaseComponent)paramJsonDeserializationContext.deserialize(jsonObject1.get("value"), IChatBaseComponent.class);
/*     */           
/* 287 */           if (enumHoverAction != null && iChatBaseComponent != null && enumHoverAction.a()) {
/* 288 */             ChatModifier.a(chatModifier, new ChatHoverable(enumHoverAction, iChatBaseComponent));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 293 */       return chatModifier;
/*     */     } 
/*     */     
/* 296 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement a(ChatModifier paramChatModifier, Type paramType, JsonSerializationContext paramJsonSerializationContext) {
/* 302 */     if (paramChatModifier.g()) return null; 
/* 303 */     JsonObject jsonObject = new JsonObject();
/*     */     
/* 305 */     if (ChatModifier.b(paramChatModifier) != null) jsonObject.addProperty("bold", ChatModifier.b(paramChatModifier)); 
/* 306 */     if (ChatModifier.c(paramChatModifier) != null) jsonObject.addProperty("italic", ChatModifier.c(paramChatModifier)); 
/* 307 */     if (ChatModifier.d(paramChatModifier) != null) jsonObject.addProperty("underlined", ChatModifier.d(paramChatModifier)); 
/* 308 */     if (ChatModifier.e(paramChatModifier) != null) jsonObject.addProperty("strikethrough", ChatModifier.e(paramChatModifier)); 
/* 309 */     if (ChatModifier.f(paramChatModifier) != null) jsonObject.addProperty("obfuscated", ChatModifier.f(paramChatModifier)); 
/* 310 */     if (ChatModifier.g(paramChatModifier) != null) jsonObject.add("color", paramJsonSerializationContext.serialize(ChatModifier.g(paramChatModifier)));
/*     */     
/* 312 */     if (ChatModifier.h(paramChatModifier) != null) {
/* 313 */       JsonObject jsonObject1 = new JsonObject();
/* 314 */       jsonObject1.addProperty("action", ChatModifier.h(paramChatModifier).a().b());
/* 315 */       jsonObject1.addProperty("value", ChatModifier.h(paramChatModifier).b());
/* 316 */       jsonObject.add("clickEvent", (JsonElement)jsonObject1);
/*     */     } 
/*     */     
/* 319 */     if (ChatModifier.i(paramChatModifier) != null) {
/* 320 */       JsonObject jsonObject1 = new JsonObject();
/* 321 */       jsonObject1.addProperty("action", ChatModifier.i(paramChatModifier).a().b());
/* 322 */       jsonObject1.add("value", paramJsonSerializationContext.serialize(ChatModifier.i(paramChatModifier).b()));
/* 323 */       jsonObject.add("hoverEvent", (JsonElement)jsonObject1);
/*     */     } 
/*     */     
/* 326 */     return (JsonElement)jsonObject;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatModifierSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */