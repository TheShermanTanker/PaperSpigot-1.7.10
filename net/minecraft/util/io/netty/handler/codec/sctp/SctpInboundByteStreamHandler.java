/*    */ package net.minecraft.util.io.netty.handler.codec.sctp;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.channel.sctp.SctpMessage;
/*    */ import net.minecraft.util.io.netty.handler.codec.CodecException;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageDecoder;
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
/*    */ 
/*    */ public class SctpInboundByteStreamHandler
/*    */   extends MessageToMessageDecoder<SctpMessage>
/*    */ {
/*    */   private final int protocolIdentifier;
/*    */   private final int streamIdentifier;
/*    */   
/*    */   public SctpInboundByteStreamHandler(int protocolIdentifier, int streamIdentifier) {
/* 40 */     this.protocolIdentifier = protocolIdentifier;
/* 41 */     this.streamIdentifier = streamIdentifier;
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean acceptInboundMessage(Object msg) throws Exception {
/* 46 */     if (super.acceptInboundMessage(msg)) {
/* 47 */       return acceptInboundMessage((SctpMessage)msg);
/*    */     }
/* 49 */     return false;
/*    */   }
/*    */   
/*    */   protected boolean acceptInboundMessage(SctpMessage msg) {
/* 53 */     return (msg.protocolIdentifier() == this.protocolIdentifier && msg.streamIdentifier() == this.streamIdentifier);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, SctpMessage msg, List<Object> out) throws Exception {
/* 58 */     if (!msg.isComplete()) {
/* 59 */       throw new CodecException(String.format("Received SctpMessage is not complete, please add %s in the pipeline before this handler", new Object[] { SctpMessageCompletionHandler.class.getSimpleName() }));
/*    */     }
/*    */     
/* 62 */     out.add(msg.content().retain());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\sctp\SctpInboundByteStreamHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */