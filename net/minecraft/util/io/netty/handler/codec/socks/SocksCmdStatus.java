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
/*    */ public enum SocksCmdStatus
/*    */ {
/* 20 */   SUCCESS((byte)0),
/* 21 */   FAILURE((byte)1),
/* 22 */   FORBIDDEN((byte)2),
/* 23 */   NETWORK_UNREACHABLE((byte)3),
/* 24 */   HOST_UNREACHABLE((byte)4),
/* 25 */   REFUSED((byte)5),
/* 26 */   TTL_EXPIRED((byte)6),
/* 27 */   COMMAND_NOT_SUPPORTED((byte)7),
/* 28 */   ADDRESS_NOT_SUPPORTED((byte)8),
/* 29 */   UNASSIGNED((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksCmdStatus(byte b) {
/* 34 */     this.b = b;
/*    */   }
/*    */   
/*    */   public static SocksCmdStatus fromByte(byte b) {
/* 38 */     for (SocksCmdStatus code : values()) {
/* 39 */       if (code.b == b) {
/* 40 */         return code;
/*    */       }
/*    */     } 
/* 43 */     return UNASSIGNED;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 47 */     return this.b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksCmdStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */