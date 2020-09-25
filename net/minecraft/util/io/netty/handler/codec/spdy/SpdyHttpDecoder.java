/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.TooLongFrameException;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpMessage;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpHeaders;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpMessage;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpMethod;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpdyHttpDecoder
/*     */   extends MessageToMessageDecoder<SpdyFrame>
/*     */ {
/*     */   private final int spdyVersion;
/*     */   private final int maxContentLength;
/*     */   private final Map<Integer, FullHttpMessage> messageMap;
/*     */   
/*     */   public SpdyHttpDecoder(int version, int maxContentLength) {
/*  55 */     this(version, maxContentLength, new HashMap<Integer, FullHttpMessage>());
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
/*     */   protected SpdyHttpDecoder(int version, int maxContentLength, Map<Integer, FullHttpMessage> messageMap) {
/*  68 */     if (version < 2 || version > 3) {
/*  69 */       throw new IllegalArgumentException("unsupported version: " + version);
/*     */     }
/*     */     
/*  72 */     if (maxContentLength <= 0) {
/*  73 */       throw new IllegalArgumentException("maxContentLength must be a positive integer: " + maxContentLength);
/*     */     }
/*     */     
/*  76 */     this.spdyVersion = version;
/*  77 */     this.maxContentLength = maxContentLength;
/*  78 */     this.messageMap = messageMap;
/*     */   }
/*     */   
/*     */   protected FullHttpMessage putMessage(int streamId, FullHttpMessage message) {
/*  82 */     return this.messageMap.put(Integer.valueOf(streamId), message);
/*     */   }
/*     */   
/*     */   protected FullHttpMessage getMessage(int streamId) {
/*  86 */     return this.messageMap.get(Integer.valueOf(streamId));
/*     */   }
/*     */   
/*     */   protected FullHttpMessage removeMessage(int streamId) {
/*  90 */     return this.messageMap.remove(Integer.valueOf(streamId));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, SpdyFrame msg, List<Object> out) throws Exception {
/*  96 */     if (msg instanceof SpdySynStreamFrame) {
/*     */ 
/*     */       
/*  99 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 100 */       int streamId = spdySynStreamFrame.getStreamId();
/*     */       
/* 102 */       if (SpdyCodecUtil.isServerId(streamId)) {
/*     */         
/* 104 */         int associatedToStreamId = spdySynStreamFrame.getAssociatedToStreamId();
/*     */ 
/*     */ 
/*     */         
/* 108 */         if (associatedToStreamId == 0) {
/* 109 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */           
/* 111 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */           
/*     */           return;
/*     */         } 
/* 115 */         String URL = SpdyHeaders.getUrl(this.spdyVersion, spdySynStreamFrame);
/*     */ 
/*     */ 
/*     */         
/* 119 */         if (URL == null) {
/* 120 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */           
/* 122 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 128 */         if (spdySynStreamFrame.isTruncated()) {
/* 129 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */           
/* 131 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */           
/*     */           return;
/*     */         } 
/*     */         try {
/* 136 */           FullHttpResponse httpResponseWithEntity = createHttpResponse(this.spdyVersion, spdySynStreamFrame);
/*     */ 
/*     */ 
/*     */           
/* 140 */           SpdyHttpHeaders.setStreamId((HttpMessage)httpResponseWithEntity, streamId);
/* 141 */           SpdyHttpHeaders.setAssociatedToStreamId((HttpMessage)httpResponseWithEntity, associatedToStreamId);
/* 142 */           SpdyHttpHeaders.setPriority((HttpMessage)httpResponseWithEntity, spdySynStreamFrame.getPriority());
/* 143 */           SpdyHttpHeaders.setUrl((HttpMessage)httpResponseWithEntity, URL);
/*     */           
/* 145 */           if (spdySynStreamFrame.isLast()) {
/* 146 */             HttpHeaders.setContentLength((HttpMessage)httpResponseWithEntity, 0L);
/* 147 */             out.add(httpResponseWithEntity);
/*     */           } else {
/*     */             
/* 150 */             putMessage(streamId, (FullHttpMessage)httpResponseWithEntity);
/*     */           } 
/* 152 */         } catch (Exception e) {
/* 153 */           SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */           
/* 155 */           ctx.writeAndFlush(spdyRstStreamFrame);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 162 */         if (spdySynStreamFrame.isTruncated()) {
/* 163 */           SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
/* 164 */           spdySynReplyFrame.setLast(true);
/* 165 */           SpdyHeaders.setStatus(this.spdyVersion, spdySynReplyFrame, HttpResponseStatus.REQUEST_HEADER_FIELDS_TOO_LARGE);
/*     */ 
/*     */           
/* 168 */           SpdyHeaders.setVersion(this.spdyVersion, spdySynReplyFrame, HttpVersion.HTTP_1_0);
/* 169 */           ctx.writeAndFlush(spdySynReplyFrame);
/*     */           
/*     */           return;
/*     */         } 
/*     */         try {
/* 174 */           FullHttpRequest httpRequestWithEntity = createHttpRequest(this.spdyVersion, spdySynStreamFrame);
/*     */ 
/*     */           
/* 177 */           SpdyHttpHeaders.setStreamId((HttpMessage)httpRequestWithEntity, streamId);
/*     */           
/* 179 */           if (spdySynStreamFrame.isLast()) {
/* 180 */             out.add(httpRequestWithEntity);
/*     */           } else {
/*     */             
/* 183 */             putMessage(streamId, (FullHttpMessage)httpRequestWithEntity);
/*     */           } 
/* 185 */         } catch (Exception e) {
/*     */ 
/*     */ 
/*     */           
/* 189 */           SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
/* 190 */           spdySynReplyFrame.setLast(true);
/* 191 */           SpdyHeaders.setStatus(this.spdyVersion, spdySynReplyFrame, HttpResponseStatus.BAD_REQUEST);
/* 192 */           SpdyHeaders.setVersion(this.spdyVersion, spdySynReplyFrame, HttpVersion.HTTP_1_0);
/* 193 */           ctx.writeAndFlush(spdySynReplyFrame);
/*     */         }
/*     */       
/*     */       } 
/* 197 */     } else if (msg instanceof SpdySynReplyFrame) {
/*     */       
/* 199 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 200 */       int streamId = spdySynReplyFrame.getStreamId();
/*     */ 
/*     */ 
/*     */       
/* 204 */       if (spdySynReplyFrame.isTruncated()) {
/* 205 */         SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR);
/*     */         
/* 207 */         ctx.writeAndFlush(spdyRstStreamFrame);
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 212 */         FullHttpResponse httpResponseWithEntity = createHttpResponse(this.spdyVersion, spdySynReplyFrame);
/*     */ 
/*     */         
/* 215 */         SpdyHttpHeaders.setStreamId((HttpMessage)httpResponseWithEntity, streamId);
/*     */         
/* 217 */         if (spdySynReplyFrame.isLast()) {
/* 218 */           HttpHeaders.setContentLength((HttpMessage)httpResponseWithEntity, 0L);
/* 219 */           out.add(httpResponseWithEntity);
/*     */         } else {
/*     */           
/* 222 */           putMessage(streamId, (FullHttpMessage)httpResponseWithEntity);
/*     */         } 
/* 224 */       } catch (Exception e) {
/*     */ 
/*     */         
/* 227 */         SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */         
/* 229 */         ctx.writeAndFlush(spdyRstStreamFrame);
/*     */       }
/*     */     
/* 232 */     } else if (msg instanceof SpdyHeadersFrame) {
/*     */       
/* 234 */       SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 235 */       int streamId = spdyHeadersFrame.getStreamId();
/* 236 */       FullHttpMessage fullHttpMessage = getMessage(streamId);
/*     */ 
/*     */       
/* 239 */       if (fullHttpMessage == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 244 */       if (!spdyHeadersFrame.isTruncated()) {
/* 245 */         for (Map.Entry<String, String> e : spdyHeadersFrame.headers().entries()) {
/* 246 */           fullHttpMessage.headers().add(e.getKey(), e.getValue());
/*     */         }
/*     */       }
/*     */       
/* 250 */       if (spdyHeadersFrame.isLast()) {
/* 251 */         HttpHeaders.setContentLength((HttpMessage)fullHttpMessage, fullHttpMessage.content().readableBytes());
/* 252 */         removeMessage(streamId);
/* 253 */         out.add(fullHttpMessage);
/*     */       }
/*     */     
/* 256 */     } else if (msg instanceof SpdyDataFrame) {
/*     */       
/* 258 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/* 259 */       int streamId = spdyDataFrame.getStreamId();
/* 260 */       FullHttpMessage fullHttpMessage = getMessage(streamId);
/*     */ 
/*     */       
/* 263 */       if (fullHttpMessage == null) {
/*     */         return;
/*     */       }
/*     */       
/* 267 */       ByteBuf content = fullHttpMessage.content();
/* 268 */       if (content.readableBytes() > this.maxContentLength - spdyDataFrame.content().readableBytes()) {
/* 269 */         removeMessage(streamId);
/* 270 */         throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
/*     */       } 
/*     */ 
/*     */       
/* 274 */       ByteBuf spdyDataFrameData = spdyDataFrame.content();
/* 275 */       int spdyDataFrameDataLen = spdyDataFrameData.readableBytes();
/* 276 */       content.writeBytes(spdyDataFrameData, spdyDataFrameData.readerIndex(), spdyDataFrameDataLen);
/*     */       
/* 278 */       if (spdyDataFrame.isLast()) {
/* 279 */         HttpHeaders.setContentLength((HttpMessage)fullHttpMessage, content.readableBytes());
/* 280 */         removeMessage(streamId);
/* 281 */         out.add(fullHttpMessage);
/*     */       }
/*     */     
/* 284 */     } else if (msg instanceof SpdyRstStreamFrame) {
/*     */       
/* 286 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 287 */       int streamId = spdyRstStreamFrame.getStreamId();
/* 288 */       removeMessage(streamId);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static FullHttpRequest createHttpRequest(int spdyVersion, SpdyHeadersFrame requestFrame) throws Exception {
/* 295 */     HttpMethod method = SpdyHeaders.getMethod(spdyVersion, requestFrame);
/* 296 */     String url = SpdyHeaders.getUrl(spdyVersion, requestFrame);
/* 297 */     HttpVersion httpVersion = SpdyHeaders.getVersion(spdyVersion, requestFrame);
/* 298 */     SpdyHeaders.removeMethod(spdyVersion, requestFrame);
/* 299 */     SpdyHeaders.removeUrl(spdyVersion, requestFrame);
/* 300 */     SpdyHeaders.removeVersion(spdyVersion, requestFrame);
/*     */     
/* 302 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(httpVersion, method, url);
/*     */ 
/*     */     
/* 305 */     SpdyHeaders.removeScheme(spdyVersion, requestFrame);
/*     */     
/* 307 */     if (spdyVersion >= 3) {
/*     */       
/* 309 */       String host = SpdyHeaders.getHost(requestFrame);
/* 310 */       SpdyHeaders.removeHost(requestFrame);
/* 311 */       HttpHeaders.setHost((HttpMessage)defaultFullHttpRequest, host);
/*     */     } 
/*     */     
/* 314 */     for (Map.Entry<String, String> e : requestFrame.headers().entries()) {
/* 315 */       defaultFullHttpRequest.headers().add(e.getKey(), e.getValue());
/*     */     }
/*     */ 
/*     */     
/* 319 */     HttpHeaders.setKeepAlive((HttpMessage)defaultFullHttpRequest, true);
/*     */ 
/*     */     
/* 322 */     defaultFullHttpRequest.headers().remove("Transfer-Encoding");
/*     */     
/* 324 */     return (FullHttpRequest)defaultFullHttpRequest;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static FullHttpResponse createHttpResponse(int spdyVersion, SpdyHeadersFrame responseFrame) throws Exception {
/* 330 */     HttpResponseStatus status = SpdyHeaders.getStatus(spdyVersion, responseFrame);
/* 331 */     HttpVersion version = SpdyHeaders.getVersion(spdyVersion, responseFrame);
/* 332 */     SpdyHeaders.removeStatus(spdyVersion, responseFrame);
/* 333 */     SpdyHeaders.removeVersion(spdyVersion, responseFrame);
/*     */     
/* 335 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(version, status);
/* 336 */     for (Map.Entry<String, String> e : responseFrame.headers().entries()) {
/* 337 */       defaultFullHttpResponse.headers().add(e.getKey(), e.getValue());
/*     */     }
/*     */ 
/*     */     
/* 341 */     HttpHeaders.setKeepAlive((HttpMessage)defaultFullHttpResponse, true);
/*     */ 
/*     */     
/* 344 */     defaultFullHttpResponse.headers().remove("Transfer-Encoding");
/* 345 */     defaultFullHttpResponse.headers().remove("Trailer");
/*     */     
/* 347 */     return (FullHttpResponse)defaultFullHttpResponse;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyHttpDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */