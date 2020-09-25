/*    */ package org.bukkit.craftbukkit.libs.com.google.gson;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import java.io.StringReader;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.Streams;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonToken;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.MalformedJsonException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class JsonParser
/*    */ {
/*    */   public JsonElement parse(String json) throws JsonSyntaxException {
/* 45 */     return parse(new StringReader(json));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonElement parse(Reader json) throws JsonIOException, JsonSyntaxException {
/*    */     try {
/* 58 */       JsonReader jsonReader = new JsonReader(json);
/* 59 */       JsonElement element = parse(jsonReader);
/* 60 */       if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
/* 61 */         throw new JsonSyntaxException("Did not consume the entire document.");
/*    */       }
/* 63 */       return element;
/* 64 */     } catch (MalformedJsonException e) {
/* 65 */       throw new JsonSyntaxException(e);
/* 66 */     } catch (IOException e) {
/* 67 */       throw new JsonIOException(e);
/* 68 */     } catch (NumberFormatException e) {
/* 69 */       throw new JsonSyntaxException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonElement parse(JsonReader json) throws JsonIOException, JsonSyntaxException {
/* 81 */     boolean lenient = json.isLenient();
/* 82 */     json.setLenient(true);
/*    */     try {
/* 84 */       return Streams.parse(json);
/* 85 */     } catch (StackOverflowError e) {
/* 86 */       throw new JsonParseException("Failed parsing JSON source: " + json + " to Json", e);
/* 87 */     } catch (OutOfMemoryError e) {
/* 88 */       throw new JsonParseException("Failed parsing JSON source: " + json + " to Json", e);
/* 89 */     } catch (JsonParseException e) {
/* 90 */       if (e.getCause() instanceof java.io.EOFException) {
/* 91 */         return JsonNull.INSTANCE;
/*    */       }
/* 93 */       throw e;
/*    */     } finally {
/* 95 */       json.setLenient(lenient);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\JsonParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */