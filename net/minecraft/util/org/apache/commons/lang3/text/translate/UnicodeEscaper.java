/*     */ package net.minecraft.util.org.apache.commons.lang3.text.translate;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
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
/*     */ public class UnicodeEscaper
/*     */   extends CodePointTranslator
/*     */ {
/*     */   private final int below;
/*     */   private final int above;
/*     */   private final boolean between;
/*     */   
/*     */   public UnicodeEscaper() {
/*  38 */     this(0, 2147483647, true);
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
/*     */   protected UnicodeEscaper(int below, int above, boolean between) {
/*  52 */     this.below = below;
/*  53 */     this.above = above;
/*  54 */     this.between = between;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnicodeEscaper below(int codepoint) {
/*  64 */     return outsideOf(codepoint, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnicodeEscaper above(int codepoint) {
/*  74 */     return outsideOf(0, codepoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnicodeEscaper outsideOf(int codepointLow, int codepointHigh) {
/*  85 */     return new UnicodeEscaper(codepointLow, codepointHigh, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnicodeEscaper between(int codepointLow, int codepointHigh) {
/*  96 */     return new UnicodeEscaper(codepointLow, codepointHigh, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean translate(int codepoint, Writer out) throws IOException {
/* 104 */     if (this.between) {
/* 105 */       if (codepoint < this.below || codepoint > this.above) {
/* 106 */         return false;
/*     */       }
/*     */     }
/* 109 */     else if (codepoint >= this.below && codepoint <= this.above) {
/* 110 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 115 */     if (codepoint > 65535) {
/* 116 */       out.write(toUtf16Escape(codepoint));
/* 117 */     } else if (codepoint > 4095) {
/* 118 */       out.write("\\u" + hex(codepoint));
/* 119 */     } else if (codepoint > 255) {
/* 120 */       out.write("\\u0" + hex(codepoint));
/* 121 */     } else if (codepoint > 15) {
/* 122 */       out.write("\\u00" + hex(codepoint));
/*     */     } else {
/* 124 */       out.write("\\u000" + hex(codepoint));
/*     */     } 
/* 126 */     return true;
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
/*     */   protected String toUtf16Escape(int codepoint) {
/* 139 */     return "\\u" + hex(codepoint);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\text\translate\UnicodeEscaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */