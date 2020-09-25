/*    */ package org.apache.logging.log4j.core.impl;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StackTracePackageElement
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -2171069569241280505L;
/*    */   private final String location;
/*    */   private final String version;
/*    */   private final boolean isExact;
/*    */   
/*    */   public StackTracePackageElement(String location, String version, boolean exact) {
/* 41 */     this.location = location;
/* 42 */     this.version = version;
/* 43 */     this.isExact = exact;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocation() {
/* 51 */     return this.location;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 59 */     return this.version;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isExact() {
/* 67 */     return this.isExact;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 72 */     String exact = this.isExact ? "" : "~";
/* 73 */     return exact + "[" + this.location + ":" + this.version + "]";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\StackTracePackageElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */