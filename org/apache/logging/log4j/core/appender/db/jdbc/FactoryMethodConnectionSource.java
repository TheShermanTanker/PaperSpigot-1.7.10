/*     */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Logger;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ @Plugin(name = "ConnectionFactory", category = "Core", elementType = "connectionSource", printObject = true)
/*     */ public final class FactoryMethodConnectionSource
/*     */   implements ConnectionSource
/*     */ {
/*  39 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private final DataSource dataSource;
/*     */   
/*     */   private final String description;
/*     */   
/*     */   private FactoryMethodConnectionSource(DataSource dataSource, String className, String methodName, String returnType) {
/*  46 */     this.dataSource = dataSource;
/*  47 */     this.description = "factory{ public static " + returnType + " " + className + "." + methodName + "() }";
/*     */   }
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/*  52 */     return this.dataSource.getConnection();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  57 */     return this.description;
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
/*     */   
/*     */   @PluginFactory
/*     */   public static FactoryMethodConnectionSource createConnectionSource(@PluginAttribute("class") String className, @PluginAttribute("method") String methodName) {
/*     */     final Method method;
/*     */     DataSource dataSource;
/*  74 */     if (Strings.isEmpty(className) || Strings.isEmpty(methodName)) {
/*  75 */       LOGGER.error("No class name or method name specified for the connection factory method.");
/*  76 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  81 */       Class<?> factoryClass = Class.forName(className);
/*  82 */       method = factoryClass.getMethod(methodName, new Class[0]);
/*  83 */     } catch (Exception e) {
/*  84 */       LOGGER.error(e.toString(), e);
/*  85 */       return null;
/*     */     } 
/*     */     
/*  88 */     Class<?> returnType = method.getReturnType();
/*  89 */     String returnTypeString = returnType.getName();
/*     */     
/*  91 */     if (returnType == DataSource.class) {
/*     */       try {
/*  93 */         dataSource = (DataSource)method.invoke(null, new Object[0]);
/*  94 */         returnTypeString = returnTypeString + "[" + dataSource + "]";
/*  95 */       } catch (Exception e) {
/*  96 */         LOGGER.error(e.toString(), e);
/*  97 */         return null;
/*     */       } 
/*  99 */     } else if (returnType == Connection.class) {
/* 100 */       dataSource = new DataSource()
/*     */         {
/*     */           public Connection getConnection() throws SQLException {
/*     */             try {
/* 104 */               return (Connection)method.invoke(null, new Object[0]);
/* 105 */             } catch (Exception e) {
/* 106 */               throw new SQLException("Failed to obtain connection from factory method.", e);
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public Connection getConnection(String username, String password) throws SQLException {
/* 112 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public int getLoginTimeout() throws SQLException {
/* 117 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public PrintWriter getLogWriter() throws SQLException {
/* 122 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Logger getParentLogger() {
/* 128 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 133 */             return false;
/*     */           }
/*     */ 
/*     */           
/*     */           public void setLoginTimeout(int seconds) throws SQLException {
/* 138 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void setLogWriter(PrintWriter out) throws SQLException {
/* 143 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public <T> T unwrap(Class<T> iface) throws SQLException {
/* 148 */             return null;
/*     */           }
/*     */         };
/*     */     } else {
/* 152 */       LOGGER.error("Method [{}.{}()] returns unsupported type [{}].", new Object[] { className, methodName, returnType.getName() });
/*     */       
/* 154 */       return null;
/*     */     } 
/*     */     
/* 157 */     return new FactoryMethodConnectionSource(dataSource, className, methodName, returnTypeString);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\FactoryMethodConnectionSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */