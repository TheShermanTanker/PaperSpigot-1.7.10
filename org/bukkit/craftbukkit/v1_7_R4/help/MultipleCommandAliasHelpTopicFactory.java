/*    */ package org.bukkit.craftbukkit.v1_7_R4.help;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.MultipleCommandAlias;
/*    */ import org.bukkit.help.HelpTopic;
/*    */ import org.bukkit.help.HelpTopicFactory;
/*    */ 
/*    */ 
/*    */ public class MultipleCommandAliasHelpTopicFactory
/*    */   implements HelpTopicFactory<MultipleCommandAlias>
/*    */ {
/*    */   public HelpTopic createTopic(MultipleCommandAlias multipleCommandAlias) {
/* 13 */     return new MultipleCommandAliasHelpTopic(multipleCommandAlias);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\help\MultipleCommandAliasHelpTopicFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */