/*    */ package net.minecraft.util.io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandler.Sharable;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder;
/*    */ import org.jboss.marshalling.Marshaller;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public class MarshallingEncoder
/*    */   extends MessageToByteEncoder<Object>
/*    */ {
/* 40 */   private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
/*    */ 
/*    */ 
/*    */   
/*    */   private final MarshallerProvider provider;
/*    */ 
/*    */ 
/*    */   
/*    */   public MarshallingEncoder(MarshallerProvider provider) {
/* 49 */     this.provider = provider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
/* 54 */     Marshaller marshaller = this.provider.getMarshaller(ctx);
/* 55 */     int lengthPos = out.writerIndex();
/* 56 */     out.writeBytes(LENGTH_PLACEHOLDER);
/* 57 */     ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
/* 58 */     marshaller.start(output);
/* 59 */     marshaller.writeObject(msg);
/* 60 */     marshaller.finish();
/* 61 */     marshaller.close();
/*    */     
/* 63 */     out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\marshalling\MarshallingEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */