/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.util.Iterator;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.entity.EntityInteractEvent;
/*    */ 
/*    */ public class BlockPressurePlateWeighted extends BlockPressurePlateAbstract {
/*    */   protected BlockPressurePlateWeighted(String s, Material material, int i) {
/*  9 */     super(s, material);
/* 10 */     this.a = i;
/*    */   }
/*    */   private final int a;
/*    */   
/*    */   protected int e(World world, int i, int j, int k) {
/* 15 */     int l = 0;
/* 16 */     Iterator<Entity> iterator = world.a(Entity.class, a(i, j, k)).iterator();
/*    */     
/* 18 */     while (iterator.hasNext()) {
/* 19 */       EntityInteractEvent entityInteractEvent; Entity entity = iterator.next();
/*    */ 
/*    */ 
/*    */       
/* 23 */       if (entity instanceof EntityHuman) {
/* 24 */         PlayerInteractEvent playerInteractEvent = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, i, j, k, -1, null);
/*    */       } else {
/* 26 */         entityInteractEvent = new EntityInteractEvent((Entity)entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
/* 27 */         world.getServer().getPluginManager().callEvent((Event)entityInteractEvent);
/*    */       } 
/*    */ 
/*    */       
/* 31 */       if (!entityInteractEvent.isCancelled()) {
/* 32 */         l++;
/*    */       }
/*    */     } 
/*    */     
/* 36 */     l = Math.min(l, this.a);
/*    */ 
/*    */     
/* 39 */     if (l <= 0) {
/* 40 */       return 0;
/*    */     }
/*    */     
/* 43 */     float f = Math.min(this.a, l) / this.a;
/* 44 */     return MathHelper.f(f * 15.0F);
/*    */   }
/*    */   
/*    */   protected int c(int i) {
/* 48 */     return i;
/*    */   }
/*    */   
/*    */   protected int d(int i) {
/* 52 */     return i;
/*    */   }
/*    */   
/*    */   public int a(World world) {
/* 56 */     return 10;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPressurePlateWeighted.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */