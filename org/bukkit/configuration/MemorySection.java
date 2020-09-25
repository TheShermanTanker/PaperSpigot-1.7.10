/*     */ package org.bukkit.configuration;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.NumberConversions;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MemorySection
/*     */   implements ConfigurationSection
/*     */ {
/*  22 */   protected final Map<String, Object> map = new LinkedHashMap<String, Object>();
/*     */ 
/*     */   
/*     */   private final Configuration root;
/*     */ 
/*     */   
/*     */   private final ConfigurationSection parent;
/*     */ 
/*     */   
/*     */   private final String path;
/*     */ 
/*     */   
/*     */   private final String fullPath;
/*     */ 
/*     */ 
/*     */   
/*     */   protected MemorySection() {
/*  39 */     if (!(this instanceof Configuration)) {
/*  40 */       throw new IllegalStateException("Cannot construct a root MemorySection when not a Configuration");
/*     */     }
/*     */     
/*  43 */     this.path = "";
/*  44 */     this.fullPath = "";
/*  45 */     this.parent = null;
/*  46 */     this.root = (Configuration)this;
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
/*     */   protected MemorySection(ConfigurationSection parent, String path) {
/*  59 */     Validate.notNull(parent, "Parent cannot be null");
/*  60 */     Validate.notNull(path, "Path cannot be null");
/*     */     
/*  62 */     this.path = path;
/*  63 */     this.parent = parent;
/*  64 */     this.root = parent.getRoot();
/*     */     
/*  66 */     Validate.notNull(this.root, "Path cannot be orphaned");
/*     */     
/*  68 */     this.fullPath = createPath(parent, path);
/*     */   }
/*     */   
/*     */   public Set<String> getKeys(boolean deep) {
/*  72 */     Set<String> result = new LinkedHashSet<String>();
/*     */     
/*  74 */     Configuration root = getRoot();
/*  75 */     if (root != null && root.options().copyDefaults()) {
/*  76 */       ConfigurationSection defaults = getDefaultSection();
/*     */       
/*  78 */       if (defaults != null) {
/*  79 */         result.addAll(defaults.getKeys(deep));
/*     */       }
/*     */     } 
/*     */     
/*  83 */     mapChildrenKeys(result, this, deep);
/*     */     
/*  85 */     return result;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getValues(boolean deep) {
/*  89 */     Map<String, Object> result = new LinkedHashMap<String, Object>();
/*     */     
/*  91 */     Configuration root = getRoot();
/*  92 */     if (root != null && root.options().copyDefaults()) {
/*  93 */       ConfigurationSection defaults = getDefaultSection();
/*     */       
/*  95 */       if (defaults != null) {
/*  96 */         result.putAll(defaults.getValues(deep));
/*     */       }
/*     */     } 
/*     */     
/* 100 */     mapChildrenValues(result, this, deep);
/*     */     
/* 102 */     return result;
/*     */   }
/*     */   
/*     */   public boolean contains(String path) {
/* 106 */     return (get(path) != null);
/*     */   }
/*     */   
/*     */   public boolean isSet(String path) {
/* 110 */     Configuration root = getRoot();
/* 111 */     if (root == null) {
/* 112 */       return false;
/*     */     }
/* 114 */     if (root.options().copyDefaults()) {
/* 115 */       return contains(path);
/*     */     }
/* 117 */     return (get(path, null) != null);
/*     */   }
/*     */   
/*     */   public String getCurrentPath() {
/* 121 */     return this.fullPath;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 125 */     return this.path;
/*     */   }
/*     */   
/*     */   public Configuration getRoot() {
/* 129 */     return this.root;
/*     */   }
/*     */   
/*     */   public ConfigurationSection getParent() {
/* 133 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void addDefault(String path, Object value) {
/* 137 */     Validate.notNull(path, "Path cannot be null");
/*     */     
/* 139 */     Configuration root = getRoot();
/* 140 */     if (root == null) {
/* 141 */       throw new IllegalStateException("Cannot add default without root");
/*     */     }
/* 143 */     if (root == this) {
/* 144 */       throw new UnsupportedOperationException("Unsupported addDefault(String, Object) implementation");
/*     */     }
/* 146 */     root.addDefault(createPath(this, path), value);
/*     */   }
/*     */   
/*     */   public ConfigurationSection getDefaultSection() {
/* 150 */     Configuration root = getRoot();
/* 151 */     Configuration defaults = (root == null) ? null : root.getDefaults();
/*     */     
/* 153 */     if (defaults != null && 
/* 154 */       defaults.isConfigurationSection(getCurrentPath())) {
/* 155 */       return defaults.getConfigurationSection(getCurrentPath());
/*     */     }
/*     */ 
/*     */     
/* 159 */     return null;
/*     */   }
/*     */   
/*     */   public void set(String path, Object value) {
/* 163 */     Validate.notEmpty(path, "Cannot set to an empty path");
/*     */     
/* 165 */     Configuration root = getRoot();
/* 166 */     if (root == null) {
/* 167 */       throw new IllegalStateException("Cannot use section without a root");
/*     */     }
/*     */     
/* 170 */     char separator = root.options().pathSeparator();
/*     */ 
/*     */     
/* 173 */     int i1 = -1;
/* 174 */     ConfigurationSection section = this; int i2;
/* 175 */     while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
/* 176 */       String node = path.substring(i2, i1);
/* 177 */       ConfigurationSection subSection = section.getConfigurationSection(node);
/* 178 */       if (subSection == null) {
/* 179 */         section = section.createSection(node); continue;
/*     */       } 
/* 181 */       section = subSection;
/*     */     } 
/*     */ 
/*     */     
/* 185 */     String key = path.substring(i2);
/* 186 */     if (section == this) {
/* 187 */       if (value == null) {
/* 188 */         this.map.remove(key);
/*     */       } else {
/* 190 */         this.map.put(key, value);
/*     */       } 
/*     */     } else {
/* 193 */       section.set(key, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object get(String path) {
/* 198 */     return get(path, getDefault(path));
/*     */   }
/*     */   
/*     */   public Object get(String path, Object def) {
/* 202 */     Validate.notNull(path, "Path cannot be null");
/*     */     
/* 204 */     if (path.length() == 0) {
/* 205 */       return this;
/*     */     }
/*     */     
/* 208 */     Configuration root = getRoot();
/* 209 */     if (root == null) {
/* 210 */       throw new IllegalStateException("Cannot access section without a root");
/*     */     }
/*     */     
/* 213 */     char separator = root.options().pathSeparator();
/*     */ 
/*     */     
/* 216 */     int i1 = -1;
/* 217 */     ConfigurationSection section = this; int i2;
/* 218 */     while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
/* 219 */       section = section.getConfigurationSection(path.substring(i2, i1));
/* 220 */       if (section == null) {
/* 221 */         return def;
/*     */       }
/*     */     } 
/*     */     
/* 225 */     String key = path.substring(i2);
/* 226 */     if (section == this) {
/* 227 */       Object result = this.map.get(key);
/* 228 */       return (result == null) ? def : result;
/*     */     } 
/* 230 */     return section.get(key, def);
/*     */   }
/*     */   
/*     */   public ConfigurationSection createSection(String path) {
/* 234 */     Validate.notEmpty(path, "Cannot create section at empty path");
/* 235 */     Configuration root = getRoot();
/* 236 */     if (root == null) {
/* 237 */       throw new IllegalStateException("Cannot create section without a root");
/*     */     }
/*     */     
/* 240 */     char separator = root.options().pathSeparator();
/*     */ 
/*     */     
/* 243 */     int i1 = -1;
/* 244 */     ConfigurationSection section = this; int i2;
/* 245 */     while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
/* 246 */       String node = path.substring(i2, i1);
/* 247 */       ConfigurationSection subSection = section.getConfigurationSection(node);
/* 248 */       if (subSection == null) {
/* 249 */         section = section.createSection(node); continue;
/*     */       } 
/* 251 */       section = subSection;
/*     */     } 
/*     */ 
/*     */     
/* 255 */     String key = path.substring(i2);
/* 256 */     if (section == this) {
/* 257 */       ConfigurationSection result = new MemorySection(this, key);
/* 258 */       this.map.put(key, result);
/* 259 */       return result;
/*     */     } 
/* 261 */     return section.createSection(key);
/*     */   }
/*     */   
/*     */   public ConfigurationSection createSection(String path, Map<?, ?> map) {
/* 265 */     ConfigurationSection section = createSection(path);
/*     */     
/* 267 */     for (Map.Entry<?, ?> entry : map.entrySet()) {
/* 268 */       if (entry.getValue() instanceof Map) {
/* 269 */         section.createSection(entry.getKey().toString(), (Map<?, ?>)entry.getValue()); continue;
/*     */       } 
/* 271 */       section.set(entry.getKey().toString(), entry.getValue());
/*     */     } 
/*     */ 
/*     */     
/* 275 */     return section;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(String path) {
/* 280 */     Object def = getDefault(path);
/* 281 */     return getString(path, (def != null) ? def.toString() : null);
/*     */   }
/*     */   
/*     */   public String getString(String path, String def) {
/* 285 */     Object val = get(path, def);
/* 286 */     return (val != null) ? val.toString() : def;
/*     */   }
/*     */   
/*     */   public boolean isString(String path) {
/* 290 */     Object val = get(path);
/* 291 */     return val instanceof String;
/*     */   }
/*     */   
/*     */   public int getInt(String path) {
/* 295 */     Object def = getDefault(path);
/* 296 */     return getInt(path, (def instanceof Number) ? NumberConversions.toInt(def) : 0);
/*     */   }
/*     */   
/*     */   public int getInt(String path, int def) {
/* 300 */     Object val = get(path, Integer.valueOf(def));
/* 301 */     return (val instanceof Number) ? NumberConversions.toInt(val) : def;
/*     */   }
/*     */   
/*     */   public boolean isInt(String path) {
/* 305 */     Object val = get(path);
/* 306 */     return val instanceof Integer;
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String path) {
/* 310 */     Object def = getDefault(path);
/* 311 */     return getBoolean(path, (def instanceof Boolean) ? ((Boolean)def).booleanValue() : false);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String path, boolean def) {
/* 315 */     Object val = get(path, Boolean.valueOf(def));
/* 316 */     return (val instanceof Boolean) ? ((Boolean)val).booleanValue() : def;
/*     */   }
/*     */   
/*     */   public boolean isBoolean(String path) {
/* 320 */     Object val = get(path);
/* 321 */     return val instanceof Boolean;
/*     */   }
/*     */   
/*     */   public double getDouble(String path) {
/* 325 */     Object def = getDefault(path);
/* 326 */     return getDouble(path, (def instanceof Number) ? NumberConversions.toDouble(def) : 0.0D);
/*     */   }
/*     */   
/*     */   public double getDouble(String path, double def) {
/* 330 */     Object val = get(path, Double.valueOf(def));
/* 331 */     return (val instanceof Number) ? NumberConversions.toDouble(val) : def;
/*     */   }
/*     */   
/*     */   public boolean isDouble(String path) {
/* 335 */     Object val = get(path);
/* 336 */     return val instanceof Double;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(String path) {
/* 341 */     Object def = getDefault(path);
/* 342 */     return getFloat(path, (def instanceof Float) ? NumberConversions.toFloat(def) : 0.0F);
/*     */   }
/*     */   
/*     */   public float getFloat(String path, float def) {
/* 346 */     Object val = get(path, Float.valueOf(def));
/* 347 */     return (val instanceof Float) ? NumberConversions.toFloat(val) : def;
/*     */   }
/*     */   
/*     */   public boolean isFloat(String path) {
/* 351 */     Object val = get(path);
/* 352 */     return val instanceof Float;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(String path) {
/* 357 */     Object def = getDefault(path);
/* 358 */     return getLong(path, (def instanceof Number) ? NumberConversions.toLong(def) : 0L);
/*     */   }
/*     */   
/*     */   public long getLong(String path, long def) {
/* 362 */     Object val = get(path, Long.valueOf(def));
/* 363 */     return (val instanceof Number) ? NumberConversions.toLong(val) : def;
/*     */   }
/*     */   
/*     */   public boolean isLong(String path) {
/* 367 */     Object val = get(path);
/* 368 */     return val instanceof Long;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<?> getList(String path) {
/* 373 */     Object def = getDefault(path);
/* 374 */     return getList(path, (def instanceof List) ? (List)def : null);
/*     */   }
/*     */   
/*     */   public List<?> getList(String path, List<?> def) {
/* 378 */     Object val = get(path, def);
/* 379 */     return (val instanceof List) ? (List)val : def;
/*     */   }
/*     */   
/*     */   public boolean isList(String path) {
/* 383 */     Object val = get(path);
/* 384 */     return val instanceof List;
/*     */   }
/*     */   
/*     */   public List<String> getStringList(String path) {
/* 388 */     List<?> list = getList(path);
/*     */     
/* 390 */     if (list == null) {
/* 391 */       return new ArrayList<String>(0);
/*     */     }
/*     */     
/* 394 */     List<String> result = new ArrayList<String>();
/*     */     
/* 396 */     for (Object object : list) {
/* 397 */       if (object instanceof String || isPrimitiveWrapper(object)) {
/* 398 */         result.add(String.valueOf(object));
/*     */       }
/*     */     } 
/*     */     
/* 402 */     return result;
/*     */   }
/*     */   
/*     */   public List<Integer> getIntegerList(String path) {
/* 406 */     List<?> list = getList(path);
/*     */     
/* 408 */     if (list == null) {
/* 409 */       return new ArrayList<Integer>(0);
/*     */     }
/*     */     
/* 412 */     List<Integer> result = new ArrayList<Integer>();
/*     */     
/* 414 */     for (Object object : list) {
/* 415 */       if (object instanceof Integer) {
/* 416 */         result.add((Integer)object); continue;
/* 417 */       }  if (object instanceof String) {
/*     */         try {
/* 419 */           result.add(Integer.valueOf((String)object));
/* 420 */         } catch (Exception ex) {} continue;
/*     */       } 
/* 422 */       if (object instanceof Character) {
/* 423 */         result.add(Integer.valueOf(((Character)object).charValue())); continue;
/* 424 */       }  if (object instanceof Number) {
/* 425 */         result.add(Integer.valueOf(((Number)object).intValue()));
/*     */       }
/*     */     } 
/*     */     
/* 429 */     return result;
/*     */   }
/*     */   
/*     */   public List<Boolean> getBooleanList(String path) {
/* 433 */     List<?> list = getList(path);
/*     */     
/* 435 */     if (list == null) {
/* 436 */       return new ArrayList<Boolean>(0);
/*     */     }
/*     */     
/* 439 */     List<Boolean> result = new ArrayList<Boolean>();
/*     */     
/* 441 */     for (Object object : list) {
/* 442 */       if (object instanceof Boolean) {
/* 443 */         result.add((Boolean)object); continue;
/* 444 */       }  if (object instanceof String) {
/* 445 */         if (Boolean.TRUE.toString().equals(object)) {
/* 446 */           result.add(Boolean.valueOf(true)); continue;
/* 447 */         }  if (Boolean.FALSE.toString().equals(object)) {
/* 448 */           result.add(Boolean.valueOf(false));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 453 */     return result;
/*     */   }
/*     */   
/*     */   public List<Double> getDoubleList(String path) {
/* 457 */     List<?> list = getList(path);
/*     */     
/* 459 */     if (list == null) {
/* 460 */       return new ArrayList<Double>(0);
/*     */     }
/*     */     
/* 463 */     List<Double> result = new ArrayList<Double>();
/*     */     
/* 465 */     for (Object object : list) {
/* 466 */       if (object instanceof Double) {
/* 467 */         result.add((Double)object); continue;
/* 468 */       }  if (object instanceof String) {
/*     */         try {
/* 470 */           result.add(Double.valueOf((String)object));
/* 471 */         } catch (Exception ex) {} continue;
/*     */       } 
/* 473 */       if (object instanceof Character) {
/* 474 */         result.add(Double.valueOf(((Character)object).charValue())); continue;
/* 475 */       }  if (object instanceof Number) {
/* 476 */         result.add(Double.valueOf(((Number)object).doubleValue()));
/*     */       }
/*     */     } 
/*     */     
/* 480 */     return result;
/*     */   }
/*     */   
/*     */   public List<Float> getFloatList(String path) {
/* 484 */     List<?> list = getList(path);
/*     */     
/* 486 */     if (list == null) {
/* 487 */       return new ArrayList<Float>(0);
/*     */     }
/*     */     
/* 490 */     List<Float> result = new ArrayList<Float>();
/*     */     
/* 492 */     for (Object object : list) {
/* 493 */       if (object instanceof Float) {
/* 494 */         result.add((Float)object); continue;
/* 495 */       }  if (object instanceof String) {
/*     */         try {
/* 497 */           result.add(Float.valueOf((String)object));
/* 498 */         } catch (Exception ex) {} continue;
/*     */       } 
/* 500 */       if (object instanceof Character) {
/* 501 */         result.add(Float.valueOf(((Character)object).charValue())); continue;
/* 502 */       }  if (object instanceof Number) {
/* 503 */         result.add(Float.valueOf(((Number)object).floatValue()));
/*     */       }
/*     */     } 
/*     */     
/* 507 */     return result;
/*     */   }
/*     */   
/*     */   public List<Long> getLongList(String path) {
/* 511 */     List<?> list = getList(path);
/*     */     
/* 513 */     if (list == null) {
/* 514 */       return new ArrayList<Long>(0);
/*     */     }
/*     */     
/* 517 */     List<Long> result = new ArrayList<Long>();
/*     */     
/* 519 */     for (Object object : list) {
/* 520 */       if (object instanceof Long) {
/* 521 */         result.add((Long)object); continue;
/* 522 */       }  if (object instanceof String) {
/*     */         try {
/* 524 */           result.add(Long.valueOf((String)object));
/* 525 */         } catch (Exception ex) {} continue;
/*     */       } 
/* 527 */       if (object instanceof Character) {
/* 528 */         result.add(Long.valueOf(((Character)object).charValue())); continue;
/* 529 */       }  if (object instanceof Number) {
/* 530 */         result.add(Long.valueOf(((Number)object).longValue()));
/*     */       }
/*     */     } 
/*     */     
/* 534 */     return result;
/*     */   }
/*     */   
/*     */   public List<Byte> getByteList(String path) {
/* 538 */     List<?> list = getList(path);
/*     */     
/* 540 */     if (list == null) {
/* 541 */       return new ArrayList<Byte>(0);
/*     */     }
/*     */     
/* 544 */     List<Byte> result = new ArrayList<Byte>();
/*     */     
/* 546 */     for (Object object : list) {
/* 547 */       if (object instanceof Byte) {
/* 548 */         result.add((Byte)object); continue;
/* 549 */       }  if (object instanceof String) {
/*     */         try {
/* 551 */           result.add(Byte.valueOf((String)object));
/* 552 */         } catch (Exception ex) {} continue;
/*     */       } 
/* 554 */       if (object instanceof Character) {
/* 555 */         result.add(Byte.valueOf((byte)((Character)object).charValue())); continue;
/* 556 */       }  if (object instanceof Number) {
/* 557 */         result.add(Byte.valueOf(((Number)object).byteValue()));
/*     */       }
/*     */     } 
/*     */     
/* 561 */     return result;
/*     */   }
/*     */   
/*     */   public List<Character> getCharacterList(String path) {
/* 565 */     List<?> list = getList(path);
/*     */     
/* 567 */     if (list == null) {
/* 568 */       return new ArrayList<Character>(0);
/*     */     }
/*     */     
/* 571 */     List<Character> result = new ArrayList<Character>();
/*     */     
/* 573 */     for (Object object : list) {
/* 574 */       if (object instanceof Character) {
/* 575 */         result.add((Character)object); continue;
/* 576 */       }  if (object instanceof String) {
/* 577 */         String str = (String)object;
/*     */         
/* 579 */         if (str.length() == 1)
/* 580 */           result.add(Character.valueOf(str.charAt(0)));  continue;
/*     */       } 
/* 582 */       if (object instanceof Number) {
/* 583 */         result.add(Character.valueOf((char)((Number)object).intValue()));
/*     */       }
/*     */     } 
/*     */     
/* 587 */     return result;
/*     */   }
/*     */   
/*     */   public List<Short> getShortList(String path) {
/* 591 */     List<?> list = getList(path);
/*     */     
/* 593 */     if (list == null) {
/* 594 */       return new ArrayList<Short>(0);
/*     */     }
/*     */     
/* 597 */     List<Short> result = new ArrayList<Short>();
/*     */     
/* 599 */     for (Object object : list) {
/* 600 */       if (object instanceof Short) {
/* 601 */         result.add((Short)object); continue;
/* 602 */       }  if (object instanceof String) {
/*     */         try {
/* 604 */           result.add(Short.valueOf((String)object));
/* 605 */         } catch (Exception ex) {} continue;
/*     */       } 
/* 607 */       if (object instanceof Character) {
/* 608 */         result.add(Short.valueOf((short)((Character)object).charValue())); continue;
/* 609 */       }  if (object instanceof Number) {
/* 610 */         result.add(Short.valueOf(((Number)object).shortValue()));
/*     */       }
/*     */     } 
/*     */     
/* 614 */     return result;
/*     */   }
/*     */   
/*     */   public List<Map<?, ?>> getMapList(String path) {
/* 618 */     List<?> list = getList(path);
/* 619 */     List<Map<?, ?>> result = new ArrayList<Map<?, ?>>();
/*     */     
/* 621 */     if (list == null) {
/* 622 */       return result;
/*     */     }
/*     */     
/* 625 */     for (Object object : list) {
/* 626 */       if (object instanceof Map) {
/* 627 */         result.add((Map<?, ?>)object);
/*     */       }
/*     */     } 
/*     */     
/* 631 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getVector(String path) {
/* 636 */     Object def = getDefault(path);
/* 637 */     return getVector(path, (def instanceof Vector) ? (Vector)def : null);
/*     */   }
/*     */   
/*     */   public Vector getVector(String path, Vector def) {
/* 641 */     Object val = get(path, def);
/* 642 */     return (val instanceof Vector) ? (Vector)val : def;
/*     */   }
/*     */   
/*     */   public boolean isVector(String path) {
/* 646 */     Object val = get(path);
/* 647 */     return val instanceof Vector;
/*     */   }
/*     */   
/*     */   public OfflinePlayer getOfflinePlayer(String path) {
/* 651 */     Object def = getDefault(path);
/* 652 */     return getOfflinePlayer(path, (def instanceof OfflinePlayer) ? (OfflinePlayer)def : null);
/*     */   }
/*     */   
/*     */   public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
/* 656 */     Object val = get(path, def);
/* 657 */     return (val instanceof OfflinePlayer) ? (OfflinePlayer)val : def;
/*     */   }
/*     */   
/*     */   public boolean isOfflinePlayer(String path) {
/* 661 */     Object val = get(path);
/* 662 */     return val instanceof OfflinePlayer;
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(String path) {
/* 666 */     Object def = getDefault(path);
/* 667 */     return getItemStack(path, (def instanceof ItemStack) ? (ItemStack)def : null);
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(String path, ItemStack def) {
/* 671 */     Object val = get(path, def);
/* 672 */     return (val instanceof ItemStack) ? (ItemStack)val : def;
/*     */   }
/*     */   
/*     */   public boolean isItemStack(String path) {
/* 676 */     Object val = get(path);
/* 677 */     return val instanceof ItemStack;
/*     */   }
/*     */   
/*     */   public Color getColor(String path) {
/* 681 */     Object def = getDefault(path);
/* 682 */     return getColor(path, (def instanceof Color) ? (Color)def : null);
/*     */   }
/*     */   
/*     */   public Color getColor(String path, Color def) {
/* 686 */     Object val = get(path, def);
/* 687 */     return (val instanceof Color) ? (Color)val : def;
/*     */   }
/*     */   
/*     */   public boolean isColor(String path) {
/* 691 */     Object val = get(path);
/* 692 */     return val instanceof Color;
/*     */   }
/*     */   
/*     */   public ConfigurationSection getConfigurationSection(String path) {
/* 696 */     Object val = get(path, null);
/* 697 */     if (val != null) {
/* 698 */       return (val instanceof ConfigurationSection) ? (ConfigurationSection)val : null;
/*     */     }
/*     */     
/* 701 */     val = get(path, getDefault(path));
/* 702 */     return (val instanceof ConfigurationSection) ? createSection(path) : null;
/*     */   }
/*     */   
/*     */   public boolean isConfigurationSection(String path) {
/* 706 */     Object val = get(path);
/* 707 */     return val instanceof ConfigurationSection;
/*     */   }
/*     */   
/*     */   protected boolean isPrimitiveWrapper(Object input) {
/* 711 */     return (input instanceof Integer || input instanceof Boolean || input instanceof Character || input instanceof Byte || input instanceof Short || input instanceof Double || input instanceof Long || input instanceof Float);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getDefault(String path) {
/* 718 */     Validate.notNull(path, "Path cannot be null");
/*     */     
/* 720 */     Configuration root = getRoot();
/* 721 */     Configuration defaults = (root == null) ? null : root.getDefaults();
/* 722 */     return (defaults == null) ? null : defaults.get(createPath(this, path));
/*     */   }
/*     */   
/*     */   protected void mapChildrenKeys(Set<String> output, ConfigurationSection section, boolean deep) {
/* 726 */     if (section instanceof MemorySection) {
/* 727 */       MemorySection sec = (MemorySection)section;
/*     */       
/* 729 */       for (Map.Entry<String, Object> entry : sec.map.entrySet()) {
/* 730 */         output.add(createPath(section, entry.getKey(), this));
/*     */         
/* 732 */         if (deep && entry.getValue() instanceof ConfigurationSection) {
/* 733 */           ConfigurationSection subsection = (ConfigurationSection)entry.getValue();
/* 734 */           mapChildrenKeys(output, subsection, deep);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 738 */       Set<String> keys = section.getKeys(deep);
/*     */       
/* 740 */       for (String key : keys) {
/* 741 */         output.add(createPath(section, key, this));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void mapChildrenValues(Map<String, Object> output, ConfigurationSection section, boolean deep) {
/* 747 */     if (section instanceof MemorySection) {
/* 748 */       MemorySection sec = (MemorySection)section;
/*     */       
/* 750 */       for (Map.Entry<String, Object> entry : sec.map.entrySet()) {
/* 751 */         output.put(createPath(section, entry.getKey(), this), entry.getValue());
/*     */         
/* 753 */         if (entry.getValue() instanceof ConfigurationSection && 
/* 754 */           deep) {
/* 755 */           mapChildrenValues(output, (ConfigurationSection)entry.getValue(), deep);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 760 */       Map<String, Object> values = section.getValues(deep);
/*     */       
/* 762 */       for (Map.Entry<String, Object> entry : values.entrySet()) {
/* 763 */         output.put(createPath(section, entry.getKey(), this), entry.getValue());
/*     */       }
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
/*     */   
/*     */   public static String createPath(ConfigurationSection section, String key) {
/* 780 */     return createPath(section, key, (section == null) ? null : section.getRoot());
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
/*     */   public static String createPath(ConfigurationSection section, String key, ConfigurationSection relativeTo) {
/* 796 */     Validate.notNull(section, "Cannot create path without a section");
/* 797 */     Configuration root = section.getRoot();
/* 798 */     if (root == null) {
/* 799 */       throw new IllegalStateException("Cannot create path without a root");
/*     */     }
/* 801 */     char separator = root.options().pathSeparator();
/*     */     
/* 803 */     StringBuilder builder = new StringBuilder();
/* 804 */     if (section != null) {
/* 805 */       for (ConfigurationSection parent = section; parent != null && parent != relativeTo; parent = parent.getParent()) {
/* 806 */         if (builder.length() > 0) {
/* 807 */           builder.insert(0, separator);
/*     */         }
/*     */         
/* 810 */         builder.insert(0, parent.getName());
/*     */       } 
/*     */     }
/*     */     
/* 814 */     if (key != null && key.length() > 0) {
/* 815 */       if (builder.length() > 0) {
/* 816 */         builder.append(separator);
/*     */       }
/*     */       
/* 819 */       builder.append(key);
/*     */     } 
/*     */     
/* 822 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 827 */     Configuration root = getRoot();
/* 828 */     return getClass().getSimpleName() + "[path='" + getCurrentPath() + "', root='" + ((root == null) ? null : root.getClass().getSimpleName()) + "']";
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\MemorySection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */