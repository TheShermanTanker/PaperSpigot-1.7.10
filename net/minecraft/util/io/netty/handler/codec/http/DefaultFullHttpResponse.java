/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
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
/*     */ public class DefaultFullHttpResponse
/*     */   extends DefaultHttpResponse
/*     */   implements FullHttpResponse
/*     */ {
/*     */   private final ByteBuf content;
/*  28 */   private final HttpHeaders trailingHeaders = new DefaultHttpHeaders();
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status) {
/*  31 */     this(version, status, Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, ByteBuf content) {
/*  35 */     super(version, status);
/*  36 */     if (content == null) {
/*  37 */       throw new NullPointerException("content");
/*     */     }
/*  39 */     this.content = content;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders trailingHeaders() {
/*  44 */     return this.trailingHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/*  49 */     return this.content;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/*  54 */     return this.content.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse retain() {
/*  59 */     this.content.retain();
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse retain(int increment) {
/*  65 */     this.content.retain(increment);
/*  66 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/*  71 */     return this.content.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/*  76 */     return this.content.release(decrement);
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse setProtocolVersion(HttpVersion version) {
/*  81 */     super.setProtocolVersion(version);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse setStatus(HttpResponseStatus status) {
/*  87 */     super.setStatus(status);
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse copy() {
/*  93 */     DefaultFullHttpResponse copy = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content().copy());
/*  94 */     copy.headers().set(headers());
/*  95 */     copy.trailingHeaders().set(trailingHeaders());
/*  96 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse duplicate() {
/* 101 */     DefaultFullHttpResponse duplicate = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content().duplicate());
/*     */     
/* 103 */     duplicate.headers().set(headers());
/* 104 */     duplicate.trailingHeaders().set(trailingHeaders());
/* 105 */     return duplicate;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\DefaultFullHttpResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */