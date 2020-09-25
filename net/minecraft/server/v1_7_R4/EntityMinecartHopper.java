/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public class EntityMinecartHopper
/*     */   extends EntityMinecartContainer
/*     */   implements IHopper
/*     */ {
/*     */   private boolean a = true;
/*  20 */   private int b = -1;
/*     */   
/*     */   public EntityMinecartHopper(World paramWorld) {
/*  23 */     super(paramWorld);
/*     */   }
/*     */   
/*     */   public EntityMinecartHopper(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  27 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int m() {
/*  32 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public Block o() {
/*  37 */     return Blocks.HOPPER;
/*     */   }
/*     */ 
/*     */   
/*     */   public int s() {
/*  42 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  47 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c(EntityHuman paramEntityHuman) {
/*  52 */     if (!this.world.isStatic) {
/*  53 */       paramEntityHuman.openMinecartHopper(this);
/*     */     }
/*     */     
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/*  61 */     boolean bool = !paramBoolean;
/*     */     
/*  63 */     if (bool != v()) {
/*  64 */       f(bool);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean v() {
/*  69 */     return this.a;
/*     */   }
/*     */   
/*     */   public void f(boolean paramBoolean) {
/*  73 */     this.a = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  78 */     return this.world;
/*     */   }
/*     */ 
/*     */   
/*     */   public double x() {
/*  83 */     return this.locX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double aD() {
/*  88 */     return this.locY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double aE() {
/*  93 */     return this.locZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {
/*  98 */     super.h();
/*     */     
/* 100 */     if (!this.world.isStatic && isAlive() && v()) {
/* 101 */       this.b--;
/* 102 */       if (!aG()) {
/* 103 */         n(0);
/*     */         
/* 105 */         if (aF()) {
/* 106 */           n(4);
/* 107 */           update();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean aF() {
/* 114 */     if (TileEntityHopper.suckInItems(this)) return true;
/*     */     
/* 116 */     List<EntityItem> list = this.world.a(EntityItem.class, this.boundingBox.grow(0.25D, 0.0D, 0.25D), IEntitySelector.a);
/*     */     
/* 118 */     if (list.size() > 0) {
/* 119 */       TileEntityHopper.addEntityItem(this, list.get(0));
/*     */     }
/*     */     
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(DamageSource paramDamageSource) {
/* 127 */     super.a(paramDamageSource);
/*     */     
/* 129 */     a(Item.getItemOf(Blocks.HOPPER), 1, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 134 */     super.b(paramNBTTagCompound);
/* 135 */     paramNBTTagCompound.setInt("TransferCooldown", this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 140 */     super.a(paramNBTTagCompound);
/* 141 */     this.b = paramNBTTagCompound.getInt("TransferCooldown");
/*     */   }
/*     */   
/*     */   public void n(int paramInt) {
/* 145 */     this.b = paramInt;
/*     */   }
/*     */   
/*     */   public boolean aG() {
/* 149 */     return (this.b > 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMinecartHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */