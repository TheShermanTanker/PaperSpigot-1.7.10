/*     */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
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
/*     */ public class FileNameCompleter
/*     */   implements Completer
/*     */ {
/*     */   private static final boolean OS_IS_WINDOWS;
/*     */   
/*     */   static {
/*  43 */     String os = Configuration.getOsName();
/*  44 */     OS_IS_WINDOWS = os.contains("windows");
/*     */   }
/*     */   
/*     */   public int complete(String buffer, int cursor, List<CharSequence> candidates) {
/*     */     File dir;
/*  49 */     assert candidates != null;
/*     */     
/*  51 */     if (buffer == null) {
/*  52 */       buffer = "";
/*     */     }
/*     */     
/*  55 */     if (OS_IS_WINDOWS) {
/*  56 */       buffer = buffer.replace('/', '\\');
/*     */     }
/*     */     
/*  59 */     String translated = buffer;
/*     */     
/*  61 */     File homeDir = getUserHome();
/*     */ 
/*     */     
/*  64 */     if (translated.startsWith("~" + separator())) {
/*  65 */       translated = homeDir.getPath() + translated.substring(1);
/*     */     }
/*  67 */     else if (translated.startsWith("~")) {
/*  68 */       translated = homeDir.getParentFile().getAbsolutePath();
/*     */     }
/*  70 */     else if (!translated.startsWith(separator())) {
/*  71 */       String cwd = getUserDir().getAbsolutePath();
/*  72 */       translated = cwd + separator() + translated;
/*     */     } 
/*     */     
/*  75 */     File file = new File(translated);
/*     */ 
/*     */     
/*  78 */     if (translated.endsWith(separator())) {
/*  79 */       dir = file;
/*     */     } else {
/*     */       
/*  82 */       dir = file.getParentFile();
/*     */     } 
/*     */     
/*  85 */     File[] entries = (dir == null) ? new File[0] : dir.listFiles();
/*     */     
/*  87 */     return matchFiles(buffer, translated, entries, candidates);
/*     */   }
/*     */   
/*     */   protected String separator() {
/*  91 */     return File.separator;
/*     */   }
/*     */   
/*     */   protected File getUserHome() {
/*  95 */     return Configuration.getUserHome();
/*     */   }
/*     */   
/*     */   protected File getUserDir() {
/*  99 */     return new File(".");
/*     */   }
/*     */   
/*     */   protected int matchFiles(String buffer, String translated, File[] files, List<CharSequence> candidates) {
/* 103 */     if (files == null) {
/* 104 */       return -1;
/*     */     }
/*     */     
/* 107 */     int matches = 0;
/*     */ 
/*     */     
/* 110 */     for (File file : files) {
/* 111 */       if (file.getAbsolutePath().startsWith(translated)) {
/* 112 */         matches++;
/*     */       }
/*     */     } 
/* 115 */     for (File file : files) {
/* 116 */       if (file.getAbsolutePath().startsWith(translated)) {
/* 117 */         CharSequence name = file.getName() + ((matches == 1 && file.isDirectory()) ? separator() : " ");
/* 118 */         candidates.add(render(file, name).toString());
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     int index = buffer.lastIndexOf(separator());
/*     */     
/* 124 */     return index + separator().length();
/*     */   }
/*     */   
/*     */   protected CharSequence render(File file, CharSequence name) {
/* 128 */     assert file != null;
/* 129 */     assert name != null;
/*     */     
/* 131 */     return name;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\FileNameCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */