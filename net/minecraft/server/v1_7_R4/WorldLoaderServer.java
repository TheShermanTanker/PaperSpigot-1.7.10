/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class WorldLoaderServer
/*     */   extends WorldLoader
/*     */ {
/*  17 */   private static final Logger b = LogManager.getLogger();
/*     */   
/*     */   public WorldLoaderServer(File paramFile) {
/*  20 */     super(paramFile);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected int c() {
/*  61 */     return 19133;
/*     */   }
/*     */ 
/*     */   
/*     */   public void d() {
/*  66 */     RegionFileCache.a();
/*     */   }
/*     */ 
/*     */   
/*     */   public IDataManager a(String paramString, boolean paramBoolean) {
/*  71 */     return new ServerNBTManager(this.a, paramString, paramBoolean);
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
/*     */   public boolean isConvertable(String paramString) {
/*  88 */     WorldData worldData = c(paramString);
/*  89 */     if (worldData == null || worldData.l() == c()) {
/*  90 */       return false;
/*     */     }
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean convert(String paramString, IProgressUpdate paramIProgressUpdate) {
/*  98 */     paramIProgressUpdate.a(0);
/*     */     
/* 100 */     ArrayList arrayList1 = new ArrayList();
/* 101 */     ArrayList arrayList2 = new ArrayList();
/* 102 */     ArrayList arrayList3 = new ArrayList();
/* 103 */     File file1 = new File(this.a, paramString);
/* 104 */     File file2 = new File(file1, "DIM-1");
/* 105 */     File file3 = new File(file1, "DIM1");
/*     */     
/* 107 */     b.info("Scanning folders...");
/*     */ 
/*     */     
/* 110 */     a(file1, arrayList1);
/*     */ 
/*     */     
/* 113 */     if (file2.exists()) {
/* 114 */       a(file2, arrayList2);
/*     */     }
/* 116 */     if (file3.exists()) {
/* 117 */       a(file3, arrayList3);
/*     */     }
/*     */     
/* 120 */     int i = arrayList1.size() + arrayList2.size() + arrayList3.size();
/* 121 */     b.info("Total conversion count is " + i);
/*     */     
/* 123 */     WorldData worldData = c(paramString);
/*     */     
/* 125 */     WorldChunkManager worldChunkManager = null;
/* 126 */     if (worldData.getType() == WorldType.FLAT) {
/* 127 */       worldChunkManager = new WorldChunkManagerHell(BiomeBase.PLAINS, 0.5F);
/*     */     } else {
/* 129 */       worldChunkManager = new WorldChunkManager(worldData.getSeed(), worldData.getType());
/*     */     } 
/*     */ 
/*     */     
/* 133 */     a(new File(file1, "region"), arrayList1, worldChunkManager, 0, i, paramIProgressUpdate);
/*     */     
/* 135 */     a(new File(file2, "region"), arrayList2, new WorldChunkManagerHell(BiomeBase.HELL, 0.0F), arrayList1.size(), i, paramIProgressUpdate);
/*     */     
/* 137 */     a(new File(file3, "region"), arrayList3, new WorldChunkManagerHell(BiomeBase.SKY, 0.0F), arrayList1.size() + arrayList2.size(), i, paramIProgressUpdate);
/*     */     
/* 139 */     worldData.e(19133);
/* 140 */     if (worldData.getType() == WorldType.NORMAL_1_1) {
/* 141 */       worldData.setType(WorldType.NORMAL);
/*     */     }
/*     */     
/* 144 */     g(paramString);
/*     */     
/* 146 */     IDataManager iDataManager = a(paramString, false);
/* 147 */     iDataManager.saveWorldData(worldData);
/*     */     
/* 149 */     return true;
/*     */   }
/*     */   
/*     */   private void g(String paramString) {
/* 153 */     File file1 = new File(this.a, paramString);
/* 154 */     if (!file1.exists()) {
/* 155 */       b.warn("Unable to create level.dat_mcr backup");
/*     */       
/*     */       return;
/*     */     } 
/* 159 */     File file2 = new File(file1, "level.dat");
/* 160 */     if (!file2.exists()) {
/* 161 */       b.warn("Unable to create level.dat_mcr backup");
/*     */       
/*     */       return;
/*     */     } 
/* 165 */     File file3 = new File(file1, "level.dat_mcr");
/* 166 */     if (!file2.renameTo(file3)) {
/* 167 */       b.warn("Unable to create level.dat_mcr backup");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(File paramFile, Iterable paramIterable, WorldChunkManager paramWorldChunkManager, int paramInt1, int paramInt2, IProgressUpdate paramIProgressUpdate) {
/* 173 */     for (File file : paramIterable) {
/* 174 */       a(paramFile, file, paramWorldChunkManager, paramInt1, paramInt2, paramIProgressUpdate);
/*     */       
/* 176 */       paramInt1++;
/* 177 */       int i = (int)Math.round(100.0D * paramInt1 / paramInt2);
/* 178 */       paramIProgressUpdate.a(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void a(File paramFile1, File paramFile2, WorldChunkManager paramWorldChunkManager, int paramInt1, int paramInt2, IProgressUpdate paramIProgressUpdate) {
/*     */     try {
/* 186 */       String str = paramFile2.getName();
/*     */       
/* 188 */       RegionFile regionFile1 = new RegionFile(paramFile2);
/* 189 */       RegionFile regionFile2 = new RegionFile(new File(paramFile1, str.substring(0, str.length() - ".mcr".length()) + ".mca"));
/*     */       
/* 191 */       for (byte b = 0; b < 32; b++) {
/* 192 */         int i; for (i = 0; i < 32; i++) {
/* 193 */           if (regionFile1.c(b, i) && !regionFile2.c(b, i)) {
/* 194 */             DataInputStream dataInputStream = regionFile1.a(b, i);
/* 195 */             if (dataInputStream == null) {
/* 196 */               b.warn("Failed to fetch input stream");
/*     */             } else {
/*     */               
/* 199 */               NBTTagCompound nBTTagCompound1 = NBTCompressedStreamTools.a(dataInputStream);
/* 200 */               dataInputStream.close();
/*     */               
/* 202 */               NBTTagCompound nBTTagCompound2 = nBTTagCompound1.getCompound("Level");
/* 203 */               OldChunk oldChunk = OldChunkLoader.a(nBTTagCompound2);
/*     */               
/* 205 */               NBTTagCompound nBTTagCompound3 = new NBTTagCompound();
/* 206 */               NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
/* 207 */               nBTTagCompound3.set("Level", nBTTagCompound4);
/* 208 */               OldChunkLoader.a(oldChunk, nBTTagCompound4, paramWorldChunkManager);
/*     */               
/* 210 */               DataOutputStream dataOutputStream = regionFile2.b(b, i);
/* 211 */               NBTCompressedStreamTools.a(nBTTagCompound3, dataOutputStream);
/* 212 */               dataOutputStream.close();
/*     */             } 
/*     */           } 
/* 215 */         }  i = (int)Math.round(100.0D * (paramInt1 * 1024) / (paramInt2 * 1024));
/* 216 */         int j = (int)Math.round(100.0D * ((b + 1) * 32 + paramInt1 * 1024) / (paramInt2 * 1024));
/* 217 */         if (j > i) {
/* 218 */           paramIProgressUpdate.a(j);
/*     */         }
/*     */       } 
/*     */       
/* 222 */       regionFile1.c();
/* 223 */       regionFile2.c();
/* 224 */     } catch (IOException iOException) {
/* 225 */       iOException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(File paramFile, Collection<? super File> paramCollection) {
/* 231 */     File file = new File(paramFile, "region");
/* 232 */     File[] arrayOfFile = file.listFiles(new ChunkFilenameFilter(this));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     if (arrayOfFile != null)
/* 240 */       Collections.addAll(paramCollection, arrayOfFile); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldLoaderServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */