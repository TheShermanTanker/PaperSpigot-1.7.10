/*     */ package com.avaje.ebean.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PathPropertiesParser
/*     */ {
/*     */   private final PathProperties pathProps;
/*     */   private final String source;
/*     */   private final char[] chars;
/*     */   private final int eof;
/*     */   private int pos;
/*     */   private int startPos;
/*     */   private PathProperties.Props currentPathProps;
/*     */   
/*     */   static PathProperties parse(String source) {
/*  49 */     return (new PathPropertiesParser(source)).pathProps;
/*     */   }
/*     */ 
/*     */   
/*     */   private PathPropertiesParser(String src) {
/*  54 */     if (src.startsWith(":")) {
/*  55 */       src = src.substring(1);
/*     */     }
/*  57 */     this.pathProps = new PathProperties();
/*  58 */     this.source = src;
/*  59 */     this.chars = src.toCharArray();
/*  60 */     this.eof = this.chars.length;
/*     */     
/*  62 */     if (this.eof > 0) {
/*  63 */       this.currentPathProps = this.pathProps.getRootProperties();
/*  64 */       parse();
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getPath() {
/*     */     while (true) {
/*  70 */       char c1 = this.chars[this.pos++];
/*  71 */       switch (c1) {
/*     */         case '(':
/*  73 */           return currentWord();
/*     */       } 
/*     */       
/*  76 */       if (this.pos >= this.eof)
/*  77 */         throw new RuntimeException("Hit EOF while reading sectionTitle from " + this.startPos); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void parse() {
/*     */     do {
/*  83 */       String path = getPath();
/*  84 */       pushPath(path);
/*  85 */       parseSection();
/*     */     }
/*  87 */     while (this.pos < this.eof);
/*     */   }
/*     */   
/*     */   private void parseSection() {
/*     */     do {
/*  92 */       char c1 = this.chars[this.pos++];
/*  93 */       switch (c1) {
/*     */         case '(':
/*  95 */           addSubpath();
/*     */           break;
/*     */         case ',':
/*  98 */           addCurrentProperty();
/*     */           break;
/*     */         
/*     */         case ':':
/* 102 */           this.startPos = this.pos;
/*     */           return;
/*     */         
/*     */         case ')':
/* 106 */           addCurrentProperty();
/* 107 */           popSubpath();
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/* 112 */     } while (this.pos < this.eof);
/*     */   }
/*     */   
/*     */   private void addSubpath() {
/* 116 */     pushPath(currentWord());
/*     */   }
/*     */   
/*     */   private void addCurrentProperty() {
/* 120 */     String w = currentWord();
/* 121 */     if (w.length() > 0) {
/* 122 */       this.currentPathProps.addProperty(w);
/*     */     }
/*     */   }
/*     */   
/*     */   private String currentWord() {
/* 127 */     if (this.startPos == this.pos) {
/* 128 */       return "";
/*     */     }
/* 130 */     String currentWord = this.source.substring(this.startPos, this.pos - 1);
/* 131 */     this.startPos = this.pos;
/* 132 */     return currentWord;
/*     */   }
/*     */ 
/*     */   
/*     */   private void pushPath(String title) {
/* 137 */     if (!"".equals(title)) {
/* 138 */       this.currentPathProps = this.currentPathProps.addChild(title);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void popSubpath() {
/* 144 */     this.currentPathProps = this.currentPathProps.getParent();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\PathPropertiesParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */