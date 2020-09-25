/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.util.com.mojang.authlib.properties.Property;
/*     */ import net.minecraft.util.com.mojang.authlib.properties.PropertyMap;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutPlayerInfo
/*     */   extends Packet
/*     */ {
/*     */   private static final int ADD_PLAYER = 0;
/*     */   private static final int UPDATE_GAMEMODE = 1;
/*     */   private static final int UPDATE_LATENCY = 2;
/*     */   private static final int UPDATE_DISPLAY_NAME = 3;
/*     */   private static final int REMOVE_PLAYER = 4;
/*     */   private int action;
/*     */   private GameProfile player;
/*     */   private int gamemode;
/*     */   private int ping;
/*     */   private String username;
/*     */   
/*     */   public static PacketPlayOutPlayerInfo addPlayer(EntityPlayer player) {
/*  37 */     PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
/*  38 */     packet.action = 0;
/*  39 */     packet.username = player.listName;
/*  40 */     packet.player = player.getProfile();
/*  41 */     packet.ping = player.ping;
/*  42 */     packet.gamemode = player.playerInteractManager.getGameMode().getId();
/*  43 */     return packet;
/*     */   }
/*     */   
/*     */   public static PacketPlayOutPlayerInfo updatePing(EntityPlayer player) {
/*  47 */     PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
/*  48 */     packet.action = 2;
/*  49 */     packet.username = player.listName;
/*  50 */     packet.player = player.getProfile();
/*  51 */     packet.ping = player.ping;
/*  52 */     return packet;
/*     */   }
/*     */   
/*     */   public static PacketPlayOutPlayerInfo updateGamemode(EntityPlayer player) {
/*  56 */     PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
/*  57 */     packet.action = 1;
/*  58 */     packet.username = player.listName;
/*  59 */     packet.player = player.getProfile();
/*  60 */     packet.gamemode = player.playerInteractManager.getGameMode().getId();
/*  61 */     return packet;
/*     */   }
/*     */   
/*     */   public static PacketPlayOutPlayerInfo updateDisplayName(EntityPlayer player) {
/*  65 */     PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
/*  66 */     packet.action = 3;
/*  67 */     packet.username = player.listName;
/*  68 */     packet.player = player.getProfile();
/*  69 */     return packet;
/*     */   }
/*     */   
/*     */   public static PacketPlayOutPlayerInfo removePlayer(EntityPlayer player) {
/*  73 */     PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
/*  74 */     packet.action = 4;
/*  75 */     packet.username = player.listName;
/*  76 */     packet.player = player.getProfile();
/*  77 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) throws IOException {}
/*     */ 
/*     */   
/*     */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/*  86 */     String username = this.username;
/*  87 */     if (packetdataserializer.version >= 47 && this.action == 0 && username != null && username.equals(this.player.getName())) {
/*  88 */       username = null;
/*     */     }
/*     */     
/*  91 */     if (packetdataserializer.version >= 20) {
/*     */       PropertyMap properties;
/*  93 */       packetdataserializer.b(this.action);
/*  94 */       packetdataserializer.b(1);
/*  95 */       packetdataserializer.writeUUID(this.player.getId());
/*  96 */       switch (this.action) {
/*     */         
/*     */         case 0:
/*  99 */           packetdataserializer.a(this.player.getName());
/* 100 */           properties = this.player.getProperties();
/* 101 */           packetdataserializer.b(properties.size());
/* 102 */           for (Property property : properties.values()) {
/*     */             
/* 104 */             packetdataserializer.a(property.getName());
/* 105 */             packetdataserializer.a(property.getValue());
/* 106 */             packetdataserializer.writeBoolean(property.hasSignature());
/* 107 */             if (property.hasSignature())
/*     */             {
/* 109 */               packetdataserializer.a(property.getSignature());
/*     */             }
/*     */           } 
/* 112 */           packetdataserializer.b(this.gamemode);
/* 113 */           packetdataserializer.b(this.ping);
/* 114 */           packetdataserializer.writeBoolean((username != null));
/* 115 */           if (username != null)
/*     */           {
/* 117 */             packetdataserializer.a(ChatSerializer.a(CraftChatMessage.fromString(username)[0]));
/*     */           }
/*     */           break;
/*     */         case 1:
/* 121 */           packetdataserializer.b(this.gamemode);
/*     */           break;
/*     */         case 2:
/* 124 */           packetdataserializer.b(this.ping);
/*     */           break;
/*     */         case 3:
/* 127 */           packetdataserializer.writeBoolean((username != null));
/* 128 */           if (username != null)
/*     */           {
/* 130 */             packetdataserializer.a(ChatSerializer.a(CraftChatMessage.fromString(username)[0]));
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } else {
/* 138 */       packetdataserializer.a(username);
/* 139 */       packetdataserializer.writeBoolean((this.action != 4));
/* 140 */       packetdataserializer.writeShort(this.ping);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 145 */     packetplayoutlistener.a(this);
/*     */   }
/*     */   
/*     */   public void handle(PacketListener packetlistener) {
/* 149 */     a((PacketPlayOutListener)packetlistener);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutPlayerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */