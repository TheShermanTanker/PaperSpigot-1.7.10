/*      */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.SocketAddress;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import net.md_5.bungee.api.chat.BaseComponent;
/*      */ import net.minecraft.server.v1_7_R4.AttributeMapServer;
/*      */ import net.minecraft.server.v1_7_R4.AttributeModifiable;
/*      */ import net.minecraft.server.v1_7_R4.ChunkCoordinates;
/*      */ import net.minecraft.server.v1_7_R4.Container;
/*      */ import net.minecraft.server.v1_7_R4.Entity;
/*      */ import net.minecraft.server.v1_7_R4.EntityHuman;
/*      */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*      */ import net.minecraft.server.v1_7_R4.EntityTracker;
/*      */ import net.minecraft.server.v1_7_R4.EntityTrackerEntry;
/*      */ import net.minecraft.server.v1_7_R4.IAttribute;
/*      */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*      */ import net.minecraft.server.v1_7_R4.Packet;
/*      */ import net.minecraft.server.v1_7_R4.PacketPlayOutBlockChange;
/*      */ import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
/*      */ import net.minecraft.server.v1_7_R4.PacketPlayOutCustomPayload;
/*      */ import net.minecraft.server.v1_7_R4.PacketPlayOutMap;
/*      */ import net.minecraft.server.v1_7_R4.PacketPlayOutNamedSoundEffect;
/*      */ import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
/*      */ import net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent;
/*      */ import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
/*      */ import net.minecraft.server.v1_7_R4.PlayerConnection;
/*      */ import net.minecraft.server.v1_7_R4.Statistic;
/*      */ import net.minecraft.server.v1_7_R4.World;
/*      */ import net.minecraft.server.v1_7_R4.WorldServer;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.bukkit.Achievement;
/*      */ import org.bukkit.BanList;
/*      */ import org.bukkit.Effect;
/*      */ import org.bukkit.GameMode;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.OfflinePlayer;
/*      */ import org.bukkit.Sound;
/*      */ import org.bukkit.Statistic;
/*      */ import org.bukkit.WeatherType;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.conversations.Conversation;
/*      */ import org.bukkit.conversations.ConversationAbandonedEvent;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftStatistic;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*      */ import org.bukkit.entity.EntityType;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.Event;
/*      */ import org.bukkit.event.player.PlayerGameModeChangeEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent;
/*      */ import org.bukkit.inventory.InventoryView;
/*      */ import org.bukkit.map.MapView;
/*      */ import org.bukkit.material.MaterialData;
/*      */ import org.bukkit.metadata.MetadataValue;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.scoreboard.Scoreboard;
/*      */ 
/*      */ @DelegateDeserialization(CraftOfflinePlayer.class)
/*      */ public class CraftPlayer extends CraftHumanEntity implements Player {
/*   65 */   private long firstPlayed = 0L;
/*   66 */   private long lastPlayed = 0L;
/*      */   private boolean hasPlayedBefore = false;
/*   68 */   private final ConversationTracker conversationTracker = new ConversationTracker();
/*   69 */   private final Set<String> channels = new HashSet<String>();
/*   70 */   private final Set<UUID> hiddenPlayers = new HashSet<UUID>();
/*   71 */   private int hash = 0;
/*   72 */   private double health = 20.0D;
/*      */   private boolean scaledHealth = false;
/*   74 */   private double healthScale = 20.0D;
/*      */   private final Player.Spigot spigot;
/*      */   
/*   77 */   public CraftPlayer(CraftServer server, EntityPlayer entity) { super(server, (EntityHuman)entity);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1340 */     this.spigot = new Player.Spigot()
/*      */       {
/*      */ 
/*      */         
/*      */         public InetSocketAddress getRawAddress()
/*      */         {
/* 1346 */           return (InetSocketAddress)(CraftPlayer.this.getHandle()).playerConnection.networkManager.getRawAddress();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean getCollidesWithEntities() {
/* 1352 */           return (CraftPlayer.this.getHandle()).collidesWithEntities;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void setCollidesWithEntities(boolean collides) {
/* 1358 */           (CraftPlayer.this.getHandle()).collidesWithEntities = collides;
/* 1359 */           (CraftPlayer.this.getHandle()).k = collides;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void respawn() {
/* 1365 */           if (CraftPlayer.this.getHealth() <= 0.0D && CraftPlayer.this.isOnline())
/*      */           {
/* 1367 */             CraftPlayer.this.server.getServer().getPlayerList().moveToWorld(CraftPlayer.this.getHandle(), 0, false);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void playEffect(Location location, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius) {
/*      */           PacketPlayOutWorldParticles packetPlayOutWorldParticles;
/* 1374 */           Validate.notNull(location, "Location cannot be null");
/* 1375 */           Validate.notNull(effect, "Effect cannot be null");
/* 1376 */           Validate.notNull(location.getWorld(), "World cannot be null");
/*      */           
/* 1378 */           if (effect.getType() != Effect.Type.PARTICLE) {
/*      */             
/* 1380 */             int packetData = effect.getId();
/* 1381 */             PacketPlayOutWorldEvent packetPlayOutWorldEvent = new PacketPlayOutWorldEvent(packetData, location.getBlockX(), location.getBlockY(), location.getBlockZ(), id, false);
/*      */           } else {
/*      */             
/* 1384 */             StringBuilder particleFullName = new StringBuilder();
/* 1385 */             particleFullName.append(effect.getName());
/* 1386 */             if (effect.getData() != null && (effect.getData().equals(Material.class) || effect.getData().equals(MaterialData.class)))
/*      */             {
/* 1388 */               particleFullName.append('_').append(id);
/*      */             }
/* 1390 */             if (effect.getData() != null && effect.getData().equals(MaterialData.class))
/*      */             {
/* 1392 */               particleFullName.append('_').append(data);
/*      */             }
/* 1394 */             packetPlayOutWorldParticles = new PacketPlayOutWorldParticles(particleFullName.toString(), (float)location.getX(), (float)location.getY(), (float)location.getZ(), offsetX, offsetY, offsetZ, speed, particleCount);
/*      */           } 
/*      */           
/* 1397 */           radius *= radius;
/* 1398 */           if ((CraftPlayer.this.getHandle()).playerConnection == null) {
/*      */             return;
/*      */           }
/*      */           
/* 1402 */           if (!location.getWorld().equals(CraftPlayer.this.getWorld())) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/* 1407 */           int distance = (int)CraftPlayer.this.getLocation().distanceSquared(location);
/* 1408 */           if (distance <= radius)
/*      */           {
/* 1410 */             (CraftPlayer.this.getHandle()).playerConnection.sendPacket((Packet)packetPlayOutWorldParticles);
/*      */           }
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public String getLocale() {
/* 1417 */           return (CraftPlayer.this.getHandle()).locale;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public Set<Player> getHiddenPlayers() {
/* 1423 */           Set<Player> ret = new HashSet<Player>();
/* 1424 */           for (UUID u : CraftPlayer.this.hiddenPlayers)
/*      */           {
/* 1426 */             ret.add(CraftPlayer.this.getServer().getPlayer(u));
/*      */           }
/*      */           
/* 1429 */           return Collections.unmodifiableSet(ret);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void sendMessage(BaseComponent component) {
/* 1440 */           sendMessage(new BaseComponent[] { component });
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void sendMessage(BaseComponent... components) {
/* 1452 */           if ((CraftPlayer.this.getHandle()).playerConnection == null)
/*      */             return; 
/* 1454 */           PacketPlayOutChat packet = new PacketPlayOutChat();
/* 1455 */           packet.components = components;
/* 1456 */           (CraftPlayer.this.getHandle()).playerConnection.sendPacket((Packet)packet);
/*      */         }
/*      */ 
/*      */         
/*      */         public void setAffectsSpawning(boolean affects) {
/* 1461 */           (CraftPlayer.this.getHandle()).affectsSpawning = affects;
/*      */         }
/*      */         
/*      */         public boolean getAffectsSpawning() {
/* 1465 */           return (CraftPlayer.this.getHandle()).affectsSpawning;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public int getViewDistance() {
/* 1472 */           return (CraftPlayer.this.getHandle()).viewDistance;
/*      */         }
/*      */         
/*      */         public void setViewDistance(int viewDistance)
/*      */         {
/* 1477 */           ((WorldServer)(CraftPlayer.this.getHandle()).world).getPlayerChunkMap().updateViewDistance(CraftPlayer.this.getHandle(), viewDistance); }
/*      */       }; this.firstPlayed = System.currentTimeMillis(); }
/*      */   public GameProfile getProfile() { return getHandle().getProfile(); }
/*      */   public boolean isOp() { return this.server.getHandle().isOp(getProfile()); }
/*      */   public void setOp(boolean value) { if (value == isOp())
/*      */       return;  if (value) { this.server.getHandle().addOp(getProfile()); }
/*      */     else { this.server.getHandle().removeOp(getProfile()); }
/* 1484 */      this.perm.recalculatePermissions(); } public boolean isOnline() { return ((this.server.getHandle()).uuidMap.get(getUniqueId()) != null); } public Player.Spigot spigot() { return this.spigot; }
/*      */ 
/*      */   
/*      */   public InetSocketAddress getAddress() {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return null; 
/*      */     SocketAddress addr = (getHandle()).playerConnection.networkManager.getSocketAddress();
/*      */     if (addr instanceof InetSocketAddress)
/*      */       return (InetSocketAddress)addr; 
/*      */     return null;
/*      */   }
/*      */   
/*      */   public double getEyeHeight() {
/*      */     return getEyeHeight(false);
/*      */   }
/*      */   
/*      */   public double getEyeHeight(boolean ignoreSneaking) {
/*      */     if (ignoreSneaking)
/*      */       return 1.62D; 
/*      */     if (isSneaking())
/*      */       return 1.54D; 
/*      */     return 1.62D;
/*      */   }
/*      */   
/*      */   public void sendRawMessage(String message) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     for (IChatBaseComponent component : CraftChatMessage.fromString(message))
/*      */       (getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutChat(component)); 
/*      */   }
/*      */   
/*      */   public void sendMessage(String message) {
/*      */     if (!this.conversationTracker.isConversingModaly())
/*      */       sendRawMessage(message); 
/*      */   }
/*      */   
/*      */   public void sendMessage(String[] messages) {
/*      */     for (String message : messages)
/*      */       sendMessage(message); 
/*      */   }
/*      */   
/*      */   public String getDisplayName() {
/*      */     return (getHandle()).displayName;
/*      */   }
/*      */   
/*      */   public void setDisplayName(String name) {
/*      */     (getHandle()).displayName = (name == null) ? getName() : name;
/*      */   }
/*      */   
/*      */   public String getPlayerListName() {
/*      */     return (getHandle()).listName;
/*      */   }
/*      */   
/*      */   public void setPlayerListName(String name) {
/*      */     String oldName = (getHandle()).listName;
/*      */     if (name == null)
/*      */       name = getName(); 
/*      */     if (oldName.equals(name))
/*      */       return; 
/*      */     if (name.length() > 16)
/*      */       throw new IllegalArgumentException("Player list names can only be a maximum of 16 characters long"); 
/*      */     for (int i = 0; i < (this.server.getHandle()).players.size(); i++) {
/*      */       if (((EntityPlayer)(this.server.getHandle()).players.get(i)).listName.equals(name))
/*      */         throw new IllegalArgumentException(name + " is already assigned as a player list name for someone"); 
/*      */     } 
/*      */     (getHandle()).listName = name;
/*      */     String temp = (getHandle()).listName;
/*      */     (getHandle()).listName = oldName;
/*      */     PacketPlayOutPlayerInfo oldpacket = PacketPlayOutPlayerInfo.removePlayer(getHandle());
/*      */     (getHandle()).listName = temp;
/*      */     PacketPlayOutPlayerInfo packet = PacketPlayOutPlayerInfo.addPlayer(getHandle());
/*      */     PacketPlayOutPlayerInfo newPacket = PacketPlayOutPlayerInfo.updateDisplayName(getHandle());
/*      */     for (int j = 0; j < (this.server.getHandle()).players.size(); j++) {
/*      */       EntityPlayer entityplayer = (this.server.getHandle()).players.get(j);
/*      */       if (entityplayer.playerConnection != null)
/*      */         if (entityplayer.getBukkitEntity().canSee(this))
/*      */           if (entityplayer.playerConnection.networkManager.getVersion() < 28) {
/*      */             entityplayer.playerConnection.sendPacket((Packet)oldpacket);
/*      */             entityplayer.playerConnection.sendPacket((Packet)packet);
/*      */           } else {
/*      */             entityplayer.playerConnection.sendPacket((Packet)newPacket);
/*      */           }   
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean equals(Object obj) {
/*      */     if (!(obj instanceof OfflinePlayer))
/*      */       return false; 
/*      */     OfflinePlayer other = (OfflinePlayer)obj;
/*      */     if (getUniqueId() == null || other.getUniqueId() == null)
/*      */       return false; 
/*      */     boolean uuidEquals = getUniqueId().equals(other.getUniqueId());
/*      */     boolean idEquals = true;
/*      */     if (other instanceof CraftPlayer)
/*      */       idEquals = (getEntityId() == ((CraftPlayer)other).getEntityId()); 
/*      */     return (uuidEquals && idEquals);
/*      */   }
/*      */   
/*      */   public void kickPlayer(String message) {
/*      */     AsyncCatcher.catchOp("player kick");
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     (getHandle()).playerConnection.disconnect((message == null) ? "" : message);
/*      */   }
/*      */   
/*      */   public void setCompassTarget(Location loc) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     (getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutSpawnPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
/*      */   }
/*      */   
/*      */   public Location getCompassTarget() {
/*      */     return (getHandle()).compassTarget;
/*      */   }
/*      */   
/*      */   public void chat(String msg) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     (getHandle()).playerConnection.chat(msg, false);
/*      */   }
/*      */   
/*      */   public boolean performCommand(String command) {
/*      */     return this.server.dispatchCommand((CommandSender)this, command);
/*      */   }
/*      */   
/*      */   public void playNote(Location loc, byte instrument, byte note) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     String instrumentName = null;
/*      */     switch (instrument) {
/*      */       case 0:
/*      */         instrumentName = "harp";
/*      */         break;
/*      */       case 1:
/*      */         instrumentName = "bd";
/*      */         break;
/*      */       case 2:
/*      */         instrumentName = "snare";
/*      */         break;
/*      */       case 3:
/*      */         instrumentName = "hat";
/*      */         break;
/*      */       case 4:
/*      */         instrumentName = "bassattack";
/*      */         break;
/*      */     } 
/*      */     (getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutNamedSoundEffect("note." + instrumentName, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 3.0F, note));
/*      */   }
/*      */   
/*      */   public void playNote(Location loc, Instrument instrument, Note note) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     String instrumentName = null;
/*      */     switch (instrument.ordinal()) {
/*      */       case 0:
/*      */         instrumentName = "harp";
/*      */         break;
/*      */       case 1:
/*      */         instrumentName = "bd";
/*      */         break;
/*      */       case 2:
/*      */         instrumentName = "snare";
/*      */         break;
/*      */       case 3:
/*      */         instrumentName = "hat";
/*      */         break;
/*      */       case 4:
/*      */         instrumentName = "bassattack";
/*      */         break;
/*      */     } 
/*      */     (getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutNamedSoundEffect("note." + instrumentName, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 3.0F, note.getId()));
/*      */   }
/*      */   
/*      */   public void playSound(Location loc, Sound sound, float volume, float pitch) {
/*      */     if (sound == null)
/*      */       return; 
/*      */     playSound(loc, CraftSound.getSound(sound), volume, pitch);
/*      */   }
/*      */   
/*      */   public void playSound(Location loc, String sound, float volume, float pitch) {
/*      */     if (loc == null || sound == null || (getHandle()).playerConnection == null)
/*      */       return; 
/*      */     double x = loc.getBlockX() + 0.5D;
/*      */     double y = loc.getBlockY() + 0.5D;
/*      */     double z = loc.getBlockZ() + 0.5D;
/*      */     PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(sound, x, y, z, volume, pitch);
/*      */     (getHandle()).playerConnection.sendPacket((Packet)packet);
/*      */   }
/*      */   
/*      */   public void playEffect(Location loc, Effect effect, int data) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     int packetData = effect.getId();
/*      */     PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(packetData, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), data, false);
/*      */     (getHandle()).playerConnection.sendPacket((Packet)packet);
/*      */   }
/*      */   
/*      */   public <T> void playEffect(Location loc, Effect effect, T data) {
/*      */     if (data != null) {
/*      */       Validate.isTrue(data.getClass().equals(effect.getData()), "Wrong kind of data for this effect!");
/*      */     } else {
/*      */       Validate.isTrue((effect.getData() == null), "Wrong kind of data for this effect!");
/*      */     } 
/*      */     int datavalue = (data == null) ? 0 : CraftEffect.getDataValue(effect, data);
/*      */     playEffect(loc, effect, datavalue);
/*      */   }
/*      */   
/*      */   public void sendBlockChange(Location loc, Material material, byte data) {
/*      */     sendBlockChange(loc, material.getId(), data);
/*      */   }
/*      */   
/*      */   public void sendBlockChange(Location loc, int material, byte data) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), (World)((CraftWorld)loc.getWorld()).getHandle());
/*      */     packet.block = CraftMagicNumbers.getBlock(material);
/*      */     packet.data = data;
/*      */     (getHandle()).playerConnection.sendPacket((Packet)packet);
/*      */   }
/*      */   
/*      */   public void sendSignChange(Location loc, String[] lines) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     if (lines == null)
/*      */       lines = new String[4]; 
/*      */     Validate.notNull(loc, "Location can not be null");
/*      */     if (lines.length < 4)
/*      */       throw new IllegalArgumentException("Must have at least 4 lines"); 
/*      */     String[] astring = CraftSign.sanitizeLines(lines);
/*      */     (getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutUpdateSign(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), astring));
/*      */   }
/*      */   
/*      */   public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return false; 
/*      */     throw new NotImplementedException("Chunk changes do not yet work");
/*      */   }
/*      */   
/*      */   public void sendMap(MapView map) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     RenderData data = ((CraftMapView)map).render(this);
/*      */     for (int x = 0; x < 128; x++) {
/*      */       byte[] bytes = new byte[131];
/*      */       bytes[1] = (byte)x;
/*      */       for (int y = 0; y < 128; y++)
/*      */         bytes[y + 3] = data.buffer[y * 128 + x]; 
/*      */       PacketPlayOutMap packet = new PacketPlayOutMap(map.getId(), bytes, map.getScale().getValue());
/*      */       (getHandle()).playerConnection.sendPacket((Packet)packet);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
/*      */     EntityPlayer entity = getHandle();
/*      */     if (getHealth() == 0.0D || entity.dead)
/*      */       return false; 
/*      */     if (entity.playerConnection == null || entity.playerConnection.isDisconnected())
/*      */       return false; 
/*      */     if (entity.passenger != null)
/*      */       return false; 
/*      */     Location from = getLocation();
/*      */     Location to = location;
/*      */     PlayerTeleportEvent event = new PlayerTeleportEvent(this, from, to, cause);
/*      */     this.server.getPluginManager().callEvent((Event)event);
/*      */     if (event.isCancelled())
/*      */       return false; 
/*      */     entity.mount(null);
/*      */     Entity vehicle = entity.vehicle;
/*      */     Entity passenger = entity.passenger;
/*      */     if (vehicle != null) {
/*      */       vehicle.passenger = null;
/*      */       vehicle.teleportTo(location, false);
/*      */       vehicle = vehicle.getBukkitEntity().getHandle();
/*      */       entity.vehicle = vehicle;
/*      */       vehicle.passenger = (Entity)entity;
/*      */     } 
/*      */     if (passenger != null) {
/*      */       passenger.vehicle = null;
/*      */       passenger.teleportTo(location, false);
/*      */       passenger = passenger.getBukkitEntity().getHandle();
/*      */       entity.passenger = passenger;
/*      */       entity.vehicle = (Entity)entity;
/*      */     } 
/*      */     from = event.getFrom();
/*      */     to = event.getTo();
/*      */     WorldServer fromWorld = ((CraftWorld)from.getWorld()).getHandle();
/*      */     WorldServer toWorld = ((CraftWorld)to.getWorld()).getHandle();
/*      */     if ((getHandle()).activeContainer != (getHandle()).defaultContainer)
/*      */       getHandle().closeInventory(); 
/*      */     if (fromWorld == toWorld) {
/*      */       entity.playerConnection.teleport(to);
/*      */     } else {
/*      */       this.server.getHandle().moveToWorld(entity, toWorld.dimension, true, to, true);
/*      */     } 
/*      */     if (vehicle != null)
/*      */       vehicle.retrack(); 
/*      */     if (passenger != null)
/*      */       passenger.retrack(); 
/*      */     return true;
/*      */   }
/*      */   
/*      */   public void setSneaking(boolean sneak) {
/*      */     getHandle().setSneaking(sneak);
/*      */   }
/*      */   
/*      */   public boolean isSneaking() {
/*      */     return getHandle().isSneaking();
/*      */   }
/*      */   
/*      */   public boolean isSprinting() {
/*      */     return getHandle().isSprinting();
/*      */   }
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/*      */     getHandle().setSprinting(sprinting);
/*      */   }
/*      */   
/*      */   public void loadData() {
/*      */     (this.server.getHandle()).playerFileData.load((EntityHuman)getHandle());
/*      */   }
/*      */   
/*      */   public void saveData() {
/*      */     (this.server.getHandle()).playerFileData.save((EntityHuman)getHandle());
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public void updateInventory() {
/*      */     getHandle().updateInventory((getHandle()).activeContainer);
/*      */   }
/*      */   
/*      */   public void setSleepingIgnored(boolean isSleeping) {
/*      */     (getHandle()).fauxSleeping = isSleeping;
/*      */     ((CraftWorld)getWorld()).getHandle().checkSleepStatus();
/*      */   }
/*      */   
/*      */   public boolean isSleepingIgnored() {
/*      */     return (getHandle()).fauxSleeping;
/*      */   }
/*      */   
/*      */   public void awardAchievement(Achievement achievement) {
/*      */     Validate.notNull(achievement, "Achievement cannot be null");
/*      */     if (achievement.hasParent() && !hasAchievement(achievement.getParent()))
/*      */       awardAchievement(achievement.getParent()); 
/*      */     getHandle().getStatisticManager().setStatistic((EntityHuman)getHandle(), (Statistic)CraftStatistic.getNMSAchievement(achievement), 1);
/*      */     getHandle().getStatisticManager().updateStatistics(getHandle());
/*      */   }
/*      */   
/*      */   public void removeAchievement(Achievement achievement) {
/*      */     Validate.notNull(achievement, "Achievement cannot be null");
/*      */     for (Achievement achieve : Achievement.values()) {
/*      */       if (achieve.getParent() == achievement && hasAchievement(achieve))
/*      */         removeAchievement(achieve); 
/*      */     } 
/*      */     getHandle().getStatisticManager().setStatistic((EntityHuman)getHandle(), (Statistic)CraftStatistic.getNMSAchievement(achievement), 0);
/*      */   }
/*      */   
/*      */   public boolean hasAchievement(Achievement achievement) {
/*      */     Validate.notNull(achievement, "Achievement cannot be null");
/*      */     return getHandle().getStatisticManager().hasAchievement(CraftStatistic.getNMSAchievement(achievement));
/*      */   }
/*      */   
/*      */   public void incrementStatistic(Statistic statistic) {
/*      */     incrementStatistic(statistic, 1);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(Statistic statistic) {
/*      */     decrementStatistic(statistic, 1);
/*      */   }
/*      */   
/*      */   public int getStatistic(Statistic statistic) {
/*      */     Validate.notNull(statistic, "Statistic cannot be null");
/*      */     Validate.isTrue((statistic.getType() == Statistic.Type.UNTYPED), "Must supply additional paramater for this statistic");
/*      */     return getHandle().getStatisticManager().getStatisticValue(CraftStatistic.getNMSStatistic(statistic));
/*      */   }
/*      */   
/*      */   public void incrementStatistic(Statistic statistic, int amount) {
/*      */     Validate.isTrue((amount > 0), "Amount must be greater than 0");
/*      */     setStatistic(statistic, getStatistic(statistic) + amount);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(Statistic statistic, int amount) {
/*      */     Validate.isTrue((amount > 0), "Amount must be greater than 0");
/*      */     setStatistic(statistic, getStatistic(statistic) - amount);
/*      */   }
/*      */   
/*      */   public void setStatistic(Statistic statistic, int newValue) {
/*      */     Validate.notNull(statistic, "Statistic cannot be null");
/*      */     Validate.isTrue((statistic.getType() == Statistic.Type.UNTYPED), "Must supply additional paramater for this statistic");
/*      */     Validate.isTrue((newValue >= 0), "Value must be greater than or equal to 0");
/*      */     Statistic nmsStatistic = CraftStatistic.getNMSStatistic(statistic);
/*      */     getHandle().getStatisticManager().setStatistic((EntityHuman)getHandle(), nmsStatistic, newValue);
/*      */   }
/*      */   
/*      */   public void incrementStatistic(Statistic statistic, Material material) {
/*      */     incrementStatistic(statistic, material, 1);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(Statistic statistic, Material material) {
/*      */     decrementStatistic(statistic, material, 1);
/*      */   }
/*      */   
/*      */   public int getStatistic(Statistic statistic, Material material) {
/*      */     Validate.notNull(statistic, "Statistic cannot be null");
/*      */     Validate.notNull(material, "Material cannot be null");
/*      */     Validate.isTrue((statistic.getType() == Statistic.Type.BLOCK || statistic.getType() == Statistic.Type.ITEM), "This statistic does not take a Material parameter");
/*      */     Statistic nmsStatistic = CraftStatistic.getMaterialStatistic(statistic, material);
/*      */     Validate.notNull(nmsStatistic, "The supplied Material does not have a corresponding statistic");
/*      */     return getHandle().getStatisticManager().getStatisticValue(nmsStatistic);
/*      */   }
/*      */   
/*      */   public void incrementStatistic(Statistic statistic, Material material, int amount) {
/*      */     Validate.isTrue((amount > 0), "Amount must be greater than 0");
/*      */     setStatistic(statistic, material, getStatistic(statistic, material) + amount);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(Statistic statistic, Material material, int amount) {
/*      */     Validate.isTrue((amount > 0), "Amount must be greater than 0");
/*      */     setStatistic(statistic, material, getStatistic(statistic, material) - amount);
/*      */   }
/*      */   
/*      */   public void setStatistic(Statistic statistic, Material material, int newValue) {
/*      */     Validate.notNull(statistic, "Statistic cannot be null");
/*      */     Validate.notNull(material, "Material cannot be null");
/*      */     Validate.isTrue((newValue >= 0), "Value must be greater than or equal to 0");
/*      */     Validate.isTrue((statistic.getType() == Statistic.Type.BLOCK || statistic.getType() == Statistic.Type.ITEM), "This statistic does not take a Material parameter");
/*      */     Statistic nmsStatistic = CraftStatistic.getMaterialStatistic(statistic, material);
/*      */     Validate.notNull(nmsStatistic, "The supplied Material does not have a corresponding statistic");
/*      */     getHandle().getStatisticManager().setStatistic((EntityHuman)getHandle(), nmsStatistic, newValue);
/*      */   }
/*      */   
/*      */   public void incrementStatistic(Statistic statistic, EntityType entityType) {
/*      */     incrementStatistic(statistic, entityType, 1);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(Statistic statistic, EntityType entityType) {
/*      */     decrementStatistic(statistic, entityType, 1);
/*      */   }
/*      */   
/*      */   public int getStatistic(Statistic statistic, EntityType entityType) {
/*      */     Validate.notNull(statistic, "Statistic cannot be null");
/*      */     Validate.notNull(entityType, "EntityType cannot be null");
/*      */     Validate.isTrue((statistic.getType() == Statistic.Type.ENTITY), "This statistic does not take an EntityType parameter");
/*      */     Statistic nmsStatistic = CraftStatistic.getEntityStatistic(statistic, entityType);
/*      */     Validate.notNull(nmsStatistic, "The supplied EntityType does not have a corresponding statistic");
/*      */     return getHandle().getStatisticManager().getStatisticValue(nmsStatistic);
/*      */   }
/*      */   
/*      */   public void incrementStatistic(Statistic statistic, EntityType entityType, int amount) {
/*      */     Validate.isTrue((amount > 0), "Amount must be greater than 0");
/*      */     setStatistic(statistic, entityType, getStatistic(statistic, entityType) + amount);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(Statistic statistic, EntityType entityType, int amount) {
/*      */     Validate.isTrue((amount > 0), "Amount must be greater than 0");
/*      */     setStatistic(statistic, entityType, getStatistic(statistic, entityType) - amount);
/*      */   }
/*      */   
/*      */   public void setStatistic(Statistic statistic, EntityType entityType, int newValue) {
/*      */     Validate.notNull(statistic, "Statistic cannot be null");
/*      */     Validate.notNull(entityType, "EntityType cannot be null");
/*      */     Validate.isTrue((newValue >= 0), "Value must be greater than or equal to 0");
/*      */     Validate.isTrue((statistic.getType() == Statistic.Type.ENTITY), "This statistic does not take an EntityType parameter");
/*      */     Statistic nmsStatistic = CraftStatistic.getEntityStatistic(statistic, entityType);
/*      */     Validate.notNull(nmsStatistic, "The supplied EntityType does not have a corresponding statistic");
/*      */     getHandle().getStatisticManager().setStatistic((EntityHuman)getHandle(), nmsStatistic, newValue);
/*      */   }
/*      */   
/*      */   public void setPlayerTime(long time, boolean relative) {
/*      */     (getHandle()).timeOffset = time;
/*      */     (getHandle()).relativeTime = relative;
/*      */   }
/*      */   
/*      */   public long getPlayerTimeOffset() {
/*      */     return (getHandle()).timeOffset;
/*      */   }
/*      */   
/*      */   public long getPlayerTime() {
/*      */     return getHandle().getPlayerTime();
/*      */   }
/*      */   
/*      */   public boolean isPlayerTimeRelative() {
/*      */     return (getHandle()).relativeTime;
/*      */   }
/*      */   
/*      */   public void resetPlayerTime() {
/*      */     setPlayerTime(0L, true);
/*      */   }
/*      */   
/*      */   public void setPlayerWeather(WeatherType type) {
/*      */     getHandle().setPlayerWeather(type, true);
/*      */   }
/*      */   
/*      */   public WeatherType getPlayerWeather() {
/*      */     return getHandle().getPlayerWeather();
/*      */   }
/*      */   
/*      */   public void resetPlayerWeather() {
/*      */     getHandle().resetPlayerWeather();
/*      */   }
/*      */   
/*      */   public boolean isBanned() {
/*      */     return this.server.getBanList(BanList.Type.NAME).isBanned(getName());
/*      */   }
/*      */   
/*      */   public void setBanned(boolean value) {
/*      */     if (value) {
/*      */       this.server.getBanList(BanList.Type.NAME).addBan(getName(), null, null, null);
/*      */     } else {
/*      */       this.server.getBanList(BanList.Type.NAME).pardon(getName());
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isWhitelisted() {
/*      */     return this.server.getHandle().getWhitelist().isWhitelisted(getProfile());
/*      */   }
/*      */   
/*      */   public void setWhitelisted(boolean value) {
/*      */     if (value) {
/*      */       this.server.getHandle().addWhitelist(getProfile());
/*      */     } else {
/*      */       this.server.getHandle().removeWhitelist(getProfile());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setGameMode(GameMode mode) {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     if (mode == null)
/*      */       throw new IllegalArgumentException("Mode cannot be null"); 
/*      */     if (mode != getGameMode()) {
/*      */       PlayerGameModeChangeEvent event = new PlayerGameModeChangeEvent(this, mode);
/*      */       this.server.getPluginManager().callEvent((Event)event);
/*      */       if (event.isCancelled())
/*      */         return; 
/*      */       (getHandle()).playerInteractManager.setGameMode(EnumGamemode.getById(mode.getValue()));
/*      */       (getHandle()).fallDistance = 0.0F;
/*      */       (getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutGameStateChange(3, mode.getValue()));
/*      */     } 
/*      */   }
/*      */   
/*      */   public GameMode getGameMode() {
/*      */     return GameMode.getByValue((getHandle()).playerInteractManager.getGameMode().getId());
/*      */   }
/*      */   
/*      */   public void giveExp(int exp) {
/*      */     getHandle().giveExp(exp);
/*      */   }
/*      */   
/*      */   public void giveExpLevels(int levels) {
/*      */     getHandle().levelDown(levels);
/*      */   }
/*      */   
/*      */   public float getExp() {
/*      */     return (getHandle()).exp;
/*      */   }
/*      */   
/*      */   public void setExp(float exp) {
/*      */     (getHandle()).exp = exp;
/*      */     (getHandle()).lastSentExp = -1;
/*      */   }
/*      */   
/*      */   public int getLevel() {
/*      */     return (getHandle()).expLevel;
/*      */   }
/*      */   
/*      */   public void setLevel(int level) {
/*      */     (getHandle()).expLevel = level;
/*      */     (getHandle()).lastSentExp = -1;
/*      */   }
/*      */   
/*      */   public int getTotalExperience() {
/*      */     return (getHandle()).expTotal;
/*      */   }
/*      */   
/*      */   public void setTotalExperience(int exp) {
/*      */     (getHandle()).expTotal = exp;
/*      */   }
/*      */   
/*      */   public float getExhaustion() {
/*      */     return (getHandle().getFoodData()).exhaustionLevel;
/*      */   }
/*      */   
/*      */   public void setExhaustion(float value) {
/*      */     (getHandle().getFoodData()).exhaustionLevel = value;
/*      */   }
/*      */   
/*      */   public float getSaturation() {
/*      */     return (getHandle().getFoodData()).saturationLevel;
/*      */   }
/*      */   
/*      */   public void setSaturation(float value) {
/*      */     (getHandle().getFoodData()).saturationLevel = value;
/*      */   }
/*      */   
/*      */   public int getFoodLevel() {
/*      */     return (getHandle().getFoodData()).foodLevel;
/*      */   }
/*      */   
/*      */   public void setFoodLevel(int value) {
/*      */     (getHandle().getFoodData()).foodLevel = value;
/*      */   }
/*      */   
/*      */   public Location getBedSpawnLocation() {
/*      */     World world = getServer().getWorld((getHandle()).spawnWorld);
/*      */     ChunkCoordinates bed = getHandle().getBed();
/*      */     if (world != null && bed != null) {
/*      */       bed = EntityHuman.getBed((World)((CraftWorld)world).getHandle(), bed, getHandle().isRespawnForced());
/*      */       if (bed != null)
/*      */         return new Location(world, bed.x, bed.y, bed.z); 
/*      */     } 
/*      */     return null;
/*      */   }
/*      */   
/*      */   public void setBedSpawnLocation(Location location) {
/*      */     setBedSpawnLocation(location, false);
/*      */   }
/*      */   
/*      */   public void setBedSpawnLocation(Location location, boolean override) {
/*      */     if (location == null) {
/*      */       getHandle().setRespawnPosition(null, override);
/*      */     } else {
/*      */       getHandle().setRespawnPosition(new ChunkCoordinates(location.getBlockX(), location.getBlockY(), location.getBlockZ()), override);
/*      */       (getHandle()).spawnWorld = location.getWorld().getName();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void hidePlayer(Player player) {
/*      */     Validate.notNull(player, "hidden player cannot be null");
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     if (equals(player))
/*      */       return; 
/*      */     if (this.hiddenPlayers.contains(player.getUniqueId()))
/*      */       return; 
/*      */     this.hiddenPlayers.add(player.getUniqueId());
/*      */     EntityTracker tracker = ((WorldServer)this.entity.world).tracker;
/*      */     EntityPlayer other = ((CraftPlayer)player).getHandle();
/*      */     EntityTrackerEntry entry = (EntityTrackerEntry)tracker.trackedEntities.get(other.getId());
/*      */     if (entry != null)
/*      */       entry.clear(getHandle()); 
/*      */     (getHandle()).playerConnection.sendPacket((Packet)PacketPlayOutPlayerInfo.removePlayer(((CraftPlayer)player).getHandle()));
/*      */   }
/*      */   
/*      */   public void showPlayer(Player player) {
/*      */     Validate.notNull(player, "shown player cannot be null");
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     if (equals(player))
/*      */       return; 
/*      */     if (!this.hiddenPlayers.contains(player.getUniqueId()))
/*      */       return; 
/*      */     this.hiddenPlayers.remove(player.getUniqueId());
/*      */     EntityTracker tracker = ((WorldServer)this.entity.world).tracker;
/*      */     EntityPlayer other = ((CraftPlayer)player).getHandle();
/*      */     EntityTrackerEntry entry = (EntityTrackerEntry)tracker.trackedEntities.get(other.getId());
/*      */     if (entry != null && !entry.trackedPlayers.contains(getHandle()))
/*      */       entry.updatePlayer(getHandle()); 
/*      */     (getHandle()).playerConnection.sendPacket((Packet)PacketPlayOutPlayerInfo.addPlayer(((CraftPlayer)player).getHandle()));
/*      */   }
/*      */   
/*      */   public void removeDisconnectingPlayer(Player player) {
/*      */     this.hiddenPlayers.remove(player.getUniqueId());
/*      */   }
/*      */   
/*      */   public boolean canSee(Player player) {
/*      */     return !this.hiddenPlayers.contains(player.getUniqueId());
/*      */   }
/*      */   
/*      */   public Map<String, Object> serialize() {
/*      */     Map<String, Object> result = new LinkedHashMap<String, Object>();
/*      */     result.put("name", getName());
/*      */     return result;
/*      */   }
/*      */   
/*      */   public Player getPlayer() {
/*      */     return this;
/*      */   }
/*      */   
/*      */   public EntityPlayer getHandle() {
/*      */     return (EntityPlayer)this.entity;
/*      */   }
/*      */   
/*      */   public void setHandle(EntityPlayer entity) {
/*      */     setHandle((EntityHuman)entity);
/*      */   }
/*      */   
/*      */   public String toString() {
/*      */     return "CraftPlayer{name=" + getName() + '}';
/*      */   }
/*      */   
/*      */   public int hashCode() {
/*      */     if (this.hash == 0 || this.hash == 485)
/*      */       this.hash = 485 + ((getUniqueId() != null) ? getUniqueId().hashCode() : 0); 
/*      */     return this.hash;
/*      */   }
/*      */   
/*      */   public long getFirstPlayed() {
/*      */     return this.firstPlayed;
/*      */   }
/*      */   
/*      */   public long getLastPlayed() {
/*      */     return this.lastPlayed;
/*      */   }
/*      */   
/*      */   public boolean hasPlayedBefore() {
/*      */     return this.hasPlayedBefore;
/*      */   }
/*      */   
/*      */   public void setFirstPlayed(long firstPlayed) {
/*      */     this.firstPlayed = firstPlayed;
/*      */   }
/*      */   
/*      */   public void readExtraData(NBTTagCompound nbttagcompound) {
/*      */     this.hasPlayedBefore = true;
/*      */     if (nbttagcompound.hasKey("bukkit")) {
/*      */       NBTTagCompound data = nbttagcompound.getCompound("bukkit");
/*      */       if (data.hasKey("firstPlayed")) {
/*      */         this.firstPlayed = data.getLong("firstPlayed");
/*      */         this.lastPlayed = data.getLong("lastPlayed");
/*      */       } 
/*      */       if (data.hasKey("newExp")) {
/*      */         EntityPlayer handle = getHandle();
/*      */         handle.newExp = data.getInt("newExp");
/*      */         handle.newTotalExp = data.getInt("newTotalExp");
/*      */         handle.newLevel = data.getInt("newLevel");
/*      */         handle.expToDrop = data.getInt("expToDrop");
/*      */         handle.keepLevel = data.getBoolean("keepLevel");
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setExtraData(NBTTagCompound nbttagcompound) {
/*      */     if (!nbttagcompound.hasKey("bukkit"))
/*      */       nbttagcompound.set("bukkit", (NBTBase)new NBTTagCompound()); 
/*      */     NBTTagCompound data = nbttagcompound.getCompound("bukkit");
/*      */     EntityPlayer handle = getHandle();
/*      */     data.setInt("newExp", handle.newExp);
/*      */     data.setInt("newTotalExp", handle.newTotalExp);
/*      */     data.setInt("newLevel", handle.newLevel);
/*      */     data.setInt("expToDrop", handle.expToDrop);
/*      */     data.setBoolean("keepLevel", handle.keepLevel);
/*      */     data.setLong("firstPlayed", getFirstPlayed());
/*      */     data.setLong("lastPlayed", System.currentTimeMillis());
/*      */     data.setString("lastKnownName", handle.getName());
/*      */   }
/*      */   
/*      */   public boolean beginConversation(Conversation conversation) {
/*      */     return this.conversationTracker.beginConversation(conversation);
/*      */   }
/*      */   
/*      */   public void abandonConversation(Conversation conversation) {
/*      */     this.conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, (ConversationCanceller)new ManuallyAbandonedConversationCanceller()));
/*      */   }
/*      */   
/*      */   public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
/*      */     this.conversationTracker.abandonConversation(conversation, details);
/*      */   }
/*      */   
/*      */   public void acceptConversationInput(String input) {
/*      */     this.conversationTracker.acceptConversationInput(input);
/*      */   }
/*      */   
/*      */   public boolean isConversing() {
/*      */     return this.conversationTracker.isConversing();
/*      */   }
/*      */   
/*      */   public void sendPluginMessage(Plugin source, String channel, byte[] message) {
/*      */     StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     if (this.channels.contains(channel)) {
/*      */       PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(channel, message);
/*      */       (getHandle()).playerConnection.sendPacket((Packet)packet);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setTexturePack(String url) {
/*      */     setResourcePack(url);
/*      */   }
/*      */   
/*      */   public void setResourcePack(String url) {
/*      */     Validate.notNull(url, "Resource pack URL cannot be null");
/*      */     getHandle().setResourcePack(url);
/*      */   }
/*      */   
/*      */   public void addChannel(String channel) {
/*      */     Preconditions.checkState((this.channels.size() < 128), "Too many channels registered");
/*      */     if (this.channels.add(channel))
/*      */       this.server.getPluginManager().callEvent((Event)new PlayerRegisterChannelEvent(this, channel)); 
/*      */   }
/*      */   
/*      */   public void removeChannel(String channel) {
/*      */     if (this.channels.remove(channel))
/*      */       this.server.getPluginManager().callEvent((Event)new PlayerUnregisterChannelEvent(this, channel)); 
/*      */   }
/*      */   
/*      */   public Set<String> getListeningPluginChannels() {
/*      */     return (Set<String>)ImmutableSet.copyOf(this.channels);
/*      */   }
/*      */   
/*      */   public void sendSupportedChannels() {
/*      */     if ((getHandle()).playerConnection == null)
/*      */       return; 
/*      */     Set<String> listening = this.server.getMessenger().getIncomingChannels();
/*      */     if (!listening.isEmpty()) {
/*      */       ByteArrayOutputStream stream = new ByteArrayOutputStream();
/*      */       for (String channel : listening) {
/*      */         try {
/*      */           stream.write(channel.getBytes("UTF8"));
/*      */           stream.write(0);
/*      */         } catch (IOException ex) {
/*      */           Logger.getLogger(CraftPlayer.class.getName()).log(Level.SEVERE, "Could not send Plugin Channel REGISTER to " + getName(), ex);
/*      */         } 
/*      */       } 
/*      */       (getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutCustomPayload("REGISTER", stream.toByteArray()));
/*      */     } 
/*      */   }
/*      */   
/*      */   public EntityType getType() {
/*      */     return EntityType.PLAYER;
/*      */   }
/*      */   
/*      */   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
/*      */     this.server.getPlayerMetadata().setMetadata(this, metadataKey, newMetadataValue);
/*      */   }
/*      */   
/*      */   public List<MetadataValue> getMetadata(String metadataKey) {
/*      */     return this.server.getPlayerMetadata().getMetadata(this, metadataKey);
/*      */   }
/*      */   
/*      */   public boolean hasMetadata(String metadataKey) {
/*      */     return this.server.getPlayerMetadata().hasMetadata(this, metadataKey);
/*      */   }
/*      */   
/*      */   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
/*      */     this.server.getPlayerMetadata().removeMetadata(this, metadataKey, owningPlugin);
/*      */   }
/*      */   
/*      */   public boolean setWindowProperty(InventoryView.Property prop, int value) {
/*      */     Container container = (getHandle()).activeContainer;
/*      */     if (container.getBukkitView().getType() != prop.getType())
/*      */       return false; 
/*      */     getHandle().setContainerData(container, prop.getId(), value);
/*      */     return true;
/*      */   }
/*      */   
/*      */   public void disconnect(String reason) {
/*      */     this.conversationTracker.abandonAllConversations();
/*      */     this.perm.clearPermissions();
/*      */   }
/*      */   
/*      */   public boolean isFlying() {
/*      */     return (getHandle()).abilities.isFlying;
/*      */   }
/*      */   
/*      */   public void setFlying(boolean value) {
/*      */     boolean needsUpdate = ((getHandle()).abilities.canFly != value);
/*      */     if (!getAllowFlight() && value)
/*      */       throw new IllegalArgumentException("Cannot make player fly if getAllowFlight() is false"); 
/*      */     (getHandle()).abilities.isFlying = value;
/*      */     if (needsUpdate)
/*      */       getHandle().updateAbilities(); 
/*      */   }
/*      */   
/*      */   public boolean getAllowFlight() {
/*      */     return (getHandle()).abilities.canFly;
/*      */   }
/*      */   
/*      */   public void setAllowFlight(boolean value) {
/*      */     if (isFlying() && !value)
/*      */       (getHandle()).abilities.isFlying = false; 
/*      */     (getHandle()).abilities.canFly = value;
/*      */     getHandle().updateAbilities();
/*      */   }
/*      */   
/*      */   public int getNoDamageTicks() {
/*      */     if ((getHandle()).invulnerableTicks > 0)
/*      */       return Math.max((getHandle()).invulnerableTicks, (getHandle()).noDamageTicks); 
/*      */     return (getHandle()).noDamageTicks;
/*      */   }
/*      */   
/*      */   public void setFlySpeed(float value) {
/*      */     validateSpeed(value);
/*      */     EntityPlayer player = getHandle();
/*      */     player.abilities.flySpeed = Math.max(value, 1.0E-4F) / 2.0F;
/*      */     player.updateAbilities();
/*      */   }
/*      */   
/*      */   public void setWalkSpeed(float value) {
/*      */     validateSpeed(value);
/*      */     EntityPlayer player = getHandle();
/*      */     player.abilities.walkSpeed = Math.max(value, 1.0E-4F) / 2.0F;
/*      */     player.updateAbilities();
/*      */   }
/*      */   
/*      */   public float getFlySpeed() {
/*      */     return (getHandle()).abilities.flySpeed * 2.0F;
/*      */   }
/*      */   
/*      */   public float getWalkSpeed() {
/*      */     return (getHandle()).abilities.walkSpeed * 2.0F;
/*      */   }
/*      */   
/*      */   private void validateSpeed(float value) {
/*      */     if (value < 0.0F) {
/*      */       if (value < -1.0F)
/*      */         throw new IllegalArgumentException(value + " is too low"); 
/*      */     } else if (value > 1.0F) {
/*      */       throw new IllegalArgumentException(value + " is too high");
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setMaxHealth(double amount) {
/*      */     super.setMaxHealth(amount);
/*      */     this.health = Math.min(this.health, this.health);
/*      */     getHandle().triggerHealthUpdate();
/*      */   }
/*      */   
/*      */   public void resetMaxHealth() {
/*      */     super.resetMaxHealth();
/*      */     getHandle().triggerHealthUpdate();
/*      */   }
/*      */   
/*      */   public CraftScoreboard getScoreboard() {
/*      */     return this.server.getScoreboardManager().getPlayerBoard(this);
/*      */   }
/*      */   
/*      */   public void setScoreboard(Scoreboard scoreboard) {
/*      */     Validate.notNull(scoreboard, "Scoreboard cannot be null");
/*      */     PlayerConnection playerConnection = (getHandle()).playerConnection;
/*      */     if (playerConnection == null)
/*      */       throw new IllegalStateException("Cannot set scoreboard yet"); 
/*      */     if (playerConnection.isDisconnected());
/*      */     this.server.getScoreboardManager().setPlayerBoard(this, scoreboard);
/*      */   }
/*      */   
/*      */   public void setHealthScale(double value) {
/*      */     Validate.isTrue(((float)value > 0.0F), "Must be greater than 0");
/*      */     this.healthScale = value;
/*      */     this.scaledHealth = true;
/*      */     updateScaledHealth();
/*      */   }
/*      */   
/*      */   public double getHealthScale() {
/*      */     return this.healthScale;
/*      */   }
/*      */   
/*      */   public void setHealthScaled(boolean scale) {
/*      */     if (this.scaledHealth != (this.scaledHealth = scale))
/*      */       updateScaledHealth(); 
/*      */   }
/*      */   
/*      */   public boolean isHealthScaled() {
/*      */     return this.scaledHealth;
/*      */   }
/*      */   
/*      */   public float getScaledHealth() {
/*      */     return (float)(isHealthScaled() ? (getHealth() * getHealthScale() / getMaxHealth()) : getHealth());
/*      */   }
/*      */   
/*      */   public double getHealth() {
/*      */     return this.health;
/*      */   }
/*      */   
/*      */   public void setRealHealth(double health) {
/*      */     this.health = health;
/*      */   }
/*      */   
/*      */   public void updateScaledHealth() {
/*      */     AttributeMapServer attributemapserver = (AttributeMapServer)getHandle().getAttributeMap();
/*      */     Set set = attributemapserver.getAttributes();
/*      */     injectScaledMaxHealth(set, true);
/*      */     getHandle().getDataWatcher().watch(6, Float.valueOf(getScaledHealth()));
/*      */     (getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutUpdateHealth(getScaledHealth(), getHandle().getFoodData().getFoodLevel(), getHandle().getFoodData().getSaturationLevel()));
/*      */     (getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutUpdateAttributes(getHandle().getId(), set));
/*      */     set.clear();
/*      */     (getHandle()).maxHealthCache = getMaxHealth();
/*      */   }
/*      */   
/*      */   public void injectScaledMaxHealth(Collection<AttributeModifiable> collection, boolean force) {
/*      */     if (!this.scaledHealth && !force)
/*      */       return; 
/*      */     for (Object genericInstance : collection) {
/*      */       IAttribute attribute = ((AttributeInstance)genericInstance).getAttribute();
/*      */       if (attribute.getName().equals("generic.maxHealth")) {
/*      */         collection.remove(genericInstance);
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     double healthMod = this.scaledHealth ? this.healthScale : getMaxHealth();
/*      */     if (healthMod >= 3.4028234663852886E38D || healthMod <= 0.0D) {
/*      */       healthMod = 20.0D;
/*      */       getServer().getLogger().warning(getName() + " tried to crash the server with a large health attribute");
/*      */     } 
/*      */     collection.add(new AttributeModifiable(getHandle().getAttributeMap(), (IAttribute)(new AttributeRanged("generic.maxHealth", healthMod, 0.0D, 3.4028234663852886E38D)).a("Max Health").a(true)));
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */