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
/*    */ 
/*    */ public abstract class SocksMessage
/*    */ {
/*    */   private final SocksMessageType type;
/* 30 */   private final SocksProtocolVersion protocolVersion = SocksProtocolVersion.SOCKS5;
/*    */   
/*    */   protected SocksMessage(SocksMessageType type) {
/* 33 */     if (type == null) {
/* 34 */       throw new NullPointerException("type");
/*    */     }
/* 36 */     this.type = type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksMessageType type() {
/* 45 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksProtocolVersion protocolVersion() {
/* 54 */     return this.protocolVersion;
/*    */   }
/*    */   
/*    */   public abstract void encodeAsByteBuf(ByteBuf paramByteBuf);
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */