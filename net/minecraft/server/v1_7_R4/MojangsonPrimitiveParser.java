/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MojangsonPrimitiveParser
/*     */   extends MojangsonTypeParser
/*     */ {
/*     */   protected String b;
/*     */   
/*     */   public MojangsonPrimitiveParser(String paramString1, String paramString2) {
/* 315 */     this.a = paramString1;
/* 316 */     this.b = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase a() {
/*     */     try {
/* 323 */       if (this.b.matches("[-+]?[0-9]*\\.?[0-9]+[d|D]"))
/* 324 */         return new NBTTagDouble(Double.parseDouble(this.b.substring(0, this.b.length() - 1))); 
/* 325 */       if (this.b.matches("[-+]?[0-9]*\\.?[0-9]+[f|F]"))
/* 326 */         return new NBTTagFloat(Float.parseFloat(this.b.substring(0, this.b.length() - 1))); 
/* 327 */       if (this.b.matches("[-+]?[0-9]+[b|B]"))
/* 328 */         return new NBTTagByte(Byte.parseByte(this.b.substring(0, this.b.length() - 1))); 
/* 329 */       if (this.b.matches("[-+]?[0-9]+[l|L]"))
/* 330 */         return new NBTTagLong(Long.parseLong(this.b.substring(0, this.b.length() - 1))); 
/* 331 */       if (this.b.matches("[-+]?[0-9]+[s|S]"))
/* 332 */         return new NBTTagShort(Short.parseShort(this.b.substring(0, this.b.length() - 1))); 
/* 333 */       if (this.b.matches("[-+]?[0-9]+"))
/* 334 */         return new NBTTagInt(Integer.parseInt(this.b.substring(0, this.b.length()))); 
/* 335 */       if (this.b.matches("[-+]?[0-9]*\\.?[0-9]+"))
/* 336 */         return new NBTTagDouble(Double.parseDouble(this.b.substring(0, this.b.length()))); 
/* 337 */       if (this.b.equalsIgnoreCase("true") || this.b.equalsIgnoreCase("false")) {
/* 338 */         return new NBTTagByte(Boolean.parseBoolean(this.b) ? 1 : 0);
/*     */       }
/* 340 */       if (this.b.startsWith("[") && this.b.endsWith("]")) {
/* 341 */         if (this.b.length() > 2) {
/* 342 */           String str = this.b.substring(1, this.b.length() - 1);
/* 343 */           String[] arrayOfString = str.split(",");
/*     */           try {
/* 345 */             if (arrayOfString.length <= 1) {
/* 346 */               return new NBTTagIntArray(new int[] { Integer.parseInt(str.trim()) });
/*     */             }
/*     */ 
/*     */             
/* 350 */             int[] arrayOfInt = new int[arrayOfString.length];
/* 351 */             for (byte b = 0; b < arrayOfString.length; b++) {
/* 352 */               arrayOfInt[b] = Integer.parseInt(arrayOfString[b].trim());
/*     */             }
/* 354 */             return new NBTTagIntArray(arrayOfInt);
/*     */           }
/* 356 */           catch (NumberFormatException numberFormatException) {
/* 357 */             return new NBTTagString(this.b);
/*     */           } 
/*     */         } 
/* 360 */         return new NBTTagIntArray();
/*     */       } 
/*     */       
/* 363 */       if (this.b.startsWith("\"") && this.b.endsWith("\"") && this.b.length() > 2) {
/* 364 */         this.b = this.b.substring(1, this.b.length() - 1);
/*     */       }
/*     */       
/* 367 */       this.b = this.b.replaceAll("\\\\\"", "\"");
/* 368 */       return new NBTTagString(this.b);
/*     */     }
/* 370 */     catch (NumberFormatException numberFormatException) {
/* 371 */       this.b = this.b.replaceAll("\\\\\"", "\"");
/* 372 */       return new NBTTagString(this.b);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MojangsonPrimitiveParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */