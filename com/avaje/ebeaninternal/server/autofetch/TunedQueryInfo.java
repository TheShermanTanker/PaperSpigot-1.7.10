/*     */ package com.avaje.ebeaninternal.server.autofetch;
/*     */ 
/*     */ import com.avaje.ebean.bean.ObjectGraphOrigin;
/*     */ import com.avaje.ebean.meta.MetaAutoFetchTunedQueryInfo;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import java.io.Serializable;
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
/*     */ public class TunedQueryInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7381493228797997282L;
/*     */   private final ObjectGraphOrigin origin;
/*     */   private OrmQueryDetail tunedDetail;
/*     */   private int profileCount;
/*  30 */   private Long lastTuneTime = Long.valueOf(0L);
/*     */   
/*  32 */   private final String rateMonitor = new String();
/*     */ 
/*     */   
/*     */   private transient int tunedCount;
/*     */ 
/*     */   
/*     */   private transient int rateTotal;
/*     */ 
/*     */   
/*     */   private transient int rateHits;
/*     */   
/*     */   private transient double lastRate;
/*     */ 
/*     */   
/*     */   public TunedQueryInfo(ObjectGraphOrigin queryPoint, OrmQueryDetail tunedDetail, int profileCount) {
/*  47 */     this.origin = queryPoint;
/*  48 */     this.tunedDetail = tunedDetail;
/*  49 */     this.profileCount = profileCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPercentageProfile(double rate) {
/*  57 */     synchronized (this.rateMonitor) {
/*     */       
/*  59 */       if (this.lastRate != rate) {
/*     */         
/*  61 */         this.lastRate = rate;
/*  62 */         this.rateTotal = 0;
/*  63 */         this.rateHits = 0;
/*     */       } 
/*     */       
/*  66 */       this.rateTotal++;
/*  67 */       if (rate > this.rateHits / this.rateTotal) {
/*  68 */         this.rateHits++;
/*  69 */         return true;
/*     */       } 
/*  71 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaAutoFetchTunedQueryInfo createPublicMeta() {
/*  80 */     return new MetaAutoFetchTunedQueryInfo(this.origin, this.tunedDetail.toString(), this.profileCount, this.tunedCount, this.lastTuneTime.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProfileCount(int profileCount) {
/*  89 */     this.profileCount = profileCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTunedDetail(OrmQueryDetail tunedDetail) {
/*  97 */     this.tunedDetail = tunedDetail;
/*  98 */     this.lastTuneTime = Long.valueOf(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSame(OrmQueryDetail newQueryDetail) {
/* 105 */     if (this.tunedDetail == null) {
/* 106 */       return false;
/*     */     }
/* 108 */     return this.tunedDetail.isAutoFetchEqual(newQueryDetail);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean autoFetchTune(SpiQuery<?> query) {
/* 117 */     if (this.tunedDetail == null) {
/* 118 */       return false;
/*     */     }
/*     */     
/* 121 */     boolean tuned = false;
/*     */     
/* 123 */     if (query.isDetailEmpty()) {
/* 124 */       tuned = true;
/*     */       
/* 126 */       query.setDetail(this.tunedDetail.copy());
/*     */     } else {
/*     */       
/* 129 */       tuned = query.tuneFetchProperties(this.tunedDetail);
/*     */     } 
/* 131 */     if (tuned) {
/* 132 */       query.setAutoFetchTuned(true);
/*     */       
/* 134 */       this.tunedCount++;
/*     */     } 
/* 136 */     return tuned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getLastTuneTime() {
/* 143 */     return this.lastTuneTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTunedCount() {
/* 150 */     return this.tunedCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProfileCount() {
/* 158 */     return this.profileCount;
/*     */   }
/*     */   
/*     */   public OrmQueryDetail getTunedDetail() {
/* 162 */     return this.tunedDetail;
/*     */   }
/*     */   
/*     */   public ObjectGraphOrigin getOrigin() {
/* 166 */     return this.origin;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLogOutput(OrmQueryDetail newQueryDetail) {
/* 171 */     boolean changed = (newQueryDetail != null);
/*     */     
/* 173 */     StringBuilder sb = new StringBuilder(150);
/* 174 */     sb.append(changed ? "\"Changed\"," : "\"New\",");
/* 175 */     sb.append("\"").append(this.origin.getBeanType()).append("\",");
/* 176 */     sb.append("\"").append(this.origin.getKey()).append("\",");
/* 177 */     if (changed) {
/* 178 */       sb.append("\"to: ").append(newQueryDetail.toString()).append("\",");
/* 179 */       sb.append("\"from: ").append(this.tunedDetail.toString()).append("\",");
/*     */     } else {
/* 181 */       sb.append("\"to: ").append(this.tunedDetail.toString()).append("\",");
/* 182 */       sb.append("\"\",");
/*     */     } 
/* 184 */     sb.append("\"").append(this.origin.getFirstStackElement()).append("\"");
/*     */     
/* 186 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 190 */     return this.origin.getBeanType() + " " + this.origin.getKey() + " " + this.tunedDetail;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\autofetch\TunedQueryInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */