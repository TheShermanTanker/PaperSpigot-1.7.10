/*    */ package com.avaje.ebeaninternal.server.cache;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class CachedManyIds
/*    */ {
/*    */   private final List<Object> idList;
/*    */   
/*    */   public CachedManyIds(List<Object> idList) {
/* 10 */     this.idList = idList;
/*    */   }
/*    */   
/*    */   public List<Object> getIdList() {
/* 14 */     return this.idList;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cache\CachedManyIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */