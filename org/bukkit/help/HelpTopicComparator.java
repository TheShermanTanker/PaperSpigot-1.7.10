/*    */ package org.bukkit.help;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HelpTopicComparator
/*    */   implements Comparator<HelpTopic>
/*    */ {
/* 16 */   private static final TopicNameComparator tnc = new TopicNameComparator();
/*    */   public static TopicNameComparator topicNameComparatorInstance() {
/* 18 */     return tnc;
/*    */   }
/*    */   
/* 21 */   private static final HelpTopicComparator htc = new HelpTopicComparator();
/*    */   public static HelpTopicComparator helpTopicComparatorInstance() {
/* 23 */     return htc;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compare(HelpTopic lhs, HelpTopic rhs) {
/* 29 */     return tnc.compare(lhs.getName(), rhs.getName());
/*    */   }
/*    */   
/*    */   public static class TopicNameComparator implements Comparator<String> {
/*    */     private TopicNameComparator() {}
/*    */     
/*    */     public int compare(String lhs, String rhs) {
/* 36 */       boolean lhsStartSlash = lhs.startsWith("/");
/* 37 */       boolean rhsStartSlash = rhs.startsWith("/");
/*    */       
/* 39 */       if (lhsStartSlash && !rhsStartSlash)
/* 40 */         return 1; 
/* 41 */       if (!lhsStartSlash && rhsStartSlash) {
/* 42 */         return -1;
/*    */       }
/* 44 */       return lhs.compareToIgnoreCase(rhs);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\help\HelpTopicComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */