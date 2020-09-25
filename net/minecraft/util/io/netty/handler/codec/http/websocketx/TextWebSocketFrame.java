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
/*     */ public class TextWebSocketFrame
/*     */   extends WebSocketFrame
/*     */ {
/*     */   public TextWebSocketFrame() {
/*  31 */     super(Unpooled.buffer(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame(String text) {
/*  41 */     super(fromText(text));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame(ByteBuf binaryData) {
/*  51 */     super(binaryData);
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
/*     */   public TextWebSocketFrame(boolean finalFragment, int rsv, String text) {
/*  65 */     super(finalFragment, rsv, fromText(text));
/*     */   }
/*     */   
/*     */   private static ByteBuf fromText(String text) {
/*  69 */     if (text == null || text.isEmpty()) {
/*  70 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*  72 */     return Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);
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
/*     */   public TextWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
/*  87 */     super(finalFragment, rsv, binaryData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String text() {
/*  94 */     return content().toString(CharsetUtil.UTF_8);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame copy() {
/*  99 */     return new TextWebSocketFrame(isFinalFragment(), rsv(), content().copy());
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame duplicate() {
/* 104 */     return new TextWebSocketFrame(isFinalFragment(), rsv(), content().duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame retain() {
/* 109 */     super.retain();
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextWebSocketFrame retain(int increment) {
/* 115 */     super.retain(increment);
/* 116 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\TextWebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */