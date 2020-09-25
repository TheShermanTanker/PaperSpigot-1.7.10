/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class GameProfileBanList
/*    */   extends JsonList
/*    */ {
/*    */   public GameProfileBanList(File paramFile) {
/* 11 */     super(paramFile);
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonListEntry a(JsonObject paramJsonObject) {
/* 16 */     return new GameProfileBanEntry(paramJsonObject);
/*    */   }
/*    */   
/*    */   public boolean isBanned(GameProfile paramGameProfile) {
/* 20 */     return d(paramGameProfile);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getEntries() {
/* 25 */     String[] arrayOfString = new String[e().size()];
/* 26 */     byte b = 0;
/* 27 */     for (GameProfileBanEntry gameProfileBanEntry : e().values()) {
/* 28 */       arrayOfString[b++] = ((GameProfile)gameProfileBanEntry.getKey()).getName();
/*    */     }
/* 30 */     return arrayOfString;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String b(GameProfile paramGameProfile) {
/* 35 */     return paramGameProfile.getId().toString();
/*    */   }
/*    */   
/*    */   public GameProfile a(String paramString) {
/* 39 */     for (GameProfileBanEntry gameProfileBanEntry : e().values()) {
/* 40 */       if (paramString.equalsIgnoreCase(((GameProfile)gameProfileBanEntry.getKey()).getName())) {
/* 41 */         return (GameProfile)gameProfileBanEntry.getKey();
/*    */       }
/*    */     } 
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GameProfileBanList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */