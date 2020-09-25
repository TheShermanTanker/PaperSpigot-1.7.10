/*    */ package net.minecraft.util.io.netty.buffer;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import net.minecraft.util.io.netty.util.ReferenceCounted;
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
/*    */ 
/*    */ 
/*    */ public abstract class AbstractDerivedByteBuf
/*    */   extends AbstractByteBuf
/*    */ {
/*    */   protected AbstractDerivedByteBuf(int maxCapacity) {
/* 28 */     super(maxCapacity);
/*    */   }
/*    */ 
/*    */   
/*    */   public final int refCnt() {
/* 33 */     return unwrap().refCnt();
/*    */   }
/*    */ 
/*    */   
/*    */   public final ByteBuf retain() {
/* 38 */     unwrap().retain();
/* 39 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public final ByteBuf retain(int increment) {
/* 44 */     unwrap().retain(increment);
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean release() {
/* 50 */     return unwrap().release();
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean release(int decrement) {
/* 55 */     return unwrap().release(decrement);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 60 */     return nioBuffer(index, length);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer nioBuffer(int index, int length) {
/* 65 */     return unwrap().nioBuffer(index, length);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\AbstractDerivedByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */