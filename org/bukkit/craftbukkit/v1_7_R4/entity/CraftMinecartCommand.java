/*     */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*     */ 
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartAbstract;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartCommandBlock;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.minecart.CommandMinecart;
/*     */ import org.bukkit.permissions.PermissibleBase;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionAttachment;
/*     */ import org.bukkit.permissions.PermissionAttachmentInfo;
/*     */ import org.bukkit.permissions.ServerOperator;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class CraftMinecartCommand extends CraftMinecart implements CommandMinecart {
/*  19 */   private final PermissibleBase perm = new PermissibleBase((ServerOperator)this);
/*     */   
/*     */   public CraftMinecartCommand(CraftServer server, EntityMinecartCommandBlock entity) {
/*  22 */     super(server, (EntityMinecartAbstract)entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCommand() {
/*  27 */     return ((EntityMinecartCommandBlock)getHandle()).getCommandBlock().getCommand();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCommand(String command) {
/*  32 */     ((EntityMinecartCommandBlock)getHandle()).getCommandBlock().setCommand((command != null) ? command : "");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/*  37 */     ((EntityMinecartCommandBlock)getHandle()).getCommandBlock().setName((name != null) ? name : "@");
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType getType() {
/*  42 */     return EntityType.MINECART_COMMAND;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(String message) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(String[] messages) {}
/*     */ 
/*     */   
/*     */   public String getName() {
/*  55 */     return ((EntityMinecartCommandBlock)getHandle()).getCommandBlock().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOp() {
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOp(boolean value) {
/*  65 */     throw new UnsupportedOperationException("Cannot change operator status of a minecart");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPermissionSet(String name) {
/*  70 */     return this.perm.isPermissionSet(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPermissionSet(Permission perm) {
/*  75 */     return this.perm.isPermissionSet(perm);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission(String name) {
/*  80 */     return this.perm.hasPermission(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission(Permission perm) {
/*  85 */     return this.perm.hasPermission(perm);
/*     */   }
/*     */ 
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
/*  90 */     return this.perm.addAttachment(plugin, name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin) {
/*  95 */     return this.perm.addAttachment(plugin);
/*     */   }
/*     */ 
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
/* 100 */     return this.perm.addAttachment(plugin, name, value, ticks);
/*     */   }
/*     */ 
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
/* 105 */     return this.perm.addAttachment(plugin, ticks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttachment(PermissionAttachment attachment) {
/* 110 */     this.perm.removeAttachment(attachment);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recalculatePermissions() {
/* 115 */     this.perm.recalculatePermissions();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<PermissionAttachmentInfo> getEffectivePermissions() {
/* 120 */     return this.perm.getEffectivePermissions();
/*     */   }
/*     */ 
/*     */   
/*     */   public Server getServer() {
/* 125 */     return Bukkit.getServer();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftMinecartCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */