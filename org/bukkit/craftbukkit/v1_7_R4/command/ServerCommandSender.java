/*    */ package org.bukkit.craftbukkit.v1_7_R4.command;
/*    */ 
/*    */ import java.util.Set;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.permissions.PermissibleBase;
/*    */ import org.bukkit.permissions.Permission;
/*    */ import org.bukkit.permissions.PermissionAttachment;
/*    */ import org.bukkit.permissions.PermissionAttachmentInfo;
/*    */ import org.bukkit.permissions.ServerOperator;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public abstract class ServerCommandSender implements CommandSender {
/*    */   private static PermissibleBase blockPermInst;
/*    */   private final PermissibleBase perm;
/*    */   
/*    */   public ServerCommandSender() {
/* 19 */     if (this instanceof CraftBlockCommandSender) {
/* 20 */       if (blockPermInst == null) {
/* 21 */         blockPermInst = new PermissibleBase((ServerOperator)this);
/*    */       }
/* 23 */       this.perm = blockPermInst;
/*    */     } else {
/* 25 */       this.perm = new PermissibleBase((ServerOperator)this);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isPermissionSet(String name) {
/* 30 */     return this.perm.isPermissionSet(name);
/*    */   }
/*    */   
/*    */   public boolean isPermissionSet(Permission perm) {
/* 34 */     return this.perm.isPermissionSet(perm);
/*    */   }
/*    */   
/*    */   public boolean hasPermission(String name) {
/* 38 */     return this.perm.hasPermission(name);
/*    */   }
/*    */   
/*    */   public boolean hasPermission(Permission perm) {
/* 42 */     return this.perm.hasPermission(perm);
/*    */   }
/*    */   
/*    */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
/* 46 */     return this.perm.addAttachment(plugin, name, value);
/*    */   }
/*    */   
/*    */   public PermissionAttachment addAttachment(Plugin plugin) {
/* 50 */     return this.perm.addAttachment(plugin);
/*    */   }
/*    */   
/*    */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
/* 54 */     return this.perm.addAttachment(plugin, name, value, ticks);
/*    */   }
/*    */   
/*    */   public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
/* 58 */     return this.perm.addAttachment(plugin, ticks);
/*    */   }
/*    */   
/*    */   public void removeAttachment(PermissionAttachment attachment) {
/* 62 */     this.perm.removeAttachment(attachment);
/*    */   }
/*    */   
/*    */   public void recalculatePermissions() {
/* 66 */     this.perm.recalculatePermissions();
/*    */   }
/*    */   
/*    */   public Set<PermissionAttachmentInfo> getEffectivePermissions() {
/* 70 */     return this.perm.getEffectivePermissions();
/*    */   }
/*    */   
/*    */   public boolean isPlayer() {
/* 74 */     return false;
/*    */   }
/*    */   
/*    */   public Server getServer() {
/* 78 */     return Bukkit.getServer();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\command\ServerCommandSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */