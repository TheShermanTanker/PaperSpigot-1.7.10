/*    */ package org.bukkit.craftbukkit.libs.jline.console;
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
/*    */ public class CursorBuffer
/*    */ {
/*    */   private boolean overTyping = false;
/* 21 */   public int cursor = 0;
/*    */   
/* 23 */   public final StringBuilder buffer = new StringBuilder();
/*    */   
/*    */   public boolean isOverTyping() {
/* 26 */     return this.overTyping;
/*    */   }
/*    */   
/*    */   public void setOverTyping(boolean b) {
/* 30 */     this.overTyping = b;
/*    */   }
/*    */   
/*    */   public int length() {
/* 34 */     return this.buffer.length();
/*    */   }
/*    */   
/*    */   public char nextChar() {
/* 38 */     if (this.cursor == this.buffer.length()) {
/* 39 */       return Character.MIN_VALUE;
/*    */     }
/* 41 */     return this.buffer.charAt(this.cursor);
/*    */   }
/*    */ 
/*    */   
/*    */   public char current() {
/* 46 */     if (this.cursor <= 0) {
/* 47 */       return Character.MIN_VALUE;
/*    */     }
/*    */     
/* 50 */     return this.buffer.charAt(this.cursor - 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(char c) {
/* 61 */     this.buffer.insert(this.cursor++, c);
/* 62 */     if (isOverTyping() && this.cursor < this.buffer.length()) {
/* 63 */       this.buffer.deleteCharAt(this.cursor);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(CharSequence str) {
/* 71 */     assert str != null;
/*    */     
/* 73 */     if (this.buffer.length() == 0) {
/* 74 */       this.buffer.append(str);
/*    */     } else {
/*    */       
/* 77 */       this.buffer.insert(this.cursor, str);
/*    */     } 
/*    */     
/* 80 */     this.cursor += str.length();
/*    */     
/* 82 */     if (isOverTyping() && this.cursor < this.buffer.length()) {
/* 83 */       this.buffer.delete(this.cursor, this.cursor + str.length());
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean clear() {
/* 88 */     if (this.buffer.length() == 0) {
/* 89 */       return false;
/*    */     }
/*    */     
/* 92 */     this.buffer.delete(0, this.buffer.length());
/* 93 */     this.cursor = 0;
/* 94 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return this.buffer.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\CursorBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */