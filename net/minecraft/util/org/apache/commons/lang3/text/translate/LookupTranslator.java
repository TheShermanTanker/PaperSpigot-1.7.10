/*    */ package net.minecraft.util.org.apache.commons.lang3.text.translate;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.HashMap;
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
/*    */ public class LookupTranslator
/*    */   extends CharSequenceTranslator
/*    */ {
/* 46 */   private final HashMap<String, CharSequence> lookupMap = new HashMap<String, CharSequence>(); public LookupTranslator(CharSequence[]... lookup) {
/* 47 */     int _shortest = Integer.MAX_VALUE;
/* 48 */     int _longest = 0;
/* 49 */     if (lookup != null) {
/* 50 */       for (CharSequence[] seq : lookup) {
/* 51 */         this.lookupMap.put(seq[0].toString(), seq[1]);
/* 52 */         int sz = seq[0].length();
/* 53 */         if (sz < _shortest) {
/* 54 */           _shortest = sz;
/*    */         }
/* 56 */         if (sz > _longest) {
/* 57 */           _longest = sz;
/*    */         }
/*    */       } 
/*    */     }
/* 61 */     this.shortest = _shortest;
/* 62 */     this.longest = _longest;
/*    */   }
/*    */ 
/*    */   
/*    */   private final int shortest;
/*    */   private final int longest;
/*    */   
/*    */   public int translate(CharSequence input, int index, Writer out) throws IOException {
/* 70 */     int max = this.longest;
/* 71 */     if (index + this.longest > input.length()) {
/* 72 */       max = input.length() - index;
/*    */     }
/*    */     
/* 75 */     for (int i = max; i >= this.shortest; i--) {
/* 76 */       CharSequence subSeq = input.subSequence(index, index + i);
/* 77 */       CharSequence result = this.lookupMap.get(subSeq.toString());
/* 78 */       if (result != null) {
/* 79 */         out.write(result.toString());
/* 80 */         return i;
/*    */       } 
/*    */     } 
/* 83 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\text\translate\LookupTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */