/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ public class HttpMethod
/*     */   implements Comparable<HttpMethod>
/*     */ {
/*  34 */   public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public static final HttpMethod GET = new HttpMethod("GET");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final HttpMethod HEAD = new HttpMethod("HEAD");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final HttpMethod POST = new HttpMethod("POST");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final HttpMethod PUT = new HttpMethod("PUT");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static final HttpMethod PATCH = new HttpMethod("PATCH");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final HttpMethod DELETE = new HttpMethod("DELETE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public static final HttpMethod TRACE = new HttpMethod("TRACE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final HttpMethod CONNECT = new HttpMethod("CONNECT");
/*     */   
/*  86 */   private static final Map<String, HttpMethod> methodMap = new HashMap<String, HttpMethod>();
/*     */   private final String name;
/*     */   
/*     */   static {
/*  90 */     methodMap.put(OPTIONS.toString(), OPTIONS);
/*  91 */     methodMap.put(GET.toString(), GET);
/*  92 */     methodMap.put(HEAD.toString(), HEAD);
/*  93 */     methodMap.put(POST.toString(), POST);
/*  94 */     methodMap.put(PUT.toString(), PUT);
/*  95 */     methodMap.put(PATCH.toString(), PATCH);
/*  96 */     methodMap.put(DELETE.toString(), DELETE);
/*  97 */     methodMap.put(TRACE.toString(), TRACE);
/*  98 */     methodMap.put(CONNECT.toString(), CONNECT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpMethod valueOf(String name) {
/* 107 */     if (name == null) {
/* 108 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 111 */     name = name.trim();
/* 112 */     if (name.isEmpty()) {
/* 113 */       throw new IllegalArgumentException("empty name");
/*     */     }
/*     */     
/* 116 */     HttpMethod result = methodMap.get(name);
/* 117 */     if (result != null) {
/* 118 */       return result;
/*     */     }
/* 120 */     return new HttpMethod(name);
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
/*     */   public HttpMethod(String name) {
/* 134 */     if (name == null) {
/* 135 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 138 */     name = name.trim();
/* 139 */     if (name.isEmpty()) {
/* 140 */       throw new IllegalArgumentException("empty name");
/*     */     }
/*     */     
/* 143 */     for (int i = 0; i < name.length(); i++) {
/* 144 */       if (Character.isISOControl(name.charAt(i)) || Character.isWhitespace(name.charAt(i)))
/*     */       {
/* 146 */         throw new IllegalArgumentException("invalid character in name");
/*     */       }
/*     */     } 
/*     */     
/* 150 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/* 157 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 162 */     return name().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 167 */     if (!(o instanceof HttpMethod)) {
/* 168 */       return false;
/*     */     }
/*     */     
/* 171 */     HttpMethod that = (HttpMethod)o;
/* 172 */     return name().equals(that.name());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 177 */     return name();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(HttpMethod o) {
/* 182 */     return name().compareTo(o.name());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */