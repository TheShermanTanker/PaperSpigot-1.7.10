/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.common.BeanSet;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanSetHelp<T>
/*     */   implements BeanCollectionHelp<T>
/*     */ {
/*     */   private final BeanPropertyAssocMany<T> many;
/*     */   private final BeanDescriptor<T> targetDescriptor;
/*     */   private BeanCollectionLoader loader;
/*     */   
/*     */   public BeanSetHelp(BeanPropertyAssocMany<T> many) {
/*  31 */     this.many = many;
/*  32 */     this.targetDescriptor = many.getTargetDescriptor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanSetHelp() {
/*  39 */     this.many = null;
/*  40 */     this.targetDescriptor = null;
/*     */   }
/*     */   
/*     */   public void setLoader(BeanCollectionLoader loader) {
/*  44 */     this.loader = loader;
/*     */   }
/*     */   
/*     */   public Iterator<?> getIterator(Object collection) {
/*  48 */     return ((Set)collection).iterator();
/*     */   }
/*     */   
/*     */   public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) {
/*  52 */     if (bc instanceof BeanSet) {
/*  53 */       BeanSet<?> beanSet = (BeanSet)bc;
/*  54 */       if (beanSet.getActualSet() == null) {
/*  55 */         beanSet.setActualSet(new LinkedHashSet());
/*     */       }
/*  57 */       return (BeanCollectionAdd)beanSet;
/*  58 */     }  if (bc instanceof Set) {
/*  59 */       return new VanillaAdd((Set)bc);
/*     */     }
/*     */     
/*  62 */     throw new RuntimeException("Unhandled type " + bc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class VanillaAdd
/*     */     implements BeanCollectionAdd
/*     */   {
/*     */     private final Set set;
/*     */ 
/*     */     
/*     */     private VanillaAdd(Set<?> set) {
/*  74 */       this.set = set;
/*     */     }
/*     */     
/*     */     public void addBean(Object bean) {
/*  78 */       this.set.add(bean);
/*     */     }
/*     */   }
/*     */   
/*     */   public void add(BeanCollection<?> collection, Object bean) {
/*  83 */     collection.internalAdd(bean);
/*     */   }
/*     */   
/*     */   public Object createEmpty(boolean vanilla) {
/*  87 */     return vanilla ? new LinkedHashSet() : new BeanSet();
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanCollection<T> createReference(Object parentBean, String propertyName) {
/*  92 */     return (BeanCollection<T>)new BeanSet(this.loader, parentBean, propertyName);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<InvalidValue> validate(Object manyValue) {
/*  97 */     ArrayList<InvalidValue> errs = null;
/*     */     
/*  99 */     Set<?> set = (Set)manyValue;
/* 100 */     Iterator<?> i = set.iterator();
/* 101 */     while (i.hasNext()) {
/* 102 */       Object detailBean = i.next();
/* 103 */       InvalidValue invalid = this.targetDescriptor.validate(true, detailBean);
/* 104 */       if (invalid != null) {
/* 105 */         if (errs == null) {
/* 106 */           errs = new ArrayList<InvalidValue>();
/*     */         }
/* 108 */         errs.add(invalid);
/*     */       } 
/*     */     } 
/* 111 */     return errs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) {
/* 116 */     BeanSet<?> newBeanSet = (BeanSet)server.findSet(query, t);
/* 117 */     refresh((BeanCollection<?>)newBeanSet, parentBean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(BeanCollection<?> bc, Object parentBean) {
/* 122 */     BeanSet<?> newBeanSet = (BeanSet)bc;
/*     */     
/* 124 */     Set<?> current = (Set)this.many.getValueUnderlying(parentBean);
/*     */     
/* 126 */     newBeanSet.setModifyListening(this.many.getModifyListenMode());
/* 127 */     if (current == null) {
/*     */       
/* 129 */       this.many.setValue(parentBean, newBeanSet);
/*     */     }
/* 131 */     else if (current instanceof BeanSet) {
/*     */       
/* 133 */       BeanSet<?> currentBeanSet = (BeanSet)current;
/* 134 */       currentBeanSet.setActualSet(newBeanSet.getActualSet());
/* 135 */       currentBeanSet.setModifyListening(this.many.getModifyListenMode());
/*     */     }
/*     */     else {
/*     */       
/* 139 */       this.many.setValue(parentBean, newBeanSet);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, String name, Object collection, boolean explicitInclude) {
/*     */     Set<?> set;
/* 146 */     if (collection instanceof BeanCollection) {
/* 147 */       BeanSet<?> bc = (BeanSet)collection;
/* 148 */       if (!bc.isPopulated()) {
/* 149 */         if (explicitInclude) {
/*     */ 
/*     */           
/* 152 */           bc.size();
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       }
/* 157 */       set = bc.getActualSet();
/*     */     } else {
/* 159 */       set = (Set)collection;
/*     */     } 
/*     */     
/* 162 */     int count = 0;
/* 163 */     ctx.beginAssocMany(name);
/* 164 */     Iterator<?> it = set.iterator();
/* 165 */     while (it.hasNext()) {
/* 166 */       Object detailBean = it.next();
/* 167 */       if (count++ > 0) {
/* 168 */         ctx.appendComma();
/*     */       }
/* 170 */       this.targetDescriptor.jsonWrite(ctx, detailBean);
/*     */     } 
/* 172 */     ctx.endAssocMany();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanSetHelp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */