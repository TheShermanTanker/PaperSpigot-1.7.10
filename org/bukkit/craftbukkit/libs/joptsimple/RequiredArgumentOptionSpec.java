/*    */ package org.bukkit.craftbukkit.libs.joptsimple;
/*    */ 
/*    */ import java.util.Collection;
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
/*    */ class RequiredArgumentOptionSpec<V>
/*    */   extends ArgumentAcceptingOptionSpec<V>
/*    */ {
/*    */   RequiredArgumentOptionSpec(String option) {
/* 39 */     super(option, true);
/*    */   }
/*    */   
/*    */   RequiredArgumentOptionSpec(Collection<String> options, String description) {
/* 43 */     super(options, true, description);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
/* 48 */     if (!arguments.hasMore()) {
/* 49 */       throw new OptionMissingRequiredArgumentException(options());
/*    */     }
/* 51 */     addArguments(detectedOptions, arguments.next());
/*    */   }
/*    */ 
/*    */   
/*    */   void accept(OptionSpecVisitor visitor) {
/* 56 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\RequiredArgumentOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */