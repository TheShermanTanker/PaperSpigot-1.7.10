/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.inventory.BrewEvent;
/*     */ import org.bukkit.inventory.BrewerInventory;
/*     */ 
/*     */ public class TileEntityBrewingStand extends TileEntity implements IWorldInventory {
/*  13 */   private static final int[] a = new int[] { 3 };
/*  14 */   private static final int[] i = new int[] { 0, 1, 2 };
/*  15 */   public ItemStack[] items = new ItemStack[4];
/*     */   public int brewTime;
/*     */   private int l;
/*     */   private Item m;
/*     */   private String n;
/*  20 */   private int lastTick = MinecraftServer.currentTick;
/*     */   public List<HumanEntity> transaction;
/*     */   private int maxStack;
/*     */   
/*     */   public TileEntityBrewingStand() {
/*  25 */     this.transaction = new ArrayList<HumanEntity>();
/*  26 */     this.maxStack = 64;
/*     */   }
/*     */   public void onOpen(CraftHumanEntity who) {
/*  29 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  33 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  37 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public ItemStack[] getContents() {
/*  41 */     return this.items;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  45 */     this.maxStack = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/*  50 */     return k_() ? this.n : "container.brewing";
/*     */   }
/*     */   
/*     */   public boolean k_() {
/*  54 */     return (this.n != null && this.n.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String s) {
/*  58 */     this.n = s;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  62 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {
/*  67 */     int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
/*  68 */     this.lastTick = MinecraftServer.currentTick;
/*     */     
/*  70 */     if (this.brewTime > 0) {
/*  71 */       this.brewTime -= elapsedTicks;
/*  72 */       if (this.brewTime <= 0) {
/*     */         
/*  74 */         l();
/*  75 */         update();
/*  76 */       } else if (!k()) {
/*  77 */         this.brewTime = 0;
/*  78 */         update();
/*  79 */       } else if (this.m != this.items[3].getItem()) {
/*  80 */         this.brewTime = 0;
/*  81 */         update();
/*     */       } 
/*  83 */     } else if (k()) {
/*  84 */       this.brewTime = 400;
/*  85 */       this.m = this.items[3].getItem();
/*     */     } 
/*     */     
/*  88 */     int i = j();
/*     */     
/*  90 */     if (i != this.l) {
/*  91 */       this.l = i;
/*  92 */       this.world.setData(this.x, this.y, this.z, i, 2);
/*     */     } 
/*     */     
/*  95 */     super.h();
/*     */   }
/*     */   
/*     */   public int i() {
/*  99 */     return this.brewTime;
/*     */   }
/*     */   
/*     */   private boolean k() {
/* 103 */     if (this.items[3] != null && (this.items[3]).count > 0) {
/* 104 */       ItemStack itemstack = this.items[3];
/*     */       
/* 106 */       if (!itemstack.getItem().m(itemstack)) {
/* 107 */         return false;
/*     */       }
/* 109 */       boolean flag = false;
/*     */       
/* 111 */       for (int i = 0; i < 3; i++) {
/* 112 */         if (this.items[i] != null && this.items[i].getItem() == Items.POTION) {
/* 113 */           int j = this.items[i].getData();
/* 114 */           int k = c(j, itemstack);
/*     */           
/* 116 */           if (!ItemPotion.g(j) && ItemPotion.g(k)) {
/* 117 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/* 121 */           List list = Items.POTION.c(j);
/* 122 */           List list1 = Items.POTION.c(k);
/*     */           
/* 124 */           if ((j <= 0 || list != list1) && (list == null || (!list.equals(list1) && list1 != null)) && j != k) {
/* 125 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 131 */       return flag;
/*     */     } 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void l() {
/* 139 */     if (k()) {
/* 140 */       ItemStack itemstack = this.items[3];
/*     */ 
/*     */       
/* 143 */       if (getOwner() != null) {
/* 144 */         BrewEvent event = new BrewEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), (BrewerInventory)getOwner().getInventory());
/* 145 */         Bukkit.getPluginManager().callEvent((Event)event);
/* 146 */         if (event.isCancelled()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 152 */       for (int i = 0; i < 3; i++) {
/* 153 */         if (this.items[i] != null && this.items[i].getItem() == Items.POTION) {
/* 154 */           int j = this.items[i].getData();
/* 155 */           int k = c(j, itemstack);
/* 156 */           List list = Items.POTION.c(j);
/* 157 */           List list1 = Items.POTION.c(k);
/*     */           
/* 159 */           if ((j <= 0 || list != list1) && (list == null || (!list.equals(list1) && list1 != null))) {
/* 160 */             if (j != k) {
/* 161 */               this.items[i].setData(k);
/*     */             }
/* 163 */           } else if (!ItemPotion.g(j) && ItemPotion.g(k)) {
/* 164 */             this.items[i].setData(k);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 169 */       if (itemstack.getItem().u()) {
/* 170 */         this.items[3] = new ItemStack(itemstack.getItem().t());
/*     */       } else {
/* 172 */         (this.items[3]).count--;
/* 173 */         if ((this.items[3]).count <= 0) {
/* 174 */           this.items[3] = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int c(int i, ItemStack itemstack) {
/* 181 */     return (itemstack == null) ? i : (itemstack.getItem().m(itemstack) ? PotionBrewer.a(i, itemstack.getItem().i(itemstack)) : i);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 185 */     super.a(nbttagcompound);
/* 186 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/* 188 */     this.items = new ItemStack[getSize()];
/*     */     
/* 190 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 191 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 192 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/* 194 */       if (b0 >= 0 && b0 < this.items.length) {
/* 195 */         this.items[b0] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */     
/* 199 */     this.brewTime = nbttagcompound.getShort("BrewTime");
/* 200 */     if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
/* 201 */       this.n = nbttagcompound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 206 */     super.b(nbttagcompound);
/* 207 */     nbttagcompound.setShort("BrewTime", (short)this.brewTime);
/* 208 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 210 */     for (int i = 0; i < this.items.length; i++) {
/* 211 */       if (this.items[i] != null) {
/* 212 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 214 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 215 */         this.items[i].save(nbttagcompound1);
/* 216 */         nbttaglist.add(nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     nbttagcompound.set("Items", nbttaglist);
/* 221 */     if (k_()) {
/* 222 */       nbttagcompound.setString("CustomName", this.n);
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/* 227 */     return (i >= 0 && i < this.items.length) ? this.items[i] : null;
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/* 231 */     if (i >= 0 && i < this.items.length) {
/* 232 */       ItemStack itemstack = this.items[i];
/*     */       
/* 234 */       this.items[i] = null;
/* 235 */       return itemstack;
/*     */     } 
/* 237 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/* 242 */     if (i >= 0 && i < this.items.length) {
/* 243 */       ItemStack itemstack = this.items[i];
/*     */       
/* 245 */       this.items[i] = null;
/* 246 */       return itemstack;
/*     */     } 
/* 248 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 253 */     if (i >= 0 && i < this.items.length) {
/* 254 */       this.items[i] = itemstack;
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 259 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 263 */     return (this.world.getTileEntity(this.x, this.y, this.z) != this) ? false : ((entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D));
/*     */   }
/*     */   
/*     */   public void startOpen() {}
/*     */   
/*     */   public void closeContainer() {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 271 */     return (i == 3) ? itemstack.getItem().m(itemstack) : ((itemstack.getItem() == Items.POTION || itemstack.getItem() == Items.GLASS_BOTTLE));
/*     */   }
/*     */   
/*     */   public int j() {
/* 275 */     int i = 0;
/*     */     
/* 277 */     for (int j = 0; j < 3; j++) {
/* 278 */       if (this.items[j] != null) {
/* 279 */         i |= 1 << j;
/*     */       }
/*     */     } 
/*     */     
/* 283 */     return i;
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(int i) {
/* 287 */     return (i == 1) ? a : TileEntityBrewingStand.i;
/*     */   }
/*     */   
/*     */   public boolean canPlaceItemThroughFace(int i, ItemStack itemstack, int j) {
/* 291 */     return b(i, itemstack);
/*     */   }
/*     */   
/*     */   public boolean canTakeItemThroughFace(int i, ItemStack itemstack, int j) {
/* 295 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityBrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */