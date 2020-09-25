/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.FireworkEffectMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.Repairable;
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaCharge
/*     */   extends CraftMetaItem implements FireworkEffectMeta {
/*  17 */   static final CraftMetaItem.ItemMetaKey EXPLOSION = new CraftMetaItem.ItemMetaKey("Explosion", "firework-effect");
/*     */   
/*     */   private FireworkEffect effect;
/*     */   
/*     */   CraftMetaCharge(CraftMetaItem meta) {
/*  22 */     super(meta);
/*     */     
/*  24 */     if (meta instanceof CraftMetaCharge) {
/*  25 */       this.effect = ((CraftMetaCharge)meta).effect;
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaCharge(Map<String, Object> map) {
/*  30 */     super(map);
/*     */     
/*  32 */     setEffect(CraftMetaItem.SerializableMeta.<FireworkEffect>getObject(FireworkEffect.class, map, EXPLOSION.BUKKIT, true));
/*     */   }
/*     */   
/*     */   CraftMetaCharge(NBTTagCompound tag) {
/*  36 */     super(tag);
/*     */     
/*  38 */     if (tag.hasKey(EXPLOSION.NBT)) {
/*  39 */       this.effect = CraftMetaFirework.getEffect(tag.getCompound(EXPLOSION.NBT));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setEffect(FireworkEffect effect) {
/*  44 */     this.effect = effect;
/*     */   }
/*     */   
/*     */   public boolean hasEffect() {
/*  48 */     return (this.effect != null);
/*     */   }
/*     */   
/*     */   public FireworkEffect getEffect() {
/*  52 */     return this.effect;
/*     */   }
/*     */ 
/*     */   
/*     */   void applyToItem(NBTTagCompound itemTag) {
/*  57 */     super.applyToItem(itemTag);
/*     */     
/*  59 */     if (hasEffect()) {
/*  60 */       itemTag.set(EXPLOSION.NBT, (NBTBase)CraftMetaFirework.getExplosion(this.effect));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   boolean applicableTo(Material type) {
/*  66 */     switch (type) {
/*     */       case FIREWORK_CHARGE:
/*  68 */         return true;
/*     */     } 
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isEmpty() {
/*  76 */     return (super.isEmpty() && !hasChargeMeta());
/*     */   }
/*     */   
/*     */   boolean hasChargeMeta() {
/*  80 */     return hasEffect();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta) {
/*  85 */     if (!super.equalsCommon(meta)) {
/*  86 */       return false;
/*     */     }
/*  88 */     if (meta instanceof CraftMetaCharge) {
/*  89 */       CraftMetaCharge that = (CraftMetaCharge)meta;
/*     */       
/*  91 */       return hasEffect() ? ((that.hasEffect() && this.effect.equals(that.effect))) : (!that.hasEffect());
/*     */     } 
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta) {
/*  98 */     return (super.notUncommon(meta) && (meta instanceof CraftMetaCharge || !hasChargeMeta()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int applyHash() {
/* 104 */     int original = super.applyHash(), hash = original;
/*     */     
/* 106 */     if (hasEffect()) {
/* 107 */       hash = 61 * hash + this.effect.hashCode();
/*     */     }
/*     */     
/* 110 */     return (hash != original) ? (CraftMetaCharge.class.hashCode() ^ hash) : hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftMetaCharge clone() {
/* 115 */     return (CraftMetaCharge)super.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
/* 120 */     super.serialize(builder);
/*     */     
/* 122 */     if (hasEffect()) {
/* 123 */       builder.put(EXPLOSION.BUKKIT, this.effect);
/*     */     }
/*     */     
/* 126 */     return builder;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftMetaCharge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */