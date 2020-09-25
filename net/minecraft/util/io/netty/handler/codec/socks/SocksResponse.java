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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SocksResponse
/*    */   extends SocksMessage
/*    */ {
/*    */   private final SocksResponseType responseType;
/*    */   
/*    */   protected SocksResponse(SocksResponseType responseType) {
/* 31 */     super(SocksMessageType.RESPONSE);
/* 32 */     if (responseType == null) {
/* 33 */       throw new NullPointerException("responseType");
/*    */     }
/* 35 */     this.responseType = responseType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksResponseType responseType() {
/* 44 */     return this.responseType;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */