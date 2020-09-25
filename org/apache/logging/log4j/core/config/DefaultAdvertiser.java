/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.net.Advertiser;
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
/*    */ @Plugin(name = "default", category = "Core", elementType = "advertiser", printObject = false)
/*    */ public class DefaultAdvertiser
/*    */   implements Advertiser
/*    */ {
/*    */   public Object advertise(Map<String, String> properties) {
/* 36 */     return null;
/*    */   }
/*    */   
/*    */   public void unadvertise(Object advertisedObject) {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\DefaultAdvertiser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */