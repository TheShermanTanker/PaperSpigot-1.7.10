/*    */ package org.bukkit.util.permissions;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.permissions.Permission;
/*    */ import org.bukkit.permissions.PermissionDefault;
/*    */ 
/*    */ 
/*    */ public final class DefaultPermissions
/*    */ {
/*    */   private static final String ROOT = "craftbukkit";
/*    */   private static final String LEGACY_PREFIX = "craft";
/*    */   
/*    */   public static Permission registerPermission(Permission perm) {
/* 15 */     return registerPermission(perm, true);
/*    */   }
/*    */   
/*    */   public static Permission registerPermission(Permission perm, boolean withLegacy) {
/* 19 */     Permission result = perm;
/*    */     
/*    */     try {
/* 22 */       Bukkit.getPluginManager().addPermission(perm);
/* 23 */     } catch (IllegalArgumentException ex) {
/* 24 */       result = Bukkit.getPluginManager().getPermission(perm.getName());
/*    */     } 
/*    */     
/* 27 */     if (withLegacy) {
/* 28 */       Permission legacy = new Permission("craft" + result.getName(), result.getDescription(), PermissionDefault.FALSE);
/* 29 */       legacy.getChildren().put(result.getName(), Boolean.valueOf(true));
/* 30 */       registerPermission(perm, false);
/*    */     } 
/*    */     
/* 33 */     return result;
/*    */   }
/*    */   
/*    */   public static Permission registerPermission(Permission perm, Permission parent) {
/* 37 */     parent.getChildren().put(perm.getName(), Boolean.valueOf(true));
/* 38 */     return registerPermission(perm);
/*    */   }
/*    */   
/*    */   public static Permission registerPermission(String name, String desc) {
/* 42 */     Permission perm = registerPermission(new Permission(name, desc));
/* 43 */     return perm;
/*    */   }
/*    */   
/*    */   public static Permission registerPermission(String name, String desc, Permission parent) {
/* 47 */     Permission perm = registerPermission(name, desc);
/* 48 */     parent.getChildren().put(perm.getName(), Boolean.valueOf(true));
/* 49 */     return perm;
/*    */   }
/*    */   
/*    */   public static Permission registerPermission(String name, String desc, PermissionDefault def) {
/* 53 */     Permission perm = registerPermission(new Permission(name, desc, def));
/* 54 */     return perm;
/*    */   }
/*    */   
/*    */   public static Permission registerPermission(String name, String desc, PermissionDefault def, Permission parent) {
/* 58 */     Permission perm = registerPermission(name, desc, def);
/* 59 */     parent.getChildren().put(perm.getName(), Boolean.valueOf(true));
/* 60 */     return perm;
/*    */   }
/*    */   
/*    */   public static Permission registerPermission(String name, String desc, PermissionDefault def, Map<String, Boolean> children) {
/* 64 */     Permission perm = registerPermission(new Permission(name, desc, def, children));
/* 65 */     return perm;
/*    */   }
/*    */   
/*    */   public static Permission registerPermission(String name, String desc, PermissionDefault def, Map<String, Boolean> children, Permission parent) {
/* 69 */     Permission perm = registerPermission(name, desc, def, children);
/* 70 */     parent.getChildren().put(perm.getName(), Boolean.valueOf(true));
/* 71 */     return perm;
/*    */   }
/*    */   
/*    */   public static void registerCorePermissions() {
/* 75 */     Permission parent = registerPermission("craftbukkit", "Gives the user the ability to use all CraftBukkit utilities and commands");
/*    */     
/* 77 */     CommandPermissions.registerPermissions(parent);
/* 78 */     BroadcastPermissions.registerPermissions(parent);
/*    */     
/* 80 */     parent.recalculatePermissibles();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\permissions\DefaultPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */