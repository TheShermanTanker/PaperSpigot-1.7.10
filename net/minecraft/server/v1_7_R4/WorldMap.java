/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.map.CraftMapView;
/*     */ 
/*     */ 
/*     */ public class WorldMap
/*     */   extends PersistentBase
/*     */ {
/*     */   public int centerX;
/*     */   public int centerZ;
/*     */   public byte map;
/*     */   public byte scale;
/*  23 */   public byte[] colors = new byte[16384];
/*  24 */   public List f = new ArrayList();
/*  25 */   public Map i = new HashMap<Object, Object>();
/*  26 */   public Map decorations = new LinkedHashMap<Object, Object>();
/*     */   
/*     */   public final CraftMapView mapView;
/*     */   
/*     */   private CraftServer server;
/*  31 */   private UUID uniqueId = null;
/*     */ 
/*     */   
/*     */   public WorldMap(String s) {
/*  35 */     super(s);
/*     */     
/*  37 */     this.mapView = new CraftMapView(this);
/*  38 */     this.server = (CraftServer)Bukkit.getServer();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  44 */     byte dimension = nbttagcompound.getByte("dimension");
/*     */     
/*  46 */     if (dimension >= 10) {
/*  47 */       long least = nbttagcompound.getLong("UUIDLeast");
/*  48 */       long most = nbttagcompound.getLong("UUIDMost");
/*     */       
/*  50 */       if (least != 0L && most != 0L) {
/*  51 */         this.uniqueId = new UUID(most, least);
/*     */         
/*  53 */         CraftWorld world = (CraftWorld)this.server.getWorld(this.uniqueId);
/*     */         
/*  55 */         if (world == null) {
/*     */ 
/*     */           
/*  58 */           dimension = Byte.MAX_VALUE;
/*     */         } else {
/*  60 */           dimension = (byte)(world.getHandle()).dimension;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     this.map = dimension;
/*     */     
/*  67 */     this.centerX = nbttagcompound.getInt("xCenter");
/*  68 */     this.centerZ = nbttagcompound.getInt("zCenter");
/*  69 */     this.scale = nbttagcompound.getByte("scale");
/*  70 */     if (this.scale < 0) {
/*  71 */       this.scale = 0;
/*     */     }
/*     */     
/*  74 */     if (this.scale > 4) {
/*  75 */       this.scale = 4;
/*     */     }
/*     */     
/*  78 */     short short1 = nbttagcompound.getShort("width");
/*  79 */     short short2 = nbttagcompound.getShort("height");
/*     */     
/*  81 */     if (short1 == 128 && short2 == 128) {
/*  82 */       this.colors = nbttagcompound.getByteArray("colors");
/*     */     } else {
/*  84 */       byte[] abyte = nbttagcompound.getByteArray("colors");
/*     */       
/*  86 */       this.colors = new byte[16384];
/*  87 */       int i = (128 - short1) / 2;
/*  88 */       int j = (128 - short2) / 2;
/*     */       
/*  90 */       for (int k = 0; k < short2; k++) {
/*  91 */         int l = k + j;
/*     */         
/*  93 */         if (l >= 0 || l < 128) {
/*  94 */           for (int i1 = 0; i1 < short1; i1++) {
/*  95 */             int j1 = i1 + i;
/*     */             
/*  97 */             if (j1 >= 0 || j1 < 128) {
/*  98 */               this.colors[j1 + l * 128] = abyte[i1 + k * short1];
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 108 */     if (this.map >= 10) {
/* 109 */       if (this.uniqueId == null) {
/* 110 */         for (World world : this.server.getWorlds()) {
/* 111 */           CraftWorld cWorld = (CraftWorld)world;
/* 112 */           if ((cWorld.getHandle()).dimension == this.map) {
/* 113 */             this.uniqueId = cWorld.getUID();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 120 */       if (this.uniqueId != null) {
/* 121 */         nbttagcompound.setLong("UUIDLeast", this.uniqueId.getLeastSignificantBits());
/* 122 */         nbttagcompound.setLong("UUIDMost", this.uniqueId.getMostSignificantBits());
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     nbttagcompound.setByte("dimension", this.map);
/* 127 */     nbttagcompound.setInt("xCenter", this.centerX);
/* 128 */     nbttagcompound.setInt("zCenter", this.centerZ);
/* 129 */     nbttagcompound.setByte("scale", this.scale);
/* 130 */     nbttagcompound.setShort("width", (short)128);
/* 131 */     nbttagcompound.setShort("height", (short)128);
/* 132 */     nbttagcompound.setByteArray("colors", this.colors);
/*     */   }
/*     */   
/*     */   public void a(EntityHuman entityhuman, ItemStack itemstack) {
/* 136 */     if (!this.i.containsKey(entityhuman)) {
/* 137 */       WorldMapHumanTracker worldmaphumantracker = new WorldMapHumanTracker(this, entityhuman);
/*     */       
/* 139 */       this.i.put(entityhuman, worldmaphumantracker);
/* 140 */       this.f.add(worldmaphumantracker);
/*     */     } 
/*     */     
/* 143 */     if (!entityhuman.inventory.c(itemstack)) {
/* 144 */       this.decorations.remove(entityhuman.getName());
/*     */     }
/*     */     
/* 147 */     for (int i = 0; i < this.f.size(); i++) {
/* 148 */       WorldMapHumanTracker worldmaphumantracker1 = this.f.get(i);
/*     */       
/* 150 */       if (!worldmaphumantracker1.trackee.dead && (worldmaphumantracker1.trackee.inventory.c(itemstack) || itemstack.A())) {
/* 151 */         if (!itemstack.A() && worldmaphumantracker1.trackee.dimension == this.map) {
/* 152 */           a(0, worldmaphumantracker1.trackee.world, worldmaphumantracker1.trackee.getName(), worldmaphumantracker1.trackee.locX, worldmaphumantracker1.trackee.locZ, worldmaphumantracker1.trackee.yaw);
/*     */         }
/*     */       } else {
/* 155 */         this.i.remove(worldmaphumantracker1.trackee);
/* 156 */         this.f.remove(worldmaphumantracker1);
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     if (itemstack.A())
/* 161 */       a(1, entityhuman.world, "frame-" + itemstack.B().getId(), (itemstack.B()).x, (itemstack.B()).z, ((itemstack.B()).direction * 90)); 
/*     */   }
/*     */   
/*     */   private void a(int i, World world, String s, double d0, double d1, double d2) {
/*     */     byte b3;
/* 166 */     int j = 1 << this.scale;
/* 167 */     float f = (float)(d0 - this.centerX) / j;
/* 168 */     float f1 = (float)(d1 - this.centerZ) / j;
/* 169 */     byte b0 = (byte)(int)((f * 2.0F) + 0.5D);
/* 170 */     byte b1 = (byte)(int)((f1 * 2.0F) + 0.5D);
/* 171 */     byte b2 = 63;
/*     */ 
/*     */     
/* 174 */     if (f >= -b2 && f1 >= -b2 && f <= b2 && f1 <= b2) {
/* 175 */       d2 += (d2 < 0.0D) ? -8.0D : 8.0D;
/* 176 */       b3 = (byte)(int)(d2 * 16.0D / 360.0D);
/* 177 */       if (this.map < 0) {
/* 178 */         int k = (int)(world.getWorldData().getDayTime() / 10L);
/*     */         
/* 180 */         b3 = (byte)(k * k * 34187121 + k * 121 >> 15 & 0xF);
/*     */       } 
/*     */     } else {
/* 183 */       if (Math.abs(f) >= 320.0F || Math.abs(f1) >= 320.0F) {
/* 184 */         this.decorations.remove(s);
/*     */         
/*     */         return;
/*     */       } 
/* 188 */       i = 6;
/* 189 */       b3 = 0;
/* 190 */       if (f <= -b2) {
/* 191 */         b0 = (byte)(int)((b2 * 2) + 2.5D);
/*     */       }
/*     */       
/* 194 */       if (f1 <= -b2) {
/* 195 */         b1 = (byte)(int)((b2 * 2) + 2.5D);
/*     */       }
/*     */       
/* 198 */       if (f >= b2) {
/* 199 */         b0 = (byte)(b2 * 2 + 1);
/*     */       }
/*     */       
/* 202 */       if (f1 >= b2) {
/* 203 */         b1 = (byte)(b2 * 2 + 1);
/*     */       }
/*     */     } 
/*     */     
/* 207 */     this.decorations.put(s, new WorldMapDecoration(this, (byte)i, b0, b1, b3));
/*     */   }
/*     */   
/*     */   public byte[] getUpdatePacket(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 211 */     WorldMapHumanTracker worldmaphumantracker = (WorldMapHumanTracker)this.i.get(entityhuman);
/*     */     
/* 213 */     return (worldmaphumantracker == null) ? null : worldmaphumantracker.a(itemstack);
/*     */   }
/*     */   
/*     */   public void flagDirty(int i, int j, int k) {
/* 217 */     c();
/*     */     
/* 219 */     for (int l = 0; l < this.f.size(); l++) {
/* 220 */       WorldMapHumanTracker worldmaphumantracker = this.f.get(l);
/*     */       
/* 222 */       if (worldmaphumantracker.b[i] < 0 || worldmaphumantracker.b[i] > j) {
/* 223 */         worldmaphumantracker.b[i] = j;
/*     */       }
/*     */       
/* 226 */       if (worldmaphumantracker.c[i] < 0 || worldmaphumantracker.c[i] < k) {
/* 227 */         worldmaphumantracker.c[i] = k;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public WorldMapHumanTracker a(EntityHuman entityhuman) {
/* 233 */     WorldMapHumanTracker worldmaphumantracker = (WorldMapHumanTracker)this.i.get(entityhuman);
/*     */     
/* 235 */     if (worldmaphumantracker == null) {
/* 236 */       worldmaphumantracker = new WorldMapHumanTracker(this, entityhuman);
/* 237 */       this.i.put(entityhuman, worldmaphumantracker);
/* 238 */       this.f.add(worldmaphumantracker);
/*     */     } 
/*     */     
/* 241 */     return worldmaphumantracker;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */