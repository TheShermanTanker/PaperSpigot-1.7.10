/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public abstract class SpdyHeaders
/*     */   implements Iterable<Map.Entry<String, String>>
/*     */ {
/*  34 */   public static final SpdyHeaders EMPTY_HEADERS = new SpdyHeaders()
/*     */     {
/*     */       public List<String> getAll(String name)
/*     */       {
/*  38 */         return Collections.emptyList();
/*     */       }
/*     */ 
/*     */       
/*     */       public List<Map.Entry<String, String>> entries() {
/*  43 */         return Collections.emptyList();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean contains(String name) {
/*  48 */         return false;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isEmpty() {
/*  53 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public Set<String> names() {
/*  58 */         return Collections.emptySet();
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders add(String name, Object value) {
/*  63 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders add(String name, Iterable<?> values) {
/*  68 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders set(String name, Object value) {
/*  73 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders set(String name, Iterable<?> values) {
/*  78 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders remove(String name) {
/*  83 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public SpdyHeaders clear() {
/*  88 */         throw new UnsupportedOperationException("read only");
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator<Map.Entry<String, String>> iterator() {
/*  93 */         return entries().iterator();
/*     */       }
/*     */ 
/*     */       
/*     */       public String get(String name) {
/*  98 */         return null;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class HttpNames
/*     */   {
/*     */     public static final String HOST = ":host";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String METHOD = ":method";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String PATH = ":path";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String SCHEME = ":scheme";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String STATUS = ":status";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String VERSION = ":version";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Spdy2HttpNames
/*     */   {
/*     */     public static final String METHOD = "method";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String SCHEME = "scheme";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String STATUS = "status";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String URL = "url";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String VERSION = "version";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getHeader(SpdyHeadersFrame frame, String name) {
/* 170 */     return frame.headers().get(name);
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
/*     */   public static String getHeader(SpdyHeadersFrame frame, String name, String defaultValue) {
/* 182 */     String value = frame.headers().get(name);
/* 183 */     if (value == null) {
/* 184 */       return defaultValue;
/*     */     }
/* 186 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setHeader(SpdyHeadersFrame frame, String name, Object value) {
/* 194 */     frame.headers().set(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setHeader(SpdyHeadersFrame frame, String name, Iterable<?> values) {
/* 202 */     frame.headers().set(name, values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addHeader(SpdyHeadersFrame frame, String name, Object value) {
/* 209 */     frame.headers().add(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeHost(SpdyHeadersFrame frame) {
/* 216 */     frame.headers().remove(":host");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getHost(SpdyHeadersFrame frame) {
/* 223 */     return frame.headers().get(":host");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setHost(SpdyHeadersFrame frame, String host) {
/* 230 */     frame.headers().set(":host", host);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeMethod(int spdyVersion, SpdyHeadersFrame frame) {
/* 237 */     if (spdyVersion < 3) {
/* 238 */       frame.headers().remove("method");
/*     */     } else {
/* 240 */       frame.headers().remove(":method");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpMethod getMethod(int spdyVersion, SpdyHeadersFrame frame) {
/*     */     try {
/* 249 */       if (spdyVersion < 3) {
/* 250 */         return HttpMethod.valueOf(frame.headers().get("method"));
/*     */       }
/* 252 */       return HttpMethod.valueOf(frame.headers().get(":method"));
/*     */     }
/* 254 */     catch (Exception e) {
/* 255 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setMethod(int spdyVersion, SpdyHeadersFrame frame, HttpMethod method) {
/* 263 */     if (spdyVersion < 3) {
/* 264 */       frame.headers().set("method", method.name());
/*     */     } else {
/* 266 */       frame.headers().set(":method", method.name());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeScheme(int spdyVersion, SpdyHeadersFrame frame) {
/* 274 */     if (spdyVersion < 2) {
/* 275 */       frame.headers().remove("scheme");
/*     */     } else {
/* 277 */       frame.headers().remove(":scheme");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getScheme(int spdyVersion, SpdyHeadersFrame frame) {
/* 285 */     if (spdyVersion < 3) {
/* 286 */       return frame.headers().get("scheme");
/*     */     }
/* 288 */     return frame.headers().get(":scheme");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setScheme(int spdyVersion, SpdyHeadersFrame frame, String scheme) {
/* 296 */     if (spdyVersion < 3) {
/* 297 */       frame.headers().set("scheme", scheme);
/*     */     } else {
/* 299 */       frame.headers().set(":scheme", scheme);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeStatus(int spdyVersion, SpdyHeadersFrame frame) {
/* 307 */     if (spdyVersion < 3) {
/* 308 */       frame.headers().remove("status");
/*     */     } else {
/* 310 */       frame.headers().remove(":status");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpResponseStatus getStatus(int spdyVersion, SpdyHeadersFrame frame) {
/*     */     try {
/*     */       String status;
/* 320 */       if (spdyVersion < 3) {
/* 321 */         status = frame.headers().get("status");
/*     */       } else {
/* 323 */         status = frame.headers().get(":status");
/*     */       } 
/* 325 */       int space = status.indexOf(' ');
/* 326 */       if (space == -1) {
/* 327 */         return HttpResponseStatus.valueOf(Integer.parseInt(status));
/*     */       }
/* 329 */       int code = Integer.parseInt(status.substring(0, space));
/* 330 */       String reasonPhrase = status.substring(space + 1);
/* 331 */       HttpResponseStatus responseStatus = HttpResponseStatus.valueOf(code);
/* 332 */       if (responseStatus.reasonPhrase().equals(reasonPhrase)) {
/* 333 */         return responseStatus;
/*     */       }
/* 335 */       return new HttpResponseStatus(code, reasonPhrase);
/*     */     
/*     */     }
/* 338 */     catch (Exception e) {
/* 339 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setStatus(int spdyVersion, SpdyHeadersFrame frame, HttpResponseStatus status) {
/* 347 */     if (spdyVersion < 3) {
/* 348 */       frame.headers().set("status", status.toString());
/*     */     } else {
/* 350 */       frame.headers().set(":status", status.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeUrl(int spdyVersion, SpdyHeadersFrame frame) {
/* 358 */     if (spdyVersion < 3) {
/* 359 */       frame.headers().remove("url");
/*     */     } else {
/* 361 */       frame.headers().remove(":path");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getUrl(int spdyVersion, SpdyHeadersFrame frame) {
/* 369 */     if (spdyVersion < 3) {
/* 370 */       return frame.headers().get("url");
/*     */     }
/* 372 */     return frame.headers().get(":path");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setUrl(int spdyVersion, SpdyHeadersFrame frame, String path) {
/* 380 */     if (spdyVersion < 3) {
/* 381 */       frame.headers().set("url", path);
/*     */     } else {
/* 383 */       frame.headers().set(":path", path);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeVersion(int spdyVersion, SpdyHeadersFrame frame) {
/* 391 */     if (spdyVersion < 3) {
/* 392 */       frame.headers().remove("version");
/*     */     } else {
/* 394 */       frame.headers().remove(":version");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpVersion getVersion(int spdyVersion, SpdyHeadersFrame frame) {
/*     */     try {
/* 403 */       if (spdyVersion < 3) {
/* 404 */         return HttpVersion.valueOf(frame.headers().get("version"));
/*     */       }
/* 406 */       return HttpVersion.valueOf(frame.headers().get(":version"));
/*     */     }
/* 408 */     catch (Exception e) {
/* 409 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setVersion(int spdyVersion, SpdyHeadersFrame frame, HttpVersion httpVersion) {
/* 417 */     if (spdyVersion < 3) {
/* 418 */       frame.headers().set("version", httpVersion.text());
/*     */     } else {
/* 420 */       frame.headers().set(":version", httpVersion.text());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<String, String>> iterator() {
/* 425 */     return entries().iterator();
/*     */   }
/*     */   
/*     */   public abstract String get(String paramString);
/*     */   
/*     */   public abstract List<String> getAll(String paramString);
/*     */   
/*     */   public abstract List<Map.Entry<String, String>> entries();
/*     */   
/*     */   public abstract boolean contains(String paramString);
/*     */   
/*     */   public abstract Set<String> names();
/*     */   
/*     */   public abstract SpdyHeaders add(String paramString, Object paramObject);
/*     */   
/*     */   public abstract SpdyHeaders add(String paramString, Iterable<?> paramIterable);
/*     */   
/*     */   public abstract SpdyHeaders set(String paramString, Object paramObject);
/*     */   
/*     */   public abstract SpdyHeaders set(String paramString, Iterable<?> paramIterable);
/*     */   
/*     */   public abstract SpdyHeaders remove(String paramString);
/*     */   
/*     */   public abstract SpdyHeaders clear();
/*     */   
/*     */   public abstract boolean isEmpty();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */