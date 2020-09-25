/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import com.avaje.ebean.event.BulkTableEvent;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public final class TransactionEventTable
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2236555729767483264L;
/*  19 */   private final Map<String, TableIUD> map = new HashMap<String, TableIUD>();
/*     */   
/*     */   public String toString() {
/*  22 */     return "TransactionEventTable " + this.map.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/*  27 */     for (TableIUD tableIud : this.map.values()) {
/*  28 */       tableIud.writeBinaryMessage(msgList);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readBinaryMessage(DataInput dataInput) throws IOException {
/*  34 */     TableIUD tableIud = TableIUD.readBinaryMessage(dataInput);
/*  35 */     this.map.put(tableIud.getTableName(), tableIud);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(TransactionEventTable table) {
/*  41 */     for (TableIUD iud : table.values()) {
/*  42 */       add(iud);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(String table, boolean insert, boolean update, boolean delete) {
/*  48 */     table = table.toUpperCase();
/*     */     
/*  50 */     add(new TableIUD(table, insert, update, delete));
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(TableIUD newTableIUD) {
/*  55 */     TableIUD existingTableIUD = this.map.put(newTableIUD.getTableName(), newTableIUD);
/*  56 */     if (existingTableIUD != null) {
/*  57 */       newTableIUD.add(existingTableIUD);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  62 */     return this.map.isEmpty();
/*     */   }
/*     */   
/*     */   public Collection<TableIUD> values() {
/*  66 */     return this.map.values();
/*     */   }
/*     */   
/*     */   public static class TableIUD
/*     */     implements Serializable, BulkTableEvent
/*     */   {
/*     */     private static final long serialVersionUID = -1958317571064162089L;
/*     */     private String table;
/*     */     private boolean insert;
/*     */     private boolean update;
/*     */     private boolean delete;
/*     */     
/*     */     private TableIUD(String table, boolean insert, boolean update, boolean delete) {
/*  79 */       this.table = table;
/*  80 */       this.insert = insert;
/*  81 */       this.update = update;
/*  82 */       this.delete = delete;
/*     */     }
/*     */ 
/*     */     
/*     */     public static TableIUD readBinaryMessage(DataInput dataInput) throws IOException {
/*  87 */       String table = dataInput.readUTF();
/*  88 */       boolean insert = dataInput.readBoolean();
/*  89 */       boolean update = dataInput.readBoolean();
/*  90 */       boolean delete = dataInput.readBoolean();
/*     */       
/*  92 */       return new TableIUD(table, insert, update, delete);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/*  97 */       BinaryMessage msg = new BinaryMessage(this.table.length() + 10);
/*  98 */       DataOutputStream os = msg.getOs();
/*  99 */       os.writeInt(2);
/* 100 */       os.writeUTF(this.table);
/* 101 */       os.writeBoolean(this.insert);
/* 102 */       os.writeBoolean(this.update);
/* 103 */       os.writeBoolean(this.delete);
/*     */       
/* 105 */       msgList.add(msg);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 109 */       return "TableIUD " + this.table + " i:" + this.insert + " u:" + this.update + " d:" + this.delete;
/*     */     }
/*     */     
/*     */     private void add(TableIUD other) {
/* 113 */       if (other.insert) {
/* 114 */         this.insert = true;
/*     */       }
/* 116 */       if (other.update) {
/* 117 */         this.update = true;
/*     */       }
/* 119 */       if (other.delete)
/* 120 */         this.delete = true; 
/*     */     }
/*     */     
/*     */     public String getTableName() {
/* 124 */       return this.table;
/*     */     }
/*     */     
/*     */     public boolean isInsert() {
/* 128 */       return this.insert;
/*     */     }
/*     */     
/*     */     public boolean isUpdate() {
/* 132 */       return this.update;
/*     */     }
/*     */     
/*     */     public boolean isDelete() {
/* 136 */       return this.delete;
/*     */     }
/*     */     
/*     */     public boolean isUpdateOrDelete() {
/* 140 */       return (this.update || this.delete);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\TransactionEventTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */