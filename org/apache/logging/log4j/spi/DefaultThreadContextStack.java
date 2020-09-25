/*     */ package org.apache.logging.log4j.spi;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class DefaultThreadContextStack
/*     */   implements ThreadContextStack
/*     */ {
/*     */   private static final long serialVersionUID = 5050501L;
/*  36 */   private static ThreadLocal<List<String>> stack = new ThreadLocal<List<String>>();
/*     */   
/*     */   private final boolean useStack;
/*     */   
/*     */   public DefaultThreadContextStack(boolean useStack) {
/*  41 */     this.useStack = useStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public String pop() {
/*  46 */     if (!this.useStack) {
/*  47 */       return "";
/*     */     }
/*  49 */     List<String> list = stack.get();
/*  50 */     if (list == null || list.size() == 0) {
/*  51 */       throw new NoSuchElementException("The ThreadContext stack is empty");
/*     */     }
/*  53 */     List<String> copy = new ArrayList<String>(list);
/*  54 */     int last = copy.size() - 1;
/*  55 */     String result = copy.remove(last);
/*  56 */     stack.set(Collections.unmodifiableList(copy));
/*  57 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String peek() {
/*  62 */     List<String> list = stack.get();
/*  63 */     if (list == null || list.size() == 0) {
/*  64 */       return null;
/*     */     }
/*  66 */     int last = list.size() - 1;
/*  67 */     return list.get(last);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(String message) {
/*  72 */     if (!this.useStack) {
/*     */       return;
/*     */     }
/*  75 */     add(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  80 */     List<String> list = stack.get();
/*  81 */     return (list == null) ? 0 : list.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> asList() {
/*  86 */     List<String> list = stack.get();
/*  87 */     if (list == null) {
/*  88 */       return Collections.emptyList();
/*     */     }
/*  90 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim(int depth) {
/*  95 */     if (depth < 0) {
/*  96 */       throw new IllegalArgumentException("Maximum stack depth cannot be negative");
/*     */     }
/*     */     
/*  99 */     List<String> list = stack.get();
/* 100 */     if (list == null) {
/*     */       return;
/*     */     }
/* 103 */     List<String> copy = new ArrayList<String>();
/* 104 */     int count = Math.min(depth, list.size());
/* 105 */     for (int i = 0; i < count; i++) {
/* 106 */       copy.add(list.get(i));
/*     */     }
/* 108 */     stack.set(copy);
/*     */   }
/*     */ 
/*     */   
/*     */   public ThreadContextStack copy() {
/* 113 */     List<String> result = null;
/* 114 */     if (!this.useStack || (result = stack.get()) == null) {
/* 115 */       return new MutableThreadContextStack(new ArrayList<String>());
/*     */     }
/* 117 */     return new MutableThreadContextStack(result);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 122 */     stack.remove();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 127 */     List<String> result = stack.get();
/* 128 */     return (result == null) ? 0 : result.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 133 */     List<String> result = stack.get();
/* 134 */     return (result == null || result.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/* 139 */     List<String> result = stack.get();
/* 140 */     return (result != null && result.contains(o));
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<String> iterator() {
/* 145 */     List<String> immutable = stack.get();
/* 146 */     if (immutable == null) {
/* 147 */       List<String> empty = Collections.emptyList();
/* 148 */       return empty.iterator();
/*     */     } 
/* 150 */     return immutable.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 155 */     List<String> result = stack.get();
/* 156 */     if (result == null) {
/* 157 */       return (Object[])new String[0];
/*     */     }
/* 159 */     return result.toArray(new Object[result.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] ts) {
/* 164 */     List<String> result = stack.get();
/* 165 */     if (result == null) {
/* 166 */       if (ts.length > 0) {
/* 167 */         ts[0] = null;
/*     */       }
/* 169 */       return ts;
/*     */     } 
/* 171 */     return result.toArray(ts);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(String s) {
/* 176 */     if (!this.useStack) {
/* 177 */       return false;
/*     */     }
/* 179 */     List<String> list = stack.get();
/* 180 */     List<String> copy = (list == null) ? new ArrayList<String>() : new ArrayList<String>(list);
/*     */     
/* 182 */     copy.add(s);
/* 183 */     stack.set(Collections.unmodifiableList(copy));
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/* 189 */     if (!this.useStack) {
/* 190 */       return false;
/*     */     }
/* 192 */     List<String> list = stack.get();
/* 193 */     if (list == null || list.size() == 0) {
/* 194 */       return false;
/*     */     }
/* 196 */     List<String> copy = new ArrayList<String>(list);
/* 197 */     boolean result = copy.remove(o);
/* 198 */     stack.set(Collections.unmodifiableList(copy));
/* 199 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> objects) {
/* 204 */     if (objects.isEmpty()) {
/* 205 */       return true;
/*     */     }
/*     */     
/* 208 */     List<String> list = stack.get();
/* 209 */     return (list != null && list.containsAll(objects));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends String> strings) {
/* 214 */     if (!this.useStack || strings.isEmpty()) {
/* 215 */       return false;
/*     */     }
/* 217 */     List<String> list = stack.get();
/* 218 */     List<String> copy = (list == null) ? new ArrayList<String>() : new ArrayList<String>(list);
/*     */     
/* 220 */     copy.addAll(strings);
/* 221 */     stack.set(Collections.unmodifiableList(copy));
/* 222 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> objects) {
/* 227 */     if (!this.useStack || objects.isEmpty()) {
/* 228 */       return false;
/*     */     }
/* 230 */     List<String> list = stack.get();
/* 231 */     if (list == null || list.isEmpty()) {
/* 232 */       return false;
/*     */     }
/* 234 */     List<String> copy = new ArrayList<String>(list);
/* 235 */     boolean result = copy.removeAll(objects);
/* 236 */     stack.set(Collections.unmodifiableList(copy));
/* 237 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> objects) {
/* 242 */     if (!this.useStack || objects.isEmpty()) {
/* 243 */       return false;
/*     */     }
/* 245 */     List<String> list = stack.get();
/* 246 */     if (list == null || list.isEmpty()) {
/* 247 */       return false;
/*     */     }
/* 249 */     List<String> copy = new ArrayList<String>(list);
/* 250 */     boolean result = copy.retainAll(objects);
/* 251 */     stack.set(Collections.unmodifiableList(copy));
/* 252 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 257 */     List<String> list = stack.get();
/* 258 */     return (list == null) ? "[]" : list.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\DefaultThreadContextStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */