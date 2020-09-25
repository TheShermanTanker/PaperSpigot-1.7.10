/*     */ package org.apache.logging.log4j.core.appender.db.jpa;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import javax.persistence.EntityManager;
/*     */ import javax.persistence.EntityManagerFactory;
/*     */ import javax.persistence.EntityTransaction;
/*     */ import javax.persistence.Persistence;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;
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
/*     */ public final class JPADatabaseManager
/*     */   extends AbstractDatabaseManager
/*     */ {
/*  34 */   private static final JPADatabaseManagerFactory FACTORY = new JPADatabaseManagerFactory();
/*     */   
/*     */   private final String entityClassName;
/*     */   
/*     */   private final Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor;
/*     */   
/*     */   private final String persistenceUnitName;
/*     */   
/*     */   private EntityManagerFactory entityManagerFactory;
/*     */ 
/*     */   
/*     */   private JPADatabaseManager(String name, int bufferSize, Class<? extends AbstractLogEventWrapperEntity> entityClass, Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor, String persistenceUnitName) {
/*  46 */     super(name, bufferSize);
/*  47 */     this.entityClassName = entityClass.getName();
/*  48 */     this.entityConstructor = entityConstructor;
/*  49 */     this.persistenceUnitName = persistenceUnitName;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void connectInternal() {
/*  54 */     this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistenceUnitName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void disconnectInternal() {
/*  59 */     if (this.entityManagerFactory != null && this.entityManagerFactory.isOpen()) {
/*  60 */       this.entityManagerFactory.close();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void writeInternal(LogEvent event) {
/*     */     AbstractLogEventWrapperEntity entity;
/*  66 */     if (!isConnected() || this.entityManagerFactory == null) {
/*  67 */       throw new AppenderLoggingException("Cannot write logging event; JPA manager not connected to the database.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  73 */       entity = this.entityConstructor.newInstance(new Object[] { event });
/*  74 */     } catch (Exception e) {
/*  75 */       throw new AppenderLoggingException("Failed to instantiate entity class [" + this.entityClassName + "].", e);
/*     */     } 
/*     */     
/*  78 */     EntityManager entityManager = null;
/*  79 */     EntityTransaction transaction = null;
/*     */     try {
/*  81 */       entityManager = this.entityManagerFactory.createEntityManager();
/*  82 */       transaction = entityManager.getTransaction();
/*  83 */       transaction.begin();
/*  84 */       entityManager.persist(entity);
/*  85 */       transaction.commit();
/*  86 */     } catch (Exception e) {
/*  87 */       if (transaction != null && transaction.isActive()) {
/*  88 */         transaction.rollback();
/*     */       }
/*  90 */       throw new AppenderLoggingException("Failed to insert record for log event in JDBC manager: " + e.getMessage(), e);
/*     */     } finally {
/*     */       
/*  93 */       if (entityManager != null && entityManager.isOpen()) {
/*  94 */         entityManager.close();
/*     */       }
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JPADatabaseManager getJPADatabaseManager(String name, int bufferSize, Class<? extends AbstractLogEventWrapperEntity> entityClass, Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor, String persistenceUnitName) {
/* 117 */     return (JPADatabaseManager)AbstractDatabaseManager.getManager(name, new FactoryData(bufferSize, entityClass, entityConstructor, persistenceUnitName), FACTORY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class FactoryData
/*     */     extends AbstractDatabaseManager.AbstractFactoryData
/*     */   {
/*     */     private final Class<? extends AbstractLogEventWrapperEntity> entityClass;
/*     */     
/*     */     private final Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor;
/*     */     
/*     */     private final String persistenceUnitName;
/*     */ 
/*     */     
/*     */     protected FactoryData(int bufferSize, Class<? extends AbstractLogEventWrapperEntity> entityClass, Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor, String persistenceUnitName) {
/* 133 */       super(bufferSize);
/*     */       
/* 135 */       this.entityClass = entityClass;
/* 136 */       this.entityConstructor = entityConstructor;
/* 137 */       this.persistenceUnitName = persistenceUnitName;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class JPADatabaseManagerFactory
/*     */     implements ManagerFactory<JPADatabaseManager, FactoryData>
/*     */   {
/*     */     private JPADatabaseManagerFactory() {}
/*     */     
/*     */     public JPADatabaseManager createManager(String name, JPADatabaseManager.FactoryData data) {
/* 147 */       return new JPADatabaseManager(name, data.getBufferSize(), data.entityClass, data.entityConstructor, data.persistenceUnitName);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\JPADatabaseManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */