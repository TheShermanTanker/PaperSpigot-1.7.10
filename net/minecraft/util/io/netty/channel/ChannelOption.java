/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.util.UniqueName;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
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
/*     */ public class ChannelOption<T>
/*     */   extends UniqueName
/*     */ {
/*  36 */   private static final ConcurrentMap<String, Boolean> names = PlatformDependent.newConcurrentHashMap();
/*     */   
/*  38 */   public static final ChannelOption<ByteBufAllocator> ALLOCATOR = new ChannelOption("ALLOCATOR");
/*     */   
/*  40 */   public static final ChannelOption<RecvByteBufAllocator> RCVBUF_ALLOCATOR = new ChannelOption("RCVBUF_ALLOCATOR");
/*     */   
/*  42 */   public static final ChannelOption<MessageSizeEstimator> MESSAGE_SIZE_ESTIMATOR = new ChannelOption("MESSAGE_SIZE_ESTIMATOR");
/*     */ 
/*     */   
/*  45 */   public static final ChannelOption<Integer> CONNECT_TIMEOUT_MILLIS = new ChannelOption("CONNECT_TIMEOUT_MILLIS");
/*     */   
/*  47 */   public static final ChannelOption<Integer> MAX_MESSAGES_PER_READ = new ChannelOption("MAX_MESSAGES_PER_READ");
/*     */   
/*  49 */   public static final ChannelOption<Integer> WRITE_SPIN_COUNT = new ChannelOption("WRITE_SPIN_COUNT");
/*     */   
/*  51 */   public static final ChannelOption<Integer> WRITE_BUFFER_HIGH_WATER_MARK = new ChannelOption("WRITE_BUFFER_HIGH_WATER_MARK");
/*     */   
/*  53 */   public static final ChannelOption<Integer> WRITE_BUFFER_LOW_WATER_MARK = new ChannelOption("WRITE_BUFFER_LOW_WATER_MARK");
/*     */ 
/*     */   
/*  56 */   public static final ChannelOption<Boolean> ALLOW_HALF_CLOSURE = new ChannelOption("ALLOW_HALF_CLOSURE");
/*     */   
/*  58 */   public static final ChannelOption<Boolean> AUTO_READ = new ChannelOption("AUTO_READ");
/*     */ 
/*     */   
/*  61 */   public static final ChannelOption<Boolean> SO_BROADCAST = new ChannelOption("SO_BROADCAST");
/*     */   
/*  63 */   public static final ChannelOption<Boolean> SO_KEEPALIVE = new ChannelOption("SO_KEEPALIVE");
/*     */   
/*  65 */   public static final ChannelOption<Integer> SO_SNDBUF = new ChannelOption("SO_SNDBUF");
/*     */   
/*  67 */   public static final ChannelOption<Integer> SO_RCVBUF = new ChannelOption("SO_RCVBUF");
/*     */   
/*  69 */   public static final ChannelOption<Boolean> SO_REUSEADDR = new ChannelOption("SO_REUSEADDR");
/*     */   
/*  71 */   public static final ChannelOption<Integer> SO_LINGER = new ChannelOption("SO_LINGER");
/*     */   
/*  73 */   public static final ChannelOption<Integer> SO_BACKLOG = new ChannelOption("SO_BACKLOG");
/*     */   
/*  75 */   public static final ChannelOption<Integer> SO_TIMEOUT = new ChannelOption("SO_TIMEOUT");
/*     */ 
/*     */   
/*  78 */   public static final ChannelOption<Integer> IP_TOS = new ChannelOption("IP_TOS");
/*     */   
/*  80 */   public static final ChannelOption<InetAddress> IP_MULTICAST_ADDR = new ChannelOption("IP_MULTICAST_ADDR");
/*     */   
/*  82 */   public static final ChannelOption<NetworkInterface> IP_MULTICAST_IF = new ChannelOption("IP_MULTICAST_IF");
/*     */   
/*  84 */   public static final ChannelOption<Integer> IP_MULTICAST_TTL = new ChannelOption("IP_MULTICAST_TTL");
/*     */   
/*  86 */   public static final ChannelOption<Boolean> IP_MULTICAST_LOOP_DISABLED = new ChannelOption("IP_MULTICAST_LOOP_DISABLED");
/*     */ 
/*     */   
/*  89 */   public static final ChannelOption<Boolean> TCP_NODELAY = new ChannelOption("TCP_NODELAY");
/*     */ 
/*     */   
/*     */   @Deprecated
/*  93 */   public static final ChannelOption<Long> AIO_READ_TIMEOUT = new ChannelOption("AIO_READ_TIMEOUT");
/*     */   
/*     */   @Deprecated
/*  96 */   public static final ChannelOption<Long> AIO_WRITE_TIMEOUT = new ChannelOption("AIO_WRITE_TIMEOUT");
/*     */ 
/*     */   
/*     */   @Deprecated
/* 100 */   public static final ChannelOption<Boolean> DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION = new ChannelOption("DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChannelOption(String name) {
/* 107 */     super(names, name, new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(T value) {
/* 115 */     if (value == null)
/* 116 */       throw new NullPointerException("value"); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\ChannelOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */