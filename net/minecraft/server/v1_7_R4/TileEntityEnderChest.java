/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ public class TileEntityEnderChest
/*     */   extends TileEntity
/*     */ {
/*     */   public float a;
/*     */   public float i;
/*     */   public int j;
/*     */   private int k;
/*     */   
/*     */   public void h() {
/*  13 */     super.h();
/*  14 */     if (++this.k % 4 == 0) {
/*  15 */       this.world.playBlockAction(this.x, this.y, this.z, Blocks.ENDER_CHEST, 1, this.j);
/*     */     }
/*     */     
/*  18 */     this.i = this.a;
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
/*     */   public boolean c(int i, int j) {
/*  63 */     if (i == 1) {
/*  64 */       this.j = j;
/*  65 */       return true;
/*     */     } 
/*  67 */     return super.c(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public void s() {
/*  72 */     u();
/*  73 */     super.s();
/*     */   }
/*     */   
/*     */   public void a() {
/*  77 */     this.j++;
/*  78 */     this.world.playBlockAction(this.x, this.y, this.z, Blocks.ENDER_CHEST, 1, this.j);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     if (this.j > 0 && this.a == 0.0F) {
/*  84 */       double d1 = this.x + 0.5D;
/*     */       
/*  86 */       double d0 = this.z + 0.5D;
/*  87 */       this.world.makeSound(d1, this.y + 0.5D, d0, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void b() {
/*  93 */     this.j--;
/*  94 */     this.world.playBlockAction(this.x, this.y, this.z, Blocks.ENDER_CHEST, 1, this.j);
/*     */ 
/*     */     
/*  97 */     float f = 0.1F;
/*     */ 
/*     */     
/* 100 */     if ((this.j == 0 && this.a == 0.0F) || (this.j > 0 && this.a < 1.0F)) {
/* 101 */       float f1 = this.a;
/* 102 */       double d0 = this.x + 0.5D;
/* 103 */       double d2 = this.z + 0.5D;
/*     */       
/* 105 */       this.world.makeSound(d0, this.y + 0.5D, d2, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*     */       
/* 107 */       if (this.a < 0.0F) {
/* 108 */         this.a = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 115 */     return (this.world.getTileEntity(this.x, this.y, this.z) != this) ? false : ((entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityEnderChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */