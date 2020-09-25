/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class WorldGenLargeFeature
/*    */   extends StructureGenerator
/*    */ {
/* 13 */   private static List e = Arrays.asList(new BiomeBase[] { BiomeBase.DESERT, BiomeBase.DESERT_HILLS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS, BiomeBase.SWAMPLAND });
/*    */   private List f;
/*    */   private int g;
/*    */   private int h;
/*    */   
/*    */   public WorldGenLargeFeature() {
/* 19 */     this.f = new ArrayList();
/* 20 */     this.g = 32;
/* 21 */     this.h = 8;
/* 22 */     this.f.add(new BiomeMeta(EntityWitch.class, 1, 1, 1));
/*    */   }
/*    */   
/*    */   public WorldGenLargeFeature(Map map) {
/* 26 */     this();
/* 27 */     Iterator<Map.Entry> iterator = map.entrySet().iterator();
/*    */     
/* 29 */     while (iterator.hasNext()) {
/* 30 */       Map.Entry entry = iterator.next();
/*    */       
/* 32 */       if (((String)entry.getKey()).equals("distance")) {
/* 33 */         this.g = MathHelper.a((String)entry.getValue(), this.g, this.h + 1);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public String a() {
/* 39 */     return "Temple";
/*    */   }
/*    */   
/*    */   protected boolean a(int i, int j) {
/* 43 */     int k = i;
/* 44 */     int l = j;
/*    */     
/* 46 */     if (i < 0) {
/* 47 */       i -= this.g - 1;
/*    */     }
/*    */     
/* 50 */     if (j < 0) {
/* 51 */       j -= this.g - 1;
/*    */     }
/*    */     
/* 54 */     int i1 = i / this.g;
/* 55 */     int j1 = j / this.g;
/* 56 */     Random random = this.c.A(i1, j1, this.c.spigotConfig.largeFeatureSeed);
/*    */     
/* 58 */     i1 *= this.g;
/* 59 */     j1 *= this.g;
/* 60 */     i1 += random.nextInt(this.g - this.h);
/* 61 */     j1 += random.nextInt(this.g - this.h);
/* 62 */     if (k == i1 && l == j1) {
/* 63 */       BiomeBase biomebase = this.c.getWorldChunkManager().getBiome(k * 16 + 8, l * 16 + 8);
/* 64 */       Iterator<BiomeBase> iterator = e.iterator();
/*    */       
/* 66 */       while (iterator.hasNext()) {
/* 67 */         BiomeBase biomebase1 = iterator.next();
/*    */         
/* 69 */         if (biomebase == biomebase1) {
/* 70 */           return true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   protected StructureStart b(int i, int j) {
/* 79 */     return new WorldGenLargeFeatureStart(this.c, this.b, i, j);
/*    */   }
/*    */   
/*    */   public boolean a(int i, int j, int k) {
/* 83 */     StructureStart structurestart = c(i, j, k);
/*    */     
/* 85 */     if (structurestart != null && structurestart instanceof WorldGenLargeFeatureStart && !structurestart.a.isEmpty()) {
/* 86 */       StructurePiece structurepiece = structurestart.a.getFirst();
/*    */       
/* 88 */       return structurepiece instanceof WorldGenWitchHut;
/*    */     } 
/* 90 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public List b() {
/* 95 */     return this.f;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenLargeFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */