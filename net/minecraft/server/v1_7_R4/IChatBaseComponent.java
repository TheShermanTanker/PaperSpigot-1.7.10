package net.minecraft.server.v1_7_R4;

import java.util.List;

public interface IChatBaseComponent extends Iterable {
  IChatBaseComponent setChatModifier(ChatModifier paramChatModifier);
  
  ChatModifier getChatModifier();
  
  IChatBaseComponent a(String paramString);
  
  IChatBaseComponent addSibling(IChatBaseComponent paramIChatBaseComponent);
  
  String e();
  
  String c();
  
  List a();
  
  IChatBaseComponent f();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IChatBaseComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */