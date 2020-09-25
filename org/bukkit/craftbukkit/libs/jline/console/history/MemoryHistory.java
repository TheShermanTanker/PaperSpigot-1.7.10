/*     */ package org.bukkit.craftbukkit.libs.jline.console.history;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class MemoryHistory
/*     */   implements History
/*     */ {
/*     */   public static final int DEFAULT_MAX_SIZE = 500;
/*  27 */   private final LinkedList<CharSequence> items = new LinkedList<CharSequence>();
/*     */   
/*  29 */   private int maxSize = 500;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean ignoreDuplicates = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean autoTrim = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private int offset = 0;
/*     */   
/*  47 */   private int index = 0;
/*     */   
/*     */   public void setMaxSize(int maxSize) {
/*  50 */     this.maxSize = maxSize;
/*  51 */     maybeResize();
/*     */   }
/*     */   
/*     */   public int getMaxSize() {
/*  55 */     return this.maxSize;
/*     */   }
/*     */   
/*     */   public boolean isIgnoreDuplicates() {
/*  59 */     return this.ignoreDuplicates;
/*     */   }
/*     */   
/*     */   public void setIgnoreDuplicates(boolean flag) {
/*  63 */     this.ignoreDuplicates = flag;
/*     */   }
/*     */   
/*     */   public boolean isAutoTrim() {
/*  67 */     return this.autoTrim;
/*     */   }
/*     */   
/*     */   public void setAutoTrim(boolean flag) {
/*  71 */     this.autoTrim = flag;
/*     */   }
/*     */   
/*     */   public int size() {
/*  75 */     return this.items.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  79 */     return this.items.isEmpty();
/*     */   }
/*     */   
/*     */   public int index() {
/*  83 */     return this.offset + this.index;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  87 */     this.items.clear();
/*  88 */     this.offset = 0;
/*  89 */     this.index = 0;
/*     */   }
/*     */   
/*     */   public CharSequence get(int index) {
/*  93 */     return this.items.get(index - this.offset);
/*     */   }
/*     */   
/*     */   public void add(CharSequence item) {
/*  97 */     assert item != null;
/*     */     
/*  99 */     if (isAutoTrim()) {
/* 100 */       item = String.valueOf(item).trim();
/*     */     }
/*     */     
/* 103 */     if (isIgnoreDuplicates() && 
/* 104 */       !this.items.isEmpty() && item.equals(this.items.getLast())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 109 */     internalAdd(item);
/*     */   }
/*     */   
/*     */   protected void internalAdd(CharSequence item) {
/* 113 */     this.items.add(item);
/*     */     
/* 115 */     maybeResize();
/*     */   }
/*     */   
/*     */   public void replace(CharSequence item) {
/* 119 */     this.items.removeLast();
/* 120 */     add(item);
/*     */   }
/*     */   
/*     */   private void maybeResize() {
/* 124 */     while (size() > getMaxSize()) {
/* 125 */       this.items.removeFirst();
/* 126 */       this.offset++;
/*     */     } 
/*     */     
/* 129 */     this.index = size();
/*     */   }
/*     */   
/*     */   public ListIterator<History.Entry> entries(int index) {
/* 133 */     return new EntriesIterator(index - this.offset);
/*     */   }
/*     */   
/*     */   public ListIterator<History.Entry> entries() {
/* 137 */     return entries(this.offset);
/*     */   }
/*     */   
/*     */   public Iterator<History.Entry> iterator() {
/* 141 */     return entries();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EntryImpl
/*     */     implements History.Entry
/*     */   {
/*     */     private final int index;
/*     */     private final CharSequence value;
/*     */     
/*     */     public EntryImpl(int index, CharSequence value) {
/* 152 */       this.index = index;
/* 153 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int index() {
/* 157 */       return this.index;
/*     */     }
/*     */     
/*     */     public CharSequence value() {
/* 161 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 166 */       return String.format("%d: %s", new Object[] { Integer.valueOf(this.index), this.value });
/*     */     }
/*     */   }
/*     */   
/*     */   private class EntriesIterator
/*     */     implements ListIterator<History.Entry>
/*     */   {
/*     */     private final ListIterator<CharSequence> source;
/*     */     
/*     */     private EntriesIterator(int index) {
/* 176 */       this.source = MemoryHistory.this.items.listIterator(index);
/*     */     }
/*     */     
/*     */     public History.Entry next() {
/* 180 */       if (!this.source.hasNext()) {
/* 181 */         throw new NoSuchElementException();
/*     */       }
/* 183 */       return new MemoryHistory.EntryImpl(MemoryHistory.this.offset + this.source.nextIndex(), this.source.next());
/*     */     }
/*     */     
/*     */     public History.Entry previous() {
/* 187 */       if (!this.source.hasPrevious()) {
/* 188 */         throw new NoSuchElementException();
/*     */       }
/* 190 */       return new MemoryHistory.EntryImpl(MemoryHistory.this.offset + this.source.previousIndex(), this.source.previous());
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 194 */       return MemoryHistory.this.offset + this.source.nextIndex();
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 198 */       return MemoryHistory.this.offset + this.source.previousIndex();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 202 */       return this.source.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 206 */       return this.source.hasPrevious();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 210 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void set(History.Entry entry) {
/* 214 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void add(History.Entry entry) {
/* 218 */       throw new UnsupportedOperationException();
/*     */     }
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
/*     */   
/*     */   public boolean moveToLast() {
/* 234 */     int lastEntry = size() - 1;
/* 235 */     if (lastEntry >= 0 && lastEntry != this.index) {
/* 236 */       this.index = size() - 1;
/* 237 */       return true;
/*     */     } 
/*     */     
/* 240 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean moveTo(int index) {
/* 249 */     index -= this.offset;
/* 250 */     if (index >= 0 && index < size()) {
/* 251 */       this.index = index;
/* 252 */       return true;
/*     */     } 
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean moveToFirst() {
/* 264 */     if (size() > 0 && this.index != 0) {
/* 265 */       this.index = 0;
/* 266 */       return true;
/*     */     } 
/*     */     
/* 269 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveToEnd() {
/* 277 */     this.index = size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence current() {
/* 284 */     if (this.index >= size()) {
/* 285 */       return "";
/*     */     }
/*     */     
/* 288 */     return this.items.get(this.index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean previous() {
/* 297 */     if (this.index <= 0) {
/* 298 */       return false;
/*     */     }
/*     */     
/* 301 */     this.index--;
/*     */     
/* 303 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean next() {
/* 312 */     if (this.index >= size()) {
/* 313 */       return false;
/*     */     }
/*     */     
/* 316 */     this.index++;
/*     */     
/* 318 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\history\MemoryHistory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */