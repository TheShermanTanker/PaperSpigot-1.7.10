/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenNether
/*    */   extends StructureGenerator
/*    */ {
/* 11 */   private List e = new ArrayList();
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldGenNether() {
/* 16 */     this.e.add(new BiomeMeta(EntityBlaze.class, 10, 2, 3));
/* 17 */     this.e.add(new BiomeMeta(EntityPigZombie.class, 5, 4, 4));
/* 18 */     this.e.add(new BiomeMeta(EntitySkeleton.class, 10, 4, 4));
/* 19 */     this.e.add(new BiomeMeta(EntityMagmaCube.class, 3, 4, 4));
/*    */   }
/*    */ 
/*    */   
/*    */   public String a() {
/* 24 */     return "Fortress";
/*    */   }
/*    */ 
/*    */   
/*    */   public List b() {
/* 29 */     return this.e;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean a(int paramInt1, int paramInt2) {
/* 35 */     int i = paramInt1 >> 4;
/* 36 */     int j = paramInt2 >> 4;
/*    */     
/* 38 */     this.b.setSeed((i ^ j << 4) ^ this.c.getSeed());
/* 39 */     this.b.nextInt();
/*    */     
/* 41 */     if (this.b.nextInt(3) != 0) {
/* 42 */       return false;
/*    */     }
/* 44 */     if (paramInt1 != (i << 4) + 4 + this.b.nextInt(8)) {
/* 45 */       return false;
/*    */     }
/* 47 */     if (paramInt2 != (j << 4) + 4 + this.b.nextInt(8)) {
/* 48 */       return false;
/*    */     }
/* 50 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureStart b(int paramInt1, int paramInt2) {
/* 55 */     return new WorldGenNetherStart(this.c, this.b, paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenNether.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */