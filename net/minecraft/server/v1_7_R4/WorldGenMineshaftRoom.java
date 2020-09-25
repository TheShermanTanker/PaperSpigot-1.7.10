/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.LinkedList;
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
/*     */ public class WorldGenMineshaftRoom
/*     */   extends StructurePiece
/*     */ {
/*  71 */   private List a = new LinkedList();
/*     */ 
/*     */   
/*     */   public WorldGenMineshaftRoom() {}
/*     */ 
/*     */   
/*     */   public WorldGenMineshaftRoom(int paramInt1, Random paramRandom, int paramInt2, int paramInt3) {
/*  78 */     super(paramInt1);
/*     */     
/*  80 */     this.f = new StructureBoundingBox(paramInt2, 50, paramInt3, paramInt2 + 7 + paramRandom.nextInt(6), 54 + paramRandom.nextInt(6), paramInt3 + 7 + paramRandom.nextInt(6));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/*  86 */     int i = d();
/*     */ 
/*     */ 
/*     */     
/*  90 */     int j = this.f.c() - 3 - 1;
/*  91 */     if (j <= 0) {
/*  92 */       j = 1;
/*     */     }
/*     */ 
/*     */     
/*  96 */     int k = 0;
/*  97 */     while (k < this.f.b()) {
/*  98 */       k += paramRandom.nextInt(this.f.b());
/*  99 */       if (k + 3 > this.f.b()) {
/*     */         break;
/*     */       }
/* 102 */       StructurePiece structurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + k, this.f.b + paramRandom.nextInt(j) + 1, this.f.c - 1, 2, i);
/*     */       
/* 104 */       if (structurePiece != null) {
/* 105 */         StructureBoundingBox structureBoundingBox = structurePiece.c();
/* 106 */         this.a.add(new StructureBoundingBox(structureBoundingBox.a, structureBoundingBox.b, this.f.c, structureBoundingBox.d, structureBoundingBox.e, this.f.c + 1));
/*     */       } 
/* 108 */       k += 4;
/*     */     } 
/*     */     
/* 111 */     k = 0;
/* 112 */     while (k < this.f.b()) {
/* 113 */       k += paramRandom.nextInt(this.f.b());
/* 114 */       if (k + 3 > this.f.b()) {
/*     */         break;
/*     */       }
/* 117 */       StructurePiece structurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + k, this.f.b + paramRandom.nextInt(j) + 1, this.f.f + 1, 0, i);
/*     */       
/* 119 */       if (structurePiece != null) {
/* 120 */         StructureBoundingBox structureBoundingBox = structurePiece.c();
/* 121 */         this.a.add(new StructureBoundingBox(structureBoundingBox.a, structureBoundingBox.b, this.f.f - 1, structureBoundingBox.d, structureBoundingBox.e, this.f.f));
/*     */       } 
/* 123 */       k += 4;
/*     */     } 
/*     */     
/* 126 */     k = 0;
/* 127 */     while (k < this.f.d()) {
/* 128 */       k += paramRandom.nextInt(this.f.d());
/* 129 */       if (k + 3 > this.f.d()) {
/*     */         break;
/*     */       }
/* 132 */       StructurePiece structurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b + paramRandom.nextInt(j) + 1, this.f.c + k, 1, i);
/*     */       
/* 134 */       if (structurePiece != null) {
/* 135 */         StructureBoundingBox structureBoundingBox = structurePiece.c();
/* 136 */         this.a.add(new StructureBoundingBox(this.f.a, structureBoundingBox.b, structureBoundingBox.c, this.f.a + 1, structureBoundingBox.e, structureBoundingBox.f));
/*     */       } 
/* 138 */       k += 4;
/*     */     } 
/*     */     
/* 141 */     k = 0;
/* 142 */     while (k < this.f.d()) {
/* 143 */       k += paramRandom.nextInt(this.f.d());
/* 144 */       if (k + 3 > this.f.d()) {
/*     */         break;
/*     */       }
/* 147 */       StructurePiece structurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b + paramRandom.nextInt(j) + 1, this.f.c + k, 3, i);
/*     */       
/* 149 */       if (structurePiece != null) {
/* 150 */         StructureBoundingBox structureBoundingBox = structurePiece.c();
/* 151 */         this.a.add(new StructureBoundingBox(this.f.d - 1, structureBoundingBox.b, structureBoundingBox.c, this.f.d, structureBoundingBox.e, structureBoundingBox.f));
/*     */       } 
/* 153 */       k += 4;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 160 */     if (a(paramWorld, paramStructureBoundingBox)) {
/* 161 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 165 */     a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.b, this.f.c, this.f.d, this.f.b, this.f.f, Blocks.DIRT, Blocks.AIR, true);
/*     */ 
/*     */     
/* 168 */     a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.b + 1, this.f.c, this.f.d, Math.min(this.f.b + 3, this.f.e), this.f.f, Blocks.AIR, Blocks.AIR, false);
/*     */     
/* 170 */     for (StructureBoundingBox structureBoundingBox : this.a) {
/* 171 */       a(paramWorld, paramStructureBoundingBox, structureBoundingBox.a, structureBoundingBox.e - 2, structureBoundingBox.c, structureBoundingBox.d, structureBoundingBox.e, structureBoundingBox.f, Blocks.AIR, Blocks.AIR, false);
/*     */     }
/*     */     
/* 174 */     a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.b + 4, this.f.c, this.f.d, this.f.e, this.f.f, Blocks.AIR, false);
/*     */     
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 181 */     NBTTagList nBTTagList = new NBTTagList();
/* 182 */     for (StructureBoundingBox structureBoundingBox : this.a) {
/* 183 */       nBTTagList.add(structureBoundingBox.h());
/*     */     }
/* 185 */     paramNBTTagCompound.set("Entrances", nBTTagList);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 190 */     NBTTagList nBTTagList = paramNBTTagCompound.getList("Entrances", 11);
/* 191 */     for (byte b = 0; b < nBTTagList.size(); b++)
/* 192 */       this.a.add(new StructureBoundingBox(nBTTagList.c(b))); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenMineshaftRoom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */