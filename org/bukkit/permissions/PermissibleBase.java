/*     */ package org.bukkit.permissions;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ public class PermissibleBase
/*     */   implements Permissible
/*     */ {
/*  17 */   private ServerOperator opable = null;
/*  18 */   private Permissible parent = this;
/*  19 */   private final List<PermissionAttachment> attachments = new LinkedList<PermissionAttachment>();
/*  20 */   private final Map<String, PermissionAttachmentInfo> permissions = new HashMap<String, PermissionAttachmentInfo>();
/*     */   
/*     */   public PermissibleBase(ServerOperator opable) {
/*  23 */     this.opable = opable;
/*     */     
/*  25 */     if (opable instanceof Permissible) {
/*  26 */       this.parent = (Permissible)opable;
/*     */     }
/*     */     
/*  29 */     recalculatePermissions();
/*     */   }
/*     */   
/*     */   public boolean isOp() {
/*  33 */     if (this.opable == null) {
/*  34 */       return false;
/*     */     }
/*  36 */     return this.opable.isOp();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOp(boolean value) {
/*  41 */     if (this.opable == null) {
/*  42 */       throw new UnsupportedOperationException("Cannot change op value as no ServerOperator is set");
/*     */     }
/*  44 */     this.opable.setOp(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPermissionSet(String name) {
/*  49 */     if (name == null) {
/*  50 */       throw new IllegalArgumentException("Permission name cannot be null");
/*     */     }
/*     */     
/*  53 */     return this.permissions.containsKey(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public boolean isPermissionSet(Permission perm) {
/*  57 */     if (perm == null) {
/*  58 */       throw new IllegalArgumentException("Permission cannot be null");
/*     */     }
/*     */     
/*  61 */     return isPermissionSet(perm.getName());
/*     */   }
/*     */   
/*     */   public boolean hasPermission(String inName) {
/*  65 */     if (inName == null) {
/*  66 */       throw new IllegalArgumentException("Permission name cannot be null");
/*     */     }
/*     */     
/*  69 */     String name = inName.toLowerCase();
/*     */     
/*  71 */     if (isPermissionSet(name)) {
/*  72 */       return ((PermissionAttachmentInfo)this.permissions.get(name)).getValue();
/*     */     }
/*  74 */     Permission perm = Bukkit.getServer().getPluginManager().getPermission(name);
/*     */     
/*  76 */     if (perm != null) {
/*  77 */       return perm.getDefault().getValue(isOp());
/*     */     }
/*  79 */     return Permission.DEFAULT_PERMISSION.getValue(isOp());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPermission(Permission perm) {
/*  85 */     if (perm == null) {
/*  86 */       throw new IllegalArgumentException("Permission cannot be null");
/*     */     }
/*     */     
/*  89 */     String name = perm.getName().toLowerCase();
/*     */     
/*  91 */     if (isPermissionSet(name)) {
/*  92 */       return ((PermissionAttachmentInfo)this.permissions.get(name)).getValue();
/*     */     }
/*  94 */     return perm.getDefault().getValue(isOp());
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
/*  98 */     if (name == null)
/*  99 */       throw new IllegalArgumentException("Permission name cannot be null"); 
/* 100 */     if (plugin == null)
/* 101 */       throw new IllegalArgumentException("Plugin cannot be null"); 
/* 102 */     if (!plugin.isEnabled()) {
/* 103 */       throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
/*     */     }
/*     */     
/* 106 */     PermissionAttachment result = addAttachment(plugin);
/* 107 */     result.setPermission(name, value);
/*     */     
/* 109 */     recalculatePermissions();
/*     */     
/* 111 */     return result;
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin) {
/* 115 */     if (plugin == null)
/* 116 */       throw new IllegalArgumentException("Plugin cannot be null"); 
/* 117 */     if (!plugin.isEnabled()) {
/* 118 */       throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
/*     */     }
/*     */     
/* 121 */     PermissionAttachment result = new PermissionAttachment(plugin, this.parent);
/*     */     
/* 123 */     this.attachments.add(result);
/* 124 */     recalculatePermissions();
/*     */     
/* 126 */     return result;
/*     */   }
/*     */   
/*     */   public void removeAttachment(PermissionAttachment attachment) {
/* 130 */     if (attachment == null) {
/* 131 */       throw new IllegalArgumentException("Attachment cannot be null");
/*     */     }
/*     */     
/* 134 */     if (this.attachments.contains(attachment)) {
/* 135 */       this.attachments.remove(attachment);
/* 136 */       PermissionRemovedExecutor ex = attachment.getRemovalCallback();
/*     */       
/* 138 */       if (ex != null) {
/* 139 */         ex.attachmentRemoved(attachment);
/*     */       }
/*     */       
/* 142 */       recalculatePermissions();
/*     */     } else {
/* 144 */       throw new IllegalArgumentException("Given attachment is not part of Permissible object " + this.parent);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void recalculatePermissions() {
/* 149 */     clearPermissions();
/* 150 */     Set<Permission> defaults = Bukkit.getServer().getPluginManager().getDefaultPermissions(isOp());
/* 151 */     Bukkit.getServer().getPluginManager().subscribeToDefaultPerms(isOp(), this.parent);
/*     */     
/* 153 */     for (Permission perm : defaults) {
/* 154 */       String name = perm.getName().toLowerCase();
/* 155 */       this.permissions.put(name, new PermissionAttachmentInfo(this.parent, name, null, true));
/* 156 */       Bukkit.getServer().getPluginManager().subscribeToPermission(name, this.parent);
/* 157 */       calculateChildPermissions(perm.getChildren(), false, null);
/*     */     } 
/*     */     
/* 160 */     for (PermissionAttachment attachment : this.attachments) {
/* 161 */       calculateChildPermissions(attachment.getPermissions(), false, attachment);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void clearPermissions() {
/* 166 */     Set<String> perms = this.permissions.keySet();
/*     */     
/* 168 */     for (String name : perms) {
/* 169 */       Bukkit.getServer().getPluginManager().unsubscribeFromPermission(name, this.parent);
/*     */     }
/*     */     
/* 172 */     Bukkit.getServer().getPluginManager().unsubscribeFromDefaultPerms(false, this.parent);
/* 173 */     Bukkit.getServer().getPluginManager().unsubscribeFromDefaultPerms(true, this.parent);
/*     */     
/* 175 */     this.permissions.clear();
/*     */   }
/*     */   
/*     */   private void calculateChildPermissions(Map<String, Boolean> children, boolean invert, PermissionAttachment attachment) {
/* 179 */     Set<String> keys = children.keySet();
/*     */     
/* 181 */     for (String name : keys) {
/* 182 */       Permission perm = Bukkit.getServer().getPluginManager().getPermission(name);
/* 183 */       boolean value = ((Boolean)children.get(name)).booleanValue() ^ invert;
/* 184 */       String lname = name.toLowerCase();
/*     */       
/* 186 */       this.permissions.put(lname, new PermissionAttachmentInfo(this.parent, lname, attachment, value));
/* 187 */       Bukkit.getServer().getPluginManager().subscribeToPermission(name, this.parent);
/*     */       
/* 189 */       if (perm != null) {
/* 190 */         calculateChildPermissions(perm.getChildren(), !value, attachment);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
/* 196 */     if (name == null)
/* 197 */       throw new IllegalArgumentException("Permission name cannot be null"); 
/* 198 */     if (plugin == null)
/* 199 */       throw new IllegalArgumentException("Plugin cannot be null"); 
/* 200 */     if (!plugin.isEnabled()) {
/* 201 */       throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
/*     */     }
/*     */     
/* 204 */     PermissionAttachment result = addAttachment(plugin, ticks);
/*     */     
/* 206 */     if (result != null) {
/* 207 */       result.setPermission(name, value);
/*     */     }
/*     */     
/* 210 */     return result;
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
/* 214 */     if (plugin == null)
/* 215 */       throw new IllegalArgumentException("Plugin cannot be null"); 
/* 216 */     if (!plugin.isEnabled()) {
/* 217 */       throw new IllegalArgumentException("Plugin " + plugin.getDescription().getFullName() + " is disabled");
/*     */     }
/*     */     
/* 220 */     PermissionAttachment result = addAttachment(plugin);
/*     */     
/* 222 */     if (Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new RemoveAttachmentRunnable(result), ticks) == -1) {
/* 223 */       Bukkit.getServer().getLogger().log(Level.WARNING, "Could not add PermissionAttachment to " + this.parent + " for plugin " + plugin.getDescription().getFullName() + ": Scheduler returned -1");
/* 224 */       result.remove();
/* 225 */       return null;
/*     */     } 
/* 227 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<PermissionAttachmentInfo> getEffectivePermissions() {
/* 232 */     return new HashSet<PermissionAttachmentInfo>(this.permissions.values());
/*     */   }
/*     */   
/*     */   private class RemoveAttachmentRunnable implements Runnable {
/*     */     private PermissionAttachment attachment;
/*     */     
/*     */     public RemoveAttachmentRunnable(PermissionAttachment attachment) {
/* 239 */       this.attachment = attachment;
/*     */     }
/*     */     
/*     */     public void run() {
/* 243 */       this.attachment.remove();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\permissions\PermissibleBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */