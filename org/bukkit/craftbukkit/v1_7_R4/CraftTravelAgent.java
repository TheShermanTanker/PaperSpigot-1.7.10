/*    */ package org.bukkit.craftbukkit.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.ChunkCoordinates;
/*    */ import net.minecraft.server.v1_7_R4.PortalTravelAgent;
/*    */ import net.minecraft.server.v1_7_R4.WorldServer;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.TravelAgent;
/*    */ 
/*    */ public class CraftTravelAgent
/*    */   extends PortalTravelAgent
/*    */   implements TravelAgent {
/* 12 */   public static TravelAgent DEFAULT = null;
/*    */   
/* 14 */   private int searchRadius = 128;
/* 15 */   private int creationRadius = 16;
/*    */   private boolean canCreatePortal = true;
/*    */   
/*    */   public CraftTravelAgent(WorldServer worldserver) {
/* 19 */     super(worldserver);
/* 20 */     if (DEFAULT == null && worldserver.dimension == 0) {
/* 21 */       DEFAULT = this;
/*    */     }
/*    */   }
/*    */   
/*    */   public Location findOrCreate(Location target) {
/* 26 */     WorldServer worldServer = ((CraftWorld)target.getWorld()).getHandle();
/* 27 */     boolean before = worldServer.chunkProviderServer.forceChunkLoad;
/* 28 */     worldServer.chunkProviderServer.forceChunkLoad = true;
/*    */     
/* 30 */     Location found = findPortal(target);
/* 31 */     if (found == null) {
/* 32 */       if (getCanCreatePortal() && createPortal(target)) {
/* 33 */         found = findPortal(target);
/*    */       } else {
/* 35 */         found = target;
/*    */       } 
/*    */     }
/*    */     
/* 39 */     worldServer.chunkProviderServer.forceChunkLoad = before;
/* 40 */     return found;
/*    */   }
/*    */   
/*    */   public Location findPortal(Location location) {
/* 44 */     PortalTravelAgent pta = ((CraftWorld)location.getWorld()).getHandle().getTravelAgent();
/* 45 */     ChunkCoordinates found = pta.findPortal(location.getX(), location.getY(), location.getZ(), getSearchRadius());
/* 46 */     return (found != null) ? new Location(location.getWorld(), found.x, found.y, found.z, location.getYaw(), location.getPitch()) : null;
/*    */   }
/*    */   
/*    */   public boolean createPortal(Location location) {
/* 50 */     PortalTravelAgent pta = ((CraftWorld)location.getWorld()).getHandle().getTravelAgent();
/* 51 */     return pta.createPortal(location.getX(), location.getY(), location.getZ(), getCreationRadius());
/*    */   }
/*    */   
/*    */   public TravelAgent setSearchRadius(int radius) {
/* 55 */     this.searchRadius = radius;
/* 56 */     return this;
/*    */   }
/*    */   
/*    */   public int getSearchRadius() {
/* 60 */     return this.searchRadius;
/*    */   }
/*    */   
/*    */   public TravelAgent setCreationRadius(int radius) {
/* 64 */     this.creationRadius = (radius < 2) ? 0 : radius;
/* 65 */     return this;
/*    */   }
/*    */   
/*    */   public int getCreationRadius() {
/* 69 */     return this.creationRadius;
/*    */   }
/*    */   
/*    */   public boolean getCanCreatePortal() {
/* 73 */     return this.canCreatePortal;
/*    */   }
/*    */   
/*    */   public void setCanCreatePortal(boolean create) {
/* 77 */     this.canCreatePortal = create;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftTravelAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */