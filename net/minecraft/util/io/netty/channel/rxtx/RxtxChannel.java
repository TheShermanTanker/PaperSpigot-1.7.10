/*     */ package net.minecraft.util.io.netty.channel.rxtx;
/*     */ 
/*     */ import gnu.io.CommPort;
/*     */ import gnu.io.CommPortIdentifier;
/*     */ import gnu.io.SerialPort;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.channel.AbstractChannel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.oio.OioByteStreamChannel;
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
/*     */ public class RxtxChannel
/*     */   extends OioByteStreamChannel
/*     */ {
/*  34 */   private static final RxtxDeviceAddress LOCAL_ADDRESS = new RxtxDeviceAddress("localhost");
/*     */   
/*     */   private final RxtxChannelConfig config;
/*     */   
/*     */   private boolean open = true;
/*     */   private RxtxDeviceAddress deviceAddress;
/*     */   private SerialPort serialPort;
/*     */   
/*     */   public RxtxChannel() {
/*  43 */     super(null);
/*     */     
/*  45 */     this.config = new DefaultRxtxChannelConfig(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public RxtxChannelConfig config() {
/*  50 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  55 */     return this.open;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/*  60 */     return new RxtxUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/*  65 */     RxtxDeviceAddress remote = (RxtxDeviceAddress)remoteAddress;
/*  66 */     CommPortIdentifier cpi = CommPortIdentifier.getPortIdentifier(remote.value());
/*  67 */     CommPort commPort = cpi.open(getClass().getName(), 1000);
/*     */     
/*  69 */     this.deviceAddress = remote;
/*     */     
/*  71 */     this.serialPort = (SerialPort)commPort;
/*     */   }
/*     */   
/*     */   protected void doInit() throws Exception {
/*  75 */     this.serialPort.setSerialPortParams(((Integer)config().getOption(RxtxChannelOption.BAUD_RATE)).intValue(), ((RxtxChannelConfig.Databits)config().getOption(RxtxChannelOption.DATA_BITS)).value(), ((RxtxChannelConfig.Stopbits)config().getOption(RxtxChannelOption.STOP_BITS)).value(), ((RxtxChannelConfig.Paritybit)config().getOption(RxtxChannelOption.PARITY_BIT)).value());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     this.serialPort.setDTR(((Boolean)config().getOption(RxtxChannelOption.DTR)).booleanValue());
/*  82 */     this.serialPort.setRTS(((Boolean)config().getOption(RxtxChannelOption.RTS)).booleanValue());
/*     */     
/*  84 */     activate(this.serialPort.getInputStream(), this.serialPort.getOutputStream());
/*     */   }
/*     */ 
/*     */   
/*     */   public RxtxDeviceAddress localAddress() {
/*  89 */     return (RxtxDeviceAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public RxtxDeviceAddress remoteAddress() {
/*  94 */     return (RxtxDeviceAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected RxtxDeviceAddress localAddress0() {
/*  99 */     return LOCAL_ADDRESS;
/*     */   }
/*     */ 
/*     */   
/*     */   protected RxtxDeviceAddress remoteAddress0() {
/* 104 */     return this.deviceAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 109 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 114 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 119 */     this.open = false;
/*     */     try {
/* 121 */       super.doClose();
/*     */     } finally {
/* 123 */       if (this.serialPort != null) {
/* 124 */         this.serialPort.removeEventListener();
/* 125 */         this.serialPort.close();
/* 126 */         this.serialPort = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private final class RxtxUnsafe extends AbstractChannel.AbstractUnsafe { private RxtxUnsafe() {
/* 131 */       super((AbstractChannel)RxtxChannel.this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, final ChannelPromise promise) {
/* 136 */       if (!ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 141 */         final boolean wasActive = RxtxChannel.this.isActive();
/* 142 */         RxtxChannel.this.doConnect(remoteAddress, localAddress);
/*     */         
/* 144 */         int waitTime = ((Integer)RxtxChannel.this.config().getOption(RxtxChannelOption.WAIT_TIME)).intValue();
/* 145 */         if (waitTime > 0) {
/* 146 */           RxtxChannel.this.eventLoop().schedule(new Runnable()
/*     */               {
/*     */                 public void run() {
/*     */                   try {
/* 150 */                     RxtxChannel.this.doInit();
/* 151 */                     promise.setSuccess();
/* 152 */                     if (!wasActive && RxtxChannel.this.isActive()) {
/* 153 */                       RxtxChannel.this.pipeline().fireChannelActive();
/*     */                     }
/* 155 */                   } catch (Throwable t) {
/* 156 */                     promise.setFailure(t);
/* 157 */                     RxtxChannel.RxtxUnsafe.this.closeIfClosed();
/*     */                   } 
/*     */                 }
/*     */               },  waitTime, TimeUnit.MILLISECONDS);
/*     */         } else {
/* 162 */           RxtxChannel.this.doInit();
/* 163 */           promise.setSuccess();
/* 164 */           if (!wasActive && RxtxChannel.this.isActive()) {
/* 165 */             RxtxChannel.this.pipeline().fireChannelActive();
/*     */           }
/*     */         } 
/* 168 */       } catch (Throwable t) {
/* 169 */         promise.setFailure(t);
/* 170 */         closeIfClosed();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\rxtx\RxtxChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */