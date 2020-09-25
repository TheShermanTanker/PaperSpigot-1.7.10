/*    */ package net.minecraft.util.io.netty.handler.codec.protobuf;
/*    */ 
/*    */ import com.google.protobuf.CodedOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBufOutputStream;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandler.Sharable;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder;
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
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public class ProtobufVarint32LengthFieldPrepender
/*    */   extends MessageToByteEncoder<ByteBuf>
/*    */ {
/*    */   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
/* 45 */     int bodyLen = msg.readableBytes();
/* 46 */     int headerLen = CodedOutputStream.computeRawVarint32Size(bodyLen);
/* 47 */     out.ensureWritable(headerLen + bodyLen);
/*    */     
/* 49 */     CodedOutputStream headerOut = CodedOutputStream.newInstance((OutputStream)new ByteBufOutputStream(out));
/*    */     
/* 51 */     headerOut.writeRawVarint32(bodyLen);
/* 52 */     headerOut.flush();
/*    */     
/* 54 */     out.writeBytes(msg, msg.readerIndex(), bodyLen);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\protobuf\ProtobufVarint32LengthFieldPrepender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */