/*    */ package org.bukkit.craftbukkit.v1_7_R4.help;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.help.HelpMap;
/*    */ import org.bukkit.help.HelpTopic;
/*    */ import org.bukkit.help.IndexHelpTopic;
/*    */ 
/*    */ 
/*    */ public class CustomIndexHelpTopic
/*    */   extends IndexHelpTopic
/*    */ {
/*    */   private List<String> futureTopics;
/*    */   private final HelpMap helpMap;
/*    */   
/*    */   public CustomIndexHelpTopic(HelpMap helpMap, String name, String shortText, String permission, List<String> futureTopics, String preamble) {
/* 19 */     super(name, shortText, permission, new HashSet(), preamble);
/* 20 */     this.helpMap = helpMap;
/* 21 */     this.futureTopics = futureTopics;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFullText(CommandSender sender) {
/* 26 */     if (this.futureTopics != null) {
/* 27 */       List<HelpTopic> topics = new LinkedList<HelpTopic>();
/* 28 */       for (String futureTopic : this.futureTopics) {
/* 29 */         HelpTopic topic = this.helpMap.getHelpTopic(futureTopic);
/* 30 */         if (topic != null) {
/* 31 */           topics.add(topic);
/*    */         }
/*    */       } 
/* 34 */       setTopicsCollection(topics);
/* 35 */       this.futureTopics = null;
/*    */     } 
/*    */     
/* 38 */     return super.getFullText(sender);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\help\CustomIndexHelpTopic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */