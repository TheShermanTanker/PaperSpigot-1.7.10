/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.entity.EntityPortalEnterEvent;
/*    */ 
/*    */ public class BlockEnderPortal extends BlockContainer {
/*    */   protected BlockEnderPortal(Material material) {
/* 13 */     super(material);
/* 14 */     a(1.0F);
/*    */   }
/*    */   public static boolean a;
/*    */   public TileEntity a(World world, int i) {
/* 18 */     return new TileEntityEnderPortal();
/*    */   }
/*    */   
/*    */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 22 */     float f = 0.0625F;
/*    */     
/* 24 */     a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List list, Entity entity) {}
/*    */   
/*    */   public boolean c() {
/* 30 */     return false;
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 34 */     return false;
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 38 */     return 0;
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Entity entity) {
/* 42 */     if (entity.vehicle == null && entity.passenger == null && !world.isStatic) {
/*    */       
/* 44 */       EntityPortalEnterEvent event = new EntityPortalEnterEvent((Entity)entity.getBukkitEntity(), new Location((World)world.getWorld(), i, j, k));
/* 45 */       world.getServer().getPluginManager().callEvent((Event)event);
/*    */       
/* 47 */       entity.b(1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int b() {
/* 52 */     return -1;
/*    */   }
/*    */   
/*    */   public void onPlace(World world, int i, int j, int k) {
/* 56 */     if (!a && 
/* 57 */       world.worldProvider.dimension != 0) {
/* 58 */       world.setAir(i, j, k);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public MaterialMapColor f(int i) {
/* 64 */     return MaterialMapColor.J;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockEnderPortal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */