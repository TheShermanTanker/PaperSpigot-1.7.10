/*    */ package com.avaje.ebean;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import javax.persistence.PersistenceException;
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
/*    */ final class DRawSqlColumnsParser
/*    */ {
/*    */   private final int end;
/*    */   private final String sqlSelect;
/*    */   private int pos;
/*    */   private int indexPos;
/*    */   
/*    */   public static RawSql.ColumnMapping parse(String sqlSelect) {
/* 25 */     return (new DRawSqlColumnsParser(sqlSelect)).parse();
/*    */   }
/*    */   
/*    */   private DRawSqlColumnsParser(String sqlSelect) {
/* 29 */     this.sqlSelect = sqlSelect;
/* 30 */     this.end = sqlSelect.length();
/*    */   }
/*    */ 
/*    */   
/*    */   private RawSql.ColumnMapping parse() {
/* 35 */     ArrayList<RawSql.ColumnMapping.Column> columns = new ArrayList<RawSql.ColumnMapping.Column>();
/* 36 */     while (this.pos <= this.end) {
/* 37 */       RawSql.ColumnMapping.Column c = nextColumnInfo();
/* 38 */       columns.add(c);
/*    */     } 
/*    */     
/* 41 */     return new RawSql.ColumnMapping(columns);
/*    */   }
/*    */   
/*    */   private RawSql.ColumnMapping.Column nextColumnInfo() {
/* 45 */     int start = this.pos;
/* 46 */     nextComma();
/* 47 */     String colInfo = this.sqlSelect.substring(start, this.pos++);
/* 48 */     colInfo = colInfo.trim();
/*    */     
/* 50 */     String[] split = colInfo.split(" ");
/* 51 */     if (split.length > 1) {
/* 52 */       ArrayList<String> tmp = new ArrayList<String>(split.length);
/* 53 */       for (int i = 0; i < split.length; i++) {
/* 54 */         if (split[i].trim().length() > 0) {
/* 55 */           tmp.add(split[i].trim());
/*    */         }
/*    */       } 
/* 58 */       split = tmp.<String>toArray(new String[tmp.size()]);
/*    */     } 
/*    */     
/* 61 */     if (split.length == 1)
/*    */     {
/* 63 */       return new RawSql.ColumnMapping.Column(this.indexPos++, split[0], null);
/*    */     }
/* 65 */     if (split.length == 2) {
/* 66 */       return new RawSql.ColumnMapping.Column(this.indexPos++, split[0], split[1]);
/*    */     }
/* 68 */     if (split.length == 3) {
/* 69 */       if (!split[1].equalsIgnoreCase("as")) {
/* 70 */         String str = "Expecting AS keyword parsing column " + colInfo;
/* 71 */         throw new PersistenceException(str);
/*    */       } 
/* 73 */       return new RawSql.ColumnMapping.Column(this.indexPos++, split[0], split[2]);
/*    */     } 
/*    */     
/* 76 */     String msg = "Expecting Max 3 words parsing column " + colInfo + ". Got " + Arrays.toString((Object[])split);
/* 77 */     throw new PersistenceException(msg);
/*    */   }
/*    */ 
/*    */   
/*    */   private int nextComma() {
/* 82 */     boolean inQuote = false;
/* 83 */     while (this.pos < this.end) {
/* 84 */       char c = this.sqlSelect.charAt(this.pos);
/* 85 */       if (c == '\'') {
/* 86 */         inQuote = !inQuote;
/* 87 */       } else if (!inQuote && c == ',') {
/* 88 */         return this.pos;
/*    */       } 
/* 90 */       this.pos++;
/*    */     } 
/* 92 */     return this.pos;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\DRawSqlColumnsParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */