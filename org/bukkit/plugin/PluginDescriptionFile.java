/*      */ package org.bukkit.plugin;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.bukkit.permissions.Permission;
/*      */ import org.bukkit.permissions.PermissionDefault;
/*      */ import org.yaml.snakeyaml.Yaml;
/*      */ import org.yaml.snakeyaml.constructor.AbstractConstruct;
/*      */ import org.yaml.snakeyaml.constructor.BaseConstructor;
/*      */ import org.yaml.snakeyaml.constructor.SafeConstructor;
/*      */ import org.yaml.snakeyaml.nodes.Node;
/*      */ import org.yaml.snakeyaml.nodes.Tag;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class PluginDescriptionFile
/*      */ {
/*  178 */   private static final ThreadLocal<Yaml> YAML = new ThreadLocal<Yaml>()
/*      */     {
/*      */       protected Yaml initialValue() {
/*  181 */         return new Yaml((BaseConstructor)new SafeConstructor()
/*      */             {
/*      */             
/*      */             });
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  211 */   String rawName = null;
/*  212 */   private String name = null;
/*  213 */   private String main = null;
/*  214 */   private String classLoaderOf = null;
/*  215 */   private List<String> depend = (List<String>)ImmutableList.of();
/*  216 */   private List<String> softDepend = (List<String>)ImmutableList.of();
/*  217 */   private List<String> loadBefore = (List<String>)ImmutableList.of();
/*  218 */   private String version = null;
/*  219 */   private Map<String, Map<String, Object>> commands = null;
/*  220 */   private String description = null;
/*  221 */   private List<String> authors = null;
/*  222 */   private String website = null;
/*  223 */   private String prefix = null;
/*      */   private boolean database = false;
/*  225 */   private PluginLoadOrder order = PluginLoadOrder.POSTWORLD;
/*  226 */   private List<Permission> permissions = null;
/*  227 */   private Map<?, ?> lazyPermissions = null;
/*  228 */   private PermissionDefault defaultPerm = PermissionDefault.OP;
/*  229 */   private Set<PluginAwareness> awareness = (Set<PluginAwareness>)ImmutableSet.of();
/*      */   
/*      */   public PluginDescriptionFile(InputStream stream) throws InvalidDescriptionException {
/*  232 */     loadMap(asMap(((Yaml)YAML.get()).load(stream)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PluginDescriptionFile(Reader reader) throws InvalidDescriptionException {
/*  243 */     loadMap(asMap(((Yaml)YAML.get()).load(reader)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PluginDescriptionFile(String pluginName, String pluginVersion, String mainClass) {
/*  254 */     this.name = pluginName.replace(' ', '_');
/*  255 */     this.version = pluginVersion;
/*  256 */     this.main = mainClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  285 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/*  305 */     return this.version;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMain() {
/*  331 */     return this.main;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  350 */     return this.description;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PluginLoadOrder getLoad() {
/*  373 */     return this.order;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getAuthors() {
/*  408 */     return this.authors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getWebsite() {
/*  427 */     return this.website;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDatabaseEnabled() {
/*  446 */     return this.database;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getDepend() {
/*  476 */     return this.depend;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getSoftDepend() {
/*  505 */     return this.softDepend;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getLoadBefore() {
/*  534 */     return this.loadBefore;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPrefix() {
/*  553 */     return this.prefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Map<String, Object>> getCommands() {
/*  672 */     return this.commands;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Permission> getPermissions() {
/*  781 */     if (this.permissions == null) {
/*  782 */       if (this.lazyPermissions == null) {
/*  783 */         this.permissions = (List<Permission>)ImmutableList.of();
/*      */       } else {
/*  785 */         this.permissions = (List<Permission>)ImmutableList.copyOf(Permission.loadPermissions(this.lazyPermissions, "Permission node '%s' in plugin description file for " + getFullName() + " is invalid", this.defaultPerm));
/*  786 */         this.lazyPermissions = null;
/*      */       } 
/*      */     }
/*  789 */     return this.permissions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PermissionDefault getPermissionDefault() {
/*  810 */     return this.defaultPerm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<PluginAwareness> getAwareness() {
/*  849 */     return this.awareness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFullName() {
/*  860 */     return this.name + " v" + this.version;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public String getClassLoaderOf() {
/*  868 */     return this.classLoaderOf;
/*      */   }
/*      */   
/*      */   public void setDatabaseEnabled(boolean database) {
/*  872 */     this.database = database;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(Writer writer) {
/*  881 */     ((Yaml)YAML.get()).dump(saveMap(), writer);
/*      */   }
/*      */   
/*      */   private void loadMap(Map<?, ?> map) throws InvalidDescriptionException {
/*      */     try {
/*  886 */       this.name = this.rawName = map.get("name").toString();
/*      */       
/*  888 */       if (!this.name.matches("^[A-Za-z0-9 _.-]+$")) {
/*  889 */         throw new InvalidDescriptionException("name '" + this.name + "' contains invalid characters.");
/*      */       }
/*  891 */       this.name = this.name.replace(' ', '_');
/*  892 */     } catch (NullPointerException ex) {
/*  893 */       throw new InvalidDescriptionException(ex, "name is not defined");
/*  894 */     } catch (ClassCastException ex) {
/*  895 */       throw new InvalidDescriptionException(ex, "name is of wrong type");
/*      */     } 
/*      */     
/*      */     try {
/*  899 */       this.version = map.get("version").toString();
/*  900 */     } catch (NullPointerException ex) {
/*  901 */       throw new InvalidDescriptionException(ex, "version is not defined");
/*  902 */     } catch (ClassCastException ex) {
/*  903 */       throw new InvalidDescriptionException(ex, "version is of wrong type");
/*      */     } 
/*      */     
/*      */     try {
/*  907 */       this.main = map.get("main").toString();
/*  908 */       if (this.main.startsWith("org.bukkit.")) {
/*  909 */         throw new InvalidDescriptionException("main may not be within the org.bukkit namespace");
/*      */       }
/*  911 */     } catch (NullPointerException ex) {
/*  912 */       throw new InvalidDescriptionException(ex, "main is not defined");
/*  913 */     } catch (ClassCastException ex) {
/*  914 */       throw new InvalidDescriptionException(ex, "main is of wrong type");
/*      */     } 
/*      */     
/*  917 */     if (map.get("commands") != null) {
/*  918 */       ImmutableMap.Builder<String, Map<String, Object>> commandsBuilder = ImmutableMap.builder();
/*      */       try {
/*  920 */         for (Map.Entry<?, ?> command : (Iterable<Map.Entry<?, ?>>)((Map)map.get("commands")).entrySet()) {
/*  921 */           ImmutableMap.Builder<String, Object> commandBuilder = ImmutableMap.builder();
/*  922 */           if (command.getValue() != null) {
/*  923 */             for (Map.Entry<?, ?> commandEntry : (Iterable<Map.Entry<?, ?>>)((Map)command.getValue()).entrySet()) {
/*  924 */               if (commandEntry.getValue() instanceof Iterable) {
/*      */                 
/*  926 */                 ImmutableList.Builder<Object> commandSubList = ImmutableList.builder();
/*  927 */                 for (Object commandSubListItem : commandEntry.getValue()) {
/*  928 */                   if (commandSubListItem != null) {
/*  929 */                     commandSubList.add(commandSubListItem);
/*      */                   }
/*      */                 } 
/*  932 */                 commandBuilder.put(commandEntry.getKey().toString(), commandSubList.build()); continue;
/*  933 */               }  if (commandEntry.getValue() != null) {
/*  934 */                 commandBuilder.put(commandEntry.getKey().toString(), commandEntry.getValue());
/*      */               }
/*      */             } 
/*      */           }
/*  938 */           commandsBuilder.put(command.getKey().toString(), commandBuilder.build());
/*      */         } 
/*  940 */       } catch (ClassCastException ex) {
/*  941 */         throw new InvalidDescriptionException(ex, "commands are of wrong type");
/*      */       } 
/*  943 */       this.commands = (Map<String, Map<String, Object>>)commandsBuilder.build();
/*      */     } 
/*      */     
/*  946 */     if (map.get("class-loader-of") != null) {
/*  947 */       this.classLoaderOf = map.get("class-loader-of").toString();
/*      */     }
/*      */     
/*  950 */     this.depend = makePluginNameList(map, "depend");
/*  951 */     this.softDepend = makePluginNameList(map, "softdepend");
/*  952 */     this.loadBefore = makePluginNameList(map, "loadbefore");
/*      */     
/*  954 */     if (map.get("database") != null) {
/*      */       try {
/*  956 */         this.database = ((Boolean)map.get("database")).booleanValue();
/*  957 */       } catch (ClassCastException ex) {
/*  958 */         throw new InvalidDescriptionException(ex, "database is of wrong type");
/*      */       } 
/*      */     }
/*      */     
/*  962 */     if (map.get("website") != null) {
/*  963 */       this.website = map.get("website").toString();
/*      */     }
/*      */     
/*  966 */     if (map.get("description") != null) {
/*  967 */       this.description = map.get("description").toString();
/*      */     }
/*      */     
/*  970 */     if (map.get("load") != null) {
/*      */       try {
/*  972 */         this.order = PluginLoadOrder.valueOf(((String)map.get("load")).toUpperCase().replaceAll("\\W", ""));
/*  973 */       } catch (ClassCastException ex) {
/*  974 */         throw new InvalidDescriptionException(ex, "load is of wrong type");
/*  975 */       } catch (IllegalArgumentException ex) {
/*  976 */         throw new InvalidDescriptionException(ex, "load is not a valid choice");
/*      */       } 
/*      */     }
/*      */     
/*  980 */     if (map.get("authors") != null) {
/*  981 */       ImmutableList.Builder<String> authorsBuilder = ImmutableList.builder();
/*  982 */       if (map.get("author") != null) {
/*  983 */         authorsBuilder.add(map.get("author").toString());
/*      */       }
/*      */       try {
/*  986 */         for (Object o : map.get("authors")) {
/*  987 */           authorsBuilder.add(o.toString());
/*      */         }
/*  989 */       } catch (ClassCastException ex) {
/*  990 */         throw new InvalidDescriptionException(ex, "authors are of wrong type");
/*  991 */       } catch (NullPointerException ex) {
/*  992 */         throw new InvalidDescriptionException(ex, "authors are improperly defined");
/*      */       } 
/*  994 */       this.authors = (List<String>)authorsBuilder.build();
/*  995 */     } else if (map.get("author") != null) {
/*  996 */       this.authors = (List<String>)ImmutableList.of(map.get("author").toString());
/*      */     } else {
/*  998 */       this.authors = (List<String>)ImmutableList.of();
/*      */     } 
/*      */     
/* 1001 */     if (map.get("default-permission") != null) {
/*      */       try {
/* 1003 */         this.defaultPerm = PermissionDefault.getByName(map.get("default-permission").toString());
/* 1004 */       } catch (ClassCastException ex) {
/* 1005 */         throw new InvalidDescriptionException(ex, "default-permission is of wrong type");
/* 1006 */       } catch (IllegalArgumentException ex) {
/* 1007 */         throw new InvalidDescriptionException(ex, "default-permission is not a valid choice");
/*      */       } 
/*      */     }
/*      */     
/* 1011 */     if (map.get("awareness") instanceof Iterable) {
/* 1012 */       Set<PluginAwareness> awareness = new HashSet<PluginAwareness>();
/*      */       try {
/* 1014 */         for (Object o : map.get("awareness")) {
/* 1015 */           awareness.add((PluginAwareness)o);
/*      */         }
/* 1017 */       } catch (ClassCastException ex) {
/* 1018 */         throw new InvalidDescriptionException(ex, "awareness has wrong type");
/*      */       } 
/* 1020 */       this.awareness = (Set<PluginAwareness>)ImmutableSet.copyOf(awareness);
/*      */     } 
/*      */     
/*      */     try {
/* 1024 */       this.lazyPermissions = (Map<?, ?>)map.get("permissions");
/* 1025 */     } catch (ClassCastException ex) {
/* 1026 */       throw new InvalidDescriptionException(ex, "permissions are of the wrong type");
/*      */     } 
/*      */     
/* 1029 */     if (map.get("prefix") != null) {
/* 1030 */       this.prefix = map.get("prefix").toString();
/*      */     }
/*      */   }
/*      */   
/*      */   private static List<String> makePluginNameList(Map<?, ?> map, String key) throws InvalidDescriptionException {
/* 1035 */     Object value = map.get(key);
/* 1036 */     if (value == null) {
/* 1037 */       return (List<String>)ImmutableList.of();
/*      */     }
/*      */     
/* 1040 */     ImmutableList.Builder<String> builder = ImmutableList.builder();
/*      */     try {
/* 1042 */       for (Object entry : value) {
/* 1043 */         builder.add(entry.toString().replace(' ', '_'));
/*      */       }
/* 1045 */     } catch (ClassCastException ex) {
/* 1046 */       throw new InvalidDescriptionException(ex, key + " is of wrong type");
/* 1047 */     } catch (NullPointerException ex) {
/* 1048 */       throw new InvalidDescriptionException(ex, "invalid " + key + " format");
/*      */     } 
/* 1050 */     return (List<String>)builder.build();
/*      */   }
/*      */   
/*      */   private Map<String, Object> saveMap() {
/* 1054 */     Map<String, Object> map = new HashMap<String, Object>();
/*      */     
/* 1056 */     map.put("name", this.name);
/* 1057 */     map.put("main", this.main);
/* 1058 */     map.put("version", this.version);
/* 1059 */     map.put("database", Boolean.valueOf(this.database));
/* 1060 */     map.put("order", this.order.toString());
/* 1061 */     map.put("default-permission", this.defaultPerm.toString());
/*      */     
/* 1063 */     if (this.commands != null) {
/* 1064 */       map.put("command", this.commands);
/*      */     }
/* 1066 */     if (this.depend != null) {
/* 1067 */       map.put("depend", this.depend);
/*      */     }
/* 1069 */     if (this.softDepend != null) {
/* 1070 */       map.put("softdepend", this.softDepend);
/*      */     }
/* 1072 */     if (this.website != null) {
/* 1073 */       map.put("website", this.website);
/*      */     }
/* 1075 */     if (this.description != null) {
/* 1076 */       map.put("description", this.description);
/*      */     }
/*      */     
/* 1079 */     if (this.authors.size() == 1) {
/* 1080 */       map.put("author", this.authors.get(0));
/* 1081 */     } else if (this.authors.size() > 1) {
/* 1082 */       map.put("authors", this.authors);
/*      */     } 
/*      */     
/* 1085 */     if (this.classLoaderOf != null) {
/* 1086 */       map.put("class-loader-of", this.classLoaderOf);
/*      */     }
/*      */     
/* 1089 */     if (this.prefix != null) {
/* 1090 */       map.put("prefix", this.prefix);
/*      */     }
/*      */     
/* 1093 */     return map;
/*      */   }
/*      */   
/*      */   private Map<?, ?> asMap(Object object) throws InvalidDescriptionException {
/* 1097 */     if (object instanceof Map) {
/* 1098 */       return (Map<?, ?>)object;
/*      */     }
/* 1100 */     throw new InvalidDescriptionException(object + " is not properly structured.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public String getRawName() {
/* 1108 */     return this.rawName;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\PluginDescriptionFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */