/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Date;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ 
/*    */ class UserCacheEntry
/*    */ {
/*    */   private final GameProfile b;
/*    */   private final Date c;
/*    */   final UserCache a;
/*    */   
/*    */   private UserCacheEntry(UserCache usercache, GameProfile gameprofile, Date date) {
/* 14 */     this.a = usercache;
/* 15 */     this.b = gameprofile;
/* 16 */     this.c = date;
/*    */   }
/*    */   
/*    */   public GameProfile a() {
/* 20 */     return this.b;
/*    */   }
/*    */   
/*    */   public Date b() {
/* 24 */     return this.c;
/*    */   }
/*    */   
/*    */   UserCacheEntry(UserCache usercache, GameProfile gameprofile, Date date, GameProfileLookup gameprofilelookup) {
/* 28 */     this(usercache, gameprofile, date);
/*    */   }
/*    */   
/*    */   static Date a(UserCacheEntry usercacheentry) {
/* 32 */     return usercacheentry.c;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\UserCacheEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */