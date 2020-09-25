/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.FileRegion;
/*     */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import net.minecraft.util.io.netty.util.CharsetUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HttpObjectEncoder<H extends HttpMessage>
/*     */   extends MessageToMessageEncoder<Object>
/*     */ {
/*  44 */   private static final byte[] CRLF = new byte[] { 13, 10 };
/*  45 */   private static final byte[] ZERO_CRLF = new byte[] { 48, 13, 10 };
/*  46 */   private static final byte[] ZERO_CRLF_CRLF = new byte[] { 48, 13, 10, 13, 10 };
/*  47 */   private static final byte[] HEADER_SEPARATOR = new byte[] { 58, 32 };
/*  48 */   private static final ByteBuf CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(CRLF.length).writeBytes(CRLF));
/*  49 */   private static final ByteBuf ZERO_CRLF_CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(ZERO_CRLF_CRLF.length).writeBytes(ZERO_CRLF_CRLF));
/*     */   
/*     */   private static final int ST_INIT = 0;
/*     */   
/*     */   private static final int ST_CONTENT_NON_CHUNK = 1;
/*     */   
/*     */   private static final int ST_CONTENT_CHUNK = 2;
/*  56 */   private int state = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
/*  61 */     if (msg instanceof HttpMessage) {
/*  62 */       if (this.state != 0) {
/*  63 */         throw new IllegalStateException("unexpected message type: " + msg.getClass().getSimpleName());
/*     */       }
/*     */ 
/*     */       
/*  67 */       HttpMessage httpMessage = (HttpMessage)msg;
/*     */       
/*  69 */       ByteBuf buf = ctx.alloc().buffer();
/*     */       
/*  71 */       encodeInitialLine(buf, (H)httpMessage);
/*  72 */       encodeHeaders(buf, httpMessage.headers());
/*  73 */       buf.writeBytes(CRLF);
/*  74 */       out.add(buf);
/*  75 */       this.state = HttpHeaders.isTransferEncodingChunked(httpMessage) ? 2 : 1;
/*     */     } 
/*  77 */     if (msg instanceof HttpContent || msg instanceof ByteBuf || msg instanceof FileRegion) {
/*  78 */       if (this.state == 0) {
/*  79 */         throw new IllegalStateException("unexpected message type: " + msg.getClass().getSimpleName());
/*     */       }
/*     */       
/*  82 */       int contentLength = contentLength(msg);
/*  83 */       if (this.state == 1) {
/*  84 */         if (contentLength > 0) {
/*  85 */           out.add(encodeAndRetain(msg));
/*     */         }
/*     */         else {
/*     */           
/*  89 */           out.add(Unpooled.EMPTY_BUFFER);
/*     */         } 
/*     */         
/*  92 */         if (msg instanceof LastHttpContent) {
/*  93 */           this.state = 0;
/*     */         }
/*  95 */       } else if (this.state == 2) {
/*  96 */         encodeChunkedContent(ctx, msg, contentLength, out);
/*     */       } else {
/*  98 */         throw new Error();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void encodeChunkedContent(ChannelHandlerContext ctx, Object msg, int contentLength, List<Object> out) {
/* 104 */     if (contentLength > 0) {
/* 105 */       byte[] length = Integer.toHexString(contentLength).getBytes(CharsetUtil.US_ASCII);
/* 106 */       ByteBuf buf = ctx.alloc().buffer(length.length + 2);
/* 107 */       buf.writeBytes(length);
/* 108 */       buf.writeBytes(CRLF);
/* 109 */       out.add(buf);
/* 110 */       out.add(encodeAndRetain(msg));
/* 111 */       out.add(CRLF_BUF.duplicate());
/*     */     } 
/*     */     
/* 114 */     if (msg instanceof LastHttpContent) {
/* 115 */       HttpHeaders headers = ((LastHttpContent)msg).trailingHeaders();
/* 116 */       if (headers.isEmpty()) {
/* 117 */         out.add(ZERO_CRLF_CRLF_BUF.duplicate());
/*     */       } else {
/* 119 */         ByteBuf buf = ctx.alloc().buffer();
/* 120 */         buf.writeBytes(ZERO_CRLF);
/* 121 */         encodeHeaders(buf, headers);
/* 122 */         buf.writeBytes(CRLF);
/* 123 */         out.add(buf);
/*     */       } 
/*     */       
/* 126 */       this.state = 0;
/*     */     }
/* 128 */     else if (contentLength == 0) {
/*     */ 
/*     */       
/* 131 */       out.add(Unpooled.EMPTY_BUFFER);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 138 */     return (msg instanceof HttpObject || msg instanceof ByteBuf || msg instanceof FileRegion);
/*     */   }
/*     */   
/*     */   private static Object encodeAndRetain(Object msg) {
/* 142 */     if (msg instanceof ByteBuf) {
/* 143 */       return ((ByteBuf)msg).retain();
/*     */     }
/* 145 */     if (msg instanceof HttpContent) {
/* 146 */       return ((HttpContent)msg).content().retain();
/*     */     }
/* 148 */     if (msg instanceof FileRegion) {
/* 149 */       return ((FileRegion)msg).retain();
/*     */     }
/* 151 */     throw new IllegalStateException("unexpected message type: " + msg.getClass().getSimpleName());
/*     */   }
/*     */   
/*     */   private static int contentLength(Object msg) {
/* 155 */     if (msg instanceof HttpContent) {
/* 156 */       return ((HttpContent)msg).content().readableBytes();
/*     */     }
/* 158 */     if (msg instanceof ByteBuf) {
/* 159 */       return ((ByteBuf)msg).readableBytes();
/*     */     }
/* 161 */     if (msg instanceof FileRegion) {
/* 162 */       return (int)((FileRegion)msg).count();
/*     */     }
/* 164 */     throw new IllegalStateException("unexpected message type: " + msg.getClass().getSimpleName());
/*     */   }
/*     */   
/*     */   private static void encodeHeaders(ByteBuf buf, HttpHeaders headers) {
/* 168 */     for (Map.Entry<String, String> h : (Iterable<Map.Entry<String, String>>)headers) {
/* 169 */       encodeHeader(buf, h.getKey(), h.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void encodeHeader(ByteBuf buf, String header, String value) {
/* 174 */     encodeAscii(header, buf);
/* 175 */     buf.writeBytes(HEADER_SEPARATOR);
/* 176 */     encodeAscii(value, buf);
/* 177 */     buf.writeBytes(CRLF);
/*     */   }
/*     */   
/*     */   protected static void encodeAscii(String s, ByteBuf buf) {
/* 181 */     for (int i = 0; i < s.length(); i++)
/* 182 */       buf.writeByte(s.charAt(i)); 
/*     */   }
/*     */   
/*     */   protected abstract void encodeInitialLine(ByteBuf paramByteBuf, H paramH) throws Exception;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpObjectEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */