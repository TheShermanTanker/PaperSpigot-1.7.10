/*     */ package com.google.common.net;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.net.InetAddress;
/*     */ import java.text.ParseException;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public final class HostSpecifier
/*     */ {
/*     */   private final String canonicalForm;
/*     */   
/*     */   private HostSpecifier(String canonicalForm) {
/*  57 */     this.canonicalForm = canonicalForm;
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
/*     */   public static HostSpecifier fromValid(String specifier) {
/*  80 */     HostAndPort parsedHost = HostAndPort.fromString(specifier);
/*  81 */     Preconditions.checkArgument(!parsedHost.hasPort());
/*  82 */     String host = parsedHost.getHostText();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     InetAddress addr = null;
/*     */     try {
/*  90 */       addr = InetAddresses.forString(host);
/*  91 */     } catch (IllegalArgumentException e) {}
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (addr != null) {
/*  96 */       return new HostSpecifier(InetAddresses.toUriString(addr));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     InternetDomainName domain = InternetDomainName.from(host);
/*     */     
/* 104 */     if (domain.hasPublicSuffix()) {
/* 105 */       return new HostSpecifier(domain.name());
/*     */     }
/*     */     
/* 108 */     throw new IllegalArgumentException("Domain name does not have a recognized public suffix: " + host);
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
/*     */   public static HostSpecifier from(String specifier) throws ParseException {
/*     */     try {
/* 123 */       return fromValid(specifier);
/* 124 */     } catch (IllegalArgumentException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       ParseException parseException = new ParseException("Invalid host specifier: " + specifier, 0);
/*     */       
/* 131 */       parseException.initCause(e);
/* 132 */       throw parseException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValid(String specifier) {
/*     */     try {
/* 143 */       fromValid(specifier);
/* 144 */       return true;
/* 145 */     } catch (IllegalArgumentException e) {
/* 146 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/* 152 */     if (this == other) {
/* 153 */       return true;
/*     */     }
/*     */     
/* 156 */     if (other instanceof HostSpecifier) {
/* 157 */       HostSpecifier that = (HostSpecifier)other;
/* 158 */       return this.canonicalForm.equals(that.canonicalForm);
/*     */     } 
/*     */     
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 166 */     return this.canonicalForm.hashCode();
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
/*     */   public String toString() {
/* 178 */     return this.canonicalForm;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\net\HostSpecifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */