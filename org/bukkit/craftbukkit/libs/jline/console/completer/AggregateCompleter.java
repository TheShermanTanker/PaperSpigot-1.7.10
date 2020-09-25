/*     */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class AggregateCompleter
/*     */   implements Completer
/*     */ {
/*  37 */   private final List<Completer> completers = new ArrayList<Completer>();
/*     */ 
/*     */   
/*     */   public AggregateCompleter() {}
/*     */ 
/*     */   
/*     */   public AggregateCompleter(Collection<Completer> completers) {
/*  44 */     assert completers != null;
/*  45 */     this.completers.addAll(completers);
/*     */   }
/*     */   
/*     */   public AggregateCompleter(Completer... completers) {
/*  49 */     this(Arrays.asList(completers));
/*     */   }
/*     */   
/*     */   public Collection<Completer> getCompleters() {
/*  53 */     return this.completers;
/*     */   }
/*     */ 
/*     */   
/*     */   public int complete(String buffer, int cursor, List<CharSequence> candidates) {
/*  58 */     assert candidates != null;
/*     */     
/*  60 */     List<Completion> completions = new ArrayList<Completion>(this.completers.size());
/*     */ 
/*     */     
/*  63 */     int max = -1;
/*  64 */     for (Completer completer : this.completers) {
/*  65 */       Completion completion = new Completion(candidates);
/*  66 */       completion.complete(completer, buffer, cursor);
/*     */ 
/*     */       
/*  69 */       max = Math.max(max, completion.cursor);
/*     */       
/*  71 */       completions.add(completion);
/*     */     } 
/*     */ 
/*     */     
/*  75 */     for (Completion completion : completions) {
/*  76 */       if (completion.cursor == max) {
/*  77 */         candidates.addAll(completion.candidates);
/*     */       }
/*     */     } 
/*     */     
/*  81 */     return max;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  86 */     return getClass().getSimpleName() + "{" + "completers=" + this.completers + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   private class Completion
/*     */   {
/*     */     public final List<CharSequence> candidates;
/*     */     
/*     */     public int cursor;
/*     */ 
/*     */     
/*     */     public Completion(List<CharSequence> candidates) {
/*  98 */       assert candidates != null;
/*  99 */       this.candidates = new LinkedList<CharSequence>(candidates);
/*     */     }
/*     */     
/*     */     public void complete(Completer completer, String buffer, int cursor) {
/* 103 */       assert completer != null;
/*     */       
/* 105 */       this.cursor = completer.complete(buffer, cursor, this.candidates);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\AggregateCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */