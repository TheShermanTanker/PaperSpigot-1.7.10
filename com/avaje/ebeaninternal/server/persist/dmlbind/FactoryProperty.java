/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
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
/*    */ public class FactoryProperty
/*    */ {
/*    */   private final boolean bindEncryptDataFirst;
/*    */   
/*    */   public FactoryProperty(boolean bindEncryptDataFirst) {
/* 38 */     this.bindEncryptDataFirst = bindEncryptDataFirst;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Bindable create(BeanProperty prop, DmlMode mode, boolean withLobs) {
/* 46 */     if (DmlMode.INSERT.equals(mode) && !prop.isDbInsertable()) {
/* 47 */       return null;
/*    */     }
/* 49 */     if (DmlMode.UPDATE.equals(mode) && !prop.isDbUpdatable()) {
/* 50 */       return null;
/*    */     }
/*    */     
/* 53 */     if (prop.isLob()) {
/* 54 */       if (DmlMode.WHERE.equals(mode) || !withLobs)
/*    */       {
/* 56 */         return null;
/*    */       }
/* 58 */       return prop.isDbEncrypted() ? new BindableEncryptedProperty(prop, this.bindEncryptDataFirst) : new BindableProperty(prop);
/*    */     } 
/*    */ 
/*    */     
/* 62 */     GeneratedProperty gen = prop.getGeneratedProperty();
/* 63 */     if (gen != null) {
/* 64 */       if (DmlMode.INSERT.equals(mode)) {
/* 65 */         if (gen.includeInInsert()) {
/* 66 */           return new BindablePropertyInsertGenerated(prop, gen);
/*    */         }
/* 68 */         return null;
/*    */       } 
/*    */ 
/*    */       
/* 72 */       if (DmlMode.UPDATE.equals(mode)) {
/* 73 */         if (gen.includeInUpdate()) {
/* 74 */           return new BindablePropertyUpdateGenerated(prop, gen);
/*    */         }
/*    */         
/* 77 */         return null;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 82 */     return prop.isDbEncrypted() ? new BindableEncryptedProperty(prop, this.bindEncryptDataFirst) : new BindableProperty(prop);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\FactoryProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */