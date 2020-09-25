/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.util.ClassPathSearch;
/*     */ import com.avaje.ebeaninternal.server.util.ClassPathSearchFilter;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
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
/*     */ public class BootupClassPathSearch
/*     */ {
/*  34 */   private static final Logger logger = Logger.getLogger(BootupClassPathSearch.class.getName());
/*     */   
/*  36 */   private final Object monitor = new Object();
/*     */ 
/*     */   
/*     */   private final ClassLoader classLoader;
/*     */   
/*     */   private final List<String> packages;
/*     */   
/*     */   private final List<String> jars;
/*     */   
/*     */   private BootupClasses bootupClasses;
/*     */ 
/*     */   
/*     */   public BootupClassPathSearch(ClassLoader classLoader, List<String> packages, List<String> jars) {
/*  49 */     this.classLoader = (classLoader == null) ? getClass().getClassLoader() : classLoader;
/*  50 */     this.packages = packages;
/*  51 */     this.jars = jars;
/*     */   }
/*     */   
/*     */   public BootupClasses getBootupClasses() {
/*  55 */     synchronized (this.monitor) {
/*     */       
/*  57 */       if (this.bootupClasses == null) {
/*  58 */         this.bootupClasses = search();
/*     */       }
/*     */       
/*  61 */       return this.bootupClasses;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BootupClasses search() {
/*  69 */     synchronized (this.monitor) {
/*     */ 
/*     */       
/*  72 */       BootupClasses bc = new BootupClasses();
/*     */       
/*  74 */       long st = System.currentTimeMillis();
/*     */       
/*  76 */       ClassPathSearchFilter filter = createFilter();
/*     */       
/*  78 */       ClassPathSearch finder = new ClassPathSearch(this.classLoader, filter, bc);
/*     */       
/*  80 */       finder.findClasses();
/*  81 */       Set<String> jars = finder.getJarHits();
/*  82 */       Set<String> pkgs = finder.getPackageHits();
/*     */       
/*  84 */       long searchTime = System.currentTimeMillis() - st;
/*     */       
/*  86 */       String msg = "Classpath search hits in jars" + jars + " pkgs" + pkgs + "  searchTime[" + searchTime + "]";
/*  87 */       logger.info(msg);
/*     */       
/*  89 */       return bc;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassPathSearchFilter createFilter() {
/* 100 */     ClassPathSearchFilter filter = new ClassPathSearchFilter();
/* 101 */     filter.addDefaultExcludePackages();
/*     */     
/* 103 */     if (this.packages != null) {
/* 104 */       for (String packageName : this.packages) {
/* 105 */         filter.includePackage(packageName);
/*     */       }
/*     */     }
/*     */     
/* 109 */     if (this.jars != null) {
/* 110 */       for (String jarName : this.jars) {
/* 111 */         filter.includeJar(jarName);
/*     */       }
/*     */     }
/*     */     
/* 115 */     return filter;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\BootupClassPathSearch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */