/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class ChatComponentText extends ChatBaseComponent {
/*    */   private final String b;
/*    */   
/*    */   public ChatComponentText(String paramString) {
/*  7 */     this.b = paramString;
/*    */   }
/*    */   
/*    */   public String g() {
/* 11 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public String e() {
/* 16 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatComponentText h() {
/* 21 */     ChatComponentText chatComponentText = new ChatComponentText(this.b);
/* 22 */     chatComponentText.setChatModifier(getChatModifier().clone());
/* 23 */     for (IChatBaseComponent iChatBaseComponent : a()) {
/* 24 */       chatComponentText.addSibling(iChatBaseComponent.f());
/*    */     }
/* 26 */     return chatComponentText;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 31 */     if (this == paramObject) return true;
/*    */     
/* 33 */     if (paramObject instanceof ChatComponentText) {
/* 34 */       ChatComponentText chatComponentText = (ChatComponentText)paramObject;
/* 35 */       return (this.b.equals(chatComponentText.g()) && super.equals(paramObject));
/*    */     } 
/*    */     
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 43 */     return "TextComponent{text='" + this.b + '\'' + ", siblings=" + this.a + ", style=" + getChatModifier() + '}';
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatComponentText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */