/*    */ package net.minecraft.util.io.netty.handler.codec.base64;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandler.Sharable;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
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
/*    */ public class Base64Encoder
/*    */   extends MessageToMessageEncoder<ByteBuf>
/*    */ {
/*    */   private final boolean breakLines;
/*    */   private final Base64Dialect dialect;
/*    */   
/*    */   public Base64Encoder() {
/* 49 */     this(true);
/*    */   }
/*    */   
/*    */   public Base64Encoder(boolean breakLines) {
/* 53 */     this(breakLines, Base64Dialect.STANDARD);
/*    */   }
/*    */   
/*    */   public Base64Encoder(boolean breakLines, Base64Dialect dialect) {
/* 57 */     if (dialect == null) {
/* 58 */       throw new NullPointerException("dialect");
/*    */     }
/*    */     
/* 61 */     this.breakLines = breakLines;
/* 62 */     this.dialect = dialect;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
/* 67 */     out.add(Base64.encode(msg, msg.readerIndex(), msg.readableBytes(), this.breakLines, this.dialect));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\base64\Base64Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */