/*     */ package org.bukkit.craftbukkit.libs.jline.console.history;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.Flushable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
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
/*     */ public class FileHistory
/*     */   extends MemoryHistory
/*     */   implements PersistentHistory, Flushable
/*     */ {
/*     */   private final File file;
/*     */   
/*     */   public FileHistory(File file) throws IOException {
/*  40 */     assert file != null;
/*  41 */     this.file = file;
/*  42 */     load(file);
/*     */   }
/*     */   
/*     */   public File getFile() {
/*  46 */     return this.file;
/*     */   }
/*     */   
/*     */   public void load(File file) throws IOException {
/*  50 */     assert file != null;
/*  51 */     if (file.exists()) {
/*  52 */       Log.trace(new Object[] { "Loading history from: ", file });
/*  53 */       load(new FileReader(file));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void load(InputStream input) throws IOException {
/*  58 */     assert input != null;
/*  59 */     load(new InputStreamReader(input));
/*     */   }
/*     */   
/*     */   public void load(Reader reader) throws IOException {
/*  63 */     assert reader != null;
/*  64 */     BufferedReader input = new BufferedReader(reader);
/*     */     
/*     */     String item;
/*  67 */     while ((item = input.readLine()) != null) {
/*  68 */       internalAdd(item);
/*     */     }
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/*  73 */     Log.trace(new Object[] { "Flushing history" });
/*     */     
/*  75 */     if (!this.file.exists()) {
/*  76 */       File dir = this.file.getParentFile();
/*  77 */       if (!dir.exists() && !dir.mkdirs()) {
/*  78 */         Log.warn(new Object[] { "Failed to create directory: ", dir });
/*     */       }
/*  80 */       if (!this.file.createNewFile()) {
/*  81 */         Log.warn(new Object[] { "Failed to create file: ", this.file });
/*     */       }
/*     */     } 
/*     */     
/*  85 */     PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(this.file)));
/*     */     try {
/*  87 */       for (History.Entry entry : this) {
/*  88 */         out.println(entry.value());
/*     */       }
/*     */     } finally {
/*     */       
/*  92 */       out.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void purge() throws IOException {
/*  97 */     Log.trace(new Object[] { "Purging history" });
/*     */     
/*  99 */     clear();
/*     */     
/* 101 */     if (!this.file.delete())
/* 102 */       Log.warn(new Object[] { "Failed to delete history file: ", this.file }); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\history\FileHistory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */