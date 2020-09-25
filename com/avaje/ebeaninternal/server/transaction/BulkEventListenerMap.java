/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebean.event.BulkTableEvent;
/*    */ import com.avaje.ebean.event.BulkTableEventListener;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class BulkEventListenerMap
/*    */ {
/* 13 */   private final HashMap<String, Entry> map = new HashMap<String, Entry>();
/*    */ 
/*    */   
/*    */   public BulkEventListenerMap(List<BulkTableEventListener> listeners) {
/* 17 */     if (listeners != null) {
/* 18 */       for (BulkTableEventListener l : listeners) {
/* 19 */         Set<String> tables = l.registeredTables();
/* 20 */         for (String tableName : tables) {
/* 21 */           register(tableName, l);
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 28 */     return this.map.isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(BulkTableEvent event) {
/* 33 */     Entry entry = this.map.get(event.getTableName());
/* 34 */     if (entry != null) {
/* 35 */       entry.process(event);
/*    */     }
/*    */   }
/*    */   
/*    */   private void register(String tableName, BulkTableEventListener l) {
/* 40 */     String upperTableName = tableName.trim().toUpperCase();
/* 41 */     Entry entry = this.map.get(upperTableName);
/* 42 */     if (entry == null) {
/* 43 */       entry = new Entry();
/* 44 */       this.map.put(upperTableName, entry);
/*    */     } 
/* 46 */     entry.add(l);
/*    */   }
/*    */   
/*    */   private static class Entry
/*    */   {
/* 51 */     List<BulkTableEventListener> listeners = new ArrayList<BulkTableEventListener>();
/*    */     private void add(BulkTableEventListener l) {
/* 53 */       this.listeners.add(l);
/*    */     }
/*    */     
/*    */     private void process(BulkTableEvent event) {
/* 57 */       for (int i = 0; i < this.listeners.size(); i++)
/* 58 */         ((BulkTableEventListener)this.listeners.get(i)).process(event); 
/*    */     }
/*    */     
/*    */     private Entry() {}
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\BulkEventListenerMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */