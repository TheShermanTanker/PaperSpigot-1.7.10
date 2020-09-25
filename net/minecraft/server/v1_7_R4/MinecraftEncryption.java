/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MinecraftEncryption
/*     */ {
/*     */   public static KeyPair b() {
/*     */     try {
/*  32 */       KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
/*  33 */       keyPairGenerator.initialize(1024);
/*     */       
/*  35 */       return keyPairGenerator.generateKeyPair();
/*  36 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  37 */       noSuchAlgorithmException.printStackTrace();
/*     */       
/*  39 */       System.err.println("Key pair generation failed!");
/*  40 */       return null;
/*     */     } 
/*     */   }
/*     */   public static byte[] a(String paramString, PublicKey paramPublicKey, SecretKey paramSecretKey) {
/*     */     try {
/*  45 */       return a("SHA-1", new byte[][] { paramString.getBytes("ISO_8859_1"), paramSecretKey.getEncoded(), paramPublicKey.getEncoded() });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  51 */     catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  52 */       unsupportedEncodingException.printStackTrace();
/*     */ 
/*     */       
/*  55 */       return null;
/*     */     } 
/*     */   }
/*     */   private static byte[] a(String paramString, byte[]... paramVarArgs) {
/*     */     try {
/*  60 */       MessageDigest messageDigest = MessageDigest.getInstance(paramString);
/*  61 */       for (byte[] arrayOfByte : paramVarArgs) {
/*  62 */         messageDigest.update(arrayOfByte);
/*     */       }
/*  64 */       return messageDigest.digest();
/*  65 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  66 */       noSuchAlgorithmException.printStackTrace();
/*     */ 
/*     */       
/*  69 */       return null;
/*     */     } 
/*     */   }
/*     */   public static PublicKey a(byte[] paramArrayOfbyte) {
/*     */     
/*  74 */     try { X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(paramArrayOfbyte);
/*  75 */       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
/*  76 */       return keyFactory.generatePublic(x509EncodedKeySpec); }
/*  77 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {  }
/*  78 */     catch (InvalidKeySpecException invalidKeySpecException) {}
/*     */     
/*  80 */     System.err.println("Public key reconstitute failed!");
/*  81 */     return null;
/*     */   }
/*     */   
/*     */   public static SecretKey a(PrivateKey paramPrivateKey, byte[] paramArrayOfbyte) {
/*  85 */     return new SecretKeySpec(b(paramPrivateKey, paramArrayOfbyte), "AES");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] b(Key paramKey, byte[] paramArrayOfbyte) {
/*  93 */     return a(2, paramKey, paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   private static byte[] a(int paramInt, Key paramKey, byte[] paramArrayOfbyte) {
/*     */     try {
/*  98 */       return a(paramInt, paramKey.getAlgorithm(), paramKey).doFinal(paramArrayOfbyte);
/*  99 */     } catch (IllegalBlockSizeException illegalBlockSizeException) {
/* 100 */       illegalBlockSizeException.printStackTrace();
/* 101 */     } catch (BadPaddingException badPaddingException) {
/* 102 */       badPaddingException.printStackTrace();
/*     */     } 
/* 104 */     System.err.println("Cipher data failed!");
/* 105 */     return null;
/*     */   }
/*     */   
/*     */   private static Cipher a(int paramInt, String paramString, Key paramKey) {
/*     */     try {
/* 110 */       Cipher cipher = Cipher.getInstance(paramString);
/* 111 */       cipher.init(paramInt, paramKey);
/* 112 */       return cipher;
/* 113 */     } catch (InvalidKeyException invalidKeyException) {
/* 114 */       invalidKeyException.printStackTrace();
/* 115 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 116 */       noSuchAlgorithmException.printStackTrace();
/* 117 */     } catch (NoSuchPaddingException noSuchPaddingException) {
/* 118 */       noSuchPaddingException.printStackTrace();
/*     */     } 
/* 120 */     System.err.println("Cipher creation failed!");
/* 121 */     return null;
/*     */   }
/*     */   
/*     */   public static Cipher a(int paramInt, Key paramKey) {
/*     */     try {
/* 126 */       Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
/* 127 */       cipher.init(paramInt, paramKey, new IvParameterSpec(paramKey.getEncoded()));
/* 128 */       return cipher;
/* 129 */     } catch (GeneralSecurityException generalSecurityException) {
/* 130 */       throw new RuntimeException(generalSecurityException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MinecraftEncryption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */