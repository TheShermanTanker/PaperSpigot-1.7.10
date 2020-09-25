/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.util.Properties;
/*    */ import net.minecraft.util.org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class EULA {
/* 11 */   private static final Logger a = LogManager.getLogger();
/*    */   private final File b;
/*    */   private final boolean c;
/*    */   
/*    */   public EULA(File paramFile) {
/* 16 */     this.b = paramFile;
/* 17 */     this.c = a(paramFile);
/*    */   }
/*    */   
/*    */   private boolean a(File paramFile) {
/* 21 */     FileInputStream fileInputStream = null;
/* 22 */     boolean bool = false;
/*    */     try {
/* 24 */       Properties properties = new Properties();
/* 25 */       fileInputStream = new FileInputStream(paramFile);
/* 26 */       properties.load(fileInputStream);
/* 27 */       bool = Boolean.parseBoolean(properties.getProperty("eula", "false"));
/* 28 */     } catch (Exception exception) {
/* 29 */       a.warn("Failed to load " + paramFile);
/* 30 */       b();
/*    */     } finally {
/* 32 */       IOUtils.closeQuietly(fileInputStream);
/*    */     } 
/* 34 */     return bool;
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 38 */     return this.c;
/*    */   }
/*    */   
/*    */   public void b() {
/* 42 */     FileOutputStream fileOutputStream = null;
/*    */     try {
/* 44 */       Properties properties = new Properties();
/* 45 */       fileOutputStream = new FileOutputStream(this.b);
/* 46 */       properties.setProperty("eula", "false");
/* 47 */       properties.store(fileOutputStream, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).");
/* 48 */     } catch (Exception exception) {
/* 49 */       a.warn("Failed to save " + this.b, exception);
/*    */     } finally {
/* 51 */       IOUtils.closeQuietly(fileOutputStream);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EULA.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */