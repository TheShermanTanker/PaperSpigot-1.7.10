/*     */ package com.avaje.ebeaninternal.server.text.json;
/*     */ 
/*     */ import com.avaje.ebean.text.TextException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReadBasicJsonContext
/*     */   implements ReadJsonInterface
/*     */ {
/*     */   private final ReadJsonSource src;
/*     */   private char tokenStart;
/*     */   private String tokenKey;
/*     */   
/*     */   public ReadBasicJsonContext(ReadJsonSource src) {
/*  32 */     this.src = src;
/*     */   }
/*     */   
/*     */   public char getToken() {
/*  36 */     return this.tokenStart;
/*     */   }
/*     */   
/*     */   public String getTokenKey() {
/*  40 */     return this.tokenKey;
/*     */   }
/*     */   
/*     */   public boolean isTokenKey() {
/*  44 */     return ('"' == this.tokenStart);
/*     */   }
/*     */   
/*     */   public boolean isTokenObjectEnd() {
/*  48 */     return ('}' == this.tokenStart);
/*     */   }
/*     */   
/*     */   public boolean readObjectBegin() {
/*  52 */     readNextToken();
/*  53 */     if ('{' == this.tokenStart)
/*  54 */       return true; 
/*  55 */     if ('n' == this.tokenStart)
/*  56 */       return false; 
/*  57 */     if (']' == this.tokenStart)
/*     */     {
/*  59 */       return false;
/*     */     }
/*  61 */     throw new RuntimeException("Expected object begin at " + this.src.getErrorHelp());
/*     */   }
/*     */   
/*     */   public boolean readKeyNext() {
/*  65 */     readNextToken();
/*  66 */     if ('"' == this.tokenStart)
/*  67 */       return true; 
/*  68 */     if ('}' == this.tokenStart) {
/*  69 */       return false;
/*     */     }
/*  71 */     throw new RuntimeException("Expected '\"' or '}' at " + this.src.getErrorHelp());
/*     */   }
/*     */   
/*     */   public boolean readValueNext() {
/*  75 */     readNextToken();
/*  76 */     if (',' == this.tokenStart)
/*  77 */       return true; 
/*  78 */     if ('}' == this.tokenStart) {
/*  79 */       return false;
/*     */     }
/*  81 */     throw new RuntimeException("Expected ',' or '}' at " + this.src.getErrorHelp() + " but got " + this.tokenStart);
/*     */   }
/*     */   
/*     */   public boolean readArrayBegin() {
/*  85 */     readNextToken();
/*  86 */     if ('[' == this.tokenStart)
/*  87 */       return true; 
/*  88 */     if ('n' == this.tokenStart) {
/*  89 */       return false;
/*     */     }
/*  91 */     throw new RuntimeException("Expected array begin at " + this.src.getErrorHelp());
/*     */   }
/*     */   
/*     */   public boolean readArrayNext() {
/*  95 */     readNextToken();
/*  96 */     if (',' == this.tokenStart) {
/*  97 */       return true;
/*     */     }
/*  99 */     if (']' == this.tokenStart) {
/* 100 */       return false;
/*     */     }
/* 102 */     throw new RuntimeException("Expected ',' or ']' at " + this.src.getErrorHelp());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readNextToken() {
/* 107 */     ignoreWhiteSpace();
/*     */     
/* 109 */     this.tokenStart = this.src.nextChar("EOF finding next token");
/* 110 */     switch (this.tokenStart) {
/*     */       case '"':
/* 112 */         internalReadKey();
/*     */       case '{':
/*     */       case '}':
/*     */       case '[':
/*     */       case ']':
/*     */       case ',':
/*     */       case ':':
/*     */         return;
/*     */       case 'n':
/* 121 */         internalReadNull();
/*     */     } 
/*     */ 
/*     */     
/* 125 */     throw new RuntimeException("Unexpected tokenStart[" + this.tokenStart + "] " + this.src.getErrorHelp());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String readQuotedValue() {
/* 132 */     boolean escape = false;
/* 133 */     StringBuilder sb = new StringBuilder();
/*     */     
/*     */     while (true) {
/* 136 */       char ch = this.src.nextChar("EOF reading quoted value");
/* 137 */       if (escape) {
/*     */         
/* 139 */         escape = false;
/* 140 */         switch (ch) {
/*     */           case 'n':
/* 142 */             sb.append('\n');
/*     */             continue;
/*     */           case 'r':
/* 145 */             sb.append('\r');
/*     */             continue;
/*     */           case 't':
/* 148 */             sb.append('\t');
/*     */             continue;
/*     */           case 'f':
/* 151 */             sb.append('\f');
/*     */             continue;
/*     */           case 'b':
/* 154 */             sb.append('\b');
/*     */             continue;
/*     */           case '"':
/* 157 */             sb.append('"');
/*     */             continue;
/*     */         } 
/*     */         
/* 161 */         sb.append('\\');
/* 162 */         sb.append(ch);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 167 */       switch (ch) {
/*     */         
/*     */         case '\\':
/* 170 */           escape = true;
/*     */           continue;
/*     */         case '"':
/* 173 */           return sb.toString();
/*     */       } 
/*     */       
/* 176 */       sb.append(ch);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String readUnquotedValue(char c) {
/* 183 */     String v = readUnquotedValueRaw(c);
/* 184 */     if ("null".equals(v)) {
/* 185 */       return null;
/*     */     }
/* 187 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String readUnquotedValueRaw(char c) {
/* 193 */     StringBuilder sb = new StringBuilder();
/* 194 */     sb.append(c);
/*     */     
/*     */     while (true) {
/* 197 */       this.tokenStart = this.src.nextChar("EOF reading unquoted value");
/* 198 */       switch (this.tokenStart) {
/*     */         case ',':
/* 200 */           this.src.back();
/* 201 */           return sb.toString();
/*     */         
/*     */         case '}':
/* 204 */           this.src.back();
/* 205 */           return sb.toString();
/*     */         
/*     */         case ' ':
/* 208 */           return sb.toString();
/*     */         
/*     */         case '\t':
/* 211 */           return sb.toString();
/*     */         
/*     */         case '\r':
/* 214 */           return sb.toString();
/*     */         
/*     */         case '\n':
/* 217 */           return sb.toString();
/*     */       } 
/*     */       
/* 220 */       sb.append(this.tokenStart);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void internalReadNull() {
/* 229 */     StringBuilder sb = new StringBuilder(4);
/* 230 */     sb.append(this.tokenStart);
/* 231 */     for (int i = 0; i < 3; i++) {
/* 232 */       char c = this.src.nextChar("EOF reading null ");
/* 233 */       sb.append(c);
/*     */     } 
/* 235 */     if (!"null".equals(sb.toString())) {
/* 236 */       throw new TextException("Expected 'null' but got " + sb.toString() + " " + this.src.getErrorHelp());
/*     */     }
/*     */   }
/*     */   
/*     */   private void internalReadKey() {
/* 241 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 243 */       char c1 = this.src.nextChar("EOF reading key");
/* 244 */       if ('"' == c1) {
/* 245 */         this.tokenKey = sb.toString();
/*     */         break;
/*     */       } 
/* 248 */       sb.append(c1);
/*     */     } 
/*     */ 
/*     */     
/* 252 */     ignoreWhiteSpace();
/*     */     
/* 254 */     char c = this.src.nextChar("EOF reading ':'");
/* 255 */     if (':' != c) {
/* 256 */       throw new TextException("Expected to find colon after key at " + (this.src.pos() - 1) + " but found [" + c + "]" + this.src.getErrorHelp());
/*     */     }
/*     */   }
/*     */   
/*     */   public void ignoreWhiteSpace() {
/* 261 */     this.src.ignoreWhiteSpace();
/*     */   }
/*     */   
/*     */   public char nextChar() {
/* 265 */     this.tokenStart = this.src.nextChar("EOF getting nextChar for raw json");
/* 266 */     return this.tokenStart;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\ReadBasicJsonContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */