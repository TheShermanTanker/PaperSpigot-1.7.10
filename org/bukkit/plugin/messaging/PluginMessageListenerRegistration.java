/*     */ package org.bukkit.plugin.messaging;
/*     */ 
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PluginMessageListenerRegistration
/*     */ {
/*     */   private final Messenger messenger;
/*     */   private final Plugin plugin;
/*     */   private final String channel;
/*     */   private final PluginMessageListener listener;
/*     */   
/*     */   public PluginMessageListenerRegistration(Messenger messenger, Plugin plugin, String channel, PluginMessageListener listener) {
/*  16 */     if (messenger == null) {
/*  17 */       throw new IllegalArgumentException("Messenger cannot be null!");
/*     */     }
/*  19 */     if (plugin == null) {
/*  20 */       throw new IllegalArgumentException("Plugin cannot be null!");
/*     */     }
/*  22 */     if (channel == null) {
/*  23 */       throw new IllegalArgumentException("Channel cannot be null!");
/*     */     }
/*  25 */     if (listener == null) {
/*  26 */       throw new IllegalArgumentException("Listener cannot be null!");
/*     */     }
/*     */     
/*  29 */     this.messenger = messenger;
/*  30 */     this.plugin = plugin;
/*  31 */     this.channel = channel;
/*  32 */     this.listener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getChannel() {
/*  41 */     return this.channel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginMessageListener getListener() {
/*  50 */     return this.listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Plugin getPlugin() {
/*  59 */     return this.plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  68 */     return this.messenger.isRegistrationValid(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  73 */     if (obj == null) {
/*  74 */       return false;
/*     */     }
/*  76 */     if (getClass() != obj.getClass()) {
/*  77 */       return false;
/*     */     }
/*  79 */     PluginMessageListenerRegistration other = (PluginMessageListenerRegistration)obj;
/*  80 */     if (this.messenger != other.messenger && (this.messenger == null || !this.messenger.equals(other.messenger))) {
/*  81 */       return false;
/*     */     }
/*  83 */     if (this.plugin != other.plugin && (this.plugin == null || !this.plugin.equals(other.plugin))) {
/*  84 */       return false;
/*     */     }
/*  86 */     if ((this.channel == null) ? (other.channel != null) : !this.channel.equals(other.channel)) {
/*  87 */       return false;
/*     */     }
/*  89 */     if (this.listener != other.listener && (this.listener == null || !this.listener.equals(other.listener))) {
/*  90 */       return false;
/*     */     }
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  97 */     int hash = 7;
/*  98 */     hash = 53 * hash + ((this.messenger != null) ? this.messenger.hashCode() : 0);
/*  99 */     hash = 53 * hash + ((this.plugin != null) ? this.plugin.hashCode() : 0);
/* 100 */     hash = 53 * hash + ((this.channel != null) ? this.channel.hashCode() : 0);
/* 101 */     hash = 53 * hash + ((this.listener != null) ? this.listener.hashCode() : 0);
/* 102 */     return hash;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\messaging\PluginMessageListenerRegistration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */