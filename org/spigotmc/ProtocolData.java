/*     */ package org.spigotmc;
/*     */ 
/*     */ 
/*     */ public class ProtocolData
/*     */ {
/*     */   public static class ByteShort
/*     */     extends Number
/*     */   {
/*     */     private short value;
/*     */     
/*     */     public ByteShort(short value) {
/*  12 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int intValue() {
/*  18 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long longValue() {
/*  24 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float floatValue() {
/*  30 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double doubleValue() {
/*  36 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DualByte
/*     */     extends Number
/*     */   {
/*     */     public byte value;
/*     */     public byte value2;
/*     */     
/*     */     public DualByte(byte value, byte value2) {
/*  48 */       this.value = value;
/*  49 */       this.value2 = value2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int intValue() {
/*  55 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long longValue() {
/*  61 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float floatValue() {
/*  67 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double doubleValue() {
/*  73 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class HiddenByte
/*     */     extends Number
/*     */   {
/*     */     private byte value;
/*     */     
/*     */     public HiddenByte(byte value) {
/*  84 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int intValue() {
/*  90 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long longValue() {
/*  96 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float floatValue() {
/* 102 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double doubleValue() {
/* 108 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class IntByte
/*     */     extends Number
/*     */   {
/*     */     public int value;
/*     */     public byte value2;
/*     */     
/*     */     public IntByte(int value, byte value2) {
/* 119 */       this.value = value;
/* 120 */       this.value2 = value2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public byte byteValue() {
/* 126 */       return this.value2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int intValue() {
/* 132 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long longValue() {
/* 138 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float floatValue() {
/* 144 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double doubleValue() {
/* 150 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DualInt
/*     */     extends Number
/*     */   {
/*     */     public int value;
/*     */     public int value2;
/*     */     
/*     */     public DualInt(int value, int value2) {
/* 162 */       this.value = value;
/* 163 */       this.value2 = value2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int intValue() {
/* 169 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long longValue() {
/* 175 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float floatValue() {
/* 181 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double doubleValue() {
/* 187 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\ProtocolData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */