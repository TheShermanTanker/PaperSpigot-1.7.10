/*     */ package org.bukkit.craftbukkit.libs.com.google.gson;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.JsonTreeReader;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.JsonTreeWriter;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonToken;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TypeAdapter<T>
/*     */ {
/*     */   public abstract void write(JsonWriter paramJsonWriter, T paramT) throws IOException;
/*     */   
/*     */   final void toJson(Writer out, T value) throws IOException {
/* 140 */     JsonWriter writer = new JsonWriter(out);
/* 141 */     write(writer, value);
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
/*     */   public final TypeAdapter<T> nullSafe() {
/* 185 */     return new TypeAdapter<T>() {
/*     */         public void write(JsonWriter out, T value) throws IOException {
/* 187 */           if (value == null) {
/* 188 */             out.nullValue();
/*     */           } else {
/* 190 */             TypeAdapter.this.write(out, value);
/*     */           } 
/*     */         }
/*     */         public T read(JsonReader reader) throws IOException {
/* 194 */           if (reader.peek() == JsonToken.NULL) {
/* 195 */             reader.nextNull();
/* 196 */             return null;
/*     */           } 
/* 198 */           return TypeAdapter.this.read(reader);
/*     */         }
/*     */       };
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
/*     */   final String toJson(T value) throws IOException {
/* 213 */     StringWriter stringWriter = new StringWriter();
/* 214 */     toJson(stringWriter, value);
/* 215 */     return stringWriter.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final JsonElement toJsonTree(T value) {
/*     */     try {
/* 226 */       JsonTreeWriter jsonWriter = new JsonTreeWriter();
/* 227 */       jsonWriter.setLenient(true);
/* 228 */       write((JsonWriter)jsonWriter, value);
/* 229 */       return jsonWriter.get();
/* 230 */     } catch (IOException e) {
/* 231 */       throw new JsonIOException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T read(JsonReader paramJsonReader) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final T fromJson(Reader in) throws IOException {
/* 252 */     JsonReader reader = new JsonReader(in);
/* 253 */     reader.setLenient(true);
/* 254 */     return read(reader);
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
/*     */   final T fromJson(String json) throws IOException {
/* 266 */     return fromJson(new StringReader(json));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final T fromJsonTree(JsonElement jsonTree) {
/*     */     try {
/* 276 */       JsonTreeReader jsonTreeReader = new JsonTreeReader(jsonTree);
/* 277 */       jsonTreeReader.setLenient(true);
/* 278 */       return read((JsonReader)jsonTreeReader);
/* 279 */     } catch (IOException e) {
/* 280 */       throw new JsonIOException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\TypeAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */