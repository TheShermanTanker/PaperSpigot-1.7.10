package org.bukkit.conversations;

public interface Conversable {
  boolean isConversing();
  
  void acceptConversationInput(String paramString);
  
  boolean beginConversation(Conversation paramConversation);
  
  void abandonConversation(Conversation paramConversation);
  
  void abandonConversation(Conversation paramConversation, ConversationAbandonedEvent paramConversationAbandonedEvent);
  
  void sendRawMessage(String paramString);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\Conversable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */