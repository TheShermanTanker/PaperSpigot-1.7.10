/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityMagmaCube;
/*    */ import net.minecraft.server.v1_7_R4.EntitySlime;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.MagmaCube;
/*    */ 
/*    */ public class CraftMagmaCube extends CraftSlime implements MagmaCube {
/*    */   public CraftMagmaCube(CraftServer server, EntityMagmaCube entity) {
/* 12 */     super(server, (EntitySlime)entity);
/*    */   }
/*    */   
/*    */   public EntityMagmaCube getHandle() {
/* 16 */     return (EntityMagmaCube)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     return "CraftMagmaCube";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.MAGMA_CUBE;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftMagmaCube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */