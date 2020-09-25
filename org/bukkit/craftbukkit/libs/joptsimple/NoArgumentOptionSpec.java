/*    */ package org.bukkit.craftbukkit.libs.joptsimple;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
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
/*    */ class NoArgumentOptionSpec
/*    */   extends AbstractOptionSpec<Void>
/*    */ {
/*    */   NoArgumentOptionSpec(String option) {
/* 41 */     this(Collections.singletonList(option), "");
/*    */   }
/*    */   
/*    */   NoArgumentOptionSpec(Collection<String> options, String description) {
/* 45 */     super(options, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void handleOption(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions, String detectedArgument) {
/* 52 */     detectedOptions.add(this);
/*    */   }
/*    */ 
/*    */   
/*    */   boolean acceptsArguments() {
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean requiresArgument() {
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   void accept(OptionSpecVisitor visitor) {
/* 67 */     visitor.visit(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Void convert(String argument) {
/* 72 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   List<Void> defaultValues() {
/* 77 */     return Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\NoArgumentOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */