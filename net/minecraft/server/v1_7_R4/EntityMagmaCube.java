/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityMagmaCube
/*     */   extends EntitySlime
/*     */ {
/*     */   public EntityMagmaCube(World paramWorld) {
/*  10 */     super(paramWorld);
/*  11 */     this.fireProof = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void aD() {
/*  16 */     super.aD();
/*     */     
/*  18 */     getAttributeInstance(GenericAttributes.d).setValue(0.20000000298023224D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSpawn() {
/*  23 */     return (this.world.difficulty != EnumDifficulty.PEACEFUL && this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox));
/*     */   }
/*     */ 
/*     */   
/*     */   public int aV() {
/*  28 */     return getSize() * 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float d(float paramFloat) {
/*  38 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String bP() {
/*  43 */     return "flame";
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntitySlime bQ() {
/*  48 */     return new EntityMagmaCube(this.world);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getLoot() {
/*  53 */     return Items.MAGMA_CREAM;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropDeathLoot(boolean paramBoolean, int paramInt) {
/*  58 */     Item item = getLoot();
/*  59 */     if (item != null && getSize() > 1) {
/*  60 */       int i = this.random.nextInt(4) - 2;
/*  61 */       if (paramInt > 0) {
/*  62 */         i += this.random.nextInt(paramInt + 1);
/*     */       }
/*  64 */       for (byte b = 0; b < i; b++) {
/*  65 */         a(item, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int bR() {
/*  77 */     return super.bR() * 4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bS() {
/*  82 */     this.h *= 0.9F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bj() {
/*  87 */     this.motY = (0.42F + getSize() * 0.1F);
/*  88 */     this.al = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(float paramFloat) {}
/*     */ 
/*     */   
/*     */   protected boolean bT() {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int bU() {
/* 102 */     return super.bU() + 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String bV() {
/* 107 */     if (getSize() > 1) {
/* 108 */       return "mob.magmacube.big";
/*     */     }
/* 110 */     return "mob.magmacube.small";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean P() {
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean bW() {
/* 121 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMagmaCube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */