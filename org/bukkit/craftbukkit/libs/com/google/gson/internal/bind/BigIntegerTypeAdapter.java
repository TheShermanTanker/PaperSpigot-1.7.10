/*    */ package org.bukkit.craftbukkit.libs.com.google.gson.internal.bind;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.math.BigInteger;
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
/*    */ public final class BigIntegerTypeAdapter
/*    */   extends TypeAdapter<BigInteger>
/*    */ {
/*    */   public BigInteger read(JsonReader in) throws IOException {
/* 36 */     if (in.peek() == JsonToken.NULL) {
/* 37 */       in.nextNull();
/* 38 */       return null;
/*    */     } 
/*    */     try {
/* 41 */       return new BigInteger(in.nextString());
/* 42 */     } catch (NumberFormatException e) {
/* 43 */       throw new JsonSyntaxException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, BigInteger value) throws IOException {
/* 49 */     out.value(value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\internal\bind\BigIntegerTypeAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */