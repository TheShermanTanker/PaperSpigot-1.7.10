/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import net.minecraft.server.v1_7_R4.GenericAttributes;
/*     */ import net.minecraft.server.v1_7_R4.IAttribute;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagList;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagString;
/*     */ import net.minecraft.util.gnu.trove.map.hash.TObjectDoubleHashMap;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.configuration.serialization.SerializableAs;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.Overridden;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.Repairable;
/*     */ import org.spigotmc.ValidateUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaItem
/*     */   implements ItemMeta, Repairable
/*     */ {
/*     */   static class ItemMetaKey
/*     */   {
/*     */     final String BUKKIT;
/*     */     final String NBT;
/*     */     
/*     */     @Retention(RetentionPolicy.SOURCE)
/*     */     @Target({ElementType.FIELD})
/*     */     static @interface Specific
/*     */     {
/*     */       To value();
/*     */       
/*     */       public enum To
/*     */       {
/*  76 */         BUKKIT,
/*  77 */         NBT;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ItemMetaKey(String both) {
/*  87 */       this(both, both);
/*     */     }
/*     */     
/*     */     ItemMetaKey(String nbt, String bukkit) {
/*  91 */       this.NBT = nbt;
/*  92 */       this.BUKKIT = bukkit;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializableAs("ItemMeta")
/*     */   public static class SerializableMeta
/*     */     implements ConfigurationSerializable
/*     */   {
/*     */     static final String TYPE_FIELD = "meta-type";
/*     */     
/* 104 */     static final ImmutableMap<Class<? extends CraftMetaItem>, String> classMap = ImmutableMap.builder().put(CraftMetaBook.class, "BOOK").put(CraftMetaSkull.class, "SKULL").put(CraftMetaLeatherArmor.class, "LEATHER_ARMOR").put(CraftMetaMap.class, "MAP").put(CraftMetaPotion.class, "POTION").put(CraftMetaEnchantedBook.class, "ENCHANTED").put(CraftMetaFirework.class, "FIREWORK").put(CraftMetaCharge.class, "FIREWORK_EFFECT").put(CraftMetaItem.class, "UNSPECIFIC").build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static final ImmutableMap<String, Constructor<? extends CraftMetaItem>> constructorMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 116 */       ImmutableMap.Builder<String, Constructor<? extends CraftMetaItem>> classConstructorBuilder = ImmutableMap.builder();
/* 117 */       for (Map.Entry<Class<? extends CraftMetaItem>, String> mapping : (Iterable<Map.Entry<Class<? extends CraftMetaItem>, String>>)classMap.entrySet()) {
/*     */         try {
/* 119 */           classConstructorBuilder.put(mapping.getValue(), ((Class)mapping.getKey()).getDeclaredConstructor(new Class[] { Map.class }));
/* 120 */         } catch (NoSuchMethodException e) {
/* 121 */           throw new AssertionError(e);
/*     */         } 
/*     */       } 
/* 124 */       constructorMap = classConstructorBuilder.build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static ItemMeta deserialize(Map<String, Object> map) throws Throwable {
/* 131 */       Validate.notNull(map, "Cannot deserialize null map");
/*     */       
/* 133 */       String type = getString(map, "meta-type", false);
/* 134 */       Constructor<? extends CraftMetaItem> constructor = (Constructor<? extends CraftMetaItem>)constructorMap.get(type);
/*     */       
/* 136 */       if (constructor == null) {
/* 137 */         throw new IllegalArgumentException(type + " is not a valid " + "meta-type");
/*     */       }
/*     */       
/*     */       try {
/* 141 */         return constructor.newInstance(new Object[] { map });
/* 142 */       } catch (InstantiationException e) {
/* 143 */         throw new AssertionError(e);
/* 144 */       } catch (IllegalAccessException e) {
/* 145 */         throw new AssertionError(e);
/* 146 */       } catch (InvocationTargetException e) {
/* 147 */         throw e.getCause();
/*     */       } 
/*     */     }
/*     */     
/*     */     public Map<String, Object> serialize() {
/* 152 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     static String getString(Map<?, ?> map, Object field, boolean nullable) {
/* 156 */       return getObject(String.class, map, field, nullable);
/*     */     }
/*     */     
/*     */     static boolean getBoolean(Map<?, ?> map, Object field) {
/* 160 */       Boolean value = getObject(Boolean.class, map, field, true);
/* 161 */       return (value != null && value.booleanValue());
/*     */     }
/*     */     
/*     */     static <T> T getObject(Class<T> clazz, Map<?, ?> map, Object field, boolean nullable) {
/* 165 */       Object object = map.get(field);
/*     */       
/* 167 */       if (clazz.isInstance(object)) {
/* 168 */         return clazz.cast(object);
/*     */       }
/* 170 */       if (object == null) {
/* 171 */         if (!nullable) {
/* 172 */           throw new NoSuchElementException(map + " does not contain " + field);
/*     */         }
/* 174 */         return null;
/*     */       } 
/* 176 */       throw new IllegalArgumentException(field + "(" + object + ") is not a valid " + clazz);
/*     */     }
/*     */   }
/*     */   
/* 180 */   static final ItemMetaKey NAME = new ItemMetaKey("Name", "display-name");
/*     */   
/* 182 */   static final ItemMetaKey DISPLAY = new ItemMetaKey("display");
/* 183 */   static final ItemMetaKey LORE = new ItemMetaKey("Lore", "lore");
/* 184 */   static final ItemMetaKey ENCHANTMENTS = new ItemMetaKey("ench", "enchants");
/*     */   
/* 186 */   static final ItemMetaKey ENCHANTMENTS_ID = new ItemMetaKey("id");
/*     */   
/* 188 */   static final ItemMetaKey ENCHANTMENTS_LVL = new ItemMetaKey("lvl");
/* 189 */   static final ItemMetaKey REPAIR = new ItemMetaKey("RepairCost", "repair-cost");
/*     */   
/* 191 */   static final ItemMetaKey ATTRIBUTES = new ItemMetaKey("AttributeModifiers");
/*     */   
/* 193 */   static final ItemMetaKey ATTRIBUTES_IDENTIFIER = new ItemMetaKey("AttributeName");
/*     */   
/* 195 */   static final ItemMetaKey ATTRIBUTES_NAME = new ItemMetaKey("Name");
/*     */   
/* 197 */   static final ItemMetaKey ATTRIBUTES_VALUE = new ItemMetaKey("Amount");
/*     */   
/* 199 */   static final ItemMetaKey ATTRIBUTES_TYPE = new ItemMetaKey("Operation");
/*     */   
/* 201 */   static final ItemMetaKey ATTRIBUTES_UUID_HIGH = new ItemMetaKey("UUIDMost");
/*     */   
/* 203 */   static final ItemMetaKey ATTRIBUTES_UUID_LOW = new ItemMetaKey("UUIDLeast");
/* 204 */   static final ItemMetaKey UNBREAKABLE = new ItemMetaKey("Unbreakable");
/*     */   
/*     */   private String displayName;
/*     */   private List<String> lore;
/*     */   private Map<Enchantment, Integer> enchantments;
/*     */   private int repairCost;
/*     */   private final NBTTagList attributes;
/*     */   
/*     */   CraftMetaItem(CraftMetaItem meta) {
/* 213 */     if (meta == null) {
/* 214 */       this.attributes = null;
/*     */       
/*     */       return;
/*     */     } 
/* 218 */     this.displayName = meta.displayName;
/*     */     
/* 220 */     if (meta.hasLore()) {
/* 221 */       this.lore = new ArrayList<String>(meta.lore);
/*     */     }
/*     */     
/* 224 */     if (meta.hasEnchants()) {
/* 225 */       this.enchantments = new HashMap<Enchantment, Integer>(meta.enchantments);
/*     */     }
/*     */     
/* 228 */     this.repairCost = meta.repairCost;
/* 229 */     this.attributes = meta.attributes;
/* 230 */     this.spigot.setUnbreakable(meta.spigot.isUnbreakable());
/*     */   }
/*     */   
/*     */   CraftMetaItem(NBTTagCompound tag) {
/* 234 */     if (tag.hasKey(DISPLAY.NBT)) {
/* 235 */       NBTTagCompound display = tag.getCompound(DISPLAY.NBT);
/*     */       
/* 237 */       if (display.hasKey(NAME.NBT)) {
/* 238 */         this.displayName = ValidateUtils.limit(display.getString(NAME.NBT), 1024);
/*     */       }
/*     */       
/* 241 */       if (display.hasKey(LORE.NBT)) {
/* 242 */         NBTTagList list = display.getList(LORE.NBT, 8);
/* 243 */         this.lore = new ArrayList<String>(list.size());
/*     */         
/* 245 */         for (int index = 0; index < list.size(); index++) {
/* 246 */           String line = ValidateUtils.limit(list.getString(index), 1024);
/* 247 */           this.lore.add(line);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 252 */     this.enchantments = buildEnchantments(tag, ENCHANTMENTS);
/*     */     
/* 254 */     if (tag.hasKey(REPAIR.NBT)) {
/* 255 */       this.repairCost = tag.getInt(REPAIR.NBT);
/*     */     }
/*     */ 
/*     */     
/* 259 */     if (tag.get(ATTRIBUTES.NBT) instanceof NBTTagList) {
/* 260 */       NBTTagList save = null;
/* 261 */       NBTTagList nbttaglist = tag.getList(ATTRIBUTES.NBT, 10);
/*     */ 
/*     */       
/* 264 */       TObjectDoubleHashMap<String> attributeTracker = new TObjectDoubleHashMap();
/* 265 */       TObjectDoubleHashMap<String> attributeTrackerX = new TObjectDoubleHashMap();
/* 266 */       Map<String, IAttribute> attributesByName = new HashMap<String, IAttribute>();
/* 267 */       attributeTracker.put("generic.maxHealth", 20.0D);
/* 268 */       attributesByName.put("generic.maxHealth", GenericAttributes.maxHealth);
/* 269 */       attributeTracker.put("generic.followRange", 32.0D);
/* 270 */       attributesByName.put("generic.followRange", GenericAttributes.b);
/* 271 */       attributeTracker.put("generic.knockbackResistance", 0.0D);
/* 272 */       attributesByName.put("generic.knockbackResistance", GenericAttributes.c);
/* 273 */       attributeTracker.put("generic.movementSpeed", 0.7D);
/* 274 */       attributesByName.put("generic.movementSpeed", GenericAttributes.d);
/* 275 */       attributeTracker.put("generic.attackDamage", 1.0D);
/* 276 */       attributesByName.put("generic.attackDamage", GenericAttributes.e);
/* 277 */       NBTTagList oldList = nbttaglist;
/* 278 */       nbttaglist = new NBTTagList();
/*     */       
/* 280 */       List<NBTTagCompound> op0 = new ArrayList<NBTTagCompound>();
/* 281 */       List<NBTTagCompound> op1 = new ArrayList<NBTTagCompound>();
/* 282 */       List<NBTTagCompound> op2 = new ArrayList<NBTTagCompound>();
/*     */       int i;
/* 284 */       for (i = 0; i < oldList.size(); i++) {
/*     */         
/* 286 */         NBTTagCompound nbttagcompound = oldList.get(i);
/* 287 */         if (nbttagcompound != null)
/*     */         {
/* 289 */           if (nbttagcompound.get(ATTRIBUTES_UUID_HIGH.NBT) instanceof net.minecraft.server.v1_7_R4.NBTTagLong)
/*     */           {
/*     */ 
/*     */             
/* 293 */             if (nbttagcompound.get(ATTRIBUTES_UUID_LOW.NBT) instanceof net.minecraft.server.v1_7_R4.NBTTagLong)
/*     */             {
/*     */ 
/*     */               
/* 297 */               if (nbttagcompound.get(ATTRIBUTES_IDENTIFIER.NBT) instanceof NBTTagString && CraftItemFactory.KNOWN_NBT_ATTRIBUTE_NAMES.contains(nbttagcompound.getString(ATTRIBUTES_IDENTIFIER.NBT)))
/*     */               {
/*     */ 
/*     */                 
/* 301 */                 if (nbttagcompound.get(ATTRIBUTES_NAME.NBT) instanceof NBTTagString && !nbttagcompound.getString(ATTRIBUTES_NAME.NBT).isEmpty())
/*     */                 {
/*     */ 
/*     */                   
/* 305 */                   if (nbttagcompound.get(ATTRIBUTES_VALUE.NBT) instanceof net.minecraft.server.v1_7_R4.NBTTagDouble)
/*     */                   {
/*     */ 
/*     */                     
/* 309 */                     if (nbttagcompound.get(ATTRIBUTES_TYPE.NBT) instanceof net.minecraft.server.v1_7_R4.NBTTagInt && nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) >= 0 && nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) <= 2)
/*     */                     {
/*     */ 
/*     */ 
/*     */                       
/* 314 */                       switch (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT)) {
/*     */                         
/*     */                         case 0:
/* 317 */                           op0.add(nbttagcompound);
/*     */                           break;
/*     */                         case 1:
/* 320 */                           op1.add(nbttagcompound);
/*     */                           break;
/*     */                         case 2:
/* 323 */                           op2.add(nbttagcompound);
/*     */                           break;
/*     */                       }  }  }  }  }  }  }  } 
/*     */       } 
/* 327 */       for (NBTTagCompound nbtTagCompound : op0) {
/*     */         
/* 329 */         String name = nbtTagCompound.getString(ATTRIBUTES_IDENTIFIER.NBT);
/* 330 */         if (attributeTracker.containsKey(name)) {
/*     */           
/* 332 */           double val = attributeTracker.get(name);
/* 333 */           val += nbtTagCompound.getDouble(ATTRIBUTES_VALUE.NBT);
/* 334 */           if (val != ((IAttribute)attributesByName.get(name)).a(val)) {
/*     */             continue;
/*     */           }
/*     */           
/* 338 */           attributeTracker.put(name, val);
/*     */         } 
/* 340 */         nbttaglist.add((NBTBase)nbtTagCompound);
/*     */       } 
/* 342 */       for (String name : attributeTracker.keySet())
/*     */       {
/* 344 */         attributeTrackerX.put(name, attributeTracker.get(name));
/*     */       }
/* 346 */       for (NBTTagCompound nbtTagCompound : op1) {
/*     */         
/* 348 */         String name = nbtTagCompound.getString(ATTRIBUTES_IDENTIFIER.NBT);
/* 349 */         if (attributeTracker.containsKey(name)) {
/*     */           
/* 351 */           double val = attributeTracker.get(name);
/* 352 */           double valX = attributeTrackerX.get(name);
/* 353 */           val += valX * nbtTagCompound.getDouble(ATTRIBUTES_VALUE.NBT);
/* 354 */           if (val != ((IAttribute)attributesByName.get(name)).a(val)) {
/*     */             continue;
/*     */           }
/*     */           
/* 358 */           attributeTracker.put(name, val);
/*     */         } 
/* 360 */         nbttaglist.add((NBTBase)nbtTagCompound);
/*     */       } 
/* 362 */       for (NBTTagCompound nbtTagCompound : op2) {
/*     */         
/* 364 */         String name = nbtTagCompound.getString(ATTRIBUTES_IDENTIFIER.NBT);
/* 365 */         if (attributeTracker.containsKey(name)) {
/*     */           
/* 367 */           double val = attributeTracker.get(name);
/* 368 */           val += val * nbtTagCompound.getDouble(ATTRIBUTES_VALUE.NBT);
/* 369 */           if (val != ((IAttribute)attributesByName.get(name)).a(val)) {
/*     */             continue;
/*     */           }
/*     */           
/* 373 */           attributeTracker.put(name, val);
/*     */         } 
/* 375 */         nbttaglist.add((NBTBase)nbtTagCompound);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 380 */       for (i = 0; i < nbttaglist.size(); i++) {
/* 381 */         if (nbttaglist.get(i) instanceof NBTTagCompound) {
/*     */ 
/*     */           
/* 384 */           NBTTagCompound nbttagcompound = nbttaglist.get(i);
/*     */           
/* 386 */           if (nbttagcompound.get(ATTRIBUTES_UUID_HIGH.NBT) instanceof net.minecraft.server.v1_7_R4.NBTTagLong)
/*     */           {
/*     */             
/* 389 */             if (nbttagcompound.get(ATTRIBUTES_UUID_LOW.NBT) instanceof net.minecraft.server.v1_7_R4.NBTTagLong)
/*     */             {
/*     */               
/* 392 */               if (nbttagcompound.get(ATTRIBUTES_IDENTIFIER.NBT) instanceof NBTTagString && CraftItemFactory.KNOWN_NBT_ATTRIBUTE_NAMES.contains(nbttagcompound.getString(ATTRIBUTES_IDENTIFIER.NBT)))
/*     */               {
/*     */                 
/* 395 */                 if (nbttagcompound.get(ATTRIBUTES_NAME.NBT) instanceof NBTTagString && !nbttagcompound.getString(ATTRIBUTES_NAME.NBT).isEmpty())
/*     */                 {
/*     */                   
/* 398 */                   if (nbttagcompound.get(ATTRIBUTES_VALUE.NBT) instanceof net.minecraft.server.v1_7_R4.NBTTagDouble)
/*     */                   {
/*     */                     
/* 401 */                     if (nbttagcompound.get(ATTRIBUTES_TYPE.NBT) instanceof net.minecraft.server.v1_7_R4.NBTTagInt && nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) >= 0 && nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) <= 2) {
/*     */ 
/*     */ 
/*     */                       
/* 405 */                       if (save == null) {
/* 406 */                         save = new NBTTagList();
/*     */                       }
/*     */                       
/* 409 */                       NBTTagCompound entry = new NBTTagCompound();
/* 410 */                       entry.set(ATTRIBUTES_UUID_HIGH.NBT, nbttagcompound.get(ATTRIBUTES_UUID_HIGH.NBT));
/* 411 */                       entry.set(ATTRIBUTES_UUID_LOW.NBT, nbttagcompound.get(ATTRIBUTES_UUID_LOW.NBT));
/* 412 */                       entry.set(ATTRIBUTES_IDENTIFIER.NBT, nbttagcompound.get(ATTRIBUTES_IDENTIFIER.NBT));
/* 413 */                       entry.set(ATTRIBUTES_NAME.NBT, nbttagcompound.get(ATTRIBUTES_NAME.NBT));
/* 414 */                       entry.set(ATTRIBUTES_VALUE.NBT, nbttagcompound.get(ATTRIBUTES_VALUE.NBT));
/* 415 */                       entry.set(ATTRIBUTES_TYPE.NBT, nbttagcompound.get(ATTRIBUTES_TYPE.NBT));
/* 416 */                       save.add((NBTBase)entry);
/*     */                     }  }  }  }  }  } 
/*     */         } 
/* 419 */       }  this.attributes = save;
/*     */     } else {
/* 421 */       this.attributes = null;
/*     */     } 
/*     */     
/* 424 */     if (tag.hasKey(UNBREAKABLE.NBT))
/*     */     {
/* 426 */       this.spigot.setUnbreakable(tag.getBoolean(UNBREAKABLE.NBT));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static Map<Enchantment, Integer> buildEnchantments(NBTTagCompound tag, ItemMetaKey key) {
/* 432 */     if (!tag.hasKey(key.NBT)) {
/* 433 */       return null;
/*     */     }
/*     */     
/* 436 */     NBTTagList ench = tag.getList(key.NBT, 10);
/* 437 */     Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>(ench.size());
/*     */     
/* 439 */     for (int i = 0; i < ench.size(); i++) {
/* 440 */       int id = 0xFFFF & ench.get(i).getShort(ENCHANTMENTS_ID.NBT);
/* 441 */       int level = 0xFFFF & ench.get(i).getShort(ENCHANTMENTS_LVL.NBT);
/*     */ 
/*     */       
/* 444 */       Enchantment e = Enchantment.getById(id);
/* 445 */       if (e != null)
/*     */       {
/* 447 */         enchantments.put(e, Integer.valueOf(level));
/*     */       }
/*     */     } 
/* 450 */     return enchantments;
/*     */   }
/*     */   
/*     */   CraftMetaItem(Map<String, Object> map) {
/* 454 */     setDisplayName(SerializableMeta.getString(map, NAME.BUKKIT, true));
/*     */     
/* 456 */     Iterable<?> lore = SerializableMeta.<Iterable>getObject(Iterable.class, map, LORE.BUKKIT, true);
/* 457 */     if (lore != null) {
/* 458 */       safelyAdd(lore, this.lore = new ArrayList<String>(), 2147483647);
/*     */     }
/*     */     
/* 461 */     this.enchantments = buildEnchantments(map, ENCHANTMENTS);
/*     */     
/* 463 */     Integer repairCost = SerializableMeta.<Integer>getObject(Integer.class, map, REPAIR.BUKKIT, true);
/* 464 */     if (repairCost != null) {
/* 465 */       setRepairCost(repairCost.intValue());
/*     */     }
/*     */     
/* 468 */     this.attributes = null;
/*     */     
/* 470 */     Boolean unbreakable = SerializableMeta.<Boolean>getObject(Boolean.class, map, UNBREAKABLE.BUKKIT, true);
/* 471 */     if (unbreakable != null)
/*     */     {
/* 473 */       this.spigot.setUnbreakable(unbreakable.booleanValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static Map<Enchantment, Integer> buildEnchantments(Map<String, Object> map, ItemMetaKey key) {
/* 479 */     Map<?, ?> ench = SerializableMeta.<Map<?, ?>>getObject((Class)Map.class, map, key.BUKKIT, true);
/* 480 */     if (ench == null) {
/* 481 */       return null;
/*     */     }
/*     */     
/* 484 */     Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>(ench.size());
/* 485 */     for (Map.Entry<?, ?> entry : ench.entrySet()) {
/* 486 */       Enchantment enchantment = Enchantment.getByName(entry.getKey().toString());
/*     */       
/* 488 */       if (enchantment != null && entry.getValue() instanceof Integer) {
/* 489 */         enchantments.put(enchantment, (Integer)entry.getValue());
/*     */       }
/*     */     } 
/*     */     
/* 493 */     return enchantments;
/*     */   }
/*     */   
/*     */   @Overridden
/*     */   void applyToItem(NBTTagCompound itemTag) {
/* 498 */     if (hasDisplayName()) {
/* 499 */       setDisplayTag(itemTag, NAME.NBT, (NBTBase)new NBTTagString(this.displayName));
/*     */     }
/*     */     
/* 502 */     if (hasLore()) {
/* 503 */       setDisplayTag(itemTag, LORE.NBT, (NBTBase)createStringList(this.lore));
/*     */     }
/*     */     
/* 506 */     applyEnchantments(this.enchantments, itemTag, ENCHANTMENTS);
/*     */ 
/*     */     
/* 509 */     if (this.spigot.isUnbreakable())
/*     */     {
/* 511 */       itemTag.setBoolean(UNBREAKABLE.NBT, true);
/*     */     }
/*     */ 
/*     */     
/* 515 */     if (hasRepairCost()) {
/* 516 */       itemTag.setInt(REPAIR.NBT, this.repairCost);
/*     */     }
/*     */     
/* 519 */     if (this.attributes != null) {
/* 520 */       itemTag.set(ATTRIBUTES.NBT, (NBTBase)this.attributes);
/*     */     }
/*     */   }
/*     */   
/*     */   static NBTTagList createStringList(List<String> list) {
/* 525 */     if (list == null || list.isEmpty()) {
/* 526 */       return null;
/*     */     }
/*     */     
/* 529 */     NBTTagList tagList = new NBTTagList();
/* 530 */     for (String value : list) {
/* 531 */       tagList.add((NBTBase)new NBTTagString(value));
/*     */     }
/*     */     
/* 534 */     return tagList;
/*     */   }
/*     */   
/*     */   static void applyEnchantments(Map<Enchantment, Integer> enchantments, NBTTagCompound tag, ItemMetaKey key) {
/* 538 */     if (enchantments == null) {
/*     */       return;
/*     */     }
/*     */     
/* 542 */     NBTTagList list = new NBTTagList();
/*     */     
/* 544 */     for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
/* 545 */       NBTTagCompound subtag = new NBTTagCompound();
/*     */       
/* 547 */       subtag.setShort(ENCHANTMENTS_ID.NBT, (short)((Enchantment)entry.getKey()).getId());
/* 548 */       subtag.setShort(ENCHANTMENTS_LVL.NBT, ((Integer)entry.getValue()).shortValue());
/*     */       
/* 550 */       list.add((NBTBase)subtag);
/*     */     } 
/*     */     
/* 553 */     tag.set(key.NBT, (NBTBase)list);
/*     */   }
/*     */   
/*     */   void setDisplayTag(NBTTagCompound tag, String key, NBTBase value) {
/* 557 */     NBTTagCompound display = tag.getCompound(DISPLAY.NBT);
/*     */     
/* 559 */     if (!tag.hasKey(DISPLAY.NBT)) {
/* 560 */       tag.set(DISPLAY.NBT, (NBTBase)display);
/*     */     }
/*     */     
/* 563 */     display.set(key, value);
/*     */   }
/*     */   
/*     */   @Overridden
/*     */   boolean applicableTo(Material type) {
/* 568 */     return (type != Material.AIR);
/*     */   }
/*     */   
/*     */   @Overridden
/*     */   boolean isEmpty() {
/* 573 */     return (!hasDisplayName() && !hasEnchants() && !hasLore() && !hasAttributes() && !hasRepairCost() && !this.spigot.isUnbreakable());
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/* 577 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public final void setDisplayName(String name) {
/* 581 */     this.displayName = name;
/*     */   }
/*     */   
/*     */   public boolean hasDisplayName() {
/* 585 */     return !Strings.isNullOrEmpty(this.displayName);
/*     */   }
/*     */   
/*     */   public boolean hasLore() {
/* 589 */     return (this.lore != null && !this.lore.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean hasAttributes() {
/* 593 */     return (this.attributes != null);
/*     */   }
/*     */   
/*     */   public boolean hasRepairCost() {
/* 597 */     return (this.repairCost > 0);
/*     */   }
/*     */   
/*     */   public boolean hasEnchant(Enchantment ench) {
/* 601 */     return (hasEnchants() && this.enchantments.containsKey(ench));
/*     */   }
/*     */   
/*     */   public int getEnchantLevel(Enchantment ench) {
/* 605 */     Integer level = hasEnchants() ? this.enchantments.get(ench) : null;
/* 606 */     if (level == null) {
/* 607 */       return 0;
/*     */     }
/* 609 */     return level.intValue();
/*     */   }
/*     */   
/*     */   public Map<Enchantment, Integer> getEnchants() {
/* 613 */     return hasEnchants() ? (Map<Enchantment, Integer>)ImmutableMap.copyOf(this.enchantments) : (Map<Enchantment, Integer>)ImmutableMap.of();
/*     */   }
/*     */   
/*     */   public boolean addEnchant(Enchantment ench, int level, boolean ignoreRestrictions) {
/* 617 */     if (this.enchantments == null) {
/* 618 */       this.enchantments = new HashMap<Enchantment, Integer>(4);
/*     */     }
/*     */     
/* 621 */     if (ignoreRestrictions || (level >= ench.getStartLevel() && level <= ench.getMaxLevel())) {
/* 622 */       Integer old = this.enchantments.put(ench, Integer.valueOf(level));
/* 623 */       return (old == null || old.intValue() != level);
/*     */     } 
/* 625 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeEnchant(Enchantment ench) {
/* 630 */     boolean b = (hasEnchants() && this.enchantments.remove(ench) != null);
/* 631 */     if (this.enchantments != null && this.enchantments.isEmpty())
/*     */     {
/* 633 */       this.enchantments = null;
/*     */     }
/* 635 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasEnchants() {
/* 640 */     return (this.enchantments != null && !this.enchantments.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean hasConflictingEnchant(Enchantment ench) {
/* 644 */     return checkConflictingEnchants(this.enchantments, ench);
/*     */   }
/*     */   
/*     */   public List<String> getLore() {
/* 648 */     return (this.lore == null) ? null : new ArrayList<String>(this.lore);
/*     */   }
/*     */   
/*     */   public void setLore(List<String> lore) {
/* 652 */     if (lore == null) {
/* 653 */       this.lore = null;
/*     */     }
/* 655 */     else if (this.lore == null) {
/* 656 */       safelyAdd(lore, this.lore = new ArrayList<String>(lore.size()), 2147483647);
/*     */     } else {
/* 658 */       this.lore.clear();
/* 659 */       safelyAdd(lore, this.lore, 2147483647);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRepairCost() {
/* 665 */     return this.repairCost;
/*     */   }
/*     */   
/*     */   public void setRepairCost(int cost) {
/* 669 */     this.repairCost = cost;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object object) {
/* 674 */     if (object == null) {
/* 675 */       return false;
/*     */     }
/* 677 */     if (object == this) {
/* 678 */       return true;
/*     */     }
/* 680 */     if (!(object instanceof CraftMetaItem)) {
/* 681 */       return false;
/*     */     }
/* 683 */     return CraftItemFactory.instance().equals(this, (ItemMeta)object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Overridden
/*     */   boolean equalsCommon(CraftMetaItem that) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual hasDisplayName : ()Z
/*     */     //   4: ifeq -> 31
/*     */     //   7: aload_1
/*     */     //   8: invokevirtual hasDisplayName : ()Z
/*     */     //   11: ifeq -> 212
/*     */     //   14: aload_0
/*     */     //   15: getfield displayName : Ljava/lang/String;
/*     */     //   18: aload_1
/*     */     //   19: getfield displayName : Ljava/lang/String;
/*     */     //   22: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   25: ifeq -> 212
/*     */     //   28: goto -> 38
/*     */     //   31: aload_1
/*     */     //   32: invokevirtual hasDisplayName : ()Z
/*     */     //   35: ifne -> 212
/*     */     //   38: aload_0
/*     */     //   39: invokevirtual hasEnchants : ()Z
/*     */     //   42: ifeq -> 71
/*     */     //   45: aload_1
/*     */     //   46: invokevirtual hasEnchants : ()Z
/*     */     //   49: ifeq -> 212
/*     */     //   52: aload_0
/*     */     //   53: getfield enchantments : Ljava/util/Map;
/*     */     //   56: aload_1
/*     */     //   57: getfield enchantments : Ljava/util/Map;
/*     */     //   60: invokeinterface equals : (Ljava/lang/Object;)Z
/*     */     //   65: ifeq -> 212
/*     */     //   68: goto -> 78
/*     */     //   71: aload_1
/*     */     //   72: invokevirtual hasEnchants : ()Z
/*     */     //   75: ifne -> 212
/*     */     //   78: aload_0
/*     */     //   79: invokevirtual hasLore : ()Z
/*     */     //   82: ifeq -> 111
/*     */     //   85: aload_1
/*     */     //   86: invokevirtual hasLore : ()Z
/*     */     //   89: ifeq -> 212
/*     */     //   92: aload_0
/*     */     //   93: getfield lore : Ljava/util/List;
/*     */     //   96: aload_1
/*     */     //   97: getfield lore : Ljava/util/List;
/*     */     //   100: invokeinterface equals : (Ljava/lang/Object;)Z
/*     */     //   105: ifeq -> 212
/*     */     //   108: goto -> 118
/*     */     //   111: aload_1
/*     */     //   112: invokevirtual hasLore : ()Z
/*     */     //   115: ifne -> 212
/*     */     //   118: aload_0
/*     */     //   119: invokevirtual hasAttributes : ()Z
/*     */     //   122: ifeq -> 149
/*     */     //   125: aload_1
/*     */     //   126: invokevirtual hasAttributes : ()Z
/*     */     //   129: ifeq -> 212
/*     */     //   132: aload_0
/*     */     //   133: getfield attributes : Lnet/minecraft/server/v1_7_R4/NBTTagList;
/*     */     //   136: aload_1
/*     */     //   137: getfield attributes : Lnet/minecraft/server/v1_7_R4/NBTTagList;
/*     */     //   140: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   143: ifeq -> 212
/*     */     //   146: goto -> 156
/*     */     //   149: aload_1
/*     */     //   150: invokevirtual hasAttributes : ()Z
/*     */     //   153: ifne -> 212
/*     */     //   156: aload_0
/*     */     //   157: invokevirtual hasRepairCost : ()Z
/*     */     //   160: ifeq -> 184
/*     */     //   163: aload_1
/*     */     //   164: invokevirtual hasRepairCost : ()Z
/*     */     //   167: ifeq -> 212
/*     */     //   170: aload_0
/*     */     //   171: getfield repairCost : I
/*     */     //   174: aload_1
/*     */     //   175: getfield repairCost : I
/*     */     //   178: if_icmpne -> 212
/*     */     //   181: goto -> 191
/*     */     //   184: aload_1
/*     */     //   185: invokevirtual hasRepairCost : ()Z
/*     */     //   188: ifne -> 212
/*     */     //   191: aload_0
/*     */     //   192: getfield spigot : Lorg/bukkit/inventory/meta/ItemMeta$Spigot;
/*     */     //   195: invokevirtual isUnbreakable : ()Z
/*     */     //   198: aload_1
/*     */     //   199: getfield spigot : Lorg/bukkit/inventory/meta/ItemMeta$Spigot;
/*     */     //   202: invokevirtual isUnbreakable : ()Z
/*     */     //   205: if_icmpne -> 212
/*     */     //   208: iconst_1
/*     */     //   209: goto -> 213
/*     */     //   212: iconst_0
/*     */     //   213: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #693	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	214	0	this	Lorg/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem;
/*     */     //   0	214	1	that	Lorg/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Overridden
/*     */   boolean notUncommon(CraftMetaItem meta) {
/* 707 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 712 */     return applyHash();
/*     */   }
/*     */   
/*     */   @Overridden
/*     */   int applyHash() {
/* 717 */     int hash = 3;
/* 718 */     hash = 61 * hash + (hasDisplayName() ? this.displayName.hashCode() : 0);
/* 719 */     hash = 61 * hash + (hasLore() ? this.lore.hashCode() : 0);
/* 720 */     hash = 61 * hash + (hasEnchants() ? this.enchantments.hashCode() : 0);
/* 721 */     hash = 61 * hash + (hasAttributes() ? this.attributes.hashCode() : 0);
/* 722 */     hash = 61 * hash + (hasRepairCost() ? this.repairCost : 0);
/* 723 */     hash = 61 * hash + (this.spigot.isUnbreakable() ? 1231 : 1237);
/* 724 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   @Overridden
/*     */   public CraftMetaItem clone() {
/*     */     try {
/* 731 */       CraftMetaItem clone = (CraftMetaItem)super.clone();
/* 732 */       if (this.lore != null) {
/* 733 */         clone.lore = new ArrayList<String>(this.lore);
/*     */       }
/* 735 */       if (this.enchantments != null) {
/* 736 */         clone.enchantments = new HashMap<Enchantment, Integer>(this.enchantments);
/*     */       }
/* 738 */       return clone;
/* 739 */     } catch (CloneNotSupportedException e) {
/* 740 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final Map<String, Object> serialize() {
/* 745 */     ImmutableMap.Builder<String, Object> map = ImmutableMap.builder();
/* 746 */     map.put("meta-type", SerializableMeta.classMap.get(getClass()));
/* 747 */     serialize(map);
/* 748 */     return (Map<String, Object>)map.build();
/*     */   }
/*     */   
/*     */   @Overridden
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
/* 753 */     if (hasDisplayName()) {
/* 754 */       builder.put(NAME.BUKKIT, this.displayName);
/*     */     }
/*     */     
/* 757 */     if (hasLore()) {
/* 758 */       builder.put(LORE.BUKKIT, ImmutableList.copyOf(this.lore));
/*     */     }
/*     */     
/* 761 */     serializeEnchantments(this.enchantments, builder, ENCHANTMENTS);
/*     */     
/* 763 */     if (hasRepairCost()) {
/* 764 */       builder.put(REPAIR.BUKKIT, Integer.valueOf(this.repairCost));
/*     */     }
/*     */ 
/*     */     
/* 768 */     if (this.spigot.isUnbreakable())
/*     */     {
/* 770 */       builder.put(UNBREAKABLE.BUKKIT, Boolean.valueOf(true));
/*     */     }
/*     */ 
/*     */     
/* 774 */     return builder;
/*     */   }
/*     */   
/*     */   static void serializeEnchantments(Map<Enchantment, Integer> enchantments, ImmutableMap.Builder<String, Object> builder, ItemMetaKey key) {
/* 778 */     if (enchantments == null || enchantments.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 782 */     ImmutableMap.Builder<String, Integer> enchants = ImmutableMap.builder();
/* 783 */     for (Map.Entry<? extends Enchantment, Integer> enchant : enchantments.entrySet()) {
/* 784 */       enchants.put(((Enchantment)enchant.getKey()).getName(), enchant.getValue());
/*     */     }
/*     */     
/* 787 */     builder.put(key.BUKKIT, enchants.build());
/*     */   }
/*     */   
/*     */   static void safelyAdd(Iterable<?> addFrom, Collection<String> addTo, int maxItemLength) {
/* 791 */     if (addFrom == null) {
/*     */       return;
/*     */     }
/*     */     
/* 795 */     for (Object object : addFrom) {
/* 796 */       if (!(object instanceof String)) {
/* 797 */         if (object != null) {
/* 798 */           throw new IllegalArgumentException(addFrom + " cannot contain non-string " + object.getClass().getName());
/*     */         }
/*     */         
/* 801 */         addTo.add(""); continue;
/*     */       } 
/* 803 */       String page = object.toString();
/*     */       
/* 805 */       if (page.length() > maxItemLength) {
/* 806 */         page = page.substring(0, maxItemLength);
/*     */       }
/*     */       
/* 809 */       addTo.add(page);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean checkConflictingEnchants(Map<Enchantment, Integer> enchantments, Enchantment ench) {
/* 815 */     if (enchantments == null || enchantments.isEmpty()) {
/* 816 */       return false;
/*     */     }
/*     */     
/* 819 */     for (Enchantment enchant : enchantments.keySet()) {
/* 820 */       if (enchant.conflictsWith(ench)) {
/* 821 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 825 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 830 */     return (String)SerializableMeta.classMap.get(getClass()) + "_META:" + serialize();
/*     */   }
/*     */ 
/*     */   
/* 834 */   private final ItemMeta.Spigot spigot = new ItemMeta.Spigot()
/*     */     {
/*     */       private boolean unbreakable;
/*     */ 
/*     */ 
/*     */       
/*     */       public void setUnbreakable(boolean setUnbreakable) {
/* 841 */         this.unbreakable = setUnbreakable;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean isUnbreakable() {
/* 847 */         return this.unbreakable;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemMeta.Spigot spigot() {
/* 854 */     return this.spigot;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftMetaItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */