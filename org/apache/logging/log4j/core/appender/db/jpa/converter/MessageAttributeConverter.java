/*    */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*    */ 
/*    */ import javax.persistence.AttributeConverter;
/*    */ import javax.persistence.Converter;
/*    */ import org.apache.logging.log4j.core.helpers.Strings;
/*    */ import org.apache.logging.log4j.message.Message;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ public class MessageAttributeConverter
/*    */   implements AttributeConverter<Message, String>
/*    */ {
/* 32 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
/*    */ 
/*    */   
/*    */   public String convertToDatabaseColumn(Message message) {
/* 36 */     if (message == null) {
/* 37 */       return null;
/*    */     }
/*    */     
/* 40 */     return message.getFormattedMessage();
/*    */   }
/*    */ 
/*    */   
/*    */   public Message convertToEntityAttribute(String s) {
/* 45 */     if (Strings.isEmpty(s)) {
/* 46 */       return null;
/*    */     }
/*    */     
/* 49 */     return LOGGER.getMessageFactory().newMessage(s);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\MessageAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */