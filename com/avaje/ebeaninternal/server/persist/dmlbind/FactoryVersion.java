/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactoryVersion
/*    */ {
/*    */   public Bindable create(BeanDescriptor<?> desc) {
/* 43 */     List<Bindable> verList = new ArrayList<Bindable>();
/*    */     
/* 45 */     BeanProperty[] vers = desc.propertiesVersion();
/* 46 */     for (int i = 0; i < vers.length; i++) {
/* 47 */       verList.add(new BindableProperty(vers[i]));
/*    */     }
/*    */ 
/*    */     
/* 51 */     BeanPropertyAssocOne[] arrayOfBeanPropertyAssocOne = desc.propertiesEmbedded();
/* 52 */     for (int j = 0; j < arrayOfBeanPropertyAssocOne.length; j++) {
/*    */       
/* 54 */       if (arrayOfBeanPropertyAssocOne[j].isEmbeddedVersion()) {
/*    */         
/* 56 */         List<Bindable> bindList = new ArrayList<Bindable>();
/*    */         
/* 58 */         BeanProperty[] embProps = arrayOfBeanPropertyAssocOne[j].getProperties();
/*    */         
/* 60 */         for (int k = 0; k < embProps.length; k++) {
/* 61 */           if (embProps[k].isVersion()) {
/* 62 */             bindList.add(new BindableProperty(embProps[k]));
/*    */           }
/*    */         } 
/*    */         
/* 66 */         verList.add(new BindableEmbedded(arrayOfBeanPropertyAssocOne[j], bindList));
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     if (verList.size() == 0) {
/* 71 */       return null;
/*    */     }
/* 73 */     if (verList.size() == 1) {
/* 74 */       return verList.get(0);
/*    */     }
/*    */     
/* 77 */     return new BindableList(verList);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\FactoryVersion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */