/*    */ package org.apache.logging.log4j.core.lookup;
/*    */ 
/*    */ import javax.naming.InitialContext;
/*    */ import javax.naming.NamingException;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*    */ @Plugin(name = "jndi", category = "Lookup")
/*    */ public class JndiLookup
/*    */   implements StrLookup
/*    */ {
/*    */   static final String CONTAINER_JNDI_RESOURCE_PATH_PREFIX = "java:comp/env/";
/*    */   
/*    */   public String lookup(String key) {
/* 41 */     return lookup(null, key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String lookup(LogEvent event, String key) {
/* 52 */     if (key == null) {
/* 53 */       return null;
/*    */     }
/*    */     
/*    */     try {
/* 57 */       InitialContext ctx = new InitialContext();
/* 58 */       return (String)ctx.lookup(convertJndiName(key));
/* 59 */     } catch (NamingException e) {
/* 60 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String convertJndiName(String jndiName) {
/* 72 */     if (!jndiName.startsWith("java:comp/env/") && jndiName.indexOf(':') == -1) {
/* 73 */       jndiName = "java:comp/env/" + jndiName;
/*    */     }
/*    */     
/* 76 */     return jndiName;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\JndiLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */