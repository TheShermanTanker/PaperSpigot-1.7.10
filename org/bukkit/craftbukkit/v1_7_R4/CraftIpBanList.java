/*    */ package org.bukkit.craftbukkit.v1_7_R4;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.util.Date;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.v1_7_R4.IpBanEntry;
/*    */ import net.minecraft.server.v1_7_R4.IpBanList;
/*    */ import net.minecraft.server.v1_7_R4.JsonListEntry;
/*    */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanEntry;
/*    */ import org.bukkit.BanList;
/*    */ 
/*    */ public class CraftIpBanList
/*    */   implements BanList {
/*    */   public CraftIpBanList(IpBanList list) {
/* 20 */     this.list = list;
/*    */   }
/*    */   private final IpBanList list;
/*    */   
/*    */   public BanEntry getBanEntry(String target) {
/* 25 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 27 */     IpBanEntry entry = (IpBanEntry)this.list.get(target);
/* 28 */     if (entry == null) {
/* 29 */       return null;
/*    */     }
/*    */     
/* 32 */     return new CraftIpBanEntry(target, entry, this.list);
/*    */   }
/*    */ 
/*    */   
/*    */   public BanEntry addBan(String target, String reason, Date expires, String source) {
/* 37 */     Validate.notNull(target, "Ban target cannot be null");
/*    */     
/* 39 */     IpBanEntry entry = new IpBanEntry(target, new Date(), StringUtils.isBlank(source) ? null : source, expires, StringUtils.isBlank(reason) ? null : reason);
/*    */ 
/*    */ 
/*    */     
/* 43 */     this.list.add((JsonListEntry)entry);
/*    */     
/*    */     try {
/* 46 */       this.list.save();
/* 47 */     } catch (IOException ex) {
/* 48 */       MinecraftServer.getLogger().error("Failed to save banned-ips.json, " + ex.getMessage());
/*    */     } 
/*    */     
/* 51 */     return new CraftIpBanEntry(target, entry, this.list);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<BanEntry> getBanEntries() {
/* 56 */     ImmutableSet.Builder<BanEntry> builder = ImmutableSet.builder();
/* 57 */     for (String target : this.list.getEntries()) {
/* 58 */       builder.add(new CraftIpBanEntry(target, (IpBanEntry)this.list.get(target), this.list));
/*    */     }
/*    */     
/* 61 */     return (Set<BanEntry>)builder.build();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBanned(String target) {
/* 66 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 68 */     return this.list.isBanned(InetSocketAddress.createUnresolved(target, 0));
/*    */   }
/*    */ 
/*    */   
/*    */   public void pardon(String target) {
/* 73 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 75 */     this.list.remove(target);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftIpBanList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */