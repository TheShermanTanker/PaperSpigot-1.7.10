/*     */ package org.spigotmc;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.com.google.common.cache.Cache;
/*     */ import net.minecraft.util.com.google.common.cache.CacheBuilder;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonArray;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonParser;
/*     */ 
/*     */ public class CachedMojangAPIConnection
/*     */   extends HttpURLConnection
/*     */ {
/*     */   private final CachedStreamHandlerFactory.CachedStreamHandler cachedStreamHandler;
/*     */   private final Proxy proxy;
/*  26 */   private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*     */   
/*     */   private ByteArrayInputStream inputStream;
/*     */   private InputStream errorStream;
/*     */   private boolean outClosed = false;
/*  31 */   private static final Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(10000L).expireAfterAccess(1L, TimeUnit.HOURS).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CachedMojangAPIConnection(CachedStreamHandlerFactory.CachedStreamHandler cachedStreamHandler, URL url, Proxy proxy) {
/*  38 */     super(url);
/*  39 */     this.cachedStreamHandler = cachedStreamHandler;
/*  40 */     this.proxy = proxy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usingProxy() {
/*  52 */     return (this.proxy != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() throws IOException {
/*  64 */     if (this.inputStream == null) {
/*     */       
/*  66 */       this.outClosed = true;
/*  67 */       JsonArray users = (new JsonParser()).parse(new String(this.outputStream.toByteArray(), Charsets.UTF_8)).getAsJsonArray();
/*  68 */       StringBuilder reply = new StringBuilder("[");
/*  69 */       StringBuilder missingUsers = new StringBuilder("[");
/*  70 */       for (JsonElement user : users) {
/*     */         
/*  72 */         String username = user.getAsString().toLowerCase();
/*  73 */         String info = (String)cache.getIfPresent(username);
/*  74 */         if (info != null) {
/*     */           
/*  76 */           reply.append(info).append(",");
/*     */           continue;
/*     */         } 
/*  79 */         missingUsers.append("\"").append(username).append("\"").append(",");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  86 */       if (missingUsers.length() > 1)
/*     */       {
/*  88 */         missingUsers.deleteCharAt(missingUsers.length() - 1).append("]");
/*     */       }
/*  90 */       if (missingUsers.length() > 2) {
/*     */         HttpURLConnection connection;
/*     */         
/*  93 */         if (this.proxy == null) {
/*     */           
/*  95 */           connection = (HttpURLConnection)this.cachedStreamHandler.getDefaultConnection(this.url);
/*     */         } else {
/*     */           
/*  98 */           connection = (HttpURLConnection)this.cachedStreamHandler.getDefaultConnection(this.url, this.proxy);
/*     */         } 
/* 100 */         connection.setRequestMethod("POST");
/* 101 */         connection.setRequestProperty("Content-Type", "application/json");
/* 102 */         connection.setDoInput(true);
/* 103 */         connection.setDoOutput(true);
/* 104 */         OutputStream out = connection.getOutputStream();
/* 105 */         out.write(missingUsers.toString().getBytes(Charsets.UTF_8));
/* 106 */         out.flush();
/* 107 */         out.close();
/* 108 */         JsonArray newUsers = (new JsonParser()).parse(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8)).getAsJsonArray();
/* 109 */         for (JsonElement user : newUsers) {
/*     */           
/* 111 */           JsonObject u = user.getAsJsonObject();
/* 112 */           cache.put(u.get("name").getAsString(), u.toString());
/* 113 */           reply.append(u.toString()).append(",");
/*     */         } 
/* 115 */         this.responseCode = connection.getResponseCode();
/* 116 */         this.errorStream = connection.getErrorStream();
/*     */       } else {
/*     */         
/* 119 */         this.responseCode = 200;
/*     */       } 
/* 121 */       if (reply.length() > 1)
/*     */       {
/* 123 */         reply.deleteCharAt(reply.length() - 1);
/*     */       }
/* 125 */       this.inputStream = new ByteArrayInputStream(reply.append("]").toString().getBytes(Charsets.UTF_8));
/*     */     } 
/* 127 */     return this.inputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getErrorStream() {
/* 133 */     return this.errorStream;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream getOutputStream() throws IOException {
/* 139 */     if (this.outClosed)
/*     */     {
/* 141 */       throw new RuntimeException("Write after send");
/*     */     }
/* 143 */     return this.outputStream;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\CachedMojangAPIConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */