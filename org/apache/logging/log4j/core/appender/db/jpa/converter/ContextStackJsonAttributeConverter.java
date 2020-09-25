/*    */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*    */ 
/*    */ import com.fasterxml.jackson.core.type.TypeReference;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import javax.persistence.AttributeConverter;
/*    */ import javax.persistence.Converter;
/*    */ import javax.persistence.PersistenceException;
/*    */ import org.apache.logging.log4j.ThreadContext;
/*    */ import org.apache.logging.log4j.core.helpers.Strings;
/*    */ import org.apache.logging.log4j.spi.DefaultThreadContextStack;
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
/*    */ @Converter(autoApply = false)
/*    */ public class ContextStackJsonAttributeConverter
/*    */   implements AttributeConverter<ThreadContext.ContextStack, String>
/*    */ {
/*    */   public String convertToDatabaseColumn(ThreadContext.ContextStack contextStack) {
/* 45 */     if (contextStack == null) {
/* 46 */       return null;
/*    */     }
/*    */     
/*    */     try {
/* 50 */       return ContextMapJsonAttributeConverter.OBJECT_MAPPER.writeValueAsString(contextStack.asList());
/* 51 */     } catch (IOException e) {
/* 52 */       throw new PersistenceException("Failed to convert stack list to JSON string.", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public ThreadContext.ContextStack convertToEntityAttribute(String s) {
/*    */     List<String> list;
/* 58 */     if (Strings.isEmpty(s)) {
/* 59 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 64 */       list = (List<String>)ContextMapJsonAttributeConverter.OBJECT_MAPPER.readValue(s, new TypeReference<List<String>>() {  });
/* 65 */     } catch (IOException e) {
/* 66 */       throw new PersistenceException("Failed to convert JSON string to list for stack.", e);
/*    */     } 
/*    */     
/* 69 */     DefaultThreadContextStack result = new DefaultThreadContextStack(true);
/* 70 */     result.addAll(list);
/* 71 */     return (ThreadContext.ContextStack)result;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\ContextStackJsonAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */