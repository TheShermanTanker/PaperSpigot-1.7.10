/*    */ package net.minecraft.util.io.netty.util;
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
/*    */ public final class ReferenceCountUtil
/*    */ {
/*    */   public static <T> T retain(T msg) {
/* 29 */     if (msg instanceof ReferenceCounted) {
/* 30 */       return (T)((ReferenceCounted)msg).retain();
/*    */     }
/* 32 */     return msg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> T retain(T msg, int increment) {
/* 41 */     if (msg instanceof ReferenceCounted) {
/* 42 */       return (T)((ReferenceCounted)msg).retain(increment);
/*    */     }
/* 44 */     return msg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean release(Object msg) {
/* 52 */     if (msg instanceof ReferenceCounted) {
/* 53 */       return ((ReferenceCounted)msg).release();
/*    */     }
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean release(Object msg, int decrement) {
/* 63 */     if (msg instanceof ReferenceCounted) {
/* 64 */       return ((ReferenceCounted)msg).release(decrement);
/*    */     }
/* 66 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\ReferenceCountUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */