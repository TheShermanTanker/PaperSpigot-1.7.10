/*     */ package net.minecraft.util.io.netty.handler.codec;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import net.minecraft.util.io.netty.util.internal.RecyclableArrayList;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*     */ public abstract class ByteToMessageDecoder
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   ByteBuf cumulation;
/*     */   private boolean singleDecode;
/*     */   private boolean decodeWasNull;
/*     */   
/*     */   protected ByteToMessageDecoder() {
/*  54 */     if (getClass().isAnnotationPresent((Class)ChannelHandler.Sharable.class)) {
/*  55 */       throw new IllegalStateException("@Sharable annotation is not allowed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSingleDecode(boolean singleDecode) {
/*  66 */     this.singleDecode = singleDecode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSingleDecode() {
/*  76 */     return this.singleDecode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int actualReadableBytes() {
/*  86 */     return internalBuffer().readableBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBuf internalBuffer() {
/*  95 */     if (this.cumulation != null) {
/*  96 */       return this.cumulation;
/*     */     }
/*  98 */     return Unpooled.EMPTY_BUFFER;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 104 */     ByteBuf buf = internalBuffer();
/* 105 */     int readable = buf.readableBytes();
/* 106 */     if (buf.isReadable()) {
/* 107 */       ByteBuf bytes = buf.readBytes(readable);
/* 108 */       buf.release();
/* 109 */       ctx.fireChannelRead(bytes);
/*     */     } 
/* 111 */     this.cumulation = null;
/* 112 */     ctx.fireChannelReadComplete();
/* 113 */     handlerRemoved0(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 124 */     RecyclableArrayList out = RecyclableArrayList.newInstance();
/*     */     try {
/* 126 */       if (msg instanceof ByteBuf) {
/* 127 */         ByteBuf data = (ByteBuf)msg;
/* 128 */         if (this.cumulation == null) {
/* 129 */           this.cumulation = data;
/*     */           try {
/* 131 */             callDecode(ctx, this.cumulation, (List<Object>)out);
/*     */           } finally {
/* 133 */             if (this.cumulation != null && !this.cumulation.isReadable()) {
/* 134 */               this.cumulation.release();
/* 135 */               this.cumulation = null;
/*     */             } 
/*     */           } 
/*     */         } else {
/*     */           try {
/* 140 */             if (this.cumulation.writerIndex() > this.cumulation.maxCapacity() - data.readableBytes()) {
/* 141 */               ByteBuf oldCumulation = this.cumulation;
/* 142 */               this.cumulation = ctx.alloc().buffer(oldCumulation.readableBytes() + data.readableBytes());
/* 143 */               this.cumulation.writeBytes(oldCumulation);
/* 144 */               oldCumulation.release();
/*     */             } 
/* 146 */             this.cumulation.writeBytes(data);
/* 147 */             callDecode(ctx, this.cumulation, (List<Object>)out);
/*     */           } finally {
/* 149 */             if (this.cumulation != null) {
/* 150 */               if (!this.cumulation.isReadable()) {
/* 151 */                 this.cumulation.release();
/* 152 */                 this.cumulation = null;
/*     */               } else {
/* 154 */                 this.cumulation.discardSomeReadBytes();
/*     */               } 
/*     */             }
/* 157 */             data.release();
/*     */           } 
/*     */         } 
/*     */       } else {
/* 161 */         out.add(msg);
/*     */       } 
/* 163 */     } catch (DecoderException e) {
/* 164 */       throw e;
/* 165 */     } catch (Throwable t) {
/* 166 */       throw new DecoderException(t);
/*     */     } finally {
/* 168 */       int size = out.size();
/* 169 */       if (size == 0) {
/* 170 */         this.decodeWasNull = true;
/*     */       } else {
/* 172 */         for (int i = 0; i < size; i++) {
/* 173 */           ctx.fireChannelRead(out.get(i));
/*     */         }
/*     */       } 
/* 176 */       out.recycle();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 182 */     if (this.decodeWasNull) {
/* 183 */       this.decodeWasNull = false;
/* 184 */       if (!ctx.channel().config().isAutoRead()) {
/* 185 */         ctx.read();
/*     */       }
/*     */     } 
/* 188 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 193 */     RecyclableArrayList out = RecyclableArrayList.newInstance();
/*     */     try {
/* 195 */       if (this.cumulation != null) {
/* 196 */         callDecode(ctx, this.cumulation, (List<Object>)out);
/* 197 */         decodeLast(ctx, this.cumulation, (List<Object>)out);
/*     */       } else {
/* 199 */         decodeLast(ctx, Unpooled.EMPTY_BUFFER, (List<Object>)out);
/*     */       } 
/* 201 */     } catch (DecoderException e) {
/* 202 */       throw e;
/* 203 */     } catch (Exception e) {
/* 204 */       throw new DecoderException(e);
/*     */     } finally {
/* 206 */       if (this.cumulation != null) {
/* 207 */         this.cumulation.release();
/* 208 */         this.cumulation = null;
/*     */       } 
/* 210 */       int size = out.size();
/* 211 */       for (int i = 0; i < size; i++) {
/* 212 */         ctx.fireChannelRead(out.get(i));
/*     */       }
/* 214 */       ctx.fireChannelInactive();
/* 215 */       out.recycle();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
/*     */     try {
/* 229 */       while (in.isReadable()) {
/* 230 */         int outSize = out.size();
/* 231 */         int oldInputLength = in.readableBytes();
/* 232 */         decode(ctx, in, out);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 238 */         if (ctx.isRemoved()) {
/*     */           break;
/*     */         }
/*     */         
/* 242 */         if (outSize == out.size()) {
/* 243 */           if (oldInputLength == in.readableBytes()) {
/*     */             break;
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 250 */         if (oldInputLength == in.readableBytes()) {
/* 251 */           throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() did not read anything but decoded a message.");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 256 */         if (isSingleDecode()) {
/*     */           break;
/*     */         }
/*     */       } 
/* 260 */     } catch (DecoderException e) {
/* 261 */       throw e;
/* 262 */     } catch (Throwable cause) {
/* 263 */       throw new DecoderException(cause);
/*     */     } 
/*     */   }
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
/*     */   protected abstract void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception;
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
/*     */   protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 288 */     decode(ctx, in, out);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\ByteToMessageDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */