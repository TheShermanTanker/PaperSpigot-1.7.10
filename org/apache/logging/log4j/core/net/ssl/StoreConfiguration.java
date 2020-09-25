/*    */ package org.apache.logging.log4j.core.net.ssl;
/*    */ 
/*    */ import org.apache.logging.log4j.status.StatusLogger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StoreConfiguration
/*    */ {
/* 25 */   protected static final StatusLogger LOGGER = StatusLogger.getLogger();
/*    */   
/*    */   private String location;
/*    */   private String password;
/*    */   
/*    */   public StoreConfiguration(String location, String password) {
/* 31 */     this.location = location;
/* 32 */     this.password = password;
/*    */   }
/*    */   
/*    */   public String getLocation() {
/* 36 */     return this.location;
/*    */   }
/*    */   
/*    */   public void setLocation(String location) {
/* 40 */     this.location = location;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 44 */     return this.password;
/*    */   }
/*    */   
/*    */   public char[] getPasswordAsCharArray() {
/* 48 */     if (this.password == null) {
/* 49 */       return null;
/*    */     }
/* 51 */     return this.password.toCharArray();
/*    */   }
/*    */   
/*    */   public void setPassword(String password) {
/* 55 */     this.password = password;
/*    */   }
/*    */   
/*    */   public boolean equals(StoreConfiguration config) {
/* 59 */     if (config == null) {
/* 60 */       return false;
/*    */     }
/* 62 */     boolean locationEquals = false;
/* 63 */     boolean passwordEquals = false;
/*    */     
/* 65 */     if (this.location != null) {
/* 66 */       locationEquals = this.location.equals(config.location);
/*    */     } else {
/* 68 */       locationEquals = (this.location == config.location);
/*    */     } 
/* 70 */     if (this.password != null) {
/* 71 */       passwordEquals = this.password.equals(config.password);
/*    */     } else {
/* 73 */       passwordEquals = (this.password == config.password);
/*    */     } 
/* 75 */     return (locationEquals && passwordEquals);
/*    */   }
/*    */   
/*    */   protected void load() throws StoreConfigurationException {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\ssl\StoreConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */