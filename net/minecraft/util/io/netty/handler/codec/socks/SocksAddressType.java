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
/*    */ public enum SocksAddressType
/*    */ {
/* 20 */   IPv4((byte)1),
/* 21 */   DOMAIN((byte)3),
/* 22 */   IPv6((byte)4),
/* 23 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksAddressType(byte b) {
/* 28 */     this.b = b;
/*    */   }
/*    */   
/*    */   public static SocksAddressType fromByte(byte b) {
/* 32 */     for (SocksAddressType code : values()) {
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksAddressType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */