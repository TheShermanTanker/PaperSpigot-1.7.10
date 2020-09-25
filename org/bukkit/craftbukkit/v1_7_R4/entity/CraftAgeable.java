/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityAgeable;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ 
/*    */ public class CraftAgeable extends CraftCreature implements Ageable {
/*    */   public CraftAgeable(CraftServer server, EntityAgeable entity) {
/* 10 */     super(server, (EntityCreature)entity);
/*    */   }
/*    */   
/*    */   public int getAge() {
/* 14 */     return getHandle().getAge();
/*    */   }
/*    */   
/*    */   public void setAge(int age) {
/* 18 */     getHandle().setAge(age);
/*    */   }
/*    */   
/*    */   public void setAgeLock(boolean lock) {
/* 22 */     (getHandle()).ageLocked = lock;
/*    */   }
/*    */   
/*    */   public boolean getAgeLock() {
/* 26 */     return (getHandle()).ageLocked;
/*    */   }
/*    */   
/*    */   public void setBaby() {
/* 30 */     if (isAdult()) {
/* 31 */       setAge(-24000);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setAdult() {
/* 36 */     if (!isAdult()) {
/* 37 */       setAge(0);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isAdult() {
/* 42 */     return (getAge() >= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBreed() {
/* 47 */     return (getAge() == 0);
/*    */   }
/*    */   
/*    */   public void setBreed(boolean breed) {
/* 51 */     if (breed) {
/* 52 */       setAge(0);
/* 53 */     } else if (isAdult()) {
/* 54 */       setAge(6000);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityAgeable getHandle() {
/* 60 */     return (EntityAgeable)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "CraftAgeable";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftAgeable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */