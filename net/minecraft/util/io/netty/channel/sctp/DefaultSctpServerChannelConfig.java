/*     */ package net.minecraft.util.io.netty.channel.sctp;
/*     */ 
/*     */ import com.sun.nio.sctp.SctpServerChannel;
/*     */ import com.sun.nio.sctp.SctpStandardSocketOptions;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOption;
/*     */ import net.minecraft.util.io.netty.channel.DefaultChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.MessageSizeEstimator;
/*     */ import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.util.NetUtil;
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
/*     */ public class DefaultSctpServerChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements SctpServerChannelConfig
/*     */ {
/*     */   private final SctpServerChannel javaChannel;
/*  38 */   private volatile int backlog = NetUtil.SOMAXCONN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSctpServerChannelConfig(SctpServerChannel channel, SctpServerChannel javaChannel) {
/*  45 */     super((Channel)channel);
/*  46 */     if (javaChannel == null) {
/*  47 */       throw new NullPointerException("javaChannel");
/*     */     }
/*  49 */     this.javaChannel = javaChannel;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  54 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_INIT_MAXSTREAMS });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  62 */     if (option == ChannelOption.SO_RCVBUF) {
/*  63 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  65 */     if (option == ChannelOption.SO_SNDBUF) {
/*  66 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  68 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  73 */     validate(option, value);
/*     */     
/*  75 */     if (option == ChannelOption.SO_RCVBUF) {
/*  76 */       setReceiveBufferSize(((Integer)value).intValue());
/*  77 */     } else if (option == ChannelOption.SO_SNDBUF) {
/*  78 */       setSendBufferSize(((Integer)value).intValue());
/*  79 */     } else if (option == SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS) {
/*  80 */       setInitMaxStreams((SctpStandardSocketOptions.InitMaxStreams)value);
/*     */     } else {
/*  82 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/*  91 */       return ((Integer)this.javaChannel.<Integer>getOption(SctpStandardSocketOptions.SO_SNDBUF)).intValue();
/*  92 */     } catch (IOException e) {
/*  93 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 100 */       this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, Integer.valueOf(sendBufferSize));
/* 101 */     } catch (IOException e) {
/* 102 */       throw new ChannelException(e);
/*     */     } 
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 110 */       return ((Integer)this.javaChannel.<Integer>getOption(SctpStandardSocketOptions.SO_RCVBUF)).intValue();
/* 111 */     } catch (IOException e) {
/* 112 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 119 */       this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, Integer.valueOf(receiveBufferSize));
/* 120 */     } catch (IOException e) {
/* 121 */       throw new ChannelException(e);
/*     */     } 
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams() {
/*     */     try {
/* 129 */       return this.javaChannel.<SctpStandardSocketOptions.InitMaxStreams>getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
/* 130 */     } catch (IOException e) {
/* 131 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setInitMaxStreams(SctpStandardSocketOptions.InitMaxStreams initMaxStreams) {
/*     */     try {
/* 138 */       this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, initMaxStreams);
/* 139 */     } catch (IOException e) {
/* 140 */       throw new ChannelException(e);
/*     */     } 
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBacklog() {
/* 147 */     return this.backlog;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setBacklog(int backlog) {
/* 152 */     if (backlog < 0) {
/* 153 */       throw new IllegalArgumentException("backlog: " + backlog);
/*     */     }
/* 155 */     this.backlog = backlog;
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 161 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 167 */     super.setWriteSpinCount(writeSpinCount);
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 173 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 179 */     super.setAllocator(allocator);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 185 */     super.setRecvByteBufAllocator(allocator);
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setAutoRead(boolean autoRead) {
/* 191 */     super.setAutoRead(autoRead);
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 197 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 203 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 209 */     super.setMessageSizeEstimator(estimator);
/* 210 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\sctp\DefaultSctpServerChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */