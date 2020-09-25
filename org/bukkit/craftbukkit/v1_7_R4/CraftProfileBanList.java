/*    */ package org.bukkit.craftbukkit.v1_7_R4;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.io.IOException;
/*    */ import java.util.Date;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.v1_7_R4.GameProfileBanEntry;
/*    */ import net.minecraft.server.v1_7_R4.GameProfileBanList;
/*    */ import net.minecraft.server.v1_7_R4.JsonListEntry;
/*    */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanEntry;
/*    */ import org.bukkit.BanList;
/*    */ 
/*    */ public class CraftProfileBanList
/*    */   implements BanList {
/*    */   private final GameProfileBanList list;
/*    */   
/*    */   public CraftProfileBanList(GameProfileBanList list) {
/* 22 */     this.list = list;
/*    */   }
/*    */ 
/*    */   
/*    */   public BanEntry getBanEntry(String target) {
/* 27 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 29 */     GameProfile profile = MinecraftServer.getServer().getUserCache().getProfile(target);
/* 30 */     if (profile == null) {
/* 31 */       return null;
/*    */     }
/*    */     
/* 34 */     GameProfileBanEntry entry = (GameProfileBanEntry)this.list.get(profile);
/* 35 */     if (entry == null) {
/* 36 */       return null;
/*    */     }
/*    */     
/* 39 */     return new CraftProfileBanEntry(profile, entry, this.list);
/*    */   }
/*    */ 
/*    */   
/*    */   public BanEntry addBan(String target, String reason, Date expires, String source) {
/* 44 */     Validate.notNull(target, "Ban target cannot be null");
/*    */     
/* 46 */     GameProfile profile = MinecraftServer.getServer().getUserCache().getProfile(target);
/* 47 */     if (profile == null) {
/* 48 */       return null;
/*    */     }
/*    */     
/* 51 */     GameProfileBanEntry entry = new GameProfileBanEntry(profile, new Date(), StringUtils.isBlank(source) ? null : source, expires, StringUtils.isBlank(reason) ? null : reason);
/*    */ 
/*    */ 
/*    */     
/* 55 */     this.list.add((JsonListEntry)entry);
/*    */     
/*    */     try {
/* 58 */       this.list.save();
/* 59 */     } catch (IOException ex) {
/* 60 */       MinecraftServer.getLogger().error("Failed to save banned-players.json, " + ex.getMessage());
/*    */     } 
/*    */     
/* 63 */     return new CraftProfileBanEntry(profile, entry, this.list);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<BanEntry> getBanEntries() {
/* 68 */     ImmutableSet.Builder<BanEntry> builder = ImmutableSet.builder();
/* 69 */     for (JsonListEntry entry : this.list.getValues()) {
/* 70 */       GameProfile profile = (GameProfile)entry.getKey();
/* 71 */       builder.add(new CraftProfileBanEntry(profile, (GameProfileBanEntry)entry, this.list));
/*    */     } 
/*    */     
/* 74 */     return (Set<BanEntry>)builder.build();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBanned(String target) {
/* 79 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 81 */     GameProfile profile = MinecraftServer.getServer().getUserCache().getProfile(target);
/* 82 */     if (profile == null) {
/* 83 */       return false;
/*    */     }
/*    */     
/* 86 */     return this.list.isBanned(profile);
/*    */   }
/*    */ 
/*    */   
/*    */   public void pardon(String target) {
/* 91 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 93 */     GameProfile profile = MinecraftServer.getServer().getUserCache().getProfile(target);
/* 94 */     this.list.remove(profile);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftProfileBanList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */