/*     */ package org.bukkit.permissions;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PermissionAttachment
/*     */ {
/*     */   private PermissionRemovedExecutor removed;
/*  13 */   private final Map<String, Boolean> permissions = new LinkedHashMap<String, Boolean>();
/*     */   private final Permissible permissible;
/*     */   private final Plugin plugin;
/*     */   
/*     */   public PermissionAttachment(Plugin plugin, Permissible permissible) {
/*  18 */     if (plugin == null)
/*  19 */       throw new IllegalArgumentException("Plugin cannot be null"); 
/*  20 */     if (!plugin.isEnabled()) {
/*  21 */       throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
/*     */     }
/*     */     
/*  24 */     this.permissible = permissible;
/*  25 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Plugin getPlugin() {
/*  34 */     return this.plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRemovalCallback(PermissionRemovedExecutor ex) {
/*  44 */     this.removed = ex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PermissionRemovedExecutor getRemovalCallback() {
/*  54 */     return this.removed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Permissible getPermissible() {
/*  63 */     return this.permissible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Boolean> getPermissions() {
/*  76 */     return new LinkedHashMap<String, Boolean>(this.permissions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPermission(String name, boolean value) {
/*  86 */     this.permissions.put(name.toLowerCase(), Boolean.valueOf(value));
/*  87 */     this.permissible.recalculatePermissions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPermission(Permission perm, boolean value) {
/*  97 */     setPermission(perm.getName(), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPermission(String name) {
/* 109 */     this.permissions.remove(name.toLowerCase());
/* 110 */     this.permissible.recalculatePermissions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetPermission(Permission perm) {
/* 122 */     unsetPermission(perm.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove() {
/*     */     try {
/* 133 */       this.permissible.removeAttachment(this);
/* 134 */       return true;
/* 135 */     } catch (IllegalArgumentException ex) {
/* 136 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\permissions\PermissionAttachment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */