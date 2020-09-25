/*     */ package org.fusesource.hawtjni.runtime;
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
/*     */ public class Callback
/*     */ {
/*     */   Object object;
/*     */   String method;
/*     */   String signature;
/*     */   int argCount;
/*     */   long address;
/*     */   long errorResult;
/*     */   boolean isStatic;
/*     */   boolean isArrayBased;
/*     */   static final String PTR_SIGNATURE = "J";
/*  37 */   static final String SIGNATURE_0 = getSignature(0);
/*  38 */   static final String SIGNATURE_1 = getSignature(1);
/*  39 */   static final String SIGNATURE_2 = getSignature(2);
/*  40 */   static final String SIGNATURE_3 = getSignature(3);
/*  41 */   static final String SIGNATURE_4 = getSignature(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String SIGNATURE_N = "([J)J";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Callback(Object object, String method, int argCount) {
/*  59 */     this(object, method, argCount, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Callback(Object object, String method, int argCount, boolean isArrayBased) {
/*  80 */     this(object, method, argCount, isArrayBased, 0L);
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
/*     */   public Callback(Object object, String method, int argCount, boolean isArrayBased, long errorResult) {
/* 106 */     this.object = object;
/* 107 */     this.method = method;
/* 108 */     this.argCount = argCount;
/* 109 */     this.isStatic = object instanceof Class;
/* 110 */     this.isArrayBased = isArrayBased;
/* 111 */     this.errorResult = errorResult;
/*     */ 
/*     */     
/* 114 */     if (isArrayBased) {
/* 115 */       this.signature = "([J)J";
/*     */     } else {
/* 117 */       switch (argCount) {
/*     */         case 0:
/* 119 */           this.signature = SIGNATURE_0;
/*     */           break;
/*     */         case 1:
/* 122 */           this.signature = SIGNATURE_1;
/*     */           break;
/*     */         case 2:
/* 125 */           this.signature = SIGNATURE_2;
/*     */           break;
/*     */         case 3:
/* 128 */           this.signature = SIGNATURE_3;
/*     */           break;
/*     */         case 4:
/* 131 */           this.signature = SIGNATURE_4;
/*     */           break;
/*     */         default:
/* 134 */           this.signature = getSignature(argCount);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 139 */     this.address = bind(this, object, method, this.signature, argCount, this.isStatic, isArrayBased, errorResult);
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
/*     */ 
/*     */   
/*     */   static synchronized native long bind(Callback paramCallback, Object paramObject, String paramString1, String paramString2, int paramInt, boolean paramBoolean1, boolean paramBoolean2, long paramLong);
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
/*     */   public void dispose() {
/* 173 */     if (this.object == null)
/*     */       return; 
/* 175 */     unbind(this);
/* 176 */     this.object = this.method = this.signature = null;
/* 177 */     this.address = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAddress() {
/* 187 */     return this.address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native String getPlatform();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native int getEntryCount();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getSignature(int argCount) {
/* 211 */     String signature = "(";
/* 212 */     for (int i = 0; i < argCount; i++)
/* 213 */       signature = signature + "J"; 
/* 214 */     signature = signature + ")J";
/* 215 */     return signature;
/*     */   }
/*     */   
/*     */   public static final synchronized native void setEnabled(boolean paramBoolean);
/*     */   
/*     */   public static final synchronized native boolean getEnabled();
/*     */   
/*     */   public static final synchronized native void reset();
/*     */   
/*     */   static final synchronized native void unbind(Callback paramCallback);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\hawtjni\runtime\Callback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */