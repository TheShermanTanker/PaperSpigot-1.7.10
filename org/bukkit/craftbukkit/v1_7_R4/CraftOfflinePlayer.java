/*     */ package org.bukkit.craftbukkit.v1_7_R4;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import net.minecraft.server.v1_7_R4.WorldNBTStorage;
/*     */ import net.minecraft.server.v1_7_R4.WorldServer;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import org.bukkit.BanList;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.SerializableAs;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ @SerializableAs("Player")
/*     */ public class CraftOfflinePlayer
/*     */   implements OfflinePlayer, ConfigurationSerializable {
/*     */   private final GameProfile profile;
/*     */   
/*     */   protected CraftOfflinePlayer(CraftServer server, GameProfile profile) {
/*  32 */     this.server = server;
/*  33 */     this.profile = profile;
/*  34 */     this.storage = (WorldNBTStorage)((WorldServer)server.console.worlds.get(0)).getDataManager();
/*     */   }
/*     */   private final CraftServer server; private final WorldNBTStorage storage;
/*     */   
/*     */   public GameProfile getProfile() {
/*  39 */     return this.profile;
/*     */   }
/*     */   
/*     */   public boolean isOnline() {
/*  43 */     return (getPlayer() != null);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  47 */     Player player = getPlayer();
/*  48 */     if (player != null) {
/*  49 */       return player.getName();
/*     */     }
/*     */ 
/*     */     
/*  53 */     if (this.profile.getName() != null) {
/*  54 */       return this.profile.getName();
/*     */     }
/*     */     
/*  57 */     NBTTagCompound data = getBukkitData();
/*     */     
/*  59 */     if (data != null && 
/*  60 */       data.hasKey("lastKnownName")) {
/*  61 */       return data.getString("lastKnownName");
/*     */     }
/*     */ 
/*     */     
/*  65 */     return null;
/*     */   }
/*     */   
/*     */   public UUID getUniqueId() {
/*  69 */     return this.profile.getId();
/*     */   }
/*     */   
/*     */   public Server getServer() {
/*  73 */     return this.server;
/*     */   }
/*     */   
/*     */   public boolean isOp() {
/*  77 */     return this.server.getHandle().isOp(this.profile);
/*     */   }
/*     */   
/*     */   public void setOp(boolean value) {
/*  81 */     if (value == isOp()) {
/*     */       return;
/*     */     }
/*     */     
/*  85 */     if (value) {
/*  86 */       this.server.getHandle().addOp(this.profile);
/*     */     } else {
/*  88 */       this.server.getHandle().removeOp(this.profile);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isBanned() {
/*  93 */     if (getName() == null) {
/*  94 */       return false;
/*     */     }
/*     */     
/*  97 */     return this.server.getBanList(BanList.Type.NAME).isBanned(getName());
/*     */   }
/*     */   
/*     */   public void setBanned(boolean value) {
/* 101 */     if (getName() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     if (value) {
/* 106 */       this.server.getBanList(BanList.Type.NAME).addBan(getName(), null, null, null);
/*     */     } else {
/* 108 */       this.server.getBanList(BanList.Type.NAME).pardon(getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isWhitelisted() {
/* 113 */     return this.server.getHandle().getWhitelist().isWhitelisted(this.profile);
/*     */   }
/*     */   
/*     */   public void setWhitelisted(boolean value) {
/* 117 */     if (value) {
/* 118 */       this.server.getHandle().addWhitelist(this.profile);
/*     */     } else {
/* 120 */       this.server.getHandle().removeWhitelist(this.profile);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<String, Object> serialize() {
/* 125 */     Map<String, Object> result = new LinkedHashMap<String, Object>();
/*     */     
/* 127 */     result.put("UUID", this.profile.getId().toString());
/*     */     
/* 129 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static OfflinePlayer deserialize(Map<String, Object> args) {
/* 134 */     if (args.get("name") != null) {
/* 135 */       return Bukkit.getServer().getOfflinePlayer((String)args.get("name"));
/*     */     }
/*     */     
/* 138 */     return Bukkit.getServer().getOfflinePlayer(UUID.fromString((String)args.get("UUID")));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 143 */     return getClass().getSimpleName() + "[UUID=" + this.profile.getId() + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/* 148 */     EntityPlayer playerEntity = (EntityPlayer)(this.server.getHandle()).uuidMap.get(getUniqueId());
/* 149 */     return (playerEntity != null) ? (Player)playerEntity.getBukkitEntity() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 155 */     if (obj == null || !(obj instanceof OfflinePlayer)) {
/* 156 */       return false;
/*     */     }
/*     */     
/* 159 */     OfflinePlayer other = (OfflinePlayer)obj;
/* 160 */     if (getUniqueId() == null || other.getUniqueId() == null) {
/* 161 */       return false;
/*     */     }
/*     */     
/* 164 */     return getUniqueId().equals(other.getUniqueId());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 169 */     int hash = 5;
/* 170 */     hash = 97 * hash + ((getUniqueId() != null) ? getUniqueId().hashCode() : 0);
/* 171 */     return hash;
/*     */   }
/*     */   
/*     */   private NBTTagCompound getData() {
/* 175 */     return this.storage.getPlayerData(getUniqueId().toString());
/*     */   }
/*     */   
/*     */   private NBTTagCompound getBukkitData() {
/* 179 */     NBTTagCompound result = getData();
/*     */     
/* 181 */     if (result != null) {
/* 182 */       if (!result.hasKey("bukkit")) {
/* 183 */         result.set("bukkit", (NBTBase)new NBTTagCompound());
/*     */       }
/* 185 */       result = result.getCompound("bukkit");
/*     */     } 
/*     */     
/* 188 */     return result;
/*     */   }
/*     */   
/*     */   private File getDataFile() {
/* 192 */     return new File(this.storage.getPlayerDir(), getUniqueId() + ".dat");
/*     */   }
/*     */   
/*     */   public long getFirstPlayed() {
/* 196 */     Player player = getPlayer();
/* 197 */     if (player != null) return player.getFirstPlayed();
/*     */     
/* 199 */     NBTTagCompound data = getBukkitData();
/*     */     
/* 201 */     if (data != null) {
/* 202 */       if (data.hasKey("firstPlayed")) {
/* 203 */         return data.getLong("firstPlayed");
/*     */       }
/* 205 */       File file = getDataFile();
/* 206 */       return file.lastModified();
/*     */     } 
/*     */     
/* 209 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLastPlayed() {
/* 214 */     Player player = getPlayer();
/* 215 */     if (player != null) return player.getLastPlayed();
/*     */     
/* 217 */     NBTTagCompound data = getBukkitData();
/*     */     
/* 219 */     if (data != null) {
/* 220 */       if (data.hasKey("lastPlayed")) {
/* 221 */         return data.getLong("lastPlayed");
/*     */       }
/* 223 */       File file = getDataFile();
/* 224 */       return file.lastModified();
/*     */     } 
/*     */     
/* 227 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlayedBefore() {
/* 232 */     return (getData() != null);
/*     */   }
/*     */   
/*     */   public Location getBedSpawnLocation() {
/* 236 */     NBTTagCompound data = getData();
/* 237 */     if (data == null) return null;
/*     */     
/* 239 */     if (data.hasKey("SpawnX") && data.hasKey("SpawnY") && data.hasKey("SpawnZ")) {
/* 240 */       String spawnWorld = data.getString("SpawnWorld");
/* 241 */       if (spawnWorld.equals("")) {
/* 242 */         spawnWorld = ((World)this.server.getWorlds().get(0)).getName();
/*     */       }
/* 244 */       return new Location(this.server.getWorld(spawnWorld), data.getInt("SpawnX"), data.getInt("SpawnY"), data.getInt("SpawnZ"));
/*     */     } 
/* 246 */     return null;
/*     */   }
/*     */   
/*     */   public void setMetadata(String metadataKey, MetadataValue metadataValue) {
/* 250 */     this.server.getPlayerMetadata().setMetadata(this, metadataKey, metadataValue);
/*     */   }
/*     */   
/*     */   public List<MetadataValue> getMetadata(String metadataKey) {
/* 254 */     return this.server.getPlayerMetadata().getMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public boolean hasMetadata(String metadataKey) {
/* 258 */     return this.server.getPlayerMetadata().hasMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public void removeMetadata(String metadataKey, Plugin plugin) {
/* 262 */     this.server.getPlayerMetadata().removeMetadata(this, metadataKey, plugin);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftOfflinePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */