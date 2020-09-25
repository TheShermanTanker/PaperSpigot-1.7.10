/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntHashMap
/*     */ {
/*   8 */   private transient IntHashMapEntry[] a = new IntHashMapEntry[16];
/*     */   private transient int b;
/*  10 */   private int c = 12;
/*  11 */   private final float d = 0.75F;
/*     */ 
/*     */   
/*     */   private volatile transient int e;
/*     */ 
/*     */   
/*     */   private static int g(int i) {
/*  18 */     i ^= i >>> 20 ^ i >>> 12;
/*  19 */     return i ^ i >>> 7 ^ i >>> 4;
/*     */   }
/*     */   
/*     */   private static int a(int i, int j) {
/*  23 */     return i & j - 1;
/*     */   }
/*     */   
/*     */   public Object get(int i) {
/*  27 */     int j = g(i);
/*     */     
/*  29 */     for (IntHashMapEntry inthashmapentry = this.a[a(j, this.a.length)]; inthashmapentry != null; inthashmapentry = inthashmapentry.c) {
/*  30 */       if (inthashmapentry.a == i) {
/*  31 */         return inthashmapentry.b;
/*     */       }
/*     */     } 
/*     */     
/*  35 */     return null;
/*     */   }
/*     */   
/*     */   public boolean b(int i) {
/*  39 */     return (c(i) != null);
/*     */   }
/*     */   
/*     */   final IntHashMapEntry c(int i) {
/*  43 */     int j = g(i);
/*     */     
/*  45 */     for (IntHashMapEntry inthashmapentry = this.a[a(j, this.a.length)]; inthashmapentry != null; inthashmapentry = inthashmapentry.c) {
/*  46 */       if (inthashmapentry.a == i) {
/*  47 */         return inthashmapentry;
/*     */       }
/*     */     } 
/*     */     
/*  51 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(int i, Object object) {
/*  56 */     int j = g(i);
/*  57 */     int k = a(j, this.a.length);
/*     */     
/*  59 */     for (IntHashMapEntry inthashmapentry = this.a[k]; inthashmapentry != null; inthashmapentry = inthashmapentry.c) {
/*  60 */       if (inthashmapentry.a == i) {
/*  61 */         inthashmapentry.b = object;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  66 */     this.e++;
/*  67 */     a(j, i, object, k);
/*     */   }
/*     */   
/*     */   private void h(int i) {
/*  71 */     IntHashMapEntry[] ainthashmapentry = this.a;
/*  72 */     int j = ainthashmapentry.length;
/*     */     
/*  74 */     if (j == 1073741824) {
/*  75 */       this.c = Integer.MAX_VALUE;
/*     */     } else {
/*  77 */       IntHashMapEntry[] ainthashmapentry1 = new IntHashMapEntry[i];
/*     */       
/*  79 */       a(ainthashmapentry1);
/*  80 */       this.a = ainthashmapentry1;
/*  81 */       getClass(); this.c = (int)(i * 0.75F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(IntHashMapEntry[] ainthashmapentry) {
/*  86 */     IntHashMapEntry[] ainthashmapentry1 = this.a;
/*  87 */     int i = ainthashmapentry.length;
/*     */     
/*  89 */     for (int j = 0; j < ainthashmapentry1.length; j++) {
/*  90 */       IntHashMapEntry inthashmapentry = ainthashmapentry1[j];
/*     */       
/*  92 */       if (inthashmapentry != null) {
/*  93 */         IntHashMapEntry inthashmapentry1; ainthashmapentry1[j] = null;
/*     */ 
/*     */ 
/*     */         
/*     */         do {
/*  98 */           inthashmapentry1 = inthashmapentry.c;
/*  99 */           int k = a(inthashmapentry.d, i);
/*     */           
/* 101 */           inthashmapentry.c = ainthashmapentry[k];
/* 102 */           ainthashmapentry[k] = inthashmapentry;
/* 103 */           inthashmapentry = inthashmapentry1;
/* 104 */         } while (inthashmapentry1 != null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object d(int i) {
/* 111 */     IntHashMapEntry inthashmapentry = e(i);
/*     */     
/* 113 */     return (inthashmapentry == null) ? null : inthashmapentry.b;
/*     */   }
/*     */   
/*     */   final IntHashMapEntry e(int i) {
/* 117 */     int j = g(i);
/* 118 */     int k = a(j, this.a.length);
/* 119 */     IntHashMapEntry inthashmapentry = this.a[k];
/*     */ 
/*     */     
/*     */     IntHashMapEntry inthashmapentry1;
/*     */     
/* 124 */     for (inthashmapentry1 = inthashmapentry; inthashmapentry1 != null; inthashmapentry1 = inthashmapentry2) {
/* 125 */       IntHashMapEntry inthashmapentry2 = inthashmapentry1.c;
/* 126 */       if (inthashmapentry1.a == i) {
/* 127 */         this.e++;
/* 128 */         this.b--;
/* 129 */         if (inthashmapentry == inthashmapentry1) {
/* 130 */           this.a[k] = inthashmapentry2;
/*     */         } else {
/* 132 */           inthashmapentry.c = inthashmapentry2;
/*     */         } 
/*     */         
/* 135 */         return inthashmapentry1;
/*     */       } 
/*     */       
/* 138 */       inthashmapentry = inthashmapentry1;
/*     */     } 
/*     */     
/* 141 */     return inthashmapentry1;
/*     */   }
/*     */   
/*     */   public void c() {
/* 145 */     this.e++;
/* 146 */     IntHashMapEntry[] ainthashmapentry = this.a;
/*     */     
/* 148 */     for (int i = 0; i < ainthashmapentry.length; i++) {
/* 149 */       ainthashmapentry[i] = null;
/*     */     }
/*     */     
/* 152 */     this.b = 0;
/*     */   }
/*     */   
/*     */   private void a(int i, int j, Object object, int k) {
/* 156 */     IntHashMapEntry inthashmapentry = this.a[k];
/*     */     
/* 158 */     this.a[k] = new IntHashMapEntry(i, j, object, inthashmapentry);
/* 159 */     if (this.b++ >= this.c) {
/* 160 */       h(2 * this.a.length);
/*     */     }
/*     */   }
/*     */   
/*     */   static int f(int i) {
/* 165 */     return g(i);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IntHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */