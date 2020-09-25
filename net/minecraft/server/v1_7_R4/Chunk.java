/*      */ package net.minecraft.server.v1_7_R4;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import net.minecraft.util.gnu.trove.map.hash.TObjectIntHashMap;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftChunk;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*      */ import org.bukkit.entity.HumanEntity;
/*      */ 
/*      */ public class Chunk {
/*   20 */   private static final Logger t = LogManager.getLogger();
/*      */   public static boolean a;
/*      */   private ChunkSection[] sections;
/*      */   private byte[] v;
/*      */   public int[] b;
/*      */   public boolean[] c;
/*      */   public boolean d;
/*      */   public World world;
/*      */   public int[] heightMap;
/*      */   public final int locX;
/*      */   public final int locZ;
/*      */   private boolean w;
/*      */   public Map tileEntities;
/*      */   public List[] entitySlices;
/*      */   public boolean done;
/*      */   public boolean lit;
/*      */   public boolean m;
/*      */   public boolean n;
/*      */   public boolean o;
/*      */   public long lastSaved;
/*      */   public boolean q;
/*      */   public int r;
/*      */   public long s;
/*      */   private int x;
/*   44 */   protected TObjectIntHashMap<Class> entityCount = new TObjectIntHashMap();
/*      */   
/*   46 */   public AtomicInteger pendingLightUpdates = new AtomicInteger();
/*      */   
/*      */   public long lightUpdateTime;
/*      */   
/*      */   private ChunkMap chunkMap17;
/*      */   
/*      */   private ChunkMap chunkMap18;
/*      */   private int emptySectionBits;
/*      */   
/*      */   public ChunkMap getChunkMap(boolean groundUpContinuous, int primaryBitMask, int version) {
/*   56 */     if (!this.world.paperSpigotConfig.cacheChunkMaps || !groundUpContinuous || (primaryBitMask != 0 && primaryBitMask != 65535)) {
/*   57 */       return PacketPlayOutMapChunk.a(this, groundUpContinuous, primaryBitMask, version);
/*      */     }
/*      */     
/*   60 */     if (primaryBitMask == 0) {
/*   61 */       ChunkMap chunkMap = new ChunkMap();
/*   62 */       chunkMap.a = new byte[0];
/*   63 */       return chunkMap;
/*      */     } 
/*      */     
/*   66 */     boolean isDirty = false;
/*   67 */     for (int i = 0; i < this.sections.length; i++) {
/*   68 */       ChunkSection section = this.sections[i];
/*   69 */       if (section == null) {
/*   70 */         if ((this.emptySectionBits & 1 << i) == 0) {
/*   71 */           isDirty = true;
/*   72 */           this.emptySectionBits |= 1 << i;
/*      */         }
/*      */       
/*   75 */       } else if ((this.emptySectionBits & 1 << i) == 1) {
/*   76 */         isDirty = true;
/*   77 */         this.emptySectionBits &= 1 << i ^ 0xFFFFFFFF;
/*   78 */         section.isDirty = false;
/*   79 */       } else if (section.isDirty) {
/*   80 */         isDirty = true;
/*   81 */         section.isDirty = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*   86 */     if (isDirty) {
/*   87 */       this.chunkMap17 = null;
/*   88 */       this.chunkMap18 = null;
/*      */     } 
/*      */     
/*   91 */     if (version < 24) {
/*   92 */       if (this.chunkMap17 == null) {
/*   93 */         this.chunkMap17 = PacketPlayOutMapChunk.a(this, true, 65535, version);
/*      */       }
/*      */       
/*   96 */       return this.chunkMap17;
/*      */     } 
/*   98 */     if (this.chunkMap18 == null) {
/*   99 */       this.chunkMap18 = PacketPlayOutMapChunk.a(this, true, 65535, version);
/*      */     }
/*      */     
/*  102 */     return this.chunkMap18;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   private int neighbors = 4096; public org.bukkit.Chunk bukkitChunk; public boolean mustSave;
/*      */   public boolean areNeighborsLoaded(int radius) {
/*      */     int mask;
/*  111 */     switch (radius) {
/*      */       case 2:
/*  113 */         return (this.neighbors == 33554431);
/*      */       case 1:
/*  115 */         mask = 473536;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  120 */         return ((this.neighbors & 0x739C0) == 473536);
/*      */     } 
/*  122 */     throw new UnsupportedOperationException(String.valueOf(radius));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNeighborLoaded(int x, int z) {
/*  127 */     this.neighbors |= 1 << x * 5 + 12 + z;
/*      */   }
/*      */   
/*      */   public void setNeighborUnloaded(int x, int z) {
/*  131 */     this.neighbors &= 1 << x * 5 + 12 + z ^ 0xFFFFFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public Chunk(World world, int i, int j) {
/*  136 */     this.sections = new ChunkSection[16];
/*  137 */     this.v = new byte[256];
/*  138 */     this.b = new int[256];
/*  139 */     this.c = new boolean[256];
/*  140 */     this.tileEntities = new HashMap<Object, Object>();
/*  141 */     this.x = 4096;
/*  142 */     this.entitySlices = new List[16];
/*  143 */     this.world = world;
/*  144 */     this.locX = i;
/*  145 */     this.locZ = j;
/*  146 */     this.heightMap = new int[256];
/*      */     
/*  148 */     for (int k = 0; k < this.entitySlices.length; k++) {
/*  149 */       this.entitySlices[k] = (List)new UnsafeList();
/*      */     }
/*      */     
/*  152 */     Arrays.fill(this.b, -999);
/*  153 */     Arrays.fill(this.v, (byte)-1);
/*      */ 
/*      */     
/*  156 */     if (!(this instanceof EmptyChunk)) {
/*  157 */       this.bukkitChunk = (org.bukkit.Chunk)new CraftChunk(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chunk(World world, Block[] ablock, int i, int j) {
/*  166 */     this(world, i, j);
/*  167 */     int k = ablock.length / 256;
/*  168 */     boolean flag = !world.worldProvider.g;
/*      */     
/*  170 */     for (int l = 0; l < 16; l++) {
/*  171 */       for (int i1 = 0; i1 < 16; i1++) {
/*  172 */         for (int j1 = 0; j1 < k; j1++) {
/*  173 */           Block block = ablock[l << 11 | i1 << 7 | j1];
/*      */           
/*  175 */           if (block != null && block.getMaterial() != Material.AIR) {
/*  176 */             int k1 = j1 >> 4;
/*      */             
/*  178 */             if (this.sections[k1] == null) {
/*  179 */               this.sections[k1] = new ChunkSection(k1 << 4, flag);
/*      */             }
/*      */             
/*  182 */             this.sections[k1].setTypeId(l, j1 & 0xF, i1, block);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Chunk(World world, Block[] ablock, byte[] abyte, int i, int j) {
/*  190 */     this(world, i, j);
/*  191 */     int k = ablock.length / 256;
/*  192 */     boolean flag = !world.worldProvider.g;
/*      */     
/*  194 */     for (int l = 0; l < 16; l++) {
/*  195 */       for (int i1 = 0; i1 < 16; i1++) {
/*  196 */         for (int j1 = 0; j1 < k; j1++) {
/*  197 */           int k1 = l * k * 16 | i1 * k | j1;
/*  198 */           Block block = ablock[k1];
/*      */           
/*  200 */           if (block != null && block != Blocks.AIR) {
/*  201 */             int l1 = j1 >> 4;
/*      */             
/*  203 */             if (this.sections[l1] == null) {
/*  204 */               this.sections[l1] = new ChunkSection(l1 << 4, flag);
/*      */             }
/*      */             
/*  207 */             this.sections[l1].setTypeId(l, j1 & 0xF, i1, block);
/*  208 */             this.sections[l1].setData(l, j1 & 0xF, i1, checkData(block, abyte[k1]));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean a(int i, int j) {
/*  216 */     return (i == this.locX && j == this.locZ);
/*      */   }
/*      */   
/*      */   public int b(int i, int j) {
/*  220 */     return this.heightMap[j << 4 | i];
/*      */   }
/*      */   
/*      */   public int h() {
/*  224 */     for (int i = this.sections.length - 1; i >= 0; i--) {
/*  225 */       if (this.sections[i] != null) {
/*  226 */         return this.sections[i].getYPosition();
/*      */       }
/*      */     } 
/*      */     
/*  230 */     return 0;
/*      */   }
/*      */   
/*      */   public ChunkSection[] getSections() {
/*  234 */     return this.sections;
/*      */   }
/*      */   
/*      */   public void initLighting() {
/*  238 */     int i = h();
/*      */     
/*  240 */     this.r = Integer.MAX_VALUE;
/*      */     
/*  242 */     for (int j = 0; j < 16; j++) {
/*  243 */       int k = 0;
/*      */       
/*  245 */       while (k < 16) {
/*  246 */         this.b[j + (k << 4)] = -999;
/*  247 */         int l = i + 16 - 1;
/*      */ 
/*      */         
/*  250 */         while (l > 0) {
/*  251 */           if (b(j, l - 1, k) == 0) {
/*  252 */             l--;
/*      */             
/*      */             continue;
/*      */           } 
/*  256 */           this.heightMap[k << 4 | j] = l;
/*  257 */           if (l < this.r) {
/*  258 */             this.r = l;
/*      */           }
/*      */         } 
/*      */         
/*  262 */         if (!this.world.worldProvider.g) {
/*  263 */           l = 15;
/*  264 */           int i1 = i + 16 - 1;
/*      */           
/*      */           do {
/*  267 */             int j1 = b(j, i1, k);
/*      */             
/*  269 */             if (j1 == 0 && l != 15) {
/*  270 */               j1 = 1;
/*      */             }
/*      */             
/*  273 */             l -= j1;
/*  274 */             if (l <= 0)
/*  275 */               continue;  ChunkSection chunksection = this.sections[i1 >> 4];
/*      */             
/*  277 */             if (chunksection == null)
/*  278 */               continue;  chunksection.setSkyLight(j, i1 & 0xF, k, l);
/*  279 */             this.world.m((this.locX << 4) + j, i1, (this.locZ << 4) + k);
/*      */ 
/*      */ 
/*      */             
/*  283 */             --i1;
/*  284 */           } while (i1 > 0 && l > 0);
/*      */         } 
/*      */         
/*  287 */         k++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  293 */     this.n = true;
/*      */   }
/*      */   
/*      */   private void e(int i, int j) {
/*  297 */     this.c[i + j * 16] = true;
/*  298 */     this.w = true;
/*      */   }
/*      */   
/*      */   private void c(boolean flag) {
/*  302 */     this.world.methodProfiler.a("recheckGaps");
/*  303 */     if (this.world.areChunksLoaded(this.locX * 16 + 8, 0, this.locZ * 16 + 8, 16)) {
/*  304 */       for (int i = 0; i < 16; i++) {
/*  305 */         for (int j = 0; j < 16; j++) {
/*  306 */           if (this.c[i + j * 16]) {
/*  307 */             this.c[i + j * 16] = false;
/*  308 */             int k = b(i, j);
/*  309 */             int l = this.locX * 16 + i;
/*  310 */             int i1 = this.locZ * 16 + j;
/*  311 */             int j1 = this.world.g(l - 1, i1);
/*  312 */             int k1 = this.world.g(l + 1, i1);
/*  313 */             int l1 = this.world.g(l, i1 - 1);
/*  314 */             int i2 = this.world.g(l, i1 + 1);
/*      */             
/*  316 */             if (k1 < j1) {
/*  317 */               j1 = k1;
/*      */             }
/*      */             
/*  320 */             if (l1 < j1) {
/*  321 */               j1 = l1;
/*      */             }
/*      */             
/*  324 */             if (i2 < j1) {
/*  325 */               j1 = i2;
/*      */             }
/*      */             
/*  328 */             g(l, i1, j1);
/*  329 */             g(l - 1, i1, k);
/*  330 */             g(l + 1, i1, k);
/*  331 */             g(l, i1 - 1, k);
/*  332 */             g(l, i1 + 1, k);
/*  333 */             if (flag) {
/*  334 */               this.world.methodProfiler.b();
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  341 */       this.w = false;
/*      */     } 
/*      */     
/*  344 */     this.world.methodProfiler.b();
/*      */   }
/*      */   
/*      */   private void g(int i, int j, int k) {
/*  348 */     int l = this.world.getHighestBlockYAt(i, j);
/*      */     
/*  350 */     if (l > k) {
/*  351 */       c(i, j, k, l + 1);
/*  352 */     } else if (l < k) {
/*  353 */       c(i, j, l, k + 1);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void c(int i, int j, int k, int l) {
/*  358 */     if (l > k && this.world.areChunksLoaded(i, 0, j, 16)) {
/*  359 */       for (int i1 = k; i1 < l; i1++) {
/*  360 */         this.world.updateLight(EnumSkyBlock.SKY, i, i1, j);
/*      */       }
/*      */       
/*  363 */       this.n = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void h(int i, int j, int k) {
/*  368 */     int l = this.heightMap[k << 4 | i] & 0xFF;
/*  369 */     int i1 = l;
/*      */     
/*  371 */     if (j > l) {
/*  372 */       i1 = j;
/*      */     }
/*      */     
/*  375 */     while (i1 > 0 && b(i, i1 - 1, k) == 0) {
/*  376 */       i1--;
/*      */     }
/*      */     
/*  379 */     if (i1 != l) {
/*  380 */       this.world.b(i + this.locX * 16, k + this.locZ * 16, i1, l);
/*  381 */       this.heightMap[k << 4 | i] = i1;
/*  382 */       int j1 = this.locX * 16 + i;
/*  383 */       int k1 = this.locZ * 16 + k;
/*      */ 
/*      */ 
/*      */       
/*  387 */       if (!this.world.worldProvider.g) {
/*      */ 
/*      */         
/*  390 */         if (i1 < l) {
/*  391 */           for (int n = i1; n < l; n++) {
/*  392 */             ChunkSection chunksection = this.sections[n >> 4];
/*  393 */             if (chunksection != null) {
/*  394 */               chunksection.setSkyLight(i, n & 0xF, k, 15);
/*  395 */               this.world.m((this.locX << 4) + i, n, (this.locZ << 4) + k);
/*      */             } 
/*      */           } 
/*      */         } else {
/*  399 */           for (int n = l; n < i1; n++) {
/*  400 */             ChunkSection chunksection = this.sections[n >> 4];
/*  401 */             if (chunksection != null) {
/*  402 */               chunksection.setSkyLight(i, n & 0xF, k, 0);
/*  403 */               this.world.m((this.locX << 4) + i, n, (this.locZ << 4) + k);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  408 */         int m = 15;
/*      */         
/*  410 */         while (i1 > 0 && m > 0) {
/*  411 */           i1--;
/*  412 */           int n = b(i, i1, k);
/*  413 */           if (n == 0) {
/*  414 */             n = 1;
/*      */           }
/*      */           
/*  417 */           m -= n;
/*  418 */           if (m < 0) {
/*  419 */             m = 0;
/*      */           }
/*      */           
/*  422 */           ChunkSection chunksection1 = this.sections[i1 >> 4];
/*      */           
/*  424 */           if (chunksection1 != null) {
/*  425 */             chunksection1.setSkyLight(i, i1 & 0xF, k, m);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  430 */       int l1 = this.heightMap[k << 4 | i];
/*  431 */       int i2 = l;
/*  432 */       int j2 = l1;
/*      */       
/*  434 */       if (l1 < l) {
/*  435 */         i2 = l1;
/*  436 */         j2 = l;
/*      */       } 
/*      */       
/*  439 */       if (l1 < this.r) {
/*  440 */         this.r = l1;
/*      */       }
/*      */       
/*  443 */       if (!this.world.worldProvider.g) {
/*  444 */         c(j1 - 1, k1, i2, j2);
/*  445 */         c(j1 + 1, k1, i2, j2);
/*  446 */         c(j1, k1 - 1, i2, j2);
/*  447 */         c(j1, k1 + 1, i2, j2);
/*  448 */         c(j1, k1, i2, j2);
/*      */       } 
/*      */       
/*  451 */       this.n = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public int b(int i, int j, int k) {
/*  456 */     return getType(i, j, k).k();
/*      */   }
/*      */   
/*      */   public Block getType(int i, int j, int k) {
/*  460 */     Block block = Blocks.AIR;
/*      */     
/*  462 */     if (j >> 4 < this.sections.length) {
/*  463 */       ChunkSection chunksection = this.sections[j >> 4];
/*      */       
/*  465 */       if (chunksection != null) {
/*      */         try {
/*  467 */           block = chunksection.getTypeId(i, j & 0xF, k);
/*  468 */         } catch (Throwable throwable) {
/*  469 */           CrashReport crashreport = CrashReport.a(throwable, "Getting block");
/*  470 */           CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being got");
/*      */           
/*  472 */           crashreportsystemdetails.a("Location", new CrashReportLocation(this, i, j, k));
/*  473 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  478 */     return block;
/*      */   }
/*      */   
/*      */   public int getData(int i, int j, int k) {
/*  482 */     if (j >> 4 >= this.sections.length) {
/*  483 */       return 0;
/*      */     }
/*  485 */     ChunkSection chunksection = this.sections[j >> 4];
/*      */     
/*  487 */     return (chunksection != null) ? chunksection.getData(i, j & 0xF, k) : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int checkData(Block block, int data) {
/*  494 */     if (block == Blocks.DOUBLE_PLANT)
/*      */     {
/*  496 */       return (data < 6 || data >= 8) ? data : 0;
/*      */     }
/*  498 */     return data;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean a(int i, int j, int k, Block block, int l) {
/*  503 */     int i1 = k << 4 | i;
/*      */     
/*  505 */     if (j >= this.b[i1] - 1) {
/*  506 */       this.b[i1] = -999;
/*      */     }
/*      */     
/*  509 */     int j1 = this.heightMap[i1];
/*  510 */     Block block1 = getType(i, j, k);
/*  511 */     int k1 = getData(i, j, k);
/*      */     
/*  513 */     if (block1 == block && k1 == l) {
/*  514 */       return false;
/*      */     }
/*  516 */     ChunkSection chunksection = this.sections[j >> 4];
/*  517 */     boolean flag = false;
/*      */     
/*  519 */     if (chunksection == null) {
/*  520 */       if (block == Blocks.AIR) {
/*  521 */         return false;
/*      */       }
/*      */       
/*  524 */       chunksection = this.sections[j >> 4] = new ChunkSection(j >> 4 << 4, !this.world.worldProvider.g);
/*  525 */       flag = (j >= j1);
/*      */     } 
/*      */     
/*  528 */     int l1 = this.locX * 16 + i;
/*  529 */     int i2 = this.locZ * 16 + k;
/*      */     
/*  531 */     if (!this.world.isStatic) {
/*  532 */       block1.f(this.world, l1, j, i2, k1);
/*      */     }
/*      */ 
/*      */     
/*  536 */     if (!(block1 instanceof IContainer)) {
/*  537 */       chunksection.setTypeId(i, j & 0xF, k, block);
/*      */     }
/*      */ 
/*      */     
/*  541 */     if (!this.world.isStatic) {
/*  542 */       block1.remove(this.world, l1, j, i2, block1, k1);
/*  543 */     } else if (block1 instanceof IContainer && block1 != block) {
/*  544 */       this.world.p(l1, j, i2);
/*      */     } 
/*      */ 
/*      */     
/*  548 */     if (block1 instanceof IContainer) {
/*  549 */       chunksection.setTypeId(i, j & 0xF, k, block);
/*      */     }
/*      */ 
/*      */     
/*  553 */     if (chunksection.getTypeId(i, j & 0xF, k) != block) {
/*  554 */       return false;
/*      */     }
/*  556 */     chunksection.setData(i, j & 0xF, k, checkData(block, l));
/*  557 */     if (flag) {
/*  558 */       initLighting();
/*      */     } else {
/*  560 */       int j2 = block.k();
/*  561 */       int k2 = block1.k();
/*      */       
/*  563 */       if (j2 > 0) {
/*  564 */         if (j >= j1) {
/*  565 */           h(i, j + 1, k);
/*      */         }
/*  567 */       } else if (j == j1 - 1) {
/*  568 */         h(i, j, k);
/*      */       } 
/*      */       
/*  571 */       if (j2 != k2 && (j2 < k2 || getBrightness(EnumSkyBlock.SKY, i, j, k) > 0 || getBrightness(EnumSkyBlock.BLOCK, i, j, k) > 0)) {
/*  572 */         e(i, k);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  578 */     if (block1 instanceof IContainer) {
/*  579 */       TileEntity tileentity = e(i, j, k);
/*  580 */       if (tileentity != null) {
/*  581 */         tileentity.u();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  586 */     if (!this.world.isStatic && (!this.world.captureBlockStates || block instanceof BlockContainer)) {
/*  587 */       block.onPlace(this.world, l1, j, i2);
/*      */     }
/*      */     
/*  590 */     if (block instanceof IContainer) {
/*      */       
/*  592 */       TileEntity tileentity = e(i, j, k);
/*  593 */       if (tileentity == null) {
/*  594 */         tileentity = ((IContainer)block).a(this.world, l);
/*  595 */         this.world.setTileEntity(l1, j, i2, tileentity);
/*      */       } 
/*      */       
/*  598 */       if (tileentity != null) {
/*  599 */         tileentity.u();
/*      */       }
/*      */     } 
/*      */     
/*  603 */     this.n = true;
/*  604 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean a(int i, int j, int k, int l) {
/*  610 */     ChunkSection chunksection = this.sections[j >> 4];
/*      */     
/*  612 */     if (chunksection == null) {
/*  613 */       return false;
/*      */     }
/*  615 */     int i1 = chunksection.getData(i, j & 0xF, k);
/*      */     
/*  617 */     if (i1 == l) {
/*  618 */       return false;
/*      */     }
/*  620 */     this.n = true;
/*  621 */     Block block = chunksection.getTypeId(i, j & 0xF, k);
/*  622 */     chunksection.setData(i, j & 0xF, k, checkData(block, l));
/*  623 */     if (block instanceof IContainer) {
/*  624 */       TileEntity tileentity = e(i, j, k);
/*      */       
/*  626 */       if (tileentity != null) {
/*  627 */         tileentity.u();
/*  628 */         tileentity.g = l;
/*      */       } 
/*      */     } 
/*      */     
/*  632 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBrightness(EnumSkyBlock enumskyblock, int i, int j, int k) {
/*  638 */     ChunkSection chunksection = this.sections[j >> 4];
/*      */     
/*  640 */     return (chunksection == null) ? (d(i, j, k) ? enumskyblock.c : 0) : ((enumskyblock == EnumSkyBlock.SKY) ? (this.world.worldProvider.g ? 0 : chunksection.getSkyLight(i, j & 0xF, k)) : ((enumskyblock == EnumSkyBlock.BLOCK) ? chunksection.getEmittedLight(i, j & 0xF, k) : enumskyblock.c));
/*      */   }
/*      */   
/*      */   public void a(EnumSkyBlock enumskyblock, int i, int j, int k, int l) {
/*  644 */     ChunkSection chunksection = this.sections[j >> 4];
/*      */     
/*  646 */     if (chunksection == null) {
/*  647 */       chunksection = this.sections[j >> 4] = new ChunkSection(j >> 4 << 4, !this.world.worldProvider.g);
/*  648 */       initLighting();
/*      */     } 
/*      */     
/*  651 */     this.n = true;
/*  652 */     if (enumskyblock == EnumSkyBlock.SKY) {
/*  653 */       if (!this.world.worldProvider.g) {
/*  654 */         chunksection.setSkyLight(i, j & 0xF, k, l);
/*      */       }
/*  656 */     } else if (enumskyblock == EnumSkyBlock.BLOCK) {
/*  657 */       chunksection.setEmittedLight(i, j & 0xF, k, l);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int b(int i, int j, int k, int l) {
/*  662 */     ChunkSection chunksection = this.sections[j >> 4];
/*      */     
/*  664 */     if (chunksection == null) {
/*  665 */       return (!this.world.worldProvider.g && l < EnumSkyBlock.SKY.c) ? (EnumSkyBlock.SKY.c - l) : 0;
/*      */     }
/*  667 */     int i1 = this.world.worldProvider.g ? 0 : chunksection.getSkyLight(i, j & 0xF, k);
/*      */     
/*  669 */     if (i1 > 0) {
/*  670 */       a = true;
/*      */     }
/*      */     
/*  673 */     i1 -= l;
/*  674 */     int j1 = chunksection.getEmittedLight(i, j & 0xF, k);
/*      */     
/*  676 */     if (j1 > i1) {
/*  677 */       i1 = j1;
/*      */     }
/*      */     
/*  680 */     return i1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(Entity entity) {
/*  685 */     this.o = true;
/*  686 */     int i = MathHelper.floor(entity.locX / 16.0D);
/*  687 */     int j = MathHelper.floor(entity.locZ / 16.0D);
/*      */     
/*  689 */     if (i != this.locX || j != this.locZ) {
/*      */       
/*  691 */       Bukkit.getLogger().warning("Wrong location for " + entity + " in world '" + this.world.getWorld().getName() + "'!");
/*      */ 
/*      */       
/*  694 */       Bukkit.getLogger().warning("Entity is at " + entity.locX + "," + entity.locZ + " (chunk " + i + "," + j + ") but was stored in chunk " + this.locX + "," + this.locZ);
/*      */     } 
/*      */ 
/*      */     
/*  698 */     int k = MathHelper.floor(entity.locY / 16.0D);
/*      */     
/*  700 */     if (k < 0) {
/*  701 */       k = 0;
/*      */     }
/*      */     
/*  704 */     if (k >= this.entitySlices.length) {
/*  705 */       k = this.entitySlices.length - 1;
/*      */     }
/*      */     
/*  708 */     entity.ag = true;
/*  709 */     entity.ah = this.locX;
/*  710 */     entity.ai = k;
/*  711 */     entity.aj = this.locZ;
/*  712 */     this.entitySlices[k].add(entity);
/*      */ 
/*      */     
/*  715 */     if (entity instanceof EntityInsentient) {
/*  716 */       EntityInsentient entityinsentient = (EntityInsentient)entity;
/*  717 */       if (entityinsentient.isTypeNotPersistent() && entityinsentient.isPersistent()) {
/*      */         return;
/*      */       }
/*      */     } 
/*  721 */     for (EnumCreatureType creatureType : EnumCreatureType.values()) {
/*      */       
/*  723 */       if (creatureType.a().isAssignableFrom(entity.getClass()))
/*      */       {
/*  725 */         this.entityCount.adjustOrPutValue(creatureType.a(), 1, 1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void b(Entity entity) {
/*  732 */     a(entity, entity.ai);
/*      */   }
/*      */   
/*      */   public void a(Entity entity, int i) {
/*  736 */     if (i < 0) {
/*  737 */       i = 0;
/*      */     }
/*      */     
/*  740 */     if (i >= this.entitySlices.length) {
/*  741 */       i = this.entitySlices.length - 1;
/*      */     }
/*      */     
/*  744 */     this.entitySlices[i].remove(entity);
/*      */ 
/*      */     
/*  747 */     if (entity instanceof EntityInsentient) {
/*  748 */       EntityInsentient entityinsentient = (EntityInsentient)entity;
/*  749 */       if (entityinsentient.isTypeNotPersistent() && entityinsentient.isPersistent()) {
/*      */         return;
/*      */       }
/*      */     } 
/*  753 */     for (EnumCreatureType creatureType : EnumCreatureType.values()) {
/*      */       
/*  755 */       if (creatureType.a().isAssignableFrom(entity.getClass()))
/*      */       {
/*  757 */         this.entityCount.adjustValue(creatureType.a(), -1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean d(int i, int j, int k) {
/*  764 */     return (j >= this.heightMap[k << 4 | i]);
/*      */   }
/*      */   
/*      */   public TileEntity e(int i, int j, int k) {
/*  768 */     ChunkPosition chunkposition = new ChunkPosition(i, j, k);
/*  769 */     TileEntity tileentity = (TileEntity)this.tileEntities.get(chunkposition);
/*      */     
/*  771 */     if (tileentity == null) {
/*  772 */       Block block = getType(i, j, k);
/*      */       
/*  774 */       if (!block.isTileEntity()) {
/*  775 */         return null;
/*      */       }
/*      */       
/*  778 */       tileentity = ((IContainer)block).a(this.world, getData(i, j, k));
/*  779 */       this.world.setTileEntity(this.locX * 16 + i, j, this.locZ * 16 + k, tileentity);
/*      */     } 
/*      */     
/*  782 */     if (tileentity != null && tileentity.r()) {
/*  783 */       this.tileEntities.remove(chunkposition);
/*  784 */       return null;
/*      */     } 
/*  786 */     return tileentity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(TileEntity tileentity) {
/*  791 */     int i = tileentity.x - this.locX * 16;
/*  792 */     int j = tileentity.y;
/*  793 */     int k = tileentity.z - this.locZ * 16;
/*      */     
/*  795 */     a(i, j, k, tileentity);
/*  796 */     if (this.d) {
/*  797 */       this.world.tileEntityList.add(tileentity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(int i, int j, int k, TileEntity tileentity) {
/*  802 */     ChunkPosition chunkposition = new ChunkPosition(i, j, k);
/*      */     
/*  804 */     tileentity.a(this.world);
/*  805 */     tileentity.x = this.locX * 16 + i;
/*  806 */     tileentity.y = j;
/*  807 */     tileentity.z = this.locZ * 16 + k;
/*  808 */     if (getType(i, j, k) instanceof IContainer) {
/*  809 */       if (this.tileEntities.containsKey(chunkposition)) {
/*  810 */         ((TileEntity)this.tileEntities.get(chunkposition)).s();
/*      */       }
/*      */       
/*  813 */       tileentity.t();
/*  814 */       this.tileEntities.put(chunkposition, tileentity);
/*      */       
/*  816 */       if (this.world.spigotConfig.altHopperTicking) {
/*  817 */         this.world.triggerHoppersList.add(tileentity);
/*      */       
/*      */       }
/*      */     }
/*  821 */     else if (this.world.paperSpigotConfig.removeInvalidMobSpawnerTEs && tileentity instanceof TileEntityMobSpawner && CraftMagicNumbers.getMaterial(getType(i, j, k)) != Material.MOB_SPAWNER) {
/*      */       
/*  823 */       this.tileEntities.remove(chunkposition);
/*      */     }
/*      */     else {
/*      */       
/*  827 */       System.out.println("Attempted to place a tile entity (" + tileentity + ") at " + tileentity.x + "," + tileentity.y + "," + tileentity.z + " (" + CraftMagicNumbers.getMaterial(getType(i, j, k)) + ") where there was no entity tile!");
/*      */       
/*  829 */       System.out.println("Chunk coordinates: " + (this.locX * 16) + "," + (this.locZ * 16));
/*  830 */       (new Exception()).printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void f(int i, int j, int k) {
/*  836 */     ChunkPosition chunkposition = new ChunkPosition(i, j, k);
/*      */     
/*  838 */     if (this.d) {
/*  839 */       TileEntity tileentity = (TileEntity)this.tileEntities.remove(chunkposition);
/*      */       
/*  841 */       if (tileentity != null) {
/*  842 */         tileentity.s();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addEntities() {
/*  848 */     this.d = true;
/*  849 */     this.world.a(this.tileEntities.values());
/*      */     
/*  851 */     for (int i = 0; i < this.entitySlices.length; i++) {
/*  852 */       Iterator<?> iterator = this.entitySlices[i].iterator();
/*      */       
/*  854 */       while (iterator.hasNext()) {
/*  855 */         Entity entity = (Entity)iterator.next();
/*      */         
/*  857 */         entity.X();
/*      */       } 
/*      */       
/*  860 */       this.world.a(this.entitySlices[i]);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeEntities() {
/*  865 */     this.d = false;
/*  866 */     Iterator<TileEntity> iterator = this.tileEntities.values().iterator();
/*      */     
/*  868 */     while (iterator.hasNext()) {
/*  869 */       TileEntity tileentity = iterator.next();
/*      */       
/*  871 */       if (tileentity instanceof IInventory)
/*      */       {
/*  873 */         for (HumanEntity h : new ArrayList(((IInventory)tileentity).getViewers())) {
/*      */           
/*  875 */           if (h instanceof CraftHumanEntity)
/*      */           {
/*  877 */             ((CraftHumanEntity)h).getHandle().closeInventory();
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  883 */       this.world.a(tileentity);
/*      */     } 
/*      */     
/*  886 */     for (int i = 0; i < this.entitySlices.length; i++) {
/*      */       
/*  888 */       Iterator<Object> iter = this.entitySlices[i].iterator();
/*  889 */       while (iter.hasNext()) {
/*  890 */         Entity entity = (Entity)iter.next();
/*      */         
/*  892 */         if (entity instanceof IInventory)
/*      */         {
/*  894 */           for (HumanEntity h : new ArrayList(((IInventory)entity).getViewers())) {
/*      */             
/*  896 */             if (h instanceof CraftHumanEntity)
/*      */             {
/*  898 */               ((CraftHumanEntity)h).getHandle().closeInventory();
/*      */             }
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  906 */         if (entity instanceof EntityPlayer) {
/*  907 */           iter.remove();
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  912 */       this.world.b(this.entitySlices[i]);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void e() {
/*  917 */     this.n = true;
/*      */   }
/*      */   
/*      */   public void a(Entity entity, AxisAlignedBB axisalignedbb, List<Entity> list, IEntitySelector ientityselector) {
/*  921 */     int i = MathHelper.floor((axisalignedbb.b - 2.0D) / 16.0D);
/*  922 */     int j = MathHelper.floor((axisalignedbb.e + 2.0D) / 16.0D);
/*      */     
/*  924 */     i = MathHelper.a(i, 0, this.entitySlices.length - 1);
/*  925 */     j = MathHelper.a(j, 0, this.entitySlices.length - 1);
/*      */     
/*  927 */     for (int k = i; k <= j; k++) {
/*  928 */       List<Entity> list1 = this.entitySlices[k];
/*      */       
/*  930 */       for (int l = 0; l < list1.size(); l++) {
/*  931 */         Entity entity1 = list1.get(l);
/*      */         
/*  933 */         if (entity1 != entity && entity1.boundingBox.b(axisalignedbb) && (ientityselector == null || ientityselector.a(entity1))) {
/*  934 */           list.add(entity1);
/*  935 */           Entity[] aentity = entity1.at();
/*      */           
/*  937 */           if (aentity != null) {
/*  938 */             for (int i1 = 0; i1 < aentity.length; i1++) {
/*  939 */               entity1 = aentity[i1];
/*  940 */               if (entity1 != entity && entity1.boundingBox.b(axisalignedbb) && (ientityselector == null || ientityselector.a(entity1))) {
/*  941 */                 list.add(entity1);
/*      */               }
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(Class oclass, AxisAlignedBB axisalignedbb, List<Entity> list, IEntitySelector ientityselector) {
/*  951 */     int i = MathHelper.floor((axisalignedbb.b - 2.0D) / 16.0D);
/*  952 */     int j = MathHelper.floor((axisalignedbb.e + 2.0D) / 16.0D);
/*      */     
/*  954 */     i = MathHelper.a(i, 0, this.entitySlices.length - 1);
/*  955 */     j = MathHelper.a(j, 0, this.entitySlices.length - 1);
/*      */     
/*  957 */     for (int k = i; k <= j; k++) {
/*  958 */       List<Entity> list1 = this.entitySlices[k];
/*      */       
/*  960 */       for (int l = 0; l < list1.size(); l++) {
/*  961 */         Entity entity = list1.get(l);
/*      */         
/*  963 */         if (oclass.isAssignableFrom(entity.getClass()) && entity.boundingBox.b(axisalignedbb) && (ientityselector == null || ientityselector.a(entity))) {
/*  964 */           list.add(entity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean a(boolean flag) {
/*  971 */     if (flag) {
/*  972 */       if ((this.o && this.world.getTime() != this.lastSaved) || this.n) {
/*  973 */         return true;
/*      */       }
/*  975 */     } else if (this.o && this.world.getTime() >= this.lastSaved + ((MinecraftServer.getServer()).autosavePeriod * 4)) {
/*  976 */       return true;
/*      */     } 
/*      */     
/*  979 */     return this.n;
/*      */   }
/*      */   
/*      */   public Random a(long i) {
/*  983 */     return new Random(this.world.getSeed() + (this.locX * this.locX * 4987142) + (this.locX * 5947611) + (this.locZ * this.locZ) * 4392871L + (this.locZ * 389711) ^ i);
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  987 */     return false;
/*      */   }
/*      */   
/*      */   public void loadNearby(IChunkProvider ichunkprovider, IChunkProvider ichunkprovider1, int i, int j) {
/*  991 */     this.world.timings.syncChunkLoadPostTimer.startTiming();
/*  992 */     if (!this.done && ichunkprovider.isChunkLoaded(i + 1, j + 1) && ichunkprovider.isChunkLoaded(i, j + 1) && ichunkprovider.isChunkLoaded(i + 1, j)) {
/*  993 */       ichunkprovider.getChunkAt(ichunkprovider1, i, j);
/*      */     }
/*      */     
/*  996 */     if (ichunkprovider.isChunkLoaded(i - 1, j) && !(ichunkprovider.getOrCreateChunk(i - 1, j)).done && ichunkprovider.isChunkLoaded(i - 1, j + 1) && ichunkprovider.isChunkLoaded(i, j + 1) && ichunkprovider.isChunkLoaded(i - 1, j + 1)) {
/*  997 */       ichunkprovider.getChunkAt(ichunkprovider1, i - 1, j);
/*      */     }
/*      */     
/* 1000 */     if (ichunkprovider.isChunkLoaded(i, j - 1) && !(ichunkprovider.getOrCreateChunk(i, j - 1)).done && ichunkprovider.isChunkLoaded(i + 1, j - 1) && ichunkprovider.isChunkLoaded(i + 1, j - 1) && ichunkprovider.isChunkLoaded(i + 1, j)) {
/* 1001 */       ichunkprovider.getChunkAt(ichunkprovider1, i, j - 1);
/*      */     }
/*      */     
/* 1004 */     if (ichunkprovider.isChunkLoaded(i - 1, j - 1) && !(ichunkprovider.getOrCreateChunk(i - 1, j - 1)).done && ichunkprovider.isChunkLoaded(i, j - 1) && ichunkprovider.isChunkLoaded(i - 1, j)) {
/* 1005 */       ichunkprovider.getChunkAt(ichunkprovider1, i - 1, j - 1);
/*      */     }
/* 1007 */     this.world.timings.syncChunkLoadPostTimer.stopTiming();
/*      */   }
/*      */   
/*      */   public int d(int i, int j) {
/* 1011 */     int k = i | j << 4;
/* 1012 */     int l = this.b[k];
/*      */     
/* 1014 */     if (l == -999) {
/* 1015 */       int i1 = h() + 15;
/*      */       
/* 1017 */       l = -1;
/*      */       
/* 1019 */       while (i1 > 0 && l == -1) {
/* 1020 */         Block block = getType(i, i1, j);
/* 1021 */         Material material = block.getMaterial();
/*      */         
/* 1023 */         if (!material.isSolid() && !material.isLiquid()) {
/* 1024 */           i1--; continue;
/*      */         } 
/* 1026 */         l = i1 + 1;
/*      */       } 
/*      */ 
/*      */       
/* 1030 */       this.b[k] = l;
/*      */     } 
/*      */     
/* 1033 */     return l;
/*      */   }
/*      */   
/*      */   public void b(boolean flag) {
/* 1037 */     if (this.w && !this.world.worldProvider.g && !flag) {
/* 1038 */       recheckGaps(this.world.isStatic);
/*      */     }
/*      */     
/* 1041 */     this.m = true;
/* 1042 */     if (!this.lit && this.done && this.world.spigotConfig.randomLightUpdates) {
/* 1043 */       p();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void recheckGaps(final boolean isStatic) {
/* 1051 */     if (!this.world.paperSpigotConfig.useAsyncLighting) {
/* 1052 */       c(isStatic);
/*      */       
/*      */       return;
/*      */     } 
/* 1056 */     this.world.lightingExecutor.submit(new Runnable()
/*      */         {
/*      */           public void run() {
/* 1059 */             Chunk.this.c(isStatic);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReady() {
/* 1072 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChunkCoordIntPair l() {
/* 1077 */     return new ChunkCoordIntPair(this.locX, this.locZ);
/*      */   }
/*      */   
/*      */   public boolean c(int i, int j) {
/* 1081 */     if (i < 0) {
/* 1082 */       i = 0;
/*      */     }
/*      */     
/* 1085 */     if (j >= 256) {
/* 1086 */       j = 255;
/*      */     }
/*      */     
/* 1089 */     for (int k = i; k <= j; k += 16) {
/* 1090 */       ChunkSection chunksection = this.sections[k >> 4];
/*      */       
/* 1092 */       if (chunksection != null && !chunksection.isEmpty()) {
/* 1093 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1097 */     return true;
/*      */   }
/*      */   
/*      */   public void a(ChunkSection[] achunksection) {
/* 1101 */     this.sections = achunksection;
/*      */   }
/*      */   
/*      */   public BiomeBase getBiome(int i, int j, WorldChunkManager worldchunkmanager) {
/* 1105 */     int k = this.v[j << 4 | i] & 0xFF;
/*      */     
/* 1107 */     if (k == 255) {
/* 1108 */       BiomeBase biomebase = worldchunkmanager.getBiome((this.locX << 4) + i, (this.locZ << 4) + j);
/*      */       
/* 1110 */       k = biomebase.id;
/* 1111 */       this.v[j << 4 | i] = (byte)(k & 0xFF);
/*      */     } 
/*      */     
/* 1114 */     return (BiomeBase.getBiome(k) == null) ? BiomeBase.PLAINS : BiomeBase.getBiome(k);
/*      */   }
/*      */   
/*      */   public byte[] m() {
/* 1118 */     return this.v;
/*      */   }
/*      */   
/*      */   public void a(byte[] abyte) {
/* 1122 */     this.v = abyte;
/*      */   }
/*      */   
/*      */   public void n() {
/* 1126 */     this.x = 0;
/*      */   }
/*      */   
/*      */   public void o() {
/* 1130 */     for (int i = 0; i < 8; i++) {
/* 1131 */       if (this.x >= 4096) {
/*      */         return;
/*      */       }
/*      */       
/* 1135 */       int j = this.x % 16;
/* 1136 */       int k = this.x / 16 % 16;
/* 1137 */       int l = this.x / 256;
/*      */       
/* 1139 */       this.x++;
/* 1140 */       int i1 = (this.locX << 4) + k;
/* 1141 */       int j1 = (this.locZ << 4) + l;
/*      */       
/* 1143 */       for (int k1 = 0; k1 < 16; k1++) {
/* 1144 */         int l1 = (j << 4) + k1;
/*      */         
/* 1146 */         if ((this.sections[j] == null && (k1 == 0 || k1 == 15 || k == 0 || k == 15 || l == 0 || l == 15)) || (this.sections[j] != null && this.sections[j].getTypeId(k, k1, l).getMaterial() == Material.AIR)) {
/* 1147 */           if (this.world.getType(i1, l1 - 1, j1).m() > 0) {
/* 1148 */             this.world.t(i1, l1 - 1, j1);
/*      */           }
/*      */           
/* 1151 */           if (this.world.getType(i1, l1 + 1, j1).m() > 0) {
/* 1152 */             this.world.t(i1, l1 + 1, j1);
/*      */           }
/*      */           
/* 1155 */           if (this.world.getType(i1 - 1, l1, j1).m() > 0) {
/* 1156 */             this.world.t(i1 - 1, l1, j1);
/*      */           }
/*      */           
/* 1159 */           if (this.world.getType(i1 + 1, l1, j1).m() > 0) {
/* 1160 */             this.world.t(i1 + 1, l1, j1);
/*      */           }
/*      */           
/* 1163 */           if (this.world.getType(i1, l1, j1 - 1).m() > 0) {
/* 1164 */             this.world.t(i1, l1, j1 - 1);
/*      */           }
/*      */           
/* 1167 */           if (this.world.getType(i1, l1, j1 + 1).m() > 0) {
/* 1168 */             this.world.t(i1, l1, j1 + 1);
/*      */           }
/*      */           
/* 1171 */           this.world.t(i1, l1, j1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void p() {
/* 1178 */     this.done = true;
/* 1179 */     this.lit = true;
/* 1180 */     if (!this.world.worldProvider.g) {
/* 1181 */       if (this.world.b(this.locX * 16 - 1, 0, this.locZ * 16 - 1, this.locX * 16 + 1, 63, this.locZ * 16 + 1)) {
/* 1182 */         for (int i = 0; i < 16; i++) {
/* 1183 */           for (int j = 0; j < 16; j++) {
/* 1184 */             if (!f(i, j)) {
/* 1185 */               this.lit = false;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1191 */         if (this.lit) {
/* 1192 */           Chunk chunk = this.world.getChunkAtWorldCoords(this.locX * 16 - 1, this.locZ * 16);
/*      */           
/* 1194 */           chunk.a(3);
/* 1195 */           chunk = this.world.getChunkAtWorldCoords(this.locX * 16 + 16, this.locZ * 16);
/* 1196 */           chunk.a(1);
/* 1197 */           chunk = this.world.getChunkAtWorldCoords(this.locX * 16, this.locZ * 16 - 1);
/* 1198 */           chunk.a(0);
/* 1199 */           chunk = this.world.getChunkAtWorldCoords(this.locX * 16, this.locZ * 16 + 16);
/* 1200 */           chunk.a(2);
/*      */         } 
/*      */       } else {
/* 1203 */         this.lit = false;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void a(int i) {
/* 1209 */     if (this.done)
/*      */     {
/*      */       
/* 1212 */       if (i == 3) {
/* 1213 */         for (int j = 0; j < 16; j++) {
/* 1214 */           f(15, j);
/*      */         }
/* 1216 */       } else if (i == 1) {
/* 1217 */         for (int j = 0; j < 16; j++) {
/* 1218 */           f(0, j);
/*      */         }
/* 1220 */       } else if (i == 0) {
/* 1221 */         for (int j = 0; j < 16; j++) {
/* 1222 */           f(j, 15);
/*      */         }
/* 1224 */       } else if (i == 2) {
/* 1225 */         for (int j = 0; j < 16; j++) {
/* 1226 */           f(j, 0);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean f(int i, int j) {
/* 1233 */     int k = h();
/* 1234 */     boolean flag = false;
/* 1235 */     boolean flag1 = false;
/*      */     
/*      */     int l;
/*      */     
/* 1239 */     for (l = k + 16 - 1; l > 63 || (l > 0 && !flag1); l--) {
/* 1240 */       int i1 = b(i, l, j);
/*      */       
/* 1242 */       if (i1 == 255 && l < 63) {
/* 1243 */         flag1 = true;
/*      */       }
/*      */       
/* 1246 */       if (!flag && i1 > 0) {
/* 1247 */         flag = true;
/* 1248 */       } else if (flag && i1 == 0 && !this.world.t(this.locX * 16 + i, l, this.locZ * 16 + j)) {
/* 1249 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/* 1253 */     for (; l > 0; l--) {
/* 1254 */       if (getType(i, l, j).m() > 0) {
/* 1255 */         this.world.t(this.locX * 16 + i, l, this.locZ * 16 + j);
/*      */       }
/*      */     } 
/*      */     
/* 1259 */     return true;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Chunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */