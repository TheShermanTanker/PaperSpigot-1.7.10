/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.util.CharsetUtil;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCounted;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContinuationWebSocketFrame
/*     */   extends WebSocketFrame
/*     */ {
/*     */   private String aggregatedText;
/*     */   
/*     */   public ContinuationWebSocketFrame() {
/*  34 */     super(Unpooled.buffer(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContinuationWebSocketFrame(ByteBuf binaryData) {
/*  44 */     super(binaryData);
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
/*     */   
/*     */   public ContinuationWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
/*  58 */     super(finalFragment, rsv, binaryData);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContinuationWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData, String aggregatedText) {
/*  76 */     super(finalFragment, rsv, binaryData);
/*  77 */     this.aggregatedText = aggregatedText;
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
/*     */   
/*     */   public ContinuationWebSocketFrame(boolean finalFragment, int rsv, String text) {
/*  91 */     this(finalFragment, rsv, fromText(text), (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String text() {
/*  98 */     return content().toString(CharsetUtil.UTF_8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ByteBuf fromText(String text) {
/* 108 */     if (text == null || text.isEmpty()) {
/* 109 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 111 */     return Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String aggregatedText() {
/* 119 */     return this.aggregatedText;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContinuationWebSocketFrame copy() {
/* 124 */     return new ContinuationWebSocketFrame(isFinalFragment(), rsv(), content().copy(), aggregatedText());
/*     */   }
/*     */ 
/*     */   
/*     */   public ContinuationWebSocketFrame duplicate() {
/* 129 */     return new ContinuationWebSocketFrame(isFinalFragment(), rsv(), content().duplicate(), aggregatedText());
/*     */   }
/*     */ 
/*     */   
/*     */   public ContinuationWebSocketFrame retain() {
/* 134 */     super.retain();
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContinuationWebSocketFrame retain(int increment) {
/* 140 */     super.retain(increment);
/* 141 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\ContinuationWebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */