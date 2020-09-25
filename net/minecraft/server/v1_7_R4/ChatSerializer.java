/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.com.google.gson.Gson;
/*     */ import net.minecraft.util.com.google.gson.GsonBuilder;
/*     */ import net.minecraft.util.com.google.gson.JsonArray;
/*     */ import net.minecraft.util.com.google.gson.JsonDeserializationContext;
/*     */ import net.minecraft.util.com.google.gson.JsonDeserializer;
/*     */ import net.minecraft.util.com.google.gson.JsonElement;
/*     */ import net.minecraft.util.com.google.gson.JsonObject;
/*     */ import net.minecraft.util.com.google.gson.JsonParseException;
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
/*     */ public class ChatSerializer
/*     */   implements JsonDeserializer, JsonSerializer
/*     */ {
/*     */   private static final Gson a;
/*     */   
/*     */   static {
/*  33 */     GsonBuilder gsonBuilder = new GsonBuilder();
/*  34 */     gsonBuilder.registerTypeHierarchyAdapter(IChatBaseComponent.class, new ChatSerializer());
/*  35 */     gsonBuilder.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifierSerializer());
/*  36 */     gsonBuilder.registerTypeAdapterFactory(new ChatTypeAdapterFactory());
/*  37 */     a = gsonBuilder.create();
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatBaseComponent a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) {
/*  42 */     if (paramJsonElement.isJsonPrimitive())
/*     */     {
/*  44 */       return new ChatComponentText(paramJsonElement.getAsString()); } 
/*  45 */     if (paramJsonElement.isJsonObject()) {
/*  46 */       ChatMessage chatMessage; JsonObject jsonObject = paramJsonElement.getAsJsonObject();
/*     */ 
/*     */       
/*  49 */       if (jsonObject.has("text")) {
/*  50 */         ChatComponentText chatComponentText = new ChatComponentText(jsonObject.get("text").getAsString());
/*  51 */       } else if (jsonObject.has("translate")) {
/*  52 */         String str = jsonObject.get("translate").getAsString();
/*     */         
/*  54 */         if (jsonObject.has("with")) {
/*  55 */           JsonArray jsonArray = jsonObject.getAsJsonArray("with");
/*  56 */           Object[] arrayOfObject = new Object[jsonArray.size()];
/*     */           
/*  58 */           for (byte b = 0; b < arrayOfObject.length; b++) {
/*  59 */             arrayOfObject[b] = a(jsonArray.get(b), paramType, paramJsonDeserializationContext);
/*     */             
/*  61 */             if (arrayOfObject[b] instanceof ChatComponentText) {
/*  62 */               ChatComponentText chatComponentText = (ChatComponentText)arrayOfObject[b];
/*  63 */               if (chatComponentText.getChatModifier().g() && chatComponentText.a().isEmpty()) {
/*  64 */                 arrayOfObject[b] = chatComponentText.g();
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/*  69 */           chatMessage = new ChatMessage(str, arrayOfObject);
/*     */         } else {
/*  71 */           chatMessage = new ChatMessage(str, new Object[0]);
/*     */         } 
/*     */       } else {
/*  74 */         throw new JsonParseException("Don't know how to turn " + paramJsonElement.toString() + " into a Component");
/*     */       } 
/*     */       
/*  77 */       if (jsonObject.has("extra")) {
/*  78 */         JsonArray jsonArray = jsonObject.getAsJsonArray("extra");
/*     */         
/*  80 */         if (jsonArray.size() > 0) {
/*  81 */           for (byte b = 0; b < jsonArray.size(); b++) {
/*  82 */             chatMessage.addSibling(a(jsonArray.get(b), paramType, paramJsonDeserializationContext));
/*     */           }
/*     */         } else {
/*  85 */           throw new JsonParseException("Unexpected empty array of components");
/*     */         } 
/*     */       } 
/*     */       
/*  89 */       chatMessage.setChatModifier((ChatModifier)paramJsonDeserializationContext.deserialize(paramJsonElement, ChatModifier.class));
/*     */       
/*  91 */       return chatMessage;
/*  92 */     }  if (paramJsonElement.isJsonArray()) {
/*     */       
/*  94 */       JsonArray jsonArray = paramJsonElement.getAsJsonArray();
/*  95 */       IChatBaseComponent iChatBaseComponent = null;
/*     */       
/*  97 */       for (JsonElement jsonElement : jsonArray) {
/*  98 */         IChatBaseComponent iChatBaseComponent1 = a(jsonElement, jsonElement.getClass(), paramJsonDeserializationContext);
/*  99 */         if (iChatBaseComponent == null) {
/* 100 */           iChatBaseComponent = iChatBaseComponent1; continue;
/*     */         } 
/* 102 */         iChatBaseComponent.addSibling(iChatBaseComponent1);
/*     */       } 
/*     */ 
/*     */       
/* 106 */       return iChatBaseComponent;
/*     */     } 
/* 108 */     throw new JsonParseException("Don't know how to turn " + paramJsonElement.toString() + " into a Component");
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(ChatModifier paramChatModifier, JsonObject paramJsonObject, JsonSerializationContext paramJsonSerializationContext) {
/* 113 */     JsonElement jsonElement = paramJsonSerializationContext.serialize(paramChatModifier);
/*     */     
/* 115 */     if (jsonElement.isJsonObject()) {
/* 116 */       JsonObject jsonObject = (JsonObject)jsonElement;
/* 117 */       for (Map.Entry entry : jsonObject.entrySet()) {
/* 118 */         paramJsonObject.add((String)entry.getKey(), (JsonElement)entry.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement a(IChatBaseComponent paramIChatBaseComponent, Type paramType, JsonSerializationContext paramJsonSerializationContext) {
/* 125 */     if (paramIChatBaseComponent instanceof ChatComponentText && paramIChatBaseComponent.getChatModifier().g() && paramIChatBaseComponent.a().isEmpty()) {
/* 126 */       return (JsonElement)new JsonPrimitive(((ChatComponentText)paramIChatBaseComponent).g());
/*     */     }
/*     */     
/* 129 */     JsonObject jsonObject = new JsonObject();
/*     */     
/* 131 */     if (!paramIChatBaseComponent.getChatModifier().g()) {
/* 132 */       a(paramIChatBaseComponent.getChatModifier(), jsonObject, paramJsonSerializationContext);
/*     */     }
/*     */     
/* 135 */     if (!paramIChatBaseComponent.a().isEmpty()) {
/* 136 */       JsonArray jsonArray = new JsonArray();
/*     */       
/* 138 */       for (IChatBaseComponent iChatBaseComponent : paramIChatBaseComponent.a()) {
/* 139 */         jsonArray.add(a(iChatBaseComponent, iChatBaseComponent.getClass(), paramJsonSerializationContext));
/*     */       }
/*     */       
/* 142 */       jsonObject.add("extra", (JsonElement)jsonArray);
/*     */     } 
/*     */     
/* 145 */     if (paramIChatBaseComponent instanceof ChatComponentText) {
/* 146 */       jsonObject.addProperty("text", ((ChatComponentText)paramIChatBaseComponent).g());
/* 147 */     } else if (paramIChatBaseComponent instanceof ChatMessage) {
/* 148 */       ChatMessage chatMessage = (ChatMessage)paramIChatBaseComponent;
/* 149 */       jsonObject.addProperty("translate", chatMessage.i());
/*     */       
/* 151 */       if (chatMessage.j() != null && (chatMessage.j()).length > 0) {
/* 152 */         JsonArray jsonArray = new JsonArray();
/*     */         
/* 154 */         for (Object object : chatMessage.j()) {
/* 155 */           if (object instanceof IChatBaseComponent) {
/* 156 */             jsonArray.add(a((IChatBaseComponent)object, object.getClass(), paramJsonSerializationContext));
/*     */           } else {
/* 158 */             jsonArray.add((JsonElement)new JsonPrimitive(String.valueOf(object)));
/*     */           } 
/*     */         } 
/*     */         
/* 162 */         jsonObject.add("with", (JsonElement)jsonArray);
/*     */       } 
/*     */     } else {
/* 165 */       throw new IllegalArgumentException("Don't know how to serialize " + paramIChatBaseComponent + " as a Component");
/*     */     } 
/*     */     
/* 168 */     return (JsonElement)jsonObject;
/*     */   }
/*     */   
/*     */   public static String a(IChatBaseComponent paramIChatBaseComponent) {
/* 172 */     return a.toJson(paramIChatBaseComponent);
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent a(String paramString) {
/* 176 */     return (IChatBaseComponent)a.fromJson(paramString, IChatBaseComponent.class);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */