/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertySimpleCollection;
/*    */ import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
/*    */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*    */ import java.util.Iterator;
/*    */ import javax.naming.NamingEnumeration;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.directory.Attribute;
/*    */ import javax.naming.directory.BasicAttribute;
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
/*    */ public class BeanPropertySimpleCollection<T>
/*    */   extends BeanPropertyAssocMany<T>
/*    */ {
/*    */   private final ScalarType<T> collectionScalarType;
/*    */   
/*    */   public BeanPropertySimpleCollection(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertySimpleCollection<T> deploy) {
/* 39 */     super(owner, descriptor, (DeployBeanPropertyAssocMany<T>)deploy);
/* 40 */     this.collectionScalarType = deploy.getCollectionScalarType();
/*    */   }
/*    */   
/*    */   public void initialise() {
/* 44 */     super.initialise();
/*    */   }
/*    */ 
/*    */   
/*    */   public Attribute createAttribute(Object bean) {
/* 49 */     Object v = getValue(bean);
/* 50 */     if (v == null) {
/* 51 */       return null;
/*    */     }
/* 53 */     if (this.ldapAttributeAdapter != null) {
/* 54 */       return this.ldapAttributeAdapter.createAttribute(v);
/*    */     }
/*    */     
/* 57 */     BasicAttribute attrs = new BasicAttribute(getDbColumn());
/*    */     
/* 59 */     Iterator<?> it = this.help.getIterator(v);
/* 60 */     if (it != null) {
/* 61 */       while (it.hasNext()) {
/* 62 */         Object beanValue = it.next();
/* 63 */         Object attrValue = this.collectionScalarType.toJdbcType(beanValue);
/* 64 */         attrs.add(attrValue);
/*    */       } 
/*    */     }
/* 67 */     return attrs;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAttributeValue(Object bean, Attribute attr) {
/*    */     try {
/* 73 */       if (attr != null) {
/*    */         Object beanValue;
/* 75 */         if (this.ldapAttributeAdapter != null) {
/* 76 */           beanValue = this.ldapAttributeAdapter.readAttribute(attr);
/*    */         } else {
/*    */           
/* 79 */           boolean vanilla = true;
/* 80 */           beanValue = this.help.createEmpty(vanilla);
/* 81 */           BeanCollectionAdd collAdd = this.help.getBeanCollectionAdd(beanValue, this.mapKey);
/*    */           
/* 83 */           NamingEnumeration<?> en = attr.getAll();
/* 84 */           while (en.hasMoreElements()) {
/* 85 */             Object attrValue = en.nextElement();
/* 86 */             Object collValue = this.collectionScalarType.toBeanType(attrValue);
/* 87 */             collAdd.addBean(collValue);
/*    */           } 
/*    */         } 
/*    */         
/* 91 */         setValue(bean, beanValue);
/*    */       } 
/* 93 */     } catch (NamingException e) {
/* 94 */       throw new LdapPersistenceException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertySimpleCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */