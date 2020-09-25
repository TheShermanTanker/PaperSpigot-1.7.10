/*    */ package net.minecraft.util.io.netty.handler.codec.http.multipart;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
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
/*    */ final class CaseIgnoringComparator
/*    */   implements Comparator<String>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4582133183775373862L;
/* 25 */   static final CaseIgnoringComparator INSTANCE = new CaseIgnoringComparator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compare(String o1, String o2) {
/* 32 */     return o1.compareToIgnoreCase(o2);
/*    */   }
/*    */ 
/*    */   
/*    */   private Object readResolve() {
/* 37 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\multipart\CaseIgnoringComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */