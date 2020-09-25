/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Note
/*     */ {
/*     */   private final byte note;
/*     */   
/*     */   public enum Tone
/*     */   {
/*  18 */     G(1, true),
/*  19 */     A(3, true),
/*  20 */     B(5, false),
/*  21 */     C(6, true),
/*  22 */     D(8, true),
/*  23 */     E(10, false),
/*  24 */     F(11, true);
/*     */     
/*     */     private final boolean sharpable;
/*     */     
/*     */     private final byte id;
/*  29 */     private static final Map<Byte, Tone> BY_DATA = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final byte TONES_COUNT = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Tone(int id, boolean sharpable) {
/*     */       this.id = (byte)(id % 12);
/*     */       this.sharpable = sharpable;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public byte getId() {
/*     */       return getId(false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public byte getId(boolean sharped) {
/*     */       byte id = (byte)((sharped && this.sharpable) ? (this.id + 1) : this.id);
/*     */       return (byte)(id % 12);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSharpable() {
/*     */       return this.sharpable;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean isSharped(byte id) {
/*     */       if (id == getId(false)) {
/*     */         return false;
/*     */       }
/*     */       if (id == getId(true)) {
/*     */         return true;
/*     */       }
/*     */       throw new IllegalArgumentException("The id isn't matching to the tone.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 108 */       for (Tone tone : values()) {
/* 109 */         int id = tone.id % 12;
/* 110 */         BY_DATA.put(Byte.valueOf((byte)id), tone);
/*     */         
/* 112 */         if (tone.isSharpable()) {
/* 113 */           id = (id + 1) % 12;
/* 114 */           BY_DATA.put(Byte.valueOf((byte)id), tone);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public static Tone getById(byte id) {
/*     */       return BY_DATA.get(Byte.valueOf(id));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Note(int note) {
/* 129 */     Validate.isTrue((note >= 0 && note <= 24), "The note value has to be between 0 and 24.");
/*     */     
/* 131 */     this.note = (byte)note;
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
/*     */   public Note(int octave, Tone tone, boolean sharped) {
/* 143 */     if (sharped && !tone.isSharpable()) {
/* 144 */       tone = Tone.values()[tone.ordinal() + 1];
/* 145 */       sharped = false;
/*     */     } 
/* 147 */     if (octave < 0 || octave > 2 || (octave == 2 && (tone != Tone.F || !sharped))) {
/* 148 */       throw new IllegalArgumentException("Tone and octave have to be between F#0 and F#2");
/*     */     }
/*     */     
/* 151 */     this.note = (byte)(octave * 12 + tone.getId(sharped));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Note flat(int octave, Tone tone) {
/* 162 */     Validate.isTrue((octave != 2), "Octave cannot be 2 for flats");
/* 163 */     tone = (tone == Tone.G) ? Tone.F : Tone.values()[tone.ordinal() - 1];
/* 164 */     return new Note(octave, tone, tone.isSharpable());
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
/*     */   public static Note sharp(int octave, Tone tone) {
/* 176 */     return new Note(octave, tone, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Note natural(int octave, Tone tone) {
/* 187 */     Validate.isTrue((octave != 2), "Octave cannot be 2 for naturals");
/* 188 */     return new Note(octave, tone, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Note sharped() {
/* 195 */     Validate.isTrue((this.note < 24), "This note cannot be sharped because it is the highest known note!");
/* 196 */     return new Note(this.note + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Note flattened() {
/* 203 */     Validate.isTrue((this.note > 0), "This note cannot be flattened because it is the lowest known note!");
/* 204 */     return new Note(this.note - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public byte getId() {
/* 215 */     return this.note;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOctave() {
/* 224 */     return this.note / 12;
/*     */   }
/*     */   
/*     */   private byte getToneByte() {
/* 228 */     return (byte)(this.note % 12);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tone getTone() {
/* 237 */     return Tone.getById(getToneByte());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSharped() {
/* 246 */     byte note = getToneByte();
/* 247 */     return Tone.getById(note).isSharped(note);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 252 */     int prime = 31;
/* 253 */     int result = 1;
/* 254 */     result = 31 * result + this.note;
/* 255 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 260 */     if (this == obj)
/* 261 */       return true; 
/* 262 */     if (obj == null)
/* 263 */       return false; 
/* 264 */     if (getClass() != obj.getClass())
/* 265 */       return false; 
/* 266 */     Note other = (Note)obj;
/* 267 */     if (this.note != other.note)
/* 268 */       return false; 
/* 269 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 274 */     return "Note{" + getTone().toString() + (isSharped() ? "#" : "") + "}";
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Note.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */