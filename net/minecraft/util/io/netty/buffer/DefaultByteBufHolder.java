/*    */ package net.minecraft.util.io.netty.buffer;
/*    */ 
/*    */ import net.minecraft.util.io.netty.util.IllegalReferenceCountException;
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
/*    */ public class DefaultByteBufHolder
/*    */   implements ByteBufHolder
/*    */ {
/*    */   private final ByteBuf data;
/*    */   
/*    */   public DefaultByteBufHolder(ByteBuf data) {
/* 29 */     if (data == null) {
/* 30 */       throw new NullPointerException("data");
/*    */     }
/* 32 */     this.data = data;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf content() {
/* 37 */     if (this.data.refCnt() <= 0) {
/* 38 */       throw new IllegalReferenceCountException(this.data.refCnt());
/*    */     }
/* 40 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBufHolder copy() {
/* 45 */     return new DefaultByteBufHolder(this.data.copy());
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBufHolder duplicate() {
/* 50 */     return new DefaultByteBufHolder(this.data.duplicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public int refCnt() {
/* 55 */     return this.data.refCnt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBufHolder retain() {
/* 60 */     this.data.retain();
/* 61 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBufHolder retain(int increment) {
/* 66 */     this.data.retain(increment);
/* 67 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release() {
/* 72 */     return this.data.release();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release(int decrement) {
/* 77 */     return this.data.release(decrement);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 82 */     return getClass().getSimpleName() + '(' + content().toString() + ')';
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\buffer\DefaultByteBufHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */