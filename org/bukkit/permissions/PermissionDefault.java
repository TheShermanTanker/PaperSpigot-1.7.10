/*    */ package org.bukkit.permissions;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum PermissionDefault
/*    */ {
/* 10 */   TRUE(new String[] { "true" }),
/* 11 */   FALSE(new String[] { "false" }),
/* 12 */   OP(new String[] { "op", "isop", "operator", "isoperator", "admin", "isadmin" }),
/* 13 */   NOT_OP(new String[] { "!op", "notop", "!operator", "notoperator", "!admin", "notadmin" });
/*    */   
/*    */   static {
/* 16 */     lookup = new HashMap<String, PermissionDefault>();
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
/* 60 */     for (PermissionDefault value : values()) {
/* 61 */       for (String name : value.names)
/* 62 */         lookup.put(name, value); 
/*    */     } 
/*    */   }
/*    */   
/*    */   private final String[] names;
/*    */   private static final Map<String, PermissionDefault> lookup;
/*    */   
/*    */   PermissionDefault(String... names) {
/*    */     this.names = names;
/*    */   }
/*    */   
/*    */   public boolean getValue(boolean op) {
/*    */     switch (this) {
/*    */       case TRUE:
/*    */         return true;
/*    */       case FALSE:
/*    */         return false;
/*    */       case OP:
/*    */         return op;
/*    */       case NOT_OP:
/*    */         return !op;
/*    */     } 
/*    */     return false;
/*    */   }
/*    */   
/*    */   public static PermissionDefault getByName(String name) {
/*    */     return lookup.get(name.toLowerCase().replaceAll("[^a-z!]", ""));
/*    */   }
/*    */   
/*    */   public String toString() {
/*    */     return this.names[0];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\permissions\PermissionDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */