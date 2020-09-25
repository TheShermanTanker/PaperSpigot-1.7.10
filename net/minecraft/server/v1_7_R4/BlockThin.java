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
/*     */ public class BlockThin
/*     */   extends Block
/*     */ {
/*     */   private final String a;
/*     */   private final boolean b;
/*     */   private final String M;
/*     */   
/*     */   protected BlockThin(String paramString1, String paramString2, Material paramMaterial, boolean paramBoolean) {
/*  20 */     super(paramMaterial);
/*  21 */     this.a = paramString2;
/*  22 */     this.b = paramBoolean;
/*  23 */     this.M = paramString1;
/*  24 */     a(CreativeModeTab.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/*  29 */     if (!this.b) {
/*  30 */       return null;
/*     */     }
/*  32 */     return super.getDropType(paramInt1, paramRandom, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  37 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/*  47 */     return (this.material == Material.SHATTERABLE) ? 41 : 18;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity) {
/*  58 */     boolean bool1 = a(paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1));
/*  59 */     boolean bool2 = a(paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1));
/*  60 */     boolean bool3 = a(paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3));
/*  61 */     boolean bool4 = a(paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3));
/*     */     
/*  63 */     if ((bool3 && bool4) || (!bool3 && !bool4 && !bool1 && !bool2)) {
/*  64 */       a(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  65 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*  66 */     } else if (bool3 && !bool4) {
/*  67 */       a(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
/*  68 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*  69 */     } else if (!bool3 && bool4) {
/*  70 */       a(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  71 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */     } 
/*  73 */     if ((bool1 && bool2) || (!bool3 && !bool4 && !bool1 && !bool2)) {
/*  74 */       a(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
/*  75 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*  76 */     } else if (bool1 && !bool2) {
/*  77 */       a(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
/*  78 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*  79 */     } else if (!bool1 && bool2) {
/*  80 */       a(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
/*  81 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void g() {
/*  87 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  92 */     float f1 = 0.4375F;
/*  93 */     float f2 = 0.5625F;
/*  94 */     float f3 = 0.4375F;
/*  95 */     float f4 = 0.5625F;
/*     */     
/*  97 */     boolean bool1 = a(paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 - 1));
/*  98 */     boolean bool2 = a(paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 + 1));
/*  99 */     boolean bool3 = a(paramIBlockAccess.getType(paramInt1 - 1, paramInt2, paramInt3));
/* 100 */     boolean bool4 = a(paramIBlockAccess.getType(paramInt1 + 1, paramInt2, paramInt3));
/*     */     
/* 102 */     if ((bool3 && bool4) || (!bool3 && !bool4 && !bool1 && !bool2)) {
/* 103 */       f1 = 0.0F;
/* 104 */       f2 = 1.0F;
/* 105 */     } else if (bool3 && !bool4) {
/* 106 */       f1 = 0.0F;
/* 107 */     } else if (!bool3 && bool4) {
/* 108 */       f2 = 1.0F;
/*     */     } 
/* 110 */     if ((bool1 && bool2) || (!bool3 && !bool4 && !bool1 && !bool2)) {
/* 111 */       f3 = 0.0F;
/* 112 */       f4 = 1.0F;
/* 113 */     } else if (bool1 && !bool2) {
/* 114 */       f3 = 0.0F;
/* 115 */     } else if (!bool1 && bool2) {
/* 116 */       f4 = 1.0F;
/*     */     } 
/*     */     
/* 119 */     a(f1, 0.0F, f3, f2, 1.0F, f4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean a(Block paramBlock) {
/* 127 */     return (paramBlock.j() || paramBlock == this || paramBlock == Blocks.GLASS || paramBlock == Blocks.STAINED_GLASS || paramBlock == Blocks.STAINED_GLASS_PANE || paramBlock instanceof BlockThin);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean E() {
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack j(int paramInt) {
/* 137 */     return new ItemStack(Item.getItemOf(this), 1, paramInt);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockThin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */