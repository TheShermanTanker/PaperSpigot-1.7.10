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
/*    */ public class SocksCmdRequestDecoder
/*    */   extends ReplayingDecoder<SocksCmdRequestDecoder.State>
/*    */ {
/*    */   private static final String name = "SOCKS_CMD_REQUEST_DECODER";
/*    */   private SocksProtocolVersion version;
/*    */   private int fieldLength;
/*    */   private SocksCmdType cmdType;
/*    */   private SocksAddressType addressType;
/*    */   private byte reserved;
/*    */   private String host;
/*    */   private int port;
/*    */   
/*    */   public static String getName() {
/* 33 */     return "SOCKS_CMD_REQUEST_DECODER";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
/*    */   
/*    */   public SocksCmdRequestDecoder() {
/* 46 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
/* 51 */     switch ((State)state()) {
/*    */       case CHECK_PROTOCOL_VERSION:
/* 53 */         this.version = SocksProtocolVersion.fromByte(byteBuf.readByte());
/* 54 */         if (this.version != SocksProtocolVersion.SOCKS5) {
/*    */           break;
/*    */         }
/* 57 */         checkpoint(State.READ_CMD_HEADER);
/*    */       
/*    */       case READ_CMD_HEADER:
/* 60 */         this.cmdType = SocksCmdType.fromByte(byteBuf.readByte());
/* 61 */         this.reserved = byteBuf.readByte();
/* 62 */         this.addressType = SocksAddressType.fromByte(byteBuf.readByte());
/* 63 */         checkpoint(State.READ_CMD_ADDRESS);
/*    */       
/*    */       case READ_CMD_ADDRESS:
/* 66 */         switch (this.addressType) {
/*    */           case CHECK_PROTOCOL_VERSION:
/* 68 */             this.host = SocksCommonUtils.intToIp(byteBuf.readInt());
/* 69 */             this.port = byteBuf.readUnsignedShort();
/* 70 */             this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
/*    */             break;
/*    */           
/*    */           case READ_CMD_HEADER:
/* 74 */             this.fieldLength = byteBuf.readByte();
/* 75 */             this.host = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/* 76 */             this.port = byteBuf.readUnsignedShort();
/* 77 */             this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
/*    */             break;
/*    */           
/*    */           case READ_CMD_ADDRESS:
/* 81 */             this.host = SocksCommonUtils.ipv6toStr(byteBuf.readBytes(16).array());
/* 82 */             this.port = byteBuf.readUnsignedShort();
/* 83 */             this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
/*    */             break;
/*    */         } 
/*    */ 
/*    */         
/*    */         break;
/*    */     } 
/*    */     
/* 91 */     ctx.pipeline().remove((ChannelHandler)this);
/* 92 */     out.add(this.msg);
/*    */   }
/*    */   
/*    */   enum State {
/* 96 */     CHECK_PROTOCOL_VERSION,
/* 97 */     READ_CMD_HEADER,
/* 98 */     READ_CMD_ADDRESS;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksCmdRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */