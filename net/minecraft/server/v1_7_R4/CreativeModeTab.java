/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CreativeModeTab
/*     */ {
/*  12 */   public static final CreativeModeTab[] a = new CreativeModeTab[12];
/*  13 */   public static final CreativeModeTab b = new CreativeModeTab1(0, "buildingBlocks");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  19 */   public static final CreativeModeTab c = new CreativeModeTab2(1, "decorations");
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
/*  30 */   public static final CreativeModeTab d = new CreativeModeTab3(2, "redstone");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public static final CreativeModeTab e = new CreativeModeTab4(3, "transportation");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public static final CreativeModeTab f = (new CreativeModeTab5(4, "misc")).a(new EnchantmentSlotType[] { EnchantmentSlotType.ALL });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final CreativeModeTab g = (new CreativeModeTab6(5, "search")).a("item_search.png");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final CreativeModeTab h = new CreativeModeTab7(6, "food");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final CreativeModeTab i = (new CreativeModeTab8(7, "tools")).a(new EnchantmentSlotType[] { EnchantmentSlotType.DIGGER, EnchantmentSlotType.FISHING_ROD, EnchantmentSlotType.BREAKABLE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final CreativeModeTab j = (new CreativeModeTab9(8, "combat")).a(new EnchantmentSlotType[] { EnchantmentSlotType.ARMOR, EnchantmentSlotType.ARMOR_FEET, EnchantmentSlotType.ARMOR_HEAD, EnchantmentSlotType.ARMOR_LEGS, EnchantmentSlotType.ARMOR_TORSO, EnchantmentSlotType.BOW, EnchantmentSlotType.WEAPON });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final CreativeModeTab k = new CreativeModeTab10(9, "brewing");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final CreativeModeTab l = new CreativeModeTab11(10, "materials");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final CreativeModeTab m = (new CreativeModeTab12(11, "inventory")).a("inventory.png").k().i();
/*     */ 
/*     */   
/*     */   private final int n;
/*     */ 
/*     */   
/*     */   private final String o;
/*     */ 
/*     */   
/*  98 */   private String p = "items.png";
/*     */   
/*     */   private boolean q = true;
/*     */   private boolean r = true;
/*     */   private EnchantmentSlotType[] s;
/*     */   
/*     */   public CreativeModeTab(int paramInt, String paramString) {
/* 105 */     this.n = paramInt;
/* 106 */     this.o = paramString;
/*     */     
/* 108 */     a[paramInt] = this;
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
/*     */   public CreativeModeTab a(String paramString) {
/* 141 */     this.p = paramString;
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeModeTab i() {
/* 150 */     this.r = false;
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeModeTab k() {
/* 159 */     this.q = false;
/* 160 */     return this;
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
/*     */   public CreativeModeTab a(EnchantmentSlotType... paramVarArgs) {
/* 176 */     this.s = paramVarArgs;
/* 177 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CreativeModeTab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */