/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ 
/*    */ public class WhiteList
/*    */   extends JsonList
/*    */ {
/*    */   public WhiteList(File paramFile) {
/* 12 */     super(paramFile);
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonListEntry a(JsonObject paramJsonObject) {
/* 17 */     return new WhiteListEntry(paramJsonObject);
/*    */   }
/*    */   
/*    */   public boolean isWhitelisted(GameProfile paramGameProfile) {
/* 21 */     return d(paramGameProfile);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getEntries() {
/* 26 */     String[] arrayOfString = new String[e().size()];
/* 27 */     byte b = 0;
/* 28 */     for (WhiteListEntry whiteListEntry : e().values()) {
/* 29 */       arrayOfString[b++] = ((GameProfile)whiteListEntry.getKey()).getName();
/*    */     }
/* 31 */     return arrayOfString;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String b(GameProfile paramGameProfile) {
/* 36 */     return paramGameProfile.getId().toString();
/*    */   }
/*    */   
/*    */   public GameProfile a(String paramString) {
/* 40 */     for (WhiteListEntry whiteListEntry : e().values()) {
/* 41 */       if (paramString.equalsIgnoreCase(((GameProfile)whiteListEntry.getKey()).getName())) {
/* 42 */         return (GameProfile)whiteListEntry.getKey();
/*    */       }
/*    */     } 
/* 45 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WhiteList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */