/*    */ package net.minecraft.util.io.netty.handler.timeout;
/*    */ 
/*    */ import net.minecraft.util.io.netty.channel.ChannelException;
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
/*    */ public class TimeoutException
/*    */   extends ChannelException
/*    */ {
/*    */   private static final long serialVersionUID = 4673641882869672533L;
/*    */   
/*    */   public Throwable fillInStackTrace() {
/* 32 */     return (Throwable)this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\timeout\TimeoutException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */