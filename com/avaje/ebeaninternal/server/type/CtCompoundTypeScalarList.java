/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.CompoundTypeProperty;
/*    */ import com.avaje.ebeaninternal.server.query.SplitName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ public final class CtCompoundTypeScalarList
/*    */ {
/* 38 */   private final LinkedHashMap<String, ScalarType<?>> scalarProps = new LinkedHashMap<String, ScalarType<?>>();
/*    */   
/* 40 */   private final LinkedHashMap<String, CtCompoundProperty> compoundProperties = new LinkedHashMap<String, CtCompoundProperty>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<CtCompoundProperty> getNonScalarProperties() {
/* 47 */     List<CtCompoundProperty> nonScalarProps = new ArrayList<CtCompoundProperty>();
/*    */     
/* 49 */     for (String propKey : this.compoundProperties.keySet()) {
/* 50 */       if (!this.scalarProps.containsKey(propKey)) {
/* 51 */         nonScalarProps.add(this.compoundProperties.get(propKey));
/*    */       }
/*    */     } 
/*    */     
/* 55 */     return nonScalarProps;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addCompoundProperty(String propName, CtCompoundType<?> t, CompoundTypeProperty<?, ?> prop) {
/* 63 */     CtCompoundProperty parent = null;
/* 64 */     String[] split = SplitName.split(propName);
/* 65 */     if (split[0] != null) {
/* 66 */       parent = this.compoundProperties.get(split[0]);
/*    */     }
/*    */     
/* 69 */     CtCompoundProperty p = new CtCompoundProperty(propName, parent, t, prop);
/* 70 */     this.compoundProperties.put(propName, p);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addScalarType(String propName, ScalarType<?> scalar) {
/* 77 */     this.scalarProps.put(propName, scalar);
/*    */   }
/*    */   
/*    */   public CtCompoundProperty getCompoundType(String propName) {
/* 81 */     return this.compoundProperties.get(propName);
/*    */   }
/*    */   
/*    */   public Set<Map.Entry<String, ScalarType<?>>> entries() {
/* 85 */     return this.scalarProps.entrySet();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\CtCompoundTypeScalarList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */