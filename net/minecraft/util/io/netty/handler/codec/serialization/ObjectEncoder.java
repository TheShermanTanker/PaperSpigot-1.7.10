/*    */ package net.minecraft.util.io.netty.handler.codec.serialization;
/*    */ 
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Serializable;
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
/*    */ @Sharable
/*    */ public class ObjectEncoder
/*    */   extends MessageToByteEncoder<Serializable>
/*    */ {
/* 38 */   private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
/* 42 */     int startIdx = out.writerIndex();
/*    */     
/* 44 */     ByteBufOutputStream bout = new ByteBufOutputStream(out);
/* 45 */     bout.write(LENGTH_PLACEHOLDER);
/* 46 */     ObjectOutputStream oout = new CompactObjectOutputStream((OutputStream)bout);
/* 47 */     oout.writeObject(msg);
/* 48 */     oout.flush();
/* 49 */     oout.close();
/*    */     
/* 51 */     int endIdx = out.writerIndex();
/*    */     
/* 53 */     out.setInt(startIdx, endIdx - startIdx - 4);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\serialization\ObjectEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */