/*    */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
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
/*    */ public class SpdyHeaderBlockRawEncoder
/*    */   extends SpdyHeaderBlockEncoder
/*    */ {
/*    */   private final int version;
/*    */   
/*    */   public SpdyHeaderBlockRawEncoder(int version) {
/* 31 */     if (version < 2 || version > 3) {
/* 32 */       throw new IllegalArgumentException("unknown version: " + version);
/*    */     }
/*    */     
/* 35 */     this.version = version;
/*    */   }
/*    */   
/*    */   private void setLengthField(ByteBuf buffer, int writerIndex, int length) {
/* 39 */     if (this.version < 3) {
/* 40 */       buffer.setShort(writerIndex, length);
/*    */     } else {
/* 42 */       buffer.setInt(writerIndex, length);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void writeLengthField(ByteBuf buffer, int length) {
/* 47 */     if (this.version < 3) {
/* 48 */       buffer.writeShort(length);
/*    */     } else {
/* 50 */       buffer.writeInt(length);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf encode(ChannelHandlerContext ctx, SpdyHeadersFrame frame) throws Exception {
/* 56 */     Set<String> names = frame.headers().names();
/* 57 */     int numHeaders = names.size();
/* 58 */     if (numHeaders == 0) {
/* 59 */       return Unpooled.EMPTY_BUFFER;
/*    */     }
/* 61 */     if (numHeaders > 65535) {
/* 62 */       throw new IllegalArgumentException("header block contains too many headers");
/*    */     }
/*    */     
/* 65 */     ByteBuf headerBlock = Unpooled.buffer();
/* 66 */     writeLengthField(headerBlock, numHeaders);
/* 67 */     for (String name : names) {
/* 68 */       byte[] nameBytes = name.getBytes("UTF-8");
/* 69 */       writeLengthField(headerBlock, nameBytes.length);
/* 70 */       headerBlock.writeBytes(nameBytes);
/* 71 */       int savedIndex = headerBlock.writerIndex();
/* 72 */       int valueLength = 0;
/* 73 */       writeLengthField(headerBlock, valueLength);
/* 74 */       for (String value : frame.headers().getAll(name)) {
/* 75 */         byte[] valueBytes = value.getBytes("UTF-8");
/* 76 */         if (valueBytes.length > 0) {
/* 77 */           headerBlock.writeBytes(valueBytes);
/* 78 */           headerBlock.writeByte(0);
/* 79 */           valueLength += valueBytes.length + 1;
/*    */         } 
/*    */       } 
/* 82 */       if (valueLength == 0) {
/* 83 */         if (this.version < 3) {
/* 84 */           throw new IllegalArgumentException("header value cannot be empty: " + name);
/*    */         }
/*    */       } else {
/*    */         
/* 88 */         valueLength--;
/*    */       } 
/* 90 */       if (valueLength > 65535) {
/* 91 */         throw new IllegalArgumentException("header exceeds allowable length: " + name);
/*    */       }
/*    */       
/* 94 */       if (valueLength > 0) {
/* 95 */         setLengthField(headerBlock, savedIndex, valueLength);
/* 96 */         headerBlock.writerIndex(headerBlock.writerIndex() - 1);
/*    */       } 
/*    */     } 
/* 99 */     return headerBlock;
/*    */   }
/*    */   
/*    */   void end() {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyHeaderBlockRawEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */