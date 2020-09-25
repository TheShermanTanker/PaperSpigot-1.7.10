/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebeaninternal.api.DerivedRelationshipData;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.DmlUtil;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.persistence.OptimisticLockException;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InsertHandler
/*     */   extends DmlHandler
/*     */ {
/*     */   private final InsertMeta meta;
/*     */   private final boolean concatinatedKey;
/*     */   private boolean useGeneratedKeys;
/*     */   private String selectLastInsertedId;
/*     */   
/*     */   public InsertHandler(PersistRequestBean<?> persist, InsertMeta meta) {
/*  74 */     super(persist, meta.isEmptyStringToNull());
/*  75 */     this.meta = meta;
/*  76 */     this.concatinatedKey = meta.isConcatinatedKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncluded(BeanProperty prop) {
/*  81 */     return (prop.isDbInsertable() && super.isIncluded(prop));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() throws SQLException {
/*     */     PreparedStatement pstmt;
/*  89 */     BeanDescriptor<?> desc = this.persistRequest.getBeanDescriptor();
/*  90 */     Object bean = this.persistRequest.getBean();
/*     */     
/*  92 */     Object idValue = desc.getId(bean);
/*     */     
/*  94 */     boolean withId = !DmlUtil.isNullOrZero(idValue);
/*     */ 
/*     */     
/*  97 */     if (!withId) {
/*  98 */       if (this.concatinatedKey) {
/*     */ 
/*     */         
/* 101 */         withId = this.meta.deriveConcatenatedId(this.persistRequest);
/*     */       }
/* 103 */       else if (this.meta.supportsGetGeneratedKeys()) {
/*     */         
/* 105 */         this.useGeneratedKeys = true;
/*     */       } else {
/*     */         
/* 108 */         this.selectLastInsertedId = this.meta.getSelectLastInsertedId();
/*     */       } 
/*     */     }
/*     */     
/* 112 */     SpiTransaction t = this.persistRequest.getTransaction();
/* 113 */     boolean isBatch = t.isBatchThisRequest();
/*     */ 
/*     */     
/* 116 */     this.sql = this.meta.getSql(withId);
/*     */ 
/*     */     
/* 119 */     if (isBatch) {
/* 120 */       pstmt = getPstmt(t, this.sql, this.persistRequest, this.useGeneratedKeys);
/*     */     } else {
/*     */       
/* 123 */       logSql(this.sql);
/* 124 */       pstmt = getPstmt(t, this.sql, this.useGeneratedKeys);
/*     */     } 
/* 126 */     this.dataBind = new DataBind(pstmt);
/*     */     
/* 128 */     bindLogAppend("Binding Insert [");
/* 129 */     bindLogAppend(desc.getBaseTable());
/* 130 */     bindLogAppend("]  set[");
/*     */ 
/*     */     
/* 133 */     this.meta.bind(this, bean, withId);
/*     */     
/* 135 */     bindLogAppend("]");
/* 136 */     logBinding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PreparedStatement getPstmt(SpiTransaction t, String sql, boolean useGeneratedKeys) throws SQLException {
/* 144 */     Connection conn = t.getInternalConnection();
/* 145 */     if (useGeneratedKeys) {
/* 146 */       return conn.prepareStatement(sql, 1);
/*     */     }
/*     */     
/* 149 */     return conn.prepareStatement(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute() throws SQLException, OptimisticLockException {
/* 158 */     int rc = this.dataBind.executeUpdate();
/* 159 */     if (this.useGeneratedKeys) {
/*     */       
/* 161 */       getGeneratedKeys();
/*     */     }
/* 163 */     else if (this.selectLastInsertedId != null) {
/*     */       
/* 165 */       fetchGeneratedKeyUsingSelect();
/*     */     } 
/*     */     
/* 168 */     checkRowCount(rc);
/* 169 */     setAdditionalProperties();
/* 170 */     executeDerivedRelationships();
/*     */   }
/*     */   
/*     */   protected void executeDerivedRelationships() {
/* 174 */     List<DerivedRelationshipData> derivedRelationships = this.persistRequest.getDerivedRelationships();
/* 175 */     if (derivedRelationships != null) {
/* 176 */       for (int i = 0; i < derivedRelationships.size(); i++) {
/* 177 */         DerivedRelationshipData derivedRelationshipData = derivedRelationships.get(i);
/*     */         
/* 179 */         EbeanServer ebeanServer = this.persistRequest.getEbeanServer();
/* 180 */         HashSet<String> updateProps = new HashSet<String>();
/* 181 */         updateProps.add(derivedRelationshipData.getLogicalName());
/* 182 */         ebeanServer.update(derivedRelationshipData.getBean(), updateProps, (Transaction)this.transaction, false, true);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getGeneratedKeys() throws SQLException {
/* 192 */     ResultSet rset = this.dataBind.getPstmt().getGeneratedKeys();
/*     */     try {
/* 194 */       if (rset.next()) {
/* 195 */         Object idValue = rset.getObject(1);
/* 196 */         if (idValue != null) {
/* 197 */           this.persistRequest.setGeneratedKey(idValue);
/*     */         }
/*     */       } else {
/*     */         
/* 201 */         throw new PersistenceException(Message.msg("persist.autoinc.norows"));
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 205 */         rset.close();
/* 206 */       } catch (SQLException ex) {
/* 207 */         String msg = "Error closing rset for returning generatedKeys?";
/* 208 */         logger.log(Level.WARNING, msg, ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fetchGeneratedKeyUsingSelect() throws SQLException {
/* 219 */     Connection conn = this.transaction.getConnection();
/*     */     
/* 221 */     PreparedStatement stmt = null;
/* 222 */     ResultSet rset = null;
/*     */     try {
/* 224 */       stmt = conn.prepareStatement(this.selectLastInsertedId);
/* 225 */       rset = stmt.executeQuery();
/* 226 */       if (rset.next()) {
/* 227 */         Object idValue = rset.getObject(1);
/* 228 */         if (idValue != null) {
/* 229 */           this.persistRequest.setGeneratedKey(idValue);
/*     */         }
/*     */       } else {
/* 232 */         throw new PersistenceException(Message.msg("persist.autoinc.norows"));
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 236 */         if (rset != null) {
/* 237 */           rset.close();
/*     */         }
/* 239 */       } catch (SQLException ex) {
/* 240 */         String msg = "Error closing rset for fetchGeneratedKeyUsingSelect?";
/* 241 */         logger.log(Level.WARNING, msg, ex);
/*     */       } 
/*     */       try {
/* 244 */         if (stmt != null) {
/* 245 */           stmt.close();
/*     */         }
/* 247 */       } catch (SQLException ex) {
/* 248 */         String msg = "Error closing stmt for fetchGeneratedKeyUsingSelect?";
/* 249 */         logger.log(Level.WARNING, msg, ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerDerivedRelationship(DerivedRelationshipData derivedRelationship) {
/* 255 */     this.persistRequest.getTransaction().registerDerivedRelationship(derivedRelationship);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\InsertHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */