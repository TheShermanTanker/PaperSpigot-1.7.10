/*     */ package net.minecraft.util.io.netty.util;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class UniqueName
/*     */   implements Comparable<UniqueName>
/*     */ {
/*  26 */   private static final AtomicInteger nextId = new AtomicInteger();
/*     */ 
/*     */ 
/*     */   
/*     */   private final int id;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */   
/*     */   public UniqueName(ConcurrentMap<String, Boolean> map, String name, Object... args) {
/*  39 */     if (map == null) {
/*  40 */       throw new NullPointerException("map");
/*     */     }
/*  42 */     if (name == null) {
/*  43 */       throw new NullPointerException("name");
/*     */     }
/*  45 */     if (args != null && args.length > 0) {
/*  46 */       validateArgs(args);
/*     */     }
/*     */     
/*  49 */     if (map.putIfAbsent(name, Boolean.TRUE) != null) {
/*  50 */       throw new IllegalArgumentException(String.format("'%s' is already in use", new Object[] { name }));
/*     */     }
/*     */     
/*  53 */     this.id = nextId.incrementAndGet();
/*  54 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateArgs(Object... args) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String name() {
/*  74 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int id() {
/*  83 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/*  88 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object o) {
/*  93 */     return super.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(UniqueName other) {
/*  98 */     if (this == other) {
/*  99 */       return 0;
/*     */     }
/*     */     
/* 102 */     int returnCode = this.name.compareTo(other.name);
/* 103 */     if (returnCode != 0) {
/* 104 */       return returnCode;
/*     */     }
/*     */     
/* 107 */     return Integer.valueOf(this.id).compareTo(Integer.valueOf(other.id));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 112 */     return name();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\UniqueName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */