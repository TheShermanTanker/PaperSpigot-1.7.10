/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.lang.annotation.ElementType;
/*    */ import java.lang.annotation.Retention;
/*    */ import java.lang.annotation.RetentionPolicy;
/*    */ import java.lang.annotation.Target;
/*    */ import java.util.Map;
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
/*    */ @Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE})
/*    */ @Retention(RetentionPolicy.RUNTIME)
/*    */ public @interface Warning
/*    */ {
/*    */   boolean value() default false;
/*    */   
/*    */   String reason() default "";
/*    */   
/*    */   public enum WarningState
/*    */   {
/* 29 */     ON,
/*    */ 
/*    */ 
/*    */     
/* 33 */     OFF,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 38 */     DEFAULT;
/*    */     
/* 40 */     private static final Map<String, WarningState> values = (Map<String, WarningState>)ImmutableMap.builder().put("off", OFF).put("false", OFF).put("f", OFF).put("no", OFF).put("n", OFF).put("on", ON).put("true", ON).put("t", ON).put("yes", ON).put("y", ON).put("", DEFAULT).put("d", DEFAULT).put("default", DEFAULT).build();
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
/*    */     static {
/*    */     
/*    */     }
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
/*    */     public boolean printFor(Warning warning) {
/* 69 */       if (this == DEFAULT) {
/* 70 */         return (warning == null || warning.value());
/*    */       }
/* 72 */       return (this == ON);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static WarningState value(String value) {
/* 84 */       if (value == null) {
/* 85 */         return DEFAULT;
/*    */       }
/* 87 */       WarningState state = values.get(value.toLowerCase());
/* 88 */       if (state == null) {
/* 89 */         return DEFAULT;
/*    */       }
/* 91 */       return state;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Warning.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */