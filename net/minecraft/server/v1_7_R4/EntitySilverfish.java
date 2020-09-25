/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import net.minecraft.util.org.apache.commons.lang3.tuple.ImmutablePair;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public class EntitySilverfish
/*     */   extends EntityMonster
/*     */ {
/*     */   private int bp;
/*     */   
/*     */   public EntitySilverfish(World world) {
/*  12 */     super(world);
/*  13 */     a(0.3F, 0.7F);
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  17 */     super.aD();
/*  18 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
/*  19 */     getAttributeInstance(GenericAttributes.d).setValue(0.6000000238418579D);
/*  20 */     getAttributeInstance(GenericAttributes.e).setValue(1.0D);
/*     */   }
/*     */   
/*     */   protected boolean g_() {
/*  24 */     return false;
/*     */   }
/*     */   
/*     */   protected Entity findTarget() {
/*  28 */     double d0 = 8.0D;
/*     */     
/*  30 */     return this.world.findNearbyVulnerablePlayer(this, d0);
/*     */   }
/*     */   
/*     */   protected String t() {
/*  34 */     return "mob.silverfish.say";
/*     */   }
/*     */   
/*     */   protected String aT() {
/*  38 */     return "mob.silverfish.hit";
/*     */   }
/*     */   
/*     */   protected String aU() {
/*  42 */     return "mob.silverfish.kill";
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  46 */     if (isInvulnerable()) {
/*  47 */       return false;
/*     */     }
/*  49 */     if (this.bp <= 0 && (damagesource instanceof EntityDamageSource || damagesource == DamageSource.MAGIC)) {
/*  50 */       this.bp = 20;
/*     */     }
/*     */     
/*  53 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(Entity entity, float f) {
/*  58 */     if (this.attackTicks <= 0 && f < 1.2F && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
/*  59 */       this.attackTicks = 20;
/*  60 */       n(entity);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, int k, Block block) {
/*  65 */     makeSound("mob.silverfish.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  69 */     return Item.getById(0);
/*     */   }
/*     */   
/*     */   public void h() {
/*  73 */     this.aM = this.yaw;
/*  74 */     super.h();
/*     */   }
/*     */   
/*     */   protected void bq() {
/*  78 */     super.bq();
/*  79 */     if (!this.world.isStatic) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  85 */       if (this.bp > 0) {
/*  86 */         this.bp--;
/*  87 */         if (this.bp == 0) {
/*  88 */           int i = MathHelper.floor(this.locX);
/*  89 */           int j = MathHelper.floor(this.locY);
/*  90 */           int k = MathHelper.floor(this.locZ);
/*  91 */           boolean flag = false;
/*     */           int i1;
/*  93 */           for (i1 = 0; !flag && i1 <= 5 && i1 >= -5; i1 = (i1 <= 0) ? (1 - i1) : (0 - i1)) {
/*  94 */             int l; for (l = 0; !flag && l <= 10 && l >= -10; l = (l <= 0) ? (1 - l) : (0 - l)) {
/*  95 */               int j1; for (j1 = 0; !flag && j1 <= 10 && j1 >= -10; j1 = (j1 <= 0) ? (1 - j1) : (0 - j1)) {
/*  96 */                 if (this.world.getType(i + l, j + i1, k + j1) == Blocks.MONSTER_EGGS)
/*     */                 {
/*  98 */                   if (!CraftEventFactory.callEntityChangeBlockEvent(this, i + l, j + i1, k + j1, Blocks.AIR, 0).isCancelled()) {
/*     */ 
/*     */ 
/*     */                     
/* 102 */                     if (!this.world.getGameRules().getBoolean("mobGriefing")) {
/* 103 */                       int k1 = this.world.getData(i + l, j + i1, k + j1);
/* 104 */                       ImmutablePair immutablepair = BlockMonsterEggs.b(k1);
/*     */                       
/* 106 */                       this.world.setTypeAndData(i + l, j + i1, k + j1, (Block)immutablepair.getLeft(), ((Integer)immutablepair.getRight()).intValue(), 3);
/*     */                     } else {
/* 108 */                       this.world.setAir(i + l, j + i1, k + j1, false);
/*     */                     } 
/*     */                     
/* 111 */                     Blocks.MONSTER_EGGS.postBreak(this.world, i + l, j + i1, k + j1, 0);
/* 112 */                     if (this.random.nextBoolean()) {
/* 113 */                       flag = true;
/*     */                       break;
/*     */                     } 
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 123 */       if (this.target == null && !bS()) {
/* 124 */         int i = MathHelper.floor(this.locX);
/* 125 */         int j = MathHelper.floor(this.locY + 0.5D);
/* 126 */         int k = MathHelper.floor(this.locZ);
/* 127 */         int l1 = this.random.nextInt(6);
/* 128 */         Block block = this.world.getType(i + Facing.b[l1], j + Facing.c[l1], k + Facing.d[l1]);
/*     */         
/* 130 */         int l = this.world.getData(i + Facing.b[l1], j + Facing.c[l1], k + Facing.d[l1]);
/* 131 */         if (BlockMonsterEggs.a(block)) {
/*     */           
/* 133 */           if (CraftEventFactory.callEntityChangeBlockEvent(this, i + Facing.b[l1], j + Facing.c[l1], k + Facing.d[l1], Blocks.MONSTER_EGGS, Block.getId(BlockMonsterEggs.getById(l))).isCancelled()) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 138 */           this.world.setTypeAndData(i + Facing.b[l1], j + Facing.c[l1], k + Facing.d[l1], Blocks.MONSTER_EGGS, BlockMonsterEggs.a(block, l), 3);
/* 139 */           s();
/* 140 */           die();
/*     */         } else {
/* 142 */           bQ();
/*     */         } 
/* 144 */       } else if (this.target != null && !bS()) {
/* 145 */         this.target = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float a(int i, int j, int k) {
/* 151 */     return (this.world.getType(i, j - 1, k) == Blocks.STONE) ? 10.0F : super.a(i, j, k);
/*     */   }
/*     */   
/*     */   protected boolean j_() {
/* 155 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canSpawn() {
/* 159 */     if (super.canSpawn()) {
/* 160 */       EntityHuman entityhuman = this.world.findNearbyPlayer(this, 5.0D);
/*     */       
/* 162 */       return (entityhuman == null);
/*     */     } 
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumMonsterType getMonsterType() {
/* 169 */     return EnumMonsterType.ARTHROPOD;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySilverfish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */