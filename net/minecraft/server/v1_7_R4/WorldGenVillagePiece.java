/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ abstract class WorldGenVillagePiece extends StructurePiece {
/*   8 */   protected int k = -1;
/*     */   private int a;
/*     */   private boolean b;
/*     */   
/*     */   public WorldGenVillagePiece() {}
/*     */   
/*     */   protected WorldGenVillagePiece(WorldGenVillageStartPiece worldgenvillagestartpiece, int i) {
/*  15 */     super(i);
/*  16 */     if (worldgenvillagestartpiece != null) {
/*  17 */       this.b = worldgenvillagestartpiece.b;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {
/*  22 */     nbttagcompound.setInt("HPos", this.k);
/*  23 */     nbttagcompound.setInt("VCount", this.a);
/*  24 */     nbttagcompound.setBoolean("Desert", this.b);
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {
/*  28 */     this.k = nbttagcompound.getInt("HPos");
/*  29 */     this.a = nbttagcompound.getInt("VCount");
/*  30 */     this.b = nbttagcompound.getBoolean("Desert");
/*     */   }
/*     */   
/*     */   protected StructurePiece a(WorldGenVillageStartPiece worldgenvillagestartpiece, List list, Random random, int i, int j) {
/*  34 */     switch (this.g) {
/*     */       case 0:
/*  36 */         return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a - 1, this.f.b + i, this.f.c + j, 1, d());
/*     */       
/*     */       case 1:
/*  39 */         return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a + j, this.f.b + i, this.f.c - 1, 2, d());
/*     */       
/*     */       case 2:
/*  42 */         return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a - 1, this.f.b + i, this.f.c + j, 1, d());
/*     */       
/*     */       case 3:
/*  45 */         return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a + j, this.f.b + i, this.f.c - 1, 2, d());
/*     */     } 
/*     */     
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructurePiece b(WorldGenVillageStartPiece worldgenvillagestartpiece, List list, Random random, int i, int j) {
/*  53 */     switch (this.g) {
/*     */       case 0:
/*  55 */         return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.d + 1, this.f.b + i, this.f.c + j, 3, d());
/*     */       
/*     */       case 1:
/*  58 */         return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a + j, this.f.b + i, this.f.f + 1, 0, d());
/*     */       
/*     */       case 2:
/*  61 */         return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.d + 1, this.f.b + i, this.f.c + j, 3, d());
/*     */       
/*     */       case 3:
/*  64 */         return WorldGenVillagePieces.a(worldgenvillagestartpiece, list, random, this.f.a + j, this.f.b + i, this.f.f + 1, 0, d());
/*     */     } 
/*     */     
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int b(World world, StructureBoundingBox structureboundingbox) {
/*  72 */     int i = 0;
/*  73 */     int j = 0;
/*     */     
/*  75 */     for (int k = this.f.c; k <= this.f.f; k++) {
/*  76 */       for (int l = this.f.a; l <= this.f.d; l++) {
/*  77 */         if (structureboundingbox.b(l, 64, k)) {
/*  78 */           i += Math.max(world.i(l, k), world.worldProvider.getSeaLevel());
/*  79 */           j++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     if (j == 0) {
/*  85 */       return -1;
/*     */     }
/*  87 */     return i / j;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean a(StructureBoundingBox structureboundingbox) {
/*  92 */     return (structureboundingbox != null && structureboundingbox.b > 10);
/*     */   }
/*     */   
/*     */   protected void a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l) {
/*  96 */     if (this.a < l) {
/*  97 */       for (int i1 = this.a; i1 < l; i1++) {
/*  98 */         int j1 = a(i + i1, k);
/*  99 */         int k1 = a(j);
/* 100 */         int l1 = b(i + i1, k);
/*     */         
/* 102 */         if (!structureboundingbox.b(j1, k1, l1)) {
/*     */           break;
/*     */         }
/*     */         
/* 106 */         this.a++;
/* 107 */         EntityVillager entityvillager = new EntityVillager(world, b(i1));
/*     */         
/* 109 */         entityvillager.setPositionRotation(j1 + 0.5D, k1, l1 + 0.5D, 0.0F, 0.0F);
/* 110 */         world.addEntity(entityvillager, CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected int b(int i) {
/* 116 */     return 0;
/*     */   }
/*     */   
/*     */   protected Block b(Block block, int i) {
/* 120 */     if (this.b) {
/* 121 */       if (block == Blocks.LOG || block == Blocks.LOG2) {
/* 122 */         return Blocks.SANDSTONE;
/*     */       }
/*     */       
/* 125 */       if (block == Blocks.COBBLESTONE) {
/* 126 */         return Blocks.SANDSTONE;
/*     */       }
/*     */       
/* 129 */       if (block == Blocks.WOOD) {
/* 130 */         return Blocks.SANDSTONE;
/*     */       }
/*     */       
/* 133 */       if (block == Blocks.WOOD_STAIRS) {
/* 134 */         return Blocks.SANDSTONE_STAIRS;
/*     */       }
/*     */       
/* 137 */       if (block == Blocks.COBBLESTONE_STAIRS) {
/* 138 */         return Blocks.SANDSTONE_STAIRS;
/*     */       }
/*     */       
/* 141 */       if (block == Blocks.GRAVEL) {
/* 142 */         return Blocks.SANDSTONE;
/*     */       }
/*     */     } 
/*     */     
/* 146 */     return block;
/*     */   }
/*     */   
/*     */   protected int c(Block block, int i) {
/* 150 */     if (this.b) {
/* 151 */       if (block == Blocks.LOG || block == Blocks.LOG2) {
/* 152 */         return 0;
/*     */       }
/*     */       
/* 155 */       if (block == Blocks.COBBLESTONE) {
/* 156 */         return 0;
/*     */       }
/*     */       
/* 159 */       if (block == Blocks.WOOD) {
/* 160 */         return 2;
/*     */       }
/*     */     } 
/*     */     
/* 164 */     return i;
/*     */   }
/*     */   
/*     */   protected void a(World world, Block block, int i, int j, int k, int l, StructureBoundingBox structureboundingbox) {
/* 168 */     Block block1 = b(block, i);
/* 169 */     int i1 = c(block, i);
/*     */     
/* 171 */     super.a(world, block1, i1, j, k, l, structureboundingbox);
/*     */   }
/*     */   
/*     */   protected void a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l, int i1, int j1, Block block, Block block1, boolean flag) {
/* 175 */     Block block2 = b(block, 0);
/* 176 */     int k1 = c(block, 0);
/* 177 */     Block block3 = b(block1, 0);
/* 178 */     int l1 = c(block1, 0);
/*     */     
/* 180 */     a(world, structureboundingbox, i, j, k, l, i1, j1, block2, k1, block3, l1, flag);
/*     */   }
/*     */   
/*     */   protected void b(World world, Block block, int i, int j, int k, int l, StructureBoundingBox structureboundingbox) {
/* 184 */     Block block1 = b(block, i);
/* 185 */     int i1 = c(block, i);
/*     */     
/* 187 */     super.b(world, block1, i1, j, k, l, structureboundingbox);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillagePiece.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */