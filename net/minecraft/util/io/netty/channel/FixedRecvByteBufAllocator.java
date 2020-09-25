/*    */ package net.minecraft.util.io.netty.channel;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FixedRecvByteBufAllocator
/*    */   implements RecvByteBufAllocator
/*    */ {
/*    */   private final RecvByteBufAllocator.Handle handle;
/*    */   
/*    */   private static final class HandleImpl
/*    */     implements RecvByteBufAllocator.Handle
/*    */   {
/*    */     private final int bufferSize;
/*    */     
/*    */     HandleImpl(int bufferSize) {
/* 33 */       this.bufferSize = bufferSize;
/*    */     }
/*    */ 
/*    */     
/*    */     public ByteBuf allocate(ByteBufAllocator alloc) {
/* 38 */       return alloc.ioBuffer(this.bufferSize);
/*    */     }
/*    */ 
/*    */     
/*    */     public int guess() {
/* 43 */       return this.bufferSize;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void record(int actualReadBytes) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FixedRecvByteBufAllocator(int bufferSize) {
/* 57 */     if (bufferSize <= 0) {
/* 58 */       throw new IllegalArgumentException("bufferSize must greater than 0: " + bufferSize);
/*    */     }
/*    */ 
/*    */     
/* 62 */     this.handle = new HandleImpl(bufferSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecvByteBufAllocator.Handle newHandle() {
/* 67 */     return this.handle;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\FixedRecvByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */