/*    */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.persistence.AttributeConverter;
/*    */ import javax.persistence.Converter;
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
/*    */ @Converter(autoApply = false)
/*    */ public class ContextMapAttributeConverter
/*    */   implements AttributeConverter<Map<String, String>, String>
/*    */ {
/*    */   public String convertToDatabaseColumn(Map<String, String> contextMap) {
/* 34 */     if (contextMap == null) {
/* 35 */       return null;
/*    */     }
/*    */     
/* 38 */     return contextMap.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, String> convertToEntityAttribute(String s) {
/* 43 */     throw new UnsupportedOperationException("Log events can only be persisted, not extracted.");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\ContextMapAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */