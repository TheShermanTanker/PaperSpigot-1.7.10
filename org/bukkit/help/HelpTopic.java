/*     */ package org.bukkit.help;
/*     */ 
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
/*     */ public abstract class HelpTopic
/*     */ {
/*     */   protected String name;
/*     */   protected String shortText;
/*     */   protected String fullText;
/*     */   protected String amendedPermission;
/*     */   
/*     */   public abstract boolean canSee(CommandSender paramCommandSender);
/*     */   
/*     */   public void amendCanSee(String amendedPermission) {
/*  47 */     this.amendedPermission = amendedPermission;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  56 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getShortText() {
/*  65 */     return this.shortText;
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
/*     */   public String getFullText(CommandSender forWho) {
/*  81 */     return this.fullText;
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
/*     */   public void amendTopic(String amendedShortText, String amendedFullText) {
/*  99 */     this.shortText = applyAmendment(this.shortText, amendedShortText);
/* 100 */     this.fullText = applyAmendment(this.fullText, amendedFullText);
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
/*     */   protected String applyAmendment(String baseText, String amendment) {
/* 115 */     if (amendment == null) {
/* 116 */       return baseText;
/*     */     }
/* 118 */     return amendment.replaceAll("<text>", baseText);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\help\HelpTopic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */