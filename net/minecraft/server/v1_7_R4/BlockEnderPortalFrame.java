/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
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
/*     */ public class BlockEnderPortalFrame
/*     */   extends Block
/*     */ {
/*     */   public BlockEnderPortalFrame() {
/*  23 */     super(Material.STONE);
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
/*     */   public boolean c() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/*  55 */     return 26;
/*     */   }
/*     */ 
/*     */   
/*     */   public void g() {
/*  60 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity) {
/*  65 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
/*  66 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */     
/*  68 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  69 */     if (b(i)) {
/*  70 */       a(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
/*  71 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */     } 
/*  73 */     g();
/*     */   }
/*     */   
/*     */   public static boolean b(int paramInt) {
/*  77 */     return ((paramInt & 0x4) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack) {
/*  87 */     int i = ((MathHelper.floor((paramEntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3) + 2) % 4;
/*  88 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComplexRedstone() {
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  98 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/* 100 */     if (b(i)) {
/* 101 */       return 15;
/*     */     }
/* 103 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockEnderPortalFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */