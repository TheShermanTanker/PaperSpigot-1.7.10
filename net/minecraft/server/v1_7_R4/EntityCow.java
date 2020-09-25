/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ 
/*     */ public class EntityCow
/*     */   extends EntityAnimal {
/*     */   public EntityCow(World world) {
/*  11 */     super(world);
/*  12 */     a(0.9F, 1.3F);
/*  13 */     getNavigation().a(true);
/*  14 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  15 */     this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));
/*  16 */     this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
/*  17 */     this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.25D, Items.WHEAT, false));
/*  18 */     this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.25D));
/*  19 */     this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
/*  20 */     this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*  21 */     this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
/*     */   }
/*     */   
/*     */   public boolean bk() {
/*  25 */     return true;
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  29 */     super.aD();
/*  30 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/*  31 */     getAttributeInstance(GenericAttributes.d).setValue(0.20000000298023224D);
/*     */   }
/*     */   
/*     */   protected String t() {
/*  35 */     return "mob.cow.say";
/*     */   }
/*     */   
/*     */   protected String aT() {
/*  39 */     return "mob.cow.hurt";
/*     */   }
/*     */   
/*     */   protected String aU() {
/*  43 */     return "mob.cow.hurt";
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, int k, Block block) {
/*  47 */     makeSound("mob.cow.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected float bf() {
/*  51 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  55 */     return Items.LEATHER;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  59 */     int j = this.random.nextInt(3) + this.random.nextInt(1 + i);
/*     */     
/*     */     int k;
/*     */     
/*  63 */     for (k = 0; k < j; k++) {
/*  64 */       a(Items.LEATHER, 1);
/*     */     }
/*     */     
/*  67 */     j = this.random.nextInt(3) + 1 + this.random.nextInt(1 + i);
/*     */     
/*  69 */     for (k = 0; k < j; k++) {
/*  70 */       if (isBurning()) {
/*  71 */         a(Items.COOKED_BEEF, 1);
/*     */       } else {
/*  73 */         a(Items.RAW_BEEF, 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  79 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/*  81 */     if (itemstack != null && itemstack.getItem() == Items.BUCKET && !entityhuman.abilities.canInstantlyBuild) {
/*     */       
/*  83 */       Location loc = getBukkitEntity().getLocation();
/*  84 */       PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Items.MILK_BUCKET);
/*     */       
/*  86 */       if (event.isCancelled()) {
/*  87 */         return false;
/*     */       }
/*     */       
/*  90 */       if (--itemstack.count <= 0) {
/*  91 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, CraftItemStack.asNMSCopy(event.getItemStack()));
/*  92 */       } else if (!entityhuman.inventory.pickup(new ItemStack(Items.MILK_BUCKET))) {
/*  93 */         entityhuman.drop(CraftItemStack.asNMSCopy(event.getItemStack()), false);
/*     */       } 
/*     */ 
/*     */       
/*  97 */       return true;
/*     */     } 
/*  99 */     return super.a(entityhuman);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityCow b(EntityAgeable entityageable) {
/* 104 */     return new EntityCow(this.world);
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 108 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityCow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */