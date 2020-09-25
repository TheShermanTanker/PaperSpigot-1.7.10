/*     */ package net.minecraft.util.org.apache.commons.codec.language;
/*     */ 
/*     */ import net.minecraft.util.org.apache.commons.codec.EncoderException;
/*     */ import net.minecraft.util.org.apache.commons.codec.StringEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class Caverphone
/*     */   implements StringEncoder
/*     */ {
/*  41 */   private final Caverphone2 encoder = new Caverphone2();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String caverphone(String source) {
/*  58 */     return this.encoder.encode(source);
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
/*     */   public Object encode(Object obj) throws EncoderException {
/*  74 */     if (!(obj instanceof String)) {
/*  75 */       throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
/*     */     }
/*  77 */     return caverphone((String)obj);
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
/*     */   public String encode(String str) {
/*  89 */     return caverphone(str);
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
/*     */   public boolean isCaverphoneEqual(String str1, String str2) {
/* 102 */     return caverphone(str1).equals(caverphone(str2));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\codec\language\Caverphone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */