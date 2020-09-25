/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.SocketAddress;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ 
/*    */ public class IpBanList
/*    */   extends JsonList
/*    */ {
/*    */   public IpBanList(File paramFile) {
/* 11 */     super(paramFile);
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonListEntry a(JsonObject paramJsonObject) {
/* 16 */     return new IpBanEntry(paramJsonObject);
/*    */   }
/*    */   
/*    */   public boolean isBanned(SocketAddress paramSocketAddress) {
/* 20 */     String str = c(paramSocketAddress);
/* 21 */     return d(str);
/*    */   }
/*    */   
/*    */   public IpBanEntry get(SocketAddress paramSocketAddress) {
/* 25 */     String str = c(paramSocketAddress);
/* 26 */     return (IpBanEntry)get(str);
/*    */   }
/*    */   
/*    */   private String c(SocketAddress paramSocketAddress) {
/* 30 */     String str = paramSocketAddress.toString();
/* 31 */     if (str.contains("/")) {
/* 32 */       str = str.substring(str.indexOf('/') + 1);
/*    */     }
/* 34 */     if (str.contains(":")) {
/* 35 */       str = str.substring(0, str.indexOf(':'));
/*    */     }
/* 37 */     return str;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IpBanList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */