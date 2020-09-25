/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumEntityUseAction
/*    */ {
/* 12 */   INTERACT(0), ATTACK(1);
/*    */   static {
/* 14 */     c = new EnumEntityUseAction[(values()).length];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 23 */     for (EnumEntityUseAction enumEntityUseAction : values())
/* 24 */       c[enumEntityUseAction.d] = enumEntityUseAction; 
/*    */   }
/*    */   
/*    */   private final int d;
/*    */   private static final EnumEntityUseAction[] c;
/*    */   
/*    */   EnumEntityUseAction(int paramInt1) {
/*    */     this.d = paramInt1;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumEntityUseAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */