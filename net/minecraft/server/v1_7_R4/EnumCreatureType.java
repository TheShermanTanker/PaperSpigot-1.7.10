/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumCreatureType
/*    */ {
/* 12 */   MONSTER(IMonster.class, 70, Material.AIR, false, false),
/* 13 */   CREATURE(EntityAnimal.class, 10, Material.AIR, true, true),
/* 14 */   AMBIENT(EntityAmbient.class, 15, Material.AIR, true, false),
/* 15 */   WATER_CREATURE(EntityWaterAnimal.class, 5, Material.WATER, true, false);
/*    */   
/*    */   private final Class e;
/*    */   
/*    */   private final int f;
/*    */   
/*    */   private final Material g;
/*    */   
/*    */   private final boolean h;
/*    */   private final boolean i;
/*    */   
/*    */   EnumCreatureType(Class paramClass, int paramInt1, Material paramMaterial, boolean paramBoolean1, boolean paramBoolean2) {
/* 27 */     this.e = paramClass;
/* 28 */     this.f = paramInt1;
/* 29 */     this.g = paramMaterial;
/* 30 */     this.h = paramBoolean1;
/* 31 */     this.i = paramBoolean2;
/*    */   }
/*    */   
/*    */   public Class a() {
/* 35 */     return this.e;
/*    */   }
/*    */   
/*    */   public int b() {
/* 39 */     return this.f;
/*    */   }
/*    */   
/*    */   public Material c() {
/* 43 */     return this.g;
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 47 */     return this.h;
/*    */   }
/*    */   
/*    */   public boolean e() {
/* 51 */     return this.i;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumCreatureType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */