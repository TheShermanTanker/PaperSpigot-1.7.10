/*     */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
/*     */ import org.bukkit.craftbukkit.libs.jline.console.CursorBuffer;
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
/*     */ public class CandidateListCompletionHandler
/*     */   implements CompletionHandler
/*     */ {
/*     */   public boolean complete(ConsoleReader reader, List<CharSequence> candidates, int pos) throws IOException {
/*  40 */     CursorBuffer buf = reader.getCursorBuffer();
/*     */ 
/*     */     
/*  43 */     if (candidates.size() == 1) {
/*  44 */       CharSequence value = candidates.get(0);
/*     */ 
/*     */       
/*  47 */       if (value.equals(buf.toString())) {
/*  48 */         return false;
/*     */       }
/*     */       
/*  51 */       setBuffer(reader, value, pos);
/*     */       
/*  53 */       return true;
/*     */     } 
/*  55 */     if (candidates.size() > 1) {
/*  56 */       String value = getUnambiguousCompletions(candidates);
/*  57 */       setBuffer(reader, value, pos);
/*     */     } 
/*     */     
/*  60 */     printCandidates(reader, candidates);
/*     */ 
/*     */     
/*  63 */     reader.drawLine();
/*     */     
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setBuffer(ConsoleReader reader, CharSequence value, int offset) throws IOException {
/*  71 */     while ((reader.getCursorBuffer()).cursor > offset && reader.backspace());
/*     */ 
/*     */ 
/*     */     
/*  75 */     reader.putString(value);
/*  76 */     reader.setCursorPosition(offset + value.length());
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
/*     */   public static void printCandidates(ConsoleReader reader, Collection<CharSequence> candidates) throws IOException {
/*  88 */     Set<CharSequence> distinct = new HashSet<CharSequence>(candidates);
/*     */     
/*  90 */     if (distinct.size() > reader.getAutoprintThreshold()) {
/*     */       
/*  92 */       reader.print(Messages.DISPLAY_CANDIDATES.format(new Object[] { Integer.valueOf(candidates.size()) }));
/*  93 */       reader.flush();
/*     */ 
/*     */ 
/*     */       
/*  97 */       String noOpt = Messages.DISPLAY_CANDIDATES_NO.format(new Object[0]);
/*  98 */       String yesOpt = Messages.DISPLAY_CANDIDATES_YES.format(new Object[0]);
/*  99 */       char[] allowed = { yesOpt.charAt(0), noOpt.charAt(0) };
/*     */       int c;
/* 101 */       while ((c = reader.readCharacter(allowed)) != -1) {
/* 102 */         String tmp = new String(new char[] { (char)c });
/*     */         
/* 104 */         if (noOpt.startsWith(tmp)) {
/* 105 */           reader.println();
/*     */           return;
/*     */         } 
/* 108 */         if (yesOpt.startsWith(tmp)) {
/*     */           break;
/*     */         }
/*     */         
/* 112 */         reader.beep();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 118 */     if (distinct.size() != candidates.size()) {
/* 119 */       Collection<CharSequence> copy = new ArrayList<CharSequence>();
/*     */       
/* 121 */       for (CharSequence next : candidates) {
/* 122 */         if (!copy.contains(next)) {
/* 123 */           copy.add(next);
/*     */         }
/*     */       } 
/*     */       
/* 127 */       candidates = copy;
/*     */     } 
/*     */     
/* 130 */     reader.println();
/* 131 */     reader.printColumns(candidates);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getUnambiguousCompletions(List<CharSequence> candidates) {
/* 140 */     if (candidates == null || candidates.isEmpty()) {
/* 141 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 145 */     String[] strings = candidates.<String>toArray(new String[candidates.size()]);
/*     */     
/* 147 */     String first = strings[0];
/* 148 */     StringBuilder candidate = new StringBuilder();
/*     */     
/* 150 */     for (int i = 0; i < first.length() && 
/* 151 */       startsWith(first.substring(0, i + 1), strings); i++) {
/* 152 */       candidate.append(first.charAt(i));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     return candidate.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean startsWith(String starts, String[] candidates) {
/* 166 */     for (String candidate : candidates) {
/* 167 */       if (!candidate.startsWith(starts)) {
/* 168 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 172 */     return true;
/*     */   }
/*     */   
/*     */   private enum Messages
/*     */   {
/* 177 */     DISPLAY_CANDIDATES,
/* 178 */     DISPLAY_CANDIDATES_YES,
/* 179 */     DISPLAY_CANDIDATES_NO;
/*     */ 
/*     */ 
/*     */     
/* 183 */     private static final ResourceBundle bundle = ResourceBundle.getBundle(CandidateListCompletionHandler.class.getName(), Locale.getDefault());
/*     */ 
/*     */     
/*     */     public String format(Object... args) {
/* 187 */       if (bundle == null) {
/* 188 */         return "";
/*     */       }
/* 190 */       return String.format(bundle.getString(name()), args);
/*     */     }
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\CandidateListCompletionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */