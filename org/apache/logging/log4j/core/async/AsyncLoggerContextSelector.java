/*    */ package org.apache.logging.log4j.core.async;
/*    */ 
/*    */ import java.net.URI;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.apache.logging.log4j.core.LoggerContext;
/*    */ import org.apache.logging.log4j.core.selector.ContextSelector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AsyncLoggerContextSelector
/*    */   implements ContextSelector
/*    */ {
/* 32 */   private static final AsyncLoggerContext CONTEXT = new AsyncLoggerContext("AsyncLoggerContext");
/*    */ 
/*    */   
/*    */   public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext) {
/* 36 */     return CONTEXT;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<LoggerContext> getLoggerContexts() {
/* 41 */     List<LoggerContext> list = new ArrayList<LoggerContext>();
/* 42 */     list.add(CONTEXT);
/* 43 */     return Collections.unmodifiableList(list);
/*    */   }
/*    */ 
/*    */   
/*    */   public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
/* 48 */     return CONTEXT;
/*    */   }
/*    */   
/*    */   public void removeContext(LoggerContext context) {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\AsyncLoggerContextSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */