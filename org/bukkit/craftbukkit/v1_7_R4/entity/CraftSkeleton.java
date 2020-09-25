/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityMonster;
/*    */ import net.minecraft.server.v1_7_R4.EntitySkeleton;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Skeleton;
/*    */ 
/*    */ public class CraftSkeleton extends CraftMonster implements Skeleton {
/*    */   public CraftSkeleton(CraftServer server, EntitySkeleton entity) {
/* 13 */     super(server, (EntityMonster)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySkeleton getHandle() {
/* 18 */     return (EntitySkeleton)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 23 */     return "CraftSkeleton";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 27 */     return EntityType.SKELETON;
/*    */   }
/*    */   
/*    */   public Skeleton.SkeletonType getSkeletonType() {
/* 31 */     return Skeleton.SkeletonType.getType(getHandle().getSkeletonType());
/*    */   }
/*    */   
/*    */   public void setSkeletonType(Skeleton.SkeletonType type) {
/* 35 */     Validate.notNull(type);
/* 36 */     getHandle().setSkeletonType(type.getId());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftSkeleton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */