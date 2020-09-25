/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import net.minecraft.util.io.netty.util.AbstractReferenceCounted;
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
/*     */ public class DefaultFileRegion
/*     */   extends AbstractReferenceCounted
/*     */   implements FileRegion
/*     */ {
/*  31 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultFileRegion.class);
/*     */ 
/*     */   
/*     */   private final FileChannel file;
/*     */ 
/*     */   
/*     */   private final long position;
/*     */ 
/*     */   
/*     */   private final long count;
/*     */   
/*     */   private long transfered;
/*     */ 
/*     */   
/*     */   public DefaultFileRegion(FileChannel file, long position, long count) {
/*  46 */     if (file == null) {
/*  47 */       throw new NullPointerException("file");
/*     */     }
/*  49 */     if (position < 0L) {
/*  50 */       throw new IllegalArgumentException("position must be >= 0 but was " + position);
/*     */     }
/*  52 */     if (count < 0L) {
/*  53 */       throw new IllegalArgumentException("count must be >= 0 but was " + count);
/*     */     }
/*  55 */     this.file = file;
/*  56 */     this.position = position;
/*  57 */     this.count = count;
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() {
/*  62 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public long count() {
/*  67 */     return this.count;
/*     */   }
/*     */ 
/*     */   
/*     */   public long transfered() {
/*  72 */     return this.transfered;
/*     */   }
/*     */ 
/*     */   
/*     */   public long transferTo(WritableByteChannel target, long position) throws IOException {
/*  77 */     long count = this.count - position;
/*  78 */     if (count < 0L || position < 0L) {
/*  79 */       throw new IllegalArgumentException("position out of range: " + position + " (expected: 0 - " + (this.count - 1L) + ')');
/*     */     }
/*     */ 
/*     */     
/*  83 */     if (count == 0L) {
/*  84 */       return 0L;
/*     */     }
/*     */     
/*  87 */     long written = this.file.transferTo(this.position + position, count, target);
/*  88 */     if (written > 0L) {
/*  89 */       this.transfered += written;
/*     */     }
/*  91 */     return written;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/*     */     try {
/*  97 */       this.file.close();
/*  98 */     } catch (IOException e) {
/*  99 */       if (logger.isWarnEnabled())
/* 100 */         logger.warn("Failed to close a file.", e); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\DefaultFileRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */