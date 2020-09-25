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
/*    */ 
/*    */ public class DefaultSpdyRstStreamFrame
/*    */   extends DefaultSpdyStreamFrame
/*    */   implements SpdyRstStreamFrame
/*    */ {
/*    */   private SpdyStreamStatus status;
/*    */   
/*    */   public DefaultSpdyRstStreamFrame(int streamId, int statusCode) {
/* 35 */     this(streamId, SpdyStreamStatus.valueOf(statusCode));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultSpdyRstStreamFrame(int streamId, SpdyStreamStatus status) {
/* 45 */     super(streamId);
/* 46 */     setStatus(status);
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyRstStreamFrame setStreamId(int streamId) {
/* 51 */     super.setStreamId(streamId);
/* 52 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyRstStreamFrame setLast(boolean last) {
/* 57 */     super.setLast(last);
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyStreamStatus getStatus() {
/* 63 */     return this.status;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyRstStreamFrame setStatus(SpdyStreamStatus status) {
/* 68 */     this.status = status;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 74 */     StringBuilder buf = new StringBuilder();
/* 75 */     buf.append(getClass().getSimpleName());
/* 76 */     buf.append(StringUtil.NEWLINE);
/* 77 */     buf.append("--> Stream-ID = ");
/* 78 */     buf.append(getStreamId());
/* 79 */     buf.append(StringUtil.NEWLINE);
/* 80 */     buf.append("--> Status: ");
/* 81 */     buf.append(getStatus().toString());
/* 82 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\DefaultSpdyRstStreamFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */