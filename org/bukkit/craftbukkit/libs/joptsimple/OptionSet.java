/*     */ package org.bukkit.craftbukkit.libs.joptsimple;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.internal.Objects;
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
/*     */ public class OptionSet
/*     */ {
/*     */   private final Map<String, AbstractOptionSpec<?>> detectedOptions;
/*     */   private final Map<AbstractOptionSpec<?>, List<String>> optionsToArguments;
/*     */   private final List<String> nonOptionArguments;
/*     */   private final Map<String, List<?>> defaultValues;
/*     */   
/*     */   OptionSet(Map<String, List<?>> defaults) {
/*  54 */     this.detectedOptions = new HashMap<String, AbstractOptionSpec<?>>();
/*  55 */     this.optionsToArguments = new IdentityHashMap<AbstractOptionSpec<?>, List<String>>();
/*  56 */     this.nonOptionArguments = new ArrayList<String>();
/*  57 */     this.defaultValues = new HashMap<String, List<?>>(defaults);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean has(String option) {
/*  68 */     return this.detectedOptions.containsKey(option);
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
/*     */   public boolean has(OptionSpec<?> option) {
/*  86 */     return this.optionsToArguments.containsKey(option);
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
/*     */   public boolean hasArgument(String option) {
/*  98 */     AbstractOptionSpec<?> spec = this.detectedOptions.get(option);
/*  99 */     return (spec != null && hasArgument(spec));
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
/*     */   public boolean hasArgument(OptionSpec<?> option) {
/* 119 */     Objects.ensureNotNull(option);
/*     */     
/* 121 */     List<String> values = this.optionsToArguments.get(option);
/* 122 */     return (values != null && !values.isEmpty());
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
/*     */   public Object valueOf(String option) {
/* 141 */     Objects.ensureNotNull(option);
/*     */     
/* 143 */     AbstractOptionSpec<?> spec = this.detectedOptions.get(option);
/* 144 */     if (spec == null) {
/* 145 */       List<?> defaults = defaultValuesFor(option);
/* 146 */       return defaults.isEmpty() ? null : defaults.get(0);
/*     */     } 
/*     */     
/* 149 */     return valueOf(spec);
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
/*     */   public <V> V valueOf(OptionSpec<V> option) {
/* 168 */     Objects.ensureNotNull(option);
/*     */     
/* 170 */     List<V> values = valuesOf(option);
/* 171 */     switch (values.size()) {
/*     */       case 0:
/* 173 */         return null;
/*     */       case 1:
/* 175 */         return values.get(0);
/*     */     } 
/* 177 */     throw new MultipleArgumentsForOptionException(option.options());
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
/*     */   public List<?> valuesOf(String option) {
/* 193 */     Objects.ensureNotNull(option);
/*     */     
/* 195 */     AbstractOptionSpec<?> spec = this.detectedOptions.get(option);
/* 196 */     return (spec == null) ? defaultValuesFor(option) : valuesOf(spec);
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
/*     */   public <V> List<V> valuesOf(OptionSpec<V> option) {
/* 217 */     Objects.ensureNotNull(option);
/*     */     
/* 219 */     List<String> values = this.optionsToArguments.get(option);
/* 220 */     if (values == null || values.isEmpty()) {
/* 221 */       return defaultValueFor(option);
/*     */     }
/* 223 */     AbstractOptionSpec<V> spec = (AbstractOptionSpec<V>)option;
/* 224 */     List<V> convertedValues = new ArrayList<V>();
/* 225 */     for (String each : values) {
/* 226 */       convertedValues.add(spec.convert(each));
/*     */     }
/* 228 */     return Collections.unmodifiableList(convertedValues);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> nonOptionArguments() {
/* 235 */     return Collections.unmodifiableList(this.nonOptionArguments);
/*     */   }
/*     */   
/*     */   void add(AbstractOptionSpec<?> option) {
/* 239 */     addWithArgument(option, null);
/*     */   }
/*     */   
/*     */   void addWithArgument(AbstractOptionSpec<?> option, String argument) {
/* 243 */     for (String each : option.options()) {
/* 244 */       this.detectedOptions.put(each, option);
/*     */     }
/* 246 */     List<String> optionArguments = this.optionsToArguments.get(option);
/*     */     
/* 248 */     if (optionArguments == null) {
/* 249 */       optionArguments = new ArrayList<String>();
/* 250 */       this.optionsToArguments.put(option, optionArguments);
/*     */     } 
/*     */     
/* 253 */     if (argument != null)
/* 254 */       optionArguments.add(argument); 
/*     */   }
/*     */   
/*     */   void addNonOptionArgument(String argument) {
/* 258 */     this.nonOptionArguments.add(argument);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 263 */     if (this == that) {
/* 264 */       return true;
/*     */     }
/* 266 */     if (that == null || !getClass().equals(that.getClass())) {
/* 267 */       return false;
/*     */     }
/* 269 */     OptionSet other = (OptionSet)that;
/* 270 */     Map<AbstractOptionSpec<?>, List<String>> thisOptionsToArguments = new HashMap<AbstractOptionSpec<?>, List<String>>(this.optionsToArguments);
/*     */     
/* 272 */     Map<AbstractOptionSpec<?>, List<String>> otherOptionsToArguments = new HashMap<AbstractOptionSpec<?>, List<String>>(other.optionsToArguments);
/*     */     
/* 274 */     return (this.detectedOptions.equals(other.detectedOptions) && thisOptionsToArguments.equals(otherOptionsToArguments) && this.nonOptionArguments.equals(other.nonOptionArguments()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 281 */     Map<AbstractOptionSpec<?>, List<String>> thisOptionsToArguments = new HashMap<AbstractOptionSpec<?>, List<String>>(this.optionsToArguments);
/*     */     
/* 283 */     return this.detectedOptions.hashCode() ^ thisOptionsToArguments.hashCode() ^ this.nonOptionArguments.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <V> List<V> defaultValuesFor(String option) {
/* 289 */     if (this.defaultValues.containsKey(option)) {
/*     */       
/* 291 */       List<V> defaults = (List<V>)this.defaultValues.get(option);
/* 292 */       return defaults;
/*     */     } 
/*     */     
/* 295 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   private <V> List<V> defaultValueFor(OptionSpec<V> option) {
/* 299 */     return defaultValuesFor(option.options().iterator().next());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\OptionSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */