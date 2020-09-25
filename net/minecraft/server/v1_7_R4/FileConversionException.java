/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ class FileConversionException
/*    */   extends RuntimeException
/*    */ {
/*    */   private FileConversionException(String s, Throwable throwable) {
/*  8 */     super(s, throwable);
/*    */   }
/*    */   
/*    */   private FileConversionException(String s) {
/* 12 */     super(s);
/*    */   }
/*    */   
/*    */   FileConversionException(String s, PredicateEmptyList predicateemptylist) {
/* 16 */     this(s);
/*    */   }
/*    */   
/*    */   FileConversionException(String s, Throwable throwable, PredicateEmptyList predicateemptylist) {
/* 20 */     this(s, throwable);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\FileConversionException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */