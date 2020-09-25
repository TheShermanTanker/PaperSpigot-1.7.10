/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
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
/*    */ public class TransientProperties
/*    */ {
/*    */   public void process(DeployBeanDescriptor<?> desc) {
/* 42 */     List<DeployBeanProperty> props = desc.propertiesBase();
/* 43 */     for (int i = 0; i < props.size(); i++) {
/* 44 */       DeployBeanProperty prop = props.get(i);
/* 45 */       if (!prop.isDbRead() && !prop.isDbInsertable() && !prop.isDbUpdateable())
/*    */       {
/* 47 */         prop.setTransient(true);
/*    */       }
/*    */     } 
/*    */     
/* 51 */     List<DeployBeanPropertyAssocOne<?>> ones = desc.propertiesAssocOne();
/* 52 */     for (int j = 0; j < ones.size(); j++) {
/* 53 */       DeployBeanPropertyAssocOne<?> prop = ones.get(j);
/* 54 */       if (prop.getBeanTable() == null && 
/* 55 */         !prop.isEmbedded()) {
/* 56 */         prop.setTransient(true);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 61 */     List<DeployBeanPropertyAssocMany<?>> manys = desc.propertiesAssocMany();
/* 62 */     for (int k = 0; k < manys.size(); k++) {
/* 63 */       DeployBeanPropertyAssocMany<?> prop = manys.get(k);
/* 64 */       if (prop.getBeanTable() == null)
/* 65 */         prop.setTransient(true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\TransientProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */