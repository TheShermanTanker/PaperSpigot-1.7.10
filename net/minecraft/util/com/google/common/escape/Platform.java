/*    */ package net.minecraft.util.com.google.common.escape;
/*    */ 
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(emulated = true)
/*    */ final class Platform
/*    */ {
/*    */   static char[] charBufferFromThreadLocal() {
/* 32 */     return DEST_TL.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   private static final ThreadLocal<char[]> DEST_TL = new ThreadLocal<char[]>()
/*    */     {
/*    */       protected char[] initialValue() {
/* 43 */         return new char[1024];
/*    */       }
/*    */     };
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\escape\Platform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */