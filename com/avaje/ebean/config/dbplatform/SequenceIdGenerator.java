/*     */ package com.avaje.ebean.config.dbplatform;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SequenceIdGenerator
/*     */   implements IdGenerator
/*     */ {
/*  22 */   protected static final Logger logger = Logger.getLogger(SequenceIdGenerator.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   protected final Object monitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   protected final Object backgroundLoadMonitor = new Object();
/*     */ 
/*     */   
/*     */   protected final String seqName;
/*     */ 
/*     */   
/*     */   protected final DataSource dataSource;
/*     */ 
/*     */   
/*     */   protected final BackgroundExecutor backgroundExecutor;
/*     */   
/*  43 */   protected final ArrayList<Integer> idList = new ArrayList<Integer>(50);
/*     */ 
/*     */   
/*     */   protected int batchSize;
/*     */ 
/*     */   
/*     */   protected int currentlyBackgroundLoading;
/*     */ 
/*     */   
/*     */   public SequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) {
/*  53 */     this.backgroundExecutor = be;
/*  54 */     this.dataSource = ds;
/*  55 */     this.seqName = seqName;
/*  56 */     this.batchSize = batchSize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getSql(int paramInt);
/*     */ 
/*     */   
/*     */   public String getName() {
/*  65 */     return this.seqName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDbSequence() {
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preAllocateIds(int allocateSize) {
/*  83 */     if (this.batchSize > 1 && allocateSize > this.batchSize) {
/*     */ 
/*     */       
/*  86 */       if (allocateSize > 100)
/*     */       {
/*  88 */         allocateSize = 100;
/*     */       }
/*  90 */       loadLargeAllocation(allocateSize);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadLargeAllocation(final int allocateSize) {
/* 101 */     this.backgroundExecutor.execute(new Runnable() {
/*     */           public void run() {
/* 103 */             SequenceIdGenerator.this.loadMoreIds(allocateSize, null);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object nextId(Transaction t) {
/* 115 */     synchronized (this.monitor) {
/*     */       
/* 117 */       if (this.idList.size() == 0) {
/* 118 */         loadMoreIds(this.batchSize, t);
/*     */       }
/* 120 */       Integer nextId = this.idList.remove(0);
/*     */       
/* 122 */       if (this.batchSize > 1 && 
/* 123 */         this.idList.size() <= this.batchSize / 2) {
/* 124 */         loadBatchInBackground();
/*     */       }
/*     */ 
/*     */       
/* 128 */       return nextId;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadBatchInBackground() {
/* 138 */     synchronized (this.backgroundLoadMonitor) {
/*     */       
/* 140 */       if (this.currentlyBackgroundLoading > 0) {
/*     */         
/* 142 */         if (logger.isLoggable(Level.FINE)) {
/* 143 */           logger.log(Level.FINE, "... skip background sequence load (another load in progress)");
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 148 */       this.currentlyBackgroundLoading = this.batchSize;
/*     */       
/* 150 */       this.backgroundExecutor.execute(new Runnable() {
/*     */             public void run() {
/* 152 */               SequenceIdGenerator.this.loadMoreIds(SequenceIdGenerator.this.batchSize, null);
/* 153 */               synchronized (SequenceIdGenerator.this.backgroundLoadMonitor) {
/* 154 */                 SequenceIdGenerator.this.currentlyBackgroundLoading = 0;
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadMoreIds(int numberToLoad, Transaction t) {
/* 163 */     ArrayList<Integer> newIds = getMoreIds(numberToLoad, t);
/*     */     
/* 165 */     if (logger.isLoggable(Level.FINE)) {
/* 166 */       logger.log(Level.FINE, "... seq:" + this.seqName + " loaded:" + numberToLoad + " ids:" + newIds);
/*     */     }
/*     */     
/* 169 */     synchronized (this.monitor) {
/* 170 */       for (int i = 0; i < newIds.size(); i++) {
/* 171 */         this.idList.add(newIds.get(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ArrayList<Integer> getMoreIds(int loadSize, Transaction t) {
/* 181 */     String sql = getSql(loadSize);
/*     */     
/* 183 */     ArrayList<Integer> newIds = new ArrayList<Integer>(loadSize);
/*     */     
/* 185 */     boolean useTxnConnection = (t != null);
/*     */     
/* 187 */     Connection c = null;
/* 188 */     PreparedStatement pstmt = null;
/* 189 */     ResultSet rset = null;
/*     */     try {
/* 191 */       c = useTxnConnection ? t.getConnection() : this.dataSource.getConnection();
/*     */       
/* 193 */       pstmt = c.prepareStatement(sql);
/* 194 */       rset = pstmt.executeQuery();
/* 195 */       while (rset.next()) {
/* 196 */         int val = rset.getInt(1);
/* 197 */         newIds.add(Integer.valueOf(val));
/*     */       } 
/* 199 */       if (newIds.size() == 0) {
/* 200 */         String m = "Always expecting more than 1 row from " + sql;
/* 201 */         throw new PersistenceException(m);
/*     */       } 
/*     */       
/* 204 */       return newIds;
/*     */     }
/* 206 */     catch (SQLException e) {
/* 207 */       if (e.getMessage().contains("Database is already closed")) {
/* 208 */         String msg = "Error getting SEQ when DB shutting down " + e.getMessage();
/* 209 */         logger.info(msg);
/* 210 */         System.out.println(msg);
/* 211 */         return newIds;
/*     */       } 
/* 213 */       throw new PersistenceException("Error getting sequence nextval", e);
/*     */     } finally {
/*     */       
/* 216 */       if (useTxnConnection) {
/* 217 */         closeResources(null, pstmt, rset);
/*     */       } else {
/* 219 */         closeResources(c, pstmt, rset);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeResources(Connection c, PreparedStatement pstmt, ResultSet rset) {
/*     */     try {
/* 229 */       if (rset != null) {
/* 230 */         rset.close();
/*     */       }
/* 232 */     } catch (SQLException e) {
/* 233 */       logger.log(Level.SEVERE, "Error closing ResultSet", e);
/*     */     } 
/*     */     try {
/* 236 */       if (pstmt != null) {
/* 237 */         pstmt.close();
/*     */       }
/* 239 */     } catch (SQLException e) {
/* 240 */       logger.log(Level.SEVERE, "Error closing PreparedStatement", e);
/*     */     } 
/*     */     try {
/* 243 */       if (c != null) {
/* 244 */         c.close();
/*     */       }
/* 246 */     } catch (SQLException e) {
/* 247 */       logger.log(Level.SEVERE, "Error closing Connection", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\SequenceIdGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */