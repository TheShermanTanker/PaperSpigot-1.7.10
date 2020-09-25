/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BiomeMesa
/*     */   extends BiomeBase {
/*     */   private byte[] aC;
/*     */   private long aD;
/*     */   private NoiseGenerator3 aE;
/*     */   private NoiseGenerator3 aF;
/*     */   private NoiseGenerator3 aG;
/*     */   private boolean aH;
/*     */   private boolean aI;
/*     */   
/*     */   public BiomeMesa(int i, boolean flag, boolean flag1) {
/*  17 */     super(i);
/*  18 */     this.aH = flag;
/*  19 */     this.aI = flag1;
/*  20 */     b();
/*  21 */     a(2.0F, 0.0F);
/*  22 */     this.at.clear();
/*  23 */     this.ai = Blocks.SAND;
/*  24 */     this.aj = 1;
/*  25 */     this.ak = Blocks.STAINED_HARDENED_CLAY;
/*  26 */     this.ar.x = -999;
/*  27 */     this.ar.A = 20;
/*  28 */     this.ar.C = 3;
/*  29 */     this.ar.D = 5;
/*  30 */     this.ar.y = 0;
/*  31 */     this.at.clear();
/*  32 */     if (flag1) {
/*  33 */       this.ar.x = 5;
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldGenTreeAbstract a(Random random) {
/*  38 */     return this.az;
/*     */   }
/*     */   
/*     */   public void a(World world, Random random, int i, int j) {
/*  42 */     super.a(world, random, i, j);
/*     */   }
/*     */   
/*     */   public void a(World world, Random random, Block[] ablock, byte[] abyte, int i, int j, double d0) {
/*  46 */     if (this.aC == null || this.aD != world.getSeed()) {
/*  47 */       a(world.getSeed());
/*     */     }
/*     */     
/*  50 */     if (this.aE == null || this.aF == null || this.aD != world.getSeed()) {
/*  51 */       Random random1 = new Random(this.aD);
/*     */       
/*  53 */       this.aE = new NoiseGenerator3(random1, 4);
/*  54 */       this.aF = new NoiseGenerator3(random1, 1);
/*     */     } 
/*     */     
/*  57 */     this.aD = world.getSeed();
/*  58 */     double d1 = 0.0D;
/*     */ 
/*     */ 
/*     */     
/*  62 */     if (this.aH) {
/*  63 */       int m = (i & 0xFFFFFFF0) + (j & 0xF);
/*  64 */       int n = (j & 0xFFFFFFF0) + (i & 0xF);
/*  65 */       double d2 = Math.min(Math.abs(d0), this.aE.a(m * 0.25D, n * 0.25D));
/*     */       
/*  67 */       if (d2 > 0.0D) {
/*  68 */         double d3 = 0.001953125D;
/*  69 */         double d4 = Math.abs(this.aF.a(m * d3, n * d3));
/*     */         
/*  71 */         d1 = d2 * d2 * 2.5D;
/*  72 */         double d5 = Math.ceil(d4 * 50.0D) + 14.0D;
/*     */         
/*  74 */         if (d1 > d5) {
/*  75 */           d1 = d5;
/*     */         }
/*     */         
/*  78 */         d1 += 64.0D;
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     int k = i & 0xF;
/*  83 */     int l = j & 0xF;
/*  84 */     boolean flag = true;
/*  85 */     Block block = Blocks.STAINED_HARDENED_CLAY;
/*  86 */     Block block1 = this.ak;
/*  87 */     int i1 = (int)(d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
/*  88 */     boolean flag1 = (Math.cos(d0 / 3.0D * Math.PI) > 0.0D);
/*  89 */     int j1 = -1;
/*  90 */     boolean flag2 = false;
/*  91 */     int k1 = ablock.length / 256;
/*     */     
/*  93 */     for (int l1 = 255; l1 >= 0; l1--) {
/*  94 */       int i2 = (l * 16 + k) * k1 + l1;
/*     */       
/*  96 */       if ((ablock[i2] == null || ablock[i2].getMaterial() == Material.AIR) && l1 < (int)d1) {
/*  97 */         ablock[i2] = Blocks.STONE;
/*     */       }
/*     */       
/* 100 */       if (l1 <= (world.paperSpigotConfig.generateFlatBedrock ? 0 : random.nextInt(5))) {
/* 101 */         ablock[i2] = Blocks.BEDROCK;
/*     */       } else {
/* 103 */         Block block2 = ablock[i2];
/*     */         
/* 105 */         if (block2 != null && block2.getMaterial() != Material.AIR) {
/* 106 */           if (block2 == Blocks.STONE)
/*     */           {
/*     */             
/* 109 */             if (j1 == -1) {
/* 110 */               flag2 = false;
/* 111 */               if (i1 <= 0) {
/* 112 */                 block = null;
/* 113 */                 block1 = Blocks.STONE;
/* 114 */               } else if (l1 >= 59 && l1 <= 64) {
/* 115 */                 block = Blocks.STAINED_HARDENED_CLAY;
/* 116 */                 block1 = this.ak;
/*     */               } 
/*     */               
/* 119 */               if (l1 < 63 && (block == null || block.getMaterial() == Material.AIR)) {
/* 120 */                 block = Blocks.STATIONARY_WATER;
/*     */               }
/*     */               
/* 123 */               j1 = i1 + Math.max(0, l1 - 63);
/* 124 */               if (l1 >= 62) {
/* 125 */                 if (this.aI && l1 > 86 + i1 * 2) {
/* 126 */                   if (flag1) {
/* 127 */                     ablock[i2] = Blocks.DIRT;
/* 128 */                     abyte[i2] = 1;
/*     */                   } else {
/* 130 */                     ablock[i2] = Blocks.GRASS;
/*     */                   } 
/* 132 */                 } else if (l1 > 66 + i1) {
/* 133 */                   byte b0 = 16;
/* 134 */                   if (l1 >= 64 && l1 <= 127) {
/* 135 */                     if (!flag1) {
/* 136 */                       b0 = d(i, l1, j);
/*     */                     }
/*     */                   } else {
/* 139 */                     b0 = 1;
/*     */                   } 
/*     */                   
/* 142 */                   if (b0 < 16) {
/* 143 */                     ablock[i2] = Blocks.STAINED_HARDENED_CLAY;
/* 144 */                     abyte[i2] = b0;
/*     */                   } else {
/* 146 */                     ablock[i2] = Blocks.HARDENED_CLAY;
/*     */                   } 
/*     */                 } else {
/* 149 */                   ablock[i2] = this.ai;
/* 150 */                   abyte[i2] = (byte)this.aj;
/* 151 */                   flag2 = true;
/*     */                 } 
/*     */               } else {
/* 154 */                 ablock[i2] = block1;
/* 155 */                 if (block1 == Blocks.STAINED_HARDENED_CLAY) {
/* 156 */                   abyte[i2] = 1;
/*     */                 }
/*     */               } 
/* 159 */             } else if (j1 > 0) {
/* 160 */               j1--;
/* 161 */               if (flag2) {
/* 162 */                 ablock[i2] = Blocks.STAINED_HARDENED_CLAY;
/* 163 */                 abyte[i2] = 1;
/*     */               } else {
/* 165 */                 byte b0 = d(i, l1, j);
/* 166 */                 if (b0 < 16) {
/* 167 */                   ablock[i2] = Blocks.STAINED_HARDENED_CLAY;
/* 168 */                   abyte[i2] = b0;
/*     */                 } else {
/* 170 */                   ablock[i2] = Blocks.HARDENED_CLAY;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } else {
/* 176 */           j1 = -1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(long i) {
/* 183 */     this.aC = new byte[64];
/* 184 */     Arrays.fill(this.aC, (byte)16);
/* 185 */     Random random = new Random(i);
/*     */     
/* 187 */     this.aG = new NoiseGenerator3(random, 1);
/*     */     
/*     */     int j;
/*     */     
/* 191 */     for (j = 0; j < 64; j++) {
/* 192 */       j += random.nextInt(5) + 1;
/* 193 */       if (j < 64) {
/* 194 */         this.aC[j] = 1;
/*     */       }
/*     */     } 
/*     */     
/* 198 */     j = random.nextInt(4) + 2;
/*     */ 
/*     */ 
/*     */     
/*     */     int k;
/*     */ 
/*     */     
/* 205 */     for (k = 0; k < j; k++) {
/* 206 */       int m = random.nextInt(3) + 1;
/* 207 */       int n = random.nextInt(64);
/*     */       
/* 209 */       for (int i2 = 0; n + i2 < 64 && i2 < m; i2++) {
/* 210 */         this.aC[n + i2] = 4;
/*     */       }
/*     */     } 
/*     */     
/* 214 */     k = random.nextInt(4) + 2;
/*     */     
/*     */     int l;
/*     */     
/* 218 */     for (l = 0; l < k; l++) {
/* 219 */       int m = random.nextInt(3) + 2;
/* 220 */       int n = random.nextInt(64);
/*     */       
/* 222 */       for (int i2 = 0; n + i2 < 64 && i2 < m; i2++) {
/* 223 */         this.aC[n + i2] = 12;
/*     */       }
/*     */     } 
/*     */     
/* 227 */     l = random.nextInt(4) + 2;
/*     */     int i1;
/* 229 */     for (i1 = 0; i1 < l; i1++) {
/* 230 */       int m = random.nextInt(3) + 1;
/* 231 */       int n = random.nextInt(64);
/*     */       
/* 233 */       for (int l1 = 0; n + l1 < 64 && l1 < m; l1++) {
/* 234 */         this.aC[n + l1] = 14;
/*     */       }
/*     */     } 
/*     */     
/* 238 */     i1 = random.nextInt(3) + 3;
/* 239 */     int j1 = 0;
/*     */     
/* 241 */     for (int k1 = 0; k1 < i1; k1++) {
/* 242 */       byte b0 = 1;
/*     */       
/* 244 */       j1 += random.nextInt(16) + 4;
/*     */       
/* 246 */       for (int i2 = 0; j1 + i2 < 64 && i2 < b0; i2++) {
/* 247 */         this.aC[j1 + i2] = 0;
/* 248 */         if (j1 + i2 > 1 && random.nextBoolean()) {
/* 249 */           this.aC[j1 + i2 - 1] = 8;
/*     */         }
/*     */         
/* 252 */         if (j1 + i2 < 63 && random.nextBoolean()) {
/* 253 */           this.aC[j1 + i2 + 1] = 8;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte d(int i, int j, int k) {
/* 260 */     int l = (int)Math.round(this.aG.a(i * 1.0D / 512.0D, i * 1.0D / 512.0D) * 2.0D);
/*     */     
/* 262 */     return this.aC[(j + l + 64) % 64];
/*     */   }
/*     */   
/*     */   protected BiomeBase k() {
/* 266 */     boolean flag = (this.id == BiomeBase.MESA.id);
/* 267 */     BiomeMesa biomemesa = new BiomeMesa(this.id + 128, flag, this.aI);
/*     */     
/* 269 */     if (!flag) {
/* 270 */       biomemesa.a(g);
/* 271 */       biomemesa.a(this.af + " M");
/*     */     } else {
/* 273 */       biomemesa.a(this.af + " (Bryce)");
/*     */     } 
/*     */     
/* 276 */     biomemesa.a(this.ag, true);
/* 277 */     return biomemesa;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeMesa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */