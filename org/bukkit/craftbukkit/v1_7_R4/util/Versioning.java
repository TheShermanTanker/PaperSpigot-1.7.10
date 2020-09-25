/*    */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Properties;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.Bukkit;
/*    */ 
/*    */ public final class Versioning {
/*    */   public static String getBukkitVersion() {
/* 12 */     String result = "Unknown-Version";
/*    */     
/* 14 */     InputStream stream = Bukkit.class.getClassLoader().getResourceAsStream("META-INF/maven/org.github.paperspigot/paperspigot-api/pom.properties");
/* 15 */     Properties properties = new Properties();
/*    */     
/* 17 */     if (stream != null) {
/*    */       try {
/* 19 */         properties.load(stream);
/*    */         
/* 21 */         result = properties.getProperty("version");
/* 22 */       } catch (IOException ex) {
/* 23 */         Logger.getLogger(Versioning.class.getName()).log(Level.SEVERE, "Could not get Bukkit version!", ex);
/*    */       } 
/*    */     }
/*    */     
/* 27 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\Versioning.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */