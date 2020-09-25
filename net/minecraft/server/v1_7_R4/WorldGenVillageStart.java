/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
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
/*     */ public class WorldGenVillageStart
/*     */   extends StructureStart
/*     */ {
/*     */   private boolean c;
/*     */   
/*     */   public WorldGenVillageStart() {}
/*     */   
/*     */   public WorldGenVillageStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  84 */     super(paramInt1, paramInt2);
/*     */     
/*  86 */     List list = WorldGenVillagePieces.a(paramRandom, paramInt3);
/*     */     
/*  88 */     WorldGenVillageStartPiece worldGenVillageStartPiece = new WorldGenVillageStartPiece(paramWorld.getWorldChunkManager(), 0, paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2, list, paramInt3);
/*  89 */     this.a.add(worldGenVillageStartPiece);
/*  90 */     worldGenVillageStartPiece.a(worldGenVillageStartPiece, this.a, paramRandom);
/*     */     
/*  92 */     List<StructurePiece> list1 = worldGenVillageStartPiece.j;
/*  93 */     List<StructurePiece> list2 = worldGenVillageStartPiece.i;
/*  94 */     while (!list1.isEmpty() || !list2.isEmpty()) {
/*     */ 
/*     */       
/*  97 */       if (list1.isEmpty()) {
/*  98 */         int j = paramRandom.nextInt(list2.size());
/*  99 */         StructurePiece structurePiece1 = list2.remove(j);
/* 100 */         structurePiece1.a(worldGenVillageStartPiece, this.a, paramRandom); continue;
/*     */       } 
/* 102 */       int i = paramRandom.nextInt(list1.size());
/* 103 */       StructurePiece structurePiece = list1.remove(i);
/* 104 */       structurePiece.a(worldGenVillageStartPiece, this.a, paramRandom);
/*     */     } 
/*     */ 
/*     */     
/* 108 */     c();
/*     */     
/* 110 */     byte b = 0;
/* 111 */     for (StructurePiece structurePiece : this.a) {
/* 112 */       if (!(structurePiece instanceof WorldGenVillageRoadPiece)) {
/* 113 */         b++;
/*     */       }
/*     */     } 
/* 116 */     this.c = (b > 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/* 121 */     return this.c;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 126 */     super.a(paramNBTTagCompound);
/*     */     
/* 128 */     paramNBTTagCompound.setBoolean("Valid", this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 133 */     super.b(paramNBTTagCompound);
/* 134 */     this.c = paramNBTTagCompound.getBoolean("Valid");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillageStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */