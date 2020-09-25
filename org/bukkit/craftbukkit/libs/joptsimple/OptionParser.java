/*     */ package org.bukkit.craftbukkit.libs.joptsimple;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.internal.AbbreviationMap;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.util.KeyValuePair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OptionParser
/*     */ {
/* 213 */   private final AbbreviationMap<AbstractOptionSpec<?>> recognizedOptions = new AbbreviationMap();
/* 214 */   private OptionParserState state = OptionParserState.moreOptions(false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean posixlyCorrect;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionParser() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionParser(String optionSpecification) {
/* 230 */     this();
/*     */     
/* 232 */     (new OptionSpecTokenizer(optionSpecification)).configure(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionSpecBuilder accepts(String option) {
/* 256 */     return acceptsAll(Collections.singletonList(option));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionSpecBuilder accepts(String option, String description) {
/* 271 */     return acceptsAll(Collections.singletonList(option), description);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionSpecBuilder acceptsAll(Collection<String> options) {
/* 286 */     return acceptsAll(options, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionSpecBuilder acceptsAll(Collection<String> options, String description) {
/* 306 */     if (options.isEmpty()) {
/* 307 */       throw new IllegalArgumentException("need at least one option");
/*     */     }
/* 309 */     ParserRules.ensureLegalOptions(options);
/*     */     
/* 311 */     return new OptionSpecBuilder(this, options, description);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void posixlyCorrect(boolean setting) {
/* 320 */     this.posixlyCorrect = setting;
/* 321 */     this.state = OptionParserState.moreOptions(setting);
/*     */   }
/*     */   
/*     */   boolean posixlyCorrect() {
/* 325 */     return this.posixlyCorrect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recognizeAlternativeLongOptions(boolean recognize) {
/* 336 */     if (recognize) {
/* 337 */       recognize(new AlternativeLongOptionSpec());
/*     */     } else {
/* 339 */       this.recognizedOptions.remove(String.valueOf("W"));
/*     */     } 
/*     */   }
/*     */   void recognize(AbstractOptionSpec<?> spec) {
/* 343 */     this.recognizedOptions.putAll(spec.options(), spec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printHelpOn(OutputStream sink) throws IOException {
/* 358 */     printHelpOn(new OutputStreamWriter(sink));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printHelpOn(Writer sink) throws IOException {
/* 373 */     sink.write((new HelpFormatter()).format(this.recognizedOptions.toJavaUtilMap()));
/* 374 */     sink.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionSet parse(String... arguments) {
/* 388 */     ArgumentList argumentList = new ArgumentList(arguments);
/* 389 */     OptionSet detected = new OptionSet(defaultValues());
/*     */     
/* 391 */     while (argumentList.hasMore()) {
/* 392 */       this.state.handleArgument(this, argumentList, detected);
/*     */     }
/* 394 */     reset();
/* 395 */     return detected;
/*     */   }
/*     */   
/*     */   void handleLongOptionToken(String candidate, ArgumentList arguments, OptionSet detected) {
/* 399 */     KeyValuePair optionAndArgument = parseLongOptionWithArgument(candidate);
/*     */     
/* 401 */     if (!isRecognized(optionAndArgument.key)) {
/* 402 */       throw OptionException.unrecognizedOption(optionAndArgument.key);
/*     */     }
/* 404 */     AbstractOptionSpec<?> optionSpec = specFor(optionAndArgument.key);
/* 405 */     optionSpec.handleOption(this, arguments, detected, optionAndArgument.value);
/*     */   }
/*     */   
/*     */   void handleShortOptionToken(String candidate, ArgumentList arguments, OptionSet detected) {
/* 409 */     KeyValuePair optionAndArgument = parseShortOptionWithArgument(candidate);
/*     */     
/* 411 */     if (isRecognized(optionAndArgument.key)) {
/* 412 */       specFor(optionAndArgument.key).handleOption(this, arguments, detected, optionAndArgument.value);
/*     */     } else {
/*     */       
/* 415 */       handleShortOptionCluster(candidate, arguments, detected);
/*     */     } 
/*     */   }
/*     */   private void handleShortOptionCluster(String candidate, ArgumentList arguments, OptionSet detected) {
/* 419 */     char[] options = extractShortOptionsFrom(candidate);
/* 420 */     validateOptionCharacters(options);
/*     */     
/* 422 */     AbstractOptionSpec<?> optionSpec = specFor(options[0]);
/*     */     
/* 424 */     if (optionSpec.acceptsArguments() && options.length > 1) {
/* 425 */       String detectedArgument = String.valueOf(options, 1, options.length - 1);
/* 426 */       optionSpec.handleOption(this, arguments, detected, detectedArgument);
/*     */     } else {
/*     */       
/* 429 */       for (char each : options)
/* 430 */         specFor(each).handleOption(this, arguments, detected, null); 
/*     */     } 
/*     */   }
/*     */   
/*     */   void noMoreOptions() {
/* 435 */     this.state = OptionParserState.noMoreOptions();
/*     */   }
/*     */   
/*     */   boolean looksLikeAnOption(String argument) {
/* 439 */     return (ParserRules.isShortOptionToken(argument) || ParserRules.isLongOptionToken(argument));
/*     */   }
/*     */   
/*     */   private boolean isRecognized(String option) {
/* 443 */     return this.recognizedOptions.contains(option);
/*     */   }
/*     */   
/*     */   private AbstractOptionSpec<?> specFor(char option) {
/* 447 */     return specFor(String.valueOf(option));
/*     */   }
/*     */   
/*     */   private AbstractOptionSpec<?> specFor(String option) {
/* 451 */     return (AbstractOptionSpec)this.recognizedOptions.get(option);
/*     */   }
/*     */   
/*     */   private void reset() {
/* 455 */     this.state = OptionParserState.moreOptions(this.posixlyCorrect);
/*     */   }
/*     */   
/*     */   private static char[] extractShortOptionsFrom(String argument) {
/* 459 */     char[] options = new char[argument.length() - 1];
/* 460 */     argument.getChars(1, argument.length(), options, 0);
/*     */     
/* 462 */     return options;
/*     */   }
/*     */   
/*     */   private void validateOptionCharacters(char[] options) {
/* 466 */     for (int i = 0; i < options.length; i++) {
/* 467 */       String option = String.valueOf(options[i]);
/*     */       
/* 469 */       if (!isRecognized(option)) {
/* 470 */         throw OptionException.unrecognizedOption(option);
/*     */       }
/* 472 */       if (specFor(option).acceptsArguments()) {
/* 473 */         if (i > 0) {
/* 474 */           throw OptionException.illegalOptionCluster(option);
/*     */         }
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static KeyValuePair parseLongOptionWithArgument(String argument) {
/* 483 */     return KeyValuePair.valueOf(argument.substring(2));
/*     */   }
/*     */   
/*     */   private static KeyValuePair parseShortOptionWithArgument(String argument) {
/* 487 */     return KeyValuePair.valueOf(argument.substring(1));
/*     */   }
/*     */   
/*     */   private Map<String, List<?>> defaultValues() {
/* 491 */     Map<String, List<?>> defaults = new HashMap<String, List<?>>();
/* 492 */     for (Map.Entry<String, AbstractOptionSpec<?>> each : (Iterable<Map.Entry<String, AbstractOptionSpec<?>>>)this.recognizedOptions.toJavaUtilMap().entrySet())
/* 493 */       defaults.put(each.getKey(), ((AbstractOptionSpec)each.getValue()).defaultValues()); 
/* 494 */     return defaults;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\OptionParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */