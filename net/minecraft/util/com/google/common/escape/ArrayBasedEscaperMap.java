/*    */ package net.minecraft.util.com.google.common.escape;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.com.google.common.annotations.Beta;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*    */ import net.minecraft.util.com.google.common.annotations.VisibleForTesting;
/*    */ import net.minecraft.util.com.google.common.base.Preconditions;
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
/*    */ @Beta
/*    */ @GwtCompatible
/*    */ public final class ArrayBasedEscaperMap
/*    */ {
/*    */   private final char[][] replacementArray;
/*    */   
/*    */   public static ArrayBasedEscaperMap create(Map<Character, String> replacements) {
/* 56 */     return new ArrayBasedEscaperMap(createReplacementArray(replacements));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ArrayBasedEscaperMap(char[][] replacementArray) {
/* 64 */     this.replacementArray = replacementArray;
/*    */   }
/*    */ 
/*    */   
/*    */   char[][] getReplacementArray() {
/* 69 */     return this.replacementArray;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @VisibleForTesting
/*    */   static char[][] createReplacementArray(Map<Character, String> map) {
/* 77 */     Preconditions.checkNotNull(map);
/* 78 */     if (map.isEmpty()) {
/* 79 */       return EMPTY_REPLACEMENT_ARRAY;
/*    */     }
/* 81 */     char max = ((Character)Collections.<Character>max(map.keySet())).charValue();
/* 82 */     char[][] replacements = new char[max + 1][];
/* 83 */     for (Iterator<Character> i$ = map.keySet().iterator(); i$.hasNext(); ) { char c = ((Character)i$.next()).charValue();
/* 84 */       replacements[c] = ((String)map.get(Character.valueOf(c))).toCharArray(); }
/*    */     
/* 86 */     return replacements;
/*    */   }
/*    */ 
/*    */   
/* 90 */   private static final char[][] EMPTY_REPLACEMENT_ARRAY = new char[0][0];
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\escape\ArrayBasedEscaperMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */