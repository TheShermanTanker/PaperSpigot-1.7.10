/*    */ package net.minecraft.util.com.mojang.authlib.minecraft;
/*    */ import net.minecraft.util.org.apache.commons.io.FilenameUtils;
/*    */ 
/*    */ public class MinecraftProfileTexture {
/*    */   private final String url;
/*    */   
/*    */   public enum Type {
/*  8 */     SKIN,
/*  9 */     CAPE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MinecraftProfileTexture(String url) {
/* 16 */     this.url = url;
/*    */   }
/*    */   
/*    */   public String getUrl() {
/* 20 */     return this.url;
/*    */   }
/*    */   
/*    */   public String getHash() {
/* 24 */     return FilenameUtils.getBaseName(this.url);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 29 */     return (new ToStringBuilder(this)).append("url", this.url).append("hash", getHash()).toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\minecraft\MinecraftProfileTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */