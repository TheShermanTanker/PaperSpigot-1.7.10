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
/*    */ public class DefaultSpdyWindowUpdateFrame
/*    */   implements SpdyWindowUpdateFrame
/*    */ {
/*    */   private int streamId;
/*    */   private int deltaWindowSize;
/*    */   
/*    */   public DefaultSpdyWindowUpdateFrame(int streamId, int deltaWindowSize) {
/* 35 */     setStreamId(streamId);
/* 36 */     setDeltaWindowSize(deltaWindowSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStreamId() {
/* 41 */     return this.streamId;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyWindowUpdateFrame setStreamId(int streamId) {
/* 46 */     if (streamId < 0) {
/* 47 */       throw new IllegalArgumentException("Stream-ID cannot be negative: " + streamId);
/*    */     }
/*    */     
/* 50 */     this.streamId = streamId;
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDeltaWindowSize() {
/* 56 */     return this.deltaWindowSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyWindowUpdateFrame setDeltaWindowSize(int deltaWindowSize) {
/* 61 */     if (deltaWindowSize <= 0) {
/* 62 */       throw new IllegalArgumentException("Delta-Window-Size must be positive: " + deltaWindowSize);
/*    */     }
/*    */ 
/*    */     
/* 66 */     this.deltaWindowSize = deltaWindowSize;
/* 67 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 72 */     StringBuilder buf = new StringBuilder();
/* 73 */     buf.append(getClass().getSimpleName());
/* 74 */     buf.append(StringUtil.NEWLINE);
/* 75 */     buf.append("--> Stream-ID = ");
/* 76 */     buf.append(getStreamId());
/* 77 */     buf.append(StringUtil.NEWLINE);
/* 78 */     buf.append("--> Delta-Window-Size = ");
/* 79 */     buf.append(getDeltaWindowSize());
/* 80 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\DefaultSpdyWindowUpdateFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */