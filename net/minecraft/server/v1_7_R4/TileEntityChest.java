/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ 
/*     */ public class TileEntityChest
/*     */   extends TileEntity
/*     */   implements IInventory {
/*  13 */   private ItemStack[] items = new ItemStack[27];
/*     */   public boolean a;
/*     */   public TileEntityChest i;
/*     */   public TileEntityChest j;
/*     */   public TileEntityChest k;
/*     */   public TileEntityChest l;
/*     */   public float m;
/*     */   public float n;
/*     */   public int o;
/*     */   private int ticks;
/*  23 */   private int r = -1;
/*     */   private String s;
/*     */   public List<HumanEntity> transaction;
/*     */   private int maxStack;
/*     */   
/*     */   public TileEntityChest() {
/*  29 */     this.transaction = new ArrayList<HumanEntity>();
/*  30 */     this.maxStack = 64;
/*     */   }
/*     */   public ItemStack[] getContents() {
/*  33 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  37 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  41 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  45 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  49 */     this.maxStack = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  54 */     return 27;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  58 */     return this.items[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  62 */     if (this.items[i] != null) {
/*     */ 
/*     */       
/*  65 */       if ((this.items[i]).count <= j) {
/*  66 */         ItemStack itemStack = this.items[i];
/*  67 */         this.items[i] = null;
/*  68 */         update();
/*  69 */         return itemStack;
/*     */       } 
/*  71 */       ItemStack itemstack = this.items[i].a(j);
/*  72 */       if ((this.items[i]).count == 0) {
/*  73 */         this.items[i] = null;
/*     */       }
/*     */       
/*  76 */       update();
/*  77 */       return itemstack;
/*     */     } 
/*     */     
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/*  85 */     if (this.items[i] != null) {
/*  86 */       ItemStack itemstack = this.items[i];
/*     */       
/*  88 */       this.items[i] = null;
/*  89 */       return itemstack;
/*     */     } 
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/*  96 */     this.items[i] = itemstack;
/*  97 */     if (itemstack != null && itemstack.count > getMaxStackSize()) {
/*  98 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */     
/* 101 */     update();
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 105 */     return k_() ? this.s : "container.chest";
/*     */   }
/*     */   
/*     */   public boolean k_() {
/* 109 */     return (this.s != null && this.s.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 113 */     this.s = s;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 117 */     super.a(nbttagcompound);
/* 118 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/* 120 */     this.items = new ItemStack[getSize()];
/* 121 */     if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
/* 122 */       this.s = nbttagcompound.getString("CustomName");
/*     */     }
/*     */     
/* 125 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 126 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 127 */       int j = nbttagcompound1.getByte("Slot") & 0xFF;
/*     */       
/* 129 */       if (j >= 0 && j < this.items.length) {
/* 130 */         this.items[j] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 136 */     super.b(nbttagcompound);
/* 137 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 139 */     for (int i = 0; i < this.items.length; i++) {
/* 140 */       if (this.items[i] != null) {
/* 141 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 143 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 144 */         this.items[i].save(nbttagcompound1);
/* 145 */         nbttaglist.add(nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     nbttagcompound.set("Items", nbttaglist);
/* 150 */     if (k_()) {
/* 151 */       nbttagcompound.setString("CustomName", this.s);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 156 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 160 */     if (this.world == null) return true; 
/* 161 */     return (this.world.getTileEntity(this.x, this.y, this.z) != this) ? false : ((entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D));
/*     */   }
/*     */   
/*     */   public void u() {
/* 165 */     super.u();
/* 166 */     this.a = false;
/*     */   }
/*     */   
/*     */   private void a(TileEntityChest tileentitychest, int i) {
/* 170 */     if (tileentitychest.r()) {
/* 171 */       this.a = false;
/* 172 */     } else if (this.a) {
/* 173 */       switch (i) {
/*     */         case 0:
/* 175 */           if (this.l != tileentitychest) {
/* 176 */             this.a = false;
/*     */           }
/*     */           break;
/*     */         
/*     */         case 1:
/* 181 */           if (this.k != tileentitychest) {
/* 182 */             this.a = false;
/*     */           }
/*     */           break;
/*     */         
/*     */         case 2:
/* 187 */           if (this.i != tileentitychest) {
/* 188 */             this.a = false;
/*     */           }
/*     */           break;
/*     */         
/*     */         case 3:
/* 193 */           if (this.j != tileentitychest)
/* 194 */             this.a = false; 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void i() {
/* 201 */     if (!this.a) {
/* 202 */       this.a = true;
/* 203 */       this.i = null;
/* 204 */       this.j = null;
/* 205 */       this.k = null;
/* 206 */       this.l = null;
/* 207 */       if (a(this.x - 1, this.y, this.z)) {
/* 208 */         this.k = (TileEntityChest)this.world.getTileEntity(this.x - 1, this.y, this.z);
/*     */       }
/*     */       
/* 211 */       if (a(this.x + 1, this.y, this.z)) {
/* 212 */         this.j = (TileEntityChest)this.world.getTileEntity(this.x + 1, this.y, this.z);
/*     */       }
/*     */       
/* 215 */       if (a(this.x, this.y, this.z - 1)) {
/* 216 */         this.i = (TileEntityChest)this.world.getTileEntity(this.x, this.y, this.z - 1);
/*     */       }
/*     */       
/* 219 */       if (a(this.x, this.y, this.z + 1)) {
/* 220 */         this.l = (TileEntityChest)this.world.getTileEntity(this.x, this.y, this.z + 1);
/*     */       }
/*     */       
/* 223 */       if (this.i != null) {
/* 224 */         this.i.a(this, 0);
/*     */       }
/*     */       
/* 227 */       if (this.l != null) {
/* 228 */         this.l.a(this, 2);
/*     */       }
/*     */       
/* 231 */       if (this.j != null) {
/* 232 */         this.j.a(this, 1);
/*     */       }
/*     */       
/* 235 */       if (this.k != null) {
/* 236 */         this.k.a(this, 3);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean a(int i, int j, int k) {
/* 242 */     if (this.world == null) {
/* 243 */       return false;
/*     */     }
/* 245 */     Block block = this.world.getType(i, j, k);
/*     */     
/* 247 */     return (block instanceof BlockChest && ((BlockChest)block).a == j());
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {
/* 252 */     super.h();
/* 253 */     if (this.world == null)
/* 254 */       return;  i();
/* 255 */     this.ticks++;
/*     */ 
/*     */     
/* 258 */     if (!this.world.isStatic && this.o != 0 && (this.ticks + this.x + this.y + this.z) % 10 == 0) {
/* 259 */       this.o = 0;
/* 260 */       float f = 5.0F;
/* 261 */       List list = this.world.a(EntityHuman.class, AxisAlignedBB.a((this.x - f), (this.y - f), (this.z - f), ((this.x + 1) + f), ((this.y + 1) + f), ((this.z + 1) + f)));
/* 262 */       Iterator<EntityHuman> iterator = list.iterator();
/*     */       
/* 264 */       while (iterator.hasNext()) {
/* 265 */         EntityHuman entityhuman = iterator.next();
/*     */         
/* 267 */         if (entityhuman.activeContainer instanceof ContainerChest) {
/* 268 */           IInventory iinventory = ((ContainerChest)entityhuman.activeContainer).e();
/*     */           
/* 270 */           if (iinventory == this || (iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).a(this))) {
/* 271 */             this.o++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 277 */     this.n = this.m;
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
/* 338 */     if (i == 1) {
/* 339 */       this.o = j;
/* 340 */       return true;
/*     */     } 
/* 342 */     return super.c(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startOpen() {
/* 347 */     if (this.o < 0) {
/* 348 */       this.o = 0;
/*     */     }
/*     */     
/* 351 */     int oldPower = Math.max(0, Math.min(15, this.o));
/*     */     
/* 353 */     this.o++;
/* 354 */     if (this.world == null)
/* 355 */       return;  this.world.playBlockAction(this.x, this.y, this.z, q(), 1, this.o);
/*     */ 
/*     */     
/* 358 */     i();
/*     */ 
/*     */     
/* 361 */     if (this.o > 0 && this.m == 0.0F && this.i == null && this.k == null) {
/* 362 */       double d1 = this.x + 0.5D;
/*     */       
/* 364 */       double d0 = this.z + 0.5D;
/* 365 */       if (this.l != null) {
/* 366 */         d0 += 0.5D;
/*     */       }
/*     */       
/* 369 */       if (this.j != null) {
/* 370 */         d1 += 0.5D;
/*     */       }
/*     */       
/* 373 */       this.world.makeSound(d1, this.y + 0.5D, d0, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 378 */     if (q() == Blocks.TRAPPED_CHEST) {
/* 379 */       int newPower = Math.max(0, Math.min(15, this.o));
/*     */       
/* 381 */       if (oldPower != newPower) {
/* 382 */         CraftEventFactory.callRedstoneChange(this.world, this.x, this.y, this.z, oldPower, newPower);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 387 */     this.world.applyPhysics(this.x, this.y, this.z, q());
/* 388 */     this.world.applyPhysics(this.x, this.y - 1, this.z, q());
/*     */   }
/*     */   
/*     */   public void closeContainer() {
/* 392 */     if (q() instanceof BlockChest) {
/* 393 */       int oldPower = Math.max(0, Math.min(15, this.o));
/*     */       
/* 395 */       this.o--;
/* 396 */       if (this.world == null)
/* 397 */         return;  this.world.playBlockAction(this.x, this.y, this.z, q(), 1, this.o);
/*     */ 
/*     */       
/* 400 */       i();
/*     */ 
/*     */       
/* 403 */       if (this.o == 0 && this.i == null && this.k == null) {
/* 404 */         double d0 = this.x + 0.5D;
/* 405 */         double d2 = this.z + 0.5D;
/*     */         
/* 407 */         if (this.l != null) {
/* 408 */           d2 += 0.5D;
/*     */         }
/*     */         
/* 411 */         if (this.j != null) {
/* 412 */           d0 += 0.5D;
/*     */         }
/*     */         
/* 415 */         this.world.makeSound(d0, this.y + 0.5D, d2, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 420 */       if (q() == Blocks.TRAPPED_CHEST) {
/* 421 */         int newPower = Math.max(0, Math.min(15, this.o));
/*     */         
/* 423 */         if (oldPower != newPower) {
/* 424 */           CraftEventFactory.callRedstoneChange(this.world, this.x, this.y, this.z, oldPower, newPower);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 429 */       this.world.applyPhysics(this.x, this.y, this.z, q());
/* 430 */       this.world.applyPhysics(this.x, this.y - 1, this.z, q());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 435 */     return true;
/*     */   }
/*     */   
/*     */   public void s() {
/* 439 */     super.s();
/* 440 */     u();
/* 441 */     i();
/*     */   }
/*     */   
/*     */   public int j() {
/* 445 */     if (this.r == -1) {
/* 446 */       if (this.world == null || !(q() instanceof BlockChest)) {
/* 447 */         return 0;
/*     */       }
/*     */       
/* 450 */       this.r = ((BlockChest)q()).a;
/*     */     } 
/*     */     
/* 453 */     return this.r;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */