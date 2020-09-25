/*    */ package org.bukkit.craftbukkit.v1_7_R4.help;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HelpTopicAmendment
/*    */ {
/*    */   private final String topicName;
/*    */   private final String shortText;
/*    */   private final String fullText;
/*    */   private final String permission;
/*    */   
/*    */   public HelpTopicAmendment(String topicName, String shortText, String fullText, String permission) {
/* 13 */     this.fullText = fullText;
/* 14 */     this.shortText = shortText;
/* 15 */     this.topicName = topicName;
/* 16 */     this.permission = permission;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFullText() {
/* 24 */     return this.fullText;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getShortText() {
/* 32 */     return this.shortText;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTopicName() {
/* 40 */     return this.topicName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermission() {
/* 48 */     return this.permission;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\help\HelpTopicAmendment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */