/*    */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*    */ 
/*    */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*    */ public class DefaultSpdyPingFrame
/*    */   implements SpdyPingFrame
/*    */ {
/*    */   private int id;
/*    */   
/*    */   public DefaultSpdyPingFrame(int id) {
/* 33 */     setId(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 38 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyPingFrame setId(int id) {
/* 43 */     this.id = id;
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     StringBuilder buf = new StringBuilder();
/* 50 */     buf.append(getClass().getSimpleName());
/* 51 */     buf.append(StringUtil.NEWLINE);
/* 52 */     buf.append("--> ID = ");
/* 53 */     buf.append(getId());
/* 54 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\DefaultSpdyPingFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */