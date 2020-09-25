/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LifeCycle;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ @Plugin(name = "filters", category = "Core", printObject = true)
/*     */ public final class CompositeFilter
/*     */   implements Iterable<Filter>, Filter, LifeCycle
/*     */ {
/*     */   private final List<Filter> filters;
/*     */   private final boolean hasFilters;
/*     */   private boolean isStarted;
/*     */   
/*     */   private CompositeFilter() {
/*  48 */     this.filters = new ArrayList<Filter>();
/*  49 */     this.hasFilters = false;
/*     */   }
/*     */   
/*     */   private CompositeFilter(List<Filter> filters) {
/*  53 */     if (filters == null) {
/*  54 */       this.filters = Collections.unmodifiableList(new ArrayList<Filter>());
/*  55 */       this.hasFilters = false;
/*     */       return;
/*     */     } 
/*  58 */     this.filters = Collections.unmodifiableList(filters);
/*  59 */     this.hasFilters = (this.filters.size() > 0);
/*     */   }
/*     */   
/*     */   public CompositeFilter addFilter(Filter filter) {
/*  63 */     List<Filter> filters = new ArrayList<Filter>(this.filters);
/*  64 */     filters.add(filter);
/*  65 */     return new CompositeFilter(Collections.unmodifiableList(filters));
/*     */   }
/*     */   
/*     */   public CompositeFilter removeFilter(Filter filter) {
/*  69 */     List<Filter> filters = new ArrayList<Filter>(this.filters);
/*  70 */     filters.remove(filter);
/*  71 */     return new CompositeFilter(Collections.unmodifiableList(filters));
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Filter> iterator() {
/*  76 */     return this.filters.iterator();
/*     */   }
/*     */   
/*     */   public List<Filter> getFilters() {
/*  80 */     return this.filters;
/*     */   }
/*     */   
/*     */   public boolean hasFilters() {
/*  84 */     return this.hasFilters;
/*     */   }
/*     */   
/*     */   public int size() {
/*  88 */     return this.filters.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  93 */     for (Filter filter : this.filters) {
/*  94 */       if (filter instanceof LifeCycle) {
/*  95 */         ((LifeCycle)filter).start();
/*     */       }
/*     */     } 
/*  98 */     this.isStarted = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 103 */     for (Filter filter : this.filters) {
/* 104 */       if (filter instanceof LifeCycle) {
/* 105 */         ((LifeCycle)filter).stop();
/*     */       }
/*     */     } 
/* 108 */     this.isStarted = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStarted() {
/* 113 */     return this.isStarted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter.Result getOnMismatch() {
/* 123 */     return Filter.Result.NEUTRAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter.Result getOnMatch() {
/* 133 */     return Filter.Result.NEUTRAL;
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
/*     */   public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
/* 154 */     Filter.Result result = Filter.Result.NEUTRAL;
/* 155 */     for (Filter filter : this.filters) {
/* 156 */       result = filter.filter(logger, level, marker, msg, params);
/* 157 */       if (result == Filter.Result.ACCEPT || result == Filter.Result.DENY) {
/* 158 */         return result;
/*     */       }
/*     */     } 
/* 161 */     return result;
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
/*     */   public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
/* 182 */     Filter.Result result = Filter.Result.NEUTRAL;
/* 183 */     for (Filter filter : this.filters) {
/* 184 */       result = filter.filter(logger, level, marker, msg, t);
/* 185 */       if (result == Filter.Result.ACCEPT || result == Filter.Result.DENY) {
/* 186 */         return result;
/*     */       }
/*     */     } 
/* 189 */     return result;
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
/*     */   public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
/* 210 */     Filter.Result result = Filter.Result.NEUTRAL;
/* 211 */     for (Filter filter : this.filters) {
/* 212 */       result = filter.filter(logger, level, marker, msg, t);
/* 213 */       if (result == Filter.Result.ACCEPT || result == Filter.Result.DENY) {
/* 214 */         return result;
/*     */       }
/*     */     } 
/* 217 */     return result;
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
/*     */   public Filter.Result filter(LogEvent event) {
/* 229 */     Filter.Result result = Filter.Result.NEUTRAL;
/* 230 */     for (Filter filter : this.filters) {
/* 231 */       result = filter.filter(event);
/* 232 */       if (result == Filter.Result.ACCEPT || result == Filter.Result.DENY) {
/* 233 */         return result;
/*     */       }
/*     */     } 
/* 236 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 241 */     StringBuilder sb = new StringBuilder();
/* 242 */     for (Filter filter : this.filters) {
/* 243 */       if (sb.length() == 0) {
/* 244 */         sb.append("{");
/*     */       } else {
/* 246 */         sb.append(", ");
/*     */       } 
/* 248 */       sb.append(filter.toString());
/*     */     } 
/* 250 */     if (sb.length() > 0) {
/* 251 */       sb.append("}");
/*     */     }
/* 253 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static CompositeFilter createFilters(@PluginElement("Filters") Filter[] filters) {
/* 265 */     List<Filter> f = (filters == null || filters.length == 0) ? new ArrayList<Filter>() : Arrays.<Filter>asList(filters);
/*     */     
/* 267 */     return new CompositeFilter(f);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\CompositeFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */