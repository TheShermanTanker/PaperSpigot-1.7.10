/*     */ package net.minecraft.util.io.netty.channel.socket.oio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOption;
/*     */ import net.minecraft.util.io.netty.channel.MessageSizeEstimator;
/*     */ import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.socket.DefaultSocketChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.socket.SocketChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.SocketChannelConfig;
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
/*     */ public class DefaultOioSocketChannelConfig
/*     */   extends DefaultSocketChannelConfig
/*     */   implements OioSocketChannelConfig
/*     */ {
/*     */   public DefaultOioSocketChannelConfig(SocketChannel channel, Socket javaSocket) {
/*  38 */     super(channel, javaSocket);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  43 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_TIMEOUT });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  50 */     if (option == ChannelOption.SO_TIMEOUT) {
/*  51 */       return (T)Integer.valueOf(getSoTimeout());
/*     */     }
/*  53 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  58 */     validate(option, value);
/*     */     
/*  60 */     if (option == ChannelOption.SO_TIMEOUT) {
/*  61 */       setSoTimeout(((Integer)value).intValue());
/*     */     } else {
/*  63 */       return super.setOption(option, value);
/*     */     } 
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setSoTimeout(int timeout) {
/*     */     try {
/*  71 */       this.javaSocket.setSoTimeout(timeout);
/*  72 */     } catch (IOException e) {
/*  73 */       throw new ChannelException(e);
/*     */     } 
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSoTimeout() {
/*     */     try {
/*  81 */       return this.javaSocket.getSoTimeout();
/*  82 */     } catch (IOException e) {
/*  83 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setTcpNoDelay(boolean tcpNoDelay) {
/*  89 */     super.setTcpNoDelay(tcpNoDelay);
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setSoLinger(int soLinger) {
/*  95 */     super.setSoLinger(soLinger);
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setSendBufferSize(int sendBufferSize) {
/* 101 */     super.setSendBufferSize(sendBufferSize);
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/* 107 */     super.setReceiveBufferSize(receiveBufferSize);
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setKeepAlive(boolean keepAlive) {
/* 113 */     super.setKeepAlive(keepAlive);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setTrafficClass(int trafficClass) {
/* 119 */     super.setTrafficClass(trafficClass);
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setReuseAddress(boolean reuseAddress) {
/* 125 */     super.setReuseAddress(reuseAddress);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 131 */     super.setPerformancePreferences(connectionTime, latency, bandwidth);
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setAllowHalfClosure(boolean allowHalfClosure) {
/* 137 */     super.setAllowHalfClosure(allowHalfClosure);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 143 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 149 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 155 */     super.setWriteSpinCount(writeSpinCount);
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 161 */     super.setAllocator(allocator);
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 167 */     super.setRecvByteBufAllocator(allocator);
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setAutoRead(boolean autoRead) {
/* 173 */     super.setAutoRead(autoRead);
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 179 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 185 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 191 */     super.setMessageSizeEstimator(estimator);
/* 192 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\oio\DefaultOioSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */