/*     */ package org.apache.logging.log4j.spi;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.ThreadContext;
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
/*     */ public class MutableThreadContextStack
/*     */   implements ThreadContextStack
/*     */ {
/*     */   private static final long serialVersionUID = 50505011L;
/*     */   private final List<String> list;
/*     */   
/*     */   public MutableThreadContextStack(List<String> list) {
/*  37 */     this.list = new ArrayList<String>(list);
/*     */   }
/*     */   
/*     */   private MutableThreadContextStack(MutableThreadContextStack stack) {
/*  41 */     this.list = new ArrayList<String>(stack.list);
/*     */   }
/*     */ 
/*     */   
/*     */   public String pop() {
/*  46 */     if (this.list.isEmpty()) {
/*  47 */       return null;
/*     */     }
/*  49 */     int last = this.list.size() - 1;
/*  50 */     String result = this.list.remove(last);
/*  51 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String peek() {
/*  56 */     if (this.list.isEmpty()) {
/*  57 */       return null;
/*     */     }
/*  59 */     int last = this.list.size() - 1;
/*  60 */     return this.list.get(last);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(String message) {
/*  65 */     this.list.add(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  70 */     return this.list.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> asList() {
/*  75 */     return this.list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim(int depth) {
/*  80 */     if (depth < 0) {
/*  81 */       throw new IllegalArgumentException("Maximum stack depth cannot be negative");
/*     */     }
/*  83 */     if (this.list == null) {
/*     */       return;
/*     */     }
/*  86 */     List<String> copy = new ArrayList<String>(this.list.size());
/*  87 */     int count = Math.min(depth, this.list.size());
/*  88 */     for (int i = 0; i < count; i++) {
/*  89 */       copy.add(this.list.get(i));
/*     */     }
/*  91 */     this.list.clear();
/*  92 */     this.list.addAll(copy);
/*     */   }
/*     */ 
/*     */   
/*     */   public ThreadContextStack copy() {
/*  97 */     return new MutableThreadContextStack(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 102 */     this.list.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 107 */     return this.list.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 112 */     return this.list.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/* 117 */     return this.list.contains(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<String> iterator() {
/* 122 */     return this.list.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 127 */     return this.list.toArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] ts) {
/* 132 */     return this.list.toArray(ts);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(String s) {
/* 137 */     return this.list.add(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/* 142 */     return this.list.remove(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> objects) {
/* 147 */     return this.list.containsAll(objects);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends String> strings) {
/* 152 */     return this.list.addAll(strings);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> objects) {
/* 157 */     return this.list.removeAll(objects);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> objects) {
/* 162 */     return this.list.retainAll(objects);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 167 */     return String.valueOf(this.list);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\MutableThreadContextStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */