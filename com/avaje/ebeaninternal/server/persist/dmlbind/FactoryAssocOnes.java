/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
/*    */ import java.util.List;
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
/*    */ public class FactoryAssocOnes
/*    */ {
/*    */   public List<Bindable> create(List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode) {
/* 41 */     BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = desc.propertiesOneImported();
/*    */     
/* 43 */     for (int i = 0; i < arrayOfBeanPropertyAssocOne.length; i++) {
/* 44 */       if (!arrayOfBeanPropertyAssocOne[i].isImportedPrimaryKey())
/*    */       {
/*    */ 
/*    */         
/* 48 */         switch (mode) {
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
/*    */           case WHERE:
/* 62 */             list.add(new BindableAssocOne(arrayOfBeanPropertyAssocOne[i])); break;case INSERT: if (!arrayOfBeanPropertyAssocOne[i].isInsertable()) break; case UPDATE: if (!arrayOfBeanPropertyAssocOne[i].isUpdateable()) break; default: list.add(new BindableAssocOne(arrayOfBeanPropertyAssocOne[i]));
/*    */             break;
/*    */         }  } 
/*    */     } 
/* 66 */     return list;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\FactoryAssocOnes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */