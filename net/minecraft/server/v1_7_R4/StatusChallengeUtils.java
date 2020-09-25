/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.util.com.google.common.base.Charsets;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatusChallengeUtils
/*    */ {
/* 10 */   public static char[] a = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String a(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 19 */     int i = paramInt2 - 1;
/* 20 */     int j = (paramInt1 > i) ? i : paramInt1;
/* 21 */     while (0 != paramArrayOfbyte[j] && j < i) {
/* 22 */       j++;
/*    */     }
/*    */     
/* 25 */     return new String(paramArrayOfbyte, paramInt1, j - paramInt1, Charsets.UTF_8);
/*    */   }
/*    */   
/*    */   public static int b(byte[] paramArrayOfbyte, int paramInt) {
/* 29 */     return b(paramArrayOfbyte, paramInt, paramArrayOfbyte.length);
/*    */   }
/*    */   
/*    */   public static int b(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 33 */     if (0 > paramInt2 - paramInt1 - 4)
/*    */     {
/*    */       
/* 36 */       return 0;
/*    */     }
/* 38 */     return paramArrayOfbyte[paramInt1 + 3] << 24 | (paramArrayOfbyte[paramInt1 + 2] & 0xFF) << 16 | (paramArrayOfbyte[paramInt1 + 1] & 0xFF) << 8 | paramArrayOfbyte[paramInt1] & 0xFF;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int c(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 46 */     if (0 > paramInt2 - paramInt1 - 4)
/*    */     {
/*    */       
/* 49 */       return 0;
/*    */     }
/* 51 */     return paramArrayOfbyte[paramInt1] << 24 | (paramArrayOfbyte[paramInt1 + 1] & 0xFF) << 16 | (paramArrayOfbyte[paramInt1 + 2] & 0xFF) << 8 | paramArrayOfbyte[paramInt1 + 3] & 0xFF;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String a(byte paramByte) {
/* 65 */     return "" + a[(paramByte & 0xF0) >>> 4] + a[paramByte & 0xF];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\StatusChallengeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */