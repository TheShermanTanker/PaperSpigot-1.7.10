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
/*     */ public class BlockFlowers
/*     */   extends BlockPlant
/*     */ {
/*  14 */   private static final String[][] M = new String[][] { { "flower_dandelion" }, { "flower_rose", "flower_blue_orchid", "flower_allium", "flower_houstonia", "flower_tulip_red", "flower_tulip_orange", "flower_tulip_white", "flower_tulip_pink", "flower_oxeye_daisy" } };
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
/*  35 */   public static final String[] a = new String[] { "poppy", "blueOrchid", "allium", "houstonia", "tulipRed", "tulipOrange", "tulipWhite", "tulipPink", "oxeyeDaisy" };
/*     */ 
/*     */ 
/*     */   
/*  39 */   public static final String[] b = new String[] { "dandelion" };
/*     */ 
/*     */   
/*     */   private int O;
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockFlowers(int paramInt) {
/*  47 */     super(Material.PLANT);
/*  48 */     this.O = paramInt;
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
/*     */   public int getDropData(int paramInt) {
/*  68 */     return paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockFlowers e(String paramString) {
/*  79 */     for (String str : b) {
/*  80 */       if (str.equals(paramString)) {
/*  81 */         return Blocks.YELLOW_FLOWER;
/*     */       }
/*     */     } 
/*  84 */     for (String str : a) {
/*  85 */       if (str.equals(paramString)) {
/*  86 */         return Blocks.RED_ROSE;
/*     */       }
/*     */     } 
/*  89 */     return null;
/*     */   }
/*     */   public static int f(String paramString) {
/*     */     byte b;
/*  93 */     for (b = 0; b < b.length; b++) {
/*  94 */       if (b[b].equals(paramString)) {
/*  95 */         return b;
/*     */       }
/*     */     } 
/*  98 */     for (b = 0; b < a.length; b++) {
/*  99 */       if (a[b].equals(paramString)) {
/* 100 */         return b;
/*     */       }
/*     */     } 
/* 103 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockFlowers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */