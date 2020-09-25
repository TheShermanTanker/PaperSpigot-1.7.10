/*     */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_7_R4.Block;
/*     */ import net.minecraft.server.v1_7_R4.Blocks;
/*     */ import net.minecraft.server.v1_7_R4.Item;
/*     */ import net.minecraft.server.v1_7_R4.ItemStack;
/*     */ import net.minecraft.server.v1_7_R4.MojangsonParser;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import net.minecraft.server.v1_7_R4.Statistic;
/*     */ import net.minecraft.server.v1_7_R4.StatisticList;
/*     */ import org.bukkit.Achievement;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Statistic;
/*     */ import org.bukkit.UnsafeValues;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftStatistic;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ public final class CraftMagicNumbers implements UnsafeValues {
/*  25 */   public static final UnsafeValues INSTANCE = new CraftMagicNumbers();
/*     */ 
/*     */ 
/*     */   
/*     */   public static Block getBlock(Block block) {
/*  30 */     return getBlock(block.getType());
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Block getBlock(int id) {
/*  36 */     return getBlock(Material.getMaterial(id));
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static int getId(Block block) {
/*  42 */     return Block.getId(block);
/*     */   }
/*     */   
/*     */   public static Material getMaterial(Block block) {
/*  46 */     return Material.getMaterial(Block.getId(block));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Item getItem(Material material) {
/*  51 */     Item item = Item.getById(material.getId());
/*  52 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Item getItem(int id) {
/*  58 */     return Item.getById(id);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static int getId(Item item) {
/*  64 */     return Item.getId(item);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Material getMaterial(Item item) {
/*  69 */     Material material = Material.getMaterial(Item.getId(item));
/*     */     
/*  71 */     if (material == null) {
/*  72 */       return Material.AIR;
/*     */     }
/*     */     
/*  75 */     return material;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Block getBlock(Material material) {
/*  80 */     Block block = Block.getById(material.getId());
/*     */     
/*  82 */     if (block == null) {
/*  83 */       return Blocks.AIR;
/*     */     }
/*     */     
/*  86 */     return block;
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterialFromInternalName(String name) {
/*  91 */     return getMaterial((Item)Item.REGISTRY.get(name));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabCompleteInternalMaterialName(String token, List<String> completions) {
/*  96 */     return (List<String>)StringUtil.copyPartialMatches(token, Item.REGISTRY.keySet(), completions);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack modifyItemStack(ItemStack stack, String arguments) {
/* 101 */     ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
/*     */     
/* 103 */     nmsStack.setTag((NBTTagCompound)MojangsonParser.parse(arguments));
/*     */     
/* 105 */     stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
/*     */     
/* 107 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public Statistic getStatisticFromInternalName(String name) {
/* 112 */     return CraftStatistic.getBukkitStatisticByName(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Achievement getAchievementFromInternalName(String name) {
/* 117 */     return CraftStatistic.getBukkitAchievementByName(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabCompleteInternalStatisticOrAchievementName(String token, List<String> completions) {
/* 122 */     List<String> matches = new ArrayList<String>();
/* 123 */     Iterator iterator = StatisticList.stats.iterator();
/* 124 */     while (iterator.hasNext()) {
/* 125 */       String statistic = ((Statistic)iterator.next()).name;
/* 126 */       if (statistic.startsWith(token)) {
/* 127 */         matches.add(statistic);
/*     */       }
/*     */     } 
/* 130 */     return matches;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\CraftMagicNumbers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */