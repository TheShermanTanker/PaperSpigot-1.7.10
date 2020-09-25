/*    */ package org.bukkit.craftbukkit.libs.com.google.gson.internal.bind;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.math.BigDecimal;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonSyntaxException;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonToken;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
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
/*    */ public final class BigDecimalTypeAdapter
/*    */   extends TypeAdapter<BigDecimal>
/*    */ {
/*    */   public BigDecimal read(JsonReader in) throws IOException {
/* 37 */     if (in.peek() == JsonToken.NULL) {
/* 38 */       in.nextNull();
/* 39 */       return null;
/*    */     } 
/*    */     try {
/* 42 */       return new BigDecimal(in.nextString());
/* 43 */     } catch (NumberFormatException e) {
/* 44 */       throw new JsonSyntaxException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, BigDecimal value) throws IOException {
/* 50 */     out.value(value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\internal\bind\BigDecimalTypeAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */