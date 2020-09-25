/*    */ package net.minecraft.util.com.google.gson.internal.bind;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.util.com.google.gson.Gson;
/*    */ import net.minecraft.util.com.google.gson.TypeAdapter;
/*    */ import net.minecraft.util.com.google.gson.reflect.TypeToken;
/*    */ import net.minecraft.util.com.google.gson.stream.JsonReader;
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
/*    */ final class TypeAdapterRuntimeTypeWrapper<T>
/*    */   extends TypeAdapter<T>
/*    */ {
/*    */   private final Gson context;
/*    */   private final TypeAdapter<T> delegate;
/*    */   private final Type type;
/*    */   
/*    */   TypeAdapterRuntimeTypeWrapper(Gson context, TypeAdapter<T> delegate, Type type) {
/* 33 */     this.context = context;
/* 34 */     this.delegate = delegate;
/* 35 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public T read(JsonReader in) throws IOException {
/* 40 */     return (T)this.delegate.read(in);
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
/*    */   public void write(JsonWriter out, T value) throws IOException {
/* 52 */     TypeAdapter<T> chosen = this.delegate;
/* 53 */     Type runtimeType = getRuntimeTypeIfMoreSpecific(this.type, value);
/* 54 */     if (runtimeType != this.type) {
/* 55 */       TypeAdapter<T> runtimeTypeAdapter = this.context.getAdapter(TypeToken.get(runtimeType));
/* 56 */       if (!(runtimeTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)) {
/*    */         
/* 58 */         chosen = runtimeTypeAdapter;
/* 59 */       } else if (!(this.delegate instanceof ReflectiveTypeAdapterFactory.Adapter)) {
/*    */ 
/*    */         
/* 62 */         chosen = this.delegate;
/*    */       } else {
/*    */         
/* 65 */         chosen = runtimeTypeAdapter;
/*    */       } 
/*    */     } 
/* 68 */     chosen.write(out, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Type getRuntimeTypeIfMoreSpecific(Type<?> type, Object value) {
/* 75 */     if (value != null && (type == Object.class || type instanceof java.lang.reflect.TypeVariable || type instanceof Class))
/*    */     {
/* 77 */       type = value.getClass();
/*    */     }
/* 79 */     return type;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\gson\internal\bind\TypeAdapterRuntimeTypeWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */