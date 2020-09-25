/*    */ package net.minecraft.util.io.netty.handler.codec.spdy;
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
/*    */ public abstract class DefaultSpdyStreamFrame
/*    */   implements SpdyStreamFrame
/*    */ {
/*    */   private int streamId;
/*    */   private boolean last;
/*    */   
/*    */   protected DefaultSpdyStreamFrame(int streamId) {
/* 32 */     setStreamId(streamId);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStreamId() {
/* 37 */     return this.streamId;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyStreamFrame setStreamId(int streamId) {
/* 42 */     if (streamId <= 0) {
/* 43 */       throw new IllegalArgumentException("Stream-ID must be positive: " + streamId);
/*    */     }
/*    */     
/* 46 */     this.streamId = streamId;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLast() {
/* 52 */     return this.last;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyStreamFrame setLast(boolean last) {
/* 57 */     this.last = last;
/* 58 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\DefaultSpdyStreamFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */