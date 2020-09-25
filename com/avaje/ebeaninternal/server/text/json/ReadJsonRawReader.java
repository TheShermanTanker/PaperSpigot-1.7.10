/*     */ package com.avaje.ebeaninternal.server.text.json;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonElement;
/*     */ import com.avaje.ebean.text.json.JsonElementArray;
/*     */ import com.avaje.ebean.text.json.JsonElementBoolean;
/*     */ import com.avaje.ebean.text.json.JsonElementNull;
/*     */ import com.avaje.ebean.text.json.JsonElementNumber;
/*     */ import com.avaje.ebean.text.json.JsonElementObject;
/*     */ import com.avaje.ebean.text.json.JsonElementString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReadJsonRawReader
/*     */ {
/*     */   private final ReadJsonInterface ctx;
/*     */   
/*     */   public static JsonElement readJsonElement(ReadJsonInterface ctx) {
/*  35 */     return (new ReadJsonRawReader(ctx)).readJsonElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ReadJsonRawReader(ReadJsonInterface ctx) {
/*  41 */     this.ctx = ctx;
/*     */   }
/*     */   
/*     */   private JsonElement readJsonElement() {
/*  45 */     return readValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private JsonElement readValue() {
/*  50 */     this.ctx.ignoreWhiteSpace();
/*     */     
/*  52 */     char c = this.ctx.nextChar();
/*     */     
/*  54 */     switch (c) {
/*     */       case '{':
/*  56 */         return readObject();
/*     */       
/*     */       case '[':
/*  59 */         return readArray();
/*     */       
/*     */       case '"':
/*  62 */         return readString();
/*     */     } 
/*     */     
/*  65 */     return readUnquoted(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonElement readArray() {
/*  71 */     JsonElementArray a = new JsonElementArray();
/*     */     
/*     */     do {
/*  74 */       JsonElement value = readValue();
/*  75 */       a.add(value);
/*  76 */     } while (this.ctx.readArrayNext());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     return (JsonElement)a;
/*     */   }
/*     */ 
/*     */   
/*     */   private JsonElement readObject() {
/*  86 */     JsonElementObject o = new JsonElementObject();
/*     */ 
/*     */     
/*  89 */     while (this.ctx.readKeyNext()) {
/*     */ 
/*     */ 
/*     */       
/*  93 */       String key = this.ctx.getTokenKey();
/*  94 */       JsonElement value = readValue();
/*     */       
/*  96 */       o.put(key, value);
/*     */       
/*  98 */       if (!this.ctx.readValueNext()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 104 */     return (JsonElement)o;
/*     */   }
/*     */   
/*     */   private JsonElement readString() {
/* 108 */     String s = this.ctx.readQuotedValue();
/* 109 */     return (JsonElement)new JsonElementString(s);
/*     */   }
/*     */   
/*     */   private JsonElement readUnquoted(char c) {
/* 113 */     String s = this.ctx.readUnquotedValue(c);
/* 114 */     if ("null".equals(s)) {
/* 115 */       return (JsonElement)JsonElementNull.NULL;
/*     */     }
/* 117 */     if ("true".equals(s)) {
/* 118 */       return (JsonElement)JsonElementBoolean.TRUE;
/*     */     }
/* 120 */     if ("false".equals(s)) {
/* 121 */       return (JsonElement)JsonElementBoolean.FALSE;
/*     */     }
/*     */     
/* 124 */     return (JsonElement)new JsonElementNumber(s);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonRawReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */