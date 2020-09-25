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
/*    */ public enum SocksCmdType
/*    */ {
/* 20 */   CONNECT((byte)1),
/* 21 */   BIND((byte)2),
/* 22 */   UDP((byte)3),
/* 23 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksCmdType(byte b) {
/* 28 */     this.b = b;
/*    */   }
/*    */   
/*    */   public static SocksCmdType fromByte(byte b) {
/* 32 */     for (SocksCmdType code : values()) {
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksCmdType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */