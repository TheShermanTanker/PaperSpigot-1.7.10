/*     */ package net.minecraft.util.com.google.common.escape;
/*     */ 
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
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
/*     */ @GwtCompatible
/*     */ public abstract class CharEscaper
/*     */   extends Escaper
/*     */ {
/*     */   private static final int DEST_PAD = 32;
/*     */   
/*     */   public String escape(String string) {
/*  59 */     Preconditions.checkNotNull(string);
/*     */     
/*  61 */     int length = string.length();
/*  62 */     for (int index = 0; index < length; index++) {
/*  63 */       if (escape(string.charAt(index)) != null) {
/*  64 */         return escapeSlow(string, index);
/*     */       }
/*     */     } 
/*  67 */     return string;
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
/*     */   protected final String escapeSlow(String s, int index) {
/*  82 */     int slen = s.length();
/*     */ 
/*     */     
/*  85 */     char[] dest = Platform.charBufferFromThreadLocal();
/*  86 */     int destSize = dest.length;
/*  87 */     int destIndex = 0;
/*  88 */     int lastEscape = 0;
/*     */ 
/*     */ 
/*     */     
/*  92 */     for (; index < slen; index++) {
/*     */ 
/*     */       
/*  95 */       char[] r = escape(s.charAt(index));
/*     */ 
/*     */       
/*  98 */       if (r != null) {
/*     */         
/* 100 */         int rlen = r.length;
/* 101 */         int charsSkipped = index - lastEscape;
/*     */ 
/*     */ 
/*     */         
/* 105 */         int sizeNeeded = destIndex + charsSkipped + rlen;
/* 106 */         if (destSize < sizeNeeded) {
/* 107 */           destSize = sizeNeeded + slen - index + 32;
/* 108 */           dest = growBuffer(dest, destIndex, destSize);
/*     */         } 
/*     */ 
/*     */         
/* 112 */         if (charsSkipped > 0) {
/* 113 */           s.getChars(lastEscape, index, dest, destIndex);
/* 114 */           destIndex += charsSkipped;
/*     */         } 
/*     */ 
/*     */         
/* 118 */         if (rlen > 0) {
/* 119 */           System.arraycopy(r, 0, dest, destIndex, rlen);
/* 120 */           destIndex += rlen;
/*     */         } 
/* 122 */         lastEscape = index + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     int charsLeft = slen - lastEscape;
/* 127 */     if (charsLeft > 0) {
/* 128 */       int sizeNeeded = destIndex + charsLeft;
/* 129 */       if (destSize < sizeNeeded)
/*     */       {
/*     */         
/* 132 */         dest = growBuffer(dest, destIndex, sizeNeeded);
/*     */       }
/* 134 */       s.getChars(lastEscape, slen, dest, destIndex);
/* 135 */       destIndex = sizeNeeded;
/*     */     } 
/* 137 */     return new String(dest, 0, destIndex);
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
/*     */   protected abstract char[] escape(char paramChar);
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
/*     */   private static char[] growBuffer(char[] dest, int index, int size) {
/* 162 */     char[] copy = new char[size];
/* 163 */     if (index > 0) {
/* 164 */       System.arraycopy(dest, 0, copy, 0, index);
/*     */     }
/* 166 */     return copy;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\escape\CharEscaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */