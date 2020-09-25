/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.entity.EntityInteractEvent;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class BlockPressurePlateBinary extends BlockPressurePlateAbstract {
/*    */   protected BlockPressurePlateBinary(String s, Material material, EnumMobType enummobtype) {
/* 13 */     super(s, material);
/* 14 */     this.a = enummobtype;
/*    */   }
/*    */   private EnumMobType a;
/*    */   protected int d(int i) {
/* 18 */     return (i > 0) ? 1 : 0;
/*    */   }
/*    */   
/*    */   protected int c(int i) {
/* 22 */     return (i == 1) ? 15 : 0;
/*    */   }
/*    */   
/*    */   protected int e(World world, int i, int j, int k) {
/* 26 */     List list = null;
/*    */     
/* 28 */     if (this.a == EnumMobType.EVERYTHING) {
/* 29 */       list = world.getEntities((Entity)null, a(i, j, k));
/*    */     }
/*    */     
/* 32 */     if (this.a == EnumMobType.MOBS) {
/* 33 */       list = world.a(EntityLiving.class, a(i, j, k));
/*    */     }
/*    */     
/* 36 */     if (this.a == EnumMobType.PLAYERS) {
/* 37 */       list = world.a(EntityHuman.class, a(i, j, k));
/*    */     }
/*    */     
/* 40 */     if (list != null && !list.isEmpty()) {
/* 41 */       Iterator<Entity> iterator = list.iterator();
/*    */       
/* 43 */       while (iterator.hasNext()) {
/* 44 */         Entity entity = iterator.next();
/*    */ 
/*    */         
/* 47 */         if (c(world.getData(i, j, k)) == 0) {
/* 48 */           EntityInteractEvent entityInteractEvent; CraftWorld craftWorld = world.getWorld();
/* 49 */           PluginManager manager = world.getServer().getPluginManager();
/*    */ 
/*    */           
/* 52 */           if (entity instanceof EntityHuman) {
/* 53 */             PlayerInteractEvent playerInteractEvent = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, i, j, k, -1, null);
/*    */           } else {
/* 55 */             entityInteractEvent = new EntityInteractEvent((Entity)entity.getBukkitEntity(), craftWorld.getBlockAt(i, j, k));
/* 56 */             manager.callEvent((Event)entityInteractEvent);
/*    */           } 
/*    */ 
/*    */           
/* 60 */           if (entityInteractEvent.isCancelled()) {
/*    */             continue;
/*    */           }
/*    */         } 
/*    */ 
/*    */         
/* 66 */         if (!entity.az()) {
/* 67 */           return 15;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPressurePlateBinary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */