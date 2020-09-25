/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class GameProfileBanEntry
/*    */   extends ExpirableListEntry
/*    */ {
/*    */   public GameProfileBanEntry(GameProfile gameprofile) {
/* 12 */     this(gameprofile, (Date)null, (String)null, (Date)null, (String)null);
/*    */   }
/*    */   
/*    */   public GameProfileBanEntry(GameProfile gameprofile, Date date, String s, Date date1, String s1) {
/* 16 */     super(gameprofile, date, s, date1, s1);
/*    */   }
/*    */   
/*    */   public GameProfileBanEntry(JsonObject jsonobject) {
/* 20 */     super(b(jsonobject), jsonobject);
/*    */   }
/*    */   
/*    */   protected void a(JsonObject jsonobject) {
/* 24 */     if (getKey() != null) {
/* 25 */       jsonobject.addProperty("uuid", (((GameProfile)getKey()).getId() == null) ? "" : ((GameProfile)getKey()).getId().toString());
/* 26 */       jsonobject.addProperty("name", ((GameProfile)getKey()).getName());
/* 27 */       super.a(jsonobject);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static GameProfile b(JsonObject jsonobject) {
/* 34 */     UUID uuid = null;
/* 35 */     String name = null;
/* 36 */     if (jsonobject.has("uuid")) {
/* 37 */       String s = jsonobject.get("uuid").getAsString();
/*    */       
/*    */       try {
/* 40 */         uuid = UUID.fromString(s);
/* 41 */       } catch (Throwable throwable) {}
/*    */     } 
/*    */ 
/*    */     
/* 45 */     if (jsonobject.has("name"))
/*    */     {
/* 47 */       name = jsonobject.get("name").getAsString();
/*    */     }
/* 49 */     if (uuid != null || name != null)
/*    */     {
/* 51 */       return new GameProfile(uuid, name);
/*    */     }
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GameProfileBanEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */