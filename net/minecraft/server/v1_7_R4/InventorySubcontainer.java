/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class InventorySubcontainer
/*     */   implements IInventory {
/*     */   private String a;
/*     */   private int b;
/*     */   protected ItemStack[] items;
/*     */   private List d;
/*     */   private boolean e;
/*     */   
/*     */   public InventorySubcontainer(String s, boolean flag, int i) {
/*  15 */     this.a = s;
/*  16 */     this.e = flag;
/*  17 */     this.b = i;
/*  18 */     this.items = new ItemStack[i];
/*     */   }
/*     */   
/*     */   public void a(IInventoryListener iinventorylistener) {
/*  22 */     if (this.d == null) {
/*  23 */       this.d = new ArrayList();
/*     */     }
/*     */     
/*  26 */     this.d.add(iinventorylistener);
/*     */   }
/*     */   
/*     */   public void b(IInventoryListener iinventorylistener) {
/*  30 */     this.d.remove(iinventorylistener);
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  34 */     return (i >= 0 && i < this.items.length) ? this.items[i] : null;
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  38 */     if (this.items[i] != null) {
/*     */ 
/*     */       
/*  41 */       if ((this.items[i]).count <= j) {
/*  42 */         ItemStack itemStack = this.items[i];
/*  43 */         this.items[i] = null;
/*  44 */         update();
/*  45 */         return itemStack;
/*     */       } 
/*  47 */       ItemStack itemstack = this.items[i].a(j);
/*  48 */       if ((this.items[i]).count == 0) {
/*  49 */         this.items[i] = null;
/*     */       }
/*     */       
/*  52 */       update();
/*  53 */       return itemstack;
/*     */     } 
/*     */     
/*  56 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/*  61 */     if (this.items[i] != null) {
/*  62 */       ItemStack itemstack = this.items[i];
/*     */       
/*  64 */       this.items[i] = null;
/*  65 */       return itemstack;
/*     */     } 
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/*  72 */     this.items[i] = itemstack;
/*  73 */     if (itemstack != null && itemstack.count > getMaxStackSize()) {
/*  74 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */     
/*  77 */     update();
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  81 */     return this.b;
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/*  85 */     return this.a;
/*     */   }
/*     */   
/*     */   public boolean k_() {
/*  89 */     return this.e;
/*     */   }
/*     */   
/*     */   public void a(String s) {
/*  93 */     this.e = true;
/*  94 */     this.a = s;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/*  98 */     return 64;
/*     */   }
/*     */   
/*     */   public void update() {
/* 102 */     if (this.d != null) {
/* 103 */       for (int i = 0; i < this.d.size(); i++) {
/* 104 */         ((IInventoryListener)this.d.get(i)).a(this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 110 */     return true;
/*     */   }
/*     */   
/*     */   public void startOpen() {}
/*     */   
/*     */   public void closeContainer() {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 118 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\InventorySubcontainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */