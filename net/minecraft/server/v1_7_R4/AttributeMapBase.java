/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.com.google.common.collect.Multimap;
/*    */ 
/*    */ 
/*    */ public abstract class AttributeMapBase
/*    */ {
/* 11 */   protected final Map a = new HashMap<Object, Object>();
/* 12 */   protected final Map b = new InsensitiveStringMap();
/*    */   
/*    */   public AttributeInstance a(IAttribute paramIAttribute) {
/* 15 */     return (AttributeInstance)this.a.get(paramIAttribute);
/*    */   }
/*    */   
/*    */   public AttributeInstance a(String paramString) {
/* 19 */     return (AttributeInstance)this.b.get(paramString);
/*    */   }
/*    */   
/*    */   public abstract AttributeInstance b(IAttribute paramIAttribute);
/*    */   
/*    */   public Collection a() {
/* 25 */     return this.b.values();
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(AttributeModifiable paramAttributeModifiable) {}
/*    */ 
/*    */   
/*    */   public void a(Multimap paramMultimap) {
/* 33 */     for (Map.Entry entry : paramMultimap.entries()) {
/* 34 */       AttributeInstance attributeInstance = a((String)entry.getKey());
/*    */       
/* 36 */       if (attributeInstance != null) {
/* 37 */         attributeInstance.b((AttributeModifier)entry.getValue());
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b(Multimap paramMultimap) {
/* 43 */     for (Map.Entry entry : paramMultimap.entries()) {
/* 44 */       AttributeInstance attributeInstance = a((String)entry.getKey());
/*    */       
/* 46 */       if (attributeInstance != null) {
/* 47 */         attributeInstance.b((AttributeModifier)entry.getValue());
/* 48 */         attributeInstance.a((AttributeModifier)entry.getValue());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\AttributeMapBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */