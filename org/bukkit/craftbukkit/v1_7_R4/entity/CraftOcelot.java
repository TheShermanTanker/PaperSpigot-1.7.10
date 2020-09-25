/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityOcelot;
/*    */ import net.minecraft.server.v1_7_R4.EntityTameableAnimal;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.Ocelot;
/*    */ 
/*    */ public class CraftOcelot extends CraftTameableAnimal implements Ocelot {
/*    */   public CraftOcelot(CraftServer server, EntityOcelot wolf) {
/* 11 */     super(server, (EntityTameableAnimal)wolf);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityOcelot getHandle() {
/* 16 */     return (EntityOcelot)this.entity;
/*    */   }
/*    */   
/*    */   public Ocelot.Type getCatType() {
/* 20 */     return Ocelot.Type.getType(getHandle().getCatType());
/*    */   }
/*    */   
/*    */   public void setCatType(Ocelot.Type type) {
/* 24 */     Validate.notNull(type, "Cat type cannot be null");
/* 25 */     getHandle().setCatType(type.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType getType() {
/* 30 */     return EntityType.OCELOT;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftOcelot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */