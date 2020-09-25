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
/*    */ public enum SocksSubnegotiationVersion
/*    */ {
/* 20 */   AUTH_PASSWORD((byte)1),
/* 21 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksSubnegotiationVersion(byte b) {
/* 26 */     this.b = b;
/*    */   }
/*    */   
/*    */   public static SocksSubnegotiationVersion fromByte(byte b) {
/* 30 */     for (SocksSubnegotiationVersion code : values()) {
/* 31 */       if (code.b == b) {
/* 32 */         return code;
/*    */       }
/*    */     } 
/* 35 */     return UNKNOWN;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 39 */     return this.b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksSubnegotiationVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */