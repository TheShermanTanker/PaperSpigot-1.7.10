/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ public class OldChunkLoader
/*     */ {
/*     */   public static OldChunk a(NBTTagCompound nbttagcompound) {
/*   6 */     int i = nbttagcompound.getInt("xPos");
/*   7 */     int j = nbttagcompound.getInt("zPos");
/*   8 */     OldChunk oldchunk = new OldChunk(i, j);
/*     */     
/*  10 */     oldchunk.g = nbttagcompound.getByteArray("Blocks");
/*  11 */     oldchunk.f = new OldNibbleArray(nbttagcompound.getByteArray("Data"), 7);
/*  12 */     oldchunk.e = new OldNibbleArray(nbttagcompound.getByteArray("SkyLight"), 7);
/*  13 */     oldchunk.d = new OldNibbleArray(nbttagcompound.getByteArray("BlockLight"), 7);
/*  14 */     oldchunk.c = nbttagcompound.getByteArray("HeightMap");
/*  15 */     oldchunk.b = nbttagcompound.getBoolean("TerrainPopulated");
/*  16 */     oldchunk.h = nbttagcompound.getList("Entities", 10);
/*  17 */     oldchunk.i = nbttagcompound.getList("TileEntities", 10);
/*  18 */     oldchunk.j = nbttagcompound.getList("TileTicks", 10);
/*     */     
/*     */     try {
/*  21 */       oldchunk.a = nbttagcompound.getLong("LastUpdate");
/*  22 */     } catch (ClassCastException classcastexception) {
/*  23 */       oldchunk.a = nbttagcompound.getInt("LastUpdate");
/*     */     } 
/*     */     
/*  26 */     return oldchunk;
/*     */   }
/*     */   
/*     */   public static void a(OldChunk oldchunk, NBTTagCompound nbttagcompound, WorldChunkManager worldchunkmanager) {
/*  30 */     nbttagcompound.setInt("xPos", oldchunk.k);
/*  31 */     nbttagcompound.setInt("zPos", oldchunk.l);
/*  32 */     nbttagcompound.setLong("LastUpdate", oldchunk.a);
/*  33 */     int[] aint = new int[oldchunk.c.length];
/*     */     
/*  35 */     for (int i = 0; i < oldchunk.c.length; i++) {
/*  36 */       aint[i] = oldchunk.c[i];
/*     */     }
/*     */     
/*  39 */     nbttagcompound.setIntArray("HeightMap", aint);
/*  40 */     nbttagcompound.setBoolean("TerrainPopulated", oldchunk.b);
/*  41 */     NBTTagList nbttaglist = new NBTTagList();
/*     */ 
/*     */ 
/*     */     
/*  45 */     for (int k = 0; k < 8; k++) {
/*  46 */       boolean flag = true;
/*     */       
/*  48 */       for (int j = 0; j < 16 && flag; j++) {
/*  49 */         int l = 0;
/*     */         
/*  51 */         while (l < 16 && flag) {
/*  52 */           int i1 = 0;
/*     */ 
/*     */           
/*  55 */           while (i1 < 16) {
/*  56 */             int j1 = j << 11 | i1 << 7 | l + (k << 4);
/*  57 */             byte b0 = oldchunk.g[j1];
/*     */             
/*  59 */             if (b0 == 0) {
/*  60 */               i1++;
/*     */               
/*     */               continue;
/*     */             } 
/*  64 */             flag = false;
/*     */           } 
/*     */           
/*  67 */           l++;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  73 */       if (!flag) {
/*  74 */         byte[] abyte = new byte[4096];
/*  75 */         NibbleArray nibblearray = new NibbleArray(abyte.length, 4);
/*  76 */         NibbleArray nibblearray1 = new NibbleArray(abyte.length, 4);
/*  77 */         NibbleArray nibblearray2 = new NibbleArray(abyte.length, 4);
/*     */         
/*  79 */         for (int k1 = 0; k1 < 16; k1++) {
/*  80 */           for (int l1 = 0; l1 < 16; l1++) {
/*  81 */             for (int i2 = 0; i2 < 16; i2++) {
/*  82 */               int j2 = k1 << 11 | i2 << 7 | l1 + (k << 4);
/*  83 */               byte b1 = oldchunk.g[j2];
/*     */               
/*  85 */               abyte[l1 << 8 | i2 << 4 | k1] = (byte)(b1 & 0xFF);
/*  86 */               nibblearray.a(k1, l1, i2, oldchunk.f.a(k1, l1 + (k << 4), i2));
/*  87 */               nibblearray1.a(k1, l1, i2, oldchunk.e.a(k1, l1 + (k << 4), i2));
/*  88 */               nibblearray2.a(k1, l1, i2, oldchunk.d.a(k1, l1 + (k << 4), i2));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  93 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/*  95 */         nbttagcompound1.setByte("Y", (byte)(k & 0xFF));
/*  96 */         nbttagcompound1.setByteArray("Blocks", abyte);
/*  97 */         nbttagcompound1.setByteArray("Data", nibblearray.a);
/*  98 */         nbttagcompound1.setByteArray("SkyLight", nibblearray1.a);
/*  99 */         nbttagcompound1.setByteArray("BlockLight", nibblearray2.a);
/* 100 */         nbttaglist.add(nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     nbttagcompound.set("Sections", nbttaglist);
/* 105 */     byte[] abyte1 = new byte[256];
/*     */     
/* 107 */     for (int k2 = 0; k2 < 16; k2++) {
/* 108 */       for (int j = 0; j < 16; j++) {
/* 109 */         abyte1[j << 4 | k2] = (byte)((worldchunkmanager.getBiome(oldchunk.k << 4 | k2, oldchunk.l << 4 | j)).id & 0xFF);
/*     */       }
/*     */     } 
/*     */     
/* 113 */     nbttagcompound.setByteArray("Biomes", abyte1);
/* 114 */     nbttagcompound.set("Entities", oldchunk.h);
/* 115 */     nbttagcompound.set("TileEntities", oldchunk.i);
/* 116 */     if (oldchunk.j != null)
/* 117 */       nbttagcompound.set("TileTicks", oldchunk.j); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\OldChunkLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */