/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagList;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.PotionMeta;
/*     */ import org.bukkit.inventory.meta.Repairable;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaPotion
/*     */   extends CraftMetaItem implements PotionMeta {
/*  24 */   static final CraftMetaItem.ItemMetaKey AMPLIFIER = new CraftMetaItem.ItemMetaKey("Amplifier", "amplifier");
/*  25 */   static final CraftMetaItem.ItemMetaKey AMBIENT = new CraftMetaItem.ItemMetaKey("Ambient", "ambient");
/*  26 */   static final CraftMetaItem.ItemMetaKey DURATION = new CraftMetaItem.ItemMetaKey("Duration", "duration");
/*  27 */   static final CraftMetaItem.ItemMetaKey POTION_EFFECTS = new CraftMetaItem.ItemMetaKey("CustomPotionEffects", "custom-effects");
/*  28 */   static final CraftMetaItem.ItemMetaKey ID = new CraftMetaItem.ItemMetaKey("Id", "potion-id");
/*     */   
/*     */   private List<PotionEffect> customEffects;
/*     */   
/*     */   CraftMetaPotion(CraftMetaItem meta) {
/*  33 */     super(meta);
/*  34 */     if (!(meta instanceof CraftMetaPotion)) {
/*     */       return;
/*     */     }
/*  37 */     CraftMetaPotion potionMeta = (CraftMetaPotion)meta;
/*  38 */     if (potionMeta.hasCustomEffects()) {
/*  39 */       this.customEffects = new ArrayList<PotionEffect>(potionMeta.customEffects);
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaPotion(NBTTagCompound tag) {
/*  44 */     super(tag);
/*     */     
/*  46 */     if (tag.hasKey(POTION_EFFECTS.NBT)) {
/*  47 */       NBTTagList list = tag.getList(POTION_EFFECTS.NBT, 10);
/*  48 */       int length = list.size();
/*  49 */       if (length > 0) {
/*  50 */         this.customEffects = new ArrayList<PotionEffect>(length);
/*     */         
/*  52 */         for (int i = 0; i < length; i++) {
/*  53 */           NBTTagCompound effect = list.get(i);
/*  54 */           PotionEffectType type = PotionEffectType.getById(effect.getByte(ID.NBT));
/*  55 */           int amp = effect.getByte(AMPLIFIER.NBT);
/*  56 */           int duration = effect.getInt(DURATION.NBT);
/*  57 */           boolean ambient = effect.getBoolean(AMBIENT.NBT);
/*  58 */           this.customEffects.add(new PotionEffect(type, duration, amp, ambient));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   CraftMetaPotion(Map<String, Object> map) {
/*  65 */     super(map);
/*     */     
/*  67 */     Iterable<?> rawEffectList = CraftMetaItem.SerializableMeta.<Iterable>getObject(Iterable.class, map, POTION_EFFECTS.BUKKIT, true);
/*  68 */     if (rawEffectList == null) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     for (Object obj : rawEffectList) {
/*  73 */       if (!(obj instanceof PotionEffect)) {
/*  74 */         throw new IllegalArgumentException("Object in effect list is not valid. " + obj.getClass());
/*     */       }
/*  76 */       addCustomEffect((PotionEffect)obj, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void applyToItem(NBTTagCompound tag) {
/*  82 */     super.applyToItem(tag);
/*  83 */     if (hasCustomEffects()) {
/*  84 */       NBTTagList effectList = new NBTTagList();
/*  85 */       tag.set(POTION_EFFECTS.NBT, (NBTBase)effectList);
/*     */       
/*  87 */       for (PotionEffect effect : this.customEffects) {
/*  88 */         NBTTagCompound effectData = new NBTTagCompound();
/*  89 */         effectData.setByte(ID.NBT, (byte)effect.getType().getId());
/*  90 */         effectData.setByte(AMPLIFIER.NBT, (byte)effect.getAmplifier());
/*  91 */         effectData.setInt(DURATION.NBT, effect.getDuration());
/*  92 */         effectData.setBoolean(AMBIENT.NBT, effect.isAmbient());
/*  93 */         effectList.add((NBTBase)effectData);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isEmpty() {
/* 100 */     return (super.isEmpty() && isPotionEmpty());
/*     */   }
/*     */   
/*     */   boolean isPotionEmpty() {
/* 104 */     return !hasCustomEffects();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean applicableTo(Material type) {
/* 109 */     switch (type) {
/*     */       case POTION:
/* 111 */         return true;
/*     */     } 
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CraftMetaPotion clone() {
/* 119 */     CraftMetaPotion clone = (CraftMetaPotion)super.clone();
/* 120 */     if (this.customEffects != null) {
/* 121 */       clone.customEffects = new ArrayList<PotionEffect>(this.customEffects);
/*     */     }
/* 123 */     return clone;
/*     */   }
/*     */   
/*     */   public boolean hasCustomEffects() {
/* 127 */     return (this.customEffects != null && !this.customEffects.isEmpty());
/*     */   }
/*     */   
/*     */   public List<PotionEffect> getCustomEffects() {
/* 131 */     if (hasCustomEffects()) {
/* 132 */       return (List<PotionEffect>)ImmutableList.copyOf(this.customEffects);
/*     */     }
/* 134 */     return (List<PotionEffect>)ImmutableList.of();
/*     */   }
/*     */   
/*     */   public boolean addCustomEffect(PotionEffect effect, boolean overwrite) {
/* 138 */     Validate.notNull(effect, "Potion effect must not be null");
/*     */     
/* 140 */     int index = indexOfEffect(effect.getType());
/* 141 */     if (index != -1) {
/* 142 */       if (overwrite) {
/* 143 */         PotionEffect old = this.customEffects.get(index);
/* 144 */         if (old.getAmplifier() == effect.getAmplifier() && old.getDuration() == effect.getDuration() && old.isAmbient() == effect.isAmbient()) {
/* 145 */           return false;
/*     */         }
/* 147 */         this.customEffects.set(index, effect);
/* 148 */         return true;
/*     */       } 
/* 150 */       return false;
/*     */     } 
/*     */     
/* 153 */     if (this.customEffects == null) {
/* 154 */       this.customEffects = new ArrayList<PotionEffect>();
/*     */     }
/* 156 */     this.customEffects.add(effect);
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeCustomEffect(PotionEffectType type) {
/* 162 */     Validate.notNull(type, "Potion effect type must not be null");
/*     */     
/* 164 */     if (!hasCustomEffects()) {
/* 165 */       return false;
/*     */     }
/*     */     
/* 168 */     boolean changed = false;
/* 169 */     Iterator<PotionEffect> iterator = this.customEffects.iterator();
/* 170 */     while (iterator.hasNext()) {
/* 171 */       PotionEffect effect = iterator.next();
/* 172 */       if (effect.getType() == type) {
/* 173 */         iterator.remove();
/* 174 */         changed = true;
/*     */       } 
/*     */     } 
/* 177 */     return changed;
/*     */   }
/*     */   
/*     */   public boolean hasCustomEffect(PotionEffectType type) {
/* 181 */     Validate.notNull(type, "Potion effect type must not be null");
/* 182 */     return (indexOfEffect(type) != -1);
/*     */   }
/*     */   
/*     */   public boolean setMainEffect(PotionEffectType type) {
/* 186 */     Validate.notNull(type, "Potion effect type must not be null");
/* 187 */     int index = indexOfEffect(type);
/* 188 */     if (index == -1 || index == 0) {
/* 189 */       return false;
/*     */     }
/*     */     
/* 192 */     PotionEffect old = this.customEffects.get(0);
/* 193 */     this.customEffects.set(0, this.customEffects.get(index));
/* 194 */     this.customEffects.set(index, old);
/* 195 */     return true;
/*     */   }
/*     */   
/*     */   private int indexOfEffect(PotionEffectType type) {
/* 199 */     if (!hasCustomEffects()) {
/* 200 */       return -1;
/*     */     }
/*     */     
/* 203 */     for (int i = 0; i < this.customEffects.size(); i++) {
/* 204 */       if (((PotionEffect)this.customEffects.get(i)).getType().equals(type)) {
/* 205 */         return i;
/*     */       }
/*     */     } 
/* 208 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean clearCustomEffects() {
/* 212 */     boolean changed = hasCustomEffects();
/* 213 */     this.customEffects = null;
/* 214 */     return changed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int applyHash() {
/* 220 */     int original = super.applyHash(), hash = original;
/* 221 */     if (hasCustomEffects()) {
/* 222 */       hash = 73 * hash + this.customEffects.hashCode();
/*     */     }
/* 224 */     return (original != hash) ? (CraftMetaPotion.class.hashCode() ^ hash) : hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsCommon(CraftMetaItem meta) {
/* 229 */     if (!super.equalsCommon(meta)) {
/* 230 */       return false;
/*     */     }
/* 232 */     if (meta instanceof CraftMetaPotion) {
/* 233 */       CraftMetaPotion that = (CraftMetaPotion)meta;
/*     */       
/* 235 */       return hasCustomEffects() ? ((that.hasCustomEffects() && this.customEffects.equals(that.customEffects))) : (!that.hasCustomEffects());
/*     */     } 
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta) {
/* 242 */     return (super.notUncommon(meta) && (meta instanceof CraftMetaPotion || isPotionEmpty()));
/*     */   }
/*     */ 
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
/* 247 */     super.serialize(builder);
/*     */     
/* 249 */     if (hasCustomEffects()) {
/* 250 */       builder.put(POTION_EFFECTS.BUKKIT, ImmutableList.copyOf(this.customEffects));
/*     */     }
/*     */     
/* 253 */     return builder;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftMetaPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */