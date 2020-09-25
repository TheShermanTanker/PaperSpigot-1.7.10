/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
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
/*     */ public class RowDataStatic
/*     */   implements RowData
/*     */ {
/*     */   private Field[] metadata;
/*     */   private int index;
/*     */   ResultSetImpl owner;
/*     */   private List rows;
/*     */   
/*     */   public RowDataStatic(List rows) {
/*  53 */     this.index = -1;
/*  54 */     this.rows = rows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRow(ResultSetRow row) {
/*  64 */     this.rows.add(row);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void afterLast() {
/*  71 */     this.index = this.rows.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beforeFirst() {
/*  78 */     this.index = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beforeLast() {
/*  85 */     this.index = this.rows.size() - 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetRow getAt(int atIndex) throws SQLException {
/* 103 */     if (atIndex < 0 || atIndex >= this.rows.size()) {
/* 104 */       return null;
/*     */     }
/*     */     
/* 107 */     return ((ResultSetRow)this.rows.get(atIndex)).setMetadata(this.metadata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentRowNumber() {
/* 116 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetInternalMethods getOwner() {
/* 123 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/* 132 */     boolean hasMore = (this.index + 1 < this.rows.size());
/*     */     
/* 134 */     return hasMore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAfterLast() {
/* 143 */     return (this.index >= this.rows.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBeforeFirst() {
/* 152 */     return (this.index == -1 && this.rows.size() != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDynamic() {
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 170 */     return (this.rows.size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirst() {
/* 179 */     return (this.index == 0);
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
/*     */   public boolean isLast() {
/* 192 */     if (this.rows.size() == 0) {
/* 193 */       return false;
/*     */     }
/*     */     
/* 196 */     return (this.index == this.rows.size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveRowRelative(int rowsToMove) {
/* 206 */     this.index += rowsToMove;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetRow next() throws SQLException {
/* 215 */     this.index++;
/*     */     
/* 217 */     if (this.index < this.rows.size()) {
/* 218 */       ResultSetRow row = this.rows.get(this.index);
/*     */       
/* 220 */       return row.setMetadata(this.metadata);
/*     */     } 
/*     */     
/* 223 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeRow(int atIndex) {
/* 233 */     this.rows.remove(atIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentRow(int newIndex) {
/* 243 */     this.index = newIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwner(ResultSetImpl rs) {
/* 250 */     this.owner = rs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 259 */     return this.rows.size();
/*     */   }
/*     */   
/*     */   public boolean wasEmpty() {
/* 263 */     return (this.rows != null && this.rows.size() == 0);
/*     */   }
/*     */   
/*     */   public void setMetadata(Field[] metadata) {
/* 267 */     this.metadata = metadata;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\RowDataStatic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */