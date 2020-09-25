/*    */ package com.avaje.ebean.bean;public interface BeanCollection<E> extends Serializable { Object getOwnerBean();
/*    */   String getPropertyName();
/*    */   int getLoaderIndex();
/*    */   boolean checkEmptyLazyLoad();
/*    */   ExpressionList<?> getFilterMany();
/*    */   void setFilterMany(ExpressionList<?> paramExpressionList);
/*    */   void setBackgroundFetch(Future<Integer> paramFuture);
/*    */   void backgroundFetchWait(long paramLong, TimeUnit paramTimeUnit);
/*    */   void backgroundFetchWait();
/*    */   void setBeanCollectionTouched(BeanCollectionTouched paramBeanCollectionTouched);
/*    */   void setLoader(int paramInt, BeanCollectionLoader paramBeanCollectionLoader);
/*    */   void setReadOnly(boolean paramBoolean);
/*    */   
/*    */   boolean isReadOnly();
/*    */   
/*    */   void internalAdd(Object paramObject);
/*    */   
/*    */   Object getActualCollection();
/*    */   
/*    */   int size();
/*    */   
/*    */   boolean isEmpty();
/*    */   
/*    */   Collection<E> getActualDetails();
/*    */   
/*    */   boolean hasMoreRows();
/*    */   
/*    */   void setHasMoreRows(boolean paramBoolean);
/*    */   
/*    */   boolean isFinishedFetch();
/*    */   
/*    */   void setFinishedFetch(boolean paramBoolean);
/*    */   
/*    */   boolean isPopulated();
/*    */   
/*    */   boolean isReference();
/*    */   
/*    */   void setModifyListening(ModifyListenMode paramModifyListenMode);
/*    */   
/*    */   void modifyAddition(E paramE);
/*    */   
/*    */   void modifyRemoval(Object paramObject);
/*    */   
/*    */   Set<E> getModifyAdditions();
/*    */   
/*    */   Set<E> getModifyRemovals();
/*    */   
/*    */   void modifyReset();
/*    */   
/* 50 */   public enum ModifyListenMode { NONE,
/*    */     
/* 52 */     REMOVALS,
/*    */     
/* 54 */     ALL; }
/*    */    }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\bean\BeanCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */