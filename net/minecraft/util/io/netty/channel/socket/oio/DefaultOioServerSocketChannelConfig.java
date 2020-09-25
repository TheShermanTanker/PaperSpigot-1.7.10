/*     */ package net.minecraft.util.io.netty.channel.socket.oio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.ServerSocket;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOption;
/*     */ import net.minecraft.util.io.netty.channel.MessageSizeEstimator;
/*     */ import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.socket.DefaultServerSocketChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.socket.ServerSocketChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.ServerSocketChannelConfig;
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
/*     */ public class DefaultOioServerSocketChannelConfig
/*     */   extends DefaultServerSocketChannelConfig
/*     */   implements OioServerSocketChannelConfig
/*     */ {
/*     */   public DefaultOioServerSocketChannelConfig(ServerSocketChannel channel, ServerSocket javaSocket) {
/*  39 */     super(channel, javaSocket);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  44 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_TIMEOUT });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  51 */     if (option == ChannelOption.SO_TIMEOUT) {
/*  52 */       return (T)Integer.valueOf(getSoTimeout());
/*     */     }
/*  54 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  59 */     validate(option, value);
/*     */     
/*  61 */     if (option == ChannelOption.SO_TIMEOUT) {
/*  62 */       setSoTimeout(((Integer)value).intValue());
/*     */     } else {
/*  64 */       return super.setOption(option, value);
/*     */     } 
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setSoTimeout(int timeout) {
/*     */     try {
/*  72 */       this.javaSocket.setSoTimeout(timeout);
/*  73 */     } catch (IOException e) {
/*  74 */       throw new ChannelException(e);
/*     */     } 
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSoTimeout() {
/*     */     try {
/*  82 */       return this.javaSocket.getSoTimeout();
/*  83 */     } catch (IOException e) {
/*  84 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setBacklog(int backlog) {
/*  90 */     super.setBacklog(backlog);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setReuseAddress(boolean reuseAddress) {
/*  96 */     super.setReuseAddress(reuseAddress);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/* 102 */     super.setReceiveBufferSize(receiveBufferSize);
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 108 */     super.setPerformancePreferences(connectionTime, latency, bandwidth);
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 114 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 120 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 126 */     super.setWriteSpinCount(writeSpinCount);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 132 */     super.setAllocator(allocator);
/* 133 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 138 */     super.setRecvByteBufAllocator(allocator);
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setAutoRead(boolean autoRead) {
/* 144 */     super.setAutoRead(autoRead);
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 150 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 156 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 162 */     super.setMessageSizeEstimator(estimator);
/* 163 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\oio\DefaultOioServerSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */