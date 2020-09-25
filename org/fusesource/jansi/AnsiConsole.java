/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import org.fusesource.jansi.internal.CLibrary;
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
/*     */ public class AnsiConsole
/*     */ {
/*  36 */   public static final PrintStream system_out = System.out;
/*  37 */   public static final PrintStream out = new PrintStream(wrapOutputStream(system_out));
/*     */   
/*  39 */   public static final PrintStream system_err = System.err;
/*  40 */   public static final PrintStream err = new PrintStream(wrapOutputStream(system_err));
/*     */ 
/*     */   
/*     */   private static int installed;
/*     */ 
/*     */ 
/*     */   
/*     */   public static OutputStream wrapOutputStream(OutputStream stream) {
/*  48 */     if (Boolean.getBoolean("jansi.passthrough")) {
/*  49 */       return stream;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  54 */     if (Boolean.getBoolean("jansi.strip")) {
/*  55 */       return new AnsiOutputStream(stream);
/*     */     }
/*     */     
/*  58 */     String os = System.getProperty("os.name");
/*  59 */     if (os.startsWith("Windows")) {
/*     */       
/*     */       try {
/*     */         
/*  63 */         return new WindowsAnsiOutputStream(stream);
/*  64 */       } catch (Throwable ignore) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  70 */         return new AnsiOutputStream(stream);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     try { int rc = CLibrary.isatty(CLibrary.STDOUT_FILENO);
/*  78 */       if (rc == 0) {
/*  79 */         return new AnsiOutputStream(stream);
/*     */       
/*     */       } }
/*     */     
/*  83 */     catch (NoClassDefFoundError ignore) {  }
/*  84 */     catch (UnsatisfiedLinkError ignore) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     return new FilterOutputStream(stream)
/*     */       {
/*     */         public void close() throws IOException {
/*  93 */           write(AnsiOutputStream.REST_CODE);
/*  94 */           flush();
/*  95 */           super.close();
/*     */         }
/*     */       };
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
/*     */   public static PrintStream out() {
/* 109 */     return out;
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
/*     */   public static PrintStream err() {
/* 121 */     return err;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void systemInstall() {
/* 128 */     installed++;
/* 129 */     if (installed == 1) {
/* 130 */       System.setOut(out);
/* 131 */       System.setErr(err);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void systemUninstall() {
/* 141 */     installed--;
/* 142 */     if (installed == 0) {
/* 143 */       System.setOut(system_out);
/* 144 */       System.setErr(system_err);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\AnsiConsole.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */