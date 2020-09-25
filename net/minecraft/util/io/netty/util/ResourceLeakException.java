/*    */ package net.minecraft.util.io.netty.util;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public class ResourceLeakException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 7186453858343358280L;
/*    */   private final StackTraceElement[] cachedStackTrace;
/*    */   
/*    */   public ResourceLeakException() {
/* 28 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(String message) {
/* 32 */     super(message);
/* 33 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(String message, Throwable cause) {
/* 37 */     super(message, cause);
/* 38 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(Throwable cause) {
/* 42 */     super(cause);
/* 43 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 48 */     StackTraceElement[] trace = this.cachedStackTrace;
/* 49 */     int hashCode = 0;
/* 50 */     for (StackTraceElement e : trace) {
/* 51 */       hashCode = hashCode * 31 + e.hashCode();
/*    */     }
/* 53 */     return hashCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 58 */     if (!(o instanceof ResourceLeakException)) {
/* 59 */       return false;
/*    */     }
/* 61 */     if (o == this) {
/* 62 */       return true;
/*    */     }
/*    */     
/* 65 */     return Arrays.equals((Object[])this.cachedStackTrace, (Object[])((ResourceLeakException)o).cachedStackTrace);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\ResourceLeakException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */