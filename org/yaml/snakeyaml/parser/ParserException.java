/*    */ package org.yaml.snakeyaml.parser;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
/*    */ import org.yaml.snakeyaml.error.MarkedYAMLException;
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
/*    */ public class ParserException
/*    */   extends MarkedYAMLException
/*    */ {
/*    */   private static final long serialVersionUID = -2349253802798398038L;
/*    */   
/*    */   public ParserException(String context, Mark contextMark, String problem, Mark problemMark) {
/* 42 */     super(context, contextMark, problem, problemMark, null, null);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\parser\ParserException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */