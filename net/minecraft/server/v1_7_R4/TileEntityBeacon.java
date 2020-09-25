/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.github.paperspigot.event.block.BeaconEffectEvent;
/*     */ 
/*     */ 
/*     */ public class TileEntityBeacon
/*     */   extends TileEntity
/*     */   implements IInventory
/*     */ {
/*  21 */   public static final MobEffectList[][] a = new MobEffectList[][] { { MobEffectList.FASTER_MOVEMENT, MobEffectList.FASTER_DIG }, { MobEffectList.RESISTANCE, MobEffectList.JUMP }, { MobEffectList.INCREASE_DAMAGE }, { MobEffectList.REGENERATION } };
/*     */   private boolean k;
/*  23 */   private int l = -1;
/*     */   
/*     */   private int m;
/*     */   private int n;
/*     */   private ItemStack inventorySlot;
/*     */   private String p;
/*  29 */   public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
/*  30 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  33 */     return new ItemStack[] { this.inventorySlot };
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void h() {
/*  57 */     y();
/*  58 */     x();
/*     */   }
/*     */ 
/*     */   
/*     */   private void x() {
/*  63 */     if (this.k && this.l > 0 && !this.world.isStatic && this.m > 0) {
/*  64 */       double d0 = (this.l * 10 + 10);
/*  65 */       byte b0 = 0;
/*     */       
/*  67 */       if (this.l >= 4 && this.m == this.n) {
/*  68 */         b0 = 1;
/*     */       }
/*     */       
/*  71 */       AxisAlignedBB axisalignedbb = AxisAlignedBB.a(this.x, this.y, this.z, (this.x + 1), (this.y + 1), (this.z + 1)).grow(d0, d0, d0);
/*     */       
/*  73 */       axisalignedbb.e = this.world.getHeight();
/*  74 */       List<EntityHuman> list = this.world.a(EntityHuman.class, axisalignedbb);
/*  75 */       Iterator<EntityHuman> iterator = list.iterator();
/*     */ 
/*     */ 
/*     */       
/*  79 */       Block block = this.world.getWorld().getBlockAt(this.x, this.y, this.z);
/*  80 */       PotionEffect primaryEffect = new PotionEffect(PotionEffectType.getById(this.m), 180, b0, true);
/*     */ 
/*     */       
/*  83 */       while (iterator.hasNext()) {
/*  84 */         EntityHuman entityhuman = iterator.next();
/*     */         
/*  86 */         BeaconEffectEvent event = new BeaconEffectEvent(block, primaryEffect, (Player)entityhuman.getBukkitEntity(), true);
/*  87 */         if (((BeaconEffectEvent)CraftEventFactory.callEvent((Event)event)).isCancelled())
/*     */           continue; 
/*  89 */         PotionEffect effect = event.getEffect();
/*  90 */         entityhuman.addEffect(new MobEffect(effect.getType().getId(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient()));
/*     */       } 
/*     */ 
/*     */       
/*  94 */       if (this.l >= 4 && this.m != this.n && this.n > 0) {
/*  95 */         iterator = list.iterator();
/*  96 */         PotionEffect secondaryEffect = new PotionEffect(PotionEffectType.getById(this.n), 180, 0, true);
/*     */         
/*  98 */         while (iterator.hasNext()) {
/*  99 */           EntityHuman entityhuman = iterator.next();
/*     */           
/* 101 */           BeaconEffectEvent event = new BeaconEffectEvent(block, secondaryEffect, (Player)entityhuman.getBukkitEntity(), false);
/* 102 */           if (((BeaconEffectEvent)CraftEventFactory.callEvent((Event)event)).isCancelled())
/*     */             continue; 
/* 104 */           PotionEffect effect = event.getEffect();
/* 105 */           entityhuman.addEffect(new MobEffect(effect.getType().getId(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void y() {
/* 113 */     int i = this.l;
/*     */     
/* 115 */     if (!this.world.i(this.x, this.y + 1, this.z)) {
/* 116 */       this.k = false;
/* 117 */       this.l = 0;
/*     */     } else {
/* 119 */       this.k = true;
/* 120 */       this.l = 0;
/*     */       
/* 122 */       for (int j = 1; j <= 4; this.l = j++) {
/* 123 */         int k = this.y - j;
/*     */         
/* 125 */         if (k < 0) {
/*     */           break;
/*     */         }
/*     */         
/* 129 */         boolean flag = true;
/*     */         
/* 131 */         for (int l = this.x - j; l <= this.x + j && flag; l++) {
/* 132 */           for (int i1 = this.z - j; i1 <= this.z + j; i1++) {
/* 133 */             Block block = this.world.getType(l, k, i1);
/*     */             
/* 135 */             if (block != Blocks.EMERALD_BLOCK && block != Blocks.GOLD_BLOCK && block != Blocks.DIAMOND_BLOCK && block != Blocks.IRON_BLOCK) {
/* 136 */               flag = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 142 */         if (!flag) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 147 */       if (this.l == 0) {
/* 148 */         this.k = false;
/*     */       }
/*     */     } 
/*     */     
/* 152 */     if (!this.world.isStatic && this.l == 4 && i < this.l) {
/* 153 */       Iterator<EntityHuman> iterator = this.world.a(EntityHuman.class, AxisAlignedBB.a(this.x, this.y, this.z, this.x, (this.y - 4), this.z).grow(10.0D, 5.0D, 10.0D)).iterator();
/*     */       
/* 155 */       while (iterator.hasNext()) {
/* 156 */         EntityHuman entityhuman = iterator.next();
/*     */         
/* 158 */         entityhuman.a(AchievementList.K);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int j() {
/* 164 */     return this.m;
/*     */   }
/*     */   
/*     */   public int k() {
/* 168 */     return this.n;
/*     */   }
/*     */   
/*     */   public int l() {
/* 172 */     return this.l;
/*     */   }
/*     */   
/*     */   public void d(int i) {
/* 176 */     this.m = 0;
/*     */     
/* 178 */     for (int j = 0; j < this.l && j < 3; j++) {
/* 179 */       MobEffectList[] amobeffectlist = a[j];
/* 180 */       int k = amobeffectlist.length;
/*     */       
/* 182 */       for (int l = 0; l < k; l++) {
/* 183 */         MobEffectList mobeffectlist = amobeffectlist[l];
/*     */         
/* 185 */         if (mobeffectlist.id == i) {
/* 186 */           this.m = i;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void e(int i) {
/* 194 */     this.n = 0;
/* 195 */     if (this.l >= 4) {
/* 196 */       for (int j = 0; j < 4; j++) {
/* 197 */         MobEffectList[] amobeffectlist = a[j];
/* 198 */         int k = amobeffectlist.length;
/*     */         
/* 200 */         for (int l = 0; l < k; l++) {
/* 201 */           MobEffectList mobeffectlist = amobeffectlist[l];
/*     */           
/* 203 */           if (mobeffectlist.id == i) {
/* 204 */             this.n = i;
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public Packet getUpdatePacket() {
/* 213 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 215 */     b(nbttagcompound);
/* 216 */     return new PacketPlayOutTileEntityData(this.x, this.y, this.z, 3, nbttagcompound);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 220 */     super.a(nbttagcompound);
/* 221 */     this.m = nbttagcompound.getInt("Primary");
/* 222 */     this.n = nbttagcompound.getInt("Secondary");
/* 223 */     this.l = nbttagcompound.getInt("Levels");
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 227 */     super.b(nbttagcompound);
/* 228 */     nbttagcompound.setInt("Primary", this.m);
/* 229 */     nbttagcompound.setInt("Secondary", this.n);
/* 230 */     nbttagcompound.setInt("Levels", this.l);
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 234 */     return 1;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/* 238 */     return (i == 0) ? this.inventorySlot : null;
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/* 242 */     if (i == 0 && this.inventorySlot != null) {
/* 243 */       if (j >= this.inventorySlot.count) {
/* 244 */         ItemStack itemstack = this.inventorySlot;
/*     */         
/* 246 */         this.inventorySlot = null;
/* 247 */         return itemstack;
/*     */       } 
/* 249 */       this.inventorySlot.count -= j;
/* 250 */       return new ItemStack(this.inventorySlot.getItem(), j, this.inventorySlot.getData());
/*     */     } 
/*     */     
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/* 258 */     if (i == 0 && this.inventorySlot != null) {
/* 259 */       ItemStack itemstack = this.inventorySlot;
/*     */       
/* 261 */       this.inventorySlot = null;
/* 262 */       return itemstack;
/*     */     } 
/* 264 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 269 */     if (i == 0) {
/* 270 */       this.inventorySlot = itemstack;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 275 */     return k_() ? this.p : "container.beacon";
/*     */   }
/*     */   
/*     */   public boolean k_() {
/* 279 */     return (this.p != null && this.p.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 283 */     this.p = s;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 287 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 291 */     return (this.world.getTileEntity(this.x, this.y, this.z) != this) ? false : ((entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D));
/*     */   }
/*     */   
/*     */   public void startOpen() {}
/*     */   
/*     */   public void closeContainer() {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 299 */     return (itemstack.getItem() == Items.EMERALD || itemstack.getItem() == Items.DIAMOND || itemstack.getItem() == Items.GOLD_INGOT || itemstack.getItem() == Items.IRON_INGOT);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */