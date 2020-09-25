/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URLEncoder;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.UnsupportedCharsetException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class QueryStringEncoder
/*     */ {
/*     */   private final Charset charset;
/*     */   private final String uri;
/*  42 */   private final List<Param> params = new ArrayList<Param>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringEncoder(String uri) {
/*  49 */     this(uri, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueryStringEncoder(String uri, Charset charset) {
/*  57 */     if (uri == null) {
/*  58 */       throw new NullPointerException("getUri");
/*     */     }
/*  60 */     if (charset == null) {
/*  61 */       throw new NullPointerException("charset");
/*     */     }
/*     */     
/*  64 */     this.uri = uri;
/*  65 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addParam(String name, String value) {
/*  72 */     if (name == null) {
/*  73 */       throw new NullPointerException("name");
/*     */     }
/*  75 */     if (value == null) {
/*  76 */       throw new NullPointerException("value");
/*     */     }
/*  78 */     this.params.add(new Param(name, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI toUri() throws URISyntaxException {
/*  87 */     return new URI(toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  97 */     if (this.params.isEmpty()) {
/*  98 */       return this.uri;
/*     */     }
/* 100 */     StringBuilder sb = (new StringBuilder(this.uri)).append('?');
/* 101 */     for (int i = 0; i < this.params.size(); i++) {
/* 102 */       Param param = this.params.get(i);
/* 103 */       sb.append(encodeComponent(param.name, this.charset));
/* 104 */       sb.append('=');
/* 105 */       sb.append(encodeComponent(param.value, this.charset));
/* 106 */       if (i != this.params.size() - 1) {
/* 107 */         sb.append('&');
/*     */       }
/*     */     } 
/* 110 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String encodeComponent(String s, Charset charset) {
/*     */     try {
/* 117 */       return URLEncoder.encode(s, charset.name()).replace("+", "%20");
/* 118 */     } catch (UnsupportedEncodingException e) {
/* 119 */       throw new UnsupportedCharsetException(charset.name());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class Param
/*     */   {
/*     */     final String name;
/*     */     final String value;
/*     */     
/*     */     Param(String name, String value) {
/* 129 */       this.value = value;
/* 130 */       this.name = name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\QueryStringEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */