/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public enum EnumItemRarity
/*    */ {
/*  6 */   COMMON(EnumChatFormat.WHITE, "Common"),
/*  7 */   UNCOMMON(EnumChatFormat.YELLOW, "Uncommon"),
/*  8 */   RARE(EnumChatFormat.AQUA, "Rare"),
/*  9 */   EPIC(EnumChatFormat.LIGHT_PURPLE, "Epic");
/*    */   
/*    */   public final EnumChatFormat e;
/*    */   public final String f;
/*    */   
/*    */   EnumItemRarity(EnumChatFormat paramEnumChatFormat, String paramString1) {
/* 15 */     this.e = paramEnumChatFormat;
/* 16 */     this.f = paramString1;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumItemRarity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */