/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.com.google.common.collect.BiMap;
/*    */ import net.minecraft.util.com.google.common.collect.HashBiMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistryMaterials
/*    */   extends RegistrySimple
/*    */   implements Registry
/*    */ {
/* 14 */   protected final RegistryID a = new RegistryID();
/*    */   
/*    */   protected final Map b;
/*    */   
/*    */   public RegistryMaterials() {
/* 19 */     this.b = (Map)((BiMap)this.c).inverse();
/*    */   }
/*    */   
/*    */   public void a(int paramInt, String paramString, Object paramObject) {
/* 23 */     this.a.a(paramObject, paramInt);
/* 24 */     a(c(paramString), paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Map a() {
/* 29 */     return (Map)HashBiMap.create();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object get(String paramString) {
/* 35 */     return super.get(c(paramString));
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(Object paramObject) {
/* 40 */     return (String)this.b.get(paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b(String paramString) {
/* 45 */     return super.d(c(paramString));
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(Object paramObject) {
/* 50 */     return this.a.b(paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object a(int paramInt) {
/* 55 */     return this.a.a(paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator iterator() {
/* 60 */     return this.a.iterator();
/*    */   }
/*    */   
/*    */   public boolean b(int paramInt) {
/* 64 */     return this.a.b(paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   private static String c(String paramString) {
/* 69 */     return (paramString.indexOf(':') == -1) ? ("minecraft:" + paramString) : paramString;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RegistryMaterials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */