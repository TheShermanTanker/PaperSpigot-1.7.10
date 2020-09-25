/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocaleI18n
/*    */ {
/*  7 */   private static LocaleLanguage a = LocaleLanguage.a();
/*  8 */   private static LocaleLanguage b = new LocaleLanguage();
/*    */ 
/*    */   
/*    */   public static String get(String paramString) {
/* 12 */     return a.a(paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String get(String paramString, Object... paramVarArgs) {
/* 17 */     return a.a(paramString, paramVarArgs);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String b(String paramString) {
/* 22 */     return b.a(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean c(String paramString) {
/* 32 */     return a.b(paramString);
/*    */   }
/*    */   
/*    */   public static long a() {
/* 36 */     return a.c();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\LocaleI18n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */