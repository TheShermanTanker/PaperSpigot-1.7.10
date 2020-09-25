package org.bukkit.conversations;

public interface ConversationCanceller extends Cloneable {
  void setConversation(Conversation paramConversation);
  
  boolean cancelBasedOnInput(ConversationContext paramConversationContext, String paramString);
  
  ConversationCanceller clone();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\ConversationCanceller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */