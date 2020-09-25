/*    */ package net.minecraft.util.io.netty.handler.codec.protobuf;
/*    */ 
/*    */ import com.google.protobuf.CodedInputStream;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import net.minecraft.util.io.netty.handler.codec.CorruptedFrameException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProtobufVarint32FrameDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 49 */     in.markReaderIndex();
/* 50 */     byte[] buf = new byte[5];
/* 51 */     for (int i = 0; i < buf.length; i++) {
/* 52 */       if (!in.isReadable()) {
/* 53 */         in.resetReaderIndex();
/*    */         
/*    */         return;
/*    */       } 
/* 57 */       buf[i] = in.readByte();
/* 58 */       if (buf[i] >= 0) {
/* 59 */         int length = CodedInputStream.newInstance(buf, 0, i + 1).readRawVarint32();
/* 60 */         if (length < 0) {
/* 61 */           throw new CorruptedFrameException("negative length: " + length);
/*    */         }
/*    */         
/* 64 */         if (in.readableBytes() < length) {
/* 65 */           in.resetReaderIndex();
/*    */           return;
/*    */         } 
/* 68 */         out.add(in.readBytes(length));
/*    */ 
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/*    */     
/* 75 */     throw new CorruptedFrameException("length wider than 32-bit");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\protobuf\ProtobufVarint32FrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */