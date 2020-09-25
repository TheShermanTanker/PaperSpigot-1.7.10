/*      */ package net.minecraft.util.io.netty.handler.codec.http;
/*      */ 
/*      */ import java.text.ParseException;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class HttpHeaders
/*      */   implements Iterable<Map.Entry<String, String>>
/*      */ {
/*   35 */   public static final HttpHeaders EMPTY_HEADERS = new HttpHeaders()
/*      */     {
/*      */       public String get(String name) {
/*   38 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public List<String> getAll(String name) {
/*   43 */         return Collections.emptyList();
/*      */       }
/*      */ 
/*      */       
/*      */       public List<Map.Entry<String, String>> entries() {
/*   48 */         return Collections.emptyList();
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean contains(String name) {
/*   53 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isEmpty() {
/*   58 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Set<String> names() {
/*   63 */         return Collections.emptySet();
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders add(String name, Object value) {
/*   68 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders add(String name, Iterable<?> values) {
/*   73 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders set(String name, Object value) {
/*   78 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders set(String name, Iterable<?> values) {
/*   83 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders remove(String name) {
/*   88 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public HttpHeaders clear() {
/*   93 */         throw new UnsupportedOperationException("read only");
/*      */       }
/*      */ 
/*      */       
/*      */       public Iterator<Map.Entry<String, String>> iterator() {
/*   98 */         return entries().iterator();
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Names
/*      */   {
/*      */     public static final String ACCEPT = "Accept";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCEPT_CHARSET = "Accept-Charset";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCEPT_ENCODING = "Accept-Encoding";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCEPT_LANGUAGE = "Accept-Language";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCEPT_RANGES = "Accept-Ranges";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCEPT_PATCH = "Accept-Patch";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String AGE = "Age";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ALLOW = "Allow";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String AUTHORIZATION = "Authorization";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CACHE_CONTROL = "Cache-Control";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONNECTION = "Connection";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_BASE = "Content-Base";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_ENCODING = "Content-Encoding";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_LANGUAGE = "Content-Language";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_LENGTH = "Content-Length";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_LOCATION = "Content-Location";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_MD5 = "Content-MD5";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_RANGE = "Content-Range";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTENT_TYPE = "Content-Type";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String COOKIE = "Cookie";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String DATE = "Date";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ETAG = "ETag";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String EXPECT = "Expect";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String EXPIRES = "Expires";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String FROM = "From";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String HOST = "Host";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IF_MATCH = "If-Match";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IF_NONE_MATCH = "If-None-Match";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IF_RANGE = "If-Range";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String LAST_MODIFIED = "Last-Modified";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String LOCATION = "Location";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MAX_FORWARDS = "Max-Forwards";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ORIGIN = "Origin";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PRAGMA = "Pragma";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String RANGE = "Range";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String REFERER = "Referer";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String RETRY_AFTER = "Retry-After";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SERVER = "Server";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SET_COOKIE = "Set-Cookie";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String SET_COOKIE2 = "Set-Cookie2";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String TE = "TE";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String TRAILER = "Trailer";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String TRANSFER_ENCODING = "Transfer-Encoding";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String UPGRADE = "Upgrade";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String USER_AGENT = "User-Agent";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String VARY = "Vary";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String VIA = "Via";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WARNING = "Warning";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WEBSOCKET_LOCATION = "WebSocket-Location";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WEBSOCKET_ORIGIN = "WebSocket-Origin";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Values
/*      */   {
/*      */     public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String BASE64 = "base64";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String BINARY = "binary";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String BOUNDARY = "boundary";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String BYTES = "bytes";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CHARSET = "charset";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CHUNKED = "chunked";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CLOSE = "close";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String COMPRESS = "compress";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String CONTINUE = "100-continue";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String DEFLATE = "deflate";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String GZIP = "gzip";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String IDENTITY = "identity";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String KEEP_ALIVE = "keep-alive";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MAX_AGE = "max-age";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MAX_STALE = "max-stale";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MIN_FRESH = "min-fresh";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MULTIPART_FORM_DATA = "multipart/form-data";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String MUST_REVALIDATE = "must-revalidate";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String NO_CACHE = "no-cache";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String NO_STORE = "no-store";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String NO_TRANSFORM = "no-transform";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String NONE = "none";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String ONLY_IF_CACHED = "only-if-cached";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PRIVATE = "private";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PROXY_REVALIDATE = "proxy-revalidate";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String PUBLIC = "public";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String QUOTED_PRINTABLE = "quoted-printable";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String S_MAXAGE = "s-maxage";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String TRAILERS = "trailers";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String UPGRADE = "Upgrade";
/*      */ 
/*      */ 
/*      */     
/*      */     public static final String WEBSOCKET = "WebSocket";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isKeepAlive(HttpMessage message) {
/*  548 */     String connection = message.headers().get("Connection");
/*  549 */     if ("close".equalsIgnoreCase(connection)) {
/*  550 */       return false;
/*      */     }
/*      */     
/*  553 */     if (message.getProtocolVersion().isKeepAliveDefault()) {
/*  554 */       return !"close".equalsIgnoreCase(connection);
/*      */     }
/*  556 */     return "keep-alive".equalsIgnoreCase(connection);
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
/*      */   public static void setKeepAlive(HttpMessage message, boolean keepAlive) {
/*  580 */     HttpHeaders h = message.headers();
/*  581 */     if (message.getProtocolVersion().isKeepAliveDefault()) {
/*  582 */       if (keepAlive) {
/*  583 */         h.remove("Connection");
/*      */       } else {
/*  585 */         h.set("Connection", "close");
/*      */       }
/*      */     
/*  588 */     } else if (keepAlive) {
/*  589 */       h.set("Connection", "keep-alive");
/*      */     } else {
/*  591 */       h.remove("Connection");
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
/*      */   public static String getHeader(HttpMessage message, String name) {
/*  604 */     return message.headers().get(name);
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
/*      */   public static String getHeader(HttpMessage message, String name, String defaultValue) {
/*  616 */     String value = message.headers().get(name);
/*  617 */     if (value == null) {
/*  618 */       return defaultValue;
/*      */     }
/*  620 */     return value;
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
/*      */   public static void setHeader(HttpMessage message, String name, Object value) {
/*  632 */     message.headers().set(name, value);
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
/*      */   public static void setHeader(HttpMessage message, String name, Iterable<?> values) {
/*  650 */     message.headers().set(name, values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addHeader(HttpMessage message, String name, Object value) {
/*  661 */     message.headers().add(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void removeHeader(HttpMessage message, String name) {
/*  668 */     message.headers().remove(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void clearHeaders(HttpMessage message) {
/*  675 */     message.headers().clear();
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
/*      */   public static int getIntHeader(HttpMessage message, String name) {
/*  688 */     String value = getHeader(message, name);
/*  689 */     if (value == null) {
/*  690 */       throw new NumberFormatException("header not found: " + name);
/*      */     }
/*  692 */     return Integer.parseInt(value);
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
/*      */   public static int getIntHeader(HttpMessage message, String name, int defaultValue) {
/*  704 */     String value = getHeader(message, name);
/*  705 */     if (value == null) {
/*  706 */       return defaultValue;
/*      */     }
/*      */     
/*      */     try {
/*  710 */       return Integer.parseInt(value);
/*  711 */     } catch (NumberFormatException e) {
/*  712 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setIntHeader(HttpMessage message, String name, int value) {
/*  721 */     message.headers().set(name, Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setIntHeader(HttpMessage message, String name, Iterable<Integer> values) {
/*  729 */     message.headers().set(name, values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addIntHeader(HttpMessage message, String name, int value) {
/*  736 */     message.headers().add(name, Integer.valueOf(value));
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
/*      */   public static Date getDateHeader(HttpMessage message, String name) throws ParseException {
/*  749 */     String value = getHeader(message, name);
/*  750 */     if (value == null) {
/*  751 */       throw new ParseException("header not found: " + name, 0);
/*      */     }
/*  753 */     return HttpHeaderDateFormat.get().parse(value);
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
/*      */   public static Date getDateHeader(HttpMessage message, String name, Date defaultValue) {
/*  765 */     String value = getHeader(message, name);
/*  766 */     if (value == null) {
/*  767 */       return defaultValue;
/*      */     }
/*      */     
/*      */     try {
/*  771 */       return HttpHeaderDateFormat.get().parse(value);
/*  772 */     } catch (ParseException e) {
/*  773 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDateHeader(HttpMessage message, String name, Date value) {
/*  784 */     if (value != null) {
/*  785 */       message.headers().set(name, HttpHeaderDateFormat.get().format(value));
/*      */     } else {
/*  787 */       message.headers().set(name, (Iterable<?>)null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDateHeader(HttpMessage message, String name, Iterable<Date> values) {
/*  798 */     message.headers().set(name, values);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addDateHeader(HttpMessage message, String name, Date value) {
/*  807 */     message.headers().add(name, value);
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
/*      */   public static long getContentLength(HttpMessage message) {
/*  823 */     String value = getHeader(message, "Content-Length");
/*  824 */     if (value != null) {
/*  825 */       return Long.parseLong(value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  830 */     long webSocketContentLength = getWebSocketContentLength(message);
/*  831 */     if (webSocketContentLength >= 0L) {
/*  832 */       return webSocketContentLength;
/*      */     }
/*      */ 
/*      */     
/*  836 */     throw new NumberFormatException("header not found: Content-Length");
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
/*      */   public static long getContentLength(HttpMessage message, long defaultValue) {
/*  850 */     String contentLength = message.headers().get("Content-Length");
/*  851 */     if (contentLength != null) {
/*      */       try {
/*  853 */         return Long.parseLong(contentLength);
/*  854 */       } catch (NumberFormatException e) {
/*  855 */         return defaultValue;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  861 */     long webSocketContentLength = getWebSocketContentLength(message);
/*  862 */     if (webSocketContentLength >= 0L) {
/*  863 */       return webSocketContentLength;
/*      */     }
/*      */ 
/*      */     
/*  867 */     return defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getWebSocketContentLength(HttpMessage message) {
/*  876 */     HttpHeaders h = message.headers();
/*  877 */     if (message instanceof HttpRequest) {
/*  878 */       HttpRequest req = (HttpRequest)message;
/*  879 */       if (HttpMethod.GET.equals(req.getMethod()) && h.contains("Sec-WebSocket-Key1") && h.contains("Sec-WebSocket-Key2"))
/*      */       {
/*      */         
/*  882 */         return 8;
/*      */       }
/*  884 */     } else if (message instanceof HttpResponse) {
/*  885 */       HttpResponse res = (HttpResponse)message;
/*  886 */       if (res.getStatus().code() == 101 && h.contains("Sec-WebSocket-Origin") && h.contains("Sec-WebSocket-Location"))
/*      */       {
/*      */         
/*  889 */         return 16;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  894 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setContentLength(HttpMessage message, long length) {
/*  901 */     message.headers().set("Content-Length", Long.valueOf(length));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getHost(HttpMessage message) {
/*  908 */     return message.headers().get("Host");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getHost(HttpMessage message, String defaultValue) {
/*  916 */     return getHeader(message, "Host", defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setHost(HttpMessage message, String value) {
/*  923 */     message.headers().set("Host", value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDate(HttpMessage message) throws ParseException {
/*  933 */     return getDateHeader(message, "Date");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDate(HttpMessage message, Date defaultValue) {
/*  942 */     return getDateHeader(message, "Date", defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDate(HttpMessage message, Date value) {
/*  949 */     if (value != null) {
/*  950 */       message.headers().set("Date", HttpHeaderDateFormat.get().format(value));
/*      */     } else {
/*  952 */       message.headers().set("Date", (Iterable<?>)null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean is100ContinueExpected(HttpMessage message) {
/*  962 */     if (!(message instanceof HttpRequest)) {
/*  963 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  967 */     if (message.getProtocolVersion().compareTo(HttpVersion.HTTP_1_1) < 0) {
/*  968 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  972 */     String value = message.headers().get("Expect");
/*  973 */     if (value == null) {
/*  974 */       return false;
/*      */     }
/*  976 */     if ("100-continue".equalsIgnoreCase(value)) {
/*  977 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  981 */     for (String v : message.headers().getAll("Expect")) {
/*  982 */       if ("100-continue".equalsIgnoreCase(v)) {
/*  983 */         return true;
/*      */       }
/*      */     } 
/*  986 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set100ContinueExpected(HttpMessage message) {
/*  995 */     set100ContinueExpected(message, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set100ContinueExpected(HttpMessage message, boolean set) {
/* 1006 */     if (set) {
/* 1007 */       message.headers().set("Expect", "100-continue");
/*      */     } else {
/* 1009 */       message.headers().remove("Expect");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void validateHeaderName(String headerName) {
/* 1020 */     if (headerName == null) {
/* 1021 */       throw new NullPointerException("Header names cannot be null");
/*      */     }
/*      */     
/* 1024 */     for (int index = 0; index < headerName.length(); index++) {
/*      */       
/* 1026 */       char character = headerName.charAt(index);
/*      */ 
/*      */       
/* 1029 */       if (character > '') {
/* 1030 */         throw new IllegalArgumentException("Header name cannot contain non-ASCII characters: " + headerName);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1035 */       switch (character) { case '\t': case '\n': case '\013': case '\f': case '\r': case ' ': case ',': case ':':
/*      */         case ';':
/*      */         case '=':
/* 1038 */           throw new IllegalArgumentException("Header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + headerName); }
/*      */     
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
/*      */   static void validateHeaderValue(String headerValue) {
/* 1052 */     if (headerValue == null) {
/* 1053 */       throw new NullPointerException("Header values cannot be null");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1065 */     int state = 0;
/*      */ 
/*      */ 
/*      */     
/* 1069 */     for (int index = 0; index < headerValue.length(); index++) {
/* 1070 */       char character = headerValue.charAt(index);
/*      */ 
/*      */       
/* 1073 */       switch (character) {
/*      */         case '\013':
/* 1075 */           throw new IllegalArgumentException("Header value contains a prohibited character '\\v': " + headerValue);
/*      */         
/*      */         case '\f':
/* 1078 */           throw new IllegalArgumentException("Header value contains a prohibited character '\\f': " + headerValue);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1083 */       switch (state) {
/*      */         case 0:
/* 1085 */           switch (character) {
/*      */             case '\r':
/* 1087 */               state = 1;
/*      */               break;
/*      */             case '\n':
/* 1090 */               state = 2;
/*      */               break;
/*      */           } 
/*      */           break;
/*      */         case 1:
/* 1095 */           switch (character) {
/*      */             case '\n':
/* 1097 */               state = 2;
/*      */               break;
/*      */           } 
/* 1100 */           throw new IllegalArgumentException("Only '\\n' is allowed after '\\r': " + headerValue);
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1105 */           switch (character) { case '\t':
/*      */             case ' ':
/* 1107 */               state = 0;
/*      */               break; }
/*      */           
/* 1110 */           throw new IllegalArgumentException("Only ' ' and '\\t' are allowed after '\\n': " + headerValue);
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 1116 */     if (state != 0) {
/* 1117 */       throw new IllegalArgumentException("Header value must not end with '\\r' or '\\n':" + headerValue);
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
/*      */   public static boolean isTransferEncodingChunked(HttpMessage message) {
/* 1129 */     List<String> transferEncodingHeaders = message.headers().getAll("Transfer-Encoding");
/* 1130 */     if (transferEncodingHeaders.isEmpty()) {
/* 1131 */       return false;
/*      */     }
/*      */     
/* 1134 */     for (String value : transferEncodingHeaders) {
/* 1135 */       if (value.equalsIgnoreCase("chunked")) {
/* 1136 */         return true;
/*      */       }
/*      */     } 
/* 1139 */     return false;
/*      */   }
/*      */   
/*      */   public static void removeTransferEncodingChunked(HttpMessage m) {
/* 1143 */     List<String> values = m.headers().getAll("Transfer-Encoding");
/* 1144 */     if (values.isEmpty()) {
/*      */       return;
/*      */     }
/* 1147 */     Iterator<String> valuesIt = values.iterator();
/* 1148 */     while (valuesIt.hasNext()) {
/* 1149 */       String value = valuesIt.next();
/* 1150 */       if (value.equalsIgnoreCase("chunked")) {
/* 1151 */         valuesIt.remove();
/*      */       }
/*      */     } 
/* 1154 */     if (values.isEmpty()) {
/* 1155 */       m.headers().remove("Transfer-Encoding");
/*      */     } else {
/* 1157 */       m.headers().set("Transfer-Encoding", values);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setTransferEncodingChunked(HttpMessage m) {
/* 1162 */     addHeader(m, "Transfer-Encoding", "chunked");
/* 1163 */     removeHeader(m, "Content-Length");
/*      */   }
/*      */   
/*      */   public static boolean isContentLengthSet(HttpMessage m) {
/* 1167 */     List<String> contentLength = m.headers().getAll("Content-Length");
/* 1168 */     return !contentLength.isEmpty();
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
/*      */   public abstract String get(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract List<String> getAll(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract List<Map.Entry<String, String>> entries();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean contains(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isEmpty();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Set<String> names();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract HttpHeaders add(String paramString, Object paramObject);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract HttpHeaders add(String paramString, Iterable<?> paramIterable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpHeaders add(HttpHeaders headers) {
/* 1259 */     if (headers == null) {
/* 1260 */       throw new NullPointerException("headers");
/*      */     }
/* 1262 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)headers) {
/* 1263 */       add(e.getKey(), e.getValue());
/*      */     }
/* 1265 */     return this;
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
/*      */   public abstract HttpHeaders set(String paramString, Object paramObject);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract HttpHeaders set(String paramString, Iterable<?> paramIterable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpHeaders set(HttpHeaders headers) {
/* 1310 */     if (headers == null) {
/* 1311 */       throw new NullPointerException("headers");
/*      */     }
/* 1313 */     clear();
/* 1314 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)headers) {
/* 1315 */       add(e.getKey(), e.getValue());
/*      */     }
/* 1317 */     return this;
/*      */   }
/*      */   
/*      */   public abstract HttpHeaders remove(String paramString);
/*      */   
/*      */   public abstract HttpHeaders clear();
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */