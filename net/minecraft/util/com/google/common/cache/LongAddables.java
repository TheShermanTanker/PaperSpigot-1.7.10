/*    */ package net.minecraft.util.com.google.common.cache;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*    */ import net.minecraft.util.com.google.common.base.Supplier;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(emulated = true)
/*    */ final class LongAddables
/*    */ {
/*    */   private static final Supplier<LongAddable> SUPPLIER;
/*    */   
/*    */   static {
/*    */     Supplier<LongAddable> supplier;
/*    */     try {
/* 37 */       new LongAdder();
/* 38 */       supplier = new Supplier<LongAddable>()
/*    */         {
/*    */           public LongAddable get() {
/* 41 */             return new LongAdder();
/*    */           }
/*    */         };
/* 44 */     } catch (Throwable t) {
/* 45 */       supplier = new Supplier<LongAddable>()
/*    */         {
/*    */           public LongAddable get() {
/* 48 */             return new LongAddables.PureJavaLongAddable();
/*    */           }
/*    */         };
/*    */     } 
/* 52 */     SUPPLIER = supplier;
/*    */   }
/*    */   
/*    */   public static LongAddable create() {
/* 56 */     return (LongAddable)SUPPLIER.get();
/*    */   }
/*    */   
/*    */   private static final class PureJavaLongAddable
/*    */     extends AtomicLong implements LongAddable {
/*    */     public void increment() {
/* 62 */       getAndIncrement();
/*    */     }
/*    */     private PureJavaLongAddable() {}
/*    */     
/*    */     public void add(long x) {
/* 67 */       getAndAdd(x);
/*    */     }
/*    */ 
/*    */     
/*    */     public long sum() {
/* 72 */       return get();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\cache\LongAddables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */