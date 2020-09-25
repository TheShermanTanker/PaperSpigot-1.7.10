/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.util.com.google.gson.Gson;
/*    */ import net.minecraft.util.com.google.gson.TypeAdapter;
/*    */ import net.minecraft.util.com.google.gson.TypeAdapterFactory;
/*    */ import net.minecraft.util.com.google.gson.reflect.TypeToken;
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
/*    */ public class ChatTypeAdapterFactory
/*    */   implements TypeAdapterFactory
/*    */ {
/*    */   public TypeAdapter create(Gson paramGson, TypeToken paramTypeToken) {
/* 23 */     Class<Object> clazz = paramTypeToken.getRawType();
/* 24 */     if (!clazz.isEnum()) {
/* 25 */       return null;
/*    */     }
/*    */     
/* 28 */     HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
/* 29 */     for (Object object : clazz.getEnumConstants()) {
/* 30 */       hashMap.put(a(object), object);
/*    */     }
/*    */     
/* 33 */     return new ChatTypeAdapter(this, hashMap);
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
/*    */   private String a(Object paramObject) {
/* 56 */     if (paramObject instanceof Enum) return ((Enum)paramObject).name().toLowerCase(Locale.US); 
/* 57 */     return paramObject.toString().toLowerCase(Locale.US);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */