/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class NibbleArray
/*    */ {
/*    */   public final byte[] a;
/*    */   private final int b;
/*    */   private final int c;
/*    */   
/*    */   public NibbleArray(int i, int j) {
/* 10 */     this.a = new byte[i >> 1];
/* 11 */     this.b = j;
/* 12 */     this.c = j + 4;
/*    */   }
/*    */   
/*    */   public NibbleArray(byte[] abyte, int i) {
/* 16 */     this.a = abyte;
/* 17 */     this.b = i;
/* 18 */     this.c = i + 4;
/*    */   }
/*    */   
/*    */   public int a(int i, int j, int k) {
/* 22 */     int l = j << this.c | k << this.b | i;
/* 23 */     int i1 = l >> 1;
/* 24 */     int j1 = l & 0x1;
/*    */     
/* 26 */     return (j1 == 0) ? (this.a[i1] & 0xF) : (this.a[i1] >> 4 & 0xF);
/*    */   }
/*    */   
/*    */   public void a(int i, int j, int k, int l) {
/* 30 */     int i1 = j << this.c | k << this.b | i;
/* 31 */     int j1 = i1 >> 1;
/* 32 */     int k1 = i1 & 0x1;
/*    */     
/* 34 */     if (k1 == 0) {
/* 35 */       this.a[j1] = (byte)(this.a[j1] & 0xF0 | l & 0xF);
/*    */     } else {
/* 37 */       this.a[j1] = (byte)(this.a[j1] & 0xF | (l & 0xF) << 4);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NibbleArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */