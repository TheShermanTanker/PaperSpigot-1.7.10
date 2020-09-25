/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.SheepDyeWoolEvent;
/*     */ 
/*     */ public class ItemDye extends Item {
/*   7 */   public static final String[] a = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };
/*   8 */   public static final String[] b = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white" };
/*   9 */   public static final int[] c = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
/*     */   
/*     */   public ItemDye() {
/*  12 */     a(true);
/*  13 */     setMaxDurability(0);
/*  14 */     a(CreativeModeTab.l);
/*     */   }
/*     */   
/*     */   public String a(ItemStack itemstack) {
/*  18 */     int i = MathHelper.a(itemstack.getData(), 0, 15);
/*     */     
/*  20 */     return getName() + "." + a[i];
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/*  24 */     if (!entityhuman.a(i, j, k, l, itemstack)) {
/*  25 */       return false;
/*     */     }
/*  27 */     if (itemstack.getData() == 15) {
/*  28 */       if (a(itemstack, world, i, j, k)) {
/*  29 */         if (!world.isStatic) {
/*  30 */           world.triggerEffect(2005, i, j, k, 0);
/*     */         }
/*     */         
/*  33 */         return true;
/*     */       } 
/*  35 */     } else if (itemstack.getData() == 3) {
/*  36 */       Block block = world.getType(i, j, k);
/*  37 */       int i1 = world.getData(i, j, k);
/*     */       
/*  39 */       if (block == Blocks.LOG && BlockLogAbstract.c(i1) == 3) {
/*  40 */         if (l == 0) {
/*  41 */           return false;
/*     */         }
/*     */         
/*  44 */         if (l == 1) {
/*  45 */           return false;
/*     */         }
/*     */         
/*  48 */         if (l == 2) {
/*  49 */           k--;
/*     */         }
/*     */         
/*  52 */         if (l == 3) {
/*  53 */           k++;
/*     */         }
/*     */         
/*  56 */         if (l == 4) {
/*  57 */           i--;
/*     */         }
/*     */         
/*  60 */         if (l == 5) {
/*  61 */           i++;
/*     */         }
/*     */         
/*  64 */         if (world.isEmpty(i, j, k)) {
/*  65 */           int j1 = Blocks.COCOA.getPlacedData(world, i, j, k, l, f, f1, f2, 0);
/*     */           
/*  67 */           world.setTypeAndData(i, j, k, Blocks.COCOA, j1, 2);
/*  68 */           if (!entityhuman.abilities.canInstantlyBuild) {
/*  69 */             itemstack.count--;
/*     */           }
/*     */         } 
/*     */         
/*  73 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean a(ItemStack itemstack, World world, int i, int j, int k) {
/*  82 */     Block block = world.getType(i, j, k);
/*     */     
/*  84 */     if (block instanceof IBlockFragilePlantElement) {
/*  85 */       IBlockFragilePlantElement iblockfragileplantelement = (IBlockFragilePlantElement)block;
/*     */       
/*  87 */       if (iblockfragileplantelement.a(world, i, j, k, world.isStatic)) {
/*  88 */         if (!world.isStatic) {
/*  89 */           if (iblockfragileplantelement.a(world, world.random, i, j, k)) {
/*  90 */             iblockfragileplantelement.b(world, world.random, i, j, k);
/*     */           }
/*     */           
/*  93 */           itemstack.count--;
/*     */         } 
/*     */         
/*  96 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, EntityLiving entityliving) {
/* 104 */     if (entityliving instanceof EntitySheep) {
/* 105 */       EntitySheep entitysheep = (EntitySheep)entityliving;
/* 106 */       int i = BlockCloth.b(itemstack.getData());
/*     */       
/* 108 */       if (!entitysheep.isSheared() && entitysheep.getColor() != i) {
/*     */         
/* 110 */         byte bColor = (byte)i;
/* 111 */         SheepDyeWoolEvent event = new SheepDyeWoolEvent((Sheep)entitysheep.getBukkitEntity(), DyeColor.getByData(bColor));
/* 112 */         entitysheep.world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/* 114 */         if (event.isCancelled()) {
/* 115 */           return false;
/*     */         }
/*     */         
/* 118 */         i = event.getColor().getWoolData();
/*     */         
/* 120 */         entitysheep.setColor(i);
/* 121 */         itemstack.count--;
/*     */       } 
/*     */       
/* 124 */       return true;
/*     */     } 
/* 126 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemDye.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */