/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
/*    */ import java.util.ArrayList;
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
/*    */ public class FactoryEmbedded
/*    */ {
/*    */   private final FactoryProperty factoryProperty;
/*    */   
/*    */   public FactoryEmbedded(boolean bindEncryptDataFirst) {
/* 38 */     this.factoryProperty = new FactoryProperty(bindEncryptDataFirst);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void create(List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode, boolean withLobs) {
/* 46 */     BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = desc.propertiesEmbedded();
/*    */     
/* 48 */     for (int j = 0; j < arrayOfBeanPropertyAssocOne.length; j++) {
/*    */       
/* 50 */       List<Bindable> bindList = new ArrayList<Bindable>();
/*    */       
/* 52 */       BeanProperty[] props = arrayOfBeanPropertyAssocOne[j].getProperties();
/* 53 */       for (int i = 0; i < props.length; i++) {
/* 54 */         Bindable item = this.factoryProperty.create(props[i], mode, withLobs);
/* 55 */         if (item != null) {
/* 56 */           bindList.add(item);
/*    */         }
/*    */       } 
/*    */       
/* 60 */       list.add(new BindableEmbedded(arrayOfBeanPropertyAssocOne[j], bindList));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\FactoryEmbedded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */