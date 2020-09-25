/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityMinecartMobSpawner
/*    */   extends EntityMinecartAbstract
/*    */ {
/* 13 */   private final MobSpawnerAbstract a = new MobSpawnerMinecart(this);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityMinecartMobSpawner(World paramWorld) {
/* 41 */     super(paramWorld);
/*    */   }
/*    */   
/*    */   public EntityMinecartMobSpawner(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 45 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*    */   }
/*    */ 
/*    */   
/*    */   public int m() {
/* 50 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public Block o() {
/* 55 */     return Blocks.MOB_SPAWNER;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 60 */     super.a(paramNBTTagCompound);
/* 61 */     this.a.a(paramNBTTagCompound);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 66 */     super.b(paramNBTTagCompound);
/* 67 */     this.a.b(paramNBTTagCompound);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void h() {
/* 77 */     super.h();
/* 78 */     this.a.g();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMinecartMobSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */