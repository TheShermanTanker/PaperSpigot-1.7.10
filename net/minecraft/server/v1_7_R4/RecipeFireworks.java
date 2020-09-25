/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class RecipeFireworks
/*     */   extends ShapelessRecipes implements IRecipe {
/*     */   private ItemStack a;
/*     */   
/*     */   public RecipeFireworks() {
/*  11 */     super(new ItemStack(Items.FIREWORKS, 0, 0), Arrays.asList(new ItemStack[] { new ItemStack(Items.SULPHUR, 0, 5) }));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(InventoryCrafting inventorycrafting, World world) {
/*  16 */     this.a = null;
/*  17 */     int i = 0;
/*  18 */     int j = 0;
/*  19 */     int k = 0;
/*  20 */     int l = 0;
/*  21 */     int i1 = 0;
/*  22 */     int j1 = 0;
/*     */     
/*  24 */     for (int k1 = 0; k1 < inventorycrafting.getSize(); k1++) {
/*  25 */       ItemStack itemstack = inventorycrafting.getItem(k1);
/*     */       
/*  27 */       if (itemstack != null) {
/*  28 */         if (itemstack.getItem() == Items.SULPHUR) {
/*  29 */           j++;
/*  30 */         } else if (itemstack.getItem() == Items.FIREWORKS_CHARGE) {
/*  31 */           l++;
/*  32 */         } else if (itemstack.getItem() == Items.INK_SACK) {
/*  33 */           k++;
/*  34 */         } else if (itemstack.getItem() == Items.PAPER) {
/*  35 */           i++;
/*  36 */         } else if (itemstack.getItem() == Items.GLOWSTONE_DUST) {
/*  37 */           i1++;
/*  38 */         } else if (itemstack.getItem() == Items.DIAMOND) {
/*  39 */           i1++;
/*  40 */         } else if (itemstack.getItem() == Items.FIREBALL) {
/*  41 */           j1++;
/*  42 */         } else if (itemstack.getItem() == Items.FEATHER) {
/*  43 */           j1++;
/*  44 */         } else if (itemstack.getItem() == Items.GOLD_NUGGET) {
/*  45 */           j1++;
/*     */         } else {
/*  47 */           if (itemstack.getItem() != Items.SKULL) {
/*  48 */             return false;
/*     */           }
/*     */           
/*  51 */           j1++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  56 */     i1 += k + j1;
/*  57 */     if (j <= 3 && i <= 1) {
/*     */ 
/*     */ 
/*     */       
/*  61 */       if (j >= 1 && i == 1 && i1 == 0) {
/*  62 */         this.a = new ItemStack(Items.FIREWORKS);
/*  63 */         if (l > 0) {
/*  64 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  65 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  66 */           NBTTagList nbttaglist = new NBTTagList();
/*     */           
/*  68 */           for (int l1 = 0; l1 < inventorycrafting.getSize(); l1++) {
/*  69 */             ItemStack itemstack1 = inventorycrafting.getItem(l1);
/*     */             
/*  71 */             if (itemstack1 != null && itemstack1.getItem() == Items.FIREWORKS_CHARGE && itemstack1.hasTag() && itemstack1.getTag().hasKeyOfType("Explosion", 10)) {
/*  72 */               nbttaglist.add(itemstack1.getTag().getCompound("Explosion"));
/*     */             }
/*     */           } 
/*     */           
/*  76 */           nbttagcompound1.set("Explosions", nbttaglist);
/*  77 */           nbttagcompound1.setByte("Flight", (byte)j);
/*  78 */           nbttagcompound.set("Fireworks", nbttagcompound1);
/*  79 */           this.a.setTag(nbttagcompound);
/*     */         } 
/*     */         
/*  82 */         return true;
/*  83 */       }  if (j == 1 && i == 0 && l == 0 && k > 0 && j1 <= 1) {
/*  84 */         this.a = new ItemStack(Items.FIREWORKS_CHARGE);
/*  85 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  86 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  87 */         byte b0 = 0;
/*  88 */         ArrayList<Integer> arraylist = new ArrayList();
/*     */         
/*  90 */         for (int i2 = 0; i2 < inventorycrafting.getSize(); i2++) {
/*  91 */           ItemStack itemstack2 = inventorycrafting.getItem(i2);
/*     */           
/*  93 */           if (itemstack2 != null) {
/*  94 */             if (itemstack2.getItem() == Items.INK_SACK) {
/*  95 */               arraylist.add(Integer.valueOf(ItemDye.c[itemstack2.getData()]));
/*  96 */             } else if (itemstack2.getItem() == Items.GLOWSTONE_DUST) {
/*  97 */               nbttagcompound1.setBoolean("Flicker", true);
/*  98 */             } else if (itemstack2.getItem() == Items.DIAMOND) {
/*  99 */               nbttagcompound1.setBoolean("Trail", true);
/* 100 */             } else if (itemstack2.getItem() == Items.FIREBALL) {
/* 101 */               b0 = 1;
/* 102 */             } else if (itemstack2.getItem() == Items.FEATHER) {
/* 103 */               b0 = 4;
/* 104 */             } else if (itemstack2.getItem() == Items.GOLD_NUGGET) {
/* 105 */               b0 = 2;
/* 106 */             } else if (itemstack2.getItem() == Items.SKULL) {
/* 107 */               b0 = 3;
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 112 */         int[] aint = new int[arraylist.size()];
/*     */         
/* 114 */         for (int j2 = 0; j2 < aint.length; j2++) {
/* 115 */           aint[j2] = ((Integer)arraylist.get(j2)).intValue();
/*     */         }
/*     */         
/* 118 */         nbttagcompound1.setIntArray("Colors", aint);
/* 119 */         nbttagcompound1.setByte("Type", b0);
/* 120 */         nbttagcompound.set("Explosion", nbttagcompound1);
/* 121 */         this.a.setTag(nbttagcompound);
/* 122 */         return true;
/* 123 */       }  if (j == 0 && i == 0 && l == 1 && k > 0 && k == i1) {
/* 124 */         ArrayList<Integer> arraylist1 = new ArrayList();
/*     */         
/* 126 */         for (int k2 = 0; k2 < inventorycrafting.getSize(); k2++) {
/* 127 */           ItemStack itemstack3 = inventorycrafting.getItem(k2);
/*     */           
/* 129 */           if (itemstack3 != null) {
/* 130 */             if (itemstack3.getItem() == Items.INK_SACK) {
/* 131 */               arraylist1.add(Integer.valueOf(ItemDye.c[itemstack3.getData()]));
/* 132 */             } else if (itemstack3.getItem() == Items.FIREWORKS_CHARGE) {
/* 133 */               this.a = itemstack3.cloneItemStack();
/* 134 */               this.a.count = 1;
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 139 */         int[] aint1 = new int[arraylist1.size()];
/*     */         
/* 141 */         for (int l2 = 0; l2 < aint1.length; l2++) {
/* 142 */           aint1[l2] = ((Integer)arraylist1.get(l2)).intValue();
/*     */         }
/*     */         
/* 145 */         if (this.a != null && this.a.hasTag()) {
/* 146 */           NBTTagCompound nbttagcompound2 = this.a.getTag().getCompound("Explosion");
/*     */           
/* 148 */           if (nbttagcompound2 == null) {
/* 149 */             return false;
/*     */           }
/* 151 */           nbttagcompound2.setIntArray("FadeColors", aint1);
/* 152 */           return true;
/*     */         } 
/*     */         
/* 155 */         return false;
/*     */       } 
/*     */       
/* 158 */       return false;
/*     */     } 
/*     */     
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack a(InventoryCrafting inventorycrafting) {
/* 166 */     return this.a.cloneItemStack();
/*     */   }
/*     */   
/*     */   public int a() {
/* 170 */     return 10;
/*     */   }
/*     */   
/*     */   public ItemStack b() {
/* 174 */     return this.a;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipeFireworks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */