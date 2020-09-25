/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ class ClientCommandOrdinalWrapper
/*    */ {
/*  6 */   static final int[] a = new int[(EnumClientCommand.values()).length];
/*    */   
/*    */   static {
/*    */     try {
/* 10 */       a[EnumClientCommand.PERFORM_RESPAWN.ordinal()] = 1;
/* 11 */     } catch (NoSuchFieldError nosuchfielderror) {}
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 16 */       a[EnumClientCommand.REQUEST_STATS.ordinal()] = 2;
/* 17 */     } catch (NoSuchFieldError nosuchfielderror1) {}
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 22 */       a[EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
/* 23 */     } catch (NoSuchFieldError nosuchfielderror2) {}
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ClientCommandOrdinalWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */