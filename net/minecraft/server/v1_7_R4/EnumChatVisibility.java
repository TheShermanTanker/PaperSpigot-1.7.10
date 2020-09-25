/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumChatVisibility
/*    */ {
/* 52 */   FULL(0, "options.chat.visibility.full"),
/* 53 */   SYSTEM(1, "options.chat.visibility.system"),
/* 54 */   HIDDEN(2, "options.chat.visibility.hidden");
/*    */   static {
/* 56 */     d = new EnumChatVisibility[(values()).length];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 74 */     for (EnumChatVisibility enumChatVisibility : values())
/* 75 */       d[enumChatVisibility.e] = enumChatVisibility; 
/*    */   }
/*    */   
/*    */   private static final EnumChatVisibility[] d;
/*    */   private final int e;
/*    */   private final String f;
/*    */   
/*    */   EnumChatVisibility(int paramInt1, String paramString1) {
/*    */     this.e = paramInt1;
/*    */     this.f = paramString1;
/*    */   }
/*    */   
/*    */   public int a() {
/*    */     return this.e;
/*    */   }
/*    */   
/*    */   public static EnumChatVisibility a(int paramInt) {
/*    */     return d[paramInt % d.length];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumChatVisibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */