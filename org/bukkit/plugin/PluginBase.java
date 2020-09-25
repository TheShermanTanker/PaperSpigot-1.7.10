/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PluginBase
/*    */   implements Plugin
/*    */ {
/*    */   public final int hashCode() {
/* 12 */     return getName().hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean equals(Object obj) {
/* 17 */     if (this == obj) {
/* 18 */       return true;
/*    */     }
/* 20 */     if (obj == null) {
/* 21 */       return false;
/*    */     }
/* 23 */     if (!(obj instanceof Plugin)) {
/* 24 */       return false;
/*    */     }
/* 26 */     return getName().equals(((Plugin)obj).getName());
/*    */   }
/*    */   
/*    */   public final String getName() {
/* 30 */     return getDescription().getName();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\PluginBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */