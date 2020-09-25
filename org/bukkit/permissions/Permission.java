/*     */ package org.bukkit.permissions;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Permission
/*     */ {
/*  19 */   public static final PermissionDefault DEFAULT_PERMISSION = PermissionDefault.OP;
/*     */   
/*     */   private final String name;
/*  22 */   private final Map<String, Boolean> children = new LinkedHashMap<String, Boolean>();
/*  23 */   private PermissionDefault defaultValue = DEFAULT_PERMISSION;
/*     */   private String description;
/*     */   
/*     */   public Permission(String name) {
/*  27 */     this(name, null, null, null);
/*     */   }
/*     */   
/*     */   public Permission(String name, String description) {
/*  31 */     this(name, description, null, null);
/*     */   }
/*     */   
/*     */   public Permission(String name, PermissionDefault defaultValue) {
/*  35 */     this(name, null, defaultValue, null);
/*     */   }
/*     */   
/*     */   public Permission(String name, String description, PermissionDefault defaultValue) {
/*  39 */     this(name, description, defaultValue, null);
/*     */   }
/*     */   
/*     */   public Permission(String name, Map<String, Boolean> children) {
/*  43 */     this(name, null, null, children);
/*     */   }
/*     */   
/*     */   public Permission(String name, String description, Map<String, Boolean> children) {
/*  47 */     this(name, description, null, children);
/*     */   }
/*     */   
/*     */   public Permission(String name, PermissionDefault defaultValue, Map<String, Boolean> children) {
/*  51 */     this(name, null, defaultValue, children);
/*     */   }
/*     */   
/*     */   public Permission(String name, String description, PermissionDefault defaultValue, Map<String, Boolean> children) {
/*  55 */     this.name = name;
/*  56 */     this.description = (description == null) ? "" : description;
/*     */     
/*  58 */     if (defaultValue != null) {
/*  59 */       this.defaultValue = defaultValue;
/*     */     }
/*     */     
/*  62 */     if (children != null) {
/*  63 */       this.children.putAll(children);
/*     */     }
/*     */     
/*  66 */     recalculatePermissibles();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  75 */     return this.name;
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
/*     */   public Map<String, Boolean> getChildren() {
/*  87 */     return this.children;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PermissionDefault getDefault() {
/*  96 */     return this.defaultValue;
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
/*     */   
/*     */   public void setDefault(PermissionDefault value) {
/* 110 */     if (this.defaultValue == null) {
/* 111 */       throw new IllegalArgumentException("Default value cannot be null");
/*     */     }
/*     */     
/* 114 */     this.defaultValue = value;
/* 115 */     recalculatePermissibles();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 124 */     return this.description;
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
/*     */   public void setDescription(String value) {
/* 136 */     if (value == null) {
/* 137 */       this.description = "";
/*     */     } else {
/* 139 */       this.description = value;
/*     */     } 
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
/*     */   public Set<Permissible> getPermissibles() {
/* 152 */     return Bukkit.getServer().getPluginManager().getPermissionSubscriptions(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recalculatePermissibles() {
/* 162 */     Set<Permissible> perms = getPermissibles();
/*     */     
/* 164 */     Bukkit.getServer().getPluginManager().recalculatePermissionDefaults(this);
/*     */     
/* 166 */     for (Permissible p : perms) {
/* 167 */       p.recalculatePermissions();
/*     */     }
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
/*     */   
/*     */   public Permission addParent(String name, boolean value) {
/* 182 */     PluginManager pm = Bukkit.getServer().getPluginManager();
/* 183 */     String lname = name.toLowerCase();
/*     */     
/* 185 */     Permission perm = pm.getPermission(lname);
/*     */     
/* 187 */     if (perm == null) {
/* 188 */       perm = new Permission(lname);
/* 189 */       pm.addPermission(perm);
/*     */     } 
/*     */     
/* 192 */     addParent(perm, value);
/*     */     
/* 194 */     return perm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addParent(Permission perm, boolean value) {
/* 204 */     perm.getChildren().put(getName(), Boolean.valueOf(value));
/* 205 */     perm.recalculatePermissibles();
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
/*     */   public static List<Permission> loadPermissions(Map<?, ?> data, String error, PermissionDefault def) {
/* 228 */     List<Permission> result = new ArrayList<Permission>();
/*     */     
/* 230 */     for (Map.Entry<?, ?> entry : data.entrySet()) {
/*     */       try {
/* 232 */         result.add(loadPermission(entry.getKey().toString(), (Map<?, ?>)entry.getValue(), def, result));
/* 233 */       } catch (Throwable ex) {
/* 234 */         Bukkit.getServer().getLogger().log(Level.SEVERE, String.format(error, new Object[] { entry.getKey() }), ex);
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     return result;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Permission loadPermission(String name, Map<String, Object> data) {
/* 258 */     return loadPermission(name, data, DEFAULT_PERMISSION, null);
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
/*     */   public static Permission loadPermission(String name, Map<?, ?> data, PermissionDefault def, List<Permission> output) {
/* 281 */     Validate.notNull(name, "Name cannot be null");
/* 282 */     Validate.notNull(data, "Data cannot be null");
/*     */     
/* 284 */     String desc = null;
/* 285 */     Map<String, Boolean> children = null;
/*     */     
/* 287 */     if (data.get("default") != null) {
/* 288 */       PermissionDefault value = PermissionDefault.getByName(data.get("default").toString());
/* 289 */       if (value != null) {
/* 290 */         def = value;
/*     */       } else {
/* 292 */         throw new IllegalArgumentException("'default' key contained unknown value");
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     if (data.get("children") != null) {
/* 297 */       Object childrenNode = data.get("children");
/* 298 */       if (childrenNode instanceof Iterable) {
/* 299 */         children = new LinkedHashMap<String, Boolean>();
/* 300 */         for (Object child : childrenNode) {
/* 301 */           if (child != null) {
/* 302 */             children.put(child.toString(), Boolean.TRUE);
/*     */           }
/*     */         } 
/* 305 */       } else if (childrenNode instanceof Map) {
/* 306 */         children = extractChildren((Map<?, ?>)childrenNode, name, def, output);
/*     */       } else {
/* 308 */         throw new IllegalArgumentException("'children' key is of wrong type");
/*     */       } 
/*     */     } 
/*     */     
/* 312 */     if (data.get("description") != null) {
/* 313 */       desc = data.get("description").toString();
/*     */     }
/*     */     
/* 316 */     return new Permission(name, desc, def, children);
/*     */   }
/*     */   
/*     */   private static Map<String, Boolean> extractChildren(Map<?, ?> input, String name, PermissionDefault def, List<Permission> output) {
/* 320 */     Map<String, Boolean> children = new LinkedHashMap<String, Boolean>();
/*     */     
/* 322 */     for (Map.Entry<?, ?> entry : input.entrySet()) {
/* 323 */       if (entry.getValue() instanceof Boolean) {
/* 324 */         children.put(entry.getKey().toString(), (Boolean)entry.getValue()); continue;
/* 325 */       }  if (entry.getValue() instanceof Map) {
/*     */         try {
/* 327 */           Permission perm = loadPermission(entry.getKey().toString(), (Map<?, ?>)entry.getValue(), def, output);
/* 328 */           children.put(perm.getName(), Boolean.TRUE);
/*     */           
/* 330 */           if (output != null) {
/* 331 */             output.add(perm);
/*     */           }
/* 333 */         } catch (Throwable ex) {
/* 334 */           throw new IllegalArgumentException("Permission node '" + entry.getKey().toString() + "' in child of " + name + " is invalid", ex);
/*     */         }  continue;
/*     */       } 
/* 337 */       throw new IllegalArgumentException("Child '" + entry.getKey().toString() + "' contains invalid value");
/*     */     } 
/*     */ 
/*     */     
/* 341 */     return children;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\permissions\Permission.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */