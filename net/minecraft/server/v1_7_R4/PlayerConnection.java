/*      */ package net.minecraft.server.v1_7_R4;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*      */ import java.util.logging.Level;
/*      */ import net.minecraft.util.com.google.common.base.Charsets;
/*      */ import net.minecraft.util.com.google.common.collect.Lists;
/*      */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.command.CommandException;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.util.LazyPlayerSet;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.util.Waitable;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.Event;
/*      */ import org.bukkit.event.block.Action;
/*      */ import org.bukkit.event.block.SignChangeEvent;
/*      */ import org.bukkit.event.inventory.ClickType;
/*      */ import org.bukkit.event.inventory.CraftItemEvent;
/*      */ import org.bukkit.event.inventory.InventoryAction;
/*      */ import org.bukkit.event.inventory.InventoryClickEvent;
/*      */ import org.bukkit.event.inventory.InventoryCreativeEvent;
/*      */ import org.bukkit.event.inventory.InventoryType;
/*      */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*      */ import org.bukkit.event.player.PlayerAnimationEvent;
/*      */ import org.bukkit.event.player.PlayerChatEvent;
/*      */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*      */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*      */ import org.bukkit.event.player.PlayerInteractEvent;
/*      */ import org.bukkit.event.player.PlayerItemHeldEvent;
/*      */ import org.bukkit.event.player.PlayerKickEvent;
/*      */ import org.bukkit.event.player.PlayerMoveEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent;
/*      */ import org.bukkit.event.player.PlayerToggleFlightEvent;
/*      */ import org.bukkit.event.player.PlayerToggleSneakEvent;
/*      */ import org.bukkit.event.player.PlayerToggleSprintEvent;
/*      */ import org.bukkit.inventory.CraftingInventory;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.InventoryView;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.Recipe;
/*      */ import org.bukkit.util.NumberConversions;
/*      */ import org.github.paperspigot.PaperSpigotConfig;
/*      */ import org.spigotmc.SpigotConfig;
/*      */ 
/*      */ public class PlayerConnection implements PacketPlayInListener {
/*   66 */   private static final Logger c = LogManager.getLogger();
/*      */   public final NetworkManager networkManager;
/*      */   private final MinecraftServer minecraftServer;
/*      */   public EntityPlayer player;
/*      */   private int e;
/*      */   private int f;
/*      */   private boolean g;
/*      */   private int h;
/*      */   private long i;
/*   75 */   private static Random j = new Random(); private long k;
/*      */   private volatile int chatThrottle;
/*   77 */   private static final AtomicIntegerFieldUpdater chatSpamField = AtomicIntegerFieldUpdater.newUpdater(PlayerConnection.class, "chatThrottle");
/*      */   private int x;
/*   79 */   private IntHashMap n = new IntHashMap(); private double y; private double z; private double q;
/*      */   public boolean checkMovement = true;
/*      */   private boolean processedDisconnect;
/*      */   private final CraftServer server;
/*      */   private int lastTick;
/*      */   private int lastDropTick;
/*      */   private int dropCount;
/*      */   private static final int SURVIVAL_PLACE_DISTANCE_SQUARED = 36;
/*      */   private static final int CREATIVE_PLACE_DISTANCE_SQUARED = 49;
/*      */   private double lastPosX;
/*      */   private double lastPosY;
/*      */   private double lastPosZ;
/*      */   private float lastPitch;
/*      */   private float lastYaw;
/*      */   private boolean justTeleported;
/*      */   private boolean hasMoved;
/*      */   Long lastPacket;
/*      */   private Item lastMaterial;
/*      */   
/*   98 */   public PlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) { this.lastTick = MinecraftServer.currentTick;
/*   99 */     this.lastDropTick = MinecraftServer.currentTick;
/*  100 */     this.dropCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  105 */     this.lastPosX = Double.MAX_VALUE;
/*  106 */     this.lastPosY = Double.MAX_VALUE;
/*  107 */     this.lastPosZ = Double.MAX_VALUE;
/*  108 */     this.lastPitch = Float.MAX_VALUE;
/*  109 */     this.lastYaw = Float.MAX_VALUE;
/*  110 */     this.justTeleported = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  582 */     this.lastPlace = -1L;
/*  583 */     this.packets = 0; this.minecraftServer = minecraftserver; this.networkManager = networkmanager; networkmanager.a(this); this.player = entityplayer; entityplayer.playerConnection = this; this.server = minecraftserver.server; }
/*      */   public CraftPlayer getPlayer() { return (this.player == null) ? null : this.player.getBukkitEntity(); }
/*      */   private static final HashSet<Integer> invalidItems = new HashSet<Integer>(Arrays.asList(new Integer[] { Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(26), Integer.valueOf(34), Integer.valueOf(36), Integer.valueOf(43), Integer.valueOf(51), Integer.valueOf(52), Integer.valueOf(55), Integer.valueOf(59), Integer.valueOf(60), Integer.valueOf(62), Integer.valueOf(63), Integer.valueOf(64), Integer.valueOf(68), Integer.valueOf(71), Integer.valueOf(74), Integer.valueOf(75), Integer.valueOf(83), Integer.valueOf(90), Integer.valueOf(92), Integer.valueOf(93), Integer.valueOf(94), Integer.valueOf(104), Integer.valueOf(105), Integer.valueOf(115), Integer.valueOf(117), Integer.valueOf(118), Integer.valueOf(119), Integer.valueOf(125), Integer.valueOf(127), Integer.valueOf(132), Integer.valueOf(140), Integer.valueOf(141), Integer.valueOf(142), Integer.valueOf(144) }));
/*  586 */   private long lastPlace; private int packets; public void a() { this.g = false; this.e++; this.minecraftServer.methodProfiler.a("keepAlive"); if (this.e - this.k > 40L) { this.k = this.e; this.i = d(); this.h = (int)this.i; sendPacket(new PacketPlayOutKeepAlive(this.h)); }  int spam; while ((spam = this.chatThrottle) > 0 && !chatSpamField.compareAndSet(this, spam, spam - 1)); if (this.x > 0) this.x--;  if (this.player.x() > 0L && this.minecraftServer.getIdleTimeout() > 0 && MinecraftServer.ar() - this.player.x() > (this.minecraftServer.getIdleTimeout() * 1000 * 60)) disconnect("You have been idle for too long!");  } public NetworkManager b() { return this.networkManager; } public void disconnect(String s) { String leaveMessage = EnumChatFormat.YELLOW + this.player.getName() + " left the game."; PlayerKickEvent event = new PlayerKickEvent(this.server.getPlayer(this.player), s, leaveMessage); if (this.server.getServer().isRunning()) this.server.getPluginManager().callEvent((Event)event);  if (event.isCancelled()) return;  s = event.getReason(); ChatComponentText chatcomponenttext = new ChatComponentText(s); this.networkManager.handle(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener[] { new PlayerConnectionFuture(this, chatcomponenttext) }); a(chatcomponenttext); this.networkManager.g(); } public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) { this.player.a(packetplayinsteervehicle.c(), packetplayinsteervehicle.d(), packetplayinsteervehicle.e(), packetplayinsteervehicle.f()); } public void a(PacketPlayInBlockPlace packetplayinblockplace) { boolean throttled = false;
/*      */     
/*  588 */     if (PaperSpigotConfig.interactLimitEnabled && this.lastPlace != -1L && packetplayinblockplace.timestamp - this.lastPlace < 30L && this.packets++ >= 4) {
/*  589 */       throttled = true;
/*  590 */     } else if (packetplayinblockplace.timestamp - this.lastPlace >= 30L || this.lastPlace == -1L) {
/*      */       
/*  592 */       this.lastPlace = packetplayinblockplace.timestamp;
/*  593 */       this.packets = 0;
/*      */     } 
/*      */     
/*  596 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/*      */ 
/*      */     
/*  599 */     if (this.player.dead) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  608 */     if (packetplayinblockplace.getFace() == 255) {
/*  609 */       if (packetplayinblockplace.getItemStack() != null && packetplayinblockplace.getItemStack().getItem() == this.lastMaterial && this.lastPacket != null && packetplayinblockplace.timestamp - this.lastPacket.longValue() < 100L) {
/*  610 */         this.lastPacket = null;
/*      */         return;
/*      */       } 
/*      */     } else {
/*  614 */       this.lastMaterial = (packetplayinblockplace.getItemStack() == null) ? null : packetplayinblockplace.getItemStack().getItem();
/*  615 */       this.lastPacket = Long.valueOf(packetplayinblockplace.timestamp);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  620 */     boolean always = false;
/*      */ 
/*      */     
/*  623 */     ItemStack itemstack = this.player.inventory.getItemInHand();
/*  624 */     boolean flag = false;
/*  625 */     int i = packetplayinblockplace.c();
/*  626 */     int j = packetplayinblockplace.d();
/*  627 */     int k = packetplayinblockplace.e();
/*  628 */     int l = packetplayinblockplace.getFace();
/*      */     
/*  630 */     this.player.v();
/*  631 */     if (packetplayinblockplace.getFace() == 255) {
/*  632 */       if (itemstack == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  637 */       int itemstackAmount = itemstack.count;
/*      */       
/*  639 */       if (!throttled) {
/*  640 */         PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.RIGHT_CLICK_AIR, itemstack);
/*  641 */         if (event.useItemInHand() != Event.Result.DENY) {
/*  642 */           this.player.playerInteractManager.useItem(this.player, this.player.world, itemstack);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  650 */       always = (itemstack.count != itemstackAmount || itemstack.getItem() == Item.getItemOf(Blocks.WATER_LILY));
/*      */     }
/*  652 */     else if (packetplayinblockplace.d() >= this.minecraftServer.getMaxBuildHeight() - 1 && (packetplayinblockplace.getFace() == 1 || packetplayinblockplace.d() >= this.minecraftServer.getMaxBuildHeight())) {
/*  653 */       ChatMessage chatmessage = new ChatMessage("build.tooHigh", new Object[] { Integer.valueOf(this.minecraftServer.getMaxBuildHeight()) });
/*      */       
/*  655 */       chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  656 */       this.player.playerConnection.sendPacket(new PacketPlayOutChat(chatmessage));
/*  657 */       flag = true;
/*      */     } else {
/*      */       
/*  660 */       Location eyeLoc = getPlayer().getEyeLocation();
/*  661 */       double reachDistance = NumberConversions.square(eyeLoc.getX() - i) + NumberConversions.square(eyeLoc.getY() - j) + NumberConversions.square(eyeLoc.getZ() - k);
/*  662 */       if (reachDistance > ((getPlayer().getGameMode() == GameMode.CREATIVE) ? 49 : 36)) {
/*      */         return;
/*      */       }
/*      */       
/*  666 */       if (throttled || !this.player.playerInteractManager.interact(this.player, worldserver, itemstack, i, j, k, l, packetplayinblockplace.h(), packetplayinblockplace.i(), packetplayinblockplace.j())) {
/*  667 */         always = true;
/*      */       }
/*      */ 
/*      */       
/*  671 */       flag = true;
/*      */     } 
/*      */     
/*  674 */     if (flag) {
/*  675 */       this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, worldserver));
/*  676 */       if (l == 0) {
/*  677 */         j--;
/*      */       }
/*      */       
/*  680 */       if (l == 1) {
/*  681 */         j++;
/*      */       }
/*      */       
/*  684 */       if (l == 2) {
/*  685 */         k--;
/*      */       }
/*      */       
/*  688 */       if (l == 3) {
/*  689 */         k++;
/*      */       }
/*      */       
/*  692 */       if (l == 4) {
/*  693 */         i--;
/*      */       }
/*      */       
/*  696 */       if (l == 5) {
/*  697 */         i++;
/*      */       }
/*      */       
/*  700 */       this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, worldserver));
/*      */     } 
/*      */     
/*  703 */     itemstack = this.player.inventory.getItemInHand();
/*  704 */     if (itemstack != null && itemstack.count == 0) {
/*  705 */       this.player.inventory.items[this.player.inventory.itemInHandIndex] = null;
/*  706 */       itemstack = null;
/*      */     } 
/*      */     
/*  709 */     if (itemstack == null || itemstack.n() == 0)
/*  710 */     { this.player.g = true;
/*  711 */       this.player.inventory.items[this.player.inventory.itemInHandIndex] = ItemStack.b(this.player.inventory.items[this.player.inventory.itemInHandIndex]);
/*  712 */       Slot slot = this.player.activeContainer.getSlot(this.player.inventory, this.player.inventory.itemInHandIndex);
/*      */       
/*  714 */       this.player.activeContainer.b();
/*  715 */       this.player.g = false;
/*      */       
/*  717 */       if (!ItemStack.matches(this.player.inventory.getItemInHand(), packetplayinblockplace.getItemStack()) || always)
/*  718 */         sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, slot.rawSlotIndex, this.player.inventory.getItemInHand()));  }  }
/*      */   public void a(PacketPlayInFlying packetplayinflying) { if (Double.isNaN(packetplayinflying.x) || Double.isNaN(packetplayinflying.y) || Double.isNaN(packetplayinflying.z) || Double.isNaN(packetplayinflying.stance)) { c.warn(this.player.getName() + " was caught trying to crash the server with an invalid position."); getPlayer().kickPlayer("NaN in position (Hacking?)"); return; }  WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension); this.g = true; if (!this.player.viewingCredits) { if (!this.checkMovement) { double d0 = packetplayinflying.d() - this.z; if (packetplayinflying.c() == this.y && d0 * d0 < 0.01D && packetplayinflying.e() == this.q)
/*      */           this.checkMovement = true;  }  CraftPlayer craftPlayer = getPlayer(); if (!this.hasMoved) { Location curPos = craftPlayer.getLocation(); this.lastPosX = curPos.getX(); this.lastPosY = curPos.getY(); this.lastPosZ = curPos.getZ(); this.lastYaw = curPos.getYaw(); this.lastPitch = curPos.getPitch(); this.hasMoved = true; }  Location from = new Location(craftPlayer.getWorld(), this.lastPosX, this.lastPosY, this.lastPosZ, this.lastYaw, this.lastPitch); Location to = craftPlayer.getLocation().clone(); if (packetplayinflying.hasPos && (!packetplayinflying.hasPos || packetplayinflying.y != -999.0D || packetplayinflying.stance != -999.0D)) { to.setX(packetplayinflying.x); to.setY(packetplayinflying.y); to.setZ(packetplayinflying.z); }  if (packetplayinflying.hasLook) { to.setYaw(packetplayinflying.yaw); to.setPitch(packetplayinflying.pitch); }  double delta = Math.pow(this.lastPosX - to.getX(), 2.0D) + Math.pow(this.lastPosY - to.getY(), 2.0D) + Math.pow(this.lastPosZ - to.getZ(), 2.0D); float deltaAngle = Math.abs(this.lastYaw - to.getYaw()) + Math.abs(this.lastPitch - to.getPitch()); if ((delta > 0.00390625D || deltaAngle > 10.0F) && this.checkMovement && !this.player.dead) { this.lastPosX = to.getX(); this.lastPosY = to.getY(); this.lastPosZ = to.getZ(); this.lastYaw = to.getYaw(); this.lastPitch = to.getPitch(); Location oldTo = to.clone(); PlayerMoveEvent event = new PlayerMoveEvent((Player)craftPlayer, from, to); this.server.getPluginManager().callEvent((Event)event); if (event.isCancelled()) { this.player.playerConnection.sendPacket(new PacketPlayOutPosition(from.getX(), from.getY() + 1.6200000047683716D, from.getZ(), from.getYaw(), from.getPitch(), false)); return; }  if (!oldTo.equals(event.getTo()) && !event.isCancelled()) { this.player.getBukkitEntity().teleport(event.getTo(), PlayerTeleportEvent.TeleportCause.UNKNOWN); return; }  if (!from.equals(getPlayer().getLocation()) && this.justTeleported) { this.justTeleported = false; return; }  }  if (this.checkMovement && !this.player.dead) { if (this.player.vehicle != null) { float f = this.player.yaw; float f1 = this.player.pitch; this.player.vehicle.ac(); double d12 = this.player.locX; double d13 = this.player.locY; double d14 = this.player.locZ; if (packetplayinflying.k()) { f = packetplayinflying.g(); f1 = packetplayinflying.h(); }  this.player.onGround = packetplayinflying.i(); this.player.i(); this.player.V = 0.0F; this.player.setLocation(d12, d13, d14, f, f1); if (this.player.vehicle != null)
/*      */             this.player.vehicle.ac();  this.minecraftServer.getPlayerList().d(this.player); if (this.checkMovement) { this.y = this.player.locX; this.z = this.player.locY; this.q = this.player.locZ; }  worldserver.playerJoinedWorld(this.player); return; }  if (this.player.isSleeping()) { this.player.i(); this.player.setLocation(this.y, this.z, this.q, this.player.yaw, this.player.pitch); worldserver.playerJoinedWorld(this.player); return; }  double d0 = this.player.locY; this.y = this.player.locX; this.z = this.player.locY; this.q = this.player.locZ; double d1 = this.player.locX; double d2 = this.player.locY; double d3 = this.player.locZ; float f2 = this.player.yaw; float f3 = this.player.pitch; if (packetplayinflying.j() && packetplayinflying.d() == -999.0D && packetplayinflying.f() == -999.0D)
/*      */           packetplayinflying.a(false);  if (packetplayinflying.j()) { d1 = packetplayinflying.c(); d2 = packetplayinflying.d(); d3 = packetplayinflying.e(); double d = packetplayinflying.f() - packetplayinflying.d(); if (!this.player.isSleeping() && (d > 1.65D || d < 0.1D)) { disconnect("Illegal stance"); c.warn(this.player.getName() + " had an illegal stance: " + d); return; }  if (Math.abs(packetplayinflying.c()) > 3.2E7D || Math.abs(packetplayinflying.e()) > 3.2E7D) { disconnect("Illegal position"); return; }  }  if (packetplayinflying.k()) { f2 = packetplayinflying.g(); f3 = packetplayinflying.h(); }  this.player.i(); this.player.V = 0.0F; this.player.setLocation(this.y, this.z, this.q, f2, f3); if (!this.checkMovement)
/*      */           return;  double d4 = d1 - this.player.locX; double d5 = d2 - this.player.locY; double d6 = d3 - this.player.locZ; double d7 = Math.max(Math.abs(d4), Math.abs(this.player.motX)); double d8 = Math.max(Math.abs(d5), Math.abs(this.player.motY)); double d9 = Math.max(Math.abs(d6), Math.abs(this.player.motZ)); double d10 = d7 * d7 + d8 * d8 + d9 * d9; if (d10 > SpigotConfig.movedTooQuicklyThreshold && this.checkMovement && (!this.minecraftServer.N() || !this.minecraftServer.M().equals(this.player.getName()))) { c.warn(this.player.getName() + " moved too quickly! " + d4 + "," + d5 + "," + d6 + " (" + d7 + ", " + d8 + ", " + d9 + ")"); a(this.y, this.z, this.q, this.player.yaw, this.player.pitch); return; }  float f4 = 0.0625F; boolean flag = worldserver.getCubes(this.player, this.player.boundingBox.clone().shrink(f4, f4, f4)).isEmpty(); if (this.player.onGround && !packetplayinflying.i() && d5 > 0.0D)
/*      */           this.player.bj();  this.player.move(d4, d5, d6); this.player.onGround = packetplayinflying.i(); this.player.checkMovement(d4, d5, d6); double d11 = d5; d4 = d1 - this.player.locX; d5 = d2 - this.player.locY; if (d5 > -0.5D || d5 < 0.5D)
/*  725 */           d5 = 0.0D;  d6 = d3 - this.player.locZ; d10 = d4 * d4 + d5 * d5 + d6 * d6; boolean flag1 = false; if (d10 > SpigotConfig.movedWronglyThreshold && !this.player.isSleeping() && !this.player.playerInteractManager.isCreative()) { flag1 = true; c.warn(this.player.getName() + " moved wrongly!"); }  this.player.setLocation(d1, d2, d3, f2, f3); boolean flag2 = worldserver.getCubes(this.player, this.player.boundingBox.clone().shrink(f4, f4, f4)).isEmpty(); if (flag && (flag1 || !flag2) && !this.player.isSleeping()) { a(this.y, this.z, this.q, f2, f3); return; }  AxisAlignedBB axisalignedbb = this.player.boundingBox.clone().grow(f4, f4, f4).a(0.0D, -0.55D, 0.0D); if (!this.minecraftServer.getAllowFlight() && !this.player.abilities.canFly && !worldserver.c(axisalignedbb)) { if (d11 >= -0.03125D) { this.f++; if (this.f > 80) { c.warn(this.player.getName() + " was kicked for floating too long!"); disconnect("Flying is not enabled on this server"); return; }  }  } else { this.f = 0; }  this.player.onGround = packetplayinflying.i(); this.minecraftServer.getPlayerList().d(this.player); this.player.b(this.player.locY - d0, packetplayinflying.i()); } else if (this.e % 20 == 0) { a(this.y, this.z, this.q, this.player.yaw, this.player.pitch); }  }  } public void a(IChatBaseComponent ichatbasecomponent) { if (this.processedDisconnect) {
/*      */       return;
/*      */     }
/*  728 */     this.processedDisconnect = true;
/*      */ 
/*      */     
/*  731 */     c.info(this.player.getName() + " lost connection: " + ichatbasecomponent.c());
/*  732 */     this.minecraftServer.az();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  741 */     this.player.n();
/*  742 */     String quitMessage = this.minecraftServer.getPlayerList().disconnect(this.player);
/*  743 */     if (quitMessage != null && quitMessage.length() > 0) {
/*  744 */       this.minecraftServer.getPlayerList().sendMessage(CraftChatMessage.fromString(quitMessage));
/*      */     }
/*      */     
/*  747 */     if (this.minecraftServer.N() && this.player.getName().equals(this.minecraftServer.M()))
/*  748 */     { c.info("Stopping singleplayer server as player logged out");
/*  749 */       this.minecraftServer.safeShutdown(); }  } public void a(double d0, double d1, double d2, float f, float f1) { CraftPlayer craftPlayer = getPlayer(); Location from = craftPlayer.getLocation(); Location to = new Location(getPlayer().getWorld(), d0, d1, d2, f, f1); PlayerTeleportEvent event = new PlayerTeleportEvent((Player)craftPlayer, from, to, PlayerTeleportEvent.TeleportCause.UNKNOWN); this.server.getPluginManager().callEvent((Event)event); from = event.getFrom(); to = event.isCancelled() ? from : event.getTo(); teleport(to); }
/*      */   public void teleport(Location dest) { double d0 = dest.getX(); double d1 = dest.getY(); double d2 = dest.getZ(); float f = dest.getYaw(); float f1 = dest.getPitch(); if (Float.isNaN(f)) f = 0.0F;  if (Float.isNaN(f1)) f1 = 0.0F;  this.lastPosX = d0; this.lastPosY = d1; this.lastPosZ = d2; this.lastYaw = f; this.lastPitch = f1; this.justTeleported = true; this.checkMovement = false; this.y = d0; this.z = d1; this.q = d2; this.player.setLocation(d0, d1, d2, f, f1); this.player.playerConnection.sendPacket(new PacketPlayOutPosition(d0, d1 + 1.6200000047683716D, d2, f, f1, false)); }
/*      */   public void a(PacketPlayInBlockDig packetplayinblockdig) { if (this.player.dead) return;  WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension); this.player.v(); if (packetplayinblockdig.g() == 4) { if (this.lastDropTick != MinecraftServer.currentTick) { this.dropCount = 0; this.lastDropTick = MinecraftServer.currentTick; } else { this.dropCount++; if (this.dropCount >= 20) { this; c.warn(this.player.getName() + " dropped their items too quickly!"); disconnect("You dropped your items too quickly (Hacking?)"); return; }  }  this.player.a(false); } else if (packetplayinblockdig.g() == 3) { this.player.a(true); } else if (packetplayinblockdig.g() == 5) { this.player.bA(); } else { boolean flag = false; if (packetplayinblockdig.g() == 0) flag = true;  if (packetplayinblockdig.g() == 1) flag = true;  if (packetplayinblockdig.g() == 2) flag = true;  int i = packetplayinblockdig.c(); int j = packetplayinblockdig.d(); int k = packetplayinblockdig.e(); if (flag) { double d0 = this.player.locX - i + 0.5D; double d1 = this.player.locY - j + 0.5D + 1.5D; double d2 = this.player.locZ - k + 0.5D; double d3 = d0 * d0 + d1 * d1 + d2 * d2; if (d3 > 36.0D) return;  if (j >= this.minecraftServer.getMaxBuildHeight()) return;  }  if (packetplayinblockdig.g() == 0) { if (!this.minecraftServer.a(worldserver, i, j, k, this.player)) { this.player.playerInteractManager.dig(i, j, k, packetplayinblockdig.f()); } else { CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, i, j, k, packetplayinblockdig.f(), this.player.inventory.getItemInHand()); this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, worldserver)); TileEntity tileentity = worldserver.getTileEntity(i, j, k); if (tileentity != null)
/*      */             this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());  }  } else if (packetplayinblockdig.g() == 2) { this.player.playerInteractManager.a(i, j, k); if (worldserver.getType(i, j, k).getMaterial() != Material.AIR)
/*      */           this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, worldserver));  } else if (packetplayinblockdig.g() == 1) { this.player.playerInteractManager.c(i, j, k); if (worldserver.getType(i, j, k).getMaterial() != Material.AIR)
/*      */           this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, worldserver));  }  }  }
/*  755 */   public void sendPacket(Packet packet) { if (((Integer)NetworkManager.a(this.networkManager).attr(NetworkManager.protocolVersion).get()).intValue() >= 17)
/*      */     {
/*  757 */       if (packet instanceof PacketPlayOutWindowItems) {
/*      */         
/*  759 */         PacketPlayOutWindowItems items = (PacketPlayOutWindowItems)packet;
/*  760 */         if (this.player.activeContainer instanceof ContainerEnchantTable && this.player.activeContainer.windowId == items.a)
/*      */         {
/*      */           
/*  763 */           ItemStack[] old = items.b;
/*  764 */           items.b = new ItemStack[old.length + 1];
/*  765 */           items.b[0] = old[0];
/*  766 */           System.arraycopy(old, 1, items.b, 2, old.length - 1);
/*  767 */           items.b[1] = new ItemStack(Items.INK_SACK, 3, 4);
/*      */         }
/*      */       
/*  770 */       } else if (packet instanceof PacketPlayOutSetSlot) {
/*      */         
/*  772 */         PacketPlayOutSetSlot items = (PacketPlayOutSetSlot)packet;
/*  773 */         if (this.player.activeContainer instanceof ContainerEnchantTable && this.player.activeContainer.windowId == items.a)
/*      */         {
/*      */           
/*  776 */           if (items.b >= 1)
/*      */           {
/*  778 */             items.b++;
/*      */           }
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  784 */     if (packet instanceof PacketPlayOutChat) {
/*  785 */       PacketPlayOutChat packetplayoutchat = (PacketPlayOutChat)packet;
/*  786 */       EnumChatVisibility enumchatvisibility = this.player.getChatFlags();
/*      */       
/*  788 */       if (enumchatvisibility == EnumChatVisibility.HIDDEN) {
/*      */         return;
/*      */       }
/*      */       
/*  792 */       if (enumchatvisibility == EnumChatVisibility.SYSTEM && !packetplayoutchat.d()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  798 */     if (packet == null)
/*      */       return; 
/*  800 */     if (packet instanceof PacketPlayOutSpawnPosition) {
/*  801 */       PacketPlayOutSpawnPosition packet6 = (PacketPlayOutSpawnPosition)packet;
/*  802 */       this.player.compassTarget = new Location(getPlayer().getWorld(), packet6.x, packet6.y, packet6.z);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  807 */       this.networkManager.handle(packet, new GenericFutureListener[0]);
/*  808 */     } catch (Throwable throwable) {
/*  809 */       CrashReport crashreport = CrashReport.a(throwable, "Sending packet");
/*  810 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Packet being sent");
/*      */       
/*  812 */       crashreportsystemdetails.a("Packet class", new CrashReportConnectionPacketClass(this, packet));
/*  813 */       throw new ReportedException(crashreport);
/*      */     }  }
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {
/*  819 */     if (this.player.dead)
/*      */       return; 
/*  821 */     if (packetplayinhelditemslot.c() >= 0 && packetplayinhelditemslot.c() < PlayerInventory.getHotbarSize()) {
/*  822 */       PlayerItemHeldEvent event = new PlayerItemHeldEvent((Player)getPlayer(), this.player.inventory.itemInHandIndex, packetplayinhelditemslot.c());
/*  823 */       this.server.getPluginManager().callEvent((Event)event);
/*  824 */       if (event.isCancelled()) {
/*  825 */         sendPacket(new PacketPlayOutHeldItemSlot(this.player.inventory.itemInHandIndex));
/*  826 */         this.player.v();
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  831 */       this.player.inventory.itemInHandIndex = packetplayinhelditemslot.c();
/*  832 */       this.player.v();
/*      */     } else {
/*  834 */       c.warn(this.player.getName() + " tried to set an invalid carried item");
/*  835 */       disconnect("Invalid hotbar selection (Hacking?)");
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInChat packetplayinchat) {
/*  840 */     if (this.player.dead || this.player.getChatFlags() == EnumChatVisibility.HIDDEN) {
/*  841 */       ChatMessage chatmessage = new ChatMessage("chat.cannotSend", new Object[0]);
/*      */       
/*  843 */       chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  844 */       sendPacket(new PacketPlayOutChat(chatmessage));
/*      */     } else {
/*  846 */       this.player.v();
/*  847 */       String s = packetplayinchat.c();
/*      */       
/*  849 */       s = StringUtils.normalizeSpace(s);
/*      */       
/*  851 */       for (int i = 0; i < s.length(); i++) {
/*  852 */         if (!SharedConstants.isAllowedChatCharacter(s.charAt(i))) {
/*      */           
/*  854 */           if (packetplayinchat.a()) {
/*  855 */             Waitable waitable = new Waitable()
/*      */               {
/*      */                 protected Object evaluate() {
/*  858 */                   PlayerConnection.this.disconnect("Illegal characters in chat");
/*  859 */                   return null;
/*      */                 }
/*      */               };
/*      */             
/*  863 */             this.minecraftServer.processQueue.add(waitable);
/*      */             
/*      */             try {
/*  866 */               waitable.get();
/*  867 */             } catch (InterruptedException e) {
/*  868 */               Thread.currentThread().interrupt();
/*  869 */             } catch (ExecutionException e) {
/*  870 */               throw new RuntimeException(e);
/*      */             } 
/*      */           } else {
/*  873 */             disconnect("Illegal characters in chat");
/*      */           } 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/*  881 */       if (!packetplayinchat.a()) {
/*      */         try {
/*  883 */           this.minecraftServer.server.playerCommandState = true;
/*  884 */           handleCommand(s);
/*      */         } finally {
/*  886 */           this.minecraftServer.server.playerCommandState = false;
/*      */         } 
/*  888 */       } else if (s.isEmpty()) {
/*  889 */         c.warn(this.player.getName() + " tried to send an empty message");
/*  890 */       } else if (getPlayer().isConversing()) {
/*      */         
/*  892 */         final String message = s;
/*  893 */         this.minecraftServer.processQueue.add(new Waitable()
/*      */             {
/*      */               
/*      */               protected Object evaluate()
/*      */               {
/*  898 */                 PlayerConnection.this.getPlayer().acceptConversationInput(message);
/*  899 */                 return null;
/*      */               }
/*      */             });
/*      */       }
/*  903 */       else if (this.player.getChatFlags() == EnumChatVisibility.SYSTEM) {
/*  904 */         ChatMessage chatmessage = new ChatMessage("chat.cannotSend", new Object[0]);
/*      */         
/*  906 */         chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  907 */         sendPacket(new PacketPlayOutChat(chatmessage));
/*      */       } else {
/*  909 */         chat(s, true);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  918 */       boolean counted = true;
/*  919 */       for (String exclude : SpigotConfig.spamExclusions) {
/*      */         
/*  921 */         if (exclude != null && s.startsWith(exclude)) {
/*      */           
/*  923 */           counted = false;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  929 */       if (counted && chatSpamField.addAndGet((T)this, 20) > 200 && !this.minecraftServer.getPlayerList().isOp(this.player.getProfile())) {
/*  930 */         if (packetplayinchat.a()) {
/*  931 */           Waitable waitable = new Waitable()
/*      */             {
/*      */               protected Object evaluate() {
/*  934 */                 PlayerConnection.this.disconnect("disconnect.spam");
/*  935 */                 return null;
/*      */               }
/*      */             };
/*      */           
/*  939 */           this.minecraftServer.processQueue.add(waitable);
/*      */           
/*      */           try {
/*  942 */             waitable.get();
/*  943 */           } catch (InterruptedException e) {
/*  944 */             Thread.currentThread().interrupt();
/*  945 */           } catch (ExecutionException e) {
/*  946 */             throw new RuntimeException(e);
/*      */           } 
/*      */         } else {
/*  949 */           disconnect("disconnect.spam");
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void chat(String s, boolean async) {
/*  958 */     if (s.isEmpty() || this.player.getChatFlags() == EnumChatVisibility.HIDDEN) {
/*      */       return;
/*      */     }
/*      */     
/*  962 */     if (!async && s.startsWith("/")) {
/*  963 */       handleCommand(s);
/*  964 */     } else if (this.player.getChatFlags() != EnumChatVisibility.SYSTEM) {
/*      */ 
/*      */       
/*  967 */       CraftPlayer craftPlayer = getPlayer();
/*  968 */       AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(async, (Player)craftPlayer, s, (Set)new LazyPlayerSet());
/*  969 */       this.server.getPluginManager().callEvent((Event)event);
/*      */       
/*  971 */       if ((PlayerChatEvent.getHandlerList().getRegisteredListeners()).length != 0) {
/*      */         
/*  973 */         final PlayerChatEvent queueEvent = new PlayerChatEvent((Player)craftPlayer, event.getMessage(), event.getFormat(), event.getRecipients());
/*  974 */         queueEvent.setCancelled(event.isCancelled());
/*  975 */         Waitable waitable = new Waitable()
/*      */           {
/*      */             protected Object evaluate() {
/*  978 */               Bukkit.getPluginManager().callEvent((Event)queueEvent);
/*      */               
/*  980 */               if (queueEvent.isCancelled()) {
/*  981 */                 return null;
/*      */               }
/*      */               
/*  984 */               String message = String.format(queueEvent.getFormat(), new Object[] { this.val$queueEvent.getPlayer().getDisplayName(), this.val$queueEvent.getMessage() });
/*  985 */               PlayerConnection.this.minecraftServer.console.sendMessage(message);
/*  986 */               if (((LazyPlayerSet)queueEvent.getRecipients()).isLazy()) {
/*  987 */                 for (Object player : (PlayerConnection.this.minecraftServer.getPlayerList()).players) {
/*  988 */                   ((EntityPlayer)player).sendMessage(CraftChatMessage.fromString(message));
/*      */                 }
/*      */               } else {
/*  991 */                 for (Player player : queueEvent.getRecipients()) {
/*  992 */                   player.sendMessage(message);
/*      */                 }
/*      */               } 
/*  995 */               return null; }
/*      */           };
/*  997 */         if (async) {
/*  998 */           this.minecraftServer.processQueue.add(waitable);
/*      */         } else {
/* 1000 */           waitable.run();
/*      */         } 
/*      */         try {
/* 1003 */           waitable.get();
/* 1004 */         } catch (InterruptedException e) {
/* 1005 */           Thread.currentThread().interrupt();
/* 1006 */         } catch (ExecutionException e) {
/* 1007 */           throw new RuntimeException("Exception processing chat event", e.getCause());
/*      */         } 
/*      */       } else {
/* 1010 */         if (event.isCancelled()) {
/*      */           return;
/*      */         }
/*      */         
/* 1014 */         s = String.format(event.getFormat(), new Object[] { event.getPlayer().getDisplayName(), event.getMessage() });
/* 1015 */         this.minecraftServer.console.sendMessage(s);
/* 1016 */         if (((LazyPlayerSet)event.getRecipients()).isLazy()) {
/* 1017 */           for (Object recipient : (this.minecraftServer.getPlayerList()).players) {
/* 1018 */             ((EntityPlayer)recipient).sendMessage(CraftChatMessage.fromString(s));
/*      */           }
/*      */         } else {
/* 1021 */           for (Player recipient : event.getRecipients()) {
/* 1022 */             recipient.sendMessage(s);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void handleCommand(String s) {
/* 1031 */     SpigotTimings.playerCommandTimer.startTiming();
/*      */ 
/*      */     
/* 1034 */     if (SpigotConfig.logCommands) { this; c.info(this.player.getName() + " issued server command: " + s); }
/*      */     
/* 1036 */     CraftPlayer player = getPlayer();
/*      */     
/* 1038 */     PlayerCommandPreprocessEvent event = new PlayerCommandPreprocessEvent((Player)player, s, (Set)new LazyPlayerSet());
/* 1039 */     this.server.getPluginManager().callEvent((Event)event);
/*      */     
/* 1041 */     if (event.isCancelled()) {
/* 1042 */       SpigotTimings.playerCommandTimer.stopTiming();
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/* 1047 */       if (this.server.dispatchCommand((CommandSender)event.getPlayer(), event.getMessage().substring(1))) {
/* 1048 */         SpigotTimings.playerCommandTimer.stopTiming();
/*      */         return;
/*      */       } 
/* 1051 */     } catch (CommandException ex) {
/* 1052 */       player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
/* 1053 */       Logger.getLogger(PlayerConnection.class.getName()).log(Level.SEVERE, (String)null, (Throwable)ex);
/* 1054 */       SpigotTimings.playerCommandTimer.stopTiming();
/*      */       return;
/*      */     } 
/* 1057 */     SpigotTimings.playerCommandTimer.stopTiming();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(PacketPlayInArmAnimation packetplayinarmanimation) {
/* 1063 */     if (this.player.dead)
/* 1064 */       return;  this.player.v();
/* 1065 */     if (packetplayinarmanimation.d() == 1) {
/*      */       
/* 1067 */       float f = 1.0F;
/* 1068 */       float f1 = this.player.lastPitch + (this.player.pitch - this.player.lastPitch) * f;
/* 1069 */       float f2 = this.player.lastYaw + (this.player.yaw - this.player.lastYaw) * f;
/* 1070 */       double d0 = this.player.lastX + (this.player.locX - this.player.lastX) * f;
/* 1071 */       double d1 = this.player.lastY + (this.player.locY - this.player.lastY) * f + 1.62D - this.player.height;
/* 1072 */       double d2 = this.player.lastZ + (this.player.locZ - this.player.lastZ) * f;
/* 1073 */       Vec3D vec3d = Vec3D.a(d0, d1, d2);
/*      */       
/* 1075 */       float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 1076 */       float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 1077 */       float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 1078 */       float f6 = MathHelper.sin(-f1 * 0.017453292F);
/* 1079 */       float f7 = f4 * f5;
/* 1080 */       float f8 = f3 * f5;
/* 1081 */       double d3 = (this.player.playerInteractManager.getGameMode() == EnumGamemode.CREATIVE) ? 5.0D : 4.5D;
/* 1082 */       Vec3D vec3d1 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
/* 1083 */       MovingObjectPosition movingobjectposition = this.player.world.rayTrace(vec3d, vec3d1, false);
/*      */       
/* 1085 */       if (movingobjectposition == null || movingobjectposition.type != EnumMovingObjectType.BLOCK) {
/* 1086 */         CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_AIR, this.player.inventory.getItemInHand());
/*      */       }
/*      */ 
/*      */       
/* 1090 */       PlayerAnimationEvent event = new PlayerAnimationEvent((Player)getPlayer());
/* 1091 */       this.server.getPluginManager().callEvent((Event)event);
/*      */       
/* 1093 */       if (event.isCancelled()) {
/*      */         return;
/*      */       }
/* 1096 */       this.player.ba();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(PacketPlayInEntityAction packetplayinentityaction) {
/* 1102 */     if (this.player.dead)
/*      */       return; 
/* 1104 */     this.player.v();
/* 1105 */     if (packetplayinentityaction.d() == 1 || packetplayinentityaction.d() == 2) {
/* 1106 */       PlayerToggleSneakEvent event = new PlayerToggleSneakEvent((Player)getPlayer(), (packetplayinentityaction.d() == 1));
/* 1107 */       this.server.getPluginManager().callEvent((Event)event);
/*      */       
/* 1109 */       if (event.isCancelled()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/* 1114 */     if (packetplayinentityaction.d() == 4 || packetplayinentityaction.d() == 5) {
/* 1115 */       PlayerToggleSprintEvent event = new PlayerToggleSprintEvent((Player)getPlayer(), (packetplayinentityaction.d() == 4));
/* 1116 */       this.server.getPluginManager().callEvent((Event)event);
/*      */       
/* 1118 */       if (event.isCancelled()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1124 */     if (packetplayinentityaction.d() == 1) {
/* 1125 */       this.player.setSneaking(true);
/* 1126 */     } else if (packetplayinentityaction.d() == 2) {
/* 1127 */       this.player.setSneaking(false);
/* 1128 */     } else if (packetplayinentityaction.d() == 4) {
/* 1129 */       this.player.setSprinting(true);
/* 1130 */     } else if (packetplayinentityaction.d() == 5) {
/* 1131 */       this.player.setSprinting(false);
/* 1132 */     } else if (packetplayinentityaction.d() == 3) {
/* 1133 */       this.player.a(false, true, true);
/*      */     }
/* 1135 */     else if (packetplayinentityaction.d() == 6) {
/* 1136 */       if (this.player.vehicle != null && this.player.vehicle instanceof EntityHorse) {
/* 1137 */         ((EntityHorse)this.player.vehicle).w(packetplayinentityaction.e());
/*      */       }
/* 1139 */     } else if (packetplayinentityaction.d() == 7 && this.player.vehicle != null && this.player.vehicle instanceof EntityHorse) {
/* 1140 */       ((EntityHorse)this.player.vehicle).g(this.player);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInUseEntity packetplayinuseentity) {
/* 1145 */     if (packetplayinuseentity.c() == null)
/* 1146 */       return;  if (this.player.dead)
/* 1147 */       return;  WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/* 1148 */     Entity entity = packetplayinuseentity.a(worldserver);
/*      */     
/* 1150 */     if (entity == this.player) {
/*      */       
/* 1152 */       disconnect("Cannot interact with self!");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1157 */     this.player.v();
/* 1158 */     if (entity != null) {
/* 1159 */       boolean flag = this.player.hasLineOfSight(entity);
/* 1160 */       double d0 = 36.0D;
/*      */       
/* 1162 */       if (!flag) {
/* 1163 */         d0 = 9.0D;
/*      */       }
/*      */       
/* 1166 */       if (this.player.f(entity) < d0) {
/* 1167 */         ItemStack itemInHand = this.player.inventory.getItemInHand();
/* 1168 */         if (packetplayinuseentity.c() == EnumEntityUseAction.INTERACT) {
/*      */           
/* 1170 */           boolean triggerTagUpdate = (itemInHand != null && itemInHand.getItem() == Items.NAME_TAG && entity instanceof EntityInsentient);
/* 1171 */           boolean triggerChestUpdate = (itemInHand != null && itemInHand.getItem() == Item.getItemOf(Blocks.CHEST) && entity instanceof EntityHorse);
/* 1172 */           boolean triggerLeashUpdate = (itemInHand != null && itemInHand.getItem() == Items.LEASH && entity instanceof EntityInsentient);
/* 1173 */           PlayerInteractEntityEvent event = new PlayerInteractEntityEvent((Player)getPlayer(), (Entity)entity.getBukkitEntity());
/* 1174 */           this.server.getPluginManager().callEvent((Event)event);
/*      */           
/* 1176 */           if (triggerLeashUpdate && (event.isCancelled() || this.player.inventory.getItemInHand() == null || this.player.inventory.getItemInHand().getItem() != Items.LEASH))
/*      */           {
/* 1178 */             sendPacket(new PacketPlayOutAttachEntity(1, entity, ((EntityInsentient)entity).getLeashHolder()));
/*      */           }
/*      */           
/* 1181 */           if (triggerTagUpdate && (event.isCancelled() || this.player.inventory.getItemInHand() == null || this.player.inventory.getItemInHand().getItem() != Items.NAME_TAG))
/*      */           {
/* 1183 */             sendPacket(new PacketPlayOutEntityMetadata(entity.getId(), entity.datawatcher, true));
/*      */           }
/* 1185 */           if (triggerChestUpdate && (event.isCancelled() || this.player.inventory.getItemInHand() == null || this.player.inventory.getItemInHand().getItem() != Item.getItemOf(Blocks.CHEST))) {
/* 1186 */             sendPacket(new PacketPlayOutEntityMetadata(entity.getId(), entity.datawatcher, true));
/*      */           }
/*      */           
/* 1189 */           if (event.isCancelled()) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/* 1194 */           this.player.q(entity);
/*      */ 
/*      */           
/* 1197 */           if (itemInHand != null && itemInHand.count <= -1) {
/* 1198 */             this.player.updateInventory(this.player.activeContainer);
/*      */           }
/*      */         }
/* 1201 */         else if (packetplayinuseentity.c() == EnumEntityUseAction.ATTACK) {
/* 1202 */           if (entity instanceof EntityItem || entity instanceof EntityExperienceOrb || entity instanceof EntityArrow || entity == this.player) {
/* 1203 */             disconnect("Attempting to attack an invalid entity");
/* 1204 */             this.minecraftServer.warning("Player " + this.player.getName() + " tried to attack an invalid entity");
/*      */             
/*      */             return;
/*      */           } 
/* 1208 */           this.player.attack(entity);
/*      */ 
/*      */           
/* 1211 */           if (itemInHand != null && itemInHand.count <= -1) {
/* 1212 */             this.player.updateInventory(this.player.activeContainer);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(PacketPlayInClientCommand packetplayinclientcommand) {
/* 1221 */     this.player.v();
/* 1222 */     EnumClientCommand enumclientcommand = packetplayinclientcommand.c();
/*      */     
/* 1224 */     switch (ClientCommandOrdinalWrapper.a[enumclientcommand.ordinal()]) {
/*      */       case 1:
/* 1226 */         if (this.player.viewingCredits) {
/* 1227 */           this.minecraftServer.getPlayerList().changeDimension(this.player, 0, PlayerTeleportEvent.TeleportCause.END_PORTAL); break;
/* 1228 */         }  if (this.player.r().getWorldData().isHardcore()) {
/* 1229 */           if (this.minecraftServer.N() && this.player.getName().equals(this.minecraftServer.M())) {
/* 1230 */             this.player.playerConnection.disconnect("You have died. Game over, man, it's game over!");
/* 1231 */             this.minecraftServer.U(); break;
/*      */           } 
/* 1233 */           GameProfileBanEntry gameprofilebanentry = new GameProfileBanEntry(this.player.getProfile(), (Date)null, "(You just lost the game)", (Date)null, "Death in Hardcore");
/*      */           
/* 1235 */           this.minecraftServer.getPlayerList().getProfileBans().add(gameprofilebanentry);
/* 1236 */           this.player.playerConnection.disconnect("You have died. Game over, man, it's game over!");
/*      */           break;
/*      */         } 
/* 1239 */         if (this.player.getHealth() > 0.0F) {
/*      */           return;
/*      */         }
/*      */         
/* 1243 */         this.player = this.minecraftServer.getPlayerList().moveToWorld(this.player, 0, false);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/* 1248 */         this.player.getStatisticManager().a(this.player);
/*      */         break;
/*      */       
/*      */       case 3:
/* 1252 */         this.player.a(AchievementList.f);
/*      */         break;
/*      */     } 
/*      */   }
/*      */   public void a(PacketPlayInCloseWindow packetplayinclosewindow) {
/* 1257 */     if (this.player.dead)
/*      */       return; 
/* 1259 */     CraftEventFactory.handleInventoryCloseEvent(this.player);
/*      */     
/* 1261 */     this.player.m();
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInWindowClick packetplayinwindowclick) {
/* 1265 */     if (this.player.dead)
/*      */       return; 
/* 1267 */     this.player.v();
/* 1268 */     if (!this.player.activeContainer.a(this.player))
/* 1269 */       return;  if (this.player.activeContainer.windowId == packetplayinwindowclick.c() && this.player.activeContainer.c(this.player)) {
/*      */       
/* 1271 */       if (packetplayinwindowclick.d() < -1 && packetplayinwindowclick.d() != -999) {
/*      */         return;
/*      */       }
/*      */       
/* 1275 */       InventoryView inventory = this.player.activeContainer.getBukkitView();
/*      */       
/* 1277 */       if (((Integer)NetworkManager.a(this.networkManager).attr(NetworkManager.protocolVersion).get()).intValue() >= 17)
/*      */       {
/* 1279 */         if (this.player.activeContainer instanceof ContainerEnchantTable) {
/*      */           
/* 1281 */           if (packetplayinwindowclick.slot == 1) {
/*      */             return;
/*      */           }
/* 1284 */           if (packetplayinwindowclick.slot > 1)
/*      */           {
/* 1286 */             packetplayinwindowclick.slot--;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 1291 */       InventoryType.SlotType type = CraftInventoryView.getSlotType(inventory, packetplayinwindowclick.d());
/*      */       
/* 1293 */       InventoryClickEvent event = null;
/* 1294 */       ClickType click = ClickType.UNKNOWN;
/* 1295 */       InventoryAction action = InventoryAction.UNKNOWN;
/*      */       
/* 1297 */       ItemStack itemstack = null;
/*      */       
/* 1299 */       if (packetplayinwindowclick.d() == -1) {
/* 1300 */         type = InventoryType.SlotType.OUTSIDE;
/* 1301 */         click = (packetplayinwindowclick.e() == 0) ? ClickType.WINDOW_BORDER_LEFT : ClickType.WINDOW_BORDER_RIGHT;
/* 1302 */         action = InventoryAction.NOTHING;
/* 1303 */       } else if (packetplayinwindowclick.h() == 0) {
/* 1304 */         if (packetplayinwindowclick.e() == 0) {
/* 1305 */           click = ClickType.LEFT;
/* 1306 */         } else if (packetplayinwindowclick.e() == 1) {
/* 1307 */           click = ClickType.RIGHT;
/*      */         } 
/* 1309 */         if (packetplayinwindowclick.e() == 0 || packetplayinwindowclick.e() == 1) {
/* 1310 */           action = InventoryAction.NOTHING;
/* 1311 */           if (packetplayinwindowclick.d() == -999) {
/* 1312 */             if (this.player.inventory.getCarried() != null) {
/* 1313 */               action = (packetplayinwindowclick.e() == 0) ? InventoryAction.DROP_ALL_CURSOR : InventoryAction.DROP_ONE_CURSOR;
/*      */             }
/*      */           } else {
/* 1316 */             Slot slot = this.player.activeContainer.getSlot(packetplayinwindowclick.d());
/* 1317 */             if (slot != null) {
/* 1318 */               ItemStack clickedItem = slot.getItem();
/* 1319 */               ItemStack cursor = this.player.inventory.getCarried();
/* 1320 */               if (clickedItem == null) {
/* 1321 */                 if (cursor != null) {
/* 1322 */                   action = (packetplayinwindowclick.e() == 0) ? InventoryAction.PLACE_ALL : InventoryAction.PLACE_ONE;
/*      */                 }
/* 1324 */               } else if (slot.isAllowed(this.player)) {
/* 1325 */                 if (cursor == null) {
/* 1326 */                   action = (packetplayinwindowclick.e() == 0) ? InventoryAction.PICKUP_ALL : InventoryAction.PICKUP_HALF;
/* 1327 */                 } else if (slot.isAllowed(cursor)) {
/* 1328 */                   if (clickedItem.doMaterialsMatch(cursor) && ItemStack.equals(clickedItem, cursor)) {
/* 1329 */                     int toPlace = (packetplayinwindowclick.e() == 0) ? cursor.count : 1;
/* 1330 */                     toPlace = Math.min(toPlace, clickedItem.getMaxStackSize() - clickedItem.count);
/* 1331 */                     toPlace = Math.min(toPlace, slot.inventory.getMaxStackSize() - clickedItem.count);
/* 1332 */                     if (toPlace == 1) {
/* 1333 */                       action = InventoryAction.PLACE_ONE;
/* 1334 */                     } else if (toPlace == cursor.count) {
/* 1335 */                       action = InventoryAction.PLACE_ALL;
/* 1336 */                     } else if (toPlace < 0) {
/* 1337 */                       action = (toPlace != -1) ? InventoryAction.PICKUP_SOME : InventoryAction.PICKUP_ONE;
/* 1338 */                     } else if (toPlace != 0) {
/* 1339 */                       action = InventoryAction.PLACE_SOME;
/*      */                     } 
/* 1341 */                   } else if (cursor.count <= slot.getMaxStackSize()) {
/* 1342 */                     action = InventoryAction.SWAP_WITH_CURSOR;
/*      */                   } 
/* 1344 */                 } else if (cursor.getItem() == clickedItem.getItem() && (!cursor.usesData() || cursor.getData() == clickedItem.getData()) && ItemStack.equals(cursor, clickedItem) && 
/* 1345 */                   clickedItem.count >= 0 && 
/* 1346 */                   clickedItem.count + cursor.count <= cursor.getMaxStackSize()) {
/*      */                   
/* 1348 */                   action = InventoryAction.PICKUP_ALL;
/*      */                 }
/*      */               
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/* 1356 */       } else if (packetplayinwindowclick.h() == 1) {
/* 1357 */         if (packetplayinwindowclick.e() == 0) {
/* 1358 */           click = ClickType.SHIFT_LEFT;
/* 1359 */         } else if (packetplayinwindowclick.e() == 1) {
/* 1360 */           click = ClickType.SHIFT_RIGHT;
/*      */         } 
/* 1362 */         if (packetplayinwindowclick.e() == 0 || packetplayinwindowclick.e() == 1) {
/* 1363 */           if (packetplayinwindowclick.d() < 0) {
/* 1364 */             action = InventoryAction.NOTHING;
/*      */           } else {
/* 1366 */             Slot slot = this.player.activeContainer.getSlot(packetplayinwindowclick.d());
/* 1367 */             if (slot != null && slot.isAllowed(this.player) && slot.hasItem()) {
/* 1368 */               action = InventoryAction.MOVE_TO_OTHER_INVENTORY;
/*      */             } else {
/* 1370 */               action = InventoryAction.NOTHING;
/*      */             } 
/*      */           } 
/*      */         }
/* 1374 */       } else if (packetplayinwindowclick.h() == 2) {
/* 1375 */         if (packetplayinwindowclick.e() >= 0 && packetplayinwindowclick.e() < 9) {
/* 1376 */           click = ClickType.NUMBER_KEY;
/* 1377 */           Slot clickedSlot = this.player.activeContainer.getSlot(packetplayinwindowclick.d());
/* 1378 */           if (clickedSlot.isAllowed(this.player)) {
/* 1379 */             ItemStack hotbar = this.player.inventory.getItem(packetplayinwindowclick.e());
/* 1380 */             boolean canCleanSwap = (hotbar == null || (clickedSlot.inventory == this.player.inventory && clickedSlot.isAllowed(hotbar)));
/* 1381 */             if (clickedSlot.hasItem()) {
/* 1382 */               if (canCleanSwap) {
/* 1383 */                 action = InventoryAction.HOTBAR_SWAP;
/*      */               } else {
/* 1385 */                 int firstEmptySlot = this.player.inventory.getFirstEmptySlotIndex();
/* 1386 */                 if (firstEmptySlot > -1) {
/* 1387 */                   action = InventoryAction.HOTBAR_MOVE_AND_READD;
/*      */                 } else {
/* 1389 */                   action = InventoryAction.NOTHING;
/*      */                 } 
/*      */               } 
/* 1392 */             } else if (!clickedSlot.hasItem() && hotbar != null && clickedSlot.isAllowed(hotbar)) {
/* 1393 */               action = InventoryAction.HOTBAR_SWAP;
/*      */             } else {
/* 1395 */               action = InventoryAction.NOTHING;
/*      */             } 
/*      */           } else {
/* 1398 */             action = InventoryAction.NOTHING;
/*      */           } 
/*      */           
/* 1401 */           event = new InventoryClickEvent(inventory, type, packetplayinwindowclick.d(), click, action, packetplayinwindowclick.e());
/*      */         } 
/* 1403 */       } else if (packetplayinwindowclick.h() == 3) {
/* 1404 */         if (packetplayinwindowclick.e() == 2) {
/* 1405 */           click = ClickType.MIDDLE;
/* 1406 */           if (packetplayinwindowclick.d() == -999) {
/* 1407 */             action = InventoryAction.NOTHING;
/*      */           } else {
/* 1409 */             Slot slot = this.player.activeContainer.getSlot(packetplayinwindowclick.d());
/* 1410 */             if (slot != null && slot.hasItem() && this.player.abilities.canInstantlyBuild && this.player.inventory.getCarried() == null) {
/* 1411 */               action = InventoryAction.CLONE_STACK;
/*      */             } else {
/* 1413 */               action = InventoryAction.NOTHING;
/*      */             } 
/*      */           } 
/*      */         } else {
/* 1417 */           click = ClickType.UNKNOWN;
/* 1418 */           action = InventoryAction.UNKNOWN;
/*      */         } 
/* 1420 */       } else if (packetplayinwindowclick.h() == 4) {
/* 1421 */         if (packetplayinwindowclick.d() >= 0) {
/* 1422 */           if (packetplayinwindowclick.e() == 0) {
/* 1423 */             click = ClickType.DROP;
/* 1424 */             Slot slot = this.player.activeContainer.getSlot(packetplayinwindowclick.d());
/* 1425 */             if (slot != null && slot.hasItem() && slot.isAllowed(this.player) && slot.getItem() != null && slot.getItem().getItem() != Item.getItemOf(Blocks.AIR)) {
/* 1426 */               action = InventoryAction.DROP_ONE_SLOT;
/*      */             } else {
/* 1428 */               action = InventoryAction.NOTHING;
/*      */             } 
/* 1430 */           } else if (packetplayinwindowclick.e() == 1) {
/* 1431 */             click = ClickType.CONTROL_DROP;
/* 1432 */             Slot slot = this.player.activeContainer.getSlot(packetplayinwindowclick.d());
/* 1433 */             if (slot != null && slot.hasItem() && slot.isAllowed(this.player) && slot.getItem() != null && slot.getItem().getItem() != Item.getItemOf(Blocks.AIR)) {
/* 1434 */               action = InventoryAction.DROP_ALL_SLOT;
/*      */             } else {
/* 1436 */               action = InventoryAction.NOTHING;
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/* 1441 */           click = ClickType.LEFT;
/* 1442 */           if (packetplayinwindowclick.e() == 1) {
/* 1443 */             click = ClickType.RIGHT;
/*      */           }
/* 1445 */           action = InventoryAction.NOTHING;
/*      */         } 
/* 1447 */       } else if (packetplayinwindowclick.h() == 5) {
/* 1448 */         itemstack = this.player.activeContainer.clickItem(packetplayinwindowclick.d(), packetplayinwindowclick.e(), 5, this.player);
/* 1449 */       } else if (packetplayinwindowclick.h() == 6) {
/* 1450 */         click = ClickType.DOUBLE_CLICK;
/* 1451 */         action = InventoryAction.NOTHING;
/* 1452 */         if (packetplayinwindowclick.d() >= 0 && this.player.inventory.getCarried() != null) {
/* 1453 */           ItemStack cursor = this.player.inventory.getCarried();
/* 1454 */           action = InventoryAction.NOTHING;
/*      */           
/* 1456 */           if (inventory.getTopInventory().contains(Material.getMaterial(Item.getId(cursor.getItem()))) || inventory.getBottomInventory().contains(Material.getMaterial(Item.getId(cursor.getItem())))) {
/* 1457 */             action = InventoryAction.COLLECT_TO_CURSOR;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1463 */       if (packetplayinwindowclick.h() != 5) {
/* 1464 */         CraftItemEvent craftItemEvent; if (click == ClickType.NUMBER_KEY) {
/* 1465 */           event = new InventoryClickEvent(inventory, type, packetplayinwindowclick.d(), click, action, packetplayinwindowclick.e());
/*      */         } else {
/* 1467 */           event = new InventoryClickEvent(inventory, type, packetplayinwindowclick.d(), click, action);
/*      */         } 
/*      */         
/* 1470 */         Inventory top = inventory.getTopInventory();
/* 1471 */         if (packetplayinwindowclick.d() == 0 && top instanceof CraftingInventory) {
/* 1472 */           Recipe recipe = ((CraftingInventory)top).getRecipe();
/* 1473 */           if (recipe != null) {
/* 1474 */             if (click == ClickType.NUMBER_KEY) {
/* 1475 */               craftItemEvent = new CraftItemEvent(recipe, inventory, type, packetplayinwindowclick.d(), click, action, packetplayinwindowclick.e());
/*      */             } else {
/* 1477 */               craftItemEvent = new CraftItemEvent(recipe, inventory, type, packetplayinwindowclick.d(), click, action);
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/* 1482 */         this.server.getPluginManager().callEvent((Event)craftItemEvent);
/*      */         
/* 1484 */         switch (craftItemEvent.getResult()) {
/*      */           case ALLOW:
/*      */           case DEFAULT:
/* 1487 */             itemstack = this.player.activeContainer.clickItem(packetplayinwindowclick.d(), packetplayinwindowclick.e(), packetplayinwindowclick.h(), this.player);
/*      */             
/* 1489 */             if (itemstack != null && ((itemstack.getItem() == Items.LAVA_BUCKET && PaperSpigotConfig.stackableLavaBuckets) || (itemstack.getItem() == Items.WATER_BUCKET && PaperSpigotConfig.stackableWaterBuckets) || (itemstack.getItem() == Items.MILK_BUCKET && PaperSpigotConfig.stackableMilkBuckets))) {
/*      */ 
/*      */ 
/*      */               
/* 1493 */               if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
/* 1494 */                 this.player.updateInventory(this.player.activeContainer); break;
/*      */               } 
/* 1496 */               this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.player.inventory.getCarried()));
/* 1497 */               this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, packetplayinwindowclick.d(), this.player.activeContainer.getSlot(packetplayinwindowclick.d()).getItem()));
/*      */             } 
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case DENY:
/* 1514 */             switch (action) {
/*      */               
/*      */               case ALLOW:
/*      */               case DEFAULT:
/*      */               case DENY:
/*      */               case null:
/*      */               case null:
/*      */               case null:
/* 1522 */                 this.player.updateInventory(this.player.activeContainer);
/*      */                 break;
/*      */               
/*      */               case null:
/*      */               case null:
/*      */               case null:
/*      */               case null:
/*      */               case null:
/*      */               case null:
/*      */               case null:
/* 1532 */                 this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.player.inventory.getCarried()));
/* 1533 */                 this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, packetplayinwindowclick.d(), this.player.activeContainer.getSlot(packetplayinwindowclick.d()).getItem()));
/*      */                 break;
/*      */               
/*      */               case null:
/*      */               case null:
/* 1538 */                 this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, packetplayinwindowclick.d(), this.player.activeContainer.getSlot(packetplayinwindowclick.d()).getItem()));
/*      */                 break;
/*      */               
/*      */               case null:
/*      */               case null:
/*      */               case null:
/* 1544 */                 this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.player.inventory.getCarried()));
/*      */                 break;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/* 1555 */       if (ItemStack.matches(packetplayinwindowclick.g(), itemstack)) {
/* 1556 */         this.player.playerConnection.sendPacket(new PacketPlayOutTransaction(packetplayinwindowclick.c(), packetplayinwindowclick.f(), true));
/* 1557 */         this.player.g = true;
/* 1558 */         this.player.activeContainer.b();
/* 1559 */         this.player.broadcastCarriedItem();
/* 1560 */         this.player.g = false;
/*      */       } else {
/* 1562 */         this.n.a(this.player.activeContainer.windowId, Short.valueOf(packetplayinwindowclick.f()));
/* 1563 */         this.player.playerConnection.sendPacket(new PacketPlayOutTransaction(packetplayinwindowclick.c(), packetplayinwindowclick.f(), false));
/* 1564 */         this.player.activeContainer.a(this.player, false);
/* 1565 */         ArrayList<ItemStack> arraylist = new ArrayList();
/*      */         
/* 1567 */         for (int i = 0; i < this.player.activeContainer.c.size(); i++) {
/* 1568 */           arraylist.add(((Slot)this.player.activeContainer.c.get(i)).getItem());
/*      */         }
/*      */         
/* 1571 */         this.player.a(this.player.activeContainer, arraylist);
/*      */ 
/*      */         
/* 1574 */         if (type == InventoryType.SlotType.RESULT && itemstack != null) {
/* 1575 */           this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, 0, itemstack));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(PacketPlayInEnchantItem packetplayinenchantitem) {
/* 1583 */     this.player.v();
/* 1584 */     if (this.player.activeContainer.windowId == packetplayinenchantitem.c() && this.player.activeContainer.c(this.player)) {
/* 1585 */       this.player.activeContainer.a(this.player, packetplayinenchantitem.d());
/* 1586 */       this.player.activeContainer.b();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {
/* 1591 */     if (this.player.playerInteractManager.isCreative()) {
/* 1592 */       boolean flag = (packetplayinsetcreativeslot.c() < 0);
/* 1593 */       ItemStack itemstack = packetplayinsetcreativeslot.getItemStack();
/* 1594 */       boolean flag1 = (packetplayinsetcreativeslot.c() >= 1 && packetplayinsetcreativeslot.c() < 36 + PlayerInventory.getHotbarSize());
/*      */       
/* 1596 */       boolean flag2 = (itemstack == null || (itemstack.getItem() != null && (!invalidItems.contains(Integer.valueOf(Item.getId(itemstack.getItem()))) || !SpigotConfig.filterCreativeItems)));
/* 1597 */       boolean flag3 = (itemstack == null || (itemstack.getData() >= 0 && itemstack.count <= 64 && itemstack.count > 0));
/*      */ 
/*      */       
/* 1600 */       if (flag || (flag1 && !ItemStack.matches(this.player.defaultContainer.getSlot(packetplayinsetcreativeslot.c()).getItem(), packetplayinsetcreativeslot.getItemStack()))) {
/*      */         
/* 1602 */         CraftPlayer craftPlayer = this.player.getBukkitEntity();
/* 1603 */         CraftInventoryView craftInventoryView = new CraftInventoryView((HumanEntity)craftPlayer, (Inventory)craftPlayer.getInventory(), this.player.defaultContainer);
/* 1604 */         ItemStack item = CraftItemStack.asBukkitCopy(packetplayinsetcreativeslot.getItemStack());
/*      */         
/* 1606 */         InventoryType.SlotType type = InventoryType.SlotType.QUICKBAR;
/* 1607 */         if (flag) {
/* 1608 */           type = InventoryType.SlotType.OUTSIDE;
/* 1609 */         } else if (packetplayinsetcreativeslot.c() < 36) {
/* 1610 */           if (packetplayinsetcreativeslot.c() >= 5 && packetplayinsetcreativeslot.c() < 9) {
/* 1611 */             type = InventoryType.SlotType.ARMOR;
/*      */           } else {
/* 1613 */             type = InventoryType.SlotType.CONTAINER;
/*      */           } 
/*      */         } 
/* 1616 */         InventoryCreativeEvent event = new InventoryCreativeEvent((InventoryView)craftInventoryView, type, flag ? -999 : packetplayinsetcreativeslot.c(), item);
/* 1617 */         this.server.getPluginManager().callEvent((Event)event);
/*      */         
/* 1619 */         itemstack = CraftItemStack.asNMSCopy(event.getCursor());
/*      */         
/* 1621 */         switch (event.getResult()) {
/*      */           
/*      */           case ALLOW:
/* 1624 */             flag2 = flag3 = true;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case DENY:
/* 1630 */             if (packetplayinsetcreativeslot.c() >= 0) {
/* 1631 */               this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.defaultContainer.windowId, packetplayinsetcreativeslot.c(), this.player.defaultContainer.getSlot(packetplayinsetcreativeslot.c()).getItem()));
/* 1632 */               this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, null));
/*      */             } 
/*      */             return;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/* 1639 */       if (flag1 && flag2 && flag3) {
/* 1640 */         if (itemstack == null) {
/* 1641 */           this.player.defaultContainer.setItem(packetplayinsetcreativeslot.c(), (ItemStack)null);
/*      */         } else {
/* 1643 */           this.player.defaultContainer.setItem(packetplayinsetcreativeslot.c(), itemstack);
/*      */         } 
/*      */         
/* 1646 */         this.player.defaultContainer.a(this.player, true);
/* 1647 */       } else if (flag && flag2 && flag3 && this.x < 200) {
/* 1648 */         this.x += 20;
/* 1649 */         EntityItem entityitem = this.player.drop(itemstack, true);
/*      */         
/* 1651 */         if (entityitem != null) {
/* 1652 */           entityitem.e();
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1657 */       else if (flag1) {
/*      */         
/* 1659 */         this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(0, packetplayinsetcreativeslot.c(), this.player.defaultContainer.getSlot(packetplayinsetcreativeslot.c()).getItem()));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(PacketPlayInTransaction packetplayintransaction) {
/* 1672 */     if (this.player.dead)
/* 1673 */       return;  if (!this.player.activeContainer.a(this.player))
/* 1674 */       return;  Short oshort = (Short)this.n.get(this.player.activeContainer.windowId);
/*      */     
/* 1676 */     if (oshort != null && packetplayintransaction.d() == oshort.shortValue() && this.player.activeContainer.windowId == packetplayintransaction.c() && !this.player.activeContainer.c(this.player)) {
/* 1677 */       this.player.activeContainer.a(this.player, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
/* 1682 */     if (this.player.dead)
/*      */       return; 
/* 1684 */     this.player.v();
/* 1685 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/*      */     
/* 1687 */     if (worldserver.isLoaded(packetplayinupdatesign.c(), packetplayinupdatesign.d(), packetplayinupdatesign.e())) {
/* 1688 */       TileEntity tileentity = worldserver.getTileEntity(packetplayinupdatesign.c(), packetplayinupdatesign.d(), packetplayinupdatesign.e());
/*      */       
/* 1690 */       if (tileentity instanceof TileEntitySign) {
/* 1691 */         TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */         
/* 1693 */         if (!tileentitysign.a() || tileentitysign.b() != this.player) {
/* 1694 */           this.minecraftServer.warning("Player " + this.player.getName() + " just tried to change non-editable sign");
/* 1695 */           sendPacket(new PacketPlayOutUpdateSign(packetplayinupdatesign.c(), packetplayinupdatesign.d(), packetplayinupdatesign.e(), tileentitysign.lines));
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/*      */       int j;
/*      */       
/* 1703 */       for (j = 0; j < 4; j++) {
/* 1704 */         boolean flag = true;
/* 1705 */         packetplayinupdatesign.f()[j] = packetplayinupdatesign.f()[j].replaceAll("", "").replaceAll("", "");
/*      */         
/* 1707 */         if (packetplayinupdatesign.f()[j].length() > 15) {
/* 1708 */           flag = false;
/*      */         } else {
/* 1710 */           for (int i = 0; i < packetplayinupdatesign.f()[j].length(); i++) {
/* 1711 */             if (!SharedConstants.isAllowedChatCharacter(packetplayinupdatesign.f()[j].charAt(i))) {
/* 1712 */               flag = false;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 1717 */         if (!flag) {
/* 1718 */           packetplayinupdatesign.f()[j] = "!?";
/*      */         }
/*      */       } 
/*      */       
/* 1722 */       if (tileentity instanceof TileEntitySign) {
/* 1723 */         j = packetplayinupdatesign.c();
/* 1724 */         int k = packetplayinupdatesign.d();
/*      */         
/* 1726 */         int i = packetplayinupdatesign.e();
/* 1727 */         TileEntitySign tileentitysign1 = (TileEntitySign)tileentity;
/*      */ 
/*      */         
/* 1730 */         Player player = this.server.getPlayer(this.player);
/* 1731 */         SignChangeEvent event = new SignChangeEvent(player.getWorld().getBlockAt(j, k, i), this.server.getPlayer(this.player), packetplayinupdatesign.f());
/* 1732 */         this.server.getPluginManager().callEvent((Event)event);
/*      */         
/* 1734 */         if (!event.isCancelled()) {
/* 1735 */           tileentitysign1.lines = CraftSign.sanitizeLines(event.getLines());
/* 1736 */           tileentitysign1.isEditable = false;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1741 */         tileentitysign1.update();
/* 1742 */         worldserver.notify(j, k, i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInKeepAlive packetplayinkeepalive) {
/* 1748 */     if (packetplayinkeepalive.c() == this.h) {
/* 1749 */       int i = (int)(d() - this.i);
/*      */       
/* 1751 */       this.player.ping = (this.player.ping * 3 + i) / 4;
/*      */     } 
/*      */   }
/*      */   
/*      */   private long d() {
/* 1756 */     return System.nanoTime() / 1000000L;
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(PacketPlayInAbilities packetplayinabilities) {
/* 1761 */     if (this.player.abilities.canFly && this.player.abilities.isFlying != packetplayinabilities.isFlying()) {
/* 1762 */       PlayerToggleFlightEvent event = new PlayerToggleFlightEvent(this.server.getPlayer(this.player), packetplayinabilities.isFlying());
/* 1763 */       this.server.getPluginManager().callEvent((Event)event);
/* 1764 */       if (!event.isCancelled()) {
/* 1765 */         this.player.abilities.isFlying = packetplayinabilities.isFlying();
/*      */       } else {
/* 1767 */         this.player.updateAbilities();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(PacketPlayInTabComplete packetplayintabcomplete) {
/* 1774 */     ArrayList<String> arraylist = Lists.newArrayList();
/* 1775 */     Iterator<String> iterator = this.minecraftServer.a(this.player, packetplayintabcomplete.c()).iterator();
/*      */     
/* 1777 */     while (iterator.hasNext()) {
/* 1778 */       String s = iterator.next();
/*      */       
/* 1780 */       arraylist.add(s);
/*      */     } 
/*      */     
/* 1783 */     this.player.playerConnection.sendPacket(new PacketPlayOutTabComplete(arraylist.<String>toArray(new String[arraylist.size()])));
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInSettings packetplayinsettings) {
/* 1787 */     this.player.a(packetplayinsettings);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(PacketPlayInCustomPayload packetplayincustompayload) {
/* 1796 */     if (packetplayincustompayload.length <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1801 */     if ("MC|BEdit".equals(packetplayincustompayload.c())) {
/* 1802 */       PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.wrappedBuffer(packetplayincustompayload.e()), this.networkManager.getVersion());
/*      */       
/*      */       try {
/* 1805 */         ItemStack itemstack = packetdataserializer.c();
/* 1806 */         if (itemstack != null) {
/* 1807 */           if (!ItemBookAndQuill.a(itemstack.getTag())) {
/* 1808 */             throw new IOException("Invalid book tag!");
/*      */           }
/*      */           
/* 1811 */           ItemStack itemstack1 = this.player.inventory.getItemInHand();
/* 1812 */           if (itemstack1 == null) {
/*      */             return;
/*      */           }
/*      */           
/* 1816 */           if (itemstack.getItem() == Items.BOOK_AND_QUILL && itemstack.getItem() == itemstack1.getItem()) {
/* 1817 */             CraftEventFactory.handleEditBookEvent(this.player, itemstack);
/*      */           }
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/* 1823 */       } catch (Exception exception) {
/* 1824 */         c.error("Couldn't handle book info", exception);
/* 1825 */         disconnect("Invalid book data!");
/*      */         
/*      */         return;
/*      */       } finally {
/* 1829 */         packetdataserializer.release();
/*      */       } 
/*      */       return;
/*      */     } 
/* 1833 */     if ("MC|BSign".equals(packetplayincustompayload.c())) {
/* 1834 */       PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.wrappedBuffer(packetplayincustompayload.e()), this.networkManager.getVersion());
/*      */       
/*      */       try {
/* 1837 */         ItemStack itemstack = packetdataserializer.c();
/* 1838 */         if (itemstack != null) {
/* 1839 */           if (!ItemWrittenBook.a(itemstack.getTag())) {
/* 1840 */             throw new IOException("Invalid book tag!");
/*      */           }
/*      */           
/* 1843 */           ItemStack itemstack1 = this.player.inventory.getItemInHand();
/* 1844 */           if (itemstack1 == null) {
/*      */             return;
/*      */           }
/*      */           
/* 1848 */           if (itemstack.getItem() == Items.WRITTEN_BOOK && itemstack1.getItem() == Items.BOOK_AND_QUILL) {
/* 1849 */             CraftEventFactory.handleEditBookEvent(this.player, itemstack);
/*      */           }
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/* 1855 */       } catch (Throwable exception1) {
/* 1856 */         c.error("Couldn't sign book", exception1);
/* 1857 */         disconnect("Invalid book data!");
/*      */         
/*      */         return;
/*      */       } finally {
/* 1861 */         packetdataserializer.release();
/*      */       } 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1869 */     if ("MC|TrSel".equals(packetplayincustompayload.c())) {
/*      */       try {
/* 1871 */         DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(packetplayincustompayload.e()));
/* 1872 */         int i = datainputstream.readInt();
/* 1873 */         Container container = this.player.activeContainer;
/*      */         
/* 1875 */         if (container instanceof ContainerMerchant) {
/* 1876 */           ((ContainerMerchant)container).e(i);
/*      */         }
/*      */       }
/* 1879 */       catch (Throwable exception2) {
/* 1880 */         c.error("Couldn't select trade", exception2);
/* 1881 */         disconnect("Invalid trade data!");
/*      */       }
/*      */     
/* 1884 */     } else if ("MC|AdvCdm".equals(packetplayincustompayload.c())) {
/* 1885 */       if (!this.minecraftServer.getEnableCommandBlock()) {
/* 1886 */         this.player.sendMessage(new ChatMessage("advMode.notEnabled", new Object[0]));
/* 1887 */       } else if (this.player.a(2, "") && this.player.abilities.canInstantlyBuild) {
/* 1888 */         PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.wrappedBuffer(packetplayincustompayload.e()));
/*      */         
/*      */         try {
/* 1891 */           byte b0 = packetdataserializer.readByte();
/* 1892 */           CommandBlockListenerAbstract commandblocklistenerabstract = null;
/*      */           
/* 1894 */           if (b0 == 0) {
/* 1895 */             TileEntity tileentity = this.player.world.getTileEntity(packetdataserializer.readInt(), packetdataserializer.readInt(), packetdataserializer.readInt());
/*      */             
/* 1897 */             if (tileentity instanceof TileEntityCommand) {
/* 1898 */               commandblocklistenerabstract = ((TileEntityCommand)tileentity).getCommandBlock();
/*      */             }
/* 1900 */           } else if (b0 == 1) {
/* 1901 */             Entity entity = this.player.world.getEntity(packetdataserializer.readInt());
/*      */             
/* 1903 */             if (entity instanceof EntityMinecartCommandBlock) {
/* 1904 */               commandblocklistenerabstract = ((EntityMinecartCommandBlock)entity).getCommandBlock();
/*      */             }
/*      */           } 
/*      */           
/* 1908 */           String s = packetdataserializer.c(packetdataserializer.readableBytes());
/*      */           
/* 1910 */           if (commandblocklistenerabstract != null) {
/* 1911 */             commandblocklistenerabstract.setCommand(s);
/* 1912 */             commandblocklistenerabstract.e();
/* 1913 */             this.player.sendMessage(new ChatMessage("advMode.setCommand.success", new Object[] { s }));
/*      */           }
/*      */         
/* 1916 */         } catch (Throwable exception3) {
/* 1917 */           c.error("Couldn't set command block", exception3);
/* 1918 */           disconnect("Invalid CommandBlock data!");
/*      */         } finally {
/*      */           
/* 1921 */           packetdataserializer.release();
/*      */         } 
/*      */       } else {
/* 1924 */         this.player.sendMessage(new ChatMessage("advMode.notAllowed", new Object[0]));
/*      */       } 
/* 1926 */     } else if ("MC|Beacon".equals(packetplayincustompayload.c())) {
/* 1927 */       if (this.player.activeContainer instanceof ContainerBeacon) {
/*      */         try {
/* 1929 */           DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(packetplayincustompayload.e()));
/* 1930 */           int i = dataInputStream.readInt();
/* 1931 */           int j = dataInputStream.readInt();
/* 1932 */           ContainerBeacon containerbeacon = (ContainerBeacon)this.player.activeContainer;
/* 1933 */           Slot slot = containerbeacon.getSlot(0);
/*      */           
/* 1935 */           if (slot.hasItem()) {
/* 1936 */             slot.a(1);
/* 1937 */             TileEntityBeacon tileentitybeacon = containerbeacon.e();
/*      */             
/* 1939 */             tileentitybeacon.d(i);
/* 1940 */             tileentitybeacon.e(j);
/* 1941 */             tileentitybeacon.update();
/*      */           }
/*      */         
/* 1944 */         } catch (Throwable exception4) {
/* 1945 */           c.error("Couldn't set beacon", exception4);
/* 1946 */           disconnect("Invalid beacon data!");
/*      */         }
/*      */       
/*      */       }
/* 1950 */     } else if ("MC|ItemName".equals(packetplayincustompayload.c()) && this.player.activeContainer instanceof ContainerAnvil) {
/* 1951 */       ContainerAnvil containeranvil = (ContainerAnvil)this.player.activeContainer;
/*      */       
/* 1953 */       if (packetplayincustompayload.e() != null && (packetplayincustompayload.e()).length >= 1) {
/* 1954 */         String s1 = SharedConstants.a(new String(packetplayincustompayload.e(), Charsets.UTF_8));
/*      */         
/* 1956 */         if (s1.length() <= 30) {
/* 1957 */           containeranvil.a(s1);
/*      */         }
/*      */       } else {
/* 1960 */         containeranvil.a("");
/*      */       }
/*      */     
/*      */     }
/* 1964 */     else if (packetplayincustompayload.c().equals("REGISTER")) {
/*      */       try {
/* 1966 */         String channels = new String(packetplayincustompayload.e(), "UTF8");
/* 1967 */         for (String channel : channels.split("\000")) {
/* 1968 */           getPlayer().addChannel(channel);
/*      */         }
/* 1970 */       } catch (UnsupportedEncodingException ex) {
/* 1971 */         throw new AssertionError(ex);
/*      */       } 
/* 1973 */     } else if (packetplayincustompayload.c().equals("UNREGISTER")) {
/*      */       try {
/* 1975 */         String channels = new String(packetplayincustompayload.e(), "UTF8");
/* 1976 */         for (String channel : channels.split("\000")) {
/* 1977 */           getPlayer().removeChannel(channel);
/*      */         }
/* 1979 */       } catch (UnsupportedEncodingException ex) {
/* 1980 */         throw new AssertionError(ex);
/*      */       } 
/*      */     } else {
/* 1983 */       this.server.getMessenger().dispatchIncomingMessage((Player)this.player.getBukkitEntity(), packetplayincustompayload.c(), packetplayincustompayload.e());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
/* 1990 */     if (enumprotocol1 != EnumProtocol.PLAY) {
/* 1991 */       throw new IllegalStateException("Unexpected change in protocol!");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDisconnected() {
/* 1997 */     return (!this.player.joining && !NetworkManager.a(this.networkManager).config().isAutoRead());
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */