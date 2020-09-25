/*    */ package net.minecraft.util.io.netty.handler.codec.sctp;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.channel.sctp.SctpMessage;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageEncoder;
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
/*    */ 
/*    */ public class SctpOutboundByteStreamHandler
/*    */   extends MessageToMessageEncoder<ByteBuf>
/*    */ {
/*    */   private final int streamIdentifier;
/*    */   private final int protocolIdentifier;
/*    */   
/*    */   public SctpOutboundByteStreamHandler(int streamIdentifier, int protocolIdentifier) {
/* 39 */     this.streamIdentifier = streamIdentifier;
/* 40 */     this.protocolIdentifier = protocolIdentifier;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
/* 45 */     out.add(new SctpMessage(this.streamIdentifier, this.protocolIdentifier, msg.retain()));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\sctp\SctpOutboundByteStreamHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */