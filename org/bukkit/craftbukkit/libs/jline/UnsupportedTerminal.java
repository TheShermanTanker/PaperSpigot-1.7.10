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
/*    */ public class UnsupportedTerminal
/*    */   extends TerminalSupport
/*    */ {
/*    */   public UnsupportedTerminal() {
/* 21 */     super(false);
/* 22 */     setAnsiSupported(false);
/* 23 */     setEchoEnabled(true);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\UnsupportedTerminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */