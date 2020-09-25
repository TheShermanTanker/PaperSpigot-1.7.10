/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptDeploy
/*     */ {
/*  38 */   public static final EncryptDeploy NO_ENCRYPT = new EncryptDeploy(Mode.MODE_NO_ENCRYPT, true, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public static final EncryptDeploy ANNOTATION = new EncryptDeploy(Mode.MODE_ANNOTATION, true, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final EncryptDeploy ENCRYPT_DB = new EncryptDeploy(Mode.MODE_ENCRYPT, true, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static final EncryptDeploy ENCRYPT_CLIENT = new EncryptDeploy(Mode.MODE_ENCRYPT, false, 0);
/*     */   
/*     */   private final Mode mode;
/*     */   
/*     */   private final boolean dbEncrypt;
/*     */   
/*     */   private final int dbLength;
/*     */   
/*     */   public enum Mode
/*     */   {
/*  63 */     MODE_ENCRYPT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     MODE_NO_ENCRYPT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     MODE_ANNOTATION;
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
/*     */   public EncryptDeploy(Mode mode, boolean dbEncrypt, int dbLength) {
/*  92 */     this.mode = mode;
/*  93 */     this.dbEncrypt = dbEncrypt;
/*  94 */     this.dbLength = dbLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mode getMode() {
/* 101 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDbEncrypt() {
/* 111 */     return this.dbEncrypt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDbLength() {
/* 121 */     return this.dbLength;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\EncryptDeploy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */