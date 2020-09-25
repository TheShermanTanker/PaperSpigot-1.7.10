/*     */ package net.minecraft.server.v1_7_R4;
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
/*     */ public class LongHashMap
/*     */ {
/*     */   private volatile transient int e;
/*  18 */   private final float d = 0.75F;
/*  19 */   private int c = 12;
/*  20 */   private transient LongHashMapEntry[] entries = new LongHashMapEntry[16];
/*     */   private transient int count;
/*     */   
/*     */   private static int g(long paramLong) {
/*  24 */     return a((int)(paramLong ^ paramLong >>> 32L));
/*     */   }
/*     */   
/*     */   private static int a(int paramInt) {
/*  28 */     paramInt ^= paramInt >>> 20 ^ paramInt >>> 12;
/*  29 */     return paramInt ^ paramInt >>> 7 ^ paramInt >>> 4;
/*     */   }
/*     */   
/*     */   private static int a(int paramInt1, int paramInt2) {
/*  33 */     return paramInt1 & paramInt2 - 1;
/*     */   }
/*     */   
/*     */   public int count() {
/*  37 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEntry(long paramLong) {
/*  45 */     int i = g(paramLong);
/*  46 */     for (LongHashMapEntry longHashMapEntry = this.entries[a(i, this.entries.length)]; longHashMapEntry != null; longHashMapEntry = longHashMapEntry.c) {
/*  47 */       if (longHashMapEntry.a == paramLong) return longHashMapEntry.b; 
/*     */     } 
/*  49 */     return null;
/*     */   }
/*     */   
/*     */   public boolean contains(long paramLong) {
/*  53 */     return (c(paramLong) != null);
/*     */   }
/*     */   
/*     */   final LongHashMapEntry c(long paramLong) {
/*  57 */     int i = g(paramLong);
/*  58 */     for (LongHashMapEntry longHashMapEntry = this.entries[a(i, this.entries.length)]; longHashMapEntry != null; longHashMapEntry = longHashMapEntry.c) {
/*  59 */       if (longHashMapEntry.a == paramLong) return longHashMapEntry; 
/*     */     } 
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   public void put(long paramLong, Object paramObject) {
/*  65 */     int i = g(paramLong);
/*  66 */     int j = a(i, this.entries.length);
/*  67 */     for (LongHashMapEntry longHashMapEntry = this.entries[j]; longHashMapEntry != null; longHashMapEntry = longHashMapEntry.c) {
/*  68 */       if (longHashMapEntry.a == paramLong) {
/*  69 */         longHashMapEntry.b = paramObject;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  74 */     this.e++;
/*  75 */     a(i, paramLong, paramObject, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private void b(int paramInt) {
/*  80 */     LongHashMapEntry[] arrayOfLongHashMapEntry1 = this.entries;
/*  81 */     int i = arrayOfLongHashMapEntry1.length;
/*  82 */     if (i == 1073741824) {
/*  83 */       this.c = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/*  87 */     LongHashMapEntry[] arrayOfLongHashMapEntry2 = new LongHashMapEntry[paramInt];
/*  88 */     a(arrayOfLongHashMapEntry2);
/*  89 */     this.entries = arrayOfLongHashMapEntry2;
/*  90 */     this.c = (int)(paramInt * this.d);
/*     */   }
/*     */   
/*     */   private void a(LongHashMapEntry[] paramArrayOfLongHashMapEntry) {
/*  94 */     LongHashMapEntry[] arrayOfLongHashMapEntry = this.entries;
/*  95 */     int i = paramArrayOfLongHashMapEntry.length;
/*  96 */     for (byte b = 0; b < arrayOfLongHashMapEntry.length; b++) {
/*  97 */       LongHashMapEntry longHashMapEntry = arrayOfLongHashMapEntry[b];
/*  98 */       if (longHashMapEntry != null) {
/*  99 */         arrayOfLongHashMapEntry[b] = null;
/*     */         do {
/* 101 */           LongHashMapEntry longHashMapEntry1 = longHashMapEntry.c;
/* 102 */           int j = a(longHashMapEntry.d, i);
/* 103 */           longHashMapEntry.c = paramArrayOfLongHashMapEntry[j];
/* 104 */           paramArrayOfLongHashMapEntry[j] = longHashMapEntry;
/* 105 */           longHashMapEntry = longHashMapEntry1;
/* 106 */         } while (longHashMapEntry != null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object remove(long paramLong) {
/* 112 */     LongHashMapEntry longHashMapEntry = e(paramLong);
/* 113 */     return (longHashMapEntry == null) ? null : longHashMapEntry.b;
/*     */   }
/*     */   
/*     */   final LongHashMapEntry e(long paramLong) {
/* 117 */     int i = g(paramLong);
/* 118 */     int j = a(i, this.entries.length);
/* 119 */     LongHashMapEntry longHashMapEntry1 = this.entries[j];
/* 120 */     LongHashMapEntry longHashMapEntry2 = longHashMapEntry1;
/*     */     
/* 122 */     while (longHashMapEntry2 != null) {
/* 123 */       LongHashMapEntry longHashMapEntry = longHashMapEntry2.c;
/* 124 */       if (longHashMapEntry2.a == paramLong) {
/* 125 */         this.e++;
/* 126 */         this.count--;
/* 127 */         if (longHashMapEntry1 == longHashMapEntry2) { this.entries[j] = longHashMapEntry; }
/* 128 */         else { longHashMapEntry1.c = longHashMapEntry; }
/* 129 */          return longHashMapEntry2;
/*     */       } 
/* 131 */       longHashMapEntry1 = longHashMapEntry2;
/* 132 */       longHashMapEntry2 = longHashMapEntry;
/*     */     } 
/*     */     
/* 135 */     return longHashMapEntry2;
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
/*     */   private void a(int paramInt1, long paramLong, Object paramObject, int paramInt2) {
/* 220 */     LongHashMapEntry longHashMapEntry = this.entries[paramInt2];
/* 221 */     this.entries[paramInt2] = new LongHashMapEntry(paramInt1, paramLong, paramObject, longHashMapEntry);
/* 222 */     if (this.count++ >= this.c) b(2 * this.entries.length); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\LongHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */