/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import net.minecraft.util.com.google.gson.JsonArray;
/*     */ import net.minecraft.util.com.google.gson.JsonElement;
/*     */ import net.minecraft.util.com.google.gson.JsonObject;
/*     */ import net.minecraft.util.com.google.gson.JsonPrimitive;
/*     */ import net.minecraft.util.com.google.gson.JsonSyntaxException;
/*     */ import net.minecraft.util.org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatDeserializer
/*     */ {
/*     */   public static boolean d(JsonObject paramJsonObject, String paramString) {
/*  53 */     if (!g(paramJsonObject, paramString)) {
/*  54 */       return false;
/*     */     }
/*  56 */     if (!paramJsonObject.get(paramString).isJsonArray()) {
/*  57 */       return false;
/*     */     }
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean g(JsonObject paramJsonObject, String paramString) {
/*  83 */     if (paramJsonObject == null) {
/*  84 */       return false;
/*     */     }
/*  86 */     if (paramJsonObject.get(paramString) == null) {
/*  87 */       return false;
/*     */     }
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   public static String a(JsonElement paramJsonElement, String paramString) {
/*  93 */     if (paramJsonElement.isJsonPrimitive()) {
/*  94 */       return paramJsonElement.getAsString();
/*     */     }
/*  96 */     throw new JsonSyntaxException("Expected " + paramString + " to be a string, was " + d(paramJsonElement));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String h(JsonObject paramJsonObject, String paramString) {
/* 101 */     if (paramJsonObject.has(paramString)) {
/* 102 */       return a(paramJsonObject.get(paramString), paramString);
/*     */     }
/* 104 */     throw new JsonSyntaxException("Missing " + paramString + ", expected to find a string");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int f(JsonElement paramJsonElement, String paramString) {
/* 213 */     if (paramJsonElement.isJsonPrimitive() && paramJsonElement.getAsJsonPrimitive().isNumber()) {
/* 214 */       return paramJsonElement.getAsInt();
/*     */     }
/* 216 */     throw new JsonSyntaxException("Expected " + paramString + " to be a Int, was " + d(paramJsonElement));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int m(JsonObject paramJsonObject, String paramString) {
/* 221 */     if (paramJsonObject.has(paramString)) {
/* 222 */       return f(paramJsonObject.get(paramString), paramString);
/*     */     }
/* 224 */     throw new JsonSyntaxException("Missing " + paramString + ", expected to find a Int");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject l(JsonElement paramJsonElement, String paramString) {
/* 357 */     if (paramJsonElement.isJsonObject()) {
/* 358 */       return paramJsonElement.getAsJsonObject();
/*     */     }
/* 360 */     throw new JsonSyntaxException("Expected " + paramString + " to be a JsonObject, was " + d(paramJsonElement));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonArray m(JsonElement paramJsonElement, String paramString) {
/* 381 */     if (paramJsonElement.isJsonArray()) {
/* 382 */       return paramJsonElement.getAsJsonArray();
/*     */     }
/* 384 */     throw new JsonSyntaxException("Expected " + paramString + " to be a JsonArray, was " + d(paramJsonElement));
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonArray t(JsonObject paramJsonObject, String paramString) {
/* 389 */     if (paramJsonObject.has(paramString)) {
/* 390 */       return m(paramJsonObject.get(paramString), paramString);
/*     */     }
/* 392 */     throw new JsonSyntaxException("Missing " + paramString + ", expected to find a JsonArray");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String d(JsonElement paramJsonElement) {
/* 405 */     String str = StringUtils.abbreviateMiddle(String.valueOf(paramJsonElement), "...", 10);
/* 406 */     if (paramJsonElement == null) return "null (missing)"; 
/* 407 */     if (paramJsonElement.isJsonNull()) return "null (json)"; 
/* 408 */     if (paramJsonElement.isJsonArray()) return "an array (" + str + ")"; 
/* 409 */     if (paramJsonElement.isJsonObject()) return "an object (" + str + ")"; 
/* 410 */     if (paramJsonElement.isJsonPrimitive()) {
/* 411 */       JsonPrimitive jsonPrimitive = paramJsonElement.getAsJsonPrimitive();
/* 412 */       if (jsonPrimitive.isNumber()) return "a number (" + str + ")"; 
/* 413 */       if (jsonPrimitive.isBoolean()) return "a boolean (" + str + ")"; 
/*     */     } 
/* 415 */     return str;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */