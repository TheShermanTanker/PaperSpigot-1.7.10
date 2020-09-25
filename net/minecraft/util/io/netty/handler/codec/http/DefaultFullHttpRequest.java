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
/*     */ public class DefaultFullHttpRequest
/*     */   extends DefaultHttpRequest
/*     */   implements FullHttpRequest
/*     */ {
/*     */   private final ByteBuf content;
/*  26 */   private final HttpHeaders trailingHeader = new DefaultHttpHeaders();
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri) {
/*  29 */     this(httpVersion, method, uri, Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content) {
/*  33 */     super(httpVersion, method, uri);
/*  34 */     if (content == null) {
/*  35 */       throw new NullPointerException("content");
/*     */     }
/*  37 */     this.content = content;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders trailingHeaders() {
/*  42 */     return this.trailingHeader;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/*  47 */     return this.content;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/*  52 */     return this.content.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest retain() {
/*  57 */     this.content.retain();
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest retain(int increment) {
/*  63 */     this.content.retain(increment);
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/*  69 */     return this.content.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/*  74 */     return this.content.release(decrement);
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest setProtocolVersion(HttpVersion version) {
/*  79 */     super.setProtocolVersion(version);
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest setMethod(HttpMethod method) {
/*  85 */     super.setMethod(method);
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest setUri(String uri) {
/*  91 */     super.setUri(uri);
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest copy() {
/*  97 */     DefaultFullHttpRequest copy = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().copy());
/*     */     
/*  99 */     copy.headers().set(headers());
/* 100 */     copy.trailingHeaders().set(trailingHeaders());
/* 101 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest duplicate() {
/* 106 */     DefaultFullHttpRequest duplicate = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().duplicate());
/*     */     
/* 108 */     duplicate.headers().set(headers());
/* 109 */     duplicate.trailingHeaders().set(trailingHeaders());
/* 110 */     return duplicate;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\DefaultFullHttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */