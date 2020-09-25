/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ public class Material {
/*   4 */   public static final Material AIR = new MaterialGas(MaterialMapColor.b);
/*   5 */   public static final Material GRASS = new Material(MaterialMapColor.c);
/*   6 */   public static final Material EARTH = new Material(MaterialMapColor.l);
/*   7 */   public static final Material WOOD = (new Material(MaterialMapColor.o)).g();
/*   8 */   public static final Material STONE = (new Material(MaterialMapColor.m)).f();
/*   9 */   public static final Material ORE = (new Material(MaterialMapColor.h)).f();
/*  10 */   public static final Material HEAVY = (new Material(MaterialMapColor.h)).f().o();
/*  11 */   public static final Material WATER = (new MaterialLiquid(MaterialMapColor.n)).n();
/*  12 */   public static final Material LAVA = (new MaterialLiquid(MaterialMapColor.f)).n();
/*  13 */   public static final Material LEAVES = (new Material(MaterialMapColor.i)).g().s().n();
/*  14 */   public static final Material PLANT = (new MaterialDecoration(MaterialMapColor.i)).n();
/*  15 */   public static final Material REPLACEABLE_PLANT = (new MaterialDecoration(MaterialMapColor.i)).g().n().i();
/*  16 */   public static final Material SPONGE = new Material(MaterialMapColor.e);
/*  17 */   public static final Material CLOTH = (new Material(MaterialMapColor.e)).g();
/*  18 */   public static final Material FIRE = (new MaterialGas(MaterialMapColor.b)).n();
/*  19 */   public static final Material SAND = new Material(MaterialMapColor.d);
/*  20 */   public static final Material ORIENTABLE = (new MaterialDecoration(MaterialMapColor.b)).n();
/*  21 */   public static final Material WOOL = (new MaterialDecoration(MaterialMapColor.e)).g();
/*  22 */   public static final Material SHATTERABLE = (new Material(MaterialMapColor.b)).s().p();
/*  23 */   public static final Material BUILDABLE_GLASS = (new Material(MaterialMapColor.b)).p();
/*  24 */   public static final Material TNT = (new Material(MaterialMapColor.f)).g().s();
/*  25 */   public static final Material CORAL = (new Material(MaterialMapColor.i)).n();
/*  26 */   public static final Material ICE = (new Material(MaterialMapColor.g)).s().p();
/*  27 */   public static final Material SNOW_LAYER = (new Material(MaterialMapColor.g)).p();
/*  28 */   public static final Material PACKED_ICE = (new MaterialDecoration(MaterialMapColor.j)).i().s().f().n();
/*  29 */   public static final Material SNOW_BLOCK = (new Material(MaterialMapColor.j)).f();
/*  30 */   public static final Material CACTUS = (new Material(MaterialMapColor.i)).s().n();
/*  31 */   public static final Material CLAY = new Material(MaterialMapColor.k);
/*  32 */   public static final Material PUMPKIN = (new Material(MaterialMapColor.i)).n();
/*  33 */   public static final Material DRAGON_EGG = (new Material(MaterialMapColor.i)).n();
/*  34 */   public static final Material PORTAL = (new MaterialPortal(MaterialMapColor.b)).o();
/*  35 */   public static final Material CAKE = (new Material(MaterialMapColor.b)).n();
/*  36 */   public static final Material WEB = (new MaterialWeb(MaterialMapColor.e)).f().n();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public static final Material PISTON = (new Material(MaterialMapColor.m)).o();
/*     */   
/*     */   private boolean canBurn;
/*     */   
/*     */   private boolean J;
/*     */   
/*     */   private boolean K;
/*     */   
/*     */   private final MaterialMapColor L;
/*     */   private boolean M = true;
/*     */   private int N;
/*     */   private boolean O;
/*     */   
/*     */   public Material(MaterialMapColor paramMaterialMapColor) {
/*  56 */     this.L = paramMaterialMapColor;
/*     */   }
/*     */   
/*     */   public boolean isLiquid() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBuildable() {
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   public boolean blocksLight() {
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isSolid() {
/*  76 */     return true;
/*     */   }
/*     */   
/*     */   private Material s() {
/*  80 */     this.K = true;
/*  81 */     return this;
/*     */   }
/*     */   
/*     */   protected Material f() {
/*  85 */     this.M = false;
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   protected Material g() {
/*  90 */     this.canBurn = true;
/*  91 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isBurnable() {
/*  95 */     return this.canBurn;
/*     */   }
/*     */   
/*     */   public Material i() {
/*  99 */     this.J = true;
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isReplaceable() {
/* 104 */     return this.J;
/*     */   }
/*     */   
/*     */   public boolean k() {
/* 108 */     if (this.K) return false; 
/* 109 */     return isSolid();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlwaysDestroyable() {
/* 115 */     return this.M;
/*     */   }
/*     */   
/*     */   public int getPushReaction() {
/* 119 */     return this.N;
/*     */   }
/*     */   
/*     */   protected Material n() {
/* 123 */     this.N = 1;
/* 124 */     return this;
/*     */   }
/*     */   
/*     */   protected Material o() {
/* 128 */     this.N = 2;
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   protected Material p() {
/* 133 */     this.O = true;
/* 134 */     return this;
/*     */   }
/*     */   
/*     */   public boolean q() {
/* 138 */     return this.O;
/*     */   }
/*     */   
/*     */   public MaterialMapColor r() {
/* 142 */     return this.L;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Material.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */