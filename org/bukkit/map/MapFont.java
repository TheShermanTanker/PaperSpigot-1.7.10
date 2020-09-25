/*     */ package org.bukkit.map;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapFont
/*     */ {
/*  10 */   private final HashMap<Character, CharacterSprite> chars = new HashMap<Character, CharacterSprite>();
/*  11 */   private int height = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean malleable = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChar(char ch, CharacterSprite sprite) {
/*  22 */     if (!this.malleable) {
/*  23 */       throw new IllegalStateException("this font is not malleable");
/*     */     }
/*     */     
/*  26 */     this.chars.put(Character.valueOf(ch), sprite);
/*  27 */     if (sprite.getHeight() > this.height) {
/*  28 */       this.height = sprite.getHeight();
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
/*     */   public CharacterSprite getChar(char ch) {
/*  40 */     return this.chars.get(Character.valueOf(ch));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(String text) {
/*  51 */     if (!isValid(text)) {
/*  52 */       throw new IllegalArgumentException("text contains invalid characters");
/*     */     }
/*     */     
/*  55 */     if (text.length() == 0) {
/*  56 */       return 0;
/*     */     }
/*     */     
/*  59 */     int result = 0;
/*  60 */     for (int i = 0; i < text.length(); i++) {
/*  61 */       result += ((CharacterSprite)this.chars.get(Character.valueOf(text.charAt(i)))).getWidth();
/*     */     }
/*  63 */     result += text.length() - 1;
/*     */     
/*  65 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  74 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String text) {
/*  85 */     for (int i = 0; i < text.length(); i++) {
/*  86 */       char ch = text.charAt(i);
/*  87 */       if (ch != '§' && ch != '\n' && 
/*  88 */         this.chars.get(Character.valueOf(ch)) == null) return false; 
/*     */     } 
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CharacterSprite
/*     */   {
/*     */     private final int width;
/*     */     
/*     */     private final int height;
/*     */     
/*     */     private final boolean[] data;
/*     */     
/*     */     public CharacterSprite(int width, int height, boolean[] data) {
/* 103 */       this.width = width;
/* 104 */       this.height = height;
/* 105 */       this.data = data;
/*     */       
/* 107 */       if (data.length != width * height) {
/* 108 */         throw new IllegalArgumentException("size of data does not match dimensions");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean get(int row, int col) {
/* 120 */       if (row < 0 || col < 0 || row >= this.height || col >= this.width) return false; 
/* 121 */       return this.data[row * this.width + col];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getWidth() {
/* 130 */       return this.width;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 139 */       return this.height;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\map\MapFont.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */