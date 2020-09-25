/*    */ package com.avaje.ebeaninternal.server.bean;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebean.common.BeanList;
/*    */ import com.avaje.ebean.event.BeanFinder;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebean.meta.MetaQueryStatistic;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.query.CQueryPlan;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BFQueryStatisticFinder
/*    */   implements BeanFinder<MetaQueryStatistic>
/*    */ {
/*    */   public MetaQueryStatistic find(BeanQueryRequest<MetaQueryStatistic> request) {
/* 25 */     throw new RuntimeException("Not Supported yet");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BeanCollection<MetaQueryStatistic> findMany(BeanQueryRequest<MetaQueryStatistic> request) {
/* 33 */     SpiQuery.Type queryType = ((SpiQuery)request.getQuery()).getType();
/* 34 */     if (!queryType.equals(SpiQuery.Type.LIST)) {
/* 35 */       throw new PersistenceException("Only findList() supported at this stage.");
/*    */     }
/*    */     
/* 38 */     BeanList<MetaQueryStatistic> list = new BeanList();
/*    */     
/* 40 */     SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
/* 41 */     build((List<MetaQueryStatistic>)list, server);
/*    */     
/* 43 */     String orderBy = request.getQuery().order().toStringFormat();
/* 44 */     if (orderBy == null) {
/* 45 */       orderBy = "beanType, origQueryPlanHash, autofetchTuned";
/*    */     }
/* 47 */     server.sort((List)list, orderBy);
/*    */     
/* 49 */     return (BeanCollection<MetaQueryStatistic>)list;
/*    */   }
/*    */ 
/*    */   
/*    */   private void build(List<MetaQueryStatistic> list, SpiEbeanServer server) {
/* 54 */     for (BeanDescriptor<?> desc : (Iterable<BeanDescriptor<?>>)server.getBeanDescriptors()) {
/* 55 */       desc.clearQueryStatistics();
/* 56 */       build(list, desc);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void build(List<MetaQueryStatistic> list, BeanDescriptor<?> desc) {
/* 62 */     Iterator<CQueryPlan> it = desc.queryPlans();
/* 63 */     while (it.hasNext()) {
/* 64 */       CQueryPlan queryPlan = it.next();
/* 65 */       list.add(queryPlan.createMetaQueryStatistic(desc.getFullName()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\bean\BFQueryStatisticFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */