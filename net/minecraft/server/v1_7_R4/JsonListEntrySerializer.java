/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.util.com.google.gson.JsonDeserializationContext;
/*    */ import net.minecraft.util.com.google.gson.JsonDeserializer;
/*    */ import net.minecraft.util.com.google.gson.JsonElement;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ import net.minecraft.util.com.google.gson.JsonSerializationContext;
/*    */ import net.minecraft.util.com.google.gson.JsonSerializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ class JsonListEntrySerializer
/*    */   implements JsonDeserializer, JsonSerializer
/*    */ {
/*    */   final JsonList a;
/*    */   
/*    */   private JsonListEntrySerializer(JsonList jsonlist) {
/* 19 */     this.a = jsonlist;
/*    */   }
/*    */   
/*    */   public JsonElement a(JsonListEntry jsonlistentry, Type type, JsonSerializationContext jsonserializationcontext) {
/* 23 */     JsonObject jsonobject = new JsonObject();
/*    */     
/* 25 */     jsonlistentry.a(jsonobject);
/* 26 */     return (JsonElement)jsonobject;
/*    */   }
/*    */   
/*    */   public JsonListEntry a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) {
/* 30 */     if (jsonelement.isJsonObject()) {
/* 31 */       JsonObject jsonobject = jsonelement.getAsJsonObject();
/* 32 */       JsonListEntry jsonlistentry = this.a.a(jsonobject);
/*    */       
/* 34 */       return jsonlistentry;
/*    */     } 
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
/* 41 */     return a((JsonListEntry)object, type, jsonserializationcontext);
/*    */   }
/*    */   
/*    */   public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) {
/* 45 */     return a(jsonelement, type, jsondeserializationcontext);
/*    */   }
/*    */   
/*    */   JsonListEntrySerializer(JsonList jsonlist, JsonListType jsonlisttype) {
/* 49 */     this(jsonlist);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\JsonListEntrySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */