/*    */ package org.bukkit.craftbukkit.v1_7_R4.updater;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.logging.Level;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.FieldNamingPolicy;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializationContext;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
/*    */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonParseException;
/*    */ 
/*    */ public class BukkitDLUpdaterService {
/*    */   private static final String API_PREFIX_ARTIFACT = "/api/1.0/downloads/projects/craftbukkit/view/";
/* 21 */   private static final DateDeserializer dateDeserializer = new DateDeserializer(); private static final String API_PREFIX_CHANNEL = "/api/1.0/downloads/channels/";
/*    */   private final String host;
/*    */   
/*    */   public BukkitDLUpdaterService(String host) {
/* 25 */     this.host = host;
/*    */   }
/*    */   
/*    */   public ArtifactDetails getArtifact(String slug, String name) {
/*    */     try {
/* 30 */       return fetchArtifact(slug);
/* 31 */     } catch (UnsupportedEncodingException ex) {
/* 32 */       Bukkit.getLogger().log(Level.WARNING, "Could not get " + name + ": " + ex.getClass().getSimpleName());
/* 33 */     } catch (IOException ex) {
/* 34 */       Bukkit.getLogger().log(Level.WARNING, "Could not get " + name + ": " + ex.getClass().getSimpleName());
/*    */     } 
/*    */     
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   private String getUserAgent() {
/* 41 */     return "CraftBukkit/" + BukkitDLUpdaterService.class.getPackage().getImplementationVersion() + "/" + System.getProperty("java.version");
/*    */   }
/*    */   
/*    */   public ArtifactDetails fetchArtifact(String slug) throws IOException {
/* 45 */     URL url = new URL("http", this.host, "/api/1.0/downloads/projects/craftbukkit/view/" + slug + "/");
/* 46 */     InputStreamReader reader = null;
/*    */     
/*    */     try {
/* 49 */       URLConnection connection = url.openConnection();
/* 50 */       connection.setRequestProperty("User-Agent", getUserAgent());
/* 51 */       reader = new InputStreamReader(connection.getInputStream());
/* 52 */       Gson gson = (new GsonBuilder()).registerTypeAdapter(Date.class, dateDeserializer).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
/* 53 */       return (ArtifactDetails)gson.fromJson(reader, ArtifactDetails.class);
/*    */     } finally {
/* 55 */       if (reader != null) {
/* 56 */         reader.close();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public ArtifactDetails.ChannelDetails getChannel(String slug, String name) {
/*    */     try {
/* 63 */       return fetchChannel(slug);
/* 64 */     } catch (UnsupportedEncodingException ex) {
/* 65 */       Bukkit.getLogger().log(Level.WARNING, "Could not get " + name + ": " + ex.getClass().getSimpleName());
/* 66 */     } catch (IOException ex) {
/* 67 */       Bukkit.getLogger().log(Level.WARNING, "Could not get " + name + ": " + ex.getClass().getSimpleName());
/*    */     } 
/*    */     
/* 70 */     return null;
/*    */   }
/*    */   
/*    */   public ArtifactDetails.ChannelDetails fetchChannel(String slug) throws IOException {
/* 74 */     URL url = new URL("http", this.host, "/api/1.0/downloads/channels/" + slug + "/");
/* 75 */     InputStreamReader reader = null;
/*    */     
/*    */     try {
/* 78 */       URLConnection connection = url.openConnection();
/* 79 */       connection.setRequestProperty("User-Agent", getUserAgent());
/* 80 */       reader = new InputStreamReader(connection.getInputStream());
/* 81 */       Gson gson = (new GsonBuilder()).registerTypeAdapter(Date.class, dateDeserializer).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
/*    */       
/* 83 */       return (ArtifactDetails.ChannelDetails)gson.fromJson(reader, ArtifactDetails.ChannelDetails.class);
/*    */     } finally {
/* 85 */       if (reader != null)
/* 86 */         reader.close(); 
/*    */     } 
/*    */   }
/*    */   
/*    */   static class DateDeserializer
/*    */     implements JsonDeserializer<Date> {
/* 92 */     private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*    */     
/*    */     public Date deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
/*    */       try {
/* 96 */         return format.parse(je.getAsString());
/* 97 */       } catch (ParseException ex) {
/* 98 */         throw new JsonParseException("Date is not formatted correctly", ex);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\updater\BukkitDLUpdaterService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */