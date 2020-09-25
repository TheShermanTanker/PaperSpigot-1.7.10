/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public class BlockCauldron
/*     */   extends Block
/*     */ {
/*     */   public BlockCauldron() {
/*  33 */     super(Material.ORE);
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
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity) {
/*  63 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
/*  64 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*  65 */     float f = 0.125F;
/*  66 */     a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  67 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*  68 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  69 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*  70 */     a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  71 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*  72 */     a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  73 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */     
/*  75 */     g();
/*     */   }
/*     */ 
/*     */   
/*     */   public void g() {
/*  80 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/*  90 */     return 24;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity) {
/* 100 */     int i = b(paramWorld.getData(paramInt1, paramInt2, paramInt3));
/* 101 */     float f = paramInt2 + (6.0F + (3 * i)) / 16.0F;
/*     */     
/* 103 */     if (!paramWorld.isStatic && paramEntity.isBurning() && i > 0 && paramEntity.boundingBox.b <= f) {
/* 104 */       paramEntity.extinguish();
/*     */       
/* 106 */       a(paramWorld, paramInt1, paramInt2, paramInt3, i - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 112 */     if (paramWorld.isStatic) {
/* 113 */       return true;
/*     */     }
/*     */     
/* 116 */     ItemStack itemStack = paramEntityHuman.inventory.getItemInHand();
/* 117 */     if (itemStack == null) {
/* 118 */       return true;
/*     */     }
/*     */     
/* 121 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 122 */     int j = b(i);
/*     */     
/* 124 */     if (itemStack.getItem() == Items.WATER_BUCKET) {
/* 125 */       if (j < 3) {
/* 126 */         if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 127 */           paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, new ItemStack(Items.BUCKET));
/*     */         }
/*     */         
/* 130 */         a(paramWorld, paramInt1, paramInt2, paramInt3, 3);
/*     */       } 
/* 132 */       return true;
/* 133 */     }  if (itemStack.getItem() == Items.GLASS_BOTTLE) {
/* 134 */       if (j > 0) {
/* 135 */         if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 136 */           ItemStack itemStack1 = new ItemStack(Items.POTION, 1, 0);
/* 137 */           if (!paramEntityHuman.inventory.pickup(itemStack1)) {
/* 138 */             paramWorld.addEntity(new EntityItem(paramWorld, paramInt1 + 0.5D, paramInt2 + 1.5D, paramInt3 + 0.5D, itemStack1));
/* 139 */           } else if (paramEntityHuman instanceof EntityPlayer) {
/* 140 */             ((EntityPlayer)paramEntityHuman).updateInventory(paramEntityHuman.defaultContainer);
/*     */           } 
/*     */           
/* 143 */           itemStack.count--;
/* 144 */           if (itemStack.count <= 0) {
/* 145 */             paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, null);
/*     */           }
/*     */         } 
/*     */         
/* 149 */         a(paramWorld, paramInt1, paramInt2, paramInt3, j - 1);
/*     */       } 
/* 151 */     } else if (j > 0 && itemStack.getItem() instanceof ItemArmor && ((ItemArmor)itemStack.getItem()).m_() == EnumArmorMaterial.CLOTH) {
/* 152 */       ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
/* 153 */       itemArmor.c(itemStack);
/* 154 */       a(paramWorld, paramInt1, paramInt2, paramInt3, j - 1);
/* 155 */       return true;
/*     */     } 
/*     */     
/* 158 */     return false;
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 162 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, MathHelper.a(paramInt4, 0, 3), 2);
/* 163 */     paramWorld.updateAdjacentComparators(paramInt1, paramInt2, paramInt3, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void l(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 168 */     if (paramWorld.random.nextInt(20) != 1)
/*     */       return; 
/* 170 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/* 172 */     if (i < 3) {
/* 173 */       paramWorld.setData(paramInt1, paramInt2, paramInt3, i + 1, 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 179 */     return Items.CAULDRON;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 189 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 194 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/* 196 */     return b(i);
/*     */   }
/*     */   
/*     */   public static int b(int paramInt) {
/* 200 */     return paramInt;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockCauldron.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */