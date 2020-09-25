/*     */ package net.minecraft.util.com.mojang.authlib;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.org.apache.commons.io.Charsets;
/*     */ import net.minecraft.util.org.apache.commons.io.IOUtils;
/*     */ import net.minecraft.util.org.apache.commons.lang3.Validate;
/*     */ 
/*     */ public abstract class HttpAuthenticationService extends BaseAuthenticationService {
/*  17 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final Proxy proxy;
/*     */   
/*     */   protected HttpAuthenticationService(Proxy proxy) {
/*  22 */     Validate.notNull(proxy);
/*  23 */     this.proxy = proxy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Proxy getProxy() {
/*  32 */     return this.proxy;
/*     */   }
/*     */   
/*     */   protected HttpURLConnection createUrlConnection(URL url) throws IOException {
/*  36 */     Validate.notNull(url);
/*  37 */     LOGGER.debug("Opening connection to " + url);
/*  38 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection(this.proxy);
/*  39 */     connection.setConnectTimeout(15000);
/*  40 */     connection.setReadTimeout(15000);
/*  41 */     connection.setUseCaches(false);
/*  42 */     return connection;
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
/*     */   public String performPostRequest(URL url, String post, String contentType) throws IOException {
/*  59 */     Validate.notNull(url);
/*  60 */     Validate.notNull(post);
/*  61 */     Validate.notNull(contentType);
/*  62 */     HttpURLConnection connection = createUrlConnection(url);
/*  63 */     byte[] postAsBytes = post.getBytes(Charsets.UTF_8);
/*     */     
/*  65 */     connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
/*  66 */     connection.setRequestProperty("Content-Length", "" + postAsBytes.length);
/*  67 */     connection.setDoOutput(true);
/*     */     
/*  69 */     LOGGER.debug("Writing POST data to " + url + ": " + post);
/*     */     
/*  71 */     OutputStream outputStream = null;
/*     */     try {
/*  73 */       outputStream = connection.getOutputStream();
/*  74 */       IOUtils.write(postAsBytes, outputStream);
/*     */     } finally {
/*  76 */       IOUtils.closeQuietly(outputStream);
/*     */     } 
/*     */     
/*  79 */     LOGGER.debug("Reading data from " + url);
/*     */     
/*  81 */     InputStream inputStream = null;
/*     */     try {
/*  83 */       inputStream = connection.getInputStream();
/*  84 */       String result = IOUtils.toString(inputStream, Charsets.UTF_8);
/*  85 */       LOGGER.debug("Successful read, server response was " + connection.getResponseCode());
/*  86 */       LOGGER.debug("Response: " + result);
/*  87 */       return result;
/*  88 */     } catch (IOException e) {
/*  89 */       IOUtils.closeQuietly(inputStream);
/*  90 */       inputStream = connection.getErrorStream();
/*     */       
/*  92 */       if (inputStream != null) {
/*  93 */         LOGGER.debug("Reading error page from " + url);
/*  94 */         String result = IOUtils.toString(inputStream, Charsets.UTF_8);
/*  95 */         LOGGER.debug("Successful read, server response was " + connection.getResponseCode());
/*  96 */         LOGGER.debug("Response: " + result);
/*  97 */         return result;
/*     */       } 
/*  99 */       LOGGER.debug("Request failed", e);
/* 100 */       throw e;
/*     */     } finally {
/*     */       
/* 103 */       IOUtils.closeQuietly(inputStream);
/*     */     } 
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
/*     */   public String performGetRequest(URL url) throws IOException {
/* 119 */     Validate.notNull(url);
/* 120 */     HttpURLConnection connection = createUrlConnection(url);
/*     */     
/* 122 */     LOGGER.debug("Reading data from " + url);
/*     */     
/* 124 */     InputStream inputStream = null;
/*     */     try {
/* 126 */       inputStream = connection.getInputStream();
/* 127 */       String result = IOUtils.toString(inputStream, Charsets.UTF_8);
/* 128 */       LOGGER.debug("Successful read, server response was " + connection.getResponseCode());
/* 129 */       LOGGER.debug("Response: " + result);
/* 130 */       return result;
/* 131 */     } catch (IOException e) {
/* 132 */       IOUtils.closeQuietly(inputStream);
/* 133 */       inputStream = connection.getErrorStream();
/*     */       
/* 135 */       if (inputStream != null) {
/* 136 */         LOGGER.debug("Reading error page from " + url);
/* 137 */         String result = IOUtils.toString(inputStream, Charsets.UTF_8);
/* 138 */         LOGGER.debug("Successful read, server response was " + connection.getResponseCode());
/* 139 */         LOGGER.debug("Response: " + result);
/* 140 */         return result;
/*     */       } 
/* 142 */       LOGGER.debug("Request failed", e);
/* 143 */       throw e;
/*     */     } finally {
/*     */       
/* 146 */       IOUtils.closeQuietly(inputStream);
/*     */     } 
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
/*     */   public static URL constantURL(String url) {
/*     */     try {
/* 160 */       return new URL(url);
/* 161 */     } catch (MalformedURLException ex) {
/* 162 */       throw new Error("Couldn't create constant for " + url, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String buildQuery(Map<String, Object> query) {
/* 173 */     if (query == null) return ""; 
/* 174 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 176 */     for (Map.Entry<String, Object> entry : query.entrySet()) {
/* 177 */       if (builder.length() > 0) {
/* 178 */         builder.append('&');
/*     */       }
/*     */       
/*     */       try {
/* 182 */         builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
/* 183 */       } catch (UnsupportedEncodingException e) {
/* 184 */         LOGGER.error("Unexpected exception building query", e);
/*     */       } 
/*     */       
/* 187 */       if (entry.getValue() != null) {
/* 188 */         builder.append('=');
/*     */         try {
/* 190 */           builder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
/* 191 */         } catch (UnsupportedEncodingException e) {
/* 192 */           LOGGER.error("Unexpected exception building query", e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL concatenateURL(URL url, String query) {
/*     */     try {
/* 209 */       if (url.getQuery() != null && url.getQuery().length() > 0) {
/* 210 */         return new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile() + "&" + query);
/*     */       }
/* 212 */       return new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile() + "?" + query);
/*     */     }
/* 214 */     catch (MalformedURLException ex) {
/* 215 */       throw new IllegalArgumentException("Could not concatenate given URL with GET arguments!", ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\HttpAuthenticationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */