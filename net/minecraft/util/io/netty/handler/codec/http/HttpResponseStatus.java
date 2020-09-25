/*     */ package net.minecraft.util.io.netty.handler.codec.http;
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
/*     */ public class HttpResponseStatus
/*     */   implements Comparable<HttpResponseStatus>
/*     */ {
/*  28 */   public static final HttpResponseStatus CONTINUE = new HttpResponseStatus(100, "Continue");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static final HttpResponseStatus SWITCHING_PROTOCOLS = new HttpResponseStatus(101, "Switching Protocols");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public static final HttpResponseStatus PROCESSING = new HttpResponseStatus(102, "Processing");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public static final HttpResponseStatus OK = new HttpResponseStatus(200, "OK");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final HttpResponseStatus CREATED = new HttpResponseStatus(201, "Created");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static final HttpResponseStatus ACCEPTED = new HttpResponseStatus(202, "Accepted");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final HttpResponseStatus NON_AUTHORITATIVE_INFORMATION = new HttpResponseStatus(203, "Non-Authoritative Information");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final HttpResponseStatus NO_CONTENT = new HttpResponseStatus(204, "No Content");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final HttpResponseStatus RESET_CONTENT = new HttpResponseStatus(205, "Reset Content");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static final HttpResponseStatus PARTIAL_CONTENT = new HttpResponseStatus(206, "Partial Content");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public static final HttpResponseStatus MULTI_STATUS = new HttpResponseStatus(207, "Multi-Status");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final HttpResponseStatus MULTIPLE_CHOICES = new HttpResponseStatus(300, "Multiple Choices");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final HttpResponseStatus MOVED_PERMANENTLY = new HttpResponseStatus(301, "Moved Permanently");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static final HttpResponseStatus FOUND = new HttpResponseStatus(302, "Found");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static final HttpResponseStatus SEE_OTHER = new HttpResponseStatus(303, "See Other");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final HttpResponseStatus NOT_MODIFIED = new HttpResponseStatus(304, "Not Modified");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static final HttpResponseStatus USE_PROXY = new HttpResponseStatus(305, "Use Proxy");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static final HttpResponseStatus TEMPORARY_REDIRECT = new HttpResponseStatus(307, "Temporary Redirect");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static final HttpResponseStatus BAD_REQUEST = new HttpResponseStatus(400, "Bad Request");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static final HttpResponseStatus UNAUTHORIZED = new HttpResponseStatus(401, "Unauthorized");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public static final HttpResponseStatus PAYMENT_REQUIRED = new HttpResponseStatus(402, "Payment Required");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public static final HttpResponseStatus FORBIDDEN = new HttpResponseStatus(403, "Forbidden");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public static final HttpResponseStatus NOT_FOUND = new HttpResponseStatus(404, "Not Found");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public static final HttpResponseStatus METHOD_NOT_ALLOWED = new HttpResponseStatus(405, "Method Not Allowed");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public static final HttpResponseStatus NOT_ACCEPTABLE = new HttpResponseStatus(406, "Not Acceptable");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public static final HttpResponseStatus PROXY_AUTHENTICATION_REQUIRED = new HttpResponseStatus(407, "Proxy Authentication Required");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public static final HttpResponseStatus REQUEST_TIMEOUT = new HttpResponseStatus(408, "Request Timeout");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public static final HttpResponseStatus CONFLICT = new HttpResponseStatus(409, "Conflict");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public static final HttpResponseStatus GONE = new HttpResponseStatus(410, "Gone");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static final HttpResponseStatus LENGTH_REQUIRED = new HttpResponseStatus(411, "Length Required");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public static final HttpResponseStatus PRECONDITION_FAILED = new HttpResponseStatus(412, "Precondition Failed");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 185 */   public static final HttpResponseStatus REQUEST_ENTITY_TOO_LARGE = new HttpResponseStatus(413, "Request Entity Too Large");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public static final HttpResponseStatus REQUEST_URI_TOO_LONG = new HttpResponseStatus(414, "Request-URI Too Long");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   public static final HttpResponseStatus UNSUPPORTED_MEDIA_TYPE = new HttpResponseStatus(415, "Unsupported Media Type");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public static final HttpResponseStatus REQUESTED_RANGE_NOT_SATISFIABLE = new HttpResponseStatus(416, "Requested Range Not Satisfiable");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public static final HttpResponseStatus EXPECTATION_FAILED = new HttpResponseStatus(417, "Expectation Failed");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   public static final HttpResponseStatus UNPROCESSABLE_ENTITY = new HttpResponseStatus(422, "Unprocessable Entity");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   public static final HttpResponseStatus LOCKED = new HttpResponseStatus(423, "Locked");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 223 */   public static final HttpResponseStatus FAILED_DEPENDENCY = new HttpResponseStatus(424, "Failed Dependency");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   public static final HttpResponseStatus UNORDERED_COLLECTION = new HttpResponseStatus(425, "Unordered Collection");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 233 */   public static final HttpResponseStatus UPGRADE_REQUIRED = new HttpResponseStatus(426, "Upgrade Required");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   public static final HttpResponseStatus PRECONDITION_REQUIRED = new HttpResponseStatus(428, "Precondition Required");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 243 */   public static final HttpResponseStatus TOO_MANY_REQUESTS = new HttpResponseStatus(429, "Too Many Requests");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 248 */   public static final HttpResponseStatus REQUEST_HEADER_FIELDS_TOO_LARGE = new HttpResponseStatus(431, "Request Header Fields Too Large");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 254 */   public static final HttpResponseStatus INTERNAL_SERVER_ERROR = new HttpResponseStatus(500, "Internal Server Error");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public static final HttpResponseStatus NOT_IMPLEMENTED = new HttpResponseStatus(501, "Not Implemented");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 265 */   public static final HttpResponseStatus BAD_GATEWAY = new HttpResponseStatus(502, "Bad Gateway");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public static final HttpResponseStatus SERVICE_UNAVAILABLE = new HttpResponseStatus(503, "Service Unavailable");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 275 */   public static final HttpResponseStatus GATEWAY_TIMEOUT = new HttpResponseStatus(504, "Gateway Timeout");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public static final HttpResponseStatus HTTP_VERSION_NOT_SUPPORTED = new HttpResponseStatus(505, "HTTP Version Not Supported");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 286 */   public static final HttpResponseStatus VARIANT_ALSO_NEGOTIATES = new HttpResponseStatus(506, "Variant Also Negotiates");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 292 */   public static final HttpResponseStatus INSUFFICIENT_STORAGE = new HttpResponseStatus(507, "Insufficient Storage");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   public static final HttpResponseStatus NOT_EXTENDED = new HttpResponseStatus(510, "Not Extended");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 302 */   public static final HttpResponseStatus NETWORK_AUTHENTICATION_REQUIRED = new HttpResponseStatus(511, "Network Authentication Required");
/*     */   
/*     */   private final int code;
/*     */   
/*     */   private final String reasonPhrase;
/*     */ 
/*     */   
/*     */   public static HttpResponseStatus valueOf(int code) {
/*     */     String reasonPhrase;
/* 311 */     switch (code) {
/*     */       case 100:
/* 313 */         return CONTINUE;
/*     */       case 101:
/* 315 */         return SWITCHING_PROTOCOLS;
/*     */       case 102:
/* 317 */         return PROCESSING;
/*     */       case 200:
/* 319 */         return OK;
/*     */       case 201:
/* 321 */         return CREATED;
/*     */       case 202:
/* 323 */         return ACCEPTED;
/*     */       case 203:
/* 325 */         return NON_AUTHORITATIVE_INFORMATION;
/*     */       case 204:
/* 327 */         return NO_CONTENT;
/*     */       case 205:
/* 329 */         return RESET_CONTENT;
/*     */       case 206:
/* 331 */         return PARTIAL_CONTENT;
/*     */       case 207:
/* 333 */         return MULTI_STATUS;
/*     */       case 300:
/* 335 */         return MULTIPLE_CHOICES;
/*     */       case 301:
/* 337 */         return MOVED_PERMANENTLY;
/*     */       case 302:
/* 339 */         return FOUND;
/*     */       case 303:
/* 341 */         return SEE_OTHER;
/*     */       case 304:
/* 343 */         return NOT_MODIFIED;
/*     */       case 305:
/* 345 */         return USE_PROXY;
/*     */       case 307:
/* 347 */         return TEMPORARY_REDIRECT;
/*     */       case 400:
/* 349 */         return BAD_REQUEST;
/*     */       case 401:
/* 351 */         return UNAUTHORIZED;
/*     */       case 402:
/* 353 */         return PAYMENT_REQUIRED;
/*     */       case 403:
/* 355 */         return FORBIDDEN;
/*     */       case 404:
/* 357 */         return NOT_FOUND;
/*     */       case 405:
/* 359 */         return METHOD_NOT_ALLOWED;
/*     */       case 406:
/* 361 */         return NOT_ACCEPTABLE;
/*     */       case 407:
/* 363 */         return PROXY_AUTHENTICATION_REQUIRED;
/*     */       case 408:
/* 365 */         return REQUEST_TIMEOUT;
/*     */       case 409:
/* 367 */         return CONFLICT;
/*     */       case 410:
/* 369 */         return GONE;
/*     */       case 411:
/* 371 */         return LENGTH_REQUIRED;
/*     */       case 412:
/* 373 */         return PRECONDITION_FAILED;
/*     */       case 413:
/* 375 */         return REQUEST_ENTITY_TOO_LARGE;
/*     */       case 414:
/* 377 */         return REQUEST_URI_TOO_LONG;
/*     */       case 415:
/* 379 */         return UNSUPPORTED_MEDIA_TYPE;
/*     */       case 416:
/* 381 */         return REQUESTED_RANGE_NOT_SATISFIABLE;
/*     */       case 417:
/* 383 */         return EXPECTATION_FAILED;
/*     */       case 422:
/* 385 */         return UNPROCESSABLE_ENTITY;
/*     */       case 423:
/* 387 */         return LOCKED;
/*     */       case 424:
/* 389 */         return FAILED_DEPENDENCY;
/*     */       case 425:
/* 391 */         return UNORDERED_COLLECTION;
/*     */       case 426:
/* 393 */         return UPGRADE_REQUIRED;
/*     */       case 428:
/* 395 */         return PRECONDITION_REQUIRED;
/*     */       case 429:
/* 397 */         return TOO_MANY_REQUESTS;
/*     */       case 431:
/* 399 */         return REQUEST_HEADER_FIELDS_TOO_LARGE;
/*     */       case 500:
/* 401 */         return INTERNAL_SERVER_ERROR;
/*     */       case 501:
/* 403 */         return NOT_IMPLEMENTED;
/*     */       case 502:
/* 405 */         return BAD_GATEWAY;
/*     */       case 503:
/* 407 */         return SERVICE_UNAVAILABLE;
/*     */       case 504:
/* 409 */         return GATEWAY_TIMEOUT;
/*     */       case 505:
/* 411 */         return HTTP_VERSION_NOT_SUPPORTED;
/*     */       case 506:
/* 413 */         return VARIANT_ALSO_NEGOTIATES;
/*     */       case 507:
/* 415 */         return INSUFFICIENT_STORAGE;
/*     */       case 510:
/* 417 */         return NOT_EXTENDED;
/*     */       case 511:
/* 419 */         return NETWORK_AUTHENTICATION_REQUIRED;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 424 */     if (code < 100) {
/* 425 */       reasonPhrase = "Unknown Status";
/* 426 */     } else if (code < 200) {
/* 427 */       reasonPhrase = "Informational";
/* 428 */     } else if (code < 300) {
/* 429 */       reasonPhrase = "Successful";
/* 430 */     } else if (code < 400) {
/* 431 */       reasonPhrase = "Redirection";
/* 432 */     } else if (code < 500) {
/* 433 */       reasonPhrase = "Client Error";
/* 434 */     } else if (code < 600) {
/* 435 */       reasonPhrase = "Server Error";
/*     */     } else {
/* 437 */       reasonPhrase = "Unknown Status";
/*     */     } 
/*     */     
/* 440 */     return new HttpResponseStatus(code, reasonPhrase + " (" + code + ')');
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
/*     */   public HttpResponseStatus(int code, String reasonPhrase) {
/* 452 */     if (code < 0) {
/* 453 */       throw new IllegalArgumentException("code: " + code + " (expected: 0+)");
/*     */     }
/*     */ 
/*     */     
/* 457 */     if (reasonPhrase == null) {
/* 458 */       throw new NullPointerException("reasonPhrase");
/*     */     }
/*     */     
/* 461 */     for (int i = 0; i < reasonPhrase.length(); i++) {
/* 462 */       char c = reasonPhrase.charAt(i);
/*     */       
/* 464 */       switch (c) { case '\n':
/*     */         case '\r':
/* 466 */           throw new IllegalArgumentException("reasonPhrase contains one of the following prohibited characters: \\r\\n: " + reasonPhrase); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 472 */     this.code = code;
/* 473 */     this.reasonPhrase = reasonPhrase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int code() {
/* 480 */     return this.code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String reasonPhrase() {
/* 487 */     return this.reasonPhrase;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 492 */     return code();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 497 */     if (!(o instanceof HttpResponseStatus)) {
/* 498 */       return false;
/*     */     }
/*     */     
/* 501 */     return (code() == ((HttpResponseStatus)o).code());
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(HttpResponseStatus o) {
/* 506 */     return code() - o.code();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 511 */     StringBuilder buf = new StringBuilder(this.reasonPhrase.length() + 5);
/* 512 */     buf.append(this.code);
/* 513 */     buf.append(' ');
/* 514 */     buf.append(this.reasonPhrase);
/* 515 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpResponseStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */