/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class OpList
/*    */   extends JsonList
/*    */ {
/*    */   public OpList(File paramFile) {
/* 11 */     super(paramFile);
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonListEntry a(JsonObject paramJsonObject) {
/* 16 */     return new OpListEntry(paramJsonObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getEntries() {
/* 21 */     String[] arrayOfString = new String[e().size()];
/* 22 */     byte b = 0;
/* 23 */     for (OpListEntry opListEntry : e().values()) {
/* 24 */       arrayOfString[b++] = ((GameProfile)opListEntry.getKey()).getName();
/*    */     }
/* 26 */     return arrayOfString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String b(GameProfile paramGameProfile) {
/* 39 */     return paramGameProfile.getId().toString();
/*    */   }
/*    */   
/*    */   public GameProfile a(String paramString) {
/* 43 */     for (OpListEntry opListEntry : e().values()) {
/* 44 */       if (paramString.equalsIgnoreCase(((GameProfile)opListEntry.getKey()).getName())) {
/* 45 */         return (GameProfile)opListEntry.getKey();
/*    */       }
/*    */     } 
/* 48 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\OpList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */