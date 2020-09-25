/*    */ package net.minecraft.util.io.netty.handler.codec.socks;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.ReplayingDecoder;
/*    */ import net.minecraft.util.io.netty.util.CharsetUtil;
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
/*    */ public class SocksAuthRequestDecoder
/*    */   extends ReplayingDecoder<SocksAuthRequestDecoder.State>
/*    */ {
/*    */   private static final String name = "SOCKS_AUTH_REQUEST_DECODER";
/*    */   private SocksSubnegotiationVersion version;
/*    */   private int fieldLength;
/*    */   private String username;
/*    */   private String password;
/*    */   
/*    */   public static String getName() {
/* 33 */     return "SOCKS_AUTH_REQUEST_DECODER";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
/*    */   
/*    */   public SocksAuthRequestDecoder() {
/* 43 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
/* 48 */     switch ((State)state()) {
/*    */       case CHECK_PROTOCOL_VERSION:
/* 50 */         this.version = SocksSubnegotiationVersion.fromByte(byteBuf.readByte());
/* 51 */         if (this.version != SocksSubnegotiationVersion.AUTH_PASSWORD) {
/*    */           break;
/*    */         }
/* 54 */         checkpoint(State.READ_USERNAME);
/*    */       
/*    */       case READ_USERNAME:
/* 57 */         this.fieldLength = byteBuf.readByte();
/* 58 */         this.username = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/* 59 */         checkpoint(State.READ_PASSWORD);
/*    */       
/*    */       case READ_PASSWORD:
/* 62 */         this.fieldLength = byteBuf.readByte();
/* 63 */         this.password = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/* 64 */         this.msg = new SocksAuthRequest(this.username, this.password);
/*    */         break;
/*    */     } 
/* 67 */     ctx.pipeline().remove((ChannelHandler)this);
/* 68 */     out.add(this.msg);
/*    */   }
/*    */   
/*    */   enum State {
/* 72 */     CHECK_PROTOCOL_VERSION,
/* 73 */     READ_USERNAME,
/* 74 */     READ_PASSWORD;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksAuthRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */