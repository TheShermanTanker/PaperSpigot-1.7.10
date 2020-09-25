/*    */ package net.minecraft.util.io.netty.handler.codec.socks;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public final class SocksInitRequest
/*    */   extends SocksRequest
/*    */ {
/*    */   private final List<SocksAuthScheme> authSchemes;
/*    */   
/*    */   public SocksInitRequest(List<SocksAuthScheme> authSchemes) {
/* 33 */     super(SocksRequestType.INIT);
/* 34 */     if (authSchemes == null) {
/* 35 */       throw new NullPointerException("authSchemes");
/*    */     }
/* 37 */     this.authSchemes = authSchemes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<SocksAuthScheme> authSchemes() {
/* 46 */     return Collections.unmodifiableList(this.authSchemes);
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf byteBuf) {
/* 51 */     byteBuf.writeByte(protocolVersion().byteValue());
/* 52 */     byteBuf.writeByte(this.authSchemes.size());
/* 53 */     for (SocksAuthScheme authScheme : this.authSchemes)
/* 54 */       byteBuf.writeByte(authScheme.byteValue()); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksInitRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */