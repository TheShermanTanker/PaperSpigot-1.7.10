/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.io.Serializable;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ public class BeanPathUpdate
/*    */ {
/* 30 */   private final Map<String, BeanPathUpdateIds> map = new LinkedHashMap<String, BeanPathUpdateIds>();
/*    */ 
/*    */   
/*    */   public void add(BeanDescriptor<?> desc, String path, Object id) {
/* 34 */     String key = desc.getFullName() + ":" + path;
/* 35 */     BeanPathUpdateIds pathIds = this.map.get(key);
/* 36 */     if (pathIds == null) {
/* 37 */       pathIds = new BeanPathUpdateIds(desc, path);
/* 38 */       this.map.put(key, pathIds);
/*    */     } 
/* 40 */     pathIds.addId((Serializable)id);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\BeanPathUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */