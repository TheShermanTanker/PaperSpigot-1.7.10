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
/*     */ public class BlockRedstoneComparator
/*     */   extends BlockDiodeAbstract
/*     */   implements IContainer
/*     */ {
/*     */   public BlockRedstoneComparator(boolean paramBoolean) {
/*  20 */     super(paramBoolean);
/*  21 */     this.isTileEntity = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/*  26 */     return Items.REDSTONE_COMPARATOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int b(int paramInt) {
/*  36 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockDiodeAbstract e() {
/*  41 */     return Blocks.REDSTONE_COMPARATOR_ON;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockDiodeAbstract i() {
/*  46 */     return Blocks.REDSTONE_COMPARATOR_OFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/*  51 */     return 37;
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
/*     */   protected boolean c(int paramInt) {
/*  76 */     return (this.a || (paramInt & 0x8) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int f(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  81 */     return e(paramIBlockAccess, paramInt1, paramInt2, paramInt3).a();
/*     */   }
/*     */   
/*     */   private int j(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  85 */     if (!d(paramInt4)) {
/*  86 */       return h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*  88 */     return Math.max(h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4) - h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4), 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d(int paramInt) {
/*  93 */     return ((paramInt & 0x4) == 4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  98 */     int i = h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
/*  99 */     if (i >= 15) return true; 
/* 100 */     if (i == 0) return false;
/*     */     
/* 102 */     int j = h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
/* 103 */     if (j == 0) return true;
/*     */     
/* 105 */     return (i >= j);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int h(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 110 */     int i = super.h(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/* 112 */     int j = l(paramInt4);
/* 113 */     int k = paramInt1 + Direction.a[j];
/* 114 */     int m = paramInt3 + Direction.b[j];
/* 115 */     Block block = paramWorld.getType(k, paramInt2, m);
/*     */     
/* 117 */     if (block.isComplexRedstone()) {
/* 118 */       i = block.g(paramWorld, k, paramInt2, m, Direction.f[j]);
/* 119 */     } else if (i < 15 && block.r()) {
/* 120 */       k += Direction.a[j];
/* 121 */       m += Direction.b[j];
/*     */       
/* 123 */       block = paramWorld.getType(k, paramInt2, m);
/* 124 */       if (block.isComplexRedstone()) {
/* 125 */         i = block.g(paramWorld, k, paramInt2, m, Direction.f[j]);
/*     */       }
/*     */     } 
/*     */     
/* 129 */     return i;
/*     */   }
/*     */   
/*     */   public TileEntityComparator e(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 133 */     return (TileEntityComparator)paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 138 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 139 */     int j = this.a | (((i & 0x8) != 0) ? 1 : 0);
/* 140 */     boolean bool = !d(i) ? true : false;
/* 141 */     int k = bool ? 4 : 0;
/* 142 */     k |= (j != 0) ? 8 : 0;
/*     */     
/* 144 */     paramWorld.makeSound(paramInt1 + 0.5D, paramInt2 + 0.5D, paramInt3 + 0.5D, "random.click", 0.3F, bool ? 0.55F : 0.5F);
/*     */     
/* 146 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, k | i & 0x3, 2);
/* 147 */     c(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.random);
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/* 153 */     if (!paramWorld.a(paramInt1, paramInt2, paramInt3, this)) {
/* 154 */       int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 155 */       int j = j(paramWorld, paramInt1, paramInt2, paramInt3, i);
/* 156 */       int k = e(paramWorld, paramInt1, paramInt2, paramInt3).a();
/*     */       
/* 158 */       if (j != k || c(i) != a(paramWorld, paramInt1, paramInt2, paramInt3, i))
/*     */       {
/* 160 */         if (i(paramWorld, paramInt1, paramInt2, paramInt3, i)) {
/* 161 */           paramWorld.a(paramInt1, paramInt2, paramInt3, this, b(0), -1);
/*     */         } else {
/* 163 */           paramWorld.a(paramInt1, paramInt2, paramInt3, this, b(0), 0);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/* 170 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 171 */     int j = j(paramWorld, paramInt1, paramInt2, paramInt3, i);
/* 172 */     int k = e(paramWorld, paramInt1, paramInt2, paramInt3).a();
/* 173 */     e(paramWorld, paramInt1, paramInt2, paramInt3).a(j);
/*     */     
/* 175 */     if (k != j || !d(i)) {
/* 176 */       boolean bool = a(paramWorld, paramInt1, paramInt2, paramInt3, i);
/* 177 */       boolean bool1 = (this.a || (i & 0x8) != 0) ? true : false;
/* 178 */       if (bool1 && !bool) {
/* 179 */         paramWorld.setData(paramInt1, paramInt2, paramInt3, i & 0xFFFFFFF7, 2);
/* 180 */       } else if (!bool1 && bool) {
/* 181 */         paramWorld.setData(paramInt1, paramInt2, paramInt3, i | 0x8, 2);
/*     */       } 
/* 183 */       e(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/* 189 */     if (this.a) {
/*     */       
/* 191 */       int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 192 */       paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, i(), i | 0x8, 4);
/*     */     } 
/* 194 */     c(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 199 */     super.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
/* 200 */     paramWorld.setTileEntity(paramInt1, paramInt2, paramInt3, a(paramWorld, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 205 */     super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
/* 206 */     paramWorld.p(paramInt1, paramInt2, paramInt3);
/*     */     
/* 208 */     e(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 213 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 214 */     TileEntity tileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 215 */     if (tileEntity != null) {
/* 216 */       return tileEntity.c(paramInt4, paramInt5);
/*     */     }
/* 218 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt) {
/* 223 */     return new TileEntityComparator();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockRedstoneComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */