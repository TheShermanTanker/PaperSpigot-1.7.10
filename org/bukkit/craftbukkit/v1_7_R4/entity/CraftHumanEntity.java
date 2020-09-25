/*     */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.v1_7_R4.Container;
/*     */ import net.minecraft.server.v1_7_R4.Entity;
/*     */ import net.minecraft.server.v1_7_R4.EntityHuman;
/*     */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartHopper;
/*     */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*     */ import net.minecraft.server.v1_7_R4.ICrafting;
/*     */ import net.minecraft.server.v1_7_R4.IInventory;
/*     */ import net.minecraft.server.v1_7_R4.ItemStack;
/*     */ import net.minecraft.server.v1_7_R4.Packet;
/*     */ import net.minecraft.server.v1_7_R4.PacketPlayInCloseWindow;
/*     */ import net.minecraft.server.v1_7_R4.PacketPlayOutOpenWindow;
/*     */ import net.minecraft.server.v1_7_R4.TileEntityDispenser;
/*     */ import net.minecraft.server.v1_7_R4.TileEntityFurnace;
/*     */ import net.minecraft.server.v1_7_R4.TileEntityHopper;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftContainer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryPlayer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.EntityEquipment;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.permissions.PermissibleBase;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionAttachment;
/*     */ import org.bukkit.permissions.ServerOperator;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class CraftHumanEntity extends CraftLivingEntity implements HumanEntity {
/*     */   private CraftInventoryPlayer inventory;
/*  43 */   protected final PermissibleBase perm = new PermissibleBase((ServerOperator)this); private final CraftInventory enderChest;
/*     */   private boolean op;
/*     */   private GameMode mode;
/*     */   
/*     */   public CraftHumanEntity(CraftServer server, EntityHuman entity) {
/*  48 */     super(server, (EntityLiving)entity);
/*  49 */     this.mode = server.getDefaultGameMode();
/*  50 */     this.inventory = new CraftInventoryPlayer(entity.inventory);
/*  51 */     this.enderChest = new CraftInventory((IInventory)entity.getEnderChest());
/*     */   }
/*     */   
/*     */   public String getName() {
/*  55 */     return getHandle().getName();
/*     */   }
/*     */   
/*     */   public PlayerInventory getInventory() {
/*  59 */     return (PlayerInventory)this.inventory;
/*     */   }
/*     */   
/*     */   public EntityEquipment getEquipment() {
/*  63 */     return (EntityEquipment)this.inventory;
/*     */   }
/*     */   
/*     */   public Inventory getEnderChest() {
/*  67 */     return (Inventory)this.enderChest;
/*     */   }
/*     */   
/*     */   public ItemStack getItemInHand() {
/*  71 */     return getInventory().getItemInHand();
/*     */   }
/*     */   
/*     */   public void setItemInHand(ItemStack item) {
/*  75 */     getInventory().setItemInHand(item);
/*     */   }
/*     */   
/*     */   public ItemStack getItemOnCursor() {
/*  79 */     return (ItemStack)CraftItemStack.asCraftMirror((getHandle()).inventory.getCarried());
/*     */   }
/*     */   
/*     */   public void setItemOnCursor(ItemStack item) {
/*  83 */     ItemStack stack = CraftItemStack.asNMSCopy(item);
/*  84 */     (getHandle()).inventory.setCarried(stack);
/*  85 */     if (this instanceof CraftPlayer) {
/*  86 */       ((EntityPlayer)getHandle()).broadcastCarriedItem();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isSleeping() {
/*  91 */     return (getHandle()).sleeping;
/*     */   }
/*     */   
/*     */   public int getSleepTicks() {
/*  95 */     return (getHandle()).sleepTicks;
/*     */   }
/*     */   
/*     */   public boolean isOp() {
/*  99 */     return this.op;
/*     */   }
/*     */   
/*     */   public boolean isPermissionSet(String name) {
/* 103 */     return this.perm.isPermissionSet(name);
/*     */   }
/*     */   
/*     */   public boolean isPermissionSet(Permission perm) {
/* 107 */     return this.perm.isPermissionSet(perm);
/*     */   }
/*     */   
/*     */   public boolean hasPermission(String name) {
/* 111 */     return this.perm.hasPermission(name);
/*     */   }
/*     */   
/*     */   public boolean hasPermission(Permission perm) {
/* 115 */     return this.perm.hasPermission(perm);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
/* 119 */     return this.perm.addAttachment(plugin, name, value);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin) {
/* 123 */     return this.perm.addAttachment(plugin);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
/* 127 */     return this.perm.addAttachment(plugin, name, value, ticks);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
/* 131 */     return this.perm.addAttachment(plugin, ticks);
/*     */   }
/*     */   
/*     */   public void removeAttachment(PermissionAttachment attachment) {
/* 135 */     this.perm.removeAttachment(attachment);
/*     */   }
/*     */   
/*     */   public void recalculatePermissions() {
/* 139 */     this.perm.recalculatePermissions();
/*     */   }
/*     */   
/*     */   public void setOp(boolean value) {
/* 143 */     this.op = value;
/* 144 */     this.perm.recalculatePermissions();
/*     */   }
/*     */   
/*     */   public Set<PermissionAttachmentInfo> getEffectivePermissions() {
/* 148 */     return this.perm.getEffectivePermissions();
/*     */   }
/*     */   
/*     */   public GameMode getGameMode() {
/* 152 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setGameMode(GameMode mode) {
/* 156 */     if (mode == null) {
/* 157 */       throw new IllegalArgumentException("Mode cannot be null");
/*     */     }
/*     */     
/* 160 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityHuman getHandle() {
/* 165 */     return (EntityHuman)this.entity;
/*     */   }
/*     */   
/*     */   public void setHandle(EntityHuman entity) {
/* 169 */     setHandle((EntityLiving)entity);
/* 170 */     this.inventory = new CraftInventoryPlayer(entity.inventory);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 175 */     return "CraftHumanEntity{id=" + getEntityId() + "name=" + getName() + '}';
/*     */   }
/*     */   
/*     */   public InventoryView getOpenInventory() {
/* 179 */     return (getHandle()).activeContainer.getBukkitView();
/*     */   }
/*     */   
/*     */   public InventoryView openInventory(Inventory inventory) {
/* 183 */     if (!(getHandle() instanceof EntityPlayer)) return null; 
/* 184 */     EntityPlayer player = (EntityPlayer)getHandle();
/* 185 */     InventoryType type = inventory.getType();
/* 186 */     Container formerContainer = (getHandle()).activeContainer;
/*     */     
/* 188 */     CraftInventory craftinv = (CraftInventory)inventory;
/* 189 */     switch (type) {
/*     */       case PLAYER:
/*     */       case CHEST:
/*     */       case ENDER_CHEST:
/* 193 */         getHandle().openContainer(craftinv.getInventory());
/*     */         break;
/*     */       case DISPENSER:
/* 196 */         if (craftinv.getInventory() instanceof TileEntityDispenser) {
/* 197 */           getHandle().openDispenser((TileEntityDispenser)craftinv.getInventory()); break;
/*     */         } 
/* 199 */         openCustomInventory(inventory, player, 3);
/*     */         break;
/*     */       
/*     */       case FURNACE:
/* 203 */         if (craftinv.getInventory() instanceof TileEntityFurnace) {
/* 204 */           getHandle().openFurnace((TileEntityFurnace)craftinv.getInventory()); break;
/*     */         } 
/* 206 */         openCustomInventory(inventory, player, 2);
/*     */         break;
/*     */       
/*     */       case WORKBENCH:
/* 210 */         openCustomInventory(inventory, player, 1);
/*     */         break;
/*     */       case BREWING:
/* 213 */         if (craftinv.getInventory() instanceof TileEntityBrewingStand) {
/* 214 */           getHandle().openBrewingStand((TileEntityBrewingStand)craftinv.getInventory()); break;
/*     */         } 
/* 216 */         openCustomInventory(inventory, player, 5);
/*     */         break;
/*     */       
/*     */       case ENCHANTING:
/* 220 */         openCustomInventory(inventory, player, 4);
/*     */         break;
/*     */       case HOPPER:
/* 223 */         if (craftinv.getInventory() instanceof TileEntityHopper) {
/* 224 */           getHandle().openHopper((TileEntityHopper)craftinv.getInventory()); break;
/* 225 */         }  if (craftinv.getInventory() instanceof EntityMinecartHopper) {
/* 226 */           getHandle().openMinecartHopper((EntityMinecartHopper)craftinv.getInventory()); break;
/*     */         } 
/* 228 */         openCustomInventory(inventory, player, 9);
/*     */         break;
/*     */       
/*     */       case CREATIVE:
/*     */       case CRAFTING:
/* 233 */         throw new IllegalArgumentException("Can't open a " + type + " inventory!");
/*     */     } 
/* 235 */     if ((getHandle()).activeContainer == formerContainer) {
/* 236 */       return null;
/*     */     }
/* 238 */     (getHandle()).activeContainer.checkReachable = false;
/* 239 */     return (getHandle()).activeContainer.getBukkitView();
/*     */   }
/*     */   
/*     */   private void openCustomInventory(Inventory inventory, EntityPlayer player, int windowType) {
/* 243 */     if (player.playerConnection == null)
/* 244 */       return;  CraftContainer craftContainer = new CraftContainer(inventory, this, player.nextContainerCounter());
/*     */     
/* 246 */     Container container = CraftEventFactory.callInventoryOpenEvent(player, (Container)craftContainer);
/* 247 */     if (container == null)
/*     */       return; 
/* 249 */     String title = container.getBukkitView().getTitle();
/* 250 */     int size = container.getBukkitView().getTopInventory().getSize();
/*     */     
/* 252 */     player.playerConnection.sendPacket((Packet)new PacketPlayOutOpenWindow(container.windowId, windowType, title, size, true));
/* 253 */     (getHandle()).activeContainer = container;
/* 254 */     (getHandle()).activeContainer.addSlotListener((ICrafting)player);
/*     */   }
/*     */   
/*     */   public InventoryView openWorkbench(Location location, boolean force) {
/* 258 */     if (!force) {
/* 259 */       Block block = location.getBlock();
/* 260 */       if (block.getType() != Material.WORKBENCH) {
/* 261 */         return null;
/*     */       }
/*     */     } 
/* 264 */     if (location == null) {
/* 265 */       location = getLocation();
/*     */     }
/* 267 */     getHandle().startCrafting(location.getBlockX(), location.getBlockY(), location.getBlockZ());
/* 268 */     if (force) {
/* 269 */       (getHandle()).activeContainer.checkReachable = false;
/*     */     }
/* 271 */     return (getHandle()).activeContainer.getBukkitView();
/*     */   }
/*     */   
/*     */   public InventoryView openEnchanting(Location location, boolean force) {
/* 275 */     if (!force) {
/* 276 */       Block block = location.getBlock();
/* 277 */       if (block.getType() != Material.ENCHANTMENT_TABLE) {
/* 278 */         return null;
/*     */       }
/*     */     } 
/* 281 */     if (location == null) {
/* 282 */       location = getLocation();
/*     */     }
/* 284 */     getHandle().startEnchanting(location.getBlockX(), location.getBlockY(), location.getBlockZ(), null);
/* 285 */     if (force) {
/* 286 */       (getHandle()).activeContainer.checkReachable = false;
/*     */     }
/* 288 */     return (getHandle()).activeContainer.getBukkitView();
/*     */   }
/*     */   public void openInventory(InventoryView inventory) {
/*     */     CraftContainer craftContainer;
/* 292 */     if (!(getHandle() instanceof EntityPlayer))
/* 293 */       return;  if (((EntityPlayer)getHandle()).playerConnection == null)
/* 294 */       return;  if ((getHandle()).activeContainer != (getHandle()).defaultContainer)
/*     */     {
/* 296 */       ((EntityPlayer)getHandle()).playerConnection.a(new PacketPlayInCloseWindow((getHandle()).activeContainer.windowId));
/*     */     }
/* 298 */     EntityPlayer player = (EntityPlayer)getHandle();
/*     */     
/* 300 */     if (inventory instanceof CraftInventoryView) {
/* 301 */       Container container1 = ((CraftInventoryView)inventory).getHandle();
/*     */     } else {
/* 303 */       craftContainer = new CraftContainer(inventory, player.nextContainerCounter());
/*     */     } 
/*     */ 
/*     */     
/* 307 */     Container container = CraftEventFactory.callInventoryOpenEvent(player, (Container)craftContainer);
/* 308 */     if (container == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 313 */     InventoryType type = inventory.getType();
/* 314 */     int windowType = CraftContainer.getNotchInventoryType(type);
/* 315 */     String title = inventory.getTitle();
/* 316 */     int size = inventory.getTopInventory().getSize();
/* 317 */     player.playerConnection.sendPacket((Packet)new PacketPlayOutOpenWindow(container.windowId, windowType, title, size, false));
/* 318 */     player.activeContainer = container;
/* 319 */     player.activeContainer.addSlotListener((ICrafting)player);
/*     */   }
/*     */   
/*     */   public void closeInventory() {
/* 323 */     getHandle().closeInventory();
/*     */   }
/*     */   
/*     */   public boolean isBlocking() {
/* 327 */     return getHandle().isBlocking();
/*     */   }
/*     */   
/*     */   public boolean setWindowProperty(InventoryView.Property prop, int value) {
/* 331 */     return false;
/*     */   }
/*     */   
/*     */   public int getExpToLevel() {
/* 335 */     return getHandle().getExpToLevel();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftHumanEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */