/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EnchantmentManager
/*     */ {
/*  13 */   private static final Random random = new Random();
/*  14 */   private static final EnchantmentModifierProtection b = new EnchantmentModifierProtection((EmptyClass)null);
/*  15 */   private static final EnchantmentModifierDamage c = new EnchantmentModifierDamage((EmptyClass)null);
/*  16 */   private static final EnchantmentModifierThorns d = new EnchantmentModifierThorns((EmptyClass)null);
/*  17 */   private static final EnchantmentModifierArthropods e = new EnchantmentModifierArthropods((EmptyClass)null);
/*     */   
/*     */   public static int getEnchantmentLevel(int i, ItemStack itemstack) {
/*  20 */     if (itemstack == null) {
/*  21 */       return 0;
/*     */     }
/*  23 */     NBTTagList nbttaglist = itemstack.getEnchantments();
/*     */     
/*  25 */     if (nbttaglist == null) {
/*  26 */       return 0;
/*     */     }
/*  28 */     for (int j = 0; j < nbttaglist.size(); j++) {
/*  29 */       short short1 = nbttaglist.get(j).getShort("id");
/*  30 */       short short2 = nbttaglist.get(j).getShort("lvl");
/*     */       
/*  32 */       if (short1 == i) {
/*  33 */         return short2;
/*     */       }
/*     */     } 
/*     */     
/*  37 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map a(ItemStack itemstack) {
/*  43 */     LinkedHashMap<Object, Object> linkedhashmap = new LinkedHashMap<Object, Object>();
/*  44 */     NBTTagList nbttaglist = (itemstack.getItem() == Items.ENCHANTED_BOOK) ? Items.ENCHANTED_BOOK.g(itemstack) : itemstack.getEnchantments();
/*     */     
/*  46 */     if (nbttaglist != null) {
/*  47 */       for (int i = 0; i < nbttaglist.size(); i++) {
/*  48 */         short short1 = nbttaglist.get(i).getShort("id");
/*  49 */         short short2 = nbttaglist.get(i).getShort("lvl");
/*     */         
/*  51 */         linkedhashmap.put(Integer.valueOf(short1), Integer.valueOf(short2));
/*     */       } 
/*     */     }
/*     */     
/*  55 */     return linkedhashmap;
/*     */   }
/*     */   
/*     */   public static void a(Map map, ItemStack itemstack) {
/*  59 */     NBTTagList nbttaglist = new NBTTagList();
/*  60 */     Iterator<Integer> iterator = map.keySet().iterator();
/*     */     
/*  62 */     while (iterator.hasNext()) {
/*  63 */       int i = ((Integer)iterator.next()).intValue();
/*  64 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/*  66 */       nbttagcompound.setShort("id", (short)i);
/*  67 */       nbttagcompound.setShort("lvl", (short)((Integer)map.get(Integer.valueOf(i))).intValue());
/*  68 */       nbttaglist.add(nbttagcompound);
/*  69 */       if (itemstack.getItem() == Items.ENCHANTED_BOOK) {
/*  70 */         Items.ENCHANTED_BOOK.a(itemstack, new EnchantmentInstance(i, ((Integer)map.get(Integer.valueOf(i))).intValue()));
/*     */       }
/*     */     } 
/*     */     
/*  74 */     if (nbttaglist.size() > 0) {
/*  75 */       if (itemstack.getItem() != Items.ENCHANTED_BOOK) {
/*  76 */         itemstack.a("ench", nbttaglist);
/*     */       }
/*  78 */     } else if (itemstack.hasTag()) {
/*  79 */       itemstack.getTag().remove("ench");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getEnchantmentLevel(int i, ItemStack[] aitemstack) {
/*  84 */     if (aitemstack == null) {
/*  85 */       return 0;
/*     */     }
/*  87 */     int j = 0;
/*  88 */     ItemStack[] aitemstack1 = aitemstack;
/*  89 */     int k = aitemstack.length;
/*     */     
/*  91 */     for (int l = 0; l < k; l++) {
/*  92 */       ItemStack itemstack = aitemstack1[l];
/*  93 */       int i1 = getEnchantmentLevel(i, itemstack);
/*     */       
/*  95 */       if (i1 > j) {
/*  96 */         j = i1;
/*     */       }
/*     */     } 
/*     */     
/* 100 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void a(EnchantmentModifier enchantmentmodifier, ItemStack itemstack) {
/* 105 */     if (itemstack != null) {
/* 106 */       NBTTagList nbttaglist = itemstack.getEnchantments();
/*     */       
/* 108 */       if (nbttaglist != null) {
/* 109 */         for (int i = 0; i < nbttaglist.size(); i++) {
/* 110 */           short short1 = nbttaglist.get(i).getShort("id");
/* 111 */           short short2 = nbttaglist.get(i).getShort("lvl");
/*     */           
/* 113 */           if (Enchantment.byId[short1] != null) {
/* 114 */             enchantmentmodifier.a(Enchantment.byId[short1], short2);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void a(EnchantmentModifier enchantmentmodifier, ItemStack[] aitemstack) {
/* 122 */     ItemStack[] aitemstack1 = aitemstack;
/* 123 */     int i = aitemstack.length;
/*     */     
/* 125 */     for (int j = 0; j < i; j++) {
/* 126 */       ItemStack itemstack = aitemstack1[j];
/*     */       
/* 128 */       a(enchantmentmodifier, itemstack);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int a(ItemStack[] aitemstack, DamageSource damagesource) {
/* 133 */     b.a = 0;
/* 134 */     b.b = damagesource;
/* 135 */     a(b, aitemstack);
/* 136 */     if (b.a > 25) {
/* 137 */       b.a = 25;
/*     */     }
/*     */     
/* 140 */     return (b.a + 1 >> 1) + random.nextInt((b.a >> 1) + 1);
/*     */   }
/*     */   
/*     */   public static float a(EntityLiving entityliving, EntityLiving entityliving1) {
/* 144 */     return a(entityliving.be(), entityliving1.getMonsterType());
/*     */   }
/*     */   
/*     */   public static float a(ItemStack itemstack, EnumMonsterType enummonstertype) {
/* 148 */     c.a = 0.0F;
/* 149 */     c.b = enummonstertype;
/* 150 */     a(c, itemstack);
/* 151 */     return c.a;
/*     */   }
/*     */   
/*     */   public static void a(EntityLiving entityliving, Entity entity) {
/* 155 */     d.b = entity;
/* 156 */     d.a = entityliving;
/* 157 */     a(d, entityliving.getEquipment());
/* 158 */     if (entity instanceof EntityHuman) {
/* 159 */       a(d, entityliving.be());
/*     */     }
/*     */     
/* 162 */     d.b = null;
/* 163 */     d.a = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void b(EntityLiving entityliving, Entity entity) {
/* 168 */     e.a = entityliving;
/* 169 */     e.b = entity;
/* 170 */     a(e, entityliving.getEquipment());
/* 171 */     if (entityliving instanceof EntityHuman) {
/* 172 */       a(e, entityliving.be());
/*     */     }
/*     */     
/* 175 */     e.a = null;
/* 176 */     e.b = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getKnockbackEnchantmentLevel(EntityLiving entityliving, EntityLiving entityliving1) {
/* 181 */     return getEnchantmentLevel(Enchantment.KNOCKBACK.id, entityliving.be());
/*     */   }
/*     */   
/*     */   public static int getFireAspectEnchantmentLevel(EntityLiving entityliving) {
/* 185 */     return getEnchantmentLevel(Enchantment.FIRE_ASPECT.id, entityliving.be());
/*     */   }
/*     */   
/*     */   public static int getOxygenEnchantmentLevel(EntityLiving entityliving) {
/* 189 */     return getEnchantmentLevel(Enchantment.OXYGEN.id, entityliving.getEquipment());
/*     */   }
/*     */   
/*     */   public static int getDigSpeedEnchantmentLevel(EntityLiving entityliving) {
/* 193 */     return getEnchantmentLevel(Enchantment.DIG_SPEED.id, entityliving.be());
/*     */   }
/*     */   
/*     */   public static boolean hasSilkTouchEnchantment(EntityLiving entityliving) {
/* 197 */     return (getEnchantmentLevel(Enchantment.SILK_TOUCH.id, entityliving.be()) > 0);
/*     */   }
/*     */   
/*     */   public static int getBonusBlockLootEnchantmentLevel(EntityLiving entityliving) {
/* 201 */     return getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS.id, entityliving.be());
/*     */   }
/*     */   
/*     */   public static int getLuckEnchantmentLevel(EntityLiving entityliving) {
/* 205 */     return getEnchantmentLevel(Enchantment.LUCK.id, entityliving.be());
/*     */   }
/*     */   
/*     */   public static int getLureEnchantmentLevel(EntityLiving entityliving) {
/* 209 */     return getEnchantmentLevel(Enchantment.LURE.id, entityliving.be());
/*     */   }
/*     */   
/*     */   public static int getBonusMonsterLootEnchantmentLevel(EntityLiving entityliving) {
/* 213 */     return getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS.id, entityliving.be());
/*     */   }
/*     */   
/*     */   public static boolean hasWaterWorkerEnchantment(EntityLiving entityliving) {
/* 217 */     return (getEnchantmentLevel(Enchantment.WATER_WORKER.id, entityliving.getEquipment()) > 0);
/*     */   }
/*     */   
/*     */   public static ItemStack a(Enchantment enchantment, EntityLiving entityliving) {
/* 221 */     ItemStack[] aitemstack = entityliving.getEquipment();
/* 222 */     int i = aitemstack.length;
/*     */     
/* 224 */     for (int j = 0; j < i; j++) {
/* 225 */       ItemStack itemstack = aitemstack[j];
/*     */       
/* 227 */       if (itemstack != null && getEnchantmentLevel(enchantment.id, itemstack) > 0) {
/* 228 */         return itemstack;
/*     */       }
/*     */     } 
/*     */     
/* 232 */     return null;
/*     */   }
/*     */   
/*     */   public static int a(Random random, int i, int j, ItemStack itemstack) {
/* 236 */     Item item = itemstack.getItem();
/* 237 */     int k = item.c();
/*     */     
/* 239 */     if (k <= 0) {
/* 240 */       return 0;
/*     */     }
/* 242 */     if (j > 15) {
/* 243 */       j = 15;
/*     */     }
/*     */     
/* 246 */     int l = random.nextInt(8) + 1 + (j >> 1) + random.nextInt(j + 1);
/*     */     
/* 248 */     return (i == 0) ? Math.max(l / 3, 1) : ((i == 1) ? (l * 2 / 3 + 1) : Math.max(l, j * 2));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack a(Random random, ItemStack itemstack, int i) {
/* 253 */     List list = b(random, itemstack, i);
/* 254 */     boolean flag = (itemstack.getItem() == Items.BOOK);
/*     */     
/* 256 */     if (flag) {
/* 257 */       itemstack.setItem(Items.ENCHANTED_BOOK);
/*     */     }
/*     */     
/* 260 */     if (list != null) {
/* 261 */       Iterator<EnchantmentInstance> iterator = list.iterator();
/*     */       
/* 263 */       while (iterator.hasNext()) {
/* 264 */         EnchantmentInstance enchantmentinstance = iterator.next();
/*     */         
/* 266 */         if (flag) {
/* 267 */           Items.ENCHANTED_BOOK.a(itemstack, enchantmentinstance); continue;
/*     */         } 
/* 269 */         itemstack.addEnchantment(enchantmentinstance.enchantment, enchantmentinstance.level);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 274 */     return itemstack;
/*     */   }
/*     */   
/*     */   public static List b(Random random, ItemStack itemstack, int i) {
/* 278 */     Item item = itemstack.getItem();
/* 279 */     int j = item.c();
/*     */     
/* 281 */     if (j <= 0) {
/* 282 */       return null;
/*     */     }
/* 284 */     j /= 2;
/* 285 */     j = 1 + random.nextInt((j >> 1) + 1) + random.nextInt((j >> 1) + 1);
/* 286 */     int k = j + i;
/* 287 */     float f = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
/* 288 */     int l = (int)(k * (1.0F + f) + 0.5F);
/*     */     
/* 290 */     if (l < 1) {
/* 291 */       l = 1;
/*     */     }
/*     */     
/* 294 */     ArrayList<EnchantmentInstance> arraylist = null;
/* 295 */     Map map = b(l, itemstack);
/*     */     
/* 297 */     if (map != null && !map.isEmpty()) {
/* 298 */       EnchantmentInstance enchantmentinstance = (EnchantmentInstance)WeightedRandom.a(random, map.values());
/*     */       
/* 300 */       if (enchantmentinstance != null) {
/* 301 */         arraylist = new ArrayList();
/* 302 */         arraylist.add(enchantmentinstance);
/*     */         int i1;
/* 304 */         for (i1 = l; random.nextInt(50) <= i1; i1 >>= 1) {
/* 305 */           Iterator<Integer> iterator = map.keySet().iterator();
/*     */           
/* 307 */           while (iterator.hasNext()) {
/* 308 */             Integer integer = iterator.next();
/* 309 */             boolean flag = true;
/* 310 */             Iterator<EnchantmentInstance> iterator1 = arraylist.iterator();
/*     */ 
/*     */             
/* 313 */             while (iterator1.hasNext()) {
/* 314 */               EnchantmentInstance enchantmentinstance1 = iterator1.next();
/*     */               
/* 316 */               if (enchantmentinstance1.enchantment.a(Enchantment.byId[integer.intValue()])) {
/*     */                 continue;
/*     */               }
/*     */               
/* 320 */               flag = false;
/*     */             } 
/*     */             
/* 323 */             if (!flag) {
/* 324 */               iterator.remove();
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 330 */           if (!map.isEmpty()) {
/* 331 */             EnchantmentInstance enchantmentinstance2 = (EnchantmentInstance)WeightedRandom.a(random, map.values());
/*     */             
/* 333 */             arraylist.add(enchantmentinstance2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 339 */     return arraylist;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map b(int i, ItemStack itemstack) {
/* 344 */     Item item = itemstack.getItem();
/* 345 */     HashMap<Object, Object> hashmap = null;
/* 346 */     boolean flag = (itemstack.getItem() == Items.BOOK);
/* 347 */     Enchantment[] aenchantment = Enchantment.byId;
/* 348 */     int j = aenchantment.length;
/*     */     
/* 350 */     for (int k = 0; k < j; k++) {
/* 351 */       Enchantment enchantment = aenchantment[k];
/*     */       
/* 353 */       if (enchantment != null && (enchantment.slot.canEnchant(item) || flag)) {
/* 354 */         for (int l = enchantment.getStartLevel(); l <= enchantment.getMaxLevel(); l++) {
/* 355 */           if (i >= enchantment.a(l) && i <= enchantment.b(l)) {
/* 356 */             if (hashmap == null) {
/* 357 */               hashmap = new HashMap<Object, Object>();
/*     */             }
/*     */             
/* 360 */             hashmap.put(Integer.valueOf(enchantment.id), new EnchantmentInstance(enchantment, l));
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 366 */     return hashmap;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */