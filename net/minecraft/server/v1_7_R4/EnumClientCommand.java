/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumClientCommand
/*    */ {
/* 10 */   PERFORM_RESPAWN(0),
/* 11 */   REQUEST_STATS(1),
/* 12 */   OPEN_INVENTORY_ACHIEVEMENT(2);
/*    */   
/*    */   static {
/* 15 */     e = new EnumClientCommand[(values()).length];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 22 */     for (EnumClientCommand enumClientCommand : values())
/* 23 */       e[enumClientCommand.d] = enumClientCommand; 
/*    */   }
/*    */   
/*    */   private final int d;
/*    */   private static final EnumClientCommand[] e;
/*    */   
/*    */   EnumClientCommand(int paramInt1) {
/*    */     this.d = paramInt1;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumClientCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */