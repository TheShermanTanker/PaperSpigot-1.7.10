/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class RecipeArmorDye
/*     */   extends ShapelessRecipes implements IRecipe {
/*     */   public RecipeArmorDye() {
/*   9 */     super(new ItemStack(Items.LEATHER_HELMET, 0, 0), Arrays.asList(new ItemStack[] { new ItemStack(Items.INK_SACK, 0, 5) }));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(InventoryCrafting inventorycrafting, World world) {
/*  14 */     ItemStack itemstack = null;
/*  15 */     ArrayList<ItemStack> arraylist = new ArrayList();
/*     */     
/*  17 */     for (int i = 0; i < inventorycrafting.getSize(); i++) {
/*  18 */       ItemStack itemstack1 = inventorycrafting.getItem(i);
/*     */       
/*  20 */       if (itemstack1 != null) {
/*  21 */         if (itemstack1.getItem() instanceof ItemArmor) {
/*  22 */           ItemArmor itemarmor = (ItemArmor)itemstack1.getItem();
/*     */           
/*  24 */           if (itemarmor.m_() != EnumArmorMaterial.CLOTH || itemstack != null) {
/*  25 */             return false;
/*     */           }
/*     */           
/*  28 */           itemstack = itemstack1;
/*     */         } else {
/*  30 */           if (itemstack1.getItem() != Items.INK_SACK) {
/*  31 */             return false;
/*     */           }
/*     */           
/*  34 */           arraylist.add(itemstack1);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  39 */     return (itemstack != null && !arraylist.isEmpty());
/*     */   }
/*     */   
/*     */   public ItemStack a(InventoryCrafting inventorycrafting) {
/*  43 */     ItemStack itemstack = null;
/*  44 */     int[] aint = new int[3];
/*  45 */     int i = 0;
/*  46 */     int j = 0;
/*  47 */     ItemArmor itemarmor = null;
/*     */ 
/*     */ 
/*     */     
/*     */     int k;
/*     */ 
/*     */ 
/*     */     
/*  55 */     for (k = 0; k < inventorycrafting.getSize(); k++) {
/*  56 */       ItemStack itemstack1 = inventorycrafting.getItem(k);
/*     */       
/*  58 */       if (itemstack1 != null) {
/*  59 */         if (itemstack1.getItem() instanceof ItemArmor) {
/*  60 */           itemarmor = (ItemArmor)itemstack1.getItem();
/*  61 */           if (itemarmor.m_() != EnumArmorMaterial.CLOTH || itemstack != null) {
/*  62 */             return null;
/*     */           }
/*     */           
/*  65 */           itemstack = itemstack1.cloneItemStack();
/*  66 */           itemstack.count = 1;
/*  67 */           if (itemarmor.c_(itemstack1)) {
/*  68 */             int m = itemarmor.b(itemstack);
/*  69 */             float f3 = (m >> 16 & 0xFF) / 255.0F;
/*  70 */             float f4 = (m >> 8 & 0xFF) / 255.0F;
/*  71 */             float f2 = (m & 0xFF) / 255.0F;
/*     */             
/*  73 */             i = (int)(i + Math.max(f3, Math.max(f4, f2)) * 255.0F);
/*  74 */             aint[0] = (int)(aint[0] + f3 * 255.0F);
/*  75 */             aint[1] = (int)(aint[1] + f4 * 255.0F);
/*  76 */             aint[2] = (int)(aint[2] + f2 * 255.0F);
/*  77 */             j++;
/*     */           } 
/*     */         } else {
/*  80 */           if (itemstack1.getItem() != Items.INK_SACK) {
/*  81 */             return null;
/*     */           }
/*     */           
/*  84 */           float[] afloat = EntitySheep.bp[BlockCloth.b(itemstack1.getData())];
/*  85 */           int j1 = (int)(afloat[0] * 255.0F);
/*  86 */           int k1 = (int)(afloat[1] * 255.0F);
/*     */           
/*  88 */           int m = (int)(afloat[2] * 255.0F);
/*  89 */           i += Math.max(j1, Math.max(k1, m));
/*  90 */           aint[0] = aint[0] + j1;
/*  91 */           aint[1] = aint[1] + k1;
/*  92 */           aint[2] = aint[2] + m;
/*  93 */           j++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  98 */     if (itemarmor == null) {
/*  99 */       return null;
/*     */     }
/* 101 */     k = aint[0] / j;
/* 102 */     int l1 = aint[1] / j;
/*     */     
/* 104 */     int l = aint[2] / j;
/* 105 */     float f = i / j;
/* 106 */     float f1 = Math.max(k, Math.max(l1, l));
/* 107 */     k = (int)(k * f / f1);
/* 108 */     l1 = (int)(l1 * f / f1);
/* 109 */     l = (int)(l * f / f1);
/* 110 */     int i1 = (k << 8) + l1;
/* 111 */     i1 = (i1 << 8) + l;
/* 112 */     itemarmor.b(itemstack, i1);
/* 113 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public int a() {
/* 118 */     return 10;
/*     */   }
/*     */   
/*     */   public ItemStack b() {
/* 122 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipeArmorDye.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */