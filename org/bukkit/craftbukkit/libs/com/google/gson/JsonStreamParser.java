/*     */ package org.bukkit.craftbukkit.libs.com.google.gson;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.internal.Streams;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonToken;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JsonStreamParser
/*     */   implements Iterator<JsonElement>
/*     */ {
/*     */   private final JsonReader parser;
/*     */   private final Object lock;
/*     */   
/*     */   public JsonStreamParser(String json) {
/*  61 */     this(new StringReader(json));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonStreamParser(Reader reader) {
/*  69 */     this.parser = new JsonReader(reader);
/*  70 */     this.parser.setLenient(true);
/*  71 */     this.lock = new Object();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement next() throws JsonParseException {
/*  82 */     if (!hasNext()) {
/*  83 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     try {
/*  87 */       return Streams.parse(this.parser);
/*  88 */     } catch (StackOverflowError e) {
/*  89 */       throw new JsonParseException("Failed parsing JSON source to Json", e);
/*  90 */     } catch (OutOfMemoryError e) {
/*  91 */       throw new JsonParseException("Failed parsing JSON source to Json", e);
/*  92 */     } catch (JsonParseException e) {
/*  93 */       throw (e.getCause() instanceof java.io.EOFException) ? new NoSuchElementException() : e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/* 103 */     synchronized (this.lock) {
/*     */       
/* 105 */       return (this.parser.peek() != JsonToken.END_DOCUMENT);
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
/*     */   public void remove() {
/* 120 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\JsonStreamParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */