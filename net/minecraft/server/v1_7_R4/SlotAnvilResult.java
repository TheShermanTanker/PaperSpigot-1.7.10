/*     */ package net.minecraft.server.v1_7_R4;
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
/*     */ class SlotAnvilResult
/*     */   extends Slot
/*     */ {
/*     */   SlotAnvilResult(ContainerAnvil paramContainerAnvil, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3, World paramWorld, int paramInt4, int paramInt5, int paramInt6) {
/*  61 */     super(paramIInventory, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   public boolean isAllowed(ItemStack paramItemStack) {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowed(EntityHuman paramEntityHuman) {
/*  69 */     return ((paramEntityHuman.abilities.canInstantlyBuild || paramEntityHuman.expLevel >= this.e.a) && this.e.a > 0 && hasItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(EntityHuman paramEntityHuman, ItemStack paramItemStack) {
/*  74 */     if (!paramEntityHuman.abilities.canInstantlyBuild) paramEntityHuman.levelDown(-this.e.a); 
/*  75 */     ContainerAnvil.a(this.e).setItem(0, null);
/*  76 */     if (ContainerAnvil.b(this.e) > 0) {
/*  77 */       ItemStack itemStack = ContainerAnvil.a(this.e).getItem(1);
/*  78 */       if (itemStack != null && itemStack.count > ContainerAnvil.b(this.e)) {
/*  79 */         itemStack.count -= ContainerAnvil.b(this.e);
/*  80 */         ContainerAnvil.a(this.e).setItem(1, itemStack);
/*     */       } else {
/*  82 */         ContainerAnvil.a(this.e).setItem(1, null);
/*     */       } 
/*     */     } else {
/*  85 */       ContainerAnvil.a(this.e).setItem(1, null);
/*     */     } 
/*  87 */     this.e.a = 0;
/*     */     
/*  89 */     if (!paramEntityHuman.abilities.canInstantlyBuild && !this.a.isStatic && this.a.getType(this.b, this.c, this.d) == Blocks.ANVIL && paramEntityHuman.aI().nextFloat() < 0.12F) {
/*  90 */       int i = this.a.getData(this.b, this.c, this.d);
/*  91 */       int j = i & 0x3;
/*  92 */       int k = i >> 2;
/*     */       
/*  94 */       if (++k > 2) {
/*  95 */         this.a.setAir(this.b, this.c, this.d);
/*  96 */         this.a.triggerEffect(1020, this.b, this.c, this.d, 0);
/*     */       } else {
/*  98 */         this.a.setData(this.b, this.c, this.d, j | k << 2, 2);
/*  99 */         this.a.triggerEffect(1021, this.b, this.c, this.d, 0);
/*     */       } 
/* 101 */     } else if (!this.a.isStatic) {
/* 102 */       this.a.triggerEffect(1021, this.b, this.c, this.d, 0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SlotAnvilResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */