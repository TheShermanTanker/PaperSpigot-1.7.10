/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.handler.codec.base64.Base64;
/*     */ import net.minecraft.util.io.netty.util.CharsetUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WebSocketUtil
/*     */ {
/*     */   static byte[] md5(byte[] data) {
/*     */     try {
/*  39 */       MessageDigest md = MessageDigest.getInstance("MD5");
/*     */       
/*  41 */       return md.digest(data);
/*  42 */     } catch (NoSuchAlgorithmException e) {
/*     */       
/*  44 */       throw new InternalError("MD5 not supported on this platform - Outdated?");
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
/*     */   static byte[] sha1(byte[] data) {
/*     */     try {
/*  57 */       MessageDigest md = MessageDigest.getInstance("SHA1");
/*     */       
/*  59 */       return md.digest(data);
/*  60 */     } catch (NoSuchAlgorithmException e) {
/*     */       
/*  62 */       throw new InternalError("SHA-1 is not supported on this platform - Outdated?");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String base64(byte[] data) {
/*  73 */     ByteBuf encodedData = Unpooled.wrappedBuffer(data);
/*  74 */     ByteBuf encoded = Base64.encode(encodedData);
/*  75 */     String encodedString = encoded.toString(CharsetUtil.UTF_8);
/*  76 */     encoded.release();
/*  77 */     return encodedString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] randomBytes(int size) {
/*  87 */     byte[] bytes = new byte[size];
/*     */     
/*  89 */     for (int index = 0; index < size; index++) {
/*  90 */       bytes[index] = (byte)randomNumber(0, 255);
/*     */     }
/*     */     
/*  93 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int randomNumber(int minimum, int maximum) {
/* 104 */     return (int)(Math.random() * maximum + minimum);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */