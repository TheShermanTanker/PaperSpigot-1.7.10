/*    */ package org.bukkit.craftbukkit.libs.joptsimple;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ArgumentList
/*    */ {
/*    */   private final String[] arguments;
/*    */   private int currentIndex;
/*    */   
/*    */   ArgumentList(String... arguments) {
/* 41 */     this.arguments = (String[])arguments.clone();
/*    */   }
/*    */   
/*    */   boolean hasMore() {
/* 45 */     return (this.currentIndex < this.arguments.length);
/*    */   }
/*    */   
/*    */   String next() {
/* 49 */     return this.arguments[this.currentIndex++];
/*    */   }
/*    */   
/*    */   String peek() {
/* 53 */     return this.arguments[this.currentIndex];
/*    */   }
/*    */   
/*    */   void treatNextAsLongOption() {
/* 57 */     if ('-' != this.arguments[this.currentIndex].charAt(0))
/* 58 */       this.arguments[this.currentIndex] = "--" + this.arguments[this.currentIndex]; 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\ArgumentList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */