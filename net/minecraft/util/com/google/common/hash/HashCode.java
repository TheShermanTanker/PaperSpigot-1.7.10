/*     */ package net.minecraft.util.com.google.common.hash;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.MessageDigest;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.primitives.Ints;
/*     */ import net.minecraft.util.com.google.common.primitives.UnsignedInts;
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
/*     */ @Beta
/*     */ public abstract class HashCode
/*     */ {
/*     */   public abstract int bits();
/*     */   
/*     */   public abstract int asInt();
/*     */   
/*     */   public abstract long asLong();
/*     */   
/*     */   public abstract long padToLong();
/*     */   
/*     */   public abstract byte[] asBytes();
/*     */   
/*     */   public int writeBytesTo(byte[] dest, int offset, int maxLength) {
/*  90 */     maxLength = Ints.min(new int[] { maxLength, bits() / 8 });
/*  91 */     Preconditions.checkPositionIndexes(offset, offset + maxLength, dest.length);
/*  92 */     writeBytesToImpl(dest, offset, maxLength);
/*  93 */     return maxLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void writeBytesToImpl(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HashCode fromInt(int hash) {
/* 105 */     return new IntHashCode(hash);
/*     */   }
/*     */   
/*     */   private static final class IntHashCode extends HashCode implements Serializable {
/*     */     final int hash;
/*     */     
/*     */     IntHashCode(int hash) {
/* 112 */       this.hash = hash;
/*     */     }
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public int bits() {
/* 117 */       return 32;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] asBytes() {
/* 122 */       return new byte[] { (byte)this.hash, (byte)(this.hash >> 8), (byte)(this.hash >> 16), (byte)(this.hash >> 24) };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int asInt() {
/* 131 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public long asLong() {
/* 136 */       throw new IllegalStateException("this HashCode only has 32 bits; cannot create a long");
/*     */     }
/*     */ 
/*     */     
/*     */     public long padToLong() {
/* 141 */       return UnsignedInts.toLong(this.hash);
/*     */     }
/*     */ 
/*     */     
/*     */     void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
/* 146 */       for (int i = 0; i < maxLength; i++) {
/* 147 */         dest[offset + i] = (byte)(this.hash >> i * 8);
/*     */       }
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
/*     */   public static HashCode fromLong(long hash) {
/* 161 */     return new LongHashCode(hash);
/*     */   }
/*     */   
/*     */   private static final class LongHashCode extends HashCode implements Serializable { final long hash;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     LongHashCode(long hash) {
/* 168 */       this.hash = hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public int bits() {
/* 173 */       return 64;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] asBytes() {
/* 178 */       return new byte[] { (byte)(int)this.hash, (byte)(int)(this.hash >> 8L), (byte)(int)(this.hash >> 16L), (byte)(int)(this.hash >> 24L), (byte)(int)(this.hash >> 32L), (byte)(int)(this.hash >> 40L), (byte)(int)(this.hash >> 48L), (byte)(int)(this.hash >> 56L) };
/*     */     }
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
/*     */     public int asInt() {
/* 191 */       return (int)this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public long asLong() {
/* 196 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public long padToLong() {
/* 201 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
/* 206 */       for (int i = 0; i < maxLength; i++) {
/* 207 */         dest[offset + i] = (byte)(int)(this.hash >> i * 8);
/*     */       }
/*     */     } }
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
/*     */   public static HashCode fromBytes(byte[] bytes) {
/* 221 */     Preconditions.checkArgument((bytes.length >= 1), "A HashCode must contain at least 1 byte.");
/* 222 */     return fromBytesNoCopy((byte[])bytes.clone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static HashCode fromBytesNoCopy(byte[] bytes) {
/* 230 */     return new BytesHashCode(bytes);
/*     */   }
/*     */   
/*     */   private static final class BytesHashCode extends HashCode implements Serializable { final byte[] bytes;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     BytesHashCode(byte[] bytes) {
/* 237 */       this.bytes = (byte[])Preconditions.checkNotNull(bytes);
/*     */     }
/*     */ 
/*     */     
/*     */     public int bits() {
/* 242 */       return this.bytes.length * 8;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] asBytes() {
/* 247 */       return (byte[])this.bytes.clone();
/*     */     }
/*     */ 
/*     */     
/*     */     public int asInt() {
/* 252 */       Preconditions.checkState((this.bytes.length >= 4), "HashCode#asInt() requires >= 4 bytes (it only has %s bytes).", new Object[] { Integer.valueOf(this.bytes.length) });
/*     */       
/* 254 */       return this.bytes[0] & 0xFF | (this.bytes[1] & 0xFF) << 8 | (this.bytes[2] & 0xFF) << 16 | (this.bytes[3] & 0xFF) << 24;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long asLong() {
/* 262 */       Preconditions.checkState((this.bytes.length >= 8), "HashCode#asLong() requires >= 8 bytes (it only has %s bytes).", new Object[] { Integer.valueOf(this.bytes.length) });
/*     */       
/* 264 */       return padToLong();
/*     */     }
/*     */ 
/*     */     
/*     */     public long padToLong() {
/* 269 */       long retVal = (this.bytes[0] & 0xFF);
/* 270 */       for (int i = 1; i < Math.min(this.bytes.length, 8); i++) {
/* 271 */         retVal |= (this.bytes[i] & 0xFFL) << i * 8;
/*     */       }
/* 273 */       return retVal;
/*     */     }
/*     */ 
/*     */     
/*     */     void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
/* 278 */       System.arraycopy(this.bytes, 0, dest, offset, maxLength);
/*     */     } }
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
/*     */   public static HashCode fromString(String string) {
/* 295 */     Preconditions.checkArgument((string.length() >= 2), "input string (%s) must have at least 2 characters", new Object[] { string });
/*     */     
/* 297 */     Preconditions.checkArgument((string.length() % 2 == 0), "input string (%s) must have an even number of characters", new Object[] { string });
/*     */ 
/*     */     
/* 300 */     byte[] bytes = new byte[string.length() / 2];
/* 301 */     for (int i = 0; i < string.length(); i += 2) {
/* 302 */       int ch1 = decode(string.charAt(i)) << 4;
/* 303 */       int ch2 = decode(string.charAt(i + 1));
/* 304 */       bytes[i / 2] = (byte)(ch1 + ch2);
/*     */     } 
/* 306 */     return fromBytesNoCopy(bytes);
/*     */   }
/*     */   
/*     */   private static int decode(char ch) {
/* 310 */     if (ch >= '0' && ch <= '9') {
/* 311 */       return ch - 48;
/*     */     }
/* 313 */     if (ch >= 'a' && ch <= 'f') {
/* 314 */       return ch - 97 + 10;
/*     */     }
/* 316 */     throw new IllegalArgumentException("Illegal hexadecimal character: " + ch);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(@Nullable Object object) {
/* 321 */     if (object instanceof HashCode) {
/* 322 */       HashCode that = (HashCode)object;
/*     */ 
/*     */       
/* 325 */       return MessageDigest.isEqual(asBytes(), that.asBytes());
/*     */     } 
/* 327 */     return false;
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
/*     */   public final int hashCode() {
/* 339 */     if (bits() >= 32) {
/* 340 */       return asInt();
/*     */     }
/*     */     
/* 343 */     byte[] bytes = asBytes();
/* 344 */     int val = bytes[0] & 0xFF;
/* 345 */     for (int i = 1; i < bytes.length; i++) {
/* 346 */       val |= (bytes[i] & 0xFF) << i * 8;
/*     */     }
/* 348 */     return val;
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
/*     */   public final String toString() {
/* 364 */     byte[] bytes = asBytes();
/* 365 */     StringBuilder sb = new StringBuilder(2 * bytes.length);
/* 366 */     for (byte b : bytes) {
/* 367 */       sb.append(hexDigits[b >> 4 & 0xF]).append(hexDigits[b & 0xF]);
/*     */     }
/* 369 */     return sb.toString();
/*     */   }
/*     */   
/* 372 */   private static final char[] hexDigits = "0123456789abcdef".toCharArray();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\hash\HashCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */