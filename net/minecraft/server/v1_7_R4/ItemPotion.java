/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class ItemPotion
/*     */   extends Item
/*     */ {
/*  25 */   private HashMap a = new HashMap<Object, Object>();
/*  26 */   private static final Map b = new LinkedHashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemPotion() {
/*  33 */     e(1);
/*  34 */     a(true);
/*  35 */     setMaxDurability(0);
/*  36 */     a(CreativeModeTab.k);
/*     */   }
/*     */   
/*     */   public List g(ItemStack paramItemStack) {
/*  40 */     if (!paramItemStack.hasTag() || !paramItemStack.getTag().hasKeyOfType("CustomPotionEffects", 9)) {
/*  41 */       List list = (List)this.a.get(Integer.valueOf(paramItemStack.getData()));
/*     */       
/*  43 */       if (list == null) {
/*  44 */         list = PotionBrewer.getEffects(paramItemStack.getData(), false);
/*  45 */         this.a.put(Integer.valueOf(paramItemStack.getData()), list);
/*     */       } 
/*     */       
/*  48 */       return list;
/*     */     } 
/*  50 */     ArrayList<MobEffect> arrayList = new ArrayList();
/*  51 */     NBTTagList nBTTagList = paramItemStack.getTag().getList("CustomPotionEffects", 10);
/*     */     
/*  53 */     for (byte b = 0; b < nBTTagList.size(); b++) {
/*  54 */       NBTTagCompound nBTTagCompound = nBTTagList.get(b);
/*  55 */       MobEffect mobEffect = MobEffect.b(nBTTagCompound);
/*  56 */       if (mobEffect != null) {
/*  57 */         arrayList.add(mobEffect);
/*     */       }
/*     */     } 
/*     */     
/*  61 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public List c(int paramInt) {
/*  66 */     List list = (List)this.a.get(Integer.valueOf(paramInt));
/*  67 */     if (list == null) {
/*  68 */       list = PotionBrewer.getEffects(paramInt, false);
/*  69 */       this.a.put(Integer.valueOf(paramInt), list);
/*     */     } 
/*  71 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/*  76 */     if (!paramEntityHuman.abilities.canInstantlyBuild) paramItemStack.count--;
/*     */     
/*  78 */     if (!paramWorld.isStatic) {
/*  79 */       List list = g(paramItemStack);
/*  80 */       if (list != null) {
/*  81 */         for (MobEffect mobEffect : list) {
/*  82 */           paramEntityHuman.addEffect(new MobEffect(mobEffect));
/*     */         }
/*     */       }
/*     */     } 
/*  86 */     if (!paramEntityHuman.abilities.canInstantlyBuild) {
/*  87 */       if (paramItemStack.count <= 0) {
/*  88 */         return new ItemStack(Items.GLASS_BOTTLE);
/*     */       }
/*  90 */       paramEntityHuman.inventory.pickup(new ItemStack(Items.GLASS_BOTTLE));
/*     */     } 
/*     */ 
/*     */     
/*  94 */     return paramItemStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public int d_(ItemStack paramItemStack) {
/*  99 */     return 32;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumAnimation d(ItemStack paramItemStack) {
/* 104 */     return EnumAnimation.DRINK;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 109 */     if (g(paramItemStack.getData())) {
/* 110 */       if (!paramEntityHuman.abilities.canInstantlyBuild) paramItemStack.count--; 
/* 111 */       paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 112 */       if (!paramWorld.isStatic) paramWorld.addEntity(new EntityPotion(paramWorld, paramEntityHuman, paramItemStack)); 
/* 113 */       return paramItemStack;
/*     */     } 
/* 115 */     paramEntityHuman.a(paramItemStack, d_(paramItemStack));
/* 116 */     return paramItemStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 121 */     return false;
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
/*     */   public static boolean g(int paramInt) {
/* 141 */     return ((paramInt & 0x4000) != 0);
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
/*     */   public String n(ItemStack paramItemStack) {
/* 176 */     if (paramItemStack.getData() == 0) {
/* 177 */       return LocaleI18n.get("item.emptyPotion.name").trim();
/*     */     }
/*     */     
/* 180 */     String str1 = "";
/* 181 */     if (g(paramItemStack.getData())) {
/* 182 */       str1 = LocaleI18n.get("potion.prefix.grenade").trim() + " ";
/*     */     }
/*     */     
/* 185 */     List<MobEffect> list = Items.POTION.g(paramItemStack);
/* 186 */     if (list != null && !list.isEmpty()) {
/* 187 */       String str = ((MobEffect)list.get(0)).f();
/* 188 */       str = str + ".postfix";
/* 189 */       return str1 + LocaleI18n.get(str).trim();
/*     */     } 
/* 191 */     String str2 = PotionBrewer.c(paramItemStack.getData());
/* 192 */     return LocaleI18n.get(str2).trim() + " " + super.n(paramItemStack);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */