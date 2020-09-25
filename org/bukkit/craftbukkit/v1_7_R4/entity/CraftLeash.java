/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityHanging;
/*    */ import net.minecraft.server.v1_7_R4.EntityLeash;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.LeashHitch;
/*    */ 
/*    */ public class CraftLeash extends CraftHanging implements LeashHitch {
/*    */   public CraftLeash(CraftServer server, EntityLeash entity) {
/* 11 */     super(server, (EntityHanging)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityLeash getHandle() {
/* 16 */     return (EntityLeash)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     return "CraftLeash";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.LEASH_HITCH;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftLeash.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */