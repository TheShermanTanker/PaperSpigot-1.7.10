/*     */ package net.minecraft.util.io.netty.handler.codec.marshalling;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.ReplayingDecoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.TooLongFrameException;
/*     */ import org.jboss.marshalling.ByteInput;
/*     */ import org.jboss.marshalling.Unmarshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompatibleMarshallingDecoder
/*     */   extends ReplayingDecoder<Void>
/*     */ {
/*     */   protected final UnmarshallerProvider provider;
/*     */   protected final int maxObjectSize;
/*     */   private boolean discardingTooLongFrame;
/*     */   
/*     */   public CompatibleMarshallingDecoder(UnmarshallerProvider provider, int maxObjectSize) {
/*  54 */     this.provider = provider;
/*  55 */     this.maxObjectSize = maxObjectSize;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
/*  60 */     if (this.discardingTooLongFrame) {
/*  61 */       buffer.skipBytes(actualReadableBytes());
/*  62 */       checkpoint();
/*     */       
/*     */       return;
/*     */     } 
/*  66 */     Unmarshaller unmarshaller = this.provider.getUnmarshaller(ctx);
/*  67 */     ByteInput input = new ChannelBufferByteInput(buffer);
/*  68 */     if (this.maxObjectSize != Integer.MAX_VALUE) {
/*  69 */       input = new LimitingByteInput(input, this.maxObjectSize);
/*     */     }
/*     */     try {
/*  72 */       unmarshaller.start(input);
/*  73 */       Object obj = unmarshaller.readObject();
/*  74 */       unmarshaller.finish();
/*  75 */       out.add(obj);
/*  76 */     } catch (TooBigObjectException e) {
/*  77 */       this.discardingTooLongFrame = true;
/*  78 */       throw new TooLongFrameException();
/*     */     }
/*     */     finally {
/*     */       
/*  82 */       unmarshaller.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
/*  88 */     switch (buffer.readableBytes()) {
/*     */       case 0:
/*     */         return;
/*     */       
/*     */       case 1:
/*  93 */         if (buffer.getByte(buffer.readerIndex()) == 121) {
/*  94 */           buffer.skipBytes(1);
/*     */           return;
/*     */         } 
/*     */         break;
/*     */     } 
/*  99 */     decode(ctx, buffer, out);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 104 */     if (cause instanceof TooLongFrameException) {
/* 105 */       ctx.close();
/*     */     } else {
/* 107 */       super.exceptionCaught(ctx, cause);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\marshalling\CompatibleMarshallingDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */