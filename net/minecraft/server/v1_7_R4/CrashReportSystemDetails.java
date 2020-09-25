/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrashReportSystemDetails
/*     */ {
/*     */   private final CrashReport a;
/*     */   private final String b;
/*  13 */   private final List c = new ArrayList();
/*  14 */   private StackTraceElement[] d = new StackTraceElement[0];
/*     */   
/*     */   public CrashReportSystemDetails(CrashReport paramCrashReport, String paramString) {
/*  17 */     this.a = paramCrashReport;
/*  18 */     this.b = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String a(int paramInt1, int paramInt2, int paramInt3) {
/*  26 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/*     */     try {
/*  29 */       stringBuilder.append(String.format("World: (%d,%d,%d)", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) }));
/*  30 */     } catch (Throwable throwable) {
/*  31 */       stringBuilder.append("(Error finding world loc)");
/*     */     } 
/*     */     
/*  34 */     stringBuilder.append(", ");
/*     */     
/*     */     try {
/*  37 */       int i = paramInt1 >> 4;
/*  38 */       int j = paramInt3 >> 4;
/*  39 */       int k = paramInt1 & 0xF;
/*  40 */       int m = paramInt2 >> 4;
/*  41 */       int n = paramInt3 & 0xF;
/*  42 */       int i1 = i << 4;
/*  43 */       int i2 = j << 4;
/*  44 */       int i3 = (i + 1 << 4) - 1;
/*  45 */       int i4 = (j + 1 << 4) - 1;
/*  46 */       stringBuilder.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(k), Integer.valueOf(m), Integer.valueOf(n), Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4) }));
/*  47 */     } catch (Throwable throwable) {
/*  48 */       stringBuilder.append("(Error finding chunk loc)");
/*     */     } 
/*     */     
/*  51 */     stringBuilder.append(", ");
/*     */     
/*     */     try {
/*  54 */       int i = paramInt1 >> 9;
/*  55 */       int j = paramInt3 >> 9;
/*  56 */       int k = i << 5;
/*  57 */       int m = j << 5;
/*  58 */       int n = (i + 1 << 5) - 1;
/*  59 */       int i1 = (j + 1 << 5) - 1;
/*  60 */       int i2 = i << 9;
/*  61 */       int i3 = j << 9;
/*  62 */       int i4 = (i + 1 << 9) - 1;
/*  63 */       int i5 = (j + 1 << 9) - 1;
/*  64 */       stringBuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(m), Integer.valueOf(n), Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5) }));
/*  65 */     } catch (Throwable throwable) {
/*  66 */       stringBuilder.append("(Error finding world loc)");
/*     */     } 
/*     */     
/*  69 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public void a(String paramString, Callable paramCallable) {
/*     */     try {
/*  74 */       a(paramString, paramCallable.call());
/*  75 */     } catch (Throwable throwable) {
/*  76 */       a(paramString, throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(String paramString, Object paramObject) {
/*  81 */     this.c.add(new CrashReportDetail(paramString, paramObject));
/*     */   }
/*     */   
/*     */   public void a(String paramString, Throwable paramThrowable) {
/*  85 */     a(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public int a(int paramInt) {
/*  89 */     StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
/*     */ 
/*     */     
/*  92 */     if (arrayOfStackTraceElement.length <= 0) {
/*  93 */       return 0;
/*     */     }
/*     */     
/*  96 */     this.d = new StackTraceElement[arrayOfStackTraceElement.length - 3 - paramInt];
/*  97 */     System.arraycopy(arrayOfStackTraceElement, 3 + paramInt, this.d, 0, this.d.length);
/*  98 */     return this.d.length;
/*     */   }
/*     */   
/*     */   public boolean a(StackTraceElement paramStackTraceElement1, StackTraceElement paramStackTraceElement2) {
/* 102 */     if (this.d.length == 0 || paramStackTraceElement1 == null) return false;
/*     */     
/* 104 */     StackTraceElement stackTraceElement = this.d[0];
/*     */ 
/*     */     
/* 107 */     if (stackTraceElement.isNativeMethod() != paramStackTraceElement1.isNativeMethod() || !stackTraceElement.getClassName().equals(paramStackTraceElement1.getClassName()) || !stackTraceElement.getFileName().equals(paramStackTraceElement1.getFileName()) || !stackTraceElement.getMethodName().equals(paramStackTraceElement1.getMethodName()))
/*     */     {
/*     */ 
/*     */       
/* 111 */       return false;
/*     */     }
/* 113 */     if (((paramStackTraceElement2 != null) ? true : false) != ((this.d.length > 1) ? true : false)) return false; 
/* 114 */     if (paramStackTraceElement2 != null && !this.d[1].equals(paramStackTraceElement2)) return false;
/*     */     
/* 116 */     this.d[0] = paramStackTraceElement1;
/*     */     
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   public void b(int paramInt) {
/* 122 */     StackTraceElement[] arrayOfStackTraceElement = new StackTraceElement[this.d.length - paramInt];
/* 123 */     System.arraycopy(this.d, 0, arrayOfStackTraceElement, 0, arrayOfStackTraceElement.length);
/* 124 */     this.d = arrayOfStackTraceElement;
/*     */   }
/*     */   
/*     */   public void a(StringBuilder paramStringBuilder) {
/* 128 */     paramStringBuilder.append("-- ").append(this.b).append(" --\n");
/* 129 */     paramStringBuilder.append("Details:");
/*     */     
/* 131 */     for (CrashReportDetail crashReportDetail : this.c) {
/* 132 */       paramStringBuilder.append("\n\t");
/* 133 */       paramStringBuilder.append(crashReportDetail.a());
/* 134 */       paramStringBuilder.append(": ");
/* 135 */       paramStringBuilder.append(crashReportDetail.b());
/*     */     } 
/*     */     
/* 138 */     if (this.d != null && this.d.length > 0) {
/* 139 */       paramStringBuilder.append("\nStacktrace:");
/*     */       
/* 141 */       for (StackTraceElement stackTraceElement : this.d) {
/* 142 */         paramStringBuilder.append("\n\tat ");
/* 143 */         paramStringBuilder.append(stackTraceElement.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public StackTraceElement[] a() {
/* 149 */     return this.d;
/*     */   }
/*     */   
/*     */   public static void a(CrashReportSystemDetails paramCrashReportSystemDetails, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 153 */     int i = Block.getId(paramBlock);
/* 154 */     paramCrashReportSystemDetails.a("Block type", new CrashReportBlockType(i, paramBlock));
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
/* 165 */     paramCrashReportSystemDetails.a("Block data value", new CrashReportBlockDataValue(paramInt4));
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
/* 176 */     paramCrashReportSystemDetails.a("Block location", new CrashReportBlockLocation(paramInt1, paramInt2, paramInt3));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CrashReportSystemDetails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */