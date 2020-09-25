/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class WorldGenVillage
/*    */   extends StructureGenerator
/*    */ {
/* 12 */   public static final List e = Arrays.asList(new BiomeBase[] { BiomeBase.PLAINS, BiomeBase.DESERT, BiomeBase.SAVANNA });
/*    */ 
/*    */   
/*    */   private int f;
/*    */ 
/*    */   
/* 18 */   private int g = 32;
/* 19 */   private int h = 8;
/*    */   public WorldGenVillage() {}
/*    */   
/*    */   public WorldGenVillage(Map map) {
/* 23 */     this();
/* 24 */     Iterator<Map.Entry> iterator = map.entrySet().iterator();
/*    */     
/* 26 */     while (iterator.hasNext()) {
/* 27 */       Map.Entry entry = iterator.next();
/*    */       
/* 29 */       if (((String)entry.getKey()).equals("size")) {
/* 30 */         this.f = MathHelper.a((String)entry.getValue(), this.f, 0); continue;
/* 31 */       }  if (((String)entry.getKey()).equals("distance")) {
/* 32 */         this.g = MathHelper.a((String)entry.getValue(), this.g, this.h + 1);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public String a() {
/* 38 */     return "Village";
/*    */   }
/*    */   
/*    */   protected boolean a(int i, int j) {
/* 42 */     int k = i;
/* 43 */     int l = j;
/*    */     
/* 45 */     if (i < 0) {
/* 46 */       i -= this.g - 1;
/*    */     }
/*    */     
/* 49 */     if (j < 0) {
/* 50 */       j -= this.g - 1;
/*    */     }
/*    */     
/* 53 */     int i1 = i / this.g;
/* 54 */     int j1 = j / this.g;
/* 55 */     Random random = this.c.A(i1, j1, this.c.spigotConfig.villageSeed);
/*    */     
/* 57 */     i1 *= this.g;
/* 58 */     j1 *= this.g;
/* 59 */     i1 += random.nextInt(this.g - this.h);
/* 60 */     j1 += random.nextInt(this.g - this.h);
/* 61 */     if (k == i1 && l == j1) {
/* 62 */       boolean flag = this.c.getWorldChunkManager().a(k * 16 + 8, l * 16 + 8, 0, e);
/*    */       
/* 64 */       if (flag) {
/* 65 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 69 */     return false;
/*    */   }
/*    */   
/*    */   protected StructureStart b(int i, int j) {
/* 73 */     return new WorldGenVillageStart(this.c, this.b, i, j, this.f);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */