/*     */ package net.minecraft.util.io.netty.handler.codec;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.util.Signal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ReplayingDecoder<S>
/*     */   extends ByteToMessageDecoder
/*     */ {
/* 269 */   static final Signal REPLAY = new Signal(ReplayingDecoder.class.getName() + ".REPLAY");
/*     */   
/* 271 */   private final ReplayingDecoderBuffer replayable = new ReplayingDecoderBuffer();
/*     */   private S state;
/* 273 */   private int checkpoint = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ReplayingDecoder() {
/* 279 */     this((S)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ReplayingDecoder(S initialState) {
/* 286 */     this.state = initialState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkpoint() {
/* 293 */     this.checkpoint = internalBuffer().readerIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkpoint(S state) {
/* 301 */     checkpoint();
/* 302 */     state(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected S state() {
/* 310 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected S state(S newState) {
/* 318 */     S oldState = this.state;
/* 319 */     this.state = newState;
/* 320 */     return oldState;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 325 */     RecyclableArrayList out = RecyclableArrayList.newInstance();
/*     */     try {
/* 327 */       this.replayable.terminate();
/* 328 */       callDecode(ctx, internalBuffer(), (List<Object>)out);
/* 329 */       decodeLast(ctx, this.replayable, (List<Object>)out);
/* 330 */     } catch (Signal replay) {
/*     */       
/* 332 */       replay.expect(REPLAY);
/* 333 */     } catch (DecoderException e) {
/* 334 */       throw e;
/* 335 */     } catch (Exception e) {
/* 336 */       throw new DecoderException(e);
/*     */     } finally {
/* 338 */       if (this.cumulation != null) {
/* 339 */         this.cumulation.release();
/* 340 */         this.cumulation = null;
/*     */       } 
/*     */       
/* 343 */       int size = out.size();
/* 344 */       for (int i = 0; i < size; i++) {
/* 345 */         ctx.fireChannelRead(out.get(i));
/*     */       }
/* 347 */       ctx.fireChannelInactive();
/* 348 */       out.recycle();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
/* 354 */     this.replayable.setCumulation(in);
/*     */     try {
/* 356 */       while (in.isReadable()) {
/* 357 */         int oldReaderIndex = this.checkpoint = in.readerIndex();
/* 358 */         int outSize = out.size();
/* 359 */         S oldState = this.state;
/* 360 */         int oldInputLength = in.readableBytes();
/*     */         try {
/* 362 */           decode(ctx, this.replayable, out);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 368 */           if (ctx.isRemoved()) {
/*     */             break;
/*     */           }
/*     */           
/* 372 */           if (outSize == out.size()) {
/* 373 */             if (oldInputLength == in.readableBytes() && oldState == this.state) {
/* 374 */               throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() must consume the inbound " + "data or change its state if it did not decode anything.");
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/* 383 */         } catch (Signal replay) {
/* 384 */           replay.expect(REPLAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 390 */           if (ctx.isRemoved()) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 395 */           int checkpoint = this.checkpoint;
/* 396 */           if (checkpoint >= 0) {
/* 397 */             in.readerIndex(checkpoint);
/*     */           }
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */ 
/*     */         
/* 405 */         if (oldReaderIndex == in.readerIndex() && oldState == this.state) {
/* 406 */           throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() method must consume the inbound data " + "or change its state if it decoded something.");
/*     */         }
/*     */ 
/*     */         
/* 410 */         if (isSingleDecode()) {
/*     */           break;
/*     */         }
/*     */       } 
/* 414 */     } catch (DecoderException e) {
/* 415 */       throw e;
/* 416 */     } catch (Throwable cause) {
/* 417 */       throw new DecoderException(cause);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\ReplayingDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */