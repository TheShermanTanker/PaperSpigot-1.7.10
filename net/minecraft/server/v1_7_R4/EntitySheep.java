/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Sheep;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.SheepRegrowWoolEvent;
/*     */ import org.bukkit.event.player.PlayerShearEntityEvent;
/*     */ 
/*     */ public class EntitySheep extends EntityAnimal {
/*  12 */   private final InventoryCrafting bq = new InventoryCrafting(new ContainerSheepBreed(this), 2, 1);
/*  13 */   public static final float[][] bp = new float[][] { { 1.0F, 1.0F, 1.0F }, { 0.85F, 0.5F, 0.2F }, { 0.7F, 0.3F, 0.85F }, { 0.4F, 0.6F, 0.85F }, { 0.9F, 0.9F, 0.2F }, { 0.5F, 0.8F, 0.1F }, { 0.95F, 0.5F, 0.65F }, { 0.3F, 0.3F, 0.3F }, { 0.6F, 0.6F, 0.6F }, { 0.3F, 0.5F, 0.6F }, { 0.5F, 0.25F, 0.7F }, { 0.2F, 0.3F, 0.7F }, { 0.4F, 0.3F, 0.2F }, { 0.4F, 0.5F, 0.2F }, { 0.6F, 0.2F, 0.2F }, { 0.1F, 0.1F, 0.1F } };
/*     */   private int br;
/*  15 */   private PathfinderGoalEatTile bs = new PathfinderGoalEatTile(this);
/*     */   
/*     */   public EntitySheep(World world) {
/*  18 */     super(world);
/*  19 */     a(0.9F, 1.3F);
/*  20 */     getNavigation().a(true);
/*  21 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  22 */     this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
/*  23 */     this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
/*  24 */     this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.1D, Items.WHEAT, false));
/*  25 */     this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));
/*  26 */     this.goalSelector.a(5, this.bs);
/*  27 */     this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
/*  28 */     this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*  29 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*  30 */     this.bq.setItem(0, new ItemStack(Items.INK_SACK, 1, 0));
/*  31 */     this.bq.setItem(1, new ItemStack(Items.INK_SACK, 1, 0));
/*  32 */     this.bq.resultInventory = new InventoryCraftResult();
/*     */   }
/*     */   
/*     */   protected boolean bk() {
/*  36 */     return true;
/*     */   }
/*     */   
/*     */   protected void bn() {
/*  40 */     this.br = this.bs.f();
/*  41 */     super.bn();
/*     */   }
/*     */   
/*     */   public void e() {
/*  45 */     if (this.world.isStatic) {
/*  46 */       this.br = Math.max(0, this.br - 1);
/*     */     }
/*     */     
/*  49 */     super.e();
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  53 */     super.aD();
/*  54 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
/*  55 */     getAttributeInstance(GenericAttributes.d).setValue(0.23000000417232513D);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  59 */     super.c();
/*  60 */     this.datawatcher.a(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  64 */     if (!isSheared()) {
/*  65 */       a(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, getColor()), 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  70 */     return Item.getItemOf(Blocks.WOOL);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  74 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/*  76 */     if (itemstack != null && itemstack.getItem() == Items.SHEARS && !isSheared() && !isBaby()) {
/*  77 */       if (!this.world.isStatic) {
/*     */         
/*  79 */         PlayerShearEntityEvent event = new PlayerShearEntityEvent((Player)entityhuman.getBukkitEntity(), (Entity)getBukkitEntity());
/*  80 */         this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/*  82 */         if (event.isCancelled()) {
/*  83 */           return false;
/*     */         }
/*     */ 
/*     */         
/*  87 */         setSheared(true);
/*  88 */         int i = 1 + this.random.nextInt(3);
/*     */         
/*  90 */         for (int j = 0; j < i; j++) {
/*  91 */           EntityItem entityitem = a(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, getColor()), 1.0F);
/*     */           
/*  93 */           entityitem.motY += (this.random.nextFloat() * 0.05F);
/*  94 */           entityitem.motX += ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
/*  95 */           entityitem.motZ += ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
/*     */         } 
/*     */       } 
/*     */       
/*  99 */       itemstack.damage(1, entityhuman);
/* 100 */       makeSound("mob.sheep.shear", 1.0F, 1.0F);
/*     */     } 
/*     */     
/* 103 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 107 */     super.b(nbttagcompound);
/* 108 */     nbttagcompound.setBoolean("Sheared", isSheared());
/* 109 */     nbttagcompound.setByte("Color", (byte)getColor());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 113 */     super.a(nbttagcompound);
/* 114 */     setSheared(nbttagcompound.getBoolean("Sheared"));
/* 115 */     setColor(nbttagcompound.getByte("Color"));
/*     */   }
/*     */   
/*     */   protected String t() {
/* 119 */     return "mob.sheep.say";
/*     */   }
/*     */   
/*     */   protected String aT() {
/* 123 */     return "mob.sheep.say";
/*     */   }
/*     */   
/*     */   protected String aU() {
/* 127 */     return "mob.sheep.say";
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, int k, Block block) {
/* 131 */     makeSound("mob.sheep.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   public int getColor() {
/* 135 */     return this.datawatcher.getByte(16) & 0xF;
/*     */   }
/*     */   
/*     */   public void setColor(int i) {
/* 139 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 141 */     this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xF0 | i & 0xF)));
/*     */   }
/*     */   
/*     */   public boolean isSheared() {
/* 145 */     return ((this.datawatcher.getByte(16) & 0x10) != 0);
/*     */   }
/*     */   
/*     */   public void setSheared(boolean flag) {
/* 149 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 151 */     if (flag) {
/* 152 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x10)));
/*     */     } else {
/* 154 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int a(Random random) {
/* 159 */     int i = random.nextInt(100);
/*     */     
/* 161 */     return (i < 5) ? 15 : ((i < 10) ? 7 : ((i < 15) ? 8 : ((i < 18) ? 12 : ((random.nextInt(500) == 0) ? 6 : 0))));
/*     */   }
/*     */   
/*     */   public EntitySheep b(EntityAgeable entityageable) {
/* 165 */     EntitySheep entitysheep = (EntitySheep)entityageable;
/* 166 */     EntitySheep entitysheep1 = new EntitySheep(this.world);
/* 167 */     int i = a(this, entitysheep);
/*     */     
/* 169 */     entitysheep1.setColor(15 - i);
/* 170 */     return entitysheep1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void p() {
/* 175 */     SheepRegrowWoolEvent event = new SheepRegrowWoolEvent((Sheep)getBukkitEntity());
/* 176 */     this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */     
/* 178 */     if (!event.isCancelled()) {
/* 179 */       setSheared(false);
/*     */     }
/*     */ 
/*     */     
/* 183 */     if (isBaby()) {
/* 184 */       a(60);
/*     */     }
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
/* 189 */     groupdataentity = super.prepare(groupdataentity);
/* 190 */     setColor(a(this.world.random));
/* 191 */     return groupdataentity;
/*     */   }
/*     */   
/*     */   private int a(EntityAnimal entityanimal, EntityAnimal entityanimal1) {
/* 195 */     int k, i = b(entityanimal);
/* 196 */     int j = b(entityanimal1);
/*     */     
/* 198 */     this.bq.getItem(0).setData(i);
/* 199 */     this.bq.getItem(1).setData(j);
/* 200 */     ItemStack itemstack = CraftingManager.getInstance().craft(this.bq, ((EntitySheep)entityanimal).world);
/*     */ 
/*     */     
/* 203 */     if (itemstack != null && itemstack.getItem() == Items.INK_SACK) {
/* 204 */       k = itemstack.getData();
/*     */     } else {
/* 206 */       k = this.world.random.nextBoolean() ? i : j;
/*     */     } 
/*     */     
/* 209 */     return k;
/*     */   }
/*     */   
/*     */   private int b(EntityAnimal entityanimal) {
/* 213 */     return 15 - ((EntitySheep)entityanimal).getColor();
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 217 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySheep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */