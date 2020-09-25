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
/*    */ public class DefaultSpdyGoAwayFrame
/*    */   implements SpdyGoAwayFrame
/*    */ {
/*    */   private int lastGoodStreamId;
/*    */   private SpdySessionStatus status;
/*    */   
/*    */   public DefaultSpdyGoAwayFrame(int lastGoodStreamId) {
/* 34 */     this(lastGoodStreamId, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultSpdyGoAwayFrame(int lastGoodStreamId, int statusCode) {
/* 44 */     this(lastGoodStreamId, SpdySessionStatus.valueOf(statusCode));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultSpdyGoAwayFrame(int lastGoodStreamId, SpdySessionStatus status) {
/* 54 */     setLastGoodStreamId(lastGoodStreamId);
/* 55 */     setStatus(status);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLastGoodStreamId() {
/* 60 */     return this.lastGoodStreamId;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyGoAwayFrame setLastGoodStreamId(int lastGoodStreamId) {
/* 65 */     if (lastGoodStreamId < 0) {
/* 66 */       throw new IllegalArgumentException("Last-good-stream-ID cannot be negative: " + lastGoodStreamId);
/*    */     }
/*    */     
/* 69 */     this.lastGoodStreamId = lastGoodStreamId;
/* 70 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdySessionStatus getStatus() {
/* 75 */     return this.status;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyGoAwayFrame setStatus(SpdySessionStatus status) {
/* 80 */     this.status = status;
/* 81 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 86 */     StringBuilder buf = new StringBuilder();
/* 87 */     buf.append(getClass().getSimpleName());
/* 88 */     buf.append(StringUtil.NEWLINE);
/* 89 */     buf.append("--> Last-good-stream-ID = ");
/* 90 */     buf.append(getLastGoodStreamId());
/* 91 */     buf.append(StringUtil.NEWLINE);
/* 92 */     buf.append("--> Status: ");
/* 93 */     buf.append(getStatus().toString());
/* 94 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\DefaultSpdyGoAwayFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */