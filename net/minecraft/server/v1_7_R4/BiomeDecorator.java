/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BiomeDecorator
/*     */ {
/*     */   protected World a;
/*     */   protected Random b;
/*     */   protected int c;
/*     */   protected int d;
/*  11 */   protected WorldGenerator e = new WorldGenClay(4);
/*     */   protected WorldGenerator f;
/*     */   protected WorldGenerator g;
/*     */   protected WorldGenerator h;
/*     */   protected WorldGenerator i;
/*     */   protected WorldGenerator j;
/*     */   protected WorldGenerator k;
/*     */   protected WorldGenerator l;
/*     */   protected WorldGenerator m;
/*     */   protected WorldGenerator n;
/*     */   protected WorldGenerator o;
/*     */   protected WorldGenFlowers p;
/*     */   protected WorldGenerator q;
/*     */   protected WorldGenerator r;
/*     */   protected WorldGenerator s;
/*     */   protected WorldGenerator t;
/*     */   protected WorldGenerator u;
/*     */   protected WorldGenerator v;
/*     */   protected int w;
/*     */   protected int x;
/*     */   protected int y;
/*     */   protected int z;
/*     */   protected int A;
/*     */   protected int B;
/*     */   protected int C;
/*     */   protected int D;
/*     */   protected int E;
/*     */   protected int F;
/*     */   protected int G;
/*     */   protected int H;
/*     */   public boolean I;
/*     */   
/*     */   public BiomeDecorator() {
/*  44 */     this.f = new WorldGenSand(Blocks.SAND, 7);
/*  45 */     this.g = new WorldGenSand(Blocks.GRAVEL, 6);
/*  46 */     this.h = new WorldGenMinable(Blocks.DIRT, 32);
/*  47 */     this.i = new WorldGenMinable(Blocks.GRAVEL, 32);
/*  48 */     this.j = new WorldGenMinable(Blocks.COAL_ORE, 16);
/*  49 */     this.k = new WorldGenMinable(Blocks.IRON_ORE, 8);
/*  50 */     this.l = new WorldGenMinable(Blocks.GOLD_ORE, 8);
/*  51 */     this.m = new WorldGenMinable(Blocks.REDSTONE_ORE, 7);
/*  52 */     this.n = new WorldGenMinable(Blocks.DIAMOND_ORE, 7);
/*  53 */     this.o = new WorldGenMinable(Blocks.LAPIS_ORE, 6);
/*  54 */     this.p = new WorldGenFlowers(Blocks.YELLOW_FLOWER);
/*  55 */     this.q = new WorldGenFlowers(Blocks.BROWN_MUSHROOM);
/*  56 */     this.r = new WorldGenFlowers(Blocks.RED_MUSHROOM);
/*  57 */     this.s = new WorldGenHugeMushroom();
/*  58 */     this.t = new WorldGenReed();
/*  59 */     this.u = new WorldGenCactus();
/*  60 */     this.v = new WorldGenWaterLily();
/*  61 */     this.y = 2;
/*  62 */     this.z = 1;
/*  63 */     this.E = 1;
/*  64 */     this.F = 3;
/*  65 */     this.G = 1;
/*  66 */     this.I = true;
/*     */   }
/*     */   
/*     */   public void a(World world, Random random, BiomeBase biomebase, int i, int j) {
/*  70 */     if (this.a != null) {
/*  71 */       throw new RuntimeException("Already decorating!!");
/*     */     }
/*  73 */     this.a = world;
/*  74 */     this.b = random;
/*  75 */     this.c = i;
/*  76 */     this.d = j;
/*  77 */     a(biomebase);
/*  78 */     this.a = null;
/*  79 */     this.b = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(BiomeBase biomebase) {
/*  84 */     a();
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */     
/*  90 */     for (i = 0; i < this.F; i++) {
/*  91 */       int m = this.c + this.b.nextInt(16) + 8;
/*  92 */       int k = this.d + this.b.nextInt(16) + 8;
/*  93 */       this.f.generate(this.a, this.b, m, this.a.i(m, k), k);
/*     */     } 
/*     */     
/*  96 */     for (i = 0; i < this.G; i++) {
/*  97 */       int m = this.c + this.b.nextInt(16) + 8;
/*  98 */       int k = this.d + this.b.nextInt(16) + 8;
/*  99 */       this.e.generate(this.a, this.b, m, this.a.i(m, k), k);
/*     */     } 
/*     */     
/* 102 */     for (i = 0; i < this.E; i++) {
/* 103 */       int m = this.c + this.b.nextInt(16) + 8;
/* 104 */       int k = this.d + this.b.nextInt(16) + 8;
/* 105 */       this.g.generate(this.a, this.b, m, this.a.i(m, k), k);
/*     */     } 
/*     */     
/* 108 */     i = this.x;
/* 109 */     if (this.b.nextInt(10) == 0) {
/* 110 */       i++;
/*     */     }
/*     */ 
/*     */     
/*     */     int j;
/*     */     
/* 116 */     for (j = 0; j < i; j++) {
/* 117 */       int k = this.c + this.b.nextInt(16) + 8;
/* 118 */       int l = this.d + this.b.nextInt(16) + 8;
/* 119 */       int i1 = this.a.getHighestBlockYAt(k, l);
/* 120 */       WorldGenTreeAbstract worldgentreeabstract = biomebase.a(this.b);
/*     */       
/* 122 */       worldgentreeabstract.a(1.0D, 1.0D, 1.0D);
/* 123 */       if (worldgentreeabstract.generate(this.a, this.b, k, i1, l)) {
/* 124 */         worldgentreeabstract.b(this.a, this.b, k, i1, l);
/*     */       }
/*     */     } 
/*     */     
/* 128 */     for (j = 0; j < this.H; j++) {
/* 129 */       int k = this.c + this.b.nextInt(16) + 8;
/* 130 */       int l = this.d + this.b.nextInt(16) + 8;
/* 131 */       this.s.generate(this.a, this.b, k, this.a.getHighestBlockYAt(k, l), l);
/*     */     } 
/*     */     
/* 134 */     for (j = 0; j < this.y; j++) {
/* 135 */       int k = this.c + this.b.nextInt(16) + 8;
/* 136 */       int l = this.d + this.b.nextInt(16) + 8;
/* 137 */       int i1 = this.b.nextInt(this.a.getHighestBlockYAt(k, l) + 32);
/* 138 */       String s = biomebase.a(this.b, k, i1, l);
/* 139 */       BlockFlowers blockflowers = BlockFlowers.e(s);
/*     */       
/* 141 */       if (blockflowers.getMaterial() != Material.AIR) {
/* 142 */         this.p.a(blockflowers, BlockFlowers.f(s));
/* 143 */         this.p.generate(this.a, this.b, k, i1, l);
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     for (j = 0; j < this.z; j++) {
/* 148 */       int k = this.c + this.b.nextInt(16) + 8;
/* 149 */       int l = this.d + this.b.nextInt(16) + 8;
/* 150 */       int i1 = this.b.nextInt(getHighestBlockYAt(k, l) * 2);
/* 151 */       WorldGenerator worldgenerator = biomebase.b(this.b);
/*     */       
/* 153 */       worldgenerator.generate(this.a, this.b, k, i1, l);
/*     */     } 
/*     */     
/* 156 */     for (j = 0; j < this.A; j++) {
/* 157 */       int k = this.c + this.b.nextInt(16) + 8;
/* 158 */       int l = this.d + this.b.nextInt(16) + 8;
/* 159 */       int i1 = this.b.nextInt(getHighestBlockYAt(k, l) * 2);
/* 160 */       (new WorldGenDeadBush(Blocks.DEAD_BUSH)).generate(this.a, this.b, k, i1, l);
/*     */     } 
/*     */     
/* 163 */     for (j = 0; j < this.w; j++) {
/* 164 */       int k = this.c + this.b.nextInt(16) + 8;
/* 165 */       int l = this.d + this.b.nextInt(16) + 8;
/*     */       int i1;
/* 167 */       for (i1 = this.b.nextInt(getHighestBlockYAt(k, l) * 2); i1 > 0 && this.a.isEmpty(k, i1 - 1, l); i1--);
/*     */ 
/*     */ 
/*     */       
/* 171 */       this.v.generate(this.a, this.b, k, i1, l);
/*     */     } 
/*     */     
/* 174 */     for (j = 0; j < this.B; j++) {
/* 175 */       if (this.b.nextInt(4) == 0) {
/* 176 */         int k = this.c + this.b.nextInt(16) + 8;
/* 177 */         int l = this.d + this.b.nextInt(16) + 8;
/* 178 */         int i1 = this.a.getHighestBlockYAt(k, l);
/* 179 */         this.q.generate(this.a, this.b, k, i1, l);
/*     */       } 
/*     */       
/* 182 */       if (this.b.nextInt(8) == 0) {
/* 183 */         int k = this.c + this.b.nextInt(16) + 8;
/* 184 */         int l = this.d + this.b.nextInt(16) + 8;
/* 185 */         int i1 = this.b.nextInt(getHighestBlockYAt(k, l) * 2);
/* 186 */         this.r.generate(this.a, this.b, k, i1, l);
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     if (this.b.nextInt(4) == 0) {
/* 191 */       j = this.c + this.b.nextInt(16) + 8;
/* 192 */       int k = this.d + this.b.nextInt(16) + 8;
/* 193 */       int l = this.b.nextInt(getHighestBlockYAt(j, k) * 2);
/* 194 */       this.q.generate(this.a, this.b, j, l, k);
/*     */     } 
/*     */     
/* 197 */     if (this.b.nextInt(8) == 0) {
/* 198 */       j = this.c + this.b.nextInt(16) + 8;
/* 199 */       int k = this.d + this.b.nextInt(16) + 8;
/* 200 */       int l = this.b.nextInt(getHighestBlockYAt(j, k) * 2);
/* 201 */       this.r.generate(this.a, this.b, j, l, k);
/*     */     } 
/*     */     
/* 204 */     for (j = 0; j < this.C; j++) {
/* 205 */       int k = this.c + this.b.nextInt(16) + 8;
/* 206 */       int l = this.d + this.b.nextInt(16) + 8;
/* 207 */       int i1 = this.b.nextInt(getHighestBlockYAt(k, l) * 2);
/* 208 */       this.t.generate(this.a, this.b, k, i1, l);
/*     */     } 
/*     */     
/* 211 */     for (j = 0; j < 10; j++) {
/* 212 */       int k = this.c + this.b.nextInt(16) + 8;
/* 213 */       int l = this.d + this.b.nextInt(16) + 8;
/* 214 */       int i1 = this.b.nextInt(getHighestBlockYAt(k, l) * 2);
/* 215 */       this.t.generate(this.a, this.b, k, i1, l);
/*     */     } 
/*     */     
/* 218 */     if (this.b.nextInt(32) == 0) {
/* 219 */       j = this.c + this.b.nextInt(16) + 8;
/* 220 */       int k = this.d + this.b.nextInt(16) + 8;
/* 221 */       int l = this.b.nextInt(getHighestBlockYAt(j, k) * 2);
/* 222 */       (new WorldGenPumpkin()).generate(this.a, this.b, j, l, k);
/*     */     } 
/*     */     
/* 225 */     for (j = 0; j < this.D; j++) {
/* 226 */       int k = this.c + this.b.nextInt(16) + 8;
/* 227 */       int l = this.d + this.b.nextInt(16) + 8;
/* 228 */       int i1 = this.b.nextInt(getHighestBlockYAt(k, l) * 2);
/* 229 */       this.u.generate(this.a, this.b, k, i1, l);
/*     */     } 
/*     */     
/* 232 */     if (this.I) {
/* 233 */       for (j = 0; j < 50; j++) {
/* 234 */         int k = this.c + this.b.nextInt(16) + 8;
/* 235 */         int l = this.b.nextInt(this.b.nextInt(248) + 8);
/* 236 */         int i1 = this.d + this.b.nextInt(16) + 8;
/* 237 */         (new WorldGenLiquids(Blocks.WATER)).generate(this.a, this.b, k, l, i1);
/*     */       } 
/*     */       
/* 240 */       for (j = 0; j < 20; j++) {
/* 241 */         int k = this.c + this.b.nextInt(16) + 8;
/* 242 */         int l = this.b.nextInt(this.b.nextInt(this.b.nextInt(240) + 8) + 8);
/* 243 */         int i1 = this.d + this.b.nextInt(16) + 8;
/* 244 */         (new WorldGenLiquids(Blocks.LAVA)).generate(this.a, this.b, k, l, i1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(int i, WorldGenerator worldgenerator, int j, int k) {
/* 250 */     for (int l = 0; l < i; l++) {
/* 251 */       int i1 = this.c + this.b.nextInt(16);
/* 252 */       int j1 = this.b.nextInt(k - j) + j;
/* 253 */       int k1 = this.d + this.b.nextInt(16);
/*     */       
/* 255 */       worldgenerator.generate(this.a, this.b, i1, j1, k1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(int i, WorldGenerator worldgenerator, int j, int k) {
/* 260 */     for (int l = 0; l < i; l++) {
/* 261 */       int i1 = this.c + this.b.nextInt(16);
/* 262 */       int j1 = this.b.nextInt(k) + this.b.nextInt(k) + j - k;
/* 263 */       int k1 = this.d + this.b.nextInt(16);
/*     */       
/* 265 */       worldgenerator.generate(this.a, this.b, i1, j1, k1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a() {
/* 270 */     a(20, this.h, 0, 256);
/* 271 */     a(10, this.i, 0, 256);
/* 272 */     a(20, this.j, 0, 128);
/* 273 */     a(20, this.k, 0, 64);
/* 274 */     a(2, this.l, 0, 32);
/* 275 */     a(8, this.m, 0, 16);
/* 276 */     a(1, this.n, 0, 16);
/* 277 */     b(1, this.o, 16, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getHighestBlockYAt(int x, int z) {
/* 283 */     return Math.max(1, this.a.getHighestBlockYAt(x, z));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeDecorator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */