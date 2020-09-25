/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSpdyHeadersFrame
/*     */   extends DefaultSpdyStreamFrame
/*     */   implements SpdyHeadersFrame
/*     */ {
/*     */   private boolean invalid;
/*     */   private boolean truncated;
/*  30 */   private final SpdyHeaders headers = new DefaultSpdyHeaders();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSpdyHeadersFrame(int streamId) {
/*  38 */     super(streamId);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeadersFrame setStreamId(int streamId) {
/*  43 */     super.setStreamId(streamId);
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeadersFrame setLast(boolean last) {
/*  49 */     super.setLast(last);
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvalid() {
/*  55 */     return this.invalid;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeadersFrame setInvalid() {
/*  60 */     this.invalid = true;
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTruncated() {
/*  66 */     return this.truncated;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeadersFrame setTruncated() {
/*  71 */     this.truncated = true;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdyHeaders headers() {
/*  77 */     return this.headers;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  82 */     StringBuilder buf = new StringBuilder();
/*  83 */     buf.append(getClass().getSimpleName());
/*  84 */     buf.append("(last: ");
/*  85 */     buf.append(isLast());
/*  86 */     buf.append(')');
/*  87 */     buf.append(StringUtil.NEWLINE);
/*  88 */     buf.append("--> Stream-ID = ");
/*  89 */     buf.append(getStreamId());
/*  90 */     buf.append(StringUtil.NEWLINE);
/*  91 */     buf.append("--> Headers:");
/*  92 */     buf.append(StringUtil.NEWLINE);
/*  93 */     appendHeaders(buf);
/*     */ 
/*     */     
/*  96 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/*  97 */     return buf.toString();
/*     */   }
/*     */   
/*     */   protected void appendHeaders(StringBuilder buf) {
/* 101 */     for (Map.Entry<String, String> e : headers().entries()) {
/* 102 */       buf.append("    ");
/* 103 */       buf.append(e.getKey());
/* 104 */       buf.append(": ");
/* 105 */       buf.append(e.getValue());
/* 106 */       buf.append(StringUtil.NEWLINE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\DefaultSpdyHeadersFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */