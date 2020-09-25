/*    */ package com.avaje.ebeaninternal.api;public interface SpiQuery<T> extends Query<T> { void setTotalHits(int paramInt); boolean selectAllForLazyLoadProperty(); void setMode(Mode paramMode); Mode getMode(); BeanCollectionTouched getBeanCollectionTouched(); void setBeanCollectionTouched(BeanCollectionTouched paramBeanCollectionTouched); void setIdList(List<Object> paramList); List<Object> getIdList(); SpiQuery<T> copy(); Type getType(); void setType(Type paramType); String getLoadDescription(); String getLoadMode(); void setLoadDescription(String paramString1, String paramString2); void setBeanDescriptor(BeanDescriptor<?> paramBeanDescriptor); boolean initManyWhereJoins(); ManyWhereJoins getManyWhereJoins(); void convertWhereNaturalKeyToId(Object paramObject); NaturalKeyBindParam getNaturalKeyBindParam(); void setSelectId(); void setFilterMany(String paramString, ExpressionList<?> paramExpressionList); List<OrmQueryProperties> removeQueryJoins(); List<OrmQueryProperties> removeLazyJoins(); void setLazyLoadManyPath(String paramString); void convertManyFetchJoinsToQueryJoins(boolean paramBoolean, int paramInt); PersistenceContext getPersistenceContext(); void setPersistenceContext(PersistenceContext paramPersistenceContext); boolean isDetailEmpty(); Boolean isAutofetch(); Boolean isForUpdate(); AutoFetchManager getAutoFetchManager(); void setAutoFetchManager(AutoFetchManager paramAutoFetchManager); ObjectGraphNode setOrigin(CallStack paramCallStack); void setParentNode(ObjectGraphNode paramObjectGraphNode); void setLazyLoadProperty(String paramString); String getLazyLoadProperty(); String getLazyLoadManyPath(); ObjectGraphNode getParentNode(); boolean isUsageProfiling();
/*    */   void setUsageProfiling(boolean paramBoolean);
/*    */   String getName();
/*    */   int queryAutofetchHash();
/*    */   int queryPlanHash(BeanQueryRequest<?> paramBeanQueryRequest);
/*    */   int queryBindHash();
/*    */   int queryHash();
/*    */   boolean isSqlSelect();
/*    */   boolean isRawSql();
/*    */   OrderBy<T> getOrderBy();
/*    */   String getAdditionalWhere();
/*    */   SpiExpressionList<T> getWhereExpressions();
/*    */   SpiExpressionList<T> getHavingExpressions();
/*    */   String getAdditionalHaving();
/*    */   boolean hasMaxRowsOrFirstRow();
/*    */   Boolean isUseBeanCache();
/*    */   boolean isUseQueryCache();
/*    */   boolean isLoadBeanCache();
/*    */   Boolean isReadOnly();
/*    */   void contextAdd(EntityBean paramEntityBean);
/*    */   Class<T> getBeanType();
/*    */   int getTimeout();
/*    */   ArrayList<EntityBean> getContextAdditions();
/*    */   BindParams getBindParams();
/*    */   String getQuery();
/*    */   void setDetail(OrmQueryDetail paramOrmQueryDetail);
/*    */   boolean tuneFetchProperties(OrmQueryDetail paramOrmQueryDetail);
/*    */   void setAutoFetchTuned(boolean paramBoolean);
/*    */   OrmQueryDetail getDetail();
/*    */   TableJoin getIncludeTableJoin();
/*    */   void setIncludeTableJoin(TableJoin paramTableJoin);
/*    */   String getMapKey();
/*    */   int getBackgroundFetchAfter();
/*    */   int getMaxRows();
/*    */   int getFirstRow();
/*    */   boolean isDistinct();
/*    */   boolean isVanillaMode(boolean paramBoolean);
/*    */   void setDefaultSelectClause();
/*    */   String getRawWhereClause();
/*    */   Object getId();
/*    */   QueryListener<T> getListener();
/*    */   boolean createOwnTransaction();
/*    */   void setGeneratedSql(String paramString);
/*    */   int getBufferFetchSizeHint();
/*    */   boolean isFutureFetch();
/*    */   void setFutureFetch(boolean paramBoolean);
/*    */   void setCancelableQuery(CancelableQuery paramCancelableQuery);
/*    */   boolean isCancelled();
/* 49 */   public enum Mode { NORMAL(false), LAZYLOAD_MANY(false), LAZYLOAD_BEAN(true), REFRESH_BEAN(true); private final boolean loadContextBean;
/*    */     Mode(boolean loadContextBean) {
/* 51 */       this.loadContextBean = loadContextBean;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean isLoadContextBean() {
/* 57 */       return this.loadContextBean;
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Type
/*    */   {
/* 69 */     BEAN,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 74 */     LIST,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 79 */     SET,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 84 */     MAP,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 89 */     ID_LIST,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 94 */     ROWCOUNT,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 99 */     SUBQUERY;
/*    */   } }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */