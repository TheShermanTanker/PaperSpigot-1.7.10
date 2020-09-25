/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*     */ import net.minecraft.server.v1_7_R4.GameProfileSerializer;
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import net.minecraft.server.v1_7_R4.TileEntitySkull;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.Repairable;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaSkull
/*     */   extends CraftMetaItem implements SkullMeta {
/*  21 */   static final CraftMetaItem.ItemMetaKey SKULL_OWNER = new CraftMetaItem.ItemMetaKey("SkullOwner", "skull-owner");
/*     */   
/*     */   static final int MAX_OWNER_LENGTH = 16;
/*     */   private GameProfile profile;
/*     */   
/*     */   CraftMetaSkull(CraftMetaItem meta) {
/*  27 */     super(meta);
/*  28 */     if (!(meta instanceof CraftMetaSkull)) {
/*     */       return;
/*     */     }
/*  31 */     CraftMetaSkull skullMeta = (CraftMetaSkull)meta;
/*  32 */     this.profile = skullMeta.profile;
/*     */   }
/*     */   
/*     */   CraftMetaSkull(NBTTagCompound tag) {
/*  36 */     super(tag);
/*     */     
/*  38 */     if (tag.hasKeyOfType(SKULL_OWNER.NBT, 10)) {
/*  39 */       this.profile = GameProfileSerializer.deserialize(tag.getCompound(SKULL_OWNER.NBT));
/*  40 */     } else if (tag.hasKeyOfType(SKULL_OWNER.NBT, 8)) {
/*  41 */       this.profile = new GameProfile(null, tag.getString(SKULL_OWNER.NBT));
/*     */     } 
/*     */   }
/*     */   
/*     */   CraftMetaSkull(Map<String, Object> map) {
/*  46 */     super(map);
/*  47 */     setOwner(CraftMetaItem.SerializableMeta.getString(map, SKULL_OWNER.BUKKIT, true));
/*     */   }
/*     */ 
/*     */   
/*     */   void applyToItem(final NBTTagCompound tag) {
/*  52 */     super.applyToItem(tag);
/*     */     
/*  54 */     if (hasOwner()) {
/*  55 */       NBTTagCompound owner = new NBTTagCompound();
/*  56 */       GameProfileSerializer.serialize(owner, this.profile);
/*  57 */       tag.set(SKULL_OWNER.NBT, (NBTBase)owner);
/*     */ 
/*     */ 
/*     */       
/*  61 */       TileEntitySkull.executor.execute(new Runnable()
/*     */           {
/*     */ 
/*     */             
/*     */             public void run()
/*     */             {
/*  67 */               final GameProfile profile = (GameProfile)TileEntitySkull.skinCache.getUnchecked(CraftMetaSkull.this.profile.getName().toLowerCase());
/*  68 */               if (profile != null)
/*     */               {
/*  70 */                 (MinecraftServer.getServer()).processQueue.add(new Runnable()
/*     */                     {
/*     */                       
/*     */                       public void run()
/*     */                       {
/*  75 */                         NBTTagCompound owner = new NBTTagCompound();
/*  76 */                         GameProfileSerializer.serialize(owner, profile);
/*  77 */                         tag.set(CraftMetaSkull.SKULL_OWNER.NBT, (NBTBase)owner);
/*     */                       }
/*     */                     });
/*     */               }
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isEmpty() {
/*  89 */     return (super.isEmpty() && isSkullEmpty());
/*     */   }
/*     */   
/*     */   boolean isSkullEmpty() {
/*  93 */     return !hasOwner();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean applicableTo(Material type) {
/*  98 */     switch (type) {
/*     */       case SKULL_ITEM:
/* 100 */         return true;
/*     */     } 
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CraftMetaSkull clone() {
/* 108 */     return (CraftMetaSkull)super.clone();
/*     */   }
/*     */   
/*     */   public boolean hasOwner() {
/* 112 */     return (this.profile != null);
/*     */   }
/*     */   
/*     */   public String getOwner() {
/* 116 */     return hasOwner() ? this.profile.getName() : null;
/*     */   }
/*     */   
/*     */   public boolean setOwner(String name) {
/* 120 */     if (name != null && name.length() > 16) {
/* 121 */       return false;
/*     */     }
/*     */     
/* 124 */     if (name == null) {
/* 125 */       this.profile = null;
/*     */     } else {
/*     */       
/* 128 */       EntityPlayer player = MinecraftServer.getServer().getPlayerList().getPlayer(name);
/* 129 */       this.profile = (player != null) ? player.getProfile() : new GameProfile(null, name);
/*     */     } 
/*     */ 
/*     */     
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int applyHash() {
/* 139 */     int original = super.applyHash(), hash = original;
/* 140 */     if (hasOwner()) {
/* 141 */       hash = 61 * hash + this.profile.hashCode();
/*     */     }
/* 143 */     return (original != hash) ? (CraftMetaSkull.class.hashCode() ^ hash) : hash;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta) {
/* 148 */     if (!super.equalsCommon(meta)) {
/* 149 */       return false;
/*     */     }
/* 151 */     if (meta instanceof CraftMetaSkull) {
/* 152 */       CraftMetaSkull that = (CraftMetaSkull)meta;
/*     */       
/* 154 */       return hasOwner() ? ((that.hasOwner() && this.profile.equals(that.profile))) : (!that.hasOwner());
/*     */     } 
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta) {
/* 161 */     return (super.notUncommon(meta) && (meta instanceof CraftMetaSkull || isSkullEmpty()));
/*     */   }
/*     */ 
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
/* 166 */     super.serialize(builder);
/* 167 */     if (hasOwner()) {
/* 168 */       return builder.put(SKULL_OWNER.BUKKIT, this.profile.getName());
/*     */     }
/* 170 */     return builder;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftMetaSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */