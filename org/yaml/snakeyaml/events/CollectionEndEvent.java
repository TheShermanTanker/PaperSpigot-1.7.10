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
/*    */ public abstract class CollectionEndEvent
/*    */   extends Event
/*    */ {
/*    */   public CollectionEndEvent(Mark startMark, Mark endMark) {
/* 26 */     super(startMark, endMark);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\events\CollectionEndEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */