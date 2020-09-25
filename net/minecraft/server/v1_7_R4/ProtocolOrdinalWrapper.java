/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ class ProtocolOrdinalWrapper
/*    */ {
/*  6 */   static final int[] a = new int[(EnumProtocol.values()).length];
/*    */   
/*    */   static {
/*    */     try {
/* 10 */       a[EnumProtocol.LOGIN.ordinal()] = 1;
/* 11 */     } catch (NoSuchFieldError nosuchfielderror) {}
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 16 */       a[EnumProtocol.STATUS.ordinal()] = 2;
/* 17 */     } catch (NoSuchFieldError nosuchfielderror1) {}
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ProtocolOrdinalWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */