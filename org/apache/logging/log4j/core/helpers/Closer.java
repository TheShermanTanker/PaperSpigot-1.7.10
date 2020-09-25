/*     */ package org.apache.logging.log4j.core.helpers;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
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
/*     */ public class Closer
/*     */ {
/*     */   public static void closeSilent(Closeable closeable) {
/*     */     try {
/*  39 */       if (closeable != null) {
/*  40 */         closeable.close();
/*     */       }
/*  42 */     } catch (Exception ignored) {}
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
/*     */   public static void close(Closeable closeable) throws IOException {
/*  54 */     if (closeable != null) {
/*  55 */       closeable.close();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeSilent(Statement statement) {
/*     */     try {
/*  67 */       if (statement != null) {
/*  68 */         statement.close();
/*     */       }
/*  70 */     } catch (Exception ignored) {}
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
/*     */   public static void close(Statement statement) throws SQLException {
/*  82 */     if (statement != null) {
/*  83 */       statement.close();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeSilent(Connection connection) {
/*     */     try {
/*  95 */       if (connection != null) {
/*  96 */         connection.close();
/*     */       }
/*  98 */     } catch (Exception ignored) {}
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
/*     */   public static void close(Connection connection) throws SQLException {
/* 110 */     if (connection != null)
/* 111 */       connection.close(); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Closer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */