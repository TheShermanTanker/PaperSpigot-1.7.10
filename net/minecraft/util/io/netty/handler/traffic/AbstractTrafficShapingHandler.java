/*     */ package net.minecraft.util.io.netty.handler.traffic;
/*     */ 
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*     */ import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.util.Attribute;
/*     */ import net.minecraft.util.io.netty.util.AttributeKey;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractTrafficShapingHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*     */   public static final long DEFAULT_CHECK_INTERVAL = 1000L;
/*     */   private static final long MINIMAL_WAIT = 10L;
/*     */   protected TrafficCounter trafficCounter;
/*     */   private long writeLimit;
/*     */   private long readLimit;
/*  74 */   protected long checkInterval = 1000L;
/*     */   
/*  76 */   private static final AttributeKey<Boolean> READ_SUSPENDED = new AttributeKey("readSuspended");
/*  77 */   private static final AttributeKey<Runnable> REOPEN_TASK = new AttributeKey("reopenTask");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTrafficCounter(TrafficCounter newTrafficCounter) {
/*  84 */     this.trafficCounter = newTrafficCounter;
/*     */   }
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
/*     */   protected AbstractTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval) {
/*  98 */     this.writeLimit = writeLimit;
/*  99 */     this.readLimit = readLimit;
/* 100 */     this.checkInterval = checkInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractTrafficShapingHandler(long writeLimit, long readLimit) {
/* 112 */     this(writeLimit, readLimit, 1000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractTrafficShapingHandler() {
/* 119 */     this(0L, 0L, 1000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractTrafficShapingHandler(long checkInterval) {
/* 130 */     this(0L, 0L, checkInterval);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configure(long newWriteLimit, long newReadLimit, long newCheckInterval) {
/* 142 */     configure(newWriteLimit, newReadLimit);
/* 143 */     configure(newCheckInterval);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configure(long newWriteLimit, long newReadLimit) {
/* 153 */     this.writeLimit = newWriteLimit;
/* 154 */     this.readLimit = newReadLimit;
/* 155 */     if (this.trafficCounter != null) {
/* 156 */       this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configure(long newCheckInterval) {
/* 166 */     this.checkInterval = newCheckInterval;
/* 167 */     if (this.trafficCounter != null) {
/* 168 */       this.trafficCounter.configure(this.checkInterval);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doAccounting(TrafficCounter counter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ReopenReadTimerTask
/*     */     implements Runnable
/*     */   {
/*     */     final ChannelHandlerContext ctx;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ReopenReadTimerTask(ChannelHandlerContext ctx) {
/* 190 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 195 */       this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(Boolean.valueOf(false));
/* 196 */       this.ctx.read();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long getTimeToWait(long limit, long bytes, long lastTime, long curtime) {
/* 204 */     long interval = curtime - lastTime;
/* 205 */     if (interval <= 0L)
/*     */     {
/* 207 */       return 0L;
/*     */     }
/* 209 */     return (bytes * 1000L / limit - interval) / 10L * 10L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
/* 214 */     long size = calculateSize(msg);
/* 215 */     long curtime = System.currentTimeMillis();
/*     */     
/* 217 */     if (this.trafficCounter != null) {
/* 218 */       this.trafficCounter.bytesRecvFlowControl(size);
/* 219 */       if (this.readLimit == 0L) {
/*     */         
/* 221 */         ctx.fireChannelRead(msg);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 227 */       long wait = getTimeToWait(this.readLimit, this.trafficCounter.currentReadBytes(), this.trafficCounter.lastTime(), curtime);
/*     */ 
/*     */       
/* 230 */       if (wait >= 10L)
/*     */       {
/*     */         
/* 233 */         if (!isSuspended(ctx)) {
/* 234 */           ctx.attr(READ_SUSPENDED).set(Boolean.valueOf(true));
/*     */ 
/*     */ 
/*     */           
/* 238 */           Attribute<Runnable> attr = ctx.attr(REOPEN_TASK);
/* 239 */           Runnable reopenTask = (Runnable)attr.get();
/* 240 */           if (reopenTask == null) {
/* 241 */             reopenTask = new ReopenReadTimerTask(ctx);
/* 242 */             attr.set(reopenTask);
/*     */           } 
/* 244 */           ctx.executor().schedule(reopenTask, wait, TimeUnit.MILLISECONDS);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 249 */           Runnable bufferUpdateTask = new Runnable()
/*     */             {
/*     */               public void run() {
/* 252 */                 ctx.fireChannelRead(msg);
/*     */               }
/*     */             };
/* 255 */           ctx.executor().schedule(bufferUpdateTask, wait, TimeUnit.MILLISECONDS);
/*     */           return;
/*     */         } 
/*     */       }
/*     */     } 
/* 260 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) {
/* 265 */     if (!isSuspended(ctx)) {
/* 266 */       ctx.read();
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isSuspended(ChannelHandlerContext ctx) {
/* 271 */     Boolean suspended = (Boolean)ctx.attr(READ_SUSPENDED).get();
/* 272 */     if (suspended == null || Boolean.FALSE.equals(suspended)) {
/* 273 */       return false;
/*     */     }
/* 275 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(final ChannelHandlerContext ctx, final Object msg, final ChannelPromise promise) throws Exception {
/* 281 */     long curtime = System.currentTimeMillis();
/* 282 */     long size = calculateSize(msg);
/*     */     
/* 284 */     if (size > -1L && this.trafficCounter != null) {
/* 285 */       this.trafficCounter.bytesWriteFlowControl(size);
/* 286 */       if (this.writeLimit == 0L) {
/* 287 */         ctx.write(msg, promise);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 292 */       long wait = getTimeToWait(this.writeLimit, this.trafficCounter.currentWrittenBytes(), this.trafficCounter.lastTime(), curtime);
/*     */ 
/*     */       
/* 295 */       if (wait >= 10L) {
/* 296 */         ctx.executor().schedule(new Runnable()
/*     */             {
/*     */               public void run() {
/* 299 */                 ctx.write(msg, promise);
/*     */               }
/*     */             }wait, TimeUnit.MILLISECONDS);
/*     */         return;
/*     */       } 
/*     */     } 
/* 305 */     ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrafficCounter trafficCounter() {
/* 314 */     return this.trafficCounter;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 319 */     return "TrafficShaping with Write Limit: " + this.writeLimit + " Read Limit: " + this.readLimit + " and Counter: " + ((this.trafficCounter != null) ? this.trafficCounter.toString() : "none");
/*     */   }
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
/*     */   protected long calculateSize(Object msg) {
/* 332 */     if (msg instanceof ByteBuf) {
/* 333 */       return ((ByteBuf)msg).readableBytes();
/*     */     }
/* 335 */     if (msg instanceof ByteBufHolder) {
/* 336 */       return ((ByteBufHolder)msg).content().readableBytes();
/*     */     }
/* 338 */     return -1L;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\traffic\AbstractTrafficShapingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */