/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EscapeTokenizer
/*     */ {
/*  37 */   private int bracesLevel = 0;
/*     */   
/*     */   private boolean emittingEscapeCode = false;
/*     */   
/*     */   private boolean inComment = false;
/*     */   
/*     */   private boolean inQuotes = false;
/*     */   
/*  45 */   private char lastChar = Character.MIN_VALUE;
/*     */   
/*  47 */   private char lastLastChar = Character.MIN_VALUE;
/*     */   
/*  49 */   private int pos = 0;
/*     */   
/*  51 */   private char quoteChar = Character.MIN_VALUE;
/*     */   
/*     */   private boolean sawVariableUse = false;
/*     */   
/*  55 */   private String source = null;
/*     */   
/*  57 */   private int sourceLength = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EscapeTokenizer(String s) {
/*  69 */     this.source = s;
/*  70 */     this.sourceLength = s.length();
/*  71 */     this.pos = 0;
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
/*     */   public synchronized boolean hasMoreTokens() {
/*  83 */     return (this.pos < this.sourceLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String nextToken() {
/*  92 */     StringBuffer tokenBuf = new StringBuffer();
/*     */     
/*  94 */     if (this.emittingEscapeCode) {
/*  95 */       tokenBuf.append("{");
/*  96 */       this.emittingEscapeCode = false;
/*     */     } 
/*     */     
/*  99 */     for (; this.pos < this.sourceLength; this.pos++) {
/* 100 */       char c = this.source.charAt(this.pos);
/*     */ 
/*     */ 
/*     */       
/* 104 */       if (!this.inQuotes && c == '@') {
/* 105 */         this.sawVariableUse = true;
/*     */       }
/*     */       
/* 108 */       if ((c == '\'' || c == '"') && !this.inComment) {
/* 109 */         if (this.inQuotes && c == this.quoteChar && 
/* 110 */           this.pos + 1 < this.sourceLength && 
/* 111 */           this.source.charAt(this.pos + 1) == this.quoteChar)
/*     */         {
/* 113 */           if (this.lastChar != '\\') {
/* 114 */             tokenBuf.append(this.quoteChar);
/* 115 */             tokenBuf.append(this.quoteChar);
/* 116 */             this.pos++;
/*     */             
/*     */             continue;
/*     */           } 
/*     */         }
/*     */         
/* 122 */         if (this.lastChar != '\\') {
/* 123 */           if (this.inQuotes) {
/* 124 */             if (this.quoteChar == c) {
/* 125 */               this.inQuotes = false;
/*     */             }
/*     */           } else {
/* 128 */             this.inQuotes = true;
/* 129 */             this.quoteChar = c;
/*     */           } 
/* 131 */         } else if (this.lastLastChar == '\\') {
/* 132 */           if (this.inQuotes) {
/* 133 */             if (this.quoteChar == c) {
/* 134 */               this.inQuotes = false;
/*     */             }
/*     */           } else {
/* 137 */             this.inQuotes = true;
/* 138 */             this.quoteChar = c;
/*     */           } 
/*     */         } 
/*     */         
/* 142 */         tokenBuf.append(c);
/* 143 */       } else if (c == '-') {
/* 144 */         if (this.lastChar == '-' && this.lastLastChar != '\\' && !this.inQuotes)
/*     */         {
/* 146 */           this.inComment = true;
/*     */         }
/*     */         
/* 149 */         tokenBuf.append(c);
/* 150 */       } else if (c == '\n' || c == '\r') {
/* 151 */         this.inComment = false;
/*     */         
/* 153 */         tokenBuf.append(c);
/* 154 */       } else if (c == '{') {
/* 155 */         if (this.inQuotes || this.inComment) {
/* 156 */           tokenBuf.append(c);
/*     */         } else {
/* 158 */           this.bracesLevel++;
/*     */           
/* 160 */           if (this.bracesLevel == 1) {
/* 161 */             this.pos++;
/* 162 */             this.emittingEscapeCode = true;
/*     */             
/* 164 */             return tokenBuf.toString();
/*     */           } 
/*     */           
/* 167 */           tokenBuf.append(c);
/*     */         } 
/* 169 */       } else if (c == '}') {
/* 170 */         tokenBuf.append(c);
/*     */         
/* 172 */         if (!this.inQuotes && !this.inComment) {
/* 173 */           this.lastChar = c;
/*     */           
/* 175 */           this.bracesLevel--;
/*     */           
/* 177 */           if (this.bracesLevel == 0) {
/* 178 */             this.pos++;
/*     */             
/* 180 */             return tokenBuf.toString();
/*     */           } 
/*     */         } 
/*     */       } else {
/* 184 */         tokenBuf.append(c);
/*     */       } 
/*     */       
/* 187 */       this.lastLastChar = this.lastChar;
/* 188 */       this.lastChar = c;
/*     */       continue;
/*     */     } 
/* 191 */     return tokenBuf.toString();
/*     */   }
/*     */   
/*     */   boolean sawVariableUse() {
/* 195 */     return this.sawVariableUse;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\EscapeTokenizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */