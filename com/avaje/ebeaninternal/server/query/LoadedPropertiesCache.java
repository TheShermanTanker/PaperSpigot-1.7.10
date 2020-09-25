/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ 
/*    */ public class LoadedPropertiesCache
/*    */ {
/* 12 */   static ConcurrentHashMap<Integer, Set<String>> cache = new ConcurrentHashMap<Integer, Set<String>>(250, 0.75F, 16);
/*    */ 
/*    */   
/*    */   public static Set<String> get(int partialHash, Set<String> partialProps, BeanDescriptor<?> desc) {
/* 16 */     int manyHash = desc.getNamesOfManyPropsHash();
/* 17 */     int totalHash = 37 * partialHash + manyHash;
/*    */     
/* 19 */     Integer key = Integer.valueOf(totalHash);
/*    */     
/* 21 */     Set<String> includedProps = cache.get(key);
/*    */     
/* 23 */     if (includedProps == null) {
/*    */       
/* 25 */       LinkedHashSet<String> mergeNames = new LinkedHashSet<String>();
/* 26 */       mergeNames.addAll(partialProps);
/* 27 */       if (manyHash != 0) {
/* 28 */         mergeNames.addAll(desc.getNamesOfManyProps());
/*    */       }
/*    */ 
/*    */       
/* 32 */       includedProps = Collections.unmodifiableSet(mergeNames);
/* 33 */       cache.put(key, includedProps);
/*    */     } 
/*    */     
/* 36 */     return includedProps;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\LoadedPropertiesCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */