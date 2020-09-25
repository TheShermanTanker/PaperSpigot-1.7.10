/*     */ package org.bukkit.craftbukkit.v1_7_R4.updater;
/*     */ 
/*     */ import java.util.Date;
/*     */ 
/*     */ public class ArtifactDetails {
/*     */   private String brokenReason;
/*     */   private boolean isBroken;
/*     */   private int buildNumber;
/*     */   private String htmlUrl;
/*     */   private String version;
/*     */   private Date created;
/*     */   private FileDetails file;
/*     */   private ChannelDetails channel;
/*     */   
/*     */   public ChannelDetails getChannel() {
/*  16 */     return this.channel;
/*     */   }
/*     */   
/*     */   public void setChannel(ChannelDetails channel) {
/*  20 */     this.channel = channel;
/*     */   }
/*     */   
/*     */   public boolean isIsBroken() {
/*  24 */     return this.isBroken;
/*     */   }
/*     */   
/*     */   public void setIsBroken(boolean isBroken) {
/*  28 */     this.isBroken = isBroken;
/*     */   }
/*     */   
/*     */   public FileDetails getFile() {
/*  32 */     return this.file;
/*     */   }
/*     */   
/*     */   public void setFile(FileDetails file) {
/*  36 */     this.file = file;
/*     */   }
/*     */   
/*     */   public String getBrokenReason() {
/*  40 */     return this.brokenReason;
/*     */   }
/*     */   
/*     */   public void setBrokenReason(String brokenReason) {
/*  44 */     this.brokenReason = brokenReason;
/*     */   }
/*     */   
/*     */   public int getBuildNumber() {
/*  48 */     return this.buildNumber;
/*     */   }
/*     */   
/*     */   public void setBuildNumber(int buildNumber) {
/*  52 */     this.buildNumber = buildNumber;
/*     */   }
/*     */   
/*     */   public Date getCreated() {
/*  56 */     return this.created;
/*     */   }
/*     */   
/*     */   public void setCreated(Date created) {
/*  60 */     this.created = created;
/*     */   }
/*     */   
/*     */   public String getHtmlUrl() {
/*  64 */     return this.htmlUrl;
/*     */   }
/*     */   
/*     */   public void setHtmlUrl(String htmlUrl) {
/*  68 */     this.htmlUrl = htmlUrl;
/*     */   }
/*     */   
/*     */   public boolean isBroken() {
/*  72 */     return this.isBroken;
/*     */   }
/*     */   
/*     */   public void setBroken(boolean isBroken) {
/*  76 */     this.isBroken = isBroken;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/*  80 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(String version) {
/*  84 */     this.version = version;
/*     */   }
/*     */   
/*     */   public static class FileDetails {
/*     */     private String url;
/*     */     
/*     */     public String getUrl() {
/*  91 */       return this.url;
/*     */     }
/*     */     
/*     */     public void setUrl(String url) {
/*  95 */       this.url = url;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ChannelDetails {
/*     */     private String name;
/*     */     private String slug;
/*     */     private int priority;
/*     */     
/*     */     public String getName() {
/* 105 */       return this.name;
/*     */     }
/*     */     
/*     */     public void setName(String name) {
/* 109 */       this.name = name;
/*     */     }
/*     */     
/*     */     public int getPriority() {
/* 113 */       return this.priority;
/*     */     }
/*     */     
/*     */     public void setPriority(int priority) {
/* 117 */       this.priority = priority;
/*     */     }
/*     */     
/*     */     public String getSlug() {
/* 121 */       return this.slug;
/*     */     }
/*     */     
/*     */     public void setSlug(String slug) {
/* 125 */       this.slug = slug;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\updater\ArtifactDetails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */