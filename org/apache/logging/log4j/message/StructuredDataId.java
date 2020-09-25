/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StructuredDataId
/*     */   implements Serializable
/*     */ {
/*  29 */   public static final StructuredDataId TIME_QUALITY = new StructuredDataId("timeQuality", null, new String[] { "tzKnown", "isSynced", "syncAccuracy" });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   public static final StructuredDataId ORIGIN = new StructuredDataId("origin", null, new String[] { "ip", "enterpriseId", "software", "swVersion" });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   public static final StructuredDataId META = new StructuredDataId("meta", null, new String[] { "sequenceId", "sysUpTime", "language" });
/*     */   
/*     */   public static final int RESERVED = -1;
/*     */   
/*     */   private static final long serialVersionUID = 9031746276396249990L;
/*     */   
/*     */   private static final int MAX_LENGTH = 32;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final int enterpriseNumber;
/*     */   
/*     */   private final String[] required;
/*     */   
/*     */   private final String[] optional;
/*     */ 
/*     */   
/*     */   protected StructuredDataId(String name, String[] required, String[] optional) {
/*  57 */     int index = -1;
/*  58 */     if (name != null) {
/*  59 */       if (name.length() > 32) {
/*  60 */         throw new IllegalArgumentException(String.format("Length of id %s exceeds maximum of %d characters", new Object[] { name, Integer.valueOf(32) }));
/*     */       }
/*     */       
/*  63 */       index = name.indexOf("@");
/*     */     } 
/*     */     
/*  66 */     if (index > 0) {
/*  67 */       this.name = name.substring(0, index);
/*  68 */       this.enterpriseNumber = Integer.parseInt(name.substring(index + 1));
/*     */     } else {
/*  70 */       this.name = name;
/*  71 */       this.enterpriseNumber = -1;
/*     */     } 
/*  73 */     this.required = required;
/*  74 */     this.optional = optional;
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
/*     */   public StructuredDataId(String name, int enterpriseNumber, String[] required, String[] optional) {
/*  87 */     if (name == null) {
/*  88 */       throw new IllegalArgumentException("No structured id name was supplied");
/*     */     }
/*  90 */     if (name.contains("@")) {
/*  91 */       throw new IllegalArgumentException("Structured id name cannot contain an '@");
/*     */     }
/*  93 */     if (enterpriseNumber <= 0) {
/*  94 */       throw new IllegalArgumentException("No enterprise number was supplied");
/*     */     }
/*  96 */     this.name = name;
/*  97 */     this.enterpriseNumber = enterpriseNumber;
/*  98 */     String id = (enterpriseNumber < 0) ? name : (name + "@" + enterpriseNumber);
/*  99 */     if (id.length() > 32) {
/* 100 */       throw new IllegalArgumentException("Length of id exceeds maximum of 32 characters: " + id);
/*     */     }
/* 102 */     this.required = required;
/* 103 */     this.optional = optional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructuredDataId makeId(StructuredDataId id) {
/* 112 */     if (id == null) {
/* 113 */       return this;
/*     */     }
/* 115 */     return makeId(id.getName(), id.getEnterpriseNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructuredDataId makeId(String defaultId, int enterpriseNumber) {
/*     */     String id;
/*     */     String[] req;
/*     */     String[] opt;
/* 128 */     if (enterpriseNumber <= 0) {
/* 129 */       return this;
/*     */     }
/* 131 */     if (this.name != null) {
/* 132 */       id = this.name;
/* 133 */       req = this.required;
/* 134 */       opt = this.optional;
/*     */     } else {
/* 136 */       id = defaultId;
/* 137 */       req = null;
/* 138 */       opt = null;
/*     */     } 
/*     */     
/* 141 */     return new StructuredDataId(id, enterpriseNumber, req, opt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRequired() {
/* 149 */     return this.required;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getOptional() {
/* 157 */     return this.optional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 165 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnterpriseNumber() {
/* 173 */     return this.enterpriseNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReserved() {
/* 181 */     return (this.enterpriseNumber <= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 186 */     return isReserved() ? this.name : (this.name + "@" + this.enterpriseNumber);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\StructuredDataId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */