/*     */ package net.minecraft.util.org.apache.commons.lang3.exception;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContextedRuntimeException
/*     */   extends RuntimeException
/*     */   implements ExceptionContext
/*     */ {
/*     */   private static final long serialVersionUID = 20110706L;
/*     */   private final ExceptionContext exceptionContext;
/*     */   
/*     */   public ContextedRuntimeException() {
/*  98 */     this.exceptionContext = new DefaultExceptionContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContextedRuntimeException(String message) {
/* 109 */     super(message);
/* 110 */     this.exceptionContext = new DefaultExceptionContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContextedRuntimeException(Throwable cause) {
/* 121 */     super(cause);
/* 122 */     this.exceptionContext = new DefaultExceptionContext();
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
/*     */   public ContextedRuntimeException(String message, Throwable cause) {
/* 134 */     super(message, cause);
/* 135 */     this.exceptionContext = new DefaultExceptionContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContextedRuntimeException(String message, Throwable cause, ExceptionContext context) {
/* 146 */     super(message, cause);
/* 147 */     if (context == null) {
/* 148 */       context = new DefaultExceptionContext();
/*     */     }
/* 150 */     this.exceptionContext = context;
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
/*     */   public ContextedRuntimeException addContextValue(String label, Object value) {
/* 169 */     this.exceptionContext.addContextValue(label, value);
/* 170 */     return this;
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
/*     */   public ContextedRuntimeException setContextValue(String label, Object value) {
/* 188 */     this.exceptionContext.setContextValue(label, value);
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Object> getContextValues(String label) {
/* 197 */     return this.exceptionContext.getContextValues(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFirstContextValue(String label) {
/* 205 */     return this.exceptionContext.getFirstContextValue(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Pair<String, Object>> getContextEntries() {
/* 213 */     return this.exceptionContext.getContextEntries();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getContextLabels() {
/* 221 */     return this.exceptionContext.getContextLabels();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 232 */     return getFormattedExceptionMessage(super.getMessage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRawMessage() {
/* 243 */     return super.getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedExceptionMessage(String baseMessage) {
/* 251 */     return this.exceptionContext.getFormattedExceptionMessage(baseMessage);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\exception\ContextedRuntimeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */