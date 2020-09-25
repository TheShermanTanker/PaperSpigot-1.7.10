/*    */ package org.bukkit;
/*    */ 
/*    */ import java.util.Date;
/*    */ import java.util.Set;
/*    */ 
/*    */ public interface BanList {
/*    */   BanEntry getBanEntry(String paramString);
/*    */   
/*    */   BanEntry addBan(String paramString1, String paramString2, Date paramDate, String paramString3);
/*    */   
/*    */   Set<BanEntry> getBanEntries();
/*    */   
/*    */   boolean isBanned(String paramString);
/*    */   
/*    */   void pardon(String paramString);
/*    */   
/*    */   public enum Type {
/* 18 */     NAME,
/*    */ 
/*    */ 
/*    */     
/* 22 */     IP;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\BanList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */