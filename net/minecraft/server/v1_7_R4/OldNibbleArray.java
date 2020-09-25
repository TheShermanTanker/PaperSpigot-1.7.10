/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OldNibbleArray
/*    */ {
/*    */   public final byte[] a;
/*    */   private final int b;
/*    */   private final int c;
/*    */   
/*    */   public OldNibbleArray(byte[] paramArrayOfbyte, int paramInt) {
/* 15 */     this.a = paramArrayOfbyte;
/* 16 */     this.b = paramInt;
/* 17 */     this.c = paramInt + 4;
/*    */   }
/*    */   
/*    */   public int a(int paramInt1, int paramInt2, int paramInt3) {
/* 21 */     int i = paramInt1 << this.c | paramInt3 << this.b | paramInt2;
/* 22 */     int j = i >> 1;
/* 23 */     int k = i & 0x1;
/*    */     
/* 25 */     if (k == 0) {
/* 26 */       return this.a[j] & 0xF;
/*    */     }
/* 28 */     return this.a[j] >> 4 & 0xF;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\OldNibbleArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */