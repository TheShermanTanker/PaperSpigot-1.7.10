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
/*     */ public class BlockLeaves1
/*     */   extends BlockLeaves
/*     */ {
/*  18 */   public static final String[][] N = new String[][] { { "leaves_oak", "leaves_spruce", "leaves_birch", "leaves_jungle" }, { "leaves_oak_opaque", "leaves_spruce_opaque", "leaves_birch_opaque", "leaves_jungle_opaque" } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   public static final String[] O = new String[] { "oak", "spruce", "birch", "jungle" };
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
/*     */   protected void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  57 */     if ((paramInt4 & 0x3) == 0 && paramWorld.random.nextInt(paramInt5) == 0) {
/*  58 */       a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Items.APPLE, 1, 0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected int b(int paramInt) {
/*  64 */     int i = super.b(paramInt);
/*     */     
/*  66 */     if ((paramInt & 0x3) == 3) {
/*  67 */       i = 40;
/*     */     }
/*     */     
/*  70 */     return i;
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
/*     */   public String[] e() {
/* 108 */     return O;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockLeaves1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */