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
/*     */ public class BlockJukeBox
/*     */   extends BlockContainer
/*     */ {
/*     */   protected BlockJukeBox() {
/*  52 */     super(Material.WOOD);
/*  53 */     a(CreativeModeTab.c);
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
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  66 */     if (paramWorld.getData(paramInt1, paramInt2, paramInt3) == 0) return false; 
/*  67 */     dropRecord(paramWorld, paramInt1, paramInt2, paramInt3);
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, ItemStack paramItemStack) {
/*  72 */     if (paramWorld.isStatic)
/*     */       return; 
/*  74 */     TileEntityRecordPlayer tileEntityRecordPlayer = (TileEntityRecordPlayer)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/*  75 */     if (tileEntityRecordPlayer == null)
/*     */       return; 
/*  77 */     tileEntityRecordPlayer.setRecord(paramItemStack.cloneItemStack());
/*     */     
/*  79 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, 1, 2);
/*     */   }
/*     */   
/*     */   public void dropRecord(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  83 */     if (paramWorld.isStatic)
/*     */       return; 
/*  85 */     TileEntityRecordPlayer tileEntityRecordPlayer = (TileEntityRecordPlayer)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/*  86 */     if (tileEntityRecordPlayer == null)
/*     */       return; 
/*  88 */     ItemStack itemStack1 = tileEntityRecordPlayer.getRecord();
/*  89 */     if (itemStack1 == null)
/*     */       return; 
/*  91 */     paramWorld.triggerEffect(1005, paramInt1, paramInt2, paramInt3, 0);
/*  92 */     paramWorld.a((String)null, paramInt1, paramInt2, paramInt3);
/*  93 */     tileEntityRecordPlayer.setRecord(null);
/*  94 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, 0, 2);
/*     */     
/*  96 */     float f = 0.7F;
/*  97 */     double d1 = (paramWorld.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  98 */     double d2 = (paramWorld.random.nextFloat() * f) + (1.0F - f) * 0.2D + 0.6D;
/*  99 */     double d3 = (paramWorld.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/*     */     
/* 101 */     ItemStack itemStack2 = itemStack1.cloneItemStack();
/*     */     
/* 103 */     EntityItem entityItem = new EntityItem(paramWorld, paramInt1 + d1, paramInt2 + d2, paramInt3 + d3, itemStack2);
/* 104 */     entityItem.pickupDelay = 10;
/* 105 */     paramWorld.addEntity(entityItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 110 */     dropRecord(paramWorld, paramInt1, paramInt2, paramInt3);
/* 111 */     super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5) {
/* 116 */     if (paramWorld.isStatic)
/* 117 */       return;  super.dropNaturally(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt) {
/* 122 */     return new TileEntityRecordPlayer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 138 */     ItemStack itemStack = ((TileEntityRecordPlayer)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3)).getRecord();
/*     */     
/* 140 */     return (itemStack == null) ? 0 : (Item.getId(itemStack.getItem()) + 1 - Item.getId(Items.RECORD_1));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockJukeBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */