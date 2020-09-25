/*    */ package org.spigotmc;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
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
/*    */ public class TrackingRange
/*    */ {
/*    */   public static int getEntityTrackingRange(Entity entity, int defaultRange) {
/* 24 */     SpigotWorldConfig config = entity.world.spigotConfig;
/* 25 */     if (entity instanceof net.minecraft.server.v1_7_R4.EntityPlayer)
/*    */     {
/* 27 */       return config.playerTrackingRange; } 
/* 28 */     if (entity.activationType == 1)
/*    */     {
/* 30 */       return config.monsterTrackingRange; } 
/* 31 */     if (entity instanceof net.minecraft.server.v1_7_R4.EntityGhast) {
/*    */       
/* 33 */       if (config.monsterTrackingRange > config.monsterActivationRange)
/*    */       {
/* 35 */         return config.monsterTrackingRange;
/*    */       }
/*    */       
/* 38 */       return config.monsterActivationRange;
/*    */     } 
/* 40 */     if (entity.activationType == 2)
/*    */     {
/* 42 */       return config.animalTrackingRange; } 
/* 43 */     if (entity instanceof net.minecraft.server.v1_7_R4.EntityItemFrame || entity instanceof net.minecraft.server.v1_7_R4.EntityPainting || entity instanceof net.minecraft.server.v1_7_R4.EntityItem || entity instanceof net.minecraft.server.v1_7_R4.EntityExperienceOrb)
/*    */     {
/* 45 */       return config.miscTrackingRange;
/*    */     }
/*    */     
/* 48 */     return config.otherTrackingRange;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\TrackingRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */