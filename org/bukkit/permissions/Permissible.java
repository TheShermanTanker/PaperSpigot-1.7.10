package org.bukkit.permissions;

import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface Permissible extends ServerOperator {
  boolean isPermissionSet(String paramString);
  
  boolean isPermissionSet(Permission paramPermission);
  
  boolean hasPermission(String paramString);
  
  boolean hasPermission(Permission paramPermission);
  
  PermissionAttachment addAttachment(Plugin paramPlugin, String paramString, boolean paramBoolean);
  
  PermissionAttachment addAttachment(Plugin paramPlugin);
  
  PermissionAttachment addAttachment(Plugin paramPlugin, String paramString, boolean paramBoolean, int paramInt);
  
  PermissionAttachment addAttachment(Plugin paramPlugin, int paramInt);
  
  void removeAttachment(PermissionAttachment paramPermissionAttachment);
  
  void recalculatePermissions();
  
  Set<PermissionAttachmentInfo> getEffectivePermissions();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\permissions\Permissible.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */