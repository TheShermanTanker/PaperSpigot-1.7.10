/*     */ package org.bukkit.map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MapCursor
/*     */ {
/*     */   private byte x;
/*     */   private byte y;
/*     */   private byte direction;
/*     */   private byte type;
/*     */   private boolean visible;
/*     */   
/*     */   @Deprecated
/*     */   public MapCursor(byte x, byte y, byte direction, byte type, boolean visible) {
/*  23 */     this.x = x;
/*  24 */     this.y = y;
/*  25 */     setDirection(direction);
/*  26 */     setRawType(type);
/*  27 */     this.visible = visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getX() {
/*  36 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getY() {
/*  45 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getDirection() {
/*  54 */     return this.direction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType() {
/*  63 */     return Type.byValue(this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public byte getRawType() {
/*  74 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/*  83 */     return this.visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(byte x) {
/*  92 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(byte y) {
/* 101 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirection(byte direction) {
/* 110 */     if (direction < 0 || direction > 15) {
/* 111 */       throw new IllegalArgumentException("Direction must be in the range 0-15");
/*     */     }
/* 113 */     this.direction = direction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(Type type) {
/* 122 */     setRawType(type.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setRawType(byte type) {
/* 133 */     if (type < 0 || type > 15) {
/* 134 */       throw new IllegalArgumentException("Type must be in the range 0-15");
/*     */     }
/* 136 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 145 */     this.visible = visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Type
/*     */   {
/* 155 */     WHITE_POINTER(0),
/* 156 */     GREEN_POINTER(1),
/* 157 */     RED_POINTER(2),
/* 158 */     BLUE_POINTER(3),
/* 159 */     WHITE_CROSS(4);
/*     */     
/*     */     private byte value;
/*     */     
/*     */     Type(int value) {
/* 164 */       this.value = (byte)value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public byte getValue() {
/* 173 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public static Type byValue(byte value) {
/* 182 */       for (Type t : values()) {
/* 183 */         if (t.value == value) return t; 
/*     */       } 
/* 185 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\map\MapCursor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */