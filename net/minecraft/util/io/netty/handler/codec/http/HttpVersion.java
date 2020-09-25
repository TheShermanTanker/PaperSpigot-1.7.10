/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class HttpVersion
/*     */   implements Comparable<HttpVersion>
/*     */ {
/*  28 */   private static final Pattern VERSION_PATTERN = Pattern.compile("(\\S+)/(\\d+)\\.(\\d+)");
/*     */ 
/*     */   
/*     */   private static final String HTTP_1_0_STRING = "HTTP/1.0";
/*     */ 
/*     */   
/*     */   private static final String HTTP_1_1_STRING = "HTTP/1.1";
/*     */ 
/*     */   
/*  37 */   public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP", 1, 0, false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP", 1, 1, true);
/*     */   
/*     */   private final String protocolName;
/*     */   
/*     */   private final int majorVersion;
/*     */   
/*     */   private final int minorVersion;
/*     */   private final String text;
/*     */   private final boolean keepAliveDefault;
/*     */   
/*     */   public static HttpVersion valueOf(String text) {
/*  53 */     if (text == null) {
/*  54 */       throw new NullPointerException("text");
/*     */     }
/*     */     
/*  57 */     text = text.trim();
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
/*  69 */     HttpVersion version = version0(text);
/*  70 */     if (version == null) {
/*  71 */       text = text.toUpperCase();
/*     */       
/*  73 */       version = version0(text);
/*  74 */       if (version == null)
/*     */       {
/*  76 */         version = new HttpVersion(text, true);
/*     */       }
/*     */     } 
/*  79 */     return version;
/*     */   }
/*     */   
/*     */   private static HttpVersion version0(String text) {
/*  83 */     if ("HTTP/1.1".equals(text)) {
/*  84 */       return HTTP_1_1;
/*     */     }
/*  86 */     if ("HTTP/1.0".equals(text)) {
/*  87 */       return HTTP_1_0;
/*     */     }
/*  89 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpVersion(String text, boolean keepAliveDefault) {
/* 110 */     if (text == null) {
/* 111 */       throw new NullPointerException("text");
/*     */     }
/*     */     
/* 114 */     text = text.trim().toUpperCase();
/* 115 */     if (text.isEmpty()) {
/* 116 */       throw new IllegalArgumentException("empty text");
/*     */     }
/*     */     
/* 119 */     Matcher m = VERSION_PATTERN.matcher(text);
/* 120 */     if (!m.matches()) {
/* 121 */       throw new IllegalArgumentException("invalid version format: " + text);
/*     */     }
/*     */     
/* 124 */     this.protocolName = m.group(1);
/* 125 */     this.majorVersion = Integer.parseInt(m.group(2));
/* 126 */     this.minorVersion = Integer.parseInt(m.group(3));
/* 127 */     this.text = this.protocolName + '/' + this.majorVersion + '.' + this.minorVersion;
/* 128 */     this.keepAliveDefault = keepAliveDefault;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpVersion(String protocolName, int majorVersion, int minorVersion, boolean keepAliveDefault) {
/* 145 */     if (protocolName == null) {
/* 146 */       throw new NullPointerException("protocolName");
/*     */     }
/*     */     
/* 149 */     protocolName = protocolName.trim().toUpperCase();
/* 150 */     if (protocolName.isEmpty()) {
/* 151 */       throw new IllegalArgumentException("empty protocolName");
/*     */     }
/*     */     
/* 154 */     for (int i = 0; i < protocolName.length(); i++) {
/* 155 */       if (Character.isISOControl(protocolName.charAt(i)) || Character.isWhitespace(protocolName.charAt(i)))
/*     */       {
/* 157 */         throw new IllegalArgumentException("invalid character in protocolName");
/*     */       }
/*     */     } 
/*     */     
/* 161 */     if (majorVersion < 0) {
/* 162 */       throw new IllegalArgumentException("negative majorVersion");
/*     */     }
/* 164 */     if (minorVersion < 0) {
/* 165 */       throw new IllegalArgumentException("negative minorVersion");
/*     */     }
/*     */     
/* 168 */     this.protocolName = protocolName;
/* 169 */     this.majorVersion = majorVersion;
/* 170 */     this.minorVersion = minorVersion;
/* 171 */     this.text = protocolName + '/' + majorVersion + '.' + minorVersion;
/* 172 */     this.keepAliveDefault = keepAliveDefault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String protocolName() {
/* 179 */     return this.protocolName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int majorVersion() {
/* 186 */     return this.majorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int minorVersion() {
/* 193 */     return this.minorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String text() {
/* 200 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeepAliveDefault() {
/* 208 */     return this.keepAliveDefault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 216 */     return text();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 221 */     return (protocolName().hashCode() * 31 + majorVersion()) * 31 + minorVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 227 */     if (!(o instanceof HttpVersion)) {
/* 228 */       return false;
/*     */     }
/*     */     
/* 231 */     HttpVersion that = (HttpVersion)o;
/* 232 */     return (minorVersion() == that.minorVersion() && majorVersion() == that.majorVersion() && protocolName().equals(that.protocolName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(HttpVersion o) {
/* 239 */     int v = protocolName().compareTo(o.protocolName());
/* 240 */     if (v != 0) {
/* 241 */       return v;
/*     */     }
/*     */     
/* 244 */     v = majorVersion() - o.majorVersion();
/* 245 */     if (v != 0) {
/* 246 */       return v;
/*     */     }
/*     */     
/* 249 */     return minorVersion() - o.minorVersion();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */