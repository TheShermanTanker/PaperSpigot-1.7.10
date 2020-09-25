/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.com.google.gson.TypeAdapter;
/*    */ import net.minecraft.util.com.google.gson.stream.JsonReader;
/*    */ import net.minecraft.util.com.google.gson.stream.JsonToken;
/*    */ import net.minecraft.util.com.google.gson.stream.JsonWriter;
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
/*    */ class ChatTypeAdapter
/*    */   extends TypeAdapter
/*    */ {
/*    */   ChatTypeAdapter(ChatTypeAdapterFactory paramChatTypeAdapterFactory, Map paramMap) {}
/*    */   
/*    */   public void write(JsonWriter paramJsonWriter, Object paramObject) {
/* 36 */     if (paramObject == null) {
/* 37 */       paramJsonWriter.nullValue();
/*    */     } else {
/* 39 */       paramJsonWriter.value(ChatTypeAdapterFactory.a(this.b, paramObject));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object read(JsonReader paramJsonReader) {
/* 45 */     if (paramJsonReader.peek() == JsonToken.NULL) {
/* 46 */       paramJsonReader.nextNull();
/* 47 */       return null;
/*    */     } 
/* 49 */     return this.a.get(paramJsonReader.nextString());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */