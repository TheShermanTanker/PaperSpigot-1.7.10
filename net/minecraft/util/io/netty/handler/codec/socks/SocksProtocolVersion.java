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
/*    */ public enum SocksProtocolVersion
/*    */ {
/* 20 */   SOCKS4a((byte)4),
/* 21 */   SOCKS5((byte)5),
/* 22 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksProtocolVersion(byte b) {
/* 27 */     this.b = b;
/*    */   }
/*    */   
/*    */   public static SocksProtocolVersion fromByte(byte b) {
/* 31 */     for (SocksProtocolVersion code : values()) {
/* 32 */       if (code.b == b) {
/* 33 */         return code;
/*    */       }
/*    */     } 
/* 36 */     return UNKNOWN;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 40 */     return this.b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksProtocolVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */