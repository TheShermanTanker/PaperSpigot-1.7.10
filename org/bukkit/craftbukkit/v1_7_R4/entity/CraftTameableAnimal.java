/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.server.v1_7_R4.EntityAgeable;
/*    */ import net.minecraft.server.v1_7_R4.EntityAnimal;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityTameableAnimal;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.entity.AnimalTamer;
/*    */ 
/*    */ public class CraftTameableAnimal extends CraftAnimals implements Tameable, Creature {
/*    */   public CraftTameableAnimal(CraftServer server, EntityTameableAnimal entity) {
/* 13 */     super(server, (EntityAnimal)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityTameableAnimal getHandle() {
/* 18 */     return (EntityTameableAnimal)super.getHandle();
/*    */   }
/*    */   
/*    */   public UUID getOwnerUUID() {
/*    */     try {
/* 23 */       return UUID.fromString(getHandle().getOwnerUUID());
/* 24 */     } catch (IllegalArgumentException ex) {
/* 25 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setOwnerUUID(UUID uuid) {
/* 30 */     if (uuid == null) {
/* 31 */       getHandle().setOwnerUUID("");
/*    */     } else {
/* 33 */       getHandle().setOwnerUUID(uuid.toString());
/*    */     } 
/*    */   }
/*    */   public AnimalTamer getOwner() {
/*    */     OfflinePlayer offlinePlayer;
/* 38 */     if (getOwnerUUID() == null) {
/* 39 */       return null;
/*    */     }
/*    */     
/* 42 */     Player player = getServer().getPlayer(getOwnerUUID());
/* 43 */     if (player == null) {
/* 44 */       offlinePlayer = getServer().getOfflinePlayer(getOwnerUUID());
/*    */     }
/*    */     
/* 47 */     return (AnimalTamer)offlinePlayer;
/*    */   }
/*    */   
/*    */   public boolean isTamed() {
/* 51 */     return getHandle().isTamed();
/*    */   }
/*    */   
/*    */   public void setOwner(AnimalTamer tamer) {
/* 55 */     if (tamer != null) {
/* 56 */       setTamed(true);
/* 57 */       getHandle().setPathEntity(null);
/* 58 */       setOwnerUUID(tamer.getUniqueId());
/*    */     } else {
/* 60 */       setTamed(false);
/* 61 */       setOwnerUUID(null);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setTamed(boolean tame) {
/* 66 */     getHandle().setTamed(tame);
/* 67 */     if (!tame) {
/* 68 */       setOwnerUUID(null);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isSitting() {
/* 73 */     return getHandle().isSitting();
/*    */   }
/*    */   
/*    */   public void setSitting(boolean sitting) {
/* 77 */     getHandle().getGoalSit().setSitting(sitting);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 82 */     return getClass().getSimpleName() + "{owner=" + getOwner() + ",tamed=" + isTamed() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftTameableAnimal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */