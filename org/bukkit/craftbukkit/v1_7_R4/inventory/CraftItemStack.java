/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.EnchantmentManager;
/*     */ import net.minecraft.server.v1_7_R4.Item;
/*     */ import net.minecraft.server.v1_7_R4.ItemStack;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagList;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ 
/*     */ 
/*     */ @DelegateDeserialization(ItemStack.class)
/*     */ public final class CraftItemStack
/*     */   extends ItemStack
/*     */ {
/*     */   ItemStack handle;
/*     */   
/*     */   public static ItemStack asNMSCopy(ItemStack original) {
/*  28 */     if (original instanceof CraftItemStack) {
/*  29 */       CraftItemStack craftItemStack = (CraftItemStack)original;
/*  30 */       return (craftItemStack.handle == null) ? null : craftItemStack.handle.cloneItemStack();
/*     */     } 
/*  32 */     if (original == null || original.getTypeId() <= 0) {
/*  33 */       return null;
/*     */     }
/*     */     
/*  36 */     Item item = CraftMagicNumbers.getItem(original.getType());
/*     */     
/*  38 */     if (item == null) {
/*  39 */       return null;
/*     */     }
/*     */     
/*  42 */     ItemStack stack = new ItemStack(item, original.getAmount(), original.getDurability());
/*  43 */     if (original.hasItemMeta()) {
/*  44 */       setItemMeta(stack, original.getItemMeta());
/*     */     }
/*  46 */     return stack;
/*     */   }
/*     */   
/*     */   public static ItemStack copyNMSStack(ItemStack original, int amount) {
/*  50 */     ItemStack stack = original.cloneItemStack();
/*  51 */     stack.count = amount;
/*  52 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack asBukkitCopy(ItemStack original) {
/*  59 */     if (original == null) {
/*  60 */       return new ItemStack(Material.AIR);
/*     */     }
/*  62 */     ItemStack stack = new ItemStack(CraftMagicNumbers.getMaterial(original.getItem()), original.count, (short)original.getData());
/*  63 */     if (hasItemMeta(original)) {
/*  64 */       stack.setItemMeta(getItemMeta(original));
/*     */     }
/*  66 */     return stack;
/*     */   }
/*     */   
/*     */   public static CraftItemStack asCraftMirror(ItemStack original) {
/*  70 */     return new CraftItemStack(original);
/*     */   }
/*     */   
/*     */   public static CraftItemStack asCraftCopy(ItemStack original) {
/*  74 */     if (original instanceof CraftItemStack) {
/*  75 */       CraftItemStack stack = (CraftItemStack)original;
/*  76 */       return new CraftItemStack((stack.handle == null) ? null : stack.handle.cloneItemStack());
/*     */     } 
/*  78 */     return new CraftItemStack(original);
/*     */   }
/*     */   
/*     */   public static CraftItemStack asNewCraftStack(Item item) {
/*  82 */     return asNewCraftStack(item, 1);
/*     */   }
/*     */   
/*     */   public static CraftItemStack asNewCraftStack(Item item, int amount) {
/*  86 */     return new CraftItemStack(CraftMagicNumbers.getMaterial(item), amount, (short)0, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CraftItemStack(ItemStack item) {
/*  95 */     this.handle = item;
/*     */   }
/*     */   
/*     */   private CraftItemStack(ItemStack item) {
/*  99 */     this(item.getTypeId(), item.getAmount(), item.getDurability(), item.hasItemMeta() ? item.getItemMeta() : null);
/*     */   }
/*     */   
/*     */   private CraftItemStack(Material type, int amount, short durability, ItemMeta itemMeta) {
/* 103 */     setType(type);
/* 104 */     setAmount(amount);
/* 105 */     setDurability(durability);
/* 106 */     setItemMeta(itemMeta);
/*     */   }
/*     */   
/*     */   private CraftItemStack(int typeId, int amount, short durability, ItemMeta itemMeta) {
/* 110 */     this(Material.getMaterial(typeId), amount, durability, itemMeta);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTypeId() {
/* 116 */     return (this.handle != null) ? CraftMagicNumbers.getId(this.handle.getItem()) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTypeId(int type) {
/* 121 */     if (getTypeId() == type)
/*     */       return; 
/* 123 */     if (type == 0) {
/* 124 */       this.handle = null;
/* 125 */     } else if (CraftMagicNumbers.getItem(type) == null) {
/* 126 */       this.handle = null;
/* 127 */     } else if (this.handle == null) {
/* 128 */       this.handle = new ItemStack(CraftMagicNumbers.getItem(type), 1, 0);
/*     */     } else {
/* 130 */       this.handle.setItem(CraftMagicNumbers.getItem(type));
/* 131 */       if (hasItemMeta())
/*     */       {
/* 133 */         setItemMeta(this.handle, getItemMeta(this.handle));
/*     */       }
/*     */     } 
/* 136 */     setData(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmount() {
/* 141 */     return (this.handle != null) ? this.handle.count : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAmount(int amount) {
/* 146 */     if (this.handle == null) {
/*     */       return;
/*     */     }
/* 149 */     if (amount == 0) {
/* 150 */       this.handle = null;
/*     */     } else {
/* 152 */       this.handle.count = amount;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDurability(short durability) {
/* 159 */     if (this.handle != null) {
/* 160 */       this.handle.setData(durability);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public short getDurability() {
/* 166 */     if (this.handle != null) {
/* 167 */       return (short)this.handle.getData();
/*     */     }
/* 169 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 175 */     return (this.handle == null) ? Material.AIR.getMaxStackSize() : this.handle.getItem().getMaxStackSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addUnsafeEnchantment(Enchantment ench, int level) {
/* 180 */     Validate.notNull(ench, "Cannot add null enchantment");
/*     */     
/* 182 */     if (!makeTag(this.handle)) {
/*     */       return;
/*     */     }
/* 185 */     NBTTagList list = getEnchantmentList(this.handle);
/* 186 */     if (list == null) {
/* 187 */       list = new NBTTagList();
/* 188 */       this.handle.tag.set(CraftMetaItem.ENCHANTMENTS.NBT, (NBTBase)list);
/*     */     } 
/* 190 */     int size = list.size();
/*     */     
/* 192 */     for (int i = 0; i < size; i++) {
/* 193 */       NBTTagCompound nBTTagCompound = list.get(i);
/* 194 */       short id = nBTTagCompound.getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
/* 195 */       if (id == ench.getId()) {
/* 196 */         nBTTagCompound.setShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short)level);
/*     */         return;
/*     */       } 
/*     */     } 
/* 200 */     NBTTagCompound tag = new NBTTagCompound();
/* 201 */     tag.setShort(CraftMetaItem.ENCHANTMENTS_ID.NBT, (short)ench.getId());
/* 202 */     tag.setShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short)level);
/* 203 */     list.add((NBTBase)tag);
/*     */   }
/*     */   
/*     */   static boolean makeTag(ItemStack item) {
/* 207 */     if (item == null) {
/* 208 */       return false;
/*     */     }
/*     */     
/* 211 */     if (item.tag == null) {
/* 212 */       item.setTag(new NBTTagCompound());
/*     */     }
/*     */     
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsEnchantment(Enchantment ench) {
/* 220 */     return (getEnchantmentLevel(ench) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnchantmentLevel(Enchantment ench) {
/* 225 */     Validate.notNull(ench, "Cannot find null enchantment");
/* 226 */     if (this.handle == null) {
/* 227 */       return 0;
/*     */     }
/* 229 */     return EnchantmentManager.getEnchantmentLevel(ench.getId(), this.handle);
/*     */   }
/*     */ 
/*     */   
/*     */   public int removeEnchantment(Enchantment ench) {
/* 234 */     Validate.notNull(ench, "Cannot remove null enchantment");
/*     */     
/* 236 */     NBTTagList list = getEnchantmentList(this.handle);
/* 237 */     if (list == null) {
/* 238 */       return 0;
/*     */     }
/* 240 */     int index = Integer.MIN_VALUE;
/* 241 */     int level = Integer.MIN_VALUE;
/* 242 */     int size = list.size();
/*     */     int i;
/* 244 */     for (i = 0; i < size; i++) {
/* 245 */       NBTTagCompound enchantment = list.get(i);
/* 246 */       int id = 0xFFFF & enchantment.getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
/* 247 */       if (id == ench.getId()) {
/* 248 */         index = i;
/* 249 */         level = 0xFFFF & enchantment.getShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 254 */     if (index == Integer.MIN_VALUE) {
/* 255 */       return 0;
/*     */     }
/* 257 */     if (size == 1) {
/* 258 */       this.handle.tag.remove(CraftMetaItem.ENCHANTMENTS.NBT);
/* 259 */       if (this.handle.tag.isEmpty()) {
/* 260 */         this.handle.tag = null;
/*     */       }
/* 262 */       return level;
/*     */     } 
/*     */ 
/*     */     
/* 266 */     NBTTagList listCopy = new NBTTagList();
/* 267 */     for (i = 0; i < size; i++) {
/* 268 */       if (i != index) {
/* 269 */         listCopy.add((NBTBase)list.get(i));
/*     */       }
/*     */     } 
/* 272 */     this.handle.tag.set(CraftMetaItem.ENCHANTMENTS.NBT, (NBTBase)listCopy);
/*     */     
/* 274 */     return level;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Enchantment, Integer> getEnchantments() {
/* 279 */     return getEnchantments(this.handle);
/*     */   }
/*     */   
/*     */   static Map<Enchantment, Integer> getEnchantments(ItemStack item) {
/* 283 */     NBTTagList list = (item != null && item.hasEnchantments()) ? item.getEnchantments() : null;
/*     */     
/* 285 */     if (list == null || list.size() == 0) {
/* 286 */       return (Map<Enchantment, Integer>)ImmutableMap.of();
/*     */     }
/*     */     
/* 289 */     ImmutableMap.Builder<Enchantment, Integer> result = ImmutableMap.builder();
/*     */     
/* 291 */     for (int i = 0; i < list.size(); i++) {
/* 292 */       int id = 0xFFFF & list.get(i).getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
/* 293 */       int level = 0xFFFF & list.get(i).getShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT);
/*     */       
/* 295 */       result.put(Enchantment.getById(id), Integer.valueOf(level));
/*     */     } 
/*     */     
/* 298 */     return (Map<Enchantment, Integer>)result.build();
/*     */   }
/*     */   
/*     */   static NBTTagList getEnchantmentList(ItemStack item) {
/* 302 */     return (item != null && item.hasEnchantments()) ? item.getEnchantments() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftItemStack clone() {
/* 307 */     CraftItemStack itemStack = (CraftItemStack)super.clone();
/* 308 */     if (this.handle != null) {
/* 309 */       itemStack.handle = this.handle.cloneItemStack();
/*     */     }
/* 311 */     return itemStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemMeta getItemMeta() {
/* 316 */     return getItemMeta(this.handle);
/*     */   }
/*     */   
/*     */   public static ItemMeta getItemMeta(ItemStack item) {
/* 320 */     if (!hasItemMeta(item)) {
/* 321 */       return CraftItemFactory.instance().getItemMeta(getType(item));
/*     */     }
/* 323 */     switch (getType(item)) {
/*     */       case WRITTEN_BOOK:
/*     */       case BOOK_AND_QUILL:
/* 326 */         return new CraftMetaBook(item.tag);
/*     */       case SKULL_ITEM:
/* 328 */         return new CraftMetaSkull(item.tag);
/*     */       case LEATHER_HELMET:
/*     */       case LEATHER_CHESTPLATE:
/*     */       case LEATHER_LEGGINGS:
/*     */       case LEATHER_BOOTS:
/* 333 */         return new CraftMetaLeatherArmor(item.tag);
/*     */       case POTION:
/* 335 */         return new CraftMetaPotion(item.tag);
/*     */       case MAP:
/* 337 */         return new CraftMetaMap(item.tag);
/*     */       case FIREWORK:
/* 339 */         return new CraftMetaFirework(item.tag);
/*     */       case FIREWORK_CHARGE:
/* 341 */         return new CraftMetaCharge(item.tag);
/*     */       case ENCHANTED_BOOK:
/* 343 */         return new CraftMetaEnchantedBook(item.tag);
/*     */     } 
/* 345 */     return new CraftMetaItem(item.tag);
/*     */   }
/*     */ 
/*     */   
/*     */   static Material getType(ItemStack item) {
/* 350 */     Material material = Material.getMaterial((item == null) ? 0 : CraftMagicNumbers.getId(item.getItem()));
/* 351 */     return (material == null) ? Material.AIR : material;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setItemMeta(ItemMeta itemMeta) {
/* 356 */     return setItemMeta(this.handle, itemMeta);
/*     */   }
/*     */   
/*     */   public static boolean setItemMeta(ItemStack item, ItemMeta itemMeta) {
/* 360 */     if (item == null) {
/* 361 */       return false;
/*     */     }
/* 363 */     if (CraftItemFactory.instance().equals(itemMeta, (ItemMeta)null)) {
/* 364 */       item.tag = null;
/* 365 */       return true;
/*     */     } 
/* 367 */     if (!CraftItemFactory.instance().isApplicable(itemMeta, getType(item))) {
/* 368 */       return false;
/*     */     }
/*     */     
/* 371 */     NBTTagCompound tag = new NBTTagCompound();
/* 372 */     item.setTag(tag);
/*     */     
/* 374 */     ((CraftMetaItem)itemMeta).applyToItem(tag);
/* 375 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSimilar(ItemStack stack) {
/* 380 */     if (stack == null) {
/* 381 */       return false;
/*     */     }
/* 383 */     if (stack == this) {
/* 384 */       return true;
/*     */     }
/* 386 */     if (!(stack instanceof CraftItemStack)) {
/* 387 */       return (stack.getClass() == ItemStack.class && stack.isSimilar(this));
/*     */     }
/*     */     
/* 390 */     CraftItemStack that = (CraftItemStack)stack;
/* 391 */     if (this.handle == that.handle) {
/* 392 */       return true;
/*     */     }
/* 394 */     if (this.handle == null || that.handle == null) {
/* 395 */       return false;
/*     */     }
/* 397 */     if (that.getTypeId() != getTypeId() || getDurability() != that.getDurability()) {
/* 398 */       return false;
/*     */     }
/* 400 */     return hasItemMeta() ? ((that.hasItemMeta() && this.handle.tag.equals(that.handle.tag))) : (!that.hasItemMeta());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasItemMeta() {
/* 405 */     return hasItemMeta(this.handle);
/*     */   }
/*     */   
/*     */   static boolean hasItemMeta(ItemStack item) {
/* 409 */     return (item != null && item.tag != null && !item.tag.isEmpty());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftItemStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */