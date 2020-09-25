/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.embedded.EmbeddedChannel;
/*     */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageCodec;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HttpContentEncoder
/*     */   extends MessageToMessageCodec<HttpRequest, HttpObject>
/*     */ {
/*     */   private enum State
/*     */   {
/*  56 */     PASS_THROUGH,
/*  57 */     AWAIT_HEADERS,
/*  58 */     AWAIT_CONTENT;
/*     */   }
/*     */   
/*  61 */   private final Queue<String> acceptEncodingQueue = new ArrayDeque<String>();
/*     */   private String acceptEncoding;
/*     */   private EmbeddedChannel encoder;
/*  64 */   private State state = State.AWAIT_HEADERS;
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/*  68 */     return (msg instanceof HttpContent || msg instanceof HttpResponse);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, HttpRequest msg, List<Object> out) throws Exception {
/*  74 */     String acceptedEncoding = msg.headers().get("Accept-Encoding");
/*  75 */     if (acceptedEncoding == null) {
/*  76 */       acceptedEncoding = "identity";
/*     */     }
/*  78 */     this.acceptEncodingQueue.add(acceptedEncoding);
/*  79 */     out.add(ReferenceCountUtil.retain(msg));
/*     */   }
/*     */   protected void encode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/*     */     HttpResponse res;
/*     */     Result result;
/*  84 */     boolean isFull = (msg instanceof HttpResponse && msg instanceof LastHttpContent);
/*  85 */     switch (this.state) {
/*     */       case AWAIT_HEADERS:
/*  87 */         ensureHeaders(msg);
/*  88 */         assert this.encoder == null;
/*     */         
/*  90 */         res = (HttpResponse)msg;
/*     */         
/*  92 */         if (res.getStatus().code() == 100) {
/*  93 */           if (isFull) {
/*  94 */             out.add(ReferenceCountUtil.retain(res)); break;
/*     */           } 
/*  96 */           out.add(res);
/*     */           
/*  98 */           this.state = State.PASS_THROUGH;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 104 */         this.acceptEncoding = this.acceptEncodingQueue.poll();
/* 105 */         if (this.acceptEncoding == null) {
/* 106 */           throw new IllegalStateException("cannot send more responses than requests");
/*     */         }
/*     */         
/* 109 */         if (isFull)
/*     */         {
/* 111 */           if (!((ByteBufHolder)res).content().isReadable()) {
/* 112 */             out.add(ReferenceCountUtil.retain(res));
/*     */             
/*     */             break;
/*     */           } 
/*     */         }
/*     */         
/* 118 */         result = beginEncode(res, this.acceptEncoding);
/*     */ 
/*     */         
/* 121 */         if (result == null) {
/* 122 */           if (isFull) {
/* 123 */             out.add(ReferenceCountUtil.retain(res)); break;
/*     */           } 
/* 125 */           out.add(res);
/*     */           
/* 127 */           this.state = State.PASS_THROUGH;
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 132 */         this.encoder = result.contentEncoder();
/*     */ 
/*     */ 
/*     */         
/* 136 */         res.headers().set("Content-Encoding", result.targetContentEncoding());
/*     */ 
/*     */         
/* 139 */         res.headers().remove("Content-Length");
/* 140 */         res.headers().set("Transfer-Encoding", "chunked");
/*     */ 
/*     */         
/* 143 */         if (isFull) {
/*     */           
/* 145 */           HttpResponse newRes = new DefaultHttpResponse(res.getProtocolVersion(), res.getStatus());
/* 146 */           newRes.headers().set(res.headers());
/* 147 */           out.add(newRes);
/*     */         } else {
/*     */           
/* 150 */           out.add(res);
/* 151 */           this.state = State.AWAIT_CONTENT;
/*     */           break;
/*     */         } 
/*     */       
/*     */       case AWAIT_CONTENT:
/* 156 */         ensureContent(msg);
/* 157 */         if (encodeContent((HttpContent)msg, out)) {
/* 158 */           this.state = State.AWAIT_HEADERS;
/*     */         }
/*     */         break;
/*     */       
/*     */       case PASS_THROUGH:
/* 163 */         ensureContent(msg);
/* 164 */         out.add(ReferenceCountUtil.retain(msg));
/*     */         
/* 166 */         if (msg instanceof LastHttpContent) {
/* 167 */           this.state = State.AWAIT_HEADERS;
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void ensureHeaders(HttpObject msg) {
/* 175 */     if (!(msg instanceof HttpResponse)) {
/* 176 */       throw new IllegalStateException("unexpected message type: " + msg.getClass().getName() + " (expected: " + HttpResponse.class.getSimpleName() + ')');
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void ensureContent(HttpObject msg) {
/* 183 */     if (!(msg instanceof HttpContent)) {
/* 184 */       throw new IllegalStateException("unexpected message type: " + msg.getClass().getName() + " (expected: " + HttpContent.class.getSimpleName() + ')');
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean encodeContent(HttpContent c, List<Object> out) {
/* 191 */     ByteBuf content = c.content();
/*     */     
/* 193 */     encode(content, out);
/*     */     
/* 195 */     if (c instanceof LastHttpContent) {
/* 196 */       finishEncode(out);
/* 197 */       LastHttpContent last = (LastHttpContent)c;
/*     */ 
/*     */ 
/*     */       
/* 201 */       HttpHeaders headers = last.trailingHeaders();
/* 202 */       if (headers.isEmpty()) {
/* 203 */         out.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*     */       } else {
/* 205 */         out.add(new ComposedLastHttpContent(headers));
/*     */       } 
/* 207 */       return true;
/*     */     } 
/* 209 */     return false;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 230 */     cleanup();
/* 231 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 236 */     cleanup();
/* 237 */     super.channelInactive(ctx);
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 241 */     if (this.encoder != null) {
/*     */       
/* 243 */       if (this.encoder.finish()) {
/*     */         while (true) {
/* 245 */           ByteBuf buf = (ByteBuf)this.encoder.readOutbound();
/* 246 */           if (buf == null) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 251 */           buf.release();
/*     */         } 
/*     */       }
/* 254 */       this.encoder = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void encode(ByteBuf in, List<Object> out) {
/* 260 */     this.encoder.writeOutbound(new Object[] { in.retain() });
/* 261 */     fetchEncoderOutput(out);
/*     */   }
/*     */   
/*     */   private void finishEncode(List<Object> out) {
/* 265 */     if (this.encoder.finish()) {
/* 266 */       fetchEncoderOutput(out);
/*     */     }
/* 268 */     this.encoder = null;
/*     */   }
/*     */   
/*     */   private void fetchEncoderOutput(List<Object> out) {
/*     */     while (true) {
/* 273 */       ByteBuf buf = (ByteBuf)this.encoder.readOutbound();
/* 274 */       if (buf == null) {
/*     */         break;
/*     */       }
/* 277 */       if (!buf.isReadable()) {
/* 278 */         buf.release();
/*     */         continue;
/*     */       } 
/* 281 */       out.add(new DefaultHttpContent(buf));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract Result beginEncode(HttpResponse paramHttpResponse, String paramString) throws Exception;
/*     */   
/*     */   public static final class Result { private final String targetContentEncoding;
/*     */     
/*     */     public Result(String targetContentEncoding, EmbeddedChannel contentEncoder) {
/* 290 */       if (targetContentEncoding == null) {
/* 291 */         throw new NullPointerException("targetContentEncoding");
/*     */       }
/* 293 */       if (contentEncoder == null) {
/* 294 */         throw new NullPointerException("contentEncoder");
/*     */       }
/*     */       
/* 297 */       this.targetContentEncoding = targetContentEncoding;
/* 298 */       this.contentEncoder = contentEncoder;
/*     */     }
/*     */     private final EmbeddedChannel contentEncoder;
/*     */     public String targetContentEncoding() {
/* 302 */       return this.targetContentEncoding;
/*     */     }
/*     */     
/*     */     public EmbeddedChannel contentEncoder() {
/* 306 */       return this.contentEncoder;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpContentEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */