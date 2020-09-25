/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.UnsupportedMessageTypeException;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
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
/*     */ public class SpdyFrameEncoder
/*     */   extends MessageToByteEncoder<SpdyFrame>
/*     */ {
/*     */   private final int version;
/*     */   private final SpdyHeaderBlockEncoder headerBlockEncoder;
/*     */   
/*     */   public SpdyFrameEncoder(int version) {
/*  43 */     this(version, 6, 15, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdyFrameEncoder(int version, int compressionLevel, int windowBits, int memLevel) {
/*  50 */     this(version, SpdyHeaderBlockEncoder.newInstance(version, compressionLevel, windowBits, memLevel));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SpdyFrameEncoder(int version, SpdyHeaderBlockEncoder headerBlockEncoder) {
/*  55 */     if (version < 2 || version > 3) {
/*  56 */       throw new IllegalArgumentException("unknown version: " + version);
/*     */     }
/*     */     
/*  59 */     this.version = version;
/*  60 */     this.headerBlockEncoder = headerBlockEncoder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  65 */     ctx.channel().closeFuture().addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/*  68 */             SpdyFrameEncoder.this.headerBlockEncoder.end();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void encode(ChannelHandlerContext ctx, SpdyFrame msg, ByteBuf out) throws Exception {
/*  75 */     if (msg instanceof SpdyDataFrame) {
/*     */       
/*  77 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/*  78 */       ByteBuf data = spdyDataFrame.content();
/*  79 */       byte flags = spdyDataFrame.isLast() ? 1 : 0;
/*  80 */       out.ensureWritable(8 + data.readableBytes());
/*  81 */       out.writeInt(spdyDataFrame.getStreamId() & Integer.MAX_VALUE);
/*  82 */       out.writeByte(flags);
/*  83 */       out.writeMedium(data.readableBytes());
/*  84 */       out.writeBytes(data, data.readerIndex(), data.readableBytes());
/*     */     }
/*  86 */     else if (msg instanceof SpdySynStreamFrame) {
/*     */       
/*  88 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/*  89 */       ByteBuf data = this.headerBlockEncoder.encode(ctx, spdySynStreamFrame); try {
/*     */         int length;
/*  91 */         byte flags = spdySynStreamFrame.isLast() ? 1 : 0;
/*  92 */         if (spdySynStreamFrame.isUnidirectional()) {
/*  93 */           flags = (byte)(flags | 0x2);
/*     */         }
/*  95 */         int headerBlockLength = data.readableBytes();
/*     */         
/*  97 */         if (this.version < 3) {
/*  98 */           length = (headerBlockLength == 0) ? 12 : (10 + headerBlockLength);
/*     */         } else {
/* 100 */           length = 10 + headerBlockLength;
/*     */         } 
/* 102 */         out.ensureWritable(8 + length);
/* 103 */         out.writeShort(this.version | 0x8000);
/* 104 */         out.writeShort(1);
/* 105 */         out.writeByte(flags);
/* 106 */         out.writeMedium(length);
/* 107 */         out.writeInt(spdySynStreamFrame.getStreamId());
/* 108 */         out.writeInt(spdySynStreamFrame.getAssociatedToStreamId());
/* 109 */         if (this.version < 3) {
/*     */           
/* 111 */           byte priority = spdySynStreamFrame.getPriority();
/* 112 */           if (priority > 3) {
/* 113 */             priority = 3;
/*     */           }
/* 115 */           out.writeShort((priority & 0xFF) << 14);
/*     */         } else {
/* 117 */           out.writeShort((spdySynStreamFrame.getPriority() & 0xFF) << 13);
/*     */         } 
/* 119 */         if (this.version < 3 && data.readableBytes() == 0) {
/* 120 */           out.writeShort(0);
/*     */         }
/* 122 */         out.writeBytes(data, data.readerIndex(), headerBlockLength);
/*     */       } finally {
/* 124 */         data.release();
/*     */       }
/*     */     
/* 127 */     } else if (msg instanceof SpdySynReplyFrame) {
/*     */       
/* 129 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 130 */       ByteBuf data = this.headerBlockEncoder.encode(ctx, spdySynReplyFrame); try {
/*     */         int length;
/* 132 */         byte flags = spdySynReplyFrame.isLast() ? 1 : 0;
/* 133 */         int headerBlockLength = data.readableBytes();
/*     */         
/* 135 */         if (this.version < 3) {
/* 136 */           length = (headerBlockLength == 0) ? 8 : (6 + headerBlockLength);
/*     */         } else {
/* 138 */           length = 4 + headerBlockLength;
/*     */         } 
/* 140 */         out.ensureWritable(8 + length);
/* 141 */         out.writeShort(this.version | 0x8000);
/* 142 */         out.writeShort(2);
/* 143 */         out.writeByte(flags);
/* 144 */         out.writeMedium(length);
/* 145 */         out.writeInt(spdySynReplyFrame.getStreamId());
/* 146 */         if (this.version < 3) {
/* 147 */           if (headerBlockLength == 0) {
/* 148 */             out.writeInt(0);
/*     */           } else {
/* 150 */             out.writeShort(0);
/*     */           } 
/*     */         }
/* 153 */         out.writeBytes(data, data.readerIndex(), headerBlockLength);
/*     */       } finally {
/* 155 */         data.release();
/*     */       }
/*     */     
/* 158 */     } else if (msg instanceof SpdyRstStreamFrame) {
/*     */       
/* 160 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 161 */       out.ensureWritable(16);
/* 162 */       out.writeShort(this.version | 0x8000);
/* 163 */       out.writeShort(3);
/* 164 */       out.writeInt(8);
/* 165 */       out.writeInt(spdyRstStreamFrame.getStreamId());
/* 166 */       out.writeInt(spdyRstStreamFrame.getStatus().getCode());
/*     */     }
/* 168 */     else if (msg instanceof SpdySettingsFrame) {
/*     */       
/* 170 */       SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
/* 171 */       byte flags = spdySettingsFrame.clearPreviouslyPersistedSettings() ? 1 : 0;
/*     */       
/* 173 */       Set<Integer> IDs = spdySettingsFrame.getIds();
/* 174 */       int numEntries = IDs.size();
/* 175 */       int length = 4 + numEntries * 8;
/* 176 */       out.ensureWritable(8 + length);
/* 177 */       out.writeShort(this.version | 0x8000);
/* 178 */       out.writeShort(4);
/* 179 */       out.writeByte(flags);
/* 180 */       out.writeMedium(length);
/* 181 */       out.writeInt(numEntries);
/* 182 */       for (Integer ID : IDs) {
/* 183 */         int id = ID.intValue();
/* 184 */         byte ID_flags = 0;
/* 185 */         if (spdySettingsFrame.isPersistValue(id)) {
/* 186 */           ID_flags = (byte)(ID_flags | 0x1);
/*     */         }
/* 188 */         if (spdySettingsFrame.isPersisted(id)) {
/* 189 */           ID_flags = (byte)(ID_flags | 0x2);
/*     */         }
/* 191 */         if (this.version < 3) {
/*     */ 
/*     */ 
/*     */           
/* 195 */           out.writeByte(id & 0xFF);
/* 196 */           out.writeByte(id >> 8 & 0xFF);
/* 197 */           out.writeByte(id >> 16 & 0xFF);
/* 198 */           out.writeByte(ID_flags);
/*     */         } else {
/* 200 */           out.writeByte(ID_flags);
/* 201 */           out.writeMedium(id);
/*     */         } 
/* 203 */         out.writeInt(spdySettingsFrame.getValue(id));
/*     */       }
/*     */     
/* 206 */     } else if (msg instanceof SpdyPingFrame) {
/*     */       
/* 208 */       SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
/* 209 */       out.ensureWritable(12);
/* 210 */       out.writeShort(this.version | 0x8000);
/* 211 */       out.writeShort(6);
/* 212 */       out.writeInt(4);
/* 213 */       out.writeInt(spdyPingFrame.getId());
/*     */     }
/* 215 */     else if (msg instanceof SpdyGoAwayFrame) {
/*     */       
/* 217 */       SpdyGoAwayFrame spdyGoAwayFrame = (SpdyGoAwayFrame)msg;
/* 218 */       int length = (this.version < 3) ? 4 : 8;
/* 219 */       out.ensureWritable(8 + length);
/* 220 */       out.writeShort(this.version | 0x8000);
/* 221 */       out.writeShort(7);
/* 222 */       out.writeInt(length);
/* 223 */       out.writeInt(spdyGoAwayFrame.getLastGoodStreamId());
/* 224 */       if (this.version >= 3) {
/* 225 */         out.writeInt(spdyGoAwayFrame.getStatus().getCode());
/*     */       }
/*     */     }
/* 228 */     else if (msg instanceof SpdyHeadersFrame) {
/*     */       
/* 230 */       SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 231 */       ByteBuf data = this.headerBlockEncoder.encode(ctx, spdyHeadersFrame); try {
/*     */         int length;
/* 233 */         byte flags = spdyHeadersFrame.isLast() ? 1 : 0;
/* 234 */         int headerBlockLength = data.readableBytes();
/*     */         
/* 236 */         if (this.version < 3) {
/* 237 */           length = (headerBlockLength == 0) ? 4 : (6 + headerBlockLength);
/*     */         } else {
/* 239 */           length = 4 + headerBlockLength;
/*     */         } 
/* 241 */         out.ensureWritable(8 + length);
/* 242 */         out.writeShort(this.version | 0x8000);
/* 243 */         out.writeShort(8);
/* 244 */         out.writeByte(flags);
/* 245 */         out.writeMedium(length);
/* 246 */         out.writeInt(spdyHeadersFrame.getStreamId());
/* 247 */         if (this.version < 3 && headerBlockLength != 0) {
/* 248 */           out.writeShort(0);
/*     */         }
/* 250 */         out.writeBytes(data, data.readerIndex(), headerBlockLength);
/*     */       } finally {
/* 252 */         data.release();
/*     */       }
/*     */     
/* 255 */     } else if (msg instanceof SpdyWindowUpdateFrame) {
/*     */       
/* 257 */       SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)msg;
/* 258 */       out.ensureWritable(16);
/* 259 */       out.writeShort(this.version | 0x8000);
/* 260 */       out.writeShort(9);
/* 261 */       out.writeInt(8);
/* 262 */       out.writeInt(spdyWindowUpdateFrame.getStreamId());
/* 263 */       out.writeInt(spdyWindowUpdateFrame.getDeltaWindowSize());
/*     */     } else {
/* 265 */       throw new UnsupportedMessageTypeException(msg, new Class[0]);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyFrameEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */