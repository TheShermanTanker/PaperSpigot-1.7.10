/*    */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.SortedSet;
/*    */ import java.util.TreeSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringsCompleter
/*    */   implements Completer
/*    */ {
/* 37 */   private final SortedSet<String> strings = new TreeSet<String>();
/*    */ 
/*    */   
/*    */   public StringsCompleter() {}
/*    */ 
/*    */   
/*    */   public StringsCompleter(Collection<String> strings) {
/* 44 */     assert strings != null;
/* 45 */     getStrings().addAll(strings);
/*    */   }
/*    */   
/*    */   public StringsCompleter(String... strings) {
/* 49 */     this(Arrays.asList(strings));
/*    */   }
/*    */   
/*    */   public Collection<String> getStrings() {
/* 53 */     return this.strings;
/*    */   }
/*    */ 
/*    */   
/*    */   public int complete(String buffer, int cursor, List<CharSequence> candidates) {
/* 58 */     assert candidates != null;
/*    */     
/* 60 */     if (buffer == null) {
/* 61 */       candidates.addAll((Collection)this.strings);
/*    */     } else {
/*    */       
/* 64 */       for (String match : this.strings.tailSet(buffer)) {
/* 65 */         if (!match.startsWith(buffer)) {
/*    */           break;
/*    */         }
/*    */         
/* 69 */         candidates.add(match);
/*    */       } 
/*    */     } 
/*    */     
/* 73 */     if (candidates.size() == 1) {
/* 74 */       candidates.set(0, (new StringBuilder()).append(candidates.get(0)).append(" ").toString());
/*    */     }
/*    */     
/* 77 */     return candidates.isEmpty() ? -1 : 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\StringsCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */