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
/*    */ public final class SocksInitResponse
/*    */   extends SocksResponse
/*    */ {
/*    */   private final SocksAuthScheme authScheme;
/*    */   
/*    */   public SocksInitResponse(SocksAuthScheme authScheme) {
/* 30 */     super(SocksResponseType.INIT);
/* 31 */     if (authScheme == null) {
/* 32 */       throw new NullPointerException("authScheme");
/*    */     }
/* 34 */     this.authScheme = authScheme;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksAuthScheme authScheme() {
/* 43 */     return this.authScheme;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf byteBuf) {
/* 48 */     byteBuf.writeByte(protocolVersion().byteValue());
/* 49 */     byteBuf.writeByte(this.authScheme.byteValue());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksInitResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */