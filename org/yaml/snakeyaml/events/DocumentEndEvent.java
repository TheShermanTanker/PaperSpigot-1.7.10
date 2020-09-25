/*    */ package org.yaml.snakeyaml.events;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
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
/*    */ public final class DocumentEndEvent
/*    */   extends Event
/*    */ {
/*    */   private final boolean explicit;
/*    */   
/*    */   public DocumentEndEvent(Mark startMark, Mark endMark, boolean explicit) {
/* 30 */     super(startMark, endMark);
/* 31 */     this.explicit = explicit;
/*    */   }
/*    */   
/*    */   public boolean getExplicit() {
/* 35 */     return this.explicit;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean is(Event.ID id) {
/* 40 */     return (Event.ID.DocumentEnd == id);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\events\DocumentEndEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */