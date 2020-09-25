/*     */ package net.minecraft.util.io.netty.util.internal.chmv8;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
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
/*     */ public class LongAdder
/*     */   extends Striped64
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7249069246863182397L;
/*     */   
/*     */   final long fn(long v, long x) {
/*  62 */     return v + x;
/*     */   }
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
/*     */   public void add(long x) {
/*     */     Striped64.Cell[] as;
/*     */     long b;
/*  77 */     if ((as = this.cells) != null || !casBase(b = this.base, b + x)) {
/*  78 */       boolean uncontended = true; Striped64.HashCode hc;
/*  79 */       int h = (hc = threadHashCode.get()).code; int n; Striped64.Cell a; long v;
/*  80 */       if (as == null || (n = as.length) < 1 || (a = as[n - 1 & h]) == null || !(uncontended = a.cas(v = a.value, v + x)))
/*     */       {
/*     */         
/*  83 */         retryUpdate(x, hc, uncontended);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment() {
/*  91 */     add(1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrement() {
/*  98 */     add(-1L);
/*     */   }
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
/*     */   public long sum() {
/* 111 */     long sum = this.base;
/* 112 */     Striped64.Cell[] as = this.cells;
/* 113 */     if (as != null) {
/* 114 */       int n = as.length;
/* 115 */       for (int i = 0; i < n; i++) {
/* 116 */         Striped64.Cell a = as[i];
/* 117 */         if (a != null)
/* 118 */           sum += a.value; 
/*     */       } 
/*     */     } 
/* 121 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 132 */     internalReset(0L);
/*     */   }
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
/*     */   public long sumThenReset() {
/* 146 */     long sum = this.base;
/* 147 */     Striped64.Cell[] as = this.cells;
/* 148 */     this.base = 0L;
/* 149 */     if (as != null) {
/* 150 */       int n = as.length;
/* 151 */       for (int i = 0; i < n; i++) {
/* 152 */         Striped64.Cell a = as[i];
/* 153 */         if (a != null) {
/* 154 */           sum += a.value;
/* 155 */           a.value = 0L;
/*     */         } 
/*     */       } 
/*     */     } 
/* 159 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 167 */     return Long.toString(sum());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 176 */     return sum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 184 */     return (int)sum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatValue() {
/* 192 */     return (float)sum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 200 */     return sum();
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 205 */     s.defaultWriteObject();
/* 206 */     s.writeLong(sum());
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 211 */     s.defaultReadObject();
/* 212 */     this.busy = 0;
/* 213 */     this.cells = null;
/* 214 */     this.base = s.readLong();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\chmv8\LongAdder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */