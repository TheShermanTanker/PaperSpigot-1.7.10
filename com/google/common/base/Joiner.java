/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.IOException;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.annotation.CheckReturnValue;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ public class Joiner
/*     */ {
/*     */   private final String separator;
/*     */   
/*     */   public static Joiner on(String separator) {
/*  68 */     return new Joiner(separator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Joiner on(char separator) {
/*  75 */     return new Joiner(String.valueOf(separator));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Joiner(String separator) {
/*  81 */     this.separator = Preconditions.<String>checkNotNull(separator);
/*     */   }
/*     */   
/*     */   private Joiner(Joiner prototype) {
/*  85 */     this.separator = prototype.separator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends Appendable> A appendTo(A appendable, Iterable<?> parts) throws IOException {
/*  93 */     Preconditions.checkNotNull(appendable);
/*  94 */     Iterator<?> iterator = parts.iterator();
/*  95 */     if (iterator.hasNext()) {
/*  96 */       appendable.append(toString(iterator.next()));
/*  97 */       while (iterator.hasNext()) {
/*  98 */         appendable.append(this.separator);
/*  99 */         appendable.append(toString(iterator.next()));
/*     */       } 
/*     */     } 
/* 102 */     return appendable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <A extends Appendable> A appendTo(A appendable, Object[] parts) throws IOException {
/* 110 */     return appendTo(appendable, Arrays.asList(parts));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <A extends Appendable> A appendTo(A appendable, @Nullable Object first, @Nullable Object second, Object... rest) throws IOException {
/* 119 */     return appendTo(appendable, iterable(first, second, rest));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final StringBuilder appendTo(StringBuilder builder, Iterable<?> parts) {
/*     */     try {
/* 129 */       appendTo(builder, parts);
/* 130 */     } catch (IOException impossible) {
/* 131 */       throw new AssertionError(impossible);
/*     */     } 
/* 133 */     return builder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final StringBuilder appendTo(StringBuilder builder, Object[] parts) {
/* 142 */     return appendTo(builder, Arrays.asList(parts));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final StringBuilder appendTo(StringBuilder builder, @Nullable Object first, @Nullable Object second, Object... rest) {
/* 152 */     return appendTo(builder, iterable(first, second, rest));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String join(Iterable<?> parts) {
/* 160 */     return appendTo(new StringBuilder(), parts).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String join(Object[] parts) {
/* 168 */     return join(Arrays.asList(parts));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String join(@Nullable Object first, @Nullable Object second, Object... rest) {
/* 176 */     return join(iterable(first, second, rest));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CheckReturnValue
/*     */   public Joiner useForNull(final String nullText) {
/* 185 */     Preconditions.checkNotNull(nullText);
/* 186 */     return new Joiner(this) {
/*     */         CharSequence toString(Object part) {
/* 188 */           return (part == null) ? nullText : Joiner.this.toString(part);
/*     */         }
/*     */         
/*     */         public Joiner useForNull(String nullText) {
/* 192 */           Preconditions.checkNotNull(nullText);
/* 193 */           throw new UnsupportedOperationException("already specified useForNull");
/*     */         }
/*     */         
/*     */         public Joiner skipNulls() {
/* 197 */           throw new UnsupportedOperationException("already specified useForNull");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CheckReturnValue
/*     */   public Joiner skipNulls() {
/* 208 */     return new Joiner(this)
/*     */       {
/*     */         public <A extends Appendable> A appendTo(A appendable, Iterable<?> parts) throws IOException {
/* 211 */           Preconditions.checkNotNull(appendable, "appendable");
/* 212 */           Preconditions.checkNotNull(parts, "parts");
/* 213 */           Iterator<?> iterator = parts.iterator();
/* 214 */           while (iterator.hasNext()) {
/* 215 */             Object part = iterator.next();
/* 216 */             if (part != null) {
/* 217 */               appendable.append(Joiner.this.toString(part));
/*     */               break;
/*     */             } 
/*     */           } 
/* 221 */           while (iterator.hasNext()) {
/* 222 */             Object part = iterator.next();
/* 223 */             if (part != null) {
/* 224 */               appendable.append(Joiner.this.separator);
/* 225 */               appendable.append(Joiner.this.toString(part));
/*     */             } 
/*     */           } 
/* 228 */           return appendable;
/*     */         }
/*     */         
/*     */         public Joiner useForNull(String nullText) {
/* 232 */           Preconditions.checkNotNull(nullText);
/* 233 */           throw new UnsupportedOperationException("already specified skipNulls");
/*     */         }
/*     */         
/*     */         public Joiner.MapJoiner withKeyValueSeparator(String kvs) {
/* 237 */           Preconditions.checkNotNull(kvs);
/* 238 */           throw new UnsupportedOperationException("can't use .skipNulls() with maps");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CheckReturnValue
/*     */   public MapJoiner withKeyValueSeparator(String keyValueSeparator) {
/* 249 */     return new MapJoiner(this, keyValueSeparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class MapJoiner
/*     */   {
/*     */     private final Joiner joiner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String keyValueSeparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private MapJoiner(Joiner joiner, String keyValueSeparator) {
/* 275 */       this.joiner = joiner;
/* 276 */       this.keyValueSeparator = Preconditions.<String>checkNotNull(keyValueSeparator);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <A extends Appendable> A appendTo(A appendable, Map<?, ?> map) throws IOException {
/* 284 */       return appendTo(appendable, map.entrySet());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StringBuilder appendTo(StringBuilder builder, Map<?, ?> map) {
/* 293 */       return appendTo(builder, map.entrySet());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String join(Map<?, ?> map) {
/* 301 */       return join(map.entrySet());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Beta
/*     */     public <A extends Appendable> A appendTo(A appendable, Iterable<? extends Map.Entry<?, ?>> entries) throws IOException {
/* 313 */       Preconditions.checkNotNull(appendable);
/* 314 */       Iterator<? extends Map.Entry<?, ?>> iterator = entries.iterator();
/* 315 */       if (iterator.hasNext()) {
/* 316 */         Map.Entry<?, ?> entry = iterator.next();
/* 317 */         appendable.append(this.joiner.toString(entry.getKey()));
/* 318 */         appendable.append(this.keyValueSeparator);
/* 319 */         appendable.append(this.joiner.toString(entry.getValue()));
/* 320 */         while (iterator.hasNext()) {
/* 321 */           appendable.append(this.joiner.separator);
/* 322 */           Map.Entry<?, ?> e = iterator.next();
/* 323 */           appendable.append(this.joiner.toString(e.getKey()));
/* 324 */           appendable.append(this.keyValueSeparator);
/* 325 */           appendable.append(this.joiner.toString(e.getValue()));
/*     */         } 
/*     */       } 
/* 328 */       return appendable;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Beta
/*     */     public StringBuilder appendTo(StringBuilder builder, Iterable<? extends Map.Entry<?, ?>> entries) {
/*     */       try {
/* 341 */         appendTo(builder, entries);
/* 342 */       } catch (IOException impossible) {
/* 343 */         throw new AssertionError(impossible);
/*     */       } 
/* 345 */       return builder;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Beta
/*     */     public String join(Iterable<? extends Map.Entry<?, ?>> entries) {
/* 356 */       return appendTo(new StringBuilder(), entries).toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CheckReturnValue
/*     */     public MapJoiner useForNull(String nullText) {
/* 365 */       return new MapJoiner(this.joiner.useForNull(nullText), this.keyValueSeparator);
/*     */     }
/*     */   }
/*     */   
/*     */   CharSequence toString(Object part) {
/* 370 */     Preconditions.checkNotNull(part);
/* 371 */     return (part instanceof CharSequence) ? (CharSequence)part : part.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Iterable<Object> iterable(final Object first, final Object second, final Object[] rest) {
/* 376 */     Preconditions.checkNotNull(rest);
/* 377 */     return new AbstractList() {
/*     */         public int size() {
/* 379 */           return rest.length + 2;
/*     */         }
/*     */         
/*     */         public Object get(int index) {
/* 383 */           switch (index) {
/*     */             case 0:
/* 385 */               return first;
/*     */             case 1:
/* 387 */               return second;
/*     */           } 
/* 389 */           return rest[index - 2];
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Joiner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */