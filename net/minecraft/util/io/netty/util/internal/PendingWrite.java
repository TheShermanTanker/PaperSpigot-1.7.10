/*    */ package net.minecraft.util.io.netty.util.internal;
/*    */ 
/*    */ import net.minecraft.util.io.netty.util.Recycler;
/*    */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*    */ import net.minecraft.util.io.netty.util.concurrent.Promise;
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
/*    */ public final class PendingWrite
/*    */ {
/* 26 */   private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>()
/*    */     {
/*    */       protected PendingWrite newObject(Recycler.Handle handle) {
/* 29 */         return new PendingWrite(handle);
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private final Recycler.Handle handle;
/*    */   
/*    */   public static PendingWrite newInstance(Object msg, Promise<Void> promise) {
/* 37 */     PendingWrite pending = (PendingWrite)RECYCLER.get();
/* 38 */     pending.msg = msg;
/* 39 */     pending.promise = promise;
/* 40 */     return pending;
/*    */   }
/*    */ 
/*    */   
/*    */   private Object msg;
/*    */   private Promise<Void> promise;
/*    */   
/*    */   private PendingWrite(Recycler.Handle handle) {
/* 48 */     this.handle = handle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean recycle() {
/* 55 */     this.msg = null;
/* 56 */     this.promise = null;
/* 57 */     return RECYCLER.recycle(this, this.handle);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean failAndRecycle(Throwable cause) {
/* 64 */     ReferenceCountUtil.release(this.msg);
/* 65 */     if (this.promise != null) {
/* 66 */       this.promise.setFailure(cause);
/*    */     }
/* 68 */     return recycle();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean successAndRecycle() {
/* 75 */     if (this.promise != null) {
/* 76 */       this.promise.setSuccess(null);
/*    */     }
/* 78 */     return recycle();
/*    */   }
/*    */   
/*    */   public Object msg() {
/* 82 */     return this.msg;
/*    */   }
/*    */   
/*    */   public Promise<Void> promise() {
/* 86 */     return this.promise;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Promise<Void> recycleAndGet() {
/* 93 */     Promise<Void> promise = this.promise;
/* 94 */     recycle();
/* 95 */     return promise;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\PendingWrite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */