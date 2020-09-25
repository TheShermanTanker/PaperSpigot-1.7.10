/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ 
/*     */ public class ItemSkull
/*     */   extends Item
/*     */ {
/*   9 */   private static final String[] b = new String[] { "skeleton", "wither", "zombie", "char", "creeper" };
/*  10 */   public static final String[] a = new String[] { "skeleton", "wither", "zombie", "steve", "creeper" };
/*     */   
/*     */   public ItemSkull() {
/*  13 */     a(CreativeModeTab.c);
/*  14 */     setMaxDurability(0);
/*  15 */     a(true);
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/*  19 */     if (l == 0)
/*  20 */       return false; 
/*  21 */     if (!world.getType(i, j, k).getMaterial().isBuildable()) {
/*  22 */       return false;
/*     */     }
/*  24 */     if (l == 1) {
/*  25 */       j++;
/*     */     }
/*     */     
/*  28 */     if (l == 2) {
/*  29 */       k--;
/*     */     }
/*     */     
/*  32 */     if (l == 3) {
/*  33 */       k++;
/*     */     }
/*     */     
/*  36 */     if (l == 4) {
/*  37 */       i--;
/*     */     }
/*     */     
/*  40 */     if (l == 5) {
/*  41 */       i++;
/*     */     }
/*     */     
/*  44 */     if (!world.isStatic) {
/*     */       
/*  46 */       if (!Blocks.SKULL.canPlace(world, i, j, k))
/*     */       {
/*  48 */         return false;
/*     */       }
/*     */       
/*  51 */       world.setTypeAndData(i, j, k, Blocks.SKULL, l, 2);
/*  52 */       int i1 = 0;
/*     */       
/*  54 */       if (l == 1) {
/*  55 */         i1 = MathHelper.floor((entityhuman.yaw * 16.0F / 360.0F) + 0.5D) & 0xF;
/*     */       }
/*     */       
/*  58 */       TileEntity tileentity = world.getTileEntity(i, j, k);
/*     */       
/*  60 */       if (tileentity != null && tileentity instanceof TileEntitySkull) {
/*  61 */         if (itemstack.getData() == 3) {
/*  62 */           GameProfile gameprofile = null;
/*     */           
/*  64 */           if (itemstack.hasTag()) {
/*  65 */             NBTTagCompound nbttagcompound = itemstack.getTag();
/*     */             
/*  67 */             if (nbttagcompound.hasKeyOfType("SkullOwner", 10)) {
/*  68 */               gameprofile = GameProfileSerializer.deserialize(nbttagcompound.getCompound("SkullOwner"));
/*  69 */             } else if (nbttagcompound.hasKeyOfType("SkullOwner", 8) && nbttagcompound.getString("SkullOwner").length() > 0) {
/*  70 */               gameprofile = new GameProfile((UUID)null, nbttagcompound.getString("SkullOwner"));
/*     */             } 
/*     */           } 
/*     */           
/*  74 */           ((TileEntitySkull)tileentity).setGameProfile(gameprofile);
/*     */         } else {
/*  76 */           ((TileEntitySkull)tileentity).setSkullType(itemstack.getData());
/*     */         } 
/*     */         
/*  79 */         ((TileEntitySkull)tileentity).setRotation(i1);
/*  80 */         ((BlockSkull)Blocks.SKULL).a(world, i, j, k, (TileEntitySkull)tileentity);
/*     */       } 
/*     */       
/*  83 */       itemstack.count--;
/*     */     } 
/*     */     
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int filterData(int i) {
/*  91 */     return i;
/*     */   }
/*     */   
/*     */   public String a(ItemStack itemstack) {
/*  95 */     int i = itemstack.getData();
/*     */     
/*  97 */     if (i < 0 || i >= b.length) {
/*  98 */       i = 0;
/*     */     }
/*     */     
/* 101 */     return getName() + "." + b[i];
/*     */   }
/*     */   
/*     */   public String n(ItemStack itemstack) {
/* 105 */     if (itemstack.getData() == 3 && itemstack.hasTag()) {
/* 106 */       if (itemstack.getTag().hasKeyOfType("SkullOwner", 10)) {
/* 107 */         return LocaleI18n.get("item.skull.player.name", new Object[] { GameProfileSerializer.deserialize(itemstack.getTag().getCompound("SkullOwner")).getName() });
/*     */       }
/*     */       
/* 110 */       if (itemstack.getTag().hasKeyOfType("SkullOwner", 8)) {
/* 111 */         return LocaleI18n.get("item.skull.player.name", new Object[] { itemstack.getTag().getString("SkullOwner") });
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return super.n(itemstack);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */