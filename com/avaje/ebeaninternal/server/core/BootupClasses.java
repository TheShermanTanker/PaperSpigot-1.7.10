/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.annotation.LdapDomain;
/*     */ import com.avaje.ebean.config.CompoundType;
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.event.BeanFinder;
/*     */ import com.avaje.ebean.event.BeanPersistController;
/*     */ import com.avaje.ebean.event.BeanPersistListener;
/*     */ import com.avaje.ebean.event.BeanQueryAdapter;
/*     */ import com.avaje.ebean.event.ServerConfigStartup;
/*     */ import com.avaje.ebean.event.TransactionEventListener;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import com.avaje.ebeaninternal.server.util.ClassPathSearchMatcher;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.Embeddable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Table;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ public class BootupClasses
/*     */   implements ClassPathSearchMatcher
/*     */ {
/*  54 */   private static final Logger logger = Logger.getLogger(BootupClasses.class.getName());
/*     */   
/*  56 */   private ArrayList<Class<?>> xmlBeanList = new ArrayList<Class<?>>();
/*     */   
/*  58 */   private ArrayList<Class<?>> embeddableList = new ArrayList<Class<?>>();
/*     */   
/*  60 */   private ArrayList<Class<?>> entityList = new ArrayList<Class<?>>();
/*     */   
/*  62 */   private ArrayList<Class<?>> scalarTypeList = new ArrayList<Class<?>>();
/*     */   
/*  64 */   private ArrayList<Class<?>> scalarConverterList = new ArrayList<Class<?>>();
/*     */   
/*  66 */   private ArrayList<Class<?>> compoundTypeList = new ArrayList<Class<?>>();
/*     */   
/*  68 */   private ArrayList<Class<?>> beanControllerList = new ArrayList<Class<?>>();
/*     */   
/*  70 */   private ArrayList<Class<?>> transactionEventListenerList = new ArrayList<Class<?>>();
/*     */   
/*  72 */   private ArrayList<Class<?>> beanFinderList = new ArrayList<Class<?>>();
/*     */   
/*  74 */   private ArrayList<Class<?>> beanListenerList = new ArrayList<Class<?>>();
/*     */   
/*  76 */   private ArrayList<Class<?>> beanQueryAdapterList = new ArrayList<Class<?>>();
/*     */   
/*  78 */   private ArrayList<Class<?>> luceneIndexList = new ArrayList<Class<?>>();
/*     */   
/*  80 */   private ArrayList<Class<?>> serverConfigStartupList = new ArrayList<Class<?>>();
/*  81 */   private ArrayList<ServerConfigStartup> serverConfigStartupInstances = new ArrayList<ServerConfigStartup>();
/*     */   
/*  83 */   private List<BeanPersistController> persistControllerInstances = new ArrayList<BeanPersistController>();
/*  84 */   private List<BeanPersistListener<?>> persistListenerInstances = new ArrayList<BeanPersistListener<?>>();
/*  85 */   private List<BeanQueryAdapter> queryAdapterInstances = new ArrayList<BeanQueryAdapter>();
/*  86 */   private List<TransactionEventListener> transactionEventListenerInstances = new ArrayList<TransactionEventListener>();
/*     */ 
/*     */   
/*     */   public BootupClasses() {}
/*     */   
/*     */   public BootupClasses(List<Class<?>> list) {
/*  92 */     if (list != null) {
/*  93 */       process(list.iterator());
/*     */     }
/*     */   }
/*     */   
/*     */   private BootupClasses(BootupClasses parent) {
/*  98 */     this.xmlBeanList.addAll(parent.xmlBeanList);
/*  99 */     this.embeddableList.addAll(parent.embeddableList);
/* 100 */     this.entityList.addAll(parent.entityList);
/* 101 */     this.scalarTypeList.addAll(parent.scalarTypeList);
/* 102 */     this.scalarConverterList.addAll(parent.scalarConverterList);
/* 103 */     this.compoundTypeList.addAll(parent.compoundTypeList);
/* 104 */     this.beanControllerList.addAll(parent.beanControllerList);
/* 105 */     this.transactionEventListenerList.addAll(parent.transactionEventListenerList);
/* 106 */     this.beanFinderList.addAll(parent.beanFinderList);
/* 107 */     this.beanListenerList.addAll(parent.beanListenerList);
/* 108 */     this.beanQueryAdapterList.addAll(parent.beanQueryAdapterList);
/* 109 */     this.luceneIndexList.addAll(parent.luceneIndexList);
/* 110 */     this.serverConfigStartupList.addAll(parent.serverConfigStartupList);
/*     */   }
/*     */   
/*     */   private void process(Iterator<Class<?>> it) {
/* 114 */     while (it.hasNext()) {
/* 115 */       Class<?> cls = it.next();
/* 116 */       isMatch(cls);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BootupClasses createCopy() {
/* 124 */     return new BootupClasses(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void runServerConfigStartup(ServerConfig serverConfig) {
/* 132 */     for (Class<?> cls : this.serverConfigStartupList) {
/*     */       try {
/* 134 */         ServerConfigStartup newInstance = (ServerConfigStartup)cls.newInstance();
/* 135 */         newInstance.onStart(serverConfig);
/*     */       }
/* 137 */       catch (Exception e) {
/* 138 */         String msg = "Error creating BeanQueryAdapter " + cls;
/* 139 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addQueryAdapters(List<BeanQueryAdapter> queryAdapterInstances) {
/* 145 */     if (queryAdapterInstances != null) {
/* 146 */       for (BeanQueryAdapter a : queryAdapterInstances) {
/* 147 */         this.queryAdapterInstances.add(a);
/*     */         
/* 149 */         this.beanQueryAdapterList.remove(a.getClass());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPersistControllers(List<BeanPersistController> beanControllerInstances) {
/* 158 */     if (beanControllerInstances != null) {
/* 159 */       for (BeanPersistController c : beanControllerInstances) {
/* 160 */         this.persistControllerInstances.add(c);
/*     */         
/* 162 */         this.beanControllerList.remove(c.getClass());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTransactionEventListeners(List<TransactionEventListener> transactionEventListeners) {
/* 171 */     if (transactionEventListeners != null) {
/* 172 */       for (TransactionEventListener c : transactionEventListeners) {
/* 173 */         this.transactionEventListenerInstances.add(c);
/*     */         
/* 175 */         this.transactionEventListenerList.remove(c.getClass());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void addPersistListeners(List<BeanPersistListener<?>> listenerInstances) {
/* 181 */     if (listenerInstances != null) {
/* 182 */       for (BeanPersistListener<?> l : listenerInstances) {
/* 183 */         this.persistListenerInstances.add(l);
/*     */         
/* 185 */         this.beanListenerList.remove(l.getClass());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void addServerConfigStartup(List<ServerConfigStartup> startupInstances) {
/* 191 */     if (startupInstances != null) {
/* 192 */       for (ServerConfigStartup l : startupInstances) {
/* 193 */         this.serverConfigStartupInstances.add(l);
/*     */         
/* 195 */         this.serverConfigStartupList.remove(l.getClass());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BeanQueryAdapter> getBeanQueryAdapters() {
/* 203 */     for (Class<?> cls : this.beanQueryAdapterList) {
/*     */       try {
/* 205 */         BeanQueryAdapter newInstance = (BeanQueryAdapter)cls.newInstance();
/* 206 */         this.queryAdapterInstances.add(newInstance);
/* 207 */       } catch (Exception e) {
/* 208 */         String msg = "Error creating BeanQueryAdapter " + cls;
/* 209 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     return this.queryAdapterInstances;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BeanPersistListener<?>> getBeanPersistListeners() {
/* 219 */     for (Class<?> cls : this.beanListenerList) {
/*     */       try {
/* 221 */         BeanPersistListener<?> newInstance = (BeanPersistListener)cls.newInstance();
/* 222 */         this.persistListenerInstances.add(newInstance);
/* 223 */       } catch (Exception e) {
/* 224 */         String msg = "Error creating BeanPersistController " + cls;
/* 225 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     } 
/*     */     
/* 229 */     return this.persistListenerInstances;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BeanPersistController> getBeanPersistControllers() {
/* 235 */     for (Class<?> cls : this.beanControllerList) {
/*     */       try {
/* 237 */         BeanPersistController newInstance = (BeanPersistController)cls.newInstance();
/* 238 */         this.persistControllerInstances.add(newInstance);
/* 239 */       } catch (Exception e) {
/* 240 */         String msg = "Error creating BeanPersistController " + cls;
/* 241 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     return this.persistControllerInstances;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TransactionEventListener> getTransactionEventListeners() {
/* 251 */     for (Class<?> cls : this.transactionEventListenerList) {
/*     */       try {
/* 253 */         TransactionEventListener newInstance = (TransactionEventListener)cls.newInstance();
/* 254 */         this.transactionEventListenerInstances.add(newInstance);
/* 255 */       } catch (Exception e) {
/* 256 */         String msg = "Error creating TransactionEventListener " + cls;
/* 257 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     return this.transactionEventListenerInstances;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<?>> getEmbeddables() {
/* 268 */     return this.embeddableList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<?>> getEntities() {
/* 275 */     return this.entityList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<?>> getScalarTypes() {
/* 282 */     return this.scalarTypeList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<?>> getScalarConverters() {
/* 289 */     return this.scalarConverterList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<?>> getCompoundTypes() {
/* 296 */     return this.compoundTypeList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<?>> getBeanControllers() {
/* 303 */     return this.beanControllerList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<?>> getTransactionEventListenerList() {
/* 310 */     return this.transactionEventListenerList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<?>> getBeanFinders() {
/* 317 */     return this.beanFinderList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<?>> getBeanListeners() {
/* 324 */     return this.beanListenerList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Class<?>> getXmlBeanList() {
/* 331 */     return this.xmlBeanList;
/*     */   }
/*     */   
/*     */   public void add(Iterator<Class<?>> it) {
/* 335 */     while (it.hasNext()) {
/* 336 */       Class<?> clazz = it.next();
/* 337 */       isMatch(clazz);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMatch(Class<?> cls) {
/* 343 */     if (isEmbeddable(cls)) {
/* 344 */       this.embeddableList.add(cls);
/*     */     }
/* 346 */     else if (isEntity(cls)) {
/* 347 */       this.entityList.add(cls);
/*     */     }
/* 349 */     else if (isXmlBean(cls)) {
/* 350 */       this.entityList.add(cls);
/*     */     } else {
/*     */       
/* 353 */       if (isInterestingInterface(cls)) {
/* 354 */         return true;
/*     */       }
/*     */       
/* 357 */       return false;
/*     */     } 
/*     */     
/* 360 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isInterestingInterface(Class<?> cls) {
/* 371 */     boolean interesting = false;
/*     */     
/* 373 */     if (BeanPersistController.class.isAssignableFrom(cls)) {
/* 374 */       this.beanControllerList.add(cls);
/* 375 */       interesting = true;
/*     */     } 
/*     */     
/* 378 */     if (TransactionEventListener.class.isAssignableFrom(cls)) {
/* 379 */       this.transactionEventListenerList.add(cls);
/* 380 */       interesting = true;
/*     */     } 
/*     */     
/* 383 */     if (ScalarType.class.isAssignableFrom(cls)) {
/* 384 */       this.scalarTypeList.add(cls);
/* 385 */       interesting = true;
/*     */     } 
/*     */     
/* 388 */     if (ScalarTypeConverter.class.isAssignableFrom(cls)) {
/* 389 */       this.scalarConverterList.add(cls);
/* 390 */       interesting = true;
/*     */     } 
/*     */     
/* 393 */     if (CompoundType.class.isAssignableFrom(cls)) {
/* 394 */       this.compoundTypeList.add(cls);
/* 395 */       interesting = true;
/*     */     } 
/*     */     
/* 398 */     if (BeanFinder.class.isAssignableFrom(cls)) {
/* 399 */       this.beanFinderList.add(cls);
/* 400 */       interesting = true;
/*     */     } 
/*     */     
/* 403 */     if (BeanPersistListener.class.isAssignableFrom(cls)) {
/* 404 */       this.beanListenerList.add(cls);
/* 405 */       interesting = true;
/*     */     } 
/*     */     
/* 408 */     if (BeanQueryAdapter.class.isAssignableFrom(cls)) {
/* 409 */       this.beanQueryAdapterList.add(cls);
/* 410 */       interesting = true;
/*     */     } 
/*     */     
/* 413 */     if (ServerConfigStartup.class.isAssignableFrom(cls)) {
/* 414 */       this.serverConfigStartupList.add(cls);
/* 415 */       interesting = true;
/*     */     } 
/*     */     
/* 418 */     return interesting;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isEntity(Class<?> cls) {
/* 423 */     Annotation ann = (Annotation)cls.getAnnotation(Entity.class);
/* 424 */     if (ann != null) {
/* 425 */       return true;
/*     */     }
/* 427 */     ann = cls.getAnnotation(Table.class);
/* 428 */     if (ann != null) {
/* 429 */       return true;
/*     */     }
/* 431 */     ann = cls.getAnnotation(LdapDomain.class);
/* 432 */     if (ann != null) {
/* 433 */       return true;
/*     */     }
/* 435 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isEmbeddable(Class<?> cls) {
/* 440 */     Annotation ann = (Annotation)cls.getAnnotation(Embeddable.class);
/* 441 */     if (ann != null) {
/* 442 */       return true;
/*     */     }
/* 444 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isXmlBean(Class<?> cls) {
/* 449 */     Annotation ann = (Annotation)cls.getAnnotation(XmlRootElement.class);
/* 450 */     if (ann != null) {
/* 451 */       return true;
/*     */     }
/* 453 */     ann = cls.getAnnotation(XmlType.class);
/* 454 */     if (ann != null)
/*     */     {
/* 456 */       return !cls.isEnum();
/*     */     }
/* 458 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\BootupClasses.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */