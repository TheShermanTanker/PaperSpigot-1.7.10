/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.CompoundTypeProperty;
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
/*    */ public class CtCompoundProperty
/*    */ {
/*    */   private final String relativeName;
/*    */   private final CtCompoundProperty parent;
/*    */   private final CtCompoundType<?> compoundType;
/*    */   private final CompoundTypeProperty property;
/*    */   
/*    */   public CtCompoundProperty(String relativeName, CtCompoundProperty parent, CtCompoundType<?> ctType, CompoundTypeProperty<?, ?> property) {
/* 44 */     this.relativeName = relativeName;
/* 45 */     this.parent = parent;
/* 46 */     this.compoundType = ctType;
/* 47 */     this.property = property;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRelativeName() {
/* 54 */     return this.relativeName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPropertyName() {
/* 61 */     return this.property.getName();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     return this.relativeName;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue(Object valueObject) {
/* 70 */     if (valueObject == null) {
/* 71 */       return null;
/*    */     }
/* 73 */     if (this.parent != null) {
/* 74 */       valueObject = this.parent.getValue(valueObject);
/*    */     }
/* 76 */     return this.property.getValue(valueObject);
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
/*    */   public Object setValue(Object bean, Object value) {
/* 90 */     Object compoundValue = ImmutableCompoundTypeBuilder.set(this.compoundType, this.property.getName(), value);
/*    */     
/* 92 */     if (compoundValue != null && this.parent != null)
/*    */     {
/* 94 */       return this.parent.setValue(bean, compoundValue);
/*    */     }
/*    */     
/* 97 */     return compoundValue;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\CtCompoundProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */