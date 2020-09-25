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
/*    */ public enum SocksAuthScheme
/*    */ {
/* 20 */   NO_AUTH((byte)0),
/* 21 */   AUTH_GSSAPI((byte)1),
/* 22 */   AUTH_PASSWORD((byte)2),
/* 23 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksAuthScheme(byte b) {
/* 28 */     this.b = b;
/*    */   }
/*    */   
/*    */   public static SocksAuthScheme fromByte(byte b) {
/* 32 */     for (SocksAuthScheme code : values()) {
/* 33 */       if (code.b == b) {
/* 34 */         return code;
/*    */       }
/*    */     } 
/* 37 */     return UNKNOWN;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 41 */     return this.b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksAuthScheme.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */