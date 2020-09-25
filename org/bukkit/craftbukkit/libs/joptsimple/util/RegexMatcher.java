/*    */ package org.bukkit.craftbukkit.libs.joptsimple.util;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import org.bukkit.craftbukkit.libs.joptsimple.ValueConversionException;
/*    */ import org.bukkit.craftbukkit.libs.joptsimple.ValueConverter;
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
/*    */ public class RegexMatcher
/*    */   implements ValueConverter<String>
/*    */ {
/*    */   private final Pattern pattern;
/*    */   
/*    */   public RegexMatcher(String pattern, int flags) {
/* 55 */     this.pattern = Pattern.compile(pattern, flags);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ValueConverter<String> regex(String pattern) {
/* 67 */     return new RegexMatcher(pattern, 0);
/*    */   }
/*    */   
/*    */   public String convert(String value) {
/* 71 */     if (!this.pattern.matcher(value).matches()) {
/* 72 */       throw new ValueConversionException("Value [" + value + "] did not match regex [" + this.pattern.pattern() + ']');
/*    */     }
/*    */ 
/*    */     
/* 76 */     return value;
/*    */   }
/*    */   
/*    */   public Class<String> valueType() {
/* 80 */     return String.class;
/*    */   }
/*    */   
/*    */   public String valuePattern() {
/* 84 */     return this.pattern.pattern();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimpl\\util\RegexMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */