/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public class BlockPoweredRail extends BlockMinecartTrackAbstract {
/*     */   protected BlockPoweredRail() {
/*   7 */     super(true);
/*     */   }
/*     */   
/*     */   protected boolean a(World world, int i, int j, int k, int l, boolean flag, int i1) {
/*  11 */     if (i1 >= 8) {
/*  12 */       return false;
/*     */     }
/*  14 */     int j1 = l & 0x7;
/*  15 */     boolean flag1 = true;
/*     */     
/*  17 */     switch (j1) {
/*     */       case 0:
/*  19 */         if (flag) {
/*  20 */           k++; break;
/*     */         } 
/*  22 */         k--;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/*  27 */         if (flag) {
/*  28 */           i--; break;
/*     */         } 
/*  30 */         i++;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/*  35 */         if (flag) {
/*  36 */           i--;
/*     */         } else {
/*  38 */           i++;
/*  39 */           j++;
/*  40 */           flag1 = false;
/*     */         } 
/*     */         
/*  43 */         j1 = 1;
/*     */         break;
/*     */       
/*     */       case 3:
/*  47 */         if (flag) {
/*  48 */           i--;
/*  49 */           j++;
/*  50 */           flag1 = false;
/*     */         } else {
/*  52 */           i++;
/*     */         } 
/*     */         
/*  55 */         j1 = 1;
/*     */         break;
/*     */       
/*     */       case 4:
/*  59 */         if (flag) {
/*  60 */           k++;
/*     */         } else {
/*  62 */           k--;
/*  63 */           j++;
/*  64 */           flag1 = false;
/*     */         } 
/*     */         
/*  67 */         j1 = 0;
/*     */         break;
/*     */       
/*     */       case 5:
/*  71 */         if (flag) {
/*  72 */           k++;
/*  73 */           j++;
/*  74 */           flag1 = false;
/*     */         } else {
/*  76 */           k--;
/*     */         } 
/*     */         
/*  79 */         j1 = 0;
/*     */         break;
/*     */     } 
/*  82 */     return a(world, i, j, k, flag, i1, j1) ? true : ((flag1 && a(world, i, j - 1, k, flag, i1, j1)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(World world, int i, int j, int k, boolean flag, int l, int i1) {
/*  87 */     Block block = world.getType(i, j, k);
/*     */     
/*  89 */     if (block == this) {
/*  90 */       int j1 = world.getData(i, j, k);
/*  91 */       int k1 = j1 & 0x7;
/*     */       
/*  93 */       if (i1 == 1 && (k1 == 0 || k1 == 4 || k1 == 5)) {
/*  94 */         return false;
/*     */       }
/*     */       
/*  97 */       if (i1 == 0 && (k1 == 1 || k1 == 2 || k1 == 3)) {
/*  98 */         return false;
/*     */       }
/*     */       
/* 101 */       if ((j1 & 0x8) != 0) {
/* 102 */         if (world.isBlockIndirectlyPowered(i, j, k)) {
/* 103 */           return true;
/*     */         }
/*     */         
/* 106 */         return a(world, i, j, k, j1, flag, l + 1);
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   protected void a(World world, int i, int j, int k, int l, int i1, Block block) {
/* 114 */     boolean flag = world.isBlockIndirectlyPowered(i, j, k);
/*     */     
/* 116 */     flag = (flag || a(world, i, j, k, l, true, 0) || a(world, i, j, k, l, false, 0));
/* 117 */     boolean flag1 = false;
/*     */     
/* 119 */     if (flag && (l & 0x8) == 0) {
/*     */       
/* 121 */       if (CraftEventFactory.callRedstoneChange(world, i, j, k, 0, 15).getNewCurrent() <= 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 126 */       world.setData(i, j, k, i1 | 0x8, 3);
/* 127 */       flag1 = true;
/* 128 */     } else if (!flag && (l & 0x8) != 0) {
/*     */       
/* 130 */       if (CraftEventFactory.callRedstoneChange(world, i, j, k, 15, 0).getNewCurrent() > 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 135 */       world.setData(i, j, k, i1, 3);
/* 136 */       flag1 = true;
/*     */     } 
/*     */     
/* 139 */     if (flag1) {
/* 140 */       world.applyPhysics(i, j - 1, k, this);
/* 141 */       if (i1 == 2 || i1 == 3 || i1 == 4 || i1 == 5)
/* 142 */         world.applyPhysics(i, j + 1, k, this); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPoweredRail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */