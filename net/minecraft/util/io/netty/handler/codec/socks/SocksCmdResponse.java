/*    */ package net.minecraft.util.io.netty.handler.codec.socks;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SocksCmdResponse
/*    */   extends SocksResponse
/*    */ {
/*    */   private final SocksCmdStatus cmdStatus;
/*    */   private final SocksAddressType addressType;
/* 31 */   private static final byte[] IPv4_HOSTNAME_ZEROED = new byte[] { 0, 0, 0, 0 };
/* 32 */   private static final byte[] IPv6_HOSTNAME_ZEROED = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksCmdResponse(SocksCmdStatus cmdStatus, SocksAddressType addressType) {
/* 38 */     super(SocksResponseType.CMD);
/* 39 */     if (cmdStatus == null) {
/* 40 */       throw new NullPointerException("cmdStatus");
/*    */     }
/* 42 */     if (addressType == null) {
/* 43 */       throw new NullPointerException("addressType");
/*    */     }
/* 45 */     this.cmdStatus = cmdStatus;
/* 46 */     this.addressType = addressType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksCmdStatus cmdStatus() {
/* 55 */     return this.cmdStatus;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksAddressType addressType() {
/* 64 */     return this.addressType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf byteBuf) {
/* 69 */     byteBuf.writeByte(protocolVersion().byteValue());
/* 70 */     byteBuf.writeByte(this.cmdStatus.byteValue());
/* 71 */     byteBuf.writeByte(0);
/* 72 */     byteBuf.writeByte(this.addressType.byteValue());
/* 73 */     switch (this.addressType) {
/*    */       case IPv4:
/* 75 */         byteBuf.writeBytes(IPv4_HOSTNAME_ZEROED);
/* 76 */         byteBuf.writeShort(0);
/*    */         break;
/*    */       
/*    */       case DOMAIN:
/* 80 */         byteBuf.writeByte(1);
/* 81 */         byteBuf.writeByte(0);
/* 82 */         byteBuf.writeShort(0);
/*    */         break;
/*    */       
/*    */       case IPv6:
/* 86 */         byteBuf.writeBytes(IPv6_HOSTNAME_ZEROED);
/* 87 */         byteBuf.writeShort(0);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksCmdResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */