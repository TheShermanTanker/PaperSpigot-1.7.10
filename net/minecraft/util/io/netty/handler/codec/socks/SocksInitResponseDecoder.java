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
/*    */ public class SocksInitResponseDecoder
/*    */   extends ReplayingDecoder<SocksInitResponseDecoder.State>
/*    */ {
/*    */   private static final String name = "SOCKS_INIT_RESPONSE_DECODER";
/*    */   private SocksProtocolVersion version;
/*    */   private SocksAuthScheme authScheme;
/*    */   
/*    */   public static String getName() {
/* 32 */     return "SOCKS_INIT_RESPONSE_DECODER";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
/*    */   
/*    */   public SocksInitResponseDecoder() {
/* 41 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
/* 46 */     switch ((State)state()) {
/*    */       case CHECK_PROTOCOL_VERSION:
/* 48 */         this.version = SocksProtocolVersion.fromByte(byteBuf.readByte());
/* 49 */         if (this.version != SocksProtocolVersion.SOCKS5) {
/*    */           break;
/*    */         }
/* 52 */         checkpoint(State.READ_PREFFERED_AUTH_TYPE);
/*    */       
/*    */       case READ_PREFFERED_AUTH_TYPE:
/* 55 */         this.authScheme = SocksAuthScheme.fromByte(byteBuf.readByte());
/* 56 */         this.msg = new SocksInitResponse(this.authScheme);
/*    */         break;
/*    */     } 
/*    */     
/* 60 */     ctx.pipeline().remove((ChannelHandler)this);
/* 61 */     out.add(this.msg);
/*    */   }
/*    */   
/*    */   enum State {
/* 65 */     CHECK_PROTOCOL_VERSION,
/* 66 */     READ_PREFFERED_AUTH_TYPE;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksInitResponseDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */