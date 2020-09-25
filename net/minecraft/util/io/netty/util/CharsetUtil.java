/*     */ package net.minecraft.util.io.netty.util;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
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
/*     */ public final class CharsetUtil
/*     */ {
/*  35 */   public static final Charset UTF_16 = Charset.forName("UTF-16");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final Charset US_ASCII = Charset.forName("US-ASCII");
/*     */   
/*  63 */   private static final ThreadLocal<Map<Charset, CharsetEncoder>> encoders = new ThreadLocal<Map<Charset, CharsetEncoder>>()
/*     */     {
/*     */       protected Map<Charset, CharsetEncoder> initialValue()
/*     */       {
/*  67 */         return new IdentityHashMap<Charset, CharsetEncoder>();
/*     */       }
/*     */     };
/*     */   
/*  71 */   private static final ThreadLocal<Map<Charset, CharsetDecoder>> decoders = new ThreadLocal<Map<Charset, CharsetDecoder>>()
/*     */     {
/*     */       protected Map<Charset, CharsetDecoder> initialValue()
/*     */       {
/*  75 */         return new IdentityHashMap<Charset, CharsetDecoder>();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharsetEncoder getEncoder(Charset charset) {
/*  84 */     if (charset == null) {
/*  85 */       throw new NullPointerException("charset");
/*     */     }
/*     */     
/*  88 */     Map<Charset, CharsetEncoder> map = encoders.get();
/*  89 */     CharsetEncoder e = map.get(charset);
/*  90 */     if (e != null) {
/*  91 */       e.reset();
/*  92 */       e.onMalformedInput(CodingErrorAction.REPLACE);
/*  93 */       e.onUnmappableCharacter(CodingErrorAction.REPLACE);
/*  94 */       return e;
/*     */     } 
/*     */     
/*  97 */     e = charset.newEncoder();
/*  98 */     e.onMalformedInput(CodingErrorAction.REPLACE);
/*  99 */     e.onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 100 */     map.put(charset, e);
/* 101 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharsetDecoder getDecoder(Charset charset) {
/* 109 */     if (charset == null) {
/* 110 */       throw new NullPointerException("charset");
/*     */     }
/*     */     
/* 113 */     Map<Charset, CharsetDecoder> map = decoders.get();
/* 114 */     CharsetDecoder d = map.get(charset);
/* 115 */     if (d != null) {
/* 116 */       d.reset();
/* 117 */       d.onMalformedInput(CodingErrorAction.REPLACE);
/* 118 */       d.onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 119 */       return d;
/*     */     } 
/*     */     
/* 122 */     d = charset.newDecoder();
/* 123 */     d.onMalformedInput(CodingErrorAction.REPLACE);
/* 124 */     d.onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 125 */     map.put(charset, d);
/* 126 */     return d;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\CharsetUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */