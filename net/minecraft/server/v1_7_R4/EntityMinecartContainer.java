/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public abstract class EntityMinecartContainer
/*     */   extends EntityMinecartAbstract
/*     */   implements IInventory {
/*  13 */   private ItemStack[] items = new ItemStack[27];
/*     */   
/*     */   private boolean b = true;
/*     */   
/*  17 */   public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
/*  18 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  21 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  25 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  29 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  33 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  37 */     CraftEntity craftEntity = getBukkitEntity();
/*  38 */     if (craftEntity instanceof InventoryHolder) return (InventoryHolder)craftEntity; 
/*  39 */     return null;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  43 */     this.maxStack = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecartContainer(World world) {
/*  48 */     super(world);
/*     */   }
/*     */   
/*     */   public EntityMinecartContainer(World world, double d0, double d1, double d2) {
/*  52 */     super(world, d0, d1, d2);
/*     */   }
/*     */   
/*     */   public void a(DamageSource damagesource) {
/*  56 */     super.a(damagesource);
/*     */     
/*  58 */     for (int i = 0; i < getSize(); i++) {
/*  59 */       ItemStack itemstack = getItem(i);
/*     */       
/*  61 */       if (itemstack != null) {
/*  62 */         float f = this.random.nextFloat() * 0.8F + 0.1F;
/*  63 */         float f1 = this.random.nextFloat() * 0.8F + 0.1F;
/*  64 */         float f2 = this.random.nextFloat() * 0.8F + 0.1F;
/*     */         
/*  66 */         while (itemstack.count > 0) {
/*  67 */           int j = this.random.nextInt(21) + 10;
/*     */           
/*  69 */           if (j > itemstack.count) {
/*  70 */             j = itemstack.count;
/*     */           }
/*     */           
/*  73 */           itemstack.count -= j;
/*  74 */           EntityItem entityitem = new EntityItem(this.world, this.locX + f, this.locY + f1, this.locZ + f2, new ItemStack(itemstack.getItem(), j, itemstack.getData()));
/*  75 */           float f3 = 0.05F;
/*     */           
/*  77 */           entityitem.motX = ((float)this.random.nextGaussian() * f3);
/*  78 */           entityitem.motY = ((float)this.random.nextGaussian() * f3 + 0.2F);
/*  79 */           entityitem.motZ = ((float)this.random.nextGaussian() * f3);
/*  80 */           this.world.addEntity(entityitem);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  87 */     return this.items[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  91 */     if (this.items[i] != null) {
/*     */ 
/*     */       
/*  94 */       if ((this.items[i]).count <= j) {
/*  95 */         ItemStack itemStack = this.items[i];
/*  96 */         this.items[i] = null;
/*  97 */         return itemStack;
/*     */       } 
/*  99 */       ItemStack itemstack = this.items[i].a(j);
/* 100 */       if ((this.items[i]).count == 0) {
/* 101 */         this.items[i] = null;
/*     */       }
/*     */       
/* 104 */       return itemstack;
/*     */     } 
/*     */     
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/* 112 */     if (this.items[i] != null) {
/* 113 */       ItemStack itemstack = this.items[i];
/*     */       
/* 115 */       this.items[i] = null;
/* 116 */       return itemstack;
/*     */     } 
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 123 */     this.items[i] = itemstack;
/* 124 */     if (itemstack != null && itemstack.count > getMaxStackSize()) {
/* 125 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */   
/*     */   public void update() {}
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 132 */     return this.dead ? false : ((entityhuman.f(this) <= 64.0D));
/*     */   }
/*     */   
/*     */   public void startOpen() {}
/*     */   
/*     */   public void closeContainer() {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 140 */     return true;
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 144 */     return k_() ? u() : "container.minecart";
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 148 */     return this.maxStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(int i) {
/* 153 */     for (HumanEntity human : new ArrayList(this.transaction))
/*     */     {
/* 155 */       human.closeInventory();
/*     */     }
/*     */     
/* 158 */     this.b = false;
/* 159 */     super.b(i);
/*     */   }
/*     */   
/*     */   public void die() {
/* 163 */     if (this.b) {
/* 164 */       for (int i = 0; i < getSize(); i++) {
/* 165 */         ItemStack itemstack = getItem(i);
/*     */         
/* 167 */         if (itemstack != null) {
/* 168 */           float f = this.random.nextFloat() * 0.8F + 0.1F;
/* 169 */           float f1 = this.random.nextFloat() * 0.8F + 0.1F;
/* 170 */           float f2 = this.random.nextFloat() * 0.8F + 0.1F;
/*     */           
/* 172 */           while (itemstack.count > 0) {
/* 173 */             int j = this.random.nextInt(21) + 10;
/*     */             
/* 175 */             if (j > itemstack.count) {
/* 176 */               j = itemstack.count;
/*     */             }
/*     */             
/* 179 */             itemstack.count -= j;
/* 180 */             EntityItem entityitem = new EntityItem(this.world, this.locX + f, this.locY + f1, this.locZ + f2, new ItemStack(itemstack.getItem(), j, itemstack.getData()));
/*     */             
/* 182 */             if (itemstack.hasTag()) {
/* 183 */               entityitem.getItemStack().setTag((NBTTagCompound)itemstack.getTag().clone());
/*     */             }
/*     */             
/* 186 */             float f3 = 0.05F;
/*     */             
/* 188 */             entityitem.motX = ((float)this.random.nextGaussian() * f3);
/* 189 */             entityitem.motY = ((float)this.random.nextGaussian() * f3 + 0.2F);
/* 190 */             entityitem.motZ = ((float)this.random.nextGaussian() * f3);
/* 191 */             this.world.addEntity(entityitem);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 197 */     super.die();
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {
/* 201 */     super.b(nbttagcompound);
/* 202 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 204 */     for (int i = 0; i < this.items.length; i++) {
/* 205 */       if (this.items[i] != null) {
/* 206 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 208 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 209 */         this.items[i].save(nbttagcompound1);
/* 210 */         nbttaglist.add(nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 214 */     nbttagcompound.set("Items", nbttaglist);
/*     */   }
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {
/* 218 */     super.a(nbttagcompound);
/* 219 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/* 221 */     this.items = new ItemStack[getSize()];
/*     */     
/* 223 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 224 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 225 */       int j = nbttagcompound1.getByte("Slot") & 0xFF;
/*     */       
/* 227 */       if (j >= 0 && j < this.items.length) {
/* 228 */         this.items[j] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean c(EntityHuman entityhuman) {
/* 234 */     if (!this.world.isStatic) {
/* 235 */       entityhuman.openContainer(this);
/*     */     }
/*     */     
/* 238 */     return true;
/*     */   }
/*     */   
/*     */   protected void i() {
/* 242 */     int i = 15 - Container.b(this);
/* 243 */     float f = 0.98F + i * 0.001F;
/*     */     
/* 245 */     this.motX *= f;
/* 246 */     this.motY *= 0.0D;
/* 247 */     this.motZ *= f;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMinecartContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */