/*    */ package net.minecraft.util.io.netty.handler.codec.socks;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ public class SocksInitRequestDecoder
/*    */   extends ReplayingDecoder<SocksInitRequestDecoder.State>
/*    */ {
/*    */   private static final String name = "SOCKS_INIT_REQUEST_DECODER";
/*    */   
/*    */   public static String getName() {
/* 33 */     return "SOCKS_INIT_REQUEST_DECODER";
/*    */   }
/*    */   
/* 36 */   private final List<SocksAuthScheme> authSchemes = new ArrayList<SocksAuthScheme>();
/*    */   private SocksProtocolVersion version;
/*    */   private byte authSchemeNum;
/* 39 */   private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
/*    */   
/*    */   public SocksInitRequestDecoder() {
/* 42 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
/*    */     int i;
/* 47 */     switch ((State)state()) {
/*    */       case CHECK_PROTOCOL_VERSION:
/* 49 */         this.version = SocksProtocolVersion.fromByte(byteBuf.readByte());
/* 50 */         if (this.version != SocksProtocolVersion.SOCKS5) {
/*    */           break;
/*    */         }
/* 53 */         checkpoint(State.READ_AUTH_SCHEMES);
/*    */       
/*    */       case READ_AUTH_SCHEMES:
/* 56 */         this.authSchemes.clear();
/* 57 */         this.authSchemeNum = byteBuf.readByte();
/* 58 */         for (i = 0; i < this.authSchemeNum; i++) {
/* 59 */           this.authSchemes.add(SocksAuthScheme.fromByte(byteBuf.readByte()));
/*    */         }
/* 61 */         this.msg = new SocksInitRequest(this.authSchemes);
/*    */         break;
/*    */     } 
/*    */     
/* 65 */     ctx.pipeline().remove((ChannelHandler)this);
/* 66 */     out.add(this.msg);
/*    */   }
/*    */   
/*    */   enum State {
/* 70 */     CHECK_PROTOCOL_VERSION,
/* 71 */     READ_AUTH_SCHEMES;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksInitRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */