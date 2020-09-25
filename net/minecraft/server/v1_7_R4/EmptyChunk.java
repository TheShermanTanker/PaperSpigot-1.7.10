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
/*     */ public class EmptyChunk
/*     */   extends Chunk
/*     */ {
/*     */   public EmptyChunk(World paramWorld, int paramInt1, int paramInt2) {
/*  15 */     super(paramWorld, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(int paramInt1, int paramInt2) {
/*  20 */     return (paramInt1 == this.locX && paramInt2 == this.locZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public int b(int paramInt1, int paramInt2) {
/*  25 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initLighting() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getType(int paramInt1, int paramInt2, int paramInt3) {
/*  38 */     return Blocks.AIR;
/*     */   }
/*     */ 
/*     */   
/*     */   public int b(int paramInt1, int paramInt2, int paramInt3) {
/*  43 */     return 255;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getData(int paramInt1, int paramInt2, int paramInt3) {
/*  58 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightness(EnumSkyBlock paramEnumSkyBlock, int paramInt1, int paramInt2, int paramInt3) {
/*  68 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(EnumSkyBlock paramEnumSkyBlock, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */ 
/*     */   
/*     */   public int b(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  77 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(Entity paramEntity) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(Entity paramEntity) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(Entity paramEntity, int paramInt) {}
/*     */ 
/*     */   
/*     */   public boolean d(int paramInt1, int paramInt2, int paramInt3) {
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity e(int paramInt1, int paramInt2, int paramInt3) {
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(TileEntity paramTileEntity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3, TileEntity paramTileEntity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void f(int paramInt1, int paramInt2, int paramInt3) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntities() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEntities() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(Entity paramEntity, AxisAlignedBB paramAxisAlignedBB, List paramList, IEntitySelector paramIEntitySelector) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(Class paramClass, AxisAlignedBB paramAxisAlignedBB, List paramList, IEntitySelector paramIEntitySelector) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(boolean paramBoolean) {
/* 141 */     return false;
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
/*     */   public Random a(long paramLong) {
/* 167 */     return new Random(this.world.getSeed() + (this.locX * this.locX * 4987142) + (this.locX * 5947611) + (this.locZ * this.locZ) * 4392871L + (this.locZ * 389711) ^ paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c(int paramInt1, int paramInt2) {
/* 177 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EmptyChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */