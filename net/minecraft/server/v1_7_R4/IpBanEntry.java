/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Date;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ 
/*    */ public class IpBanEntry
/*    */   extends ExpirableListEntry {
/*    */   public IpBanEntry(String paramString) {
/*  9 */     this(paramString, null, null, null, null);
/*    */   }
/*    */   
/*    */   public IpBanEntry(String paramString1, Date paramDate1, String paramString2, Date paramDate2, String paramString3) {
/* 13 */     super(paramString1, paramDate1, paramString2, paramDate2, paramString3);
/*    */   }
/*    */   
/*    */   public IpBanEntry(JsonObject paramJsonObject) {
/* 17 */     super(b(paramJsonObject), paramJsonObject);
/*    */   }
/*    */   
/*    */   private static String b(JsonObject paramJsonObject) {
/* 21 */     return paramJsonObject.has("ip") ? paramJsonObject.get("ip").getAsString() : null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(JsonObject paramJsonObject) {
/* 26 */     if (getKey() == null) {
/*    */       return;
/*    */     }
/* 29 */     paramJsonObject.addProperty("ip", (String)getKey());
/* 30 */     super.a(paramJsonObject);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IpBanEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */