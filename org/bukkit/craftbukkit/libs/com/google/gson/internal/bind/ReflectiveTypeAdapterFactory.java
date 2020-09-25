/*     */ package org.bukkit.craftbukkit.libs.com.google.gson.internal.bind;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.FieldNamingStrategy;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonSyntaxException;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapterFactory;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.annotations.SerializedName;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.ConstructorConstructor;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.Excluder;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.ObjectConstructor;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.Primitives;
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
/*     */ public final class ReflectiveTypeAdapterFactory
/*     */   implements TypeAdapterFactory
/*     */ {
/*     */   private final ConstructorConstructor constructorConstructor;
/*     */   private final FieldNamingStrategy fieldNamingPolicy;
/*     */   private final Excluder excluder;
/*     */   
/*     */   public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingPolicy, Excluder excluder) {
/*  50 */     this.constructorConstructor = constructorConstructor;
/*  51 */     this.fieldNamingPolicy = fieldNamingPolicy;
/*  52 */     this.excluder = excluder;
/*     */   }
/*     */   
/*     */   public boolean excludeField(Field f, boolean serialize) {
/*  56 */     return (!this.excluder.excludeClass(f.getType(), serialize) && !this.excluder.excludeField(f, serialize));
/*     */   }
/*     */   
/*     */   private String getFieldName(Field f) {
/*  60 */     SerializedName serializedName = f.<SerializedName>getAnnotation(SerializedName.class);
/*  61 */     return (serializedName == null) ? this.fieldNamingPolicy.translateName(f) : serializedName.value();
/*     */   }
/*     */   
/*     */   public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/*  65 */     Class<? super T> raw = type.getRawType();
/*     */     
/*  67 */     if (!Object.class.isAssignableFrom(raw)) {
/*  68 */       return null;
/*     */     }
/*     */     
/*  71 */     ObjectConstructor<T> constructor = this.constructorConstructor.getConstructor(type);
/*  72 */     return new Adapter<T>(constructor, getBoundFields(gson, type, raw));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BoundField createBoundField(final Gson context, final Field field, String name, final TypeToken<?> fieldType, boolean serialize, boolean deserialize) {
/*  78 */     final boolean isPrimitive = Primitives.isPrimitive(fieldType.getRawType());
/*     */ 
/*     */     
/*  81 */     return new BoundField(name, serialize, deserialize) {
/*  82 */         final TypeAdapter<?> typeAdapter = context.getAdapter(fieldType);
/*     */ 
/*     */         
/*     */         void write(JsonWriter writer, Object value) throws IOException, IllegalAccessException {
/*  86 */           Object fieldValue = field.get(value);
/*  87 */           TypeAdapter t = new TypeAdapterRuntimeTypeWrapper(context, this.typeAdapter, fieldType.getType());
/*     */           
/*  89 */           t.write(writer, fieldValue);
/*     */         }
/*     */         
/*     */         void read(JsonReader reader, Object value) throws IOException, IllegalAccessException {
/*  93 */           Object fieldValue = this.typeAdapter.read(reader);
/*  94 */           if (fieldValue != null || !isPrimitive) {
/*  95 */             field.set(value, fieldValue);
/*     */           }
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private Map<String, BoundField> getBoundFields(Gson context, TypeToken<?> type, Class<?> raw) {
/* 102 */     Map<String, BoundField> result = new LinkedHashMap<String, BoundField>();
/* 103 */     if (raw.isInterface()) {
/* 104 */       return result;
/*     */     }
/*     */     
/* 107 */     Type declaredType = type.getType();
/* 108 */     while (raw != Object.class) {
/* 109 */       Field[] fields = raw.getDeclaredFields();
/* 110 */       for (Field field : fields) {
/* 111 */         boolean serialize = excludeField(field, true);
/* 112 */         boolean deserialize = excludeField(field, false);
/* 113 */         if (serialize || deserialize) {
/*     */ 
/*     */           
/* 116 */           field.setAccessible(true);
/* 117 */           Type fieldType = .Gson.Types.resolve(type.getType(), raw, field.getGenericType());
/* 118 */           BoundField boundField = createBoundField(context, field, getFieldName(field), TypeToken.get(fieldType), serialize, deserialize);
/*     */           
/* 120 */           BoundField previous = result.put(boundField.name, boundField);
/* 121 */           if (previous != null) {
/* 122 */             throw new IllegalArgumentException(declaredType + " declares multiple JSON fields named " + previous.name);
/*     */           }
/*     */         } 
/*     */       } 
/* 126 */       type = TypeToken.get(.Gson.Types.resolve(type.getType(), raw, raw.getGenericSuperclass()));
/* 127 */       raw = type.getRawType();
/*     */     } 
/* 129 */     return result;
/*     */   }
/*     */   
/*     */   static abstract class BoundField {
/*     */     final String name;
/*     */     final boolean serialized;
/*     */     final boolean deserialized;
/*     */     
/*     */     protected BoundField(String name, boolean serialized, boolean deserialized) {
/* 138 */       this.name = name;
/* 139 */       this.serialized = serialized;
/* 140 */       this.deserialized = deserialized;
/*     */     }
/*     */     
/*     */     abstract void write(JsonWriter param1JsonWriter, Object param1Object) throws IOException, IllegalAccessException;
/*     */     
/*     */     abstract void read(JsonReader param1JsonReader, Object param1Object) throws IOException, IllegalAccessException; }
/*     */   
/*     */   public final class Adapter<T> extends TypeAdapter<T> {
/*     */     private final ObjectConstructor<T> constructor;
/*     */     private final Map<String, ReflectiveTypeAdapterFactory.BoundField> boundFields;
/*     */     
/*     */     private Adapter(ObjectConstructor<T> constructor, Map<String, ReflectiveTypeAdapterFactory.BoundField> boundFields) {
/* 152 */       this.constructor = constructor;
/* 153 */       this.boundFields = boundFields;
/*     */     }
/*     */ 
/*     */     
/*     */     public T read(JsonReader in) throws IOException {
/* 158 */       if (in.peek() == JsonToken.NULL) {
/* 159 */         in.nextNull();
/* 160 */         return null;
/*     */       } 
/*     */       
/* 163 */       T instance = (T)this.constructor.construct();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 168 */         in.beginObject();
/* 169 */         while (in.hasNext()) {
/* 170 */           String name = in.nextName();
/* 171 */           ReflectiveTypeAdapterFactory.BoundField field = this.boundFields.get(name);
/* 172 */           if (field == null || !field.deserialized) {
/*     */             
/* 174 */             in.skipValue(); continue;
/*     */           } 
/* 176 */           field.read(in, instance);
/*     */         }
/*     */       
/* 179 */       } catch (IllegalStateException e) {
/* 180 */         throw new JsonSyntaxException(e);
/* 181 */       } catch (IllegalAccessException e) {
/* 182 */         throw new AssertionError(e);
/*     */       } 
/* 184 */       in.endObject();
/* 185 */       return instance;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter out, T value) throws IOException {
/* 190 */       if (value == null) {
/* 191 */         out.nullValue();
/*     */         
/*     */         return;
/*     */       } 
/* 195 */       out.beginObject();
/*     */       try {
/* 197 */         for (ReflectiveTypeAdapterFactory.BoundField boundField : this.boundFields.values()) {
/* 198 */           if (boundField.serialized) {
/* 199 */             out.name(boundField.name);
/* 200 */             boundField.write(out, value);
/*     */           } 
/*     */         } 
/* 203 */       } catch (IllegalAccessException e) {
/* 204 */         throw new AssertionError();
/*     */       } 
/* 206 */       out.endObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\internal\bind\ReflectiveTypeAdapterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */