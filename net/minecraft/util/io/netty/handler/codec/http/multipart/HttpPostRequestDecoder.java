/*      */ package net.minecraft.util.io.netty.handler.codec.http.multipart;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URLDecoder;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*      */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*      */ import net.minecraft.util.io.netty.handler.codec.DecoderException;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpConstants;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpContent;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpMethod;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpRequest;
/*      */ import net.minecraft.util.io.netty.util.internal.StringUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HttpPostRequestDecoder
/*      */ {
/*      */   private static final int DEFAULT_DISCARD_THRESHOLD = 10485760;
/*      */   private final HttpDataFactory factory;
/*      */   private final HttpRequest request;
/*      */   private final Charset charset;
/*      */   private boolean bodyToDecode;
/*      */   private boolean isLastChunk;
/*   79 */   private final List<InterfaceHttpData> bodyListHttpData = new ArrayList<InterfaceHttpData>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   84 */   private final Map<String, List<InterfaceHttpData>> bodyMapHttpData = new TreeMap<String, List<InterfaceHttpData>>(CaseIgnoringComparator.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuf undecodedChunk;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isMultipart;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int bodyListHttpDataRank;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String multipartDataBoundary;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String multipartMixedBoundary;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   private MultiPartStatus currentStatus = MultiPartStatus.NOTSTARTED;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, Attribute> currentFieldAttributes;
/*      */ 
/*      */ 
/*      */   
/*      */   private FileUpload currentFileUpload;
/*      */ 
/*      */ 
/*      */   
/*      */   private Attribute currentAttribute;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean destroyed;
/*      */ 
/*      */   
/*  135 */   private int discardThreshold = 10485760;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestDecoder(HttpRequest request) throws ErrorDataDecoderException, IncompatibleDataDecoderException {
/*  151 */     this(new DefaultHttpDataFactory(16384L), request, HttpConstants.DEFAULT_CHARSET);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestDecoder(HttpDataFactory factory, HttpRequest request) throws ErrorDataDecoderException, IncompatibleDataDecoderException {
/*  170 */     this(factory, request, HttpConstants.DEFAULT_CHARSET);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset) throws ErrorDataDecoderException, IncompatibleDataDecoderException {
/*  191 */     if (factory == null) {
/*  192 */       throw new NullPointerException("factory");
/*      */     }
/*  194 */     if (request == null) {
/*  195 */       throw new NullPointerException("request");
/*      */     }
/*  197 */     if (charset == null) {
/*  198 */       throw new NullPointerException("charset");
/*      */     }
/*  200 */     this.request = request;
/*  201 */     HttpMethod method = request.getMethod();
/*  202 */     if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH)) {
/*  203 */       this.bodyToDecode = true;
/*      */     }
/*  205 */     this.charset = charset;
/*  206 */     this.factory = factory;
/*      */ 
/*      */     
/*  209 */     String contentType = this.request.headers().get("Content-Type");
/*  210 */     if (contentType != null) {
/*  211 */       checkMultipart(contentType);
/*      */     } else {
/*  213 */       this.isMultipart = false;
/*      */     } 
/*  215 */     if (!this.bodyToDecode) {
/*  216 */       throw new IncompatibleDataDecoderException("No Body to decode");
/*      */     }
/*  218 */     if (request instanceof HttpContent) {
/*      */ 
/*      */       
/*  221 */       offer((HttpContent)request);
/*      */     } else {
/*  223 */       this.undecodedChunk = Unpooled.buffer();
/*  224 */       parseBody();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private enum MultiPartStatus
/*      */   {
/*  259 */     NOTSTARTED, PREAMBLE, HEADERDELIMITER, DISPOSITION, FIELD, FILEUPLOAD, MIXEDPREAMBLE, MIXEDDELIMITER,
/*  260 */     MIXEDDISPOSITION, MIXEDFILEUPLOAD, MIXEDCLOSEDELIMITER, CLOSEDELIMITER, PREEPILOGUE, EPILOGUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkMultipart(String contentType) throws ErrorDataDecoderException {
/*  269 */     String[] headerContentType = splitHeaderContentType(contentType);
/*  270 */     if (headerContentType[0].toLowerCase().startsWith("multipart/form-data") && headerContentType[1].toLowerCase().startsWith("boundary")) {
/*      */       
/*  272 */       String[] boundary = StringUtil.split(headerContentType[1], '=');
/*  273 */       if (boundary.length != 2) {
/*  274 */         throw new ErrorDataDecoderException("Needs a boundary value");
/*      */       }
/*  276 */       this.multipartDataBoundary = "--" + boundary[1];
/*  277 */       this.isMultipart = true;
/*  278 */       this.currentStatus = MultiPartStatus.HEADERDELIMITER;
/*      */     } else {
/*  280 */       this.isMultipart = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkDestroyed() {
/*  285 */     if (this.destroyed) {
/*  286 */       throw new IllegalStateException(HttpPostRequestDecoder.class.getSimpleName() + " was destroyed already");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMultipart() {
/*  296 */     checkDestroyed();
/*  297 */     return this.isMultipart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDiscardThreshold(int discardThreshold) {
/*  306 */     if (discardThreshold < 0) {
/*  307 */       throw new IllegalArgumentException("discardThreshold must be >= 0");
/*      */     }
/*  309 */     this.discardThreshold = discardThreshold;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDiscardThreshold() {
/*  316 */     return this.discardThreshold;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<InterfaceHttpData> getBodyHttpDatas() throws NotEnoughDataDecoderException {
/*  330 */     checkDestroyed();
/*      */     
/*  332 */     if (!this.isLastChunk) {
/*  333 */       throw new NotEnoughDataDecoderException();
/*      */     }
/*  335 */     return this.bodyListHttpData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<InterfaceHttpData> getBodyHttpDatas(String name) throws NotEnoughDataDecoderException {
/*  350 */     checkDestroyed();
/*      */     
/*  352 */     if (!this.isLastChunk) {
/*  353 */       throw new NotEnoughDataDecoderException();
/*      */     }
/*  355 */     return this.bodyMapHttpData.get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InterfaceHttpData getBodyHttpData(String name) throws NotEnoughDataDecoderException {
/*  371 */     checkDestroyed();
/*      */     
/*  373 */     if (!this.isLastChunk) {
/*  374 */       throw new NotEnoughDataDecoderException();
/*      */     }
/*  376 */     List<InterfaceHttpData> list = this.bodyMapHttpData.get(name);
/*  377 */     if (list != null) {
/*  378 */       return list.get(0);
/*      */     }
/*  380 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestDecoder offer(HttpContent content) throws ErrorDataDecoderException {
/*  393 */     checkDestroyed();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  398 */     ByteBuf buf = content.content();
/*  399 */     if (this.undecodedChunk == null) {
/*  400 */       this.undecodedChunk = buf.copy();
/*      */     } else {
/*  402 */       this.undecodedChunk.writeBytes(buf);
/*      */     } 
/*  404 */     if (content instanceof net.minecraft.util.io.netty.handler.codec.http.LastHttpContent) {
/*  405 */       this.isLastChunk = true;
/*      */     }
/*  407 */     parseBody();
/*  408 */     if (this.undecodedChunk != null && this.undecodedChunk.writerIndex() > this.discardThreshold) {
/*  409 */       this.undecodedChunk.discardReadBytes();
/*      */     }
/*  411 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasNext() throws EndOfDataDecoderException {
/*  425 */     checkDestroyed();
/*      */     
/*  427 */     if (this.currentStatus == MultiPartStatus.EPILOGUE)
/*      */     {
/*  429 */       if (this.bodyListHttpDataRank >= this.bodyListHttpData.size()) {
/*  430 */         throw new EndOfDataDecoderException();
/*      */       }
/*      */     }
/*  433 */     return (!this.bodyListHttpData.isEmpty() && this.bodyListHttpDataRank < this.bodyListHttpData.size());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InterfaceHttpData next() throws EndOfDataDecoderException {
/*  449 */     checkDestroyed();
/*      */     
/*  451 */     if (hasNext()) {
/*  452 */       return this.bodyListHttpData.get(this.bodyListHttpDataRank++);
/*      */     }
/*  454 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseBody() throws ErrorDataDecoderException {
/*  465 */     if (this.currentStatus == MultiPartStatus.PREEPILOGUE || this.currentStatus == MultiPartStatus.EPILOGUE) {
/*  466 */       if (this.isLastChunk) {
/*  467 */         this.currentStatus = MultiPartStatus.EPILOGUE;
/*      */       }
/*      */       return;
/*      */     } 
/*  471 */     if (this.isMultipart) {
/*  472 */       parseBodyMultipart();
/*      */     } else {
/*  474 */       parseBodyAttributes();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addHttpData(InterfaceHttpData data) {
/*  482 */     if (data == null) {
/*      */       return;
/*      */     }
/*  485 */     List<InterfaceHttpData> datas = this.bodyMapHttpData.get(data.getName());
/*  486 */     if (datas == null) {
/*  487 */       datas = new ArrayList<InterfaceHttpData>(1);
/*  488 */       this.bodyMapHttpData.put(data.getName(), datas);
/*      */     } 
/*  490 */     datas.add(data);
/*  491 */     this.bodyListHttpData.add(data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseBodyAttributesStandard() throws ErrorDataDecoderException {
/*  503 */     int firstpos = this.undecodedChunk.readerIndex();
/*  504 */     int currentpos = firstpos;
/*      */ 
/*      */     
/*  507 */     if (this.currentStatus == MultiPartStatus.NOTSTARTED) {
/*  508 */       this.currentStatus = MultiPartStatus.DISPOSITION;
/*      */     }
/*  510 */     boolean contRead = true;
/*      */     try {
/*  512 */       while (this.undecodedChunk.isReadable() && contRead) {
/*  513 */         char read = (char)this.undecodedChunk.readUnsignedByte();
/*  514 */         currentpos++;
/*  515 */         switch (this.currentStatus) {
/*      */           case DISPOSITION:
/*  517 */             if (read == '=') {
/*  518 */               this.currentStatus = MultiPartStatus.FIELD;
/*  519 */               int equalpos = currentpos - 1;
/*  520 */               String key = decodeAttribute(this.undecodedChunk.toString(firstpos, equalpos - firstpos, this.charset), this.charset);
/*      */               
/*  522 */               this.currentAttribute = this.factory.createAttribute(this.request, key);
/*  523 */               firstpos = currentpos; continue;
/*  524 */             }  if (read == '&') {
/*  525 */               this.currentStatus = MultiPartStatus.DISPOSITION;
/*  526 */               int ampersandpos = currentpos - 1;
/*  527 */               String key = decodeAttribute(this.undecodedChunk.toString(firstpos, ampersandpos - firstpos, this.charset), this.charset);
/*      */               
/*  529 */               this.currentAttribute = this.factory.createAttribute(this.request, key);
/*  530 */               this.currentAttribute.setValue("");
/*  531 */               addHttpData(this.currentAttribute);
/*  532 */               this.currentAttribute = null;
/*  533 */               firstpos = currentpos;
/*  534 */               contRead = true;
/*      */             } 
/*      */             continue;
/*      */           case FIELD:
/*  538 */             if (read == '&') {
/*  539 */               this.currentStatus = MultiPartStatus.DISPOSITION;
/*  540 */               int i = currentpos - 1;
/*  541 */               setFinalBuffer(this.undecodedChunk.slice(firstpos, i - firstpos).retain());
/*  542 */               firstpos = currentpos;
/*  543 */               contRead = true; continue;
/*  544 */             }  if (read == '\r') {
/*  545 */               if (this.undecodedChunk.isReadable()) {
/*  546 */                 read = (char)this.undecodedChunk.readUnsignedByte();
/*  547 */                 currentpos++;
/*  548 */                 if (read == '\n') {
/*  549 */                   this.currentStatus = MultiPartStatus.PREEPILOGUE;
/*  550 */                   int ampersandpos = currentpos - 2;
/*  551 */                   setFinalBuffer(this.undecodedChunk.slice(firstpos, ampersandpos - firstpos).retain());
/*  552 */                   firstpos = currentpos;
/*  553 */                   contRead = false;
/*      */                   continue;
/*      */                 } 
/*  556 */                 throw new ErrorDataDecoderException("Bad end of line");
/*      */               } 
/*      */               
/*  559 */               currentpos--; continue;
/*      */             } 
/*  561 */             if (read == '\n') {
/*  562 */               this.currentStatus = MultiPartStatus.PREEPILOGUE;
/*  563 */               int ampersandpos = currentpos - 1;
/*  564 */               setFinalBuffer(this.undecodedChunk.slice(firstpos, ampersandpos - firstpos).retain());
/*  565 */               firstpos = currentpos;
/*  566 */               contRead = false;
/*      */             } 
/*      */             continue;
/*      */         } 
/*      */         
/*  571 */         contRead = false;
/*      */       } 
/*      */       
/*  574 */       if (this.isLastChunk && this.currentAttribute != null) {
/*      */         
/*  576 */         int i = currentpos;
/*  577 */         if (i > firstpos) {
/*  578 */           setFinalBuffer(this.undecodedChunk.slice(firstpos, i - firstpos).retain());
/*  579 */         } else if (!this.currentAttribute.isCompleted()) {
/*  580 */           setFinalBuffer(Unpooled.EMPTY_BUFFER);
/*      */         } 
/*  582 */         firstpos = currentpos;
/*  583 */         this.currentStatus = MultiPartStatus.EPILOGUE;
/*      */         return;
/*      */       } 
/*  586 */       if (contRead && this.currentAttribute != null)
/*      */       {
/*  588 */         if (this.currentStatus == MultiPartStatus.FIELD) {
/*  589 */           this.currentAttribute.addContent(this.undecodedChunk.slice(firstpos, currentpos - firstpos).retain(), false);
/*  590 */           firstpos = currentpos;
/*      */         } 
/*  592 */         this.undecodedChunk.readerIndex(firstpos);
/*      */       }
/*      */     
/*      */     }
/*  596 */     catch (ErrorDataDecoderException e) {
/*      */       
/*  598 */       this.undecodedChunk.readerIndex(firstpos);
/*  599 */       throw e;
/*  600 */     } catch (IOException e) {
/*      */       
/*  602 */       this.undecodedChunk.readerIndex(firstpos);
/*  603 */       throw new ErrorDataDecoderException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseBodyAttributes() throws ErrorDataDecoderException {
/*      */     // Byte code:
/*      */     //   0: new net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostBodyUtil$SeekAheadOptimize
/*      */     //   3: dup
/*      */     //   4: aload_0
/*      */     //   5: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   8: invokespecial <init> : (Lnet/minecraft/util/io/netty/buffer/ByteBuf;)V
/*      */     //   11: astore_1
/*      */     //   12: goto -> 21
/*      */     //   15: astore_2
/*      */     //   16: aload_0
/*      */     //   17: invokespecial parseBodyAttributesStandard : ()V
/*      */     //   20: return
/*      */     //   21: aload_0
/*      */     //   22: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   25: invokevirtual readerIndex : ()I
/*      */     //   28: istore_2
/*      */     //   29: iload_2
/*      */     //   30: istore_3
/*      */     //   31: aload_0
/*      */     //   32: getfield currentStatus : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   35: getstatic net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.NOTSTARTED : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   38: if_acmpne -> 48
/*      */     //   41: aload_0
/*      */     //   42: getstatic net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.DISPOSITION : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   45: putfield currentStatus : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   48: iconst_1
/*      */     //   49: istore #4
/*      */     //   51: aload_1
/*      */     //   52: getfield pos : I
/*      */     //   55: aload_1
/*      */     //   56: getfield limit : I
/*      */     //   59: if_icmpge -> 523
/*      */     //   62: aload_1
/*      */     //   63: getfield bytes : [B
/*      */     //   66: aload_1
/*      */     //   67: dup
/*      */     //   68: getfield pos : I
/*      */     //   71: dup_x1
/*      */     //   72: iconst_1
/*      */     //   73: iadd
/*      */     //   74: putfield pos : I
/*      */     //   77: baload
/*      */     //   78: sipush #255
/*      */     //   81: iand
/*      */     //   82: i2c
/*      */     //   83: istore #5
/*      */     //   85: iinc #3, 1
/*      */     //   88: getstatic net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$1.$SwitchMap$io$netty$handler$codec$http$multipart$HttpPostRequestDecoder$MultiPartStatus : [I
/*      */     //   91: aload_0
/*      */     //   92: getfield currentStatus : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   95: invokevirtual ordinal : ()I
/*      */     //   98: iaload
/*      */     //   99: lookupswitch default -> 509, 1 -> 124, 2 -> 288
/*      */     //   124: iload #5
/*      */     //   126: bipush #61
/*      */     //   128: if_icmpne -> 192
/*      */     //   131: aload_0
/*      */     //   132: getstatic net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.FIELD : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   135: putfield currentStatus : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   138: iload_3
/*      */     //   139: iconst_1
/*      */     //   140: isub
/*      */     //   141: istore #6
/*      */     //   143: aload_0
/*      */     //   144: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   147: iload_2
/*      */     //   148: iload #6
/*      */     //   150: iload_2
/*      */     //   151: isub
/*      */     //   152: aload_0
/*      */     //   153: getfield charset : Ljava/nio/charset/Charset;
/*      */     //   156: invokevirtual toString : (IILjava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   159: aload_0
/*      */     //   160: getfield charset : Ljava/nio/charset/Charset;
/*      */     //   163: invokestatic decodeAttribute : (Ljava/lang/String;Ljava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   166: astore #7
/*      */     //   168: aload_0
/*      */     //   169: aload_0
/*      */     //   170: getfield factory : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpDataFactory;
/*      */     //   173: aload_0
/*      */     //   174: getfield request : Lnet/minecraft/util/io/netty/handler/codec/http/HttpRequest;
/*      */     //   177: aload #7
/*      */     //   179: invokeinterface createAttribute : (Lnet/minecraft/util/io/netty/handler/codec/http/HttpRequest;Ljava/lang/String;)Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   184: putfield currentAttribute : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   187: iload_3
/*      */     //   188: istore_2
/*      */     //   189: goto -> 520
/*      */     //   192: iload #5
/*      */     //   194: bipush #38
/*      */     //   196: if_icmpne -> 520
/*      */     //   199: aload_0
/*      */     //   200: getstatic net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.DISPOSITION : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   203: putfield currentStatus : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   206: iload_3
/*      */     //   207: iconst_1
/*      */     //   208: isub
/*      */     //   209: istore #8
/*      */     //   211: aload_0
/*      */     //   212: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   215: iload_2
/*      */     //   216: iload #8
/*      */     //   218: iload_2
/*      */     //   219: isub
/*      */     //   220: aload_0
/*      */     //   221: getfield charset : Ljava/nio/charset/Charset;
/*      */     //   224: invokevirtual toString : (IILjava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   227: aload_0
/*      */     //   228: getfield charset : Ljava/nio/charset/Charset;
/*      */     //   231: invokestatic decodeAttribute : (Ljava/lang/String;Ljava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   234: astore #7
/*      */     //   236: aload_0
/*      */     //   237: aload_0
/*      */     //   238: getfield factory : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpDataFactory;
/*      */     //   241: aload_0
/*      */     //   242: getfield request : Lnet/minecraft/util/io/netty/handler/codec/http/HttpRequest;
/*      */     //   245: aload #7
/*      */     //   247: invokeinterface createAttribute : (Lnet/minecraft/util/io/netty/handler/codec/http/HttpRequest;Ljava/lang/String;)Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   252: putfield currentAttribute : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   255: aload_0
/*      */     //   256: getfield currentAttribute : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   259: ldc_w ''
/*      */     //   262: invokeinterface setValue : (Ljava/lang/String;)V
/*      */     //   267: aload_0
/*      */     //   268: aload_0
/*      */     //   269: getfield currentAttribute : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   272: invokevirtual addHttpData : (Lnet/minecraft/util/io/netty/handler/codec/http/multipart/InterfaceHttpData;)V
/*      */     //   275: aload_0
/*      */     //   276: aconst_null
/*      */     //   277: putfield currentAttribute : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   280: iload_3
/*      */     //   281: istore_2
/*      */     //   282: iconst_1
/*      */     //   283: istore #4
/*      */     //   285: goto -> 520
/*      */     //   288: iload #5
/*      */     //   290: bipush #38
/*      */     //   292: if_icmpne -> 334
/*      */     //   295: aload_0
/*      */     //   296: getstatic net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.DISPOSITION : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   299: putfield currentStatus : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   302: iload_3
/*      */     //   303: iconst_1
/*      */     //   304: isub
/*      */     //   305: istore #8
/*      */     //   307: aload_0
/*      */     //   308: aload_0
/*      */     //   309: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   312: iload_2
/*      */     //   313: iload #8
/*      */     //   315: iload_2
/*      */     //   316: isub
/*      */     //   317: invokevirtual slice : (II)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   320: invokevirtual retain : ()Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   323: invokespecial setFinalBuffer : (Lnet/minecraft/util/io/netty/buffer/ByteBuf;)V
/*      */     //   326: iload_3
/*      */     //   327: istore_2
/*      */     //   328: iconst_1
/*      */     //   329: istore #4
/*      */     //   331: goto -> 520
/*      */     //   334: iload #5
/*      */     //   336: bipush #13
/*      */     //   338: if_icmpne -> 458
/*      */     //   341: aload_1
/*      */     //   342: getfield pos : I
/*      */     //   345: aload_1
/*      */     //   346: getfield limit : I
/*      */     //   349: if_icmpge -> 445
/*      */     //   352: aload_1
/*      */     //   353: getfield bytes : [B
/*      */     //   356: aload_1
/*      */     //   357: dup
/*      */     //   358: getfield pos : I
/*      */     //   361: dup_x1
/*      */     //   362: iconst_1
/*      */     //   363: iadd
/*      */     //   364: putfield pos : I
/*      */     //   367: baload
/*      */     //   368: sipush #255
/*      */     //   371: iand
/*      */     //   372: i2c
/*      */     //   373: istore #5
/*      */     //   375: iinc #3, 1
/*      */     //   378: iload #5
/*      */     //   380: bipush #10
/*      */     //   382: if_icmpne -> 429
/*      */     //   385: aload_0
/*      */     //   386: getstatic net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.PREEPILOGUE : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   389: putfield currentStatus : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   392: iload_3
/*      */     //   393: iconst_2
/*      */     //   394: isub
/*      */     //   395: istore #8
/*      */     //   397: aload_1
/*      */     //   398: iconst_0
/*      */     //   399: invokevirtual setReadPosition : (I)V
/*      */     //   402: aload_0
/*      */     //   403: aload_0
/*      */     //   404: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   407: iload_2
/*      */     //   408: iload #8
/*      */     //   410: iload_2
/*      */     //   411: isub
/*      */     //   412: invokevirtual slice : (II)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   415: invokevirtual retain : ()Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   418: invokespecial setFinalBuffer : (Lnet/minecraft/util/io/netty/buffer/ByteBuf;)V
/*      */     //   421: iload_3
/*      */     //   422: istore_2
/*      */     //   423: iconst_0
/*      */     //   424: istore #4
/*      */     //   426: goto -> 523
/*      */     //   429: aload_1
/*      */     //   430: iconst_0
/*      */     //   431: invokevirtual setReadPosition : (I)V
/*      */     //   434: new net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$ErrorDataDecoderException
/*      */     //   437: dup
/*      */     //   438: ldc_w 'Bad end of line'
/*      */     //   441: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   444: athrow
/*      */     //   445: aload_1
/*      */     //   446: getfield limit : I
/*      */     //   449: ifle -> 520
/*      */     //   452: iinc #3, -1
/*      */     //   455: goto -> 520
/*      */     //   458: iload #5
/*      */     //   460: bipush #10
/*      */     //   462: if_icmpne -> 520
/*      */     //   465: aload_0
/*      */     //   466: getstatic net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.PREEPILOGUE : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   469: putfield currentStatus : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   472: iload_3
/*      */     //   473: iconst_1
/*      */     //   474: isub
/*      */     //   475: istore #8
/*      */     //   477: aload_1
/*      */     //   478: iconst_0
/*      */     //   479: invokevirtual setReadPosition : (I)V
/*      */     //   482: aload_0
/*      */     //   483: aload_0
/*      */     //   484: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   487: iload_2
/*      */     //   488: iload #8
/*      */     //   490: iload_2
/*      */     //   491: isub
/*      */     //   492: invokevirtual slice : (II)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   495: invokevirtual retain : ()Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   498: invokespecial setFinalBuffer : (Lnet/minecraft/util/io/netty/buffer/ByteBuf;)V
/*      */     //   501: iload_3
/*      */     //   502: istore_2
/*      */     //   503: iconst_0
/*      */     //   504: istore #4
/*      */     //   506: goto -> 523
/*      */     //   509: aload_1
/*      */     //   510: iconst_0
/*      */     //   511: invokevirtual setReadPosition : (I)V
/*      */     //   514: iconst_0
/*      */     //   515: istore #4
/*      */     //   517: goto -> 523
/*      */     //   520: goto -> 51
/*      */     //   523: aload_0
/*      */     //   524: getfield isLastChunk : Z
/*      */     //   527: ifeq -> 597
/*      */     //   530: aload_0
/*      */     //   531: getfield currentAttribute : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   534: ifnull -> 597
/*      */     //   537: iload_3
/*      */     //   538: istore #8
/*      */     //   540: iload #8
/*      */     //   542: iload_2
/*      */     //   543: if_icmple -> 568
/*      */     //   546: aload_0
/*      */     //   547: aload_0
/*      */     //   548: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   551: iload_2
/*      */     //   552: iload #8
/*      */     //   554: iload_2
/*      */     //   555: isub
/*      */     //   556: invokevirtual slice : (II)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   559: invokevirtual retain : ()Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   562: invokespecial setFinalBuffer : (Lnet/minecraft/util/io/netty/buffer/ByteBuf;)V
/*      */     //   565: goto -> 587
/*      */     //   568: aload_0
/*      */     //   569: getfield currentAttribute : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   572: invokeinterface isCompleted : ()Z
/*      */     //   577: ifne -> 587
/*      */     //   580: aload_0
/*      */     //   581: getstatic net/minecraft/util/io/netty/buffer/Unpooled.EMPTY_BUFFER : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   584: invokespecial setFinalBuffer : (Lnet/minecraft/util/io/netty/buffer/ByteBuf;)V
/*      */     //   587: iload_3
/*      */     //   588: istore_2
/*      */     //   589: aload_0
/*      */     //   590: getstatic net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.EPILOGUE : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   593: putfield currentStatus : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   596: return
/*      */     //   597: iload #4
/*      */     //   599: ifeq -> 654
/*      */     //   602: aload_0
/*      */     //   603: getfield currentAttribute : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   606: ifnull -> 654
/*      */     //   609: aload_0
/*      */     //   610: getfield currentStatus : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   613: getstatic net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus.FIELD : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$MultiPartStatus;
/*      */     //   616: if_acmpne -> 645
/*      */     //   619: aload_0
/*      */     //   620: getfield currentAttribute : Lnet/minecraft/util/io/netty/handler/codec/http/multipart/Attribute;
/*      */     //   623: aload_0
/*      */     //   624: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   627: iload_2
/*      */     //   628: iload_3
/*      */     //   629: iload_2
/*      */     //   630: isub
/*      */     //   631: invokevirtual slice : (II)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   634: invokevirtual retain : ()Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   637: iconst_0
/*      */     //   638: invokeinterface addContent : (Lnet/minecraft/util/io/netty/buffer/ByteBuf;Z)V
/*      */     //   643: iload_3
/*      */     //   644: istore_2
/*      */     //   645: aload_0
/*      */     //   646: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   649: iload_2
/*      */     //   650: invokevirtual readerIndex : (I)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   653: pop
/*      */     //   654: goto -> 692
/*      */     //   657: astore #5
/*      */     //   659: aload_0
/*      */     //   660: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   663: iload_2
/*      */     //   664: invokevirtual readerIndex : (I)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   667: pop
/*      */     //   668: aload #5
/*      */     //   670: athrow
/*      */     //   671: astore #5
/*      */     //   673: aload_0
/*      */     //   674: getfield undecodedChunk : Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   677: iload_2
/*      */     //   678: invokevirtual readerIndex : (I)Lnet/minecraft/util/io/netty/buffer/ByteBuf;
/*      */     //   681: pop
/*      */     //   682: new net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$ErrorDataDecoderException
/*      */     //   685: dup
/*      */     //   686: aload #5
/*      */     //   688: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   691: athrow
/*      */     //   692: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #618	-> 0
/*      */     //   #622	-> 12
/*      */     //   #619	-> 15
/*      */     //   #620	-> 16
/*      */     //   #621	-> 20
/*      */     //   #623	-> 21
/*      */     //   #624	-> 29
/*      */     //   #627	-> 31
/*      */     //   #628	-> 41
/*      */     //   #630	-> 48
/*      */     //   #632	-> 51
/*      */     //   #633	-> 62
/*      */     //   #634	-> 85
/*      */     //   #635	-> 88
/*      */     //   #637	-> 124
/*      */     //   #638	-> 131
/*      */     //   #639	-> 138
/*      */     //   #640	-> 143
/*      */     //   #642	-> 168
/*      */     //   #643	-> 187
/*      */     //   #644	-> 189
/*      */     //   #645	-> 199
/*      */     //   #646	-> 206
/*      */     //   #647	-> 211
/*      */     //   #649	-> 236
/*      */     //   #650	-> 255
/*      */     //   #651	-> 267
/*      */     //   #652	-> 275
/*      */     //   #653	-> 280
/*      */     //   #654	-> 282
/*      */     //   #655	-> 285
/*      */     //   #658	-> 288
/*      */     //   #659	-> 295
/*      */     //   #660	-> 302
/*      */     //   #661	-> 307
/*      */     //   #662	-> 326
/*      */     //   #663	-> 328
/*      */     //   #664	-> 334
/*      */     //   #665	-> 341
/*      */     //   #666	-> 352
/*      */     //   #667	-> 375
/*      */     //   #668	-> 378
/*      */     //   #669	-> 385
/*      */     //   #670	-> 392
/*      */     //   #671	-> 397
/*      */     //   #672	-> 402
/*      */     //   #673	-> 421
/*      */     //   #674	-> 423
/*      */     //   #675	-> 426
/*      */     //   #678	-> 429
/*      */     //   #679	-> 434
/*      */     //   #682	-> 445
/*      */     //   #683	-> 452
/*      */     //   #686	-> 458
/*      */     //   #687	-> 465
/*      */     //   #688	-> 472
/*      */     //   #689	-> 477
/*      */     //   #690	-> 482
/*      */     //   #691	-> 501
/*      */     //   #692	-> 503
/*      */     //   #693	-> 506
/*      */     //   #698	-> 509
/*      */     //   #699	-> 514
/*      */     //   #700	-> 517
/*      */     //   #702	-> 520
/*      */     //   #703	-> 523
/*      */     //   #705	-> 537
/*      */     //   #706	-> 540
/*      */     //   #707	-> 546
/*      */     //   #708	-> 568
/*      */     //   #709	-> 580
/*      */     //   #711	-> 587
/*      */     //   #712	-> 589
/*      */     //   #713	-> 596
/*      */     //   #715	-> 597
/*      */     //   #717	-> 609
/*      */     //   #718	-> 619
/*      */     //   #719	-> 643
/*      */     //   #721	-> 645
/*      */     //   #733	-> 654
/*      */     //   #725	-> 657
/*      */     //   #727	-> 659
/*      */     //   #728	-> 668
/*      */     //   #729	-> 671
/*      */     //   #731	-> 673
/*      */     //   #732	-> 682
/*      */     //   #734	-> 692
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   16	5	2	e1	Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostBodyUtil$SeekAheadNoBackArrayException;
/*      */     //   168	21	7	key	Ljava/lang/String;
/*      */     //   143	49	6	equalpos	I
/*      */     //   236	49	7	key	Ljava/lang/String;
/*      */     //   211	123	8	ampersandpos	I
/*      */     //   397	32	8	ampersandpos	I
/*      */     //   85	435	5	read	C
/*      */     //   477	120	8	ampersandpos	I
/*      */     //   659	12	5	e	Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$ErrorDataDecoderException;
/*      */     //   673	19	5	e	Ljava/io/IOException;
/*      */     //   0	693	0	this	Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder;
/*      */     //   12	681	1	sao	Lnet/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostBodyUtil$SeekAheadOptimize;
/*      */     //   29	664	2	firstpos	I
/*      */     //   31	662	3	currentpos	I
/*      */     //   51	642	4	contRead	Z
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   0	12	15	net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostBodyUtil$SeekAheadNoBackArrayException
/*      */     //   51	596	657	net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$ErrorDataDecoderException
/*      */     //   51	596	671	java/io/IOException
/*      */     //   597	654	657	net/minecraft/util/io/netty/handler/codec/http/multipart/HttpPostRequestDecoder$ErrorDataDecoderException
/*      */     //   597	654	671	java/io/IOException
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFinalBuffer(ByteBuf buffer) throws ErrorDataDecoderException, IOException {
/*  737 */     this.currentAttribute.addContent(buffer, true);
/*  738 */     String value = decodeAttribute(this.currentAttribute.getByteBuf().toString(this.charset), this.charset);
/*  739 */     this.currentAttribute.setValue(value);
/*  740 */     addHttpData(this.currentAttribute);
/*  741 */     this.currentAttribute = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String decodeAttribute(String s, Charset charset) throws ErrorDataDecoderException {
/*  750 */     if (s == null) {
/*  751 */       return "";
/*      */     }
/*      */     try {
/*  754 */       return URLDecoder.decode(s, charset.name());
/*  755 */     } catch (UnsupportedEncodingException e) {
/*  756 */       throw new ErrorDataDecoderException(charset.toString(), e);
/*  757 */     } catch (IllegalArgumentException e) {
/*  758 */       throw new ErrorDataDecoderException("Bad string: '" + s + '\'', e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseBodyMultipart() throws ErrorDataDecoderException {
/*  770 */     if (this.undecodedChunk == null || this.undecodedChunk.readableBytes() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  774 */     InterfaceHttpData data = decodeMultipart(this.currentStatus);
/*  775 */     while (data != null) {
/*  776 */       addHttpData(data);
/*  777 */       if (this.currentStatus == MultiPartStatus.PREEPILOGUE || this.currentStatus == MultiPartStatus.EPILOGUE) {
/*      */         break;
/*      */       }
/*  780 */       data = decodeMultipart(this.currentStatus);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InterfaceHttpData decodeMultipart(MultiPartStatus state) throws ErrorDataDecoderException {
/*      */     Charset localCharset;
/*      */     Attribute charsetAttribute;
/*      */     Attribute nameAttribute;
/*      */     Attribute finalAttribute;
/*  801 */     switch (state) {
/*      */       case NOTSTARTED:
/*  803 */         throw new ErrorDataDecoderException("Should not be called with the current getStatus");
/*      */       
/*      */       case PREAMBLE:
/*  806 */         throw new ErrorDataDecoderException("Should not be called with the current getStatus");
/*      */       
/*      */       case HEADERDELIMITER:
/*  809 */         return findMultipartDelimiter(this.multipartDataBoundary, MultiPartStatus.DISPOSITION, MultiPartStatus.PREEPILOGUE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case DISPOSITION:
/*  822 */         return findMultipartDisposition();
/*      */ 
/*      */       
/*      */       case FIELD:
/*  826 */         localCharset = null;
/*  827 */         charsetAttribute = this.currentFieldAttributes.get("charset");
/*  828 */         if (charsetAttribute != null) {
/*      */           try {
/*  830 */             localCharset = Charset.forName(charsetAttribute.getValue());
/*  831 */           } catch (IOException e) {
/*  832 */             throw new ErrorDataDecoderException(e);
/*      */           } 
/*      */         }
/*  835 */         nameAttribute = this.currentFieldAttributes.get("name");
/*  836 */         if (this.currentAttribute == null) {
/*      */           try {
/*  838 */             this.currentAttribute = this.factory.createAttribute(this.request, cleanString(nameAttribute.getValue()));
/*      */           }
/*  840 */           catch (NullPointerException e) {
/*  841 */             throw new ErrorDataDecoderException(e);
/*  842 */           } catch (IllegalArgumentException e) {
/*  843 */             throw new ErrorDataDecoderException(e);
/*  844 */           } catch (IOException e) {
/*  845 */             throw new ErrorDataDecoderException(e);
/*      */           } 
/*  847 */           if (localCharset != null) {
/*  848 */             this.currentAttribute.setCharset(localCharset);
/*      */           }
/*      */         } 
/*      */         
/*      */         try {
/*  853 */           loadFieldMultipart(this.multipartDataBoundary);
/*  854 */         } catch (NotEnoughDataDecoderException e) {
/*  855 */           return null;
/*      */         } 
/*  857 */         finalAttribute = this.currentAttribute;
/*  858 */         this.currentAttribute = null;
/*  859 */         this.currentFieldAttributes = null;
/*      */         
/*  861 */         this.currentStatus = MultiPartStatus.HEADERDELIMITER;
/*  862 */         return finalAttribute;
/*      */ 
/*      */       
/*      */       case FILEUPLOAD:
/*  866 */         return getFileUpload(this.multipartDataBoundary);
/*      */ 
/*      */ 
/*      */       
/*      */       case MIXEDDELIMITER:
/*  871 */         return findMultipartDelimiter(this.multipartMixedBoundary, MultiPartStatus.MIXEDDISPOSITION, MultiPartStatus.HEADERDELIMITER);
/*      */ 
/*      */       
/*      */       case MIXEDDISPOSITION:
/*  875 */         return findMultipartDisposition();
/*      */ 
/*      */       
/*      */       case MIXEDFILEUPLOAD:
/*  879 */         return getFileUpload(this.multipartMixedBoundary);
/*      */       
/*      */       case PREEPILOGUE:
/*  882 */         return null;
/*      */       case EPILOGUE:
/*  884 */         return null;
/*      */     } 
/*  886 */     throw new ErrorDataDecoderException("Shouldn't reach here.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void skipControlCharacters() throws NotEnoughDataDecoderException {
/*      */     HttpPostBodyUtil.SeekAheadOptimize sao;
/*      */     try {
/*  898 */       sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/*  899 */     } catch (SeekAheadNoBackArrayException e) {
/*      */       try {
/*  901 */         skipControlCharactersStandard();
/*  902 */       } catch (IndexOutOfBoundsException e1) {
/*  903 */         throw new NotEnoughDataDecoderException(e1);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  908 */     while (sao.pos < sao.limit) {
/*  909 */       char c = (char)(sao.bytes[sao.pos++] & 0xFF);
/*  910 */       if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
/*  911 */         sao.setReadPosition(1);
/*      */         return;
/*      */       } 
/*      */     } 
/*  915 */     throw new NotEnoughDataDecoderException("Access out of bounds");
/*      */   }
/*      */   
/*      */   void skipControlCharactersStandard() {
/*      */     while (true) {
/*  920 */       char c = (char)this.undecodedChunk.readUnsignedByte();
/*  921 */       if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
/*  922 */         this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InterfaceHttpData findMultipartDelimiter(String delimiter, MultiPartStatus dispositionStatus, MultiPartStatus closeDelimiterStatus) throws ErrorDataDecoderException {
/*      */     String newline;
/*  943 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     try {
/*  945 */       skipControlCharacters();
/*  946 */     } catch (NotEnoughDataDecoderException e1) {
/*  947 */       this.undecodedChunk.readerIndex(readerIndex);
/*  948 */       return null;
/*      */     } 
/*  950 */     skipOneLine();
/*      */     
/*      */     try {
/*  953 */       newline = readDelimiter(delimiter);
/*  954 */     } catch (NotEnoughDataDecoderException e) {
/*  955 */       this.undecodedChunk.readerIndex(readerIndex);
/*  956 */       return null;
/*      */     } 
/*  958 */     if (newline.equals(delimiter)) {
/*  959 */       this.currentStatus = dispositionStatus;
/*  960 */       return decodeMultipart(dispositionStatus);
/*      */     } 
/*  962 */     if (newline.equals(delimiter + "--")) {
/*      */       
/*  964 */       this.currentStatus = closeDelimiterStatus;
/*  965 */       if (this.currentStatus == MultiPartStatus.HEADERDELIMITER) {
/*      */ 
/*      */         
/*  968 */         this.currentFieldAttributes = null;
/*  969 */         return decodeMultipart(MultiPartStatus.HEADERDELIMITER);
/*      */       } 
/*  971 */       return null;
/*      */     } 
/*  973 */     this.undecodedChunk.readerIndex(readerIndex);
/*  974 */     throw new ErrorDataDecoderException("No Multipart delimiter found");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InterfaceHttpData findMultipartDisposition() throws ErrorDataDecoderException {
/*  984 */     int readerIndex = this.undecodedChunk.readerIndex();
/*  985 */     if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/*  986 */       this.currentFieldAttributes = new TreeMap<String, Attribute>(CaseIgnoringComparator.INSTANCE);
/*      */     }
/*      */     
/*  989 */     while (!skipOneLine()) {
/*      */       String newline;
/*      */       try {
/*  992 */         skipControlCharacters();
/*  993 */         newline = readLine();
/*  994 */       } catch (NotEnoughDataDecoderException e) {
/*  995 */         this.undecodedChunk.readerIndex(readerIndex);
/*  996 */         return null;
/*      */       } 
/*  998 */       String[] contents = splitMultipartHeader(newline);
/*  999 */       if (contents[0].equalsIgnoreCase("Content-Disposition")) {
/*      */         boolean checkSecondArg;
/* 1001 */         if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/* 1002 */           checkSecondArg = contents[1].equalsIgnoreCase("form-data");
/*      */         } else {
/* 1004 */           checkSecondArg = (contents[1].equalsIgnoreCase("attachment") || contents[1].equalsIgnoreCase("file"));
/*      */         } 
/*      */         
/* 1007 */         if (checkSecondArg)
/*      */         {
/* 1009 */           for (int i = 2; i < contents.length; i++) {
/* 1010 */             Attribute attribute; String[] values = StringUtil.split(contents[i], '=');
/*      */             
/*      */             try {
/* 1013 */               String name = cleanString(values[0]);
/* 1014 */               String value = values[1];
/*      */ 
/*      */               
/* 1017 */               if ("filename".equals(name)) {
/*      */                 
/* 1019 */                 value = value.substring(1, value.length() - 1);
/*      */               } else {
/*      */                 
/* 1022 */                 value = cleanString(value);
/*      */               } 
/* 1024 */               attribute = this.factory.createAttribute(this.request, name, value);
/* 1025 */             } catch (NullPointerException e) {
/* 1026 */               throw new ErrorDataDecoderException(e);
/* 1027 */             } catch (IllegalArgumentException e) {
/* 1028 */               throw new ErrorDataDecoderException(e);
/*      */             } 
/* 1030 */             this.currentFieldAttributes.put(attribute.getName(), attribute);
/*      */           }  }  continue;
/*      */       } 
/* 1033 */       if (contents[0].equalsIgnoreCase("Content-Transfer-Encoding")) {
/*      */         Attribute attribute;
/*      */         try {
/* 1036 */           attribute = this.factory.createAttribute(this.request, "Content-Transfer-Encoding", cleanString(contents[1]));
/*      */         }
/* 1038 */         catch (NullPointerException e) {
/* 1039 */           throw new ErrorDataDecoderException(e);
/* 1040 */         } catch (IllegalArgumentException e) {
/* 1041 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 1043 */         this.currentFieldAttributes.put("Content-Transfer-Encoding", attribute); continue;
/* 1044 */       }  if (contents[0].equalsIgnoreCase("Content-Length")) {
/*      */         Attribute attribute;
/*      */         try {
/* 1047 */           attribute = this.factory.createAttribute(this.request, "Content-Length", cleanString(contents[1]));
/*      */         }
/* 1049 */         catch (NullPointerException e) {
/* 1050 */           throw new ErrorDataDecoderException(e);
/* 1051 */         } catch (IllegalArgumentException e) {
/* 1052 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 1054 */         this.currentFieldAttributes.put("Content-Length", attribute); continue;
/* 1055 */       }  if (contents[0].equalsIgnoreCase("Content-Type")) {
/*      */         
/* 1057 */         if (contents[1].equalsIgnoreCase("multipart/mixed")) {
/* 1058 */           if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/* 1059 */             String[] values = StringUtil.split(contents[2], '=');
/* 1060 */             this.multipartMixedBoundary = "--" + values[1];
/* 1061 */             this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
/* 1062 */             return decodeMultipart(MultiPartStatus.MIXEDDELIMITER);
/*      */           } 
/* 1064 */           throw new ErrorDataDecoderException("Mixed Multipart found in a previous Mixed Multipart");
/*      */         } 
/*      */         
/* 1067 */         for (int i = 1; i < contents.length; i++) {
/* 1068 */           if (contents[i].toLowerCase().startsWith("charset")) {
/* 1069 */             Attribute attribute; String[] values = StringUtil.split(contents[i], '=');
/*      */             
/*      */             try {
/* 1072 */               attribute = this.factory.createAttribute(this.request, "charset", cleanString(values[1]));
/*      */             }
/* 1074 */             catch (NullPointerException e) {
/* 1075 */               throw new ErrorDataDecoderException(e);
/* 1076 */             } catch (IllegalArgumentException e) {
/* 1077 */               throw new ErrorDataDecoderException(e);
/*      */             } 
/* 1079 */             this.currentFieldAttributes.put("charset", attribute);
/*      */           } else {
/*      */             Attribute attribute;
/*      */             try {
/* 1083 */               attribute = this.factory.createAttribute(this.request, cleanString(contents[0]), contents[i]);
/*      */             }
/* 1085 */             catch (NullPointerException e) {
/* 1086 */               throw new ErrorDataDecoderException(e);
/* 1087 */             } catch (IllegalArgumentException e) {
/* 1088 */               throw new ErrorDataDecoderException(e);
/*      */             } 
/* 1090 */             this.currentFieldAttributes.put(attribute.getName(), attribute);
/*      */           } 
/*      */         } 
/*      */         continue;
/*      */       } 
/* 1095 */       throw new ErrorDataDecoderException("Unknown Params: " + newline);
/*      */     } 
/*      */ 
/*      */     
/* 1099 */     Attribute filenameAttribute = this.currentFieldAttributes.get("filename");
/* 1100 */     if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/* 1101 */       if (filenameAttribute != null) {
/*      */         
/* 1103 */         this.currentStatus = MultiPartStatus.FILEUPLOAD;
/*      */         
/* 1105 */         return decodeMultipart(MultiPartStatus.FILEUPLOAD);
/*      */       } 
/*      */       
/* 1108 */       this.currentStatus = MultiPartStatus.FIELD;
/*      */       
/* 1110 */       return decodeMultipart(MultiPartStatus.FIELD);
/*      */     } 
/*      */     
/* 1113 */     if (filenameAttribute != null) {
/*      */       
/* 1115 */       this.currentStatus = MultiPartStatus.MIXEDFILEUPLOAD;
/*      */       
/* 1117 */       return decodeMultipart(MultiPartStatus.MIXEDFILEUPLOAD);
/*      */     } 
/*      */     
/* 1120 */     throw new ErrorDataDecoderException("Filename not found");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected InterfaceHttpData getFileUpload(String delimiter) throws ErrorDataDecoderException {
/* 1136 */     Attribute encoding = this.currentFieldAttributes.get("Content-Transfer-Encoding");
/* 1137 */     Charset localCharset = this.charset;
/*      */     
/* 1139 */     HttpPostBodyUtil.TransferEncodingMechanism mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT7;
/* 1140 */     if (encoding != null) {
/*      */       String code;
/*      */       try {
/* 1143 */         code = encoding.getValue().toLowerCase();
/* 1144 */       } catch (IOException e) {
/* 1145 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/* 1147 */       if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT7.value())) {
/* 1148 */         localCharset = HttpPostBodyUtil.US_ASCII;
/* 1149 */       } else if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT8.value())) {
/* 1150 */         localCharset = HttpPostBodyUtil.ISO_8859_1;
/* 1151 */         mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT8;
/* 1152 */       } else if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
/*      */         
/* 1154 */         mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BINARY;
/*      */       } else {
/* 1156 */         throw new ErrorDataDecoderException("TransferEncoding Unknown: " + code);
/*      */       } 
/*      */     } 
/* 1159 */     Attribute charsetAttribute = this.currentFieldAttributes.get("charset");
/* 1160 */     if (charsetAttribute != null) {
/*      */       try {
/* 1162 */         localCharset = Charset.forName(charsetAttribute.getValue());
/* 1163 */       } catch (IOException e) {
/* 1164 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     }
/* 1167 */     if (this.currentFileUpload == null) {
/* 1168 */       long l; Attribute filenameAttribute = this.currentFieldAttributes.get("filename");
/* 1169 */       Attribute nameAttribute = this.currentFieldAttributes.get("name");
/* 1170 */       Attribute contentTypeAttribute = this.currentFieldAttributes.get("Content-Type");
/* 1171 */       if (contentTypeAttribute == null) {
/* 1172 */         throw new ErrorDataDecoderException("Content-Type is absent but required");
/*      */       }
/* 1174 */       Attribute lengthAttribute = this.currentFieldAttributes.get("Content-Length");
/*      */       
/*      */       try {
/* 1177 */         l = (lengthAttribute != null) ? Long.parseLong(lengthAttribute.getValue()) : 0L;
/* 1178 */       } catch (IOException e) {
/* 1179 */         throw new ErrorDataDecoderException(e);
/* 1180 */       } catch (NumberFormatException e) {
/* 1181 */         l = 0L;
/*      */       } 
/*      */       try {
/* 1184 */         this.currentFileUpload = this.factory.createFileUpload(this.request, cleanString(nameAttribute.getValue()), cleanString(filenameAttribute.getValue()), contentTypeAttribute.getValue(), mechanism.value(), localCharset, l);
/*      */ 
/*      */       
/*      */       }
/* 1188 */       catch (NullPointerException e) {
/* 1189 */         throw new ErrorDataDecoderException(e);
/* 1190 */       } catch (IllegalArgumentException e) {
/* 1191 */         throw new ErrorDataDecoderException(e);
/* 1192 */       } catch (IOException e) {
/* 1193 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     } 
/*      */     
/*      */     try {
/* 1198 */       readFileUploadByteMultipart(delimiter);
/* 1199 */     } catch (NotEnoughDataDecoderException e) {
/*      */ 
/*      */ 
/*      */       
/* 1203 */       return null;
/*      */     } 
/* 1205 */     if (this.currentFileUpload.isCompleted()) {
/*      */       
/* 1207 */       if (this.currentStatus == MultiPartStatus.FILEUPLOAD) {
/* 1208 */         this.currentStatus = MultiPartStatus.HEADERDELIMITER;
/* 1209 */         this.currentFieldAttributes = null;
/*      */       } else {
/* 1211 */         this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
/* 1212 */         cleanMixedAttributes();
/*      */       } 
/* 1214 */       FileUpload fileUpload = this.currentFileUpload;
/* 1215 */       this.currentFileUpload = null;
/* 1216 */       return fileUpload;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1221 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroy() {
/* 1229 */     checkDestroyed();
/* 1230 */     cleanFiles();
/* 1231 */     this.destroyed = true;
/*      */     
/* 1233 */     if (this.undecodedChunk != null && this.undecodedChunk.refCnt() > 0) {
/* 1234 */       this.undecodedChunk.release();
/* 1235 */       this.undecodedChunk = null;
/*      */     } 
/*      */ 
/*      */     
/* 1239 */     for (int i = this.bodyListHttpDataRank; i < this.bodyListHttpData.size(); i++) {
/* 1240 */       ((InterfaceHttpData)this.bodyListHttpData.get(i)).release();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanFiles() {
/* 1248 */     checkDestroyed();
/*      */     
/* 1250 */     this.factory.cleanRequestHttpDatas(this.request);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeHttpDataFromClean(InterfaceHttpData data) {
/* 1257 */     checkDestroyed();
/*      */     
/* 1259 */     this.factory.removeHttpDataFromClean(this.request, data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cleanMixedAttributes() {
/* 1267 */     this.currentFieldAttributes.remove("charset");
/* 1268 */     this.currentFieldAttributes.remove("Content-Length");
/* 1269 */     this.currentFieldAttributes.remove("Content-Transfer-Encoding");
/* 1270 */     this.currentFieldAttributes.remove("Content-Type");
/* 1271 */     this.currentFieldAttributes.remove("filename");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readLineStandard() throws NotEnoughDataDecoderException {
/* 1283 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     try {
/* 1285 */       ByteBuf line = Unpooled.buffer(64);
/*      */       
/* 1287 */       while (this.undecodedChunk.isReadable()) {
/* 1288 */         byte nextByte = this.undecodedChunk.readByte();
/* 1289 */         if (nextByte == 13) {
/* 1290 */           nextByte = this.undecodedChunk.readByte();
/* 1291 */           if (nextByte == 10)
/* 1292 */             return line.toString(this.charset);  continue;
/*      */         } 
/* 1294 */         if (nextByte == 10) {
/* 1295 */           return line.toString(this.charset);
/*      */         }
/* 1297 */         line.writeByte(nextByte);
/*      */       }
/*      */     
/* 1300 */     } catch (IndexOutOfBoundsException e) {
/* 1301 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1302 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/* 1304 */     this.undecodedChunk.readerIndex(readerIndex);
/* 1305 */     throw new NotEnoughDataDecoderException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readLine() throws NotEnoughDataDecoderException {
/*      */     HttpPostBodyUtil.SeekAheadOptimize sao;
/*      */     try {
/* 1319 */       sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/* 1320 */     } catch (SeekAheadNoBackArrayException e1) {
/* 1321 */       return readLineStandard();
/*      */     } 
/* 1323 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     try {
/* 1325 */       ByteBuf line = Unpooled.buffer(64);
/*      */       
/* 1327 */       while (sao.pos < sao.limit) {
/* 1328 */         byte nextByte = sao.bytes[sao.pos++];
/* 1329 */         if (nextByte == 13) {
/* 1330 */           if (sao.pos < sao.limit) {
/* 1331 */             nextByte = sao.bytes[sao.pos++];
/* 1332 */             if (nextByte == 10) {
/* 1333 */               sao.setReadPosition(0);
/* 1334 */               return line.toString(this.charset);
/*      */             }  continue;
/*      */           } 
/* 1337 */           line.writeByte(nextByte); continue;
/*      */         } 
/* 1339 */         if (nextByte == 10) {
/* 1340 */           sao.setReadPosition(0);
/* 1341 */           return line.toString(this.charset);
/*      */         } 
/* 1343 */         line.writeByte(nextByte);
/*      */       }
/*      */     
/* 1346 */     } catch (IndexOutOfBoundsException e) {
/* 1347 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1348 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/* 1350 */     this.undecodedChunk.readerIndex(readerIndex);
/* 1351 */     throw new NotEnoughDataDecoderException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readDelimiterStandard(String delimiter) throws NotEnoughDataDecoderException {
/* 1370 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     try {
/* 1372 */       StringBuilder sb = new StringBuilder(64);
/* 1373 */       int delimiterPos = 0;
/* 1374 */       int len = delimiter.length();
/* 1375 */       while (this.undecodedChunk.isReadable() && delimiterPos < len) {
/* 1376 */         byte nextByte = this.undecodedChunk.readByte();
/* 1377 */         if (nextByte == delimiter.charAt(delimiterPos)) {
/* 1378 */           delimiterPos++;
/* 1379 */           sb.append((char)nextByte);
/*      */           continue;
/*      */         } 
/* 1382 */         this.undecodedChunk.readerIndex(readerIndex);
/* 1383 */         throw new NotEnoughDataDecoderException();
/*      */       } 
/*      */ 
/*      */       
/* 1387 */       if (this.undecodedChunk.isReadable()) {
/* 1388 */         byte nextByte = this.undecodedChunk.readByte();
/*      */         
/* 1390 */         if (nextByte == 13) {
/* 1391 */           nextByte = this.undecodedChunk.readByte();
/* 1392 */           if (nextByte == 10) {
/* 1393 */             return sb.toString();
/*      */           }
/*      */ 
/*      */           
/* 1397 */           this.undecodedChunk.readerIndex(readerIndex);
/* 1398 */           throw new NotEnoughDataDecoderException();
/*      */         } 
/* 1400 */         if (nextByte == 10)
/* 1401 */           return sb.toString(); 
/* 1402 */         if (nextByte == 45) {
/* 1403 */           sb.append('-');
/*      */           
/* 1405 */           nextByte = this.undecodedChunk.readByte();
/* 1406 */           if (nextByte == 45) {
/* 1407 */             sb.append('-');
/*      */             
/* 1409 */             if (this.undecodedChunk.isReadable()) {
/* 1410 */               nextByte = this.undecodedChunk.readByte();
/* 1411 */               if (nextByte == 13) {
/* 1412 */                 nextByte = this.undecodedChunk.readByte();
/* 1413 */                 if (nextByte == 10) {
/* 1414 */                   return sb.toString();
/*      */                 }
/*      */ 
/*      */                 
/* 1418 */                 this.undecodedChunk.readerIndex(readerIndex);
/* 1419 */                 throw new NotEnoughDataDecoderException();
/*      */               } 
/* 1421 */               if (nextByte == 10) {
/* 1422 */                 return sb.toString();
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 1427 */               this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 1428 */               return sb.toString();
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1435 */             return sb.toString();
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } 
/* 1441 */     } catch (IndexOutOfBoundsException e) {
/* 1442 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1443 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/* 1445 */     this.undecodedChunk.readerIndex(readerIndex);
/* 1446 */     throw new NotEnoughDataDecoderException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readDelimiter(String delimiter) throws NotEnoughDataDecoderException {
/*      */     HttpPostBodyUtil.SeekAheadOptimize sao;
/*      */     try {
/* 1466 */       sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/* 1467 */     } catch (SeekAheadNoBackArrayException e1) {
/* 1468 */       return readDelimiterStandard(delimiter);
/*      */     } 
/* 1470 */     int readerIndex = this.undecodedChunk.readerIndex();
/* 1471 */     int delimiterPos = 0;
/* 1472 */     int len = delimiter.length();
/*      */     try {
/* 1474 */       StringBuilder sb = new StringBuilder(64);
/*      */       
/* 1476 */       while (sao.pos < sao.limit && delimiterPos < len) {
/* 1477 */         byte nextByte = sao.bytes[sao.pos++];
/* 1478 */         if (nextByte == delimiter.charAt(delimiterPos)) {
/* 1479 */           delimiterPos++;
/* 1480 */           sb.append((char)nextByte);
/*      */           continue;
/*      */         } 
/* 1483 */         this.undecodedChunk.readerIndex(readerIndex);
/* 1484 */         throw new NotEnoughDataDecoderException();
/*      */       } 
/*      */ 
/*      */       
/* 1488 */       if (sao.pos < sao.limit) {
/* 1489 */         byte nextByte = sao.bytes[sao.pos++];
/* 1490 */         if (nextByte == 13)
/*      */         
/* 1492 */         { if (sao.pos < sao.limit) {
/* 1493 */             nextByte = sao.bytes[sao.pos++];
/* 1494 */             if (nextByte == 10) {
/* 1495 */               sao.setReadPosition(0);
/* 1496 */               return sb.toString();
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 1501 */             this.undecodedChunk.readerIndex(readerIndex);
/* 1502 */             throw new NotEnoughDataDecoderException();
/*      */           }  }
/* 1504 */         else { if (nextByte == 10) {
/*      */ 
/*      */             
/* 1507 */             sao.setReadPosition(0);
/* 1508 */             return sb.toString();
/* 1509 */           }  if (nextByte == 45) {
/* 1510 */             sb.append('-');
/*      */             
/* 1512 */             if (sao.pos < sao.limit) {
/* 1513 */               nextByte = sao.bytes[sao.pos++];
/* 1514 */               if (nextByte == 45) {
/* 1515 */                 sb.append('-');
/*      */                 
/* 1517 */                 if (sao.pos < sao.limit) {
/* 1518 */                   nextByte = sao.bytes[sao.pos++];
/* 1519 */                   if (nextByte == 13)
/* 1520 */                   { if (sao.pos < sao.limit) {
/* 1521 */                       nextByte = sao.bytes[sao.pos++];
/* 1522 */                       if (nextByte == 10) {
/* 1523 */                         sao.setReadPosition(0);
/* 1524 */                         return sb.toString();
/*      */                       }
/*      */                     
/*      */                     } else {
/*      */                       
/* 1529 */                       this.undecodedChunk.readerIndex(readerIndex);
/* 1530 */                       throw new NotEnoughDataDecoderException();
/*      */                     }  }
/* 1532 */                   else { if (nextByte == 10) {
/* 1533 */                       sao.setReadPosition(0);
/* 1534 */                       return sb.toString();
/*      */                     } 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1540 */                     sao.setReadPosition(1);
/* 1541 */                     return sb.toString(); }
/*      */                 
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1548 */                 sao.setReadPosition(0);
/* 1549 */                 return sb.toString();
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           }  }
/*      */       
/*      */       } 
/* 1557 */     } catch (IndexOutOfBoundsException e) {
/* 1558 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1559 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/* 1561 */     this.undecodedChunk.readerIndex(readerIndex);
/* 1562 */     throw new NotEnoughDataDecoderException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readFileUploadByteMultipartStandard(String delimiter) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
/* 1577 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     
/* 1579 */     boolean newLine = true;
/* 1580 */     int index = 0;
/* 1581 */     int lastPosition = this.undecodedChunk.readerIndex();
/* 1582 */     boolean found = false;
/* 1583 */     while (this.undecodedChunk.isReadable()) {
/* 1584 */       byte nextByte = this.undecodedChunk.readByte();
/* 1585 */       if (newLine) {
/*      */         
/* 1587 */         if (nextByte == delimiter.codePointAt(index)) {
/* 1588 */           index++;
/* 1589 */           if (delimiter.length() == index) {
/* 1590 */             found = true;
/*      */             break;
/*      */           } 
/*      */           continue;
/*      */         } 
/* 1595 */         newLine = false;
/* 1596 */         index = 0;
/*      */         
/* 1598 */         if (nextByte == 13) {
/* 1599 */           if (this.undecodedChunk.isReadable()) {
/* 1600 */             nextByte = this.undecodedChunk.readByte();
/* 1601 */             if (nextByte == 10) {
/* 1602 */               newLine = true;
/* 1603 */               index = 0;
/* 1604 */               lastPosition = this.undecodedChunk.readerIndex() - 2;
/*      */               continue;
/*      */             } 
/* 1607 */             lastPosition = this.undecodedChunk.readerIndex() - 1;
/*      */ 
/*      */             
/* 1610 */             this.undecodedChunk.readerIndex(lastPosition);
/*      */           }  continue;
/*      */         } 
/* 1613 */         if (nextByte == 10) {
/* 1614 */           newLine = true;
/* 1615 */           index = 0;
/* 1616 */           lastPosition = this.undecodedChunk.readerIndex() - 1;
/*      */           continue;
/*      */         } 
/* 1619 */         lastPosition = this.undecodedChunk.readerIndex();
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1624 */       if (nextByte == 13) {
/* 1625 */         if (this.undecodedChunk.isReadable()) {
/* 1626 */           nextByte = this.undecodedChunk.readByte();
/* 1627 */           if (nextByte == 10) {
/* 1628 */             newLine = true;
/* 1629 */             index = 0;
/* 1630 */             lastPosition = this.undecodedChunk.readerIndex() - 2;
/*      */             continue;
/*      */           } 
/* 1633 */           lastPosition = this.undecodedChunk.readerIndex() - 1;
/*      */ 
/*      */           
/* 1636 */           this.undecodedChunk.readerIndex(lastPosition);
/*      */         }  continue;
/*      */       } 
/* 1639 */       if (nextByte == 10) {
/* 1640 */         newLine = true;
/* 1641 */         index = 0;
/* 1642 */         lastPosition = this.undecodedChunk.readerIndex() - 1;
/*      */         continue;
/*      */       } 
/* 1645 */       lastPosition = this.undecodedChunk.readerIndex();
/*      */     } 
/*      */ 
/*      */     
/* 1649 */     ByteBuf buffer = this.undecodedChunk.slice(readerIndex, lastPosition - readerIndex).retain();
/* 1650 */     if (found) {
/*      */       
/*      */       try {
/* 1653 */         this.currentFileUpload.addContent(buffer, true);
/*      */         
/* 1655 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1656 */       } catch (IOException e) {
/* 1657 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/* 1663 */         this.currentFileUpload.addContent(buffer, false);
/*      */         
/* 1665 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1666 */         throw new NotEnoughDataDecoderException();
/* 1667 */       } catch (IOException e) {
/* 1668 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readFileUploadByteMultipart(String delimiter) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
/*      */     HttpPostBodyUtil.SeekAheadOptimize sao;
/*      */     try {
/* 1687 */       sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/* 1688 */     } catch (SeekAheadNoBackArrayException e1) {
/* 1689 */       readFileUploadByteMultipartStandard(delimiter);
/*      */       return;
/*      */     } 
/* 1692 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     
/* 1694 */     boolean newLine = true;
/* 1695 */     int index = 0;
/* 1696 */     int lastrealpos = sao.pos;
/*      */     
/* 1698 */     boolean found = false;
/*      */     
/* 1700 */     while (sao.pos < sao.limit) {
/* 1701 */       byte nextByte = sao.bytes[sao.pos++];
/* 1702 */       if (newLine) {
/*      */         
/* 1704 */         if (nextByte == delimiter.codePointAt(index)) {
/* 1705 */           index++;
/* 1706 */           if (delimiter.length() == index) {
/* 1707 */             found = true;
/*      */             break;
/*      */           } 
/*      */           continue;
/*      */         } 
/* 1712 */         newLine = false;
/* 1713 */         index = 0;
/*      */         
/* 1715 */         if (nextByte == 13) {
/* 1716 */           if (sao.pos < sao.limit) {
/* 1717 */             nextByte = sao.bytes[sao.pos++];
/* 1718 */             if (nextByte == 10) {
/* 1719 */               newLine = true;
/* 1720 */               index = 0;
/* 1721 */               lastrealpos = sao.pos - 2;
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/* 1727 */             lastrealpos = --sao.pos;
/*      */           }  continue;
/*      */         } 
/* 1730 */         if (nextByte == 10) {
/* 1731 */           newLine = true;
/* 1732 */           index = 0;
/* 1733 */           lastrealpos = sao.pos - 1;
/*      */           continue;
/*      */         } 
/* 1736 */         lastrealpos = sao.pos;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1741 */       if (nextByte == 13) {
/* 1742 */         if (sao.pos < sao.limit) {
/* 1743 */           nextByte = sao.bytes[sao.pos++];
/* 1744 */           if (nextByte == 10) {
/* 1745 */             newLine = true;
/* 1746 */             index = 0;
/* 1747 */             lastrealpos = sao.pos - 2;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 1753 */           lastrealpos = --sao.pos;
/*      */         }  continue;
/*      */       } 
/* 1756 */       if (nextByte == 10) {
/* 1757 */         newLine = true;
/* 1758 */         index = 0;
/* 1759 */         lastrealpos = sao.pos - 1;
/*      */         continue;
/*      */       } 
/* 1762 */       lastrealpos = sao.pos;
/*      */     } 
/*      */ 
/*      */     
/* 1766 */     int lastPosition = sao.getReadPosition(lastrealpos);
/* 1767 */     ByteBuf buffer = this.undecodedChunk.slice(readerIndex, lastPosition - readerIndex).retain();
/* 1768 */     if (found) {
/*      */       
/*      */       try {
/* 1771 */         this.currentFileUpload.addContent(buffer, true);
/*      */         
/* 1773 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1774 */       } catch (IOException e) {
/* 1775 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/* 1781 */         this.currentFileUpload.addContent(buffer, false);
/*      */         
/* 1783 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1784 */         throw new NotEnoughDataDecoderException();
/* 1785 */       } catch (IOException e) {
/* 1786 */         throw new ErrorDataDecoderException(e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadFieldMultipartStandard(String delimiter) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
/* 1800 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     
/*      */     try {
/* 1803 */       boolean newLine = true;
/* 1804 */       int index = 0;
/* 1805 */       int lastPosition = this.undecodedChunk.readerIndex();
/* 1806 */       boolean found = false;
/* 1807 */       while (this.undecodedChunk.isReadable()) {
/* 1808 */         byte nextByte = this.undecodedChunk.readByte();
/* 1809 */         if (newLine) {
/*      */           
/* 1811 */           if (nextByte == delimiter.codePointAt(index)) {
/* 1812 */             index++;
/* 1813 */             if (delimiter.length() == index) {
/* 1814 */               found = true;
/*      */               break;
/*      */             } 
/*      */             continue;
/*      */           } 
/* 1819 */           newLine = false;
/* 1820 */           index = 0;
/*      */           
/* 1822 */           if (nextByte == 13) {
/* 1823 */             if (this.undecodedChunk.isReadable()) {
/* 1824 */               nextByte = this.undecodedChunk.readByte();
/* 1825 */               if (nextByte == 10) {
/* 1826 */                 newLine = true;
/* 1827 */                 index = 0;
/* 1828 */                 lastPosition = this.undecodedChunk.readerIndex() - 2;
/*      */               } 
/*      */             }  continue;
/* 1831 */           }  if (nextByte == 10) {
/* 1832 */             newLine = true;
/* 1833 */             index = 0;
/* 1834 */             lastPosition = this.undecodedChunk.readerIndex() - 1; continue;
/*      */           } 
/* 1836 */           lastPosition = this.undecodedChunk.readerIndex();
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1841 */         if (nextByte == 13) {
/* 1842 */           if (this.undecodedChunk.isReadable()) {
/* 1843 */             nextByte = this.undecodedChunk.readByte();
/* 1844 */             if (nextByte == 10) {
/* 1845 */               newLine = true;
/* 1846 */               index = 0;
/* 1847 */               lastPosition = this.undecodedChunk.readerIndex() - 2;
/*      */             } 
/*      */           }  continue;
/* 1850 */         }  if (nextByte == 10) {
/* 1851 */           newLine = true;
/* 1852 */           index = 0;
/* 1853 */           lastPosition = this.undecodedChunk.readerIndex() - 1; continue;
/*      */         } 
/* 1855 */         lastPosition = this.undecodedChunk.readerIndex();
/*      */       } 
/*      */ 
/*      */       
/* 1859 */       if (found) {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 1865 */           this.currentAttribute.addContent(this.undecodedChunk.slice(readerIndex, lastPosition - readerIndex).retain(), true);
/*      */         }
/* 1867 */         catch (IOException e) {
/* 1868 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 1870 */         this.undecodedChunk.readerIndex(lastPosition);
/*      */       } else {
/*      */         try {
/* 1873 */           this.currentAttribute.addContent(this.undecodedChunk.slice(readerIndex, lastPosition - readerIndex).retain(), false);
/*      */         }
/* 1875 */         catch (IOException e) {
/* 1876 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 1878 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1879 */         throw new NotEnoughDataDecoderException();
/*      */       } 
/* 1881 */     } catch (IndexOutOfBoundsException e) {
/* 1882 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1883 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadFieldMultipart(String delimiter) throws NotEnoughDataDecoderException, ErrorDataDecoderException {
/*      */     HttpPostBodyUtil.SeekAheadOptimize sao;
/*      */     try {
/* 1897 */       sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/* 1898 */     } catch (SeekAheadNoBackArrayException e1) {
/* 1899 */       loadFieldMultipartStandard(delimiter);
/*      */       return;
/*      */     } 
/* 1902 */     int readerIndex = this.undecodedChunk.readerIndex();
/*      */     
/*      */     try {
/* 1905 */       boolean newLine = true;
/* 1906 */       int index = 0;
/*      */       
/* 1908 */       int lastrealpos = sao.pos;
/* 1909 */       boolean found = false;
/*      */       
/* 1911 */       while (sao.pos < sao.limit) {
/* 1912 */         byte nextByte = sao.bytes[sao.pos++];
/* 1913 */         if (newLine) {
/*      */           
/* 1915 */           if (nextByte == delimiter.codePointAt(index)) {
/* 1916 */             index++;
/* 1917 */             if (delimiter.length() == index) {
/* 1918 */               found = true;
/*      */               break;
/*      */             } 
/*      */             continue;
/*      */           } 
/* 1923 */           newLine = false;
/* 1924 */           index = 0;
/*      */           
/* 1926 */           if (nextByte == 13) {
/* 1927 */             if (sao.pos < sao.limit) {
/* 1928 */               nextByte = sao.bytes[sao.pos++];
/* 1929 */               if (nextByte == 10) {
/* 1930 */                 newLine = true;
/* 1931 */                 index = 0;
/* 1932 */                 lastrealpos = sao.pos - 2;
/*      */               } 
/*      */             }  continue;
/* 1935 */           }  if (nextByte == 10) {
/* 1936 */             newLine = true;
/* 1937 */             index = 0;
/* 1938 */             lastrealpos = sao.pos - 1; continue;
/*      */           } 
/* 1940 */           lastrealpos = sao.pos;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1945 */         if (nextByte == 13) {
/* 1946 */           if (sao.pos < sao.limit) {
/* 1947 */             nextByte = sao.bytes[sao.pos++];
/* 1948 */             if (nextByte == 10) {
/* 1949 */               newLine = true;
/* 1950 */               index = 0;
/* 1951 */               lastrealpos = sao.pos - 2;
/*      */             } 
/*      */           }  continue;
/* 1954 */         }  if (nextByte == 10) {
/* 1955 */           newLine = true;
/* 1956 */           index = 0;
/* 1957 */           lastrealpos = sao.pos - 1; continue;
/*      */         } 
/* 1959 */         lastrealpos = sao.pos;
/*      */       } 
/*      */ 
/*      */       
/* 1963 */       int lastPosition = sao.getReadPosition(lastrealpos);
/* 1964 */       if (found) {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 1970 */           this.currentAttribute.addContent(this.undecodedChunk.slice(readerIndex, lastPosition - readerIndex).retain(), true);
/*      */         }
/* 1972 */         catch (IOException e) {
/* 1973 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 1975 */         this.undecodedChunk.readerIndex(lastPosition);
/*      */       } else {
/*      */         try {
/* 1978 */           this.currentAttribute.addContent(this.undecodedChunk.slice(readerIndex, lastPosition - readerIndex).retain(), false);
/*      */         }
/* 1980 */         catch (IOException e) {
/* 1981 */           throw new ErrorDataDecoderException(e);
/*      */         } 
/* 1983 */         this.undecodedChunk.readerIndex(lastPosition);
/* 1984 */         throw new NotEnoughDataDecoderException();
/*      */       } 
/* 1986 */     } catch (IndexOutOfBoundsException e) {
/* 1987 */       this.undecodedChunk.readerIndex(readerIndex);
/* 1988 */       throw new NotEnoughDataDecoderException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String cleanString(String field) {
/* 1998 */     StringBuilder sb = new StringBuilder(field.length());
/* 1999 */     for (int i = 0; i < field.length(); i++) {
/* 2000 */       char nextChar = field.charAt(i);
/* 2001 */       if (nextChar == ':') {
/* 2002 */         sb.append(32);
/* 2003 */       } else if (nextChar == ',') {
/* 2004 */         sb.append(32);
/* 2005 */       } else if (nextChar == '=') {
/* 2006 */         sb.append(32);
/* 2007 */       } else if (nextChar == ';') {
/* 2008 */         sb.append(32);
/* 2009 */       } else if (nextChar == '\t') {
/* 2010 */         sb.append(32);
/* 2011 */       } else if (nextChar != '"') {
/*      */ 
/*      */         
/* 2014 */         sb.append(nextChar);
/*      */       } 
/*      */     } 
/* 2017 */     return sb.toString().trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean skipOneLine() {
/* 2026 */     if (!this.undecodedChunk.isReadable()) {
/* 2027 */       return false;
/*      */     }
/* 2029 */     byte nextByte = this.undecodedChunk.readByte();
/* 2030 */     if (nextByte == 13) {
/* 2031 */       if (!this.undecodedChunk.isReadable()) {
/* 2032 */         this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 2033 */         return false;
/*      */       } 
/* 2035 */       nextByte = this.undecodedChunk.readByte();
/* 2036 */       if (nextByte == 10) {
/* 2037 */         return true;
/*      */       }
/* 2039 */       this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 2);
/* 2040 */       return false;
/*      */     } 
/* 2042 */     if (nextByte == 10) {
/* 2043 */       return true;
/*      */     }
/* 2045 */     this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 2046 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitHeaderContentType(String sb) {
/* 2059 */     int aStart = HttpPostBodyUtil.findNonWhitespace(sb, 0);
/* 2060 */     int aEnd = sb.indexOf(';');
/* 2061 */     if (aEnd == -1) {
/* 2062 */       return new String[] { sb, "" };
/*      */     }
/* 2064 */     if (sb.charAt(aEnd - 1) == ' ') {
/* 2065 */       aEnd--;
/*      */     }
/* 2067 */     int bStart = HttpPostBodyUtil.findNonWhitespace(sb, aEnd + 1);
/* 2068 */     int bEnd = HttpPostBodyUtil.findEndOfString(sb);
/* 2069 */     return new String[] { sb.substring(aStart, aEnd), sb.substring(bStart, bEnd) };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitMultipartHeader(String sb) {
/*      */     String[] values;
/* 2079 */     ArrayList<String> headers = new ArrayList<String>(1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2085 */     int nameStart = HttpPostBodyUtil.findNonWhitespace(sb, 0); int nameEnd;
/* 2086 */     for (nameEnd = nameStart; nameEnd < sb.length(); nameEnd++) {
/* 2087 */       char ch = sb.charAt(nameEnd);
/* 2088 */       if (ch == ':' || Character.isWhitespace(ch))
/*      */         break; 
/*      */     } 
/*      */     int colonEnd;
/* 2092 */     for (colonEnd = nameEnd; colonEnd < sb.length(); colonEnd++) {
/* 2093 */       if (sb.charAt(colonEnd) == ':') {
/* 2094 */         colonEnd++;
/*      */         break;
/*      */       } 
/*      */     } 
/* 2098 */     int valueStart = HttpPostBodyUtil.findNonWhitespace(sb, colonEnd);
/* 2099 */     int valueEnd = HttpPostBodyUtil.findEndOfString(sb);
/* 2100 */     headers.add(sb.substring(nameStart, nameEnd));
/* 2101 */     String svalue = sb.substring(valueStart, valueEnd);
/*      */     
/* 2103 */     if (svalue.indexOf(';') >= 0) {
/* 2104 */       values = StringUtil.split(svalue, ';');
/*      */     } else {
/* 2106 */       values = StringUtil.split(svalue, ',');
/*      */     } 
/* 2108 */     for (String value : values) {
/* 2109 */       headers.add(value.trim());
/*      */     }
/* 2111 */     String[] array = new String[headers.size()];
/* 2112 */     for (int i = 0; i < headers.size(); i++) {
/* 2113 */       array[i] = headers.get(i);
/*      */     }
/* 2115 */     return array;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class NotEnoughDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = -7846841864603865638L;
/*      */ 
/*      */     
/*      */     public NotEnoughDataDecoderException() {}
/*      */ 
/*      */     
/*      */     public NotEnoughDataDecoderException(String msg) {
/* 2129 */       super(msg);
/*      */     }
/*      */     
/*      */     public NotEnoughDataDecoderException(Throwable cause) {
/* 2133 */       super(cause);
/*      */     }
/*      */     
/*      */     public NotEnoughDataDecoderException(String msg, Throwable cause) {
/* 2137 */       super(msg, cause);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class EndOfDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = 1336267941020800769L;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ErrorDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = 5020247425493164465L;
/*      */ 
/*      */     
/*      */     public ErrorDataDecoderException() {}
/*      */     
/*      */     public ErrorDataDecoderException(String msg) {
/* 2158 */       super(msg);
/*      */     }
/*      */     
/*      */     public ErrorDataDecoderException(Throwable cause) {
/* 2162 */       super(cause);
/*      */     }
/*      */     
/*      */     public ErrorDataDecoderException(String msg, Throwable cause) {
/* 2166 */       super(msg, cause);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class IncompatibleDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = -953268047926250267L;
/*      */ 
/*      */     
/*      */     public IncompatibleDataDecoderException() {}
/*      */     
/*      */     public IncompatibleDataDecoderException(String msg) {
/* 2180 */       super(msg);
/*      */     }
/*      */     
/*      */     public IncompatibleDataDecoderException(Throwable cause) {
/* 2184 */       super(cause);
/*      */     }
/*      */     
/*      */     public IncompatibleDataDecoderException(String msg, Throwable cause) {
/* 2188 */       super(msg, cause);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\multipart\HttpPostRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */