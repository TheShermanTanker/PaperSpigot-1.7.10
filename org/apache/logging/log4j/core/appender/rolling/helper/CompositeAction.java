/*    */ package org.apache.logging.log4j.core.appender.rolling.helper;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
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
/*    */ public class CompositeAction
/*    */   extends AbstractAction
/*    */ {
/*    */   private final Action[] actions;
/*    */   private final boolean stopOnError;
/*    */   
/*    */   public CompositeAction(List<Action> actions, boolean stopOnError) {
/* 45 */     this.actions = new Action[actions.size()];
/* 46 */     actions.toArray(this.actions);
/* 47 */     this.stopOnError = stopOnError;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 56 */       execute();
/* 57 */     } catch (IOException ex) {
/* 58 */       LOGGER.warn("Exception during file rollover.", ex);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute() throws IOException {
/* 70 */     if (this.stopOnError) {
/* 71 */       for (Action action : this.actions) {
/* 72 */         if (!action.execute()) {
/* 73 */           return false;
/*    */         }
/*    */       } 
/*    */       
/* 77 */       return true;
/*    */     } 
/* 79 */     boolean status = true;
/* 80 */     IOException exception = null;
/*    */     
/* 82 */     for (Action action : this.actions) {
/*    */       try {
/* 84 */         status &= action.execute();
/* 85 */       } catch (IOException ex) {
/* 86 */         status = false;
/*    */         
/* 88 */         if (exception == null) {
/* 89 */           exception = ex;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 94 */     if (exception != null) {
/* 95 */       throw exception;
/*    */     }
/*    */     
/* 98 */     return status;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\helper\CompositeAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */