/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalEatTile
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityInsentient b;
/*    */   private World c;
/*    */   int a;
/*    */   
/*    */   public PathfinderGoalEatTile(EntityInsentient entityinsentient) {
/* 15 */     this.b = entityinsentient;
/* 16 */     this.c = entityinsentient.world;
/* 17 */     a(7);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 21 */     if (this.b.aI().nextInt(this.b.isBaby() ? 50 : 1000) != 0) {
/* 22 */       return false;
/*    */     }
/* 24 */     int i = MathHelper.floor(this.b.locX);
/* 25 */     int j = MathHelper.floor(this.b.locY);
/* 26 */     int k = MathHelper.floor(this.b.locZ);
/*    */     
/* 28 */     return (this.c.getType(i, j, k) == Blocks.LONG_GRASS && this.c.getData(i, j, k) == 1) ? true : ((this.c.getType(i, j - 1, k) == Blocks.GRASS));
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 33 */     this.a = 40;
/* 34 */     this.c.broadcastEntityEffect(this.b, (byte)10);
/* 35 */     this.b.getNavigation().h();
/*    */   }
/*    */   
/*    */   public void d() {
/* 39 */     this.a = 0;
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 43 */     return (this.a > 0);
/*    */   }
/*    */   
/*    */   public int f() {
/* 47 */     return this.a;
/*    */   }
/*    */   
/*    */   public void e() {
/* 51 */     this.a = Math.max(0, this.a - 1);
/* 52 */     if (this.a == 4) {
/* 53 */       int i = MathHelper.floor(this.b.locX);
/* 54 */       int j = MathHelper.floor(this.b.locY);
/* 55 */       int k = MathHelper.floor(this.b.locZ);
/*    */       
/* 57 */       if (this.c.getType(i, j, k) == Blocks.LONG_GRASS) {
/*    */         
/* 59 */         if (!CraftEventFactory.callEntityChangeBlockEvent(this.b, this.b.world.getWorld().getBlockAt(i, j, k), Material.AIR, !this.c.getGameRules().getBoolean("mobGriefing")).isCancelled()) {
/* 60 */           this.c.setAir(i, j, k, false);
/*    */         }
/*    */         
/* 63 */         this.b.p();
/* 64 */       } else if (this.c.getType(i, j - 1, k) == Blocks.GRASS) {
/*    */         
/* 66 */         if (!CraftEventFactory.callEntityChangeBlockEvent(this.b, this.b.world.getWorld().getBlockAt(i, j - 1, k), Material.DIRT, !this.c.getGameRules().getBoolean("mobGriefing")).isCancelled()) {
/* 67 */           this.c.triggerEffect(2001, i, j - 1, k, Block.getId(Blocks.GRASS));
/* 68 */           this.c.setTypeAndData(i, j - 1, k, Blocks.DIRT, 0, 2);
/*    */         } 
/*    */         
/* 71 */         this.b.p();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalEatTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */