/*     */ package com.avaje.ebeaninternal.server.deploy.id;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.ldap.LdapName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IdBinderEmpty
/*     */   implements IdBinder
/*     */ {
/*     */   private static final String bindIdSql = "";
/*  26 */   private static final BeanProperty[] properties = new BeanProperty[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialise() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readTerm(String idTermValue) {
/*  36 */     return null;
/*     */   }
/*     */   
/*     */   public String writeTerm(Object idValue) {
/*  40 */     return null;
/*     */   }
/*     */   
/*     */   public String getOrderBy(String pathPrefix, boolean ascending) {
/*  44 */     return pathPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildSelectExpressionChain(String prefix, List<String> selectChain) {}
/*     */ 
/*     */   
/*     */   public void createLdapNameById(LdapName name, Object id) throws InvalidNameException {}
/*     */ 
/*     */   
/*     */   public void createLdapNameByBean(LdapName name, Object bean) throws InvalidNameException {}
/*     */   
/*     */   public int getPropertyCount() {
/*  57 */     return 0;
/*     */   }
/*     */   
/*     */   public String getIdProperty() {
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   public BeanProperty findBeanProperty(String dbColumnName) {
/*  65 */     return null;
/*     */   }
/*     */   public boolean isComplexId() {
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultOrderBy() {
/*  74 */     return "";
/*     */   }
/*     */   
/*     */   public BeanProperty[] getProperties() {
/*  78 */     return properties;
/*     */   }
/*     */   
/*     */   public String getBindIdSql(String baseTableAlias) {
/*  82 */     return "";
/*     */   }
/*     */   
/*     */   public String getAssocOneIdExpr(String prefix, String operator) {
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   public String getAssocIdInExpr(String prefix) {
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addIdInBindValue(SpiExpressionRequest request, Object value) {}
/*     */ 
/*     */   
/*     */   public String getIdInValueExprDelete(int size) {
/*  98 */     return getIdInValueExpr(size);
/*     */   }
/*     */   
/*     */   public String getIdInValueExpr(int size) {
/* 102 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBindIdInSql(String baseTableAlias) {
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   public Object[] getIdValues(Object bean) {
/* 111 */     return null;
/*     */   }
/*     */   
/*     */   public Object[] getBindValues(Object idValue) {
/* 115 */     return new Object[] { idValue };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindId(DefaultSqlUpdate sqlUpdate, Object value) {}
/*     */ 
/*     */   
/*     */   public void bindId(DataBind dataBind, Object value) throws SQLException {}
/*     */ 
/*     */   
/*     */   public void loadIgnore(DbReadContext ctx) {}
/*     */ 
/*     */   
/*     */   public Object readSet(DbReadContext ctx, Object bean) throws SQLException {
/* 130 */     return null;
/*     */   }
/*     */   
/*     */   public Object read(DbReadContext ctx) throws SQLException {
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {}
/*     */   
/*     */   public Object convertSetId(Object idValue, Object bean) {
/* 141 */     return idValue;
/*     */   }
/*     */   
/*     */   public Object readData(DataInput dataOutput) throws IOException {
/* 145 */     return null;
/*     */   }
/*     */   
/*     */   public void writeData(DataOutput dataOutput, Object idValue) throws IOException {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\id\IdBinderEmpty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */