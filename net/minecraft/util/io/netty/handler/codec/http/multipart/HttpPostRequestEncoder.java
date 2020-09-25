/*      */ package net.minecraft.util.io.netty.handler.codec.http.multipart;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URLEncoder;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Pattern;
/*      */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*      */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*      */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*      */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*      */ import net.minecraft.util.io.netty.handler.codec.DecoderResult;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.DefaultFullHttpRequest;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.DefaultHttpContent;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpMessage;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpConstants;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpContent;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpHeaders;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpMessage;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpMethod;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpRequest;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.HttpVersion;
/*      */ import net.minecraft.util.io.netty.handler.codec.http.LastHttpContent;
/*      */ import net.minecraft.util.io.netty.handler.stream.ChunkedInput;
/*      */ import net.minecraft.util.io.netty.util.ReferenceCounted;
/*      */ import net.minecraft.util.io.netty.util.internal.ThreadLocalRandom;
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
/*      */ public class HttpPostRequestEncoder
/*      */   implements ChunkedInput<HttpContent>
/*      */ {
/*      */   public enum EncoderMode
/*      */   {
/*   60 */     RFC1738,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   65 */     RFC3986;
/*      */   }
/*      */   
/*   68 */   private static final Map<Pattern, String> percentEncodings = new HashMap<Pattern, String>(); private final HttpDataFactory factory; private final HttpRequest request; private final Charset charset; private boolean isChunked; private final List<InterfaceHttpData> bodyListDatas; private final List<InterfaceHttpData> multipartHttpDatas; private final boolean isMultipart; private String multipartDataBoundary; private String multipartMixedBoundary; private boolean headerFinalized; private final EncoderMode encoderMode; private boolean isLastChunk; private boolean isLastChunkSent; private FileUpload currentFileUpload; private boolean duringMixedMode; private long globalBodySize; private ListIterator<InterfaceHttpData> iterator; private ByteBuf currentBuffer; private InterfaceHttpData currentData; private boolean isKey;
/*      */   
/*      */   static {
/*   71 */     percentEncodings.put(Pattern.compile("\\*"), "%2A");
/*   72 */     percentEncodings.put(Pattern.compile("\\+"), "%20");
/*   73 */     percentEncodings.put(Pattern.compile("%7E"), "~");
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
/*      */   public HttpPostRequestEncoder(HttpRequest request, boolean multipart) throws ErrorDataEncoderException {
/*  138 */     this(new DefaultHttpDataFactory(16384L), request, multipart, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
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
/*      */   public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart) throws ErrorDataEncoderException {
/*  157 */     this(factory, request, multipart, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
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
/*      */   public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart, Charset charset, EncoderMode encoderMode) throws ErrorDataEncoderException {
/*  766 */     this.isKey = true; if (factory == null) throw new NullPointerException("factory");  if (request == null) throw new NullPointerException("request");  if (charset == null) throw new NullPointerException("charset");  if (request.getMethod() != HttpMethod.POST) throw new ErrorDataEncoderException("Cannot create a Encoder if not a POST");  this.request = request; this.charset = charset; this.factory = factory; this.bodyListDatas = new ArrayList<InterfaceHttpData>(); this.isLastChunk = false; this.isLastChunkSent = false; this.isMultipart = multipart; this.multipartHttpDatas = new ArrayList<InterfaceHttpData>(); this.encoderMode = encoderMode; if (this.isMultipart) initDataMultipart(); 
/*      */   } public void cleanFiles() { this.factory.cleanRequestHttpDatas(this.request); } public boolean isMultipart() { return this.isMultipart; }
/*      */   private void initDataMultipart() { this.multipartDataBoundary = getNewMultipartDelimiter(); }
/*      */   private void initMixedMultipart() { this.multipartMixedBoundary = getNewMultipartDelimiter(); }
/*      */   private static String getNewMultipartDelimiter() { return Long.toHexString(ThreadLocalRandom.current().nextLong()).toLowerCase(); }
/*      */   public List<InterfaceHttpData> getBodyListAttributes() { return this.bodyListDatas; }
/*      */   public void setBodyHttpDatas(List<InterfaceHttpData> datas) throws ErrorDataEncoderException { if (datas == null) throw new NullPointerException("datas");  this.globalBodySize = 0L; this.bodyListDatas.clear(); this.currentFileUpload = null; this.duringMixedMode = false; this.multipartHttpDatas.clear(); for (InterfaceHttpData data : datas) addBodyHttpData(data);  }
/*  773 */   private ByteBuf fillByteBuf() { int length = this.currentBuffer.readableBytes();
/*  774 */     if (length > 8096) {
/*  775 */       ByteBuf byteBuf = this.currentBuffer.slice(this.currentBuffer.readerIndex(), 8096);
/*  776 */       this.currentBuffer.skipBytes(8096);
/*  777 */       return byteBuf;
/*      */     } 
/*      */     
/*  780 */     ByteBuf slice = this.currentBuffer;
/*  781 */     this.currentBuffer = null;
/*  782 */     return slice; } public void addBodyAttribute(String name, String value) throws ErrorDataEncoderException { if (name == null)
/*      */       throw new NullPointerException("name");  String svalue = value; if (value == null)
/*      */       svalue = "";  Attribute data = this.factory.createAttribute(this.request, name, svalue); addBodyHttpData(data); }
/*      */   public void addBodyFileUpload(String name, File file, String contentType, boolean isText) throws ErrorDataEncoderException { if (name == null)
/*      */       throw new NullPointerException("name");  if (file == null)
/*      */       throw new NullPointerException("file");  String scontentType = contentType; String contentTransferEncoding = null; if (contentType == null)
/*      */       if (isText) { scontentType = "text/plain"; }
/*      */       else { scontentType = "application/octet-stream"; }
/*      */         if (!isText)
/*      */       contentTransferEncoding = HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value();  FileUpload fileUpload = this.factory.createFileUpload(this.request, name, file.getName(), scontentType, contentTransferEncoding, null, file.length()); try { fileUpload.setContent(file); }
/*      */     catch (IOException e) { throw new ErrorDataEncoderException(e); }
/*      */      addBodyHttpData(fileUpload); }
/*      */   public void addBodyFileUploads(String name, File[] file, String[] contentType, boolean[] isText) throws ErrorDataEncoderException { if (file.length != contentType.length && file.length != isText.length)
/*      */       throw new NullPointerException("Different array length");  for (int i = 0; i < file.length; i++)
/*      */       addBodyFileUpload(name, file[i], contentType[i], isText[i]);  }
/*  797 */   private HttpContent encodeNextChunkMultipart(int sizeleft) throws ErrorDataEncoderException { if (this.currentData == null) {
/*  798 */       return null;
/*      */     }
/*      */     
/*  801 */     if (this.currentData instanceof InternalAttribute) {
/*  802 */       byteBuf = ((InternalAttribute)this.currentData).toByteBuf();
/*  803 */       this.currentData = null;
/*      */     } else {
/*  805 */       if (this.currentData instanceof Attribute) {
/*      */         try {
/*  807 */           byteBuf = ((Attribute)this.currentData).getChunk(sizeleft);
/*  808 */         } catch (IOException e) {
/*  809 */           throw new ErrorDataEncoderException(e);
/*      */         } 
/*      */       } else {
/*      */         try {
/*  813 */           byteBuf = ((HttpData)this.currentData).getChunk(sizeleft);
/*  814 */         } catch (IOException e) {
/*  815 */           throw new ErrorDataEncoderException(e);
/*      */         } 
/*      */       } 
/*  818 */       if (byteBuf.capacity() == 0) {
/*      */         
/*  820 */         this.currentData = null;
/*  821 */         return null;
/*      */       } 
/*      */     } 
/*  824 */     if (this.currentBuffer == null) {
/*  825 */       this.currentBuffer = byteBuf;
/*      */     } else {
/*  827 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, byteBuf });
/*      */     } 
/*  829 */     if (this.currentBuffer.readableBytes() < 8096) {
/*  830 */       this.currentData = null;
/*  831 */       return null;
/*      */     } 
/*  833 */     ByteBuf byteBuf = fillByteBuf();
/*  834 */     return (HttpContent)new DefaultHttpContent(byteBuf); }
/*      */   public void addBodyHttpData(InterfaceHttpData data) throws ErrorDataEncoderException { if (this.headerFinalized)
/*      */       throw new ErrorDataEncoderException("Cannot add value once finalized");  if (data == null)
/*      */       throw new NullPointerException("data");  this.bodyListDatas.add(data); if (!this.isMultipart) { if (data instanceof Attribute) { Attribute attribute = (Attribute)data; try { String key = encodeAttribute(attribute.getName(), this.charset); String value = encodeAttribute(attribute.getValue(), this.charset); Attribute newattribute = this.factory.createAttribute(this.request, key, value); this.multipartHttpDatas.add(newattribute); this.globalBodySize += (newattribute.getName().length() + 1) + newattribute.length() + 1L; } catch (IOException e) { throw new ErrorDataEncoderException(e); }  } else if (data instanceof FileUpload) { FileUpload fileUpload = (FileUpload)data; String key = encodeAttribute(fileUpload.getName(), this.charset); String value = encodeAttribute(fileUpload.getFilename(), this.charset); Attribute newattribute = this.factory.createAttribute(this.request, key, value); this.multipartHttpDatas.add(newattribute); this.globalBodySize += (newattribute.getName().length() + 1) + newattribute.length() + 1L; }  return; }  if (data instanceof Attribute) { if (this.duringMixedMode) { InternalAttribute internalAttribute = new InternalAttribute(this.charset); internalAttribute.addValue("\r\n--" + this.multipartMixedBoundary + "--"); this.multipartHttpDatas.add(internalAttribute); this.multipartMixedBoundary = null; this.currentFileUpload = null; this.duringMixedMode = false; }  InternalAttribute internal = new InternalAttribute(this.charset); if (!this.multipartHttpDatas.isEmpty())
/*      */         internal.addValue("\r\n");  internal.addValue("--" + this.multipartDataBoundary + "\r\n"); Attribute attribute = (Attribute)data; internal.addValue("Content-Disposition: form-data; name=\"" + attribute.getName() + "\"\r\n"); Charset localcharset = attribute.getCharset(); if (localcharset != null)
/*      */         internal.addValue("Content-Type: text/plain; charset=" + localcharset + "\r\n");  internal.addValue("\r\n"); this.multipartHttpDatas.add(internal); this.multipartHttpDatas.add(data); this.globalBodySize += attribute.length() + internal.size(); } else if (data instanceof FileUpload) { boolean localMixed; FileUpload fileUpload = (FileUpload)data; InternalAttribute internal = new InternalAttribute(this.charset); if (!this.multipartHttpDatas.isEmpty())
/*      */         internal.addValue("\r\n");  if (this.duringMixedMode) { if (this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload.getName())) { localMixed = true; } else { internal.addValue("--" + this.multipartMixedBoundary + "--"); this.multipartHttpDatas.add(internal); this.multipartMixedBoundary = null; internal = new InternalAttribute(this.charset); internal.addValue("\r\n"); localMixed = false; this.currentFileUpload = fileUpload; this.duringMixedMode = false; }  } else if (this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload.getName())) { initMixedMultipart(); InternalAttribute pastAttribute = (InternalAttribute)this.multipartHttpDatas.get(this.multipartHttpDatas.size() - 2); this.globalBodySize -= pastAttribute.size(); String replacement = "Content-Disposition: form-data; name=\"" + fileUpload.getName() + "\"\r\n"; replacement = replacement + "Content-Type: multipart/mixed; boundary=" + this.multipartMixedBoundary + "\r\n\r\n"; replacement = replacement + "--" + this.multipartMixedBoundary + "\r\n"; replacement = replacement + "Content-Disposition: file; filename=\"" + fileUpload.getFilename() + "\"\r\n"; pastAttribute.setValue(replacement, 1); this.globalBodySize += pastAttribute.size(); localMixed = true; this.duringMixedMode = true; }
/*      */       else { localMixed = false; this.currentFileUpload = fileUpload; this.duringMixedMode = false; }
/*      */        if (localMixed) { internal.addValue("--" + this.multipartMixedBoundary + "\r\n"); internal.addValue("Content-Disposition: file; filename=\"" + fileUpload.getFilename() + "\"\r\n"); }
/*      */       else { internal.addValue("--" + this.multipartDataBoundary + "\r\n"); internal.addValue("Content-Disposition: form-data; name=\"" + fileUpload.getName() + "\"; " + "filename" + "=\"" + fileUpload.getFilename() + "\"\r\n"); }
/*      */        internal.addValue("Content-Type: " + fileUpload.getContentType()); String contentTransferEncoding = fileUpload.getContentTransferEncoding(); if (contentTransferEncoding != null && contentTransferEncoding.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) { internal.addValue("\r\nContent-Transfer-Encoding: " + HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value() + "\r\n\r\n"); }
/*      */       else if (fileUpload.getCharset() != null) { internal.addValue("; charset=" + fileUpload.getCharset() + "\r\n\r\n"); }
/*      */       else { internal.addValue("\r\n\r\n"); }
/*      */        this.multipartHttpDatas.add(internal); this.multipartHttpDatas.add(data); this.globalBodySize += fileUpload.length() + internal.size(); }
/*  848 */      } private HttpContent encodeNextChunkUrlEncoded(int sizeleft) throws ErrorDataEncoderException { if (this.currentData == null) {
/*  849 */       return null;
/*      */     }
/*  851 */     int size = sizeleft;
/*      */ 
/*      */ 
/*      */     
/*  855 */     if (this.isKey) {
/*  856 */       String key = this.currentData.getName();
/*  857 */       buffer = Unpooled.wrappedBuffer(key.getBytes());
/*  858 */       this.isKey = false;
/*  859 */       if (this.currentBuffer == null) {
/*  860 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { buffer, Unpooled.wrappedBuffer("=".getBytes()) });
/*      */         
/*  862 */         size -= buffer.readableBytes() + 1;
/*      */       } else {
/*  864 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, buffer, Unpooled.wrappedBuffer("=".getBytes()) });
/*      */         
/*  866 */         size -= buffer.readableBytes() + 1;
/*      */       } 
/*  868 */       if (this.currentBuffer.readableBytes() >= 8096) {
/*  869 */         buffer = fillByteBuf();
/*  870 */         return (HttpContent)new DefaultHttpContent(buffer);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  876 */       buffer = ((HttpData)this.currentData).getChunk(size);
/*  877 */     } catch (IOException e) {
/*  878 */       throw new ErrorDataEncoderException(e);
/*      */     } 
/*      */ 
/*      */     
/*  882 */     ByteBuf delimiter = null;
/*  883 */     if (buffer.readableBytes() < size) {
/*  884 */       this.isKey = true;
/*  885 */       delimiter = this.iterator.hasNext() ? Unpooled.wrappedBuffer("&".getBytes()) : null;
/*      */     } 
/*      */ 
/*      */     
/*  889 */     if (buffer.capacity() == 0) {
/*  890 */       this.currentData = null;
/*  891 */       if (this.currentBuffer == null) {
/*  892 */         this.currentBuffer = delimiter;
/*      */       }
/*  894 */       else if (delimiter != null) {
/*  895 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, delimiter });
/*      */       } 
/*      */       
/*  898 */       if (this.currentBuffer.readableBytes() >= 8096) {
/*  899 */         buffer = fillByteBuf();
/*  900 */         return (HttpContent)new DefaultHttpContent(buffer);
/*      */       } 
/*  902 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  906 */     if (this.currentBuffer == null) {
/*  907 */       if (delimiter != null) {
/*  908 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { buffer, delimiter });
/*      */       } else {
/*  910 */         this.currentBuffer = buffer;
/*      */       }
/*      */     
/*  913 */     } else if (delimiter != null) {
/*  914 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, buffer, delimiter });
/*      */     } else {
/*  916 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, buffer });
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  921 */     if (this.currentBuffer.readableBytes() < 8096) {
/*  922 */       this.currentData = null;
/*  923 */       this.isKey = true;
/*  924 */       return null;
/*      */     } 
/*      */     
/*  927 */     ByteBuf buffer = fillByteBuf();
/*  928 */     return (HttpContent)new DefaultHttpContent(buffer); }
/*      */   public HttpRequest finalizeRequest() throws ErrorDataEncoderException { if (!this.headerFinalized) { if (this.isMultipart) { InternalAttribute internal = new InternalAttribute(this.charset); if (this.duringMixedMode)
/*      */           internal.addValue("\r\n--" + this.multipartMixedBoundary + "--");  internal.addValue("\r\n--" + this.multipartDataBoundary + "--\r\n"); this.multipartHttpDatas.add(internal); this.multipartMixedBoundary = null; this.currentFileUpload = null; this.duringMixedMode = false; this.globalBodySize += internal.size(); }  this.headerFinalized = true; } else { throw new ErrorDataEncoderException("Header already encoded"); }  HttpHeaders headers = this.request.headers(); List<String> contentTypes = headers.getAll("Content-Type"); List<String> transferEncoding = headers.getAll("Transfer-Encoding"); if (contentTypes != null) { headers.remove("Content-Type"); for (String contentType : contentTypes) { if (contentType.toLowerCase().startsWith("multipart/form-data"))
/*      */           continue;  if (contentType.toLowerCase().startsWith("application/x-www-form-urlencoded"))
/*      */           continue;  headers.add("Content-Type", contentType); }  }  if (this.isMultipart) { String value = "multipart/form-data; boundary=" + this.multipartDataBoundary; headers.add("Content-Type", value); } else { headers.add("Content-Type", "application/x-www-form-urlencoded"); }  long realSize = this.globalBodySize; if (this.isMultipart) { this.iterator = this.multipartHttpDatas.listIterator(); }
/*      */     else { realSize--; this.iterator = this.multipartHttpDatas.listIterator(); }
/*      */      headers.set("Content-Length", String.valueOf(realSize)); if (realSize > 8096L || this.isMultipart) { this.isChunked = true; if (transferEncoding != null) { headers.remove("Transfer-Encoding"); for (String v : transferEncoding) { if (v.equalsIgnoreCase("chunked"))
/*      */             continue;  headers.add("Transfer-Encoding", v); }
/*      */          }
/*      */        HttpHeaders.setTransferEncodingChunked((HttpMessage)this.request); return new WrappedHttpRequest(this.request); }
/*      */      HttpContent chunk = nextChunk(); if (this.request instanceof FullHttpRequest) { FullHttpRequest fullRequest = (FullHttpRequest)this.request; ByteBuf chunkContent = chunk.content(); if (fullRequest.content() != chunkContent) { fullRequest.content().clear().writeBytes(chunkContent); chunkContent.release(); }
/*      */        return (HttpRequest)fullRequest; }
/*      */      return new WrappedFullHttpRequest(this.request, chunk); }
/*      */   public boolean isChunked() { return this.isChunked; }
/*      */   private String encodeAttribute(String s, Charset charset) throws ErrorDataEncoderException { if (s == null)
/*      */       return "";  try { String encoded = URLEncoder.encode(s, charset.name()); if (this.encoderMode == EncoderMode.RFC3986)
/*      */         for (Map.Entry<Pattern, String> entry : percentEncodings.entrySet()) { String replacement = entry.getValue(); encoded = ((Pattern)entry.getKey()).matcher(encoded).replaceAll(replacement); }
/*      */           return encoded; }
/*      */     catch (UnsupportedEncodingException e) { throw new ErrorDataEncoderException(charset.name(), e); }
/*  947 */      } public void close() throws Exception {} public HttpContent readChunk(ChannelHandlerContext ctx) throws Exception { if (this.isLastChunkSent) {
/*  948 */       return null;
/*      */     }
/*  950 */     return nextChunk(); }
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
/*      */   private HttpContent nextChunk() throws ErrorDataEncoderException {
/*  963 */     if (this.isLastChunk) {
/*  964 */       this.isLastChunkSent = true;
/*  965 */       return (HttpContent)LastHttpContent.EMPTY_LAST_CONTENT;
/*      */     } 
/*      */     
/*  968 */     int size = 8096;
/*      */     
/*  970 */     if (this.currentBuffer != null) {
/*  971 */       size -= this.currentBuffer.readableBytes();
/*      */     }
/*  973 */     if (size <= 0) {
/*      */       
/*  975 */       ByteBuf byteBuf = fillByteBuf();
/*  976 */       return (HttpContent)new DefaultHttpContent(byteBuf);
/*      */     } 
/*      */     
/*  979 */     if (this.currentData != null) {
/*      */       
/*  981 */       if (this.isMultipart) {
/*  982 */         HttpContent chunk = encodeNextChunkMultipart(size);
/*  983 */         if (chunk != null) {
/*  984 */           return chunk;
/*      */         }
/*      */       } else {
/*  987 */         HttpContent chunk = encodeNextChunkUrlEncoded(size);
/*  988 */         if (chunk != null)
/*      */         {
/*  990 */           return chunk;
/*      */         }
/*      */       } 
/*  993 */       size = 8096 - this.currentBuffer.readableBytes();
/*      */     } 
/*  995 */     if (!this.iterator.hasNext()) {
/*  996 */       this.isLastChunk = true;
/*      */       
/*  998 */       ByteBuf byteBuf = this.currentBuffer;
/*  999 */       this.currentBuffer = null;
/* 1000 */       return (HttpContent)new DefaultHttpContent(byteBuf);
/*      */     } 
/* 1002 */     while (size > 0 && this.iterator.hasNext()) {
/* 1003 */       HttpContent chunk; this.currentData = this.iterator.next();
/*      */       
/* 1005 */       if (this.isMultipart) {
/* 1006 */         chunk = encodeNextChunkMultipart(size);
/*      */       } else {
/* 1008 */         chunk = encodeNextChunkUrlEncoded(size);
/*      */       } 
/* 1010 */       if (chunk == null) {
/*      */         
/* 1012 */         size = 8096 - this.currentBuffer.readableBytes();
/*      */         
/*      */         continue;
/*      */       } 
/* 1016 */       return chunk;
/*      */     } 
/*      */     
/* 1019 */     this.isLastChunk = true;
/* 1020 */     if (this.currentBuffer == null) {
/* 1021 */       this.isLastChunkSent = true;
/*      */       
/* 1023 */       return (HttpContent)LastHttpContent.EMPTY_LAST_CONTENT;
/*      */     } 
/*      */     
/* 1026 */     ByteBuf buffer = this.currentBuffer;
/* 1027 */     this.currentBuffer = null;
/* 1028 */     return (HttpContent)new DefaultHttpContent(buffer);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEndOfInput() throws Exception {
/* 1033 */     return this.isLastChunkSent;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ErrorDataEncoderException
/*      */     extends Exception
/*      */   {
/*      */     private static final long serialVersionUID = 5020247425493164465L;
/*      */ 
/*      */     
/*      */     public ErrorDataEncoderException() {}
/*      */     
/*      */     public ErrorDataEncoderException(String msg) {
/* 1046 */       super(msg);
/*      */     }
/*      */     
/*      */     public ErrorDataEncoderException(Throwable cause) {
/* 1050 */       super(cause);
/*      */     }
/*      */     
/*      */     public ErrorDataEncoderException(String msg, Throwable cause) {
/* 1054 */       super(msg, cause);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class WrappedHttpRequest implements HttpRequest { private final HttpRequest request;
/*      */     
/*      */     WrappedHttpRequest(HttpRequest request) {
/* 1061 */       this.request = request;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpRequest setProtocolVersion(HttpVersion version) {
/* 1066 */       this.request.setProtocolVersion(version);
/* 1067 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpRequest setMethod(HttpMethod method) {
/* 1072 */       this.request.setMethod(method);
/* 1073 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpRequest setUri(String uri) {
/* 1078 */       this.request.setUri(uri);
/* 1079 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpMethod getMethod() {
/* 1084 */       return this.request.getMethod();
/*      */     }
/*      */ 
/*      */     
/*      */     public String getUri() {
/* 1089 */       return this.request.getUri();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpVersion getProtocolVersion() {
/* 1094 */       return this.request.getProtocolVersion();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpHeaders headers() {
/* 1099 */       return this.request.headers();
/*      */     }
/*      */ 
/*      */     
/*      */     public DecoderResult getDecoderResult() {
/* 1104 */       return this.request.getDecoderResult();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setDecoderResult(DecoderResult result) {
/* 1109 */       this.request.setDecoderResult(result);
/*      */     } }
/*      */   
/*      */   private static final class WrappedFullHttpRequest extends WrappedHttpRequest implements FullHttpRequest {
/*      */     private final HttpContent content;
/*      */     
/*      */     private WrappedFullHttpRequest(HttpRequest request, HttpContent content) {
/* 1116 */       super(request);
/* 1117 */       this.content = content;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest setProtocolVersion(HttpVersion version) {
/* 1122 */       super.setProtocolVersion(version);
/* 1123 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest setMethod(HttpMethod method) {
/* 1128 */       super.setMethod(method);
/* 1129 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest setUri(String uri) {
/* 1134 */       super.setUri(uri);
/* 1135 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest copy() {
/* 1140 */       DefaultFullHttpRequest copy = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().copy());
/*      */       
/* 1142 */       copy.headers().set(headers());
/* 1143 */       copy.trailingHeaders().set(trailingHeaders());
/* 1144 */       return (FullHttpRequest)copy;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest duplicate() {
/* 1149 */       DefaultFullHttpRequest duplicate = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().duplicate());
/*      */       
/* 1151 */       duplicate.headers().set(headers());
/* 1152 */       duplicate.trailingHeaders().set(trailingHeaders());
/* 1153 */       return (FullHttpRequest)duplicate;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest retain(int increment) {
/* 1158 */       this.content.retain(increment);
/* 1159 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest retain() {
/* 1164 */       this.content.retain();
/* 1165 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf content() {
/* 1170 */       return this.content.content();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpHeaders trailingHeaders() {
/* 1175 */       if (this.content instanceof LastHttpContent) {
/* 1176 */         return ((LastHttpContent)this.content).trailingHeaders();
/*      */       }
/* 1178 */       return HttpHeaders.EMPTY_HEADERS;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int refCnt() {
/* 1184 */       return this.content.refCnt();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean release() {
/* 1189 */       return this.content.release();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean release(int decrement) {
/* 1194 */       return this.content.release(decrement);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\multipart\HttpPostRequestEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */