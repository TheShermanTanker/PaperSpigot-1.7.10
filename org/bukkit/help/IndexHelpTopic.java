/*     */ package org.bukkit.help;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
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
/*     */ public class IndexHelpTopic
/*     */   extends HelpTopic
/*     */ {
/*     */   protected String permission;
/*     */   protected String preamble;
/*     */   protected Collection<HelpTopic> allTopics;
/*     */   
/*     */   public IndexHelpTopic(String name, String shortText, String permission, Collection<HelpTopic> topics) {
/*  26 */     this(name, shortText, permission, topics, (String)null);
/*     */   }
/*     */   
/*     */   public IndexHelpTopic(String name, String shortText, String permission, Collection<HelpTopic> topics, String preamble) {
/*  30 */     this.name = name;
/*  31 */     this.shortText = shortText;
/*  32 */     this.permission = permission;
/*  33 */     this.preamble = preamble;
/*  34 */     setTopicsCollection(topics);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTopicsCollection(Collection<HelpTopic> topics) {
/*  43 */     this.allTopics = topics;
/*     */   }
/*     */   
/*     */   public boolean canSee(CommandSender sender) {
/*  47 */     if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
/*  48 */       return true;
/*     */     }
/*  50 */     if (this.permission == null) {
/*  51 */       return true;
/*     */     }
/*  53 */     return sender.hasPermission(this.permission);
/*     */   }
/*     */ 
/*     */   
/*     */   public void amendCanSee(String amendedPermission) {
/*  58 */     this.permission = amendedPermission;
/*     */   }
/*     */   
/*     */   public String getFullText(CommandSender sender) {
/*  62 */     StringBuilder sb = new StringBuilder();
/*     */     
/*  64 */     if (this.preamble != null) {
/*  65 */       sb.append(buildPreamble(sender));
/*  66 */       sb.append("\n");
/*     */     } 
/*     */     
/*  69 */     for (HelpTopic topic : this.allTopics) {
/*  70 */       if (topic.canSee(sender)) {
/*  71 */         String lineStr = buildIndexLine(sender, topic).replace("\n", ". ");
/*  72 */         if (sender instanceof org.bukkit.entity.Player && lineStr.length() > 55) {
/*  73 */           sb.append(lineStr.substring(0, 52));
/*  74 */           sb.append("...");
/*     */         } else {
/*  76 */           sb.append(lineStr);
/*     */         } 
/*  78 */         sb.append("\n");
/*     */       } 
/*     */     } 
/*  81 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String buildPreamble(CommandSender sender) {
/*  92 */     return ChatColor.GRAY + this.preamble;
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
/*     */   protected String buildIndexLine(CommandSender sender, HelpTopic topic) {
/* 104 */     StringBuilder line = new StringBuilder();
/* 105 */     line.append(ChatColor.GOLD);
/* 106 */     line.append(topic.getName());
/* 107 */     line.append(": ");
/* 108 */     line.append(ChatColor.WHITE);
/* 109 */     line.append(topic.getShortText());
/* 110 */     return line.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\help\IndexHelpTopic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */