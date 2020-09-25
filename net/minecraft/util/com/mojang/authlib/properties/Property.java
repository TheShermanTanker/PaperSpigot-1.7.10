/*    */ package net.minecraft.util.com.mojang.authlib.properties;
/*    */ import java.security.InvalidKeyException;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.security.PublicKey;
/*    */ import java.security.Signature;
/*    */ import java.security.SignatureException;
/*    */ import net.minecraft.util.org.apache.commons.codec.binary.Base64;
/*    */ 
/*    */ public class Property {
/*    */   private final String name;
/*    */   
/*    */   public Property(String value, String name) {
/* 13 */     this(value, name, null);
/*    */   }
/*    */   private final String value; private final String signature;
/*    */   public Property(String name, String value, String signature) {
/* 17 */     this.name = name;
/* 18 */     this.value = value;
/* 19 */     this.signature = signature;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 23 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 27 */     return this.value;
/*    */   }
/*    */   
/*    */   public String getSignature() {
/* 31 */     return this.signature;
/*    */   }
/*    */   
/*    */   public boolean hasSignature() {
/* 35 */     return (this.signature != null);
/*    */   }
/*    */   
/*    */   public boolean isSignatureValid(PublicKey publicKey) {
/*    */     try {
/* 40 */       Signature signature = Signature.getInstance("SHA1withRSA");
/* 41 */       signature.initVerify(publicKey);
/* 42 */       signature.update(this.value.getBytes());
/* 43 */       return signature.verify(Base64.decodeBase64(this.signature));
/* 44 */     } catch (NoSuchAlgorithmException e) {
/* 45 */       e.printStackTrace();
/* 46 */     } catch (InvalidKeyException e) {
/* 47 */       e.printStackTrace();
/* 48 */     } catch (SignatureException e) {
/* 49 */       e.printStackTrace();
/*    */     } 
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\properties\Property.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */