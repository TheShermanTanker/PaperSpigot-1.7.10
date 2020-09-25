/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSpdySynStreamFrame
/*     */   extends DefaultSpdyHeadersFrame
/*     */   implements SpdySynStreamFrame
/*     */ {
/*     */   private int associatedToStreamId;
/*     */   private byte priority;
/*     */   private boolean unidirectional;
/*     */   
/*     */   public DefaultSpdySynStreamFrame(int streamId, int associatedToStreamId, byte priority) {
/*  39 */     super(streamId);
/*  40 */     setAssociatedToStreamId(associatedToStreamId);
/*  41 */     setPriority(priority);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdySynStreamFrame setStreamId(int streamId) {
/*  46 */     super.setStreamId(streamId);
/*  47 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdySynStreamFrame setLast(boolean last) {
/*  52 */     super.setLast(last);
/*  53 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdySynStreamFrame setInvalid() {
/*  58 */     super.setInvalid();
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAssociatedToStreamId() {
/*  64 */     return this.associatedToStreamId;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdySynStreamFrame setAssociatedToStreamId(int associatedToStreamId) {
/*  69 */     if (associatedToStreamId < 0) {
/*  70 */       throw new IllegalArgumentException("Associated-To-Stream-ID cannot be negative: " + associatedToStreamId);
/*     */     }
/*     */ 
/*     */     
/*  74 */     this.associatedToStreamId = associatedToStreamId;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getPriority() {
/*  80 */     return this.priority;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdySynStreamFrame setPriority(byte priority) {
/*  85 */     if (priority < 0 || priority > 7) {
/*  86 */       throw new IllegalArgumentException("Priority must be between 0 and 7 inclusive: " + priority);
/*     */     }
/*     */     
/*  89 */     this.priority = priority;
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnidirectional() {
/*  95 */     return this.unidirectional;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpdySynStreamFrame setUnidirectional(boolean unidirectional) {
/* 100 */     this.unidirectional = unidirectional;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 106 */     StringBuilder buf = new StringBuilder();
/* 107 */     buf.append(getClass().getSimpleName());
/* 108 */     buf.append("(last: ");
/* 109 */     buf.append(isLast());
/* 110 */     buf.append("; unidirectional: ");
/* 111 */     buf.append(isUnidirectional());
/* 112 */     buf.append(')');
/* 113 */     buf.append(StringUtil.NEWLINE);
/* 114 */     buf.append("--> Stream-ID = ");
/* 115 */     buf.append(getStreamId());
/* 116 */     buf.append(StringUtil.NEWLINE);
/* 117 */     if (this.associatedToStreamId != 0) {
/* 118 */       buf.append("--> Associated-To-Stream-ID = ");
/* 119 */       buf.append(getAssociatedToStreamId());
/* 120 */       buf.append(StringUtil.NEWLINE);
/*     */     } 
/* 122 */     buf.append("--> Priority = ");
/* 123 */     buf.append(getPriority());
/* 124 */     buf.append(StringUtil.NEWLINE);
/* 125 */     buf.append("--> Headers:");
/* 126 */     buf.append(StringUtil.NEWLINE);
/* 127 */     appendHeaders(buf);
/*     */ 
/*     */     
/* 130 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/* 131 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\DefaultSpdySynStreamFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */