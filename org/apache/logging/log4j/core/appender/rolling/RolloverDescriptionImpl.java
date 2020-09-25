/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.Action;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RolloverDescriptionImpl
/*     */   implements RolloverDescription
/*     */ {
/*     */   private final String activeFileName;
/*     */   private final boolean append;
/*     */   private final Action synchronous;
/*     */   private final Action asynchronous;
/*     */   
/*     */   public RolloverDescriptionImpl(String activeFileName, boolean append, Action synchronous, Action asynchronous) {
/*  58 */     if (activeFileName == null) {
/*  59 */       throw new NullPointerException("activeFileName");
/*     */     }
/*     */     
/*  62 */     this.append = append;
/*  63 */     this.activeFileName = activeFileName;
/*  64 */     this.synchronous = synchronous;
/*  65 */     this.asynchronous = asynchronous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getActiveFileName() {
/*  75 */     return this.activeFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAppend() {
/*  83 */     return this.append;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getSynchronous() {
/*  94 */     return this.synchronous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getAsynchronous() {
/* 105 */     return this.asynchronous;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\RolloverDescriptionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */