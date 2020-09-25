/*     */ package net.minecraft.util.org.apache.commons.codec.digest;
/*     */ 
/*     */ import net.minecraft.util.org.apache.commons.codec.Charsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Crypt
/*     */ {
/*     */   public static String crypt(byte[] keyBytes) {
/*  46 */     return crypt(keyBytes, (String)null);
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
/*     */   public static String crypt(byte[] keyBytes, String salt) {
/*  66 */     if (salt == null)
/*  67 */       return Sha2Crypt.sha512Crypt(keyBytes); 
/*  68 */     if (salt.startsWith("$6$"))
/*  69 */       return Sha2Crypt.sha512Crypt(keyBytes, salt); 
/*  70 */     if (salt.startsWith("$5$"))
/*  71 */       return Sha2Crypt.sha256Crypt(keyBytes, salt); 
/*  72 */     if (salt.startsWith("$1$")) {
/*  73 */       return Md5Crypt.md5Crypt(keyBytes, salt);
/*     */     }
/*  75 */     return UnixCrypt.crypt(keyBytes, salt);
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
/*     */   public static String crypt(String key) {
/*  92 */     return crypt(key, (String)null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String crypt(String key, String salt) {
/* 149 */     return crypt(key.getBytes(Charsets.UTF_8), salt);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\codec\digest\Crypt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */