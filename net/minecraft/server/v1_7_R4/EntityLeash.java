/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public class EntityLeash
/*     */   extends EntityHanging
/*     */ {
/*     */   public EntityLeash(World world) {
/*  11 */     super(world);
/*     */   }
/*     */   
/*     */   public EntityLeash(World world, int i, int j, int k) {
/*  15 */     super(world, i, j, k, 0);
/*  16 */     setPosition(i + 0.5D, j + 0.5D, k + 0.5D);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  20 */     super.c();
/*     */   }
/*     */   
/*     */   public void setDirection(int i) {}
/*     */   
/*     */   public int f() {
/*  26 */     return 9;
/*     */   }
/*     */   
/*     */   public int i() {
/*  30 */     return 9;
/*     */   }
/*     */   
/*     */   public void b(Entity entity) {}
/*     */   
/*     */   public boolean d(NBTTagCompound nbttagcompound) {
/*  36 */     return false;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   public boolean c(EntityHuman entityhuman) {
/*  44 */     ItemStack itemstack = entityhuman.be();
/*  45 */     boolean flag = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  51 */     if (itemstack != null && itemstack.getItem() == Items.LEASH && !this.world.isStatic) {
/*  52 */       double d0 = 7.0D;
/*  53 */       List list = this.world.a(EntityInsentient.class, AxisAlignedBB.a(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + d0, this.locZ + d0));
/*  54 */       if (list != null) {
/*  55 */         Iterator<EntityInsentient> iterator = list.iterator();
/*     */         
/*  57 */         while (iterator.hasNext()) {
/*  58 */           EntityInsentient entityinsentient = iterator.next();
/*  59 */           if (entityinsentient.bN() && entityinsentient.getLeashHolder() == entityhuman) {
/*     */             
/*  61 */             if (CraftEventFactory.callPlayerLeashEntityEvent(entityinsentient, this, entityhuman).isCancelled()) {
/*  62 */               ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, entityinsentient, entityinsentient.getLeashHolder()));
/*     */               
/*     */               continue;
/*     */             } 
/*  66 */             entityinsentient.setLeashHolder(this, true);
/*  67 */             flag = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     if (!this.world.isStatic && !flag) {
/*     */ 
/*     */       
/*  76 */       boolean die = true;
/*     */ 
/*     */       
/*  79 */       double d0 = 7.0D;
/*  80 */       List list = this.world.a(EntityInsentient.class, AxisAlignedBB.a(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + d0, this.locZ + d0));
/*  81 */       if (list != null) {
/*  82 */         Iterator<EntityInsentient> iterator = list.iterator();
/*     */         
/*  84 */         while (iterator.hasNext()) {
/*  85 */           EntityInsentient entityinsentient = iterator.next();
/*  86 */           if (entityinsentient.bN() && entityinsentient.getLeashHolder() == this) {
/*     */             
/*  88 */             if (CraftEventFactory.callPlayerUnleashEntityEvent(entityinsentient, entityhuman).isCancelled()) {
/*  89 */               die = false;
/*     */               continue;
/*     */             } 
/*  92 */             entityinsentient.unleash(true, !entityhuman.abilities.canInstantlyBuild);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  99 */       if (die) {
/* 100 */         die();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   public boolean survives() {
/* 109 */     return (this.world.getType(this.x, this.y, this.z).b() == 11);
/*     */   }
/*     */   
/*     */   public static EntityLeash a(World world, int i, int j, int k) {
/* 113 */     EntityLeash entityleash = new EntityLeash(world, i, j, k);
/*     */     
/* 115 */     entityleash.attachedToPlayer = true;
/* 116 */     world.addEntity(entityleash);
/* 117 */     return entityleash;
/*     */   }
/*     */   
/*     */   public static EntityLeash b(World world, int i, int j, int k) {
/* 121 */     List list = world.a(EntityLeash.class, AxisAlignedBB.a(i - 1.0D, j - 1.0D, k - 1.0D, i + 1.0D, j + 1.0D, k + 1.0D));
/*     */     
/* 123 */     if (list != null) {
/* 124 */       Iterator<EntityLeash> iterator = list.iterator();
/*     */       
/* 126 */       while (iterator.hasNext()) {
/* 127 */         EntityLeash entityleash = iterator.next();
/*     */         
/* 129 */         if (entityleash.x == i && entityleash.y == j && entityleash.z == k) {
/* 130 */           return entityleash;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityLeash.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */