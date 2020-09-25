/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLClientInfoException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
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
/*     */ public class JDBC4CommentClientInfoProvider
/*     */   implements JDBC4ClientInfoProvider
/*     */ {
/*     */   private Properties clientInfo;
/*     */   
/*     */   public synchronized void initialize(Connection conn, Properties configurationProps) throws SQLException {
/*  52 */     this.clientInfo = new Properties();
/*     */   }
/*     */   
/*     */   public synchronized void destroy() throws SQLException {
/*  56 */     this.clientInfo = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Properties getClientInfo(Connection conn) throws SQLException {
/*  61 */     return this.clientInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized String getClientInfo(Connection conn, String name) throws SQLException {
/*  66 */     return this.clientInfo.getProperty(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setClientInfo(Connection conn, Properties properties) throws SQLClientInfoException {
/*  71 */     this.clientInfo = new Properties();
/*     */     
/*  73 */     Enumeration<?> propNames = properties.propertyNames();
/*     */     
/*  75 */     while (propNames.hasMoreElements()) {
/*  76 */       String name = (String)propNames.nextElement();
/*     */       
/*  78 */       this.clientInfo.put(name, properties.getProperty(name));
/*     */     } 
/*     */     
/*  81 */     setComment(conn);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setClientInfo(Connection conn, String name, String value) throws SQLClientInfoException {
/*  86 */     this.clientInfo.setProperty(name, value);
/*  87 */     setComment(conn);
/*     */   }
/*     */   
/*     */   private synchronized void setComment(Connection conn) {
/*  91 */     StringBuffer commentBuf = new StringBuffer();
/*  92 */     Iterator<Map.Entry<Object, Object>> elements = this.clientInfo.entrySet().iterator();
/*     */     
/*  94 */     while (elements.hasNext()) {
/*  95 */       if (commentBuf.length() > 0) {
/*  96 */         commentBuf.append(", ");
/*     */       }
/*     */       
/*  99 */       Map.Entry entry = elements.next();
/* 100 */       commentBuf.append("" + entry.getKey());
/* 101 */       commentBuf.append("=");
/* 102 */       commentBuf.append("" + entry.getValue());
/*     */     } 
/*     */     
/* 105 */     ((Connection)conn).setStatementComment(commentBuf.toString());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\JDBC4CommentClientInfoProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */