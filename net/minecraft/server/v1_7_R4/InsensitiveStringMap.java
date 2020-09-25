/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class InsensitiveStringMap implements Map {
/*  7 */   private final Map a = new LinkedHashMap<Object, Object>();
/*    */ 
/*    */   
/*    */   public int size() {
/* 11 */     return this.a.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 16 */     return this.a.isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsKey(Object paramObject) {
/* 21 */     return this.a.containsKey(paramObject.toString().toLowerCase());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsValue(Object paramObject) {
/* 26 */     return this.a.containsKey(paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object get(Object paramObject) {
/* 31 */     return this.a.get(paramObject.toString().toLowerCase());
/*    */   }
/*    */ 
/*    */   
/*    */   public Object put(String paramString, Object paramObject) {
/* 36 */     return this.a.put(paramString.toLowerCase(), paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object remove(Object paramObject) {
/* 41 */     return this.a.remove(paramObject.toString().toLowerCase());
/*    */   }
/*    */ 
/*    */   
/*    */   public void putAll(Map paramMap) {
/* 46 */     for (Map.Entry entry : paramMap.entrySet()) {
/* 47 */       put((String)entry.getKey(), entry.getValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 53 */     this.a.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set keySet() {
/* 59 */     return this.a.keySet();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection values() {
/* 65 */     return this.a.values();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set entrySet() {
/* 71 */     return this.a.entrySet();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\InsensitiveStringMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */