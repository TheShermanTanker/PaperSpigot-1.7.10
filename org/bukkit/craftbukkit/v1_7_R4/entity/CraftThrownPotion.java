/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityPotion;
/*    */ import net.minecraft.server.v1_7_R4.EntityProjectile;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.ThrownPotion;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.potion.Potion;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ 
/*    */ public class CraftThrownPotion extends CraftProjectile implements ThrownPotion {
/*    */   public CraftThrownPotion(CraftServer server, EntityPotion entity) {
/* 19 */     super(server, (Entity)entity);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<PotionEffect> getEffects() {
/* 25 */     return Potion.getBrewer().getEffectsFromDamage(getHandle().getPotionValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItem() {
/* 30 */     getHandle().getPotionValue();
/*    */     
/* 32 */     return CraftItemStack.asBukkitCopy((getHandle()).item);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setItem(ItemStack item) {
/* 37 */     Validate.notNull(item, "ItemStack cannot be null.");
/*    */ 
/*    */     
/* 40 */     Validate.isTrue((item.getType() == Material.POTION), "ItemStack must be a potion. This item stack was " + item.getType() + ".");
/*    */     
/* 42 */     (getHandle()).item = CraftItemStack.asNMSCopy(item);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPotion getHandle() {
/* 47 */     return (EntityPotion)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return "CraftThrownPotion";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 56 */     return EntityType.SPLASH_POTION;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftThrownPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */