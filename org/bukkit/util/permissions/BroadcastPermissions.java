/*    */ package org.bukkit.util.permissions;
/*    */ 
/*    */ import org.bukkit.permissions.Permission;
/*    */ import org.bukkit.permissions.PermissionDefault;
/*    */ 
/*    */ 
/*    */ public final class BroadcastPermissions
/*    */ {
/*    */   private static final String ROOT = "bukkit.broadcast";
/*    */   private static final String PREFIX = "bukkit.broadcast.";
/*    */   
/*    */   public static Permission registerPermissions(Permission parent) {
/* 13 */     Permission broadcasts = DefaultPermissions.registerPermission("bukkit.broadcast", "Allows the user to receive all broadcast messages", parent);
/*    */     
/* 15 */     DefaultPermissions.registerPermission("bukkit.broadcast.admin", "Allows the user to receive administrative broadcasts", PermissionDefault.OP, broadcasts);
/* 16 */     DefaultPermissions.registerPermission("bukkit.broadcast.user", "Allows the user to receive user broadcasts", PermissionDefault.TRUE, broadcasts);
/*    */     
/* 18 */     broadcasts.recalculatePermissibles();
/*    */     
/* 20 */     return broadcasts;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\permissions\BroadcastPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */