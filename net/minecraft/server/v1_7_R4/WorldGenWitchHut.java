/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ 
/*    */ public class WorldGenWitchHut extends WorldGenScatteredPiece {
/*    */   private boolean e;
/*    */   
/*    */   public WorldGenWitchHut() {}
/*    */   
/*    */   public WorldGenWitchHut(Random random, int i, int j) {
/* 12 */     super(random, i, 64, j, 7, 5, 9);
/*    */   }
/*    */   
/*    */   protected void a(NBTTagCompound nbttagcompound) {
/* 16 */     super.a(nbttagcompound);
/* 17 */     nbttagcompound.setBoolean("Witch", this.e);
/*    */   }
/*    */   
/*    */   protected void b(NBTTagCompound nbttagcompound) {
/* 21 */     super.b(nbttagcompound);
/* 22 */     this.e = nbttagcompound.getBoolean("Witch");
/*    */   }
/*    */   
/*    */   public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/* 26 */     if (!a(world, structureboundingbox, 0)) {
/* 27 */       return false;
/*    */     }
/* 29 */     a(world, structureboundingbox, 1, 1, 1, 5, 1, 7, Blocks.WOOD, 1, Blocks.WOOD, 1, false);
/* 30 */     a(world, structureboundingbox, 1, 4, 2, 5, 4, 7, Blocks.WOOD, 1, Blocks.WOOD, 1, false);
/* 31 */     a(world, structureboundingbox, 2, 1, 0, 4, 1, 0, Blocks.WOOD, 1, Blocks.WOOD, 1, false);
/* 32 */     a(world, structureboundingbox, 2, 2, 2, 3, 3, 2, Blocks.WOOD, 1, Blocks.WOOD, 1, false);
/* 33 */     a(world, structureboundingbox, 1, 2, 3, 1, 3, 6, Blocks.WOOD, 1, Blocks.WOOD, 1, false);
/* 34 */     a(world, structureboundingbox, 5, 2, 3, 5, 3, 6, Blocks.WOOD, 1, Blocks.WOOD, 1, false);
/* 35 */     a(world, structureboundingbox, 2, 2, 7, 4, 3, 7, Blocks.WOOD, 1, Blocks.WOOD, 1, false);
/* 36 */     a(world, structureboundingbox, 1, 0, 2, 1, 3, 2, Blocks.LOG, Blocks.LOG, false);
/* 37 */     a(world, structureboundingbox, 5, 0, 2, 5, 3, 2, Blocks.LOG, Blocks.LOG, false);
/* 38 */     a(world, structureboundingbox, 1, 0, 7, 1, 3, 7, Blocks.LOG, Blocks.LOG, false);
/* 39 */     a(world, structureboundingbox, 5, 0, 7, 5, 3, 7, Blocks.LOG, Blocks.LOG, false);
/* 40 */     a(world, Blocks.FENCE, 0, 2, 3, 2, structureboundingbox);
/* 41 */     a(world, Blocks.FENCE, 0, 3, 3, 7, structureboundingbox);
/* 42 */     a(world, Blocks.AIR, 0, 1, 3, 4, structureboundingbox);
/* 43 */     a(world, Blocks.AIR, 0, 5, 3, 4, structureboundingbox);
/* 44 */     a(world, Blocks.AIR, 0, 5, 3, 5, structureboundingbox);
/* 45 */     a(world, Blocks.FLOWER_POT, 7, 1, 3, 5, structureboundingbox);
/* 46 */     a(world, Blocks.WORKBENCH, 0, 3, 2, 6, structureboundingbox);
/* 47 */     a(world, Blocks.CAULDRON, 0, 4, 2, 6, structureboundingbox);
/* 48 */     a(world, Blocks.FENCE, 0, 1, 2, 1, structureboundingbox);
/* 49 */     a(world, Blocks.FENCE, 0, 5, 2, 1, structureboundingbox);
/* 50 */     int i = a(Blocks.WOOD_STAIRS, 3);
/* 51 */     int j = a(Blocks.WOOD_STAIRS, 1);
/* 52 */     int k = a(Blocks.WOOD_STAIRS, 0);
/* 53 */     int l = a(Blocks.WOOD_STAIRS, 2);
/*    */     
/* 55 */     a(world, structureboundingbox, 0, 4, 1, 6, 4, 1, Blocks.SPRUCE_WOOD_STAIRS, i, Blocks.SPRUCE_WOOD_STAIRS, i, false);
/* 56 */     a(world, structureboundingbox, 0, 4, 2, 0, 4, 7, Blocks.SPRUCE_WOOD_STAIRS, k, Blocks.SPRUCE_WOOD_STAIRS, k, false);
/* 57 */     a(world, structureboundingbox, 6, 4, 2, 6, 4, 7, Blocks.SPRUCE_WOOD_STAIRS, j, Blocks.SPRUCE_WOOD_STAIRS, j, false);
/* 58 */     a(world, structureboundingbox, 0, 4, 8, 6, 4, 8, Blocks.SPRUCE_WOOD_STAIRS, l, Blocks.SPRUCE_WOOD_STAIRS, l, false);
/*    */ 
/*    */     
/*    */     int i1;
/*    */     
/* 63 */     for (i1 = 2; i1 <= 7; i1 += 5) {
/* 64 */       for (int j1 = 1; j1 <= 5; j1 += 4) {
/* 65 */         b(world, Blocks.LOG, 0, j1, -1, i1, structureboundingbox);
/*    */       }
/*    */     } 
/*    */     
/* 69 */     if (!this.e) {
/* 70 */       i1 = a(2, 5);
/* 71 */       int j1 = a(2);
/* 72 */       int k1 = b(2, 5);
/*    */       
/* 74 */       if (structureboundingbox.b(i1, j1, k1)) {
/* 75 */         this.e = true;
/* 76 */         EntityWitch entitywitch = new EntityWitch(world);
/*    */         
/* 78 */         entitywitch.setPositionRotation(i1 + 0.5D, j1, k1 + 0.5D, 0.0F, 0.0F);
/* 79 */         entitywitch.prepare((GroupDataEntity)null);
/* 80 */         world.addEntity(entitywitch, CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
/*    */       } 
/*    */     } 
/*    */     
/* 84 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenWitchHut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */