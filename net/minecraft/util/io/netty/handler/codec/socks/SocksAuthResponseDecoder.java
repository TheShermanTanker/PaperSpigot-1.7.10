/*    */ package net.minecraft.util.io.netty.handler.codec.socks;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.ReplayingDecoder;
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
/*    */ public class SocksAuthResponseDecoder
/*    */   extends ReplayingDecoder<SocksAuthResponseDecoder.State>
/*    */ {
/*    */   private static final String name = "SOCKS_AUTH_RESPONSE_DECODER";
/*    */   private SocksSubnegotiationVersion version;
/*    */   private SocksAuthStatus authStatus;
/*    */   
/*    */   public static String getName() {
/* 32 */     return "SOCKS_AUTH_RESPONSE_DECODER";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 37 */   private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
/*    */   
/*    */   public SocksAuthResponseDecoder() {
/* 40 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
/* 46 */     switch ((State)state()) {
/*    */       case CHECK_PROTOCOL_VERSION:
/* 48 */         this.version = SocksSubnegotiationVersion.fromByte(byteBuf.readByte());
/* 49 */         if (this.version != SocksSubnegotiationVersion.AUTH_PASSWORD) {
/*    */           break;
/*    */         }
/* 52 */         checkpoint(State.READ_AUTH_RESPONSE);
/*    */       
/*    */       case READ_AUTH_RESPONSE:
/* 55 */         this.authStatus = SocksAuthStatus.fromByte(byteBuf.readByte());
/* 56 */         this.msg = new SocksAuthResponse(this.authStatus);
/*    */         break;
/*    */     } 
/* 59 */     channelHandlerContext.pipeline().remove((ChannelHandler)this);
/* 60 */     out.add(this.msg);
/*    */   }
/*    */   
/*    */   enum State {
/* 64 */     CHECK_PROTOCOL_VERSION,
/* 65 */     READ_AUTH_RESPONSE;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksAuthResponseDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */