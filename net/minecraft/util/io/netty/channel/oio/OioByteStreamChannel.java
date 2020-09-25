/*     */ package net.minecraft.util.io.netty.channel.oio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.FileRegion;
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
/*     */ public abstract class OioByteStreamChannel
/*     */   extends AbstractOioByteChannel
/*     */ {
/*  35 */   private static final InputStream CLOSED_IN = new InputStream()
/*     */     {
/*     */       public int read() {
/*  38 */         return -1;
/*     */       }
/*     */     };
/*     */   
/*  42 */   private static final OutputStream CLOSED_OUT = new OutputStream()
/*     */     {
/*     */       public void write(int b) throws IOException {
/*  45 */         throw new ClosedChannelException();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream is;
/*     */ 
/*     */   
/*     */   private OutputStream os;
/*     */   
/*     */   private WritableByteChannel outChannel;
/*     */ 
/*     */   
/*     */   protected OioByteStreamChannel(Channel parent) {
/*  60 */     super(parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void activate(InputStream is, OutputStream os) {
/*  67 */     if (this.is != null) {
/*  68 */       throw new IllegalStateException("input was set already");
/*     */     }
/*  70 */     if (this.os != null) {
/*  71 */       throw new IllegalStateException("output was set already");
/*     */     }
/*  73 */     if (is == null) {
/*  74 */       throw new NullPointerException("is");
/*     */     }
/*  76 */     if (os == null) {
/*  77 */       throw new NullPointerException("os");
/*     */     }
/*  79 */     this.is = is;
/*  80 */     this.os = os;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  85 */     InputStream is = this.is;
/*  86 */     if (is == null || is == CLOSED_IN) {
/*  87 */       return false;
/*     */     }
/*     */     
/*  90 */     OutputStream os = this.os;
/*  91 */     if (os == null || os == CLOSED_OUT) {
/*  92 */       return false;
/*     */     }
/*     */     
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int available() {
/*     */     try {
/* 101 */       return this.is.available();
/* 102 */     } catch (IOException e) {
/* 103 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadBytes(ByteBuf buf) throws Exception {
/* 109 */     int length = Math.max(1, Math.min(available(), buf.maxWritableBytes()));
/* 110 */     return buf.writeBytes(this.is, length);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteBytes(ByteBuf buf) throws Exception {
/* 115 */     OutputStream os = this.os;
/* 116 */     if (os == null) {
/* 117 */       throw new NotYetConnectedException();
/*     */     }
/* 119 */     buf.readBytes(os, buf.readableBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWriteFileRegion(FileRegion region) throws Exception {
/* 124 */     OutputStream os = this.os;
/* 125 */     if (os == null) {
/* 126 */       throw new NotYetConnectedException();
/*     */     }
/* 128 */     if (this.outChannel == null) {
/* 129 */       this.outChannel = Channels.newChannel(os);
/*     */     }
/*     */     
/* 132 */     long written = 0L;
/*     */     do {
/* 134 */       long localWritten = region.transferTo(this.outChannel, written);
/* 135 */       if (localWritten == -1L) {
/* 136 */         checkEOF(region);
/*     */         return;
/*     */       } 
/* 139 */       written += localWritten;
/*     */     }
/* 141 */     while (written < region.count());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 149 */     InputStream is = this.is;
/* 150 */     OutputStream os = this.os;
/* 151 */     this.is = CLOSED_IN;
/* 152 */     this.os = CLOSED_OUT;
/*     */     
/*     */     try {
/* 155 */       if (is != null) {
/* 156 */         is.close();
/*     */       }
/*     */     } finally {
/* 159 */       if (os != null)
/* 160 */         os.close(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\oio\OioByteStreamChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */