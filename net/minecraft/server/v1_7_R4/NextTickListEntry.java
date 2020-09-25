/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class NextTickListEntry
/*    */   implements Comparable {
/*    */   private static long f;
/*    */   private final Block g;
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public long d;
/*    */   public int e;
/*    */   private long h;
/*    */   
/*    */   public NextTickListEntry(int i, int j, int k, Block block) {
/* 15 */     this.h = f++;
/* 16 */     this.a = i;
/* 17 */     this.b = j;
/* 18 */     this.c = k;
/* 19 */     this.g = block;
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 23 */     if (!(object instanceof NextTickListEntry)) {
/* 24 */       return false;
/*    */     }
/* 26 */     NextTickListEntry nextticklistentry = (NextTickListEntry)object;
/*    */     
/* 28 */     return (this.a == nextticklistentry.a && this.b == nextticklistentry.b && this.c == nextticklistentry.c && Block.a(this.g, nextticklistentry.g));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 33 */     return (this.a * 1024 * 1024 + this.c * 1024 + this.b) * 256;
/*    */   }
/*    */   
/*    */   public NextTickListEntry a(long i) {
/* 37 */     this.d = i;
/* 38 */     return this;
/*    */   }
/*    */   
/*    */   public void a(int i) {
/* 42 */     this.e = i;
/*    */   }
/*    */   
/*    */   public int compareTo(NextTickListEntry nextticklistentry) {
/* 46 */     return (this.d < nextticklistentry.d) ? -1 : ((this.d > nextticklistentry.d) ? 1 : ((this.e != nextticklistentry.e) ? (this.e - nextticklistentry.e) : ((this.h < nextticklistentry.h) ? -1 : ((this.h > nextticklistentry.h) ? 1 : 0))));
/*    */   }
/*    */   
/*    */   public String toString() {
/* 50 */     return Block.getId(this.g) + ": (" + this.a + ", " + this.b + ", " + this.c + "), " + this.d + ", " + this.e + ", " + this.h;
/*    */   }
/*    */   
/*    */   public Block a() {
/* 54 */     return this.g;
/*    */   }
/*    */   
/*    */   public int compareTo(Object object) {
/* 58 */     return compareTo((NextTickListEntry)object);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NextTickListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */