/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.SerializableAs;
/*     */ 
/*     */ @SerializableAs("Firework")
/*     */ public final class FireworkEffect
/*     */   implements ConfigurationSerializable {
/*     */   private static final String FLICKER = "flicker";
/*     */   private static final String TRAIL = "trail";
/*     */   private static final String COLORS = "colors";
/*     */   private static final String FADE_COLORS = "fade-colors";
/*     */   private static final String TYPE = "type";
/*     */   private final boolean flicker;
/*     */   private final boolean trail;
/*     */   private final ImmutableList<Color> colors;
/*     */   private final ImmutableList<Color> fadeColors;
/*     */   private final Type type;
/*     */   
/*     */   public enum Type {
/*  26 */     BALL,
/*     */ 
/*     */ 
/*     */     
/*  30 */     BALL_LARGE,
/*     */ 
/*     */ 
/*     */     
/*  34 */     STAR,
/*     */ 
/*     */ 
/*     */     
/*  38 */     BURST,
/*     */ 
/*     */ 
/*     */     
/*  42 */     CREEPER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/*  52 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     boolean flicker = false;
/*     */     
/*     */     boolean trail = false;
/*     */     
/*  63 */     final ImmutableList.Builder<Color> colors = ImmutableList.builder();
/*  64 */     ImmutableList.Builder<Color> fadeColors = null;
/*  65 */     FireworkEffect.Type type = FireworkEffect.Type.BALL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder with(FireworkEffect.Type type) throws IllegalArgumentException {
/*  77 */       Validate.notNull(type, "Cannot have null type");
/*  78 */       this.type = type;
/*  79 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder withFlicker() {
/*  88 */       this.flicker = true;
/*  89 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder flicker(boolean flicker) {
/*  99 */       this.flicker = flicker;
/* 100 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder withTrail() {
/* 109 */       this.trail = true;
/* 110 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder trail(boolean trail) {
/* 120 */       this.trail = trail;
/* 121 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder withColor(Color color) throws IllegalArgumentException {
/* 132 */       Validate.notNull(color, "Cannot have null color");
/*     */       
/* 134 */       this.colors.add(color);
/*     */       
/* 136 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder withColor(Color... colors) throws IllegalArgumentException {
/* 149 */       Validate.notNull(colors, "Cannot have null colors");
/* 150 */       if (colors.length == 0) {
/* 151 */         return this;
/*     */       }
/*     */       
/* 154 */       ImmutableList.Builder<Color> list = this.colors;
/* 155 */       for (Color color : colors) {
/* 156 */         Validate.notNull(color, "Color cannot be null");
/* 157 */         list.add(color);
/*     */       } 
/*     */       
/* 160 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder withColor(Iterable<?> colors) throws IllegalArgumentException {
/* 174 */       Validate.notNull(colors, "Cannot have null colors");
/*     */       
/* 176 */       ImmutableList.Builder<Color> list = this.colors;
/* 177 */       for (Object color : colors) {
/* 178 */         if (!(color instanceof Color)) {
/* 179 */           throw new IllegalArgumentException(color + " is not a Color in " + colors);
/*     */         }
/* 181 */         list.add(color);
/*     */       } 
/*     */       
/* 184 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder withFade(Color color) throws IllegalArgumentException {
/* 197 */       Validate.notNull(color, "Cannot have null color");
/*     */       
/* 199 */       if (this.fadeColors == null) {
/* 200 */         this.fadeColors = ImmutableList.builder();
/*     */       }
/*     */       
/* 203 */       this.fadeColors.add(color);
/*     */       
/* 205 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder withFade(Color... colors) throws IllegalArgumentException {
/* 218 */       Validate.notNull(colors, "Cannot have null colors");
/* 219 */       if (colors.length == 0) {
/* 220 */         return this;
/*     */       }
/*     */       
/* 223 */       ImmutableList.Builder<Color> list = this.fadeColors;
/* 224 */       if (list == null) {
/* 225 */         list = this.fadeColors = ImmutableList.builder();
/*     */       }
/*     */       
/* 228 */       for (Color color : colors) {
/* 229 */         Validate.notNull(color, "Color cannot be null");
/* 230 */         list.add(color);
/*     */       } 
/*     */       
/* 233 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder withFade(Iterable<?> colors) throws IllegalArgumentException {
/* 247 */       Validate.notNull(colors, "Cannot have null colors");
/*     */       
/* 249 */       ImmutableList.Builder<Color> list = this.fadeColors;
/* 250 */       if (list == null) {
/* 251 */         list = this.fadeColors = ImmutableList.builder();
/*     */       }
/*     */       
/* 254 */       for (Object color : colors) {
/* 255 */         if (!(color instanceof Color)) {
/* 256 */           throw new IllegalArgumentException(color + " is not a Color in " + colors);
/*     */         }
/* 258 */         list.add(color);
/*     */       } 
/*     */       
/* 261 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FireworkEffect build() {
/* 273 */       return new FireworkEffect(this.flicker, this.trail, this.colors.build(), (this.fadeColors == null) ? ImmutableList.of() : this.fadeColors.build(), this.type);
/*     */     }
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
/* 294 */   private String string = null;
/*     */   
/*     */   FireworkEffect(boolean flicker, boolean trail, ImmutableList<Color> colors, ImmutableList<Color> fadeColors, Type type) {
/* 297 */     if (colors.isEmpty()) {
/* 298 */       throw new IllegalStateException("Cannot make FireworkEffect without any color");
/*     */     }
/* 300 */     this.flicker = flicker;
/* 301 */     this.trail = trail;
/* 302 */     this.colors = colors;
/* 303 */     this.fadeColors = fadeColors;
/* 304 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFlicker() {
/* 313 */     return this.flicker;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTrail() {
/* 322 */     return this.trail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Color> getColors() {
/* 331 */     return (List<Color>)this.colors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Color> getFadeColors() {
/* 340 */     return (List<Color>)this.fadeColors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 349 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurationSerializable deserialize(Map<String, Object> map) {
/* 356 */     Type type = Type.valueOf((String)map.get("type"));
/* 357 */     if (type == null) {
/* 358 */       throw new IllegalArgumentException((new StringBuilder()).append(map.get("type")).append(" is not a valid Type").toString());
/*     */     }
/*     */     
/* 361 */     return builder().flicker(((Boolean)map.get("flicker")).booleanValue()).trail(((Boolean)map.get("trail")).booleanValue()).withColor((Iterable)map.get("colors")).withFade((Iterable)map.get("fade-colors")).with(type).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> serialize() {
/* 371 */     return (Map<String, Object>)ImmutableMap.of("flicker", Boolean.valueOf(this.flicker), "trail", Boolean.valueOf(this.trail), "colors", this.colors, "fade-colors", this.fadeColors, "type", this.type.name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 382 */     String string = this.string;
/* 383 */     if (string == null) {
/* 384 */       return this.string = "FireworkEffect:" + serialize();
/*     */     }
/* 386 */     return string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 394 */     int PRIME = 31, TRUE = 1231, FALSE = 1237;
/* 395 */     int hash = 1;
/* 396 */     hash = hash * 31 + (this.flicker ? 1231 : 1237);
/* 397 */     hash = hash * 31 + (this.trail ? 1231 : 1237);
/* 398 */     hash = hash * 31 + this.type.hashCode();
/* 399 */     hash = hash * 31 + this.colors.hashCode();
/* 400 */     hash = hash * 31 + this.fadeColors.hashCode();
/* 401 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 406 */     if (this == obj) {
/* 407 */       return true;
/*     */     }
/*     */     
/* 410 */     if (!(obj instanceof FireworkEffect)) {
/* 411 */       return false;
/*     */     }
/*     */     
/* 414 */     FireworkEffect that = (FireworkEffect)obj;
/* 415 */     return (this.flicker == that.flicker && this.trail == that.trail && this.type == that.type && this.colors.equals(that.colors) && this.fadeColors.equals(that.fadeColors));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\FireworkEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */