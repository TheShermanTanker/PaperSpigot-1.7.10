/*     */ package net.minecraft.util.org.apache.commons.lang3.text.translate;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.Locale;
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
/*     */ public abstract class CharSequenceTranslator
/*     */ {
/*     */   public abstract int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter) throws IOException;
/*     */   
/*     */   public final String translate(CharSequence input) {
/*  54 */     if (input == null) {
/*  55 */       return null;
/*     */     }
/*     */     try {
/*  58 */       StringWriter writer = new StringWriter(input.length() * 2);
/*  59 */       translate(input, writer);
/*  60 */       return writer.toString();
/*  61 */     } catch (IOException ioe) {
/*     */       
/*  63 */       throw new RuntimeException(ioe);
/*     */     } 
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
/*     */   public final void translate(CharSequence input, Writer out) throws IOException {
/*  76 */     if (out == null) {
/*  77 */       throw new IllegalArgumentException("The Writer must not be null");
/*     */     }
/*  79 */     if (input == null) {
/*     */       return;
/*     */     }
/*  82 */     int pos = 0;
/*  83 */     int len = input.length();
/*  84 */     while (pos < len) {
/*  85 */       int consumed = translate(input, pos, out);
/*  86 */       if (consumed == 0) {
/*  87 */         char[] c = Character.toChars(Character.codePointAt(input, pos));
/*  88 */         out.write(c);
/*  89 */         pos += c.length;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  94 */       for (int pt = 0; pt < consumed; pt++) {
/*  95 */         pos += Character.charCount(Character.codePointAt(input, pt));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CharSequenceTranslator with(CharSequenceTranslator... translators) {
/* 108 */     CharSequenceTranslator[] newArray = new CharSequenceTranslator[translators.length + 1];
/* 109 */     newArray[0] = this;
/* 110 */     System.arraycopy(translators, 0, newArray, 1, translators.length);
/* 111 */     return new AggregateTranslator(newArray);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String hex(int codepoint) {
/* 122 */     return Integer.toHexString(codepoint).toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\text\translate\CharSequenceTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */