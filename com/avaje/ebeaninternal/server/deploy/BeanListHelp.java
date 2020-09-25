/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.common.BeanList;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanListHelp<T>
/*     */   implements BeanCollectionHelp<T>
/*     */ {
/*     */   private final BeanPropertyAssocMany<T> many;
/*     */   private final BeanDescriptor<T> targetDescriptor;
/*     */   private BeanCollectionLoader loader;
/*     */   
/*     */   public BeanListHelp(BeanPropertyAssocMany<T> many) {
/*  27 */     this.many = many;
/*  28 */     this.targetDescriptor = many.getTargetDescriptor();
/*     */   }
/*     */   
/*     */   public BeanListHelp() {
/*  32 */     this.many = null;
/*  33 */     this.targetDescriptor = null;
/*     */   }
/*     */   
/*     */   public void setLoader(BeanCollectionLoader loader) {
/*  37 */     this.loader = loader;
/*     */   }
/*     */   
/*     */   public void add(BeanCollection<?> collection, Object bean) {
/*  41 */     collection.internalAdd(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) {
/*  46 */     if (bc instanceof BeanList) {
/*     */       
/*  48 */       BeanList<?> bl = (BeanList)bc;
/*  49 */       if (bl.getActualList() == null) {
/*  50 */         bl.setActualList(new ArrayList());
/*     */       }
/*  52 */       return (BeanCollectionAdd)bl;
/*  53 */     }  if (bc instanceof List) {
/*  54 */       return new VanillaAdd((List)bc);
/*     */     }
/*     */     
/*  57 */     throw new RuntimeException("Unhandled type " + bc);
/*     */   }
/*     */ 
/*     */   
/*     */   static class VanillaAdd
/*     */     implements BeanCollectionAdd
/*     */   {
/*     */     private final List list;
/*     */ 
/*     */     
/*     */     private VanillaAdd(List<?> list) {
/*  68 */       this.list = list;
/*     */     }
/*     */     
/*     */     public void addBean(Object bean) {
/*  72 */       this.list.add(bean);
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator<?> getIterator(Object collection) {
/*  77 */     return ((List)collection).iterator();
/*     */   }
/*     */   
/*     */   public Object createEmpty(boolean vanilla) {
/*  81 */     return vanilla ? new ArrayList() : new BeanList();
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanCollection<T> createReference(Object parentBean, String propertyName) {
/*  86 */     return (BeanCollection<T>)new BeanList(this.loader, parentBean, propertyName);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<InvalidValue> validate(Object manyValue) {
/*  91 */     ArrayList<InvalidValue> errs = null;
/*     */     
/*  93 */     List<?> l = (List)manyValue;
/*  94 */     for (int i = 0; i < l.size(); i++) {
/*  95 */       Object detailBean = l.get(i);
/*  96 */       InvalidValue invalid = this.targetDescriptor.validate(true, detailBean);
/*  97 */       if (invalid != null) {
/*  98 */         if (errs == null) {
/*  99 */           errs = new ArrayList<InvalidValue>();
/*     */         }
/* 101 */         errs.add(invalid);
/*     */       } 
/*     */     } 
/* 104 */     return errs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) {
/* 109 */     BeanList<?> newBeanList = (BeanList)server.findList(query, t);
/* 110 */     refresh((BeanCollection<?>)newBeanList, parentBean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(BeanCollection<?> bc, Object parentBean) {
/* 115 */     BeanList<?> newBeanList = (BeanList)bc;
/*     */     
/* 117 */     List<?> currentList = (List)this.many.getValueUnderlying(parentBean);
/*     */     
/* 119 */     newBeanList.setModifyListening(this.many.getModifyListenMode());
/*     */     
/* 121 */     if (currentList == null) {
/*     */       
/* 123 */       this.many.setValue(parentBean, newBeanList);
/*     */     }
/* 125 */     else if (currentList instanceof BeanList) {
/*     */       
/* 127 */       BeanList<?> currentBeanList = (BeanList)currentList;
/* 128 */       currentBeanList.setActualList(newBeanList.getActualList());
/* 129 */       currentBeanList.setModifyListening(this.many.getModifyListenMode());
/*     */     }
/*     */     else {
/*     */       
/* 133 */       this.many.setValue(parentBean, newBeanList);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, String name, Object collection, boolean explicitInclude) {
/*     */     List<?> list;
/* 140 */     if (collection instanceof BeanCollection) {
/* 141 */       BeanList<?> beanList = (BeanList)collection;
/* 142 */       if (!beanList.isPopulated()) {
/* 143 */         if (explicitInclude) {
/*     */ 
/*     */           
/* 146 */           beanList.size();
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       }
/* 151 */       list = beanList.getActualList();
/*     */     } else {
/* 153 */       list = (List)collection;
/*     */     } 
/*     */     
/* 156 */     ctx.beginAssocMany(name);
/* 157 */     for (int j = 0; j < list.size(); j++) {
/* 158 */       if (j > 0) {
/* 159 */         ctx.appendComma();
/*     */       }
/* 161 */       Object detailBean = list.get(j);
/* 162 */       this.targetDescriptor.jsonWrite(ctx, detailBean);
/*     */     } 
/* 164 */     ctx.endAssocMany();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanListHelp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */