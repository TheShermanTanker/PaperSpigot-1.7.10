/*     */ package com.avaje.ebeaninternal.server.querydefn;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleTextParser
/*     */ {
/*     */   private final String oql;
/*     */   private final char[] chars;
/*     */   private final int eof;
/*     */   private int pos;
/*     */   private String word;
/*     */   private String lowerWord;
/*     */   private int openParenthesisCount;
/*     */   
/*     */   public SimpleTextParser(String oql) {
/*  16 */     this.oql = oql;
/*  17 */     this.chars = oql.toCharArray();
/*  18 */     this.eof = oql.length();
/*     */   }
/*     */   
/*     */   public int getPos() {
/*  22 */     return this.pos;
/*     */   }
/*     */   
/*     */   public String getOql() {
/*  26 */     return this.oql;
/*     */   }
/*     */   
/*     */   public String getWord() {
/*  30 */     return this.word;
/*     */   }
/*     */   
/*     */   public String peekNextWord() {
/*  34 */     int origPos = this.pos;
/*  35 */     String nw = nextWordInternal();
/*  36 */     this.pos = origPos;
/*  37 */     return nw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMatch(String lowerMatch, String nextWordMatch) {
/*  45 */     if (isMatch(lowerMatch)) {
/*  46 */       String nw = peekNextWord();
/*  47 */       if (nw != null) {
/*  48 */         nw = nw.toLowerCase();
/*  49 */         return nw.equals(nextWordMatch);
/*     */       } 
/*     */     } 
/*  52 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/*  56 */     return (this.word == null);
/*     */   }
/*     */   
/*     */   public int findWordLower(String lowerMatch, int afterPos) {
/*  60 */     this.pos = afterPos;
/*  61 */     return findWordLower(lowerMatch);
/*     */   }
/*     */   
/*     */   public int findWordLower(String lowerMatch) {
/*     */     while (true) {
/*  66 */       if (nextWord() == null) {
/*  67 */         return -1;
/*     */       }
/*  69 */       if (lowerMatch.equals(this.lowerWord)) {
/*  70 */         return this.pos - this.lowerWord.length();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMatch(String lowerMatch) {
/*  79 */     return lowerMatch.equals(this.lowerWord);
/*     */   }
/*     */   
/*     */   public String nextWord() {
/*  83 */     this.word = nextWordInternal();
/*  84 */     if (this.word != null) {
/*  85 */       this.lowerWord = this.word.toLowerCase();
/*     */     }
/*  87 */     return this.word;
/*     */   }
/*     */   
/*     */   private String nextWordInternal() {
/*  91 */     trimLeadingWhitespace();
/*  92 */     if (this.pos >= this.eof) {
/*  93 */       return null;
/*     */     }
/*  95 */     int start = this.pos;
/*  96 */     if (this.chars[this.pos] == '(') {
/*  97 */       moveToClose();
/*     */     } else {
/*  99 */       moveToEndOfWord();
/*     */     } 
/* 101 */     return this.oql.substring(start, this.pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private void moveToClose() {
/* 106 */     this.pos++;
/* 107 */     this.openParenthesisCount = 0;
/*     */     
/* 109 */     for (; this.pos < this.eof; this.pos++) {
/* 110 */       char c = this.chars[this.pos];
/* 111 */       if (c == '(') {
/*     */         
/* 113 */         this.openParenthesisCount++;
/*     */       }
/* 115 */       else if (c == ')') {
/* 116 */         if (this.openParenthesisCount > 0) {
/*     */           
/* 118 */           this.openParenthesisCount--;
/*     */         } else {
/*     */           
/* 121 */           this.pos++;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void moveToEndOfWord() {
/* 129 */     char c = this.chars[this.pos];
/* 130 */     boolean isOperator = isOperator(c);
/* 131 */     for (; this.pos < this.eof; this.pos++) {
/* 132 */       c = this.chars[this.pos];
/* 133 */       if (isWordTerminator(c, isOperator)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isWordTerminator(char c, boolean isOperator) {
/* 140 */     if (Character.isWhitespace(c)) {
/* 141 */       return true;
/*     */     }
/* 143 */     if (isOperator(c)) {
/* 144 */       return !isOperator;
/*     */     }
/* 146 */     if (c == '(') {
/* 147 */       return true;
/*     */     }
/*     */     
/* 150 */     return isOperator;
/*     */   }
/*     */   
/*     */   private boolean isOperator(char c) {
/* 154 */     switch (c) {
/*     */       case '<':
/* 156 */         return true;
/*     */       case '>':
/* 158 */         return true;
/*     */       case '=':
/* 160 */         return true;
/*     */       case '!':
/* 162 */         return true;
/*     */     } 
/*     */     
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void trimLeadingWhitespace() {
/* 170 */     for (; this.pos < this.eof; this.pos++) {
/* 171 */       char c = this.chars[this.pos];
/* 172 */       if (!Character.isWhitespace(c))
/*     */         break; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\querydefn\SimpleTextParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */