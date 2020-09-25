/*     */ package org.bukkit.craftbukkit.libs.com.google.gson;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.GsonInternalAccess;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.Streams;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
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
/*     */ final class TreeTypeAdapter<T>
/*     */   extends TypeAdapter<T>
/*     */ {
/*     */   private final JsonSerializer<T> serializer;
/*     */   private final JsonDeserializer<T> deserializer;
/*     */   private final Gson gson;
/*     */   private final TypeToken<T> typeToken;
/*     */   private final TypeAdapterFactory skipPast;
/*     */   private TypeAdapter<T> delegate;
/*     */   
/*     */   private TreeTypeAdapter(JsonSerializer<T> serializer, JsonDeserializer<T> deserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory skipPast) {
/*  44 */     this.serializer = serializer;
/*  45 */     this.deserializer = deserializer;
/*  46 */     this.gson = gson;
/*  47 */     this.typeToken = typeToken;
/*  48 */     this.skipPast = skipPast;
/*     */   }
/*     */   
/*     */   public T read(JsonReader in) throws IOException {
/*  52 */     if (this.deserializer == null) {
/*  53 */       return delegate().read(in);
/*     */     }
/*  55 */     JsonElement value = Streams.parse(in);
/*  56 */     if (value.isJsonNull()) {
/*  57 */       return null;
/*     */     }
/*  59 */     return this.deserializer.deserialize(value, this.typeToken.getType(), this.gson.deserializationContext);
/*     */   }
/*     */   
/*     */   public void write(JsonWriter out, T value) throws IOException {
/*  63 */     if (this.serializer == null) {
/*  64 */       delegate().write(out, value);
/*     */       return;
/*     */     } 
/*  67 */     if (value == null) {
/*  68 */       out.nullValue();
/*     */       return;
/*     */     } 
/*  71 */     JsonElement tree = this.serializer.serialize(value, this.typeToken.getType(), this.gson.serializationContext);
/*  72 */     Streams.write(tree, out);
/*     */   }
/*     */   
/*     */   private TypeAdapter<T> delegate() {
/*  76 */     TypeAdapter<T> d = this.delegate;
/*  77 */     return (d != null) ? d : (this.delegate = GsonInternalAccess.INSTANCE.getNextAdapter(this.gson, this.skipPast, this.typeToken));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapterFactory newFactory(TypeToken<?> exactType, Object typeAdapter) {
/*  86 */     return new SingleTypeFactory(typeAdapter, exactType, false, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken<?> exactType, Object typeAdapter) {
/*  96 */     boolean matchRawType = (exactType.getType() == exactType.getRawType());
/*  97 */     return new SingleTypeFactory(typeAdapter, exactType, matchRawType, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> hierarchyType, Object typeAdapter) {
/* 106 */     return new SingleTypeFactory(typeAdapter, null, false, hierarchyType);
/*     */   }
/*     */   
/*     */   private static class SingleTypeFactory
/*     */     implements TypeAdapterFactory {
/*     */     private final TypeToken<?> exactType;
/*     */     private final boolean matchRawType;
/*     */     private final Class<?> hierarchyType;
/*     */     private final JsonSerializer<?> serializer;
/*     */     private final JsonDeserializer<?> deserializer;
/*     */     
/*     */     private SingleTypeFactory(Object typeAdapter, TypeToken<?> exactType, boolean matchRawType, Class<?> hierarchyType) {
/* 118 */       this.serializer = (typeAdapter instanceof JsonSerializer) ? (JsonSerializer)typeAdapter : null;
/*     */ 
/*     */       
/* 121 */       this.deserializer = (typeAdapter instanceof JsonDeserializer) ? (JsonDeserializer)typeAdapter : null;
/*     */ 
/*     */       
/* 124 */       .Gson.Preconditions.checkArgument((this.serializer != null || this.deserializer != null));
/* 125 */       this.exactType = exactType;
/* 126 */       this.matchRawType = matchRawType;
/* 127 */       this.hierarchyType = hierarchyType;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/* 132 */       boolean matches = (this.exactType != null) ? ((this.exactType.equals(type) || (this.matchRawType && this.exactType.getType() == type.getRawType()))) : this.hierarchyType.isAssignableFrom(type.getRawType());
/*     */ 
/*     */       
/* 135 */       return matches ? new TreeTypeAdapter<T>(this.serializer, this.deserializer, gson, type, this) : null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\TreeTypeAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */