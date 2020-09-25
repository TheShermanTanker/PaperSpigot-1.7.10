/*    */ package org.bukkit.craftbukkit.libs.com.google.gson.internal.bind;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Array;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapterFactory;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
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
/*    */ public final class ArrayTypeAdapter<E>
/*    */   extends TypeAdapter<Object>
/*    */ {
/* 39 */   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*    */     {
/*    */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 42 */         Type type = typeToken.getType();
/* 43 */         if (!(type instanceof java.lang.reflect.GenericArrayType) && (!(type instanceof Class) || !((Class)type).isArray())) {
/* 44 */           return null;
/*    */         }
/*    */         
/* 47 */         Type componentType = .Gson.Types.getArrayComponentType(type);
/* 48 */         TypeAdapter<?> componentTypeAdapter = gson.getAdapter(TypeToken.get(componentType));
/* 49 */         return new ArrayTypeAdapter(gson, componentTypeAdapter, .Gson.Types.getRawType(componentType));
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private final Class<E> componentType;
/*    */   private final TypeAdapter<E> componentTypeAdapter;
/*    */   
/*    */   public ArrayTypeAdapter(Gson context, TypeAdapter<E> componentTypeAdapter, Class<E> componentType) {
/* 58 */     this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(context, componentTypeAdapter, componentType);
/*    */     
/* 60 */     this.componentType = componentType;
/*    */   }
/*    */   
/*    */   public Object read(JsonReader in) throws IOException {
/* 64 */     if (in.peek() == JsonToken.NULL) {
/* 65 */       in.nextNull();
/* 66 */       return null;
/*    */     } 
/*    */     
/* 69 */     List<E> list = new ArrayList<E>();
/* 70 */     in.beginArray();
/* 71 */     while (in.hasNext()) {
/* 72 */       E instance = (E)this.componentTypeAdapter.read(in);
/* 73 */       list.add(instance);
/*    */     } 
/* 75 */     in.endArray();
/* 76 */     Object array = Array.newInstance(this.componentType, list.size());
/* 77 */     for (int i = 0; i < list.size(); i++) {
/* 78 */       Array.set(array, i, list.get(i));
/*    */     }
/* 80 */     return array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, Object array) throws IOException {
/* 85 */     if (array == null) {
/* 86 */       out.nullValue();
/*    */       
/*    */       return;
/*    */     } 
/* 90 */     out.beginArray();
/* 91 */     for (int i = 0, length = Array.getLength(array); i < length; i++) {
/* 92 */       E value = (E)Array.get(array, i);
/* 93 */       this.componentTypeAdapter.write(out, value);
/*    */     } 
/* 95 */     out.endArray();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\internal\bind\ArrayTypeAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */