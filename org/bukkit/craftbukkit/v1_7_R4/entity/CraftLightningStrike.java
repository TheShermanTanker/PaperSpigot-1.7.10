/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityLightning;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.LightningStrike;
/*    */ 
/*    */ public class CraftLightningStrike extends CraftEntity implements LightningStrike {
/*    */   public CraftLightningStrike(CraftServer server, EntityLightning entity) {
/* 12 */     super(server, (Entity)entity);
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
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 34 */     this.spigot = new LightningStrike.Spigot()
/*    */       {
/*    */ 
/*    */         
/*    */         public boolean isSilent()
/*    */         {
/* 40 */           return (CraftLightningStrike.this.getHandle()).isSilent;
/*    */         }
/*    */       };
/*    */   }
/*    */   private final LightningStrike.Spigot spigot;
/*    */   
/*    */   public LightningStrike.Spigot spigot() {
/* 47 */     return this.spigot;
/*    */   }
/*    */   
/*    */   public boolean isEffect() {
/*    */     return ((EntityLightning)super.getHandle()).isEffect;
/*    */   }
/*    */   
/*    */   public EntityLightning getHandle() {
/*    */     return (EntityLightning)this.entity;
/*    */   }
/*    */   
/*    */   public String toString() {
/*    */     return "CraftLightningStrike";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/*    */     return EntityType.LIGHTNING;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftLightningStrike.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */