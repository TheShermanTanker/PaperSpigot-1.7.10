/*    */ package org.apache.logging.log4j.core.config.plugins;
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
/*    */ public class PluginType<T>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4743255148794846612L;
/*    */   private final Class<T> pluginClass;
/*    */   private final String elementName;
/*    */   private final boolean printObject;
/*    */   private final boolean deferChildren;
/*    */   
/*    */   public PluginType(Class<T> clazz, String name, boolean printObj, boolean deferChildren) {
/* 37 */     this.pluginClass = clazz;
/* 38 */     this.elementName = name;
/* 39 */     this.printObject = printObj;
/* 40 */     this.deferChildren = deferChildren;
/*    */   }
/*    */   
/*    */   public Class<T> getPluginClass() {
/* 44 */     return this.pluginClass;
/*    */   }
/*    */   
/*    */   public String getElementName() {
/* 48 */     return this.elementName;
/*    */   }
/*    */   
/*    */   public boolean isObjectPrintable() {
/* 52 */     return this.printObject;
/*    */   }
/*    */   
/*    */   public boolean isDeferChildren() {
/* 56 */     return this.deferChildren;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\PluginType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */