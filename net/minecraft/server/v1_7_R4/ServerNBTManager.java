/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerNBTManager
/*    */   extends WorldNBTStorage
/*    */ {
/*    */   public ServerNBTManager(File paramFile, String paramString, boolean paramBoolean) {
/* 16 */     super(paramFile, paramString, paramBoolean);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IChunkLoader createChunkLoader(WorldProvider paramWorldProvider) {
/* 22 */     File file = getDirectory();
/*    */     
/* 24 */     if (paramWorldProvider instanceof WorldProviderHell) {
/* 25 */       File file1 = new File(file, "DIM-1");
/* 26 */       file1.mkdirs();
/* 27 */       return new ChunkRegionLoader(file1);
/*    */     } 
/* 29 */     if (paramWorldProvider instanceof WorldProviderTheEnd) {
/* 30 */       File file1 = new File(file, "DIM1");
/* 31 */       file1.mkdirs();
/* 32 */       return new ChunkRegionLoader(file1);
/*    */     } 
/*    */     
/* 35 */     return new ChunkRegionLoader(file);
/*    */   }
/*    */ 
/*    */   
/*    */   public void saveWorldData(WorldData paramWorldData, NBTTagCompound paramNBTTagCompound) {
/* 40 */     paramWorldData.e(19133);
/* 41 */     super.saveWorldData(paramWorldData, paramNBTTagCompound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a() {
/*    */     try {
/* 47 */       FileIOThread.a.a();
/* 48 */     } catch (InterruptedException interruptedException) {
/* 49 */       interruptedException.printStackTrace();
/*    */     } 
/*    */     
/* 52 */     RegionFileCache.a();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerNBTManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */