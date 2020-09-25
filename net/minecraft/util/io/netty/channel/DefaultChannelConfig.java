/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.buffer.UnpooledByteBufAllocator;
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
/*     */ public class DefaultChannelConfig
/*     */   implements ChannelConfig
/*     */ {
/*  33 */   private static final ByteBufAllocator DEFAULT_ALLOCATOR = (ByteBufAllocator)UnpooledByteBufAllocator.DEFAULT;
/*  34 */   private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = AdaptiveRecvByteBufAllocator.DEFAULT;
/*  35 */   private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
/*     */   
/*     */   private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
/*     */   
/*     */   protected final Channel channel;
/*     */   
/*  41 */   private volatile ByteBufAllocator allocator = DEFAULT_ALLOCATOR;
/*  42 */   private volatile RecvByteBufAllocator rcvBufAllocator = DEFAULT_RCVBUF_ALLOCATOR;
/*  43 */   private volatile MessageSizeEstimator msgSizeEstimator = DEFAULT_MSG_SIZE_ESTIMATOR;
/*     */   
/*  45 */   private volatile int connectTimeoutMillis = 30000;
/*     */   private volatile int maxMessagesPerRead;
/*  47 */   private volatile int writeSpinCount = 16;
/*     */   private volatile boolean autoRead = true;
/*  49 */   private volatile int writeBufferHighWaterMark = 65536;
/*  50 */   private volatile int writeBufferLowWaterMark = 32768;
/*     */   
/*     */   public DefaultChannelConfig(Channel channel) {
/*  53 */     if (channel == null) {
/*  54 */       throw new NullPointerException("channel");
/*     */     }
/*  56 */     this.channel = channel;
/*     */     
/*  58 */     if (channel instanceof ServerChannel) {
/*     */       
/*  60 */       this.maxMessagesPerRead = 16;
/*     */     } else {
/*  62 */       this.maxMessagesPerRead = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  68 */     return getOptions(null, (ChannelOption<?>[])new ChannelOption[] { ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.MAX_MESSAGES_PER_READ, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.RCVBUF_ALLOCATOR, ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, ChannelOption.MESSAGE_SIZE_ESTIMATOR });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> result, ChannelOption<?>... options) {
/*  77 */     if (result == null) {
/*  78 */       result = new IdentityHashMap<ChannelOption<?>, Object>();
/*     */     }
/*  80 */     for (ChannelOption<?> o : options) {
/*  81 */       result.put(o, getOption(o));
/*     */     }
/*  83 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setOptions(Map<ChannelOption<?>, ?> options) {
/*  89 */     if (options == null) {
/*  90 */       throw new NullPointerException("options");
/*     */     }
/*     */     
/*  93 */     boolean setAllOptions = true;
/*  94 */     for (Map.Entry<ChannelOption<?>, ?> e : options.entrySet()) {
/*  95 */       if (!setOption(e.getKey(), e.getValue())) {
/*  96 */         setAllOptions = false;
/*     */       }
/*     */     } 
/*     */     
/* 100 */     return setAllOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/* 106 */     if (option == null) {
/* 107 */       throw new NullPointerException("option");
/*     */     }
/*     */     
/* 110 */     if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
/* 111 */       return (T)Integer.valueOf(getConnectTimeoutMillis());
/*     */     }
/* 113 */     if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
/* 114 */       return (T)Integer.valueOf(getMaxMessagesPerRead());
/*     */     }
/* 116 */     if (option == ChannelOption.WRITE_SPIN_COUNT) {
/* 117 */       return (T)Integer.valueOf(getWriteSpinCount());
/*     */     }
/* 119 */     if (option == ChannelOption.ALLOCATOR) {
/* 120 */       return (T)getAllocator();
/*     */     }
/* 122 */     if (option == ChannelOption.RCVBUF_ALLOCATOR) {
/* 123 */       return (T)getRecvByteBufAllocator();
/*     */     }
/* 125 */     if (option == ChannelOption.AUTO_READ) {
/* 126 */       return (T)Boolean.valueOf(isAutoRead());
/*     */     }
/* 128 */     if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
/* 129 */       return (T)Integer.valueOf(getWriteBufferHighWaterMark());
/*     */     }
/* 131 */     if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
/* 132 */       return (T)Integer.valueOf(getWriteBufferLowWaterMark());
/*     */     }
/* 134 */     if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
/* 135 */       return (T)getMessageSizeEstimator();
/*     */     }
/* 137 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 142 */     validate(option, value);
/*     */     
/* 144 */     if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
/* 145 */       setConnectTimeoutMillis(((Integer)value).intValue());
/* 146 */     } else if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
/* 147 */       setMaxMessagesPerRead(((Integer)value).intValue());
/* 148 */     } else if (option == ChannelOption.WRITE_SPIN_COUNT) {
/* 149 */       setWriteSpinCount(((Integer)value).intValue());
/* 150 */     } else if (option == ChannelOption.ALLOCATOR) {
/* 151 */       setAllocator((ByteBufAllocator)value);
/* 152 */     } else if (option == ChannelOption.RCVBUF_ALLOCATOR) {
/* 153 */       setRecvByteBufAllocator((RecvByteBufAllocator)value);
/* 154 */     } else if (option == ChannelOption.AUTO_READ) {
/* 155 */       setAutoRead(((Boolean)value).booleanValue());
/* 156 */     } else if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
/* 157 */       setWriteBufferHighWaterMark(((Integer)value).intValue());
/* 158 */     } else if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
/* 159 */       setWriteBufferLowWaterMark(((Integer)value).intValue());
/* 160 */     } else if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
/* 161 */       setMessageSizeEstimator((MessageSizeEstimator)value);
/*     */     } else {
/* 163 */       return false;
/*     */     } 
/*     */     
/* 166 */     return true;
/*     */   }
/*     */   
/*     */   protected <T> void validate(ChannelOption<T> option, T value) {
/* 170 */     if (option == null) {
/* 171 */       throw new NullPointerException("option");
/*     */     }
/* 173 */     option.validate(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getConnectTimeoutMillis() {
/* 178 */     return this.connectTimeoutMillis;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 183 */     if (connectTimeoutMillis < 0) {
/* 184 */       throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", new Object[] { Integer.valueOf(connectTimeoutMillis) }));
/*     */     }
/*     */     
/* 187 */     this.connectTimeoutMillis = connectTimeoutMillis;
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxMessagesPerRead() {
/* 193 */     return this.maxMessagesPerRead;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 198 */     if (maxMessagesPerRead <= 0) {
/* 199 */       throw new IllegalArgumentException("maxMessagesPerRead: " + maxMessagesPerRead + " (expected: > 0)");
/*     */     }
/* 201 */     this.maxMessagesPerRead = maxMessagesPerRead;
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWriteSpinCount() {
/* 207 */     return this.writeSpinCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 212 */     if (writeSpinCount <= 0) {
/* 213 */       throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
/*     */     }
/*     */     
/* 216 */     this.writeSpinCount = writeSpinCount;
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator getAllocator() {
/* 222 */     return this.allocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 227 */     if (allocator == null) {
/* 228 */       throw new NullPointerException("allocator");
/*     */     }
/* 230 */     this.allocator = allocator;
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecvByteBufAllocator getRecvByteBufAllocator() {
/* 236 */     return this.rcvBufAllocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 241 */     if (allocator == null) {
/* 242 */       throw new NullPointerException("allocator");
/*     */     }
/* 244 */     this.rcvBufAllocator = allocator;
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAutoRead() {
/* 250 */     return this.autoRead;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setAutoRead(boolean autoRead) {
/* 255 */     boolean oldAutoRead = this.autoRead;
/* 256 */     this.autoRead = autoRead;
/* 257 */     if (autoRead && !oldAutoRead) {
/* 258 */       this.channel.read();
/*     */     }
/* 260 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWriteBufferHighWaterMark() {
/* 265 */     return this.writeBufferHighWaterMark;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 270 */     if (writeBufferHighWaterMark < getWriteBufferLowWaterMark()) {
/* 271 */       throw new IllegalArgumentException("writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + getWriteBufferLowWaterMark() + "): " + writeBufferHighWaterMark);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 276 */     if (writeBufferHighWaterMark < 0) {
/* 277 */       throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
/*     */     }
/*     */     
/* 280 */     this.writeBufferHighWaterMark = writeBufferHighWaterMark;
/* 281 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWriteBufferLowWaterMark() {
/* 286 */     return this.writeBufferLowWaterMark;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 291 */     if (writeBufferLowWaterMark > getWriteBufferHighWaterMark()) {
/* 292 */       throw new IllegalArgumentException("writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + getWriteBufferHighWaterMark() + "): " + writeBufferLowWaterMark);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 297 */     if (writeBufferLowWaterMark < 0) {
/* 298 */       throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
/*     */     }
/*     */     
/* 301 */     this.writeBufferLowWaterMark = writeBufferLowWaterMark;
/* 302 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageSizeEstimator getMessageSizeEstimator() {
/* 307 */     return this.msgSizeEstimator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 312 */     if (estimator == null) {
/* 313 */       throw new NullPointerException("estimator");
/*     */     }
/* 315 */     this.msgSizeEstimator = estimator;
/* 316 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\DefaultChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */