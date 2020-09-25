/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Instrument
/*    */ {
/* 12 */   PIANO(0),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 17 */   BASS_DRUM(1),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   SNARE_DRUM(2),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   STICKS(3),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   BASS_GUITAR(4);
/*    */   
/*    */   static {
/* 35 */     BY_DATA = Maps.newHashMap();
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
/* 63 */     for (Instrument instrument : values())
/* 64 */       BY_DATA.put(Byte.valueOf(instrument.getType()), instrument); 
/*    */   }
/*    */   
/*    */   private final byte type;
/*    */   private static final Map<Byte, Instrument> BY_DATA;
/*    */   
/*    */   Instrument(int type) {
/*    */     this.type = (byte)type;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public byte getType() {
/*    */     return this.type;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static Instrument getByType(byte type) {
/*    */     return BY_DATA.get(Byte.valueOf(type));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Instrument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */