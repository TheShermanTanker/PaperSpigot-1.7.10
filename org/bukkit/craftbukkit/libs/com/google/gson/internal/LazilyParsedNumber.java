/*    */ package org.bukkit.craftbukkit.libs.com.google.gson.internal;
/*    */ 
/*    */ import java.math.BigInteger;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LazilyParsedNumber
/*    */   extends Number
/*    */ {
/*    */   private final String value;
/*    */   
/*    */   public LazilyParsedNumber(String value) {
/* 30 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int intValue() {
/*    */     try {
/* 36 */       return Integer.parseInt(this.value);
/* 37 */     } catch (NumberFormatException e) {
/*    */       try {
/* 39 */         return (int)Long.parseLong(this.value);
/* 40 */       } catch (NumberFormatException nfe) {
/* 41 */         return (new BigInteger(this.value)).intValue();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public long longValue() {
/*    */     try {
/* 49 */       return Long.parseLong(this.value);
/* 50 */     } catch (NumberFormatException e) {
/* 51 */       return (new BigInteger(this.value)).longValue();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public float floatValue() {
/* 57 */     return Float.parseFloat(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public double doubleValue() {
/* 62 */     return Double.parseDouble(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\internal\LazilyParsedNumber.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */