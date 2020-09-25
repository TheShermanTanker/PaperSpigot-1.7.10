/*    */ package org.bukkit.craftbukkit.libs.jline;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoInterruptUnixTerminal
/*    */   extends UnixTerminal
/*    */ {
/*    */   public void init() throws Exception {
/* 35 */     super.init();
/* 36 */     getSettings().set("intr undef");
/*    */   }
/*    */ 
/*    */   
/*    */   public void restore() throws Exception {
/* 41 */     getSettings().set("intr ^C");
/* 42 */     super.restore();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\NoInterruptUnixTerminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */