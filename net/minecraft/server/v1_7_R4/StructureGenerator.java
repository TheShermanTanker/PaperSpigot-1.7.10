/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ 
/*     */ public abstract class StructureGenerator
/*     */   extends WorldGenBase
/*     */ {
/*     */   private PersistentStructure e;
/*  13 */   protected Map d = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   public abstract String a();
/*     */ 
/*     */   
/*     */   protected final void a(World world, int i, int j, int k, int l, Block[] ablock) {
/*  20 */     a(world);
/*  21 */     if (!this.d.containsKey(Long.valueOf(ChunkCoordIntPair.a(i, j)))) {
/*  22 */       this.b.nextInt();
/*     */       
/*     */       try {
/*  25 */         if (a(i, j)) {
/*  26 */           StructureStart structurestart = b(i, j);
/*     */           
/*  28 */           this.d.put(Long.valueOf(ChunkCoordIntPair.a(i, j)), structurestart);
/*  29 */           a(i, j, structurestart);
/*     */         } 
/*  31 */       } catch (Throwable throwable) {
/*  32 */         CrashReport crashreport = CrashReport.a(throwable, "Exception preparing structure feature");
/*  33 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Feature being prepared");
/*     */         
/*  35 */         crashreportsystemdetails.a("Is feature chunk", new CrashReportIsFeatureChunk(this, i, j));
/*  36 */         crashreportsystemdetails.a("Chunk location", String.format("%d,%d", new Object[] { Integer.valueOf(i), Integer.valueOf(j) }));
/*  37 */         crashreportsystemdetails.a("Chunk pos hash", new CrashReportChunkPosHash(this, i, j));
/*  38 */         crashreportsystemdetails.a("Structure type", new CrashReportStructureType(this));
/*  39 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, int i, int j) {
/*  45 */     a(world);
/*  46 */     int k = (i << 4) + 8;
/*  47 */     int l = (j << 4) + 8;
/*  48 */     boolean flag = false;
/*  49 */     Iterator<StructureStart> iterator = this.d.values().iterator();
/*     */     
/*  51 */     while (iterator.hasNext()) {
/*  52 */       StructureStart structurestart = iterator.next();
/*     */       
/*  54 */       if (structurestart.d() && structurestart.a().a(k, l, k + 15, l + 15)) {
/*  55 */         structurestart.a(world, random, new StructureBoundingBox(k, l, k + 15, l + 15));
/*  56 */         flag = true;
/*  57 */         a(structurestart.e(), structurestart.f(), structurestart);
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean b(int i, int j, int k) {
/*  65 */     if (this.c == null) return false; 
/*  66 */     a(this.c);
/*  67 */     return (c(i, j, k) != null);
/*     */   }
/*     */   
/*     */   protected StructureStart c(int i, int j, int k) {
/*  71 */     Iterator<StructureStart> iterator = this.d.values().iterator();
/*     */     
/*  73 */     while (iterator.hasNext()) {
/*  74 */       StructureStart structurestart = iterator.next();
/*     */       
/*  76 */       if (structurestart.d() && structurestart.a().a(i, k, i, k)) {
/*  77 */         Iterator<StructurePiece> iterator1 = structurestart.b().iterator();
/*     */         
/*  79 */         while (iterator1.hasNext()) {
/*  80 */           StructurePiece structurepiece = iterator1.next();
/*     */           
/*  82 */           if (structurepiece.c().b(i, j, k)) {
/*  83 */             return structurestart;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     return null;
/*     */   }
/*     */   public boolean d(int i, int j, int k) {
/*     */     StructureStart structurestart;
/*  93 */     if (this.c == null) return false; 
/*  94 */     a(this.c);
/*  95 */     Iterator<StructureStart> iterator = this.d.values().iterator();
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 100 */       if (!iterator.hasNext()) {
/* 101 */         return false;
/*     */       }
/*     */       
/* 104 */       structurestart = iterator.next();
/* 105 */     } while (!structurestart.d());
/*     */     
/* 107 */     return structurestart.a().a(i, k, i, k);
/*     */   }
/*     */   
/*     */   public ChunkPosition getNearestGeneratedFeature(World world, int i, int j, int k) {
/* 111 */     this.c = world;
/* 112 */     a(world);
/* 113 */     this.b.setSeed(world.getSeed());
/* 114 */     long l = this.b.nextLong();
/* 115 */     long i1 = this.b.nextLong();
/* 116 */     long j1 = (i >> 4) * l;
/* 117 */     long k1 = (k >> 4) * i1;
/*     */     
/* 119 */     this.b.setSeed(j1 ^ k1 ^ world.getSeed());
/* 120 */     a(world, i >> 4, k >> 4, 0, 0, (Block[])null);
/* 121 */     double d0 = Double.MAX_VALUE;
/* 122 */     ChunkPosition chunkposition = null;
/* 123 */     Iterator<StructureStart> iterator = this.d.values().iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     while (iterator.hasNext()) {
/* 132 */       StructureStart structurestart = iterator.next();
/*     */       
/* 134 */       if (structurestart.d()) {
/* 135 */         StructurePiece structurepiece = structurestart.b().get(0);
/*     */         
/* 137 */         ChunkPosition chunkposition1 = structurepiece.a();
/* 138 */         int i2 = chunkposition1.x - i;
/* 139 */         int l1 = chunkposition1.y - j;
/* 140 */         int j2 = chunkposition1.z - k;
/* 141 */         double d1 = (i2 * i2 + l1 * l1 + j2 * j2);
/* 142 */         if (d1 < d0) {
/* 143 */           d0 = d1;
/* 144 */           chunkposition = chunkposition1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     if (chunkposition != null) {
/* 150 */       return chunkposition;
/*     */     }
/* 152 */     List list = o_();
/*     */     
/* 154 */     if (list != null) {
/* 155 */       ChunkPosition chunkposition2 = null;
/* 156 */       Iterator<ChunkPosition> iterator1 = list.iterator();
/*     */       
/* 158 */       while (iterator1.hasNext()) {
/* 159 */         ChunkPosition chunkposition1 = iterator1.next();
/* 160 */         int i2 = chunkposition1.x - i;
/* 161 */         int l1 = chunkposition1.y - j;
/* 162 */         int j2 = chunkposition1.z - k;
/* 163 */         double d1 = (i2 * i2 + l1 * l1 + j2 * j2);
/* 164 */         if (d1 < d0) {
/* 165 */           d0 = d1;
/* 166 */           chunkposition2 = chunkposition1;
/*     */         } 
/*     */       } 
/*     */       
/* 170 */       return chunkposition2;
/*     */     } 
/* 172 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List o_() {
/* 178 */     return null;
/*     */   }
/*     */   
/*     */   private void a(World world) {
/* 182 */     if (this.e == null) {
/*     */       
/* 184 */       if (world.spigotConfig.saveStructureInfo && !a().equals("Mineshaft")) {
/*     */         
/* 186 */         this.e = (PersistentStructure)world.a(PersistentStructure.class, a());
/*     */       } else {
/*     */         
/* 189 */         this.e = new PersistentStructure(a());
/*     */       } 
/*     */       
/* 192 */       if (this.e == null) {
/* 193 */         this.e = new PersistentStructure(a());
/* 194 */         world.a(a(), this.e);
/*     */       } else {
/* 196 */         NBTTagCompound nbttagcompound = this.e.a();
/* 197 */         Iterator<String> iterator = nbttagcompound.c().iterator();
/*     */         
/* 199 */         while (iterator.hasNext()) {
/* 200 */           String s = iterator.next();
/* 201 */           NBTBase nbtbase = nbttagcompound.get(s);
/*     */           
/* 203 */           if (nbtbase.getTypeId() == 10) {
/* 204 */             NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase;
/*     */             
/* 206 */             if (nbttagcompound1.hasKey("ChunkX") && nbttagcompound1.hasKey("ChunkZ")) {
/* 207 */               int i = nbttagcompound1.getInt("ChunkX");
/* 208 */               int j = nbttagcompound1.getInt("ChunkZ");
/* 209 */               StructureStart structurestart = WorldGenFactory.a(nbttagcompound1, world);
/*     */               
/* 211 */               if (structurestart != null) {
/* 212 */                 this.d.put(Long.valueOf(ChunkCoordIntPair.a(i, j)), structurestart);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(int i, int j, StructureStart structurestart) {
/* 222 */     this.e.a(structurestart.a(i, j), i, j);
/* 223 */     this.e.c();
/*     */   }
/*     */   
/*     */   protected abstract boolean a(int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract StructureStart b(int paramInt1, int paramInt2);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\StructureGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */