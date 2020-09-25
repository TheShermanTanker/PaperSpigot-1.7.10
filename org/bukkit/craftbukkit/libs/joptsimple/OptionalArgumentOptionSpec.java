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
/*    */ class OptionalArgumentOptionSpec<V>
/*    */   extends ArgumentAcceptingOptionSpec<V>
/*    */ {
/*    */   OptionalArgumentOptionSpec(String option) {
/* 39 */     super(option, false);
/*    */   }
/*    */   
/*    */   OptionalArgumentOptionSpec(Collection<String> options, String description) {
/* 43 */     super(options, false, description);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
/* 48 */     if (arguments.hasMore()) {
/* 49 */       String nextArgument = arguments.peek();
/*    */       
/* 51 */       if (!parser.looksLikeAnOption(nextArgument)) {
/* 52 */         handleOptionArgument(parser, detectedOptions, arguments);
/* 53 */       } else if (isArgumentOfNumberType() && canConvertArgument(nextArgument)) {
/* 54 */         addArguments(detectedOptions, arguments.next());
/*    */       } else {
/* 56 */         detectedOptions.add(this);
/*    */       } 
/*    */     } else {
/* 59 */       detectedOptions.add(this);
/*    */     } 
/*    */   }
/*    */   private void handleOptionArgument(OptionParser parser, OptionSet detectedOptions, ArgumentList arguments) {
/* 63 */     if (parser.posixlyCorrect()) {
/* 64 */       detectedOptions.add(this);
/* 65 */       parser.noMoreOptions();
/*    */     } else {
/*    */       
/* 68 */       addArguments(detectedOptions, arguments.next());
/*    */     } 
/*    */   }
/*    */   
/*    */   void accept(OptionSpecVisitor visitor) {
/* 73 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\OptionalArgumentOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */