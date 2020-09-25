/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemEnderEye
/*     */   extends Item
/*     */ {
/*     */   public ItemEnderEye() {
/*  13 */     a(CreativeModeTab.f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  19 */     Block block = paramWorld.getType(paramInt1, paramInt2, paramInt3);
/*  20 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/*  22 */     if (paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack) && block == Blocks.ENDER_PORTAL_FRAME && !BlockEnderPortalFrame.b(i)) {
/*  23 */       if (paramWorld.isStatic) return true; 
/*  24 */       paramWorld.setData(paramInt1, paramInt2, paramInt3, i + 4, 2);
/*  25 */       paramWorld.updateAdjacentComparators(paramInt1, paramInt2, paramInt3, Blocks.ENDER_PORTAL_FRAME);
/*  26 */       paramItemStack.count--;
/*     */       int j;
/*  28 */       for (j = 0; j < 16; j++) {
/*  29 */         double d1 = (paramInt1 + (5.0F + g.nextFloat() * 6.0F) / 16.0F);
/*  30 */         double d2 = (paramInt2 + 0.8125F);
/*  31 */         double d3 = (paramInt3 + (5.0F + g.nextFloat() * 6.0F) / 16.0F);
/*  32 */         double d4 = 0.0D;
/*  33 */         double d5 = 0.0D;
/*  34 */         double d6 = 0.0D;
/*     */         
/*  36 */         paramWorld.addParticle("smoke", d1, d2, d3, d4, d5, d6);
/*     */       } 
/*     */ 
/*     */       
/*  40 */       j = i & 0x3;
/*     */ 
/*     */       
/*  43 */       int k = 0;
/*  44 */       int m = 0;
/*  45 */       boolean bool1 = false;
/*  46 */       boolean bool2 = true;
/*  47 */       int n = Direction.g[j]; int i1;
/*  48 */       for (i1 = -2; i1 <= 2; i1++) {
/*  49 */         int i2 = paramInt1 + Direction.a[n] * i1;
/*  50 */         int i3 = paramInt3 + Direction.b[n] * i1;
/*     */         
/*  52 */         if (paramWorld.getType(i2, paramInt2, i3) == Blocks.ENDER_PORTAL_FRAME) {
/*  53 */           if (!BlockEnderPortalFrame.b(paramWorld.getData(i2, paramInt2, i3))) {
/*  54 */             bool2 = false;
/*     */             break;
/*     */           } 
/*  57 */           m = i1;
/*  58 */           if (!bool1) {
/*  59 */             k = i1;
/*  60 */             bool1 = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  66 */       if (bool2 && m == k + 2) {
/*     */ 
/*     */         
/*  69 */         for (i1 = k; i1 <= m; i1++) {
/*  70 */           int i2 = paramInt1 + Direction.a[n] * i1;
/*  71 */           int i3 = paramInt3 + Direction.b[n] * i1;
/*  72 */           i2 += Direction.a[j] * 4;
/*  73 */           i3 += Direction.b[j] * 4;
/*     */           
/*  75 */           if (paramWorld.getType(i2, paramInt2, i3) != Blocks.ENDER_PORTAL_FRAME || !BlockEnderPortalFrame.b(paramWorld.getData(i2, paramInt2, i3))) {
/*  76 */             bool2 = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  81 */         for (i1 = k - 1; i1 <= m + 1; i1 += 4) {
/*  82 */           for (byte b = 1; b <= 3; b++) {
/*  83 */             int i2 = paramInt1 + Direction.a[n] * i1;
/*  84 */             int i3 = paramInt3 + Direction.b[n] * i1;
/*  85 */             i2 += Direction.a[j] * b;
/*  86 */             i3 += Direction.b[j] * b;
/*     */             
/*  88 */             if (paramWorld.getType(i2, paramInt2, i3) != Blocks.ENDER_PORTAL_FRAME || !BlockEnderPortalFrame.b(paramWorld.getData(i2, paramInt2, i3))) {
/*  89 */               bool2 = false;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*  94 */         if (bool2)
/*     */         {
/*  96 */           for (i1 = k; i1 <= m; i1++) {
/*  97 */             for (byte b = 1; b <= 3; b++) {
/*  98 */               int i2 = paramInt1 + Direction.a[n] * i1;
/*  99 */               int i3 = paramInt3 + Direction.b[n] * i1;
/* 100 */               i2 += Direction.a[j] * b;
/* 101 */               i3 += Direction.b[j] * b;
/*     */               
/* 103 */               paramWorld.setTypeAndData(i2, paramInt2, i3, Blocks.ENDER_PORTAL, 0, 2);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 109 */       return true;
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 117 */     MovingObjectPosition movingObjectPosition = a(paramWorld, paramEntityHuman, false);
/* 118 */     if (movingObjectPosition != null && movingObjectPosition.type == EnumMovingObjectType.BLOCK && 
/* 119 */       paramWorld.getType(movingObjectPosition.b, movingObjectPosition.c, movingObjectPosition.d) == Blocks.ENDER_PORTAL_FRAME) {
/* 120 */       return paramItemStack;
/*     */     }
/*     */ 
/*     */     
/* 124 */     if (!paramWorld.isStatic) {
/* 125 */       ChunkPosition chunkPosition = paramWorld.b("Stronghold", (int)paramEntityHuman.locX, (int)paramEntityHuman.locY, (int)paramEntityHuman.locZ);
/* 126 */       if (chunkPosition != null) {
/* 127 */         EntityEnderSignal entityEnderSignal = new EntityEnderSignal(paramWorld, paramEntityHuman.locX, paramEntityHuman.locY + 1.62D - paramEntityHuman.height, paramEntityHuman.locZ);
/* 128 */         entityEnderSignal.a(chunkPosition.x, chunkPosition.y, chunkPosition.z);
/* 129 */         paramWorld.addEntity(entityEnderSignal);
/*     */         
/* 131 */         paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 132 */         paramWorld.a((EntityHuman)null, 1002, (int)paramEntityHuman.locX, (int)paramEntityHuman.locY, (int)paramEntityHuman.locZ, 0);
/* 133 */         if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 134 */           paramItemStack.count--;
/*     */         }
/*     */       } 
/*     */     } 
/* 138 */     return paramItemStack;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemEnderEye.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */