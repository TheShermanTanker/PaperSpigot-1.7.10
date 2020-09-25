/*    */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*    */ 
/*    */ import javax.persistence.AttributeConverter;
/*    */ import javax.persistence.Converter;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
/*    */ import org.apache.logging.log4j.core.helpers.Strings;
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
/*    */ public class MarkerAttributeConverter
/*    */   implements AttributeConverter<Marker, String>
/*    */ {
/*    */   public String convertToDatabaseColumn(Marker marker) {
/* 34 */     if (marker == null) {
/* 35 */       return null;
/*    */     }
/*    */     
/* 38 */     StringBuilder builder = new StringBuilder(marker.getName());
/* 39 */     Marker parent = marker.getParent();
/* 40 */     int levels = 0;
/* 41 */     boolean hasParent = false;
/* 42 */     while (parent != null) {
/* 43 */       levels++;
/* 44 */       hasParent = true;
/* 45 */       builder.append("[ ").append(parent.getName());
/* 46 */       parent = parent.getParent();
/*    */     } 
/* 48 */     for (int i = 0; i < levels; i++) {
/* 49 */       builder.append(" ]");
/*    */     }
/* 51 */     if (hasParent) {
/* 52 */       builder.append(" ]");
/*    */     }
/* 54 */     return builder.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Marker convertToEntityAttribute(String s) {
/* 59 */     if (Strings.isEmpty(s)) {
/* 60 */       return null;
/*    */     }
/*    */     
/* 63 */     int bracket = s.indexOf("[");
/*    */     
/* 65 */     return (bracket < 1) ? MarkerManager.getMarker(s) : MarkerManager.getMarker(s.substring(0, bracket));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\MarkerAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */