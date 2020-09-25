/*     */ package net.minecraft.util.io.netty.handler.logging;
/*     */ 
/*     */ import java.net.SocketAddress;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*     */ import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler.Sharable;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogLevel;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ @Sharable
/*     */ public class LoggingHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  38 */   private static final LogLevel DEFAULT_LEVEL = LogLevel.DEBUG;
/*     */   
/*  40 */   private static final String NEWLINE = String.format("%n", new Object[0]);
/*     */   
/*  42 */   private static final String[] BYTE2HEX = new String[256];
/*  43 */   private static final String[] HEXPADDING = new String[16];
/*  44 */   private static final String[] BYTEPADDING = new String[16];
/*  45 */   private static final char[] BYTE2CHAR = new char[256];
/*     */   
/*     */   protected final InternalLogger logger;
/*     */   
/*     */   static {
/*     */     int i;
/*  51 */     for (i = 0; i < 10; i++) {
/*  52 */       StringBuilder buf = new StringBuilder(3);
/*  53 */       buf.append(" 0");
/*  54 */       buf.append(i);
/*  55 */       BYTE2HEX[i] = buf.toString();
/*     */     } 
/*  57 */     for (; i < 16; i++) {
/*  58 */       StringBuilder buf = new StringBuilder(3);
/*  59 */       buf.append(" 0");
/*  60 */       buf.append((char)(97 + i - 10));
/*  61 */       BYTE2HEX[i] = buf.toString();
/*     */     } 
/*  63 */     for (; i < BYTE2HEX.length; i++) {
/*  64 */       StringBuilder buf = new StringBuilder(3);
/*  65 */       buf.append(' ');
/*  66 */       buf.append(Integer.toHexString(i));
/*  67 */       BYTE2HEX[i] = buf.toString();
/*     */     } 
/*     */ 
/*     */     
/*  71 */     for (i = 0; i < HEXPADDING.length; i++) {
/*  72 */       int padding = HEXPADDING.length - i;
/*  73 */       StringBuilder buf = new StringBuilder(padding * 3);
/*  74 */       for (int j = 0; j < padding; j++) {
/*  75 */         buf.append("   ");
/*     */       }
/*  77 */       HEXPADDING[i] = buf.toString();
/*     */     } 
/*     */ 
/*     */     
/*  81 */     for (i = 0; i < BYTEPADDING.length; i++) {
/*  82 */       int padding = BYTEPADDING.length - i;
/*  83 */       StringBuilder buf = new StringBuilder(padding);
/*  84 */       for (int j = 0; j < padding; j++) {
/*  85 */         buf.append(' ');
/*     */       }
/*  87 */       BYTEPADDING[i] = buf.toString();
/*     */     } 
/*     */ 
/*     */     
/*  91 */     for (i = 0; i < BYTE2CHAR.length; i++) {
/*  92 */       if (i <= 31 || i >= 127) {
/*  93 */         BYTE2CHAR[i] = '.';
/*     */       } else {
/*  95 */         BYTE2CHAR[i] = (char)i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final InternalLogLevel internalLevel;
/*     */ 
/*     */   
/*     */   private final LogLevel level;
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler() {
/* 110 */     this(DEFAULT_LEVEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(LogLevel level) {
/* 120 */     if (level == null) {
/* 121 */       throw new NullPointerException("level");
/*     */     }
/*     */     
/* 124 */     this.logger = InternalLoggerFactory.getInstance(getClass());
/* 125 */     this.level = level;
/* 126 */     this.internalLevel = level.toInternalLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(Class<?> clazz) {
/* 134 */     this(clazz, DEFAULT_LEVEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(Class<?> clazz, LogLevel level) {
/* 143 */     if (clazz == null) {
/* 144 */       throw new NullPointerException("clazz");
/*     */     }
/* 146 */     if (level == null) {
/* 147 */       throw new NullPointerException("level");
/*     */     }
/* 149 */     this.logger = InternalLoggerFactory.getInstance(clazz);
/* 150 */     this.level = level;
/* 151 */     this.internalLevel = level.toInternalLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(String name) {
/* 158 */     this(name, DEFAULT_LEVEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggingHandler(String name, LogLevel level) {
/* 167 */     if (name == null) {
/* 168 */       throw new NullPointerException("name");
/*     */     }
/* 170 */     if (level == null) {
/* 171 */       throw new NullPointerException("level");
/*     */     }
/* 173 */     this.logger = InternalLoggerFactory.getInstance(name);
/* 174 */     this.level = level;
/* 175 */     this.internalLevel = level.toInternalLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogLevel level() {
/* 182 */     return this.level;
/*     */   }
/*     */   
/*     */   protected String format(ChannelHandlerContext ctx, String message) {
/* 186 */     String chStr = ctx.channel().toString();
/* 187 */     StringBuilder buf = new StringBuilder(chStr.length() + message.length() + 1);
/* 188 */     buf.append(chStr);
/* 189 */     buf.append(' ');
/* 190 */     buf.append(message);
/* 191 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 197 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 198 */       this.logger.log(this.internalLevel, format(ctx, "REGISTERED"));
/*     */     }
/* 200 */     super.channelRegistered(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
/* 206 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 207 */       this.logger.log(this.internalLevel, format(ctx, "UNREGISTERED"));
/*     */     }
/* 209 */     super.channelUnregistered(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 215 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 216 */       this.logger.log(this.internalLevel, format(ctx, "ACTIVE"));
/*     */     }
/* 218 */     super.channelActive(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 224 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 225 */       this.logger.log(this.internalLevel, format(ctx, "INACTIVE"));
/*     */     }
/* 227 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 233 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 234 */       this.logger.log(this.internalLevel, format(ctx, "EXCEPTION: " + cause), cause);
/*     */     }
/* 236 */     super.exceptionCaught(ctx, cause);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/* 242 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 243 */       this.logger.log(this.internalLevel, format(ctx, "USER_EVENT: " + evt));
/*     */     }
/* 245 */     super.userEventTriggered(ctx, evt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 251 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 252 */       this.logger.log(this.internalLevel, format(ctx, "BIND(" + localAddress + ')'));
/*     */     }
/* 254 */     super.bind(ctx, localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 261 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 262 */       this.logger.log(this.internalLevel, format(ctx, "CONNECT(" + remoteAddress + ", " + localAddress + ')'));
/*     */     }
/* 264 */     super.connect(ctx, remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 270 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 271 */       this.logger.log(this.internalLevel, format(ctx, "DISCONNECT()"));
/*     */     }
/* 273 */     super.disconnect(ctx, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 279 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 280 */       this.logger.log(this.internalLevel, format(ctx, "CLOSE()"));
/*     */     }
/* 282 */     super.close(ctx, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 288 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 289 */       this.logger.log(this.internalLevel, format(ctx, "DEREGISTER()"));
/*     */     }
/* 291 */     super.deregister(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 296 */     logMessage(ctx, "RECEIVED", msg);
/* 297 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 302 */     logMessage(ctx, "WRITE", msg);
/* 303 */     ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 308 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 309 */       this.logger.log(this.internalLevel, format(ctx, "FLUSH"));
/*     */     }
/* 311 */     ctx.flush();
/*     */   }
/*     */   
/*     */   private void logMessage(ChannelHandlerContext ctx, String eventName, Object msg) {
/* 315 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 316 */       this.logger.log(this.internalLevel, format(ctx, formatMessage(eventName, msg)));
/*     */     }
/*     */   }
/*     */   
/*     */   protected String formatMessage(String eventName, Object msg) {
/* 321 */     if (msg instanceof ByteBuf)
/* 322 */       return formatByteBuf(eventName, (ByteBuf)msg); 
/* 323 */     if (msg instanceof ByteBufHolder) {
/* 324 */       return formatByteBufHolder(eventName, (ByteBufHolder)msg);
/*     */     }
/* 326 */     return formatNonByteBuf(eventName, msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String formatByteBuf(String eventName, ByteBuf buf) {
/* 334 */     int length = buf.readableBytes();
/* 335 */     int rows = length / 16 + ((length % 15 == 0) ? 0 : 1) + 4;
/* 336 */     StringBuilder dump = new StringBuilder(rows * 80 + eventName.length() + 16);
/*     */     
/* 338 */     dump.append(eventName).append('(').append(length).append('B').append(')');
/* 339 */     dump.append(NEWLINE + "         +-------------------------------------------------+" + NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |" + NEWLINE + "+--------+-------------------------------------------------+----------------+");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 344 */     int startIndex = buf.readerIndex();
/* 345 */     int endIndex = buf.writerIndex();
/*     */     
/*     */     int i;
/* 348 */     for (i = startIndex; i < endIndex; i++) {
/* 349 */       int relIdx = i - startIndex;
/* 350 */       int relIdxMod16 = relIdx & 0xF;
/* 351 */       if (relIdxMod16 == 0) {
/* 352 */         dump.append(NEWLINE);
/* 353 */         dump.append(Long.toHexString(relIdx & 0xFFFFFFFFL | 0x100000000L));
/* 354 */         dump.setCharAt(dump.length() - 9, '|');
/* 355 */         dump.append('|');
/*     */       } 
/* 357 */       dump.append(BYTE2HEX[buf.getUnsignedByte(i)]);
/* 358 */       if (relIdxMod16 == 15) {
/* 359 */         dump.append(" |");
/* 360 */         for (int j = i - 15; j <= i; j++) {
/* 361 */           dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
/*     */         }
/* 363 */         dump.append('|');
/*     */       } 
/*     */     } 
/*     */     
/* 367 */     if ((i - startIndex & 0xF) != 0) {
/* 368 */       int remainder = length & 0xF;
/* 369 */       dump.append(HEXPADDING[remainder]);
/* 370 */       dump.append(" |");
/* 371 */       for (int j = i - remainder; j < i; j++) {
/* 372 */         dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
/*     */       }
/* 374 */       dump.append(BYTEPADDING[remainder]);
/* 375 */       dump.append('|');
/*     */     } 
/*     */     
/* 378 */     dump.append(NEWLINE + "+--------+-------------------------------------------------+----------------+");
/*     */ 
/*     */     
/* 381 */     return dump.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String formatNonByteBuf(String eventName, Object msg) {
/* 388 */     return eventName + ": " + msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String formatByteBufHolder(String eventName, ByteBufHolder msg) {
/* 398 */     return formatByteBuf(eventName, msg.content());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\logging\LoggingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */