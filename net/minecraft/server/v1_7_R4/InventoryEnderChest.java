/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class InventoryEnderChest
/*     */   extends InventorySubcontainer
/*     */ {
/*     */   private TileEntityEnderChest a;
/*  14 */   public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
/*     */   public Player player;
/*  16 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  19 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  23 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  27 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  31 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  35 */     return (InventoryHolder)this.player;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  39 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/*  43 */     return this.maxStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryEnderChest() {
/*  48 */     super("container.enderchest", false, 27);
/*     */   }
/*     */   
/*     */   public void a(TileEntityEnderChest tileentityenderchest) {
/*  52 */     this.a = tileentityenderchest;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(NBTTagList nbttaglist) {
/*     */     int i;
/*  58 */     for (i = 0; i < getSize(); i++) {
/*  59 */       setItem(i, (ItemStack)null);
/*     */     }
/*     */     
/*  62 */     for (i = 0; i < nbttaglist.size(); i++) {
/*  63 */       NBTTagCompound nbttagcompound = nbttaglist.get(i);
/*  64 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/*  66 */       if (j >= 0 && j < getSize()) {
/*  67 */         setItem(j, ItemStack.createStack(nbttagcompound));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public NBTTagList h() {
/*  73 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  75 */     for (int i = 0; i < getSize(); i++) {
/*  76 */       ItemStack itemstack = getItem(i);
/*     */       
/*  78 */       if (itemstack != null) {
/*  79 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */         
/*  81 */         nbttagcompound.setByte("Slot", (byte)i);
/*  82 */         itemstack.save(nbttagcompound);
/*  83 */         nbttaglist.add(nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  91 */     return (this.a != null && !this.a.a(entityhuman)) ? false : super.a(entityhuman);
/*     */   }
/*     */   
/*     */   public void startOpen() {
/*  95 */     if (this.a != null) {
/*  96 */       this.a.a();
/*     */     }
/*     */     
/*  99 */     super.startOpen();
/*     */   }
/*     */   
/*     */   public void closeContainer() {
/* 103 */     if (this.a != null) {
/* 104 */       this.a.b();
/*     */     }
/*     */     
/* 107 */     super.closeContainer();
/* 108 */     this.a = null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\InventoryEnderChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */