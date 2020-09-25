/*     */ package org.bukkit.craftbukkit.libs.com.google.gson.internal.bind;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapterFactory;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
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
/*     */ public final class ObjectTypeAdapter
/*     */   extends TypeAdapter<Object>
/*     */ {
/*  37 */   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*     */     {
/*     */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/*  40 */         if (type.getRawType() == Object.class) {
/*  41 */           return new ObjectTypeAdapter(gson);
/*     */         }
/*  43 */         return null;
/*     */       }
/*     */     };
/*     */   
/*     */   private final Gson gson;
/*     */   
/*     */   private ObjectTypeAdapter(Gson gson) {
/*  50 */     this.gson = gson;
/*     */   } public Object read(JsonReader in) throws IOException {
/*     */     List<Object> list;
/*     */     Map<String, Object> map;
/*  54 */     JsonToken token = in.peek();
/*  55 */     switch (token) {
/*     */       case BEGIN_ARRAY:
/*  57 */         list = new ArrayList();
/*  58 */         in.beginArray();
/*  59 */         while (in.hasNext()) {
/*  60 */           list.add(read(in));
/*     */         }
/*  62 */         in.endArray();
/*  63 */         return list;
/*     */       
/*     */       case BEGIN_OBJECT:
/*  66 */         map = new LinkedHashMap<String, Object>();
/*  67 */         in.beginObject();
/*  68 */         while (in.hasNext()) {
/*  69 */           map.put(in.nextName(), read(in));
/*     */         }
/*  71 */         in.endObject();
/*  72 */         return map;
/*     */       
/*     */       case STRING:
/*  75 */         return in.nextString();
/*     */       
/*     */       case NUMBER:
/*  78 */         return Double.valueOf(in.nextDouble());
/*     */       
/*     */       case BOOLEAN:
/*  81 */         return Boolean.valueOf(in.nextBoolean());
/*     */       
/*     */       case NULL:
/*  84 */         in.nextNull();
/*  85 */         return null;
/*     */     } 
/*     */     
/*  88 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Object value) throws IOException {
/*  93 */     if (value == null) {
/*  94 */       out.nullValue();
/*     */       
/*     */       return;
/*     */     } 
/*  98 */     TypeAdapter<Object> typeAdapter = this.gson.getAdapter(value.getClass());
/*  99 */     if (typeAdapter instanceof ObjectTypeAdapter) {
/* 100 */       out.beginObject();
/* 101 */       out.endObject();
/*     */       
/*     */       return;
/*     */     } 
/* 105 */     typeAdapter.write(out, value);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\internal\bind\ObjectTypeAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */