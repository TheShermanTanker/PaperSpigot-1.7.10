/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import javax.transaction.xa.Xid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MysqlXid
/*     */   implements Xid
/*     */ {
/*  36 */   int hash = 0;
/*     */   
/*     */   byte[] myBqual;
/*     */   
/*     */   int myFormatId;
/*     */   
/*     */   byte[] myGtrid;
/*     */   
/*     */   public MysqlXid(byte[] gtrid, byte[] bqual, int formatId) {
/*  45 */     this.myGtrid = gtrid;
/*  46 */     this.myBqual = bqual;
/*  47 */     this.myFormatId = formatId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object another) {
/*  52 */     if (another instanceof Xid) {
/*  53 */       Xid anotherAsXid = (Xid)another;
/*     */       
/*  55 */       if (this.myFormatId != anotherAsXid.getFormatId()) {
/*  56 */         return false;
/*     */       }
/*     */       
/*  59 */       byte[] otherBqual = anotherAsXid.getBranchQualifier();
/*  60 */       byte[] otherGtrid = anotherAsXid.getGlobalTransactionId();
/*     */       
/*  62 */       if (otherGtrid != null && otherGtrid.length == this.myGtrid.length) {
/*  63 */         int length = otherGtrid.length;
/*     */         int i;
/*  65 */         for (i = 0; i < length; i++) {
/*  66 */           if (otherGtrid[i] != this.myGtrid[i]) {
/*  67 */             return false;
/*     */           }
/*     */         } 
/*     */         
/*  71 */         if (otherBqual != null && otherBqual.length == this.myBqual.length) {
/*  72 */           length = otherBqual.length;
/*     */           
/*  74 */           for (i = 0; i < length; i++) {
/*  75 */             if (otherBqual[i] != this.myBqual[i]) {
/*  76 */               return false;
/*     */             }
/*     */           } 
/*     */         } else {
/*  80 */           return false;
/*     */         } 
/*     */         
/*  83 */         return true;
/*     */       } 
/*  85 */       return false;
/*     */     } 
/*     */     
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getBranchQualifier() {
/*  93 */     return this.myBqual;
/*     */   }
/*     */   
/*     */   public int getFormatId() {
/*  97 */     return this.myFormatId;
/*     */   }
/*     */   
/*     */   public byte[] getGlobalTransactionId() {
/* 101 */     return this.myGtrid;
/*     */   }
/*     */   
/*     */   public synchronized int hashCode() {
/* 105 */     if (this.hash == 0) {
/* 106 */       for (int i = 0; i < this.myGtrid.length; i++) {
/* 107 */         this.hash = 33 * this.hash + this.myGtrid[i];
/*     */       }
/*     */     }
/*     */     
/* 111 */     return this.hash;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\jdbc2\optional\MysqlXid.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */