/*     */ package net.minecraft.util.io.netty.util;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
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
/*     */ public abstract class Recycler<T>
/*     */ {
/*  29 */   private final ThreadLocal<Stack<T>> threadLocal = (ThreadLocal)new ThreadLocal<Stack<Stack<T>>>()
/*     */     {
/*     */       protected Recycler.Stack<T> initialValue() {
/*  32 */         return new Recycler.Stack<T>(Recycler.this, Thread.currentThread());
/*     */       }
/*     */     };
/*     */   
/*     */   public final T get() {
/*  37 */     Stack<T> stack = this.threadLocal.get();
/*  38 */     T o = stack.pop();
/*  39 */     if (o == null) {
/*  40 */       o = newObject(stack);
/*     */     }
/*  42 */     return o;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean recycle(T o, Handle handle) {
/*  47 */     Stack<T> stack = (Stack<T>)handle;
/*  48 */     if (stack.parent != this) {
/*  49 */       return false;
/*     */     }
/*     */     
/*  52 */     if (Thread.currentThread() != stack.thread) {
/*  53 */       return false;
/*     */     }
/*     */     
/*  56 */     stack.push(o);
/*  57 */     return true;
/*     */   }
/*     */   
/*     */   protected abstract T newObject(Handle paramHandle);
/*     */   
/*     */   public static interface Handle {}
/*     */   
/*     */   static final class Stack<T>
/*     */     implements Handle
/*     */   {
/*     */     private static final int INITIAL_CAPACITY = 256;
/*     */     final Recycler<T> parent;
/*     */     final Thread thread;
/*     */     private T[] elements;
/*     */     private int size;
/*  72 */     private final Map<T, Boolean> map = new IdentityHashMap<T, Boolean>(256);
/*     */ 
/*     */     
/*     */     Stack(Recycler<T> parent, Thread thread) {
/*  76 */       this.parent = parent;
/*  77 */       this.thread = thread;
/*  78 */       this.elements = newArray(256);
/*     */     }
/*     */     
/*     */     T pop() {
/*  82 */       int size = this.size;
/*  83 */       if (size == 0) {
/*  84 */         return null;
/*     */       }
/*  86 */       size--;
/*  87 */       T ret = this.elements[size];
/*  88 */       this.elements[size] = null;
/*  89 */       this.map.remove(ret);
/*  90 */       this.size = size;
/*  91 */       return ret;
/*     */     }
/*     */     
/*     */     void push(T o) {
/*  95 */       if (this.map.put(o, Boolean.TRUE) != null) {
/*  96 */         throw new IllegalStateException("recycled already");
/*     */       }
/*     */       
/*  99 */       int size = this.size;
/* 100 */       if (size == this.elements.length) {
/* 101 */         T[] newElements = newArray(size << 1);
/* 102 */         System.arraycopy(this.elements, 0, newElements, 0, size);
/* 103 */         this.elements = newElements;
/*     */       } 
/*     */       
/* 106 */       this.elements[size] = o;
/* 107 */       this.size = size + 1;
/*     */     }
/*     */ 
/*     */     
/*     */     private static <T> T[] newArray(int length) {
/* 112 */       return (T[])new Object[length];
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\Recycler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */