/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.UnsupportedMessageTypeException;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpMessage;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpContent;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpHeaders;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpMessage;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpObject;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpRequest;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.LastHttpContent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpdyHttpEncoder
/*     */   extends MessageToMessageEncoder<HttpObject>
/*     */ {
/*     */   private final int spdyVersion;
/*     */   private int currentStreamId;
/*     */   
/*     */   public SpdyHttpEncoder(int version) {
/* 134 */     if (version < 2 || version > 3) {
/* 135 */       throw new IllegalArgumentException("unsupported version: " + version);
/*     */     }
/*     */     
/* 138 */     this.spdyVersion = version;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/* 144 */     boolean valid = false;
/* 145 */     boolean last = false;
/*     */     
/* 147 */     if (msg instanceof HttpRequest) {
/*     */       
/* 149 */       HttpRequest httpRequest = (HttpRequest)msg;
/* 150 */       SpdySynStreamFrame spdySynStreamFrame = createSynStreamFrame((HttpMessage)httpRequest);
/* 151 */       out.add(spdySynStreamFrame);
/*     */       
/* 153 */       last = spdySynStreamFrame.isLast();
/* 154 */       valid = true;
/*     */     } 
/* 156 */     if (msg instanceof HttpResponse) {
/*     */       
/* 158 */       HttpResponse httpResponse = (HttpResponse)msg;
/* 159 */       if (httpResponse.headers().contains("X-SPDY-Associated-To-Stream-ID")) {
/* 160 */         SpdySynStreamFrame spdySynStreamFrame = createSynStreamFrame((HttpMessage)httpResponse);
/* 161 */         last = spdySynStreamFrame.isLast();
/* 162 */         out.add(spdySynStreamFrame);
/*     */       } else {
/* 164 */         SpdySynReplyFrame spdySynReplyFrame = createSynReplyFrame(httpResponse);
/* 165 */         last = spdySynReplyFrame.isLast();
/* 166 */         out.add(spdySynReplyFrame);
/*     */       } 
/*     */       
/* 169 */       valid = true;
/*     */     } 
/* 171 */     if (msg instanceof HttpContent && !last) {
/*     */       
/* 173 */       HttpContent chunk = (HttpContent)msg;
/*     */       
/* 175 */       chunk.content().retain();
/* 176 */       SpdyDataFrame spdyDataFrame = new DefaultSpdyDataFrame(this.currentStreamId, chunk.content());
/* 177 */       spdyDataFrame.setLast(chunk instanceof LastHttpContent);
/* 178 */       if (chunk instanceof LastHttpContent) {
/* 179 */         LastHttpContent trailer = (LastHttpContent)chunk;
/* 180 */         List<Map.Entry<String, String>> trailers = trailer.trailingHeaders().entries();
/* 181 */         if (trailers.isEmpty()) {
/* 182 */           out.add(spdyDataFrame);
/*     */         } else {
/*     */           
/* 185 */           SpdyHeadersFrame spdyHeadersFrame = new DefaultSpdyHeadersFrame(this.currentStreamId);
/* 186 */           for (Map.Entry<String, String> entry : trailers) {
/* 187 */             spdyHeadersFrame.headers().add(entry.getKey(), entry.getValue());
/*     */           }
/*     */ 
/*     */           
/* 191 */           out.add(spdyHeadersFrame);
/* 192 */           out.add(spdyDataFrame);
/*     */         } 
/*     */       } else {
/* 195 */         out.add(spdyDataFrame);
/*     */       } 
/*     */       
/* 198 */       valid = true;
/*     */     } 
/*     */     
/* 201 */     if (!valid) {
/* 202 */       throw new UnsupportedMessageTypeException(msg, new Class[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SpdySynStreamFrame createSynStreamFrame(HttpMessage httpMessage) throws Exception {
/* 209 */     int streamID = SpdyHttpHeaders.getStreamId(httpMessage);
/* 210 */     int associatedToStreamId = SpdyHttpHeaders.getAssociatedToStreamId(httpMessage);
/* 211 */     byte priority = SpdyHttpHeaders.getPriority(httpMessage);
/* 212 */     String URL = SpdyHttpHeaders.getUrl(httpMessage);
/* 213 */     String scheme = SpdyHttpHeaders.getScheme(httpMessage);
/* 214 */     SpdyHttpHeaders.removeStreamId(httpMessage);
/* 215 */     SpdyHttpHeaders.removeAssociatedToStreamId(httpMessage);
/* 216 */     SpdyHttpHeaders.removePriority(httpMessage);
/* 217 */     SpdyHttpHeaders.removeUrl(httpMessage);
/* 218 */     SpdyHttpHeaders.removeScheme(httpMessage);
/*     */ 
/*     */ 
/*     */     
/* 222 */     httpMessage.headers().remove("Connection");
/* 223 */     httpMessage.headers().remove("Keep-Alive");
/* 224 */     httpMessage.headers().remove("Proxy-Connection");
/* 225 */     httpMessage.headers().remove("Transfer-Encoding");
/*     */     
/* 227 */     SpdySynStreamFrame spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamID, associatedToStreamId, priority);
/*     */ 
/*     */ 
/*     */     
/* 231 */     if (httpMessage instanceof net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest) {
/* 232 */       HttpRequest httpRequest = (HttpRequest)httpMessage;
/* 233 */       SpdyHeaders.setMethod(this.spdyVersion, spdySynStreamFrame, httpRequest.getMethod());
/* 234 */       SpdyHeaders.setUrl(this.spdyVersion, spdySynStreamFrame, httpRequest.getUri());
/* 235 */       SpdyHeaders.setVersion(this.spdyVersion, spdySynStreamFrame, httpMessage.getProtocolVersion());
/*     */     } 
/* 237 */     if (httpMessage instanceof HttpResponse) {
/* 238 */       HttpResponse httpResponse = (HttpResponse)httpMessage;
/* 239 */       SpdyHeaders.setStatus(this.spdyVersion, spdySynStreamFrame, httpResponse.getStatus());
/* 240 */       SpdyHeaders.setUrl(this.spdyVersion, spdySynStreamFrame, URL);
/* 241 */       SpdyHeaders.setVersion(this.spdyVersion, spdySynStreamFrame, httpMessage.getProtocolVersion());
/* 242 */       spdySynStreamFrame.setUnidirectional(true);
/*     */     } 
/*     */ 
/*     */     
/* 246 */     if (this.spdyVersion >= 3) {
/* 247 */       String host = HttpHeaders.getHost(httpMessage);
/* 248 */       httpMessage.headers().remove("Host");
/* 249 */       SpdyHeaders.setHost(spdySynStreamFrame, host);
/*     */     } 
/*     */ 
/*     */     
/* 253 */     if (scheme == null) {
/* 254 */       scheme = "https";
/*     */     }
/* 256 */     SpdyHeaders.setScheme(this.spdyVersion, spdySynStreamFrame, scheme);
/*     */ 
/*     */     
/* 259 */     for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)httpMessage.headers()) {
/* 260 */       spdySynStreamFrame.headers().add(entry.getKey(), entry.getValue());
/*     */     }
/* 262 */     this.currentStreamId = spdySynStreamFrame.getStreamId();
/* 263 */     spdySynStreamFrame.setLast(isLast(httpMessage));
/*     */     
/* 265 */     return spdySynStreamFrame;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SpdySynReplyFrame createSynReplyFrame(HttpResponse httpResponse) throws Exception {
/* 271 */     int streamID = SpdyHttpHeaders.getStreamId((HttpMessage)httpResponse);
/* 272 */     SpdyHttpHeaders.removeStreamId((HttpMessage)httpResponse);
/*     */ 
/*     */ 
/*     */     
/* 276 */     httpResponse.headers().remove("Connection");
/* 277 */     httpResponse.headers().remove("Keep-Alive");
/* 278 */     httpResponse.headers().remove("Proxy-Connection");
/* 279 */     httpResponse.headers().remove("Transfer-Encoding");
/*     */     
/* 281 */     SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamID);
/*     */ 
/*     */     
/* 284 */     SpdyHeaders.setStatus(this.spdyVersion, spdySynReplyFrame, httpResponse.getStatus());
/* 285 */     SpdyHeaders.setVersion(this.spdyVersion, spdySynReplyFrame, httpResponse.getProtocolVersion());
/*     */ 
/*     */     
/* 288 */     for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)httpResponse.headers()) {
/* 289 */       spdySynReplyFrame.headers().add(entry.getKey(), entry.getValue());
/*     */     }
/*     */     
/* 292 */     this.currentStreamId = streamID;
/* 293 */     spdySynReplyFrame.setLast(isLast((HttpMessage)httpResponse));
/*     */     
/* 295 */     return spdySynReplyFrame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isLast(HttpMessage httpMessage) {
/* 305 */     if (httpMessage instanceof FullHttpMessage) {
/* 306 */       FullHttpMessage fullMessage = (FullHttpMessage)httpMessage;
/* 307 */       if (fullMessage.trailingHeaders().isEmpty() && !fullMessage.content().isReadable()) {
/* 308 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 312 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyHttpEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */