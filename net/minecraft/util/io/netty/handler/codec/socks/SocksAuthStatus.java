/*    */ package net.minecraft.util.io.netty.handler.codec.socks;
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
/*    */ public enum SocksAuthStatus
/*    */ {
/* 20 */   SUCCESS((byte)0),
/* 21 */   FAILURE((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksAuthStatus(byte b) {
/* 26 */     this.b = b;
/*    */   }
/*    */   
/*    */   public static SocksAuthStatus fromByte(byte b) {
/* 30 */     for (SocksAuthStatus code : values()) {
/* 31 */       if (code.b == b) {
/* 32 */         return code;
/*    */       }
/*    */     } 
/* 35 */     return FAILURE;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 39 */     return this.b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksAuthStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */