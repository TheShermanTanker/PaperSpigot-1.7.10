/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class WorldGenVillagePieces
/*     */ {
/*     */   public static void a() {
/*  11 */     WorldGenFactory.a(WorldGenVillageLibrary.class, "ViBH");
/*  12 */     WorldGenFactory.a(WorldGenVillageFarm2.class, "ViDF");
/*  13 */     WorldGenFactory.a(WorldGenVillageFarm.class, "ViF");
/*  14 */     WorldGenFactory.a(WorldGenVillageLight.class, "ViL");
/*  15 */     WorldGenFactory.a(WorldGenVillageButcher.class, "ViPH");
/*  16 */     WorldGenFactory.a(WorldGenVillageHouse.class, "ViSH");
/*  17 */     WorldGenFactory.a(WorldGenVillageHut.class, "ViSmH");
/*  18 */     WorldGenFactory.a(WorldGenVillageTemple.class, "ViST");
/*  19 */     WorldGenFactory.a(WorldGenVillageBlacksmith.class, "ViS");
/*  20 */     WorldGenFactory.a(WorldGenVillageStartPiece.class, "ViStart");
/*  21 */     WorldGenFactory.a(WorldGenVillageRoad.class, "ViSR");
/*  22 */     WorldGenFactory.a(WorldGenVillageHouse2.class, "ViTRH");
/*  23 */     WorldGenFactory.a(WorldGenVillageWell.class, "ViW");
/*     */   }
/*     */   
/*     */   public static List a(Random random, int i) {
/*  27 */     ArrayList<WorldGenVillagePieceWeight> arraylist = new ArrayList();
/*     */     
/*  29 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageHouse.class, 4, MathHelper.nextInt(random, 2 + i, 4 + i * 2)));
/*  30 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageTemple.class, 20, MathHelper.nextInt(random, 0 + i, 1 + i)));
/*  31 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageLibrary.class, 20, MathHelper.nextInt(random, 0 + i, 2 + i)));
/*  32 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageHut.class, 3, MathHelper.nextInt(random, 2 + i, 5 + i * 3)));
/*  33 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageButcher.class, 15, MathHelper.nextInt(random, 0 + i, 2 + i)));
/*  34 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageFarm2.class, 3, MathHelper.nextInt(random, 1 + i, 4 + i)));
/*  35 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageFarm.class, 3, MathHelper.nextInt(random, 2 + i, 4 + i * 2)));
/*  36 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageBlacksmith.class, 15, MathHelper.nextInt(random, 0, 1 + i)));
/*  37 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageHouse2.class, 8, MathHelper.nextInt(random, 0 + i, 3 + i * 2)));
/*  38 */     Iterator<WorldGenVillagePieceWeight> iterator = arraylist.iterator();
/*     */     
/*  40 */     while (iterator.hasNext()) {
/*  41 */       if (((WorldGenVillagePieceWeight)iterator.next()).d == 0) {
/*  42 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */     
/*  46 */     return arraylist;
/*     */   }
/*     */   
/*     */   private static int a(List list) {
/*  50 */     boolean flag = false;
/*  51 */     int i = 0;
/*     */ 
/*     */ 
/*     */     
/*  55 */     for (Iterator<WorldGenVillagePieceWeight> iterator = list.iterator(); iterator.hasNext(); i += worldgenvillagepieceweight.b) {
/*  56 */       WorldGenVillagePieceWeight worldgenvillagepieceweight = iterator.next();
/*  57 */       if (worldgenvillagepieceweight.d > 0 && worldgenvillagepieceweight.c < worldgenvillagepieceweight.d) {
/*  58 */         flag = true;
/*     */       }
/*     */     } 
/*     */     
/*  62 */     return flag ? i : -1;
/*     */   }
/*     */   
/*     */   private static WorldGenVillagePiece a(WorldGenVillageStartPiece worldgenvillagestartpiece, WorldGenVillagePieceWeight worldgenvillagepieceweight, List list, Random random, int i, int j, int k, int l, int i1) {
/*  66 */     Class<WorldGenVillageHouse> oclass = worldgenvillagepieceweight.a;
/*  67 */     Object object = null;
/*     */     
/*  69 */     if (oclass == WorldGenVillageHouse.class) {
/*  70 */       object = WorldGenVillageHouse.a(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*  71 */     } else if (oclass == WorldGenVillageTemple.class) {
/*  72 */       object = WorldGenVillageTemple.a(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*  73 */     } else if (oclass == WorldGenVillageLibrary.class) {
/*  74 */       object = WorldGenVillageLibrary.a(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*  75 */     } else if (oclass == WorldGenVillageHut.class) {
/*  76 */       object = WorldGenVillageHut.a(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*  77 */     } else if (oclass == WorldGenVillageButcher.class) {
/*  78 */       object = WorldGenVillageButcher.a(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*  79 */     } else if (oclass == WorldGenVillageFarm2.class) {
/*  80 */       object = WorldGenVillageFarm2.a(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*  81 */     } else if (oclass == WorldGenVillageFarm.class) {
/*  82 */       object = WorldGenVillageFarm.a(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*  83 */     } else if (oclass == WorldGenVillageBlacksmith.class) {
/*  84 */       object = WorldGenVillageBlacksmith.a(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*  85 */     } else if (oclass == WorldGenVillageHouse2.class) {
/*  86 */       object = WorldGenVillageHouse2.a(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*     */     } 
/*     */     
/*  89 */     return (WorldGenVillagePiece)object;
/*     */   }
/*     */   
/*     */   private static WorldGenVillagePiece c(WorldGenVillageStartPiece worldgenvillagestartpiece, List list, Random random, int i, int j, int k, int l, int i1) {
/*  93 */     int j1 = a(worldgenvillagestartpiece.e);
/*     */     
/*  95 */     if (j1 <= 0) {
/*  96 */       return null;
/*     */     }
/*  98 */     int k1 = 0;
/*     */     
/* 100 */     while (k1 < 5) {
/* 101 */       k1++;
/* 102 */       int l1 = random.nextInt(j1);
/* 103 */       Iterator<WorldGenVillagePieceWeight> iterator = worldgenvillagestartpiece.e.iterator();
/*     */       
/* 105 */       while (iterator.hasNext()) {
/* 106 */         WorldGenVillagePieceWeight worldgenvillagepieceweight = iterator.next();
/*     */         
/* 108 */         l1 -= worldgenvillagepieceweight.b;
/* 109 */         if (l1 < 0) {
/* 110 */           if (!worldgenvillagepieceweight.a(i1) || (worldgenvillagepieceweight == worldgenvillagestartpiece.d && worldgenvillagestartpiece.e.size() > 1)) {
/*     */             break;
/*     */           }
/*     */           
/* 114 */           WorldGenVillagePiece worldgenvillagepiece = a(worldgenvillagestartpiece, worldgenvillagepieceweight, list, random, i, j, k, l, i1);
/*     */           
/* 116 */           if (worldgenvillagepiece != null) {
/* 117 */             worldgenvillagepieceweight.c++;
/* 118 */             worldgenvillagestartpiece.d = worldgenvillagepieceweight;
/* 119 */             if (!worldgenvillagepieceweight.a()) {
/* 120 */               worldgenvillagestartpiece.e.remove(worldgenvillagepieceweight);
/*     */             }
/*     */             
/* 123 */             return worldgenvillagepiece;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     StructureBoundingBox structureboundingbox = WorldGenVillageLight.a(worldgenvillagestartpiece, list, random, i, j, k, l);
/*     */     
/* 131 */     if (structureboundingbox != null) {
/* 132 */       return new WorldGenVillageLight(worldgenvillagestartpiece, i1, random, structureboundingbox, l);
/*     */     }
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static StructurePiece d(WorldGenVillageStartPiece worldgenvillagestartpiece, List<WorldGenVillagePiece> list, Random random, int i, int j, int k, int l, int i1) {
/* 140 */     if (i1 > 50)
/* 141 */       return null; 
/* 142 */     if (Math.abs(i - (worldgenvillagestartpiece.c()).a) <= 112 && Math.abs(k - (worldgenvillagestartpiece.c()).c) <= 112) {
/* 143 */       WorldGenVillagePiece worldgenvillagepiece = c(worldgenvillagestartpiece, list, random, i, j, k, l, i1 + 1);
/*     */       
/* 145 */       if (worldgenvillagepiece != null) {
/* 146 */         int j1 = (worldgenvillagepiece.f.a + worldgenvillagepiece.f.d) / 2;
/* 147 */         int k1 = (worldgenvillagepiece.f.c + worldgenvillagepiece.f.f) / 2;
/* 148 */         int l1 = worldgenvillagepiece.f.d - worldgenvillagepiece.f.a;
/* 149 */         int i2 = worldgenvillagepiece.f.f - worldgenvillagepiece.f.c;
/* 150 */         int j2 = (l1 > i2) ? l1 : i2;
/*     */         
/* 152 */         if (worldgenvillagestartpiece.e().a(j1, k1, j2 / 2 + 4, WorldGenVillage.e)) {
/* 153 */           list.add(worldgenvillagepiece);
/* 154 */           worldgenvillagestartpiece.i.add(worldgenvillagepiece);
/* 155 */           return worldgenvillagepiece;
/*     */         } 
/*     */       } 
/*     */       
/* 159 */       return null;
/*     */     } 
/* 161 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static StructurePiece e(WorldGenVillageStartPiece worldgenvillagestartpiece, List<WorldGenVillageRoad> list, Random random, int i, int j, int k, int l, int i1) {
/* 166 */     if (i1 > 3 + worldgenvillagestartpiece.c)
/* 167 */       return null; 
/* 168 */     if (Math.abs(i - (worldgenvillagestartpiece.c()).a) <= 112 && Math.abs(k - (worldgenvillagestartpiece.c()).c) <= 112) {
/* 169 */       StructureBoundingBox structureboundingbox = WorldGenVillageRoad.a(worldgenvillagestartpiece, list, random, i, j, k, l);
/*     */       
/* 171 */       if (structureboundingbox != null && structureboundingbox.b > 10) {
/* 172 */         WorldGenVillageRoad worldgenvillageroad = new WorldGenVillageRoad(worldgenvillagestartpiece, i1, random, structureboundingbox, l);
/* 173 */         int j1 = (worldgenvillageroad.f.a + worldgenvillageroad.f.d) / 2;
/* 174 */         int k1 = (worldgenvillageroad.f.c + worldgenvillageroad.f.f) / 2;
/* 175 */         int l1 = worldgenvillageroad.f.d - worldgenvillageroad.f.a;
/* 176 */         int i2 = worldgenvillageroad.f.f - worldgenvillageroad.f.c;
/* 177 */         int j2 = (l1 > i2) ? l1 : i2;
/*     */         
/* 179 */         if (worldgenvillagestartpiece.e().a(j1, k1, j2 / 2 + 4, WorldGenVillage.e)) {
/* 180 */           list.add(worldgenvillageroad);
/* 181 */           worldgenvillagestartpiece.j.add(worldgenvillageroad);
/* 182 */           return worldgenvillageroad;
/*     */         } 
/*     */       } 
/*     */       
/* 186 */       return null;
/*     */     } 
/* 188 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   static StructurePiece a(WorldGenVillageStartPiece worldgenvillagestartpiece, List list, Random random, int i, int j, int k, int l, int i1) {
/* 193 */     return d(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*     */   }
/*     */   
/*     */   static StructurePiece b(WorldGenVillageStartPiece worldgenvillagestartpiece, List list, Random random, int i, int j, int k, int l, int i1) {
/* 197 */     return e(worldgenvillagestartpiece, list, random, i, j, k, l, i1);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillagePieces.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */