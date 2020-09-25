/*    */ package net.minecraft.util.io.netty.handler.codec.sctp;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.channel.sctp.SctpMessage;
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
/*    */ public class SctpMessageCompletionHandler
/*    */   extends MessageToMessageDecoder<SctpMessage>
/*    */ {
/* 36 */   private final Map<Integer, ByteBuf> fragments = new HashMap<Integer, ByteBuf>();
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, SctpMessage msg, List<Object> out) throws Exception {
/* 40 */     ByteBuf frag, byteBuf = msg.content();
/* 41 */     int protocolIdentifier = msg.protocolIdentifier();
/* 42 */     int streamIdentifier = msg.streamIdentifier();
/* 43 */     boolean isComplete = msg.isComplete();
/*    */ 
/*    */     
/* 46 */     if (this.fragments.containsKey(Integer.valueOf(streamIdentifier))) {
/* 47 */       frag = this.fragments.remove(Integer.valueOf(streamIdentifier));
/*    */     } else {
/* 49 */       frag = Unpooled.EMPTY_BUFFER;
/*    */     } 
/*    */     
/* 52 */     if (isComplete && !frag.isReadable()) {
/*    */       
/* 54 */       out.add(msg);
/* 55 */     } else if (!isComplete && frag.isReadable()) {
/*    */       
/* 57 */       this.fragments.put(Integer.valueOf(streamIdentifier), Unpooled.wrappedBuffer(new ByteBuf[] { frag, byteBuf }));
/* 58 */     } else if (isComplete && frag.isReadable()) {
/*    */       
/* 60 */       this.fragments.remove(Integer.valueOf(streamIdentifier));
/* 61 */       SctpMessage assembledMsg = new SctpMessage(protocolIdentifier, streamIdentifier, Unpooled.wrappedBuffer(new ByteBuf[] { frag, byteBuf }));
/*    */ 
/*    */ 
/*    */       
/* 65 */       out.add(assembledMsg);
/*    */     } else {
/*    */       
/* 68 */       this.fragments.put(Integer.valueOf(streamIdentifier), byteBuf);
/*    */     } 
/* 70 */     byteBuf.retain();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\sctp\SctpMessageCompletionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */