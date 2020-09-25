/*     */ package org.bukkit.inventory.meta;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ItemMeta
/*     */   extends Cloneable, ConfigurationSerializable
/*     */ {
/*     */   boolean hasDisplayName();
/*     */   
/*     */   String getDisplayName();
/*     */   
/*     */   void setDisplayName(String paramString);
/*     */   
/*     */   boolean hasLore();
/*     */   
/*     */   List<String> getLore();
/*     */   
/*     */   void setLore(List<String> paramList);
/*     */   
/*     */   boolean hasEnchants();
/*     */   
/*     */   boolean hasEnchant(Enchantment paramEnchantment);
/*     */   
/*     */   int getEnchantLevel(Enchantment paramEnchantment);
/*     */   
/*     */   Map<Enchantment, Integer> getEnchants();
/*     */   
/*     */   boolean addEnchant(Enchantment paramEnchantment, int paramInt, boolean paramBoolean);
/*     */   
/*     */   boolean removeEnchant(Enchantment paramEnchantment);
/*     */   
/*     */   boolean hasConflictingEnchant(Enchantment paramEnchantment);
/*     */   
/*     */   Spigot spigot();
/*     */   
/*     */   ItemMeta clone();
/*     */   
/*     */   public static class Spigot
/*     */   {
/*     */     public void setUnbreakable(boolean unbreakable) {
/* 138 */       throw new UnsupportedOperationException("Not supported yet.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isUnbreakable() {
/* 148 */       throw new UnsupportedOperationException("Not supported yet.");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\ItemMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */