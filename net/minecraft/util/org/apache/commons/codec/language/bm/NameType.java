/*    */ package net.minecraft.util.org.apache.commons.codec.language.bm;
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
/*    */ public enum NameType
/*    */ {
/* 31 */   ASHKENAZI("ash"),
/*    */ 
/*    */   
/* 34 */   GENERIC("gen"),
/*    */ 
/*    */   
/* 37 */   SEPHARDIC("sep");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   NameType(String name) {
/* 42 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 51 */     return this.name;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\codec\language\bm\NameType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */