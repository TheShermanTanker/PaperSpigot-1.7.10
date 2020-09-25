/*     */ package org.bukkit.craftbukkit.v1_7_R4.updater;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class AutoUpdater
/*     */ {
/*     */   public static final String WARN_CONSOLE = "warn-console";
/*     */   public static final String WARN_OPERATORS = "warn-ops";
/*     */   private final BukkitDLUpdaterService service;
/*  12 */   private final List<String> onUpdate = new ArrayList<String>();
/*  13 */   private final List<String> onBroken = new ArrayList<String>();
/*     */   private final Logger log;
/*     */   private final String channel;
/*     */   private boolean enabled;
/*  17 */   private ArtifactDetails current = null;
/*  18 */   private ArtifactDetails latest = null;
/*     */   private boolean suggestChannels = true;
/*     */   
/*     */   public AutoUpdater(BukkitDLUpdaterService service, Logger log, String channel) {
/*  22 */     this.service = service;
/*  23 */     this.log = log;
/*  24 */     this.channel = channel;
/*     */   }
/*     */   
/*     */   public String getChannel() {
/*  28 */     return this.channel;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/*  32 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean isEnabled) {
/*  36 */     this.enabled = isEnabled;
/*     */   }
/*     */   
/*     */   public boolean shouldSuggestChannels() {
/*  40 */     return this.suggestChannels;
/*     */   }
/*     */   
/*     */   public void setSuggestChannels(boolean suggestChannels) {
/*  44 */     this.suggestChannels = suggestChannels;
/*     */   }
/*     */   
/*     */   public List<String> getOnBroken() {
/*  48 */     return this.onBroken;
/*     */   }
/*     */   
/*     */   public List<String> getOnUpdate() {
/*  52 */     return this.onUpdate;
/*     */   }
/*     */   
/*     */   public boolean isUpdateAvailable() {
/*  56 */     if (this.latest == null || this.current == null || !isEnabled()) {
/*  57 */       return false;
/*     */     }
/*  59 */     return this.latest.getCreated().after(this.current.getCreated());
/*     */   }
/*     */ 
/*     */   
/*     */   public ArtifactDetails getCurrent() {
/*  64 */     return this.current;
/*     */   }
/*     */   
/*     */   public ArtifactDetails getLatest() {
/*  68 */     return this.latest;
/*     */   }
/*     */   
/*     */   public void check(final String currentSlug) {
/*  72 */     if (!isEnabled())
/*     */       return; 
/*  74 */     (new Thread()
/*     */       {
/*     */         public void run() {
/*  77 */           AutoUpdater.this.current = AutoUpdater.this.service.getArtifact(currentSlug, "information about this CraftBukkit version; perhaps you are running a custom one?");
/*  78 */           AutoUpdater.this.latest = AutoUpdater.this.service.getArtifact("latest-" + AutoUpdater.this.channel, "latest artifact information");
/*     */           
/*  80 */           if (AutoUpdater.this.isUpdateAvailable()) {
/*  81 */             if (AutoUpdater.this.current.isBroken() && AutoUpdater.this.onBroken.contains("warn-console")) {
/*  82 */               AutoUpdater.this.log.severe("----- Bukkit Auto Updater -----");
/*  83 */               AutoUpdater.this.log.severe("Your version of CraftBukkit is known to be broken. It is strongly advised that you update to a more recent version ASAP.");
/*  84 */               AutoUpdater.this.log.severe("Known issues with your version:");
/*     */               
/*  86 */               for (String line : AutoUpdater.this.current.getBrokenReason().split("\n")) {
/*  87 */                 AutoUpdater.this.log.severe("> " + line);
/*     */               }
/*     */               
/*  90 */               AutoUpdater.this.log.severe("Newer version " + AutoUpdater.this.latest.getVersion() + " (build #" + AutoUpdater.this.latest.getBuildNumber() + ") was released on " + AutoUpdater.this.latest.getCreated() + ".");
/*  91 */               AutoUpdater.this.log.severe("Details: " + AutoUpdater.this.latest.getHtmlUrl());
/*  92 */               AutoUpdater.this.log.severe("Download: " + AutoUpdater.this.latest.getFile().getUrl());
/*  93 */               AutoUpdater.this.log.severe("----- ------------------- -----");
/*  94 */             } else if (AutoUpdater.this.onUpdate.contains("warn-console")) {
/*  95 */               AutoUpdater.this.log.warning("----- Bukkit Auto Updater -----");
/*  96 */               AutoUpdater.this.log.warning("Your version of CraftBukkit is out of date. Version " + AutoUpdater.this.latest.getVersion() + " (build #" + AutoUpdater.this.latest.getBuildNumber() + ") was released on " + AutoUpdater.this.latest.getCreated() + ".");
/*  97 */               AutoUpdater.this.log.warning("Details: " + AutoUpdater.this.latest.getHtmlUrl());
/*  98 */               AutoUpdater.this.log.warning("Download: " + AutoUpdater.this.latest.getFile().getUrl());
/*  99 */               AutoUpdater.this.log.warning("----- ------------------- -----");
/*     */             } 
/* 101 */           } else if (AutoUpdater.this.current != null && AutoUpdater.this.current.isBroken() && AutoUpdater.this.onBroken.contains("warn-console")) {
/* 102 */             AutoUpdater.this.log.severe("----- Bukkit Auto Updater -----");
/* 103 */             AutoUpdater.this.log.severe("Your version of CraftBukkit is known to be broken. It is strongly advised that you update to a more recent version ASAP.");
/* 104 */             AutoUpdater.this.log.severe("Known issues with your version:");
/*     */             
/* 106 */             for (String line : AutoUpdater.this.current.getBrokenReason().split("\n")) {
/* 107 */               AutoUpdater.this.log.severe("> " + line);
/*     */             }
/*     */             
/* 110 */             AutoUpdater.this.log.severe("Unfortunately, there is not yet a newer version suitable for your server. We would advise you wait an hour or two, or try out a dev build.");
/* 111 */             AutoUpdater.this.log.severe("----- ------------------- -----");
/* 112 */           } else if (AutoUpdater.this.current != null && AutoUpdater.this.shouldSuggestChannels()) {
/* 113 */             ArtifactDetails.ChannelDetails prefChan = AutoUpdater.this.service.getChannel(AutoUpdater.this.channel, "preferred channel details");
/*     */             
/* 115 */             if (prefChan != null && AutoUpdater.this.current.getChannel().getPriority() < prefChan.getPriority()) {
/* 116 */               AutoUpdater.this.log.info("----- Bukkit Auto Updater -----");
/* 117 */               AutoUpdater.this.log.info("It appears that you're running a " + AutoUpdater.this.current.getChannel().getName() + ", when you've specified in bukkit.yml that you prefer to run " + prefChan.getName() + "s.");
/* 118 */               AutoUpdater.this.log.info("If you would like to be kept informed about new " + AutoUpdater.this.current.getChannel().getName() + " releases, it is recommended that you change 'preferred-channel' in your bukkit.yml to '" + AutoUpdater.this.current.getChannel().getSlug() + "'.");
/* 119 */               AutoUpdater.this.log.info("With that set, you will be told whenever a new version is available for download, so that you can always keep up to date and secure with the latest fixes.");
/* 120 */               AutoUpdater.this.log.info("If you would like to disable this warning, simply set 'suggest-channels' to false in bukkit.yml.");
/* 121 */               AutoUpdater.this.log.info("----- ------------------- -----");
/*     */             } 
/*     */           } 
/*     */         }
/*     */       }).start();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\updater\AutoUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */