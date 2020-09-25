/*     */ package net.minecraft.util.org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import net.minecraft.util.org.apache.commons.lang3.text.translate.AggregateTranslator;
/*     */ import net.minecraft.util.org.apache.commons.lang3.text.translate.CharSequenceTranslator;
/*     */ import net.minecraft.util.org.apache.commons.lang3.text.translate.EntityArrays;
/*     */ import net.minecraft.util.org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;
/*     */ import net.minecraft.util.org.apache.commons.lang3.text.translate.LookupTranslator;
/*     */ import net.minecraft.util.org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
/*     */ import net.minecraft.util.org.apache.commons.lang3.text.translate.OctalUnescaper;
/*     */ import net.minecraft.util.org.apache.commons.lang3.text.translate.UnicodeUnescaper;
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
/*     */ public class StringEscapeUtils
/*     */ {
/*  52 */   public static final CharSequenceTranslator ESCAPE_JAVA = (new LookupTranslator((CharSequence[][])new String[][] { { "\"", "\\\"" }, { "\\", "\\\\" } })).with(new CharSequenceTranslator[] { (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.JAVA_CTRL_CHARS_ESCAPE()) }).with(new CharSequenceTranslator[] { (CharSequenceTranslator)JavaUnicodeEscaper.outsideOf(32, 127) });
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
/*  72 */   public static final CharSequenceTranslator ESCAPE_ECMASCRIPT = (CharSequenceTranslator)new AggregateTranslator(new CharSequenceTranslator[] { (CharSequenceTranslator)new LookupTranslator((CharSequence[][])new String[][] { { "'", "\\'" }, { "\"", "\\\"" }, { "\\", "\\\\" }, { "/", "\\/" } }), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.JAVA_CTRL_CHARS_ESCAPE()), (CharSequenceTranslator)JavaUnicodeEscaper.outsideOf(32, 127) });
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
/*  94 */   public static final CharSequenceTranslator ESCAPE_JSON = (CharSequenceTranslator)new AggregateTranslator(new CharSequenceTranslator[] { (CharSequenceTranslator)new LookupTranslator((CharSequence[][])new String[][] { { "\"", "\\\"" }, { "\\", "\\\\" }, { "/", "\\/" } }), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.JAVA_CTRL_CHARS_ESCAPE()), (CharSequenceTranslator)JavaUnicodeEscaper.outsideOf(32, 127) });
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
/* 115 */   public static final CharSequenceTranslator ESCAPE_XML = (CharSequenceTranslator)new AggregateTranslator(new CharSequenceTranslator[] { (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.BASIC_ESCAPE()), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.APOS_ESCAPE()) });
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
/* 130 */   public static final CharSequenceTranslator ESCAPE_HTML3 = (CharSequenceTranslator)new AggregateTranslator(new CharSequenceTranslator[] { (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.BASIC_ESCAPE()), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.ISO8859_1_ESCAPE()) });
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
/* 145 */   public static final CharSequenceTranslator ESCAPE_HTML4 = (CharSequenceTranslator)new AggregateTranslator(new CharSequenceTranslator[] { (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.BASIC_ESCAPE()), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.ISO8859_1_ESCAPE()), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.HTML40_EXTENDED_ESCAPE()) });
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
/* 161 */   public static final CharSequenceTranslator ESCAPE_CSV = new CsvEscaper();
/*     */ 
/*     */   
/*     */   static class CsvEscaper
/*     */     extends CharSequenceTranslator
/*     */   {
/*     */     private static final char CSV_DELIMITER = ',';
/*     */     
/*     */     private static final char CSV_QUOTE = '"';
/* 170 */     private static final String CSV_QUOTE_STR = String.valueOf('"');
/* 171 */     private static final char[] CSV_SEARCH_CHARS = new char[] { ',', '"', '\r', '\n' };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int translate(CharSequence input, int index, Writer out) throws IOException {
/* 177 */       if (index != 0) {
/* 178 */         throw new IllegalStateException("CsvEscaper should never reach the [1] index");
/*     */       }
/*     */       
/* 181 */       if (StringUtils.containsNone(input.toString(), CSV_SEARCH_CHARS)) {
/* 182 */         out.write(input.toString());
/*     */       } else {
/* 184 */         out.write(34);
/* 185 */         out.write(StringUtils.replace(input.toString(), CSV_QUOTE_STR, CSV_QUOTE_STR + CSV_QUOTE_STR));
/* 186 */         out.write(34);
/*     */       } 
/* 188 */       return input.length();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public static final CharSequenceTranslator UNESCAPE_JAVA = (CharSequenceTranslator)new AggregateTranslator(new CharSequenceTranslator[] { (CharSequenceTranslator)new OctalUnescaper(), (CharSequenceTranslator)new UnicodeUnescaper(), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.JAVA_CTRL_CHARS_UNESCAPE()), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])new String[][] { { "\\\\", "\\" }, { "\\\"", "\"" }, { "\\'", "'" }, { "\\", "" } }) });
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
/* 227 */   public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT = UNESCAPE_JAVA;
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
/* 238 */   public static final CharSequenceTranslator UNESCAPE_JSON = UNESCAPE_JAVA;
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
/* 249 */   public static final CharSequenceTranslator UNESCAPE_HTML3 = (CharSequenceTranslator)new AggregateTranslator(new CharSequenceTranslator[] { (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.BASIC_UNESCAPE()), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.ISO8859_1_UNESCAPE()), (CharSequenceTranslator)new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]) });
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
/* 265 */   public static final CharSequenceTranslator UNESCAPE_HTML4 = (CharSequenceTranslator)new AggregateTranslator(new CharSequenceTranslator[] { (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.BASIC_UNESCAPE()), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.ISO8859_1_UNESCAPE()), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.HTML40_EXTENDED_UNESCAPE()), (CharSequenceTranslator)new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]) });
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
/* 282 */   public static final CharSequenceTranslator UNESCAPE_XML = (CharSequenceTranslator)new AggregateTranslator(new CharSequenceTranslator[] { (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.BASIC_UNESCAPE()), (CharSequenceTranslator)new LookupTranslator((CharSequence[][])EntityArrays.APOS_UNESCAPE()), (CharSequenceTranslator)new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]) });
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
/* 298 */   public static final CharSequenceTranslator UNESCAPE_CSV = new CsvUnescaper();
/*     */   
/*     */   static class CsvUnescaper
/*     */     extends CharSequenceTranslator {
/*     */     private static final char CSV_DELIMITER = ',';
/*     */     private static final char CSV_QUOTE = '"';
/* 304 */     private static final String CSV_QUOTE_STR = String.valueOf('"');
/* 305 */     private static final char[] CSV_SEARCH_CHARS = new char[] { ',', '"', '\r', '\n' };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int translate(CharSequence input, int index, Writer out) throws IOException {
/* 311 */       if (index != 0) {
/* 312 */         throw new IllegalStateException("CsvUnescaper should never reach the [1] index");
/*     */       }
/*     */       
/* 315 */       if (input.charAt(0) != '"' || input.charAt(input.length() - 1) != '"') {
/* 316 */         out.write(input.toString());
/* 317 */         return input.length();
/*     */       } 
/*     */ 
/*     */       
/* 321 */       String quoteless = input.subSequence(1, input.length() - 1).toString();
/*     */       
/* 323 */       if (StringUtils.containsAny(quoteless, CSV_SEARCH_CHARS)) {
/*     */         
/* 325 */         out.write(StringUtils.replace(quoteless, CSV_QUOTE_STR + CSV_QUOTE_STR, CSV_QUOTE_STR));
/*     */       } else {
/* 327 */         out.write(input.toString());
/*     */       } 
/* 329 */       return input.length();
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
/*     */   public static final String escapeJava(String input) {
/* 373 */     return ESCAPE_JAVA.translate(input);
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
/*     */   public static final String escapeEcmaScript(String input) {
/* 402 */     return ESCAPE_ECMASCRIPT.translate(input);
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
/*     */   public static final String escapeJson(String input) {
/* 431 */     return ESCAPE_JSON.translate(input);
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
/*     */   public static final String unescapeJava(String input) {
/* 444 */     return UNESCAPE_JAVA.translate(input);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String unescapeEcmaScript(String input) {
/* 461 */     return UNESCAPE_ECMASCRIPT.translate(input);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String unescapeJson(String input) {
/* 478 */     return UNESCAPE_JSON.translate(input);
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
/*     */   public static final String escapeHtml4(String input) {
/* 511 */     return ESCAPE_HTML4.translate(input);
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
/*     */   public static final String escapeHtml3(String input) {
/* 524 */     return ESCAPE_HTML3.translate(input);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String unescapeHtml4(String input) {
/* 546 */     return UNESCAPE_HTML4.translate(input);
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
/*     */   public static final String unescapeHtml3(String input) {
/* 560 */     return UNESCAPE_HTML3.translate(input);
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
/*     */   public static final String escapeXml(String input) {
/* 584 */     return ESCAPE_XML.translate(input);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String unescapeXml(String input) {
/* 605 */     return UNESCAPE_XML.translate(input);
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
/*     */   public static final String escapeCsv(String input) {
/* 634 */     return ESCAPE_CSV.translate(input);
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
/*     */   public static final String unescapeCsv(String input) {
/* 660 */     return UNESCAPE_CSV.translate(input);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\StringEscapeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */