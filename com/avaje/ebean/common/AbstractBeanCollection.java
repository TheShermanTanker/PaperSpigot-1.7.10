/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.Ebean;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.bean.BeanCollectionTouched;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractBeanCollection<E>
/*     */   implements BeanCollection<E>
/*     */ {
/*     */   private static final long serialVersionUID = 3365725236140187588L;
/*     */   protected boolean readOnly;
/*     */   protected transient BeanCollectionLoader loader;
/*     */   protected transient ExpressionList<?> filterMany;
/*     */   protected int loaderIndex;
/*     */   protected String ebeanServerName;
/*     */   protected transient BeanCollectionTouched beanCollectionTouched;
/*     */   protected transient Future<Integer> fetchFuture;
/*     */   protected final Object ownerBean;
/*     */   protected final String propertyName;
/*     */   protected boolean finishedFetch = true;
/*     */   protected boolean hasMoreRows;
/*     */   protected ModifyHolder<E> modifyHolder;
/*     */   protected BeanCollection.ModifyListenMode modifyListenMode;
/*     */   protected boolean modifyAddListening;
/*     */   protected boolean modifyRemoveListening;
/*     */   protected boolean modifyListening;
/*     */   
/*     */   public AbstractBeanCollection() {
/*  97 */     this.ownerBean = null;
/*  98 */     this.propertyName = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractBeanCollection(BeanCollectionLoader loader, Object ownerBean, String propertyName) {
/* 105 */     this.loader = loader;
/* 106 */     this.ebeanServerName = loader.getName();
/* 107 */     this.ownerBean = ownerBean;
/* 108 */     this.propertyName = propertyName;
/*     */     
/* 110 */     if (ownerBean instanceof EntityBean) {
/* 111 */       EntityBeanIntercept ebi = ((EntityBean)ownerBean)._ebean_getIntercept();
/* 112 */       this.readOnly = ebi.isReadOnly();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getOwnerBean() {
/* 117 */     return this.ownerBean;
/*     */   }
/*     */   
/*     */   public String getPropertyName() {
/* 121 */     return this.propertyName;
/*     */   }
/*     */   
/*     */   public int getLoaderIndex() {
/* 125 */     return this.loaderIndex;
/*     */   }
/*     */   
/*     */   public ExpressionList<?> getFilterMany() {
/* 129 */     return this.filterMany;
/*     */   }
/*     */   
/*     */   public void setFilterMany(ExpressionList<?> filterMany) {
/* 133 */     this.filterMany = filterMany;
/*     */   }
/*     */   
/*     */   protected void lazyLoadCollection(boolean onlyIds) {
/* 137 */     if (this.loader == null) {
/* 138 */       this.loader = (BeanCollectionLoader)Ebean.getServer(this.ebeanServerName);
/*     */     }
/* 140 */     if (this.loader == null) {
/* 141 */       String msg = "Lazy loading but LazyLoadEbeanServer is null? The LazyLoadEbeanServer needs to be set after deserialization to support lazy loading.";
/*     */ 
/*     */       
/* 144 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     
/* 147 */     this.loader.loadMany(this, onlyIds);
/* 148 */     checkEmptyLazyLoad();
/*     */   }
/*     */   
/*     */   protected void touched() {
/* 152 */     if (this.beanCollectionTouched != null) {
/*     */       
/* 154 */       this.beanCollectionTouched.notifyTouched(this);
/* 155 */       this.beanCollectionTouched = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBeanCollectionTouched(BeanCollectionTouched notify) {
/* 160 */     this.beanCollectionTouched = notify;
/*     */   }
/*     */   
/*     */   public void setLoader(int beanLoaderIndex, BeanCollectionLoader loader) {
/* 164 */     this.loaderIndex = beanLoaderIndex;
/* 165 */     this.loader = loader;
/* 166 */     this.ebeanServerName = loader.getName();
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 170 */     return this.readOnly;
/*     */   }
/*     */   
/*     */   public void setReadOnly(boolean readOnly) {
/* 174 */     this.readOnly = readOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMoreRows() {
/* 188 */     return this.hasMoreRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHasMoreRows(boolean hasMoreRows) {
/* 197 */     this.hasMoreRows = hasMoreRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFinishedFetch() {
/* 205 */     return this.finishedFetch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFinishedFetch(boolean finishedFetch) {
/* 213 */     this.finishedFetch = finishedFetch;
/*     */   }
/*     */   
/*     */   public void setBackgroundFetch(Future<Integer> fetchFuture) {
/* 217 */     this.fetchFuture = fetchFuture;
/*     */   }
/*     */   
/*     */   public void backgroundFetchWait(long wait, TimeUnit timeUnit) {
/* 221 */     if (this.fetchFuture != null) {
/*     */       try {
/* 223 */         this.fetchFuture.get(wait, timeUnit);
/* 224 */       } catch (Exception e) {
/* 225 */         throw new PersistenceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void backgroundFetchWait() {
/* 231 */     if (this.fetchFuture != null) {
/*     */       try {
/* 233 */         this.fetchFuture.get();
/* 234 */       } catch (Exception e) {
/* 235 */         throw new PersistenceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void checkReadOnly() {
/* 241 */     if (this.readOnly) {
/* 242 */       String msg = "This collection is in ReadOnly mode";
/* 243 */       throw new IllegalStateException(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModifyListening(BeanCollection.ModifyListenMode mode) {
/* 259 */     this.modifyListenMode = mode;
/* 260 */     this.modifyAddListening = BeanCollection.ModifyListenMode.ALL.equals(mode);
/* 261 */     this.modifyRemoveListening = (this.modifyAddListening || BeanCollection.ModifyListenMode.REMOVALS.equals(mode));
/* 262 */     this.modifyListening = (this.modifyRemoveListening || this.modifyAddListening);
/* 263 */     if (this.modifyListening)
/*     */     {
/* 265 */       this.modifyHolder = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollection.ModifyListenMode getModifyListenMode() {
/* 273 */     return this.modifyListenMode;
/*     */   }
/*     */   
/*     */   protected ModifyHolder<E> getModifyHolder() {
/* 277 */     if (this.modifyHolder == null) {
/* 278 */       this.modifyHolder = new ModifyHolder<E>();
/*     */     }
/* 280 */     return this.modifyHolder;
/*     */   }
/*     */   
/*     */   public void modifyAddition(E bean) {
/* 284 */     if (this.modifyAddListening) {
/* 285 */       getModifyHolder().modifyAddition(bean);
/*     */     }
/*     */   }
/*     */   
/*     */   public void modifyRemoval(Object bean) {
/* 290 */     if (this.modifyRemoveListening) {
/* 291 */       getModifyHolder().modifyRemoval(bean);
/*     */     }
/*     */   }
/*     */   
/*     */   public void modifyReset() {
/* 296 */     if (this.modifyHolder != null) {
/* 297 */       this.modifyHolder.reset();
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<E> getModifyAdditions() {
/* 302 */     if (this.modifyHolder == null) {
/* 303 */       return null;
/*     */     }
/* 305 */     return this.modifyHolder.getModifyAdditions();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<E> getModifyRemovals() {
/* 310 */     if (this.modifyHolder == null) {
/* 311 */       return null;
/*     */     }
/* 313 */     return this.modifyHolder.getModifyRemovals();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\common\AbstractBeanCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */