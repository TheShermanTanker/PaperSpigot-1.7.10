/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.net.URLEncoder;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class HttpUtilities {
/* 16 */   private static final AtomicInteger a = new AtomicInteger(0);
/* 17 */   private static final Logger b = LogManager.getLogger();
/*    */ 
/*    */ 
/*    */   
/*    */   public static String a(Map paramMap) {
/* 22 */     StringBuilder stringBuilder = new StringBuilder();
/*    */     
/* 24 */     for (Map.Entry entry : paramMap.entrySet()) {
/* 25 */       if (stringBuilder.length() > 0) {
/* 26 */         stringBuilder.append('&');
/*    */       }
/*    */       
/*    */       try {
/* 30 */         stringBuilder.append(URLEncoder.encode((String)entry.getKey(), "UTF-8"));
/* 31 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 32 */         unsupportedEncodingException.printStackTrace();
/*    */       } 
/*    */       
/* 35 */       if (entry.getValue() != null) {
/* 36 */         stringBuilder.append('=');
/*    */         try {
/* 38 */           stringBuilder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
/* 39 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 40 */           unsupportedEncodingException.printStackTrace();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     return stringBuilder.toString();
/*    */   }
/*    */   
/*    */   public static String a(URL paramURL, Map paramMap, boolean paramBoolean) {
/* 49 */     return a(paramURL, a(paramMap), paramBoolean);
/*    */   }
/*    */   
/*    */   private static String a(URL paramURL, String paramString, boolean paramBoolean) {
/*    */     try {
/* 54 */       Proxy proxy = (MinecraftServer.getServer() == null) ? null : MinecraftServer.getServer().aq();
/* 55 */       if (proxy == null) proxy = Proxy.NO_PROXY; 
/* 56 */       HttpURLConnection httpURLConnection = (HttpURLConnection)paramURL.openConnection(proxy);
/* 57 */       httpURLConnection.setRequestMethod("POST");
/* 58 */       httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*    */       
/* 60 */       httpURLConnection.setRequestProperty("Content-Length", "" + (paramString.getBytes()).length);
/* 61 */       httpURLConnection.setRequestProperty("Content-Language", "en-US");
/*    */       
/* 63 */       httpURLConnection.setUseCaches(false);
/* 64 */       httpURLConnection.setDoInput(true);
/* 65 */       httpURLConnection.setDoOutput(true);
/*    */ 
/*    */       
/* 68 */       DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
/* 69 */       dataOutputStream.writeBytes(paramString);
/* 70 */       dataOutputStream.flush();
/* 71 */       dataOutputStream.close();
/*    */ 
/*    */       
/* 74 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
/*    */       
/* 76 */       StringBuffer stringBuffer = new StringBuffer();
/*    */       String str;
/* 78 */       while ((str = bufferedReader.readLine()) != null) {
/* 79 */         stringBuffer.append(str);
/* 80 */         stringBuffer.append('\r');
/*    */       } 
/*    */       
/* 83 */       bufferedReader.close();
/* 84 */       return stringBuffer.toString();
/* 85 */     } catch (Exception exception) {
/* 86 */       if (!paramBoolean) {
/* 87 */         b.error("Could not post to " + paramURL, exception);
/*    */       }
/* 89 */       return "";
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\HttpUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */