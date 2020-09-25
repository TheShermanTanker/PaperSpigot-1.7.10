/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityComplexPart;
/*    */ import net.minecraft.server.v1_7_R4.EntityEnderDragon;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.ComplexEntityPart;
/*    */ import org.bukkit.entity.EnderDragon;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftEnderDragon
/*    */   extends CraftComplexLivingEntity
/*    */   implements EnderDragon {
/*    */   public CraftEnderDragon(CraftServer server, EntityEnderDragon entity) {
/* 18 */     super(server, (EntityLiving)entity);
/*    */   }
/*    */   
/*    */   public Set<ComplexEntityPart> getParts() {
/* 22 */     ImmutableSet.Builder<ComplexEntityPart> builder = ImmutableSet.builder();
/*    */     
/* 24 */     for (EntityComplexPart part : (getHandle()).children) {
/* 25 */       builder.add(part.getBukkitEntity());
/*    */     }
/*    */     
/* 28 */     return (Set<ComplexEntityPart>)builder.build();
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityEnderDragon getHandle() {
/* 33 */     return (EntityEnderDragon)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     return "CraftEnderDragon";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 42 */     return EntityType.ENDER_DRAGON;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftEnderDragon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */