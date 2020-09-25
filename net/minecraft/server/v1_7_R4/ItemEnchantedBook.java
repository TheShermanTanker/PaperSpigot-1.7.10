/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ public class ItemEnchantedBook
/*     */   extends Item
/*     */ {
/*     */   public boolean e_(ItemStack paramItemStack) {
/*  20 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumItemRarity f(ItemStack paramItemStack) {
/*  25 */     if (g(paramItemStack).size() > 0) {
/*  26 */       return EnumItemRarity.UNCOMMON;
/*     */     }
/*  28 */     return super.f(paramItemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagList g(ItemStack paramItemStack) {
/*  33 */     if (paramItemStack.tag == null || !paramItemStack.tag.hasKeyOfType("StoredEnchantments", 9)) {
/*  34 */       return new NBTTagList();
/*     */     }
/*     */     
/*  37 */     return (NBTTagList)paramItemStack.tag.get("StoredEnchantments");
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
/*     */   public void a(ItemStack paramItemStack, EnchantmentInstance paramEnchantmentInstance) {
/*  59 */     NBTTagList nBTTagList = g(paramItemStack);
/*  60 */     boolean bool = true;
/*     */     
/*  62 */     for (byte b = 0; b < nBTTagList.size(); b++) {
/*  63 */       NBTTagCompound nBTTagCompound = nBTTagList.get(b);
/*     */       
/*  65 */       if (nBTTagCompound.getShort("id") == paramEnchantmentInstance.enchantment.id) {
/*  66 */         if (nBTTagCompound.getShort("lvl") < paramEnchantmentInstance.level) {
/*  67 */           nBTTagCompound.setShort("lvl", (short)paramEnchantmentInstance.level);
/*     */         }
/*     */         
/*  70 */         bool = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  75 */     if (bool) {
/*  76 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*     */       
/*  78 */       nBTTagCompound.setShort("id", (short)paramEnchantmentInstance.enchantment.id);
/*  79 */       nBTTagCompound.setShort("lvl", (short)paramEnchantmentInstance.level);
/*     */       
/*  81 */       nBTTagList.add(nBTTagCompound);
/*     */     } 
/*     */     
/*  84 */     if (!paramItemStack.hasTag()) paramItemStack.setTag(new NBTTagCompound()); 
/*  85 */     paramItemStack.getTag().set("StoredEnchantments", nBTTagList);
/*     */   }
/*     */   
/*     */   public ItemStack a(EnchantmentInstance paramEnchantmentInstance) {
/*  89 */     ItemStack itemStack = new ItemStack(this);
/*  90 */     a(itemStack, paramEnchantmentInstance);
/*  91 */     return itemStack;
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
/*     */   public StructurePieceTreasure b(Random paramRandom) {
/* 111 */     return a(paramRandom, 1, 1, 1);
/*     */   }
/*     */   
/*     */   public StructurePieceTreasure a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 115 */     ItemStack itemStack = new ItemStack(Items.BOOK, 1, 0);
/* 116 */     EnchantmentManager.a(paramRandom, itemStack, 30);
/*     */     
/* 118 */     return new StructurePieceTreasure(itemStack, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemEnchantedBook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */