/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.com.google.common.collect.Sets;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class BiomeBase
/*     */ {
/*  14 */   private static final Logger aC = LogManager.getLogger();
/*  15 */   protected static final BiomeTemperature a = new BiomeTemperature(0.1F, 0.2F);
/*  16 */   protected static final BiomeTemperature b = new BiomeTemperature(-0.5F, 0.0F);
/*  17 */   protected static final BiomeTemperature c = new BiomeTemperature(-1.0F, 0.1F);
/*  18 */   protected static final BiomeTemperature d = new BiomeTemperature(-1.8F, 0.1F);
/*  19 */   protected static final BiomeTemperature e = new BiomeTemperature(0.125F, 0.05F);
/*  20 */   protected static final BiomeTemperature f = new BiomeTemperature(0.2F, 0.2F);
/*  21 */   protected static final BiomeTemperature g = new BiomeTemperature(0.45F, 0.3F);
/*  22 */   protected static final BiomeTemperature h = new BiomeTemperature(1.5F, 0.025F);
/*  23 */   protected static final BiomeTemperature i = new BiomeTemperature(1.0F, 0.5F);
/*  24 */   protected static final BiomeTemperature j = new BiomeTemperature(0.0F, 0.025F);
/*  25 */   protected static final BiomeTemperature k = new BiomeTemperature(0.1F, 0.8F);
/*  26 */   protected static final BiomeTemperature l = new BiomeTemperature(0.2F, 0.3F);
/*  27 */   protected static final BiomeTemperature m = new BiomeTemperature(-0.2F, 0.1F);
/*  28 */   private static final BiomeBase[] biomes = new BiomeBase[256];
/*  29 */   public static final Set n = Sets.newHashSet();
/*  30 */   public static final BiomeBase OCEAN = (new BiomeOcean(0)).b(112).a("Ocean").a(c);
/*  31 */   public static final BiomeBase PLAINS = (new BiomePlains(1)).b(9286496).a("Plains");
/*  32 */   public static final BiomeBase DESERT = (new BiomeDesert(2)).b(16421912).a("Desert").b().a(2.0F, 0.0F).a(e);
/*  33 */   public static final BiomeBase EXTREME_HILLS = (new BiomeBigHills(3, false)).b(6316128).a("Extreme Hills").a(BiomeBase.i).a(0.2F, 0.3F);
/*  34 */   public static final BiomeBase FOREST = (new BiomeForest(4, 0)).b(353825).a("Forest");
/*  35 */   public static final BiomeBase TAIGA = (new BiomeTaiga(5, 0)).b(747097).a("Taiga").a(5159473).a(0.25F, 0.8F).a(f);
/*  36 */   public static final BiomeBase SWAMPLAND = (new BiomeSwamp(6)).b(522674).a("Swampland").a(9154376).a(m).a(0.8F, 0.9F);
/*  37 */   public static final BiomeBase RIVER = (new BiomeRiver(7)).b(255).a("River").a(b);
/*  38 */   public static final BiomeBase HELL = (new BiomeHell(8)).b(16711680).a("Hell").b().a(2.0F, 0.0F);
/*  39 */   public static final BiomeBase SKY = (new BiomeTheEnd(9)).b(8421631).a("Sky").b();
/*  40 */   public static final BiomeBase FROZEN_OCEAN = (new BiomeOcean(10)).b(9474208).a("FrozenOcean").c().a(c).a(0.0F, 0.5F);
/*  41 */   public static final BiomeBase FROZEN_RIVER = (new BiomeRiver(11)).b(10526975).a("FrozenRiver").c().a(b).a(0.0F, 0.5F);
/*  42 */   public static final BiomeBase ICE_PLAINS = (new BiomeIcePlains(12, false)).b(16777215).a("Ice Plains").c().a(0.0F, 0.5F).a(e);
/*  43 */   public static final BiomeBase ICE_MOUNTAINS = (new BiomeIcePlains(13, false)).b(10526880).a("Ice Mountains").c().a(g).a(0.0F, 0.5F);
/*  44 */   public static final BiomeBase MUSHROOM_ISLAND = (new BiomeMushrooms(14)).b(16711935).a("MushroomIsland").a(0.9F, 1.0F).a(l);
/*  45 */   public static final BiomeBase MUSHROOM_SHORE = (new BiomeMushrooms(15)).b(10486015).a("MushroomIslandShore").a(0.9F, 1.0F).a(BiomeBase.j);
/*  46 */   public static final BiomeBase BEACH = (new BiomeBeach(16)).b(16440917).a("Beach").a(0.8F, 0.4F).a(BiomeBase.j);
/*  47 */   public static final BiomeBase DESERT_HILLS = (new BiomeDesert(17)).b(13786898).a("DesertHills").b().a(2.0F, 0.0F).a(g);
/*  48 */   public static final BiomeBase FOREST_HILLS = (new BiomeForest(18, 0)).b(2250012).a("ForestHills").a(g);
/*  49 */   public static final BiomeBase TAIGA_HILLS = (new BiomeTaiga(19, 0)).b(1456435).a("TaigaHills").a(5159473).a(0.25F, 0.8F).a(g);
/*  50 */   public static final BiomeBase SMALL_MOUNTAINS = (new BiomeBigHills(20, true)).b(7501978).a("Extreme Hills Edge").a(BiomeBase.i.a()).a(0.2F, 0.3F);
/*  51 */   public static final BiomeBase JUNGLE = (new BiomeJungle(21, false)).b(5470985).a("Jungle").a(5470985).a(0.95F, 0.9F);
/*  52 */   public static final BiomeBase JUNGLE_HILLS = (new BiomeJungle(22, false)).b(2900485).a("JungleHills").a(5470985).a(0.95F, 0.9F).a(g);
/*  53 */   public static final BiomeBase JUNGLE_EDGE = (new BiomeJungle(23, true)).b(6458135).a("JungleEdge").a(5470985).a(0.95F, 0.8F);
/*  54 */   public static final BiomeBase DEEP_OCEAN = (new BiomeOcean(24)).b(48).a("Deep Ocean").a(d);
/*  55 */   public static final BiomeBase STONE_BEACH = (new BiomeStoneBeach(25)).b(10658436).a("Stone Beach").a(0.2F, 0.3F).a(k);
/*  56 */   public static final BiomeBase COLD_BEACH = (new BiomeBeach(26)).b(16445632).a("Cold Beach").a(0.05F, 0.3F).a(BiomeBase.j).c();
/*  57 */   public static final BiomeBase BIRCH_FOREST = (new BiomeForest(27, 2)).a("Birch Forest").b(3175492);
/*  58 */   public static final BiomeBase BIRCH_FOREST_HILLS = (new BiomeForest(28, 2)).a("Birch Forest Hills").b(2055986).a(g);
/*  59 */   public static final BiomeBase ROOFED_FOREST = (new BiomeForest(29, 3)).b(4215066).a("Roofed Forest");
/*  60 */   public static final BiomeBase COLD_TAIGA = (new BiomeTaiga(30, 0)).b(3233098).a("Cold Taiga").a(5159473).c().a(-0.5F, 0.4F).a(f).c(16777215);
/*  61 */   public static final BiomeBase COLD_TAIGA_HILLS = (new BiomeTaiga(31, 0)).b(2375478).a("Cold Taiga Hills").a(5159473).c().a(-0.5F, 0.4F).a(g).c(16777215);
/*  62 */   public static final BiomeBase MEGA_TAIGA = (new BiomeTaiga(32, 1)).b(5858897).a("Mega Taiga").a(5159473).a(0.3F, 0.8F).a(f);
/*  63 */   public static final BiomeBase MEGA_TAIGA_HILLS = (new BiomeTaiga(33, 1)).b(4542270).a("Mega Taiga Hills").a(5159473).a(0.3F, 0.8F).a(g);
/*  64 */   public static final BiomeBase EXTREME_HILLS_PLUS = (new BiomeBigHills(34, true)).b(5271632).a("Extreme Hills+").a(BiomeBase.i).a(0.2F, 0.3F);
/*  65 */   public static final BiomeBase SAVANNA = (new BiomeSavanna(35)).b(12431967).a("Savanna").a(1.2F, 0.0F).b().a(e);
/*  66 */   public static final BiomeBase SAVANNA_PLATEAU = (new BiomeSavanna(36)).b(10984804).a("Savanna Plateau").a(1.0F, 0.0F).b().a(h);
/*  67 */   public static final BiomeBase MESA = (new BiomeMesa(37, false, false)).b(14238997).a("Mesa");
/*  68 */   public static final BiomeBase MESA_PLATEAU_F = (new BiomeMesa(38, false, true)).b(11573093).a("Mesa Plateau F").a(h);
/*  69 */   public static final BiomeBase MESA_PLATEAU = (new BiomeMesa(39, false, false)).b(13274213).a("Mesa Plateau").a(h);
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
/*     */   protected BiomeBase(int i) {
/*  98 */     this.ai = Blocks.GRASS;
/*  99 */     this.aj = 0;
/* 100 */     this.ak = Blocks.DIRT;
/* 101 */     this.al = 5169201;
/* 102 */     this.am = a.a;
/* 103 */     this.an = a.b;
/* 104 */     this.temperature = 0.5F;
/* 105 */     this.humidity = 0.5F;
/* 106 */     this.aq = 16777215;
/* 107 */     this.as = new ArrayList();
/* 108 */     this.at = new ArrayList();
/* 109 */     this.au = new ArrayList();
/* 110 */     this.av = new ArrayList();
/* 111 */     this.ax = true;
/* 112 */     this.az = new WorldGenTrees(false);
/* 113 */     this.aA = new WorldGenBigTree(false);
/* 114 */     this.aB = new WorldGenSwampTree();
/* 115 */     this.id = i;
/* 116 */     biomes[i] = this;
/* 117 */     this.ar = a();
/* 118 */     this.at.add(new BiomeMeta(EntitySheep.class, 12, 4, 4));
/* 119 */     this.at.add(new BiomeMeta(EntityPig.class, 10, 4, 4));
/* 120 */     this.at.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
/* 121 */     this.at.add(new BiomeMeta(EntityCow.class, 8, 4, 4));
/* 122 */     this.as.add(new BiomeMeta(EntitySpider.class, 100, 4, 4));
/* 123 */     this.as.add(new BiomeMeta(EntityZombie.class, 100, 4, 4));
/* 124 */     this.as.add(new BiomeMeta(EntitySkeleton.class, 100, 4, 4));
/* 125 */     this.as.add(new BiomeMeta(EntityCreeper.class, 100, 4, 4));
/* 126 */     this.as.add(new BiomeMeta(EntitySlime.class, 100, 4, 4));
/* 127 */     this.as.add(new BiomeMeta(EntityEnderman.class, 10, 1, 4));
/* 128 */     this.as.add(new BiomeMeta(EntityWitch.class, 5, 1, 1));
/* 129 */     this.au.add(new BiomeMeta(EntitySquid.class, 10, 4, 4));
/* 130 */     this.av.add(new BiomeMeta(EntityBat.class, 10, 8, 8));
/*     */   }
/*     */   
/*     */   protected BiomeDecorator a() {
/* 134 */     return new BiomeDecorator();
/*     */   }
/*     */   
/*     */   protected BiomeBase a(float f, float f1) {
/* 138 */     if (f > 0.1F && f < 0.2F) {
/* 139 */       throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
/*     */     }
/* 141 */     this.temperature = f;
/* 142 */     this.humidity = f1;
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final BiomeBase a(BiomeTemperature biometemperature) {
/* 148 */     this.am = biometemperature.a;
/* 149 */     this.an = biometemperature.b;
/* 150 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase b() {
/* 154 */     this.ax = false;
/* 155 */     return this;
/*     */   }
/*     */   
/*     */   public WorldGenTreeAbstract a(Random random) {
/* 159 */     return (random.nextInt(10) == 0) ? this.aA : this.az;
/*     */   }
/*     */   
/*     */   public WorldGenerator b(Random random) {
/* 163 */     return new WorldGenGrass(Blocks.LONG_GRASS, 1);
/*     */   }
/*     */   
/*     */   public String a(Random random, int i, int j, int k) {
/* 167 */     return (random.nextInt(3) > 0) ? BlockFlowers.b[0] : BlockFlowers.a[0];
/*     */   }
/*     */   
/*     */   protected BiomeBase c() {
/* 171 */     this.aw = true;
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase a(String s) {
/* 176 */     this.af = s;
/* 177 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase a(int i) {
/* 181 */     this.al = i;
/* 182 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase b(int i) {
/* 186 */     a(i, false);
/* 187 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase c(int i) {
/* 191 */     this.ah = i;
/* 192 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase a(int i, boolean flag) {
/* 196 */     this.ag = i;
/* 197 */     if (flag) {
/* 198 */       this.ah = (i & 0xFEFEFE) >> 1;
/*     */     } else {
/* 200 */       this.ah = i;
/*     */     } 
/*     */     
/* 203 */     return this;
/*     */   }
/*     */   
/*     */   public List getMobs(EnumCreatureType enumcreaturetype) {
/* 207 */     return (enumcreaturetype == EnumCreatureType.MONSTER) ? this.as : ((enumcreaturetype == EnumCreatureType.CREATURE) ? this.at : ((enumcreaturetype == EnumCreatureType.WATER_CREATURE) ? this.au : ((enumcreaturetype == EnumCreatureType.AMBIENT) ? this.av : null)));
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 211 */     return j();
/*     */   }
/*     */   
/*     */   public boolean e() {
/* 215 */     return j() ? false : this.ax;
/*     */   }
/*     */   
/*     */   public boolean f() {
/* 219 */     return (this.humidity > 0.85F);
/*     */   }
/*     */   
/*     */   public float g() {
/* 223 */     return 0.1F;
/*     */   }
/*     */   
/*     */   public final int h() {
/* 227 */     return (int)(this.humidity * 65536.0F);
/*     */   }
/*     */   
/*     */   public final float a(int i, int j, int k) {
/* 231 */     if (j > 64) {
/* 232 */       float f = (float)ac.a(i * 1.0D / 8.0D, k * 1.0D / 8.0D) * 4.0F;
/*     */       
/* 234 */       return this.temperature - (f + j - 64.0F) * 0.05F / 30.0F;
/*     */     } 
/* 236 */     return this.temperature;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World world, Random random, int i, int j) {
/* 241 */     this.ar.a(world, random, this, i, j);
/*     */   }
/*     */   
/*     */   public boolean j() {
/* 245 */     return this.aw;
/*     */   }
/*     */   
/*     */   public void a(World world, Random random, Block[] ablock, byte[] abyte, int i, int j, double d0) {
/* 249 */     b(world, random, ablock, abyte, i, j, d0);
/*     */   }
/*     */   
/*     */   public final void b(World world, Random random, Block[] ablock, byte[] abyte, int i, int j, double d0) {
/* 253 */     boolean flag = true;
/* 254 */     Block block = this.ai;
/* 255 */     byte b0 = (byte)(this.aj & 0xFF);
/* 256 */     Block block1 = this.ak;
/* 257 */     int k = -1;
/* 258 */     int l = (int)(d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
/* 259 */     int i1 = i & 0xF;
/* 260 */     int j1 = j & 0xF;
/* 261 */     int k1 = ablock.length / 256;
/*     */     
/* 263 */     for (int l1 = 255; l1 >= 0; l1--) {
/* 264 */       int i2 = (j1 * 16 + i1) * k1 + l1;
/*     */       
/* 266 */       if (l1 <= (world.paperSpigotConfig.generateFlatBedrock ? 0 : random.nextInt(5))) {
/* 267 */         ablock[i2] = Blocks.BEDROCK;
/*     */       } else {
/* 269 */         Block block2 = ablock[i2];
/*     */         
/* 271 */         if (block2 != null && block2.getMaterial() != Material.AIR) {
/* 272 */           if (block2 == Blocks.STONE) {
/* 273 */             if (k == -1) {
/* 274 */               if (l <= 0) {
/* 275 */                 block = null;
/* 276 */                 b0 = 0;
/* 277 */                 block1 = Blocks.STONE;
/* 278 */               } else if (l1 >= 59 && l1 <= 64) {
/* 279 */                 block = this.ai;
/* 280 */                 b0 = (byte)(this.aj & 0xFF);
/* 281 */                 block1 = this.ak;
/*     */               } 
/*     */               
/* 284 */               if (l1 < 63 && (block == null || block.getMaterial() == Material.AIR)) {
/* 285 */                 if (a(i, l1, j) < 0.15F) {
/* 286 */                   block = Blocks.ICE;
/* 287 */                   b0 = 0;
/*     */                 } else {
/* 289 */                   block = Blocks.STATIONARY_WATER;
/* 290 */                   b0 = 0;
/*     */                 } 
/*     */               }
/*     */               
/* 294 */               k = l;
/* 295 */               if (l1 >= 62) {
/* 296 */                 ablock[i2] = block;
/* 297 */                 abyte[i2] = b0;
/* 298 */               } else if (l1 < 56 - l) {
/* 299 */                 block = null;
/* 300 */                 block1 = Blocks.STONE;
/* 301 */                 ablock[i2] = Blocks.GRAVEL;
/*     */               } else {
/* 303 */                 ablock[i2] = block1;
/*     */               } 
/* 305 */             } else if (k > 0) {
/* 306 */               k--;
/* 307 */               ablock[i2] = block1;
/* 308 */               if (k == 0 && block1 == Blocks.SAND) {
/* 309 */                 k = random.nextInt(4) + Math.max(0, l1 - 63);
/* 310 */                 block1 = Blocks.SANDSTONE;
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } else {
/* 315 */           k = -1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected BiomeBase k() {
/* 322 */     return new BiomeBaseSub(this.id + 128, this);
/*     */   }
/*     */   
/*     */   public Class l() {
/* 326 */     return getClass();
/*     */   }
/*     */   
/*     */   public boolean a(BiomeBase biomebase) {
/* 330 */     return (biomebase == this) ? true : ((biomebase == null) ? false : ((l() == biomebase.l())));
/*     */   }
/*     */   
/*     */   public EnumTemperature m() {
/* 334 */     return (this.temperature < 0.2D) ? EnumTemperature.COLD : ((this.temperature < 1.0D) ? EnumTemperature.MEDIUM : EnumTemperature.WARM);
/*     */   }
/*     */   
/*     */   public static BiomeBase[] getBiomes() {
/* 338 */     return biomes;
/*     */   }
/*     */   
/*     */   public static BiomeBase getBiome(int i) {
/* 342 */     if (i >= 0 && i <= biomes.length) {
/* 343 */       return biomes[i];
/*     */     }
/* 345 */     aC.warn("Biome ID is out of bounds: " + i + ", defaulting to 0 (Ocean)");
/* 346 */     return OCEAN;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 351 */     PLAINS.k();
/* 352 */     DESERT.k();
/* 353 */     FOREST.k();
/* 354 */     TAIGA.k();
/* 355 */     SWAMPLAND.k();
/* 356 */     ICE_PLAINS.k();
/* 357 */     JUNGLE.k();
/* 358 */     JUNGLE_EDGE.k();
/* 359 */     COLD_TAIGA.k();
/* 360 */     SAVANNA.k();
/* 361 */     SAVANNA_PLATEAU.k();
/* 362 */     MESA.k();
/* 363 */     MESA_PLATEAU_F.k();
/* 364 */     MESA_PLATEAU.k();
/* 365 */     BIRCH_FOREST.k();
/* 366 */     BIRCH_FOREST_HILLS.k();
/* 367 */     ROOFED_FOREST.k();
/* 368 */     MEGA_TAIGA.k();
/* 369 */     EXTREME_HILLS.k();
/* 370 */     EXTREME_HILLS_PLUS.k();
/* 371 */     biomes[MEGA_TAIGA_HILLS.id + 128] = biomes[MEGA_TAIGA.id + 128];
/* 372 */     BiomeBase[] abiomebase = biomes;
/* 373 */     int i = abiomebase.length;
/*     */     
/* 375 */     for (int j = 0; j < i; j++) {
/* 376 */       BiomeBase biomebase = abiomebase[j];
/*     */       
/* 378 */       if (biomebase != null && biomebase.id < 128) {
/* 379 */         n.add(biomebase);
/*     */       }
/*     */     } 
/*     */     
/* 383 */     n.remove(HELL);
/* 384 */     n.remove(SKY);
/* 385 */     n.remove(FROZEN_OCEAN);
/* 386 */     n.remove(SMALL_MOUNTAINS);
/* 387 */   } protected static final NoiseGenerator3 ac = new NoiseGenerator3(new Random(1234L), 1);
/* 388 */   protected static final NoiseGenerator3 ad = new NoiseGenerator3(new Random(2345L), 1);
/* 389 */   protected static final WorldGenTallPlant ae = new WorldGenTallPlant();
/*     */   public String af;
/*     */   public int ag;
/*     */   public int ah;
/*     */   public Block ai;
/*     */   public int aj;
/*     */   public Block ak;
/*     */   public int al;
/*     */   public float am;
/*     */   public float an;
/*     */   public float temperature;
/*     */   public float humidity;
/*     */   public int aq;
/*     */   public BiomeDecorator ar;
/*     */   protected List as;
/*     */   protected List at;
/*     */   protected List au;
/*     */   protected List av;
/*     */   protected boolean aw;
/*     */   protected boolean ax;
/*     */   public final int id;
/*     */   protected WorldGenTrees az;
/*     */   protected WorldGenBigTree aA;
/*     */   protected WorldGenSwampTree aB;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */