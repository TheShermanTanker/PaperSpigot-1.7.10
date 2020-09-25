/*    */ package com.avaje.ebeaninternal.server.bean;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebean.common.BeanList;
/*    */ import com.avaje.ebean.event.BeanFinder;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebean.meta.MetaAutoFetchTunedQueryInfo;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*    */ import com.avaje.ebeaninternal.server.autofetch.TunedQueryInfo;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BFAutoFetchTunedFetchFinder
/*    */   implements BeanFinder<MetaAutoFetchTunedQueryInfo>
/*    */ {
/*    */   public MetaAutoFetchTunedQueryInfo find(BeanQueryRequest<MetaAutoFetchTunedQueryInfo> request) {
/* 25 */     SpiQuery<?> query = (SpiQuery)request.getQuery();
/*    */     try {
/* 27 */       String queryPointKey = (String)query.getId();
/*    */       
/* 29 */       SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
/* 30 */       AutoFetchManager manager = server.getAutoFetchManager();
/*    */       
/* 32 */       TunedQueryInfo tunedFetch = manager.getTunedQueryInfo(queryPointKey);
/* 33 */       if (tunedFetch != null) {
/* 34 */         return tunedFetch.createPublicMeta();
/*    */       }
/* 36 */       return null;
/*    */     
/*    */     }
/* 39 */     catch (Exception e) {
/* 40 */       throw new PersistenceException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BeanCollection<MetaAutoFetchTunedQueryInfo> findMany(BeanQueryRequest<MetaAutoFetchTunedQueryInfo> request) {
/* 49 */     SpiQuery.Type queryType = ((SpiQuery)request.getQuery()).getType();
/* 50 */     if (!queryType.equals(SpiQuery.Type.LIST)) {
/* 51 */       throw new PersistenceException("Only findList() supported at this stage.");
/*    */     }
/*    */     
/* 54 */     SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
/* 55 */     AutoFetchManager manager = server.getAutoFetchManager();
/*    */     
/* 57 */     BeanList<MetaAutoFetchTunedQueryInfo> list = new BeanList();
/*    */     
/* 59 */     Iterator<TunedQueryInfo> it = manager.iterateTunedQueryInfo();
/* 60 */     while (it.hasNext()) {
/* 61 */       TunedQueryInfo tunedFetch = it.next();
/*    */       
/* 63 */       list.add(tunedFetch.createPublicMeta());
/*    */     } 
/*    */     
/* 66 */     String orderBy = request.getQuery().order().toStringFormat();
/* 67 */     if (orderBy == null) {
/* 68 */       orderBy = "beanType, origQueryPlanHash";
/*    */     }
/* 70 */     server.sort((List)list, orderBy);
/*    */ 
/*    */     
/* 73 */     return (BeanCollection<MetaAutoFetchTunedQueryInfo>)list;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\bean\BFAutoFetchTunedFetchFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */