/*     */ package com.avaje.ebean.bean;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public final class CallStack
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8590644046907438579L;
/*     */   private final String zeroHash;
/*     */   private final String pathHash;
/*     */   private final StackTraceElement[] callStack;
/*     */   private static final int radix = 64;
/*     */   private static final int mask = 63;
/*     */   
/*     */   public CallStack(StackTraceElement[] callStack) {
/*  48 */     this.callStack = callStack;
/*  49 */     this.zeroHash = enc(callStack[0].hashCode());
/*  50 */     int hc = 0;
/*  51 */     for (int i = 1; i < callStack.length; i++) {
/*  52 */       hc = 31 * hc + callStack[i].hashCode();
/*     */     }
/*  54 */     this.pathHash = enc(hc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackTraceElement getFirstStackTraceElement() {
/*  61 */     return this.callStack[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackTraceElement[] getCallStack() {
/*  68 */     return this.callStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getZeroHash() {
/*  75 */     return this.zeroHash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPathHash() {
/*  83 */     return this.pathHash;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  87 */     return this.zeroHash + ":" + this.pathHash + ":" + this.callStack[0];
/*     */   }
/*     */   
/*     */   public String getOriginKey(int queryHash) {
/*  91 */     return this.zeroHash + "." + enc(queryHash) + "." + this.pathHash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String enc(int i) {
/* 102 */     char[] buf = new char[32];
/* 103 */     int charPos = 32;
/*     */     do {
/* 105 */       buf[--charPos] = intToBase64[i & 0x3F];
/* 106 */       i >>>= 6;
/* 107 */     } while (i != 0);
/*     */     
/* 109 */     return new String(buf, charPos, 32 - charPos);
/*     */   }
/*     */   
/* 112 */   private static final char[] intToBase64 = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_' };
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\bean\CallStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */