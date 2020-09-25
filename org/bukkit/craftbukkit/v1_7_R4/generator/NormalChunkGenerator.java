/*    */ package org.bukkit.craftbukkit.v1_7_R4.generator;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.server.v1_7_R4.Chunk;
/*    */ import net.minecraft.server.v1_7_R4.ChunkPosition;
/*    */ import net.minecraft.server.v1_7_R4.EnumCreatureType;
/*    */ import net.minecraft.server.v1_7_R4.IChunkProvider;
/*    */ import net.minecraft.server.v1_7_R4.IProgressUpdate;
/*    */ import net.minecraft.server.v1_7_R4.World;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.generator.BlockPopulator;
/*    */ 
/*    */ public class NormalChunkGenerator
/*    */   extends InternalChunkGenerator {
/*    */   private final IChunkProvider provider;
/*    */   
/*    */   public NormalChunkGenerator(World world, long seed) {
/* 21 */     this.provider = world.worldProvider.getChunkProvider();
/*    */   }
/*    */   
/*    */   public byte[] generate(World world, Random random, int x, int z) {
/* 25 */     throw new UnsupportedOperationException("Not supported.");
/*    */   }
/*    */   
/*    */   public boolean canSpawn(World world, int x, int z) {
/* 29 */     return (((CraftWorld)world).getHandle()).worldProvider.canSpawn(x, z);
/*    */   }
/*    */   
/*    */   public List<BlockPopulator> getDefaultPopulators(World world) {
/* 33 */     return new ArrayList<BlockPopulator>();
/*    */   }
/*    */   
/*    */   public boolean isChunkLoaded(int i, int i1) {
/* 37 */     return this.provider.isChunkLoaded(i, i1);
/*    */   }
/*    */   
/*    */   public Chunk getOrCreateChunk(int i, int i1) {
/* 41 */     return this.provider.getOrCreateChunk(i, i1);
/*    */   }
/*    */   
/*    */   public Chunk getChunkAt(int i, int i1) {
/* 45 */     return this.provider.getChunkAt(i, i1);
/*    */   }
/*    */   
/*    */   public void getChunkAt(IChunkProvider icp, int i, int i1) {
/* 49 */     this.provider.getChunkAt(icp, i, i1);
/*    */   }
/*    */   
/*    */   public boolean saveChunks(boolean bln, IProgressUpdate ipu) {
/* 53 */     return this.provider.saveChunks(bln, ipu);
/*    */   }
/*    */   
/*    */   public boolean unloadChunks() {
/* 57 */     return this.provider.unloadChunks();
/*    */   }
/*    */   
/*    */   public boolean canSave() {
/* 61 */     return this.provider.canSave();
/*    */   }
/*    */   
/*    */   public List<?> getMobsFor(EnumCreatureType ect, int i, int i1, int i2) {
/* 65 */     return this.provider.getMobsFor(ect, i, i1, i2);
/*    */   }
/*    */   
/*    */   public ChunkPosition findNearestMapFeature(World world, String string, int i, int i1, int i2) {
/* 69 */     return this.provider.findNearestMapFeature(world, string, i, i1, i2);
/*    */   }
/*    */   
/*    */   public void recreateStructures(int i, int j) {
/* 73 */     this.provider.recreateStructures(i, j);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLoadedChunks() {
/* 78 */     return 0;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 82 */     return "NormalWorldGenerator";
/*    */   }
/*    */   
/*    */   public void c() {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\generator\NormalChunkGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */