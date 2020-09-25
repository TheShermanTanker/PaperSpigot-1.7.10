/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LifeCycle;
/*     */ import org.apache.logging.log4j.core.LogEvent;
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
/*     */ public abstract class AbstractFilterable
/*     */   implements Filterable
/*     */ {
/*  32 */   protected static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private volatile Filter filter;
/*     */   
/*     */   protected AbstractFilterable(Filter filter) {
/*  37 */     this.filter = filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractFilterable() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter getFilter() {
/*  49 */     return this.filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addFilter(Filter filter) {
/*  58 */     if (this.filter == null) {
/*  59 */       this.filter = filter;
/*  60 */     } else if (filter instanceof CompositeFilter) {
/*  61 */       this.filter = ((CompositeFilter)this.filter).addFilter(filter);
/*     */     } else {
/*  63 */       Filter[] filters = { this.filter, filter };
/*  64 */       this.filter = CompositeFilter.createFilters(filters);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeFilter(Filter filter) {
/*  74 */     if (this.filter == filter) {
/*  75 */       this.filter = null;
/*  76 */     } else if (filter instanceof CompositeFilter) {
/*  77 */       CompositeFilter composite = (CompositeFilter)filter;
/*  78 */       composite = composite.removeFilter(filter);
/*  79 */       if (composite.size() > 1) {
/*  80 */         this.filter = composite;
/*  81 */       } else if (composite.size() == 1) {
/*  82 */         Iterator<Filter> iter = composite.iterator();
/*  83 */         this.filter = iter.next();
/*     */       } else {
/*  85 */         this.filter = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFilter() {
/*  96 */     return (this.filter != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startFilter() {
/* 103 */     if (this.filter != null && this.filter instanceof LifeCycle) {
/* 104 */       ((LifeCycle)this.filter).start();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopFilter() {
/* 112 */     if (this.filter != null && this.filter instanceof LifeCycle) {
/* 113 */       ((LifeCycle)this.filter).stop();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFiltered(LogEvent event) {
/* 124 */     return (this.filter != null && this.filter.filter(event) == Filter.Result.DENY);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\AbstractFilterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */